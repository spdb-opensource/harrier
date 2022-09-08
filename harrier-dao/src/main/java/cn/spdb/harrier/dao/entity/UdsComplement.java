package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsComplement {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.id")
	private Long id;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.com_name")
	private String comName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.start_time")
	private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.end_time")
	private LocalDateTime endTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.last_status")
	private String lastStatus;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.batch_range")
	private String batchRange;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.server_name_range")
	private String serverNameRange;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.max_run_job")
	private Integer maxRunJob;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.des")
	private String des;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.id")
	public Long getId() {
		return id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.id")
	public void setId(Long id) {
		this.id = id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.com_name")
	public String getComName() {
		return comName;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.com_name")
	public void setComName(String comName) {
		this.comName = comName == null ? null : comName.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.start_time")
	public LocalDateTime getStartTime() {
		return startTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.start_time")
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.end_time")
	public LocalDateTime getEndTime() {
		return endTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.end_time")
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.last_status")
	public String getLastStatus() {
		return lastStatus;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.902+08:00", comments = "Source field: uds_complement.last_status")
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus == null ? null : lastStatus.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.batch_range")
	public String getBatchRange() {
		return batchRange;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.batch_range")
	public void setBatchRange(String batchRange) {
		this.batchRange = batchRange == null ? null : batchRange.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.server_name_range")
	public String getServerNameRange() {
		return serverNameRange;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.server_name_range")
	public void setServerNameRange(String serverNameRange) {
		this.serverNameRange = serverNameRange == null ? null : serverNameRange.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.max_run_job")
	public Integer getMaxRunJob() {
		return maxRunJob;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.max_run_job")
	public void setMaxRunJob(Integer maxRunJob) {
		this.maxRunJob = maxRunJob;
	}


	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.des")
	public String getDes() {
		return des;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-06-20T13:57:10.903+08:00", comments = "Source field: uds_complement.des")
	public void setDes(String des) {
		this.des = des == null ? null : des.trim();
	}

}