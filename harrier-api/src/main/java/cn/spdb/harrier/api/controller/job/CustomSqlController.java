package cn.spdb.harrier.api.controller.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.spdb.harrier.api.service.job.CustomSqlService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@RestController
@RequestMapping(path = {"/jobmanage/customsql"})
public class CustomSqlController {
    @Autowired
    private CustomSqlService customSqlService;
	
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> list(int pageCurrent,int pageSize,String selectSql, boolean isAgree) throws Exception {
    	if(!isAgree) {
    		throw new Exception("请选择同意 我承担执行SQL的后果!");
    	}
    	Map<String, Object> result = new HashMap<String, Object>();
    	List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    	try {	
    		if (selectSql != null) {
    			if (selectSql.trim().toLowerCase().startsWith("select")) {
    				datas = customSqlService.execSelect(pageCurrent,pageSize,selectSql);
    			} else {			
		    			if (selectSql.trim().toLowerCase().startsWith("update")) {
		    				customSqlService.updateExec(selectSql);
		    			} else if (selectSql.trim().toLowerCase().startsWith("delete")) {
		    				customSqlService.deleteExec(selectSql);
		    			} else if (selectSql.trim().toLowerCase().startsWith("insert")) {
		    				customSqlService.insertExec(selectSql);
		    			} else {
		    				throw new Exception("暂不支持DDL语句,如:alter,drop,create");
		    			}
	    			} 
    		
    			result.put("rows", datas);  
    	 }
    		return result;
}catch(Exception e) {
    	throw e;
    }
}
}