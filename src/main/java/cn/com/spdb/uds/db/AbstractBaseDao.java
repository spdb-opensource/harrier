package cn.com.spdb.uds.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

public abstract class AbstractBaseDao {

	private long updataTime = 0;

	protected SqlSessionFactory sqlSessionFactory = DBManager.getInstance().getSqlSessionFactory();

	protected int delete(String arg0) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.delete(arg0);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	protected int delete(String arg0, Object arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.delete(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	protected int insert(String arg0) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.insert(arg0);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	protected int insert(String arg0, Object arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.insert(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	protected <E> List<E> selectList(String arg0) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<E> list = null;
		try {
			list = sqlSession.selectList(arg0);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return list;
	}

	protected <E> List<E> selectList(String arg0, Object arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<E> list = null;
		try {
			list = sqlSession.selectList(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return list;
	}

	protected <K, V> Map<K, V> selectMap(String arg0, String arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<K, V> map = null;
		try {
			map = sqlSession.selectMap(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return map;
	}

	protected <K, V> Map<K, V> selectMap(String arg0, Object arg1, String arg2) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<K, V> map = null;
		try {
			map = sqlSession.selectMap(arg0, arg1, arg2);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return map;
	}

	protected <T> T selectOne(String arg0) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		T tmp = null;
		try {
			tmp = sqlSession.selectOne(arg0);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return tmp;
	}

	protected <T> T selectOne(String arg0, Object arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		T tmp = null;
		try {
			tmp = sqlSession.selectOne(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return tmp;
	}

	protected int update(String arg0) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.update(arg0);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	protected int update(String arg0, Object arg1) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			num = sqlSession.update(arg0, arg1);
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, arg0, arg1, e.getMessage());
		} finally {
			sqlSession.close();
		}
		updataTime = System.currentTimeMillis();
		return num;
	}

	public boolean isCheckOver() {
		return System.currentTimeMillis() - updataTime > 3 * DateUtils.TIME_MILLSECOND_OF_MINUTE;
	}

	public long getUpdataTime() {
		return updataTime;
	}

	protected void setUpdataTime(long updataTime) {
		this.updataTime = updataTime;
	}

}
