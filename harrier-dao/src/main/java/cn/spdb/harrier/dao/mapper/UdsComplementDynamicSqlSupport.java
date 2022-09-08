package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UdsComplementDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source Table: uds_complement")
    public static final UdsComplement udsComplement = new UdsComplement();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source field: uds_complement.id")
    public static final SqlColumn<Long> id = udsComplement.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source field: uds_complement.com_name")
    public static final SqlColumn<String> comName = udsComplement.comName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source field: uds_complement.start_time")
    public static final SqlColumn<LocalDateTime> startTime = udsComplement.startTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source field: uds_complement.end_time")
    public static final SqlColumn<LocalDateTime> endTime = udsComplement.endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source field: uds_complement.last_status")
    public static final SqlColumn<String> lastStatus = udsComplement.lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source field: uds_complement.batch_range")
    public static final SqlColumn<String> batchRange = udsComplement.batchRange;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source field: uds_complement.server_name_range")
    public static final SqlColumn<String> serverNameRange = udsComplement.serverNameRange;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source field: uds_complement.max_run_job")
    public static final SqlColumn<Integer> maxRunJob = udsComplement.maxRunJob;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source field: uds_complement.des")
    public static final SqlColumn<String> des = udsComplement.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.904+08:00", comments="Source Table: uds_complement")
    public static final class UdsComplement extends AliasableSqlTable<UdsComplement> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> comName = column("com_name", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> lastStatus = column("last_status", JDBCType.VARCHAR);

        public final SqlColumn<String> batchRange = column("batch_range", JDBCType.VARCHAR);

        public final SqlColumn<String> serverNameRange = column("server_name_range", JDBCType.VARCHAR);

        public final SqlColumn<Integer> maxRunJob = column("max_run_job", JDBCType.INTEGER);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsComplement() {
            super("uds_complement", UdsComplement::new);
        }
    }
}