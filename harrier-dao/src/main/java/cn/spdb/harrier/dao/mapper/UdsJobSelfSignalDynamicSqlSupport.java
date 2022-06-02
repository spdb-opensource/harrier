package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobSelfSignalDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.708+08:00", comments="Source Table: uds_ job_self_signal")
    public static final UdsJobSelfSignal udsJobSelfSignal = new UdsJobSelfSignal();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.71+08:00", comments="Source field: uds_ job_self_signal.id")
    public static final SqlColumn<Integer> id = udsJobSelfSignal.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.713+08:00", comments="Source field: uds_ job_self_signal.signal")
    public static final SqlColumn<String> signal = udsJobSelfSignal.signal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.713+08:00", comments="Source field: uds_ job_self_signal.platform")
    public static final SqlColumn<String> platform = udsJobSelfSignal.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.713+08:00", comments="Source field: uds_ job_self_signal.systems")
    public static final SqlColumn<String> systems = udsJobSelfSignal.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.714+08:00", comments="Source field: uds_ job_self_signal.job")
    public static final SqlColumn<String> job = udsJobSelfSignal.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.714+08:00", comments="Source field: uds_ job_self_signal.create_time")
    public static final SqlColumn<LocalDateTime> createTime = udsJobSelfSignal.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.714+08:00", comments="Source field: uds_ job_self_signal.batch")
    public static final SqlColumn<Integer> batch = udsJobSelfSignal.batch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.714+08:00", comments="Source field: uds_ job_self_signal.job_date")
    public static final SqlColumn<LocalDate> jobDate = udsJobSelfSignal.jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.714+08:00", comments="Source field: uds_ job_self_signal.evns")
    public static final SqlColumn<String> evns = udsJobSelfSignal.evns;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.715+08:00", comments="Source field: uds_ job_self_signal.params")
    public static final SqlColumn<String> params = udsJobSelfSignal.params;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.715+08:00", comments="Source field: uds_ job_self_signal.useful")
    public static final SqlColumn<Boolean> useful = udsJobSelfSignal.useful;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.71+08:00", comments="Source Table: uds_ job_self_signal")
    public static final class UdsJobSelfSignal extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> signal = column("signal", JDBCType.VARCHAR);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> batch = column("batch", JDBCType.INTEGER);

        public final SqlColumn<LocalDate> jobDate = column("job_date", JDBCType.DATE);

        public final SqlColumn<String> evns = column("evns", JDBCType.VARCHAR);

        public final SqlColumn<String> params = column("params", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> useful = column("useful", JDBCType.BIT);

        public UdsJobSelfSignal() {
            super("\"uds_ job_self_signal\"");
        }
    }
}