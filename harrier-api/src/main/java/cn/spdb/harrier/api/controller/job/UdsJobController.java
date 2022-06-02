package cn.spdb.harrier.api.controller.job;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.model.TreeJobNode;
import cn.spdb.harrier.api.service.job.UdsJobService;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobMenu;

@RestController
@RequestMapping("/udsJob")
public class UdsJobController {
    @Autowired
    private UdsJobService service;

    @PutMapping("/add")
    public int add(UdsJob record) {
        return service.add(record);
    }

    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    public int update(UdsJob record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJob getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJob> listQuery() {
        return service.listQuery();
    }
    
    /**
     * 作业管理—查询所有作业(分页)
     */
    @GetMapping("/selectAll")
    public Map<String, Object> selectAll(Page<UdsJobMenu> page,String platform,String systems,String job,String lastStatus){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsJobMenu> pageinfo = service.selectAll(page,platform,systems,job,lastStatus);
	    result.put("rows", pageinfo.getRecords());  
    	return result;
    }
    
    /**
     * 作业列表-查询作业详情	
     */
    @GetMapping("/selectJobDetail")
    public Map<String, Object> selectJobDetail(String platform,String systems,String job){
		Map<String, Object> result = new HashMap<String, Object>();
		UdsJobMenu datas = service.selectJobDetail(platform, systems,job);
	    result.put("rows", datas);  
    	return result;
    }
    
    /**
     * 作业列表-更新作业详情	
     */
    @PostMapping("/updateJobDetail")
    public int updateJobDetail(UdsJobMenu udsJobMenu) {
        return service.updateJobDetail(udsJobMenu);
    }
    
    /**
     * 作业列表-查询作业上游
     * @return
     */
    @GetMapping(path="allUpjobList")
	public List<TreeJobNode> queryAllUpjobList(String job,Integer level) {
    	return service.listAllUpJobs(job,level);
	}
    
    /** 
     *作业列表-查询作业下游
   	 */  
    @GetMapping(path="allDownjobList")
   	public List<TreeJobNode> queryAllDownjobList(String job,Integer level) {
       	return service.listAllDownjobs(job,level);
   	}
    
    /**
     * 作业列表-invoke作业	
     */
    @PostMapping("/invokeJob")
    public int invokeJob(String platform,String systems,String job,@DateTimeFormat(pattern="yyyyMMdd") LocalDate jobdate,Integer multibatch) {
        return service.invokeJob(platform, systems, job, jobdate, multibatch);
    }
    
    /**
     * 作业列表-kill作业	
     */
    @PostMapping("/killJob")
    public int killJob(String platform,String systems,String job,Integer multibatch) {
        /**
         * 主节点
         */
    	return 1;
    }
    
    /**
     * 作业列表-补数	
     */
    @PostMapping("/forceStartJob")
    public int forceStartJob(String platform,String systems,String job,Integer multibatch) {
        /**
         * 主节点
         */
    	return 1;
    }
    
}