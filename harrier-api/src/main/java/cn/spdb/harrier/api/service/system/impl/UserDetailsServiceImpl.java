package cn.spdb.harrier.api.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.exceptions.ServiceException;
import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.service.job.UdsSystemService;
import cn.spdb.harrier.api.service.system.ISysRoleService;
import cn.spdb.harrier.api.service.system.ISysUserService;
import cn.spdb.harrier.api.service.system.SysPermissionService;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.MRole;
import cn.spdb.harrier.dao.entity.MUser;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private UdsSystemService systemService;

	@Override
	public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
		MUser user = userService.selectUserByUserCode(userCode);
		if (user == null) {
			throw new ServiceException("登录用户：" + userCode + " 不存在");
		}
		return createLoginUser(user);
	}

	public UserDetails createLoginUser(MUser user) {
		List<MRole> roles = sysRoleService.rolesByUserCode(user.getUserCode());
		LoginUser loginUser = new LoginUser(user);
		for (MRole mRole : roles) {
			if (mRole.getIsEnable()) {
				loginUser.addAuthoritie(mRole.getRoleName());
			}
		}
		if (user.getUserId() == 1 || loginUser.getAuthorities().stream()
				.anyMatch(predicate -> SysPermissionService.ROLE_ADMIN.equals(predicate.getAuthority()))) {
			systemService.selectAllPlatform().stream().forEach(action -> {
				StringBuilder sb = new StringBuilder().append(action.getPlatform()).append(Symbol.MAO_HAO)
						.append(action.getSystems()).append(Symbol.MAO_HAO).append("W");
				loginUser.addPermissions(sb.toString());

			});
		} else {
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

		return loginUser;
	}

}