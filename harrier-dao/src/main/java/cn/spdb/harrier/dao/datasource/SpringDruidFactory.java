
package cn.spdb.harrier.dao.datasource;

import java.sql.SQLException;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.PropertyUtils;

/**
 * data source connection factory
 */
@Configuration
@MapperScan("cn.spdb.harrier.dao.mapper")
public class SpringDruidFactory {

	private static final Logger logger = LoggerFactory.getLogger(SpringDruidFactory.class);

	private MybatisConfiguration configuration = new MybatisConfiguration();

	public SpringDruidFactory() {
		logger.info("load SpringDruidFactory");
	}

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptorByMybatis3());
		return interceptor;
	}

	@Bean
	public DruidDataSource dataSource() throws SQLException {
		PropertyUtils.loadPropertyFile(Constants.DATASOURCE_PROPERTIES);
		DruidDataSource druidDataSource = new DruidDataSource();
		System.setProperty(Constants.SPRING_DRUID_MYSQL_USEPINGMETHOD, Constants.STRING_FALSE);
		druidDataSource.setDriverClassName(PropertyUtils.getString(Constants.SPRING_DATASOURCE_DRIVER_CLASS_NAME));
		druidDataSource.setUrl(PropertyUtils.getString(Constants.SPRING_DATASOURCE_URL));
		druidDataSource.setUsername(PropertyUtils.getString(Constants.SPRING_DATASOURCE_USERNAME));
		druidDataSource.setPassword(PropertyUtils.getString(Constants.SPRING_DATASOURCE_PASSWORD));
		druidDataSource
				.setValidationQuery(PropertyUtils.getString(Constants.SPRING_DATASOURCE_VALIDATION_QUERY, "SELECT 1"));

		druidDataSource.setPoolPreparedStatements(
				PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_POOL_PREPARED_STATEMENTS, true));
		druidDataSource.setTestWhileIdle(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_TEST_WHILE_IDLE, true));
		druidDataSource.setTestOnBorrow(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_TEST_ON_BORROW, true));
		druidDataSource.setTestOnReturn(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_TEST_ON_RETURN, true));
		druidDataSource.setKeepAlive(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_KEEP_ALIVE, true));

		druidDataSource.setMinIdle(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MIN_IDLE, 5));
		druidDataSource.setMaxActive(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));
		druidDataSource.setMaxWait(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_WAIT, 60000));
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
				PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE, 20));
		druidDataSource.setInitialSize(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_INITIAL_SIZE, 5));
		druidDataSource.setTimeBetweenEvictionRunsMillis(
				PropertyUtils.getLong(Constants.SPRING_DATASOURCE_TIME_BETWEEN_EVICTION_RUNS_MILLIS, 60000));
		druidDataSource.setTimeBetweenConnectErrorMillis(
				PropertyUtils.getLong(Constants.SPRING_DATASOURCE_TIME_BETWEEN_CONNECT_ERROR_MILLIS, 60000));
		druidDataSource.setMinEvictableIdleTimeMillis(
				PropertyUtils.getLong(Constants.SPRING_DATASOURCE_MIN_EVICTABLE_IDLE_TIME_MILLIS, 300000));
		druidDataSource.setValidationQueryTimeout(
				PropertyUtils.getInt(Constants.SPRING_DATASOURCE_VALIDATION_QUERY_TIMEOUT, 3));

		// auto commit
		druidDataSource
				.setDefaultAutoCommit(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_DEFAULT_AUTO_COMMIT, true));
		druidDataSource.init();
		return druidDataSource;
	}

	/**
	 * * get transaction manager
	 * 
	 * @return DataSourceTransactionManager
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() throws SQLException{
		return new DataSourceTransactionManager(dataSource());
	}

	/**
	 * * get sql session factory
	 * 
	 * @return sqlSessionFactory
	 * @throws Exception sqlSessionFactory exception
	 */
	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory() throws Exception {

		/** 缓存开启 */
		configuration.setCacheEnabled(true);
		/** 查询时关闭关联对象及时加载以提高性能 */
		configuration.setLazyLoadingEnabled(false);
		/** 设置对关联对象加载形态，此处按需加载字段（加载字段由SQL指定），不会对加载关联所有字段，以提高效率 */
		configuration.setAggressiveLazyLoading(true);
		/** 对于未知SQL查询，允许返回不同结果集以达到通用效果 */
		configuration.setMultipleResultSetsEnabled(true);
		/** 允许使用标签代替列名 */
		configuration.setUseColumnLabel(true);
		/** 允许使用自定义主键，数据表的主键生成策略可以覆盖 */
		configuration.setUseGeneratedKeys(true);
		/** 给予被嵌套的resultmap以字段-属性映射支持 */
		configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
		/** 对于批处理更新操作缓存SQL以性能提高 ,缺点不会返回影响行数*/
//		configuration.setDefaultExecutorType(ExecutorType.BATCH);

		/** msyql下划线换转驼峰式自动处理，要与entity对应 */
		configuration.setMapUnderscoreToCamelCase(true);
		/** 处理hashmap过滤，某些结果集的字段为NULL，导致map缺少key */
		configuration.setCallSettersOnNulls(true);

		configuration.setJdbcTypeForNull(JdbcType.NULL);

		configuration.addInterceptor(mybatisPlusInterceptor());

		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		/** 设置配置 */
		sqlSessionFactoryBean.setConfiguration(configuration);
		/** 数据源 */
		sqlSessionFactoryBean.setDataSource(dataSource());

		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		dbConfig.setIdType(IdType.AUTO);

		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setDbConfig(dbConfig);

		sqlSessionFactoryBean.setGlobalConfig(globalConfig);
		/** 包扫描 */
		sqlSessionFactoryBean.setTypeAliasesPackage("cn.spdb.harrier.dao.entity");

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		/** 映射XML */
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("cn/spdb/harrier/dao/mapper/*.xml"));
		// sqlSessionFactoryBean.setTypeEnumsPackage("org.apache.dolphinscheduler.*.enums");
		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * get sql session
	 * 
	 * @return SqlSession
	 * @throws Exception
	 */
	@Bean
	@Primary
	public SqlSession sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	public void addInnerInterceptor(InnerInterceptor innerInterceptor) {
		MybatisPlusInterceptor mybatisPlusInterceptor = null;
		for (Interceptor inter : configuration.getInterceptors()) {
			if (MybatisPlusInterceptor.class.isInstance(inter)) {
				mybatisPlusInterceptor = (MybatisPlusInterceptor) inter;
				break;
			}
		}
		if (mybatisPlusInterceptor == null) {
			mybatisPlusInterceptor = new MybatisPlusInterceptor();
			mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor);
			configuration.addInterceptor(mybatisPlusInterceptor);
		} else {
			mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor);
		}
	}

}
