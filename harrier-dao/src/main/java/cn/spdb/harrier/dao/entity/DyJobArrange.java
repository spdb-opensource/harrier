package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DyJobArrange {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.task_status")
    private Integer taskStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.process_status")
    private Integer processStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.sync_status")
    private Integer syncStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.start_date")
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.version")
    private Integer version;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.deploy_yaml")
    private String deployYaml;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.task_status")
    public Integer getTaskStatus() {
        return taskStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.task_status")
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.process_status")
    public Integer getProcessStatus() {
        return processStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.process_status")
    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.sync_status")
    public Integer getSyncStatus() {
        return syncStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.sync_status")
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.start_date")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.start_date")
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.end_date")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.end_date")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.version")
    public Integer getVersion() {
        return version;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.deploy_yaml")
    public String getDeployYaml() {
        return deployYaml;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.487+08:00", comments="Source field: dy_job_arrange.deploy_yaml")
    public void setDeployYaml(String deployYaml) {
        this.deployYaml = deployYaml == null ? null : deployYaml.trim();
    }
}