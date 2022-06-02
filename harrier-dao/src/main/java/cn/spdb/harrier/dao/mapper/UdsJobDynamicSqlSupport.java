package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source Table: uds_job")
    public static final UdsJob udsJob = new UdsJob();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.id")
    public static final SqlColumn<Long> id = udsJob.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.platform")
    public static final SqlColumn<String> platform = udsJob.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.systems")
    public static final SqlColumn<String> systems = udsJob.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.job")
    public static final SqlColumn<String> job = udsJob.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.last_status")
    public static final SqlColumn<String> lastStatus = udsJob.lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source field: uds_job.job_date")
    public static final SqlColumn<LocalDate> jobDate = udsJob.jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.next_job_date")
    public static final SqlColumn<LocalDate> nextJobDate = udsJob.nextJobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.server_name")
    public static final SqlColumn<String> serverName = udsJob.serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.multi_batch")
    public static final SqlColumn<Integer> multiBatch = udsJob.multiBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.pending_time")
    public static final SqlColumn<LocalDateTime> pendingTime = udsJob.pendingTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.dispatcher_time")
    public static final SqlColumn<LocalDateTime> dispatcherTime = udsJob.dispatcherTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.start_time")
    public static final SqlColumn<LocalDateTime> startTime = udsJob.startTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.end_time")
    public static final SqlColumn<LocalDateTime> endTime = udsJob.endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.231+08:00", comments="Source field: uds_job.num_times")
    public static final SqlColumn<Long> numTimes = udsJob.numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.232+08:00", comments="Source field: uds_job.call_again_num")
    public static final SqlColumn<Integer> callAgainNum = udsJob.callAgainNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.232+08:00", comments="Source field: uds_job.stream_type")
    public static final SqlColumn<Byte> streamType = udsJob.streamType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.232+08:00", comments="Source field: uds_job.des")
    public static final SqlColumn<String> des = udsJob.des;

    public static final SqlColumn<LocalDateTime> updateTime = udsJob.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.23+08:00", comments="Source Table: uds_job")
    public static final class UdsJob extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> lastStatus = column("last_status", JDBCType.VARCHAR);

        public final SqlColumn<LocalDate> jobDate = column("job_date", JDBCType.DATE);

        public final SqlColumn<LocalDate> nextJobDate = column("next_job_date", JDBCType.DATE);

        public final SqlColumn<String> serverName = column("server_name", JDBCType.VARCHAR);

        public final SqlColumn<Integer> multiBatch = column("multi_batch", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> pendingTime = column("pending_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> dispatcherTime = column("dispatcher_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> numTimes = column("num_times", JDBCType.BIGINT);

        public final SqlColumn<Integer> callAgainNum = column("call_again_num", JDBCType.INTEGER);

        public final SqlColumn<Byte> streamType = column("stream_type", JDBCType.TINYINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);
      
        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJob() {
            super("uds_job");
        }
    }
}