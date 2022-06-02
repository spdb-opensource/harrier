package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MAlarmSendQueueDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
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
public interface MAlarmSendQueueMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    BasicColumn[] selectList = BasicColumn.columnList(id, alarmId, groupName, sendStatus, sendType, sendParams, title, content, filePath, expcetion, sendTime, createTime, remark);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source Table: m_alarm_send_queue")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source Table: m_alarm_send_queue")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.375+08:00", comments="Source Table: m_alarm_send_queue")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MAlarmSendQueue> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MAlarmSendQueue> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MAlarmSendQueueResult")
    Optional<MAlarmSendQueue> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MAlarmSendQueueResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="alarm_id", property="alarmId", jdbcType=JdbcType.BIGINT),
        @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_status", property="sendStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_type", property="sendType", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_params", property="sendParams", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="file_path", property="filePath", jdbcType=JdbcType.VARCHAR),
        @Result(column="expcetion", property="expcetion", jdbcType=JdbcType.VARCHAR),
        @Result(column="send_time", property="sendTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<MAlarmSendQueue> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default int insert(MAlarmSendQueue record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmSendQueue, c ->
            c.map(id).toProperty("id")
            .map(alarmId).toProperty("alarmId")
            .map(groupName).toProperty("groupName")
            .map(sendStatus).toProperty("sendStatus")
            .map(sendType).toProperty("sendType")
            .map(sendParams).toProperty("sendParams")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(filePath).toProperty("filePath")
            .map(expcetion).toProperty("expcetion")
            .map(sendTime).toProperty("sendTime")
            .map(createTime).toProperty("createTime")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default int insertMultiple(Collection<MAlarmSendQueue> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MAlarmSendQueue, c ->
            c.map(id).toProperty("id")
            .map(alarmId).toProperty("alarmId")
            .map(groupName).toProperty("groupName")
            .map(sendStatus).toProperty("sendStatus")
            .map(sendType).toProperty("sendType")
            .map(sendParams).toProperty("sendParams")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(filePath).toProperty("filePath")
            .map(expcetion).toProperty("expcetion")
            .map(sendTime).toProperty("sendTime")
            .map(createTime).toProperty("createTime")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.376+08:00", comments="Source Table: m_alarm_send_queue")
    default int insertSelective(MAlarmSendQueue record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarmSendQueue, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(alarmId).toPropertyWhenPresent("alarmId", record::getAlarmId)
            .map(groupName).toPropertyWhenPresent("groupName", record::getGroupName)
            .map(sendStatus).toPropertyWhenPresent("sendStatus", record::getSendStatus)
            .map(sendType).toPropertyWhenPresent("sendType", record::getSendType)
            .map(sendParams).toPropertyWhenPresent("sendParams", record::getSendParams)
            .map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(content).toPropertyWhenPresent("content", record::getContent)
            .map(filePath).toPropertyWhenPresent("filePath", record::getFilePath)
            .map(expcetion).toPropertyWhenPresent("expcetion", record::getExpcetion)
            .map(sendTime).toPropertyWhenPresent("sendTime", record::getSendTime)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(remark).toPropertyWhenPresent("remark", record::getRemark)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default Optional<MAlarmSendQueue> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default List<MAlarmSendQueue> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default List<MAlarmSendQueue> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default Optional<MAlarmSendQueue> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MAlarmSendQueue, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    static UpdateDSL<UpdateModel> updateAllColumns(MAlarmSendQueue record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(alarmId).equalTo(record::getAlarmId)
                .set(groupName).equalTo(record::getGroupName)
                .set(sendStatus).equalTo(record::getSendStatus)
                .set(sendType).equalTo(record::getSendType)
                .set(sendParams).equalTo(record::getSendParams)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(filePath).equalTo(record::getFilePath)
                .set(expcetion).equalTo(record::getExpcetion)
                .set(sendTime).equalTo(record::getSendTime)
                .set(createTime).equalTo(record::getCreateTime)
                .set(remark).equalTo(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MAlarmSendQueue record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(alarmId).equalToWhenPresent(record::getAlarmId)
                .set(groupName).equalToWhenPresent(record::getGroupName)
                .set(sendStatus).equalToWhenPresent(record::getSendStatus)
                .set(sendType).equalToWhenPresent(record::getSendType)
                .set(sendParams).equalToWhenPresent(record::getSendParams)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(filePath).equalToWhenPresent(record::getFilePath)
                .set(expcetion).equalToWhenPresent(record::getExpcetion)
                .set(sendTime).equalToWhenPresent(record::getSendTime)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(remark).equalToWhenPresent(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.377+08:00", comments="Source Table: m_alarm_send_queue")
    default int updateByPrimaryKey(MAlarmSendQueue record) {
        return update(c ->
            c.set(alarmId).equalTo(record::getAlarmId)
            .set(groupName).equalTo(record::getGroupName)
            .set(sendStatus).equalTo(record::getSendStatus)
            .set(sendType).equalTo(record::getSendType)
            .set(sendParams).equalTo(record::getSendParams)
            .set(title).equalTo(record::getTitle)
            .set(content).equalTo(record::getContent)
            .set(filePath).equalTo(record::getFilePath)
            .set(expcetion).equalTo(record::getExpcetion)
            .set(sendTime).equalTo(record::getSendTime)
            .set(createTime).equalTo(record::getCreateTime)
            .set(remark).equalTo(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.378+08:00", comments="Source Table: m_alarm_send_queue")
    default int updateByPrimaryKeySelective(MAlarmSendQueue record) {
        return update(c ->
            c.set(alarmId).equalToWhenPresent(record::getAlarmId)
            .set(groupName).equalToWhenPresent(record::getGroupName)
            .set(sendStatus).equalToWhenPresent(record::getSendStatus)
            .set(sendType).equalToWhenPresent(record::getSendType)
            .set(sendParams).equalToWhenPresent(record::getSendParams)
            .set(title).equalToWhenPresent(record::getTitle)
            .set(content).equalToWhenPresent(record::getContent)
            .set(filePath).equalToWhenPresent(record::getFilePath)
            .set(expcetion).equalToWhenPresent(record::getExpcetion)
            .set(sendTime).equalToWhenPresent(record::getSendTime)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(remark).equalToWhenPresent(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }

    default int insertMultipleByAlarm(Collection<MAlarmSendQueue> updateList) {
    	return MyBatis3Utils.insertMultiple(this::insertMultiple, updateList, MAlarmSendQueue, c ->
        c.map(alarmId).toProperty("alarmId")
        .map(groupName).toProperty("groupName")
        .map(sendStatus).toProperty("sendStatus")
        .map(sendType).toProperty("sendType")
        .map(sendParams).toProperty("sendParams")
        .map(title).toProperty("title")
        .map(content).toProperty("content")
        .map(filePath).toProperty("filePath")
        .map(expcetion).toProperty("expcetion")
        .map(sendTime).toProperty("sendTime")
        .map(createTime).toProperty("createTime")
        .map(remark).toProperty("remark")
    );
    }

	default List<MAlarmSendQueue> selectNeedSend(String sendStatus_){
		return select(c->c.where(sendStatus,isEqualTo(sendStatus_)));
	}
}