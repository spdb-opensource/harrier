package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobTimeTriggerDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.223+08:00", comments="Source Table: uds_job_time_trigger")
    public static final UdsJobTimeTrigger udsJobTimeTrigger = new UdsJobTimeTrigger();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.id")
    public static final SqlColumn<Long> id = udsJobTimeTrigger.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.platform")
    public static final SqlColumn<String> platform = udsJobTimeTrigger.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.systems")
    public static final SqlColumn<String> systems = udsJobTimeTrigger.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.job")
    public static final SqlColumn<String> job = udsJobTimeTrigger.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.start_time")
    public static final SqlColumn<LocalDateTime> startTime = udsJobTimeTrigger.startTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.end_time")
    public static final SqlColumn<LocalDateTime> endTime = udsJobTimeTrigger.endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.crontab")
    public static final SqlColumn<String> crontab = udsJobTimeTrigger.crontab;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.is_enable")
    public static final SqlColumn<Boolean> isEnable = udsJobTimeTrigger.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.224+08:00", comments="Source field: uds_job_time_trigger.des")
    public static final SqlColumn<String> des = udsJobTimeTrigger.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.223+08:00", comments="Source Table: uds_job_time_trigger")
    public static final class UdsJobTimeTrigger extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> crontab = column("crontab", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobTimeTrigger() {
            super("uds_job_time_trigger");
        }
    }
}