package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.DyJobAttributesDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.DyJobAttributes;

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
import org.mybatis.dynamic.sql.SqlBuilder;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
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
public interface DyJobAttributesMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.462+08:00", comments="Source Table: dy_job_attributes")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, jobName, jobType, streamType, jobDate, lastStatus, taskStatus, multiBatch, timeWindow, virtualEnable, priority, callAgainMaxNum, callAgainWaitSec, checkFrequency, checkTimeTrigger, checkStreamSelf, jobFrequency, offsetDay, ignoreError, des, depJob, jobStep);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.432+08:00", comments="Source Table: dy_job_attributes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.432+08:00", comments="Source Table: dy_job_attributes")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.437+08:00", comments="Source Table: dy_job_attributes")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<DyJobAttributes> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.437+08:00", comments="Source Table: dy_job_attributes")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<DyJobAttributes> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.437+08:00", comments="Source Table: dy_job_attributes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("DyJobAttributesResult")
    Optional<DyJobAttributes> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.442+08:00", comments="Source Table: dy_job_attributes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="DyJobAttributesResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_name", property="jobName", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_type", property="jobType", jdbcType=JdbcType.VARCHAR),
        @Result(column="stream_type", property="streamType", jdbcType=JdbcType.INTEGER),
        @Result(column="job_date", property="jobDate", jdbcType=JdbcType.DATE),
        @Result(column="last_status", property="lastStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_status", property="taskStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="multi_batch", property="multiBatch", jdbcType=JdbcType.INTEGER),
        @Result(column="time_window", property="timeWindow", jdbcType=JdbcType.VARCHAR),
        @Result(column="virtual_enable", property="virtualEnable", jdbcType=JdbcType.BIT),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="call_again_max_num", property="callAgainMaxNum", jdbcType=JdbcType.TINYINT),
        @Result(column="call_again_wait_sec", property="callAgainWaitSec", jdbcType=JdbcType.TINYINT),
        @Result(column="check_frequency", property="checkFrequency", jdbcType=JdbcType.BIT),
        @Result(column="check_time_trigger", property="checkTimeTrigger", jdbcType=JdbcType.BIT),
        @Result(column="check_stream_self", property="checkStreamSelf", jdbcType=JdbcType.BIT),
        @Result(column="job_frequency", property="jobFrequency", jdbcType=JdbcType.VARCHAR),
        @Result(column="offset_day", property="offsetDay", jdbcType=JdbcType.TINYINT),
        @Result(column="ignore_error", property="ignoreError", jdbcType=JdbcType.BIT),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_job", property="depJob", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="job_step", property="jobStep", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<DyJobAttributes> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.447+08:00", comments="Source Table: dy_job_attributes")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.447+08:00", comments="Source Table: dy_job_attributes")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.447+08:00", comments="Source Table: dy_job_attributes")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.452+08:00", comments="Source Table: dy_job_attributes")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.452+08:00", comments="Source Table: dy_job_attributes")
    default int insert(DyJobAttributes record) {
        return MyBatis3Utils.insert(this::insert, record, dyJobAttributes, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobName).toProperty("jobName")
            .map(jobType).toProperty("jobType")
            .map(streamType).toProperty("streamType")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(taskStatus).toProperty("taskStatus")
            .map(multiBatch).toProperty("multiBatch")
            .map(timeWindow).toProperty("timeWindow")
            .map(virtualEnable).toProperty("virtualEnable")
            .map(priority).toProperty("priority")
            .map(callAgainMaxNum).toProperty("callAgainMaxNum")
            .map(callAgainWaitSec).toProperty("callAgainWaitSec")
            .map(checkFrequency).toProperty("checkFrequency")
            .map(checkTimeTrigger).toProperty("checkTimeTrigger")
            .map(checkStreamSelf).toProperty("checkStreamSelf")
            .map(jobFrequency).toProperty("jobFrequency")
            .map(offsetDay).toProperty("offsetDay")
            .map(ignoreError).toProperty("ignoreError")
            .map(des).toProperty("des")
            .map(depJob).toProperty("depJob")
            .map(jobStep).toProperty("jobStep")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.457+08:00", comments="Source Table: dy_job_attributes")
    default int insertMultiple(Collection<DyJobAttributes> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, dyJobAttributes, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobName).toProperty("jobName")
            .map(jobType).toProperty("jobType")
            .map(streamType).toProperty("streamType")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(taskStatus).toProperty("taskStatus")
            .map(multiBatch).toProperty("multiBatch")
            .map(timeWindow).toProperty("timeWindow")
            .map(virtualEnable).toProperty("virtualEnable")
            .map(priority).toProperty("priority")
            .map(callAgainMaxNum).toProperty("callAgainMaxNum")
            .map(callAgainWaitSec).toProperty("callAgainWaitSec")
            .map(checkFrequency).toProperty("checkFrequency")
            .map(checkTimeTrigger).toProperty("checkTimeTrigger")
            .map(checkStreamSelf).toProperty("checkStreamSelf")
            .map(jobFrequency).toProperty("jobFrequency")
            .map(offsetDay).toProperty("offsetDay")
            .map(ignoreError).toProperty("ignoreError")
            .map(des).toProperty("des")
            .map(depJob).toProperty("depJob")
            .map(jobStep).toProperty("jobStep")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.457+08:00", comments="Source Table: dy_job_attributes")
    default int insertSelective(DyJobAttributes record) {
        return MyBatis3Utils.insert(this::insert, record, dyJobAttributes, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(jobName).toPropertyWhenPresent("jobName", record::getJobName)
            .map(jobType).toPropertyWhenPresent("jobType", record::getJobType)
            .map(streamType).toPropertyWhenPresent("streamType", record::getStreamType)
            .map(jobDate).toPropertyWhenPresent("jobDate", record::getJobDate)
            .map(lastStatus).toPropertyWhenPresent("lastStatus", record::getLastStatus)
            .map(taskStatus).toPropertyWhenPresent("taskStatus", record::getTaskStatus)
            .map(multiBatch).toPropertyWhenPresent("multiBatch", record::getMultiBatch)
            .map(timeWindow).toPropertyWhenPresent("timeWindow", record::getTimeWindow)
            .map(virtualEnable).toPropertyWhenPresent("virtualEnable", record::getVirtualEnable)
            .map(priority).toPropertyWhenPresent("priority", record::getPriority)
            .map(callAgainMaxNum).toPropertyWhenPresent("callAgainMaxNum", record::getCallAgainMaxNum)
            .map(callAgainWaitSec).toPropertyWhenPresent("callAgainWaitSec", record::getCallAgainWaitSec)
            .map(checkFrequency).toPropertyWhenPresent("checkFrequency", record::getCheckFrequency)
            .map(checkTimeTrigger).toPropertyWhenPresent("checkTimeTrigger", record::getCheckTimeTrigger)
            .map(checkStreamSelf).toPropertyWhenPresent("checkStreamSelf", record::getCheckStreamSelf)
            .map(jobFrequency).toPropertyWhenPresent("jobFrequency", record::getJobFrequency)
            .map(offsetDay).toPropertyWhenPresent("offsetDay", record::getOffsetDay)
            .map(ignoreError).toPropertyWhenPresent("ignoreError", record::getIgnoreError)
            .map(des).toPropertyWhenPresent("des", record::getDes)
            .map(depJob).toPropertyWhenPresent("depJob", record::getDepJob)
            .map(jobStep).toPropertyWhenPresent("jobStep", record::getJobStep)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.462+08:00", comments="Source Table: dy_job_attributes")
    default Optional<DyJobAttributes> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.467+08:00", comments="Source Table: dy_job_attributes")
    default List<DyJobAttributes> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.467+08:00", comments="Source Table: dy_job_attributes")
    default List<DyJobAttributes> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.472+08:00", comments="Source Table: dy_job_attributes")
    default Optional<DyJobAttributes> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.472+08:00", comments="Source Table: dy_job_attributes")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, dyJobAttributes, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.472+08:00", comments="Source Table: dy_job_attributes")
    static UpdateDSL<UpdateModel> updateAllColumns(DyJobAttributes record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(jobName).equalTo(record::getJobName)
                .set(jobType).equalTo(record::getJobType)
                .set(streamType).equalTo(record::getStreamType)
                .set(jobDate).equalTo(record::getJobDate)
                .set(lastStatus).equalTo(record::getLastStatus)
                .set(taskStatus).equalTo(record::getTaskStatus)
                .set(multiBatch).equalTo(record::getMultiBatch)
                .set(timeWindow).equalTo(record::getTimeWindow)
                .set(virtualEnable).equalTo(record::getVirtualEnable)
                .set(priority).equalTo(record::getPriority)
                .set(callAgainMaxNum).equalTo(record::getCallAgainMaxNum)
                .set(callAgainWaitSec).equalTo(record::getCallAgainWaitSec)
                .set(checkFrequency).equalTo(record::getCheckFrequency)
                .set(checkTimeTrigger).equalTo(record::getCheckTimeTrigger)
                .set(checkStreamSelf).equalTo(record::getCheckStreamSelf)
                .set(jobFrequency).equalTo(record::getJobFrequency)
                .set(offsetDay).equalTo(record::getOffsetDay)
                .set(ignoreError).equalTo(record::getIgnoreError)
                .set(des).equalTo(record::getDes)
                .set(depJob).equalTo(record::getDepJob)
                .set(jobStep).equalTo(record::getJobStep);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.477+08:00", comments="Source Table: dy_job_attributes")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(DyJobAttributes record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(jobName).equalToWhenPresent(record::getJobName)
                .set(jobType).equalToWhenPresent(record::getJobType)
                .set(streamType).equalTo(record::getStreamType)
                .set(jobDate).equalToWhenPresent(record::getJobDate)
                .set(lastStatus).equalToWhenPresent(record::getLastStatus)
                .set(taskStatus).equalToWhenPresent(record::getTaskStatus)
                .set(multiBatch).equalToWhenPresent(record::getMultiBatch)
                .set(timeWindow).equalToWhenPresent(record::getTimeWindow)
                .set(virtualEnable).equalToWhenPresent(record::getVirtualEnable)
                .set(priority).equalToWhenPresent(record::getPriority)
                .set(callAgainMaxNum).equalToWhenPresent(record::getCallAgainMaxNum)
                .set(callAgainWaitSec).equalToWhenPresent(record::getCallAgainWaitSec)
                .set(checkFrequency).equalToWhenPresent(record::getCheckFrequency)
                .set(checkTimeTrigger).equalToWhenPresent(record::getCheckTimeTrigger)
                .set(checkStreamSelf).equalToWhenPresent(record::getCheckStreamSelf)
                .set(jobFrequency).equalToWhenPresent(record::getJobFrequency)
                .set(offsetDay).equalToWhenPresent(record::getOffsetDay)
                .set(ignoreError).equalToWhenPresent(record::getIgnoreError)
                .set(des).equalToWhenPresent(record::getDes)
                .set(depJob).equalToWhenPresent(record::getDepJob)
                .set(jobStep).equalToWhenPresent(record::getJobStep);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.477+08:00", comments="Source Table: dy_job_attributes")
    default int updateByPrimaryKey(DyJobAttributes record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(jobName).equalTo(record::getJobName)
            .set(jobType).equalTo(record::getJobType)
            .set(streamType).equalTo(record::getStreamType)
            .set(jobDate).equalTo(record::getJobDate)
            .set(lastStatus).equalTo(record::getLastStatus)
            .set(taskStatus).equalTo(record::getTaskStatus)
            .set(multiBatch).equalTo(record::getMultiBatch)
            .set(timeWindow).equalTo(record::getTimeWindow)
            .set(virtualEnable).equalTo(record::getVirtualEnable)
            .set(priority).equalTo(record::getPriority)
            .set(callAgainMaxNum).equalTo(record::getCallAgainMaxNum)
            .set(callAgainWaitSec).equalTo(record::getCallAgainWaitSec)
            .set(checkFrequency).equalTo(record::getCheckFrequency)
            .set(checkTimeTrigger).equalTo(record::getCheckTimeTrigger)
            .set(checkStreamSelf).equalTo(record::getCheckStreamSelf)
            .set(jobFrequency).equalTo(record::getJobFrequency)
            .set(offsetDay).equalTo(record::getOffsetDay)
            .set(ignoreError).equalTo(record::getIgnoreError)
            .set(des).equalTo(record::getDes)
            .set(depJob).equalTo(record::getDepJob)
            .set(jobStep).equalTo(record::getJobStep)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.482+08:00", comments="Source Table: dy_job_attributes")
    default int updateByPrimaryKeySelective(DyJobAttributes record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(jobName).equalToWhenPresent(record::getJobName)
            .set(jobType).equalToWhenPresent(record::getJobType)
            .set(streamType).equalTo(record::getStreamType)
            .set(jobDate).equalToWhenPresent(record::getJobDate)
            .set(lastStatus).equalToWhenPresent(record::getLastStatus)
            .set(taskStatus).equalToWhenPresent(record::getTaskStatus)
            .set(multiBatch).equalToWhenPresent(record::getMultiBatch)
            .set(timeWindow).equalToWhenPresent(record::getTimeWindow)
            .set(virtualEnable).equalToWhenPresent(record::getVirtualEnable)
            .set(priority).equalToWhenPresent(record::getPriority)
            .set(callAgainMaxNum).equalToWhenPresent(record::getCallAgainMaxNum)
            .set(callAgainWaitSec).equalToWhenPresent(record::getCallAgainWaitSec)
            .set(checkFrequency).equalToWhenPresent(record::getCheckFrequency)
            .set(checkTimeTrigger).equalToWhenPresent(record::getCheckTimeTrigger)
            .set(checkStreamSelf).equalToWhenPresent(record::getCheckStreamSelf)
            .set(jobFrequency).equalToWhenPresent(record::getJobFrequency)
            .set(offsetDay).equalToWhenPresent(record::getOffsetDay)
            .set(ignoreError).equalToWhenPresent(record::getIgnoreError)
            .set(des).equalToWhenPresent(record::getDes)
            .set(depJob).equalToWhenPresent(record::getDepJob)
            .set(jobStep).equalToWhenPresent(record::getJobStep)
            .where(id, isEqualTo(record::getId))
        );
    }
    
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<DyJobAttributes> selectManyPage(SelectStatementProvider selectStatement, Page<DyJobAttributes> page);
    
	default Page <DyJobAttributes> selectManyInfo(Page<DyJobAttributes> page){
    	SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(dyJobAttributes).build()
    			.render(RenderingStrategies.MYBATIS3);
    	List<DyJobAttributes> selectManyPage = selectManyPage(selectStatement, page);
    	page = page.setRecords(selectManyPage);
    	return page;
    }
	
	default Optional<DyJobAttributes> selectByJobName(String platform_, String systems_, String job_) {
        return selectOne(c ->
            c.where(job, isEqualTo(job_))
            .and(platform, isEqualToWhenPresent(platform_))
            .and(systems, isEqualToWhenPresent(systems_))
        );
    }
}