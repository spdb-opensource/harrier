import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.spdb.uds.core.bean.JobTagsType;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobTagBean;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.db.dao.UdsJobTagDao;
import cn.com.spdb.uds.db.dao.UdsJobWeightDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DAG;

public class DaoTest {

	@Test
	public void testDao() {
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		File logConfig = new File("./config/log4j2.xml");
		if (!logConfig.exists()) {
			System.err.println("./config/log4j2.xml is exsit");
			return;
		}
		loggerContext.setConfigLocation(logConfig.toURI());
		loggerContext.reconfigure();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/application.spring.xml");
//		UdsJobTagDao baseDao = DBManager.getInstance().getDao(UdsJobTagDao.class);
//		List<UdsJobTagBean> list = baseDao.getUdsJobTagsOnlySort("DEP", "DS", "DEP_DS_JOB_A", JobTagsType.server);
//		System.out.println(list.size());
//		for (int i = 0, s = list.get(0).getSort(); i < list.size() && s == list.get(i).getSort(); i++) {
//			UdsJobTagBean baen = list.get(i);
//			System.out.println(baen.getJob() + "|" + baen.getTag() + "|" + baen.getSort());
//		}
		UdsJobWeightDao dao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
		Integer value = dao.getUdsJobWeightLimit(5);
		HashMap<Integer, Integer> map = dao.getUdsJobWeightLimitMap();
		HashMap<Integer, Integer> map1 = dao.getUdsJobWeightMap("UDS", "TMP", "UDS_TMP_JOB");
	}

	public static void main(String[] args) {
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		File logConfig = new File("./config/log4j2.xml");
		if (!logConfig.exists()) {
			System.err.println("./config/log4j2.xml is exsit");
			return;
		}
		loggerContext.setConfigLocation(logConfig.toURI());
		loggerContext.reconfigure();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/application.spring.xml");

		BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
		List<HashMap<Object, Object>> jobList = baseDao
				.select("select job from uds_job where platform='EDW' and is_enable=1 ");
		List<HashMap<Object, Object>> depList = baseDao
				.select("select job,dep_job from uds_job_dependency where platform='EDW' and is_enable=1");

		DAG<String, String, String> dag = new DAG<String, String, String>();

		for (HashMap<Object, Object> map : jobList) {
			String job = (String) map.get("job");
			switch (job) {
			case "EDW_BVL_CT01_CORP":
			case "EDW_CSA_CSA_JX_AGMTFTP":
			case "EDW_MAM_M03_B_INTER_ACCT":
			case "EDW_PDM_T00_AGMT_PROD_RELA_H_OTC_F3":
				System.out.println("Ok:" + job + ":");
				break;
			default:
				break;
			}
			dag.addNode(job.trim(), job);
		}

		for (HashMap<Object, Object> map : depList) {
			String job = (String) map.get("job");
			String dep_job = (String) map.get("dep_job");
			dag.addEdge(job.trim(), dep_job.trim());
		}

		System.out.println(dag.getNodesCount());
		System.out.println(dag.getEdgesCount());
		System.out.println(dag.getBeginNode().size());
		System.out.println(dag.getEndNode().size());
		System.out.println(dag.hasCycle());
		try {
//			System.out.println(dag.topologicalSort());
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		}
	}

	@Test
	public static void getDag() {
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		File logConfig = new File("./config/log4j2.xml");
		if (!logConfig.exists()) {
			System.err.println("./config/log4j2.xml is exsit");
			return;
		}
		loggerContext.setConfigLocation(logConfig.toURI());
		loggerContext.reconfigure();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/application.spring.xml");

		BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
		List<HashMap<Object, Object>> jobList = baseDao.select("select job from uds_job where platform='EDW'");
		List<HashMap<Object, Object>> depList = baseDao
				.select("select job,dep_job from uds_job_dependency where platform='EDW'");

		DAG<String, String, String> dag = new DAG<String, String, String>();

		for (HashMap<Object, Object> map : jobList) {
			String job = (String) map.get("job");
			dag.addNode(job, job);
		}

		for (HashMap<Object, Object> map : depList) {
			String job = (String) map.get("job");
			String dep_job = (String) map.get("dep_job");
			dag.addEdge(job, dep_job);
		}

		System.out.println(dag.getNodesCount());
		System.out.println(dag.getEdgesCount());
		System.out.println(dag.getBeginNode());
		System.out.println(dag.getEndNode().size());
		System.out.println(dag.getPreviousNodes("EDW_CSA_CSA_JX_AGMTFTP"));
		System.out.println(dag.getSubsequentNodes("EDW_BVL_CT01_CORP"));
	}

}
