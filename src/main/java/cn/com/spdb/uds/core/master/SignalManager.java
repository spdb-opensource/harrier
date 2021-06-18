package cn.com.spdb.uds.core.master;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.UdsUtils;

public class SignalManager {

	private final static Object KEY = new Object();
	public static SignalManager instance;
	/**
	 * 文件加载
	 */
	private HashMap<String,Long> stableCheckSize = new HashMap<String,Long>();

	public static SignalManager getInstance() {
		synchronized (KEY) {
			if (instance == null) {
				instance = new SignalManager();
			}
		}
		return instance;
	}

	/**
	 * 静态方法 检查控制文件大小稳定后接受
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean isSizeStable(File file) {
		String filePath = file.getPath();
		long len = file.length();
		Long oldLen = stableCheckSize.get(filePath);
		if (oldLen == null) {
			stableCheckSize.put(filePath, len);
			return false;
		}
		if (oldLen < len) {
			stableCheckSize.put(filePath, len);
			return false;
		}
		stableCheckSize.remove(filePath);
		return true;
	}

	/**
	 * 信号文件检测
	 * 
	 * @param file
	 * @return
	 */
	public boolean checkSignalFile(File file) {
		if (isSizeStable(file)) {
			String fileName = file.getName();
			// 正则表达式匹配dir.开头至少一个数字@只要有一个字段包含数字大写字母下划线结尾8位数字
			if (fileName.matches("^dir.[0-9]+@[0-9A-Z_]+[0-9]{8,}$")) {
				String strDate = fileName.substring(fileName.length() - 8);
				if (DateUtils.isDate(strDate, DateUtils.PATTERN_YYYYMMDD_CONS)) {
					UdsLogger.logEvent(LogEvent.MASTER_SIGNAL, "receive file ", fileName);
					return true;
				}
			}
			UdsUtils.moveToDayFile(UdsConstant.AUTO_UNKNOW_DIR_PATH, file);
			UdsLogger.logErrorInstertDbError(UdsErrorCode.SIGNA_NORMALIZE_ERROR, UdsErrorLevel.M, fileName);
		}
		return false;
	}

	public SignalFileInfo createSignalFileBean(File file) {
		return SignalFileInfo.bulidSignalFileBean(file);
	}

}
