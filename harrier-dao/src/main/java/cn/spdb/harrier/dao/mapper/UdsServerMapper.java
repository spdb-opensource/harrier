package cn.spdb.harrier.dao.mapper;

import static cn.spdb.harrier.dao.mapper.UdsServerDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.spdb.harrier.dao.entity.UdsServer;
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
public interface UdsServerMapper {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	BasicColumn[] selectList = BasicColumn.columnList(id, serverRoleName, serverRoleNameGroup, nodeClusterType,
			serverName, ip, port, lastStart, updateTime, lastEnd, isEnable, para, des);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.611+08:00", comments = "Source Table: uds_server")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.611+08:00", comments = "Source Table: uds_server")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.611+08:00", comments = "Source Table: uds_server")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	int insert(InsertStatementProvider<UdsServer> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.611+08:00", comments = "Source Table: uds_server")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
	int insertMultiple(MultiRowInsertStatementProvider<UdsServer> multipleInsertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UdsServerResult")
	Optional<UdsServer> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UdsServerResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "server_role_name", property = "serverRoleName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "server_role_name_group", property = "serverRoleNameGroup", jdbcType = JdbcType.VARCHAR),
			@Result(column = "node_cluster_type", property = "nodeClusterType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "server_name", property = "serverName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
			@Result(column = "port", property = "port", jdbcType = JdbcType.INTEGER),
			@Result(column = "last_start", property = "lastStart", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "last_end", property = "lastEnd", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "is_enable", property = "isEnable", jdbcType = JdbcType.BIT),
			@Result(column = "para", property = "para", jdbcType = JdbcType.VARCHAR),
			@Result(column = "des", property = "des", jdbcType = JdbcType.VARCHAR) })
	List<UdsServer> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default int deleteByPrimaryKey(Integer id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default int insert(UdsServer record) {
		return MyBatis3Utils.insert(this::insert, record, udsServer,
				c -> c.map(id).toProperty("id").map(serverRoleName).toProperty("serverRoleName")
						.map(serverRoleNameGroup).toProperty("serverRoleNameGroup").map(nodeClusterType)
						.toProperty("nodeClusterType").map(serverName).toProperty("serverName").map(ip).toProperty("ip")
						.map(port).toProperty("port").map(lastStart).toProperty("lastStart").map(updateTime)
						.toProperty("updateTime").map(lastEnd).toProperty("lastEnd").map(isEnable)
						.toProperty("isEnable").map(para).toProperty("para").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default int insertMultiple(Collection<UdsServer> records) {
		return MyBatis3Utils.insertMultiple(this::insertMultiple, records, udsServer,
				c -> c.map(id).toProperty("id").map(serverRoleName).toProperty("serverRoleName")
						.map(serverRoleNameGroup).toProperty("serverRoleNameGroup").map(nodeClusterType)
						.toProperty("nodeClusterType").map(serverName).toProperty("serverName").map(ip).toProperty("ip")
						.map(port).toProperty("port").map(lastStart).toProperty("lastStart").map(updateTime)
						.toProperty("updateTime").map(lastEnd).toProperty("lastEnd").map(isEnable)
						.toProperty("isEnable").map(para).toProperty("para").map(des).toProperty("des"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.612+08:00", comments = "Source Table: uds_server")
	default int insertSelective(UdsServer record) {
		return MyBatis3Utils.insert(this::insert, record, udsServer, c -> c.map(id)
				.toPropertyWhenPresent("id", record::getId).map(serverRoleName)
				.toPropertyWhenPresent("serverRoleName", record::getServerRoleName).map(serverRoleNameGroup)
				.toPropertyWhenPresent("serverRoleNameGroup", record::getServerRoleNameGroup).map(nodeClusterType)
				.toPropertyWhenPresent("nodeClusterType", record::getNodeClusterType).map(serverName)
				.toPropertyWhenPresent("serverName", record::getServerName).map(ip)
				.toPropertyWhenPresent("ip", record::getIp).map(port).toPropertyWhenPresent("port", record::getPort)
				.map(lastStart).toPropertyWhenPresent("lastStart", record::getLastStart).map(updateTime)
				.toPropertyWhenPresent("updateTime", record::getUpdateTime).map(lastEnd)
				.toPropertyWhenPresent("lastEnd", record::getLastEnd).map(isEnable)
				.toPropertyWhenPresent("isEnable", record::getIsEnable).map(para)
				.toPropertyWhenPresent("para", record::getPara).map(des).toPropertyWhenPresent("des", record::getDes));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default Optional<UdsServer> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default List<UdsServer> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default List<UdsServer> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default Optional<UdsServer> selectByPrimaryKey(Integer id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, udsServer, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	static UpdateDSL<UpdateModel> updateAllColumns(UdsServer record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalTo(record::getId).set(serverRoleName).equalTo(record::getServerRoleName)
				.set(serverRoleNameGroup).equalTo(record::getServerRoleNameGroup).set(nodeClusterType)
				.equalTo(record::getNodeClusterType).set(serverName).equalTo(record::getServerName).set(ip)
				.equalTo(record::getIp).set(port).equalTo(record::getPort).set(lastStart).equalTo(record::getLastStart)
				.set(updateTime).equalTo(record::getUpdateTime).set(lastEnd).equalTo(record::getLastEnd).set(isEnable)
				.equalTo(record::getIsEnable).set(para).equalTo(record::getPara).set(des).equalTo(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(UdsServer record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(id).equalToWhenPresent(record::getId).set(serverRoleName)
				.equalToWhenPresent(record::getServerRoleName).set(serverRoleNameGroup)
				.equalToWhenPresent(record::getServerRoleNameGroup).set(nodeClusterType)
				.equalToWhenPresent(record::getNodeClusterType).set(serverName)
				.equalToWhenPresent(record::getServerName).set(ip).equalToWhenPresent(record::getIp).set(port)
				.equalToWhenPresent(record::getPort).set(lastStart).equalToWhenPresent(record::getLastStart)
				.set(updateTime).equalToWhenPresent(record::getUpdateTime).set(lastEnd)
				.equalToWhenPresent(record::getLastEnd).set(isEnable).equalToWhenPresent(record::getIsEnable).set(para)
				.equalToWhenPresent(record::getPara).set(des).equalToWhenPresent(record::getDes);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.613+08:00", comments = "Source Table: uds_server")
	default int updateByPrimaryKey(UdsServer record) {
		return update(c -> c.set(serverRoleName).equalTo(record::getServerRoleName).set(serverRoleNameGroup)
				.equalTo(record::getServerRoleNameGroup).set(nodeClusterType).equalTo(record::getNodeClusterType)
				.set(serverName).equalTo(record::getServerName).set(ip).equalTo(record::getIp).set(port)
				.equalTo(record::getPort).set(lastStart).equalTo(record::getLastStart).set(updateTime)
				.equalTo(record::getUpdateTime).set(lastEnd).equalTo(record::getLastEnd).set(isEnable)
				.equalTo(record::getIsEnable).set(para).equalTo(record::getPara).set(des).equalTo(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-04T10:53:06.614+08:00", comments = "Source Table: uds_server")
	default int updateByPrimaryKeySelective(UdsServer record) {
		return update(c -> c.set(serverRoleName).equalToWhenPresent(record::getServerRoleName).set(serverRoleNameGroup)
				.equalToWhenPresent(record::getServerRoleNameGroup).set(nodeClusterType)
				.equalToWhenPresent(record::getNodeClusterType).set(serverName)
				.equalToWhenPresent(record::getServerName).set(ip).equalToWhenPresent(record::getIp).set(port)
				.equalToWhenPresent(record::getPort).set(lastStart).equalToWhenPresent(record::getLastStart)
				.set(updateTime).equalToWhenPresent(record::getUpdateTime).set(lastEnd)
				.equalToWhenPresent(record::getLastEnd).set(isEnable).equalToWhenPresent(record::getIsEnable).set(para)
				.equalToWhenPresent(record::getPara).set(des).equalToWhenPresent(record::getDes)
				.where(id, isEqualTo(record::getId)));
	}

	default int updateByIpPortSelective(UdsServer record) {
		return update(c -> c.set(lastStart).equalToWhenPresent(record::getLastStart).set(updateTime)
				.equalToWhenPresent(record::getUpdateTime).set(lastEnd).equalToWhenPresent(record::getLastEnd)
				.set(isEnable).equalToWhenPresent(record::getIsEnable).set(para).equalToWhenPresent(record::getPara)
				.where().and(isEnable, isEqualTo(true)).and(ip, isEqualTo(record::getIp))
				.and(port, isEqualTo(record::getPort)));
	}

	default int deleteByIpPortSelective(String ip_, int port_) {
		return delete(c -> c.where().and(ip, isEqualTo(ip_)).and(port, isEqualTo(port_)));
	}

	default Optional<UdsServer> selectByIpPortSelective(String ip_, Integer port_) {
		return selectOne(c -> c.where().and(ip, isEqualTo(ip_)).and(port, isEqualTo(port_)));
	}

	default List<UdsServer> selectListByRole(String serverRoleName_, String serverRoleNameGroup_, boolean b) {
		return select(c -> c.where(serverRoleName, isEqualTo(serverRoleName_),
				and(serverRoleNameGroup, isEqualTo(serverRoleNameGroup_))).and(isEnable, isEqualTo(true)));
	}

	@ResultMap("UdsServerResult")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	List<UdsServer> selectManyPage(SelectStatementProvider selectStatement, Page<UdsServer> page);

	default Page<UdsServer> selectAll(Page<UdsServer> page, String servername) {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(udsServer)
				.where(udsServer.serverName, isLikeWhenPresent(servername)).build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsServer> records = selectManyPage(selectStatement, page);
		page.setRecords(records);
		return page;
	}

	default int setEnable(String serverName_, Boolean is_enable) {
		UpdateStatementProvider updateStatement = SqlBuilder.update(udsServer).set(isEnable)
				.equalToWhenPresent(is_enable).where(serverName, isEqualTo(serverName_)).build()
				.render(RenderingStrategies.MYBATIS3);
		return update(updateStatement);

	}

	/*
	 * 首页-统计主子节点资源
	 */
	default List<UdsServer> ServerResourceInfo() {
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList).from(udsServer)
				.where(udsServer.nodeClusterType, isLike("Master-Server"))
				.or(udsServer.nodeClusterType, isLike("Worker-Server")).build().render(RenderingStrategies.MYBATIS3);
		List<UdsServer> records = selectMany(selectStatement);
		return records;
	}

	default List<UdsServer> availableWorker(){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(udsServer)
				.where(udsServer.nodeClusterType,isLike("Worker-Server"))
				.and(udsServer.isEnable, isEqualTo(true))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsServer> records = selectMany(selectStatement);
		return records;
	}

	default List<UdsServer> availableMaster(){
		SelectStatementProvider selectStatement = SqlBuilder.select(selectList)
				.from(udsServer)
				.where(udsServer.nodeClusterType,isLike("Master-Server"))
				.and(udsServer.isEnable, isEqualTo(true))
				.build()
				.render(RenderingStrategies.MYBATIS3);
		List<UdsServer> records = selectMany(selectStatement);
		return records;
	}
}