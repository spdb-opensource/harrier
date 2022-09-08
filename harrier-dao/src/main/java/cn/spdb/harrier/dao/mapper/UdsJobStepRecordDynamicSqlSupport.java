package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobStepRecordDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source Table: uds_job_step_record")
    public static final UdsJobStepRecord udsJobStepRecord = new UdsJobStepRecord();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.id")
    public static final SqlColumn<Long> id = udsJobStepRecord.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.job_record_id")
    public static final SqlColumn<Long> jobRecordId = udsJobStepRecord.jobRecordId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source field: uds_job_step_record.platform")
    public static final SqlColumn<String> platform = udsJobStepRecord.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.systems")
    public static final SqlColumn<String> systems = udsJobStepRecord.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.job")
    public static final SqlColumn<String> job = udsJobStepRecord.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.num_times")
    public static final SqlColumn<Long> numTimes = udsJobStepRecord.numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.step_num")
    public static final SqlColumn<Byte> stepNum = udsJobStepRecord.stepNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.return_code")
    public static final SqlColumn<Integer> returnCode = udsJobStepRecord.returnCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.address")
    public static final SqlColumn<String> address = udsJobStepRecord.address;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.environments")
    public static final SqlColumn<String> environments = udsJobStepRecord.environments;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.cmd")
    public static final SqlColumn<String> cmd = udsJobStepRecord.cmd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.804+08:00", comments="Source field: uds_job_step_record.script_path")
    public static final SqlColumn<String> scriptPath = udsJobStepRecord.scriptPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source field: uds_job_step_record.parameter")
    public static final SqlColumn<String> parameter = udsJobStepRecord.parameter;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source field: uds_job_step_record.start_time")
    public static final SqlColumn<LocalDateTime> startTime = udsJobStepRecord.startTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source field: uds_job_step_record.end_time")
    public static final SqlColumn<LocalDateTime> endTime = udsJobStepRecord.endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source field: uds_job_step_record.log_path")
    public static final SqlColumn<String> logPath = udsJobStepRecord.logPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.803+08:00", comments="Source Table: uds_job_step_record")
    public static final class UdsJobStepRecord extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> jobRecordId = column("job_record_id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Long> numTimes = column("num_times", JDBCType.BIGINT);

        public final SqlColumn<Byte> stepNum = column("step_num", JDBCType.TINYINT);

        public final SqlColumn<Integer> returnCode = column("return_code", JDBCType.INTEGER);

        public final SqlColumn<String> address = column("address", JDBCType.VARCHAR);

        public final SqlColumn<String> environments = column("environments", JDBCType.VARCHAR);

        public final SqlColumn<String> cmd = column("cmd", JDBCType.VARCHAR);

        public final SqlColumn<String> scriptPath = column("script_path", JDBCType.VARCHAR);

        public final SqlColumn<String> parameter = column("parameter", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> logPath = column("log_path", JDBCType.VARCHAR);

        public UdsJobStepRecord() {
            super("uds_job_step_record");
        }
    }
}