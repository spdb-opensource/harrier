package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.job.UdsJobStepService;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.rpc.transport.File.FileMessage;
import cn.spdb.harrier.rpc.transport.File.FileStatus;

@RestController
@RequestMapping("/udsJobStep")
public class UdsJobStepController {
	@Autowired
	private UdsJobStepService service;

	@AccessLogAnnotation(isDbInstall = true)
	@PutMapping("/add")
	public int add(UdsJobStep record) {
		return service.add(record);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("/delete")
	public int delete(Long id) {
		return service.delete(id);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PostMapping("/update")
	public int update(UdsJobStep record) {
		return service.update(record);
	}

	@GetMapping("/getDetail")
	public UdsJobStep getDetail(Long id) {
		return service.getDetail(id);
	}

	@GetMapping("/listQuery")
	public List<UdsJobStep> listQuery() {
		return service.listQuery();
	}

	@GetMapping("/selectJobStepList")
	public Page<UdsJobStep> selectJobStepList(Page<UdsJobStep> page, String platform, String systems, String job) {
		return service.selectJobStepList(page, platform, systems, job);
	}

//	@RequestMapping(path = "loadStep", method = RequestMethod.GET)
//	public FileMessage loadStep(String platform, String systems, String job, Byte index, Long streamId, Long pos,
//			Long size) {
//		FileMessage fileMessage = new FileMessage();
//		fileMessage.setStatus(FileStatus.FAIL_FILE.getType());
//		fileMessage.setStreamId(streamId);
//		UdsJobStep step = service.downloadJobStep(platform, systems, job, index);
//		if (ObjectUtils.isEmpty(step)) {
//			return fileMessage;
//
//		}
//		String uir = step.getScriptPath();
//		if() {
//			
//		}
//		service.downloadResource(URI.valueOf(uir), streamId, pos, size);
//
//	}

	@RequestMapping(path = "loadStep", method = RequestMethod.POST)
	public FileMessage loadStep(String uir, Long streamId, Long pos, Long size) {
		return service.downloadResource(URI.valueOf(uir), streamId, pos, size);

	}
}