package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MRoleMenuDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    public static final MRoleMenu MRoleMenu = new MRoleMenu();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.role_id")
    public static final SqlColumn<Integer> roleId = MRoleMenu.roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.menu_id")
    public static final SqlColumn<Integer> menuId = MRoleMenu.menuId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    public static final class MRoleMenu extends SqlTable {
        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> menuId = column("menu_id", JDBCType.INTEGER);

        public MRoleMenu() {
            super("m_role_menu");
        }
    }
}