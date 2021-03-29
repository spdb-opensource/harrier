package cn.com.spdb.uds.db.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class BaseDao extends AbstractBaseDao {

	public List<HashMap<Object, Object>> select(String sql) {
		List<HashMap<Object, Object>> list = selectList("uds_base_sql.select", sql);
		return list;
	}

	public int update(String sql) {
		return super.update("uds_base_sql.update", sql);
	}

	public int delete(String sql) {
		return super.delete("uds_base_sql.delete", sql);
	}

	public int insert(String sql) {
		return super.insert("uds_base_sql.insert", sql);
	}

	public int delete(List<String> sqlList) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			for (String sql : sqlList) {
				num = sqlSession.delete("uds_base_sql.delete",sql);
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			sqlSession.close();
		}
		return num;

	}

	public int insert(List<String> sqlList) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int num = 0;
		try {
			for (String sql : sqlList) {
				num = sqlSession.insert("uds_base_sql.insert", sql);
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			sqlSession.close();
		}
		return num;

	}
}
