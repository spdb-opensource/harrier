package cn.spdb.harrier.api.controller.develop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.service.develop.IJobConfigChangeService;
import cn.spdb.harrier.api.service.develop.IYamlManagerService;
import cn.spdb.harrier.api.utils.JobDeployPath;
import cn.spdb.harrier.api.utils.JobDeployStatus;
import cn.spdb.harrier.api.utils.FileUtils;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.DyJobAttributes;
import cn.spdb.harrier.dao.entity.UdsJob;

@RestController
@RequestMapping(path = "jobarrange")
public class JobArrangeController {

	@Autowired
	private IJobArrangeService jobArrangeService;
	@Autowired
	private IYamlManagerService yamlManagerService;
	@Autowired
	private IJobConfigChangeService jobConfigChangeService;

	/**
	 * 分页查询作业编排表记录
	 * 
	 * @param page
	 * @param platform
	 * @param systems
	 * @param job
	 * @param version
	 * @return
	 */
	@GetMapping("/findDjAgAll")
	public Page<DyJobArrange> findDjAgAll(Page<DyJobArrange> page, String platform, String systems, String job,
			Integer version) {
		if (!StringUtils.isBlank(job)) {
			job = job.toUpperCase();
		}
		Page<DyJobArrange> records = jobArrangeService.selectManyInfo(page, platform, systems, job, version);
		return records;
	}

	/**
	 * 根据作业名获取该作业所有版本的作业编排信息(支持模糊查询)
	 * 
	 * @param page
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	@GetMapping("/findAllJobVersion")
	public List<DyJobArrange> findAllJobVersion(Page<DyJobArrange> page, String platform, String systems, String job) {
		Page<DyJobArrange> records = jobArrangeService.findAllJobVersion(page, platform, systems, job);
		List<DyJobArrange> jobAllVersion = records.getRecords();
		return jobAllVersion;
	}

	/**
	 * 查询指定版本的作业编排信息
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @param version
	 * @return
	 */
	@GetMapping("/loadJobArrangesByVersion")
	public DyJobArrange loadJobArrangesByVersion(String platform, String systems, String job, Integer version) {
		return jobArrangeService.loadByJobVersion(platform, systems, job, version);
	}

	/**
	 * 根据作业名查询最新版本的作业编排信息
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	@GetMapping("/loadMaxVersionJob")
	public DyJobArrange loadMaxVersionJob(String platform, String systems, String job) {
		return jobArrangeService.loadMaxVersionJob(platform, systems, job);
	}

	/**
	 * 获取指定作业所有的版本号
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	@GetMapping("/versionCodeList")
	public List<Integer> versionCodeList(String platform, String systems, String job) {
		return jobArrangeService.findJobVersionCode(platform, systems, job);
	}

	/**
	 * 根据ID获取作业属性信息，用于编辑
	 * 
	 * @param id 编排信息ID
	 * @return
	 */
	@GetMapping("/loadJobArranges/{id}")
	public Map<String, Object> loadJobArranges(@PathVariable(value = "id") Long id) throws Exception {
		// 返回结果
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 查询作业编排信息
		DyJobArrange arrange = jobArrangeService.loadById(id);

		// 作业属性信息
		DyJobAttributes attributes = yamlManagerService.parseDeployYaml(arrange);

		// 作业依赖信息
		List<JobDepRelations> jobDepList = yamlManagerService.parseJobDep(arrange);

		// 作业脚本关系信息
		List<JobStepConfig> jobStepList = yamlManagerService.parseJobStep(arrange);

		resultMap.put("DyJobAttributes", attributes);
		resultMap.put("DepList", jobDepList);
		resultMap.put("StepList", jobStepList);

		return resultMap;
	}

	/**
	 * 根据id删除作业编排信息
	 * 
	 * @param id
	 * @return
	 */
	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("/delete/{id}")
	public Integer delete(@PathVariable(value = "id") Long id) {
		return jobArrangeService.deleteJobById(id);
	}

	/**
	 * 更新作业编排信息
	 * 
	 * @param id
	 * @param dyJobAttributes
	 * @return
	 */
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "dyJobArrange" })
	@PostMapping("/update/{id}")
	public Integer update(@PathVariable(value = "id") Long id, DyJobArrange dyJobArrange) {
		dyJobArrange.setId(id);
		return jobArrangeService.updateJobArrange(dyJobArrange);
	}

	/**
	 * 下载工程tar包
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "request", "response" })
	@GetMapping("/download")
	public void download(String platform, String systems, String job, Integer version, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 判断job工程目录是否存在，不存在则创建
		File uploadFile = new File(JobDeployPath.UPLOAD_SCRIPT_PATH.getValue());
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}

		// 压缩
		String path = uploadFile + "/" + platform + "/" + systems + "/" + job + "/" + version;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		// path是指欲下载的文件的路径
		File compressFile = new File(JobDeployPath.COMPRESS_TEMP_PATH.getValue());
		if (!compressFile.exists()) {
			compressFile.mkdirs();
		}
		String destPath = compressFile + "/" + job + ".tar";
		FileUtils.doArchiver(destPath, file);

		// 下载
		File projectFile = new File(destPath);
		InputStream fis = new BufferedInputStream(new FileInputStream(projectFile));
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(projectFile.getName(), "UTF-8"));

		// 以流的形式下载文件。
		byte[] buffer = new byte[fis.available()];
		OutputStream outputStream4Client = null;

		try {
			outputStream4Client = new BufferedOutputStream(response.getOutputStream());
			int len;
			while ((len = fis.read(buffer)) > 0) {
				outputStream4Client.write(buffer, 0, len);
				outputStream4Client.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件下载失败，请联系管理员");
		} finally {
			/** close stream */
			try {
				if (null != fis) {
					fis.close();
				}
				// 删除临时文件
				if ((null != projectFile) && projectFile.isFile() && projectFile.exists()) {
					projectFile.delete();
				}
				// 关闭输出流
				if (null != outputStream4Client) {
					outputStream4Client.close();
				}
			} catch (IOException e) {
				// Exception handle
				e.printStackTrace();
				throw new RuntimeException("文件下载失败，请联系管理员");
			}
		}
		response.setContentType("application/octet-stream");
	}

	/**
	 * 变更和下线作业生成作业编排信息
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @param taskStatus
	 * @return
	 */

	@AccessLogAnnotation(isDbInstall = true)
	@GetMapping("/generateJobArrange")
	public Map<String, Object> createDyJobArrange(String platform, String systems, String job, Integer taskStatus) {
		// 返回结果
		Map<String, Object> res = new HashMap<String, Object>();
		// 作业编排表中如果该作业有尚未上线（投产成功状态）的编排信息，则不允许对作业进行变更
		DyJobArrange dyJobArrange = jobArrangeService.loadByJobName(platform, systems, job);
		if (dyJobArrange != null && dyJobArrange.getProcessStatus() == 3) {
			// 生成作业编排信息
			JobYamlObject jobYamlObject = jobConfigChangeService.createJobYamloJobYamlObject(platform, systems, job,
					taskStatus);
			try {
				String jobYaml = yamlManagerService.JobYaml(yamlManagerService.GenerateYaml(jobYamlObject));
				DyJobArrange jobArrange = yamlManagerService.createDyJobArrange(jobYamlObject, jobYaml);
				// 拉链时间处理，新增作业编排
				dyJobArrange.setEndDate(LocalDateTime.now());
				jobArrangeService.updateJobArrange(dyJobArrange);
				jobArrange.setTaskStatus(taskStatus);
				jobArrange.setStartDate(dyJobArrange.getEndDate());
				jobArrangeService.insertOneJobArrange(jobArrange);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (dyJobArrange == null) {
			UdsJob udsJobBean = jobConfigChangeService.selectUdsJob(platform, systems, job);
			if (udsJobBean != null) {
				// 生成作业编排信息
				JobYamlObject jobYamlObject = jobConfigChangeService.createJobYamloJobYamlObject(platform, systems, job,
						taskStatus);
				try {
					String jobYaml = yamlManagerService.JobYaml(yamlManagerService.GenerateYaml(jobYamlObject));
					DyJobArrange jobArrangeOld = yamlManagerService.createDyJobArrange(jobYamlObject, jobYaml);
					// 处理拉链
					jobArrangeOld.setEndDate(LocalDateTime.now());
					jobArrangeOld.setTaskStatus(taskStatus);
					jobArrangeOld.setProcessStatus(JobDeployStatus.PROCESS_SUCCESS.getValue());
					jobArrangeService.insertOneJobArrange(jobArrangeOld);

					DyJobArrange jobArrangeNew = yamlManagerService.createDyJobArrange(jobYamlObject, jobYaml);
					Integer version = jobYamlObject.getVersion();
					jobArrangeNew.setVersion(++version);
					jobArrangeNew.setTaskStatus(taskStatus);
					jobArrangeNew.setStartDate(LocalDateTime.now());
					jobArrangeService.insertOneJobArrange(jobArrangeNew);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				res.put("msg", "未知作业，请核对作业名！");
				return res;
			}
		} else {
			res.put("msg", "作业编排中有尚未投产的任务！");
			return res;
		}
		res.put("msg", "生成作业编排信息成功！");
		return res;
	}
}
