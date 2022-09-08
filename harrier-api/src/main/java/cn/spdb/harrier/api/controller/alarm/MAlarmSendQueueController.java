package cn.spdb.harrier.api.controller.alarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import cn.spdb.harrier.api.service.alarm.MAlarmSendQueueService;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;

@RestController
@RequestMapping("/alarm/send")
public class MAlarmSendQueueController {
    @Autowired
    private MAlarmSendQueueService service;

	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarmSendQueue> list(Page<MAlarmSendQueue> page)
			throws Exception {
		return service.search(page);
	}
	
    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(MAlarmSendQueue record) {
        return service.add(record);
    }
    
    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(MAlarmSendQueue record) {
        return service.update(record);
    }
    @GetMapping("/getDetail")
    public MAlarmSendQueue getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<MAlarmSendQueue> listQuery() {
        return service.listQuery();
    }
}