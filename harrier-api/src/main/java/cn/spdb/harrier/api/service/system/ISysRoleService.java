package cn.spdb.harrier.api.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.dao.entity.MRole;

public interface ISysRoleService {

	List<MRole> rolesByUserid(Long userId);
	
	List<MRole> rolesByUserCode(String userCode);

	Page<MRole> search(Page<MRole> page,String roleName);

	MRole load(Integer roleId);

	int add(MRole role);

	boolean update(MRole role);

	int delete(Integer roleId);

	int allotMenus(Integer roleId, Integer[] menuIds);

	int delete(Integer[] ids);
}
