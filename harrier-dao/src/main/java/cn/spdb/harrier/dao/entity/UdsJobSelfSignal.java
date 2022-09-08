package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJobSelfSignal {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.69+08:00", comments="Source field: uds_ job_self_signal.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.signal")
    private String signal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.job")
    private String job;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.batch")
    private Integer batch;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.job_date")
    private LocalDate jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.evns")
    private String evns;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.params")
    private String params;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.699+08:00", comments="Source field: uds_ job_self_signal.useful")
    private Boolean useful;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.695+08:00", comments="Source field: uds_ job_self_signal.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.696+08:00", comments="Source field: uds_ job_self_signal.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.signal")
    public String getSignal() {
        return signal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.signal")
    public void setSignal(String signal) {
        this.signal = signal == null ? null : signal.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.697+08:00", comments="Source field: uds_ job_self_signal.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.batch")
    public Integer getBatch() {
        return batch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.batch")
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.job_date")
    public LocalDate getJobDate() {
        return jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.job_date")
    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.evns")
    public String getEvns() {
        return evns;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.evns")
    public void setEvns(String evns) {
        this.evns = evns == null ? null : evns.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.698+08:00", comments="Source field: uds_ job_self_signal.params")
    public String getParams() {
        return params;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.699+08:00", comments="Source field: uds_ job_self_signal.params")
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.699+08:00", comments="Source field: uds_ job_self_signal.useful")
    public Boolean getUseful() {
        return useful;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.699+08:00", comments="Source field: uds_ job_self_signal.useful")
    public void setUseful(Boolean useful) {
        this.useful = useful;
    }
}