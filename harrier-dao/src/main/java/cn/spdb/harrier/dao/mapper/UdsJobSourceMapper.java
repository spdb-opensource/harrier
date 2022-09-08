package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobSourceDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobSource;
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
public interface UdsJobSourceMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.792+08:00", comments="Source Table: uds_job_source")
    BasicColumn[] selectList = BasicColumn.columnList(id, signals, platform, systems, job, isEnable);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.772+08:00", comments="Source Table: uds_job_source")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.774+08:00", comments="Source Table: uds_job_source")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.775+08:00", comments="Source Table: uds_job_source")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobSource> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.777+08:00", comments="Source Table: uds_job_source")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobSource> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.778+08:00", comments="Source Table: uds_job_source")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobSourceResult")
    Optional<UdsJobSource> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.778+08:00", comments="Source Table: uds_job_source")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobSourceResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="signals", property="signals", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_enable", property="isEnable", jdbcType=JdbcType.BIT)
    })
    List<UdsJobSource> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.781+08:00", comments="Source Table: uds_job_source")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.782+08:00", comments="Source Table: uds_job_source")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.784+08:00", comments="Source Table: uds_job_source")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.784+08:00", comments="Source Table: uds_job_source")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.786+08:00", comments="Source Table: uds_job_source")
    default int insert(UdsJobSource record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobSource, c ->
            c.map(id).toProperty("id")
            .map(signals).toProperty("signals")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(isEnable).toProperty("enable")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.788+08:00", comments="Source Table: uds_job_source")
    default int insertMultiple(Collection<UdsJobSource> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobSource, c ->
            c.map(id).toProperty("id")
            .map(signals).toProperty("signals")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(isEnable).toProperty("enable")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.789+08:00", comments="Source Table: uds_job_source")
    default int insertSelective(UdsJobSource record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobSource, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(signals).toPropertyWhenPresent("signals", record::getSignals)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(isEnable).toPropertyWhenPresent("enable", record::getIsEnable)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.795+08:00", comments="Source Table: uds_job_source")
    default Optional<UdsJobSource> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.795+08:00", comments="Source Table: uds_job_source")
    default List<UdsJobSource> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.796+08:00", comments="Source Table: uds_job_source")
    default List<UdsJobSource> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.797+08:00", comments="Source Table: uds_job_source")
    default Optional<UdsJobSource> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }
    
    default Optional<UdsJobSource> selectByJobName(String job_) {
        return selectOne(c ->
            c.where(job, isEqualTo(job_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.798+08:00", comments="Source Table: uds_job_source")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobSource, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.799+08:00", comments="Source Table: uds_job_source")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobSource record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(signals).equalTo(record::getSignals)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(isEnable).equalTo(record::getIsEnable);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.8+08:00", comments="Source Table: uds_job_source")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobSource record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(signals).equalToWhenPresent(record::getSignals)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(isEnable).equalToWhenPresent(record::getIsEnable);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.802+08:00", comments="Source Table: uds_job_source")
    default int updateByPrimaryKey(UdsJobSource record) {
        return update(c ->
            c.set(signals).equalTo(record::getSignals)
            .set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(isEnable).equalTo(record::getIsEnable)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-02-21T15:37:49.803+08:00", comments="Source Table: uds_job_source")
    default int updateByPrimaryKeySelective(UdsJobSource record) {
        return update(c ->
            c.set(signals).equalToWhenPresent(record::getSignals)
            .set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(isEnable).equalToWhenPresent(record::getIsEnable)
            .where(id, isEqualTo(record::getId))
        );
    }

    default Optional<UdsJobSource> selectOne(String platform_, String systems_, String job_){
		return selectOne(c->c.where()
				.and(platform,isEqualTo(platform_))
				.and(systems,isEqualTo(systems_))
				.and(job,isEqualTo(job_))
				.and(isEnable,isEqualTo(true))
				);
	}
}