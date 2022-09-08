package cn.spdb.harrier.server.master.stream;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.FileUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.server.master.MasterManagerService;

@Component
public class SignalManager implements Runnable {

	/**
	 * 文件加载
	 */
	private Hashtable<String, Long> stableCheckSize = new Hashtable<String, Long>();

	private final AtomicBoolean STOP = new AtomicBoolean(false);

	@Value("${harrier.signal.receiver.path:}")
	private String RECEIVER_DIR_PATH;

	@Value("${harrier.signal.filename.matche:^dir.[0-9]+@[0-9A-Z_]+[0-9]{8,}$}")
	private String RECEIVER_SINGNAL_MATCHE;
	
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
		if (file.exists() && file.isFile() && isSizeStable(file)) {
			String fileName = file.getName();
			// 正则表达式匹配dir.开头至少一个数字@只要有一个字段包含数字大写字母下划线结尾8位数字
			if (fileName.matches(RECEIVER_SINGNAL_MATCHE)) {
				String strDate = fileName.substring(fileName.length() - 8);
				if (DateUtils.isDate(strDate, DateUtils.PATTERN_YYYYMMDD_CONS)) {
					return true;
				}
			}
		}
		return false;
	}

	public JobSignal createSignalFileBean(File file) {
		String fileName = file.getName();
		String[] tmp = fileName.split(Symbol.AT);
		String signal = tmp[1].substring(0, tmp[1].length() - 8);
		String jobDate = tmp[1].substring(tmp[1].length() - 8);
		String[] batchStr = tmp[0].split(Symbol.DIAN);
		int batch = Integer.valueOf(batchStr[1]);
		JobSignal jobSignal = new JobSignal();
		jobSignal.setBatch(batch);
		jobSignal.setJobDate(jobDate);
		jobSignal.setSignal(signal);
		jobSignal.setEnvs(null);
		jobSignal.setParams(null);
		try {
			String jobStr = FileUtils.readFileToString(file);
			if (!StringUtils.isEmpty(jobStr)) {
				JSONObject jsonObject = JSON.parseObject(jobStr);
				HashMap<String, String> params = jsonObject.getObject("params", HashMap.class);
				if (!ObjectUtils.isEmpty(params)) {
					jobSignal.setParams(params);
				}
				HashMap<String, String> envs = jsonObject.getObject("envs", HashMap.class);
				if (!ObjectUtils.isEmpty(envs)) {
					jobSignal.setEnvs(envs);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jobSignal;
	}

	public void start() {
		STOP.set(false);
	}

	public void stop() {
		STOP.set(true);
	}

	@Override
	public void run() {
		if (STOP.get()||StringUtils.isEmpty(RECEIVER_DIR_PATH)) {
			return;
		}
		File dirFile = new File(RECEIVER_DIR_PATH);
		File[] files = dirFile.listFiles();
		for (File file : files) {
			if (checkSignalFile(file)) {
				JobSignal jobSignal = createSignalFileBean(file);
				BeanContext.getBean(MasterManagerService.class).streamJobSignal(jobSignal);
			}
		}

	}
}
