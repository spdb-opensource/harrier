package cn.spdb.harrier.api.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.spdb.harrier.api.model.TreeMenuNode;

/**
 * 菜单树工具.
 *
 */
@Component
public class TreeMenuCahece {

	private final Cache<List<TreeMenuNode>, List<TreeMenuNode>> menuCache;

	public TreeMenuCahece() {
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
	 * @param treeMenuNodes 节点列表
	 * @return 组织好的菜单树
	 */
	private List<TreeMenuNode> generateTreeByRecursion(List<TreeMenuNode> treeMenuNodes) {
		List<TreeMenuNode> result = new ArrayList<TreeMenuNode>();
		Iterator<TreeMenuNode> it = treeMenuNodes.iterator();
		TreeMenuNode treeMenuNode = null;
		while (it.hasNext()) {
			treeMenuNode = it.next();
			if ((null == treeMenuNode.getParentId()) || 0==(treeMenuNode.getParentId())) {
				result.add(treeMenuNode);
				treeMenuNode.setChildren(getTreeMenuNodeByParentId(treeMenuNode.getId(), treeMenuNodes));
			}
		}
		return result;
	}

	/**
	 * 根据传入的TreeMenuNode列表，组织树形结构.
	 * 
	 * @param nodeList 节点列表，传入节点要求按照展示顺序排好序，若使用菜单编码构建菜单，则需要按菜单编码和展示顺序依次排序
	 * @return 组织好的菜单树
	 */
	public List<TreeMenuNode> getMenuTree(List<TreeMenuNode> nodeList) {
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

		return new ArrayList<TreeMenuNode>();

	}

	/**
	 * 根据父ID递归构建树.
	 * 
	 * @param id            父节点ID
	 * @param treeMenuNodes 节点列表
	 * @return 组织好的节点
	 */
	private static List<TreeMenuNode> getTreeMenuNodeByParentId(Integer id, List<TreeMenuNode> treeMenuNodes) {
		Iterator<TreeMenuNode> it = treeMenuNodes.iterator();
		List<TreeMenuNode> list = new ArrayList<TreeMenuNode>();
		TreeMenuNode treeMenuNode = null;
		while (it.hasNext()) {
			treeMenuNode = it.next();
			if ((treeMenuNode.getParentId() != null) && treeMenuNode.getParentId().equals(id)) {
				list.add(treeMenuNode);
				treeMenuNode.setChildren(getTreeMenuNodeByParentId(treeMenuNode.getId(), treeMenuNodes));
			}
		}
		return list;
	}

}
