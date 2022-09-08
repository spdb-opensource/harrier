package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MAlarmDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source Table: m_alarm")
    public static final MAlarm MAlarm = new MAlarm();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.id")
    public static final SqlColumn<Long> id = MAlarm.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source field: m_alarm.platform")
    public static final SqlColumn<String> platform = MAlarm.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.systems")
    public static final SqlColumn<String> systems = MAlarm.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.job")
    public static final SqlColumn<String> job = MAlarm.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.code")
    public static final SqlColumn<String> code = MAlarm.code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.alarm_type")
    public static final SqlColumn<String> alarmType = MAlarm.alarmType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.alarm_level")
    public static final SqlColumn<String> alarmLevel = MAlarm.alarmLevel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.times")
    public static final SqlColumn<LocalDateTime> times = MAlarm.times;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.alarm_status")
    public static final SqlColumn<String> alarmStatus = MAlarm.alarmStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.src_content")
    public static final SqlColumn<String> srcContent = MAlarm.srcContent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.src_param")
    public static final SqlColumn<String> srcParam = MAlarm.srcParam;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.386+08:00", comments="Source field: m_alarm.title")
    public static final SqlColumn<String> title = MAlarm.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.content")
    public static final SqlColumn<String> content = MAlarm.content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.notice_group_name")
    public static final SqlColumn<String> noticeGroupName = MAlarm.noticeGroupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.notice_count")
    public static final SqlColumn<Integer> noticeCount = MAlarm.noticeCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.notice_times")
    public static final SqlColumn<Integer> noticeTimes = MAlarm.noticeTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.notice_cyce")
    public static final SqlColumn<Integer> noticeCyce = MAlarm.noticeCyce;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.notice_send_time")
    public static final SqlColumn<LocalDateTime> noticeSendTime = MAlarm.noticeSendTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.operation_type")
    public static final SqlColumn<String> operationType = MAlarm.operationType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.operation_time")
    public static final SqlColumn<LocalDateTime> operationTime = MAlarm.operationTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.operation_user")
    public static final SqlColumn<String> operationUser = MAlarm.operationUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source field: m_alarm.remark")
    public static final SqlColumn<String> remark = MAlarm.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.385+08:00", comments="Source Table: m_alarm")
    public static final class MAlarm extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> code = column("code", JDBCType.VARCHAR);

        public final SqlColumn<String> alarmType = column("alarm_type", JDBCType.VARCHAR);

        public final SqlColumn<String> alarmLevel = column("alarm_level", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> times = column("times", JDBCType.TIMESTAMP);

        public final SqlColumn<String> alarmStatus = column("alarm_status", JDBCType.VARCHAR);

        public final SqlColumn<String> srcContent = column("src_content", JDBCType.VARCHAR);

        public final SqlColumn<String> srcParam = column("src_param", JDBCType.VARCHAR);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> content = column("content", JDBCType.VARCHAR);

        public final SqlColumn<String> noticeGroupName = column("notice_group_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> noticeCount = column("notice_count", JDBCType.INTEGER);

        public final SqlColumn<Integer> noticeTimes = column("notice_times", JDBCType.INTEGER);

        public final SqlColumn<Integer> noticeCyce = column("notice_cyce", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> noticeSendTime = column("notice_send_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> operationType = column("operation_type", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> operationTime = column("operation_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> operationUser = column("operation_user", JDBCType.VARCHAR);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MAlarm() {
            super("m_alarm");
        }
    }
}