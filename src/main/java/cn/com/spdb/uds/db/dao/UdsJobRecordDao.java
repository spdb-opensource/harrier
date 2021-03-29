package cn.com.spdb.uds.db.dao;

import java.util.HashMap;
import java.util.List;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStepInfo;
import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.db.bean.UdsErrorBean;
import cn.com.spdb.uds.db.bean.UdsJobStepRecord;

public class UdsJobRecordDao extends AbstractBaseDao {

	public int insertUdsErrorList(List<UdsErrorBean> listError) {
		return insert("uds_job_record.insertUdsErrorList", listError);
	}

	public int insertUdsError(int code, byte msgLevel, String msg, List<String> msgPars) {
		UdsErrorBean bean = new UdsErrorBean();
		bean.setCode(code);
		bean.setMsg_level(msgLevel);
		bean.setMsg(msg);
		StringBuffer msg_parBuffer = new StringBuffer();
		for (String par : msgPars) {
			msg_parBuffer.append(par).append(",");
		}
		bean.setMsg_par(msg_parBuffer.toString());
		return insert("uds_job_record.insertUdsError", bean);
	}

	public int insertJobRecord(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		return insert("uds_job_record.insertJobRecord", map);
	}

	public int updateJobStepRecord(long id, String platform, String system, String job, long numTimes, String job_date,
			JobStepInfo jobStepInfo, byte virtualEnable) {
		UdsJobStepRecord udsJobStepRecord = new UdsJobStepRecord();
		udsJobStepRecord.setId(id);
		udsJobStepRecord.setPlatform(platform);
		udsJobStepRecord.setSystem(system);
		udsJobStepRecord.setJob(job);
		udsJobStepRecord.setSetp_num(jobStepInfo.getSetpNum());
		udsJobStepRecord.setReturn_code(jobStepInfo.getReturnCode());
		udsJobStepRecord.setNum_times(numTimes);
		udsJobStepRecord.setServer_name(UdsConstant.SERVER_NAME);
		udsJobStepRecord.setStart_time(jobStepInfo.getStartTime());
		udsJobStepRecord.setEnd_time(jobStepInfo.getEndtTime());
		udsJobStepRecord.setJob_date(job_date);
		udsJobStepRecord.setVirtual_enable(virtualEnable);
		udsJobStepRecord.setScript_name(jobStepInfo.getScript_name());
		udsJobStepRecord.setLog_name(jobStepInfo.getLogName());
		udsJobStepRecord.setLog_path(jobStepInfo.getLogDir());
		return insert("uds_job_record.updateJobStepRecord", udsJobStepRecord);
	}

	public int updateJobStepRecordById(long id, String platform, String system, String job, long numTimes, String job_date,
			JobStepInfo jobStepInfo, byte virtualEnable) {
		UdsJobStepRecord udsJobStepRecord = new UdsJobStepRecord();
		udsJobStepRecord.setId(id);
		udsJobStepRecord.setPlatform(platform);
		udsJobStepRecord.setSystem(system);
		udsJobStepRecord.setJob(job);
		udsJobStepRecord.setSetp_num(jobStepInfo.getSetpNum());
		udsJobStepRecord.setReturn_code(jobStepInfo.getReturnCode());
		udsJobStepRecord.setNum_times(numTimes);
		udsJobStepRecord.setServer_name(UdsConstant.SERVER_NAME);
		udsJobStepRecord.setStart_time(jobStepInfo.getStartTime());
		udsJobStepRecord.setEnd_time(jobStepInfo.getEndtTime());
		udsJobStepRecord.setJob_date(job_date);
		udsJobStepRecord.setVirtual_enable(virtualEnable);
		udsJobStepRecord.setScript_name(jobStepInfo.getScript_name());
		udsJobStepRecord.setLog_name(jobStepInfo.getLogName());
		udsJobStepRecord.setLog_path(jobStepInfo.getLogDir());
		return insert("uds_job_record.updateJobStepRecordById", udsJobStepRecord);
	}
	
	public int insertJobStepRecord(long id, String platform, String system, String job, long numTimes, String job_date,
			JobStepInfo jobStepInfo, byte virtualEnable) {
		UdsJobStepRecord udsJobStepRecord = new UdsJobStepRecord();
		udsJobStepRecord.setId(id);
		udsJobStepRecord.setPlatform(platform);
		udsJobStepRecord.setSystem(system);
		udsJobStepRecord.setJob(job);
		udsJobStepRecord.setSetp_num(jobStepInfo.getSetpNum());
		udsJobStepRecord.setReturn_code(jobStepInfo.getReturnCode());
		udsJobStepRecord.setNum_times(numTimes);
		udsJobStepRecord.setServer_name(UdsConstant.SERVER_NAME);
		udsJobStepRecord.setStart_time(jobStepInfo.getStartTime());
		udsJobStepRecord.setEnd_time(jobStepInfo.getEndtTime());
		udsJobStepRecord.setJob_date(job_date);
		udsJobStepRecord.setVirtual_enable(virtualEnable);
		udsJobStepRecord.setScript_name(jobStepInfo.getScript_name());
		udsJobStepRecord.setLog_name(jobStepInfo.getLogName());
		udsJobStepRecord.setLog_path(jobStepInfo.getLogDir());
		return insert("uds_job_record.insertJobStepRecord", udsJobStepRecord);
	}
}
