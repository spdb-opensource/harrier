package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobDateFrequencyDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.212+08:00", comments="Source Table: uds_job_date_frequency")
    public static final UdsJobDateFrequency udsJobDateFrequency = new UdsJobDateFrequency();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.212+08:00", comments="Source field: uds_job_date_frequency.id")
    public static final SqlColumn<Long> id = udsJobDateFrequency.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.platform")
    public static final SqlColumn<String> platform = udsJobDateFrequency.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.systems")
    public static final SqlColumn<String> systems = udsJobDateFrequency.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.job")
    public static final SqlColumn<String> job = udsJobDateFrequency.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.is_enable")
    public static final SqlColumn<Boolean> isEnable = udsJobDateFrequency.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.crontab")
    public static final SqlColumn<String> crontab = udsJobDateFrequency.crontab;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source field: uds_job_date_frequency.des")
    public static final SqlColumn<String> des = udsJobDateFrequency.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.212+08:00", comments="Source Table: uds_job_date_frequency")
    public static final class UdsJobDateFrequency extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> crontab = column("crontab", JDBCType.VARCHAR);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobDateFrequency() {
            super("uds_job_date_frequency");
        }
    }
}