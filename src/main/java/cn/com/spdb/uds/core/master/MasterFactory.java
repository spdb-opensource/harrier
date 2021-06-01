package cn.com.spdb.uds.core.master;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.PlatformSystemCenterBean;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.filter.pending.AbstractPendingFilter;
import cn.com.spdb.uds.core.filter.pending.JobDependFilter;
import cn.com.spdb.uds.core.filter.pending.JobTimeWindowFilter;
import cn.com.spdb.uds.core.master.plan.AbstractDispatcherPlan;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;
import cn.com.spdb.uds.utils.Symbol;

public class MasterFactory {
	/** 等待处理线程 */
	private ThreadPoolExecutor executorPending = new ThreadPoolExecutor(10, 10, 10000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),
			new NameThreadFactory(MasterFactory.class.getSimpleName() + "Pending"));
	/** 分发处理线程 */
	private ThreadPoolExecutor executorDispatcher = new ThreadPoolExecutor(10, 10, 10000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),
			new NameThreadFactory(MasterFactory.class.getSimpleName() + "Dispatcher"));

	private LinkedBlockingQueue<String> signalQueue = new LinkedBlockingQueue<String>();
	private ConcurrentHashMap<String, AtomicInteger> signalNum = new ConcurrentHashMap<String, AtomicInteger>();

	/** 等待作业过滤 */
	private List<AbstractPendingFilter> jobPendingFilterList = new ArrayList<AbstractPendingFilter>();

	/** 平台作业分发队列 */
	private ConcurrentHashMap<String, PlatformSystemCenterBean> platformMap = new ConcurrentHashMap<String, PlatformSystemCenterBean>();

	private int checkDBDispatcherNum = 0;

	private static Object LOCK = new Object();

	public MasterFactory() {
		init();
	}

	private void init() {
		// jobPendingFilterList.add(new JobSystemMaxNumFilter());
		jobPendingFilterList.add(new JobTimeWindowFilter());
		jobPendingFilterList.add(new JobDependFilter());
	}

	/** 提交Pending */
	private void submitExecutorPending(UdsJobBean udsJobBean, PlatformSystemCenterBean platformSystemConterBean) {
		executorPending.submit(new PendingJobConsumer(udsJobBean, platformSystemConterBean));
	}

	public void start() {
		NameThreadFactory factory = new NameThreadFactory(MasterFactory.class.getSimpleName() + "SinghtJobDeal");
		factory.newThread(true, new PlaformJobSignalTake()).start();
	}

	/** 信号量进入计数和队列 */
	public void offerDispatcherSignlaQueue(String string) {
		AtomicInteger atomicInteger = signalNum.get(string);
		if (atomicInteger == null) {
			signalNum.put(string, atomicInteger = new AtomicInteger(0));
		}
		// 大于3个不再添加
		if (atomicInteger.get() >= 5) {
			return;
		}
		atomicInteger.incrementAndGet();
		signalQueue.offer(string);
	}

	/** 信号量减少 */
	private int decrementSinnalNum(String keyJobPlafrom) {
		AtomicInteger atomicInteger = signalNum.get(keyJobPlafrom);
		if (atomicInteger != null) {
			return atomicInteger.decrementAndGet();
		}
		return 0;
	}

	/** 等待检测 */
	public void checkDbToPending() {
		if (!MasterManager.getInstance().isCheckRecive()) {
			return;
		}
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		for (UdsSystemBean systemBean : UdsConstant.MAP_SYSTEM_JOB.values()) {
			if (!systemBean.getSystem().equals(Symbol.XING_HAO)) {
				continue;
			}
			String platform = systemBean.getPlatform();
			PlatformSystemCenterBean conterBean = platformMap.get(platform);
			if (conterBean == null) {
				platformMap.put(platform, conterBean = new PlatformSystemCenterBean(platform));
			}
			if (systemBean.getMax_run_job() <= 0) {
				UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG PLATFORM NUM IS 0", systemBean.getSystem(),
						systemBean.getPlatform());
				continue;
			}
			// 是否处理完
			if (!conterBean.isDealJobfinsh()) {
				continue;
			}
			// 是否获等待处理的作业
			if (conterBean.isWaitDealJobinsh()) {
				int initNum = conterBean.getWaitInitNum();
				List<UdsJobBean> jobList = udsJobBaseDao.checkPendingJobByPlatformOrder(platform, initNum);
				if (jobList.size() < UdsConstant.CHECK_DB_PENDING_LIMIT_NUM) {
					initNum = 0;
				} else {
					initNum += UdsConstant.CHECK_DB_PENDING_LIMIT_NUM;
				}
				conterBean.setWaitInitNum(initNum);
				conterBean.addWaitDealJobAll(jobList);
			}
			// 等待处理到处理中
			ArrayList<UdsJobBean> dealJobList = conterBean.transfromWaitToDeal(UdsConstant.CHECK_PENDING_LIMIT_NUM);
			if (dealJobList.size() <= 0) {
				continue;
			}
			// 提交处理
			for (UdsJobBean udsJobBean : dealJobList) {
				submitExecutorPending(udsJobBean, conterBean);
			}
			UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG SUBMIT", systemBean.getPlatform(),
					systemBean.getSystem(), dealJobList.size(), dealJobList.size());
		}
	}

	/** 分发检测 */
	public void checkPendingToDispatcher() {
		if (!MasterManager.getInstance().isCheckRecive()) {
			return;
		}
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// 数据超时校验
		if (checkDBDispatcherNum == 0) {
			List<UdsJobBean> tmpList = udsJobBaseDao.getJobStatusDispatcherOverTime();
			for (UdsJobBean bean : tmpList) {
				PlatformSystemCenterBean platformSystemConterBean = platformMap.get(bean.getPlatform());
				if (platformSystemConterBean == null) {
					platformMap.put(bean.getPlatform(),
							platformSystemConterBean = new PlatformSystemCenterBean(bean.getPlatform()));
				}
				platformSystemConterBean.addDispatcherQueue(bean);
				offerDispatcherSignlaQueue(bean.getPlatfromSytemKey());
			}
			int size = tmpList.size();
			if (size > 0) {
				UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "checkDBDispatcherNum:", size);
			}
		}
		checkDBDispatcherNum = ++checkDBDispatcherNum >= UdsConstant.CHECK_DB_DISPATCHER_NUM ? 0 : checkDBDispatcherNum;

		int tmpNum = 0;
		// 更新状态，发送信号
		for (PlatformSystemCenterBean conterBean : platformMap.values()) {
			if (conterBean == null) {
				continue;
			}
			tmpNum = 0;
			UdsSystemBean systemBean = UdsConstant.getUdsSystemBean(conterBean.getPlatform(), Symbol.XING_HAO);
			int max = systemBean.getMax_run_job();
			if (max <= 0) {
				UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER PLATFORM NUM IS 0",
						conterBean.getPlatform());
				continue;
			}
			List<UdsJobBean> updateDispatcherWaitList = conterBean.getNewOrRemoveUpdateDispatcherJobList();
			if (updateDispatcherWaitList.size() > 0) {
				ArrayList<String> jobs = new ArrayList<String>(updateDispatcherWaitList.size());
				for (UdsJobBean bean : updateDispatcherWaitList) {
					jobs.add(bean.getJob());
					tmpNum++;
				}
				if (udsJobBaseDao.updateAllJobStatusDispatcher(jobs) > 0) {
					updateDispatcherWaitList.forEach(new Consumer<UdsJobBean>() {
						@Override
						public void accept(UdsJobBean udsJobBean) {
							conterBean.addDispatcherQueue(udsJobBean);
							offerDispatcherSignlaQueue(udsJobBean.getPlatfromSytemKey());
						}
					});

				}
			}
			offerDispatcherSignlaQueue(systemBean.getPlatformAndSystemKey());
			conterBean.checkRemovePlatformConterMapBytime(DateUtils.TIME_MILLSECOND_OF_DAY);
			if (tmpNum > 0) {
				UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER SUBMIT", conterBean.getPlatform(),
						tmpNum);
			}
		}
	}

	/** 信号出列 */
	private class DispatcherJobConsumer implements Runnable {

		public DispatcherJobConsumer(String platformSystemKey, PlatformSystemCenterBean platformSystemConterBean) {
			this.platformSystemKey = platformSystemKey;
			this.platformSystemConterBean = platformSystemConterBean;
			this.createTime = System.currentTimeMillis();
		}

		private String platformSystemKey = null;
		private PlatformSystemCenterBean platformSystemConterBean = null;
		private long createTime = 0L;

		@Override
		public void run() {
			try {
				while (true) {
					// 平台信息
					UdsSystemBean systemBean = UdsConstant.getUdsSystemBean(platformSystemConterBean.getPlatform(),
							Symbol.XING_HAO);
					if (systemBean == null) {
						UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER PLATFORM IS NULL");
						return;
					}
					// 平台最大运行数
					int max = systemBean.getMax_run_job();
					// 平台是否关闭并发
					if (max <= 0) {
						UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER PLATFORM NUM IS 0",
								systemBean.getPlatform(), systemBean.getSystem());
						return;
					}
					// 获取作业
					UdsJobBean udsJobBean = platformSystemConterBean.dispatcherQueuePoll(platformSystemKey);
					if (udsJobBean == null) {
						return;
					}

					// 应用参数
					UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(platformSystemKey);
					if (udsSystemBean == null) {
						UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER SYSTEM IS NULL");
						return;
					}
					// 机器选择
					UdsRpcClient client = null;
					synchronized (LOCK) {
						// 当前应用的运行数
						int num = MasterManager.getInstance().getChildServerSystemSum(udsSystemBean.getPlatform(),
								udsSystemBean.getSystem());
						if (num >= max) {
							UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER SYSTEM NUM IS MAX",
									systemBean.getPlatform(), systemBean.getSystem());
							return;
						}
						client = AbstractDispatcherPlan.getUdsRpcClient(udsSystemBean, udsJobBean);
						if (client == null) {
							platformSystemConterBean.addDispatcherQueue(udsJobBean);
							return;
						}
						// 地域判断
						if ((client.getLocation() & UdsConstant.LOCATION) <= 0) {
							UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "CLIENT LOCATION NOT MY LOCATION",
									client.getLocation(), UdsConstant.LOCATION);
							return;
						}
						if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM) {
							if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM) {
								if (!MasterManager.getInstance().checkLimitWeight(udsJobBean)) {
									UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER,
											"MASTER DISPATCHER JOB IS NOT LIMITWEIGHT ", udsJobBean.getJob());
									platformSystemConterBean.addDispatcherQueue(udsJobBean);
									return;
								}
								// 权重控制
								MasterManager.getInstance().addWeight(client.getServerName(), udsJobBean);
							}
						}
						// 单独并发控制
						MasterManager.getInstance().incrementChildServerSystem(client.getServerName(),
								udsJobBean.getPlatform(), udsJobBean.getSystem());
					}

					// 数据库更新
					UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
					udsJobBaseDao.updateJobServerNameByLastStatus(udsJobBean.getPlatform(), udsJobBean.getSystem(),
							udsJobBean.getJob(), client.getServerName(), JobStatus.DISPATCHER);
					UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER job", udsJobBean.toString());
					UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(client.getServerName(),
							RpcCommand.DISTRIBUTION_JOB);
					UdsRpcClientManager.getInstance().sendMessage(client, udsRpcEvent, udsJobBean.getJob());
					if (System.currentTimeMillis() - createTime > 1 * DateUtils.TIME_MILLSECOND_OF_MINUTE) {
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				decrementSinnalNum(platformSystemKey);
			}
		}
	}

	/** 信号处理 */
	private class PlaformJobSignalTake implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					if (!MasterManager.getInstance().isCheckRecive()) {
						Thread.sleep(1000 * 10);
						continue;
					}
					String keyJobPlafrom = signalQueue.take();
					String[] tmpStr = keyJobPlafrom.split(Symbol.XIA_HUA_XIAN);
					if (tmpStr.length < 1) {
						continue;
					}
					PlatformSystemCenterBean platformSystemConterBean = platformMap.get(tmpStr[0]);
					if (platformSystemConterBean != null) {
						UdsJobBean udsJobBean = platformSystemConterBean.dispatcherQueuePeek(keyJobPlafrom);
						if (udsJobBean != null) {
							executorDispatcher.submit(new DispatcherJobConsumer(udsJobBean.getPlatfromSytemKey(),
									platformSystemConterBean));
						} else {
							decrementSinnalNum(keyJobPlafrom);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/** Pending依赖检测作业 */
	private class PendingJobConsumer implements Runnable {
		private UdsJobBean udsJobBean = null;

		private PlatformSystemCenterBean platformSystemConterBean = null;

		public PendingJobConsumer(UdsJobBean udsJobBean, PlatformSystemCenterBean platformSystemConterBean) {
			this.udsJobBean = udsJobBean;
			this.platformSystemConterBean = platformSystemConterBean;
		}

		@Override
		public void run() {
			if (udsJobBean == null) {
				return;
			}

			try {
				UdsSystemBean udsplatform = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(), Symbol.XING_HAO);
				if (udsplatform == null) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob());
					return;
				}
				// 平台参数设置为0停止对该平台分发
				if (udsplatform.getMax_run_job() <= 0) {
					UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG PLATFORM NUM IS 0",
							udsplatform.getSystem(), udsplatform.getPlatform());
					return;
				}
				UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(),
						udsJobBean.getSystem());
				if (udsSystemBean == null) {
					return;
				}
				boolean pass = true;
				for (AbstractPendingFilter filter : jobPendingFilterList) {
					if (!filter.checkDependency(udsJobBean, udsSystemBean)) {
						pass = false;
						break;
					}
				}
				if (pass) {
					platformSystemConterBean.addUpdateDispatcherJobList(udsJobBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				platformSystemConterBean.removeDealJobQueueJob(udsJobBean);
			}
		}
	}

	public ThreadPoolExecutor getExecutorPending() {
		return executorPending;
	}

	public void setExecutorPending(ThreadPoolExecutor executorPending) {
		this.executorPending = executorPending;
	}

	public ThreadPoolExecutor getExecutorDispatcher() {
		return executorDispatcher;
	}

	public void setExecutorDispatcher(ThreadPoolExecutor executorDispatcher) {
		this.executorDispatcher = executorDispatcher;
	}

	public int getCheckDBDispatcherNum() {
		return checkDBDispatcherNum;
	}

	public void setCheckDBDispatcherNum(int checkDBDispatcherNum) {
		this.checkDBDispatcherNum = checkDBDispatcherNum;
	}

	public LinkedBlockingQueue<String> getSignalQueue() {
		return signalQueue;
	}

	public void setSignalQueue(LinkedBlockingQueue<String> signalQueue) {
		this.signalQueue = signalQueue;
	}

	public ConcurrentHashMap<String, AtomicInteger> getSignalNum() {
		return signalNum;
	}

	public void setSignalNum(ConcurrentHashMap<String, AtomicInteger> sinnalNum) {
		this.signalNum = sinnalNum;
	}

	public List<AbstractPendingFilter> getJobPendingFilterList() {
		return jobPendingFilterList;
	}

	public void setJobPendingFilterList(List<AbstractPendingFilter> jobPendingFilterList) {
		this.jobPendingFilterList = jobPendingFilterList;
	}

	public ConcurrentHashMap<String, PlatformSystemCenterBean> getPlatformMap() {
		return platformMap;
	}

	public void setPlatformMap(ConcurrentHashMap<String, PlatformSystemCenterBean> platformMap) {
		this.platformMap = platformMap;
	}

}
