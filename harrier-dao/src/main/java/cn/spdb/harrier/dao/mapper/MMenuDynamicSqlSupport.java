package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MMenuDynamicSqlSupport {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	public static final MMenu MMenu = new MMenu();

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_id")
	public static final SqlColumn<Integer> menuId = MMenu.menuId;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_name")
	public static final SqlColumn<String> menuName = MMenu.menuName;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.parent_menu_id")
	public static final SqlColumn<Integer> parentMenuId = MMenu.parentMenuId;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_no")
	public static final SqlColumn<Integer> menuNo = MMenu.menuNo;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_url")
	public static final SqlColumn<String> menuUrl = MMenu.menuUrl;

	public static final SqlColumn<String> menuIconUrl = MMenu.menuIconUrl;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_flag")
	public static final SqlColumn<Byte> menuFlag = MMenu.menuFlag;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_time")
	public static final SqlColumn<LocalDateTime> createTime = MMenu.createTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_user")
	public static final SqlColumn<String> createUser = MMenu.createUser;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_time")
	public static final SqlColumn<LocalDateTime> updateTime = MMenu.updateTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_user")
	public static final SqlColumn<String> updateUser = MMenu.updateUser;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.is_enable")
	public static final SqlColumn<Boolean> isEnable = MMenu.isEnable;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.remark")
	public static final SqlColumn<String> remark = MMenu.remark;
	
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.component")
	public static final SqlColumn<String> component = MMenu.component;
	
	
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	public static final class MMenu extends SqlTable {
		public final SqlColumn<Integer> menuId = column("menu_id", JDBCType.INTEGER);

		public final SqlColumn<String> menuName = column("menu_name", JDBCType.VARCHAR);

		public final SqlColumn<Integer> parentMenuId = column("parent_menu_id", JDBCType.INTEGER);

		public final SqlColumn<Integer> menuNo = column("menu_no", JDBCType.INTEGER);

		public final SqlColumn<String> menuUrl = column("menu_url", JDBCType.VARCHAR);
		public final SqlColumn<String> menuIconUrl = column("menu_icon_url", JDBCType.VARCHAR);

		public final SqlColumn<Byte> menuFlag = column("menu_flag", JDBCType.TINYINT);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<String> createUser = column("create_user", JDBCType.VARCHAR);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<String> updateUser = column("update_user", JDBCType.VARCHAR);

		public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

		public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);
		
		public final SqlColumn<String> component = column("component", JDBCType.VARCHAR);

		public MMenu() {
			super("m_menu");
		}
	}
}