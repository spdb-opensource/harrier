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

import cn.spdb.harrier.api.service.job.UdsServerService;
import cn.spdb.harrier.dao.entity.UdsServer;

@RestController
@RequestMapping("/udsServer")
public class UdsServerController {
    @Autowired
    private UdsServerService service;

    @PutMapping("/add")
    public int add(UdsServer record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Integer id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsServer record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsServer getDetail(Integer id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsServer> listQuery() {
        return service.listQuery();
    }
    
    @GetMapping("/selectAll")
    public Map<String, Object> selectAll(Page<UdsServer> page, String servername){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsServer> pageinfo = service.selectAll(page,servername);
	    result.put("rows", pageinfo.getRecords());  
    	return result;
    }
    
    @PostMapping("/setEnable")
    public int setEnable(String serverName,Boolean is_enable) {
    	return service.setEnable(serverName, is_enable);
    }
}