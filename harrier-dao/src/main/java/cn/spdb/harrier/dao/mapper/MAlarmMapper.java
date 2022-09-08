package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MAlarmDynamicSqlSupport.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
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

import cn.spdb.harrier.dao.entity.MAlarm;

@Mapper
public interface MAlarmMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, job, code, alarmType, alarmLevel, times, alarmStatus, srcContent, srcParam, title, content, noticeGroupName, noticeCount, noticeTimes, noticeCyce, noticeSendTime, operationType, operationTime, operationUser, remark);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source Table: m_alarm")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.387+08:00", comments="Source Table: m_alarm")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<MAlarm> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<MAlarm> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MAlarmResult")
    Optional<MAlarm> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MAlarmResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
        @Result(column="systems", property="systems", jdbcType=JdbcType.VARCHAR),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_type", property="alarmType", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_level", property="alarmLevel", jdbcType=JdbcType.VARCHAR),
        @Result(column="times", property="times", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="alarm_status", property="alarmStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_content", property="srcContent", jdbcType=JdbcType.VARCHAR),
        @Result(column="src_param", property="srcParam", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="notice_group_name", property="noticeGroupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="notice_count", property="noticeCount", jdbcType=JdbcType.INTEGER),
        @Result(column="notice_times", property="noticeTimes", jdbcType=JdbcType.INTEGER),
        @Result(column="notice_cyce", property="noticeCyce", jdbcType=JdbcType.INTEGER),
        @Result(column="notice_send_time", property="noticeSendTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="operation_type", property="operationType", jdbcType=JdbcType.VARCHAR),
        @Result(column="operation_time", property="operationTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="operation_user", property="operationUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<MAlarm> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int insert(MAlarm record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarm, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(code).toProperty("code")
            .map(alarmType).toProperty("alarmType")
            .map(alarmLevel).toProperty("alarmLevel")
            .map(times).toProperty("times")
            .map(alarmStatus).toProperty("alarmStatus")
            .map(srcContent).toProperty("srcContent")
            .map(srcParam).toProperty("srcParam")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(noticeGroupName).toProperty("noticeGroupName")
            .map(noticeCount).toProperty("noticeCount")
            .map(noticeTimes).toProperty("noticeTimes")
            .map(noticeCyce).toProperty("noticeCyce")
            .map(noticeSendTime).toProperty("noticeSendTime")
            .map(operationType).toProperty("operationType")
            .map(operationTime).toProperty("operationTime")
            .map(operationUser).toProperty("operationUser")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int insertMultiple(Collection<MAlarm> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MAlarm, c ->
            c.map(id).toProperty("id")
            .map(platform).toProperty("platform")
            .map(systems).toProperty("systems")
            .map(job).toProperty("job")
            .map(code).toProperty("code")
            .map(alarmType).toProperty("alarmType")
            .map(alarmLevel).toProperty("alarmLevel")
            .map(times).toProperty("times")
            .map(alarmStatus).toProperty("alarmStatus")
            .map(srcContent).toProperty("srcContent")
            .map(srcParam).toProperty("srcParam")
            .map(title).toProperty("title")
            .map(content).toProperty("content")
            .map(noticeGroupName).toProperty("noticeGroupName")
            .map(noticeCount).toProperty("noticeCount")
            .map(noticeTimes).toProperty("noticeTimes")
            .map(noticeCyce).toProperty("noticeCyce")
            .map(noticeSendTime).toProperty("noticeSendTime")
            .map(operationType).toProperty("operationType")
            .map(operationTime).toProperty("operationTime")
            .map(operationUser).toProperty("operationUser")
            .map(remark).toProperty("remark")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int insertSelective(MAlarm record) {
        return MyBatis3Utils.insert(this::insert, record, MAlarm, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(platform).toPropertyWhenPresent("platform", record::getPlatform)
            .map(systems).toPropertyWhenPresent("systems", record::getSystems)
            .map(job).toPropertyWhenPresent("job", record::getJob)
            .map(code).toPropertyWhenPresent("code", record::getCode)
            .map(alarmType).toPropertyWhenPresent("alarmType", record::getAlarmType)
            .map(alarmLevel).toPropertyWhenPresent("alarmLevel", record::getAlarmLevel)
            .map(times).toPropertyWhenPresent("times", record::getTimes)
            .map(alarmStatus).toPropertyWhenPresent("alarmStatus", record::getAlarmStatus)
            .map(srcContent).toPropertyWhenPresent("srcContent", record::getSrcContent)
            .map(srcParam).toPropertyWhenPresent("srcParam", record::getSrcParam)
            .map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(content).toPropertyWhenPresent("content", record::getContent)
            .map(noticeGroupName).toPropertyWhenPresent("noticeGroupName", record::getNoticeGroupName)
            .map(noticeCount).toPropertyWhenPresent("noticeCount", record::getNoticeCount)
            .map(noticeTimes).toPropertyWhenPresent("noticeTimes", record::getNoticeTimes)
            .map(noticeCyce).toPropertyWhenPresent("noticeCyce", record::getNoticeCyce)
            .map(noticeSendTime).toPropertyWhenPresent("noticeSendTime", record::getNoticeSendTime)
            .map(operationType).toPropertyWhenPresent("operationType", record::getOperationType)
            .map(operationTime).toPropertyWhenPresent("operationTime", record::getOperationTime)
            .map(operationUser).toPropertyWhenPresent("operationUser", record::getOperationUser)
            .map(remark).toPropertyWhenPresent("remark", record::getRemark)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default Optional<MAlarm> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default List<MAlarm> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default List<MAlarm> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default Optional<MAlarm> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.388+08:00", comments="Source Table: m_alarm")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MAlarm, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.389+08:00", comments="Source Table: m_alarm")
    static UpdateDSL<UpdateModel> updateAllColumns(MAlarm record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(platform).equalTo(record::getPlatform)
                .set(systems).equalTo(record::getSystems)
                .set(job).equalTo(record::getJob)
                .set(code).equalTo(record::getCode)
                .set(alarmType).equalTo(record::getAlarmType)
                .set(alarmLevel).equalTo(record::getAlarmLevel)
                .set(times).equalTo(record::getTimes)
                .set(alarmStatus).equalTo(record::getAlarmStatus)
                .set(srcContent).equalTo(record::getSrcContent)
                .set(srcParam).equalTo(record::getSrcParam)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(noticeGroupName).equalTo(record::getNoticeGroupName)
                .set(noticeCount).equalTo(record::getNoticeCount)
                .set(noticeTimes).equalTo(record::getNoticeTimes)
                .set(noticeCyce).equalTo(record::getNoticeCyce)
                .set(noticeSendTime).equalTo(record::getNoticeSendTime)
                .set(operationType).equalTo(record::getOperationType)
                .set(operationTime).equalTo(record::getOperationTime)
                .set(operationUser).equalTo(record::getOperationUser)
                .set(remark).equalTo(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.389+08:00", comments="Source Table: m_alarm")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(MAlarm record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(platform).equalToWhenPresent(record::getPlatform)
                .set(systems).equalToWhenPresent(record::getSystems)
                .set(job).equalToWhenPresent(record::getJob)
                .set(code).equalToWhenPresent(record::getCode)
                .set(alarmType).equalToWhenPresent(record::getAlarmType)
                .set(alarmLevel).equalToWhenPresent(record::getAlarmLevel)
                .set(times).equalToWhenPresent(record::getTimes)
                .set(alarmStatus).equalToWhenPresent(record::getAlarmStatus)
                .set(srcContent).equalToWhenPresent(record::getSrcContent)
                .set(srcParam).equalToWhenPresent(record::getSrcParam)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(noticeGroupName).equalToWhenPresent(record::getNoticeGroupName)
                .set(noticeCount).equalToWhenPresent(record::getNoticeCount)
                .set(noticeTimes).equalToWhenPresent(record::getNoticeTimes)
                .set(noticeCyce).equalToWhenPresent(record::getNoticeCyce)
                .set(noticeSendTime).equalToWhenPresent(record::getNoticeSendTime)
                .set(operationType).equalToWhenPresent(record::getOperationType)
                .set(operationTime).equalToWhenPresent(record::getOperationTime)
                .set(operationUser).equalToWhenPresent(record::getOperationUser)
                .set(remark).equalToWhenPresent(record::getRemark);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.389+08:00", comments="Source Table: m_alarm")
    default int updateByPrimaryKey(MAlarm record) {
        return update(c ->
            c.set(platform).equalTo(record::getPlatform)
            .set(systems).equalTo(record::getSystems)
            .set(job).equalTo(record::getJob)
            .set(code).equalTo(record::getCode)
            .set(alarmType).equalTo(record::getAlarmType)
            .set(alarmLevel).equalTo(record::getAlarmLevel)
            .set(times).equalTo(record::getTimes)
            .set(alarmStatus).equalTo(record::getAlarmStatus)
            .set(srcContent).equalTo(record::getSrcContent)
            .set(srcParam).equalTo(record::getSrcParam)
            .set(title).equalTo(record::getTitle)
            .set(content).equalTo(record::getContent)
            .set(noticeGroupName).equalTo(record::getNoticeGroupName)
            .set(noticeCount).equalTo(record::getNoticeCount)
            .set(noticeTimes).equalTo(record::getNoticeTimes)
            .set(noticeCyce).equalTo(record::getNoticeCyce)
            .set(noticeSendTime).equalTo(record::getNoticeSendTime)
            .set(operationType).equalTo(record::getOperationType)
            .set(operationTime).equalTo(record::getOperationTime)
            .set(operationUser).equalTo(record::getOperationUser)
            .set(remark).equalTo(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-14T14:51:28.389+08:00", comments="Source Table: m_alarm")
    default int updateByPrimaryKeySelective(MAlarm record) {
        return update(c ->
            c.set(platform).equalToWhenPresent(record::getPlatform)
            .set(systems).equalToWhenPresent(record::getSystems)
            .set(job).equalToWhenPresent(record::getJob)
            .set(code).equalToWhenPresent(record::getCode)
            .set(alarmType).equalToWhenPresent(record::getAlarmType)
            .set(alarmLevel).equalToWhenPresent(record::getAlarmLevel)
            .set(times).equalToWhenPresent(record::getTimes)
            .set(alarmStatus).equalToWhenPresent(record::getAlarmStatus)
            .set(srcContent).equalToWhenPresent(record::getSrcContent)
            .set(srcParam).equalToWhenPresent(record::getSrcParam)
            .set(title).equalToWhenPresent(record::getTitle)
            .set(content).equalToWhenPresent(record::getContent)
            .set(noticeGroupName).equalToWhenPresent(record::getNoticeGroupName)
            .set(noticeCount).equalToWhenPresent(record::getNoticeCount)
            .set(noticeTimes).equalToWhenPresent(record::getNoticeTimes)
            .set(noticeCyce).equalToWhenPresent(record::getNoticeCyce)
            .set(noticeSendTime).equalToWhenPresent(record::getNoticeSendTime)
            .set(operationType).equalToWhenPresent(record::getOperationType)
            .set(operationTime).equalToWhenPresent(record::getOperationTime)
            .set(operationUser).equalToWhenPresent(record::getOperationUser)
            .set(remark).equalToWhenPresent(record::getRemark)
            .where(id, isEqualTo(record::getId))
        );
    }


	default List<MAlarm> selectSendAlarmByDb(String ... statusList){
		return select(c->c.where()
				.and(alarmStatus, isIn(statusList))
				.and(noticeCount, isLessThan(noticeTimes))
				.and(constant("adddate(" + noticeSendTime.name() + ",INTERVAL "
				+ noticeCyce.name() + " MINUTE)"), isLessThan(LocalDateTime.now()))
				);
	}

	@ResultMap("MAlarmResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MAlarm> selectManyPage(SelectStatementProvider selectStatement, Page<MAlarm> page);

	
	default Page<MAlarm> search(Page<MAlarm> page, String platform_, String system_, String job_,String code_ ,String status_,
			LocalDate localDate){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(MAlarm)
				.where()
				.and(platform, isEqualToWhenPresent(platform_))
				.and(systems, isEqualToWhenPresent(system_))
				.and(job, isEqualToWhenPresent(job_))
				.and(code, isEqualToWhenPresent(code_))
				.and(alarmStatus, isEqualToWhenPresent(status_))
				.and(times, isBetweenWhenPresent(localDate.atTime(0, 0)).and(localDate.plusDays(1).atTime(0, 0)))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MAlarm> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}
}