package cn.spdb.harrier.api.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.job.UdsJobStepRecordService;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;

@RestController
@RequestMapping("/udsJobStepRecord")
public class UdsJobStepRecordController {
    @Autowired
    private UdsJobStepRecordService service;

    @PutMapping("/add")
    public int add(UdsJobStepRecord record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJobStepRecord record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobStepRecord getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobStepRecord> listQuery() {
        return service.listQuery();
    }
    
    /**
     * 作业列表-查询作业脚本运行记录
     */
    @GetMapping("/selectJobStepRecord")
    public List<UdsJobStepRecord> selectJobStepFrequency(Long job_record_id){
    	return service.selectJobStepRecord(job_record_id);
    }
}