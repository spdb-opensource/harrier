package cn.spdb.harrier.api.service.alarm.impl;

import cn.spdb.harrier.api.service.alarm.MAlarmService;
import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.mapper.MAlarmMapper;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class MAlarmServiceImpl implements MAlarmService {
	@Autowired
	private MAlarmMapper mapper;

	@Override
	public Page<MAlarm> search(Page<MAlarm> page, String platfrom, String system, String job, String code,
			String status, LocalDate localDate) {
		return mapper.search(page, platfrom, system, job, code, status, localDate);
	}

	/**
	 * 新增
	 */
	@Override
	public int add(MAlarm record) {
		return mapper.insertSelective(record);
	}

	/**
	 * id删除
	 */
	@Override
	public int delete(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改
	 */
	@Override
	public int update(MAlarm record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 查看详情
	 */
	@Override
	public MAlarm getDetail(Long id) {
		return mapper.selectByPrimaryKey(id).orElse(null);
	}

	/**
	 * 列表查询
	 */
	@Override
	public List<MAlarm> listQuery() {
		return mapper.select(null);
	}

}