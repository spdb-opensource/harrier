package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MAlarmUserGroup {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.group_name")
    private String groupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.send_type")
    private String sendType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.send_params")
    private String sendParams;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.group_name")
    public String getGroupName() {
        return groupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.344+08:00", comments="Source field: m_alarm_user_group.send_type")
    public String getSendType() {
        return sendType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.send_type")
    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.send_params")
    public String getSendParams() {
        return sendParams;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.send_params")
    public void setSendParams(String sendParams) {
        this.sendParams = sendParams == null ? null : sendParams.trim();
    }
}