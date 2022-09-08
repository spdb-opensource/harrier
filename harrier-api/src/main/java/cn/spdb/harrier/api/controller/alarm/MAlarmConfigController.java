package cn.spdb.harrier.api.controller.alarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.aspect.DataScope;
import cn.spdb.harrier.api.service.alarm.MAlarmConfigService;
import cn.spdb.harrier.common.enmus.alarm.AlarmStatus;
import cn.spdb.harrier.common.enmus.alarm.SendTypeResource;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.MAlarmConfig;

@RestController
@RequestMapping("/alarm/config")
public class MAlarmConfigController {
	@Autowired
	private MAlarmConfigService service;

	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarmConfig> list(Page<MAlarmConfig> page, String platform, String system, String job, String code)
			throws Exception {
		return service.search(page, platform, system, job, code);
	}
	
	@DataScope
	@GetMapping("/jobConfig")
	public Page<MAlarmConfig> listJobConfig(Page<MAlarmConfig> page, String platform, String system, String job, String code)
			throws Exception {
		return service.searchJobConfig(page, platform, system, job, code);
	}
 
	@AccessLogAnnotation(isDbInstall = true)
	@PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,'*','W')")
	@PutMapping("/add")
	public int add(MAlarmConfig record) {
		return service.add(record);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PreAuthorize("@HarrierPermission.hasPermissions(#platform,'*','W')")
	@DeleteMapping("/delete")
	public int delete(Integer id, String platform) {
		return service.delete(id, platform);
	}

	@AccessLogAnnotation(isDbInstall = true)
	@PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,#record.systems,'W')")
	@PostMapping("/update")
	public int update(MAlarmConfig record) {
		return service.update(record);
	}

	@DataScope
	@GetMapping("/getDetail")
	public MAlarmConfig getDetail(Integer id) {
		return service.getDetail(id);
	}

	@DataScope
	@GetMapping("/listQuery")
	public List<MAlarmConfig> listQuery() {
		return service.listQuery();
	}
	
	@GetMapping("/defaultStatus")
	public HashMap<String, String> defaultStatus() {
		HashMap<String, String> result = new HashMap<String, String>();
		Arrays.stream(AlarmStatus.values()).forEach(
				action -> result.put(action.name(), action.getName()));
		return result;
	}
}