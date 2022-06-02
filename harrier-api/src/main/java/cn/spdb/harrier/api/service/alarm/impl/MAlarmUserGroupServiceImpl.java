package cn.spdb.harrier.api.service.alarm.impl;

import cn.spdb.harrier.api.service.alarm.MAlarmUserGroupService;
import cn.spdb.harrier.dao.entity.MAlarmUserGroup;
import cn.spdb.harrier.dao.mapper.MAlarmUserGroupMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class MAlarmUserGroupServiceImpl implements MAlarmUserGroupService {
	@Autowired
	private MAlarmUserGroupMapper mapper;

	/**
	 * 新增
	 */
	@Override
	public int add(MAlarmUserGroup record) {
		return mapper.insertSelective(record);
	}

	/**
	 * id删除
	 */
	@Override
	public int delete(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改
	 */
	@Override
	public int update(MAlarmUserGroup record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 查看详情
	 */
	@Override
	public MAlarmUserGroup getDetail(Integer id) {
		return mapper.selectByPrimaryKey(id).orElse(null);
	}

	/**
	 * 列表查询
	 */
	@Override
	public List<MAlarmUserGroup> listQuery() {
		return mapper.select(null);
	}

	@Override
	public Page<MAlarmUserGroup> search(Page<MAlarmUserGroup> page, String groupName) {
		return mapper.search(page, groupName);
	}

	@Override
	public List<MAlarmUserGroup> getDetailByGroupName(String groupName) {
		return mapper.selectListByGroupName(groupName);
	}
}