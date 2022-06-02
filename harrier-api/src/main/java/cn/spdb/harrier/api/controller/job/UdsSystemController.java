package cn.spdb.harrier.api.controller.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.job.UdsSystemService;
import cn.spdb.harrier.dao.entity.UdsSystem;

@RestController
@RequestMapping("/udsSystem")
public class UdsSystemController {
    @Autowired
    private UdsSystemService service;

    @PutMapping("/add")
    public int add(UdsSystem record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Integer id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsSystem record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsSystem getDetail(Integer id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsSystem> listQuery() {
        return service.listQuery();
    }
    
    @GetMapping("/selectAll")
    public Map<String, Object> selectAll(Page<UdsSystem> page, String platform, String systems, String use_platform,String server_role_name_group){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsSystem> pageinfo = service.selectAll(page,platform,systems,use_platform,server_role_name_group);
	    result.put("rows", pageinfo.getRecords());  
    	return result;
    }
    
    @PostMapping("/updateConcurrencyState")
    public int setEnable(UdsSystem udsSystem) {
    	return service.updateConcurrencyState(udsSystem);
    }
    
    @GetMapping("/selectSystem")
    public List<UdsSystem> selectSystem(String platform){
		List<UdsSystem> list = service.selectByPlatform(platform);
    	return list;
    }
}