package cn.spdb.harrier.api.controller.system;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.enums.Status;
import cn.spdb.harrier.api.model.LoginBody;
import cn.spdb.harrier.api.service.system.SysLoginService;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.uitls.StringUtils;

@RestController
@RequestMapping()
public class SysLoginController {

	@Autowired
	private SysLoginService loginService;

	/**
	 * 登录方法
	 * 
	 * @param loginBody 登录信息
	 * @return 结果
	 */
	@GetMapping("/login")
	@AccessLogAnnotation
	public Result login(String username,String password,String code, String uuid) {
		// 生成令牌
		String token = loginService.login(username, password, code, uuid);
		if (StringUtils.isBlank(token)) {
			return Result.error(Status.ACCESS_TOKEN_NOT_EXIST);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.TOKEN, token);
		return Result.success(map);
	}

}
