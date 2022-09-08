package cn.spdb.harrier.api.service.system;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.utils.ObjectUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.MRole;
import cn.spdb.harrier.dao.entity.MUserRole;
import cn.spdb.harrier.dao.mapper.CustomSqlMapper;
import cn.spdb.harrier.dao.mapper.MRoleDynamicSqlSupport;
import cn.spdb.harrier.dao.mapper.MRoleMapper;
import cn.spdb.harrier.dao.mapper.MUserRoleDynamicSqlSupport;
import cn.spdb.harrier.dao.mapper.MUserRoleMapper;

/**
 * @PreAuthorize("@HarrierPermission.hasPermissions(#platfrom,*,'R')")
 * 
 * @author luzl2
 *
 */
@Service("HarrierPermission")
public class SysPermissionService {

	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_PLATFORM = "platform";
	public static final String ROLE_SYSTEMS = "systems";

	@Autowired
	private MRoleMapper mRoleMapper;
	@Autowired
	private MUserRoleMapper mUserRoleMapper;
	@PostConstruct
	private void cteatRole() {
		List<MRole> list=mRoleMapper.select(c->c.where(MRoleDynamicSqlSupport.roleId,SqlBuilder.isIn(1,2,3)));
		if(list.size()<3) {
			mRoleMapper.delete(new Integer[] {1,2,3});
			mRoleMapper.insertSelective(new MRole(1,"admin",true));
			mRoleMapper.insertSelective(new MRole(2,"platform",true));
			mRoleMapper.insertSelective(new MRole(3,"systems",true));
		}
		List<MUserRole> listR = mUserRoleMapper.select(c->c.where(MUserRoleDynamicSqlSupport.roleId,SqlBuilder.isEqualTo(1)).and(MUserRoleDynamicSqlSupport.userId,SqlBuilder.isEqualTo(1L)));
		if(ObjectUtils.isEmpty(listR)) {
			mUserRoleMapper.insertSelective(new MUserRole(1L,1));
		}
	}

	public boolean hasPermissions(String platfrom, String system, String permission) {
		if (SecurityUtils.isAdmin()) {
			return true;
		}
		if (StringUtils.isBlank(platfrom) || StringUtils.isBlank(system) || StringUtils.isBlank(permission)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (null == loginUser || CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		StringBuilder sb = new StringBuilder().append(platfrom).append(Symbol.MAO_HAO).append(Symbol.XING_HAO)
				.append(Symbol.MAO_HAO).append(permission);
		boolean flage = hasPermissions(loginUser.getPermissions(), sb.toString());
		if (flage) {
			return flage;
		}
		sb.setLength(0);
		sb.append(platfrom).append(Symbol.MAO_HAO).append(system).append(Symbol.MAO_HAO).append(permission);
		flage = hasPermissions(loginUser.getPermissions(), sb.toString());
		return flage;
	}

	private boolean hasPermissions(Set<String> permissions, String permission) {
		return permissions.contains(permission.trim());
	}

	public boolean hasPermissionsRole(String role) {
		if (StringUtils.isBlank(role)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (null == loginUser || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}

		if (SecurityUtils.isAdmin()) {
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @param roles , 分割
	 * @return
	 */
	public boolean hasAnyRoles(String roles) {
		if (SecurityUtils.isAdmin()) {
			return true;
		}
		if (StringUtils.isBlank(roles)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (null == loginUser || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}

		for (String role : roles.split(Symbol.DOU_HAO)) {
			for (GrantedAuthority ga : loginUser.getAuthorities()) {
				if (ga.getAuthority().equals(role)) {
					return true;
				}
			}
		}
		return false;
	}
}
