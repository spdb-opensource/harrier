package cn.spdb.harrier.server.worker;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.Host;
import cn.spdb.harrier.common.utils.IPUtils;
import cn.spdb.harrier.common.utils.NameThreadFactory;
import cn.spdb.harrier.common.utils.OSUtils;
import cn.spdb.harrier.common.utils.PropertyUtils;
import cn.spdb.harrier.common.utils.SnowFlakeBuidID;
import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.common.utils.ThreadUtils;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.WorkingInfo;
import cn.spdb.harrier.server.master.rpc.transport.MasterTransportServerInterfasce;
import cn.spdb.harrier.server.worker.cache.TaskExecutionContextCacheManager;
import cn.spdb.harrier.server.worker.cache.TaskExecutionContextCacheManagerImpl;
import cn.spdb.harrier.server.worker.conf.WorkerConfig;
import cn.spdb.harrier.server.worker.exec.ExecuteRunable;
import cn.spdb.harrier.server.worker.exec.TaskExecuteRunnable;
import cn.spdb.harrier.service.db.DbRegistryService;

@Component
public class WorkerManagerThread implements Runnable {

	private final DelayQueue<ExecuteRunable> workerExecuteQueue = new DelayQueue<ExecuteRunable>();

	private final Logger logger = LoggerFactory.getLogger(WorkerManagerThread.class.getSimpleName());

	private final ExecutorService workerExecService;

	private final WorkerConfig workerConfig;

	private SnowFlakeBuidID buildID;

	private WorkingInfo workingInfo = new WorkingInfo();

	private UdsServer udsServer;

	private TaskExecutionContextCacheManager taskCache = new TaskExecutionContextCacheManagerImpl();

	private ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(
			Runtime.getRuntime().availableProcessors(), new NameThreadFactory("Worker-Scheduled-Service"));

	@Autowired
	private DbRegistryService dbRegistryService;

	@Autowired
	public WorkerManagerThread(WorkerConfig workerConfig) {
		this.workerConfig = workerConfig;
		this.workerExecService = ThreadUtils.newDaemonFixedThreadExecutor("Worker-Execute-Thread",
				this.workerConfig.getWorkerExecThreads());
	}

	/**
	 * get queue size
	 *
	 * @return queue size
	 */
	public int getQueueSize() {
		return workerExecuteQueue.size();
	}

	/**
	 * submit task
	 *
	 * @param taskExecuteRunnable taskExecuteThread
	 * @return submit result
	 */
	public boolean offer(ExecuteRunable taskExecuteRunnable) {
		taskCache.cacheTaskExecutionContext(taskExecuteRunnable);
		return workerExecuteQueue.offer(taskExecuteRunnable);

	}

	@PostConstruct
	public void start() {
		Thread thread = new Thread(this, "Worker-Execute-Manager-Thread");
		thread.setDaemon(true);
		thread.start();
		UdsServer udsServer = new UdsServer();
		udsServer.setPort(workerConfig.getListenPort());
		udsServer.setServerRoleName(workerConfig.getRoleName());
		udsServer.setServerRoleNameGroup(workerConfig.getRoleGroup());
		udsServer.setNodeClusterType(Constants.THREAD_NAME_WORKER_SERVER);
		udsServer.setIp(IPUtils.getHostIp());
		udsServer.setServerName(workerConfig.getServerName());
		udsServer = dbRegistryService.registrydb(udsServer, event -> {
		});
		logger.info("Registry Service address:{}", udsServer.getIp() + Symbol.MAO_HAO + udsServer.getPort());
		this.udsServer=udsServer;
		workingInfo.setHost(new Host(udsServer.getIp(), udsServer.getPort(), udsServer.getServerName()));
		workingInfo.setJobNumMax(workerConfig.getWorkerExecThreads());
		workingInfo.setId(udsServer.getId());
		buildID = new SnowFlakeBuidID(udsServer.getId());
		scheduledService.scheduleWithFixedDelay(() -> {
			try {
				sendWorkingInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 30, 30, TimeUnit.SECONDS);
	}

	private void sendWorkingInfo() {
		for (UdsServer udsServer : dbRegistryService.getUdsServerList()) {
			if (udsServer.getNodeClusterType().equals(Constants.THREAD_NAME_MASTER_SERVER) && udsServer.getIsEnable()) {
				MasterTransportServerInterfasce serverInterfasce = null;
				try {
					serverInterfasce = RpcClient.getInstance().create(MasterTransportServerInterfasce.class,
							new URI("spdb", udsServer.getIp(), udsServer.getPort()));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				if (!ObjectUtils.isEmpty(serverInterfasce)) {
					workingInfo.setCpu(OSUtils.cpuUsage());
					workingInfo.setMemory(OSUtils.memoryUsage());
					workingInfo.setLoadAverage(OSUtils.loadAverage());
					serverInterfasce.updateWorkingInfo(workingInfo);
				}
			}
		}
	}

	public void killTaskBeforeExecuteByInstanceId(Long taskInstanceId) {
		workerExecuteQueue.stream().filter(
				taskExecuteThread -> taskExecuteThread.getJobExecutionContext().getTaskInstanceId() == taskInstanceId)
				.forEach(workerExecuteQueue::remove);
		ExecuteRunable exec = taskCache.getByTaskInstanceId(taskInstanceId);
		if (ObjectUtils.isNotEmpty(exec)) {
			exec.kill();
		}
	}

	public void insertUdsJobRecord(JobExecutionContext jobContext) {
		UdsServer udsServer = getLeaderMaster();
		if (ObjectUtils.isEmpty(udsServer)) {
			return;
		}
		try {
			MasterTransportServerInterfasce serverInterfasce = RpcClient.getInstance().create(
					MasterTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			if (ObjectUtils.isEmpty(serverInterfasce)) {
				return;
			}
			serverInterfasce.insertUdsJobRecord(jobContext);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("insertUdsJobRecord", e);
		}
	}
	public void insertUdsJobStepRecord(UdsJobStepRecord record) {
		UdsServer udsServer = getLeaderMaster();
		if (ObjectUtils.isEmpty(udsServer)) {
			return;
		}
		try {
			MasterTransportServerInterfasce serverInterfasce = RpcClient.getInstance().create(
					MasterTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			if (ObjectUtils.isEmpty(serverInterfasce)) {
				return;
			}
			record.setAddress(workingInfo.getHost().getAddress());
			serverInterfasce.insertUdsJobStepRecord(record);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("insertUdsJobStepRecord", e);
		}
	}

	public void updatejobDone(JobExecutionContext jobContext) {
		UdsServer udsServer = getLeaderMaster();
		if (ObjectUtils.isEmpty(udsServer)) {
			return;
		}
		try {
			MasterTransportServerInterfasce serverInterfasce = RpcClient.getInstance().create(
					MasterTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			if (ObjectUtils.isEmpty(serverInterfasce)) {
				return;
			}
			serverInterfasce.jobContextDone(jobContext);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("updatejobDone", e);
		} finally {
			taskCache.removeByTaskInstanceId(jobContext.getTaskInstanceId());
		}
	}

	public void updateJobStepRecord(UdsJobStepRecord record) {
		UdsServer udsServer = getLeaderMaster();
		if (ObjectUtils.isEmpty(udsServer)) {
			return;
		}
		try {
			MasterTransportServerInterfasce serverInterfasce = RpcClient.getInstance().create(
					MasterTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			if (ObjectUtils.isEmpty(serverInterfasce)) {
				return;
			}
			serverInterfasce.updateJobStepRecord(record);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("updateJobStepRecord", e);
		}
	}

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				ExecuteRunable taskExecuteRunnable = workerExecuteQueue.take();
				workerExecService.submit(taskExecuteRunnable);
			} catch (Exception e) {
				logger.error("An unexpected interrupt is happened, "
						+ "the exception will be ignored and this thread will continue to run", e);
			}
		}
	}

	public void incrementJob(JobExecutionContext jobExecutionContext) {
		workingInfo.incrementJob(jobExecutionContext);
	}

	public void decrementJob(JobExecutionContext jobExecutionContext) {
		workingInfo.decrementJob(jobExecutionContext);
	}

	public WorkingInfo getWorkingInfo() {
		return workingInfo;
	}

	public void setWorkingInfo(WorkingInfo workingInfo) {
		this.workingInfo = workingInfo;
	}

	public UdsServer getLeaderMaster() {
		return dbRegistryService.getMaster();
	}

	public long getSingleId() {
		if (ObjectUtils.isEmpty(buildID)) {
			buildID = new SnowFlakeBuidID();
		}
		return buildID.getNextId();
	}

	public WorkerConfig getWorkerConfig() {
		return workerConfig;
	}

	
}
