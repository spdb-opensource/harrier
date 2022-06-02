package cn.spdb.harrier.api.service.alarm;

import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.MRole;

import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface MAlarmService {
	/**
	 * 新增
	 */
	int add(MAlarm record);

	/**
	 * id删除
	 */
	int delete(Long id);

	/**
	 * 修改
	 */
	int update(MAlarm record);

	/**
	 * 查看详情
	 */
	MAlarm getDetail(Long id);

	/**
	 * 未完成的列表查询，待完善
	 */
	List<MAlarm> listQuery();

	Page<MAlarm> search(Page<MAlarm> page, String platfrom, String system, String job, String code, String status,
			LocalDate localDate);
}