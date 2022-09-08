package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobSource {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.748+08:00", comments="Source field: uds_job_source.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.signal")
    private String signals;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.753+08:00", comments="Source field: uds_job_source.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.754+08:00", comments="Source field: uds_job_source.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.signal")
    public String getSignals() {
        return signals;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.signal")
    public void setSignals(String signal) {
        this.signals = signal == null ? null : signal.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.755+08:00", comments="Source field: uds_job_source.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.756+08:00", comments="Source field: uds_job_source.enable")
    public void setIsEnable(Boolean enable) {
        this.isEnable = enable;
    }
}