package cn.spdb.harrier.api.controller.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.enums.Status;
import cn.spdb.harrier.api.model.RegisterBody;
import cn.spdb.harrier.api.service.system.ISysConfigService;
import cn.spdb.harrier.api.service.system.SysRegisterService;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.StringUtils;
import net.bytebuddy.asm.Advice.Return;

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
	@AccessLogAnnotation
	public Result register(@RequestBody RegisterBody user) {
		if (!("true".equals(configService.selectConfigByKey(Constants.REGISTER_SWITCH)))) {
			return Result.error(Status.REGISTER_NOT_OPEN);
		}
		Map<String,Object> registerResult = registerService.register(user);
		return Result.success(registerResult);
	}
	
	@GetMapping("/register/selectCaptchaOnOff") 
	public Result selectCaptchaOnOff() {
		boolean result = configService.selectCaptchaOnOff(Constants.REGIST_CAPTCHA_SWITCH);
		return Result.success(result);
	}
}
