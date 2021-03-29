package cn.com.spdb.uds.db;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.spdb.uds.SchedulerManager;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

public class DBManager {

	private static DBManager daoManager = null;

	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	private SqlSessionFactory sqlSessionFactory;

	private Object LOCK = new Object();

	private static ConcurrentHashMap<Class<?>, AbstractBaseDao> daoMap = new ConcurrentHashMap<Class<?>, AbstractBaseDao>();

	/**
	 * 配置表注册
	 * 
	 * @param sqlSessionFactory
	 */
	public DBManager(SqlSessionFactory sqlSessionFactory) {
		synchronized (LOCK) {
			if (daoManager == null) {
				this.sqlSessionFactory = sqlSessionFactory;
				init();
				daoManager = this;
			}
		}
	}

	public static DBManager getInstance() {
		if (daoManager == null) {
			logger.warn("ApplicationContext  not configuration DBManager ");
			ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/application.spring.xml");
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) app.getBean("sqlSessionFactory");
			daoManager = new DBManager(sqlSessionFactory);
			logger.info("DBManager load OK");
		}
		return daoManager;
	}

	/**
	 * 重新加载 SqlSessionFactory
	 */
	public void reload() {
		logger.warn("ApplicationContext  not configuration DBManager ");
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/application.spring.xml");
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) app.getBean("sqlSessionFactory");
		this.sqlSessionFactory = sqlSessionFactory;
		daoMap.clear();
		init();
	}

	private void init() {

	}

	private void clearDaoSchedulerThread() {
		SchedulerManager.getInstance().scheduleWithFixedDelay("DBMANAGER_INIT", new Runnable() {
			@Override
			public void run() {
				try {
					for (Entry<Class<?>, AbstractBaseDao> en : daoMap.entrySet()) {
						if (en.getValue().isCheckOver()) {
							daoMap.remove(en.getKey());
						}
					}
				} catch (Exception e) {
					UdsLogger.error(e.getMessage());
				}
			}
		}, 5 * DateUtils.TIME_MILLSECOND_OF_MINUTE, 5 * DateUtils.TIME_MILLSECOND_OF_MINUTE);
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public ConcurrentHashMap<Class<?>, AbstractBaseDao> getDaoMap() {
		return daoMap;
	}

	public void setDaoMap(ConcurrentHashMap<Class<?>, AbstractBaseDao> daoMap) {
		DBManager.daoMap = daoMap;
	}

	public <T> T getDao(Class<?> c) {
		synchronized (LOCK) {
			try {
				if (c.getSuperclass().isAssignableFrom(AbstractBaseDao.class)) {
					AbstractBaseDao dao = daoMap.get(c);
					if (dao == null) {
						dao = (AbstractBaseDao) c.newInstance();
						daoMap.put(c, dao);
					}
					return (T) dao;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public String getSql(String namespace, String Id) {
		return sqlSessionFactory.getConfiguration().getMappedStatement(namespace + "." + Id).getBoundSql(null).getSql();
	}

	public String getSql(String nameSpaceId) {
		return sqlSessionFactory.getConfiguration().getMappedStatement(nameSpaceId).getBoundSql(null).getSql();
	}

}
