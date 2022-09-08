package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MUserRoleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MUserRole;
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
public interface MUserRoleMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    BasicColumn[] selectList = BasicColumn.columnList(userId, roleId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MUserRole> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MUserRole> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MUserRoleResult")
    Optional<MUserRole> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MUserRoleResult", value = {
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<MUserRole> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int deleteByPrimaryKey(Long userId_, Integer roleId_) {
        return delete(c -> 
            c.where(userId, isEqualTo(userId_))
            .and(roleId, isEqualTo(roleId_))
        );
    }

    default int deleteByUserId(Long userId_) {
        return delete(c -> 
            c.where(userId, isEqualTo(userId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int insert(MUserRole record) {
        return MyBatis3Utils.insert(this::insert, record, MUserRole, c ->
            c.map(userId).toProperty("userId")
            .map(roleId).toProperty("roleId")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int insertMultiple(Collection<MUserRole> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MUserRole, c ->
            c.map(userId).toProperty("userId")
            .map(roleId).toProperty("roleId")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int insertSelective(MUserRole record) {
        return MyBatis3Utils.insert(this::insert, record, MUserRole, c ->
            c.map(userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default Optional<MUserRole> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default List<MUserRole> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default List<MUserRole> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MUserRole, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    static UpdateDSL<UpdateModel> updateAllColumns(MUserRole record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(userId).equalTo(record::getUserId)
                .set(roleId).equalTo(record::getRoleId);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source Table: m_user_role")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MUserRole record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(userId).equalToWhenPresent(record::getUserId)
                .set(roleId).equalToWhenPresent(record::getRoleId);
    }
}