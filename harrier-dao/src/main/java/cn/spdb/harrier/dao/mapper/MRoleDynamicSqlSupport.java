package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MRoleDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.92+08:00", comments="Source Table: m_role")
    public static final MRole MRole = new MRole();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.92+08:00", comments="Source field: m_role.role_id")
    public static final SqlColumn<Integer> roleId = MRole.roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.92+08:00", comments="Source field: m_role.role_name")
    public static final SqlColumn<String> roleName = MRole.roleName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.create_time")
    public static final SqlColumn<LocalDateTime> createTime = MRole.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.create_user")
    public static final SqlColumn<String> createUser = MRole.createUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = MRole.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.update_user")
    public static final SqlColumn<String> updateUser = MRole.updateUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.is_enable")
    public static final SqlColumn<Boolean> isEnable = MRole.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.925+08:00", comments="Source field: m_role.remark")
    public static final SqlColumn<String> remark = MRole.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.92+08:00", comments="Source Table: m_role")
    public static final class MRole extends SqlTable {
        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public final SqlColumn<String> roleName = column("role_name", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> createUser = column("create_user", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> updateUser = column("update_user", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MRole() {
            super("m_role");
        }
    }
}