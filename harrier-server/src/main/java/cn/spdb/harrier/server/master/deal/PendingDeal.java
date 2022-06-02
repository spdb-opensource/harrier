package cn.spdb.harrier.server.master.deal;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.uitls.Stopper;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.master.MasterManagerService;

public class PendingDeal implements Runnable,Deal {

	private Logger logger = LoggerFactory.getLogger("Master-"+this.getClass().getSimpleName());

	private PriorityBlockingQueue<JobExecutionContext> pendingQueue = new PriorityBlockingQueue<JobExecutionContext>(
			200, new Comparator<JobExecutionContext>() {
				@Override
				public int compare(JobExecutionContext o1, JobExecutionContext o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					if (o1.getPriority() == o2.getPriority()) {
						if (o1.getPendingTime() != null && o2.getPendingTime() != null) {
							if (o1.getPendingTime().equals(o2.getPendingTime())) {
								return o1.getJob().compareTo(o2.getJob());
							}
							return o1.getPendingTime().compareTo(o2.getPendingTime());
						}
					}
					return o2.getPriority() - o1.getPriority();
				}
			});

	private final AtomicBoolean STOP = new AtomicBoolean(false);
	private MasterManagerService masterService;

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					continue;
				}
				JobExecutionContext jobContext = pendingQueue.take();
				if (!masterService.checkJobWinds(jobContext.getUdsJobConfig())) {
					logger.debug("job:{} is not pass Winds {}", jobContext,
							jobContext.getUdsJobConfig().getTimeWindow());
					continue;
				}
				if (!masterService.checkDependency(jobContext.getUdsJob())) {
					logger.debug("job is not dependency,jog:{}", jobContext);
					continue;
				}
				if (jobContext.getUdsJobConfig().getVirtualEnable()) {
					jobContext.getUdsJob().setDispatcherTime(LocalDateTime.now());
					jobContext.getUdsJob().setNumTimes(jobContext.getUdsJob().getNumTimes() + 1);
					jobContext.getUdsJob().setServerName("");
					jobContext.getUdsJob().setStartTime(LocalDateTime.now());
					logger.info("job is virtual conversion success,jog:{}", jobContext);
					masterService.conversionSuccess(jobContext);
					masterService.insertJobRecord(jobContext);
					masterService.conversionSuccessAfter(jobContext);
				} else {
					masterService.conversionDispatcher(jobContext);
					masterService.addDispatcherDealQueue(jobContext);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addQueue(JobExecutionContext jobContext) {
		pendingQueue.offer(jobContext);
	}

	public void start() {
		STOP.set(false);
	}

	public void stop() {
		STOP.set(true);
		pendingQueue.clear();
	}

	public PendingDeal(MasterManagerService masterService) {
		this.masterService = masterService;
	}
}
