package cn.spdb.harrier.api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.spdb.harrier.api.exceptions.ServiceException;
import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.service.system.SysPermissionService;

/**
 * 安全服务工具类
 * 
 * @author ruoyi
 */
public class SecurityUtils {
	/**
	 * 用户ID
	 **/
	public static Long getUserId() {
		try {
			return getLoginUser().getUserId();
		} catch (Exception e) {
			throw new ServiceException(1, "获取用户ID异常");
		}
	}

	/**
	 * 获取用户账户
	 **/
	public static String getUsername() {
		try {
			return getLoginUser().getUsername();
		} catch (Exception e) {
			throw new ServiceException(1, "获取用户异常");
		}
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new ServiceException(1, "获取用户信息异常");
		}
	}

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 生成BCryptPasswordEncoder密码
	 *
	 * @param password 密码
	 * @return 加密字符串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 判断密码是否相同
	 *
	 * @param rawPassword     真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 是否为管理员
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	public static boolean isAdmin() {

		for (GrantedAuthority ga : getLoginUser().getAuthorities()) {
			if (ga.getAuthority().equals(SysPermissionService.ROLE_ADMIN)) {
				return true;
			}
		}
		return isAdmin(getLoginUser().getUserId());
	}
}
