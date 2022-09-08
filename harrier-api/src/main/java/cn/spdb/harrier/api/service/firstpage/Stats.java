package cn.spdb.harrier.api.service.firstpage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Stats {
	//平台总数
	long SumPlatforms();
	//应用总数
	long SumSystems();
	//未完成平台总数
	long SumUndonePlatforms();
	//未完成应用总数
	long SumUndoneSystems();
	//作业总数
	long SumJobs();
	//未完成作业总数
	long SumUndoneJobs();
	//集群资源利用率统计
	List<Map<String,String>> ServerResourceInfo();
	//各平台应用作业总量
	List<Map<String, Object>>  sumAllPatformJob();
	//平台作业时刻运行情况统计
	Map<String, List<String>> platformJobStatus(String platform,Date Start,Date end);
	//节点作业时刻运行情况统计
	Map<String, List<String>> ServerJobStatus (String servername,Date start,Date end);
	//查询平台
	List<String> ListAllPlatform();
	//查询子节点
	List<String> ListAllWorker();
	
}
