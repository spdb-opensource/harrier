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
import cn.spdb.harrier.api.service.alarm.MAlarmUserGroupService;
import cn.spdb.harrier.dao.entity.MAlarmUserGroup;

@RestController
@RequestMapping("/alarm/group")
public class MAlarmUserGroupController {
    @Autowired
    private MAlarmUserGroupService service;

	@DataScope
	@RequestMapping(method = RequestMethod.GET)
	public Page<MAlarmUserGroup> list(Page<MAlarmUserGroup> page, String group_name) throws Exception {
		return service.search(page, group_name);
	}

    @PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,'*','RW')")
    @PutMapping("/add")
    public int add(MAlarmUserGroup record) {
        return service.add(record);
    }


    @PreAuthorize("@HarrierPermission.hasPermissions(#platform,'*','RW')")
    @DeleteMapping("/delete")
    public int delete(Integer id,String platform) {
        return service.delete(id);
    }


    @PreAuthorize("@HarrierPermission.hasPermissions(#record.platform,#record.systems,'RW')")
    @PostMapping("/update")
    public int update(MAlarmUserGroup record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public MAlarmUserGroup getDetail(Integer id) {
        return service.getDetail(id);
    }
    
    @GetMapping("/getDetailByGroupName")
    public List<MAlarmUserGroup> getDetailByGroupName(String groupName) {
        return service.getDetailByGroupName(groupName);
    }

    @GetMapping("/listQuery")
    public List<MAlarmUserGroup> listQuery() {
        return service.listQuery();
    }
}