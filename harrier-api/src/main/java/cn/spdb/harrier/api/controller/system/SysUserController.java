package cn.spdb.harrier.api.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.service.job.UdsSystemService;
import cn.spdb.harrier.api.service.system.ISysUserService;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.enmus.Message;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.MUser;
import cn.spdb.harrier.dao.entity.MUserSystems;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.service.email.EmailOperator;

@RestController
@RequestMapping(path = "/user")
public class SysUserController {

	@Autowired
	private ISysUserService userInfoService;
	@Autowired
	private ISysUserService userService;

	@PreAuthorize("@HarrierPermission.hasAnyRoles('admin')")
	@RequestMapping(method = RequestMethod.GET)
	public Page<MUser> list(Page<MUser> page, String userCode, Boolean isEnable) throws Exception {
		Page<MUser> result = userInfoService.search(page, userCode, isEnable);
		return result;
	}

	/**
	 * 判断是否存在
	 */
	@RequestMapping(path = "{id}/exists", method = RequestMethod.GET)
	public boolean exists(@PathVariable(value = "id") Long userId) throws Exception {
		return userInfoService.exists(userId);
	}

	/**
	 * 判断是否存在
	 */
	@RequestMapping(path = "{usercode}/codeExists", method = RequestMethod.GET)
	public boolean exists(@PathVariable(value = "usercode") String usercode) throws Exception {
		MUser userInfo = userInfoService.loadByUsercode(usercode);
		return ObjectUtils.isNotEmpty(userInfo);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public MUser load(@PathVariable(value = "id") Long userId) throws Exception {
		MUser userInfo = userInfoService.loadById(userId);
		return userInfo;
	}

	/**
	 * 加载用户
	 */
	@RequestMapping(path = "loadByUserCode", method = RequestMethod.GET)
	public MUser loadByUserCode(String usercode) throws Exception {
		MUser userInfo = userInfoService.loadByUsercode(usercode);
		return userInfo;
	}

	@RequestMapping(path = "loadByUserName", method = RequestMethod.GET)
	public List<MUser> selectByUserName(String userName) throws Exception {
		List<MUser> userInfo = userInfoService.selectByUserName(userName);
		return userInfo;
	}

	/**
	 * 修改用户.
	 * 
	 * @param userinfo 用户信息
	 */
	@RequestMapping(path = "{id}/update", method = RequestMethod.PUT)
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "request" })
	public boolean update(HttpServletRequest request, @PathVariable(value = "id") Long userId, MUser userinfo)
			throws Exception {
		String userName = SecurityUtils.getUsername();
		userinfo.setUpdateUser(userName);
		return userInfoService.update(userinfo);
	}

	/**
	 * 新增用户.
	 * 
	 * @param userinfo 用户信息
	 */
	@RequestMapping(path = "add", method = RequestMethod.POST)
	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "request" })
	public boolean add(HttpServletRequest request, MUser userinfo) throws Exception {
		String userName = SecurityUtils.getUsername();
		userinfo.setUpdateUser(userName);
		userinfo.setCreateUser(userName);
		userinfo.setUserPwd(SecurityUtils.encryptPassword(userinfo.getUserPwd()));
		return userInfoService.insert(userinfo);
	}

	/**
	 * 用户删除.
	 */
	@RequestMapping(path = "{ids}/delete", method = RequestMethod.DELETE)
	@AccessLogAnnotation(isDbInstall = true)
	public int delete(@PathVariable(value = "ids") Long[] userIds) throws Exception {
		return userInfoService.delete(userIds);
	}

	/**
	 * 添加用户角色关联.
	 */
	@RequestMapping(path = "{id}/role", method = RequestMethod.POST)
	@AccessLogAnnotation(isDbInstall = true)
	public int addURRelation(@PathVariable(value = "id") Long userId, Integer[] roleIds) throws Exception {
		userInfoService.delete(userId);
		return userInfoService.addURRelation(userId, roleIds);
	}

	/**
	 * 查询用户下挂载的平台
	 */
	@RequestMapping(path = "{id}/platform/permiss", method = RequestMethod.GET)
	public List<UserSystemPermiss> selectUserPlatformPermiss(@PathVariable(value = "id") Long userId) throws Exception {
		List<UserSystemPermiss> list = new ArrayList<UserSystemPermiss>();
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (userId == loginUser.getUserId()) {
			loginUser.getPermissions().forEach(action -> {
				String[] str = action.split(Symbol.MAO_HAO);
				if (str.length > 2) {
					UserSystemPermiss permiss = new UserSystemPermiss();
					permiss.setUserId(userId);
					permiss.setPlatform(str[0]);
					permiss.setSystems(str[1]);
					permiss.setPermissions(str[2]);
					list.add(permiss);
				}
			});
		} else {
			list.addAll(userInfoService.selectUserByUserId(userId));
		}
		return list;
	}

	@RequestMapping(path = "{id}/platform/permissId", method = RequestMethod.GET)
	public List<MUserSystems> selectUserPlatformPermissIds(@PathVariable(value = "id") Long userId) throws Exception {
		return userInfoService.selectUserByUserIds(userId);
	}

	@RequestMapping(path = "{id}/allot/permiss", method = RequestMethod.POST)
	@AccessLogAnnotation
	public int allotPlatformPermiss(@PathVariable(value = "id") Long userId,
			@RequestBody List<MUserSystems> permissList) {
		userInfoService.deletePlatformPermiss(userId);
		for (MUserSystems permiss : permissList) {
			permiss.setUserId(userId);
		}
		int num = userInfoService.addPlatformPermiss(permissList);
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (userId == loginUser.getUserId()) {
			List<UserSystemPermiss> list = userService.selectUserByUserId(loginUser.getUserId());
			for (UserSystemPermiss usp : list) {
				if (StringUtils.isBlank(usp.getPlatform()) || StringUtils.isBlank(usp.getSystems())
						|| StringUtils.isBlank(usp.getPermissions())) {
					continue;
				}
				StringBuilder sb = new StringBuilder().append(usp.getPlatform()).append(Symbol.MAO_HAO)
						.append(usp.getSystems()).append(Symbol.MAO_HAO).append(usp.getPermissions());
				loginUser.addPermissions(sb.toString());
			}
		}
		return num;
	}

	/**
	 * 修改密码.
	 * 
	 */
	@RequestMapping(path = "/updatePwd", method = RequestMethod.PUT)
	@AccessLogAnnotation(ignoreRequestArgs = { "request" })
	public String updatePwd(HttpServletRequest request, String oldPassword, String newPassword) throws Exception {
		return userInfoService.updatePwd(SecurityUtils.getLoginUser(), oldPassword, newPassword);
	}

	/**
	 * 查询用户信息.
	 * 
	 */
	@GetMapping("/selectUserToCode")
	public MUser selectUserToCode(String userCode) throws Exception {
		MUser userInfo = userInfoService.selectUserToCode(userCode);
		return userInfo;
	}

	/**
	 * 发送验证码.
	 * 
	 */
	@PostMapping("/sendvaricode")
	public boolean sendvaricode(String userCode, String userEmail) {
		try {
			String verificationCode = userInfoService.getvaricode(userCode);
			BeanContext.getBean(EmailOperator.class).sendEmailMine(Arrays.asList(userEmail), null,
					Message.FORGOT_PWD_EMAIL_TITLE.getMsg("zh"),
					String.format(Message.FORGOT_PWD_EMAIL_CONTENT.getMsg("zh"), verificationCode), null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取图片验证码
	 * 
	 * @return 结果
	 */
	@GetMapping("/captchImage")
	@AccessLogAnnotation
	public Result captchImage(String uuid) {
		// 生成验证码图片
		Map<String, Object> map = userInfoService.captchImage(uuid);
		return Result.success(map);
	}

	/**
	 * 发送注册验证码邮件
	 * 
	 */
	@PostMapping("/sendRegistervaricode")
	public boolean sendRegistervaricode(String userCode, String userEmail) {
		try {
			String verificationCode = userInfoService.getvaricode(userEmail);
			BeanContext.getBean(EmailOperator.class).sendEmailMine(Arrays.asList(userEmail), null,
					Message.REGISTER_USER_EMAIL_TITLE.getMsg("zh"),
					String.format(Message.REGISTER_USER_EMAIL_CONTENT.getMsg("zh"), verificationCode), null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改密码.
	 * 
	 */
	@PostMapping("/forgotPwd")
	@AccessLogAnnotation
	public String forgotPwd(Long userId, String userCode, String newPassword, String code) throws Exception {
		return userInfoService.forgotPwd(userId, userCode, newPassword, code);
	}

	/**
	 * 更新用户状态.
	 */
	@PostMapping(path = "{ids}/updateEnable")
	@AccessLogAnnotation(isDbInstall = true)
	public int updateEnable(@PathVariable(value = "ids") Long[] userIds, Boolean isEnable) throws Exception {
		return userInfoService.updateEnable(userIds, isEnable);
	}

	/**
	 * 重置密码.
	 * 
	 */
	@PostMapping("/resetPwd")
	@AccessLogAnnotation
	public int resetPwd(Long[] userIds) throws Exception {
		return userInfoService.resetPwd(userIds);
	}
}
