package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MMenuDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MMenu;
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
public interface MMenuMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	BasicColumn[] selectList = BasicColumn.columnList(menuId, menuName, parentMenuId, menuNo, menuUrl, menuIconUrl,
			menuFlag, createTime, createUser, updateTime, updateUser, isEnable, remark, component);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<MMenu> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<MMenu> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source Table: m_menu")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("MMenuResult")
	Optional<MMenu> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "MMenuResult", value = {
			@Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "menu_name", property = "menuName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "parent_menu_id", property = "parentMenuId", jdbcType = JdbcType.INTEGER),
			@Result(column = "menu_no", property = "menuNo", jdbcType = JdbcType.INTEGER),
			@Result(column = "menu_url", property = "menuUrl", jdbcType = JdbcType.VARCHAR),
			@Result(column = "menu_flag", property = "menuFlag", jdbcType = JdbcType.TINYINT),
			@Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "create_user", property = "createUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "update_user", property = "updateUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
			@Result(column = "component", property = "component", jdbcType = JdbcType.VARCHAR) })
	List<MMenu> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int deleteByPrimaryKey(Integer menuId_) {
		return delete(c -> c.where(menuId, isEqualTo(menuId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int insert(MMenu record) {
		return MyBatis3Utils.insert(this::insert, record, MMenu,
				c -> c.map(menuId).toProperty("menuId").map(menuName).toProperty("menuName").map(parentMenuId)
						.toProperty("parentMenuId").map(menuNo).toProperty("menuNo").map(menuUrl).toProperty("menuUrl")
						.map(menuIconUrl).toProperty("menuIconUrl").map(menuFlag).toProperty("menuFlag").map(createTime)
						.toProperty("createTime").map(createUser).toProperty("createUser").map(updateTime)
						.toProperty("updateTime").map(updateUser).toProperty("updateUser").map(isEnable)
						.toProperty("isEnable").map(remark).toProperty("remark").map(component).toProperty("component"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int insertMultiple(Collection<MMenu> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MMenu,
				c -> c.map(menuId).toProperty("menuId").map(menuName).toProperty("menuName").map(parentMenuId)
						.toProperty("parentMenuId").map(menuNo).toProperty("menuNo").map(menuUrl).toProperty("menuUrl")
						.map(menuIconUrl).toProperty("menuIconUrl").map(menuFlag).toProperty("menuFlag").map(createTime)
						.toProperty("createTime").map(createUser).toProperty("createUser").map(updateTime)
						.toProperty("updateTime").map(updateUser).toProperty("updateUser").map(isEnable)
						.toProperty("isEnable").map(remark).toProperty("remark").map(component).toProperty("component"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int insertSelective(MMenu record) {
		return MyBatis3Utils.insert(this::insert, record, MMenu,
				c -> c.map(menuId).toPropertyWhenPresent("menuId", record::getMenuId).map(menuName)
						.toPropertyWhenPresent("menuName", record::getMenuName).map(parentMenuId)
						.toPropertyWhenPresent("parentMenuId", record::getParentMenuId).map(menuNo)
						.toPropertyWhenPresent("menuNo", record::getMenuNo).map(menuUrl)
						.toPropertyWhenPresent("menuUrl", record::getMenuUrl).map(menuIconUrl)
						.toPropertyWhenPresent("menuIconUrl", record::getMenuIconUrl).map(menuFlag)
						.toPropertyWhenPresent("menuFlag", record::getMenuFlag).map(createTime)
						.toPropertyWhenPresent("createTime", record::getCreateTime).map(createUser)
						.toPropertyWhenPresent("createUser", record::getCreateUser).map(updateTime)
						.toPropertyWhenPresent("updateTime", record::getUpdateTime).map(updateUser)
						.toPropertyWhenPresent("updateUser", record::getUpdateUser).map(isEnable)
						.toPropertyWhenPresent("isEnable", record::getIsEnable).map(remark)
						.toPropertyWhenPresent("remark", record::getRemark).map(component)
						.toPropertyWhenPresent("component", record::getComponent));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default Optional<MMenu> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default List<MMenu> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default List<MMenu> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default Optional<MMenu> selectByPrimaryKey(Integer menuId_) {
		return selectOne(c -> c.where(menuId, isEqualTo(menuId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, MMenu, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	static UpdateDSL<UpdateModel> updateAllColumns(MMenu record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(menuId).equalTo(record::getMenuId).set(menuName).equalTo(record::getMenuName).set(parentMenuId)
				.equalTo(record::getParentMenuId).set(menuNo).equalTo(record::getMenuNo).set(menuUrl)
				.equalTo(record::getMenuUrl).set(menuIconUrl).equalTo(record::getMenuIconUrl).set(menuFlag)
				.equalTo(record::getMenuFlag).set(createTime).equalTo(record::getCreateTime).set(createUser)
				.equalTo(record::getCreateUser).set(updateTime).equalTo(record::getUpdateTime).set(updateUser)
				.equalTo(record::getUpdateUser).set(isEnable).equalTo(record::getIsEnable).set(remark)
				.equalTo(record::getRemark).set(component).equalTo(record::getComponent);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(MMenu record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(menuId).equalToWhenPresent(record::getMenuId).set(menuName)
				.equalToWhenPresent(record::getMenuName).set(parentMenuId).equalToWhenPresent(record::getParentMenuId)
				.set(menuNo).equalToWhenPresent(record::getMenuNo).set(menuUrl).equalToWhenPresent(record::getMenuUrl)
				.set(menuIconUrl).equalToWhenPresent(record::getMenuIconUrl).set(menuFlag)
				.equalToWhenPresent(record::getMenuFlag).set(createTime).equalToWhenPresent(record::getCreateTime)
				.set(createUser).equalToWhenPresent(record::getCreateUser).set(updateTime)
				.equalToWhenPresent(record::getUpdateTime).set(updateUser).equalToWhenPresent(record::getUpdateUser)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(remark)
				.equalToWhenPresent(record::getRemark).set(component).equalToWhenPresent(record::getComponent);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int updateByPrimaryKey(MMenu record) {
		return update(c -> c.set(menuName).equalTo(record::getMenuName).set(parentMenuId)
				.equalTo(record::getParentMenuId).set(menuNo).equalTo(record::getMenuNo).set(menuUrl)
				.equalTo(record::getMenuUrl).set(menuIconUrl).equalTo(record::getMenuIconUrl).set(menuFlag)
				.equalTo(record::getMenuFlag).set(createTime).equalToWhenPresent(record::getCreateTime).set(createUser)
				.equalTo(record::getCreateUser).set(updateTime).equalToWhenPresent(record::getUpdateTime).set(updateUser)
				.equalTo(record::getUpdateUser).set(isEnable).equalTo(record::getIsEnable).set(remark)
				.equalTo(record::getRemark).set(component).equalTo(record::getComponent).where(menuId, isEqualTo(record::getMenuId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.548+08:00", comments = "Source Table: m_menu")
	default int updateByPrimaryKeySelective(MMenu record) {
		return update(c -> c.set(menuName).equalToWhenPresent(record::getMenuName).set(parentMenuId)
				.equalToWhenPresent(record::getParentMenuId).set(menuNo).equalToWhenPresent(record::getMenuNo)
				.set(menuUrl).equalToWhenPresent(record::getMenuUrl).set(menuIconUrl)
				.equalToWhenPresent(record::getMenuIconUrl).set(menuFlag).equalToWhenPresent(record::getMenuFlag)
				.set(createTime).equalToWhenPresent(record::getCreateTime).set(createUser)
				.equalToWhenPresent(record::getCreateUser).set(updateTime).equalToWhenPresent(record::getUpdateTime)
				.set(updateUser).equalToWhenPresent(record::getUpdateUser).set(isEnable)
				.equalToWhenPresent(record::getIsEnable).set(remark).equalToWhenPresent(record::getRemark)
				.set(component).equalToWhenPresent(record::getComponent)
				.where(menuId, isEqualTo(record::getMenuId)));
	}

	default int deletById(Integer[] menuIds) {
		return delete(c -> c.where(menuId, isIn(menuIds)));
	}

	default List<MMenu> selectByUserid(Long userId) {
		return select(c -> c.where(menuId,
				isIn(SqlBuilder.select(MRoleMenuDynamicSqlSupport.menuId).from(MRoleMenuDynamicSqlSupport.MRoleMenu)
						.where(MRoleMenuDynamicSqlSupport.roleId,
								isIn(SqlBuilder.select(MUserRoleDynamicSqlSupport.roleId)
										.from(MUserRoleDynamicSqlSupport.MUserRole)
										.where(MUserRoleDynamicSqlSupport.userId, isEqualTo(userId)))))));
	}

	default List<MMenu> selectByUsercode(String usercode) {
		 List<MMenu> list = select(c -> c.where(menuId, isIn(SqlBuilder.select(MRoleMenuDynamicSqlSupport.menuId)
				.from(MRoleMenuDynamicSqlSupport.MRoleMenu).where(MRoleMenuDynamicSqlSupport.roleId,
						isIn(SqlBuilder.select(MUserRoleDynamicSqlSupport.roleId)
								.from(MUserRoleDynamicSqlSupport.MUserRole).where(MUserRoleDynamicSqlSupport.userId,
										isEqualTo(SqlBuilder.select(MUserDynamicSqlSupport.userId)
												.from(MUserDynamicSqlSupport.MUser)
												.where(MUserDynamicSqlSupport.userCode, isEqualTo(usercode))

										)))))));
		 return list;
	}

	default List<MMenu> selectByUsercodeToLogin(String usercode) {
		 List<MMenu> list = select(c -> c.where(menuId, isIn(SqlBuilder.select(MRoleMenuDynamicSqlSupport.menuId)
				.from(MRoleMenuDynamicSqlSupport.MRoleMenu).where(MRoleMenuDynamicSqlSupport.roleId,
						isIn(SqlBuilder.select(MUserRoleDynamicSqlSupport.roleId)
								.from(MUserRoleDynamicSqlSupport.MUserRole).where(MUserRoleDynamicSqlSupport.userId,
										isEqualTo(SqlBuilder.select(MUserDynamicSqlSupport.userId)
												.from(MUserDynamicSqlSupport.MUser)
												.where(MUserDynamicSqlSupport.userCode, isEqualTo(usercode))

										)))))).and(isEnable, isEqualTo(true)));
		 return list;
	}
	
	
	default List<MMenu> selectByRoleId(Integer roleId) {
		return select(c -> c.where(menuId,
				isIn(SqlBuilder.select(MRoleMenuDynamicSqlSupport.menuId).from(MRoleMenuDynamicSqlSupport.MRoleMenu)
						.where(MRoleMenuDynamicSqlSupport.roleId, isEqualTo(roleId)))));
	}

	default List<MMenu> menuchildrens(Integer menuId) {
		return select(c -> c.where(parentMenuId, isEqualTo(menuId)));
	}

	default List<MMenu> select() {
		return select(c -> c.where(isEnable, isEqualTo(true)));
	}

}