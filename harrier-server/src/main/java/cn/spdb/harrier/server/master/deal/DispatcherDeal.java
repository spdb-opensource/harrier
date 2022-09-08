package cn.spdb.harrier.server.master.deal;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.utils.Host;
import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.master.MasterManagerService;

public class DispatcherDeal implements Runnable, Deal {

	private Logger logger = LoggerFactory.getLogger("Master-" + this.getClass().getSimpleName());

	private LinkedBlockingQueue<String> signal = new LinkedBlockingQueue<String>();

	private PriorityBlockingQueue<JobExecutionContext> dispatcherQueue = new PriorityBlockingQueue<JobExecutionContext>(
			200, new Comparator<JobExecutionContext>() {
				@Override
				public int compare(JobExecutionContext o1, JobExecutionContext o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					if (o1.getPriority() == o2.getPriority()) {
						if (o1.getDispatcherTime() != null && o2.getDispatcherTime() != null) {
							if (o1.getDispatcherTime().equals(o2.getDispatcherTime())) {
								return o1.getJob().compareTo(o2.getJob());
							}
							return o1.getDispatcherTime().compareTo(o2.getDispatcherTime());
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
				signal.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			synchronized (dispatcherQueue) {
				try {
					while (Stopper.isRunning()) {
						try {
							if (STOP.get()) {
								Thread.sleep(1000 * 10);
								continue;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						JobExecutionContext jobContext = dispatcherQueue.peek();
						if (ObjectUtils.isEmpty(jobContext)) {
							break;
						}
						List<JobStepBean> stepList = masterService.getJobStepList(jobContext.getPlatform(),
								jobContext.getSystem(), jobContext.getJob());
						if (CollectionUtils.isEmpty(stepList)) {
							logger.error("job step is null,job:{}", jobContext);
							dispatcherQueue.remove(jobContext);
							masterService.conversionFailure(jobContext);
							masterService.insertJobRecord(jobContext);
							continue;
						}
						jobContext.setStepList(stepList);
						//
						synchronized (DispatcherDeal.class) {
							Host host = masterService.passDispatcherGetHost(jobContext);
							if (ObjectUtils.isEmpty(host)) {
								logger.debug("job select host is null,job:{}", jobContext);
								break;
							}
							masterService.incrementWeightAndJob(host, jobContext);
							jobContext.setHost(host);
							jobContext.getUdsJob().setServerName(host.getName());
							long id = masterService.getSingleId();
							jobContext.setTaskInstanceId(id);
							if (masterService.conversionRuning(jobContext)) {
								if (masterService.sendJobExecutionContext(jobContext)) {
									logger.info("job dispatcher success,job:{}", jobContext);
									jobContext.getUdsJob().setEndTime(null);
									masterService.insertJobRecord(jobContext);
									dispatcherQueue.remove(jobContext);
								} else {
									logger.info("job dispatcher failure,job:{}", jobContext);
									masterService.decrementWeightAndJob(jobContext.getHost(), jobContext);
									masterService.updateJobStatus(ExecutionStatus.DISPATCHER, jobContext.getPlatform(),
											jobContext.getSystem(), jobContext.getJob());
								}
							} else {
								masterService.decrementWeightAndJob(jobContext.getHost(), jobContext);
								masterService.updateJobStatus(ExecutionStatus.DISPATCHER, jobContext.getPlatform(),
										jobContext.getSystem(), jobContext.getJob());
							}
						}
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stop() {
		STOP.set(true);
		dispatcherQueue.clear();
	}

	public void start() {
		STOP.set(false);
	}

	public void addQueue(JobExecutionContext jobContext) {
		synchronized (dispatcherQueue) {			
			if (dispatcherQueue.contains(jobContext)) {
				dispatcherQueue.remove();
			}
			dispatcherQueue.offer(jobContext);
		}
		signal.offer(jobContext.getPlatform());
	}

	public DispatcherDeal(MasterManagerService masterService) {
		super();
		this.masterService = masterService;
	}

	public void addSignal(String platfrom) {
		signal.offer(platfrom);
	}
}
