package cn.com.spdb.uds;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.spdb.uds.background.http.HttpServer;
import cn.com.spdb.uds.background.socket.BackgroundServer;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.server.UdsRpcServer;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.SystemOutAndErrPrintStream;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.UdsUtils;

public class Server {

	private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private static ApplicationContext applicationContext = null;

	private static UdsRpcServer udsRpcServer;

	public static void main(String[] args) throws Exception {

		/* -----加载LOG4J2日志配置----- */
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		File logConfig = new File("./config/log4j2.xml");
		if (!logConfig.exists()) {
			System.err.println("./config/log4j2.xml is not exsit");
			return;
		}
		loggerContext.setConfigLocation(logConfig.toURI());
		loggerContext.reconfigure();
		/* 加载spring配置 ， 实例化DBManager */
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application.spring.xml");
		new SystemOutAndErrPrintStream(System.out, false);
		new SystemOutAndErrPrintStream(System.err, true);
		/*--- 注意注册加载的顺序---*/
		/*--- 常用变量加载 ---*/
		UdsConstant.load();
		/*--- 定时任务管理  ---*/
		SchedulerManager.getInstance();

		/*--- 已连接客服端管理  ---*/
		UdsRpcClientManager.getInstance().startHeartbeat();
		/*----- 启动RPC服务--- */
		udsRpcServer = new UdsRpcServer();
		udsRpcServer.start();

		/*---------- 后台启动 ----*/
		BackgroundServer.getInstance().startBackend();

		/*--- http 监控  --*/
		if (UdsConstant.IS_PRIMARY_SERVER) {
			HttpServer.getInstance().start();
		}

		/*----------JOB作业日志和信号文件清理-------*/
		if (UdsConstant.CLEAR_JOB_LOG == UdsConstant.TRUE_NUM) {
			UdsLogger.clearLogschedule();
		}

		/*------ 业务逻辑 ------*/
		if (UdsConstant.IS_PRIMARY_SERVER) {
			MasterManager.getInstance();
			LOGGER.info(UdsConstant.SERVER_NAME + " start  MasterManager");
		} else {
			ChildManager.getInstance();
			LOGGER.info(UdsConstant.SERVER_NAME + " start ChildManager");
		}

		UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);
		udsServerDao.updateUdsServerStartTime(UdsConstant.SERVER_NAME);
		/*** 钩子程序 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("start--close!!!!!!!---:" + UdsConstant.SERVER_NAME);
				UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);
				udsServerDao.updateUdsServerEndTime(UdsConstant.SERVER_NAME);
				// 停止接受新任务
				UdsRpcClientManager.getInstance().shutDownRpcClient();
				System.out.println("UdsRpcClient is close");
				if (!UdsConstant.IS_PRIMARY_SERVER && UdsConstant.SEND_LOCATE!=UdsConstant.TRUE_NUM) {
					int num = ChildManager.getInstance().getExecutorJobNum();
					while (num > 0) {
						System.out.println(UdsConstant.SERVER_NAME + " will close, job num :" + num);
						try {
							Thread.sleep(DateUtils.TIME_MILLSECOND_OF_SECOND * 20);
						} catch (InterruptedException e) {
							UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
						}
						num = ChildManager.getInstance().getExecutorJobNum();
					}
					UdsUtils.childShutDownJobShutDown(UdsConstant.SERVER_NAME);
				}
				udsRpcServer.shutDown();
				System.out.println("udsRpcServer close!");
				SchedulerManager.getInstance().shutDown();
				System.out.println("SchedulerManager is close");
				BackgroundServer.getInstance().shutDown();
				System.err.println("end--close--!!!!!!!!!!" + UdsConstant.SERVER_NAME);
			}
		}));
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		Server.applicationContext = applicationContext;
	}

	public static UdsRpcServer getUdsRpcServer() {
		return udsRpcServer;
	}

	public static void setUdsRpcServer(UdsRpcServer udsRpcServer) {
		Server.udsRpcServer = udsRpcServer;
	}

}
