package cn.spdb.harrier.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.spdb.harrier.common.utils.DateUtils;

@Mapper
public interface FirstPageStats {
	/*
	 * 平台总数
	 */
	@Select("SELECT COUNT(*) as data FROM (Select DISTINCT platform  FROM uds_system ) AS A")
	long sumplatforms();
	
	default long SumPlatforms(){
		return sumplatforms();
	}
	
	/*
	 * 应用总数
	 */
	@Select("SELECT COUNT(*) as data FROM (Select DISTINCT systems  FROM uds_system WHERE systems <> '*') AS A")
	long sumsystems();
	
	default long SumSystems(){
		return sumsystems();
	}
	
	/*
	 * 作业总数
	 */
	@Select("SELECT COUNT(*) as data FROM uds_job")
	long sumjobs();
	
	default long SumJobs(){
		return sumjobs();
	}
	
	/*
	 * 未完成平台总数
	 */
	@Select("SELECT COUNT(*) FROM (SELECT DISTINCT platform FROM (select * from uds_job WHERE last_status <> 'Done') AS A) AS B")
	long sumundoneplatforms();
	
	default long SumUndonePlatforms(){
		return sumundoneplatforms();
	}
	
	/*
	 * 未完成应用总数
	 */
	@Select("SELECT COUNT(*) FROM (SELECT DISTINCT systems FROM (select * from uds_job WHERE last_status <> 'Done') AS A)AS B")
	long sumdonesystems();
	
	default long SumUndoneSystems(){
		return sumdonesystems();
	}
	
	
	/*
	 * 未完成作业总数
	 */
	@Select("select COUNT(*) from uds_job WHERE last_status <> 'Done'")
	long sumundonejobs();
	
	default long SumUndoneJobs(){
		return sumundonejobs();
	}
	
	@Select("SELECT COUNT(*) AS nums FROM uds_job_record WHERE platform ='${platform_}' AND Pending_time <= '${datetime_}' AND dispatcher_time >= '${datetime_}'  \r\n"
			+ "UNION ALL \r\n"
			+ "SELECT COUNT(*)  AS nums FROM uds_job_record WHERE platform ='${platform_}' AND dispatcher_time <= '${datetime_}' AND start_time >= '${datetime_}'\r\n"
			+ "UNION ALL \r\n"
			+ "SELECT COUNT(*) AS nums FROM uds_job_record WHERE platform ='${platform_}' AND start_time <= '${datetime_}' AND end_time >= '${datetime_}'")
	List<Long> platformJobStatus(@Param("platform_") String platform_,@Param("datetime_") String datetime_);
	
	default List<Long> PlatformJobStatus(String platform_,Date datetime_){
		return platformJobStatus(platform_,DateUtils.dateToString(datetime_));
	}
	
	
	@Select("SELECT COUNT(*) AS nums FROM uds_job_record WHERE server_name ='${servername_}' AND start_time <= '${datetime_}' AND end_time >= '${datetime_}'")

	String serverJobStatus(@Param("servername_") String servername_,@Param("datetime_") String datetime_);

	default String ServerJobStatus(String servername_,Date datetime_){
		return serverJobStatus(servername_,DateUtils.dateToString(datetime_));
	}
	
	@Select("SELECT server_name FROM uds_server WHERE node_cluster_type='Worker-Server'")

	List<String> listAllWorker();

	default List<String> ListAllWorker(){
		return listAllWorker();
	}
	
	
	
	@Select("SELECT DISTINCT platform FROM uds_system")

	List<String> listAllPlatform();

	default List<String> ListAllPlatform(){
		return listAllPlatform();
	}
	
	
}






