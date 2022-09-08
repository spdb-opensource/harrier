package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobSelfSignalDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobSelfSignal;

import java.time.LocalDate;
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
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface UdsJobSelfSignalMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.738+08:00", comments="Source Table: uds_ job_self_signal")
    BasicColumn[] selectList = BasicColumn.columnList(id, signal, platform, systems, job, createTime, batch, jobDate, evns, params, useful);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.716+08:00", comments="Source Table: uds_ job_self_signal")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.719+08:00", comments="Source Table: uds_ job_self_signal")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.72+08:00", comments="Source Table: uds_ job_self_signal")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobSelfSignal> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.721+08:00", comments="Source Table: uds_ job_self_signal")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobSelfSignal> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.722+08:00", comments="Source Table: uds_ job_self_signal")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobSelfSignalResult")
    Optional<UdsJobSelfSignal> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.725+08:00", comments="Source Table: uds_ job_self_signal")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobSelfSignalResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="signal", property="signal", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="batch", property="batch", jdbcType=JdbcType.INTEGER),
        @Result(column="job_date", property="jobDate", jdbcType=JdbcType.DATE),
        @Result(column="evns", property="evns", jdbcType=JdbcType.VARCHAR),
        @Result(column="params", property="params", jdbcType=JdbcType.VARCHAR),
        @Result(column="useful", property="useful", jdbcType=JdbcType.BIT)
    })
    List<UdsJobSelfSignal> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.729+08:00", comments="Source Table: uds_ job_self_signal")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.73+08:00", comments="Source Table: uds_ job_self_signal")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.731+08:00", comments="Source Table: uds_ job_self_signal")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.732+08:00", comments="Source Table: uds_ job_self_signal")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.733+08:00", comments="Source Table: uds_ job_self_signal")
    default int insert(UdsJobSelfSignal record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobSelfSignal, c ->
            c.map(id).toProperty("id")
            .map(signal).toProperty("signal")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(createTime).toProperty("createTime")
            .map(batch).toProperty("batch")
            .map(jobDate).toProperty("jobDate")
            .map(evns).toProperty("evns")
            .map(params).toProperty("params")
            .map(useful).toProperty("useful")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.735+08:00", comments="Source Table: uds_ job_self_signal")
    default int insertMultiple(Collection<UdsJobSelfSignal> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobSelfSignal, c ->
            c.map(id).toProperty("id")
            .map(signal).toProperty("signal")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(createTime).toProperty("createTime")
            .map(batch).toProperty("batch")
            .map(jobDate).toProperty("jobDate")
            .map(evns).toProperty("evns")
            .map(params).toProperty("params")
            .map(useful).toProperty("useful")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.736+08:00", comments="Source Table: uds_ job_self_signal")
    default int insertSelective(UdsJobSelfSignal record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobSelfSignal, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(signal).toPropertyWhenPresent("signal", record::getSignal)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(batch).toPropertyWhenPresent("batch", record::getBatch)
            .map(jobDate).toPropertyWhenPresent("jobDate", record::getJobDate)
            .map(evns).toPropertyWhenPresent("evns", record::getEvns)
            .map(params).toPropertyWhenPresent("params", record::getParams)
            .map(useful).toPropertyWhenPresent("useful", record::getUseful)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.74+08:00", comments="Source Table: uds_ job_self_signal")
    default Optional<UdsJobSelfSignal> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.741+08:00", comments="Source Table: uds_ job_self_signal")
    default List<UdsJobSelfSignal> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.742+08:00", comments="Source Table: uds_ job_self_signal")
    default List<UdsJobSelfSignal> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.743+08:00", comments="Source Table: uds_ job_self_signal")
    default Optional<UdsJobSelfSignal> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.744+08:00", comments="Source Table: uds_ job_self_signal")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobSelfSignal, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.745+08:00", comments="Source Table: uds_ job_self_signal")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobSelfSignal record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(signal).equalTo(record::getSignal)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(createTime).equalTo(record::getCreateTime)
                .set(batch).equalTo(record::getBatch)
                .set(jobDate).equalTo(record::getJobDate)
                .set(evns).equalTo(record::getEvns)
                .set(params).equalTo(record::getParams)
                .set(useful).equalTo(record::getUseful);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.746+08:00", comments="Source Table: uds_ job_self_signal")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobSelfSignal record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(signal).equalToWhenPresent(record::getSignal)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(batch).equalToWhenPresent(record::getBatch)
                .set(jobDate).equalToWhenPresent(record::getJobDate)
                .set(evns).equalToWhenPresent(record::getEvns)
                .set(params).equalToWhenPresent(record::getParams)
                .set(useful).equalToWhenPresent(record::getUseful);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.748+08:00", comments="Source Table: uds_ job_self_signal")
    default int updateByPrimaryKey(UdsJobSelfSignal record) {
        return update(c ->
            c.set(signal).equalTo(record::getSignal)
            .set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(createTime).equalTo(record::getCreateTime)
            .set(batch).equalTo(record::getBatch)
            .set(jobDate).equalTo(record::getJobDate)
            .set(evns).equalTo(record::getEvns)
            .set(params).equalTo(record::getParams)
            .set(useful).equalTo(record::getUseful)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-28T14:07:33.749+08:00", comments="Source Table: uds_ job_self_signal")
    default int updateByPrimaryKeySelective(UdsJobSelfSignal record) {
        return update(c ->
            c.set(signal).equalToWhenPresent(record::getSignal)
            .set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(batch).equalToWhenPresent(record::getBatch)
            .set(jobDate).equalToWhenPresent(record::getJobDate)
            .set(evns).equalToWhenPresent(record::getEvns)
            .set(params).equalToWhenPresent(record::getParams)
            .set(useful).equalToWhenPresent(record::getUseful)
            .where(id, isEqualTo(record::getId))
        );
    }

    default Optional<UdsJobSelfSignal> selectOneSignal(String platform_, String system_, String job_, LocalDate jobDate_, Integer batch_){
    	return selectOne(c->c.where()
    			.and(platform, isEqualTo(platform_))
    			.and(systems, isEqualTo(system_))
    			.and(job, isEqualTo(job_))
    			.and(jobDate, isEqualTo(jobDate_))
    			.and(batch, isEqualTo(batch_))
    			.and(useful, isEqualTo(true))
    			);
    }
}