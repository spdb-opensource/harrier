package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJobStepRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.job_record_id")
    private Long jobRecordId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.num_times")
    private Long numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.step_num")
    private Byte stepNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.return_code")
    private Integer returnCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.address")
    private String address;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.environments")
    private String environments;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.cmd")
    private String cmd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.script_path")
    private String scriptPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.parameter")
    private String parameter;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.start_time")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.end_time")
    private LocalDateTime endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.log_path")
    private String logPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.job_record_id")
    public Long getJobRecordId() {
        return jobRecordId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.job_record_id")
    public void setJobRecordId(Long jobRecordId) {
        this.jobRecordId = jobRecordId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source field: uds_job_step_record.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.num_times")
    public Long getNumTimes() {
        return numTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.num_times")
    public void setNumTimes(Long numTimes) {
        this.numTimes = numTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.step_num")
    public Byte getStepNum() {
        return stepNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.step_num")
    public void setStepNum(Byte stepNum) {
        this.stepNum = stepNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.return_code")
    public Integer getReturnCode() {
        return returnCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.return_code")
    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.address")
    public String getAddress() {
        return address;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.address")
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.environments")
    public String getEnvironments() {
        return environments;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.environments")
    public void setEnvironments(String environments) {
        this.environments = environments == null ? null : environments.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.cmd")
    public String getCmd() {
        return cmd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.cmd")
    public void setCmd(String cmd) {
        this.cmd = cmd == null ? null : cmd.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.script_path")
    public String getScriptPath() {
        return scriptPath;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.script_path")
    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath == null ? null : scriptPath.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.parameter")
    public String getParameter() {
        return parameter;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.802+08:00", comments="Source field: uds_job_step_record.parameter")
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.start_time")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.end_time")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.log_path")
    public String getLogPath() {
        return logPath;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.log_path")
    public void setLogPath(String logPath) {
        this.logPath = logPath == null ? null : logPath.trim();
    }
}