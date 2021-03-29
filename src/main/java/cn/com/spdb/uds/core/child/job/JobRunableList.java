package cn.com.spdb.uds.core.child.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobStepBean;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.UdsLogger;

/**
 * 强制调用专用
 * 
 * @author T-luzl
 *
 */
public class JobRunableList implements Runnable {

	private static final String UPDATESQL = "UPDATE m_substream SET JOB_STATUS=1 WHERE JOB_ID='%s';";

	/**
	 * String sql =
	 * "SELECT JOB_ID,JOB,TX_DATE,BATCH FROM m_substream WHERE STREAM_ID='" +
	 * param + "';";
	 */
	private List<HashMap<Object, Object>> list = new ArrayList<HashMap<Object, Object>>();

	@Override
	public void run() {
		for (HashMap<Object, Object> map : list) {
			String jobId = (String) map.get("JOB_ID");
			String job = (String) map.get("JOB");
			String jobDate = (String) map.get("TX_DATE");
			int batch = (int) map.get("BATCH");

			UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			UdsJobBean udsJobBean = udsJobBaseDao.getUdsJobBeanByJob(job);
			if (udsJobBean == null) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FORCE_START, UdsErrorLevel.M, job, jobId, jobDate, batch,"作业不存在");
				return;
			}
			udsJobBaseDao.updateJobServerName(udsJobBean.getPlatform(), udsJobBean.getSystem(), job, UdsConstant.SERVER_NAME);
			String platform = udsJobBean.getPlatform();
			String system = udsJobBean.getSystem();

			// 检查作业批次号 单批次，发的多批次
			if (udsJobBean.getBatch() == 0 && batch > 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FORCE_START, UdsErrorLevel.M, job, jobId, jobDate, batch,"批次号错误");
				return;
			}
			// 检查作业批次号 多批次，发的单批次
			if (udsJobBean.getBatch() > 0 && batch == 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FORCE_START, UdsErrorLevel.M, job, jobId, jobDate, batch,"批次号错误");
				return;
			}

			// 设置作业状态
			if (udsJobBaseDao.updateJobRuningByForceStart(platform, system, job, batch, jobDate, UdsConstant.SERVER_NAME) <= 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FORCE_START, UdsErrorLevel.M, job, jobId, jobDate, batch,"修改作业状态失败");
				return;
			}

			udsJobBean.setNum_times(udsJobBean.getNum_times() + 1);
			udsJobBean.setJob_date(jobDate);
			udsJobBean.setBatch(batch);
			List<UdsJobStepBean> jobStepBeanList = udsJobBaseDao.getUdsJobStepBean(platform, system, job);
			String conv_signal = udsJobBaseDao.getConvSignalByJob(platform, system, job);
			JobRunable jobRunable = ChildManager.getInstance().bulidJobRunable(udsJobBean, jobStepBeanList, conv_signal);
			jobRunable.setNotForcestartJob(false);
			jobRunable.run();
			if (jobRunable.getReturnCode() != 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FORCE_START, UdsErrorLevel.M, job, jobId, jobDate, batch,"执行失败");
				return;
			} else {
				if (!jobId.equals("0")) {
					BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
					String sql = String.format(UPDATESQL, jobId);
					baseDao.update(sql);
				}
			}
		}

	}

	public List<HashMap<Object, Object>> getList() {
		return list;
	}

	public void setList(List<HashMap<Object, Object>> list) {
		this.list = list;
	}

	public JobRunableList(List<HashMap<Object, Object>> list) {
		this.list = list;
	}

}
