package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobDateFrequencyDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
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
public interface UdsJobDateFrequencyMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, isEnable, crontab, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source Table: uds_job_date_frequency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source Table: uds_job_date_frequency")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.213+08:00", comments="Source Table: uds_job_date_frequency")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobDateFrequency> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobDateFrequency> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobDateFrequencyResult")
    Optional<UdsJobDateFrequency> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobDateFrequencyResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_enable", property="isEnable", jdbcType=JdbcType.BIT),
        @Result(column="crontab", property="crontab", jdbcType=JdbcType.VARCHAR),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobDateFrequency> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.214+08:00", comments="Source Table: uds_job_date_frequency")
    default int insert(UdsJobDateFrequency record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobDateFrequency, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(isEnable).toProperty("isEnable")
            .map(crontab).toProperty("crontab")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default int insertMultiple(Collection<UdsJobDateFrequency> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobDateFrequency, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(isEnable).toProperty("isEnable")
            .map(crontab).toProperty("crontab")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default int insertSelective(UdsJobDateFrequency record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobDateFrequency, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(isEnable).toPropertyWhenPresent("isEnable", record::getIsEnable)
            .map(crontab).toPropertyWhenPresent("crontab", record::getCrontab)
            .map(des).toPropertyWhenPresent("des", record::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default Optional<UdsJobDateFrequency> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default List<UdsJobDateFrequency> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default List<UdsJobDateFrequency> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default Optional<UdsJobDateFrequency> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }
    
    default Optional<UdsJobDateFrequency> selectByJobName(String job_) {
        return selectOne(c ->
            c.where(job, isEqualTo(job_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobDateFrequency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.215+08:00", comments="Source Table: uds_job_date_frequency")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobDateFrequency record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(isEnable).equalTo(record::getIsEnable)
                .set(crontab).equalTo(record::getCrontab)
                .set(des).equalTo(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.216+08:00", comments="Source Table: uds_job_date_frequency")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobDateFrequency record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(isEnable).equalToWhenPresent(record::getIsEnable)
                .set(crontab).equalToWhenPresent(record::getCrontab)
                .set(des).equalToWhenPresent(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.216+08:00", comments="Source Table: uds_job_date_frequency")
    default int updateByPrimaryKey(UdsJobDateFrequency record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(isEnable).equalTo(record::getIsEnable)
            .set(crontab).equalTo(record::getCrontab)
            .set(des).equalTo(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.216+08:00", comments="Source Table: uds_job_date_frequency")
    default int updateByPrimaryKeySelective(UdsJobDateFrequency record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(isEnable).equalToWhenPresent(record::getIsEnable)
            .set(crontab).equalToWhenPresent(record::getCrontab)
            .set(des).equalToWhenPresent(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    default List<UdsJobDateFrequency> select(String platform_, String system_, String job_){
		return select(c->c.where()
				.and(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(system_))
				.and(job, isEqualTo(job_))
				.and(isEnable, isEqualTo(true))
				
				);
	}
    
    default Optional<UdsJobDateFrequency> selectJobFrequency(String platform_, String system_, String job_){
		return selectOne(c->c.where()
				.and(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(system_))
				.and(job, isEqualTo(job_)));
	}
}