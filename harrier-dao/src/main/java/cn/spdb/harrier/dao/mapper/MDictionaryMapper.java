package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.MAlarmConfig;
import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.code;
import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.job;
import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.platform;
import static cn.spdb.harrier.dao.mapper.MAlarmConfigDynamicSqlSupport.systems;
import static cn.spdb.harrier.dao.mapper.MDictionaryDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.MDictionary;

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
public interface MDictionaryMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.731+08:00", comments = "Source Table: m_dictionary")
	BasicColumn[] selectList = BasicColumn.columnList(id, dicCode, dicKey, dicName, dicValue, dicDesc);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.708+08:00", comments = "Source Table: m_dictionary")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.71+08:00", comments = "Source Table: m_dictionary")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.711+08:00", comments = "Source Table: m_dictionary")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<MDictionary> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.713+08:00", comments = "Source Table: m_dictionary")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<MDictionary> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.714+08:00", comments = "Source Table: m_dictionary")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("MDictionaryResult")
	Optional<MDictionary> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.716+08:00", comments = "Source Table: m_dictionary")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "MDictionaryResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "dic_code", property = "dicCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dic_key", property = "dicKey", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dic_name", property = "dicName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dic_value", property = "dicValue", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dic_desc", property = "dicDesc", jdbcType = JdbcType.VARCHAR) })
	List<MDictionary> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.72+08:00", comments = "Source Table: m_dictionary")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.721+08:00", comments = "Source Table: m_dictionary")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.722+08:00", comments = "Source Table: m_dictionary")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.723+08:00", comments = "Source Table: m_dictionary")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.724+08:00", comments = "Source Table: m_dictionary")
	default int insert(MDictionary record) {
		return MyBatis3Utils.insert(this::insert, record, MDictionary,
				c -> c.map(id).toProperty("id").map(dicCode).toProperty("dicCode").map(dicKey).toProperty("dicKey")
						.map(dicName).toProperty("dicName").map(dicValue).toProperty("dicValue").map(dicDesc)
						.toProperty("dicDesc"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.727+08:00", comments = "Source Table: m_dictionary")
	default int insertMultiple(Collection<MDictionary> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, MDictionary,
				c -> c.map(id).toProperty("id").map(dicCode).toProperty("dicCode").map(dicKey).toProperty("dicKey")
						.map(dicName).toProperty("dicName").map(dicValue).toProperty("dicValue").map(dicDesc)
						.toProperty("dicDesc"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.728+08:00", comments = "Source Table: m_dictionary")
	default int insertSelective(MDictionary record) {
		return MyBatis3Utils.insert(this::insert, record, MDictionary,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(dicCode)
						.toPropertyWhenPresent("dicCode", record::getDicCode).map(dicKey)
						.toPropertyWhenPresent("dicKey", record::getDicKey).map(dicName)
						.toPropertyWhenPresent("dicName", record::getDicName).map(dicValue)
						.toPropertyWhenPresent("dicValue", record::getDicValue).map(dicDesc)
						.toPropertyWhenPresent("dicDesc", record::getDicDesc));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.733+08:00", comments = "Source Table: m_dictionary")
	default Optional<MDictionary> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.734+08:00", comments = "Source Table: m_dictionary")
	default List<MDictionary> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.735+08:00", comments = "Source Table: m_dictionary")
	default List<MDictionary> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.736+08:00", comments = "Source Table: m_dictionary")
	default Optional<MDictionary> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.737+08:00", comments = "Source Table: m_dictionary")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, MDictionary, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.738+08:00", comments = "Source Table: m_dictionary")
	static UpdateDSL<UpdateModel> updateAllColumns(MDictionary record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(dicCode).equalTo(record::getDicCode).set(dicKey)
				.equalTo(record::getDicKey).set(dicName).equalTo(record::getDicName).set(dicValue)
				.equalTo(record::getDicValue).set(dicDesc).equalTo(record::getDicDesc);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.739+08:00", comments = "Source Table: m_dictionary")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(MDictionary record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(dicCode).equalToWhenPresent(record::getDicCode)
				.set(dicKey).equalToWhenPresent(record::getDicKey).set(dicName).equalToWhenPresent(record::getDicName)
				.set(dicValue).equalToWhenPresent(record::getDicValue).set(dicDesc)
				.equalToWhenPresent(record::getDicDesc);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.741+08:00", comments = "Source Table: m_dictionary")
	default int updateByPrimaryKey(MDictionary record) {
		return update(c -> c.set(dicCode).equalTo(record::getDicCode).set(dicKey).equalTo(record::getDicKey)
				.set(dicName).equalTo(record::getDicName).set(dicValue).equalTo(record::getDicValue).set(dicDesc)
				.equalTo(record::getDicDesc).where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-12T15:33:57.742+08:00", comments = "Source Table: m_dictionary")
	default int updateByPrimaryKeySelective(MDictionary record) {
		return update(c -> c.set(dicCode).equalToWhenPresent(record::getDicCode).set(dicKey)
				.equalToWhenPresent(record::getDicKey).set(dicName).equalToWhenPresent(record::getDicName).set(dicValue)
				.equalToWhenPresent(record::getDicValue).set(dicDesc).equalToWhenPresent(record::getDicDesc)
				.where(id, isEqualTo(record::getId)));
	}

	default MDictionary selectMDictionary(String code, String key) {
		Optional<MDictionary> optional = selectOne(c -> c.where(dicCode, isEqualTo(code), and(dicKey, isEqualTo(key))));
		return optional.get();
	}
	
	@ResultMap("MDictionaryResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<MDictionary> selectManys(SelectStatementProvider selectStatement,Page<MDictionary> page);

	default Page<MDictionary> search(Page<MDictionary> page, String dicCode_, String dicKey_) {
		SelectStatementProvider sql = SqlBuilder.select(selectList).from(MDictionary)
				.where(MDictionary.dicCode,isEqualToWhenPresent(dicCode_))
				.and(MDictionary.dicKey, isEqualToWhenPresent(dicKey_))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<MDictionary> list=selectManys(sql, page);
		page.setRecords(list);
		return page;
	}
	
	default int delete(Long[] ids) {
		return delete(c -> c.where(id, isIn(ids)));
	}
	
	default List<MDictionary> selectDicCode(String dicCode_, String dicKey_) {
		return select(c->c.where(dicCode, isEqualToWhenPresent(dicCode_))
				.and(dicKey, isEqualToWhenPresent(dicKey_)));
	}
	
	
	
	default Optional<MDictionary> selectOneByKey(String dickey_){
		return selectOne(c -> c.where(dicKey, isEqualTo(dickey_)));
		
	};
}