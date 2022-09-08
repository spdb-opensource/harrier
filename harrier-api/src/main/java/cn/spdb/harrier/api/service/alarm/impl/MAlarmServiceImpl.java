package cn.spdb.harrier.api.service.alarm.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.enums.AlarmOper;
import cn.spdb.harrier.api.service.alarm.MAlarmService;
import cn.spdb.harrier.api.service.job.UdsJobConfigService;
import cn.spdb.harrier.api.service.job.UdsJobService;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.enmus.alarm.AlarmStatus;
import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.mapper.MAlarmMapper;
import cn.spdb.harrier.dao.mapper.UdsJobMapper;

@Service
public class MAlarmServiceImpl implements MAlarmService {
	@Autowired
	private MAlarmMapper mapper;
	@Autowired
	private UdsJobMapper jobMapper;
	@Autowired
	private UdsJobService jobservice;
	@Autowired
	private UdsJobConfigService configService;

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

	@Override
	public List<String[]> getAlarmOper() {
		return Arrays.stream(AlarmOper.values()).sorted((o1, o2) -> o1.getOreder() - o2.getOreder()).map(en -> {
			String key = en.name();
			String desc = ObjectUtils.isNotEmpty(en.getDesc()) ? en.getDesc() : key;
			return new String[] { key, desc };
		}).collect(Collectors.toList());
	}

	@Override
	public Boolean operAlarm(String key, long alramId) {
		AlarmOper en = AlarmOper.valueOf(key);
		if (ObjectUtils.isEmpty(en)) {
			return false;
		}
		return en.apply(alramId);
	}

	@Override
	public Boolean setVirtuaJump(long alramId) {
		Optional<MAlarm> opt = mapper.selectByPrimaryKey(alramId);
		if (!opt.isPresent()) {
			return false;
		}
		MAlarm alarm = opt.get();
		Optional<UdsJob> jobOpt = jobMapper.selectOne(alarm.getPlatform(), alarm.getSystems(), alarm.getJob());
		if (!jobOpt.isPresent()) {
			return false;
		}
		UdsJob job = jobOpt.get();
		configService.setVirtual(job.getPlatform(), job.getSystems(), job.getJob(), true);
		jobservice.invokeJob(job.getPlatform(), job.getSystems(), job.getJob(), job.getJobDate(), job.getMultiBatch());
		MAlarm record = new MAlarm();
		record.setId(alramId);
		record.setOperationTime(LocalDateTime.now());
		record.setOperationUser(SecurityUtils.getUsername());
		record.setOperationType(AlarmOper.SET_VIRTUAL_JUMP.name());
		record.setAlarmStatus(AlarmStatus.END.getName());
		mapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public Boolean invokeJob(long alramId) {
		Optional<MAlarm> opt = mapper.selectByPrimaryKey(alramId);
		if (!opt.isPresent()) {
			return false;
		}
		MAlarm alarm = opt.get();
		Optional<UdsJob> jobOpt = jobMapper.selectOne(alarm.getPlatform(), alarm.getSystems(), alarm.getJob());
		if (!jobOpt.isPresent()) {
			return false;
		}
		UdsJob job = jobOpt.get();
		jobservice.invokeJob(job.getPlatform(), job.getSystems(), job.getJob(), job.getJobDate(), job.getMultiBatch());
		MAlarm record = new MAlarm();
		record.setId(alramId);
		record.setOperationTime(LocalDateTime.now());
		record.setOperationUser(SecurityUtils.getUsername());
		record.setOperationType(AlarmOper.AGAIN.name());
		record.setAlarmStatus(AlarmStatus.END.getName());
		mapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public Boolean alarmProcessed(long alramId) {
		MAlarm record = new MAlarm();
		record.setId(alramId);
		record.setOperationTime(LocalDateTime.now());
		record.setOperationUser(SecurityUtils.getUsername());
		record.setOperationType(AlarmOper.PROCESSED.name());
		record.setAlarmStatus(AlarmStatus.END.getName());
		mapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public Boolean alarmDealing(long alramId) {
		MAlarm record = new MAlarm();
		record.setId(alramId);
		record.setOperationTime(LocalDateTime.now());
		record.setOperationUser(SecurityUtils.getUsername());
		record.setOperationType(AlarmOper.DEALING.name());
		record.setAlarmStatus(AlarmStatus.DEAL.getName());
		mapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	public Boolean alarmRecover(long alramId) {
		MAlarm record = new MAlarm();
		record.setId(alramId);
		record.setOperationTime(LocalDateTime.now());
		record.setOperationUser(SecurityUtils.getUsername());
		record.setOperationType(AlarmOper.RECOVER.name());
		record.setAlarmStatus(AlarmStatus.WARN.getName());
		record.setNoticeCount(0);
		mapper.updateByPrimaryKeySelective(record);
		return true;
	}

}