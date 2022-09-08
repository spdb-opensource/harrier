package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.des;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.id;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.maxRunJob;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.platform;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.select;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.selectPro;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.serverRoleNameGroup;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.systems;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.udsSystem;
import static cn.spdb.harrier.dao.mapper.UdsSystemDynamicSqlSupport.usePlatform;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

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

import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsSystem;

@Mapper
public interface UdsSystemMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	BasicColumn[] selectList = BasicColumn.columnList(id, platform, systems, maxRunJob, select, selectPro, usePlatform,
			serverRoleNameGroup, des);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<UdsSystem> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<UdsSystem> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UdsSystemResult")
	Optional<UdsSystem> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UdsSystemResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "platform", property = "platform", jdbcType = JdbcType.VARCHAR),
			@Result(column = "systems", property = "systems", jdbcType = JdbcType.VARCHAR),
			@Result(column = "max_run_job", property = "maxRunJob", jdbcType = JdbcType.SMALLINT),
			@Result(column = "selects", property = "selects", jdbcType = JdbcType.TINYINT),
			@Result(column = "selects_pro", property = "selectsPro", jdbcType = JdbcType.VARCHAR),
			@Result(column = "use_platform", property = "usePlatform", jdbcType = JdbcType.BIT),
			@Result(column = "server_role_name_group", property = "serverRoleNameGroup", jdbcType = JdbcType.VARCHAR),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR) })
	List<UdsSystem> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.247+08:00", comments = "Source Table: uds_system")
	default int deleteByPrimaryKey(Integer id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int insert(UdsSystem record) {
		return MyBatis3Utils.insert(this::insert, record, udsSystem,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(maxRunJob).toProperty("maxRunJob").map(select).toProperty("select").map(selectPro)
						.toProperty("selectPro").map(usePlatform).toProperty("usePlatform").map(serverRoleNameGroup)
						.toProperty("serverRoleNameGroup").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int insertMultiple(Collection<UdsSystem> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsSystem,
				c -> c.map(id).toProperty("id").map(platform).toProperty("platform").map(systems).toProperty("systems")
						.map(maxRunJob).toProperty("maxRunJob").map(select).toProperty("select").map(selectPro)
						.toProperty("selectPro").map(usePlatform).toProperty("usePlatform").map(serverRoleNameGroup)
						.toProperty("serverRoleNameGroup").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int insertSelective(UdsSystem record) {
		return MyBatis3Utils.insert(this::insert, record, udsSystem,
				c -> c.map(id).toPropertyWhenPresent("id", record::getId).map(platform)
						.toPropertyWhenPresent("platform", record::getPlatform).map(systems)
						.toPropertyWhenPresent("systems", record::getSystems).map(maxRunJob)
						.toPropertyWhenPresent("maxRunJob", record::getMaxRunJob).map(select)
						.toPropertyWhenPresent("select", record::getSelect).map(selectPro)
						.toPropertyWhenPresent("selectPro", record::getSelectPro).map(usePlatform)
						.toPropertyWhenPresent("usePlatform", record::getUsePlatform).map(serverRoleNameGroup)
						.toPropertyWhenPresent("serverRoleNameGroup", record::getServerRoleNameGroup).map(des)
						.toPropertyWhenPresent("des", record::getDes));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default Optional<UdsSystem> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default List<UdsSystem> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default List<UdsSystem> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default Optional<UdsSystem> selectByPrimaryKey(Integer id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}
	
	default Optional<UdsSystem> selectByPlatAndSys(String platform_, String systems_) {
		return selectOne(c -> c.where(platform, isEqualTo(platform_)).and(systems, isEqualTo(systems_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, udsSystem, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	static UpdateDSL<UpdateModel> updateAllColumns(UdsSystem record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(platform).equalTo(record::getPlatform).set(systems)
				.equalTo(record::getSystems).set(maxRunJob).equalTo(record::getMaxRunJob).set(select)
				.equalTo(record::getSelect).set(selectPro).equalTo(record::getSelectPro).set(usePlatform)
				.equalTo(record::getUsePlatform).set(serverRoleNameGroup).equalTo(record::getServerRoleNameGroup)
				.set(des).equalTo(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsSystem record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(platform).equalToWhenPresent(record::getPlatform)
				.set(systems).equalToWhenPresent(record::getSystems).set(maxRunJob)
				.equalToWhenPresent(record::getMaxRunJob).set(select).equalToWhenPresent(record::getSelect)
				.set(selectPro).equalToWhenPresent(record::getSelectPro).set(usePlatform)
				.equalToWhenPresent(record::getUsePlatform).set(serverRoleNameGroup)
				.equalToWhenPresent(record::getServerRoleNameGroup).set(des).equalToWhenPresent(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int updateByPrimaryKey(UdsSystem record) {
		return update(c -> c.set(platform).equalTo(record::getPlatform).set(systems).equalTo(record::getSystems)
				.set(maxRunJob).equalTo(record::getMaxRunJob).set(select).equalTo(record::getSelect).set(selectPro)
				.equalTo(record::getSelectPro).set(usePlatform).equalTo(record::getUsePlatform).set(serverRoleNameGroup)
				.equalTo(record::getServerRoleNameGroup).set(des).equalTo(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.248+08:00", comments = "Source Table: uds_system")
	default int updateByPrimaryKeySelective(UdsSystem record) {
		return update(c -> c.set(platform).equalToWhenPresent(record::getPlatform).set(systems)
				.equalToWhenPresent(record::getSystems).set(maxRunJob).equalToWhenPresent(record::getMaxRunJob)
				.set(select).equalToWhenPresent(record::getSelect).set(selectPro)
				.equalToWhenPresent(record::getSelectPro).set(usePlatform).equalToWhenPresent(record::getUsePlatform)
				.set(serverRoleNameGroup).equalToWhenPresent(record::getServerRoleNameGroup).set(des)
				.equalToWhenPresent(record::getDes).where(id, isEqualTo(record::getId)));
	}

	default Optional<UdsSystem> selectUdsSystem(String platform_, String system) {
		Optional<UdsSystem> optional = selectOne(
				c -> c.where(platform, isEqualTo(platform_)).and(systems, isEqualTo(system)));
		return optional;
	}
	
	@ResultMap("UdsSystemResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsSystem> selectManyPage(SelectStatementProvider selectStatement,Page<UdsSystem> page);
	default Page<UdsSystem> selectAll(Page<UdsSystem> page, String platform_, String systems_, Boolean use_platform,String server_role_name_group) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(UdsSystemDynamicSqlSupport.udsSystem)
				.where(udsSystem.platform,isEqualToWhenPresent(platform_))
				.and(udsSystem.systems,isEqualToWhenPresent(systems_))
				.and(udsSystem.usePlatform,isEqualToWhenPresent(use_platform))
				.and(udsSystem.serverRoleNameGroup,isEqualToWhenPresent(server_role_name_group))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsSystem> records = selectManyPage(selectStatement,page);
		page.setRecords(records);
		return page;
		
	}
	
	default int updateConcurrencyState(UdsSystem udsSystem_) {
		UpdateStatementProvider updateStatement = SqlBuilder.update(udsSystem)
				.set(maxRunJob).equalToWhenPresent(udsSystem_.getMaxRunJob())
				.set(select).equalToWhenPresent(udsSystem_.getSelect())
				.set(des).equalToWhenPresent(udsSystem_.getDes())
				.set(selectPro).equalToWhenPresent(udsSystem_.getSelectPro())
				.set(usePlatform).equalToWhenPresent(udsSystem_.getUsePlatform())
				.where(platform, isEqualTo(udsSystem_.getPlatform()))
				.and(systems, isEqualTo(udsSystem_.getSystems()))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		return update(updateStatement);
		
	}

	default List<UdsSystem> selectByPlatform(String platform_){
		return select(c->c.where(platform, isEqualTo(platform_)));
	}
	
	default int delete(Integer[] ids) {
		return delete(c -> c.where(id, isIn(ids)));
	}

	default List<UdsSystem> selectAllPlatform(){
		return select(c->c.where(systems, isEqualTo(Symbol.XING_HAO)));
	}
}