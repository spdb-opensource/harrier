package cn.spdb.harrier.api.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.enums.Status;
import cn.spdb.harrier.api.model.RegisterBody;
import cn.spdb.harrier.api.service.system.ISysConfigService;
import cn.spdb.harrier.api.service.system.SysRegisterService;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.common.uitls.StringUtils;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController {
	@Autowired
	private SysRegisterService registerService;

	@Autowired
	private ISysConfigService configService;

	@PostMapping("/register")
	public Result register(@RequestBody RegisterBody user) {
		if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
			return Result.error(Status.REGISTER_NOT_OPEN);
		}
		String msg = registerService.register(user);
		return StringUtils.isEmpty(msg) ? Result.success(null) : Result.error(Status.REGISTER_NOT_OPEN);
	}
}
