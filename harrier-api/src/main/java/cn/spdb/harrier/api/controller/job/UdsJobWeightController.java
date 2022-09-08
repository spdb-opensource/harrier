package cn.spdb.harrier.api.controller.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.job.UdsJobWeightService;
import cn.spdb.harrier.dao.entity.UdsJobWeight;

@RestController
@RequestMapping("/udsJobWeight")
public class UdsJobWeightController {
    @Autowired
    private UdsJobWeightService service;

    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsJobWeight record) {
        return service.add(record);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsJobWeight record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobWeight getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobWeight> listQuery() {
        return service.listQuery();
    }
    
    @GetMapping("/selectAll")
    public Map<String, Object> selectAll(Page<UdsJobWeight> page, String platform, String systems, String job, Integer limitType){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsJobWeight> pageinfo = service.selectAll(page,platform,systems,job,limitType);
	    result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
    	return result;
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/updateConfValue")
    public int updateConfValue(String platform,String systems,String job,Integer limit_type,Integer conf_value) {
    	return service.updateConfValue(platform,systems,job,limit_type,conf_value);
    
    }
    @AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("{ids}/delete")
	public int delete(@PathVariable(value = "ids") Long[] ids) {
		return service.delete(ids);
	}
}