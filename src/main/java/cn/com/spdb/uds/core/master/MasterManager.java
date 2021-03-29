package cn.com.spdb.uds.core.master;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.SchedulerManager;
import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.PlatformConterBean;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.filter.pending.AbstractPendingFilter;
import cn.com.spdb.uds.core.filter.pending.JobDependFilter;
import cn.com.spdb.uds.core.filter.pending.JobSystemMaxNumFilter;
import cn.com.spdb.uds.core.filter.pending.JobTimeWindowFilter;
import cn.com.spdb.uds.core.filter.receiver.AbstractReceiverFilter;
import cn.com.spdb.uds.core.filter.receiver.JobFilter;
import cn.com.spdb.uds.core.filter.receiver.JobFrequencyFilter;
import cn.com.spdb.uds.core.master.plan.AbstractDispatcherPlan;
import cn.com.spdb.uds.core.master.plan.AppointDispatcher;
import cn.com.spdb.uds.core.master.plan.AppointOrderDispatcher;
import cn.com.spdb.uds.core.master.plan.AppointTagsDispatcher;
import cn.com.spdb.uds.core.master.plan.CommonDispatcher;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateTriggerBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.db.dao.UdsJobWeightDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;
import cn.com.spdb.uds.utils.UdsUtils;

public class MasterManager {

	private final static Object KEY = new Object();

	public static MasterManager instance;

	public static MasterManager getInstance() {
		synchronized (KEY) {
			if (instance == null) {
				instance = new MasterManager();
				instance.init();
			}
		}
		return instance;
	}

	/** 本节点是在检测分发 */
	private boolean checkRecive = true;
	// 接受目录作业过滤
	private List<AbstractReceiverFilter> jobReceiverFilterList = new ArrayList<AbstractReceiverFilter>();
	// 等待作业过滤
	private List<AbstractPendingFilter> jobPendingFilterList = new ArrayList<AbstractPendingFilter>();
	// 子节点机器运行作业数
	private ConcurrentHashMap<String, ChildServerInfo> childServerJobMap = new ConcurrentHashMap<String, ChildServerInfo>();

	private MasterPendingJobDisposeFactory pendingJobDisposeFactory = new MasterPendingJobDisposeFactory();

	/**
	 * 获取一个系统目前运行的数量
	 * 
	 * @param key {@link UdsJobBean} : platform+"_"+system use
	 *            {@link UdsConstant#getUdsSystemBean(String, String)}
	 * @return
	 */
	public int getSystemJobNum(String platfrom, String system) {
		int num = 0;
		for (Entry<String, ChildServerInfo> entry : childServerJobMap.entrySet()) {
			ChildServerInfo childServerInfo = entry.getValue();
			num += childServerInfo.getSystemJob(platfrom, system);
		}
		return num;
	}

	public void incrementChildServerSystem(String serverName, String platform, String system) {
		ChildServerInfo childServerInfo = childServerJobMap.get(serverName);
		if (childServerInfo != null) {
			childServerInfo.incrementSystemJob(platform, system);
		}
	}

	public void decrementChildServerSystem(String serverName, String platform, String system) {
		ChildServerInfo childServerInfo = childServerJobMap.get(serverName);
		if (childServerInfo != null) {
			childServerInfo.decrementSystemJob(platform, system);
		}
	}

	public void addChildServerJob(String serverName, ChildServerInfo childServerInfo) {
		ChildServerInfo info = childServerJobMap.get(serverName);
		if (info == null) {
			childServerJobMap.put(serverName, childServerInfo);
		} else {
			if (info.isStabilize()) {
				childServerJobMap.put(serverName, childServerInfo);
			}
		}
	}

	public ChildServerInfo removeChildServerJobMap(String serverName) {
		return childServerJobMap.remove(serverName);
	}

	private void init() {
		// 接受过滤
		jobReceiverFilterList.add(new JobFilter());
		jobReceiverFilterList.add(new JobFrequencyFilter());
		// 等待过滤
		jobPendingFilterList.add(new JobSystemMaxNumFilter());
		jobPendingFilterList.add(new JobTimeWindowFilter());
		jobPendingFilterList.add(new JobDependFilter());
		// 策略
		new CommonDispatcher();
		new AppointDispatcher();
		new AppointOrderDispatcher();
		new AppointTagsDispatcher();

		SchedulerManager.getInstance().scheduleWithFixedDelay("MASTERMANAGER_CHECKRECEIVERDIR", new Runnable() {

			@Override
			public void run() {
				try {
					checkReceiverDir();
				} catch (Exception e) {
					UdsLogger.logEvent(LogEvent.ERROR, "checkReceiverDir", e.getMessage());
					e.printStackTrace();
				}
			}
		}, 20 * DateUtils.TIME_MILLSECOND_OF_SECOND, 20 * DateUtils.TIME_MILLSECOND_OF_SECOND);

		
		SchedulerManager.getInstance().scheduleWithFixedDelay("MASTERMANAGER_CHECKDBSCHEDULERJOB", new Runnable() {
		  
			@Override 
			public void run() { 
				try { 
				  checkDbSchedulerJob();
				} catch (Exception e) { 
					UdsLogger.logEvent(LogEvent.ERROR, "checkDbSchedulerJob",e.getMessage()); 
					e.printStackTrace(); 
				} 
			} 
		}, 10 * DateUtils.TIME_MILLSECOND_OF_SECOND + 5 * DateUtils.TIME_MILLSECOND_OF_SECOND, 20 * DateUtils.TIME_MILLSECOND_OF_SECOND);
		 

		SchedulerManager.getInstance().scheduleWithFixedDelay("MASTERMANAGER_CHECKDBPENDING", new Runnable() {
			@Override
			public void run() {
				try {
					checkDbPendingToFactory();
				} catch (Exception e) {
					UdsLogger.logEvent(LogEvent.ERROR, "checkDbPending", e.getMessage());
					e.printStackTrace();
				}
			}
		}, 10 * DateUtils.TIME_MILLSECOND_OF_SECOND + 10 * DateUtils.TIME_MILLSECOND_OF_SECOND,
				20 * DateUtils.TIME_MILLSECOND_OF_SECOND);

		SchedulerManager.getInstance().scheduleWithFixedDelay("CHECKPENDING_FACTORYTODISPATCHER", new Runnable() {

			@Override
			public void run() {
				try {
					checkPendingFactoryToDispatcher();
				} catch (Exception e) {
					UdsLogger.logEvent(LogEvent.ERROR, "checkPendingFactoryToDispatcher", e.getMessage());
					e.printStackTrace();
				}
			}
		}, 10 * DateUtils.TIME_MILLSECOND_OF_SECOND + 10 * DateUtils.TIME_MILLSECOND_OF_SECOND,
				20 * DateUtils.TIME_MILLSECOND_OF_SECOND);

		SchedulerManager.getInstance().scheduleWithFixedDelay("MASTERMANAGER_CHECKCALLAGAINJOB", new Runnable() {

			@Override
			public void run() {
				try {
					checkCallAgainJob();
				} catch (Exception e) {
					UdsLogger.logEvent(LogEvent.ERROR, "checkCallAgainJob", e.getMessage());
					e.printStackTrace();
				}
			}
		}, 10 * DateUtils.TIME_MILLSECOND_OF_SECOND + 15 * DateUtils.TIME_MILLSECOND_OF_SECOND,
				5 * DateUtils.TIME_MILLSECOND_OF_MINUTE);
	}

	/**
	 * 检测数据库时间触发作业
	 */
	public void checkDbSchedulerJob() {
		if (!isCheckRecive()) {
			return;
		}
		UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		UdsJobBaseDao jobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// 获取本地符合要求的定时作业
		List<UdsJobDateTriggerBean> list = controlDao.checkDateTriggerJobByLocation(UdsConstant.LOCATION);
		HashMap<String, HashMap<Object, Object>> jobsMaps = new HashMap<String, HashMap<Object, Object>>();
		HashMap<String, HashMap<Object, Object>> jobsStartMaps = new HashMap<String, HashMap<Object, Object>>();

		/** 是否跳过更新作业，并更新时间 */
		boolean isContinus = false;
		/** 是否转移至触发文件夹 */
		boolean isStream = false;
		for (UdsJobDateTriggerBean bean : list) {
			try {
				// 想要真实关闭报警要关闭定时出发,等改作业处理完再开
				if (!(bean.getLast_status().equals(JobStatus.DONE.status())
						|| bean.getLast_status().equals(JobStatus.READY.status()))) {
					if (UdsConstant.SCHEDULER_FAIL_MOVE_STREAM == UdsConstant.TRUE_NUM
							&& bean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
						UdsLogger.logEvent(LogEvent.MASTER_JOB_SCHEDULER_STATUS_ERROR,
								"uds job status is error create stream file", bean.getJob(), bean.getLast_status(),
								bean.getJob_type());
						isStream = true;
						isContinus = true;
					} else {
						if (bean.getLast_status().equals(JobStatus.FAILED.status())) {
							UdsLogger.logEvent(LogEvent.MASTER_JOB_SCHEDULER_STATUS_ERROR, "uds job is Fail  ",
									bean.getJob(), bean.getLast_status(), bean.getJob_type());
						} else {
							UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_SCHEDULER_STATUS_ERROR, UdsErrorLevel.H,
									bean.getJob(), DateUtils.getDateTime(new Date(), DateUtils.PATTERN_YYYYMMDD_CONS),
									bean.getJob_type(), bean.getLast_status());
						}
						continue;
					}
				}
				StringBuffer bufferCron = new StringBuffer();
				bufferCron.append(bean.getSecond()).append(" ").append(bean.getMinute()).append(" ")
						.append(bean.getHour()).append(" ").append(bean.getDay()).append(" ").append(bean.getMonth())
						.append(" ").append(bean.getWeek()).append(" ").append(bean.getYear());
				String cron = bufferCron.toString();
				Date startDate = new Date(bean.getStart_time().getTime());
				// 下次触发的时间
				Date nextDate = null;
				try {
					nextDate = DateUtils.getNextValidTimeAfter(cron, startDate);
				} catch (ParseException e) {
					UdsLogger.logEvent(LogEvent.ERROR, "UdsJobDateTriggerBean is error ", bean.getJob(),
							e.getMessage());
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());
					e.printStackTrace();
				}
				// 根据偏移获取真实运行job_date时间
				Date targetJobDate = startDate;
				byte offsetDay = bean.getOffset_day();
				if (offsetDay != 0) {
					targetJobDate = DateUtils.add(startDate, offsetDay);
				}
				// 作业当前的job_date时间
				Date jobDate = DateUtils.parserDate(bean.getJob_date(), DateUtils.PATTERN_YYYYMMDD_CONS);
				int batch = bean.getBatch();

				if (DateUtils.isSameDay(jobDate, targetJobDate)) {
					if (batch > 0) {
						batch++;
					} else {
						// 报错跳过
						isContinus = true;
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_SCHEDULER_BATCH_ERROR, UdsErrorLevel.M,
								bean.getJob());
					}
				} else {
					batch = batch > 0 ? 1 : 0;
				}
				Timestamp start_time = new Timestamp(nextDate.getTime());
				HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
				hashMap.put("id", bean.getId());
				hashMap.put("start_time", start_time);
				if (isContinus) {
					if (isStream) {
						// 更新Start时间
						if (!UdsUtils.createSignFileIncreaseBatch(bean.getJob(), batch,
								DateUtils.getDateTime(targetJobDate, DateUtils.PATTERN_YYYYMMDD_CONS),
								UdsConstant.AUTO_STREAM_DIR_PATH)) {
							// 生成信号文件失败
							UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, bean.getJob(),
									targetJobDate, bean.getJob_date(), bean.getJob_type());
						} else {
							jobsStartMaps.put(bean.getJob(), hashMap);
						}
					} else {
						jobsStartMaps.put(bean.getJob(), hashMap);
					}
					continue;
				}
				// 更新批次号和状态
				hashMap.put("batch", batch);
				hashMap.put("job_date", DateUtils.getDateTime(targetJobDate, DateUtils.PATTERN_YYYYMMDD_CONS));
				jobsMaps.put(bean.getJob(), hashMap);
				UdsLogger.logEvent(LogEvent.MASTER_SCHEDULER, "UdsJobDateTriggerBean is success", bean.getJob());
			} catch (Exception e) {
				UdsLogger.logEvent(LogEvent.ERROR, "UdsJobDateTriggerBean error ", bean.getJob());
				e.printStackTrace();
			}
		}
		if (jobsMaps.size() > 0) {
			jobBaseDao.updateDateTriggerJobStartTime(jobsMaps.values());
		}
		if (jobsStartMaps.size() > 0) {
			jobBaseDao.updateDateTriggerStartTime(jobsStartMaps.values());
		}
	}

	private HashMap<String, PlatformConterBean> platformConterBeanMap = new HashMap<String, PlatformConterBean>();

	/**
	 * 检测等待分发的作业,提交到工厂处理
	 */
	public void checkDbPendingToFactory() {
		if (!isCheckRecive()) {
			return;
		}
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		for (UdsSystemBean systemBean : UdsConstant.MAP_SYSTEM_JOB.values()) {
			if (systemBean.getSystem().equals(Symbol.XING_HAO)) {
				String platform = systemBean.getPlatform();
				PlatformConterBean conterBean = platformConterBeanMap.get(platform);
				if (conterBean == null) {
					platformConterBeanMap.put(platform, conterBean = new PlatformConterBean());
				}
				if (systemBean.getMax_run_job() == 0) {
					UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG PLATFORM NUM IS 0",
							systemBean.getSystem(), systemBean.getPlatform());
					continue;
				}

				if (conterBean.isDealJobfinsh()) {
					int initNum = conterBean.getInitNum();
					if (conterBean.isWaitDealJobinsh()) {
						List<UdsJobBean> jobList = udsJobBaseDao.checkPendingJobByPlatformOrder(platform, initNum);
						if (jobList.size() < UdsConstant.CHECK_DB_PENDING_LIMIT_NUM) {
							initNum = 0;
						} else {
							initNum += UdsConstant.CHECK_DB_PENDING_LIMIT_NUM;
						}
						conterBean.setInitNum(initNum);
						conterBean.addWaitDealJobAll(jobList);
					}
					conterBean.transfromWaitToDeal(UdsConstant.CHECK_PENDING_LIMIT_NUM);
					ArrayList<UdsJobBean> dealJobList = conterBean.getNewDealJobList();
					if (dealJobList.size() > 0) {
						UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG SUBMIT", systemBean.getPlatform(),
								systemBean.getSystem(), initNum, dealJobList.size());
					}
					for (UdsJobBean udsJobBean : dealJobList) {
						pendingJobDisposeFactory.submit(udsJobBean);
					}
				}
			}
		}
	}

	public void transfromDealToDispatcher(UdsJobBean bean) {
		PlatformConterBean conterBean = platformConterBeanMap.get(bean.getPlatform());
		if (conterBean != null) {
			conterBean.transfromDealToUpdateDispatcher(bean);
		}
	}

	public void removeDeal(UdsJobBean bean) {
		PlatformConterBean conterBean = platformConterBeanMap.get(bean.getPlatform());
		if (conterBean != null) {
			conterBean.removeDealJob(bean);
		}
	}

	public void addJobToDispatcherWaitMap(UdsJobBean bean) {
		PlatformConterBean conterBean = platformConterBeanMap.get(bean.getPlatform());
		if (conterBean != null) {
			conterBean.addDispatcherJobList(bean);
		}
	}

	public void removeDispatcherJob(UdsJobBean bean) {
		PlatformConterBean conterBean = platformConterBeanMap.get(bean.getPlatform());
		conterBean.removeDispatcherJob(bean);
	}

	/**
	 * 从工厂提货准备分发
	 * 
	 */
	private int checkDBDispatcherNum = 0;

	public void checkPendingFactoryToDispatcher() {
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		if (!isCheckRecive()) {
			return;
		}

		// 数据超时校验
		checkDBDispatcherNum = checkDBDispatcherNum >= UdsConstant.CHECK_DB_DISPATCHER_NUM ? 0 : checkDBDispatcherNum;
		if (checkDBDispatcherNum == 0) {
			List<UdsJobBean> tmpList = udsJobBaseDao.getJobStatusDispatcherOverTime();
			for (UdsJobBean bean : tmpList) {
				addJobToDispatcherWaitMap(bean);
			}
		}

		// 汇总需要更新数据
		List<UdsJobBean> updateDispatcherWaitList = new ArrayList<UdsJobBean>();
		for (PlatformConterBean conterBean : platformConterBeanMap.values()) {
			if (conterBean == null) {
				continue;
			}
			updateDispatcherWaitList.addAll(conterBean.transfromUpdateDispatcherToDispatcher());
		}

		// 新增,并更新状态
		if (updateDispatcherWaitList.size() > 0) {
			ArrayList<String> jobs = new ArrayList<String>(updateDispatcherWaitList.size());
			for (UdsJobBean bean : updateDispatcherWaitList) {
				jobs.add(bean.getJob());
			}
			udsJobBaseDao.updateAllJobStatusDispatcher(jobs);
		}

		List<UdsJobBean> dispatcherWaitList = new ArrayList<UdsJobBean>();
		long now = System.currentTimeMillis();

		int checkNum = 0;
		for (Entry<String, PlatformConterBean> entry : platformConterBeanMap.entrySet()) {
			try {
				String platform = entry.getKey();
				UdsSystemBean systemBean = UdsConstant.getUdsSystemBean(platform, Symbol.XING_HAO);
				int max = systemBean.getMax_run_job();
				if (max <= 0) {
					UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "systemBean.getMax_run_job() is 0", platform);
					continue;
				}
				PlatformConterBean conterBean = entry.getValue();

				// 获取平台最大并发
				dispatcherWaitList = conterBean.getNewDispatcherJobList(systemBean.getMax_run_job());

				checkNum += dispatcherWaitList.size();
				for (UdsJobBean udsJobBean : dispatcherWaitList) {
					try {
						// 权重判断
						if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM) {
							if (!checkLimitWeight(udsJobBean)) {
								break;
							}
						}
						if (dispatcherJob(udsJobBean)) {
							removeDispatcherJob(udsJobBean);
						} else {
							if (udsJobBean.getDispatcher_time() == null || now
									- udsJobBean.getDispatcher_time().getTime() > DateUtils.TIME_MILLSECOND_OF_MINUTE
											* UdsConstant.DISPATCHER_OVER_MINUTE) {
								removeDispatcherJob(udsJobBean);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		checkDBDispatcherNum++;
		if (checkNum > 0) {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "checkDispatcherNum:", checkNum);
		}
	}

	public boolean dispatcherJob(UdsJobBean udsJobBean) {
		UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(), udsJobBean.getSystem());
		if (udsSystemBean == null) {
			return false;
		}
		return dispatcherJob(udsJobBean, udsSystemBean);
	}

	// 分发作业
	private boolean dispatcherJob(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean) {
		// 该平台应用最大并行数
		int max = udsSystemBean.getMax_run_job();
		if (max <= 0) {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "dispatcherJob,systemBean.getMax_run_job() is 0",
					udsSystemBean.getPlatform(), udsSystemBean.getSystem());
			return false;
		}
		// 当前运行数
		int num = MasterManager.getInstance().getSystemJobNum(udsSystemBean.getPlatform(), udsSystemBean.getSystem());
		if (num >= max) {
			return false;
		}
		// 机器选择
		UdsRpcClient client = AbstractDispatcherPlan.getUdsRpcClient(udsSystemBean, udsJobBean);
		if (client == null) {
			return false;
		}
		// 地域判断
		if ((client.getLocation() & UdsConstant.LOCATION) <= 0) {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "client location not my location", client.getLocation(),
					UdsConstant.LOCATION);
			return false;
		}
		// 数据库更新
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		int tmp = udsJobBaseDao.updateJobServerNameByLastStatus(udsJobBean.getPlatform(), udsJobBean.getSystem(),
				udsJobBean.getJob(), client.getServerName(), JobStatus.DISPATCHER);
		if (tmp > 0) {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "MASTER DISPATCHER job", udsJobBean.toString());
			UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(client.getServerName(), RpcCommand.DISTRIBUTION_JOB);
			// 单独并发控制
			MasterManager.getInstance().incrementChildServerSystem(client.getServerName(), udsJobBean.getPlatform(),
					udsJobBean.getSystem());
			// 权重控制
			if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM) {
				MasterManager.getInstance().addWeight(client.getServerName(), udsJobBean);
			}
			UdsRpcClientManager.getInstance().sendMessage(client, udsRpcEvent, udsJobBean.getJob());
			return true;
		}
		return false;
	}

	/**
	 * 检测信号文件夹
	 */
	public void checkReceiverDir() {
		if (!isCheckRecive()) {
			return;
		}
		File dirFile = new File(UdsConstant.AUTO_RECEIVER_DIR_PATH);
		File[] files = dirFile.listFiles();
		if (files != null) {
			UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				// 信号文件规范检测
				if (!SignalManager.getInstance().checkSignalFile(file)) {
					continue;
				}
				SignalFileInfo signalFileInfo = SignalManager.getInstance().createSignalFileBean(file);
				UdsJobBean udsJobBean = udsJobBaseDao.getUdsJobBeanBySourceAndLoaction(signalFileInfo.getJobSource());
				if (udsJobBean == null) {
					UdsUtils.moveToDayFile(UdsConstant.AUTO_UNKNOW_DIR_PATH, file);
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M,
							signalFileInfo.getJobSource(), "UdsJobBean is null");
					continue;
				}
				boolean pass = true;
				for (AbstractReceiverFilter filter : jobReceiverFilterList) {
					pass = filter.check(signalFileInfo, udsJobBean);
					if (!pass) {
						break;
					}
				}
				if (pass) {
					String platform = udsJobBean.getPlatform();
					String system = udsJobBean.getSystem();
					String job = udsJobBean.getJob();
					int tmp = 0;
					tmp = udsJobBaseDao.updateJobPending(platform, system, job, signalFileInfo.getBatch(),
							signalFileInfo.getJobDate());
					if (tmp > 0) {
						// success
						UdsUtils.moveToDayFile(UdsConstant.AUTO_COMPLETE_DIR_PATH, file);
						UdsLogger.logEvent(LogEvent.MASTER_PENDIG, job, "uds update job status is success");
					} else {
						// 失败
						UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, file);
						UdsLogger.logEvent(LogEvent.ERROR, "uds update job status is fail",
								signalFileInfo.getJobSource());
					}
				}
			}
		}
	}

	/**
	 * 可以重调的重调
	 */
	public void checkCallAgainJob() {
		if (!isCheckRecive()) {
			return;
		}
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		List<UdsJobBean> jobList = udsJobBaseDao.checkCallAgainJob();
		for (UdsJobBean bean : jobList) {
			int tmp = 0;
			if (bean.getLast_status().equals(JobStatus.FAILED.status())) {
				udsJobBaseDao.callAgainJobByStatusPending(bean.getPlatform(), bean.getSystem(), bean.getJob());
			}
			if (tmp > 0) {
				UdsLogger.logEvent(LogEvent.MASTER_JOB_CALL_AGAIN, bean.getJob(), bean.getCall_again_max_num(),
						bean.getCall_again_num(), "succ");
			} else {
				UdsLogger.logEvent(LogEvent.MASTER_JOB_CALL_AGAIN, bean.getJob(), bean.getCall_again_max_num(),
						bean.getCall_again_num(), "error");
			}
		}

	}

	public boolean checkLimitWeight(UdsJobBean udsJobBean) {
		// 获取作业过滤条件
		HashMap<Integer, Integer> weightMap = udsJobBean.getWeightConfMap();
		if (weightMap == null) {
			// 数据库获取过滤条件
			UdsJobWeightDao udsJobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
			weightMap = udsJobWeightDao.getUdsJobWeightMap(udsJobBean.getPlatform(), udsJobBean.getSystem(),
					udsJobBean.getJob());
			udsJobBean.setWeightConfMap(weightMap);
		}
		HashMap<Integer, Integer> nowWeightMap = new HashMap<Integer, Integer>();
		for (Entry<Integer, Integer> en : weightMap.entrySet()) {
			Integer weightType = en.getKey();
			int weightValue = en.getValue();
			// 根据过滤类型累积所有子节点相同类型值
			for (Entry<String, ChildServerInfo> enc : childServerJobMap.entrySet()) {
				ChildServerInfo childServerInfo = enc.getValue();
				int value = childServerInfo.getWeightValue(weightType);
				Integer tmpValue = nowWeightMap.get(weightType);
				if (tmpValue == null) {
					nowWeightMap.put(en.getKey(), value);
				} else {
					nowWeightMap.put(en.getKey(), value + tmpValue);
				}
			}
			// 该类型最大限制值
			int maxValue = UdsConstant.getWeightLimit(weightType);
			if (maxValue != -1) {
				// 该类型当前值
				Integer tmpValue = nowWeightMap.get(weightType);
				if (tmpValue == null) {
					tmpValue = 0;
				} else {
					// 判断是否大于最大值
					if (tmpValue + weightValue > maxValue) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void addWeight(String serverName, UdsJobBean udsJobBean) {
		HashMap<Integer, Integer> weightMap = udsJobBean.getWeightConfMap();
		if (weightMap == null) {
			UdsJobWeightDao udsJobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
			weightMap = udsJobWeightDao.getUdsJobWeightMap(udsJobBean.getPlatform(), udsJobBean.getSystem(),
					udsJobBean.getJob());
			udsJobBean.setWeightConfMap(weightMap);
		}
		ChildServerInfo childServerInfo = childServerJobMap.get(serverName);
		childServerInfo.addWeightValueMap(weightMap);
	}

	public void subWeight(String serverName, String jobName) {
		UdsJobBaseDao jobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsJobBean = jobBaseDao.getUdsJobBeanByJob(jobName);
		HashMap<Integer, Integer> weightMap = udsJobBean.getWeightConfMap();
		if (weightMap == null) {
			UdsJobWeightDao udsJobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
			weightMap = udsJobWeightDao.getUdsJobWeightMap(udsJobBean.getPlatform(), udsJobBean.getSystem(),
					udsJobBean.getJob());
			udsJobBean.setWeightConfMap(weightMap);
		}
		ChildServerInfo childServerInfo = childServerJobMap.get(serverName);
		childServerInfo.subWeightValueMap(weightMap);
	}

	public boolean isCheckRecive() {
		return checkRecive;
	}

	public void setCheckRecive(boolean isCheck) {
		this.checkRecive = isCheck;
	}

	public List<AbstractReceiverFilter> getJobReceiverFilterList() {
		return jobReceiverFilterList;
	}

	public void setJobReceiverFilterList(List<AbstractReceiverFilter> jobReceiverFilterList) {
		this.jobReceiverFilterList = jobReceiverFilterList;
	}

	public List<AbstractPendingFilter> getJobPendingFilterList() {
		return jobPendingFilterList;
	}

	public void setJobPendingFilterList(List<AbstractPendingFilter> jobPendingFilterList) {
		this.jobPendingFilterList = jobPendingFilterList;
	}

	public ConcurrentHashMap<String, ChildServerInfo> getChildServerJobMap() {
		return childServerJobMap;
	}

	public void setChildServerJobMap(ConcurrentHashMap<String, ChildServerInfo> childServerJobMap) {
		this.childServerJobMap = childServerJobMap;
	}

	public MasterPendingJobDisposeFactory getPendingJobDisposeFactory() {
		return pendingJobDisposeFactory;
	}

	public void setPendingJobDisposeFactory(MasterPendingJobDisposeFactory pendingJobDisposeFactory) {
		this.pendingJobDisposeFactory = pendingJobDisposeFactory;
	}

}
