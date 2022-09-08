package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobStepRecordDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
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
public interface UdsJobStepRecordMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    BasicColumn[] selectList = BasicColumn.columnList(id, jobRecordId, platform, systems, job, numTimes, stepNum, returnCode, address, environments, cmd, scriptPath, parameter, startTime, endTime, logPath);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source Table: uds_job_step_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source Table: uds_job_step_record")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source Table: uds_job_step_record")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobStepRecord> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.805+08:00", comments="Source Table: uds_job_step_record")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobStepRecord> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobStepRecordResult")
    Optional<UdsJobStepRecord> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobStepRecordResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="job_record_id", property="jobRecordId", jdbcType=JdbcType.BIGINT),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="num_times", property="numTimes", jdbcType=JdbcType.BIGINT),
        @Result(column="step_num", property="stepNum", jdbcType=JdbcType.TINYINT),
        @Result(column="return_code", property="returnCode", jdbcType=JdbcType.INTEGER),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="environments", property="environments", jdbcType=JdbcType.VARCHAR),
        @Result(column="cmd", property="cmd", jdbcType=JdbcType.VARCHAR),
        @Result(column="script_path", property="scriptPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="parameter", property="parameter", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="log_path", property="logPath", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobStepRecord> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default int insert(UdsJobStepRecord record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobStepRecord, c ->
            c.map(id).toProperty("id")
            .map(jobRecordId).toProperty("jobRecordId")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(numTimes).toProperty("numTimes")
            .map(stepNum).toProperty("stepNum")
            .map(returnCode).toProperty("returnCode")
            .map(address).toProperty("address")
            .map(environments).toProperty("environments")
            .map(cmd).toProperty("cmd")
            .map(scriptPath).toProperty("scriptPath")
            .map(parameter).toProperty("parameter")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(logPath).toProperty("logPath")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default int insertMultiple(Collection<UdsJobStepRecord> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobStepRecord, c ->
            c.map(id).toProperty("id")
            .map(jobRecordId).toProperty("jobRecordId")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(numTimes).toProperty("numTimes")
            .map(stepNum).toProperty("stepNum")
            .map(returnCode).toProperty("returnCode")
            .map(address).toProperty("address")
            .map(environments).toProperty("environments")
            .map(cmd).toProperty("cmd")
            .map(scriptPath).toProperty("scriptPath")
            .map(parameter).toProperty("parameter")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(logPath).toProperty("logPath")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.806+08:00", comments="Source Table: uds_job_step_record")
    default int insertSelective(UdsJobStepRecord record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobStepRecord, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(jobRecordId).toPropertyWhenPresent("jobRecordId", record::getJobRecordId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(numTimes).toPropertyWhenPresent("numTimes", record::getNumTimes)
            .map(stepNum).toPropertyWhenPresent("stepNum", record::getStepNum)
            .map(returnCode).toPropertyWhenPresent("returnCode", record::getReturnCode)
            .map(address).toPropertyWhenPresent("address", record::getAddress)
            .map(environments).toPropertyWhenPresent("environments", record::getEnvironments)
            .map(cmd).toPropertyWhenPresent("cmd", record::getCmd)
            .map(scriptPath).toPropertyWhenPresent("scriptPath", record::getScriptPath)
            .map(parameter).toPropertyWhenPresent("parameter", record::getParameter)
            .map(startTime).toPropertyWhenPresent("startTime", record::getStartTime)
            .map(endTime).toPropertyWhenPresent("endTime", record::getEndTime)
            .map(logPath).toPropertyWhenPresent("logPath", record::getLogPath)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default Optional<UdsJobStepRecord> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default List<UdsJobStepRecord> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default List<UdsJobStepRecord> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default Optional<UdsJobStepRecord> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobStepRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobStepRecord record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(jobRecordId).equalTo(record::getJobRecordId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(numTimes).equalTo(record::getNumTimes)
                .set(stepNum).equalTo(record::getStepNum)
                .set(returnCode).equalTo(record::getReturnCode)
                .set(address).equalTo(record::getAddress)
                .set(environments).equalTo(record::getEnvironments)
                .set(cmd).equalTo(record::getCmd)
                .set(scriptPath).equalTo(record::getScriptPath)
                .set(parameter).equalTo(record::getParameter)
                .set(startTime).equalTo(record::getStartTime)
                .set(endTime).equalTo(record::getEndTime)
                .set(logPath).equalTo(record::getLogPath);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobStepRecord record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(jobRecordId).equalToWhenPresent(record::getJobRecordId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(numTimes).equalToWhenPresent(record::getNumTimes)
                .set(stepNum).equalToWhenPresent(record::getStepNum)
                .set(returnCode).equalToWhenPresent(record::getReturnCode)
                .set(address).equalToWhenPresent(record::getAddress)
                .set(environments).equalToWhenPresent(record::getEnvironments)
                .set(cmd).equalToWhenPresent(record::getCmd)
                .set(scriptPath).equalToWhenPresent(record::getScriptPath)
                .set(parameter).equalToWhenPresent(record::getParameter)
                .set(startTime).equalToWhenPresent(record::getStartTime)
                .set(endTime).equalToWhenPresent(record::getEndTime)
                .set(logPath).equalToWhenPresent(record::getLogPath);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default int updateByPrimaryKey(UdsJobStepRecord record) {
        return update(c ->
            c.set(jobRecordId).equalTo(record::getJobRecordId)
            .set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(numTimes).equalTo(record::getNumTimes)
            .set(stepNum).equalTo(record::getStepNum)
            .set(returnCode).equalTo(record::getReturnCode)
            .set(address).equalTo(record::getAddress)
            .set(environments).equalTo(record::getEnvironments)
            .set(cmd).equalTo(record::getCmd)
            .set(scriptPath).equalTo(record::getScriptPath)
            .set(parameter).equalTo(record::getParameter)
            .set(startTime).equalTo(record::getStartTime)
            .set(endTime).equalTo(record::getEndTime)
            .set(logPath).equalTo(record::getLogPath)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.807+08:00", comments="Source Table: uds_job_step_record")
    default int updateByPrimaryKeySelective(UdsJobStepRecord record) {
        return update(c ->
            c.set(jobRecordId).equalToWhenPresent(record::getJobRecordId)
            .set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(numTimes).equalToWhenPresent(record::getNumTimes)
            .set(stepNum).equalToWhenPresent(record::getStepNum)
            .set(returnCode).equalToWhenPresent(record::getReturnCode)
            .set(address).equalToWhenPresent(record::getAddress)
            .set(environments).equalToWhenPresent(record::getEnvironments)
            .set(cmd).equalToWhenPresent(record::getCmd)
            .set(scriptPath).equalToWhenPresent(record::getScriptPath)
            .set(parameter).equalToWhenPresent(record::getParameter)
            .set(startTime).equalToWhenPresent(record::getStartTime)
            .set(endTime).equalToWhenPresent(record::getEndTime)
            .set(logPath).equalToWhenPresent(record::getLogPath)
            .where(id, isEqualTo(record::getId))
        );
    }
    
    default List<UdsJobStepRecord> selectJobStepRecord(Long job_record_id){
		return select(c->c.where(jobRecordId, isEqualTo(job_record_id)));
	}
}