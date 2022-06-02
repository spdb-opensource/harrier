package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobConfigDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.517+08:00", comments="Source Table: uds_job_config")
    public static final UdsJobConfig udsJobConfig = new UdsJobConfig();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.52+08:00", comments="Source field: uds_job_config.id")
    public static final SqlColumn<Long> id = udsJobConfig.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.521+08:00", comments="Source field: uds_job_config.platform")
    public static final SqlColumn<String> platform = udsJobConfig.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.521+08:00", comments="Source field: uds_job_config.systems")
    public static final SqlColumn<String> systems = udsJobConfig.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.521+08:00", comments="Source field: uds_job_config.job")
    public static final SqlColumn<String> job = udsJobConfig.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.521+08:00", comments="Source field: uds_job_config.job_name")
    public static final SqlColumn<String> jobName = udsJobConfig.jobName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.521+08:00", comments="Source field: uds_job_config.job_type")
    public static final SqlColumn<String> jobType = udsJobConfig.jobType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.offset_day")
    public static final SqlColumn<Byte> offsetDay = udsJobConfig.offsetDay;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.time_window")
    public static final SqlColumn<String> timeWindow = udsJobConfig.timeWindow;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.virtual_enable")
    public static final SqlColumn<Boolean> virtualEnable = udsJobConfig.virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.priority")
    public static final SqlColumn<Integer> priority = udsJobConfig.priority;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.call_again_max_num")
    public static final SqlColumn<Byte> callAgainMaxNum = udsJobConfig.callAgainMaxNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.call_again_wait_sec")
    public static final SqlColumn<Byte> callAgainWaitSec = udsJobConfig.callAgainWaitSec;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.count_batch")
    public static final SqlColumn<Integer> countBatch = udsJobConfig.countBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.522+08:00", comments="Source field: uds_job_config.batch_conversion")
    public static final SqlColumn<Integer> batchConversion = udsJobConfig.batchConversion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.check_frequency")
    public static final SqlColumn<Boolean> checkFrequency = udsJobConfig.checkFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.check_time_trigger")
    public static final SqlColumn<Boolean> checkTimeTrigger = udsJobConfig.checkTimeTrigger;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.check_stream_self")
    public static final SqlColumn<Boolean> checkStreamSelf = udsJobConfig.checkStreamSelf;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.ignore_error")
    public static final SqlColumn<Boolean> ignoreError = udsJobConfig.ignoreError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.check_weight")
    public static final SqlColumn<Boolean> checkWeight = udsJobConfig.checkWeight;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.enable")
    public static final SqlColumn<Boolean> isEnable = udsJobConfig.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.523+08:00", comments="Source field: uds_job_config.des")
    public static final SqlColumn<String> des = udsJobConfig.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-25T10:13:27.519+08:00", comments="Source Table: uds_job_config")
    public static final class UdsJobConfig extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> jobName = column("job_name", JDBCType.VARCHAR);

        public final SqlColumn<String> jobType = column("job_type", JDBCType.VARCHAR);

        public final SqlColumn<Byte> offsetDay = column("offset_day", JDBCType.TINYINT);

        public final SqlColumn<String> timeWindow = column("time_window", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> virtualEnable = column("virtual_enable", JDBCType.BIT);

        public final SqlColumn<Integer> priority = column("priority", JDBCType.INTEGER);

        public final SqlColumn<Byte> callAgainMaxNum = column("call_again_max_num", JDBCType.TINYINT);

        public final SqlColumn<Byte> callAgainWaitSec = column("call_again_wait_sec", JDBCType.TINYINT);

        public final SqlColumn<Integer> countBatch = column("count_batch", JDBCType.INTEGER);

        public final SqlColumn<Integer> batchConversion = column("batch_conversion", JDBCType.INTEGER);

        public final SqlColumn<Boolean> checkFrequency = column("check_frequency", JDBCType.BIT);

        public final SqlColumn<Boolean> checkTimeTrigger = column("check_time_trigger", JDBCType.BIT);

        public final SqlColumn<Boolean> checkStreamSelf = column("check_stream_self", JDBCType.BIT);

        public final SqlColumn<Boolean> ignoreError = column("ignore_error", JDBCType.BIT);

        public final SqlColumn<Boolean> checkWeight = column("check_weight", JDBCType.BIT);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobConfig() {
            super("uds_job_config");
        }
    }
}