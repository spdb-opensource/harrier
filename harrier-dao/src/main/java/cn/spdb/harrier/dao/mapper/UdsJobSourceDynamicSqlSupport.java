package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobSourceDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.766+08:00", comments="Source Table: uds_job_source")
    public static final UdsJobSource udsJobSource = new UdsJobSource();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.769+08:00", comments="Source field: uds_job_source.id")
    public static final SqlColumn<Long> id = udsJobSource.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.77+08:00", comments="Source field: uds_job_source.signal")
    public static final SqlColumn<String> signal = udsJobSource.signal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.77+08:00", comments="Source field: uds_job_source.platform")
    public static final SqlColumn<String> platform = udsJobSource.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.77+08:00", comments="Source field: uds_job_source.systems")
    public static final SqlColumn<String> systems = udsJobSource.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.77+08:00", comments="Source field: uds_job_source.job")
    public static final SqlColumn<String> job = udsJobSource.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.77+08:00", comments="Source field: uds_job_source.enable")
    public static final SqlColumn<Boolean> isEnable = udsJobSource.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.768+08:00", comments="Source Table: uds_job_source")
    public static final class UdsJobSource extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> signal = column("signal", JDBCType.VARCHAR);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public UdsJobSource() {
            super("uds_job_source");
        }
    }
}