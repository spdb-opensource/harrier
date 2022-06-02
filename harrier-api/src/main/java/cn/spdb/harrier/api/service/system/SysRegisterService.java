package cn.spdb.harrier.api.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.model.RegisterBody;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.uitls.StringUtils;
import cn.spdb.harrier.dao.entity.MUser;

@Service
public class SysRegisterService {

	private static final int USERNAME_MIN_LENGTH = 2;

	private static final int USERNAME_MAX_LENGTH = 20;

	private static final int PASSWORD_MIN_LENGTH = 5;

	private static final int PASSWORD_MAX_LENGTH = 20;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private HarrierCache cache;


	/**
	 * 注册
	 */
	public String register(RegisterBody registerBody) {
		String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 验证码开关
		if (captchaOnOff) {
			validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
		}

		if (StringUtils.isEmpty(username)) {
			msg = "用户名不能为空";
		} else if (StringUtils.isEmpty(password)) {
			msg = "用户密码不能为空";
		} else if (username.length() < USERNAME_MIN_LENGTH || username.length() > USERNAME_MAX_LENGTH) {
			msg = "账户长度必须在2到20个字符之间";
		} else if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
			msg = "密码长度必须在5到20个字符之间";
		} else if (userService.checkUserNameUnique(username)) {
			msg = "保存用户'" + username + "'失败，注册账号已存在";
		} else {
			MUser sysUser = new MUser();
			sysUser.setUserCode(username);
			sysUser.setUpdateUser(username);
			sysUser.setCreateUser(username);
			sysUser.setUserPwd(SecurityUtils.encryptPassword(registerBody.getPassword()));
			sysUser.setIsEnable(true);
			boolean regFlag = userService.registerUser(sysUser);
			if (!regFlag) {
				msg = "注册失败,请联系系统管理人员";
			} else {
				msg = "";
			}
		}
		return msg;
	}

	/**
	 * 校验验证码
	 * 
	 * @param username 用户名
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public void validateCaptcha(String username, String code, String uuid) {
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		String captcha = cache.get(verifyKey);
		cache.delete(verifyKey);
		if (captcha == null) {
			// FIXME
		}
		if (!code.equalsIgnoreCase(captcha)) {
			// FIXME check fire
		}
	}
}
