package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MOperatLogDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MOperatLog;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface MOperatLogMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    BasicColumn[] selectList = BasicColumn.columnList(id, userCode, operatType, operat, job, ip, operatDate, operatContent);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MOperatLog> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MOperatLog> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MOperatLogResult")
    Optional<MOperatLog> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MOperatLogResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_code", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="operat_type", property="operatType", jdbcType=JdbcType.VARCHAR),
        @Result(column="operat", property="operat", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="operat_date", property="operatDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="operat_content", property="operatContent", jdbcType=JdbcType.VARCHAR)
    })
    List<MOperatLog> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default int insert(MOperatLog record) {
        return MyBatis3Utils.insert(this::insert, record, MOperatLog, c ->
            c.map(id).toProperty("id")
            .map(userCode).toProperty("userCode")
            .map(operatType).toProperty("operatType")
            .map(operat).toProperty("operat")
            .map(job).toProperty("job")
            .map(ip).toProperty("ip")
            .map(operatDate).toProperty("operatDate")
            .map(operatContent).toProperty("operatContent")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default int insertMultiple(Collection<MOperatLog> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MOperatLog, c ->
            c.map(id).toProperty("id")
            .map(userCode).toProperty("userCode")
            .map(operatType).toProperty("operatType")
            .map(operat).toProperty("operat")
            .map(job).toProperty("job")
            .map(ip).toProperty("ip")
            .map(operatDate).toProperty("operatDate")
            .map(operatContent).toProperty("operatContent")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.983+08:00", comments="Source Table: m_operat_log")
    default int insertSelective(MOperatLog record) {
        return MyBatis3Utils.insert(this::insert, record, MOperatLog, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(userCode).toPropertyWhenPresent("userCode", record::getUserCode)
            .map(operatType).toPropertyWhenPresent("operatType", record::getOperatType)
            .map(operat).toPropertyWhenPresent("operat", record::getOperat)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(ip).toPropertyWhenPresent("ip", record::getIp)
            .map(operatDate).toPropertyWhenPresent("operatDate", record::getOperatDate)
            .map(operatContent).toPropertyWhenPresent("operatContent", record::getOperatContent)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default Optional<MOperatLog> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default List<MOperatLog> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default List<MOperatLog> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default Optional<MOperatLog> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MOperatLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    static UpdateDSL<UpdateModel> updateAllColumns(MOperatLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(userCode).equalTo(record::getUserCode)
                .set(operatType).equalTo(record::getOperatType)
                .set(operat).equalTo(record::getOperat)
                .set(job).equalTo(record::getJob)
                .set(ip).equalTo(record::getIp)
                .set(operatDate).equalTo(record::getOperatDate)
                .set(operatContent).equalTo(record::getOperatContent);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MOperatLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(userCode).equalToWhenPresent(record::getUserCode)
                .set(operatType).equalToWhenPresent(record::getOperatType)
                .set(operat).equalToWhenPresent(record::getOperat)
                .set(job).equalToWhenPresent(record::getJob)
                .set(ip).equalToWhenPresent(record::getIp)
                .set(operatDate).equalToWhenPresent(record::getOperatDate)
                .set(operatContent).equalToWhenPresent(record::getOperatContent);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default int updateByPrimaryKey(MOperatLog record) {
        return update(c ->
            c.set(userCode).equalTo(record::getUserCode)
            .set(operatType).equalTo(record::getOperatType)
            .set(operat).equalTo(record::getOperat)
            .set(job).equalTo(record::getJob)
            .set(ip).equalTo(record::getIp)
            .set(operatDate).equalTo(record::getOperatDate)
            .set(operatContent).equalTo(record::getOperatContent)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.984+08:00", comments="Source Table: m_operat_log")
    default int updateByPrimaryKeySelective(MOperatLog record) {
        return update(c ->
            c.set(userCode).equalToWhenPresent(record::getUserCode)
            .set(operatType).equalToWhenPresent(record::getOperatType)
            .set(operat).equalToWhenPresent(record::getOperat)
            .set(job).equalToWhenPresent(record::getJob)
            .set(ip).equalToWhenPresent(record::getIp)
            .set(operatDate).equalToWhenPresent(record::getOperatDate)
            .set(operatContent).equalToWhenPresent(record::getOperatContent)
            .where(id, isEqualTo(record::getId))
        );
    }
    
    @ResultMap("MOperatLogResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MOperatLog> selectManyPage(SelectStatementProvider selectStatement,Page<MOperatLog> page);
	default Page<MOperatLog> selectAll(Page<MOperatLog> page, String userCode_, String operatType_, String job_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(MOperatLog)
				.where(MOperatLog.userCode,isLikeWhenPresent(userCode_))
				.and(MOperatLog.operatType, isEqualToWhenPresent(operatType_))
				.and(MOperatLog.job, isEqualToWhenPresent(job_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MOperatLog> records = selectManyPage(selectStatement,page);
		page.setRecords(records);
		return page;
	}
}