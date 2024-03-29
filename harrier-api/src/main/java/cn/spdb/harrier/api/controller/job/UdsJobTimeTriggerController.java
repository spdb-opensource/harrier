package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.job.UdsJobTimeTriggerService;
import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;

@RestController
@RequestMapping("/udsJobTimeTrigger")
public class UdsJobTimeTriggerController {
    @Autowired
    private UdsJobTimeTriggerService service;

    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsJobTimeTrigger record) {
        return service.add(record);
    }

    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsJobTimeTrigger record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobTimeTrigger getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobTimeTrigger> listQuery() {
        return service.listQuery();
    }

    @GetMapping("/selectJobTrigger")
    public UdsJobTimeTrigger selectJobTrigger(String platform, String system, String job) {
        return service.selectJobTrigger(platform, system, job);
    }
}