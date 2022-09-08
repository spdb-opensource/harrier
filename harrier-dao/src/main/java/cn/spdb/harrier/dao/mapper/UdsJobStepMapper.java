package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobStepDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;

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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.dao.entity.UdsJobStep;

@Mapper
public interface UdsJobStepMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.174+08:00", comments = "Source Table: uds_job_step")
	BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, stepType, stepNum, operCmd,
			scriptPath, parameter, updateTime, environments, workDir, isEnable, des);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.155+08:00", comments = "Source Table: uds_job_step")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.157+08:00", comments = "Source Table: uds_job_step")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.158+08:00", comments = "Source Table: uds_job_step")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<UdsJobStep> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.159+08:00", comments = "Source Table: uds_job_step")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<UdsJobStep> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.16+08:00", comments = "Source Table: uds_job_step")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UdsJobStepResult")
	Optional<UdsJobStep> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.161+08:00", comments = "Source Table: uds_job_step")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UdsJobStepResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job", property = "job", jdbcType = JdbcType.VARCHAR),
			@Result(column = "step_type", property = "stepType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "step_num", property = "stepNum", jdbcType = JdbcType.TINYINT),
			@Result(column = "oper_cmd", property = "operCmd", jdbcType = JdbcType.VARCHAR),
			@Result(column = "script_path", property = "scriptPath", jdbcType = JdbcType.VARCHAR),
			@Result(column = "parameter", property = "parameter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "environments", property = "environments", jdbcType = JdbcType.VARCHAR),
			@Result(column = "work_dir", property = "workDir", jdbcType = JdbcType.VARCHAR),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR) })
	List<UdsJobStep> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.165+08:00", comments = "Source Table: uds_job_step")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.166+08:00", comments = "Source Table: uds_job_step")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.167+08:00", comments = "Source Table: uds_job_step")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.167+08:00", comments = "Source Table: uds_job_step")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.168+08:00", comments = "Source Table: uds_job_step")
	default int insert(UdsJobStep record) {
		return MyBatis3Utils.insert(this::insert, record, udsJobStep,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(updateTime)
						.toProperty("updateTime").map(systems).toProperty("systems").map(job).toProperty("job")
						.map(stepType).toProperty("stepType").map(stepNum).toProperty("stepNum").map(operCmd)
						.toProperty("operCmd").map(scriptPath).toProperty("scriptPath").map(parameter)
						.toProperty("parameter").map(environments).toProperty("environments").map(workDir)
						.toProperty("workDir").map(isEnable).toProperty("isEnable").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.171+08:00", comments = "Source Table: uds_job_step")
	default int insertMultiple(Collection<UdsJobStep> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobStep,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(job).toProperty("job").map(updateTime).toProperty("updateTime").map(stepType)
						.toProperty("stepType").map(stepNum).toProperty("stepNum").map(operCmd).toProperty("operCmd")
						.map(scriptPath).toProperty("scriptPath").map(parameter).toProperty("parameter")
						.map(environments).toProperty("environments").map(workDir).toProperty("workDir").map(isEnable)
						.toProperty("isEnable").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.172+08:00", comments = "Source Table: uds_job_step")
	default int insertSelective(UdsJobStep record) {
		return MyBatis3Utils.insert(this::insert, record, udsJobStep,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(platform)
						.toPropertyWhenPresent("platform", record::getPlatform).map(systems)
						.toPropertyWhenPresent("systems", record::getSystems).map(job)
						.toPropertyWhenPresent("updateTime", record::getUpdateTime).map(updateTime)
						.toPropertyWhenPresent("job", record::getJob).map(stepType)
						.toPropertyWhenPresent("stepType", record::getStepType).map(stepNum)
						.toPropertyWhenPresent("stepNum", record::getStepNum).map(operCmd)
						.toPropertyWhenPresent("operCmd", record::getOperCmd).map(scriptPath)
						.toPropertyWhenPresent("scriptPath", record::getScriptPath).map(parameter)
						.toPropertyWhenPresent("parameter", record::getParameter).map(environments)
						.toPropertyWhenPresent("environments", record::getEnvironments).map(workDir)
						.toPropertyWhenPresent("workDir", record::getWorkDir).map(isEnable)
						.toPropertyWhenPresent("isEnable", record::getIsEnable).map(des)
						.toPropertyWhenPresent("des", record::getDes));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.177+08:00", comments = "Source Table: uds_job_step")
	default Optional<UdsJobStep> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.177+08:00", comments = "Source Table: uds_job_step")
	default List<UdsJobStep> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.178+08:00", comments = "Source Table: uds_job_step")
	default List<UdsJobStep> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.179+08:00", comments = "Source Table: uds_job_step")
	default Optional<UdsJobStep> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	default List<UdsJobStep> selectJobSteps(String job_) {
		return select(c -> c.where(job, isEqualTo(job_)).and(isEnable, isEqualTo(true)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.18+08:00", comments = "Source Table: uds_job_step")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, udsJobStep, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.181+08:00", comments = "Source Table: uds_job_step")
	static UpdateDSL<UpdateModel> updateAllColumns(UdsJobStep record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(platform).equalTo(record::getPlatform).set(systems)
				.equalTo(record::getSystems).set(job).equalTo(record::getJob).set(stepType).equalTo(record::getStepType)
				.set(stepNum).equalTo(record::getStepNum).set(operCmd).equalTo(record::getOperCmd).set(scriptPath)
				.equalTo(record::getScriptPath).set(parameter).equalTo(record::getParameter).set(environments)
				.equalTo(record::getEnvironments).set(updateTime).equalTo(record::getUpdateTime).set(workDir)
				.equalTo(record::getWorkDir).set(isEnable).equalTo(record::getIsEnable).set(des)
				.equalTo(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.182+08:00", comments = "Source Table: uds_job_step")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobStep record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(platform).equalToWhenPresent(record::getPlatform)
				.set(systems).equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob)
				.set(stepType).equalToWhenPresent(record::getStepType).set(stepNum)
				.equalToWhenPresent(record::getStepNum).set(operCmd).equalToWhenPresent(record::getOperCmd)
				.set(scriptPath).equalToWhenPresent(record::getScriptPath).set(parameter)
				.equalToWhenPresent(record::getParameter).set(environments).equalToWhenPresent(record::getEnvironments)
				.set(updateTime).equalToWhenPresent(record::getUpdateTime).set(workDir)
				.equalToWhenPresent(record::getWorkDir).set(isEnable).equalToWhenPresent(record::getIsEnable).set(des)
				.equalToWhenPresent(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.184+08:00", comments = "Source Table: uds_job_step")
	default int updateByPrimaryKey(UdsJobStep record) {
		return update(c -> c.set(platform).equalTo(record::getPlatform).set(systems).equalTo(record::getSystems)
				.set(job).equalTo(record::getJob).set(stepType).equalTo(record::getStepType).set(stepNum)
				.equalTo(record::getStepNum).set(operCmd).equalTo(record::getOperCmd).set(scriptPath)
				.equalTo(record::getScriptPath).set(parameter).equalTo(record::getParameter).set(environments)
				.equalTo(record::getEnvironments).set(updateTime).equalTo(record::getUpdateTime).set(workDir)
				.equalTo(record::getWorkDir).set(isEnable).equalTo(record::getIsEnable).set(des).equalTo(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.185+08:00", comments = "Source Table: uds_job_step")
	default int updateByPrimaryKeySelective(UdsJobStep record) {
		return update(c -> c.set(platform).equalToWhenPresent(record::getPlatform).set(systems)
				.equalToWhenPresent(record::getSystems).set(job).equalToWhenPresent(record::getJob).set(stepType)
				.equalToWhenPresent(record::getStepType).set(stepNum).equalToWhenPresent(record::getStepNum)
				.set(operCmd).equalToWhenPresent(record::getOperCmd).set(scriptPath)
				.equalToWhenPresent(record::getScriptPath).set(parameter).equalToWhenPresent(record::getParameter)
				.set(environments).equalToWhenPresent(record::getEnvironments).set(updateTime)
				.equalToWhenPresent(record::getUpdateTime).set(workDir).equalToWhenPresent(record::getWorkDir)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(des).equalToWhenPresent(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	default List<UdsJobStep> selectJobStepList(String platform_, String systems_, String job_) {
		return select(c -> c.where(platform, isEqualTo(platform_)).and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_)).and(isEnable, isEqualTo(true)));
	}

	@ResultMap("UdsJobStepResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobStep> selectManyPage(SelectStatementProvider selectStatement, Page<UdsJobStep> page);

	default Page<UdsJobStep> selectJobStepPage(Page<UdsJobStep> page, String platform_, String system_, String job_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(UdsJobStepDynamicSqlSupport.udsJobStep)
				.where(udsJobStep.platform, isEqualToWhenPresent(platform_))
				.and(udsJobStep.systems, isEqualToWhenPresent(system_)).and(udsJobStep.job, isEqualToWhenPresent(job_))
				.build().render(RenderingStrategies.MYBATIS3);
		List<UdsJobStep> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}

	default Optional<UdsJobStep> selectJobStep(String platform_, String system_, String job_, Byte index){
		return selectOne(c->
			c.where(platform,isEqualTo(platform_))
				.and(systems,isEqualTo(system_))
				.and(job,isEqualTo(job_))
				.and(stepNum,isEqualTo(index)));
				
	}
}