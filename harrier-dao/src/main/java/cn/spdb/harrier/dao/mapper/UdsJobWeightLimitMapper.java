package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.des;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.id;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.limitType;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.limitValue;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.serverIds;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.timeWinds;
import static cn.spdb.harrier.dao.mapper.UdsJobWeightLimitDynamicSqlSupport.udsJobWeightLimit;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;

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

import cn.spdb.harrier.dao.entity.UdsJobWeightLimit;

@Mapper
public interface UdsJobWeightLimitMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.372+08:00", comments="Source Table: uds_job_weight_limit")
    BasicColumn[] selectList = BasicColumn.columnList(id, limitType, limitValue, serverIds, timeWinds, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.356+08:00", comments="Source Table: uds_job_weight_limit")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.358+08:00", comments="Source Table: uds_job_weight_limit")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.359+08:00", comments="Source Table: uds_job_weight_limit")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UdsJobWeightLimit> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.36+08:00", comments="Source Table: uds_job_weight_limit")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<UdsJobWeightLimit> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.361+08:00", comments="Source Table: uds_job_weight_limit")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobWeightLimitResult")
    Optional<UdsJobWeightLimit> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.362+08:00", comments="Source Table: uds_job_weight_limit")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobWeightLimitResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="limit_type", property="limitType", jdbcType=JdbcType.INTEGER),
        @Result(column="limit_value", property="limitValue", jdbcType=JdbcType.INTEGER),
        @Result(column="server_ids", property="serverIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_winds", property="timeWinds", jdbcType=JdbcType.VARCHAR),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobWeightLimit> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.364+08:00", comments="Source Table: uds_job_weight_limit")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.365+08:00", comments="Source Table: uds_job_weight_limit")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.365+08:00", comments="Source Table: uds_job_weight_limit")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.366+08:00", comments="Source Table: uds_job_weight_limit")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.367+08:00", comments="Source Table: uds_job_weight_limit")
    default int insert(UdsJobWeightLimit record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobWeightLimit, c ->
            c.map(id).toProperty("id")
            .map(limitType).toProperty("limitType")
            .map(limitValue).toProperty("limitValue")
            .map(serverIds).toProperty("serverIds")
            .map(timeWinds).toProperty("timeWinds")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.369+08:00", comments="Source Table: uds_job_weight_limit")
    default int insertMultiple(Collection<UdsJobWeightLimit> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobWeightLimit, c ->
            c.map(id).toProperty("id")
            .map(limitType).toProperty("limitType")
            .map(limitValue).toProperty("limitValue")
            .map(serverIds).toProperty("serverIds")
            .map(timeWinds).toProperty("timeWinds")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.37+08:00", comments="Source Table: uds_job_weight_limit")
    default int insertSelective(UdsJobWeightLimit record) {
        return MyBatis3Utils.insert(this::insert, record, udsJobWeightLimit, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(limitType).toPropertyWhenPresent("limitType", record::getLimitType)
            .map(limitValue).toPropertyWhenPresent("limitValue", record::getLimitValue)
            .map(serverIds).toPropertyWhenPresent("serverIds", record::getServerIds)
            .map(timeWinds).toPropertyWhenPresent("timeWinds", record::getTimeWinds)
            .map(des).toPropertyWhenPresent("des", record::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.374+08:00", comments="Source Table: uds_job_weight_limit")
    default Optional<UdsJobWeightLimit> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.374+08:00", comments="Source Table: uds_job_weight_limit")
    default List<UdsJobWeightLimit> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.375+08:00", comments="Source Table: uds_job_weight_limit")
    default List<UdsJobWeightLimit> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.376+08:00", comments="Source Table: uds_job_weight_limit")
    default Optional<UdsJobWeightLimit> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.377+08:00", comments="Source Table: uds_job_weight_limit")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobWeightLimit, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.377+08:00", comments="Source Table: uds_job_weight_limit")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobWeightLimit record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(limitType).equalTo(record::getLimitType)
                .set(limitValue).equalTo(record::getLimitValue)
                .set(serverIds).equalTo(record::getServerIds)
                .set(timeWinds).equalTo(record::getTimeWinds)
                .set(des).equalTo(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.378+08:00", comments="Source Table: uds_job_weight_limit")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobWeightLimit record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(limitType).equalToWhenPresent(record::getLimitType)
                .set(limitValue).equalToWhenPresent(record::getLimitValue)
                .set(serverIds).equalToWhenPresent(record::getServerIds)
                .set(timeWinds).equalToWhenPresent(record::getTimeWinds)
                .set(des).equalToWhenPresent(record::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.38+08:00", comments="Source Table: uds_job_weight_limit")
    default int updateByPrimaryKey(UdsJobWeightLimit record) {
        return update(c ->
            c.set(limitType).equalTo(record::getLimitType)
            .set(limitValue).equalTo(record::getLimitValue)
            .set(serverIds).equalTo(record::getServerIds)
            .set(timeWinds).equalTo(record::getTimeWinds)
            .set(des).equalTo(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T09:50:02.382+08:00", comments="Source Table: uds_job_weight_limit")
    default int updateByPrimaryKeySelective(UdsJobWeightLimit record) {
        return update(c ->
            c.set(limitType).equalToWhenPresent(record::getLimitType)
            .set(limitValue).equalToWhenPresent(record::getLimitValue)
            .set(serverIds).equalToWhenPresent(record::getServerIds)
            .set(timeWinds).equalToWhenPresent(record::getTimeWinds)
            .set(des).equalToWhenPresent(record::getDes)
            .where(id, isEqualTo(record::getId))
        );
    }
    
	@ResultMap("UdsJobWeightLimitResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobWeightLimit> selectManyPage(SelectStatementProvider selectStatement, Page<UdsJobWeightLimit> page); 
	default Page<UdsJobWeightLimit> selectAll(Page<UdsJobWeightLimit> page,Integer limit_type){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(udsJobWeightLimit)
				.where(limitType,isLikeWhenPresent(limit_type))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsJobWeightLimit> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
	
	default int updateWeightLimit(Integer limit_type,Integer limit_value,String serverIds_,String timeWindows) {
		UpdateStatementProvider updateStatement = SqlBuilder.update(udsJobWeightLimit)
				.set(limitValue).equalToWhenPresent(limit_value)
				.set(serverIds).equalToWhenPresent(serverIds_)
				.set(timeWinds).equalToWhenPresent(timeWindows)
				.where(limitType, isEqualTo(limit_type))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		return update(updateStatement);
		
	}
	
	default int delete(Long[] ids) {
		return delete(c -> c.where(id, isIn(ids)));
	}
}