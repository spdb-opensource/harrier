package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MAlarmConfig {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.def_status")
    private String defStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_times")
    private Integer noticeTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_cycle")
    private Integer noticeCycle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_user_groups")
    private String noticeGroupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.build")
    private Boolean build;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_user")
    private String updateUser;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone= "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_time")
    private LocalDateTime updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.code")
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.358+08:00", comments="Source field: m_alarm_config.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.def_status")
    public String getDefStatus() {
        return defStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.def_status")
    public void setDefStatus(String defStatus) {
        this.defStatus = defStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_times")
    public Integer getNoticeTimes() {
        return noticeTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_times")
    public void setNoticeTimes(Integer noticeTimes) {
        this.noticeTimes = noticeTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_cycle")
    public Integer getNoticeCycle() {
        return noticeCycle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_cycle")
    public void setNoticeCycle(Integer noticeCycle) {
        this.noticeCycle = noticeCycle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_user_groups")
    public String getNoticeGroupName() {
        return noticeGroupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.notice_user_groups")
    public void setNoticeGroupName(String noticeUserGroups) {
        this.noticeGroupName = noticeUserGroups == null ? null : noticeUserGroups.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.build")
    public Boolean getBuild() {
        return build;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.build")
    public void setBuild(Boolean build) {
        this.build = build;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.is_enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.is_enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_user")
    public String getUpdateUser() {
        return updateUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_user")
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.359+08:00", comments="Source field: m_alarm_config.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}