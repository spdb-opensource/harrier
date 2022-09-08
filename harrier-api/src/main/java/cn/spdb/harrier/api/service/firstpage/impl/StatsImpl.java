package cn.spdb.harrier.api.service.firstpage.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.spdb.harrier.api.service.firstpage.Stats;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.mapper.FirstPageStats;
import cn.spdb.harrier.dao.mapper.UdsJobMapper;
import cn.spdb.harrier.dao.mapper.UdsServerMapper;

@Service
public class StatsImpl implements Stats{

	@Autowired
	FirstPageStats firstPageStats;
	@Autowired
	UdsServerMapper mapper;
	@Autowired
	UdsJobMapper jobMapper;
	@Override
	public long SumPlatforms() {
		return firstPageStats.SumPlatforms();
	}

	@Override
	public long SumSystems() {
		return firstPageStats.SumSystems();
	}
	
	@Override
	public long SumJobs() {
		return firstPageStats.SumJobs();
	}

	@Override
	public long SumUndonePlatforms() {
		return firstPageStats.SumUndonePlatforms();
	}

	@Override
	public long SumUndoneSystems() {
		return firstPageStats.SumUndoneSystems();
	}

	@Override
	public long SumUndoneJobs() {
		return firstPageStats.SumUndoneJobs();
	}

	@Override
	public List<Map<String,String>> ServerResourceInfo() {
		
		List<UdsServer> datas=mapper.ServerResourceInfo();
		
		//节点数量
		float allservernum = datas.size();
		//就绪节点数量
		float runservernum = 0;
		//总cpu
		float allclustercpu = 0;
		//使用cpu
		float usedclustercpu = 0;
		//总内存
		float allclustermem= 0;
		//使用内存
		float usedclustermem = 0;
		
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		
		for(UdsServer server :datas) {
			Map<String, String> serverinfo = handleResourceInfo(server);
			if(serverinfo.get("is_enable") == "true") {
				runservernum = runservernum + 1 ;
			}
			
			allclustermem = allclustermem + Float.parseFloat(serverinfo.get("allmem"));
			usedclustermem = usedclustermem + Float.parseFloat(serverinfo.get("usedmem"));
			allclustercpu = allclustercpu + Float.parseFloat(serverinfo.get("allcpu"));
			usedclustercpu = usedclustercpu + Float.parseFloat(serverinfo.get("usedcpu"));
			
			results.add(serverinfo);
		
		}
		
		Map<String, String> clusterstats =new HashMap<String, String>();
		JSONObject jsonObject =new JSONObject();
		
		jsonObject.put("allservernum", allservernum);
		jsonObject.put("runservernum", runservernum);
		jsonObject.put("allclustercpu", allclustercpu);
		jsonObject.put("usedclustercpu", usedclustercpu);
		jsonObject.put("allclustermem", allclustermem);
		jsonObject.put("usedclustermem", usedclustermem);
		
		clusterstats.put("clusterstats", jsonObject.toJSONString());
		results.add(clusterstats);
		
		return results;
	}
	
	private Map<String,String> handleResourceInfo(UdsServer udsServer){
		
		Map<String, String> results =new HashMap<String, String>();
		
		results.put("name", udsServer.getServerRoleName());
		results.put("type", udsServer.getNodeClusterType());
		results.put("ip", udsServer.getIp());
		results.put("is_enable", udsServer.getIsEnable().toString());
		
		JSONObject jsonObject = JSON.parseObject(udsServer.getPara());
		
		String[] MEM = jsonObject.get("MEM").toString().replace("[", "").replace("]", "").split(",");
		String[] CPU = jsonObject.get("CPU").toString().replace("[", "").replace("]", "").split(",");
		
		results.put("allmem", MEM[0]);
		BigDecimal b1 =new BigDecimal(MEM[0]);
		BigDecimal b2 = new BigDecimal(MEM[1]);
		Double usemem = b1.multiply(b2).doubleValue();
		results.put("usedmem", usemem.toString());
		results.put("allcpu", CPU[0]);
		BigDecimal b3 =new BigDecimal(CPU[0]);
		BigDecimal b4 = new BigDecimal(CPU[1]);
		Double usecpu = b3.multiply(b4).doubleValue();
		results.put("usedcpu", usecpu.toString());

		return results;
		
	}

	@Override
	public List<Map<String, Object>> sumAllPatformJob() {
		return jobMapper.sumAllPatformJob();
	}
	
	@Override
	public Map<String, List<String>>  platformJobStatus(String platform,Date start,Date end) {
		
		Date time = start;
//		List<Map<String, Long>>  results = new ArrayList<Map<String, Long>>();
		Map<String, List<String>>  results = new HashMap<String, List<String>>();
		List<String> PendingList = new ArrayList<String>();
		List<String> DispatcherList = new ArrayList<String>();
		List<String> RunningList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		
		Date afterend = DateUtils.add(end,Calendar.MINUTE, 1);
		while(DateUtils.compare(afterend, time)) {	
			
			List<Long> datas  = firstPageStats.PlatformJobStatus(platform, time);
//			Map<String, Long> result = new HashMap<String, Long>();
			
			PendingList.add(datas.get(0).toString());
			DispatcherList.add(datas.get(1).toString());
			RunningList.add(datas.get(2).toString());
			timeList.add(DateUtils.dateToString(time));
			
//			result.put("PendingNum", (Long) datas.get(0));
//			result.put("DispatcherNum", (Long) datas.get(1));
//			result.put("RunningNum", (Long) datas.get(2));
//			results.add(result);
			
			time = DateUtils.add(time,Calendar.MINUTE, 1);
		}
			
		results.put("pending", PendingList);
		results.put("dispatcher", DispatcherList);
		results.put("running", RunningList);
		results.put("timeList", timeList);
		
		return results;
	}

	@Override
	public Map<String, List<String>> ServerJobStatus(String servername, Date start, Date end) {

		Map<String, List<String>>  results = new HashMap<String, List<String>>();
		List<String> runninglist= new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		Date time = start;
		Date afterend = DateUtils.add(end,Calendar.MINUTE, 1);
		while(DateUtils.compare(afterend, time)) {	
			String data  = firstPageStats.ServerJobStatus(servername, time);
			runninglist.add(data);
			timeList.add(DateUtils.dateToString(time));
			time = DateUtils.add(time,Calendar.MINUTE, 1);
		}
		results.put("running", runninglist);
		results.put("timeList", timeList);
		return results;
	}

	@Override
	public List<String> ListAllPlatform() {
		return firstPageStats.ListAllPlatform();
	}

	@Override
	public List<String> ListAllWorker() {
		return firstPageStats.ListAllWorker();
	}
}
