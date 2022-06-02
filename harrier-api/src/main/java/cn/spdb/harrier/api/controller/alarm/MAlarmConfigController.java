package cn.spdb.harrier.api.controller.alarm;

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

import cn.spdb.harrier.api.aspect.DataScope;
import cn.spdb.harrier.api.service.alarm.MAlarmConfigService;
import cn.spdb.harrier.dao.entity.MAlarmConfig;

@RestController
@RequestMapping("/alarm/config")
public class MAlarmConfigController {
	@Autowired
	private MAlarmConfigService service;

	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarmConfig> list(Page<MAlarmConfig> page, String platfrom, String system, String job, String code)
			throws Exception {
		return service.search(page, platfrom, system, job, code);
	}
	
	@DataScope
	@GetMapping("/jobConfig")
	public Page<MAlarmConfig> listJobConfig(Page<MAlarmConfig> page, String platfrom, String system, String job, String code)
			throws Exception {
		return service.searchJobConfig(page, platfrom, system, job, code);
	}

	@PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,'*','RW')")
	@PutMapping("/add")
	public int add(MAlarmConfig record) {
		return service.add(record);
	}

	@PreAuthorize("@HarrierPermission.hasPermissions(#platform,'*','RW')")
	@DeleteMapping("/delete")
	public int delete(Integer id, String platform) {
		return service.delete(id, platform);
	}

	@PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,#record.systems,'RW')")
	@PostMapping("/update")
	public int update(MAlarmConfig record) {
		return service.update(record);
	}

	@GetMapping("/getDetail")
	public MAlarmConfig getDetail(Integer id) {
		return service.getDetail(id);
	}

	@GetMapping("/listQuery")
	public List<MAlarmConfig> listQuery() {
		return service.listQuery();
	}
}