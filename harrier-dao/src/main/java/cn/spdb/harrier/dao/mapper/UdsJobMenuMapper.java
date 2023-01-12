package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.batchConversion;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.callAgainMaxNum;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.callAgainWaitSec;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.checkFrequency;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.checkStreamSelf;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.checkTimeTrigger;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.checkWeight;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.countBatch;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.ignoreError;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.isEnable;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.jobName;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.jobType;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.offsetDay;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.priority;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.timeWindow;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.udsJobConfig;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.virtualEnable;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.callAgainNum;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.dispatcherTime;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.endTime;
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
import static org.mybatis.dynamic.sql.SqlBuilder.constant;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.dao.entity.UdsJobMenu;
import cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.UdsJobConfig;
import cn.spdb.harrier.dao.mapper.UdsJobDependencyDynamicSqlSupport.UdsJobDependency;
import cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.UdsJob;

@Mapper
public interface UdsJobMenuMapper {

	/**
	 * uds_job & uds_job_config
	 */
	BasicColumn[] selectList = BasicColumn.columnList(UdsJobDynamicSqlSupport.id, UdsJobDynamicSqlSupport.platform,
			UdsJobDynamicSqlSupport.systems, UdsJobDynamicSqlSupport.job, lastStatus, jobDate, nextJobDate, serverName,
			multiBatch, pendingTime, dispatcherTime, startTime, endTime, numTimes, callAgainNum, streamType,
			UdsJobDynamicSqlSupport.des, jobName, jobType, offsetDay, timeWindow, virtualEnable, priority,
			callAgainMaxNum, callAgainWaitSec, countBatch, batchConversion, checkFrequency, checkTimeTrigger,
			checkStreamSelf, ignoreError, checkWeight, isEnable);

	@Results(id = "UdsJobMenuResultMap", value = {
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
			@Result(column = "dispatcher_time", property = "dispatcherTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "start_time", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "num_times", property = "numTimes", jdbcType = JdbcType.BIGINT),
			@Result(column = "call_again_num", property = "callAgainNum", jdbcType = JdbcType.INTEGER),
			@Result(column = "stream_type", property = "streamType", jdbcType = JdbcType.TINYINT),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job_name", property = "jobName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job_type", property = "jobType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "offset_day", property = "offsetDay", jdbcType = JdbcType.TINYINT),
			@Result(column = "time_window", property = "timeWindow", jdbcType = JdbcType.VARCHAR),
			@Result(column = "virtual_enable", property = "virtualEnable", jdbcType = JdbcType.TINYINT),
			@Result(column = "priority", property = "priority", jdbcType = JdbcType.CHAR),
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
			@Result(column = "pjob", property = "pjob", jdbcType = JdbcType.VARCHAR),
			@Result(column = "depIsEnable", property = "depIsEnable", jdbcType = JdbcType.BIT) })
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobMenu> selectManyPage(SelectStatementProvider selectStatement, Page<UdsJobMenu> page);

	@ResultMap("UdsJobMenuResultMap")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	UdsJobMenu selectOne(SelectStatementProvider selectStatement);

	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@ResultMap("UdsJobMenuResultMap")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobMenu> selectMany(SelectStatementProvider selectStatement);

	default Page<UdsJobMenu> selectAll(Page<UdsJobMenu> page, String platform_, String systems_, String job_,
			String last_status_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(UdsJobDynamicSqlSupport.udsJob)
				.leftJoin(UdsJobConfigDynamicSqlSupport.udsJobConfig)
				.on(platform, equalTo(UdsJobConfigDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobConfigDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobConfigDynamicSqlSupport.job)).where(udsJob.job, isLikeWhenPresent(job_))
				.and(udsJob.platform, isEqualTo(platform_)).and(udsJob.systems, isEqualToWhenPresent(systems_))
				.and(udsJob.lastStatus, isEqualToWhenPresent(last_status_)).build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsJobMenu> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}

	default UdsJobMenu selectJobDetail(String platform_, String systems_, String job_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(UdsJobDynamicSqlSupport.udsJob)
				.leftJoin(UdsJobConfigDynamicSqlSupport.udsJobConfig)
				.on(platform, equalTo(UdsJobConfigDynamicSqlSupport.platform))
				.and(systems, equalTo(UdsJobConfigDynamicSqlSupport.systems))
				.and(job, equalTo(UdsJobConfigDynamicSqlSupport.job)).where(udsJob.job, isEqualTo(job_))
				.and(udsJob.platform, isEqualTo(platform_)).and(udsJob.systems, isEqualTo(systems_)).build()
				.render(RenderingStrategies.MYBATIS3);
		return selectOne(selectStatement);

	}

	default int updateJobDetail(UdsJobMenu udsJobMenu) {
		LocalDateTime uptime = ExecutionStatus.PENDING.name().equals(udsJobMenu.getLastStatus())
				? LocalDateTime.of(1970, 1, 1, 0, 0)
				: null;
		UpdateStatementProvider updateStatement1 = SqlBuilder.update(udsJob).set(jobDate)
				.equalToWhenPresent(udsJobMenu.getJobDate()).set(lastStatus)
				.equalToWhenPresent(udsJobMenu.getLastStatus()).set(udsJob.updateTime).equalToWhenPresent(uptime)
				.set(multiBatch).equalToWhenPresent(udsJobMenu.getMultiBatch())
				.where(udsJob.job, isEqualTo(udsJobMenu.getJob()))
				.and(udsJob.platform, isEqualTo(udsJobMenu.getPlatform()))
				.and(udsJob.systems, isEqualTo(udsJobMenu.getSystems())).build().render(RenderingStrategies.MYBATIS3);
		UpdateStatementProvider updateStatement2 = SqlBuilder.update(udsJobConfig).set(priority)
				.equalToWhenPresent(udsJobMenu.getPriority()).set(callAgainMaxNum)
				.equalToWhenPresent(udsJobMenu.getCallAgainMaxNum()).set(checkFrequency)
				.equalToWhenPresent(udsJobMenu.getCheckFrequency()).set(checkTimeTrigger)
				.equalToWhenPresent(udsJobMenu.getCheckTimeTrigger()).set(timeWindow)
				.equalToWhenPresent(udsJobMenu.getTimeWindow()).set(ignoreError)
				.equalToWhenPresent(udsJobMenu.getIgnoreError()).where(udsJob.job, isEqualTo(udsJobMenu.getJob()))
				.and(udsJob.platform, isEqualTo(udsJobMenu.getPlatform()))
				.and(udsJob.systems, isEqualTo(udsJobMenu.getSystems())).build().render(RenderingStrategies.MYBATIS3);
		return update(updateStatement1) & update(updateStatement2);
	}

	default Page<UdsJobMenu> listUpJobs(Page<UdsJobMenu> page, String job_) {

		UdsJob t1 = UdsJobDynamicSqlSupport.udsJob;
		UdsJobDependency t2 = UdsJobDependencyDynamicSqlSupport.udsJobDependency;
		UdsJobConfig t3 = UdsJobConfigDynamicSqlSupport.udsJobConfig;

		SelectStatementProvider selectStatement = SqlBuilder.select(constant(
				"t1.platform,t1.systems,t2.job pjob,t1.job,t1.server_name,t3.job_type,t1.last_status,t2.dep_batch depBatch,t1.start_time,t1.end_time,t1.job_date,t2.is_enable depIsEnable,t2.des,t2.id pid"))
				.from(t1, "t1").leftJoin(t2, "t2").on(t1.platform, equalTo(t2.depPlatform))
				.and(t1.systems, equalTo(t2.depSystem)).and(t1.job, equalTo(t2.depJob)).leftJoin(t3, "t3")
				.on(t1.platform, equalTo(t3.platform)).and(t1.systems, equalTo(t3.systems)).and(t1.job, equalTo(t3.job))
				.where(t2.job, isEqualTo(job_)).build().render(RenderingStrategies.MYBATIS3);

		List<UdsJobMenu> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}

	default List<UdsJobMenu> listAllUpJobs(String[] job_) {
		UdsJob t1 = UdsJobDynamicSqlSupport.udsJob;
		UdsJobDependency t2 = UdsJobDependencyDynamicSqlSupport.udsJobDependency;
		UdsJobConfig t3 = UdsJobConfigDynamicSqlSupport.udsJobConfig;

		SelectStatementProvider selectStatement = SqlBuilder.select(constant(
				"t1.platform,t1.systems,t2.job pjob,t1.job,t1.server_name,t3.job_type,t1.last_status,t1.pending_time,t1.dispatcher_time,t1.start_time,t1.end_time,t1.job_date,t1.multi_batch,t1.num_times,t3.priority,t3.time_window,t3.is_enable,t2.is_enable depIsEnable,t3.check_time_trigger"))
				.from(t1, "t1").leftJoin(t2, "t2").on(t1.platform, equalTo(t2.depPlatform))
				.and(t1.systems, equalTo(t2.depSystem)).and(t1.job, equalTo(t2.depJob)).leftJoin(t3, "t3")
				.on(t1.platform, equalTo(t3.platform)).and(t1.systems, equalTo(t3.systems)).and(t1.job, equalTo(t3.job))
				.where(t2.job, isEqualTo(job_[0])).build().render(RenderingStrategies.MYBATIS3);
		System.out.print(1);
		List<UdsJobMenu> records = selectMany(selectStatement);

		return records;
	}

	default List<UdsJobMenu> listAllDownjobs(String[] job_) {

		UdsJob t1 = UdsJobDynamicSqlSupport.udsJob;
		UdsJobDependency t2 = UdsJobDependencyDynamicSqlSupport.udsJobDependency;
		UdsJobConfig t3 = UdsJobConfigDynamicSqlSupport.udsJobConfig;

		SelectStatementProvider selectStatement = SqlBuilder.select(constant(
				"t1.platform,t1.systems,t2.dep_job pjob,t1.job,t1.server_name,t3.job_type,t1.last_status,t1.pending_time,t1.dispatcher_time,t1.start_time,t1.end_time,t1.job_date,t1.multi_batch,t1.num_times,t3.priority,t3.time_window,t3.is_enable,t2.is_enable depIsEnable,t3.check_time_trigger"))
				.from(t1, "t1").leftJoin(t2, "t2").on(t1.platform, equalTo(t2.platform))
				.and(t1.systems, equalTo(t2.systems)).and(t1.job, equalTo(t2.job)).leftJoin(t3, "t3")
				.on(t1.platform, equalTo(t3.platform)).and(t1.systems, equalTo(t3.systems)).and(t1.job, equalTo(t3.job))
				.where(t2.depJob, isEqualTo(job_[0])).build().render(RenderingStrategies.MYBATIS3);
		System.out.print(1);
		List<UdsJobMenu> records = selectMany(selectStatement);
		System.out.print(1);
		return records;
	}
}
