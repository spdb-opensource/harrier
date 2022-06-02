package cn.spdb.harrier.api.service.develop.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.service.develop.IYamlManagerService;
import cn.spdb.harrier.api.utils.JobDeployStatus;
import cn.spdb.harrier.common.uitls.CollectionUtils;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.DyJobAttributes;

@Service
public class YamlManagerServiceImpl implements IYamlManagerService {

	@Autowired
	private IJobArrangeService jobArrangeService;

	private DyJobArrange dyJobArrange = new DyJobArrange();

	private JobYamlObject jobYamlObject = new JobYamlObject();

	private DyJobAttributes dyJobAttributes = new DyJobAttributes();

	// yaml预处理
	public JobYamlObject YamlPretreat(DyJobAttributes jobAttributes, List<JobStepConfig> jobStepList,
			List<JobDepRelations> jobDepRelationList) {
		// 获取作业版本信息
		DyJobArrange jobArrangeInfo = jobArrangeService.loadByJobName(jobAttributes.getPlatform(),
				jobAttributes.getSystems(), jobAttributes.getJob());
		Integer version;
		if (jobArrangeInfo != null) {
			version = jobArrangeInfo.getVersion();
			version++;
			jobYamlObject.setVersion(version);
		} else {
			version = 1;
			jobYamlObject.setVersion(version);
		}
		// 常规属性赋值
		jobYamlObject.setPlatform(jobAttributes.getPlatform());
		jobYamlObject.setSystems(jobAttributes.getSystems());
		jobYamlObject.setJob(jobAttributes.getJob());
		jobYamlObject.setJobName(jobAttributes.getJobName());
		jobYamlObject.setJobType(jobAttributes.getJobType());
		jobYamlObject.setJobDate(jobAttributes.getJobDate().toString());
		jobYamlObject.setLastStatus(jobAttributes.getLastStatus());
		jobYamlObject.setTaskStatus(jobAttributes.getTaskStatus());
		jobYamlObject.setMultiBatch(jobAttributes.getMultiBatch());
		jobYamlObject.setTimeWindow(jobAttributes.getTimeWindow());
		jobYamlObject.setVirtualEnable(jobAttributes.getVirtualEnable());
		jobYamlObject.setPriority(jobAttributes.getPriority());
		jobYamlObject.setCallAgainMaxNum(jobAttributes.getCallAgainMaxNum());
		jobYamlObject.setCallAgainWaitSec(jobAttributes.getCallAgainWaitSec());
		jobYamlObject.setCheckFrequency(jobAttributes.getCheckFrequency());
		jobYamlObject.setCheckTimeTrigger(jobAttributes.getCheckTimeTrigger());
		jobYamlObject.setCheckStreamSelf(jobAttributes.getCheckStreamSelf());
		jobYamlObject.setOffsetDay(jobAttributes.getOffsetDay());
		jobYamlObject.setIgnoreError(jobAttributes.getIgnoreError());
		jobYamlObject.setDes(jobAttributes.getDes());

		// 频度处理
		if (!"D".equals(jobAttributes.getJobType())) {
			jobYamlObject.setJobFrequency(Arrays.asList(jobAttributes.getJobFrequency().split("@")));
		}

		// 依赖处理
		jobYamlObject.setDepJob(jobDepRelationList);

		// 脚本处理
		jobYamlObject.setJobStep(jobStepList);

		return jobYamlObject;
	}

	// 生成yaml文件
	@Override
	public String GenerateYaml(JobYamlObject jobYamlObject) {
		String filePath = "";
		Yaml yaml = new Yaml();
		String dumpAsMap = yaml.dumpAsMap(jobYamlObject);
		String yamlFilePath = "./job/project/dev/" + jobYamlObject.getPlatform() + "/" + jobYamlObject.getSystems()
				+ "/" + jobYamlObject.getJob() + "/";
		File devDir = new File(yamlFilePath);
		if (!devDir.exists()) {
			devDir.mkdirs();
		}
		File file = new File(yamlFilePath + "/" + jobYamlObject.getJob() + "-" + jobYamlObject.getVersion() + ".yaml");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(dumpAsMap.getBytes());
			fos.flush();
			filePath = file.getAbsolutePath();
			return filePath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	// 读取yaml文件，处理成字符串
	@Override
	public String JobYaml(String filePath) {
		// 存储yaml文件内容
		String yamlStr = "";
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
				String line = null;
				StringBuffer bf = new StringBuffer();
				// 逐行读取文件内容
				while ((line = br.readLine()) != null) {
					if (line.length() > 0) {
						bf.append(line).append("\n");
					}
				}
				yamlStr = bf.toString();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("没有找到该文件");
		}
		return yamlStr;
	}

	// 新增作业编排
	@Override
	public DyJobArrange createDyJobArrange(JobYamlObject jobYamlObject, String jobYamlStr) {
		// 常规属性赋值
		dyJobArrange.setPlatform(jobYamlObject.getPlatform());
		dyJobArrange.setSystems(jobYamlObject.getSystems());
		dyJobArrange.setJob(jobYamlObject.getJob());
		dyJobArrange.setDeployYaml(jobYamlStr);
		dyJobArrange.setTaskStatus(jobYamlObject.getTaskStatus());
		dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_REDAY.getValue());
		dyJobArrange.setSyncStatus(JobDeployStatus.SYN_SUCCESS.getValue());
		dyJobArrange.setVersion(Integer.valueOf(jobYamlObject.getVersion()));
		dyJobArrange.setDes(jobYamlObject.getDes());

		// 拉链时间处理
		dyJobArrange.setStartDate(LocalDateTime.now());
		dyJobArrange.setEndDate(LocalDateTime.of(LocalDate.of(2099, 12, 31), LocalTime.of(23, 59, 59)));
		return dyJobArrange;
	}

	@Override
	public DyJobAttributes parseDeployYaml(DyJobArrange dyJobArrange) {
		String deployYaml = dyJobArrange.getDeployYaml();
		if (!StringUtils.isBlank(deployYaml)) {
			Yaml yaml = new Yaml();
			JobYamlObject jobYamlObject = yaml.loadAs(deployYaml, JobYamlObject.class);

			// 常规属性赋值
			dyJobAttributes.setPlatform(jobYamlObject.getPlatform());
			dyJobAttributes.setSystems(jobYamlObject.getSystems());
			dyJobAttributes.setJob(jobYamlObject.getJob());
			dyJobAttributes.setJobName(jobYamlObject.getJobName());
			dyJobAttributes.setJobType(jobYamlObject.getJobType());
			dyJobAttributes.setJobDate(LocalDate.parse(jobYamlObject.getJobDate()));
			dyJobAttributes.setLastStatus(jobYamlObject.getLastStatus());
			dyJobAttributes.setTaskStatus(jobYamlObject.getTaskStatus());
			dyJobAttributes.setMultiBatch(jobYamlObject.getMultiBatch());
			dyJobAttributes.setTimeWindow(jobYamlObject.getTimeWindow());
			dyJobAttributes.setVirtualEnable(jobYamlObject.getVirtualEnable());
			dyJobAttributes.setPriority(jobYamlObject.getPriority());
			dyJobAttributes.setCallAgainMaxNum(jobYamlObject.getCallAgainMaxNum());
			dyJobAttributes.setCallAgainWaitSec(jobYamlObject.getCallAgainWaitSec());
			dyJobAttributes.setCheckFrequency(jobYamlObject.getCheckFrequency());
			dyJobAttributes.setCheckTimeTrigger(jobYamlObject.getCheckTimeTrigger());
			dyJobAttributes.setCheckStreamSelf(jobYamlObject.getCheckStreamSelf());
			dyJobAttributes.setOffsetDay(jobYamlObject.getOffsetDay());
			dyJobAttributes.setIgnoreError(jobYamlObject.isIgnoreError());
			dyJobAttributes.setStreamType(Integer.valueOf(0));
			dyJobAttributes.setDes(jobYamlObject.getDes());

			// 作业频度处理
			if (!"D".equals(jobYamlObject.getJobType())) {
				List<String> jobFrequency = jobYamlObject.getJobFrequency();
				String jobFre = "";
				for (String strFre : jobFrequency) {
					jobFre = strFre + "@";
				}
				dyJobAttributes.setJobFrequency(jobFre.substring(0, jobFre.length() - 1));
			}

			// 作业依赖关系处理
			String jobDepRelations = "";
			if (!CollectionUtils.isEmpty(jobYamlObject.getDepJob())) {
				for (JobDepRelations jobDep : jobYamlObject.getDepJob()) {
					jobDepRelations = jobDepRelations + jobDep.toString() + ",";
				}
				;
				dyJobAttributes.setDepJob(jobDepRelations.substring(0, jobDepRelations.length() - 1));
			} else {
				dyJobAttributes.setDepJob(jobDepRelations);
			}

			// 作业脚本关系处理
			String jobSteps = "";
			if (!CollectionUtils.isEmpty(jobYamlObject.getJobStep())) {
				for (JobStepConfig jobStep : jobYamlObject.getJobStep()) {
					jobSteps = jobSteps + jobStep.toString() + ",";
				}
				dyJobAttributes.setJobStep(jobSteps.substring(0, jobSteps.length() - 1));
			} else {
				dyJobAttributes.setJobStep(jobSteps);
			}

			return dyJobAttributes;
		}
		return null;
	}

	/**
	 * 解析编排表中的yaml获取作业依赖关系列表
	 * 
	 * @param dyJobArrange
	 * @return
	 */
	@Override
	public List<JobDepRelations> parseJobDep(DyJobArrange dyJobArrange) {
		String deployYaml = dyJobArrange.getDeployYaml();
		if (!StringUtils.isBlank(deployYaml)) {
			Yaml yaml = new Yaml();
			JobYamlObject jobYamlObject = yaml.loadAs(deployYaml, JobYamlObject.class);

			List<JobDepRelations> depJobList = jobYamlObject.getDepJob();

			return depJobList;
		}
		return null;
	}

	/**
	 * 解析编排表中的yaml获取作业脚本关系列表
	 * 
	 * @param dyJobArrange
	 * @return
	 */
	@Override
	public List<JobStepConfig> parseJobStep(DyJobArrange dyJobArrange) {
		String deployYaml = dyJobArrange.getDeployYaml();
		if (!StringUtils.isBlank(deployYaml)) {
			Yaml yaml = new Yaml();
			JobYamlObject jobYamlObject = yaml.loadAs(deployYaml, JobYamlObject.class);

			List<JobStepConfig> jobStepList = jobYamlObject.getJobStep();

			return jobStepList;
		}
		return null;
	}
}
