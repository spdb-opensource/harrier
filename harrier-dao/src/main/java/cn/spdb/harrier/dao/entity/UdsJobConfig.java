package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobConfig {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.496+08:00", comments="Source field: uds_job_config.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.job_name")
    private String jobName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.job_type")
    private String jobType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.offset_day")
    private Byte offsetDay;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.time_window")
    private String timeWindow;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.virtual_enable")
    private Boolean virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.priority")
    private Integer priority;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_max_num")
    private Byte callAgainMaxNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    private Byte callAgainWaitSec;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.count_batch")
    private Integer countBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.batch_conversion")
    private Integer batchConversion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.check_frequency")
    private Boolean checkFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_time_trigger")
    private Boolean checkTimeTrigger;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_stream_self")
    private Boolean checkStreamSelf;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.ignore_error")
    private Boolean ignoreError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_weight")
    private Boolean checkWeight;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.509+08:00", comments="Source field: uds_job_config.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.504+08:00", comments="Source field: uds_job_config.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.505+08:00", comments="Source field: uds_job_config.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.job_name")
    public String getJobName() {
        return jobName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.job_name")
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.job_type")
    public String getJobType() {
        return jobType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.job_type")
    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.offset_day")
    public Byte getOffsetDay() {
        return offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.offset_day")
    public void setOffsetDay(Byte offsetDay) {
        this.offsetDay = offsetDay;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.time_window")
    public String getTimeWindow() {
        return timeWindow;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.time_window")
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow == null ? null : timeWindow.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.virtual_enable")
    public Boolean getVirtualEnable() {
        return virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.506+08:00", comments="Source field: uds_job_config.virtual_enable")
    public void setVirtualEnable(Boolean virtualEnable) {
        this.virtualEnable = virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.priority")
    public Integer getPriority() {
        return priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_max_num")
    public Byte getCallAgainMaxNum() {
        return callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_max_num")
    public void setCallAgainMaxNum(Byte callAgainMaxNum) {
        this.callAgainMaxNum = callAgainMaxNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    public Byte getCallAgainWaitSec() {
        return callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    public void setCallAgainWaitSec(Byte callAgainWaitSec) {
        this.callAgainWaitSec = callAgainWaitSec;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.count_batch")
    public Integer getCountBatch() {
        return countBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.count_batch")
    public void setCountBatch(Integer countBatch) {
        this.countBatch = countBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.batch_conversion")
    public Integer getBatchConversion() {
        return batchConversion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.507+08:00", comments="Source field: uds_job_config.batch_conversion")
    public void setBatchConversion(Integer batchConversion) {
        this.batchConversion = batchConversion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_frequency")
    public Boolean getCheckFrequency() {
        return checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_frequency")
    public void setCheckFrequency(Boolean checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_time_trigger")
    public Boolean getCheckTimeTrigger() {
        return checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_time_trigger")
    public void setCheckTimeTrigger(Boolean checkTimeTrigger) {
        this.checkTimeTrigger = checkTimeTrigger;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_stream_self")
    public Boolean getCheckStreamSelf() {
        return checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_stream_self")
    public void setCheckStreamSelf(Boolean checkStreamSelf) {
        this.checkStreamSelf = checkStreamSelf;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.ignore_error")
    public Boolean getIgnoreError() {
        return ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.ignore_error")
    public void setIgnoreError(Boolean ignoreError) {
        this.ignoreError = ignoreError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_weight")
    public Boolean getCheckWeight() {
        return checkWeight;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.508+08:00", comments="Source field: uds_job_config.check_weight")
    public void setCheckWeight(Boolean checkWeight) {
        this.checkWeight = checkWeight;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.509+08:00", comments="Source field: uds_job_config.enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.509+08:00", comments="Source field: uds_job_config.enable")
    public void setIsEnable(Boolean enable) {
        this.isEnable = enable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.509+08:00", comments="Source field: uds_job_config.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.509+08:00", comments="Source field: uds_job_config.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}