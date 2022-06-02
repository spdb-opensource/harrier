package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class DyJobArrangeDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    public static final DyJobArrange dyJobArrange = new DyJobArrange();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.id")
    public static final SqlColumn<Long> id = dyJobArrange.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.platform")
    public static final SqlColumn<String> platform = dyJobArrange.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.systems")
    public static final SqlColumn<String> systems = dyJobArrange.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.job")
    public static final SqlColumn<String> job = dyJobArrange.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.task_status")
    public static final SqlColumn<Integer> taskStatus = dyJobArrange.taskStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.process_status")
    public static final SqlColumn<Integer> processStatus = dyJobArrange.processStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.sync_status")
    public static final SqlColumn<Integer> syncStatus = dyJobArrange.syncStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.start_date")
    public static final SqlColumn<LocalDateTime> startDate = dyJobArrange.startDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.end_date")
    public static final SqlColumn<LocalDateTime> endDate = dyJobArrange.endDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.version")
    public static final SqlColumn<Integer> version = dyJobArrange.version;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.des")
    public static final SqlColumn<String> des = dyJobArrange.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source field: dy_job_arrange.deploy_yaml")
    public static final SqlColumn<String> deployYaml = dyJobArrange.deployYaml;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    public static final class DyJobArrange extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Integer> taskStatus = column("task_status", JDBCType.INTEGER);

        public final SqlColumn<Integer> processStatus = column("process_status", JDBCType.INTEGER);

        public final SqlColumn<Integer> syncStatus = column("sync_status", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> startDate = column("start_date", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> endDate = column("end_date", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public final SqlColumn<String> deployYaml = column("deploy_yaml", JDBCType.LONGVARCHAR);

        public DyJobArrange() {
            super("dy_job_arrange");
        }
    }
}