package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;

import javax.annotation.Generated;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsSystemDynamicSqlSupport {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source Table: uds_system")
	public static final UdsSystem udsSystem = new UdsSystem();

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.id")
	public static final SqlColumn<Integer> id = udsSystem.id;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.platform")
	public static final SqlColumn<String> platform = udsSystem.platform;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.systems")
	public static final SqlColumn<String> systems = udsSystem.systems;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.max_run_job")
	public static final SqlColumn<Short> maxRunJob = udsSystem.maxRunJob;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.select")
	public static final SqlColumn<Byte> select = udsSystem.selects;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.select_pro")
	public static final SqlColumn<String> selectPro = udsSystem.selectsPro;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.use_platform")
	public static final SqlColumn<Boolean> usePlatform = udsSystem.usePlatform;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.server_role_name_group")
	public static final SqlColumn<String> serverRoleNameGroup = udsSystem.serverRoleNameGroup;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source field: uds_system.des")
	public static final SqlColumn<String> des = udsSystem.des;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source Table: uds_system")
	public static final class UdsSystem extends SqlTable {
		public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

		public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

		public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

		public final SqlColumn<Short> maxRunJob = column("max_run_job", JDBCType.SMALLINT);

		public final SqlColumn<Byte> selects = column("selects", JDBCType.TINYINT);

		public final SqlColumn<String> selectsPro = column("selects_pro", JDBCType.VARCHAR);

		public final SqlColumn<Boolean> usePlatform = column("use_platform", JDBCType.BIT);

		public final SqlColumn<String> serverRoleNameGroup = column("server_role_name_group", JDBCType.VARCHAR);

		public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

		public UdsSystem() {
			super("uds_system");
		}
	}
}