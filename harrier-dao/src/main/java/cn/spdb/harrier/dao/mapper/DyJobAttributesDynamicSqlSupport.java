package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDate;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class DyJobAttributesDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.417+08:00", comments="Source Table: dy_job_attributes")
    public static final DyJobAttributes dyJobAttributes = new DyJobAttributes();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source field: dy_job_attributes.id")
    public static final SqlColumn<Long> id = dyJobAttributes.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source field: dy_job_attributes.platform")
    public static final SqlColumn<String> platform = dyJobAttributes.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source field: dy_job_attributes.systems")
    public static final SqlColumn<String> systems = dyJobAttributes.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source field: dy_job_attributes.job")
    public static final SqlColumn<String> job = dyJobAttributes.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source field: dy_job_attributes.job_name")
    public static final SqlColumn<String> jobName = dyJobAttributes.jobName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.job_type")
    public static final SqlColumn<String> jobType = dyJobAttributes.jobType;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.stream_type")
    public static final SqlColumn<Integer> streamType = dyJobAttributes.streamType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.job_date")
    public static final SqlColumn<LocalDate> jobDate = dyJobAttributes.jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.last_status")
    public static final SqlColumn<String> lastStatus = dyJobAttributes.lastStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.task_status")
    public static final SqlColumn<Integer> taskStatus = dyJobAttributes.taskStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.multi_batch")
    public static final SqlColumn<Integer> multiBatch = dyJobAttributes.multiBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.time_window")
    public static final SqlColumn<String> timeWindow = dyJobAttributes.timeWindow;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.virtual_enable")
    public static final SqlColumn<Boolean> virtualEnable = dyJobAttributes.virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.priority")
    public static final SqlColumn<Integer> priority = dyJobAttributes.priority;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.call_again_max_num")
    public static final SqlColumn<Byte> callAgainMaxNum = dyJobAttributes.callAgainMaxNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.call_again_wait_sec")
    public static final SqlColumn<Byte> callAgainWaitSec = dyJobAttributes.callAgainWaitSec;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.check_frequency")
    public static final SqlColumn<Boolean> checkFrequency = dyJobAttributes.checkFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.check_time_trigger")
    public static final SqlColumn<Boolean> checkTimeTrigger = dyJobAttributes.checkTimeTrigger;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.check_stream_self")
    public static final SqlColumn<Boolean> checkStreamSelf = dyJobAttributes.checkStreamSelf;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.job_frequency")
    public static final SqlColumn<String> jobFrequency = dyJobAttributes.jobFrequency;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.offset_day")
    public static final SqlColumn<Byte> offsetDay = dyJobAttributes.offsetDay;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.ignore_error")
    public static final SqlColumn<Boolean> ignoreError = dyJobAttributes.ignoreError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.des")
    public static final SqlColumn<String> des = dyJobAttributes.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.dep_job")
    public static final SqlColumn<String> depJob = dyJobAttributes.depJob;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.427+08:00", comments="Source field: dy_job_attributes.job_step")
    public static final SqlColumn<String> jobStep = dyJobAttributes.jobStep;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.422+08:00", comments="Source Table: dy_job_attributes")
    public static final class DyJobAttributes extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> jobName = column("job_name", JDBCType.VARCHAR);

        public final SqlColumn<String> jobType = column("job_type", JDBCType.VARCHAR);
        
        public final SqlColumn<Integer> streamType = column("stream_type", JDBCType.INTEGER);

        public final SqlColumn<LocalDate> jobDate = column("job_date", JDBCType.DATE);

        public final SqlColumn<String> lastStatus = column("last_status", JDBCType.VARCHAR);

        public final SqlColumn<Integer> taskStatus = column("task_status", JDBCType.INTEGER);

        public final SqlColumn<Integer> multiBatch = column("multi_batch", JDBCType.INTEGER);

        public final SqlColumn<String> timeWindow = column("time_window", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> virtualEnable = column("virtual_enable", JDBCType.BIT);

        public final SqlColumn<Integer> priority = column("priority", JDBCType.INTEGER);

        public final SqlColumn<Byte> callAgainMaxNum = column("call_again_max_num", JDBCType.TINYINT);

        public final SqlColumn<Byte> callAgainWaitSec = column("call_again_wait_sec", JDBCType.TINYINT);

        public final SqlColumn<Boolean> checkFrequency = column("check_frequency", JDBCType.BIT);

        public final SqlColumn<Boolean> checkTimeTrigger = column("check_time_trigger", JDBCType.BIT);

        public final SqlColumn<Boolean> checkStreamSelf = column("check_stream_self", JDBCType.BIT);

        public final SqlColumn<String> jobFrequency = column("job_frequency", JDBCType.VARCHAR);

        public final SqlColumn<Byte> offsetDay = column("offset_day", JDBCType.TINYINT);

        public final SqlColumn<Boolean> ignoreError = column("ignore_error", JDBCType.BIT);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public final SqlColumn<String> depJob = column("dep_job", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> jobStep = column("job_step", JDBCType.LONGVARCHAR);

        public DyJobAttributes() {
            super("dy_job_attributes");
        }
    }
}