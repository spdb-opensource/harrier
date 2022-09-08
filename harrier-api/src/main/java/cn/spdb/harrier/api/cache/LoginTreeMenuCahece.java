package cn.spdb.harrier.api.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.spdb.harrier.api.model.LoginMenu;


/**
 * 菜单树工具.
 *
 */
@Component
public class LoginTreeMenuCahece {

	private final Cache<List<LoginMenu>, List<LoginMenu>> menuCache;

	public LoginTreeMenuCahece() {
		this.menuCache = CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors())
				.initialCapacity(100).maximumSize(10000).expireAfterAccess(10, TimeUnit.MINUTES).build();
	}

	/**
	 * 清除菜单缓存.
	 */
	public void clearMenuCache() {
		menuCache.cleanUp();
	}

	/**
	 * 查找根节点开始构建树.
	 * 
	 * @param loginMenus 节点列表
	 * @return 组织好的菜单树
	 */
	private List<LoginMenu> generateTreeByRecursion(List<LoginMenu> loginMenus) {
		List<LoginMenu> result = new ArrayList<LoginMenu>();
		Iterator<LoginMenu> it = loginMenus.iterator();
		LoginMenu loginMenu = null;
		while (it.hasNext()) {
			loginMenu = it.next();
			if ((null == loginMenu.getParentId()) || 0==(loginMenu.getParentId())) {
				result.add(loginMenu);
				loginMenu.setChildren(getLoginMenuByParentId(loginMenu.getId(), loginMenus));
			}
		}
		return result;
	}

	/**
	 * 根据传入的LoginMenu列表，组织树形结构.
	 * 
	 * @param nodeList 节点列表，传入节点要求按照展示顺序排好序，若使用菜单编码构建菜单，则需要按菜单编码和展示顺序依次排序
	 * @return 组织好的菜单树
	 */
	public List<LoginMenu> getMenuTree(List<LoginMenu> nodeList) {
		if ((nodeList == null) || (nodeList.size() == 0)) {
			return nodeList;
		}

		try {
			return menuCache.get(nodeList, () -> {
				return generateTreeByRecursion(nodeList);
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ArrayList<LoginMenu>();

	}

	/**
	 * 根据父ID递归构建树.
	 * 
	 * @param id            父节点ID
	 * @param loginMenus 节点列表
	 * @return 组织好的节点
	 */
	private static List<LoginMenu> getLoginMenuByParentId(Integer id, List<LoginMenu> loginMenus) {
		Iterator<LoginMenu> it = loginMenus.iterator();
		List<LoginMenu> list = new ArrayList<LoginMenu>();
		LoginMenu loginMenu = null;
		while (it.hasNext()) {
			loginMenu = it.next();
			if ((loginMenu.getParentId() != null) && loginMenu.getParentId().equals(id)) {
				list.add(loginMenu);
				loginMenu.setChildren(getLoginMenuByParentId(loginMenu.getId(), loginMenus));
			}
		}
		return list;
	}

}
