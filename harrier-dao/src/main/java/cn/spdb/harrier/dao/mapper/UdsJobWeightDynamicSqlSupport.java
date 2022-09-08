package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobWeightDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source Table: uds_job_weight")
    public static final UdsJobWeight udsJobWeight = new UdsJobWeight();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.id")
    public static final SqlColumn<Long> id = udsJobWeight.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source field: uds_job_weight.platform")
    public static final SqlColumn<String> platform = udsJobWeight.platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source field: uds_job_weight.systems")
    public static final SqlColumn<String> systems = udsJobWeight.systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source field: uds_job_weight.job")
    public static final SqlColumn<String> job = udsJobWeight.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source field: uds_job_weight.limit_type")
    public static final SqlColumn<Integer> limitType = udsJobWeight.limitType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source field: uds_job_weight.conf_value")
    public static final SqlColumn<Integer> confValue = udsJobWeight.confValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source field: uds_job_weight.des")
    public static final SqlColumn<String> des = udsJobWeight.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.389+08:00", comments="Source Table: uds_job_weight")
    public static final class UdsJobWeight extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

        public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<Integer> limitType = column("limit_type", JDBCType.INTEGER);

        public final SqlColumn<Integer> confValue = column("conf_value", JDBCType.INTEGER);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobWeight() {
            super("uds_job_weight");
        }
    }
}