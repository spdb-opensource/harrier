package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobDateFrequency {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.209+08:00", comments="Source field: uds_job_date_frequency.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.209+08:00", comments="Source field: uds_job_date_frequency.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.crontab")
    private String crontab;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.209+08:00", comments="Source field: uds_job_date_frequency.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.209+08:00", comments="Source field: uds_job_date_frequency.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.21+08:00", comments="Source field: uds_job_date_frequency.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.crontab")
    public String getCrontab() {
        return crontab;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.crontab")
    public void setCrontab(String crontab) {
        this.crontab = crontab == null ? null : crontab.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.211+08:00", comments="Source field: uds_job_date_frequency.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}