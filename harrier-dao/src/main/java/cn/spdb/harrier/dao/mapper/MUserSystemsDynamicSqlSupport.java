package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MUserSystemsDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source Table: m_user_systems")
    public static final MUserSystems MUserSystems = new MUserSystems();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_systems.user_id")
    public static final SqlColumn<Long> userId = MUserSystems.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_systems.systems_id")
    public static final SqlColumn<Integer> systemsId = MUserSystems.systemsId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_systems.permissions")
    public static final SqlColumn<String> permissions = MUserSystems.permissions;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source Table: m_user_systems")
    public static final class MUserSystems extends SqlTable {
        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> systemsId = column("systems_id", JDBCType.INTEGER);

        public final SqlColumn<String> permissions = column("permissions", JDBCType.VARCHAR);

        public MUserSystems() {
            super("m_user_systems");
        }
    }
}