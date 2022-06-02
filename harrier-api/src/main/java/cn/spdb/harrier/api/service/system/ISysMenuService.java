package cn.spdb.harrier.api.service.system;

import java.util.List;

import cn.spdb.harrier.api.model.TreeMenuNode;
import cn.spdb.harrier.dao.entity.MMenu;

/**
 * 菜单与请求服务.
 */
public interface ISysMenuService {

	/**
	 * 获取所有菜单
	 * 
	 * @return 菜单列表
	 */
	List<MMenu> list();

	/**
	 * 获取所有资源
	 * 
	 * @return 树形菜单列表
	 */
	List<TreeMenuNode> menuTree();

	/**
	 * 加载菜单
	 * 
	 * @param menuId 菜单ID
	 * @return 菜单列表
	 */
	MMenu load(Integer menuId);

	/**
	 * 删除菜单.
	 * 
	 * @param menuIds 菜单的IDs
	 * @return 标志
	 */
	int delete(Integer[] menuIds);

	/**
	 * 修改菜单.
	 * 
	 * @param funcInfo 菜单实体
	 * @return 条数
	 */
	boolean update(MMenu funcInfo);

	/**
	 * 通过登录名获得菜单.
	 * 
	 * @param loginName 登录名
	 * @return 菜单列表
	 */
	List<MMenu> menusByLoginName(String loginName);

	/**
	 * 通过单个角色ID获得菜单.
	 * 
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<MMenu> menusByRoleid(Integer roleId);

	/**
	 * 通过某个菜单ID下的子菜单.
	 * 
	 * @param menuId 菜单ID
	 * @return 菜单列表
	 */
	List<MMenu> menuchildrens(Integer menuId);

	/**
	 * 添加根菜单
	 * 
	 * @param funcInfo 菜单
	 */
	void addRootMenu(MMenu funcInfo);


	/**
	 * 菜单转树.
	 * 
	 * @param menuList 菜单信息
	 * @return 树形菜单节点列表
	 */
	public List<TreeMenuNode> menus2Tree(List<MMenu> menuList);

	List<MMenu> menusByUserId(Long userId);

}
