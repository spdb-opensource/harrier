package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;

import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobStepDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.147+08:00", comments="Source Table: uds_job_step")
    public static final UdsJobStep udsJobStep = new UdsJobStep();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.15+08:00", comments="Source field: uds_job_step.id")
    public static final SqlColumn<Long> id = udsJobStep.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.151+08:00", comments="Source field: uds_job_step.platform")
    public static final SqlColumn<String> platform = udsJobStep.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.151+08:00", comments="Source field: uds_job_step.systems")
    public static final SqlColumn<String> systems = udsJobStep.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.151+08:00", comments="Source field: uds_job_step.job")
    public static final SqlColumn<String> job = udsJobStep.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.151+08:00", comments="Source field: uds_job_step.step_type")
    public static final SqlColumn<String> stepType = udsJobStep.stepType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.152+08:00", comments="Source field: uds_job_step.step_num")
    public static final SqlColumn<Byte> stepNum = udsJobStep.stepNum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.152+08:00", comments="Source field: uds_job_step.oper_cmd")
    public static final SqlColumn<String> operCmd = udsJobStep.operCmd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.152+08:00", comments="Source field: uds_job_step.script_path")
    public static final SqlColumn<String> scriptPath = udsJobStep.scriptPath;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.152+08:00", comments="Source field: uds_job_step.parameter")
    public static final SqlColumn<String> parameter = udsJobStep.parameter;

    public static final SqlColumn<LocalDateTime> updateTime = udsJobStep.updateTime;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.152+08:00", comments="Source field: uds_job_step.environments")
    public static final SqlColumn<String> environments = udsJobStep.environments;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.153+08:00", comments="Source field: uds_job_step.work_dir")
    public static final SqlColumn<String> workDir = udsJobStep.workDir;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.153+08:00", comments="Source field: uds_job_step.enable")
    public static final SqlColumn<Boolean> isEnable = udsJobStep.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.153+08:00", comments="Source field: uds_job_step.des")
    public static final SqlColumn<String> des = udsJobStep.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.149+08:00", comments="Source Table: uds_job_step")
    public static final class UdsJobStep extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> stepType = column("step_type", JDBCType.VARCHAR);

        public final SqlColumn<Byte> stepNum = column("step_num", JDBCType.TINYINT);

        public final SqlColumn<String> operCmd = column("oper_cmd", JDBCType.VARCHAR);

        public final SqlColumn<String> scriptPath = column("script_path", JDBCType.VARCHAR);

        public final SqlColumn<String> parameter = column("parameter", JDBCType.VARCHAR);

        public final SqlColumn<String> environments = column("environments", JDBCType.VARCHAR);

        public final SqlColumn<String> workDir = column("work_dir", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        
        
        public UdsJobStep() {
            super("uds_job_step");
        }
    }
}