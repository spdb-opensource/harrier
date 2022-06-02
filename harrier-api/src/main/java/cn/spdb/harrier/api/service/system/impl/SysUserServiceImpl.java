package cn.spdb.harrier.api.service.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.ISysUserService;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.uitls.StringUtils;
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
}
