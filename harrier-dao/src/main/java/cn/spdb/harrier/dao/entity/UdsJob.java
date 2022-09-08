package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJob {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.last_status")
    private String lastStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job_date")
    private LocalDate jobDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.next_job_date")
    private LocalDate nextJobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.server_name")
    private String serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.multi_batch")
    private Integer multiBatch;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.pending_time")
    private LocalDateTime pendingTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.dispatcher_time")
    private LocalDateTime dispatcherTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.start_time")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.end_time")
    private LocalDateTime endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.num_times")
    private Long numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.call_again_num")
    private Integer callAgainNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.stream_type")
    private Byte streamType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.last_status")
    public String getLastStatus() {
        return lastStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.last_status")
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus == null ? null : lastStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job_date")
    public LocalDate getJobDate() {
        return jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job_date")
    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.next_job_date")
    public LocalDate getNextJobDate() {
        return nextJobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.next_job_date")
    public void setNextJobDate(LocalDate nextJobDate) {
        this.nextJobDate = nextJobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.server_name")
    public String getServerName() {
        return serverName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.server_name")
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.multi_batch")
    public Integer getMultiBatch() {
        return multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.multi_batch")
    public void setMultiBatch(Integer multiBatch) {
        this.multiBatch = multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.pending_time")
    public LocalDateTime getPendingTime() {
        return pendingTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.pending_time")
    public void setPendingTime(LocalDateTime pendingTime) {
        this.pendingTime = pendingTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.dispatcher_time")
    public LocalDateTime getDispatcherTime() {
        return dispatcherTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.dispatcher_time")
    public void setDispatcherTime(LocalDateTime dispatcherTime) {
        this.dispatcherTime = dispatcherTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.start_time")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.end_time")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.num_times")
    public Long getNumTimes() {
        return numTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.num_times")
    public void setNumTimes(Long numTimes) {
        this.numTimes = numTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.call_again_num")
    public Integer getCallAgainNum() {
        return callAgainNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.call_again_num")
    public void setCallAgainNum(Integer callAgainNum) {
        this.callAgainNum = callAgainNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.stream_type")
    public Byte getStreamType() {
        return streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.stream_type")
    public void setStreamType(Byte streamType) {
        this.streamType = streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UdsJob [platform=" + platform + ", systems=" + systems + ", job=" + job + ", jobDate=" + jobDate
				+ ", multiBatch=" + multiBatch + "]";
	}
}