package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MAlarmMsgDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.379+08:00", comments="Source Table: m_alarm_msg")
    public static final MAlarmMsg MAlarmMsg = new MAlarmMsg();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.id")
    public static final SqlColumn<Integer> id = MAlarmMsg.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.platform")
    public static final SqlColumn<String> platform = MAlarmMsg.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.systems")
    public static final SqlColumn<String> systems = MAlarmMsg.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.code")
    public static final SqlColumn<String> code = MAlarmMsg.code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.alarm_type")
    public static final SqlColumn<String> alarmType = MAlarmMsg.alarmType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.alarm_level")
    public static final SqlColumn<String> alarmLevel = MAlarmMsg.alarmLevel;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.title")
    public static final SqlColumn<String> title = MAlarmMsg.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.content")
    public static final SqlColumn<String> content = MAlarmMsg.content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source field: m_alarm_msg.remark")
    public static final SqlColumn<String> remark = MAlarmMsg.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    public static final class MAlarmMsg extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> code = column("code", JDBCType.VARCHAR);

        public final SqlColumn<String> alarmType = column("alarm_type", JDBCType.VARCHAR);

        public final SqlColumn<String> alarmLevel = column("alarm_level", JDBCType.VARCHAR);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> content = column("content", JDBCType.VARCHAR);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MAlarmMsg() {
            super("m_alarm_msg");
        }
    }
}