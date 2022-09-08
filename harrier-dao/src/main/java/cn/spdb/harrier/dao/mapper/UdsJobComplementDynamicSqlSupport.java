package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UdsJobComplementDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.861+08:00", comments="Source Table: uds_job_complement")
    public static final UdsJobComplement udsJobComplement = new UdsJobComplement();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.864+08:00", comments="Source field: uds_job_complement.id")
    public static final SqlColumn<Long> id = udsJobComplement.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.865+08:00", comments="Source field: uds_job_complement.complement_id")
    public static final SqlColumn<Long> complementId = udsJobComplement.complementId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.865+08:00", comments="Source field: uds_job_complement.platform")
    public static final SqlColumn<String> platform = udsJobComplement.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.865+08:00", comments="Source field: uds_job_complement.systems")
    public static final SqlColumn<String> systems = udsJobComplement.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.865+08:00", comments="Source field: uds_job_complement.job")
    public static final SqlColumn<String> job = udsJobComplement.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.job_date")
    public static final SqlColumn<LocalDate> jobDate = udsJobComplement.jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.last_status")
    public static final SqlColumn<String> lastStatus = udsJobComplement.lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.server_name")
    public static final SqlColumn<String> serverName = udsJobComplement.serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.multi_batch")
    public static final SqlColumn<Integer> multiBatch = udsJobComplement.multiBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.start_time")
    public static final SqlColumn<LocalDateTime> startTime = udsJobComplement.startTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.end_time")
    public static final SqlColumn<LocalDateTime> endTime = udsJobComplement.endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.866+08:00", comments="Source field: uds_job_complement.des")
    public static final SqlColumn<String> des = udsJobComplement.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.864+08:00", comments="Source Table: uds_job_complement")
    public static final class UdsJobComplement extends AliasableSqlTable<UdsJobComplement> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> complementId = column("complement_id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<LocalDate> jobDate = column("job_date", JDBCType.DATE);

        public final SqlColumn<String> lastStatus = column("last_status", JDBCType.VARCHAR);

        public final SqlColumn<String> serverName = column("server_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> multiBatch = column("multi_batch", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobComplement() {
            super("uds_job_complement", UdsJobComplement::new);
        }
    }
}