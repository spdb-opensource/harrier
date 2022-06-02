package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MAlarmMsg {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.alarm_type")
    private String alarmType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.alarm_level")
    private String alarmLevel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.content")
    private String content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.code")
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.alarm_type")
    public String getAlarmType() {
        return alarmType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source field: m_alarm_msg.alarm_type")
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType == null ? null : alarmType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.alarm_level")
    public String getAlarmLevel() {
        return alarmLevel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.alarm_level")
    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel == null ? null : alarmLevel.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.title")
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.content")
    public String getContent() {
        return content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.content")
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source field: m_alarm_msg.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}