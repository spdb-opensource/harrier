package cn.spdb.harrier.server.master.deal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.spdb.harrier.common.uitls.NameThreadFactory;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.master.MasterManagerService;

public class PlatformDealExecutor {

	private String name = "";

	private MasterManagerService masterService;

	private PendingDeal pendingDeal;

	private DispatcherDeal dispatcherDeal;

	private DepStreamJobDeal depStreamJobDeal;

	private SelfStreamJobDeal selfStreamJobDeal;

	private ExecutorService executor = Executors.newFixedThreadPool(5,
			new NameThreadFactory("Master-" + PlatformDealExecutor.class.getSimpleName() + Symbol.MAO_HAO + getName()));

	public PlatformDealExecutor(MasterManagerService masterService) {
		this.masterService = masterService;
		this.pendingDeal = new PendingDeal(masterService);
		this.dispatcherDeal = new DispatcherDeal(masterService);
		this.depStreamJobDeal = new DepStreamJobDeal(masterService);
		this.selfStreamJobDeal = new SelfStreamJobDeal(masterService);
	}

	public void addPendingQueue(JobExecutionContext jobContext) {
		pendingDeal.addQueue(jobContext);
	}

	public void addDispatcherDealQueue(JobExecutionContext jobContext) {
		dispatcherDeal.addQueue(jobContext);
	}

	public void addDepStreamJobDealQueue(JobExecutionContext jobContext) {
		depStreamJobDeal.addQueue(jobContext);
	}

	public void addSelfStreamJobDealQueue(JobExecutionContext jobContext) {
		selfStreamJobDeal.addQueue(jobContext);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void execute() {
		executor.execute(dispatcherDeal);
		executor.execute(pendingDeal);
		executor.execute(depStreamJobDeal);
		executor.execute(selfStreamJobDeal);
	}

	public void start() {
		dispatcherDeal.start();
		pendingDeal.start();
		depStreamJobDeal.start();
		selfStreamJobDeal.start();
	}

	public void stop() {
		dispatcherDeal.stop();
		pendingDeal.stop();
		depStreamJobDeal.stop();
		selfStreamJobDeal.stop();
	}

	public void shutdown() {
		selfStreamJobDeal.stop();
		executor.shutdown();
	}

	public void addReadyDispatcherDealSignal(String platfrom) {
		dispatcherDeal.addSignal(platfrom);
	}

	public MasterManagerService getMasterService() {
		return masterService;
	}

	public void setMasterService(MasterManagerService masterService) {
		this.masterService = masterService;
	}

}
