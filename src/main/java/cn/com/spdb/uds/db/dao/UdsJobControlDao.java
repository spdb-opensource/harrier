package cn.com.spdb.uds.db.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.bean.UdsJobDateTriggerBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

/**
 * 作业控制表
 * 
 * @author T-luzl
 */
public class UdsJobControlDao extends AbstractBaseDao {

	/**
	 * 获取触发的作业信息
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public List<UdsJobBean> getUdsJobStream(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		List<UdsJobBean> list = selectList("uds_job_control.getUdsJobStream", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * 获取依赖的作业信息
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public List<UdsJobBean> getUdsJobDependency(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		List<UdsJobBean> list = selectList("uds_job_control.getUdsJobDependency", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	public List<UdsJobBean> getUdsJobByDependency(String platform, String system, String job, int batch) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dep_platform", platform);
		map.put("dep_system", system);
		map.put("dep_job", job);
		map.put("dep_batch", batch);
		List<UdsJobBean> list = selectList("uds_job_control.getUdsJobByDependency", map);
		if (list == null) {
			list = new ArrayList<UdsJobBean>();
		}
		return list;
	}

	/**
	 * 获取数据库中触发时间大于当前时间的作业信息
	 * 
	 * @return
	 */
	public List<UdsJobDateTriggerBean> checkDateTriggerJob() {
		List<UdsJobDateTriggerBean> list = selectList("uds_job_control.checkDateTriggerJob");
		if (list == null) {
			list = new ArrayList<UdsJobDateTriggerBean>();
		}
		return list;
	}

	/**
	 * 获取数据库中触发时间大于当前时间的作业信息
	 * 
	 * @return
	 */
	public List<UdsJobDateTriggerBean> getUdsJobDateTrigger(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		List<UdsJobDateTriggerBean> list = selectList("uds_job_control.getUdsJobDateTrigger", map);
		if (list == null) {
			list = new ArrayList<UdsJobDateTriggerBean>();
		}
		return list;
	}

	/**
	 * 获取数据库中触发时间大于当前时间的作业信息
	 * 
	 * @return
	 */
	public List<UdsJobDateTriggerBean> checkDateTriggerJobByLocation(int location) {
		List<UdsJobDateTriggerBean> list = selectList("uds_job_control.checkDateTriggerJobByLocation", location);
		if (list == null) {
			list = new ArrayList<UdsJobDateTriggerBean>();
		}
		return list;
	}

	/**
	 * 获取某个作业下次触发的时间
	 * 
	 * @return
	 */
	public Timestamp getDateTriggerJob(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		Timestamp date = selectOne("uds_job_control.getDateTriggerJob", map);
		return date;
	}

	/**
	 * 获取时间间隔数据
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @return
	 */
	public List<UdsJobDateFrequencyBean> getUdsJobDateFrequency(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		List<UdsJobDateFrequencyBean> list = selectList("uds_job_control.getUdsJobDateFrequency", map);
		if (list == null) {
			list = new ArrayList<UdsJobDateFrequencyBean>();
		}
		return list;
	}

	/**
	 * 获取平台应用配置参数
	 * 
	 * @return
	 */
	public List<UdsSystemBean> getUdsSystemBeanList() {
		List<UdsSystemBean> list = selectList("uds_job_control.getUdsSystemBeanList");
		if (list == null) {
			list = new ArrayList<UdsSystemBean>();
		}
		return list;
	}

	/**
	 * 更新平台参数
	 * 
	 * @param listUdsSystemBeans
	 * @return
	 */
	public int updateUdsSystemList(List<UdsSystemBean> listUdsSystemBeans) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int tmp = 0;
		try {
			for (UdsSystemBean udsSystemBean : listUdsSystemBeans) {
				tmp += sqlSession.update("uds_job_control.updateUdsSystem", udsSystemBean);
			}
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, e.getMessage());
		} finally {
			sqlSession.close();
		}
		return tmp;
	}

	/**
	 * 更新平台参数
	 * 
	 * @param listUdsSystemBeans
	 * @return
	 */
	public int updateUdsSystemListRunNum(List<UdsSystemBean> listUdsSystemBeans) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int tmp = 0;
		try {
			for (UdsSystemBean udsSystemBean : listUdsSystemBeans) {
				tmp += sqlSession.update("uds_job_control.updateUdsSystemRunNum", udsSystemBean);
			}
			sqlSession.commit();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.ERROR_DB, e.getMessage());
		} finally {
			sqlSession.close();
		}
		return tmp;
	}
	
	/**
	 * 获取平台参数
	 * 
	 * @param platform
	 * @param system
	 * @return
	 */
	public UdsSystemBean getUdsSystemBean(String platform, String system) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		return selectOne("uds_job_control.getUdsSystemBean", map);
	}

	// 获取依赖批次号
	public int getDepbatch(String platform, String system, String job, String dep_platform, String dep_system,
			String dep_job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("dep_platform", dep_platform);
		map.put("dep_system", dep_system);
		map.put("dep_job", dep_job);
		return selectOne("uds_job_control.getDepbatch", map);
	}

	// 获取触发次号
	public int getStmbatch(String platform, String system, String job, String stm_platform, String stm_system,
			String stm_job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("stm_platform", stm_platform);
		map.put("stm_system", stm_system);
		map.put("stm_job", stm_job);
		return selectOne("uds_job_control.getStmbatch", map);
	}

}
