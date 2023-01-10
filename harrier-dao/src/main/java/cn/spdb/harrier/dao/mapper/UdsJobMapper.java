package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.callAgainNum;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.des;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.dispatcherTime;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.endTime;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.id;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.job;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.jobDate;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.lastStatus;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.multiBatch;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.nextJobDate;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.numTimes;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.pendingTime;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.platform;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.serverName;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.startTime;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.streamType;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.systems;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.udsJob;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.updateTime;
import static org.mybatis.dynamic.sql.SqlBuilder.constant;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThan;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThanOrEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.or;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Generated;

import org.apache.commons.lang3.ObjectUtils;
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

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.enmus.UdsJobType;
import cn.spdb.harrier.dao.entity.UdsJob;

@Mapper
public interface UdsJobMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, lastStatus, jobDate, nextJobDate,
			serverName, multiBatch, pendingTime, dispatcherTime, startTime, endTime, numTimes, callAgainNum, streamType,
			updateTime,des);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<UdsJob> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<UdsJob> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UdsJobResult")
	Optional<UdsJob> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UdsJobResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job", property = "job", jdbcType = JdbcType.VARCHAR),
			@Result(column = "last_status", property = "lastStatus", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job_date", property = "jobDate", jdbcType = JdbcType.DATE),
			@Result(column = "next_job_date", property = "nextJobDate", jdbcType = JdbcType.DATE),
			@Result(column = "server_name", property = "serverName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "multi_batch", property = "multiBatch", jdbcType = JdbcType.INTEGER),
			@Result(column = "pending_time", property = "pendingTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "dispatcher_time", property = "dispatcherTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "start_time", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "num_times", property = "numTimes", jdbcType = JdbcType.BIGINT),
			@Result(column = "call_again_num", property = "callAgainNum", jdbcType = JdbcType.INTEGER),
			@Result(column = "stream_type", property = "streamType", jdbcType = JdbcType.TINYINT),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR) })
	List<UdsJob> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	default int insert(UdsJob record) {
		return MyBatis3Utils.insert(this::insert, record, udsJob,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(job).toProperty("job").map(lastStatus).toProperty("lastStatus").map(jobDate)
						.toProperty("jobDate").map(nextJobDate).toProperty("nextJobDate").map(serverName)
						.toProperty("serverName").map(multiBatch).toProperty("multiBatch").map(pendingTime)
						.toProperty("pendingTime").map(dispatcherTime).toProperty("dispatcherTime").map(startTime)
						.toProperty("startTime").map(endTime).toProperty("endTime").map(numTimes).toProperty("numTimes")
						.map(callAgainNum).toProperty("callAgainNum").map(streamType).toProperty("streamType").map(des)
						.toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.232+08:00", comments = "Source Table: uds_job")
	default int insertMultiple(Collection<UdsJob> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJob,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(job).toProperty("job").map(lastStatus).toProperty("lastStatus").map(jobDate)
						.toProperty("jobDate").map(nextJobDate).toProperty("nextJobDate").map(serverName)
						.toProperty("serverName").map(multiBatch).toProperty("multiBatch").map(pendingTime)
						.toProperty("pendingTime").map(dispatcherTime).toProperty("dispatcherTime").map(startTime)
						.toProperty("startTime").map(endTime).toProperty("endTime").map(numTimes).toProperty("numTimes")
						.map(callAgainNum).toProperty("callAgainNum").map(streamType).toProperty("streamType").map(des)
						.toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default int insertSelective(UdsJob record) {
		return MyBatis3Utils.insert(this::insert, record, udsJob,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(platform)
						.toPropertyWhenPresent("platform", record::getPlatform).map(systems)
						.toPropertyWhenPresent("systems", record::getSystems).map(job)
						.toPropertyWhenPresent("job", record::getJob).map(lastStatus)
						.toPropertyWhenPresent("lastStatus", record::getLastStatus).map(jobDate)
						.toPropertyWhenPresent("jobDate", record::getJobDate).map(nextJobDate)
						.toPropertyWhenPresent("nextJobDate", record::getNextJobDate).map(serverName)
						.toPropertyWhenPresent("serverName", record::getServerName).map(multiBatch)
						.toPropertyWhenPresent("multiBatch", record::getMultiBatch).map(pendingTime)
						.toPropertyWhenPresent("pendingTime", record::getPendingTime).map(dispatcherTime)
						.toPropertyWhenPresent("dispatcherTime", record::getDispatcherTime).map(startTime)
						.toPropertyWhenPresent("startTime", record::getStartTime).map(endTime)
						.toPropertyWhenPresent("endTime", record::getEndTime).map(numTimes)
						.toPropertyWhenPresent("numTimes", record::getNumTimes).map(callAgainNum)
						.toPropertyWhenPresent("callAgainNum", record::getCallAgainNum).map(streamType)
						.toPropertyWhenPresent("streamType", record::getStreamType).map(des)
						.toPropertyWhenPresent("des", record::getDes));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default Optional<UdsJob> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default List<UdsJob> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default List<UdsJob> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default Optional<UdsJob> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, udsJob, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	static UpdateDSL<UpdateModel> updateAllColumns(UdsJob record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(platform).equalTo(record::getPlatform).set(systems)
				.equalTo(record::getSystems).set(job).equalTo(record::getJob).set(lastStatus)
				.equalTo(record::getLastStatus).set(jobDate).equalTo(record::getJobDate).set(nextJobDate)
				.equalTo(record::getNextJobDate).set(serverName).equalTo(record::getServerName).set(multiBatch)
				.equalTo(record::getMultiBatch).set(pendingTime).equalTo(record::getPendingTime).set(dispatcherTime)
				.equalTo(record::getDispatcherTime).set(startTime).equalTo(record::getStartTime).set(endTime)
				.equalTo(record::getEndTime).set(numTimes).equalTo(record::getNumTimes).set(callAgainNum)
				.equalTo(record::getCallAgainNum).set(streamType).equalTo(record::getStreamType).set(des)
				.equalTo(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJob record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(platform).equalToWhenPresent(record::getPlatform)
				.set(systems).equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob)
				.set(lastStatus).equalToWhenPresent(record::getLastStatus).set(jobDate)
				.equalToWhenPresent(record::getJobDate).set(nextJobDate).equalToWhenPresent(record::getNextJobDate)
				.set(serverName).equalToWhenPresent(record::getServerName).set(multiBatch)
				.equalToWhenPresent(record::getMultiBatch).set(pendingTime).equalToWhenPresent(record::getPendingTime)
				.set(dispatcherTime).equalToWhenPresent(record::getDispatcherTime).set(startTime)
				.equalToWhenPresent(record::getStartTime).set(endTime).equalToWhenPresent(record::getEndTime)
				.set(numTimes).equalToWhenPresent(record::getNumTimes).set(callAgainNum)
				.equalToWhenPresent(record::getCallAgainNum).set(streamType).equalToWhenPresent(record::getStreamType)
				.set(des).equalToWhenPresent(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default int updateByPrimaryKey(UdsJob record) {
		return update(c -> c.set(platform).equalTo(record::getPlatform).set(systems).equalTo(record::getSystems)
				.set(job).equalTo(record::getJob).set(lastStatus).equalTo(record::getLastStatus).set(jobDate)
				.equalTo(record::getJobDate).set(nextJobDate).equalTo(record::getNextJobDate).set(serverName)
				.equalTo(record::getServerName).set(multiBatch).equalTo(record::getMultiBatch).set(pendingTime)
				.equalTo(record::getPendingTime).set(dispatcherTime).equalTo(record::getDispatcherTime).set(startTime)
				.equalTo(record::getStartTime).set(endTime).equalTo(record::getEndTime).set(numTimes)
				.equalTo(record::getNumTimes).set(callAgainNum).equalTo(record::getCallAgainNum).set(streamType)
				.equalTo(record::getStreamType).set(des).equalTo(record::getDes).where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.233+08:00", comments = "Source Table: uds_job")
	default int updateByPrimaryKeySelective(UdsJob record) {
		return update(c -> c.set(platform).equalToWhenPresent(record::getPlatform).set(systems)
				.equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob).set(lastStatus)
				.equalToWhenPresent(record::getLastStatus).set(jobDate).equalToWhenPresent(record::getJobDate)
				.set(nextJobDate).equalToWhenPresent(record::getNextJobDate).set(serverName)
				.equalToWhenPresent(record::getServerName).set(multiBatch).equalToWhenPresent(record::getMultiBatch)
				.set(pendingTime).equalToWhenPresent(record::getPendingTime).set(dispatcherTime)
				.equalToWhenPresent(record::getDispatcherTime).set(startTime).equalToWhenPresent(record::getStartTime)
				.set(endTime).equalToWhenPresent(record::getEndTime).set(numTimes)
				.equalToWhenPresent(record::getNumTimes).set(callAgainNum).equalToWhenPresent(record::getCallAgainNum)
				.set(streamType).equalToWhenPresent(record::getStreamType).set(des).equalToWhenPresent(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	default int updateByPrimaryKeySelective(UdsJob record,ExecutionStatus status) {
		return update(c -> c.set(platform).equalToWhenPresent(record::getPlatform).set(systems)
				.equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob).set(lastStatus)
				.equalToWhenPresent(record::getLastStatus).set(jobDate).equalToWhenPresent(record::getJobDate)
				.set(nextJobDate).equalToWhenPresent(record::getNextJobDate).set(serverName)
				.equalToWhenPresent(record::getServerName).set(multiBatch).equalToWhenPresent(record::getMultiBatch)
				.set(pendingTime).equalToWhenPresent(record::getPendingTime).set(dispatcherTime)
				.equalToWhenPresent(record::getDispatcherTime).set(startTime).equalToWhenPresent(record::getStartTime)
				.set(endTime).equalToWhenPresent(record::getEndTime).set(numTimes)
				.equalToWhenPresent(record::getNumTimes).set(callAgainNum).equalToWhenPresent(record::getCallAgainNum)
				.set(streamType).equalToWhenPresent(record::getStreamType).set(des).equalToWhenPresent(record::getDes)
				.where(id, isEqualTo(record::getId)).and(lastStatus, isEqualToWhenPresent(status.name())));
	}

	
	
	default Optional<UdsJob> selectBySignal(String signal) {
		return selectOne(MyBatis3Utils.select(SqlBuilder.select(selectList).from(udsJob), c -> {
			return c.join(UdsJobSourceDynamicSqlSupport.udsJobSource)
					.on(platform, equalTo(UdsJobSourceDynamicSqlSupport.platform))
					.and(systems, equalTo(UdsJobSourceDynamicSqlSupport.systems))
					.and(job, equalTo(UdsJobSourceDynamicSqlSupport.job))
					.where(UdsJobSourceDynamicSqlSupport.isEnable, isEqualTo(true))
					.and(UdsJobSourceDynamicSqlSupport.signals, isEqualTo(signal));
		}));
	}

	default List<UdsJob> getDependency(String platform_, String systems_, String job_){
		SelectStatementProvider statementProvider = SqlBuilder.select(selectList).from(udsJob)
				.join(UdsJobConfigDynamicSqlSupport.udsJobConfig)
				.on(platform, equalTo(UdsJobConfigDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobConfigDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobConfigDynamicSqlSupport.job))
				.join(UdsJobDependencyDynamicSqlSupport.udsJobDependency)
				.on(platform, equalTo(UdsJobDependencyDynamicSqlSupport.depPlatform))
				.and(systems, equalTo(UdsJobDependencyDynamicSqlSupport.depSystem))
				.and(job, equalTo(UdsJobDependencyDynamicSqlSupport.depJob))
				.where(UdsJobDependencyDynamicSqlSupport.platform, isEqualTo(platform_))
				.and(UdsJobDependencyDynamicSqlSupport.systems, isEqualTo(systems_))
				.and(UdsJobDependencyDynamicSqlSupport.job, isEqualTo(job_))
				.and(UdsJobConfigDynamicSqlSupport.isEnable, isEqualTo(true))
				.build().render(RenderingStrategies.MYBATIS3);
				return selectMany(statementProvider);
	}

	default List<UdsJob> selectByDepAndCondition(String depPlatform, String depSystem, String depJob,Integer batch){
		SelectStatementProvider statementProvider = SqlBuilder.select(selectList).from(udsJob)
				.join(UdsJobConfigDynamicSqlSupport.udsJobConfig)
				.on(platform, equalTo(UdsJobConfigDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobConfigDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobConfigDynamicSqlSupport.job))
				.join(UdsJobSourceDynamicSqlSupport.udsJobSource)
				.on(platform, equalTo(UdsJobSourceDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobSourceDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobSourceDynamicSqlSupport.job))
				.join(UdsJobDependencyDynamicSqlSupport.udsJobDependency)
				.on(platform, equalTo(UdsJobDependencyDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobDependencyDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobDependencyDynamicSqlSupport.job))
				.where(UdsJobDependencyDynamicSqlSupport.depPlatform, isEqualTo(depPlatform))
				.and(UdsJobDependencyDynamicSqlSupport.depSystem, isEqualTo(depSystem))
				.and(UdsJobDependencyDynamicSqlSupport.depJob, isEqualTo(depJob))
				.and(UdsJobConfigDynamicSqlSupport.isEnable, isEqualTo(true))
				.and(UdsJobDependencyDynamicSqlSupport.depBatch, isLessThanOrEqualTo(batch))
				.and(UdsJobConfigDynamicSqlSupport.jobType, isEqualTo(UdsJobType.C.name()))
				.build().render(RenderingStrategies.MYBATIS3);
				return selectMany(statementProvider);
	}

	default Optional<UdsJob> selectOne(String platform_, String systems_, String job_){
		return selectOne(c->c.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job,  isEqualTo(job_)));
	}

	default List<UdsJob> selectOverTimePendingOrDispathcer(){
		SelectStatementProvider statementProvider = SqlBuilder.select(selectList).from(udsJob)
				.join(UdsJobConfigDynamicSqlSupport.udsJobConfig)
				.on(platform, equalTo(UdsJobConfigDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobConfigDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobConfigDynamicSqlSupport.job))
				.where(UdsJobConfigDynamicSqlSupport.isEnable, isEqualTo(true))
				.and(updateTime, isLessThan(LocalDateTime.now().plusMinutes(-5)))
				.and(lastStatus, isEqualTo(ExecutionStatus.PENDING.name()),
						or(lastStatus, isEqualTo(ExecutionStatus.DISPATCHER.name())))
				.build().render(RenderingStrategies.MYBATIS3);
		return selectMany(statementProvider);
	}

	default int invokeJob(String platform_,String systems_,String job_,LocalDate jobdate_,Integer multibatch_,Byte streamtype_) {
		return update(c->c.set(UdsJobDynamicSqlSupport.lastStatus).equalTo(ExecutionStatus.PENDING.name())
				.set(UdsJobDynamicSqlSupport.jobDate).equalTo(jobdate_)
				.set(UdsJobDynamicSqlSupport.multiBatch).equalTo(multibatch_)
				.set(UdsJobDynamicSqlSupport.streamType).equalTo(streamtype_)
				.set(UdsJobDynamicSqlSupport.updateTime).equalTo(LocalDateTime.of(1970, 1, 1,0,0))
				.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_)));
		
	}

	default int updateJobStatus(ExecutionStatus newStatus,ExecutionStatus oldStatus,String platform_,String systems_,String job_
			,String serverName_) {
		String oldStatusStr=ObjectUtils.isEmpty(oldStatus)?null:oldStatus.name();
		return update(c->c.set(lastStatus).equalTo(newStatus.name())
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isEqualToWhenPresent(job_))
				.and(lastStatus, isEqualToWhenPresent(oldStatusStr))
				.and(serverName, isEqualToWhenPresent(serverName_)));
	}
	
	default List<UdsJob> selectUdsJob(UdsJob udsJob){
		return select(c->
				c.where(platform, isEqualToWhenPresent(udsJob.getPlatform()))
				.and(systems, isEqualToWhenPresent(udsJob.getSystems()))
				.and(job, isEqualToWhenPresent(udsJob.getJob()))
				.and(lastStatus, isEqualToWhenPresent(udsJob.getLastStatus()))
				.and(jobDate, isEqualToWhenPresent(udsJob.getJobDate()))
				.and(multiBatch, isEqualToWhenPresent(udsJob.getMultiBatch()))
				.and(serverName, isEqualToWhenPresent(udsJob.getServerName()))
				);
	}
	
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<Map<String, Object>> sumAllPatformJob(SelectStatementProvider selectStatement);
	//首页-统计各平台作业总数
	default List<Map<String, Object>> sumAllPatformJob() {
		
		SelectStatementProvider statementProvider = SqlBuilder.select(constant("platform,count(*) as num"))
				.from(udsJob)
				.groupBy(platform)
				.build()
				.render(RenderingStrategies.MYBATIS3);
		
	return sumAllPatformJob(statementProvider);
}
}