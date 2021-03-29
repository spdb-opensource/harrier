package cn.com.spdb.uds.db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobStepBean;

public class UdsJobBaseDao extends AbstractBaseDao {

	/**
	 * 通过作业名获取信号文件关键字段 dir.batch@关键字段YYYYmmDD
	 * 
	 * @param job
	 * @return 关键字段
	 */
	public String getSignFileKeyByJob(String job) {
		String signFileKey = selectOne("uds_job_base.getSignFileKeyByJob", job);
		return signFileKey;
	}

	/**
	 * 通过信号文件，获取作业信息
	 * 
	 * @param src_signal
	 * @return
	 */
	public UdsJobBean getUdsJobBeanBySource(String src_signal) {
		UdsJobBean bean = selectOne("uds_job_base.getUdsJobBeanBySource", src_signal);
		return bean;
	}

	/**
	 * 通过信号文件，获取作业信息
	 * 
	 * @param src_signal
	 * @return
	 */
	public UdsJobBean getUdsJobBeanBySourceAndLoaction(String src_signal) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("src_signal", src_signal);
		map.put("location", UdsConstant.LOCATION);
		UdsJobBean bean = selectOne("uds_job_base.getUdsJobBeanBySourceAndLoaction", map);
		return bean;
	}

	/**
	 * 通过作业名获取作业信息
	 * 
	 * @param job
	 * @return
	 */
	public UdsJobBean getUdsJobBeanByJob(String job) {
		UdsJobBean bean = selectOne("uds_job_base.getUdsJobBeanByJob", job);
		return bean;
	}

	/**
	 * 作业名获取转换后信号文件名
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public String getConvSignalByJob(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		String conv_signal = selectOne("uds_job_base.getConvSignalByJob", map);
		return conv_signal;
	}

	/**
	 * 按平台获取作业
	 * 
	 * @return
	 */
	public List<UdsJobBean> checkPendingJobByPlatform(String platform) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("last_status", JobStatus.PENDING.status());
		map.put("max_job_num", 50000);
		map.put("platform", platform);
		List<UdsJobBean> list = selectList("uds_job_base.checkPendingJobByPlatform", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * 获取当前等待中的作业
	 * 
	 * @return
	 */
	public List<UdsJobBean> checkPendingJob() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("last_status", JobStatus.PENDING.status());
		map.put("max_job_num", 2500);
		List<UdsJobBean> list = selectList("uds_job_base.checkPendingJob", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * 获取当前等待中的作业 分页获取
	 * 
	 * @return
	 */
	public List<UdsJobBean> checkPendingJobOrder(int initNum) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("last_status", JobStatus.PENDING.status());
		map.put("init_num", initNum);
		map.put("max_job_num", UdsConstant.CHECK_DB_PENDING_LIMIT_NUM);
		List<UdsJobBean> list = selectList("uds_job_base.checkPendingJobOrder", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	public List<UdsJobBean> checkPendingJobByPlatformOrder(String platform, int initNum) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("last_status", JobStatus.PENDING.status());
		map.put("init_num", initNum);
		map.put("platform", platform);
		map.put("max_job_num", UdsConstant.CHECK_DB_PENDING_LIMIT_NUM);
		map.put("location", UdsConstant.LOCATION);
		List<UdsJobBean> list = selectList("uds_job_base.checkPendingJobByPlatformOrder", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * 获取脚本信息
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public List<UdsJobStepBean> getUdsJobStepBean(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		List<UdsJobStepBean> list = selectList("uds_job_base.getUdsJobStepBean", map);
		if (list == null) {
			list = new ArrayList<UdsJobStepBean>();
		}
		return list;
	}

	/**
	 * 更新作业状态
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param last_status
	 * @return
	 */
	public int updateJobStatus(String platform, String system, String job, String last_status) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", last_status);
		return update("uds_job_base.updateJobStatus", map);
	}

	/**
	 * 进入pending 状态修改pending时间 信号文件触发作业进入pending
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param nowJoyStatus
	 * @return
	 */
	public int updateJobPending(String platform, String system, String job, int batch, String job_date) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("done_status", JobStatus.DONE.status());
		map.put("ready_status", JobStatus.READY.status());
		map.put("last_status", JobStatus.PENDING.status());
		map.put("batch", batch);
		map.put("job_date", job_date);
		return update("uds_job_base.updateJobPending", map);
	}

	/**
	 * 作业触发批量修改触发作业
	 * 
	 * @param jobs
	 * @param batch
	 * @param job_date
	 * @return
	 */
	public int updateJobTriggerByStatusPending(List<String> jobs, int batch, String job_date) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("jobList", jobs);
		map.put("done_status", JobStatus.DONE.status());
		map.put("ready_status", JobStatus.READY.status());
		map.put("last_status", JobStatus.PENDING.status());
		map.put("batch", batch);
		map.put("job_date", job_date);
		if (batch == 0) {
			return update("uds_job_base.updateJobOnlyTriggerByStatusPending", map);
		} else {
			return update("uds_job_base.updateJobManyTriggerByStatusPending", map);
		}
	}

	/**
	 * 定时作业触发后修改作业为Pending
	 * 
	 * @param listMaps
	 * @return
	 */
	public int updateDateTriggerJobStartTime(Collection<HashMap<Object, Object>> listMaps) {
		int num = 0;
		if (listMaps.size() > 0) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				for (HashMap<Object, Object> map : listMaps) {
					map.put("last_status", JobStatus.PENDING.status());
					map.put("done_status", JobStatus.DONE.status());
					map.put("ready_status", JobStatus.READY.status());
					num += sqlSession.update("uds_job_base.updateDateTriggerJobStartTime", map);
				}
				sqlSession.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlSession.close();
				setUpdataTime(System.currentTimeMillis());
			}
		}
		return num;
	}

	/**
	 * 修改触发时间
	 * 
	 * @param listMaps
	 * @return
	 */
	public int updateDateTriggerStartTime(Collection<HashMap<Object, Object>> listMaps) {
		int num = 0;
		if (listMaps.size() > 0) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				for (HashMap<Object, Object> map : listMaps) {
					num += sqlSession.update("uds_job_base.updateDateTriggerStartTime", map);
				}
				sqlSession.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlSession.close();
				setUpdataTime(System.currentTimeMillis());
			}
		}
		return num;
	}

	/**
	 * 尝试重新让作业处于等待状态
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	@Deprecated
	public int retryDistributByStatusPending(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", JobStatus.PENDING.status());
		map.put("runing_status", JobStatus.RUNING.status());
		return update("uds_job_base.retryDistributByStatusPending", map);
	}

	/**
	 * 进入分发状态设置分发时间
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param server_name
	 * @return
	 */
	public int updateAllJobStatusDispatcher(List<String> jobs) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("old_status", JobStatus.PENDING.status());
		map.put("last_status", JobStatus.DISPATCHER.status());
		map.put("jobList", jobs);
		return update("uds_job_base.updateAllJobStatusDispatcher", map);
	}

	public int updateJobServerNameByLastStatus(String platform, String system, String job, String serverName,
			JobStatus jobStatus) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("server_name", serverName);
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", jobStatus.status());
		return update("uds_job_base.updateJobServerNameByLastStatus", map);
	}

	public int updateJobDispatcher(String platform, String system, String job, String server_name) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("old_status", JobStatus.PENDING.status());
		map.put("last_status", JobStatus.DISPATCHER.status());
		map.put("server_name", server_name);
		return update("uds_job_base.updateJobDispatcher", map);
	}

	public List<UdsJobBean> getJobStatusDispatcherOverTime() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dispatcher_status", JobStatus.DISPATCHER.status());
		map.put("over_time", 100 * UdsConstant.DISPATCHER_OVER_MINUTE);// 3分钟
		map.put("location", UdsConstant.LOCATION);// 地域
		List<UdsJobBean> list = selectList("uds_job_base.getJobStatusDispatcherOverTime", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * forceStart 强制执行修改
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param batch
	 * @param jobDate
	 * @param server_name
	 * @return
	 */
	public int updateJobRuningByForceStart(String platform, String system, String job, int batch, String jobDate,
			String server_name) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);

		map.put("batch", batch);
		map.put("job_date", jobDate);
		map.put("last_status", JobStatus.RUNING.status());
		map.put("server_name", server_name);
		return update("uds_job_base.updateJobRuningByForceStart", map);
	}

	/**
	 * 作业正常执行状态
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public int updateJobRuning(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("old_status", JobStatus.DISPATCHER.status());
		map.put("server_name", UdsConstant.SERVER_NAME);
		map.put("last_status", JobStatus.RUNING.status());
		map.put("location", UdsConstant.LOCATION);
		return update("uds_job_base.updateJobRuning", map);
	}

	/**
	 * 作业完成状态
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public int updateJobDone(String platform, String system, String job, String last_script_name) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", JobStatus.DONE.status());
		map.put("last_runing", JobStatus.RUNING.status());
		map.put("last_unknown", JobStatus.UNKNOWN.status());
		map.put("server_name", UdsConstant.SERVER_NAME);
		map.put("call_again_num", 0);
		map.put("last_script_name", last_script_name);
		return update("uds_job_base.updateJobDone", map);
	}

	/**
	 * 作业失败
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public int updateJobFailed(String platform, String system, String job, String last_script_name) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", JobStatus.FAILED.status());
		map.put("server_name", UdsConstant.SERVER_NAME);
		map.put("last_script_name", last_script_name);
		return update("uds_job_base.updateJobFailed", map);
	}

	/**
	 * 子接点所有作业修改
	 * 
	 * @param serverName
	 * @param last_status 修改状态
	 * @param old_status  目标状态
	 * @return
	 */
	public int updateChildJobStatus(String serverName, JobStatus last_status, JobStatus old_status) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("last_status", last_status.status());
		map.put("old_status", old_status.status());
		map.put("server_name", serverName);
		return update("uds_job_base.updateChildJobStatus", map);
	}

	/**
	 * 修改作业所在机器名字
	 * 
	 * @param serverName
	 * @param last_status
	 * @param old_status
	 * @return
	 */
	public int updateJobServerName(String platform, String system, String job, String serverName) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("server_name", serverName);
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		return update("uds_job_base.updateJobServerName", map);
	}

	/**
	 * 尝试重调，
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public int callAgainJobByStatusPending(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("last_status", JobStatus.PENDING.status());
		map.put("runing_status", JobStatus.RUNING.status());
		return update("uds_job_base.callAgainJobByStatusPending", map);
	}

	/**
	 * 获取可以重调可以 有重调重调次数的
	 * 
	 * 
	 * @return
	 */
	public List<UdsJobBean> checkCallAgainJob() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("failed_status", JobStatus.FAILED.status());
		map.put("location", UdsConstant.LOCATION);
		// map.put("dispatcher_status", JobStatus.DISPATCHER.status());
		// map.put("over_time", UdsConstant.CALL_AGAIN_JOB_TIME);// 5分钟
		List<UdsJobBean> list = selectList("uds_job_base.checkCallAgainJob", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}
}
