package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MRoleMenuDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MRoleMenu;
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
public interface MRoleMenuMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    BasicColumn[] selectList = BasicColumn.columnList(roleId, menuId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MRoleMenu> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MRoleMenu> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MRoleMenuResult")
    Optional<MRoleMenu> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MRoleMenuResult", value = {
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="menu_id", property="menuId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<MRoleMenu> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int deleteByPrimaryKey(Integer roleId_, Integer menuId_) {
        return delete(c -> 
            c.where(roleId, isEqualTo(roleId_))
            .and(menuId, isEqualTo(menuId_))
        );
    }

    default int deleteByRoleId(Integer roleId_) {
        return delete(c -> 
            c.where(roleId, isEqualTo(roleId_))
        );
    }
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int insert(MRoleMenu record) {
        return MyBatis3Utils.insert(this::insert, record, MRoleMenu, c ->
            c.map(roleId).toProperty("roleId")
            .map(menuId).toProperty("menuId")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int insertMultiple(Collection<MRoleMenu> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MRoleMenu, c ->
            c.map(roleId).toProperty("roleId")
            .map(menuId).toProperty("menuId")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int insertSelective(MRoleMenu record) {
        return MyBatis3Utils.insert(this::insert, record, MRoleMenu, c ->
            c.map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
            .map(menuId).toPropertyWhenPresent("menuId", record::getMenuId)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default Optional<MRoleMenu> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default List<MRoleMenu> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default List<MRoleMenu> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MRoleMenu, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    static UpdateDSL<UpdateModel> updateAllColumns(MRoleMenu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalTo(record::getRoleId)
                .set(menuId).equalTo(record::getMenuId);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source Table: m_role_menu")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MRoleMenu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalToWhenPresent(record::getRoleId)
                .set(menuId).equalToWhenPresent(record::getMenuId);
    }
}