package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MAlarmSendQueue {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.37+08:00", comments="Source field: m_alarm_send_queue.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.37+08:00", comments="Source field: m_alarm_send_queue.alarm_id")
    private Long alarmId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.group_name")
    private String groupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_status")
    private String sendStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_type")
    private String sendType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_params")
    private String sendParams;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.content")
    private String content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.file_path")
    private String filePath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.expcetion")
    private String expcetion;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.send_time")
    private LocalDateTime sendTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.37+08:00", comments="Source field: m_alarm_send_queue.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.37+08:00", comments="Source field: m_alarm_send_queue.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.37+08:00", comments="Source field: m_alarm_send_queue.alarm_id")
    public Long getAlarmId() {
        return alarmId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.alarm_id")
    public void setAlarmId(Long alarmId) {
        this.alarmId = alarmId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.group_name")
    public String getGroupName() {
        return groupName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_status")
    public String getSendStatus() {
        return sendStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_status")
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_type")
    public String getSendType() {
        return sendType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_type")
    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_params")
    public String getSendParams() {
        return sendParams;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.send_params")
    public void setSendParams(String sendParams) {
        this.sendParams = sendParams == null ? null : sendParams.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.title")
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.content")
    public String getContent() {
        return content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.371+08:00", comments="Source field: m_alarm_send_queue.content")
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.file_path")
    public String getFilePath() {
        return filePath;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.file_path")
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.expcetion")
    public String getExpcetion() {
        return expcetion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.expcetion")
    public void setExpcetion(String expcetion) {
        this.expcetion = expcetion == null ? null : expcetion.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.send_time")
    public LocalDateTime getSendTime() {
        return sendTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.372+08:00", comments="Source field: m_alarm_send_queue.send_time")
    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "MAlarmSendQueue [id=" + id + ", alarmId=" + alarmId + ", groupName=" + groupName + ", sendStatus="
				+ sendStatus + ", sendType=" + sendType + "]";
	}
    
}