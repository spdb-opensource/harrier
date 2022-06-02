package cn.spdb.harrier.api.service.system;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;

@Service
public class SysLoginService {
	@Autowired
	private SysTokenService tokenService;

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private HarrierCache cache;

	@Autowired
	private ISysConfigService configService;

	/**
	 * 登录验证
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public String login(String username, String password, String code, String uuid) {
		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 验证码开关
		if (captchaOnOff) {
			if (!validateCaptcha(username, code, uuid)) {
				return null;
			}
		}
		// 用户验证
		Authentication authentication = null;
		// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "";
		}

		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		// 生成token
		return tokenService.createToken(loginUser);
	}

	/**
	 * 校验验证码
	 * 
	 * @param username 用户名
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public Boolean validateCaptcha(String username, String code, String uuid) {
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		String captcha = cache.get(verifyKey);
		cache.delete(verifyKey);
		if (code.equalsIgnoreCase(captcha)) {
			return true;
		}
		return false;
	}

}
