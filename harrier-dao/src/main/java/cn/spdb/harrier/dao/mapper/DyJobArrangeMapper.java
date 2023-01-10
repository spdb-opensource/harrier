package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.DyJobArrangeDynamicSqlSupport.*;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.job;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.platform;
import static cn.spdb.harrier.dao.mapper.UdsJobDynamicSqlSupport.systems;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.DyJobArrange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.apache.commons.lang3.StringUtils;
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

@Mapper
public interface DyJobArrangeMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, taskStatus, processStatus, syncStatus, startDate, endDate, version, des, deployYaml);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<DyJobArrange> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<DyJobArrange> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("DyJobArrangeResult")
    Optional<DyJobArrange> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="DyJobArrangeResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_status", property="taskStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="process_status", property="processStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="sync_status", property="syncStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR),
        @Result(column="deploy_yaml", property="deployYaml", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<DyJobArrange> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int insert(DyJobArrange record) {
        return MyBatis3Utils.insert(this::insert, record, dyJobArrange, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(taskStatus).toProperty("taskStatus")
            .map(processStatus).toProperty("processStatus")
            .map(syncStatus).toProperty("syncStatus")
            .map(startDate).toProperty("startDate")
            .map(endDate).toProperty("endDate")
            .map(version).toProperty("version")
            .map(des).toProperty("des")
            .map(deployYaml).toProperty("deployYaml")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int insertMultiple(Collection<DyJobArrange> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, dyJobArrange, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(taskStatus).toProperty("taskStatus")
            .map(processStatus).toProperty("processStatus")
            .map(syncStatus).toProperty("syncStatus")
            .map(startDate).toProperty("startDate")
            .map(endDate).toProperty("endDate")
            .map(version).toProperty("version")
            .map(des).toProperty("des")
            .map(deployYaml).toProperty("deployYaml")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int insertSelective(DyJobArrange record) {
        return MyBatis3Utils.insert(this::insert, record, dyJobArrange, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(taskStatus).toPropertyWhenPresent("taskStatus", record::getTaskStatus)
            .map(processStatus).toPropertyWhenPresent("processStatus", record::getProcessStatus)
            .map(syncStatus).toPropertyWhenPresent("syncStatus", record::getSyncStatus)
            .map(startDate).toPropertyWhenPresent("startDate", record::getStartDate)
            .map(endDate).toPropertyWhenPresent("endDate", record::getEndDate)
            .map(version).toPropertyWhenPresent("version", record::getVersion)
            .map(des).toPropertyWhenPresent("des", record::getDes)
            .map(deployYaml).toPropertyWhenPresent("deployYaml", record::getDeployYaml)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default Optional<DyJobArrange> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default List<DyJobArrange> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default List<DyJobArrange> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default Optional<DyJobArrange> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }
    
	default Optional<DyJobArrange> selectOne(String platform_, String systems_, String job_){
		return selectOne(c->c.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job,  isEqualTo(job_)).orderBy(version.descending()).limit(1));
	}

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, dyJobArrange, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    static UpdateDSL<UpdateModel> updateAllColumns(DyJobArrange record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(taskStatus).equalTo(record::getTaskStatus)
                .set(processStatus).equalTo(record::getProcessStatus)
                .set(syncStatus).equalTo(record::getSyncStatus)
                .set(startDate).equalTo(record::getStartDate)
                .set(endDate).equalTo(record::getEndDate)
                .set(version).equalTo(record::getVersion)
                .set(des).equalTo(record::getDes)
                .set(deployYaml).equalTo(record::getDeployYaml);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(DyJobArrange record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(taskStatus).equalToWhenPresent(record::getTaskStatus)
                .set(processStatus).equalToWhenPresent(record::getProcessStatus)
                .set(syncStatus).equalToWhenPresent(record::getSyncStatus)
                .set(startDate).equalToWhenPresent(record::getStartDate)
                .set(endDate).equalToWhenPresent(record::getEndDate)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(des).equalToWhenPresent(record::getDes)
                .set(deployYaml).equalToWhenPresent(record::getDeployYaml);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int updateByPrimaryKey(DyJobArrange record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(taskStatus).equalTo(record::getTaskStatus)
            .set(processStatus).equalTo(record::getProcessStatus)
            .set(syncStatus).equalTo(record::getSyncStatus)
            .set(startDate).equalTo(record::getStartDate)
            .set(endDate).equalTo(record::getEndDate)
            .set(version).equalTo(record::getVersion)
            .set(des).equalTo(record::getDes)
            .set(deployYaml).equalTo(record::getDeployYaml)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int update(DyJobArrange record) {
        return update(c ->
                c.set(platform).equalTo(record::getPlatform)
                        .set(systems).equalTo(record::getSystems)
                        .set(job).equalTo(record::getJob)
                        .set(taskStatus).equalTo(record::getTaskStatus)
                        .set(processStatus).equalTo(record::getProcessStatus)
                        .set(syncStatus).equalTo(record::getSyncStatus)
                        .set(startDate).equalTo(record::getStartDate)
                        .set(endDate).equalTo(record::getEndDate)
                        .set(version).equalTo(record::getVersion)
                        .set(des).equalTo(record::getDes)
                        .set(deployYaml).equalTo(record::getDeployYaml)
                        .where(platform, isEqualTo(record::getPlatform))
                        .and(systems, isEqualTo(record::getSystems))
                        .and(job,isEqualTo(record::getJob))
                        .and(version,isEqualTo(record::getVersion))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-20T14:40:36.492+08:00", comments="Source Table: dy_job_arrange")
    default int updateByPrimaryKeySelective(DyJobArrange record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(taskStatus).equalToWhenPresent(record::getTaskStatus)
            .set(processStatus).equalToWhenPresent(record::getProcessStatus)
            .set(syncStatus).equalToWhenPresent(record::getSyncStatus)
            .set(startDate).equalToWhenPresent(record::getStartDate)
            .set(endDate).equalToWhenPresent(record::getEndDate)
            .set(version).equalToWhenPresent(record::getVersion)
            .set(des).equalToWhenPresent(record::getDes)
            .set(deployYaml).equalToWhenPresent(record::getDeployYaml)
            .where(id, isEqualTo(record::getId))
        );
    }

	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<DyJobArrange> selectManyPage(SelectStatementProvider selectStatement, Page<DyJobArrange> page);
	
	default Page<DyJobArrange> selectManyInfo(Page<DyJobArrange> page, String platform_, String systems_, String job_, Integer version_){
		String jobLikeStr = "";
		LocalDateTime endDate_ = null;
		if(StringUtils.isBlank(job_)) {
			jobLikeStr = null;
		}else {
			jobLikeStr = "%" + job_ + "%";
		}
		if(version_ == null) {
			endDate_ = LocalDateTime.of(LocalDate.of(2099, 12, 31), LocalTime.of(23, 59, 59));
		}
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(dyJobArrange)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isLikeWhenPresent(jobLikeStr))
				.and(version, isEqualToWhenPresent(version_))
				.and(endDate, isGreaterThanOrEqualToWhenPresent(endDate_))
				.orderBy(startDate.descending()).build().render(RenderingStrategies.MYBATIS3);
		List<DyJobArrange> selectManyPage = selectManyPage(selectStatement, page);
		return page.setRecords(selectManyPage);
	};
	
	default Page<DyJobArrange> selectAllJobVersion(Page<DyJobArrange> page, String platform_, String systems_, String job_){
		String jobLikeStr = "";
		if(StringUtils.isBlank(job_)) {
			jobLikeStr = null;
		}else {
			jobLikeStr = "%" + job_ + "%";
		}
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(dyJobArrange)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isLikeWhenPresent(jobLikeStr))
				.orderBy(startDate.descending()).build().render(RenderingStrategies.MYBATIS3);
		List<DyJobArrange> arrangeList = selectManyPage(selectStatement, page);
		return page.setRecords(arrangeList);
	};
	
    default Optional<DyJobArrange> selectLastJobArrange(String platform_, String systems_, String job_) {
        return selectOne(c ->
            c.where(platform, isEqualToWhenPresent(platform_))
    		.and(systems, isEqualToWhenPresent(systems_))
    		.and(job, isEqualTo(job_))
    		.orderBy(version.descending())
    		.limit(1)
        );
    }

	default Optional<DyJobArrange> selectJobArrabgeByVersion(String platform_, String systems_, String job_, Integer version_){
		return selectOne(c ->
        c.where(platform, isEqualToWhenPresent(platform_))
		.and(systems, isEqualToWhenPresent(systems_))
		.and(job, isEqualTo(job_))
		.and(version, isEqualTo(version_))
    );
	}
	
	/**
	 * 查询作业编排表中新增的且尚未投产上线的作业
	 * @param selectStatement
	 * @return
	 */
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<String> selectManyJob(SelectStatementProvider selectStatement);
	
	default Set<String> newJobSearch(String platform_, String systems_, String job_){
		SelectStatementProvider selectStatement = SqlBuilder.select(job).from(dyJobArrange)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isEqualToWhenPresent(job_))
				.and(taskStatus, isEqualTo(1))
				.and(processStatus, isNotEqualTo(3)).build().render(RenderingStrategies.MYBATIS3);
		List<String> newJobList = selectManyJob(selectStatement);
		return newJobList.stream().collect(Collectors.toSet());
	}

	/**
	 * 根据作业的版本号删除作业编排信息
	 * @param job
	 * @param version
	 * @return
	 */
	default Integer deleteJobByVersion(String job_, Integer version_) {
		DeleteStatementProvider deleteStatement = SqlBuilder.deleteFrom(dyJobArrange).where(job, isEqualTo(job_))
				.and(version, isEqualTo(version_)).build().render(RenderingStrategies.MYBATIS3);
		return delete(deleteStatement);
	}

	/**
	 * 获取作业的所有版本号信息
	 * @param job
	 * @return
	 */
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<Integer> selecJobVersionCode(SelectStatementProvider selectStatement);
	
	default List<Integer> findJobVersionCode(String platform_, String systems_, String job_){
		SelectStatementProvider selectStatement = SqlBuilder.select(version).from(dyJobArrange)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isEqualTo(job_))
				.orderBy(version.descending()).build().render(RenderingStrategies.MYBATIS3);
		List<Integer> jobAllVersionCode = selecJobVersionCode(selectStatement);
		return jobAllVersionCode;
	};
}