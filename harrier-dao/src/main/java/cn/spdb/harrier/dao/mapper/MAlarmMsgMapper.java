package cn.spdb.harrier.dao.mapper;


import static cn.spdb.harrier.dao.mapper.MAlarmMsgDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MAlarmMsg;
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
public interface MAlarmMsgMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, code, alarmType, alarmLevel, title, content, remark);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MAlarmMsg> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MAlarmMsg> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.38+08:00", comments="Source Table: m_alarm_msg")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MAlarmMsgResult")
    Optional<MAlarmMsg> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MAlarmMsgResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_type", property="alarmType", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_level", property="alarmLevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<MAlarmMsg> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int insert(MAlarmMsg record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmMsg, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(code).toProperty("code")
            .map(alarmType).toProperty("alarmType")
            .map(alarmLevel).toProperty("alarmLevel")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int insertMultiple(Collection<MAlarmMsg> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MAlarmMsg, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(code).toProperty("code")
            .map(alarmType).toProperty("alarmType")
            .map(alarmLevel).toProperty("alarmLevel")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int insertSelective(MAlarmMsg record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmMsg, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(code).toPropertyWhenPresent("code", record::getCode)
            .map(alarmType).toPropertyWhenPresent("alarmType", record::getAlarmType)
            .map(alarmLevel).toPropertyWhenPresent("alarmLevel", record::getAlarmLevel)
            .map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(content).toPropertyWhenPresent("content", record::getContent)
            .map(remark).toPropertyWhenPresent("remark", record::getRemark)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default Optional<MAlarmMsg> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default List<MAlarmMsg> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default List<MAlarmMsg> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default Optional<MAlarmMsg> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MAlarmMsg, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.381+08:00", comments="Source Table: m_alarm_msg")
    static UpdateDSL<UpdateModel> updateAllColumns(MAlarmMsg record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(code).equalTo(record::getCode)
                .set(alarmType).equalTo(record::getAlarmType)
                .set(alarmLevel).equalTo(record::getAlarmLevel)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(remark).equalTo(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source Table: m_alarm_msg")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MAlarmMsg record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(code).equalToWhenPresent(record::getCode)
                .set(alarmType).equalToWhenPresent(record::getAlarmType)
                .set(alarmLevel).equalToWhenPresent(record::getAlarmLevel)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(remark).equalToWhenPresent(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source Table: m_alarm_msg")
    default int updateByPrimaryKey(MAlarmMsg record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(code).equalTo(record::getCode)
            .set(alarmType).equalTo(record::getAlarmType)
            .set(alarmLevel).equalTo(record::getAlarmLevel)
            .set(title).equalTo(record::getTitle)
            .set(content).equalTo(record::getContent)
            .set(remark).equalTo(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.382+08:00", comments="Source Table: m_alarm_msg")
    default int updateByPrimaryKeySelective(MAlarmMsg record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(code).equalToWhenPresent(record::getCode)
            .set(alarmType).equalToWhenPresent(record::getAlarmType)
            .set(alarmLevel).equalToWhenPresent(record::getAlarmLevel)
            .set(title).equalToWhenPresent(record::getTitle)
            .set(content).equalToWhenPresent(record::getContent)
            .set(remark).equalToWhenPresent(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }


    default Optional<MAlarmMsg> selectOne(String code_, String platform_, String systems_){
    	return selectOne(c->c.where(platform,isEqualTo(platform_))
    			.and(systems, isEqualTo(systems_))
    			.and(code, isEqualTo(code_))
    			);
    	
    }

	@ResultMap("MAlarmMsgResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MAlarmMsg> selectManyPage(SelectStatementProvider selectStatement, Page<MAlarmMsg> page);

	
	default Page<MAlarmMsg> search(Page<MAlarmMsg> page, String platform_, String systems_, String code_){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MAlarmMsg)
				.where()
				.and(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(systems_))
				.and(code, isEqualToWhenPresent(code_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MAlarmMsg> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
}