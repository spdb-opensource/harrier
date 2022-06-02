package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MDictionaryDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.7+08:00", comments="Source Table: m_dictionary")
    public static final MDictionary MDictionary = new MDictionary();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.704+08:00", comments="Source field: m_dictionary.id")
    public static final SqlColumn<Long> id = MDictionary.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.705+08:00", comments="Source field: m_dictionary.dic_code")
    public static final SqlColumn<String> dicCode = MDictionary.dicCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.705+08:00", comments="Source field: m_dictionary.dic_key")
    public static final SqlColumn<String> dicKey = MDictionary.dicKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.705+08:00", comments="Source field: m_dictionary.dic_name")
    public static final SqlColumn<String> dicName = MDictionary.dicName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.706+08:00", comments="Source field: m_dictionary.dic_value")
    public static final SqlColumn<String> dicValue = MDictionary.dicValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.706+08:00", comments="Source field: m_dictionary.dic_desc")
    public static final SqlColumn<String> dicDesc = MDictionary.dicDesc;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.703+08:00", comments="Source Table: m_dictionary")
    public static final class MDictionary extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> dicCode = column("dic_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dicKey = column("dic_key", JDBCType.VARCHAR);

        public final SqlColumn<String> dicName = column("dic_name", JDBCType.VARCHAR);

        public final SqlColumn<String> dicValue = column("dic_value", JDBCType.VARCHAR);

        public final SqlColumn<String> dicDesc = column("dic_desc", JDBCType.VARCHAR);

        public MDictionary() {
            super("m_dictionary");
        }
    }
}