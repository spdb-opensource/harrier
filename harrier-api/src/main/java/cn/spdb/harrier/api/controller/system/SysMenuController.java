package cn.spdb.harrier.api.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.interceptor.RepeatSubmit;
import cn.spdb.harrier.api.model.TreeMenuNode;
import cn.spdb.harrier.api.service.system.ISysMenuService;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.dao.entity.MMenu;

@RestController
@RequestMapping(path = "/menu")
public class SysMenuController {

	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 获得所有的菜单.
	 */
	@RepeatSubmit
	@RequestMapping(method = RequestMethod.GET)
	public List<TreeMenuNode> menus() {
		List<TreeMenuNode> list = sysMenuService.menuTree();
		return list;
	}

	/**
	 * 加载指定菜单列表
	 * 
	 * @param id 菜单ID
	 */
	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public MMenu loadMenu(@PathVariable(value = "id") Integer menuId) throws Exception {
		return sysMenuService.load(menuId);
	}

	@RequestMapping(path = "user/tree", method = RequestMethod.GET)
	public List<TreeMenuNode> menus(HttpServletRequest request) throws Exception {
		String userName = SecurityUtils.getUsername();
		List<MMenu> roleList = sysMenuService.menusByLoginName(userName);
		List<TreeMenuNode> menuTrees = sysMenuService.menus2Tree(roleList);
		return menuTrees;
	}

	/**
	 * 加载指定菜单列表
	 * 
	 * @param id 菜单ID
	 */
	@RequestMapping(path = "{id}/children", method = RequestMethod.GET)
	public List<MMenu> loadMenuChildren(@PathVariable(value = "id") Integer menuId) throws Exception {
		if (null == menuId) {
			menuId = 0;
		}
		return sysMenuService.menuchildrens(menuId);
	}

	/**
	 * 增加菜单
	 * 
	 * @param funcInfo 菜单
	 */
	@RequestMapping(path = "add",method = RequestMethod.POST)
	public boolean add(MMenu funcInfo, HttpServletRequest request) throws Exception {
		String userName = SecurityUtils.getUsername();
		funcInfo.setCreateUser(userName);
		funcInfo.setUpdateUser(userName);
		sysMenuService.addRootMenu(funcInfo);
		return true;
	}

	@RequestMapping(path = "{id}/update", method = RequestMethod.PUT)
	public boolean update(@PathVariable(value = "id") String resourceId, MMenu funcInfo, HttpServletRequest request)
			throws Exception {
		String userName = SecurityUtils.getUsername();
		funcInfo.setUpdateUser(userName);
		return sysMenuService.update(funcInfo);
	}

	@RequestMapping(path = "{ids}", method = RequestMethod.DELETE)
	public int delete(@PathVariable(value = "ids") Integer[] menuIds) throws Exception {
		int count = sysMenuService.delete(menuIds);
		return count;
	}
}
