package cn.com.spdb.uds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.db.dao.UdsJobWeightDao;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;
import io.netty.util.NettyRuntime;

/**
 * UDS常量配置表 final 修饰不可改变
 * 
 * @author T-luzl
 *
 */
public class UdsConstant {

	private final static Logger LOGGER = LoggerFactory.getLogger(UdsConstant.class);
	public final static String Version = "1.0.3_20191118";

	public final static int TRUE_NUM = 1;
	public final static int FALSE_NUM = 0;
	// 主节点分发服务器
	public final static int SERVER_TYPE_MASTER = 1;
	// 子节点执行服务器
	public final static int SERVER_TYPE_CHILD = 0;

	// ---------配置文件重要常量 file:./config/uds.configuration.properties------//

	// 服务器名称，唯一设置
	public static String SERVER_NAME = "";
	// 脚本路径前缀
	public static String SCRIPT_PATH_PERFIX = "$AUTO_HOME";
	// 生成目录文件位置
	public static String AUTO_HOME = "/uds";
	// 临时文件
	public static String AUTO_TMP_DIR_PATH = AUTO_HOME + "/tmp";
	public static String AUTO_BIN_DIR_PATH = AUTO_HOME + "/bin";
	public static String AUTO_ETC_DIR_PATH = AUTO_HOME + "/etc";
	// 等待检查
	public static String AUTO_RECEIVER_DIR_PATH = AUTO_HOME + "/data/receive";
	// 完成
	public static String AUTO_COMPLETE_DIR_PATH = AUTO_HOME + "/data/complete";
	// 失败信号
	public static String AUTO_FAIL_DIR_PATH = AUTO_HOME + "/data/fail";
	// 作业错误
	public static String AUTO_ERROR_DIR_PATH = AUTO_HOME + "/data/error";
	// 未来时间可能触发的信号文件
	public static String AUTO_STREAM_DIR_PATH = AUTO_HOME + "/data/stream";
	// 未知信号文件
	public static String AUTO_UNKNOW_DIR_PATH = AUTO_HOME + "/data/unknown";
	// 作业日志
	public static String AUTO_JOB_LOG_DIR_PATH = AUTO_HOME + "/log";
	// 作业
	public static String AUTO_APP_DIR_PATH = AUTO_HOME + "/app";

	// 是否启用自动清除脚本日志
	public static int CLEAR_JOB_LOG = 0;
	
	//clear Platform map time
	public static int CLEAR_PLATFORM_MAP = DateUtils.TIME_MILLSECOND_OF_DAY;

	public static int TRANSFER_STEP_UTF8 = 0;

	// -----------------服务器配置常量信息-----------------------//

	// 本机是否为主节点
	public static boolean IS_PRIMARY_SERVER = true;
	// 服务器顺序
	public static int ORDER = 0;
	// 本机rpc服务IP
	public static String LOCATE_IP = "127.0.0.1";
	// rpc服务端口89
	public static int RPC_SERVER_PORT = 9696;
	// 后台端口
	public static int BACKEND_PORT = 6969;
	// HTTP端口
	public static int HTTP_PORT = 7878;
	// 后台接口关闭时间
	public static int BACKEND_TIMEOUT_SECOND = 5 * 60 * 1000;
	// 服务器性能比 千分比
	public static short PERFORMANCE_RATIO = 1000;
	// 服务器启用
	public static byte SERVER_ENABLE = 1;
	// 作业最大并发数
	public static short MAX_JOB_NUM = 30;
	// 地域按照位区分 
	public static int LOCATION = 1;
	// 程序脚本路径
	public static String SERVER_SCRIPT_DIR_PATH = "./script";
	// 程序常用命令和默认脚本执行路径
	public static String SERVER_BIN_DIR_PATH = "./bin";

	public static byte SEND_LOCATE = 0;

	public static int AVAILABLE_PROCESSORS_SIZE = NettyRuntime.availableProcessors();

	// 经过几次轮询获取数据库中分发的数据
	public static int CHECK_DB_DISPATCHER_NUM = 10;

	public static int DISPATCHER_OVER_MINUTE = 5;

	// -------------所有服务器通用常量-------------------------//

	public static byte SIGNAL_FILE_NAME_MIN_LONG = 12;
	public static byte SIGNAL_FILE_NAME_DATE_LONG = 8;
	// 强制执行脚本作业数
	public static int FORCESTART_LIST_JOB = 200;

	public static byte SIGNAL_FILE_NAME_PREFIX_LONG = 4;
	public static String SIGNAL_FILE_NAME_PREFIX = "dir.";
	public static int CHECK_DB_PENDING_LIMIT_NUM = 3000;
	public static int CHECK_PENDING_LIMIT_NUM = CHECK_DB_PENDING_LIMIT_NUM / 5;
	public static int CHECK_PENDING_DEAL_OVER_ITMES = 30;
	public static int UDS_LOGGER_ERROR_MAX = 10000;

	// 触发作业失败删除是否创建信号文件
	public static byte STREAM_FAIL_CREATE_SIGN_STREAM = 0;
	// 接受信号文件后，需要运行却不满足条件的移出
	public static byte RCV_SIGN_FAIL_MOVE_STREAM = 0;
	// 定时作业，需要运行却不满足条件的移出
	public static byte SCHEDULER_FAIL_MOVE_STREAM = 0;
	// 重调作业时间
	public static int CALL_AGAIN_JOB_TIME = 500;

	public static byte DEP_STREAM = 0;

	// 系统
	public static HashMap<String, UdsSystemBean> MAP_SYSTEM_JOB = new HashMap<String, UdsSystemBean>();

	public static HashMap<String, ArrayList<Integer>> FINSH_NOTICE_JOB_MAP = new HashMap<String, ArrayList<Integer>>();

	// 机器标签
	public static HashMap<String, ArrayList<String>> SERVER_TAGS = new HashMap<String, ArrayList<String>>();

	// 权值
	private static HashMap<Integer, Integer> LIMIT_WEIGHT = new HashMap<Integer, Integer>();

	public static void loadNoticeFinshJob() {
		final String select_table = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.`TABLES` WHERE "
				+ "TABLE_SCHEMA=(SELECT DATABASE()) AND TABLE_NAME='m_alarmforjobs';";
		final String select_sql = "SELECT job,BATCH_NO FROM m_alarmforjobs "
				+ "WHERE custom_alarm_type=2 AND alarmstatus=1;";
		BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
		List<HashMap<Object, Object>> list = baseDao.select(select_table);
		if (!list.isEmpty()) {
			FINSH_NOTICE_JOB_MAP.clear();
			list = baseDao.select(select_sql);
			if (!list.isEmpty()) {
				for (HashMap<Object, Object> map : list) {
					String job = (String) map.get("job");
					Integer batch = (Integer) map.get("BATCH_NO");
					if (!StringUtils.isBlank(job)) {
						ArrayList<Integer> tmpList = FINSH_NOTICE_JOB_MAP.get(job);
						if (tmpList == null) {
							FINSH_NOTICE_JOB_MAP.put(job, tmpList = new ArrayList<Integer>());
						}
						batch = batch < 0 ? 0 : batch;
						if (!tmpList.contains(batch)) {
							tmpList.add(batch);
						}
					}
				}
			}
		}
	}

	public static void load() {
		UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);

		// 加载配置常量
		loadProperties();

		UdsServerBean udsServerBean = udsServerDao.getUdsServerByName(UdsConstant.SERVER_NAME);
		if (udsServerBean == null) {
			String ip = "";
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			udsServerBean = udsServerDao.getUdsServerByIp(ip);
		}

		if (udsServerBean == null) {
			LOGGER.error(
					"this server not register database check table uds_server.ip is not local host ip or uds_server.server_name not exist");
			System.exit(1);
			return;
		}
		if (udsServerBean.getIs_enable() != TRUE_NUM) {
			LOGGER.error("this server  udsServerBean.isenable is unenable");
			System.exit(1);
			return;
		}
		// --------加载服务器全局常量
		loadConstant();
		// ---------加载服务器常量
		loadServerConstant(udsServerBean);
		// --------加载指定作业告警
		if (!UdsConstant.IS_PRIMARY_SERVER) {
			loadNoticeFinshJob();
		} else {
			// 注册标签
			loadServerTags();

			// 注册权重
			loadWeightLimitMap();
		}
		// 创建目录
		try {
			for (Field field : UdsConstant.class.getDeclaredFields()) {
				if (field.getName().endsWith("DIR_PATH")) {
					String dirPath = (String) field.get(null);
					if (dirPath != null) {
						File dir = new File(dirPath);
						if (!dir.exists()) {
							dir.mkdirs();
							LOGGER.info("create dir : " + field.get(null));
						}
					}
				}
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		}
		// 加载平台信息
		loadUdsSystemMap();

		LOGGER.info("load UdsConstant OK!");
	}

	public static void loadServerTags() {
		UdsServerDao dao = DBManager.getInstance().getDao(UdsServerDao.class);
		List<UdsServerBean> list = dao.getUdsServerList();
		SERVER_TAGS.clear();
		for (UdsServerBean server : list) {
			if (server.getServer_type() == UdsConstant.SERVER_TYPE_CHILD) {
				String tagstr = server.getTags();
				if (!StringUtils.isEmpty(tagstr)) {
					String[] tagArray = server.getTags().split(Symbol.FEN_HAO_REG);
					List<String> tagList = Arrays.asList(tagArray);
					if (tagList.size() > 0) {
						ArrayList<String> tagalist = new ArrayList<String>(tagList);
						SERVER_TAGS.put(server.getServer_name(), tagalist);
					}
				}
			}
		}
	}

	/**
	 * 加载配置文件
	 */
	public static void loadProperties() {
		File file = new File("./config/uds.configuration.properties");
		if (!file.exists()) {
			LOGGER.error("uds.configuration.properties is path error");
			System.exit(0);
			return;
		}
		// 关键配置信息
		Properties properties = new Properties();
		InputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			properties.load(fileIn);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}
		LOGGER.info("Properties load start");
		// 加载配置常量
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			if (!key.matches("[0-9A-Z_]*")) {
				continue;
			}
			Field field = null;
			try {
				field = UdsConstant.class.getDeclaredField(key);

				if (field == null) {
					continue;
				}
				// 不是final 修饰 并且是的static 修饰
				if (!Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
					Object value = JSON.parseObject(entry.getValue().toString(), field.getType());
					field.set(null, value);
					LOGGER.info(field.getName() + " : " + field.get(null));
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
		LOGGER.info("Properties load end");
	}

	/**
	 * 服务器独有常量 一个是config配置文件 一个数据库文件
	 */
	public static void loadServerConstant(UdsServerBean udsServerBean) {
		LOGGER.info("db UdsServerDao load start");
		IS_PRIMARY_SERVER = udsServerBean.getServer_type() == SERVER_TYPE_MASTER ? true : false;
		ORDER = udsServerBean.getOrder();
		LOGGER.info("rpc ORDER:[{}]", ORDER);
		SERVER_NAME = udsServerBean.getServer_name();
		LOGGER.info("rpc SERVER_NAME:[{}]", SERVER_NAME);
		LOCATE_IP = udsServerBean.getIp();
		LOGGER.info("rpc ip:[{}]", LOCATE_IP);
		RPC_SERVER_PORT = udsServerBean.getPort();
		LOGGER.info("rpc RPC_SERVER_PORT:[{}]", RPC_SERVER_PORT);
		BACKEND_PORT = udsServerBean.getAgent_port();
		LOGGER.info("BACKEND_PORT:[{}]", BACKEND_PORT);
		HTTP_PORT = udsServerBean.getHttp_port();
		LOGGER.info("HTTP_PORT:[{}]", HTTP_PORT);
		PERFORMANCE_RATIO = udsServerBean.getPerformance_ratio();
		LOGGER.info("PERFORMANCE_RATIO:[{}]", PERFORMANCE_RATIO);
		MAX_JOB_NUM = udsServerBean.getMax_job_num();
		LOGGER.info("MAX_JOB_NUM:[{}]", MAX_JOB_NUM);
		SERVER_ENABLE = udsServerBean.getIs_enable();
		LOGGER.info("SERVER_ENABLE:[{}]", SERVER_ENABLE);
		LOCATION = udsServerBean.getLocation();
		LOGGER.info("LOCATION:[{}]", LOCATION);
		LOGGER.info("db UdsServerDao load end");
	}

	/**
	 * 数据库全服务器常量加载
	 */
	public static void loadConstant() {
		LOGGER.info("db UdsConstantDao load end");
		// ------加载所有服务器通用量-------------//
		UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);
		HashMap<String, String> cfgVar = udsServerDao.getUdsConstant();
		try {
			// 加载配置常量
			for (Entry<String, String> entry : cfgVar.entrySet()) {
				try {
					Field field = UdsConstant.class.getDeclaredField(entry.getKey().toString());
					if (!Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
						String valueStr = entry.getValue().toString();
						Class<?> zlass = field.getType();
						Object value = JSON.parseObject(valueStr, zlass);
						field.set(null, value);
						LOGGER.info(field.getName() + " : " + field.get(null));
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
						| IllegalAccessException e) {
					LOGGER.error(e.toString());
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		} catch (Exception e1) {
			LOGGER.error(e1.toString());
			e1.printStackTrace();
		}
		LOGGER.info("db UdsConstantDao load end");
	}

	public static void loadUdsSystemMap() {
		UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		List<UdsSystemBean> list = controlDao.getUdsSystemBeanList();
		HashMap<String, UdsSystemBean> map = new HashMap<String, UdsSystemBean>();
		for (UdsSystemBean bean : list) {
			String key = bean.getPlatform() + "_" + bean.getSystem();
			map.put(key, bean);
		}
		MAP_SYSTEM_JOB = map;
	}

	public static UdsSystemBean getUdsSystemBean(String platformSystemKey) {
		UdsSystemBean bean = MAP_SYSTEM_JOB.get(platformSystemKey);
		if (bean != null) {
			return bean;
		}
		if (StringUtils.isBlank(platformSystemKey)) {
			return null;
		}
		String[] tmp = platformSystemKey.split(Symbol.XIA_HUA_XIAN);
		if (tmp.length < 2) {
			return null;
		}
		String platform = tmp[0];
		String system = tmp[1];
		return getUdsSystemBean(platform, system);
	}

	public static UdsSystemBean getUdsSystemBean(String platform, String system) {
		UdsSystemBean bean = getMapSystemValue(platform, system);
		if (bean != null && bean.getUse_platform() == TRUE_NUM) {
			return getMapSystemValue(platform, Symbol.XING_HAO);
		}
		return bean;
	}

	public static UdsSystemBean getMapSystemValue(String platform, String system) {
		String key = platform + Symbol.XIA_HUA_XIAN + system;
		UdsSystemBean bean = MAP_SYSTEM_JOB.get(key);
		if (bean == null) {
			UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
			bean = controlDao.getUdsSystemBean(platform, system);
			if (bean == null) {
				return bean;
			}
			MAP_SYSTEM_JOB.put(key, bean);
		}
		return bean;
	}
	
	public static String getUdsSystemBeanKey(String platform, String system) {
		UdsSystemBean bean=getUdsSystemBean(platform, system);
		if (bean == null ) {
			return null;
		}
		return bean.getPlatformAndSystemKey();
	}

	public static int getWeightLimit(int weightType) {
		Integer value = LIMIT_WEIGHT.get(weightType);
		if (value == null) {
			UdsJobWeightDao jobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
			value = jobWeightDao.getUdsJobWeightLimit(weightType);
			if (value == null) {
				return -1;
			}
			LIMIT_WEIGHT.put(weightType, value);
		}
		return value;
	}

	public static void loadWeightLimitMap() {
		UdsJobWeightDao jobWeightDao = DBManager.getInstance().getDao(UdsJobWeightDao.class);
		HashMap<Integer, Integer> weightLimitMap = jobWeightDao.getUdsJobWeightLimitMap();
		LIMIT_WEIGHT = weightLimitMap;
	}
}
