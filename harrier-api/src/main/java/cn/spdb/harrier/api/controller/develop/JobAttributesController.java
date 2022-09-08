package cn.spdb.harrier.api.controller.develop;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.service.develop.IJobAttributesService;
import cn.spdb.harrier.api.service.develop.IYamlManagerService;
import cn.spdb.harrier.api.utils.FileUtils;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.DyJobAttributes;

@RestController
@RequestMapping(path = "/jobattributes")
public class JobAttributesController {
	@Autowired
	private IJobAttributesService jobAttributesService;
	@Autowired
	private IYamlManagerService yamlManagerService;
	@Autowired
	private IJobArrangeService jobArrangeService;

	/**
	 * 根据id查询
	 * 
	 * @param job
	 * @return
	 */
	@GetMapping("/loadById/{id}")
	public DyJobAttributes load(@PathVariable(value = "id") Long id) {
		return jobAttributesService.loadById(id);
	}

	/**
	 * 根据作业名查找
	 * 
	 * @param job
	 * @return
	 */
	@GetMapping("/loadByJobName")
	public DyJobAttributes loadByJobName(String platform, String systems, String job, Integer version) {
		// 获取最新作业编排信息
		DyJobArrange jobArrange = jobArrangeService.loadByJobVersion(platform, systems, job, version);

		// 解析作业属性
		DyJobAttributes jobAttributes = yamlManagerService.parseDeployYaml(jobArrange);
		DyJobAttributes jobAttributesTemp = jobAttributesService.loadByJobName(platform, systems, job);

		if (jobAttributesTemp != null) {
			jobAttributesService.updateJobAttributes(jobAttributes);
		} else {
			jobAttributesService.createJob(jobAttributes);
		}
		// 根据作业编排信息
		return jobAttributesService.loadByJobName(platform, systems, job);
	}

	/**
	 * 查询可用的作业，用于配置作业依赖
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	@GetMapping("/jobSearch")
	public Set<String> jobSearch(String platform, String systems, String job) {
		return jobAttributesService.loadJob(platform, systems, job);
	}

	/**
	 * 根据id删除作业属性信息
	 * 
	 * @param id
	 * @return
	 */
	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("/delete/{id}")
	public Integer delete(@PathVariable(value = "id") Long id) {
		return jobAttributesService.deleteJobById(id);
	}

	/**
	 * 新增
	 * 
	 * @param dyJobAttributes
	 * @return
	 */
	@PostMapping("/add")
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "map" })
	public String add(@RequestBody Map<String, String> map) {
		DyJobAttributes dyJobAttributes = JSONObject.parseObject(map.get("dyJobAttributes"), DyJobAttributes.class);
		List<JobStepConfig> jobStepList = JSONArray.parseArray(map.get("jobStepList"), JobStepConfig.class);
		List<JobDepRelations> jobDepRelationList = JSONArray.parseArray(map.get("depJobList"), JobDepRelations.class);

		// 属性规范检测
		String CheckCode = jobAttributesCheck(dyJobAttributes);
		String result = "sucess";
		// 数据插入
		switch (CheckCode) {
		case "000":
			// 校验通过，正常插入数据
			try {
				jobAttributesService.createJob(dyJobAttributes);

				// 获取jobyaml对象
				JobYamlObject jobYamlObject = yamlManagerService.YamlPretreat(dyJobAttributes, jobStepList,
						jobDepRelationList);

				// 生成作业编排信息
				String filePath = yamlManagerService.GenerateYaml(jobYamlObject);
				String jobYaml = yamlManagerService.JobYaml(filePath);
				DyJobArrange dyJobArrange = yamlManagerService.createDyJobArrange(jobYamlObject, jobYaml);

				// 将作业编排信息插入到作业编排表中
				jobArrangeService.insertOneJobArrange(dyJobArrange);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "001":
			// 作业名不符合规范
			result = "crontab error";
			break;
		case "002":
			result = "job name error";
			break;
		}
		return result;
	}

	/**
	 * 更新作业属性信息
	 * 
	 * @param id
	 * @param dyJobAttributes
	 * @return
	 */
	@PutMapping("/update")
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "map" })
	public String update(@RequestBody Map<String, String> map) {
		DyJobAttributes dyJobAttributes = JSONObject.parseObject(map.get("dyJobAttributes"), DyJobAttributes.class);
		List<JobStepConfig> jobStepList = JSONArray.parseArray(map.get("jobStepList"), JobStepConfig.class);
		List<JobDepRelations> jobDepRelationList = JSONArray.parseArray(map.get("depJobList"), JobDepRelations.class);
		dyJobAttributes.setId(Long.valueOf(map.get("id")));
		// 属性规范检测
		String CheckCode = jobAttributesCheck(dyJobAttributes);
		String result = "sucess";
		// 数据插入
		switch (CheckCode) {
		case "000":
			try {
				// 校验通过，正常插入数据
				jobAttributesService.updateJobAttributes(dyJobAttributes);

				// 获取jobyaml对象
				JobYamlObject jobYamlObject = yamlManagerService.YamlPretreat(dyJobAttributes, jobStepList,
						jobDepRelationList);
				jobYamlObject.setVersion(jobYamlObject.getVersion() - 1);

				// 生成作业编排信息
				String filePath = yamlManagerService.GenerateYaml(jobYamlObject);
				String jobYaml = yamlManagerService.JobYaml(filePath);
				DyJobArrange dyJobArrange = yamlManagerService.createDyJobArrange(jobYamlObject, jobYaml);

				// 将作业编排信息插入到作业编排表中
				jobArrangeService.updateJobArrange(dyJobArrange);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "001":
			// 作业名不符合规范
			result = "crontab error";
			break;
		case "002":
			result = "job name error";
			break;
		}
		return result;
	}

	/**
	 * 上传脚本文件
	 * 
	 * @param req
	 * @param resp
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload")
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "files" })
	public Map<String, Object> upload(@RequestParam("file") MultipartFile[] files, String fileType, String platform,
			String systems, String job, Integer version) throws Exception {
		return FileUtils.uploadFile(files, fileType, platform, systems, job, version);
	}

	/**
	 * 上传工程文件，并执行解析，插入编排表
	 * 
	 * @param file
	 * @param fileType
	 * @param platform
	 * @param systems
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/parseProject")
	public Map<String, Object> parseProject(@RequestParam("file") MultipartFile file, String fileType)
			throws Exception {
		// 返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		// 文件上传
		File srcfile = FileUtils.uploadTar(file, fileType);
		// 解压
		String destpath = srcfile.getParent();
		String password = "";
		if (null != srcfile) {
			FileUtils.doUnArchiver(srcfile, destpath, password);
		}
		// 解析
		File projectFile = new File(
				srcfile.getCanonicalPath().substring(0, srcfile.getCanonicalPath().indexOf(".tar")));
		File[] listFiles = projectFile.listFiles();
		if (!ArrayUtils.isEmpty(listFiles)) {
			for (File f : listFiles) {
				if (Pattern.matches("^[1-9]d*|0$", f.getName())) {// 单包部署
					File[] listFilesTmp1 = f.listFiles();
					for (File f1 : listFilesTmp1) {
						if (!f1.isDirectory() && f1.getName().contains(".yaml")) {
							String jobYaml = yamlManagerService.JobYaml(f1.getAbsolutePath());
							Yaml yaml = new Yaml();
							JobYamlObject jobYamlObject = yaml.loadAs(jobYaml, JobYamlObject.class);
							DyJobArrange importDyJobArrange = yamlManagerService.createDyJobArrange(jobYamlObject,
									jobYaml);
							jobArrangeService.insertOneJobArrange(importDyJobArrange);
							res.put("msg", "文件解析成功，插入编排表");
							break;
						} else {
							res.put("msg", "未找到yaml配置文件");
							continue;
						}
					}
				} else { // 批量导入
					File[] listFilesTmp2 = f.listFiles();
					for (File f2 : listFilesTmp2) {
						File[] listFilesTmp3 = f2.listFiles();
						for (File f3 : listFilesTmp3) {
							if (Pattern.matches("^[1-9]d*|0$", f3.getName())) {
								File[] listFilesTmp4 = f3.listFiles();
								for (File f4 : listFilesTmp4) {
									if (!f4.isDirectory() && f4.getName().contains(".yaml")) {
										String jobYaml = yamlManagerService.JobYaml(f4.getAbsolutePath());
										Yaml yaml = new Yaml();
										JobYamlObject jobYamlObject = yaml.loadAs(jobYaml, JobYamlObject.class);
										DyJobArrange importDyJobArrange = yamlManagerService
												.createDyJobArrange(jobYamlObject, jobYaml);
										jobArrangeService.insertOneJobArrange(importDyJobArrange);
										break;
									} else {
										res.put("msg", "未找到yaml配置文件");
										continue;
									}
								}
							}
						}
					}
				}
			}

		}
		return res;
	}

	/**
	 * 检测作业属性填写合规性
	 * 
	 * @param dyJobAttributes
	 * @return
	 */
	public String jobAttributesCheck(DyJobAttributes dyJobAttributes) {
		String checkCode = "000";
		// 检查作业Cron表达式的规范性
		if ("C".equals(dyJobAttributes.getJobType())) {
			try {
				LocalDateTime nextStart = DateUtils.getNextValidTime(dyJobAttributes.getJobFrequency(),
						LocalDateTime.now());
				if (null == nextStart) {
					return "001";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ("W".equals(dyJobAttributes.getJobType()) || "M".equals(dyJobAttributes.getJobType())
				|| "Y".equals(dyJobAttributes.getJobType())) {
			try {
				Date nextStart = DateUtils.getNextValidTime(dyJobAttributes.getJobFrequency(), new Date());
				if (null == nextStart) {
					return "001";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 检查作业名是否符合规范
		String[] tempJob = dyJobAttributes.getJob().split("_");
		if (!tempJob[0].equals(dyJobAttributes.getPlatform())) {
			return "002";
		}
		if (!tempJob[1].equals(dyJobAttributes.getSystems())) {
			return "002";
		}
		return checkCode;
	}
}
