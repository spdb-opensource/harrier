package cn.spdb.harrier.api.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.ISysUserService;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.dao.entity.MUser;
import cn.spdb.harrier.dao.entity.MUserSystems;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;

@RestController
@RequestMapping(path = "/user")
public class SysUserController {

	@Autowired
	private ISysUserService userInfoService;

	@PreAuthorize("hasAnyAuthority('system','admin')")
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
	public int delete(@PathVariable(value = "ids") Long[] userIds) throws Exception {
		return userInfoService.delete(userIds);
	}

	/**
	 * 添加用户角色关联.
	 */
	@RequestMapping(path = "{id}/role", method = RequestMethod.POST)
	public int addURRelation(@PathVariable(value = "id") Long userId, Integer[] roleIds) throws Exception {
		userInfoService.delete(userId);
		return userInfoService.addURRelation(userId, roleIds);
	}

	/**
	 * 查询用户下挂载的平台
	 */
	@RequestMapping(path = "{id}/platform/permiss", method = RequestMethod.GET)
	public List<UserSystemPermiss> selectUserPlatformPermiss(@PathVariable(value = "id") Long userId) throws Exception {
		return userInfoService.selectUserByUserId(userId);
	}
	@RequestMapping(path = "{id}/platform/permissId", method = RequestMethod.GET)
	public List<MUserSystems> selectUserPlatformPermissIds(@PathVariable(value = "id") Long userId) throws Exception {
		return userInfoService.selectUserByUserIds(userId);
	}

	@RequestMapping(path = "{id}/allot/permiss", method = RequestMethod.POST)
	public int allotPlatformPermiss(@PathVariable(value = "id") Long userId, @RequestBody List<MUserSystems> permissList) {
		userInfoService.deletePlatformPermiss(userId);
		for (MUserSystems permiss : permissList) {
			permiss.setUserId(userId);
		}
		return userInfoService.addPlatformPermiss(permissList);
	}
}
