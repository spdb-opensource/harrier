package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobWeightDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsJobWeight;
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

@Mapper
public interface UdsJobWeightMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, limitType, confValue, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source Table: uds_job_weight")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source Table: uds_job_weight")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.39+08:00", comments="Source Table: uds_job_weight")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobWeight> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobWeight> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobWeightResult")
    Optional<UdsJobWeight> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobWeightResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="limit_type", property="limitType", jdbcType=JdbcType.INTEGER),
        @Result(column="conf_value", property="confValue", jdbcType=JdbcType.INTEGER),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobWeight> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    default int insert(UdsJobWeight record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobWeight, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(limitType).toProperty("limitType")
            .map(confValue).toProperty("confValue")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.391+08:00", comments="Source Table: uds_job_weight")
    default int insertMultiple(Collection<UdsJobWeight> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobWeight, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(limitType).toProperty("limitType")
            .map(confValue).toProperty("confValue")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default int insertSelective(UdsJobWeight record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobWeight, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(limitType).toPropertyWhenPresent("limitType", record::getLimitType)
            .map(confValue).toPropertyWhenPresent("confValue", record::getConfValue)
            .map(des).toPropertyWhenPresent("des", record::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default Optional<UdsJobWeight> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default List<UdsJobWeight> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default List<UdsJobWeight> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default Optional<UdsJobWeight> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }
    
    default Optional<UdsJobWeight> selectByJobName(String job_) {
        return selectOne(c ->
            c.where(job, isEqualTo(job_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobWeight, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.392+08:00", comments="Source Table: uds_job_weight")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobWeight record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(limitType).equalTo(record::getLimitType)
                .set(confValue).equalTo(record::getConfValue)
                .set(des).equalTo(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.393+08:00", comments="Source Table: uds_job_weight")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobWeight record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(limitType).equalToWhenPresent(record::getLimitType)
                .set(confValue).equalToWhenPresent(record::getConfValue)
                .set(des).equalToWhenPresent(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.393+08:00", comments="Source Table: uds_job_weight")
    default int updateByPrimaryKey(UdsJobWeight record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(limitType).equalTo(record::getLimitType)
            .set(confValue).equalTo(record::getConfValue)
            .set(des).equalTo(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.393+08:00", comments="Source Table: uds_job_weight")
    default int updateByPrimaryKeySelective(UdsJobWeight record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(limitType).equalToWhenPresent(record::getLimitType)
            .set(confValue).equalToWhenPresent(record::getConfValue)
            .set(des).equalToWhenPresent(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }
    
    default List<UdsJobWeight> select(String platform_,String system,String job_){
    	return select(c->
    	c.where(platform, isEqualTo(platform_))
    	.and(systems, isEqualTo(system))
    	.and(job, isEqualTo(job_)));
    }
    
	@ResultMap("UdsJobWeightResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobWeight> selectManyPage(SelectStatementProvider selectStatement, Page<UdsJobWeight> page);
	default Page<UdsJobWeight> selectAll(Page<UdsJobWeight> page,String platform_,String systems_,String job_, Integer limitType_){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(udsJobWeight)
				.where(udsJobWeight.job,isLikeWhenPresent(job_))
				.and(udsJobWeight.platform, isEqualToWhenPresent(platform_))
				.and(udsJobWeight.systems, isEqualToWhenPresent(systems_))
				.and(udsJobWeight.limitType, isEqualToWhenPresent(limitType_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsJobWeight> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
	
	default int updateConfValue(String platform_,String systems_,String job_,Integer limit_type,Integer conf_value) {
		UpdateStatementProvider updateStatement = SqlBuilder.update(udsJobWeight)
				.set(udsJobWeight.confValue).equalTo(conf_value)
				.where(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(job, isEqualToWhenPresent(job_))
				.and(limitType, isEqualToWhenPresent(limit_type))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		return update(updateStatement);
	}
	
	default int delete(Long[] ids) {
		return delete(c -> c.where(id, isIn(ids)));
	}
}