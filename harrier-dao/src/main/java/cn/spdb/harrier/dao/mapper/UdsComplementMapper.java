package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsComplementDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;

import cn.spdb.harrier.dao.entity.UdsComplement;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface UdsComplementMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<UdsComplement>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    BasicColumn[] selectList = BasicColumn.columnList(id, comName, startTime, endTime, lastStatus, batchRange, serverNameRange, maxRunJob,des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.905+08:00", comments="Source Table: uds_complement")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsComplementResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="com_name", property="comName", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_status", property="lastStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="batch_range", property="batchRange", jdbcType=JdbcType.VARCHAR),
        @Result(column="server_name_range", property="serverNameRange", jdbcType=JdbcType.VARCHAR),
        @Result(column="max_run_job", property="maxRunJob", jdbcType=JdbcType.INTEGER),
        @Result(column="notice_group_name", property="noticeGroupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsComplement> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsComplementResult")
    Optional<UdsComplement> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default int insert(UdsComplement row) {
        return MyBatis3Utils.insert(this::insert, row, udsComplement, c ->
            c.map(id).toProperty("id")
            .map(comName).toProperty("comName")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(lastStatus).toProperty("lastStatus")
            .map(batchRange).toProperty("batchRange")
            .map(serverNameRange).toProperty("serverNameRange")
            .map(maxRunJob).toProperty("maxRunJob")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default int insertMultiple(Collection<UdsComplement> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsComplement, c ->
            c.map(id).toProperty("id")
            .map(comName).toProperty("comName")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(lastStatus).toProperty("lastStatus")
            .map(batchRange).toProperty("batchRange")
            .map(serverNameRange).toProperty("serverNameRange")
            .map(maxRunJob).toProperty("maxRunJob")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.906+08:00", comments="Source Table: uds_complement")
    default int insertSelective(UdsComplement row) {
        return MyBatis3Utils.insert(this::insert, row, udsComplement, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(comName).toPropertyWhenPresent("comName", row::getComName)
            .map(startTime).toPropertyWhenPresent("startTime", row::getStartTime)
            .map(endTime).toPropertyWhenPresent("endTime", row::getEndTime)
            .map(lastStatus).toPropertyWhenPresent("lastStatus", row::getLastStatus)
            .map(batchRange).toPropertyWhenPresent("batchRange", row::getBatchRange)
            .map(serverNameRange).toPropertyWhenPresent("serverNameRange", row::getServerNameRange)
            .map(maxRunJob).toPropertyWhenPresent("maxRunJob", row::getMaxRunJob)
            .map(des).toPropertyWhenPresent("des", row::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    default Optional<UdsComplement> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    default List<UdsComplement> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    default List<UdsComplement> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    default Optional<UdsComplement> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.907+08:00", comments="Source Table: uds_complement")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsComplement row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(comName).equalTo(row::getComName)
                .set(startTime).equalTo(row::getStartTime)
                .set(endTime).equalTo(row::getEndTime)
                .set(lastStatus).equalTo(row::getLastStatus)
                .set(batchRange).equalTo(row::getBatchRange)
                .set(serverNameRange).equalTo(row::getServerNameRange)
                .set(maxRunJob).equalTo(row::getMaxRunJob)
                .set(des).equalTo(row::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.908+08:00", comments="Source Table: uds_complement")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsComplement row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(comName).equalToWhenPresent(row::getComName)
                .set(startTime).equalToWhenPresent(row::getStartTime)
                .set(endTime).equalToWhenPresent(row::getEndTime)
                .set(lastStatus).equalToWhenPresent(row::getLastStatus)
                .set(batchRange).equalToWhenPresent(row::getBatchRange)
                .set(serverNameRange).equalToWhenPresent(row::getServerNameRange)
                .set(maxRunJob).equalToWhenPresent(row::getMaxRunJob)
                .set(des).equalToWhenPresent(row::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.908+08:00", comments="Source Table: uds_complement")
    default int updateByPrimaryKey(UdsComplement row) {
        return update(c ->
            c.set(comName).equalTo(row::getComName)
            .set(startTime).equalTo(row::getStartTime)
            .set(endTime).equalTo(row::getEndTime)
            .set(lastStatus).equalTo(row::getLastStatus)
            .set(batchRange).equalTo(row::getBatchRange)
            .set(serverNameRange).equalTo(row::getServerNameRange)
            .set(maxRunJob).equalTo(row::getMaxRunJob)
            .set(des).equalTo(row::getDes)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.908+08:00", comments="Source Table: uds_complement")
    default int updateByPrimaryKeySelective(UdsComplement row) {
        return update(c ->
            c.set(comName).equalToWhenPresent(row::getComName)
            .set(startTime).equalToWhenPresent(row::getStartTime)
            .set(endTime).equalToWhenPresent(row::getEndTime)
            .set(lastStatus).equalToWhenPresent(row::getLastStatus)
            .set(batchRange).equalToWhenPresent(row::getBatchRange)
            .set(serverNameRange).equalToWhenPresent(row::getServerNameRange)
            .set(maxRunJob).equalToWhenPresent(row::getMaxRunJob)
            .set(des).equalToWhenPresent(row::getDes)
            .where(id, isEqualTo(row::getId))
        );
    }
   
	default Page<UdsComplement> selectAll(Page<UdsComplement> page,String com_name_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(UdsComplementDynamicSqlSupport.udsComplement)
				.where(udsComplement.comName,isLikeWhenPresent(com_name_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsComplement> records = selectManyPage(selectStatement,page);
		page.setRecords(records);
		return page;
	}
    
	@ResultMap("UdsComplementResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsComplement> selectManyPage(SelectStatementProvider selectStatement,Page<UdsComplement> page);
	
}