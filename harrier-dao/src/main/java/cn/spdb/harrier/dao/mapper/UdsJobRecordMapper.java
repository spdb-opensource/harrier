package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobRecordDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobRecord;
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
public interface UdsJobRecordMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, jobType, serverName, jobDate, lastStatus, pendingTime, dispatcherTime, startTime, endTime, streamType, virtualEnable, multiBatch, numTimes);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.798+08:00", comments="Source Table: uds_job_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.798+08:00", comments="Source Table: uds_job_record")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobRecord> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobRecord> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobRecordResult")
    Optional<UdsJobRecord> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobRecordResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_type", property="jobType", jdbcType=JdbcType.VARCHAR),
        @Result(column="server_name", property="serverName", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_date", property="jobDate", jdbcType=JdbcType.VARCHAR),
        @Result(column="last_status", property="lastStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="pending_time", property="pendingTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dispatcher_time", property="dispatcherTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="stream_type", property="streamType", jdbcType=JdbcType.TINYINT),
        @Result(column="virtual_enable", property="virtualEnable", jdbcType=JdbcType.BIT),
        @Result(column="multi_batch", property="multiBatch", jdbcType=JdbcType.INTEGER),
        @Result(column="num_times", property="numTimes", jdbcType=JdbcType.BIGINT)
    })
    List<UdsJobRecord> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default int insert(UdsJobRecord record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobRecord, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobType).toProperty("jobType")
            .map(serverName).toProperty("serverName")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(pendingTime).toProperty("pendingTime")
            .map(dispatcherTime).toProperty("dispatcherTime")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(streamType).toProperty("streamType")
            .map(virtualEnable).toProperty("virtualEnable")
            .map(multiBatch).toProperty("multiBatch")
            .map(numTimes).toProperty("numTimes")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default int insertMultiple(Collection<UdsJobRecord> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobRecord, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobType).toProperty("jobType")
            .map(serverName).toProperty("serverName")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(pendingTime).toProperty("pendingTime")
            .map(dispatcherTime).toProperty("dispatcherTime")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(streamType).toProperty("streamType")
            .map(virtualEnable).toProperty("virtualEnable")
            .map(multiBatch).toProperty("multiBatch")
            .map(numTimes).toProperty("numTimes")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.799+08:00", comments="Source Table: uds_job_record")
    default int insertSelective(UdsJobRecord record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobRecord, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(jobType).toPropertyWhenPresent("jobType", record::getJobType)
            .map(serverName).toPropertyWhenPresent("serverName", record::getServerName)
            .map(jobDate).toPropertyWhenPresent("jobDate", record::getJobDate)
            .map(lastStatus).toPropertyWhenPresent("lastStatus", record::getLastStatus)
            .map(pendingTime).toPropertyWhenPresent("pendingTime", record::getPendingTime)
            .map(dispatcherTime).toPropertyWhenPresent("dispatcherTime", record::getDispatcherTime)
            .map(startTime).toPropertyWhenPresent("startTime", record::getStartTime)
            .map(endTime).toPropertyWhenPresent("endTime", record::getEndTime)
            .map(streamType).toPropertyWhenPresent("streamType", record::getStreamType)
            .map(virtualEnable).toPropertyWhenPresent("virtualEnable", record::getVirtualEnable)
            .map(multiBatch).toPropertyWhenPresent("multiBatch", record::getMultiBatch)
            .map(numTimes).toPropertyWhenPresent("numTimes", record::getNumTimes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default Optional<UdsJobRecord> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default List<UdsJobRecord> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default List<UdsJobRecord> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default Optional<UdsJobRecord> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobRecord, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobRecord record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(jobType).equalTo(record::getJobType)
                .set(serverName).equalTo(record::getServerName)
                .set(jobDate).equalTo(record::getJobDate)
                .set(lastStatus).equalTo(record::getLastStatus)
                .set(pendingTime).equalTo(record::getPendingTime)
                .set(dispatcherTime).equalTo(record::getDispatcherTime)
                .set(startTime).equalTo(record::getStartTime)
                .set(endTime).equalTo(record::getEndTime)
                .set(streamType).equalTo(record::getStreamType)
                .set(virtualEnable).equalTo(record::getVirtualEnable)
                .set(multiBatch).equalTo(record::getMultiBatch)
                .set(numTimes).equalTo(record::getNumTimes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobRecord record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(jobType).equalToWhenPresent(record::getJobType)
                .set(serverName).equalToWhenPresent(record::getServerName)
                .set(jobDate).equalToWhenPresent(record::getJobDate)
                .set(lastStatus).equalToWhenPresent(record::getLastStatus)
                .set(pendingTime).equalToWhenPresent(record::getPendingTime)
                .set(dispatcherTime).equalToWhenPresent(record::getDispatcherTime)
                .set(startTime).equalToWhenPresent(record::getStartTime)
                .set(endTime).equalToWhenPresent(record::getEndTime)
                .set(streamType).equalToWhenPresent(record::getStreamType)
                .set(virtualEnable).equalToWhenPresent(record::getVirtualEnable)
                .set(multiBatch).equalToWhenPresent(record::getMultiBatch)
                .set(numTimes).equalToWhenPresent(record::getNumTimes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.8+08:00", comments="Source Table: uds_job_record")
    default int updateByPrimaryKey(UdsJobRecord record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(jobType).equalTo(record::getJobType)
            .set(serverName).equalTo(record::getServerName)
            .set(jobDate).equalTo(record::getJobDate)
            .set(lastStatus).equalTo(record::getLastStatus)
            .set(pendingTime).equalTo(record::getPendingTime)
            .set(dispatcherTime).equalTo(record::getDispatcherTime)
            .set(startTime).equalTo(record::getStartTime)
            .set(endTime).equalTo(record::getEndTime)
            .set(streamType).equalTo(record::getStreamType)
            .set(virtualEnable).equalTo(record::getVirtualEnable)
            .set(multiBatch).equalTo(record::getMultiBatch)
            .set(numTimes).equalTo(record::getNumTimes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.801+08:00", comments="Source Table: uds_job_record")
    default int updateByPrimaryKeySelective(UdsJobRecord record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(jobType).equalToWhenPresent(record::getJobType)
            .set(serverName).equalToWhenPresent(record::getServerName)
            .set(jobDate).equalToWhenPresent(record::getJobDate)
            .set(lastStatus).equalToWhenPresent(record::getLastStatus)
            .set(pendingTime).equalToWhenPresent(record::getPendingTime)
            .set(dispatcherTime).equalToWhenPresent(record::getDispatcherTime)
            .set(startTime).equalToWhenPresent(record::getStartTime)
            .set(endTime).equalToWhenPresent(record::getEndTime)
            .set(streamType).equalToWhenPresent(record::getStreamType)
            .set(virtualEnable).equalToWhenPresent(record::getVirtualEnable)
            .set(multiBatch).equalToWhenPresent(record::getMultiBatch)
            .set(numTimes).equalToWhenPresent(record::getNumTimes)
            .where(id, isEqualTo(record::getId))
        );
    }
    
    default List<UdsJobRecord> selectJobRecord(String platform_, String system_, String job_){
		return select(c->c.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(system_))
				.and(job, isEqualTo(job_)));
	}
}