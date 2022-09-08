package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsJobStep {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.127+08:00", comments="Source field: uds_job_step.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_type")
    private String stepType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_num")
    private Byte stepNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.oper_cmd")
    private String operCmd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.script_path")
    private String scriptPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.parameter")
    private String parameter;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.environments")
    private String environments;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.work_dir")
    private String workDir;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.132+08:00", comments="Source field: uds_job_step.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.134+08:00", comments="Source field: uds_job_step.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_type")
    public String getStepType() {
        return stepType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_type")
    public void setStepType(String stepType) {
        this.stepType = stepType == null ? null : stepType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_num")
    public Byte getStepNum() {
        return stepNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.step_num")
    public void setStepNum(Byte stepNum) {
        this.stepNum = stepNum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.oper_cmd")
    public String getOperCmd() {
        return operCmd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.oper_cmd")
    public void setOperCmd(String operCmd) {
        this.operCmd = operCmd == null ? null : operCmd.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.script_path")
    public String getScriptPath() {
        return scriptPath;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.script_path")
    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath == null ? null : scriptPath.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.parameter")
    public String getParameter() {
        return parameter;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.135+08:00", comments="Source field: uds_job_step.parameter")
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.environments")
    public String getEnvironments() {
        return environments;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.environments")
    public void setEnvironments(String environments) {
        this.environments = environments == null ? null : environments.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.work_dir")
    public String getWorkDir() {
        return workDir;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.work_dir")
    public void setWorkDir(String workDir) {
        this.workDir = workDir == null ? null : workDir.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.136+08:00", comments="Source field: uds_job_step.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
    
    
}