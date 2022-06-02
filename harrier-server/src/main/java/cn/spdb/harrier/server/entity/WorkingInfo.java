package cn.spdb.harrier.server.entity;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import cn.spdb.harrier.common.uitls.Host;

public class WorkingInfo {

	private Integer id;

	private Host host = new Host();

	private double cpu;

	private double memory;

	private double loadAverage;

	private AtomicInteger jobNum = new AtomicInteger();

	private Integer jobNumMax = 0;

	private LocalDateTime update = LocalDateTime.now();

	// worker job sum weight map
	private ConcurrentHashMap<Integer, Integer> weightMap = new ConcurrentHashMap<Integer, Integer>();

	// platform system num map
	private ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> jobNumMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();

	public synchronized void incrementJob(JobExecutionContext jobExecutionContext) {
		ConcurrentHashMap<String, Integer> systemMap = jobNumMap.get(jobExecutionContext.getPlatform());
		if (systemMap == null) {
			systemMap = new ConcurrentHashMap<String, Integer>();
			jobNumMap.put(jobExecutionContext.getPlatform(), systemMap);
		}
		Integer jobNum = systemMap.get(jobExecutionContext.getSystem());
		if (jobNum == null) {
			systemMap.put(jobExecutionContext.getSystem(), 1);
		} else {
			systemMap.put(jobExecutionContext.getSystem(), jobNum + 1);
		}
		this.jobNum.incrementAndGet();
		jobExecutionContext.getWeightMap().forEach((k, v) -> {
			Integer weightNum = weightMap.get(k);
			if (weightNum == null) {
				weightMap.put(k, v);
			} else {
				weightMap.put(k, weightNum + v);
			}
		});

	}

	public synchronized void decrementJob(JobExecutionContext jobExecutionContext) {
		ConcurrentHashMap<String, Integer> systemMap = jobNumMap.get(jobExecutionContext.getPlatform());
		if (systemMap != null) {
			Integer jobNum = systemMap.get(jobExecutionContext.getSystem());
			systemMap.put(jobExecutionContext.getSystem(), jobNum = jobNum - 1 < 0 ? 0 : jobNum - 1);
		}
		this.jobNum.decrementAndGet();
		jobExecutionContext.getWeightMap().forEach((k, v) -> {
			Integer weightNum = weightMap.get(k);
			if (weightNum != null) {
				weightMap.put(k, weightNum = weightNum - v < 0 ? 0 : weightNum - v);
			}
		});

	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getLoadAverage() {
		return loadAverage;
	}

	public void setLoadAverage(double loadAverage) {
		this.loadAverage = loadAverage;
	}

	public AtomicInteger getJobNum() {
		return jobNum;
	}

	public void setJobNum(AtomicInteger jobNum) {
		this.jobNum = jobNum;
	}

	public ConcurrentHashMap<Integer, Integer> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(ConcurrentHashMap<Integer, Integer> weightMap) {
		this.weightMap = weightMap;
	}

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getJobNums() {
		return jobNumMap;
	}

	public void setJobNums(ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> jobNums) {
		this.jobNumMap = jobNums;
	}

	public Integer getJobNumMax() {
		return jobNumMax;
	}

	public void setJobNumMax(Integer jobNumMax) {
		this.jobNumMax = jobNumMax;
	}

	public LocalDateTime getUpdate() {
		return update;
	}

	public void setUpdate(LocalDateTime update) {
		this.update = update;
	}

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getJobNumMap() {
		return jobNumMap;
	}

	public void setJobNumMap(ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> jobNumMap) {
		this.jobNumMap = jobNumMap;
	}

	public int getHostWeight() {
		return getHostWeight(2, 3, 5);
	}

	public int getHostWeight(int cpuLv, int memoryLv, int loadAverageLv) {
		double calculatedWeight = 0;
		if (loadAverage > 0) {
			int totl = cpuLv + memoryLv + loadAverageLv;
			calculatedWeight = (1 - cpu) * (cpuLv / totl) + (1 - memory) * (memoryLv / totl)
					+ (1 - loadAverage) * (loadAverageLv / totl);
		} else {
			int totl = cpuLv + memoryLv;
			calculatedWeight = (1 - cpu) * (cpuLv / totl) + (1 - memory) * (memoryLv / totl);
		}
		return (int) calculatedWeight * 10000;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWeightValue(Integer limitType) {
		return weightMap.get(limitType);
	}

	public Integer getJobNumByPlatformAndSystem(String platform, String systems) {
		ConcurrentHashMap<String, Integer> map = jobNumMap.get(platform);
		if (map == null) {
			return 0;
		}
		Integer num = map.get(systems);
		return num == null ? 0 : num;
	}

}
