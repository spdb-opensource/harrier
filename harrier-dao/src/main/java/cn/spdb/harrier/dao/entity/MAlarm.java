package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MAlarm {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_type")
    private String alarmType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_level")
    private String alarmLevel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.times")
    private LocalDateTime times;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_status")
    private String alarmStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_content")
    private String srcContent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_param")
    private String srcParam;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.content")
    private String content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.notice_group_name")
    private String noticeGroupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.notice_count")
    private Integer noticeCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_times")
    private Integer noticeTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_cyce")
    private Integer noticeCyce;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_send_time")
    private LocalDateTime noticeSendTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_type")
    private String operationType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone= "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_time")
    private LocalDateTime operationTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_user")
    private String operationUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.code")
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_type")
    public String getAlarmType() {
        return alarmType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_type")
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType == null ? null : alarmType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_level")
    public String getAlarmLevel() {
        return alarmLevel;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_level")
    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel == null ? null : alarmLevel.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.times")
    public LocalDateTime getTimes() {
        return times;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.times")
    public void setTimes(LocalDateTime times) {
        this.times = times;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source field: m_alarm.alarm_status")
    public String getAlarmStatus() {
        return alarmStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.alarm_status")
    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus == null ? null : alarmStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_content")
    public String getSrcContent() {
        return srcContent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_content")
    public void setSrcContent(String srcContent) {
        this.srcContent = srcContent == null ? null : srcContent.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_param")
    public String getSrcParam() {
        return srcParam;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.src_param")
    public void setSrcParam(String srcParam) {
        this.srcParam = srcParam == null ? null : srcParam.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.383+08:00", comments="Source field: m_alarm.title")
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.content")
    public String getContent() {
        return content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.content")
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.notice_group_name")
    public String getNoticeGroupName() {
        return noticeGroupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.384+08:00", comments="Source field: m_alarm.notice_group_name")
    public void setNoticeGroupName(String noticeGroupName) {
        this.noticeGroupName = noticeGroupName == null ? null : noticeGroupName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_count")
    public Integer getNoticeCount() {
        return noticeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_count")
    public void setNoticeCount(Integer noticeCount) {
        this.noticeCount = noticeCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_times")
    public Integer getNoticeTimes() {
        return noticeTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_times")
    public void setNoticeTimes(Integer noticeTimes) {
        this.noticeTimes = noticeTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_cyce")
    public Integer getNoticeCyce() {
        return noticeCyce;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_cyce")
    public void setNoticeCyce(Integer noticeCyce) {
        this.noticeCyce = noticeCyce;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_send_time")
    public LocalDateTime getNoticeSendTime() {
        return noticeSendTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.notice_send_time")
    public void setNoticeSendTime(LocalDateTime noticeSendTime) {
        this.noticeSendTime = noticeSendTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_type")
    public String getOperationType() {
        return operationType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_type")
    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_time")
    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_time")
    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_user")
    public String getOperationUser() {
        return operationUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.operation_user")
    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser == null ? null : operationUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "MAlarm [platform=" + platform + ", systems=" + systems + ", job=" + job + ", code=" + code + ", title="
				+ title + ", content=" + content + "]";
	}
    
    

    
}