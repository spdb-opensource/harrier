package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MUserRoleDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    public static final MUserRole MUserRole = new MUserRole();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.user_id")
    public static final SqlColumn<Long> userId = MUserRole.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.role_id")
    public static final SqlColumn<Integer> roleId = MUserRole.roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    public static final class MUserRole extends SqlTable {
        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public MUserRole() {
            super("m_user_role");
        }
    }
}