package cn.spdb.harrier.api.controller.develop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.develop.IDeploySqlService;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.utils.JobDeployStatus;
import cn.spdb.harrier.dao.entity.DyJobArrange;

@RestController
@RequestMapping(path = "/jobdevelop")
public class JobDevelopController {
	
	@Autowired
	private IJobArrangeService jobArrangeService;
	@Autowired
	private IDeploySqlService deploySqlService;
	
	/**
	 * 部署
	 * @param dyJobArrange
	 * @return
	 */
	@PostMapping("/deploy")
	public boolean deploy(DyJobArrange dyJobArrange) {
		if(dyJobArrange != null) {
			// 生成sql，执行部署
			boolean isDeploySuccess = deploySqlService.deploySqlGenerate(dyJobArrange);
			if(isDeploySuccess) {
				// 修改部署状态为部署成功
				dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_SUCCESS.getValue());
				jobArrangeService.updateJobArrange(dyJobArrange);
			}else {
				// 修改部署状态为部署失败
				dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_FAILED.getValue());
				jobArrangeService.updateJobArrange(dyJobArrange);
			}
			return isDeploySuccess;
		}
		return false;
	}
	
	/**
	 * 回滚
	 * @param dyJobArrange
	 * @return
	 */
	@PostMapping("/rollback")
	public boolean rollback(DyJobArrange dyJobArrange) {
		if(dyJobArrange != null) {
			try {
				deploySqlService.rollBack(dyJobArrange);
				dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_REDAY.getValue());
				jobArrangeService.updateJobArrange(dyJobArrange);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
}
