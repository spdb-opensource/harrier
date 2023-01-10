package cn.spdb.harrier.api.controller.develop;

import cn.spdb.harrier.api.service.develop.IDeployScriptService;
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
	@Autowired
	private IDeployScriptService deployScriptService;
	
	/**
	 * 部署
	 * @param dyJobArrange
	 * @return
	 */
	@PostMapping("/deploy")
	public boolean deploy(DyJobArrange dyJobArrange) {
		boolean deployStatus = false;
		if(dyJobArrange != null) {
			// 生成sql，执行知识库部署
			boolean isDeploySqlSuccess = deploySqlService.deploySqlGenerate(dyJobArrange);
			// 脚本文件部署
			boolean isDeployScriptSuccess = deployScriptService.deployScript(dyJobArrange);
			if(isDeploySqlSuccess && isDeployScriptSuccess) {
				// 修改部署状态为部署成功
				dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_SUCCESS.getValue());
				jobArrangeService.updateJobArrange(dyJobArrange);
				deployStatus = true;
			}else {
				// 修改部署状态为部署失败
				deploySqlService.rollBack(dyJobArrange);
				dyJobArrange.setProcessStatus(JobDeployStatus.PROCESS_FAILED.getValue());
				jobArrangeService.updateJobArrange(dyJobArrange);
				deployStatus = false;
			}
		}
		return deployStatus;
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
