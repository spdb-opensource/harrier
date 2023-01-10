package cn.spdb.harrier.api.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.dao.entity.MUser;
import cn.spdb.harrier.dao.entity.MUserSystems;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;

public interface ISysUserService {

	Boolean checkUserNameUnique(String username);

	boolean registerUser(MUser sysUser);

	MUser selectUserByUserCode(String username);

	MUser loadByUserCode(String userName);

	Page<MUser> search(Page<MUser> page, String userCode, Boolean isEnbale);

	boolean exists(Long userId);

	MUser loadById(Long userId);

	MUser loadByUsercode(String usercode);

	int delete(Long[] userIds);

	boolean update(MUser userinfo);

	Integer addURRelation(Long userId, Integer[] roles);

	List<MUser> selectByUserName(String userName);

	List<UserSystemPermiss> selectUserByUserId(Long id);
	
	void deletePlatformPermiss(Long userId);

	int addPlatformPermiss(List<MUserSystems> permissList);

	List<MUserSystems> selectUserByUserIds(Long userId);

	boolean insert(MUser userinfo);
	
	int delete(Long userId);
	
	String updatePwd(LoginUser loginUser, String oldPassword, String newPassword);

	MUser selectUserToCode(String userCode);

	String getvaricode(String userCode);

	String forgotPwd(Long userId, String userCode, String newPassword, String code);
	
	Map<String, Object> captchImage(String uuid);
	
	int updateEnable(Long[] userIds, Boolean isEnable);
	
	int resetPwd(Long[] userIds);

}
