package cn.spdb.harrier.api.controller.firstpage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.api.service.firstpage.impl.StatsImpl;
import cn.spdb.harrier.common.utils.DateUtils;

@RestController
@RequestMapping("/firstPage")
public class FirstPageStatsController {

	@Autowired StatsImpl service;

@GetMapping("/psjStats")
public Map<String, Long> psjStats(){
	Map<String, Long> datas = new ConcurrentHashMap<String, Long>();
	
	datas.put("sumPlatforms", service.SumPlatforms());
	datas.put("sumSystems", service.SumSystems());
	datas.put("sumJobs", service.SumJobs());
	datas.put("sumUndonePlatforms", service.SumUndonePlatforms());
	datas.put("sumUndoneSystems", service.SumUndoneSystems());
	datas.put("sumUndoneJobs", service.SumUndoneJobs());
	
	return datas;
}

@GetMapping("/clusterStats")
public List<Map<String, String>> clusterStats(){

	return service.ServerResourceInfo();
}

@GetMapping("/sumAllPatformJob")
public List<Map<String, Object>> sumAllPatformJob(){

	return service.sumAllPatformJob();
}

@GetMapping("/platformJobStatus")
public Map<String, List<String>> platformJobStatus(String platform,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end){

	if (end == null || start == null) {
		end = DateUtils.getCurrentDate();
		start = DateUtils.add(end, Calendar.HOUR, -1);
	}
	return service.platformJobStatus(platform, start, end);
}

@GetMapping("/serverJobStatus")
public Map<String, List<String>> serverJobStatus(String servername,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end){

	if (end == null || start == null) {
		end = DateUtils.getCurrentDate();
		start = DateUtils.add(end, Calendar.HOUR, -1);
	}
	return service.ServerJobStatus(servername, start, end);
}

@GetMapping("/ListAllWorker")
public List<String> ListAllWorker() {
	return service.ListAllWorker();
}

@GetMapping("/ListAllPlatform")
public List<String> ListAllPlatform() {
	return service.ListAllPlatform();
}

}
