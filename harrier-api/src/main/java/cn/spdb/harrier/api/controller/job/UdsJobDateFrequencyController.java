package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobDateFrequencyService;
import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;

@RestController
@RequestMapping("/udsJobDateFrequency")
public class UdsJobDateFrequencyController {
    @Autowired
    private UdsJobDateFrequencyService service;

    @PutMapping("/add")
    public int add(UdsJobDateFrequency record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobDateFrequency record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobDateFrequency getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobDateFrequency> listQuery() {
        return service.listQuery();
    }
    
    /**
     * 作业列表-查询作业频度
     */
    @GetMapping("/selectJobFrequency")
    public List<UdsJobDateFrequency> selectJobFrequency(String platform, String system, String job){
    	return service.selectJobFrequency(platform, system, job);
    }
}