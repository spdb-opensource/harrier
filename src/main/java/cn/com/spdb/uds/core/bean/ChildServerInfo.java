package cn.com.spdb.uds.core.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;

public class ChildServerInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	// 服务器名字
	private String name;
	// 本机最大作业数
	private short maxJobNum;
	// 性能比值
	private short performanceRatio;

	private byte enable;

	private volatile int jobNum;

	private long updateTime;

	// 平台应用运行作业数
	public ConcurrentHashMap<String, Integer> systemJobMap = new ConcurrentHashMap<String, Integer>();

	// 权重
	public ConcurrentHashMap<Integer, Integer> weightMap = new ConcurrentHashMap<Integer, Integer>();

	/**
	 * 获取当前机器运行作业的总数
	 * 
	 * @return
	 */
	public int getJobNum() {
		return jobNum;
	}

	/**
	 * 增加权值
	 * 
	 * @param weightType
	 * @param value
	 */
	public void addWeightValue(int weightType, int value) {
		synchronized (weightMap) {
			Integer num = weightMap.get(weightType);
			if (num == null) {
				weightMap.put(weightType, value);
			} else {
				weightMap.put(weightType, value + num);
			}
		}
		updateTime = System.currentTimeMillis();
	}

	/**
	 * 减少权值
	 * 
	 * @param weightType
	 * @param value
	 */
	public void subWeightValue(int weightType, int value) {
		synchronized (weightMap) {
			Integer num = weightMap.get(weightType);
			if (num != null) {
				int tmpValue = num - value;
				if (tmpValue > 0) {
					weightMap.put(weightType, tmpValue);
				} else {
					weightMap.put(weightType, 0);
				}
			}
		}
		updateTime = System.currentTimeMillis();
	}

	/**
	 * key:weightType,value:weightValue
	 * 
	 * @param map
	 */
	public void addWeightValueMap(HashMap<Integer, Integer> map) {
		for (Entry<Integer, Integer> en : map.entrySet()) {
			int weightType = en.getKey();
			int value = en.getValue();
			addWeightValue(weightType, value);
		}
	}

	/**
	 * key:weightType,value:weightValue
	 * 
	 * @param map
	 */
	public void subWeightValueMap(HashMap<Integer, Integer> map) {
		for (Entry<Integer, Integer> en : map.entrySet()) {
			int weightType = en.getKey();
			int value = en.getValue();
			subWeightValue(weightType, value);
		}
	}

	public int getWeightValue(int weightType) {
		Integer value = weightMap.get(weightType);
		return value != null ? value : 0;
	}

	/**
	 * 
	 * @param key {@link UdsJobBean} : platform+"_"+system
	 * @return
	 */
	public int getSystemJob(String platform, String system) {
		String key = UdsConstant.getUdsSystemBeanKey(platform, system);
		Integer num = 0;
		synchronized (systemJobMap) {
			num = systemJobMap.get(key);
			if (num == null) {
				return 0;
			}
		}
		return num;
	}

	public boolean isStabilize() {
		return System.currentTimeMillis() - updateTime > 2 * DateUtils.TIME_MILLSECOND_OF_MINUTE;
	}

	
	private void incrementJob(String key) {
		synchronized (systemJobMap) {
			Integer num = systemJobMap.get(key);
			if (num == null) {
				systemJobMap.put(key, 1);
			} else {
				systemJobMap.put(key, num + 1);
			}
		}
	}
	
	public void incrementPlatformAndSystemJob(String platform, String system) {
		UdsSystemBean udssystem  = UdsConstant.getUdsSystemBean(platform, system);
		String key=platform+Symbol.XIA_HUA_XIAN+Symbol.XING_HAO;
		incrementJob(key);
		if(!udssystem.getSystem().equals(Symbol.XING_HAO)) {
			key=udssystem.getPlatformAndSystemKey();
			incrementJob(key);
		}
		jobNum++;
		updateTime = System.currentTimeMillis();
	}
	
	public void incrementSystemJob(String platform, String system) {
		String key = UdsConstant.getUdsSystemBeanKey(platform, system);
		incrementJob(key);
		jobNum++;
		updateTime = System.currentTimeMillis();
	}

	private void decrementJob(String key) {
		synchronized (systemJobMap) {
			Integer num = systemJobMap.get(key);
			if (num != null) {
				num = num > 0 ? num - 1 : 0;
				systemJobMap.put(key, num);
			}
		}
	}
	public void decrementtPlatformAndSystemJob(String platform, String system) {
		UdsSystemBean udssystem  = UdsConstant.getUdsSystemBean(platform, system);
		String key=platform+Symbol.XIA_HUA_XIAN+Symbol.XING_HAO;
		decrementJob(key);
		if(!udssystem.getSystem().equals(Symbol.XING_HAO)) {
			key=udssystem.getPlatformAndSystemKey();
			decrementJob(key);
		}
		jobNum--;
		if (jobNum < 0) {
			jobNum = 0;
		}
		updateTime = System.currentTimeMillis();
	}
	
	public void decrementSystemJob(String platform, String system) {
		String key = UdsConstant.getUdsSystemBeanKey(platform, system);
		decrementJob(key);
		jobNum--;
		if (jobNum < 0) {
			jobNum = 0;
		}
		updateTime = System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getMaxJobNum() {
		return maxJobNum;
	}

	public void setMaxJobNum(short maxJobNum) {
		this.maxJobNum = maxJobNum;
	}

	public short getPerformanceRatio() {
		return performanceRatio;
	}

	public void setPerformanceRatio(short performance_ratio) {
		this.performanceRatio = performance_ratio;
	}

	public ConcurrentHashMap<String, Integer> getSystemJobMap() {
		return systemJobMap;
	}

	public void setSystemJobMap(ConcurrentHashMap<String, Integer> systemJobMap) {
		this.systemJobMap = systemJobMap;
	}

	public byte getEnable() {
		return enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	public void setJobNum(int jobNum) {
		this.jobNum = jobNum;
	}

	public ConcurrentHashMap<Integer, Integer> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(ConcurrentHashMap<Integer, Integer> weightMap) {
		this.weightMap = weightMap;
	}

}
