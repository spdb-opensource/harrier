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
import cn.spdb.harrier.api.service.job.UdsJobDependencyService;
import cn.spdb.harrier.dao.entity.UdsJobDependency;

@RestController
@RequestMapping("/udsJobDependency")
public class UdsJobDependencyController {
    @Autowired
    private UdsJobDependencyService service;

    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsJobDependency record) {
        return service.add(record);
    }

    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsJobDependency record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobDependency getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobDependency> listQuery() {
        return service.listQuery();
    }
    
	/**
	 * 启用禁用依赖
	 */
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/setDepEnable")
    public int setDepEnable(String pjob,String job,Boolean enable) {
        return service.setDepEnable(pjob, job, enable);
    }
}