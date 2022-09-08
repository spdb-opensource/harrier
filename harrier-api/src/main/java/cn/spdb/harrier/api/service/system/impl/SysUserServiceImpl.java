package cn.spdb.harrier.api.service.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.service.system.ISysUserService;
import cn.spdb.harrier.api.utils.ImageUtils;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.dao.entity.MUser;
import cn.spdb.harrier.dao.entity.MUserRole;
import cn.spdb.harrier.dao.entity.MUserSystems;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;
import cn.spdb.harrier.dao.mapper.MUserMapper;
import cn.spdb.harrier.dao.mapper.MUserRoleMapper;
import cn.spdb.harrier.dao.mapper.MUserSystemsMapper;

@Service
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private MUserMapper userMapper;

	@Autowired
	private MUserSystemsMapper userSystemsMapper;

	@Autowired
	private MUserRoleMapper userRoleMapper;
	
	@Autowired
	private HarrierCache cache;
	
	@Override
	public Boolean checkUserNameUnique(String username) {
		return userMapper.selectByUserCode(username).isPresent();
	}

	@Override
	public boolean registerUser(MUser sysUser) {
		return userMapper.insertSelective(sysUser) > 0;
	}

	@Override
	public MUser selectUserByUserCode(String username) {
		return userMapper.selectByUserCode(username).orElse(null);
	}

	@Override
	public List<UserSystemPermiss> selectUserByUserId(Long id) {
		return userSystemsMapper.selectManyUserPeres(id);
	}

	@Override
	public MUser loadByUserCode(String usercode) {
		return userMapper.selectByUserCode(usercode).get();
	}

	@Override
	public Page<MUser> search(Page<MUser> page, String userName, Boolean isEnbale) {

		return userMapper.search(page, userName, isEnbale);
	}

	@Override
	public boolean exists(Long userId) {
		return userMapper.selectByPrimaryKey(userId).isPresent();
	}

	@Override
	public MUser loadById(Long userId) {
		return userMapper.selectByPrimaryKey(userId).orElse(null);

	}

	@Override
	public MUser loadByUsercode(String userCode) {
		return userMapper.selectByUserCode(userCode).orElse(null);
	}

	@Override
	public int delete(Long[] userIds) {
		return userMapper.delete(userIds);
	}

	@Override
	public boolean update(MUser userinfo) {
		String pwd = userinfo.getUserPwd();
		pwd = StringUtils.isEmpty(pwd) ? null : SecurityUtils.encryptPassword(userinfo.getUserPwd());
		return userMapper.updateByPrimaryKeySelective(userinfo) > 0;
	}

	@Override
	public Integer addURRelation(Long userId, Integer[] roles) {
		Collection<MUserRole> records = new ArrayList<MUserRole>();
		for (Integer roleId : roles) {
			MUserRole muserRole = new MUserRole();
			muserRole.setRoleId(roleId);
			muserRole.setUserId(userId);
			records.add(muserRole);
		}
		return userRoleMapper.insertMultiple(records);
	}

	@Override
	public List<MUser> selectByUserName(String userName) {
		List<MUser> list = userMapper.selectByUserName(userName);
		return list;
	}

	@Override
	public void deletePlatformPermiss(Long userId) {
		userSystemsMapper.deleteByUserId(userId);
	}

	@Override
	public int addPlatformPermiss(List<MUserSystems> permissList) {
		return userSystemsMapper.insertMultiple(permissList);
	}

	@Override
	public List<MUserSystems> selectUserByUserIds(Long userId) {
		return userSystemsMapper.selectUserByUserIds(userId);
	}

	@Override
	public boolean insert(MUser userinfo) {
		return userMapper.insertSelective(userinfo)>0;
	}

	@Override
	public int delete(Long userId) {
		return userRoleMapper.deleteByUserId(userId);
	}

	@Override
	public MUser selectUserToCode(String userCode) {
		return userMapper.selectUserToCode(userCode).orElse(null);
	}

	@Override
	public String getvaricode(String userCode) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 4; i++) {
	        int randomNum = random.nextInt(3);
	        switch(randomNum){
	            case 0:
	                char capital = (char)(random.nextInt(26)+65);
	                result = result + capital;
	                break;
	            case 1:
	                char letters = (char)(random.nextInt(26)+97);
	                result = result + letters;
	                break;
	            case 2:
	                int num = random.nextInt(10);
	                result = result + num;
	                break;
	        }
		}
		String verifyKey = Constants.VERIFICATION + "-" + userCode;
		cache.put(verifyKey, result);
		return result;
	}

	@Override
	public String forgotPwd(Long userId, String userCode, String newPassword, String code) {
		String verifyKey = Constants.VERIFICATION + "-" + userCode;
		String captcha = cache.get(verifyKey);
		if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
			return Constants.CODEFAIL;
		}
		String password = StringUtils.isEmpty(newPassword) ? null : SecurityUtils.encryptPassword(newPassword);
		if(userMapper.updatePwd(userId, password) > 0) {
			cache.delete(verifyKey);
			return Constants.SUCCESS;
		} else {
			return Constants.FAIL;
		}
	}

	@Override
	public String updatePwd(LoginUser loginUser, String oldPassword, String newPassword) {
		if (loginUser == null || loginUser.getUserId() == null || StringUtils.isEmpty(loginUser.getPassword())) {
			return Constants.USERFAIL;
		}
		if (!SecurityUtils.matchesPassword(oldPassword, loginUser.getPassword())) {
			return Constants.PWDFAIL;
		}
		String password = StringUtils.isEmpty(newPassword) ? null : SecurityUtils.encryptPassword(newPassword);
		if(userMapper.updatePwd(loginUser.getUserId(), password) > 0) {
			return Constants.SUCCESS;
		} else {
			return Constants.FAIL;
		}
	}
	
	
	/**
	 * 生成验证码，返回验证码图片 
	 * 
	 * @return 结果
	 */
	@Override
	public Map<String, Object> captchImage(String uuid){
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(uuid)){
			uuid = UUID.randomUUID().toString();
		}
		String code = getvaricode(uuid);
		String imgBase64 = ImageUtils.createCaptchImage(code);
		result.put("img", imgBase64);
		result.put("uuid", uuid);
		return result;
	}
}
