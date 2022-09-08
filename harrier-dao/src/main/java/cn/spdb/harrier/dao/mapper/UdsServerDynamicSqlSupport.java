package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsServerDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source Table: uds_server")
    public static final UdsServer udsServer = new UdsServer();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.id")
    public static final SqlColumn<Integer> id = udsServer.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.server_role_name")
    public static final SqlColumn<String> serverRoleName = udsServer.serverRoleName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.server_role_name_group")
    public static final SqlColumn<String> serverRoleNameGroup = udsServer.serverRoleNameGroup;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.node_cluster_type")
    public static final SqlColumn<String> nodeClusterType = udsServer.nodeClusterType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.server_name")
    public static final SqlColumn<String> serverName = udsServer.serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.ip")
    public static final SqlColumn<String> ip = udsServer.ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.port")
    public static final SqlColumn<Integer> port = udsServer.port;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.last_start")
    public static final SqlColumn<LocalDateTime> lastStart = udsServer.lastStart;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = udsServer.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.61+08:00", comments="Source field: uds_server.last_end")
    public static final SqlColumn<LocalDateTime> lastEnd = udsServer.lastEnd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.611+08:00", comments="Source field: uds_server.is_enable")
    public static final SqlColumn<Boolean> isEnable = udsServer.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.611+08:00", comments="Source field: uds_server.para")
    public static final SqlColumn<String> para = udsServer.para;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.611+08:00", comments="Source field: uds_server.des")
    public static final SqlColumn<String> des = udsServer.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source Table: uds_server")
    public static final class UdsServer extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> serverRoleName = column("server_role_name", JDBCType.VARCHAR);

        public final SqlColumn<String> serverRoleNameGroup = column("server_role_name_group", JDBCType.VARCHAR);

        public final SqlColumn<String> nodeClusterType = column("node_cluster_type", JDBCType.VARCHAR);

        public final SqlColumn<String> serverName = column("server_name", JDBCType.VARCHAR);

        public final SqlColumn<String> ip = column("ip", JDBCType.VARCHAR);

        public final SqlColumn<Integer> port = column("port", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastStart = column("last_start", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> lastEnd = column("last_end", JDBCType.TIMESTAMP);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> para = column("para", JDBCType.VARCHAR);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsServer() {
            super("uds_server");
        }
    }
}