package cn.spdb.harrier.api.controller.alarm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.aspect.DataScope;
import cn.spdb.harrier.api.enums.AlarmOper;
import cn.spdb.harrier.api.service.alarm.MAlarmService;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.dao.entity.MAlarm;

@RestController
@RequestMapping("/alarm")
public class MAlarmController {
	@Autowired
	private MAlarmService service;

	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarm> list(Page<MAlarm> page, String platform, String system, String job, String code, String status,
			String localDate) throws Exception {
		LocalDate date = null;
		if (StringUtils.isNotEmpty(localDate)) {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			date = LocalDate.parse(localDate, fmt);
		}
		return service.search(page, platform, system, job, code, status, date);
	}

//	@PutMapping("/add")
//	public int add(MAlarm record) {
//		
//		return service.add(record);
//	}

//	@DeleteMapping("/delete")
//	public int delete(Long id) {
//		return service.delete(id);
//	}
	
	@AccessLogAnnotation(isDbInstall = true)
//	@PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,#record.systems,'RW')")
	@PostMapping("/update")
	public int update(MAlarm record) {
		return service.update(record);
	}

	@DataScope
	@GetMapping("/getDetail")
	public MAlarm getDetail(Long id) {
		return service.getDetail(id);
	}

	@DataScope
	@GetMapping("/listQuery")
	public List<MAlarm> listQuery() {
		return service.listQuery();
	}

	/**
	 * 
	 * @return {@link AlarmOper} ,string[]{ name,desc },
	 */
	@GetMapping("/getOperater")
	public List<String[]> getAlarmOper() {
		return service.getAlarmOper();

	}

	@AccessLogAnnotation(isDbInstall = true)
	@GetMapping("/operater")
	public boolean operaterAlarm(String key, Long alarmId) {
		return service.operAlarm(key, alarmId);
	}
}