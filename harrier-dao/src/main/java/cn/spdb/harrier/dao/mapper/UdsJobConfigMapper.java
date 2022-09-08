package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThanOrEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isNotEqualTo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import cn.spdb.harrier.common.enmus.UdsJobType;
import cn.spdb.harrier.dao.entity.UdsJobConfig;

@Mapper
public interface UdsJobConfigMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.545+08:00", comments = "Source Table: uds_job_config")
	BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, jobName, jobType, offsetDay,
			timeWindow, virtualEnable, priority, callAgainMaxNum, callAgainWaitSec, countBatch, batchConversion,
			checkFrequency, checkTimeTrigger, checkStreamSelf, ignoreError, checkWeight, isEnable, des);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.525+08:00", comments = "Source Table: uds_job_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.526+08:00", comments = "Source Table: uds_job_config")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.527+08:00", comments = "Source Table: uds_job_config")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<UdsJobConfig> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.529+08:00", comments = "Source Table: uds_job_config")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<UdsJobConfig> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.53+08:00", comments = "Source Table: uds_job_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UdsJobConfigResult")
	Optional<UdsJobConfig> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.53+08:00", comments = "Source Table: uds_job_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UdsJobConfigResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job", property = "job", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job_name", property = "jobName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job_type", property = "jobType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "offset_day", property = "offsetDay", jdbcType = JdbcType.TINYINT),
			@Result(column = "time_window", property = "timeWindow", jdbcType = JdbcType.VARCHAR),
			@Result(column = "virtual_enable", property = "virtualEnable", jdbcType = JdbcType.BIT),
			@Result(column = "priority", property = "priority", jdbcType = JdbcType.INTEGER),
			@Result(column = "call_again_max_num", property = "callAgainMaxNum", jdbcType = JdbcType.TINYINT),
			@Result(column = "call_again_wait_sec", property = "callAgainWaitSec", jdbcType = JdbcType.TINYINT),
			@Result(column = "count_batch", property = "countBatch", jdbcType = JdbcType.INTEGER),
			@Result(column = "batch_conversion", property = "batchConversion", jdbcType = JdbcType.INTEGER),
			@Result(column = "check_frequency", property = "checkFrequency", jdbcType = JdbcType.BIT),
			@Result(column = "check_time_trigger", property = "checkTimeTrigger", jdbcType = JdbcType.BIT),
			@Result(column = "check_stream_self", property = "checkStreamSelf", jdbcType = JdbcType.BIT),
			@Result(column = "ignore_error", property = "ignoreError", jdbcType = JdbcType.BIT),
			@Result(column = "check_weight", property = "checkWeight", jdbcType = JdbcType.BIT),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR) })
	List<UdsJobConfig> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.533+08:00", comments = "Source Table: uds_job_config")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.534+08:00", comments = "Source Table: uds_job_config")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.536+08:00", comments = "Source Table: uds_job_config")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.537+08:00", comments = "Source Table: uds_job_config")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.538+08:00", comments = "Source Table: uds_job_config")
	default int insert(UdsJobConfig record) {
		return MyBatis3Utils.insert(this::insert, record, udsJobConfig,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(job).toProperty("job").map(jobName).toProperty("jobName").map(jobType)
						.toProperty("jobType").map(offsetDay).toProperty("offsetDay").map(timeWindow)
						.toProperty("timeWindow").map(virtualEnable).toProperty("virtualEnable").map(priority)
						.toProperty("priority").map(callAgainMaxNum).toProperty("callAgainMaxNum").map(callAgainWaitSec)
						.toProperty("callAgainWaitSec").map(countBatch).toProperty("countBatch").map(batchConversion)
						.toProperty("batchConversion").map(checkFrequency).toProperty("checkFrequency")
						.map(checkTimeTrigger).toProperty("checkTimeTrigger").map(checkStreamSelf)
						.toProperty("checkStreamSelf").map(ignoreError).toProperty("ignoreError").map(checkWeight)
						.toProperty("checkWeight").map(isEnable).toProperty("enable").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.541+08:00", comments = "Source Table: uds_job_config")
	default int insertMultiple(Collection<UdsJobConfig> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobConfig,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(job).toProperty("job").map(jobName).toProperty("jobName").map(jobType)
						.toProperty("jobType").map(offsetDay).toProperty("offsetDay").map(timeWindow)
						.toProperty("timeWindow").map(virtualEnable).toProperty("virtualEnable").map(priority)
						.toProperty("priority").map(callAgainMaxNum).toProperty("callAgainMaxNum").map(callAgainWaitSec)
						.toProperty("callAgainWaitSec").map(countBatch).toProperty("countBatch").map(batchConversion)
						.toProperty("batchConversion").map(checkFrequency).toProperty("checkFrequency")
						.map(checkTimeTrigger).toProperty("checkTimeTrigger").map(checkStreamSelf)
						.toProperty("checkStreamSelf").map(ignoreError).toProperty("ignoreError").map(checkWeight)
						.toProperty("checkWeight").map(isEnable).toProperty("enable").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.542+08:00", comments = "Source Table: uds_job_config")
	default int insertSelective(UdsJobConfig record) {
		return MyBatis3Utils.insert(this::insert, record, udsJobConfig,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(platform)
						.toPropertyWhenPresent("platform", record::getPlatform).map(systems)
						.toPropertyWhenPresent("systems", record::getSystems).map(job)
						.toPropertyWhenPresent("job", record::getJob).map(jobName)
						.toPropertyWhenPresent("jobName", record::getJobName).map(jobType)
						.toPropertyWhenPresent("jobType", record::getJobType).map(offsetDay)
						.toPropertyWhenPresent("offsetDay", record::getOffsetDay).map(timeWindow)
						.toPropertyWhenPresent("timeWindow", record::getTimeWindow).map(virtualEnable)
						.toPropertyWhenPresent("virtualEnable", record::getVirtualEnable).map(priority)
						.toPropertyWhenPresent("priority", record::getPriority).map(callAgainMaxNum)
						.toPropertyWhenPresent("callAgainMaxNum", record::getCallAgainMaxNum).map(callAgainWaitSec)
						.toPropertyWhenPresent("callAgainWaitSec", record::getCallAgainWaitSec).map(countBatch)
						.toPropertyWhenPresent("countBatch", record::getCountBatch).map(batchConversion)
						.toPropertyWhenPresent("batchConversion", record::getBatchConversion).map(checkFrequency)
						.toPropertyWhenPresent("checkFrequency", record::getCheckFrequency).map(checkTimeTrigger)
						.toPropertyWhenPresent("checkTimeTrigger", record::getCheckTimeTrigger).map(checkStreamSelf)
						.toPropertyWhenPresent("checkStreamSelf", record::getCheckStreamSelf).map(ignoreError)
						.toPropertyWhenPresent("ignoreError", record::getIgnoreError).map(checkWeight)
						.toPropertyWhenPresent("checkWeight", record::getCheckWeight).map(isEnable)
						.toPropertyWhenPresent("enable", record::getIsEnable).map(des)
						.toPropertyWhenPresent("des", record::getDes));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.546+08:00", comments = "Source Table: uds_job_config")
	default Optional<UdsJobConfig> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.547+08:00", comments = "Source Table: uds_job_config")
	default List<UdsJobConfig> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.548+08:00", comments = "Source Table: uds_job_config")
	default List<UdsJobConfig> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.549+08:00", comments = "Source Table: uds_job_config")
	default Optional<UdsJobConfig> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.55+08:00", comments = "Source Table: uds_job_config")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, udsJobConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.55+08:00", comments = "Source Table: uds_job_config")
	static UpdateDSL<UpdateModel> updateAllColumns(UdsJobConfig record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(platform).equalTo(record::getPlatform).set(systems)
				.equalTo(record::getSystems).set(job).equalTo(record::getJob).set(jobName).equalTo(record::getJobName)
				.set(jobType).equalTo(record::getJobType).set(offsetDay).equalTo(record::getOffsetDay).set(timeWindow)
				.equalTo(record::getTimeWindow).set(virtualEnable).equalTo(record::getVirtualEnable).set(priority)
				.equalTo(record::getPriority).set(callAgainMaxNum).equalTo(record::getCallAgainMaxNum)
				.set(callAgainWaitSec).equalTo(record::getCallAgainWaitSec).set(countBatch)
				.equalTo(record::getCountBatch).set(batchConversion).equalTo(record::getBatchConversion)
				.set(checkFrequency).equalTo(record::getCheckFrequency).set(checkTimeTrigger)
				.equalTo(record::getCheckTimeTrigger).set(checkStreamSelf).equalTo(record::getCheckStreamSelf)
				.set(ignoreError).equalTo(record::getIgnoreError).set(checkWeight).equalTo(record::getCheckWeight)
				.set(isEnable).equalTo(record::getIsEnable).set(des).equalTo(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.551+08:00", comments = "Source Table: uds_job_config")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobConfig record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(platform).equalToWhenPresent(record::getPlatform)
				.set(systems).equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob)
				.set(jobName).equalToWhenPresent(record::getJobName).set(jobType).equalToWhenPresent(record::getJobType)
				.set(offsetDay).equalToWhenPresent(record::getOffsetDay).set(timeWindow)
				.equalToWhenPresent(record::getTimeWindow).set(virtualEnable)
				.equalToWhenPresent(record::getVirtualEnable).set(priority).equalToWhenPresent(record::getPriority)
				.set(callAgainMaxNum).equalToWhenPresent(record::getCallAgainMaxNum).set(callAgainWaitSec)
				.equalToWhenPresent(record::getCallAgainWaitSec).set(countBatch)
				.equalToWhenPresent(record::getCountBatch).set(batchConversion)
				.equalToWhenPresent(record::getBatchConversion).set(checkFrequency)
				.equalToWhenPresent(record::getCheckFrequency).set(checkTimeTrigger)
				.equalToWhenPresent(record::getCheckTimeTrigger).set(checkStreamSelf)
				.equalToWhenPresent(record::getCheckStreamSelf).set(ignoreError)
				.equalToWhenPresent(record::getIgnoreError).set(checkWeight).equalToWhenPresent(record::getCheckWeight)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(des).equalToWhenPresent(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.553+08:00", comments = "Source Table: uds_job_config")
	default int updateByPrimaryKey(UdsJobConfig record) {
		return update(c -> c.set(platform).equalTo(record::getPlatform).set(systems).equalTo(record::getSystems)
				.set(job).equalTo(record::getJob).set(jobName).equalTo(record::getJobName).set(jobType)
				.equalTo(record::getJobType).set(offsetDay).equalTo(record::getOffsetDay).set(timeWindow)
				.equalTo(record::getTimeWindow).set(virtualEnable).equalTo(record::getVirtualEnable).set(priority)
				.equalTo(record::getPriority).set(callAgainMaxNum).equalTo(record::getCallAgainMaxNum)
				.set(callAgainWaitSec).equalTo(record::getCallAgainWaitSec).set(countBatch)
				.equalTo(record::getCountBatch).set(batchConversion).equalTo(record::getBatchConversion)
				.set(checkFrequency).equalTo(record::getCheckFrequency).set(checkTimeTrigger)
				.equalTo(record::getCheckTimeTrigger).set(checkStreamSelf).equalTo(record::getCheckStreamSelf)
				.set(ignoreError).equalTo(record::getIgnoreError).set(checkWeight).equalTo(record::getCheckWeight)
				.set(isEnable).equalTo(record::getIsEnable).set(des).equalTo(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-02-25T10:13:27.554+08:00", comments = "Source Table: uds_job_config")
	default int updateByPrimaryKeySelective(UdsJobConfig record) {
		return update(c -> c.set(platform).equalToWhenPresent(record::getPlatform).set(systems)
				.equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob).set(jobName)
				.equalToWhenPresent(record::getJobName).set(jobType).equalToWhenPresent(record::getJobType)
				.set(offsetDay).equalToWhenPresent(record::getOffsetDay).set(timeWindow)
				.equalToWhenPresent(record::getTimeWindow).set(virtualEnable)
				.equalToWhenPresent(record::getVirtualEnable).set(priority).equalToWhenPresent(record::getPriority)
				.set(callAgainMaxNum).equalToWhenPresent(record::getCallAgainMaxNum).set(callAgainWaitSec)
				.equalToWhenPresent(record::getCallAgainWaitSec).set(countBatch)
				.equalToWhenPresent(record::getCountBatch).set(batchConversion)
				.equalToWhenPresent(record::getBatchConversion).set(checkFrequency)
				.equalToWhenPresent(record::getCheckFrequency).set(checkTimeTrigger)
				.equalToWhenPresent(record::getCheckTimeTrigger).set(checkStreamSelf)
				.equalToWhenPresent(record::getCheckStreamSelf).set(ignoreError)
				.equalToWhenPresent(record::getIgnoreError).set(checkWeight).equalToWhenPresent(record::getCheckWeight)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(des).equalToWhenPresent(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	default Optional<UdsJobConfig> selectBySignal(String signal) {
		return selectOne(MyBatis3Utils.select(SqlBuilder.select(selectList).from(udsJobConfig), c -> {
			return c.join(UdsJobSourceDynamicSqlSupport.udsJobSource)
					.on(platform, equalTo(UdsJobSourceDynamicSqlSupport.platform))
					.and(systems, equalTo(UdsJobSourceDynamicSqlSupport.systems))
					.and(job, equalTo(UdsJobSourceDynamicSqlSupport.job))
					.where(UdsJobSourceDynamicSqlSupport.isEnable, isEqualTo(true))
					.and(UdsJobSourceDynamicSqlSupport.signals, isEqualTo(signal));
		}));
		
	}

	default List<UdsJobConfig> selectByDepAndCondition(String platform_, String system_, String job_, Integer batch) {
		SelectStatementProvider statementProvider = SqlBuilder.select(selectList).from(udsJobConfig)
				.join(UdsJobDependencyDynamicSqlSupport.udsJobDependency)
				.on(platform, equalTo(UdsJobDependencyDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobDependencyDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobDependencyDynamicSqlSupport.job))
				.where(UdsJobDependencyDynamicSqlSupport.depPlatform, isEqualTo(platform_))
				.and(UdsJobDependencyDynamicSqlSupport.depSystem, isEqualTo(system_))
				.and(UdsJobDependencyDynamicSqlSupport.depJob, isEqualTo(job_))
				.and(UdsJobDependencyDynamicSqlSupport.depBatch, isLessThanOrEqualTo(batch))
				.and(isEnable, isEqualTo(true)).and(jobType, isNotEqualTo(UdsJobType.C.name())).build()
				.render(RenderingStrategies.MYBATIS3);
		return selectMany(statementProvider);

	}

	default Optional<UdsJobConfig> selectOne(String platform_, String systems_, String job_){
		return selectOne(c->c.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_))
				.and(isEnable, isEqualTo(true))
				);
				
	}
	
	/**
	 * 从作业配置表中查询可以用于配置作业依赖的可用作业清单
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<String> selectManyJob(SelectStatementProvider selectStatement);
	
	default Set<String> jobSearch(String platform_, String systems_, String job_){
		SelectStatementProvider selectStatement = SqlBuilder.select(job).from(udsJobConfig)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isEqualToWhenPresent(job_))
				.and(isEnable, isEqualTo(true)).build().render(RenderingStrategies.MYBATIS3);
		List<String> newJobList = selectManyJob(selectStatement);
		return newJobList.stream().collect(Collectors.toSet());
	};
	
	default int setVirtual(String platform_,String systems_,String job_,Boolean virtual_) {
		return update(c->c.set(virtualEnable).equalTo(virtual_)
				.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_)));
	}
	
	default int setEnable(String platform_,String systems_,String job_,Boolean enable_) {
		return update(c->c.set(isEnable).equalTo(enable_)
				.where(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_)));
	}
}