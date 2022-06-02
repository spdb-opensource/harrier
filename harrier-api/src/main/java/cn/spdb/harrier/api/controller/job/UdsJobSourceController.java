package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobSourceService;
import cn.spdb.harrier.dao.entity.UdsJobSource;

@RestController
@RequestMapping("/udsJobSource")
public class UdsJobSourceController {
    @Autowired
    private UdsJobSourceService service;

    @PutMapping("/add")
    public int add(UdsJobSource record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobSource record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobSource getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobSource> listQuery() {
        return service.listQuery();
    }
}