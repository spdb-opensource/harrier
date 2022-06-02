package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.crontab;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.des;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.endTime;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.id;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.isEnable;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.job;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.platform;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.startTime;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.systems;
import static cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerDynamicSqlSupport.udsJobTimeTrigger;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThan;

import java.time.LocalDateTime;
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

import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;

@Mapper
public interface UdsJobTimeTriggerMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, startTime, endTime, crontab, isEnable, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobTimeTrigger> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobTimeTrigger> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobTimeTriggerResult")
    Optional<UdsJobTimeTrigger> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobTimeTriggerResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="crontab", property="crontab", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_enable", property="isEnable", jdbcType=JdbcType.BIT),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobTimeTrigger> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default int insert(UdsJobTimeTrigger record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobTimeTrigger, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(crontab).toProperty("crontab")
            .map(isEnable).toProperty("isEnable")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default int insertMultiple(Collection<UdsJobTimeTrigger> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobTimeTrigger, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(crontab).toProperty("crontab")
            .map(isEnable).toProperty("isEnable")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.225+08:00", comments="Source Table: uds_job_time_trigger")
    default int insertSelective(UdsJobTimeTrigger record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobTimeTrigger, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(startTime).toPropertyWhenPresent("startTime", record::getStartTime)
            .map(endTime).toPropertyWhenPresent("endTime", record::getEndTime)
            .map(crontab).toPropertyWhenPresent("crontab", record::getCrontab)
            .map(isEnable).toPropertyWhenPresent("isEnable", record::getIsEnable)
            .map(des).toPropertyWhenPresent("des", record::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default Optional<UdsJobTimeTrigger> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default List<UdsJobTimeTrigger> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default List<UdsJobTimeTrigger> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default Optional<UdsJobTimeTrigger> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }
    
    default Optional<UdsJobTimeTrigger> selectByJobName(String job_) {
        return selectOne(c ->
            c.where(job, isEqualTo(job_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobTimeTrigger, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobTimeTrigger record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(startTime).equalTo(record::getStartTime)
                .set(endTime).equalTo(record::getEndTime)
                .set(crontab).equalTo(record::getCrontab)
                .set(isEnable).equalTo(record::getIsEnable)
                .set(des).equalTo(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobTimeTrigger record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(startTime).equalToWhenPresent(record::getStartTime)
                .set(endTime).equalToWhenPresent(record::getEndTime)
                .set(crontab).equalToWhenPresent(record::getCrontab)
                .set(isEnable).equalToWhenPresent(record::getIsEnable)
                .set(des).equalToWhenPresent(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.226+08:00", comments="Source Table: uds_job_time_trigger")
    default int updateByPrimaryKey(UdsJobTimeTrigger record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(startTime).equalTo(record::getStartTime)
            .set(endTime).equalTo(record::getEndTime)
            .set(crontab).equalTo(record::getCrontab)
            .set(isEnable).equalTo(record::getIsEnable)
            .set(des).equalTo(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.227+08:00", comments="Source Table: uds_job_time_trigger")
    default int updateByPrimaryKeySelective(UdsJobTimeTrigger record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(startTime).equalToWhenPresent(record::getStartTime)
            .set(endTime).equalToWhenPresent(record::getEndTime)
            .set(crontab).equalToWhenPresent(record::getCrontab)
            .set(isEnable).equalToWhenPresent(record::getIsEnable)
            .set(des).equalToWhenPresent(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    default List<UdsJobTimeTrigger> select(String platform_, String system_, String job_){
		return select(c->c.where()
				.and(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(system_))
				.and(job, isEqualTo(job_))
				.and(isEnable, isEqualTo(true))
				);
	}

	default List<UdsJobTimeTrigger> selectJobToScheduleTime(){
		return select(c->c.where(isEnable,isEqualTo(true))
				.and(startTime, isLessThan(LocalDateTime.now())));
	}

}