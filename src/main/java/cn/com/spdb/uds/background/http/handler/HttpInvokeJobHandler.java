package cn.com.spdb.uds.background.http.handler;

import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

@HttpMapProtocol(value = "/invoke/job")
public class HttpInvokeJobHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		String job = (String) objectMap.get("job");
		String job_date = (String) objectMap.get("job_date");
		int batch = (int) objectMap.get("batch");
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsJobBean = baseDao.getUdsJobBeanByJob(job);
		if (udsJobBean == null) {
			return "作业不存在";
		}
		if (!((udsJobBean.getLocation() & UdsConstant.LOCATION) > 0)) {
			UdsLogger.error(LogEvent.HTTP_ERROR, "/invoke/job", "Location is error", job, UdsConstant.LOCATION);
			return "job location and servre location not the same";
		}
		if ((udsJobBean.getLast_status().equals(JobStatus.DONE.status()) || udsJobBean.getLast_status().equals(
				JobStatus.READY.status()))) {
			if (batch < 0) {
				return "批次号为>=0";
			}

			if (udsJobBean.getBatch() == 0) {
				if (job_date.compareTo(udsJobBean.getJob_date()) <= 0) {
					return "传入执行信号文件日期小于等于作业最后执行信号文件时间";
				}

				if (batch != 0) {
					return "单批次作业，批次号为=0";
				}
			}

			if (udsJobBean.getBatch() != 0) {
				if (job_date.compareTo(udsJobBean.getJob_date()) < 0) {
					return "传入执行信号文件日期小于作业最后执行信号文件时间";
				}
				if (batch == 0) {
					return "多批次作业，批次号>0";
				}
			}
			/*
			Rename Variable
			 */
			int temp = baseDao.updateJobPending(udsJobBean.getPlatform(), udsJobBean.getSystem(), job, batch, job_date);
			if (temp <= 0) {
				return "作业执行失败";
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("执行作业invork->job:").append(job).append("job_date:").append(job_date).append("batch")
					.append(batch);
			UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, buffer.toString());
			return HttpResultCode.SUCCESS;

		} else {
			return "作业状态不是Done or Ready";
		}
	}
}
