package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobDependencyDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source Table: uds_job_dependency")
    public static final UdsJobDependency udsJobDependency = new UdsJobDependency();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.id")
    public static final SqlColumn<Long> id = udsJobDependency.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.platform")
    public static final SqlColumn<String> platform = udsJobDependency.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.systems")
    public static final SqlColumn<String> systems = udsJobDependency.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.job")
    public static final SqlColumn<String> job = udsJobDependency.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.dep_platform")
    public static final SqlColumn<String> depPlatform = udsJobDependency.depPlatform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source field: uds_job_dependency.dep_system")
    public static final SqlColumn<String> depSystem = udsJobDependency.depSystem;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source field: uds_job_dependency.dep_job")
    public static final SqlColumn<String> depJob = udsJobDependency.depJob;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source field: uds_job_dependency.dep_batch")
    public static final SqlColumn<Integer> depBatch = udsJobDependency.depBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source field: uds_job_dependency.enable")
    public static final SqlColumn<Boolean> isEnable = udsJobDependency.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source field: uds_job_dependency.des")
    public static final SqlColumn<String> des = udsJobDependency.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.201+08:00", comments="Source Table: uds_job_dependency")
    public static final class UdsJobDependency extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> depPlatform = column("dep_platform", JDBCType.VARCHAR);

        public final SqlColumn<String> depSystem = column("dep_system", JDBCType.VARCHAR);

        public final SqlColumn<String> depJob = column("dep_job", JDBCType.VARCHAR);

        public final SqlColumn<Integer> depBatch = column("dep_batch", JDBCType.INTEGER);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobDependency() {
            super("uds_job_dependency");
        }
    }
}