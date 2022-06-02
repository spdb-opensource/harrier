package cn.spdb.harrier.api.service.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.ISysRoleService;
import cn.spdb.harrier.dao.entity.MRole;
import cn.spdb.harrier.dao.entity.MRoleMenu;
import cn.spdb.harrier.dao.mapper.MRoleMapper;
import cn.spdb.harrier.dao.mapper.MRoleMenuMapper;

@Service
public class RoleServiceImpl implements ISysRoleService {

	@Autowired
	private MRoleMapper roleMapper;

	@Autowired
	private MRoleMenuMapper roleMenuMapper;

	@Override
	public List<MRole> rolesByUserid(Long userId) {
		return roleMapper.selectByUserId(userId);
	}

	@Override
	public int add(MRole role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public boolean update(MRole role) {
		int tmp = roleMapper.updateByPrimaryKey(role);
		return tmp > 0;
	}

	@Override
	public int delete(Integer[] ids) {
		return roleMapper.delete(ids);
	}

	@Override
	public int allotMenus(Integer roleId, Integer[] menuIds) {
		Collection<MRoleMenu> records = new ArrayList<MRoleMenu>();
		for (Integer menuId : menuIds) {
			MRoleMenu roleMenu = new MRoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(roleId);
			records.add(roleMenu);
		}
		return roleMenuMapper.insertMultiple(records);
	}

	@Override
	public Page<MRole> search(Page<MRole> page,String roleName) {
		roleMapper.search(page,roleName);
		return page;
	}

	@Override
	public List<MRole> rolesByUserCode(String userCode) {
		return roleMapper.selectByUsercode(userCode);
	}

	@Override
	public MRole load(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId).orElse(null);
	}

	@Override
	public int delete(Integer roleId) {
		return roleMenuMapper.deleteByRoleId(roleId);
	}

}
