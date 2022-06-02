package cn.spdb.harrier.api.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.ISysMenuService;
import cn.spdb.harrier.api.service.system.ISysRoleService;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.dao.entity.MMenu;
import cn.spdb.harrier.dao.entity.MRole;

@RestController
@RequestMapping(path = "/role")
public class SysRoleController {

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 加载角色列表.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Page<MRole> list(Page<MRole> page, String roleName) throws Exception {
		sysRoleService.search(page, roleName);
		return page;
	}

	/**
	 * 加载角色.
	 */
	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public MRole load(@PathVariable(value = "id") Integer roleId) throws Exception {
		return sysRoleService.load(roleId);
	}

	/**
	 * 加载角色下拉选项
	 */
	@RequestMapping(path = "{userCode}/list", method = RequestMethod.GET)
	public List<MRole> loadRoleList(HttpServletRequest request, @PathVariable(value = "userCode") String userCode) {
		List<MRole> list = sysRoleService.rolesByUserCode(userCode);
		return list;
	}

	/**
	 * 添加角色.
	 * 
	 * @param role 角色
	 */
	@RequestMapping(path = "addRole", method = RequestMethod.POST)
	public int addRole(HttpServletRequest request, MRole role) throws Exception {
		String userName = SecurityUtils.getUsername();
		role.setCreateUser(userName);
		role.setUpdateUser(userName);
		return sysRoleService.add(role);
	}

	/**
	 * 更新角色.
	 * 
	 * @param role 角色
	 */
	@RequestMapping(path = "{id}/update", method = RequestMethod.PUT)
	public boolean update(HttpServletRequest request, @PathVariable(value = "id") String roleId, MRole role)
			throws Exception {
		String userName = SecurityUtils.getUsername();
		role.setUpdateUser(userName);
		return sysRoleService.update(role);
	}

	/**
	 * 删除角色.
	 */
	@RequestMapping(path = "{ids}", method = RequestMethod.DELETE)
	public int delete(@PathVariable(value = "ids") Integer[] ids) throws Exception {
		return sysRoleService.delete(ids);
	}

	/**
	 * 查询角色下挂载的菜单.
	 */
	@RequestMapping(path = "{id}/slectMenus", method = RequestMethod.GET)
	public List<MMenu> roleMenus(@PathVariable(value = "id") Integer roleId) throws Exception {
		return sysMenuService.menusByRoleid(roleId);
	}

	/**
	 * 给角色分配菜单.
	 */
	@RequestMapping(path = "{id}/allotMenus", method = RequestMethod.POST)
	public int allotMenus(@PathVariable(value = "id") Integer roleId, Integer[] menuIds) throws Exception {
		sysRoleService.delete(roleId);
		return sysRoleService.allotMenus(roleId, menuIds);
	}

}
