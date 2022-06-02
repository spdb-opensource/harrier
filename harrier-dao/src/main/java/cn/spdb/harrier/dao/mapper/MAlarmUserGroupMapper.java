package cn.spdb.harrier.dao.mapper;


import static cn.spdb.harrier.dao.mapper.MAlarmUserGroupDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MAlarmUserGroup;
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
public interface MAlarmUserGroupMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, groupName, sendType, sendParams);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source Table: m_alarm_user_group")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source Table: m_alarm_user_group")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source Table: m_alarm_user_group")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MAlarmUserGroup> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source Table: m_alarm_user_group")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MAlarmUserGroup> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.346+08:00", comments="Source Table: m_alarm_user_group")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MAlarmUserGroupResult")
    Optional<MAlarmUserGroup> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MAlarmUserGroupResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_type", property="sendType", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_params", property="sendParams", jdbcType=JdbcType.VARCHAR)
    })
    List<MAlarmUserGroup> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default int insert(MAlarmUserGroup record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmUserGroup, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(groupName).toProperty("groupName")
            .map(sendType).toProperty("sendType")
            .map(sendParams).toProperty("sendParams")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default int insertMultiple(Collection<MAlarmUserGroup> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MAlarmUserGroup, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(groupName).toProperty("groupName")
            .map(sendType).toProperty("sendType")
            .map(sendParams).toProperty("sendParams")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default int insertSelective(MAlarmUserGroup record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmUserGroup, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(groupName).toPropertyWhenPresent("groupName", record::getGroupName)
            .map(sendType).toPropertyWhenPresent("sendType", record::getSendType)
            .map(sendParams).toPropertyWhenPresent("sendParams", record::getSendParams)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default Optional<MAlarmUserGroup> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default List<MAlarmUserGroup> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.347+08:00", comments="Source Table: m_alarm_user_group")
    default List<MAlarmUserGroup> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    default Optional<MAlarmUserGroup> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MAlarmUserGroup, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    static UpdateDSL<UpdateModel> updateAllColumns(MAlarmUserGroup record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(groupName).equalTo(record::getGroupName)
                .set(sendType).equalTo(record::getSendType)
                .set(sendParams).equalTo(record::getSendParams);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MAlarmUserGroup record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(groupName).equalToWhenPresent(record::getGroupName)
                .set(sendType).equalToWhenPresent(record::getSendType)
                .set(sendParams).equalToWhenPresent(record::getSendParams);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    default int updateByPrimaryKey(MAlarmUserGroup record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(groupName).equalTo(record::getGroupName)
            .set(sendType).equalTo(record::getSendType)
            .set(sendParams).equalTo(record::getSendParams)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.348+08:00", comments="Source Table: m_alarm_user_group")
    default int updateByPrimaryKeySelective(MAlarmUserGroup record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(groupName).equalToWhenPresent(record::getGroupName)
            .set(sendType).equalToWhenPresent(record::getSendType)
            .set(sendParams).equalToWhenPresent(record::getSendParams)
            .where(id, isEqualTo(record::getId))
        );
    }

	default List<MAlarmUserGroup> selectListByGroupName(String groupName_){
		return select(c->c.where(groupName, isEqualTo(groupName_)));
	}

	@ResultMap("MAlarmUserGroupResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MAlarmUserGroup> selectManyPage(SelectStatementProvider selectStatement, Page<MAlarmUserGroup> page);

	
	
	default Page<MAlarmUserGroup> search(Page<MAlarmUserGroup> page,String groupName_){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MAlarmUserGroup)
				.where()
				.and(groupName, isEqualToWhenPresent(groupName_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MAlarmUserGroup> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
}