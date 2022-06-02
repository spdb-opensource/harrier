package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobWeight {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.limit_type")
    private Integer limitType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.conf_value")
    private Integer confValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.desc")
    private String desc;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.387+08:00", comments="Source field: uds_job_weight.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.limit_type")
    public Integer getLimitType() {
        return limitType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.limit_type")
    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.388+08:00", comments="Source field: uds_job_weight.conf_value")
    public Integer getConfValue() {
        return confValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.conf_value")
    public void setConfValue(Integer confValue) {
        this.confValue = confValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.desc")
    public String getDesc() {
        return desc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.desc")
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}