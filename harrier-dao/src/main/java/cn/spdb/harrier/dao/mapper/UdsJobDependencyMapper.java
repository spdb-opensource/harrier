package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobDependencyDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobDependency;

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
public interface UdsJobDependencyMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, depPlatform, depSystem, depJob, depBatch, isEnable, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobDependency> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobDependency> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobDependencyResult")
    Optional<UdsJobDependency> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.202+08:00", comments="Source Table: uds_job_dependency")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobDependencyResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_platform", property="depPlatform", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_system", property="depSystem", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_job", property="depJob", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_batch", property="depBatch", jdbcType=JdbcType.INTEGER),
        @Result(column="is_enable", property="isEnable", jdbcType=JdbcType.BIT),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobDependency> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default int insert(UdsJobDependency record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobDependency, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(depPlatform).toProperty("depPlatform")
            .map(depSystem).toProperty("depSystem")
            .map(depJob).toProperty("depJob")
            .map(depBatch).toProperty("depBatch")
            .map(isEnable).toProperty("isEnable")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default int insertMultiple(Collection<UdsJobDependency> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobDependency, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(depPlatform).toProperty("depPlatform")
            .map(depSystem).toProperty("depSystem")
            .map(depJob).toProperty("depJob")
            .map(depBatch).toProperty("depBatch")
            .map(isEnable).toProperty("isEnable")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.203+08:00", comments="Source Table: uds_job_dependency")
    default int insertSelective(UdsJobDependency record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobDependency, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(depPlatform).toPropertyWhenPresent("depPlatform", record::getDepPlatform)
            .map(depSystem).toPropertyWhenPresent("depSystem", record::getDepSystem)
            .map(depJob).toPropertyWhenPresent("depJob", record::getDepJob)
            .map(depBatch).toPropertyWhenPresent("depBatch", record::getDepBatch)
            .map(isEnable).toPropertyWhenPresent("isEnable", record::getIsEnable)
            .map(des).toPropertyWhenPresent("des", record::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default Optional<UdsJobDependency> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default List<UdsJobDependency> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default List<UdsJobDependency> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default Optional<UdsJobDependency> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobDependency, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobDependency record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(depPlatform).equalTo(record::getDepPlatform)
                .set(depSystem).equalTo(record::getDepSystem)
                .set(depJob).equalTo(record::getDepJob)
                .set(depBatch).equalTo(record::getDepBatch)
                .set(isEnable).equalTo(record::getIsEnable)
                .set(des).equalTo(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobDependency record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(depPlatform).equalToWhenPresent(record::getDepPlatform)
                .set(depSystem).equalToWhenPresent(record::getDepSystem)
                .set(depJob).equalToWhenPresent(record::getDepJob)
                .set(depBatch).equalToWhenPresent(record::getDepBatch)
                .set(isEnable).equalToWhenPresent(record::getIsEnable)
                .set(des).equalToWhenPresent(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default int updateByPrimaryKey(UdsJobDependency record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(depPlatform).equalTo(record::getDepPlatform)
            .set(depSystem).equalTo(record::getDepSystem)
            .set(depJob).equalTo(record::getDepJob)
            .set(depBatch).equalTo(record::getDepBatch)
            .set(isEnable).equalTo(record::getIsEnable)
            .set(des).equalTo(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T10:08:54.204+08:00", comments="Source Table: uds_job_dependency")
    default int updateByPrimaryKeySelective(UdsJobDependency record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(depPlatform).equalToWhenPresent(record::getDepPlatform)
            .set(depSystem).equalToWhenPresent(record::getDepSystem)
            .set(depJob).equalToWhenPresent(record::getDepJob)
            .set(depBatch).equalToWhenPresent(record::getDepBatch)
            .set(isEnable).equalToWhenPresent(record::getIsEnable)
            .set(des).equalToWhenPresent(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

	default Optional<UdsJobDependency> getDependencyBatch(String platform1, String systems1, String job1, String platform2, String systems2, String job2) {
		return selectOne(c->c.where()
				.and(platform, isEqualTo(platform1))
				.and(systems, isEqualTo(systems1))
				.and(job, isEqualTo(job1))
				.and(depPlatform, isEqualTo(platform2))
				.and(depSystem, isEqualTo(systems2))
				.and(depJob, isEqualTo(job2))
				.and(isEnable, isEqualTo(true))
				);
				
	}
	
    default List<UdsJobDependency> selectJobDeps(String platform_, String systems_, String job_) {
        return select(c -> c.where(job, isEqualTo(job_)).and(platform, isEqualTo(platform_)).and(systems, isEqualTo(systems_)).and(isEnable, isEqualTo(true)));
    }
    
	default int setDepEnable(String pjob,String job_,Boolean enable_) {
		return update(c->c.set(isEnable).equalTo(enable_)
				.where(depJob, isEqualTo(pjob))
				.and(job, isEqualTo(job_)));
	}
}