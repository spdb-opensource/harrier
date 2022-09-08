package cn.spdb.harrier.server.master.deal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.master.MasterManagerService;

public class SelfStreamJobDeal implements Runnable, Deal {

	private final AtomicBoolean STOP = new AtomicBoolean(false);
	private MasterManagerService masterService;

	private LinkedBlockingQueue<JobExecutionContext> selfStreamQueue = new LinkedBlockingQueue<JobExecutionContext>();

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					continue;
				}
				JobExecutionContext jobContext = selfStreamQueue.take();
				masterService.selfStream(jobContext);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		STOP.set(true);
		selfStreamQueue.clear();
	}

	public void start() {
		STOP.set(false);
	}

	public void addQueue(JobExecutionContext jobContext) {
		selfStreamQueue.offer(jobContext);
	}

	public SelfStreamJobDeal(MasterManagerService masterService) {
		super();
		this.masterService = masterService;
	}

}
