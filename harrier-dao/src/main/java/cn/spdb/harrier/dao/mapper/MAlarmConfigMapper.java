package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MAlarmConfig;

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
public interface MAlarmConfigMapper {
    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	BasicColumn[] selectList = BasicColumn.columnList(id, code, platform, systems, job, defStatus, noticeTimes,
			noticeCycle, noticeGroupName, build, isEnable, updateUser, updateTime, remark);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.361+08:00", comments = "Source Table: m_alarm_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.361+08:00", comments = "Source Table: m_alarm_config")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.361+08:00", comments = "Source Table: m_alarm_config")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<MAlarmConfig> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.361+08:00", comments = "Source Table: m_alarm_config")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<MAlarmConfig> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.361+08:00", comments = "Source Table: m_alarm_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("MAlarmConfigResult")
	Optional<MAlarmConfig> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "MAlarmConfigResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "job", property = "job", jdbcType = JdbcType.VARCHAR),
			@Result(column = "def_status", property = "defStatus", jdbcType = JdbcType.INTEGER),
			@Result(column = "notice_times", property = "noticeTimes", jdbcType = JdbcType.INTEGER),
			@Result(column = "notice_cycle", property = "noticeCycle", jdbcType = JdbcType.INTEGER),
			@Result(column = "notice_group_name", property = "noticeGroupName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "build", property = "build", jdbcType = JdbcType.BIT),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "update_user", property = "updateUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR) })
	List<MAlarmConfig> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, MAlarmConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, MAlarmConfig, completer);
	}

	@Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.362+08:00", comments="Source Table: m_alarm_config")
    default int deleteByPrimaryKey(Integer id_ ,String platform_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_)).and(platform, isEqualToWhenPresent(platform_))
        );
    }

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default int insert(MAlarmConfig record) {
		return MyBatis3Utils.insert(this::insert, record, MAlarmConfig, c -> c.map(id).toProperty("id").map(code)
				.toProperty("code").map(platform).toProperty("platform").map(systems).toProperty("systems").map(job)
				.toProperty("job").map(defStatus).toProperty("defStatus").map(noticeTimes).toProperty("noticeTimes")
				.map(noticeCycle).toProperty("noticeCycle").map(noticeGroupName).toProperty("noticeGroupName")
				.map(build).toProperty("build").map(isEnable).toProperty("isEnable").map(updateUser)
				.toProperty("updateUser").map(updateTime).toProperty("updateTime").map(remark).toProperty("remark"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default int insertMultiple(Collection<MAlarmConfig> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MAlarmConfig,
				c -> c.map(id).toProperty("id").map(code).toProperty("code").map(platform).toProperty("platform")
						.map(systems).toProperty("systems").map(job).toProperty("job").map(defStatus)
						.toProperty("defStatus").map(noticeTimes).toProperty("noticeTimes").map(noticeCycle)
						.toProperty("noticeCycle").map(noticeGroupName).toProperty("noticeGroupName").map(build)
						.toProperty("build").map(isEnable).toProperty("isEnable").map(updateUser)
						.toProperty("updateUser").map(updateTime).toProperty("updateTime").map(remark)
						.toProperty("remark"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default int insertSelective(MAlarmConfig record) {
		return MyBatis3Utils.insert(this::insert, record, MAlarmConfig,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(code)
						.toPropertyWhenPresent("code", record::getCode).map(platform)
						.toPropertyWhenPresent("platform", record::getPlatform).map(systems)
						.toPropertyWhenPresent("systems", record::getSystems).map(job)
						.toPropertyWhenPresent("job", record::getJob).map(defStatus)
						.toPropertyWhenPresent("defStatus", record::getDefStatus).map(noticeTimes)
						.toPropertyWhenPresent("noticeTimes", record::getNoticeTimes).map(noticeCycle)
						.toPropertyWhenPresent("noticeCycle", record::getNoticeCycle).map(noticeGroupName)
						.toPropertyWhenPresent("noticeGroupName", record::getNoticeGroupName).map(build)
						.toPropertyWhenPresent("build", record::getBuild).map(isEnable)
						.toPropertyWhenPresent("isEnable", record::getIsEnable).map(updateUser)
						.toPropertyWhenPresent("updateUser", record::getUpdateUser).map(updateTime)
						.toPropertyWhenPresent("updateTime", record::getUpdateTime).map(remark)
						.toPropertyWhenPresent("remark", record::getRemark));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default Optional<MAlarmConfig> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, MAlarmConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default List<MAlarmConfig> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, MAlarmConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default List<MAlarmConfig> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MAlarmConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default Optional<MAlarmConfig> selectByPrimaryKey(Integer id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.362+08:00", comments = "Source Table: m_alarm_config")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, MAlarmConfig, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.363+08:00", comments = "Source Table: m_alarm_config")
	static UpdateDSL<UpdateModel> updateAllColumns(MAlarmConfig record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(code).equalTo(record::getCode).set(platform)
				.equalTo(record::getPlatform).set(systems).equalTo(record::getSystems).set(job).equalTo(record::getJob)
				.set(defStatus).equalTo(record::getDefStatus).set(noticeTimes).equalTo(record::getNoticeTimes)
				.set(noticeCycle).equalTo(record::getNoticeCycle).set(noticeGroupName)
				.equalTo(record::getNoticeGroupName).set(build).equalTo(record::getBuild).set(isEnable)
				.equalTo(record::getIsEnable).set(updateUser).equalTo(record::getUpdateUser).set(updateTime)
				.equalTo(record::getUpdateTime).set(remark).equalTo(record::getRemark);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.363+08:00", comments = "Source Table: m_alarm_config")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(MAlarmConfig record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(code).equalToWhenPresent(record::getCode).set(platform)
				.equalToWhenPresent(record::getPlatform).set(systems).equalToWhenPresent(record::getSystems).set(job)
				.equalToWhenPresent(record::getJob).set(defStatus).equalToWhenPresent(record::getDefStatus)
				.set(noticeTimes).equalToWhenPresent(record::getNoticeTimes).set(noticeCycle)
				.equalToWhenPresent(record::getNoticeCycle).set(noticeGroupName)
				.equalToWhenPresent(record::getNoticeGroupName).set(build).equalToWhenPresent(record::getBuild)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(updateUser)
				.equalToWhenPresent(record::getUpdateUser).set(updateTime).equalToWhenPresent(record::getUpdateTime)
				.set(remark).equalToWhenPresent(record::getRemark);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.363+08:00", comments = "Source Table: m_alarm_config")
	default int updateByPrimaryKey(MAlarmConfig record) {
		return update(c -> c.set(code).equalTo(record::getCode).set(platform).equalTo(record::getPlatform).set(systems)
				.equalTo(record::getSystems).set(job).equalTo(record::getJob).set(defStatus)
				.equalTo(record::getDefStatus).set(noticeTimes).equalTo(record::getNoticeTimes).set(noticeCycle)
				.equalTo(record::getNoticeCycle).set(noticeGroupName).equalTo(record::getNoticeGroupName).set(build)
				.equalTo(record::getBuild).set(isEnable).equalTo(record::getIsEnable).set(updateUser)
				.equalTo(record::getUpdateUser).set(updateTime).equalTo(record::getUpdateTime).set(remark)
				.equalTo(record::getRemark).where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-14T14:51:28.363+08:00", comments = "Source Table: m_alarm_config")
	default int updateByPrimaryKeySelective(MAlarmConfig record) {
		return update(c -> c.set(code).equalToWhenPresent(record::getCode).set(platform)
				.equalToWhenPresent(record::getPlatform).set(systems).equalToWhenPresent(record::getSystems).set(job)
				.equalToWhenPresent(record::getJob).set(defStatus).equalToWhenPresent(record::getDefStatus)
				.set(noticeTimes).equalToWhenPresent(record::getNoticeTimes).set(noticeCycle)
				.equalToWhenPresent(record::getNoticeCycle).set(noticeGroupName)
				.equalToWhenPresent(record::getNoticeGroupName).set(build).equalToWhenPresent(record::getBuild)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(updateUser)
				.equalToWhenPresent(record::getUpdateUser).set(updateTime).equalToWhenPresent(record::getUpdateTime)
				.set(remark).equalToWhenPresent(record::getRemark).where(id, isEqualTo(record::getId)));
	}

	default Optional<MAlarmConfig> selectOne(String code_, String platform_, String systems_, String job_){
		return selectOne(c->c.where(code,isEqualTo(code_))
				.and(platform, isEqualTo(platform_))
				.and(systems, isEqualTo(systems_))
				.and(job, isEqualTo(job_))
				);
	}

	@ResultMap("MAlarmConfigResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MAlarmConfig> selectManyPage(SelectStatementProvider selectStatement, Page<MAlarmConfig> page);

	
	default Page<MAlarmConfig> search(Page<MAlarmConfig> page, String platform_, String systems_, String code_, String job_){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MAlarmConfig)
				.where()
				.and(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(code, isEqualToWhenPresent(code_))
				.and(job, isEqualToWhenPresent(job_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MAlarmConfig> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}

	default Page<MAlarmConfig> searchJobConfig(Page<MAlarmConfig> page, String platfrom_, String systems_, String code_, String job_){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MAlarmConfig)
				.where()
				.and(platform, isEqualToWhenPresent(platfrom_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(code, isEqualToWhenPresent(code_))
				.and(job,isNotEqualTo("*"))
				.and(job,isLikeWhenPresent(job_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MAlarmConfig> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	};
}