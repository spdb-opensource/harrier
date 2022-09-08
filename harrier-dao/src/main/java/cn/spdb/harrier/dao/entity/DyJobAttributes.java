package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import javax.annotation.Generated;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DyJobAttributes {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.397+08:00", comments="Source field: dy_job_attributes.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job_name")
    private String jobName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job_type")
    private String jobType;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.stream_type")
    private Integer streamType;

    @JsonFormat(pattern="yyyy-MM-dd",timezone= "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_date")
    private LocalDate jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.last_status")
    private String lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.task_status")
    private Integer taskStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.multi_batch")
    private Integer multiBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.time_window")
    private String timeWindow;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.virtual_enable")
    private Boolean virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.priority")
    private Integer priority;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_max_num")
    private Byte callAgainMaxNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_wait_sec")
    private Byte callAgainWaitSec;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_frequency")
    private Boolean checkFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_time_trigger")
    private Boolean checkTimeTrigger;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_stream_self")
    private Boolean checkStreamSelf;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_frequency")
    private String jobFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.offset_day")
    private Byte offsetDay;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.ignore_error")
    private Boolean ignoreError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.dep_job")
    private String depJob;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_step")
    private String jobStep;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job_name")
    public String getJobName() {
        return jobName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job_name")
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.402+08:00", comments="Source field: dy_job_attributes.job_type")
    public String getJobType() {
        return jobType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_type")
    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.stream_type")
    public Integer getStreamType() {
        return streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.streamType")
    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_date")
    public LocalDate getJobDate() {
        return jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_date")
    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.last_status")
    public String getLastStatus() {
        return lastStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.last_status")
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus == null ? null : lastStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.task_status")
    public Integer getTaskStatus() {
        return taskStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.task_status")
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.multi_batch")
    public Integer getMultiBatch() {
        return multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.multi_batch")
    public void setMultiBatch(Integer multiBatch) {
        this.multiBatch = multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.time_window")
    public String getTimeWindow() {
        return timeWindow;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.time_window")
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow == null ? null : timeWindow.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.virtual_enable")
    public Boolean getVirtualEnable() {
        return virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.virtual_enable")
    public void setVirtualEnable(Boolean virtualEnable) {
        this.virtualEnable = virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.priority")
    public Integer getPriority() {
        return priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_max_num")
    public Byte getCallAgainMaxNum() {
        return callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_max_num")
    public void setCallAgainMaxNum(Byte callAgainMaxNum) {
        this.callAgainMaxNum = callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_wait_sec")
    public Byte getCallAgainWaitSec() {
        return callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.call_again_wait_sec")
    public void setCallAgainWaitSec(Byte callAgainWaitSec) {
        this.callAgainWaitSec = callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_frequency")
    public Boolean getCheckFrequency() {
        return checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_frequency")
    public void setCheckFrequency(Boolean checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_time_trigger")
    public Boolean getCheckTimeTrigger() {
        return checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_time_trigger")
    public void setCheckTimeTrigger(Boolean checkTimeTrigger) {
        this.checkTimeTrigger = checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_stream_self")
    public Boolean getCheckStreamSelf() {
        return checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.check_stream_self")
    public void setCheckStreamSelf(Boolean checkStreamSelf) {
        this.checkStreamSelf = checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_frequency")
    public String getJobFrequency() {
        return jobFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_frequency")
    public void setJobFrequency(String jobFrequency) {
        this.jobFrequency = jobFrequency == null ? null : jobFrequency.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.offset_day")
    public Byte getOffsetDay() {
        return offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.offset_day")
    public void setOffsetDay(Byte offsetDay) {
        this.offsetDay = offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.ignore_error")
    public Boolean getIgnoreError() {
        return ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.ignore_error")
    public void setIgnoreError(Boolean ignoreError) {
        this.ignoreError = ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.dep_job")
    public String getDepJob() {
        return depJob;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.dep_job")
    public void setDepJob(String depJob) {
        this.depJob = depJob == null ? null : depJob.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_step")
    public String getJobStep() {
        return jobStep;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.407+08:00", comments="Source field: dy_job_attributes.job_step")
    public void setJobStep(String jobStep) {
        this.jobStep = jobStep == null ? null : jobStep.trim();
    }
}