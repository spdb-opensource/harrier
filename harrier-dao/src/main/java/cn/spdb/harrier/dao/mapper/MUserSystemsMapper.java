package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MUserSystemsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MUserSystems;
import cn.spdb.harrier.dao.entity.UserSystemPermiss;

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

@Mapper
public interface MUserSystemsMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	BasicColumn[] selectList = BasicColumn.columnList(userId, systemsId, permissions);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<MUserSystems> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<MUserSystems> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("MUserSystemsResult")
	Optional<MUserSystems> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "MUserSystemsResult", value = {
			@Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "systems_id", property = "systemsId", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "permissions", property = "permissions", jdbcType = JdbcType.VARCHAR) })
	List<MUserSystems> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int deleteByPrimaryKey(Long userId_, Integer systemsId_) {
		return delete(c -> c.where(userId, isEqualTo(userId_)).and(systemsId, isEqualTo(systemsId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int insert(MUserSystems record) {
		return MyBatis3Utils.insert(this::insert, record, MUserSystems, c -> c.map(userId).toProperty("userId")
				.map(systemsId).toProperty("systemsId").map(permissions).toProperty("permissions"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int insertMultiple(Collection<MUserSystems> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MUserSystems,
				c -> c.map(userId).toProperty("userId").map(systemsId).toProperty("systemsId").map(permissions)
						.toProperty("permissions"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int insertSelective(MUserSystems record) {
		return MyBatis3Utils.insert(this::insert, record, MUserSystems,
				c -> c.map(userId).toPropertyWhenPresent("userId", record::getUserId).map(systemsId)
						.toPropertyWhenPresent("systemsId", record::getSystemsId).map(permissions)
						.toPropertyWhenPresent("permissions", record::getPermissions));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default Optional<MUserSystems> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default List<MUserSystems> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default List<MUserSystems> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default Optional<MUserSystems> selectByPrimaryKey(Long userId_, Integer systemsId_) {
		return selectOne(c -> c.where(userId, isEqualTo(userId_)).and(systemsId, isEqualTo(systemsId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, MUserSystems, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	static UpdateDSL<UpdateModel> updateAllColumns(MUserSystems record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(userId).equalTo(record::getUserId).set(systemsId).equalTo(record::getSystemsId).set(permissions)
				.equalTo(record::getPermissions);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(MUserSystems record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(userId).equalToWhenPresent(record::getUserId).set(systemsId)
				.equalToWhenPresent(record::getSystemsId).set(permissions).equalToWhenPresent(record::getPermissions);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int updateByPrimaryKey(MUserSystems record) {
		return update(c -> c.set(permissions).equalTo(record::getPermissions)
				.where(userId, isEqualTo(record::getUserId)).and(systemsId, isEqualTo(record::getSystemsId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.538+08:00", comments = "Source Table: m_user_systems")
	default int updateByPrimaryKeySelective(MUserSystems record) {
		return update(c -> c.set(permissions).equalToWhenPresent(record::getPermissions)
				.where(userId, isEqualTo(record::getUserId)).and(systemsId, isEqualTo(record::getSystemsId)));
	}

	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UserSystemPermiss", value = {
			@Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "permissions", property = "permissions", jdbcType = JdbcType.VARCHAR), })
	List<UserSystemPermiss> selectManyUserPeres(SelectStatementProvider selectStatement);

	default List<UserSystemPermiss> selectManyUserPeres(Long id) {
		SelectStatementProvider sql = SqlBuilder
				.select(userId, permissions, UdsSystemDynamicSqlSupport.platform, UdsSystemDynamicSqlSupport.systems)
				.from(MUserSystems).join(UdsSystemDynamicSqlSupport.udsSystem)
				.on(systemsId, equalTo(UdsSystemDynamicSqlSupport.id)).where(userId, isEqualTo(id)).build()
				.render(RenderingStrategies.MYBATIS3);

		return selectManyUserPeres(sql);
	}

	default int deleteByUserId(Long userId_) {
		return delete(c -> c.where(userId, isEqualTo(userId_)));
	}

	default List<MUserSystems> selectUserByUserIds(Long userId_) {
		return select(c -> c.where(userId, isEqualTo(userId_)));
	}

}