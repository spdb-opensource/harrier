package cn.spdb.harrier.api.service.develop.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IDeploySqlCreate;
import cn.spdb.harrier.api.utils.JobDeployStatus;
import cn.spdb.harrier.common.utils.CollectionUtils;

@Service
public class DeploySqlCreateServiceImpl implements IDeploySqlCreate{

	// sql语句
	List<String> jobSqlList = new ArrayList<String>();
	
	/**
	 * 新增作业SQL
	 * @param jobConfig
	 * @return
	 */
	@Override
	public List<String> newJobSql(JobYamlObject jobConfig) {
		// 如果list不为空，先情况list
		if(jobSqlList.size() > 0) {
			jobSqlList.clear();
		}
		
		if(null != jobConfig) {
			// uds_job
			jobSqlList.add("REPLACE INTO `uds_job` (`platform`, `systems`, `job`, `last_status`, `job_date`, `multi_batch`, `des`) VALUES( '" + 
					jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getLastStatus() 
					+ "', '" + jobConfig.getJobDate() + "', '" + jobConfig.getMultiBatch() + "', '" + jobConfig.getDes() + "');");
			// uds_job_config
			jobSqlList.add("REPLACE INTO `uds_job_config` (`platform`, `systems`, `job`, `job_name`, `job_type`, `offset_day`, `time_window`, `virtual_enable`, `priority`, "
					+ "`call_again_max_num`, `call_again_wait_sec`, `check_frequency`, `check_time_trigger`, `check_stream_self`, `ignore_error`, `des`) VALUES( '"
					+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobName() + "', '" 
					+ jobConfig.getJobType() + "', '" + jobConfig.getOffsetDay() + "', '" + jobConfig.getTimeWindow() + "', " +  jobConfig.getVirtualEnable() + ", '"
					+ jobConfig.getPriority() + "', '" + jobConfig.getCallAgainMaxNum() + "', '" + jobConfig.getCallAgainWaitSec() + "', " + jobConfig.getCheckFrequency()
					+ ", " + jobConfig.getCheckTimeTrigger() + ", " + jobConfig.getCheckStreamSelf() + ", " + jobConfig.isIgnoreError() + ", '" + jobConfig.getDes() + "');");
			// uds_job_source
			if(jobConfig.getStreamType() != null && (jobConfig.getStreamType() == (int) JobDeployStatus.STREAM_SIGNAL.getValue() || jobConfig.getStreamType() == (int) JobDeployStatus.STREAM_HTTP.getValue())) {
				jobSqlList.add("REPLACE INTO `uds_job_source` (`signals`, `platform`, `systems`, `job`, `enable`) VALUES( '" + jobConfig.getJob() + "', '" + jobConfig.getPlatform()
				+ "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + "1" + "');");			
			}
			// uds_job_dependency
			List<JobDepRelations> depJobList = jobConfig.getDepJob();
			if(!CollectionUtils.isEmpty(depJobList)) {
				for(JobDepRelations depJob : depJobList) {
					jobSqlList.add("REPLACE INTO `uds_job_dependency` (`platform`, `systems`, `job`, `dep_platform`, `dep_system`, `dep_job`, `dep_batch`, `des`) VALUES( '"
							+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + depJob.getDepPlatform() + "', '" 
							+ depJob.getDepSystem() + "', '" + depJob.getDepJob() + "', '" + depJob.getDepBatch() + "', " + "now());");	
				}
			}
			// uds_job_step
			List<JobStepConfig> jobStepList = jobConfig.getJobStep();
			if(!CollectionUtils.isEmpty(jobStepList)) {
				for(JobStepConfig jobStep : jobStepList) {
					jobSqlList.add("REPLACE INTO `uds_job_step` (`platform`, `systems`, `job`, `step_type`, `step_num`, `oper_cmd`, `script_path`, `parameter`, `des`) VALUES( '"
							+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobStep.getStepType() + "', '" + jobStep.getStepNum() 
							+ "', '" +  jobStep.getOperCmd() + "', '" + jobStep.getScriptPath() + "', '" + jobStep.getParameter() + "', " + "now());");
				}
			}
			
			// uds_job_time_trigger
			if("C".equals(jobConfig.getJobType())) {
				jobSqlList.add("REPLACE INTO `uds_job_time_trigger` (`platform`, `systems`, `job`, `crontab`, `des`) VALUES( '" + jobConfig.getPlatform() + "', '" +
						jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobFrequency() + "', " + "now());");
			}
			
			// uds_job_date_frequency
			if("W".equals(jobConfig.getJobType()) || "M".equals(jobConfig.getJobType()) || "Y".equals(jobConfig.getJobType())) {
				jobSqlList.add("REPLACE INTO `uds_job_date_frequency` (`platform`, `systems`, `job`, `crontab`, `des`) VALUES( '" + jobConfig.getPlatform() + "', '" +
						jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobFrequency() + "', " + "now());");
			}
			return jobSqlList;
		}
		return null;
	}
	
	/**
	 * 变更作业SQL
	 * @param jobConfig
	 * @return
	 */
	@Override
	public List<String> updateJobSql(JobYamlObject jobConfig) {
		// 如果list不为空，先情况list
		if(!CollectionUtils.isEmpty(jobSqlList)) {
			jobSqlList.clear();
		}
		
		if(null != jobConfig) {
			// uds_job
			jobSqlList.add("REPLACE INTO `uds_job` (`platform`, `systems`, `job`, `last_status`, `job_date`, `multi_batch`, `des`) VALUES( '" + 
					jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getLastStatus() 
					+ "', '" + jobConfig.getJobDate() + "', '" + jobConfig.getMultiBatch() + "', '" + jobConfig.getDes() + "');");
			// uds_job_config
			jobSqlList.add("REPLACE INTO `uds_job_config` (`platform`, `systems`, `job`, `job_name`, `job_type`, `offset_day`, `time_window`, `virtual_enable`, `priority`, "
					+ "`call_again_max_num`, `call_again_wait_sec`, `check_frequency`, `check_time_trigger`, `check_stream_self`, `ignore_error`, `des`) VALUES( '"
					+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobName() + "', '" 
					+ jobConfig.getJobType() + "', '" + jobConfig.getOffsetDay() + "', '" + jobConfig.getTimeWindow() + "', " +  jobConfig.getVirtualEnable() + ", '"
					+ jobConfig.getPriority() + "', '" + jobConfig.getCallAgainMaxNum() + "', '" + jobConfig.getCallAgainWaitSec() + "', " + jobConfig.getCheckFrequency()
					+ ", " + jobConfig.getCheckTimeTrigger() + ", " + jobConfig.getCheckStreamSelf() + ", " + jobConfig.isIgnoreError() + ", '" + jobConfig.getDes() + "');");
			// uds_job_source
			if(jobConfig.getStreamType() != null && jobConfig.getStreamType() == (int) JobDeployStatus.STREAM_SIGNAL.getValue()) {
				jobSqlList.add("REPLACE INTO `uds_job_source` (`signals`, `platform`, `systems`, `job`, `enable`) VALUES( '" + jobConfig.getJob() + "', '" + jobConfig.getPlatform()
				+ "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + "1" + "');");			
			}
			// uds_job_dependency
			List<JobDepRelations> depJobList = jobConfig.getDepJob();
			if(!CollectionUtils.isEmpty(depJobList)) {
				for(JobDepRelations depJob : depJobList) {
					jobSqlList.add("REPLACE INTO `uds_job_dependency` (`platform`, `systems`, `job`, `dep_platform`, `dep_system`, `dep_job`, `dep_batch`, `des`) VALUES( '"
							+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + depJob.getDepPlatform() + "', '" 
							+ depJob.getDepSystem() + "', '" + depJob.getDepJob() + "', '" + depJob.getDepBatch() + "', " + "now());");	
				}
			}
			// uds_job_step
			List<JobStepConfig> jobStepList = jobConfig.getJobStep();
			if(!CollectionUtils.isEmpty(jobStepList)) {
				for(JobStepConfig jobStep : jobStepList) {
					jobSqlList.add("REPLACE INTO `uds_job_step` (`platform`, `systems`, `job`, `step_type`, `step_num`, `oper_cmd`, `script_path`, `parameter`, `des`) VALUES( '"
							+ jobConfig.getPlatform() + "', '" + jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobStep.getStepType() + "', '" + jobStep.getStepNum() 
							+ "', '" +  jobStep.getOperCmd() + "', '" + jobStep.getScriptPath() + "', '" + jobStep.getParameter() + "', " + "now());");
				}
			}
			
			// uds_job_time_trigger
			if("C".equals(jobConfig.getJobType())) {
				jobSqlList.add("REPLACE INTO `uds_job_time_trigger` (`platform`, `systems`, `job`, `crontab`, `des`) VALUES( '" + jobConfig.getPlatform() + "', '" +
						jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobFrequency() + "', " + "now());");
			}
			
			// uds_job_date_frequency
			if("W".equals(jobConfig.getJobType()) || "M".equals(jobConfig.getJobType()) || "Y".equals(jobConfig.getJobType())) {
				jobSqlList.add("REPLACE INTO `uds_job_date_frequency` (`platform`, `systems`, `job`, `crontab`, `des`) VALUES( '" + jobConfig.getPlatform() + "', '" +
						jobConfig.getSystems() + "', '" + jobConfig.getJob() + "', '" + jobConfig.getJobFrequency() + "', " + "now());");
			}
			return jobSqlList;
		}
		return null;
	}
	
	/**
	 * 下线作业SQL
	 * @param jobConfig
	 * @return
	 */
	@Override
	public List<String> deleteJobSql(JobYamlObject jobConfig) {
		// 如果list不为空，先情况list
		if(jobSqlList.size() > 0) {
			jobSqlList.clear();
		}
		if(null != jobConfig) {
			// uds_job
			jobSqlList.add("DELETE FROM `uds_job` WHERE job = '" + jobConfig.getJob() + "';");
			// uds_job_config
			jobSqlList.add("DELETE FROM `uds_job_config` WHERE job = '" + jobConfig.getJob() + "';");
			// uds_job_source
			if(jobConfig.getStreamType() != null && jobConfig.getStreamType() == (int) JobDeployStatus.STREAM_SIGNAL.getValue()) {
				jobSqlList.add("DELETE FROM `uds_job_source` WHERE job = '" + jobConfig.getJob() + "';");			
			}
			// uds_job_dependency
			jobSqlList.add("DELETE FROM `uds_job_dependency` WHERE job = '" + jobConfig.getJob() + "';");
			jobSqlList.add("DELETE FROM `uds_job_dependency` WHERE dep_job = '" + jobConfig.getJob() + "';");
			// uds_job_step
			jobSqlList.add("DELETE FROM `uds_job_step` WHERE job = '" + jobConfig.getJob() + "';");
			
			// uds_job_time_trigger
			if("C".equals(jobConfig.getJobType())) {
				jobSqlList.add("DELETE FROM `uds_job_time_trigger` WHERE job = '" + jobConfig.getJob() + "';");
			}
			
			// uds_job_date_frequency
			if("W".equals(jobConfig.getJobType()) || "M".equals(jobConfig.getJobType()) || "Y".equals(jobConfig.getJobType())) {
				jobSqlList.add("DELETE FROM `uds_job_date_frequency` WHERE job = '" + jobConfig.getJob() + "';");
			}
			return jobSqlList;
		}
		return null;
	}

}
