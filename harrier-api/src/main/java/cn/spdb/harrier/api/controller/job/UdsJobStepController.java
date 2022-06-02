package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobStepService;
import cn.spdb.harrier.dao.entity.UdsJobStep;

@RestController
@RequestMapping("/udsJobStep")
public class UdsJobStepController {
    @Autowired
    private UdsJobStepService service;

    @PutMapping("/add")
    public int add(UdsJobStep record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobStep record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobStep getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobStep> listQuery() {
        return service.listQuery();
    }
}