package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MOperatLogDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source Table: m_operat_log")
    public static final MOperatLog MOperatLog = new MOperatLog();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.id")
    public static final SqlColumn<Integer> id = MOperatLog.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.user_code")
    public static final SqlColumn<String> userCode = MOperatLog.userCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.operat_type")
    public static final SqlColumn<String> operatType = MOperatLog.operatType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.operat")
    public static final SqlColumn<String> operat = MOperatLog.operat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.job")
    public static final SqlColumn<String> job = MOperatLog.job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.ip")
    public static final SqlColumn<String> ip = MOperatLog.ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.operat_date")
    public static final SqlColumn<LocalDateTime> operatDate = MOperatLog.operatDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source field: m_operat_log.operat_content")
    public static final SqlColumn<String> operatContent = MOperatLog.operatContent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source Table: m_operat_log")
    public static final class MOperatLog extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> userCode = column("user_code", JDBCType.VARCHAR);

        public final SqlColumn<String> operatType = column("operat_type", JDBCType.VARCHAR);

        public final SqlColumn<String> operat = column("operat", JDBCType.VARCHAR);

        public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

        public final SqlColumn<String> ip = column("ip", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> operatDate = column("operat_date", JDBCType.TIMESTAMP);

        public final SqlColumn<String> operatContent = column("operat_content", JDBCType.VARCHAR);

        public MOperatLog() {
            super("m_operat_log");
        }
    }
}