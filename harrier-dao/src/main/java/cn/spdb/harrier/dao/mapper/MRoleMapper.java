package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MRoleDynamicSqlSupport.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

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

import cn.spdb.harrier.dao.entity.MRole;

@Mapper
public interface MRoleMapper{
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.945+08:00", comments="Source Table: m_role")
    BasicColumn[] selectList = BasicColumn.columnList(roleId, roleName, createTime, createUser, updateTime, updateUser, isEnable, remark);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.93+08:00", comments="Source Table: m_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.93+08:00", comments="Source Table: m_role")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.935+08:00", comments="Source Table: m_role")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MRole> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.935+08:00", comments="Source Table: m_role")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MRole> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.935+08:00", comments="Source Table: m_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MRoleResult")
    Optional<MRole> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.935+08:00", comments="Source Table: m_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MRoleResult", value = {
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user", property="updateUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_enable", property="isEnable", jdbcType=JdbcType.BIT),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<MRole> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.94+08:00", comments="Source Table: m_role")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.94+08:00", comments="Source Table: m_role")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.94+08:00", comments="Source Table: m_role")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.94+08:00", comments="Source Table: m_role")
    default int deleteByPrimaryKey(Integer roleId_) {
        return delete(c -> 
            c.where(roleId, isEqualTo(roleId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.94+08:00", comments="Source Table: m_role")
    default int insert(MRole record) {
        return MyBatis3Utils.insert(this::insert, record, MRole, c ->
            c.map(roleId).toProperty("roleId")
            .map(roleName).toProperty("roleName")
            .map(createTime).toProperty("createTime")
            .map(createUser).toProperty("createUser")
            .map(updateTime).toProperty("updateTime")
            .map(updateUser).toProperty("updateUser")
            .map(isEnable).toProperty("isEnable")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.945+08:00", comments="Source Table: m_role")
    default int insertMultiple(Collection<MRole> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MRole, c ->
            c.map(roleId).toProperty("roleId")
            .map(roleName).toProperty("roleName")
            .map(createTime).toProperty("createTime")
            .map(createUser).toProperty("createUser")
            .map(updateTime).toProperty("updateTime")
            .map(updateUser).toProperty("updateUser")
            .map(isEnable).toProperty("isEnable")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.945+08:00", comments="Source Table: m_role")
    default int insertSelective(MRole record) {
        return MyBatis3Utils.insert(this::insert, record, MRole, c ->
            c.map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
            .map(roleName).toPropertyWhenPresent("roleName", record::getRoleName)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(createUser).toPropertyWhenPresent("createUser", record::getCreateUser)
            .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
            .map(updateUser).toPropertyWhenPresent("updateUser", record::getUpdateUser)
            .map(isEnable).toPropertyWhenPresent("isEnable", record::getIsEnable)
            .map(remark).toPropertyWhenPresent("remark", record::getRemark)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.95+08:00", comments="Source Table: m_role")
    default Optional<MRole> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.95+08:00", comments="Source Table: m_role")
    default List<MRole> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.955+08:00", comments="Source Table: m_role")
    default List<MRole> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.955+08:00", comments="Source Table: m_role")
    default Optional<MRole> selectByPrimaryKey(Integer roleId_) {
        return selectOne(c ->
            c.where(roleId, isEqualTo(roleId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.955+08:00", comments="Source Table: m_role")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.955+08:00", comments="Source Table: m_role")
    static UpdateDSL<UpdateModel> updateAllColumns(MRole record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalTo(record::getRoleId)
                .set(roleName).equalTo(record::getRoleName)
                .set(createTime).equalTo(record::getCreateTime)
                .set(createUser).equalTo(record::getCreateUser)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(updateUser).equalTo(record::getUpdateUser)
                .set(isEnable).equalTo(record::getIsEnable)
                .set(remark).equalTo(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.96+08:00", comments="Source Table: m_role")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MRole record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalToWhenPresent(record::getRoleId)
                .set(roleName).equalToWhenPresent(record::getRoleName)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(createUser).equalToWhenPresent(record::getCreateUser)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(updateUser).equalToWhenPresent(record::getUpdateUser)
                .set(isEnable).equalToWhenPresent(record::getIsEnable)
                .set(remark).equalToWhenPresent(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.96+08:00", comments="Source Table: m_role")
    default int updateByPrimaryKey(MRole record) {
        return update(c ->
            c.set(roleName).equalTo(record::getRoleName)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(createUser).equalTo(record::getCreateUser)
            .set(updateTime).equalToWhenPresent(record::getUpdateTime)
            .set(updateUser).equalTo(record::getUpdateUser)
            .set(isEnable).equalTo(record::getIsEnable)
            .set(remark).equalTo(record::getRemark)
            .where(roleId, isEqualTo(record::getRoleId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.96+08:00", comments="Source Table: m_role")
    default int updateByPrimaryKeySelective(MRole record) {
        return update(c ->
            c.set(roleName).equalToWhenPresent(record::getRoleName)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(createUser).equalToWhenPresent(record::getCreateUser)
            .set(updateTime).equalToWhenPresent(record::getUpdateTime)
            .set(updateUser).equalToWhenPresent(record::getUpdateUser)
            .set(isEnable).equalToWhenPresent(record::getIsEnable)
            .set(remark).equalToWhenPresent(record::getRemark)
            .where(roleId, isEqualTo(record::getRoleId))
        );
    }
    
	default List<MRole> selectByUserId(Long userId) {
		return select(c -> c.where(roleId,
				isIn(SqlBuilder.select(MUserRoleDynamicSqlSupport.roleId).from(MUserRoleDynamicSqlSupport.MUserRole)
						.where(MUserRoleDynamicSqlSupport.userId, isEqualTo(userId)))));
	}

	default List<MRole> selectByUsercode(String userCode) {
		return select(c -> c.where(roleId,
				isIn(SqlBuilder.select(MUserRoleDynamicSqlSupport.roleId).from(MUserRoleDynamicSqlSupport.MUserRole)
						.where(MUserRoleDynamicSqlSupport.userId,
								isEqualTo(SqlBuilder.select(MUserDynamicSqlSupport.userId)
										.from(MUserDynamicSqlSupport.MUser)
										.where(MUserDynamicSqlSupport.userCode, isEqualTo(userCode)))))));
	}

	@ResultMap("MRoleResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MRole> selectManyPage(SelectStatementProvider selectStatement, Page<MRole> page);

	default Page<MRole> search(Page<MRole> page,String roleName_) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MRole)
				.where().and(roleName, isEqualToWhenPresent(roleName_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MRole> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
	
	default int delete(Integer[] roleIds) {
		return delete(c -> c.where(roleId, isIn(roleIds)));
	}
}