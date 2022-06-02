package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobConfigService;
import cn.spdb.harrier.dao.entity.UdsJobConfig;

@RestController
@RequestMapping("/udsJobConfig")
public class UdsJobConfigController {
    @Autowired
    private UdsJobConfigService service;

    @PutMapping("/add")
    public int add(UdsJobConfig record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobConfig record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobConfig getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobConfig> listQuery() {
        return service.listQuery();
    }
    
    @PostMapping("/setEnable")
    public int setEnable(String platform, String systems, String job, Boolean enable) {
        return service.setEnable(platform, systems, job, enable);
    }
    
    @PostMapping("/setVirtual")
    public int setVirtual(String platform, String systems, String job, Boolean virtual) {
        return service.setVirtual(platform, systems, job, virtual);
    }
}