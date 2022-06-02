package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MAlarmConfigDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source Table: m_alarm_config")
    public static final MAlarmConfig MAlarmConfig = new MAlarmConfig();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.id")
    public static final SqlColumn<Integer> id = MAlarmConfig.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.code")
    public static final SqlColumn<String> code = MAlarmConfig.code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.platform")
    public static final SqlColumn<String> platform = MAlarmConfig.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.systems")
    public static final SqlColumn<String> systems = MAlarmConfig.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.job")
    public static final SqlColumn<String> job = MAlarmConfig.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.def_status")
    public static final SqlColumn<Integer> defStatus = MAlarmConfig.defStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source field: m_alarm_config.notice_times")
    public static final SqlColumn<Integer> noticeTimes = MAlarmConfig.noticeTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.notice_cycle")
    public static final SqlColumn<Integer> noticeCycle = MAlarmConfig.noticeCycle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.notice_user_groups")
    public static final SqlColumn<String> noticeGroupName = MAlarmConfig.noticeGroupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.build")
    public static final SqlColumn<Boolean> build = MAlarmConfig.build;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.is_enable")
    public static final SqlColumn<Boolean> isEnable = MAlarmConfig.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.update_user")
    public static final SqlColumn<String> updateUser = MAlarmConfig.updateUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = MAlarmConfig.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.361+08:00", comments="Source field: m_alarm_config.remark")
    public static final SqlColumn<String> remark = MAlarmConfig.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.36+08:00", comments="Source Table: m_alarm_config")
    public static final class MAlarmConfig extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> code = column("code", JDBCType.VARCHAR);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Integer> defStatus = column("def_status", JDBCType.INTEGER);

        public final SqlColumn<Integer> noticeTimes = column("notice_times", JDBCType.INTEGER);

        public final SqlColumn<Integer> noticeCycle = column("notice_cycle", JDBCType.INTEGER);

        public final SqlColumn<String> noticeGroupName = column("notice_group_name", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> build = column("build", JDBCType.BIT);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> updateUser = column("update_user", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MAlarmConfig() {
            super("m_alarm_config");
        }
    }
}