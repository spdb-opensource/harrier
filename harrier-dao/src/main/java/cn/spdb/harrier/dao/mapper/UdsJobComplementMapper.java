package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsJobComplementDynamicSqlSupport.*;
import static cn.spdb.harrier.dao.mapper.UdsJobConfigDynamicSqlSupport.udsJobConfig;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;

import cn.spdb.harrier.dao.entity.UdsJobComplement;

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
public interface UdsJobComplementMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<UdsJobComplement>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.886+08:00", comments="Source Table: uds_job_complement")
    BasicColumn[] selectList = BasicColumn.columnList(id, complementId, platform, systems, job, jobDate, lastStatus, serverName, multiBatch, startTime, endTime, des);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.868+08:00", comments="Source Table: uds_job_complement")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UdsJobComplementResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="complement_id", property="complementId", jdbcType=JdbcType.BIGINT),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_date", property="jobDate", jdbcType=JdbcType.DATE),
        @Result(column="last_status", property="lastStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="server_name", property="serverName", jdbcType=JdbcType.VARCHAR),
        @Result(column="multi_batch", property="multiBatch", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="des", property="des", jdbcType=JdbcType.VARCHAR)
    })
    List<UdsJobComplement> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.872+08:00", comments="Source Table: uds_job_complement")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UdsJobComplementResult")
    Optional<UdsJobComplement> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.873+08:00", comments="Source Table: uds_job_complement")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.873+08:00", comments="Source Table: uds_job_complement")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.875+08:00", comments="Source Table: uds_job_complement")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.876+08:00", comments="Source Table: uds_job_complement")
    default int insert(UdsJobComplement row) {
        return MyBatis3Utils.insert(this::insert, row, udsJobComplement, c ->
            c.map(id).toProperty("id")
            .map(complementId).toProperty("complementId")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(serverName).toProperty("serverName")
            .map(multiBatch).toProperty("multiBatch")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.881+08:00", comments="Source Table: uds_job_complement")
    default int insertMultiple(Collection<UdsJobComplement> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsJobComplement, c ->
            c.map(id).toProperty("id")
            .map(complementId).toProperty("complementId")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(jobDate).toProperty("jobDate")
            .map(lastStatus).toProperty("lastStatus")
            .map(serverName).toProperty("serverName")
            .map(multiBatch).toProperty("multiBatch")
            .map(startTime).toProperty("startTime")
            .map(endTime).toProperty("endTime")
            .map(des).toProperty("des")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.882+08:00", comments="Source Table: uds_job_complement")
    default int insertSelective(UdsJobComplement row) {
        return MyBatis3Utils.insert(this::insert, row, udsJobComplement, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(complementId).toPropertyWhenPresent("complementId", row::getComplementId)
            .map(platform).toPropertyWhenPresent("platform", row::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", row::getSystems)
            .map(job).toPropertyWhenPresent("job", row::getJob)
            .map(jobDate).toPropertyWhenPresent("jobDate", row::getJobDate)
            .map(lastStatus).toPropertyWhenPresent("lastStatus", row::getLastStatus)
            .map(serverName).toPropertyWhenPresent("serverName", row::getServerName)
            .map(multiBatch).toPropertyWhenPresent("multiBatch", row::getMultiBatch)
            .map(startTime).toPropertyWhenPresent("startTime", row::getStartTime)
            .map(endTime).toPropertyWhenPresent("endTime", row::getEndTime)
            .map(des).toPropertyWhenPresent("des", row::getDes)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.887+08:00", comments="Source Table: uds_job_complement")
    default Optional<UdsJobComplement> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.889+08:00", comments="Source Table: uds_job_complement")
    default List<UdsJobComplement> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.889+08:00", comments="Source Table: uds_job_complement")
    default List<UdsJobComplement> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.89+08:00", comments="Source Table: uds_job_complement")
    default Optional<UdsJobComplement> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.89+08:00", comments="Source Table: uds_job_complement")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, udsJobComplement, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.891+08:00", comments="Source Table: uds_job_complement")
    static UpdateDSL<UpdateModel> updateAllColumns(UdsJobComplement row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(complementId).equalTo(row::getComplementId)
                .set(platform).equalTo(row::getPlatform)
                .set(systems).equalTo(row::getSystems)
                .set(job).equalTo(row::getJob)
                .set(jobDate).equalTo(row::getJobDate)
                .set(lastStatus).equalTo(row::getLastStatus)
                .set(serverName).equalTo(row::getServerName)
                .set(multiBatch).equalTo(row::getMultiBatch)
                .set(startTime).equalTo(row::getStartTime)
                .set(endTime).equalTo(row::getEndTime)
                .set(des).equalTo(row::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.892+08:00", comments="Source Table: uds_job_complement")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsJobComplement row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(complementId).equalToWhenPresent(row::getComplementId)
                .set(platform).equalToWhenPresent(row::getPlatform)
                .set(systems).equalToWhenPresent(row::getSystems)
                .set(job).equalToWhenPresent(row::getJob)
                .set(jobDate).equalToWhenPresent(row::getJobDate)
                .set(lastStatus).equalToWhenPresent(row::getLastStatus)
                .set(serverName).equalToWhenPresent(row::getServerName)
                .set(multiBatch).equalToWhenPresent(row::getMultiBatch)
                .set(startTime).equalToWhenPresent(row::getStartTime)
                .set(endTime).equalToWhenPresent(row::getEndTime)
                .set(des).equalToWhenPresent(row::getDes);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.894+08:00", comments="Source Table: uds_job_complement")
    default int updateByPrimaryKey(UdsJobComplement row) {
        return update(c ->
            c.set(complementId).equalTo(row::getComplementId)
            .set(platform).equalTo(row::getPlatform)
            .set(systems).equalTo(row::getSystems)
            .set(job).equalTo(row::getJob)
            .set(jobDate).equalTo(row::getJobDate)
            .set(lastStatus).equalTo(row::getLastStatus)
            .set(serverName).equalTo(row::getServerName)
            .set(multiBatch).equalTo(row::getMultiBatch)
            .set(startTime).equalTo(row::getStartTime)
            .set(endTime).equalTo(row::getEndTime)
            .set(des).equalTo(row::getDes)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.895+08:00", comments="Source Table: uds_job_complement")
    default int updateByPrimaryKeySelective(UdsJobComplement row) {
        return update(c ->
            c.set(complementId).equalToWhenPresent(row::getComplementId)
            .set(platform).equalToWhenPresent(row::getPlatform)
            .set(systems).equalToWhenPresent(row::getSystems)
            .set(job).equalToWhenPresent(row::getJob)
            .set(jobDate).equalToWhenPresent(row::getJobDate)
            .set(lastStatus).equalToWhenPresent(row::getLastStatus)
            .set(serverName).equalToWhenPresent(row::getServerName)
            .set(multiBatch).equalToWhenPresent(row::getMultiBatch)
            .set(startTime).equalToWhenPresent(row::getStartTime)
            .set(endTime).equalToWhenPresent(row::getEndTime)
            .set(des).equalToWhenPresent(row::getDes)
            .where(id, isEqualTo(row::getId))
        );
    }
    
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-06-20T13:57:10.895+08:00", comments="Source Table: uds_job_complement")
    default int updateByPrimaryIdSelective(UdsJobComplement row) {
        return update(c ->
            c.set(complementId).equalToWhenPresent(row::getComplementId)
            .set(platform).equalToWhenPresent(row::getPlatform)
            .set(systems).equalToWhenPresent(row::getSystems)
            .set(job).equalToWhenPresent(row::getJob)
            .set(jobDate).equalToWhenPresent(row::getJobDate)
            .set(lastStatus).equalToWhenPresent(row::getLastStatus)
            .set(serverName).equalToWhenPresent(row::getServerName)
            .set(multiBatch).equalToWhenPresent(row::getMultiBatch)
            .set(startTime).equalToWhenPresent(row::getStartTime)
            .set(endTime).equalToWhenPresent(row::getEndTime)
            .set(des).equalToWhenPresent(row::getDes)
            .where(complementId, isEqualTo(row::getComplementId))
            .and(platform, isEqualTo(row::getPlatform))
            .and(systems, isEqualTo(row::getSystems))
            .and(job, isEqualTo(row::getJob))
        );
    }
    
    
	default Page<UdsJobComplement> selectAll(Page<UdsJobComplement> page,String platform_,String systems_,String job_,String last_status_, Long complement_id_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(UdsJobComplementDynamicSqlSupport.udsJobComplement)
				.where(udsJobComplement.job,isLikeWhenPresent(job_))
				.and(udsJobComplement.platform,isEqualToWhenPresent(platform_))
				.and(udsJobComplement.systems,isEqualToWhenPresent(systems_))
				.and(udsJobComplement.lastStatus,isEqualToWhenPresent(last_status_))
				.and(udsJobComplement.complementId,isEqualToWhenPresent(complement_id_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsJobComplement> records = selectManyPage(selectStatement,page);
		page.setRecords(records);
		return page;
	}
    
	@ResultMap("UdsJobComplementResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsJobComplement> selectManyPage(SelectStatementProvider selectStatement,Page<UdsJobComplement> page);
	
}