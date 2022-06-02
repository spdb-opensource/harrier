package cn.spdb.harrier.api.service.system.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.cache.TreeMenuCahece;
import cn.spdb.harrier.api.model.TreeMenuNode;
import cn.spdb.harrier.api.model.TreeMenuNodeAttribute;
import cn.spdb.harrier.api.service.system.ISysMenuService;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.dao.entity.MMenu;
import cn.spdb.harrier.dao.mapper.MMenuMapper;

/**
 * 菜单与请求服务实现类.
 *
 */
@Service
public class MenuServiceImpl implements ISysMenuService {

	@Autowired
	private MMenuMapper menuMapper;

	@Autowired
	private TreeMenuCahece menuCahece;

	@Override
	public List<MMenu> list() {
		return menuMapper.select();
	}

	@Override
	public List<TreeMenuNode> menuTree() {
		List<MMenu> menus = menuMapper.select();
		return menus2Tree(menus);
	}

	@Override
	public MMenu load(Integer menuId) {
		return menuMapper.selectByPrimaryKey(menuId).orElse(null);
	}

	@Override
	public int delete(Integer[] menuIds) {
		int tmp = menuMapper.deletById(menuIds);
		if (tmp > 0) {
			menuCahece.clearMenuCache();
		}
		return tmp;
	}

	@Override
	public boolean update(MMenu funcInfo) {
		menuMapper.updateByPrimaryKey(funcInfo);
		menuCahece.clearMenuCache();
		return true;
	}

	@Override
	public List<MMenu> menusByLoginName(String usercode) {
		return menuMapper.selectByUsercode(usercode);
	}

	@Override
	public List<MMenu> menusByUserId(Long userId) {
		return menuMapper.selectByUserid(userId);
	}

	@Override
	public List<MMenu> menusByRoleid(Integer roleId) {
		return menuMapper.selectByRoleId(roleId);
	}

	@Override
	public List<MMenu> menuchildrens(Integer menuId) {
		return menuMapper.menuchildrens(menuId);
	}

	@Override
	public void addRootMenu(MMenu funcInfo) {
		int tmp = menuMapper.insertSelective(funcInfo);
		if (tmp > 0) {
			menuCahece.clearMenuCache();
		}
	}

	@Override
	public List<TreeMenuNode> menus2Tree(List<MMenu> menuList) {
		List<TreeMenuNode> result = new LinkedList<TreeMenuNode>();
		List<TreeMenuNode> nodeList = new ArrayList<TreeMenuNode>();
		Iterator<MMenu> it = menuList.iterator();
		while (it.hasNext()) {
			MMenu menu = it.next();
			TreeMenuNode tn = new TreeMenuNode();
			tn.setId(menu.getMenuId());
			tn.setParentId(menu.getParentMenuId());
			tn.setText(menu.getMenuName());
			tn.setMenuRankNo(menu.getMenuNo());
			TreeMenuNodeAttribute tna = new TreeMenuNodeAttribute();
			tna.setUrl(menu.getMenuUrl());
			tna.setIcon(menu.getMenuIconUrl());
			tn.setAttributes(tna);
			nodeList.add(tn);
		}
		result = menuCahece.getMenuTree(nodeList);
		sortMenuRank(result);
		return result;
	}

	private void sortMenuRank(List<TreeMenuNode> treeMenuNodes) {
		sort(treeMenuNodes);
		Iterator<TreeMenuNode> it = treeMenuNodes.iterator();
		TreeMenuNode treeMenuNode = null;
		while (it.hasNext()) {
			treeMenuNode = it.next();
			if ((null != treeMenuNode.getChildren()) || treeMenuNode.getChildren().size() > 0) {
				sortMenuRank(treeMenuNode.getChildren());
			}
		}
	}

	private void sort(List<TreeMenuNode> treeMenuNodes) {
		treeMenuNodes.sort(new Comparator<TreeMenuNode>() {
			@Override
			public int compare(TreeMenuNode node1, TreeMenuNode node2) {
				if (node1.getMenuRankNo() == null || node2.getMenuRankNo() == null) {
					return -1;
				}
				return node1.getMenuRankNo() - node2.getMenuRankNo();
			}
		});
	}

}
