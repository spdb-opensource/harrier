package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MAlarmSendQueueDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source Table: m_alarm_send_queue")
    public static final MAlarmSendQueue MAlarmSendQueue = new MAlarmSendQueue();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.id")
    public static final SqlColumn<Long> id = MAlarmSendQueue.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source field: m_alarm_send_queue.alarm_id")
    public static final SqlColumn<Long> alarmId = MAlarmSendQueue.alarmId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.group_name")
    public static final SqlColumn<String> groupName = MAlarmSendQueue.groupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.send_status")
    public static final SqlColumn<String> sendStatus = MAlarmSendQueue.sendStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.send_type")
    public static final SqlColumn<String> sendType = MAlarmSendQueue.sendType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.send_params")
    public static final SqlColumn<String> sendParams = MAlarmSendQueue.sendParams;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.title")
    public static final SqlColumn<String> title = MAlarmSendQueue.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.content")
    public static final SqlColumn<String> content = MAlarmSendQueue.content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.file_path")
    public static final SqlColumn<String> filePath = MAlarmSendQueue.filePath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.374+08:00", comments="Source field: m_alarm_send_queue.expcetion")
    public static final SqlColumn<String> expcetion = MAlarmSendQueue.expcetion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source field: m_alarm_send_queue.send_time")
    public static final SqlColumn<LocalDateTime> sendTime = MAlarmSendQueue.sendTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source field: m_alarm_send_queue.create_time")
    public static final SqlColumn<LocalDateTime> createTime = MAlarmSendQueue.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source field: m_alarm_send_queue.remark")
    public static final SqlColumn<String> remark = MAlarmSendQueue.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.373+08:00", comments="Source Table: m_alarm_send_queue")
    public static final class MAlarmSendQueue extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> alarmId = column("alarm_id", JDBCType.BIGINT);

        public final SqlColumn<String> groupName = column("group_name", JDBCType.VARCHAR);

        public final SqlColumn<String> sendStatus = column("send_status", JDBCType.VARCHAR);

        public final SqlColumn<String> sendType = column("send_type", JDBCType.VARCHAR);

        public final SqlColumn<String> sendParams = column("send_params", JDBCType.VARCHAR);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> content = column("content", JDBCType.VARCHAR);

        public final SqlColumn<String> filePath = column("file_path", JDBCType.VARCHAR);

        public final SqlColumn<String> expcetion = column("expcetion", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> sendTime = column("send_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MAlarmSendQueue() {
            super("m_alarm_send_queue");
        }
    }
}