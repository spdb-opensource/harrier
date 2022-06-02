package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MAlarmUserGroupDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source Table: m_alarm_user_group")
    public static final MAlarmUserGroup MAlarmUserGroup = new MAlarmUserGroup();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.id")
    public static final SqlColumn<Integer> id = MAlarmUserGroup.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source field: m_alarm_user_group.platform")
    public static final SqlColumn<String> platform = MAlarmUserGroup.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source field: m_alarm_user_group.systems")
    public static final SqlColumn<String> systems = MAlarmUserGroup.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source field: m_alarm_user_group.group_name")
    public static final SqlColumn<String> groupName = MAlarmUserGroup.groupName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source field: m_alarm_user_group.send_type")
    public static final SqlColumn<String> sendType = MAlarmUserGroup.sendType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source field: m_alarm_user_group.send_params")
    public static final SqlColumn<String> sendParams = MAlarmUserGroup.sendParams;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.345+08:00", comments="Source Table: m_alarm_user_group")
    public static final class MAlarmUserGroup extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> groupName = column("group_name", JDBCType.VARCHAR);

        public final SqlColumn<String> sendType = column("send_type", JDBCType.VARCHAR);

        public final SqlColumn<String> sendParams = column("send_params", JDBCType.VARCHAR);

        public MAlarmUserGroup() {
            super("m_alarm_user_group");
        }
    }
}