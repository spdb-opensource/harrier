package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MUserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MUser;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
public interface MUserMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	BasicColumn[] selectList = BasicColumn.columnList(userId, userCode, userName, userPwd, userEmail, userPhone, empId,
			createTime, createUser, updateTime, updateUser, isExpired, isLocked, isEnable, isSingle, isModifyPwd,
			pwdWrongCount, remark);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<MUser> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<MUser> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("MUserResult")
	Optional<MUser> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "MUserResult", value = {
			@Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "user_code", property = "userCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "user_pwd", property = "userPwd", jdbcType = JdbcType.VARCHAR),
			@Result(column = "user_email", property = "userEmail", jdbcType = JdbcType.VARCHAR),
			@Result(column = "user_phone", property = "userPhone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "emp_id", property = "empId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "create_user", property = "createUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "update_user", property = "updateUser", jdbcType = JdbcType.VARCHAR),
			@Result(column = "is_expired", property = "isExpired", jdbcType = JdbcType.BIT),
			@Result(column = "is_locked", property = "isLocked", jdbcType = JdbcType.BIT),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "is_single", property = "isSingle", jdbcType = JdbcType.BIT),
			@Result(column = "is_modify_pwd", property = "isModifyPwd", jdbcType = JdbcType.BIT),
			@Result(column = "pwd_wrong_count", property = "pwdWrongCount", jdbcType = JdbcType.INTEGER),
			@Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR) })
	List<MUser> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int deleteByPrimaryKey(Long userId_) {
		return delete(c -> c.where(userId, isEqualTo(userId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int insert(MUser record) {
		return MyBatis3Utils.insert(this::insert, record, MUser, c -> c.map(userId).toProperty("userId").map(userCode)
				.toProperty("userCode").map(userName).toProperty("userName").map(userPwd).toProperty("userPwd")
				.map(userEmail).toProperty("userEmail").map(userPhone).toProperty("userPhone").map(empId)
				.toProperty("empId").map(createTime).toProperty("createTime").map(createUser).toProperty("createUser")
				.map(updateTime).toProperty("updateTime").map(updateUser).toProperty("updateUser").map(isExpired)
				.toProperty("isExpired").map(isLocked).toProperty("isLocked").map(isEnable).toProperty("isEnable")
				.map(isSingle).toProperty("isSingle").map(isModifyPwd).toProperty("isModifyPwd").map(pwdWrongCount)
				.toProperty("pwdWrongCount").map(remark).toProperty("remark"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int insertMultiple(Collection<MUser> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MUser,
				c -> c.map(userId).toProperty("userId").map(userCode).toProperty("userCode").map(userName)
						.toProperty("userName").map(userPwd).toProperty("userPwd").map(userEmail)
						.toProperty("userEmail").map(userPhone).toProperty("userPhone").map(empId).toProperty("empId")
						.map(createTime).toProperty("createTime").map(createUser).toProperty("createUser")
						.map(updateTime).toProperty("updateTime").map(updateUser).toProperty("updateUser")
						.map(isExpired).toProperty("isExpired").map(isLocked).toProperty("isLocked").map(isEnable)
						.toProperty("isEnable").map(isSingle).toProperty("isSingle").map(isModifyPwd)
						.toProperty("isModifyPwd").map(pwdWrongCount).toProperty("pwdWrongCount").map(remark)
						.toProperty("remark"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int insertSelective(MUser record) {
		return MyBatis3Utils.insert(this::insert, record, MUser,
				c -> c.map(userId).toPropertyWhenPresent("userId", record::getUserId).map(userCode)
						.toPropertyWhenPresent("userCode", record::getUserCode).map(userName)
						.toPropertyWhenPresent("userName", record::getUserName).map(userPwd)
						.toPropertyWhenPresent("userPwd", record::getUserPwd).map(userEmail)
						.toPropertyWhenPresent("userEmail", record::getUserEmail).map(userPhone)
						.toPropertyWhenPresent("userPhone", record::getUserPhone).map(empId)
						.toPropertyWhenPresent("empId", record::getEmpId).map(createTime)
						.toPropertyWhenPresent("createTime", record::getCreateTime).map(createUser)
						.toPropertyWhenPresent("createUser", record::getCreateUser).map(updateTime)
						.toPropertyWhenPresent("updateTime", record::getUpdateTime).map(updateUser)
						.toPropertyWhenPresent("updateUser", record::getUpdateUser).map(isExpired)
						.toPropertyWhenPresent("isExpired", record::getIsExpired).map(isLocked)
						.toPropertyWhenPresent("isLocked", record::getIsLocked).map(isEnable)
						.toPropertyWhenPresent("isEnable", record::getIsEnable).map(isSingle)
						.toPropertyWhenPresent("isSingle", record::getIsSingle).map(isModifyPwd)
						.toPropertyWhenPresent("isModifyPwd", record::getIsModifyPwd).map(pwdWrongCount)
						.toPropertyWhenPresent("pwdWrongCount", record::getPwdWrongCount).map(remark)
						.toPropertyWhenPresent("remark", record::getRemark));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default Optional<MUser> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default List<MUser> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default List<MUser> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default Optional<MUser> selectByPrimaryKey(Long userId_) {
		return selectOne(c -> c.where(userId, isEqualTo(userId_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, MUser, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	static UpdateDSL<UpdateModel> updateAllColumns(MUser record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(userId).equalTo(record::getUserId).set(userCode).equalTo(record::getUserCode).set(userName)
				.equalTo(record::getUserName).set(userPwd).equalTo(record::getUserPwd).set(userEmail)
				.equalTo(record::getUserEmail).set(userPhone).equalTo(record::getUserPhone).set(empId)
				.equalTo(record::getEmpId).set(createTime).equalTo(record::getCreateTime).set(createUser)
				.equalTo(record::getCreateUser).set(updateTime).equalTo(record::getUpdateTime).set(updateUser)
				.equalTo(record::getUpdateUser).set(isExpired).equalTo(record::getIsExpired).set(isLocked)
				.equalTo(record::getIsLocked).set(isEnable).equalTo(record::getIsEnable).set(isSingle)
				.equalTo(record::getIsSingle).set(isModifyPwd).equalTo(record::getIsModifyPwd).set(pwdWrongCount)
				.equalTo(record::getPwdWrongCount).set(remark).equalTo(record::getRemark);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(MUser record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(userId).equalToWhenPresent(record::getUserId).set(userCode)
				.equalToWhenPresent(record::getUserCode).set(userName).equalToWhenPresent(record::getUserName)
				.set(userPwd).equalToWhenPresent(record::getUserPwd).set(userEmail)
				.equalToWhenPresent(record::getUserEmail).set(userPhone).equalToWhenPresent(record::getUserPhone)
				.set(empId).equalToWhenPresent(record::getEmpId).set(createTime)
				.equalToWhenPresent(record::getCreateTime).set(createUser).equalToWhenPresent(record::getCreateUser)
				.set(updateTime).equalToWhenPresent(record::getUpdateTime).set(updateUser)
				.equalToWhenPresent(record::getUpdateUser).set(isExpired).equalToWhenPresent(record::getIsExpired)
				.set(isLocked).equalToWhenPresent(record::getIsLocked).set(isEnable)
				.equalToWhenPresent(record::getIsEnable).set(isSingle).equalToWhenPresent(record::getIsSingle)
				.set(isModifyPwd).equalToWhenPresent(record::getIsModifyPwd).set(pwdWrongCount)
				.equalToWhenPresent(record::getPwdWrongCount).set(remark).equalToWhenPresent(record::getRemark);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int updateByPrimaryKey(MUser record) {
		return update(c -> c.set(userCode).equalTo(record::getUserCode).set(userName).equalTo(record::getUserName)
				.set(userPwd).equalTo(record::getUserPwd).set(userEmail).equalTo(record::getUserEmail).set(userPhone)
				.equalTo(record::getUserPhone).set(empId).equalTo(record::getEmpId).set(createTime)
				.equalTo(record::getCreateTime).set(createUser).equalTo(record::getCreateUser).set(updateTime)
				.equalTo(record::getUpdateTime).set(updateUser).equalTo(record::getUpdateUser).set(isExpired)
				.equalTo(record::getIsExpired).set(isLocked).equalTo(record::getIsLocked).set(isEnable)
				.equalTo(record::getIsEnable).set(isSingle).equalTo(record::getIsSingle).set(isModifyPwd)
				.equalTo(record::getIsModifyPwd).set(pwdWrongCount).equalTo(record::getPwdWrongCount).set(remark)
				.equalTo(record::getRemark).where(userId, isEqualTo(record::getUserId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.534+08:00", comments = "Source Table: m_user")
	default int updateByPrimaryKeySelective(MUser record) {
		return update(c -> c.set(userCode).equalToWhenPresent(record::getUserCode).set(userName)
				.equalToWhenPresent(record::getUserName).set(userPwd).equalToWhenPresent(record::getUserPwd)
				.set(userEmail).equalToWhenPresent(record::getUserEmail).set(userPhone)
				.equalToWhenPresent(record::getUserPhone).set(empId).equalToWhenPresent(record::getEmpId)
				.set(createTime).equalToWhenPresent(record::getCreateTime).set(createUser)
				.equalToWhenPresent(record::getCreateUser).set(updateTime).equalToWhenPresent(record::getUpdateTime)
				.set(updateUser).equalToWhenPresent(record::getUpdateUser).set(isExpired)
				.equalToWhenPresent(record::getIsExpired).set(isLocked).equalToWhenPresent(record::getIsLocked)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(isSingle)
				.equalToWhenPresent(record::getIsSingle).set(isModifyPwd).equalToWhenPresent(record::getIsModifyPwd)
				.set(pwdWrongCount).equalToWhenPresent(record::getPwdWrongCount).set(remark)
				.equalToWhenPresent(record::getRemark).where(userId, isEqualTo(record::getUserId)));
	}

	default Optional<MUser> selectByUserCode(String usercode_) {
		return selectOne(c -> c.where(userCode, isEqualTo(usercode_)));
	}

	@ResultMap("MUserResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MUser> selectManyPage(SelectStatementProvider selectStatement,Page<MUser> page);

	default Page<MUser> search(Page<MUser> page, String userName_, Boolean isEnbale_) {
		SelectStatementProvider sql = SqlBuilder.select(selectList).from(MUser)
				.where(userName,isEqualToWhenPresent(userName_))
				.and(isEnable, isEqualToWhenPresent(isEnbale_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MUser> records = selectManyPage(sql,page);
		page.setRecords(records);
		return page;
	}
	
	default Page<MUser> search(Page<MUser> page) {
		SelectStatementProvider sql = SqlBuilder.select(selectList).from(MUser)
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MUser> records = selectManyPage(sql,page);
		page.setRecords(records);
		return page;
	}
	
	
	@ResultMap("MUserResult")
	@Select("select * from m_user where user_name=#{user_name}")
	List<MUser> select(Page<MUser> page,@Param("user_name")String user_name);

	
	default int delete(Long[] userIds) {
		return delete(c->c.where(userId, isIn(userIds)));
	}

	default List<MUser> selectByUserName(String userName_){
		return select(c->c.where(userName, isEqualTo(userName_)));
	}
	
	default int updatePwd(Long userId_, String pwd_) {
		return update(c -> c.set(userPwd).equalTo(pwd_)
				.where(userId, isEqualTo(userId_)));
	}
	
	BasicColumn[] selectUser = BasicColumn.columnList(userId, userCode, userName, userEmail, userPhone, empId,
			createTime, createUser, updateTime, updateUser, remark);
	default Optional<MUser> selectUser(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectUser , MUser, completer);
	}
	default Optional<MUser> selectUserToCode(String usercode_) {
		return selectUser(c -> c.where(userCode, isEqualTo(usercode_))
				.and(isEnable, isEqualTo(true)));
	}
	
	default int updateEnable(Long[] userIds, Boolean isEnable_) {
		return update(c -> c.set(isEnable).equalTo(isEnable_)
				.where(userId, isIn(userIds)));
	}
	
	default int resetPwd(Long[] userIds, String password) {
		return update(c -> c.set(userPwd).equalTo(password)
				.where(userId, isIn(userIds)));
	}
}