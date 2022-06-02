package cn.spdb.harrier.api.service.develop.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IJobConfigChangeService;
import cn.spdb.harrier.common.uitls.StringUtils;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobConfig;
import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import cn.spdb.harrier.dao.entity.UdsJobDependency;
import cn.spdb.harrier.dao.entity.UdsJobSource;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;
import cn.spdb.harrier.dao.entity.UdsJobWeight;
import cn.spdb.harrier.dao.entity.UdsSystem;
import cn.spdb.harrier.dao.mapper.DyJobArrangeMapper;
import cn.spdb.harrier.dao.mapper.UdsJobConfigMapper;
import cn.spdb.harrier.dao.mapper.UdsJobDateFrequencyMapper;
import cn.spdb.harrier.dao.mapper.UdsJobDependencyMapper;
import cn.spdb.harrier.dao.mapper.UdsJobMapper;
import cn.spdb.harrier.dao.mapper.UdsJobSourceMapper;
import cn.spdb.harrier.dao.mapper.UdsJobStepMapper;
import cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerMapper;
import cn.spdb.harrier.dao.mapper.UdsJobWeightMapper;
import cn.spdb.harrier.dao.mapper.UdsSystemMapper;

@Service
public class JobConfigChangeServiceImpl implements IJobConfigChangeService {
	
	@Autowired
	private UdsJobMapper udsJobMapper;
	@Autowired
	private UdsJobConfigMapper udsJobConfigMapper;
	@Autowired
	private UdsJobDateFrequencyMapper udsJobDateFrequencyMapper;
	@Autowired
	private UdsJobDependencyMapper udsJobDependencyMapper;
	@Autowired
	private UdsJobSourceMapper udsJobSourceMapper;
	@Autowired
	private UdsJobStepMapper udsJobStepMapper;
	@Autowired
	private UdsJobTimeTriggerMapper udsJobTimeTriggerMapper;
	@Autowired
	private UdsJobWeightMapper udsJobWeightMapper;
	@Autowired
	private UdsSystemMapper udsSystemMapper;
	@Autowired
	private DyJobArrangeMapper dyJobArrangeMapper;
	
	private JobYamlObject jobYamlObject = new JobYamlObject();
	
	
	@Override
	public UdsJob selectUdsJob(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobMapper.selectOne(platform, systems, job).orElse(null);
	}

	@Override
	public UdsJobConfig selectUdsJobConfig(String platform, String systems, String job)  {
		// TODO Auto-generated method stub
		return udsJobConfigMapper.selectOne(platform, systems, job).orElse(null);
	}

	@Override
	public List<UdsJobDateFrequency> selectUdsJobDateFrequency(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobDateFrequencyMapper.select(platform, systems, job);
	}

	@Override
	public List<UdsJobDependency> selectUdsJobDependency(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobDependencyMapper.selectJobDeps(platform, systems, job);
	} 

	@Override
	public UdsJobSource selectUdsJobSource(String platform, String systems, String job)  {
		// TODO Auto-generated method stub
		return udsJobSourceMapper.selectOne(platform, systems, job).orElse(null);
	}

	@Override
	@SelectProvider
	public List<UdsJobStep> selectUdsJobStep(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobStepMapper.selectJobStepList(platform, systems, job);
	}

	@Override
	public List<UdsJobTimeTrigger> selectUdsJobTimeTrigger(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobTimeTriggerMapper.select(platform, systems, job);
	}

	@Override
	public List<UdsJobWeight> selectUdsJobWeight(String platform, String systems, String job) {
		// TODO Auto-generated method stub
		return udsJobWeightMapper.select(platform, systems, job);
	}

	@Override
	public UdsSystem selectUdsSystem(String platform, String systems) {
		// TODO Auto-generated method stub
		return udsSystemMapper.selectByPlatAndSys(platform, systems).orElse(null);
	}

	@Override
	public JobYamlObject createJobYamloJobYamlObject(String platform, String systems, String job, int taskStatus) {
		// TODO Auto-generated method stub
		if(!StringUtils.isBlank(job)) {
			// 从uds_job表中获取属性信息，并赋值给yamlobject
			UdsJob selectUdsJob = selectUdsJob(platform, systems, job);
			jobYamlObject.setPlatform(selectUdsJob.getPlatform());
			jobYamlObject.setSystems(selectUdsJob.getSystems());
			jobYamlObject.setJob(selectUdsJob.getJob());
			jobYamlObject.setJobDate(selectUdsJob.getJobDate().toString());
			jobYamlObject.setLastStatus(selectUdsJob.getLastStatus());
			jobYamlObject.setMultiBatch(selectUdsJob.getMultiBatch());
			jobYamlObject.setDes(selectUdsJob.getDes());
			
			// 从uds_job_config表中获取作业属性配置信息
			UdsJobConfig selectUdsJobConfig = selectUdsJobConfig(platform, systems, job);
			jobYamlObject.setJobName(selectUdsJobConfig.getJobName());
			jobYamlObject.setJobType(selectUdsJobConfig.getJobType());
			jobYamlObject.setTimeWindow(selectUdsJobConfig.getTimeWindow());
			jobYamlObject.setVirtualEnable(selectUdsJobConfig.getVirtualEnable());
			jobYamlObject.setPriority(selectUdsJobConfig.getPriority());
			jobYamlObject.setCallAgainMaxNum(selectUdsJobConfig.getCallAgainMaxNum());
			jobYamlObject.setCallAgainWaitSec(selectUdsJobConfig.getCallAgainWaitSec());
			jobYamlObject.setCheckFrequency(selectUdsJobConfig.getCheckFrequency());
			jobYamlObject.setCheckTimeTrigger(selectUdsJobConfig.getCheckTimeTrigger());
			jobYamlObject.setCheckStreamSelf(selectUdsJobConfig.getCheckStreamSelf());
			jobYamlObject.setOffsetDay(selectUdsJobConfig.getOffsetDay());
			jobYamlObject.setIgnoreError(selectUdsJobConfig.getIgnoreError());
			
			// 处理作业依赖关系
			List<UdsJobDependency> JobDependencyList = selectUdsJobDependency(platform, systems, job);
			List<JobDepRelations> jobDepsList = new ArrayList<JobDepRelations>();
			if(JobDependencyList != null) {
				for(UdsJobDependency jobDep : JobDependencyList) {
					JobDepRelations jobDepRelation = new JobDepRelations();
					jobDepRelation.setDepPlatform(jobDep.getDepPlatform());
					jobDepRelation.setDepSystem(jobDep.getDepSystem());
					jobDepRelation.setDepJob(jobDep.getDepJob());
					jobDepRelation.setDepBatch(jobDep.getDepBatch());
					
					jobDepsList.add(jobDepRelation);
				}
				jobYamlObject.setDepJob(jobDepsList);
			}
			
			// 处理作业的脚本
			List<UdsJobStep> JobStepList = selectUdsJobStep(platform, systems, job);
			List<JobStepConfig> stepsList = new ArrayList<JobStepConfig>();
			if(JobStepList != null) {
				for(UdsJobStep jobStep : JobStepList) {
					JobStepConfig jobStepConfig = new JobStepConfig();
					jobStepConfig.setStepNum(jobStep.getStepNum());
					jobStepConfig.setOperCmd(jobStep.getOperCmd());
					jobStepConfig.setScriptPath(jobStep.getScriptPath());
					jobStepConfig.setParameter(jobStep.getParameter());
					
					stepsList.add(jobStepConfig);
				}
				jobYamlObject.setJobStep(stepsList);
			}
			
			// 定时或特殊频度Crontab
			if("C".equals(selectUdsJobConfig.getJobType())) {
				ArrayList<String> arrayList = new ArrayList<String>();
				List<UdsJobTimeTrigger> selectUdsJobTimeTrigger = selectUdsJobTimeTrigger(platform, systems, job);
				for(UdsJobTimeTrigger u:selectUdsJobTimeTrigger) {
					arrayList.add(u.getCrontab());
				}
				jobYamlObject.setJobFrequency(arrayList);
			}else if("W".equals(selectUdsJobConfig.getJobType())|| "M".equals(selectUdsJobConfig.getJobType()) || 
					"Y".equals(selectUdsJobConfig.getJobType())) {
				ArrayList<String> arrayList = new ArrayList<String>();
				List<UdsJobDateFrequency> selectUdsJobDateFrequency = selectUdsJobDateFrequency(platform, systems, job);
				for(UdsJobDateFrequency u:selectUdsJobDateFrequency) {
					arrayList.add(u.getCrontab());
				}
				jobYamlObject.setJobFrequency(arrayList);
			}
			
			// 作业打版
			DyJobArrange jobArrangeInfo = dyJobArrangeMapper.selectOne(platform, systems, job).orElse(null);
			Integer version;
			if (jobArrangeInfo != null) {
				version = jobArrangeInfo.getVersion();
				version++;
				jobYamlObject.setVersion(version);
			} else {
				version = 1;
				jobYamlObject.setVersion(version);
			}
			jobYamlObject.setTaskStatus(taskStatus);
			return jobYamlObject;
		}
		return null;
	}

}
