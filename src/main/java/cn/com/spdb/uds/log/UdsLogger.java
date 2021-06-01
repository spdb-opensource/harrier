package cn.com.spdb.uds.log;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.SchedulerManager;
import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsErrorBean;
import cn.com.spdb.uds.db.dao.UdsJobRecordDao;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;

public class UdsLogger {

	public static final char SPLIT_CHAR = '|';
	private static final Logger ERROR_LOG = LoggerFactory.getLogger(UdsLogger.class);

	/**
	 * 相同错误2分钟介入一次,最大100条
	 */
	private static ConcurrentHashMap<Integer, LogErrorSign> MAP_ERROR = new ConcurrentHashMap<Integer, LogErrorSign>(
			256);

	private static void addErrorLogDb(LogErrorSign logErrorSign) {
		synchronized (MAP_ERROR) {
			MAP_ERROR.put(logErrorSign.hashCode(), logErrorSign);
			// FIXME 虽然我认为这里设置的不合理出现大量报错会有数据大量占用内存，但是有个缓存存储的能力
			if (MAP_ERROR.size() >= UdsConstant.UDS_LOGGER_ERROR_MAX) {
				updateMapErrorDb();
			}
		}
	}

	private static List<UdsErrorBean> tmpErrorList = new ArrayList<UdsErrorBean>(1024);

	public static void updateMapErrorDb() {
		synchronized (MAP_ERROR) {
			for (LogErrorSign loge : MAP_ERROR.values()) {
				UdsErrorBean uds = new UdsErrorBean();
				uds.setMsg(loge.getMsg());
				uds.setMsg_level(loge.getMsg_level());
				uds.setMsg_par(loge.getMsg_par());
				uds.setCode(loge.getCode());
				uds.setMsg_time(new Timestamp(loge.getNow()));
				tmpErrorList.add(uds);
				if (tmpErrorList.size() >= 1024) {
					tmpErrorList.sort(new Comparator<UdsErrorBean>() {

						@Override
						public int compare(UdsErrorBean o1, UdsErrorBean o2) {
							return o1.getMsg_time().compareTo(o2.getMsg_time());
						}
					});
					UdsJobRecordDao dao = DBManager.getInstance().getDao(UdsJobRecordDao.class);
					dao.insertUdsErrorList(tmpErrorList);
					tmpErrorList.clear();
				}
			}
			MAP_ERROR.clear();
		}
		if (!tmpErrorList.isEmpty()) {
			tmpErrorList.sort(new Comparator<UdsErrorBean>() {

				@Override
				public int compare(UdsErrorBean o1, UdsErrorBean o2) {
					return o1.getMsg_time().compareTo(o2.getMsg_time());
				}
			});
			UdsJobRecordDao dao = DBManager.getInstance().getDao(UdsJobRecordDao.class);
			dao.insertUdsErrorList(tmpErrorList);
			tmpErrorList.clear();
		}
	}

	/**
	 * 日志级别为INFOA
	 * 
	 * @param logEvent {@link LogEvent}
	 * @param objects
	 */
	public static void logEvent(LogEvent logEvent, Object... objects) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(logEvent.name()).append(SPLIT_CHAR);
		for (Object object : objects) {
			String msg = "";
			try {
				msg = JSON.toJSONString(object);
			} catch (Exception e) {
				msg = "JSON ERROR";
				ERROR_LOG.error(e.toString());
				e.printStackTrace();
			}
			buffer.append(msg).append(SPLIT_CHAR);
		}
		if (logEvent.getLogType().equals(LogType.ERROR_LOG)) {
			logEvent.getLogType().getLogger().error(buffer.toString());
		} else {
			logEvent.getLogType().getLogger().info(buffer.toString());
		}
	}

	public static void logErrorInstertDbError(UdsErrorCode code, UdsErrorLevel level, Object... objects) {
		StringBuffer msg_parBuffer = new StringBuffer();
		for (Object object : objects) {
			String msg = "";
			try {
				msg = JSON.toJSONString(object);
			} catch (Exception e) {
				msg = "JSON ERROR";
				e.printStackTrace();
			}
			msg_parBuffer.append(msg).append(Symbol.DOU_HAO);
		}
		LogErrorSign logErrorSign = new LogErrorSign(code.code(), level.level(), msg_parBuffer.toString(),
				code.getMsg());
		addErrorLogDb(logErrorSign);
		StringBuffer tmpBuffer = new StringBuffer();
		tmpBuffer.append("UdsErrorCode:").append(code.getCode()).append(Symbol.DOU_HAO).append(code.getMsg())
				.append(Symbol.DOU_HAO).append(msg_parBuffer.toString());
		LogEvent.ERROR.getLogType().getLogger().error(tmpBuffer.toString());
	}

	/**
	 * 错误集合
	 * 
	 * @param logEvent
	 * @param objects
	 */
	public static void error(LogEvent logEvent, Object... objects) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(logEvent.name()).append(SPLIT_CHAR);
		for (Object object : objects) {
			String msg = "";
			try {
				msg = JSON.toJSONString(object);
			} catch (Exception e) {
				msg = "JSON ERROR";
				ERROR_LOG.error(e.toString());
				e.printStackTrace();
			}
			buffer.append(msg).append(SPLIT_CHAR);
		}
		logEvent.getLogType().getLogger().error((buffer.toString()));
	}

	public static void error(String arg0) {
		LogType.ERROR_LOG.getLogger().error(arg0);
	}

	public static void error(String arg0, Object arg1) {
		LogType.ERROR_LOG.getLogger().error(arg0, arg1);
	}

	public static void error(String arg0, Object[] arg1) {
		LogType.ERROR_LOG.getLogger().error(arg0, arg1);
	}

	public static void clearLogschedule() {
		long time = DateUtils.getZeroTime(new Date()).getTime() + DateUtils.TIME_MILLSECOND_OF_DAY
				- System.currentTimeMillis();
		SchedulerManager.getInstance().scheduleWithFixedDelay("CLEAR_JOB_LOG", new Runnable() {

			@Override
			public void run() {
				try {
					int dateNum = Integer.valueOf(DateUtils.getDateTime(new Date(), DateUtils.PATTERN_YYYYMMDD_CONS));
					clearJobLog(new File(UdsConstant.AUTO_JOB_LOG_DIR_PATH), dateNum, System.currentTimeMillis(), 30);
					clearJobLog(new File(UdsConstant.AUTO_UNKNOW_DIR_PATH), dateNum, System.currentTimeMillis(), 30);
					clearJobLog(new File(UdsConstant.AUTO_ERROR_DIR_PATH), dateNum, System.currentTimeMillis(), 30);
					clearJobLog(new File(UdsConstant.AUTO_FAIL_DIR_PATH), dateNum, System.currentTimeMillis(), 30);
					clearJobLog(new File(UdsConstant.AUTO_COMPLETE_DIR_PATH), dateNum, System.currentTimeMillis(), 30);
				} catch (Exception e) {
					UdsLogger.error(e.getMessage());
				}
			}
		}, time, DateUtils.TIME_MILLSECOND_OF_DAY);

	}

	private static void clearJobLog(File file, int dateNum, long now, int day) {
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			if (now - file.lastModified() > day * DateUtils.TIME_MILLSECOND_OF_DAY) {
				file.delete();
				return;
			}
		} else {
			String fileName = file.getName();
			if (fileName.matches("[0-9]+")) {
				if (dateNum - Integer.parseInt(fileName) > day) {
					File[] files = file.listFiles();
					if (files.length == 0) {
						file.delete();
					} else {
						for (File f : files) {
							clearJobLog(f, dateNum, now, day);
						}
					}
				}
			}
			return;
		}
	}

}
