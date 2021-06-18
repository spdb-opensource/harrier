package cn.com.spdb.uds.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class UdsUtils {

	// 获得指定文件的byte数组
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}
		return buffer;
	}

	/**
	 * 作业名： sys_subSys_表名_算法
	 * 
	 * @param ctlFile 批次号@作业名_YYYYMMDD.dir
	 * @return
	 */
	public static String getSys(String ctlFile) {
		String sys = "";
		if (ctlFile.contains("@")) {
			String[] tmp = ctlFile.split("@");
			if (tmp.length > 1) {
				String[] data = tmp[1].split("_");
				if (data.length > 1) {
					sys = data[0].startsWith("CTR") ? data[1] : data[0];
				}
			}
		}
		return sys;
	}

	static public Calendar fromTxDate(String txDate) {
		Calendar c = Calendar.getInstance();
		int year, month, date;
		year = Integer.parseInt(txDate.substring(0, 4));
		month = Integer.parseInt(txDate.substring(4, 6));
		date = Integer.parseInt(txDate.substring(6));
		c.set(year, month - 1, date);
		return c;
	}

	/**
	 * 作业名： sys_subSys_表名_算法
	 * 
	 * @param ctlFile 批次号@作业名_YYYYMMDD.dir
	 * @return
	 */
	public static String getSysSub(String ctlFile) {
		String sysSub = "";
		if (ctlFile.contains("@")) {
			String[] tmp = ctlFile.split("@");
			if (tmp.length > 1) {
				String[] data = tmp[1].split("_");
				if (data.length > 1) {
					sysSub = data[0].startsWith("CTR") ? data[0] : data[1];
					;
				}
			}
		}
		return sysSub;
	}

	// 判断日期是否范围正确
	public static boolean isOKDate(String txDate) {
		int year, month, day;
		if (txDate.length() < 8) // 如果txDate长度小于8返回false
			return false;
		try {
			year = Integer.parseInt(txDate.substring(0, 4));
			month = Integer.parseInt(txDate.substring(4, 6));
			day = Integer.parseInt(txDate.substring(6));
			if ((year < 1) || (month < 1) || (day < 1) || (month > 12) || (day > 31)) {
				return false;
			} else {
				if ((day < 29) // month为2月 day小于29天
						&& (month == 2) // 或者 month不为2月 day小于31天
						|| (day < 31) // 或者 month是大月 day=31天 则返回true
								&& (month != 2)
						|| (day == 31) && ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8)
								|| (month == 10) || (month == 12))) {
					return true;
				} else if ((month == 2) // 判断2月29天时候是否是闰年
						&& (day == 29) && ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))))
					return true;
				else
					return false;
			}

		} catch (NumberFormatException e) {
		}
		return false;
	}

	/**
	 * 作业名： sys_subSys_表名_算法
	 * 
	 * @param ctlFile 批次号@作业名_YYYYMMDD.dir
	 * @return
	 */
	public static String getLastTxData(String ctlFile) {
		return ctlFile.substring(ctlFile.length() - 12, ctlFile.length() - 4);
	}

	/**
	 * 作业名： sys_subSys_表名_算法
	 * 
	 * @param ctlFile 批次号@作业名_YYYYMMDD.dir
	 * @return
	 */
	public static int getFileCnt(String ctlFile) {
		String filecnt = "0";
		if (ctlFile.contains("@")) {
			String[] args_ctl = ctlFile.split("@");
			filecnt = args_ctl[0];
		}
		return Integer.parseInt(filecnt);
	}

	public static boolean moveFile(String dir, File file) {
		if (file == null || !file.exists()) {
			return false;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File targetFile = new File(dir + File.separator + file.getName());
		return file.renameTo(targetFile);
	}

	public static boolean moveToDayFile(String dir, String strFile) {
		File file = new File(strFile);
		if (file == null || !file.exists()) {
			return false;
		}
		File dirFile = new File(dir + File.separator + DateUtils.getDateTime(DateUtils.PATTERN_YYYYMMDD_CONS));
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// File targetFile = new File(dirFile.getPath() + File.separator +
		// file.getName());
		// if (targetFile.exists()) {
		// targetFile.delete();
		// }
		try {
			FileUtils.moveFileToDirectory(file, dirFile, true);
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			return false;
		}
		return true;
		// return file.renameTo(targetFile);
	}

	public static boolean moveToDayFile(String dir, File file) {
		if (file == null || !file.exists()) {
			return false;
		}
		File dirFile = new File(dir + File.separator + DateUtils.getDateTime(DateUtils.PATTERN_YYYYMMDD_CONS));
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File targetFile = new File(dirFile.getPath() + File.separator + file.getName());
		if (targetFile.exists()) {
			targetFile.delete();
		}
		try {
			FileUtils.moveFileToDirectory(file, dirFile, true);
		} catch (IOException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			return false;
		}
		return true;
		// return file.renameTo(targetFile);
	}

	public static void jobErrorMoveToFile(String job, String jobDate, int batch) {
		String pathname = UdsConstant.AUTO_COMPLETE_DIR_PATH + File.separator
				+ DateUtils.getDateTime(DateUtils.PATTERN_YYYYMMDD_CONS) + File.separator + "dir." + batch + "@" + job
				+ jobDate;
		File file = new File(pathname);
		moveToDayFile(UdsConstant.AUTO_ERROR_DIR_PATH, file);
	}

	/**
	 * 数据库对于错误作业设置
	 * 
	 * @param serverName
	 */
	public static void childShutDownJobShutDown(String serverName) {
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// baseDao.updateChildJobStatus(serverName, JobStatus.PENDING,
		// JobStatus.DISPATCHER);
		baseDao.updateChildJobStatus(serverName, JobStatus.UNKNOWN, JobStatus.RUNING);
	}

	public static String linkServerMachine() {
		UdsServerDao dao = DBManager.getInstance().getDao(UdsServerDao.class);
		List<UdsServerBean> list = dao.getUdsServerList();
		StringBuffer buffer = new StringBuffer();
		for (UdsServerBean serverBean : list) {
			try {
				UdsRpcClientManager.getInstance().removeUdsRpcClient(serverBean.getServer_name());
				// 机器子节点不和子节点相联系
				if (!UdsConstant.IS_PRIMARY_SERVER && serverBean.getServer_type() == UdsConstant.SERVER_TYPE_CHILD) {
					continue;
				}
				// 我自己
				if (UdsConstant.SERVER_NAME.equals(serverBean.getServer_name())) {
					continue;
				}
				UdsRpcClient udsRpcClient = UdsRpcClientManager.getInstance().addUdsRpcClient(serverBean);
				if (udsRpcClient == null) {
					buffer.append(
							"try link server error Name:" + serverBean.getServer_name() + " ip:" + serverBean.getIp())
							.append("\r\n");
					continue;
				}
				UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(udsRpcClient.getServerName(),
						RpcCommand.SERVER_REGISTER);
				UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent, null);
				buffer.append("try link server Name:" + serverBean.getServer_name() + " ip:" + serverBean.getIp())
						.append("\r\n");
			} catch (Exception e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				buffer.append("try link server error Name:" + serverBean.getServer_name() + " ip:" + serverBean.getIp())
						.append("\r\n");
			}
		}
		return buffer.toString();
	}

	// FIXME 这个方法很不友善大量的IO吞吐，建议在数据库添加batch的计数
	/**
	 * 这个方法很不友善大量的IO吞吐，建议在数据库添加batch的计数
	 * 
	 * @return
	 */
	public static boolean createSignFileIncreaseBatch(String job, int batch, String jobDate, String dirPtah) {
		String signFileKey = job;
		StringBuffer filePathBuffer = new StringBuffer();
		filePathBuffer.append(dirPtah);
		File dir = new File(filePathBuffer.toString());
		if (!dir.exists()) {
			return false;
		}
		filePathBuffer.append(Symbol.XIE_XIAN).append(UdsConstant.SIGNAL_FILE_NAME_PREFIX).append(batch)
				.append(Symbol.AT).append(signFileKey).append(jobDate);
		String signFile = filePathBuffer.toString();
		String regex = UdsConstant.SIGNAL_FILE_NAME_PREFIX + "\\d+" + Symbol.AT;
		// 290 按照一天5分一批 一天最多288批
		for (int i = batch + 1; i < 290; i++) {
			File file = new File(signFile);
			if (!file.exists()) {
				try {
					return file.createNewFile();
				} catch (IOException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					return false;
				}
			} else {
				String replacement = UdsConstant.SIGNAL_FILE_NAME_PREFIX + i + Symbol.AT;
				signFile = signFile.replaceAll(regex, replacement);
			}
		}
		return true;
	}

	public static boolean createSignFile(String job, int batch, String jobDate, String dirPtah) {
		// FIXME 这里不使用源信号文件名，使用作业名，减少数据库操作
		// UdsJobBaseDao udsJobBaseDao =
		// DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// String signFileKey = udsJobBaseDao.getSignFileKeyByJob(job);
		// if (StringUtils.isEmpty(signFileKey)) {
		// return false;
		// }
		String signFileKey = job;
		StringBuffer filePathBuffer = new StringBuffer();
		filePathBuffer.append(dirPtah);
		File dir = new File(filePathBuffer.toString());
		if (!dir.exists()) {
			return false;
		}
		filePathBuffer.append(Symbol.XIE_XIAN).append(UdsConstant.SIGNAL_FILE_NAME_PREFIX).append(batch)
				.append(Symbol.AT).append(signFileKey).append(jobDate);
		File file = new File(filePathBuffer.toString());
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
		return true;
	}

	public static boolean isExists(String strDir, String strFile) {
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(strDir).append(Symbol.XIE_XIAN).append(strFile);
		File file = new File(pathBuffer.toString());
		if (file.exists() && file.isFile()) {
			return true;
		}
		return false;
	}

	public static boolean isExists(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			return true;
		}
		return false;
	}

	

	public static String guessEncodeS(byte[] bs) {
		BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
		String fileCode = BytesEncodingDetect.javaname[bytesEncodingDetect.detectEncoding(bs)];
		return fileCode;
	}

	public static String guessEncodeFile(File file) {
		if (!file.exists()) {
			return null;
		}
		BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
		String fileCode = BytesEncodingDetect.javaname[bytesEncodingDetect.detectEncoding(file)];
		return fileCode;
	}

}
