package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJobComplement {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.845+08:00", comments="Source field: uds_job_complement.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.complement_id")
    private Long complementId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job")
    private String job;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job_date")
    private LocalDate jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.last_status")
    private String lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.853+08:00", comments="Source field: uds_job_complement.server_name")
    private String serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.multi_batch")
    private Integer multiBatch;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.start_time")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.end_time")
    private LocalDateTime endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.855+08:00", comments="Source field: uds_job_complement.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.85+08:00", comments="Source field: uds_job_complement.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.complement_id")
    public Long getComplementId() {
        return complementId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.complement_id")
    public void setComplementId(Long complementId) {
        this.complementId = complementId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.851+08:00", comments="Source field: uds_job_complement.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job_date")
    public LocalDate getJobDate() {
        return jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.852+08:00", comments="Source field: uds_job_complement.job_date")
    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.853+08:00", comments="Source field: uds_job_complement.last_status")
    public String getLastStatus() {
        return lastStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.853+08:00", comments="Source field: uds_job_complement.last_status")
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus == null ? null : lastStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.853+08:00", comments="Source field: uds_job_complement.server_name")
    public String getServerName() {
        return serverName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.853+08:00", comments="Source field: uds_job_complement.server_name")
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.multi_batch")
    public Integer getMultiBatch() {
        return multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.multi_batch")
    public void setMultiBatch(Integer multiBatch) {
        this.multiBatch = multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.854+08:00", comments="Source field: uds_job_complement.start_time")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.855+08:00", comments="Source field: uds_job_complement.end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.855+08:00", comments="Source field: uds_job_complement.end_time")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.855+08:00", comments="Source field: uds_job_complement.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.855+08:00", comments="Source field: uds_job_complement.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}