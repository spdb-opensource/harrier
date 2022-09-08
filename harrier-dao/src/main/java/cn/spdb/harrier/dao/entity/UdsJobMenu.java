package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJobMenu {
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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.job_date")
    private LocalDate jobDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.next_job_date")
    private LocalDate nextJobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.server_name")
    private String serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.228+08:00", comments="Source field: uds_job.multi_batch")
    private Integer multiBatch;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.pending_time")
    private LocalDateTime pendingTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.dispatcher_time")
    private LocalDateTime dispatcherTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.start_time")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.end_time")
    private LocalDateTime endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.num_times")
    private Long numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.229+08:00", comments="Source field: uds_job.call_again_num")
    private Integer callAgainNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.stream_type")
    private Byte streamType;

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
    
/*
 * uds_job_config
 */

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_name")
    private String jobName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_type")
    private String jobType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.offset_day")
    private Byte offsetDay;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.time_window")
    private String timeWindow;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.virtual_enable")
    private Byte virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.priority")
    private Integer priority;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_max_num")
    private Byte callAgainMaxNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    private Byte callAgainWaitSec;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.count_batch")
    private Integer countBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.batch_conversion")
    private Integer batchConversion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_frequency")
    private Boolean checkFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_time_trigger")
    private Boolean checkTimeTrigger;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_stream_self")
    private Boolean checkStreamSelf;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.ignore_error")
    private Boolean ignoreError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_weight")
    private Boolean checkWeight;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.is_enable")
    private Boolean isEnable;


    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_name")
    public String getJobName() {
        return jobName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_name")
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_type")
    public String getJobType() {
        return jobType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.job_type")
    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.234+08:00", comments="Source field: uds_job_config.offset_day")
    public Byte getOffsetDay() {
        return offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.offset_day")
    public void setOffsetDay(Byte offsetDay) {
        this.offsetDay = offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.time_window")
    public String getTimeWindow() {
        return timeWindow;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.time_window")
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow == null ? null : timeWindow.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.virtual_enable")
    public Byte getVirtualEnable() {
        return virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.virtual_enable")
    public void setVirtualEnable(Byte virtualEnable) {
        this.virtualEnable = virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.priority")
    public Integer getPriority() {
        return priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.priority")
    public void setPriority(Integer priority) {
        this.priority = priority == null ? null : priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_max_num")
    public Byte getCallAgainMaxNum() {
        return callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_max_num")
    public void setCallAgainMaxNum(Byte callAgainMaxNum) {
        this.callAgainMaxNum = callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    public Byte getCallAgainWaitSec() {
        return callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    public void setCallAgainWaitSec(Byte callAgainWaitSec) {
        this.callAgainWaitSec = callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.count_batch")
    public Integer getCountBatch() {
        return countBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.count_batch")
    public void setCountBatch(Integer countBatch) {
        this.countBatch = countBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.batch_conversion")
    public Integer getBatchConversion() {
        return batchConversion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.batch_conversion")
    public void setBatchConversion(Integer batchConversion) {
        this.batchConversion = batchConversion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_frequency")
    public Boolean getCheckFrequency() {
        return checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_frequency")
    public void setCheckFrequency(Boolean checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_time_trigger")
    public Boolean getCheckTimeTrigger() {
        return checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_time_trigger")
    public void setCheckTimeTrigger(Boolean checkTimeTrigger) {
        this.checkTimeTrigger = checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_stream_self")
    public Boolean getCheckStreamSelf() {
        return checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_stream_self")
    public void setCheckStreamSelf(Boolean checkStreamSelf) {
        this.checkStreamSelf = checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.ignore_error")
    public Boolean getIgnoreError() {
        return ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.ignore_error")
    public void setIgnoreError(Boolean ignoreError) {
        this.ignoreError = ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_weight")
    public Boolean getCheckWeight() {
        return checkWeight;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.check_weight")
    public void setCheckWeight(Boolean checkWeight) {
        this.checkWeight = checkWeight;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.235+08:00", comments="Source field: uds_job_config.is_enable")
    public Boolean getEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.236+08:00", comments="Source field: uds_job_config.is_enable")
    public void setEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

	//查询上下游作业的父作业
    private String pjob;
    private Boolean depIsEnable;
    private Integer depBatch;
    private Long pid;
    
	public Boolean getdepIsEnable() {
		return depIsEnable;
	}
	public void setdepIsEnable(Boolean depIsEnable) {
		this.depIsEnable = depIsEnable;
	}
	public String getPjob() {
		return pjob;
	}
	public void setPjob(String pjob) {
		this.pjob = pjob;
	}
	public Integer getDepBatch() {
		return depBatch;
	}
	public void setDepBatch(Integer depBatch) {
		this.depBatch = depBatch;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}

}