package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobRecordService;
import cn.spdb.harrier.dao.entity.UdsJobRecord;

@RestController
@RequestMapping("/udsJobRecord")
public class UdsJobRecordController {
    @Autowired
    private UdsJobRecordService service;

    @PutMapping("/add")
    public int add(UdsJobRecord record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobRecord record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobRecord getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobRecord> listQuery() {
        return service.listQuery();
    }
    
    /**
     * 作业列表-查询作业运行记录
     */
    @GetMapping("/selectJobRecord")
    public List<UdsJobRecord> selectJobFrequency(String platform, String system, String job){
    	return service.selectJobRecord(platform, system, job);
    }
}