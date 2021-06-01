package cn.com.spdb.uds.core.child;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.bean.JobStepInfo;
import cn.com.spdb.uds.core.child.job.JobRunable;
import cn.com.spdb.uds.core.filter.stream.AbstractStreamFilter;
import cn.com.spdb.uds.core.filter.stream.JobStreamFilter;
import cn.com.spdb.uds.core.filter.stream.JobStreamFrequencyFilter;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobStepBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.db.dao.UdsJobWeightDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;
import cn.com.spdb.uds.utils.Symbol;

public class ChildManager {

	private final static Object KEY = new Object();
	public static ChildManager instance;

	/**
	 * 作业触发过滤
	 */
	private List<AbstractStreamFilter> jobStreamFilterList = new ArrayList<AbstractStreamFilter>();

	/**
	 * 运行作业池
	 */
	private ConcurrentHashMap<String, JobRunable> runJobsPool = new ConcurrentHashMap<String, JobRunable>();

	/**
	 * 权重
	 */
	private ConcurrentHashMap<Integer, Integer> weightMap = new ConcurrentHashMap<Integer, Integer>();

	public static ChildManager getInstance() {

		synchronized (KEY) {
			if (instance == null) {
				instance = new ChildManager();
				instance.init();
			}
		}
		return instance;
	}

	private ThreadPoolExecutor executor;

	public void setExecutorMaxJob() {
		if (UdsConstant.MAX_JOB_NUM <= 0) {
			executor.setCorePoolSize(1);
			executor.setMaximumPoolSize(1);
		} else {
			executor.setCorePoolSize(UdsConstant.MAX_JOB_NUM);
			executor.setMaximumPoolSize(UdsConstant.MAX_JOB_NUM);
		}
	}

	public void init() {
		int maxNUm = UdsConstant.MAX_JOB_NUM > 0 ? UdsConstant.MAX_JOB_NUM : 1;
		executor = new ThreadPoolExecutor(maxNUm, maxNUm, 10000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new NameThreadFactory(ChildManager.class.getSimpleName()));
		jobStreamFilterList.add(new JobStreamFilter());
		jobStreamFilterList.add(new JobStreamFrequencyFilter());
	}

	/**
	 * @param udsJobBean
	 * @return
	 */
	@Deprecated
	public boolean retryDistribut(UdsJobBean udsJobBean) {
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		String platform = udsJobBean.getPlatform();
		String system = udsJobBean.getSystem();
		String job = udsJobBean.getJob();
		return udsJobBaseDao.retryDistributByStatusPending(platform, system, job) > 0;
	}

	public void submitJobList(Runnable jobRunableList) {
		executor.submit(jobRunableList);
	}

	/**
	 * 提交作业
	 * 
	 * @param udsJobBean 作业
	 * @return
	 */
	public boolean submitJob(UdsJobBean udsJobBean) {
		return submitJob(udsJobBean, true);
	}

	/**
	 * 提交作业
	 * 
	 * @param udsJobBean 作业
	 * @param streamJob  是否触发下游
	 * @return
	 */
	public boolean submitJob(UdsJobBean udsJobBean, boolean notForcestart) {
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		String platform = udsJobBean.getPlatform();
		String system = udsJobBean.getSystem();
		String job = udsJobBean.getJob();
		if (runJobsPool.size() > UdsConstant.MAX_JOB_NUM) {
			UdsLogger.logEvent(LogEvent.CHILD_DISPATCHER, "ActiveCount> MAX_JOB_NUM", executor.getActiveCount(),
					UdsConstant.MAX_JOB_NUM);
			return false;
		}
		if (udsJobBaseDao.updateJobRuning(platform, system, job) <= 0) {
			UdsLogger.logEvent(LogEvent.CHILD_DISPATCHER, "updateJobRuning error ", udsJobBean.toString());
			return false;
		}
		// FIXME
		if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM && notForcestart) {
			UdsJobWeightDao udsJobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
			HashMap<Integer, Integer> weightMap = udsJobWeightDao.getUdsJobWeightMap(platform, system, job);
			udsJobBean.setWeightConfMap(weightMap);
			addWeightValueMap(weightMap);
		}
		udsJobBean.setNum_times(udsJobBean.getNum_times() + 1);
		List<UdsJobStepBean> jobStepBeanList = udsJobBaseDao.getUdsJobStepBean(platform, system, job);
		String conv_signal = udsJobBaseDao.getConvSignalByJob(platform, system, job);
		JobRunable jobRunable = bulidJobRunable(udsJobBean, jobStepBeanList, conv_signal);
		jobRunable.setNotForcestartJob(notForcestart);
		executor.submit(jobRunable);
		UdsLogger.logEvent(LogEvent.CHILD_DISPATCHER, "submit jobRun ", udsJobBean.toString());
		return true;
	}

	public JobRunable bulidJobRunable(UdsJobBean udsJobBean, List<UdsJobStepBean> jobStepBeanList, String conv_signal) {
		List<JobStepInfo> jobStepInfos = new ArrayList<JobStepInfo>();
		for (UdsJobStepBean jobStepBean : jobStepBeanList) {
			Map<String, String> envMap = new HashMap<String, String>();
			String environments = jobStepBean.getEnvironments();
			if (!StringUtils.isBlank(environments)) {
				String[] tmp = environments.split(Symbol.FEN_HAO);
				for (String str : tmp) {
					String[] ens = str.split(Symbol.DENG_YU_HAO);
					if (ens.length > 1) {
						String key = ens[0];
						String value = ens[1];
						envMap.put(key, value);
					}
				}
			}
			List<String> cmdList = new ArrayList<String>();
			String[] strCmd = jobStepBean.getOper_cmd().split(Symbol.KONG_GE);
			cmdList.addAll(Arrays.asList(strCmd));
			String file = jobStepBean.getScript_dir().replace(UdsConstant.SCRIPT_PATH_PERFIX, UdsConstant.AUTO_HOME)
					+ File.separator + jobStepBean.getScript_name();
			cmdList.add(file);
			StringBuffer convSignalBuffer = new StringBuffer();
			convSignalBuffer.append(udsJobBean.getBatch()).append(Symbol.AT).append(conv_signal)
					.append(Symbol.XIA_HUA_XIAN).append(udsJobBean.getJob_date()).append(".dir");
			cmdList.add(convSignalBuffer.toString());
			cmdList.add(String.valueOf(jobStepBean.getSetp_num()));
			String parameter = jobStepBean.getParameter();
			if (!StringUtils.isBlank(parameter)) {
				String[] parameters = parameter.split(Symbol.KONG_GE);
				cmdList.addAll(Arrays.asList(parameters));
			}

			StringBuffer logDirBuffer = new StringBuffer();
			logDirBuffer.append(UdsConstant.AUTO_JOB_LOG_DIR_PATH).append(File.separator)
					.append(jobStepBean.getPlatform()).append(File.separator).append(jobStepBean.getSystem())
					.append(File.separator).append(DateUtils.getDateTime(new Date(), DateUtils.PATTERN_YYYYMMDD_CONS));

			StringBuffer logNameBuffer = new StringBuffer();
			logNameBuffer.append(jobStepBean.getJob().toLowerCase()).append(Symbol.XIA_HUA_XIAN)
					.append(jobStepBean.getSetp_num()).append(Symbol.XIA_HUA_XIAN).append(udsJobBean.getNum_times())
					.append(Symbol.XIA_HUA_XIAN).append(udsJobBean.getBatch()).append(Symbol.XIA_HUA_XIAN)
					.append(udsJobBean.getJob_date()).append(".log");

			JobStepInfo jobStepInfo = new JobStepInfo();
			jobStepInfo.setSetpNum(jobStepBean.getSetp_num());
			jobStepInfo.setEnvMap(envMap);
			jobStepInfo.setCmdList(cmdList);
			jobStepInfo.setWorkDir(jobStepBean.getWork_dir());
			jobStepInfo.setStepType(jobStepBean.getStep_type());
			jobStepInfo.setLogDir(logDirBuffer.toString());
			jobStepInfo.setLogName(logNameBuffer.toString());
			jobStepInfo.setScript_dir(jobStepBean.getScript_dir().replace(UdsConstant.SCRIPT_PATH_PERFIX, UdsConstant.AUTO_HOME));
			jobStepInfo.setScript_name(jobStepBean.getScript_name());
			jobStepInfos.add(jobStepInfo);
		}
		JobRunable jobRunable = new JobRunable(udsJobBean, jobStepInfos);
		return jobRunable;
	}

	public int getExecutorJobNum() {
		return executor.getActiveCount();
	}

	public ChildServerInfo buildChildServerDate() {
		ChildServerInfo childServerInfo = new ChildServerInfo();
		if (executor.getActiveCount() == 0) {
			runJobsPool.clear();
		}
		Iterator<JobRunable> jobRunableS = runJobsPool.values().iterator();
		while (jobRunableS.hasNext()) {
			JobRunable jobRunable = jobRunableS.next();
			childServerInfo.incrementSystemJob(jobRunable.getPlatform(), jobRunable.getSystem());
		}
		childServerInfo.setMaxJobNum((short) UdsConstant.MAX_JOB_NUM);
		childServerInfo.setPerformanceRatio((short) UdsConstant.PERFORMANCE_RATIO);
		childServerInfo.setName(UdsConstant.SERVER_NAME);
		childServerInfo.setEnable(UdsConstant.SERVER_ENABLE);
		childServerInfo.setWeightMap(weightMap);
		return childServerInfo;
	}

	/**
	 * 添加JobRun
	 * 
	 * @param job
	 * @param jobRunable
	 */
	public void addJobPool(String job, JobRunable jobRunable) {
		runJobsPool.put(job, jobRunable);
	}

	/**
	 * 移除JobRun
	 * 
	 * @param job
	 */
	public void removeJobPool(String job) {
		runJobsPool.remove(job);
	}

	public List<AbstractStreamFilter> getJobStreamFilterList() {
		return jobStreamFilterList;
	}

	public void setJobStreamFilterList(List<AbstractStreamFilter> jobStreamFilterList) {
		this.jobStreamFilterList = jobStreamFilterList;
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public ConcurrentHashMap<String, JobRunable> getRunJobsPool() {
		return runJobsPool;
	}

	public void setRunJobsPool(ConcurrentHashMap<String, JobRunable> runJobsPool) {
		this.runJobsPool = runJobsPool;
	}

	public void killJobRun(String job) {
		JobRunable jobRunable = runJobsPool.remove(job);
		if (jobRunable != null) {
			jobRunable.kill();
		}
	}

	/**
	 * 增加权值
	 * 
	 * @param weightType
	 * @param value
	 */
	public void addWeightValue(int weightType, int value) {
		synchronized (weightMap) {
			Integer num = weightMap.get(weightType);
			if (num == null) {
				weightMap.put(weightType, value);
			} else {
				weightMap.put(weightType, value + num);
			}
		}
	}

	/**
	 * 减少权值
	 * 
	 * @param weightType
	 * @param value
	 */
	public void subWeightValue(int weightType, int value) {
		synchronized (weightMap) {
			Integer num = weightMap.get(weightType);
			if (num != null) {
				int tmpValue = num - value;
				if (tmpValue > 0) {
					weightMap.put(weightType, tmpValue);
				} else {
					weightMap.put(weightType, 0);
				}
			}
		}
	}

	/**
	 * key:weightType,value:weightValue
	 * 
	 * @param map
	 */
	public void addWeightValueMap(HashMap<Integer, Integer> map) {
		for (Entry<Integer, Integer> en : map.entrySet()) {
			int weightType = en.getKey();
			int value = en.getValue();
			addWeightValue(weightType, value);
		}
	}

	/**
	 * key:weightType,value:weightValue
	 * 
	 * @param map
	 */
	public void subWeightValueMap(HashMap<Integer, Integer> map) {
		for (Entry<Integer, Integer> en : map.entrySet()) {
			int weightType = en.getKey();
			int value = en.getValue();
			subWeightValue(weightType, value);
		}
	}

	public ConcurrentHashMap<Integer, Integer> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(ConcurrentHashMap<Integer, Integer> weightMap) {
		this.weightMap = weightMap;
	}

}
