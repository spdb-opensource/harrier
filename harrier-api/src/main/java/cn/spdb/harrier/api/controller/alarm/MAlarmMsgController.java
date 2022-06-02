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
import cn.spdb.harrier.api.service.alarm.MAlarmMsgService;
import cn.spdb.harrier.dao.entity.MAlarmMsg;

@RestController
@RequestMapping("/alarm/msg")
public class MAlarmMsgController {
    @Autowired
    private MAlarmMsgService service;

    
	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarmMsg> list(Page<MAlarmMsg> page, String platfrom, String system, String code) throws Exception {
		return service.search(page, platfrom, system, code);
	}
    
    @PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,'*','RW')")
    @PutMapping("/add")
    public int add(MAlarmMsg record) {
        return service.add(record);
    }

    @PreAuthorize("@HarrierPermission.hasPermissions(#platform,'*','RW')")
    @DeleteMapping("/delete")
    public int delete(Integer id,String platorm) {
        return service.delete(id);
    }

    @PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,#record.systems,'RW')")
    @PostMapping("/update")
    public int update(MAlarmMsg record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public MAlarmMsg getDetail(Integer id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<MAlarmMsg> listQuery() {
        return service.listQuery();
    }
}