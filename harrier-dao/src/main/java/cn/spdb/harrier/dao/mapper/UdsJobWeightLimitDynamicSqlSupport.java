package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobWeightLimitDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.35+08:00", comments="Source Table: uds_job_weight_limit")
    public static final UdsJobWeightLimit udsJobWeightLimit = new UdsJobWeightLimit();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.353+08:00", comments="Source field: uds_job_weight_limit.id")
    public static final SqlColumn<Long> id = udsJobWeightLimit.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.354+08:00", comments="Source field: uds_job_weight_limit.limit_type")
    public static final SqlColumn<Integer> limitType = udsJobWeightLimit.limitType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.354+08:00", comments="Source field: uds_job_weight_limit.limit_value")
    public static final SqlColumn<Integer> limitValue = udsJobWeightLimit.limitValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.354+08:00", comments="Source field: uds_job_weight_limit.server_ids")
    public static final SqlColumn<String> serverIds = udsJobWeightLimit.serverIds;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.354+08:00", comments="Source field: uds_job_weight_limit.time_winds")
    public static final SqlColumn<String> timeWinds = udsJobWeightLimit.timeWinds;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.354+08:00", comments="Source field: uds_job_weight_limit.des")
    public static final SqlColumn<String> des = udsJobWeightLimit.des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.352+08:00", comments="Source Table: uds_job_weight_limit")
    public static final class UdsJobWeightLimit extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Integer> limitType = column("limit_type", JDBCType.INTEGER);

        public final SqlColumn<Integer> limitValue = column("limit_value", JDBCType.INTEGER);

        public final SqlColumn<String> serverIds = column("server_ids", JDBCType.VARCHAR);

        public final SqlColumn<String> timeWinds = column("time_winds", JDBCType.VARCHAR);

        public final SqlColumn<String> des = column("des", JDBCType.VARCHAR);

        public UdsJobWeightLimit() {
            super("uds_job_weight_limit");
        }
    }
}