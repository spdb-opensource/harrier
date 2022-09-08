
package cn.spdb.harrier.common;

import java.util.regex.Pattern;

import cn.spdb.harrier.common.utils.OSUtils;

/**
 * Constants
 */
public final class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	public static final String SYSTEM_STR = "SYSTEM";
	/**
	 * datasource configuration path
	 */
	public static final String DATASOURCE_PROPERTIES = "/datasource.properties";

	/**
	 * data source config
	 */
	public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

	public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";

	public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";

	public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";

	public static final String SPRING_DATASOURCE_VALIDATION_QUERY_TIMEOUT = "spring.datasource.validationQueryTimeout";

	public static final String SPRING_DATASOURCE_INITIAL_SIZE = "spring.datasource.initialSize";

	public static final String SPRING_DATASOURCE_MIN_IDLE = "spring.datasource.minIdle";

	public static final String SPRING_DATASOURCE_MAX_ACTIVE = "spring.datasource.maxActive";

	public static final String SPRING_DATASOURCE_MAX_WAIT = "spring.datasource.maxWait";

	public static final String SPRING_DATASOURCE_TIME_BETWEEN_EVICTION_RUNS_MILLIS = "spring.datasource.timeBetweenEvictionRunsMillis";

	public static final String SPRING_DATASOURCE_TIME_BETWEEN_CONNECT_ERROR_MILLIS = "spring.datasource.timeBetweenConnectErrorMillis";

	public static final String SPRING_DATASOURCE_MIN_EVICTABLE_IDLE_TIME_MILLIS = "spring.datasource.minEvictableIdleTimeMillis";

	public static final String SPRING_DATASOURCE_VALIDATION_QUERY = "spring.datasource.validationQuery";

	public static final String SPRING_DATASOURCE_TEST_WHILE_IDLE = "spring.datasource.testWhileIdle";

	public static final String SPRING_DATASOURCE_TEST_ON_BORROW = "spring.datasource.testOnBorrow";

	public static final String SPRING_DATASOURCE_TEST_ON_RETURN = "spring.datasource.testOnReturn";

	public static final String SPRING_DATASOURCE_POOL_PREPARED_STATEMENTS = "spring.datasource.poolPreparedStatements";

	public static final String SPRING_DATASOURCE_DEFAULT_AUTO_COMMIT = "spring.datasource.defaultAutoCommit";

	public static final String SPRING_DATASOURCE_KEEP_ALIVE = "spring.datasource.keepAlive";

	public static final String SPRING_DATASOURCE_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = "spring.datasource.maxPoolPreparedStatementPerConnectionSize";

	public static final String SPRING_DRUID_MYSQL_USEPINGMETHOD = "druid.mysql.usePingMethod";

	public static final String DEVELOPMENT = "development";

	/**
	 * redis configuration path
	 */
	public static final String REDIS_PROPERTIES = "/redis.properties";

	/**
	 * redis config
	 */
	public static final String SPRING_REDIS_URL = "spring.redis.url";

	public static final String SPRING_REDIS_DATABASE = "spring.redis.database";

	public static final String SPRING_REDIS_HOST = "spring.redis.host";

	public static final String SPRING_REDIS_PASSWORD = "spring.redis.password";

	public static final String SPRING_REDIS_PORT = "spring.redis.port";

	public static final String SPRING_REDIS_TIMEOUT = "spring.redis.timeout";

	public static final String SPRING_REDIS_POOL_MAX_ACTIVE = "spring.redis.pool.max-active";

	public static final String SPRING_REDIS_POOL_MAX_IDLE = "spring.redis.pool.max-idle";

	public static final String SPRING_REDIS_POOL_MAX_WAIT = "spring.redis.pool.max-wait";

	public static final String SPRING_REDIS_POOL_MIN_IDLE = "spring.redis.pool.min-idle";
	/** 普通集群不使用则不开启，逗号分割主机：端口 */
	public static final String SPRING_REDIS_CLUSTER_NODES = "spring.redis.cluster.nodes";
	/** 普通集群不使用则不开启，在集群中执行命令时要遵循最大重重定向数目 */
	public static final String SPRING_REDIS_CLUSTER_MAX_REDIRECTS = "spring.redis.cluster.max.redirects";
	/** 哨兵模式，不使用不开启，redis服务器名称 */
	public static final String SPRING_REDIS_SENTINEL_MASTER = "spring.redis.sentinel.master";
	/** 哨兵模式，不使用不开启，主机：端口 */
	public static final String SPRING_REDIS_SENTINEL_NODES = "spring.redis.sentinel.nodes";

	/**
	 * common properties path
	 */
	public static final String COMMON_PROPERTIES_PATH = "/common.properties";

	/**
	 * string true
	 */
	public static final String STRING_TRUE = "true";

	/**
	 * string false
	 */
	public static final String STRING_FALSE = "false";

	/**
	 * UTF-8
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * user name regex
	 */
	public static final Pattern REGEX_USER_NAME = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$");

	/**
	 * email regex
	 */
	public static final Pattern REGEX_MAIL_NAME = Pattern
			.compile("^([a-z0-9A-Z]+[_|\\-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	/**
	 * read permission
	 */
	public static final int READ_PERMISSION = 2 * 1;

	/**
	 * write permission
	 */
	public static final int WRITE_PERMISSION = 2 * 2;

	/**
	 * execute permission
	 */
	public static final int EXECUTE_PERMISSION = 1;

	/**
	 * default admin permission
	 */
	public static final int DEFAULT_ADMIN_PERMISSION = 7;

	/**
	 * all permissions
	 */
	public static final int ALL_PERMISSIONS = READ_PERMISSION | WRITE_PERMISSION | EXECUTE_PERMISSION;

	/**
	 * max task timeout
	 */
	public static final int MAX_TASK_TIMEOUT = 24 * 3600;

	/**
	 * master cpu load
	 */
	public static final int DEFAULT_MASTER_CPU_LOAD = Runtime.getRuntime().availableProcessors() * 2;

	/**
	 * worker cpu load
	 */
	public static final int DEFAULT_WORKER_CPU_LOAD = Runtime.getRuntime().availableProcessors() * 2;

	/**
	 * log flush interval?output when reach the interval
	 */
	public static final int DEFAULT_LOG_FLUSH_INTERVAL = 1000;

	/***
	 *
	 * rpc port
	 */
	public static final int RPC_PORT = 50051;

	public static final String DEFAULT = "Default";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String XXXXXX = "******";
	public static final String NULL = "NULL";

	public static final String THREAD_NAME_ALARM_SERVER = "Alarm-Server";
	public static final String THREAD_NAME_MASTER_SERVER = "Master-Server";
	public static final String THREAD_NAME_WORKER_SERVER = "Worker-Server";
	public static final String THREAD_NAME_MONITOR_SERVER = "Monitor-Server";

	/**
	 * sleep time
	 */
	public static final int SLEEP_TIME_MILLIS = 1000;

	/**
	 * exit code success
	 */
	public static final int EXIT_CODE_SUCCESS = 0;

	/**
	 * exit code kill
	 */
	public static final int EXIT_CODE_KILL = 137;

	/**
	 * exit code failure
	 */
	public static final int EXIT_CODE_FAILURE = -1;

	public static final String PID = OSUtils.isWindows() ? "handle" : "pid";

	/**
	 * driver
	 */
	public static final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
	public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String ORG_APACHE_HIVE_JDBC_HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	public static final String COM_CLICKHOUSE_JDBC_DRIVER = "ru.yandex.clickhouse.ClickHouseDriver";
	public static final String COM_ORACLE_JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String COM_SQLSERVER_JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String COM_DB2_JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String COM_TERA_DATA_JDBC_DRIVER = "com.teradata.jdbc.TeraDriver";

	/**
	 * database type
	 */
	public static final String MYSQL = "MYSQL";
	public static final String POSTGRESQL = "POSTGRESQL";
	public static final String HIVE = "HIVE";
	public static final String SPARK = "SPARK";
	public static final String CLICKHOUSE = "CLICKHOUSE";
	public static final String ORACLE = "ORACLE";
	public static final String SQLSERVER = "SQLSERVER";
	public static final String DB2 = "DB2";

	/**
	 * jdbc url
	 */
	public static final String JDBC_MYSQL = "jdbc:mysql://";
	public static final String JDBC_POSTGRESQL = "jdbc:postgresql://";
	public static final String JDBC_HIVE_2 = "jdbc:hive2://";
	public static final String JDBC_CLICKHOUSE = "jdbc:clickhouse://";
	public static final String JDBC_ORACLE_SID = "jdbc:oracle:thin:@";
	public static final String JDBC_ORACLE_SERVICE_NAME = "jdbc:oracle:thin:@//";
	public static final String JDBC_SQLSERVER = "jdbc:sqlserver://";
	public static final String JDBC_DB2 = "jdbc:db2://";

	public static final String ADDRESS = "address";
	public static final String DATABASE = "database";
	public static final String JDBC_URL = "jdbcUrl";
	public static final String PRINCIPAL = "principal";
	public static final String OTHER = "other";

	public static final String CPU = "CPU";
	public static final String MEM = "MEM";
	public static final String LOAD_AVERAGE = "LOAD";

	/**
	 * dataSource sensitive param
	 */
	public static final String DATASOURCE_PASSWORD_REGEX = "(?<=(\"password\":\")).*?(?=(\"))";

	public static final Integer TASK_INFO_LENGTH = 5;

	/**
	 * authorize writable perm
	 */
	public static final int AUTHORIZE_WRITABLE_PERM = 7;
	/**
	 * authorize readable perm
	 */
	public static final int AUTHORIZE_READABLE_PERM = 4;

	/**
	 * plugin configurations
	 */
	public static final String PLUGIN_JAR_SUFFIX = ".jar";

	public static final String DATASOURCE_ENCRYPTION_ENABLE = null;

	public static final String DATASOURCE_ENCRYPTION_SALT = null;

	public static final String DATASOURCE_ENCRYPTION_SALT_DEFAULT = null;

	public static final String SUDO_ENABLE = null;

	public static final Object PSTREE = null;

	public static final String LOCALE_LANGUAGE = "language";
	public static String LANGUAGE = "";

	public static final String TOKEN_PREFIX = "bearer ";

	public static final String LOGIN_TOKEN_KEY = "login_tokens:";

	public static final String LOGIN_USER_KEY = "login_user_key";

	public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

	public static final String TOKEN = "token";

	public static final String REPEAT_SUBMIT_KEY = "repeat_submit";

	public static final Byte MENU_BUTEN = 0;
	public static final Byte MENU_RES = 1;

	public static final String APPLICATION_REGEX = "[app-";

	public static final int HTTP_CONNECT_TIMEOUT = 60 * 1000;

	public static final int HTTP_CONNECTION_REQUEST_TIMEOUT = 60 * 1000;

	public static final int SOCKET_TIMEOUT = 60 * 1000;

	public static final String HDFS_ROOT_USER = null;

	public static final String RESOURCE_UPLOAD_PATH = null;

	public static final String YARN_RESOURCEMANAGER_HA_RM_IDS = null;

	public static final String YARN_APPLICATION_STATUS_ADDRESS = null;

	public static final String KERBEROS_EXPIRE_TIME = null;

	public static final String FS_DEFAULTFS = null;

	public static final String HADOOP_RESOURCE_MANAGER_HTTPADDRESS_PORT = null;

	public static final String HADOOP_RM_STATE_ACTIVE = null;

	public static final String LOGIN_USER_KEY_TAB_USERNAME = null;

	public static final String HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE = null;

	public static final String LOGIN_USER_KEY_TAB_PATH = null;

	public static final String JAVA_SECURITY_KRB5_CONF_PATH = null;

	public static final String JAVA_SECURITY_KRB5_CONF = null;

	public static final String HADOOP_SECURITY_AUTHENTICATION = null;

	public static final String KERBEROS = null;

	public static final String VERIFICATION = "verification";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String CODEFAIL = "codefail";
	public static final String USERFAIL = "userfail";
	public static final String PWDFAIL = "pwdfail";
	
	public static final String REGISTER_SWITCH = "register_switch";
	public static final String REGIST_CAPTCHA_SWITCH = "register_captcha_switch";
	public static final String LOGIN_CAPTCHA_SWITCH = "login_captcha_switch";


	
	public static final Long LOAD_BUFFER_SIZE = 1024 * 1024 * 10L;

}
