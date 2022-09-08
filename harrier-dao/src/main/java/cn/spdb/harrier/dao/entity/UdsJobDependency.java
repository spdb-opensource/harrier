package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobDependency {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.dep_platform")
    private String depPlatform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_system")
    private String depSystem;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_job")
    private String depJob;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_batch")
    private Integer depBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.isEnable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.199+08:00", comments="Source field: uds_job_dependency.dep_platform")
    public String getDepPlatform() {
        return depPlatform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_platform")
    public void setDepPlatform(String depPlatform) {
        this.depPlatform = depPlatform == null ? null : depPlatform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_system")
    public String getDepSystem() {
        return depSystem;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_system")
    public void setDepSystem(String depSystem) {
        this.depSystem = depSystem == null ? null : depSystem.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_job")
    public String getDepJob() {
        return depJob;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_job")
    public void setDepJob(String depJob) {
        this.depJob = depJob == null ? null : depJob.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_batch")
    public Integer getDepBatch() {
        return depBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.dep_batch")
    public void setDepBatch(Integer depBatch) {
        this.depBatch = depBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.isEnable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.isEnable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.2+08:00", comments="Source field: uds_job_dependency.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}