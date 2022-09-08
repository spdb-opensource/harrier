package cn.spdb.harrier.server.master.weight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsJobWeight;
import cn.spdb.harrier.dao.entity.UdsJobWeightLimit;
import cn.spdb.harrier.dao.mapper.UdsJobWeightLimitMapper;
import cn.spdb.harrier.dao.mapper.UdsJobWeightMapper;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.WorkingInfo;

@Component
public class WeightManger {

	private static Logger logger = LoggerFactory.getLogger("Master-"+WeightManger.class.getSimpleName());

	@Autowired
	private UdsJobWeightLimitMapper jobWeightLimitMapper;

	@Autowired
	private UdsJobWeightMapper jobWeightMapper;

	/**
	 * type:weight
	 */
	private static HashMap<Integer, ArrayList<UdsJobWeightLimit>> weightCountMap = new HashMap<Integer, ArrayList<UdsJobWeightLimit>>();

	/**
	 * serverid:weight
	 */
	private static HashMap<Integer, ArrayList<UdsJobWeightLimit>> weightServerMap = new HashMap<Integer, ArrayList<UdsJobWeightLimit>>();

	public Collection<WorkingInfo> check(JobExecutionContext jobExecutionContext,
			final Collection<WorkingInfo> collection) {

 		if (collection.isEmpty() || !jobExecutionContext.getUdsJobConfig().getCheckWeight()) {
			return collection;
		}

		HashMap<Integer, Integer> weightMap = loadAndGetWeightMapOfJobContext(jobExecutionContext);

		if (weightMap.isEmpty()) {
			return collection;
		}

		// 待处理节点
		ArrayList<WorkingInfo> hosts = new ArrayList<WorkingInfo>(collection);

		// 当前资源类型总和
		HashMap<Integer, Integer> nowWeightMap = new HashMap<Integer, Integer>();

		// 机器id和机器信息
		HashMap<Integer, WorkingInfo> hostById = new HashMap<Integer, WorkingInfo>();

		// 数据处理
		for (WorkingInfo info : hosts) {
			hostById.putIfAbsent(info.getId(), info);
			for (Entry<Integer, Integer> en : info.getWeightMap().entrySet()) {
				Integer key = en.getKey();
				Integer valueNow = nowWeightMap.get(key);
				if (valueNow == null) {
					nowWeightMap.put(key, en.getValue());
				} else {
					nowWeightMap.put(key, en.getValue() + valueNow);
				}
			}
		}

		boolean bool = CheckCountWeight(weightMap, nowWeightMap);
		if (!bool) {
			return new ArrayList<WorkingInfo>();
		}
		return checkAndGetHost(weightMap, hostById);
	}

	/**
	 * 
	 * @param weightMap 作业需要资源的多少
	 * @param hostById  当前需要过滤的机器
	 * @return
	 */
	private ArrayList<WorkingInfo> checkAndGetHost(HashMap<Integer, Integer> weightMap,
			HashMap<Integer, WorkingInfo> hostById) {
		ArrayList<WorkingInfo> hosts = new ArrayList<WorkingInfo>();
		// 逐个机检测
		for (Entry<Integer, WorkingInfo> en : hostById.entrySet()) {
			ArrayList<UdsJobWeightLimit> weightServerList = weightServerMap.get(en.getKey());
			WorkingInfo hostInfo = en.getValue();
			boolean add = true;
			if (weightServerList != null) {
				for (Entry<Integer, Integer> entry : weightMap.entrySet()) {
					boolean exist = false;
					boolean noOne = false;
					for (UdsJobWeightLimit limit : weightServerList) {
						if (limit.getLimitType() == entry.getKey()
								&& DateUtils.isTimeWindowRang(limit.getTimeWinds())) {
							exist = true;
							if (limit.getLimitValue() < hostInfo.getWeightValue(limit.getLimitType())
									+ entry.getValue()) {
								noOne = true;
								break;
							}
						}
					}

					if (exist) {
						if (noOne) {
							add = false;
							break;
						}
					} else {
						boolean countExit = null == weightCountMap.get(entry.getKey());
						if (countExit) {
							add = false;
							break;
						}
					}
				}
			} else {
				for (Entry<Integer, Integer> entry : weightMap.entrySet()) {
					boolean countExit = null == weightCountMap.get(entry.getKey());
					if (countExit) {
						add = false;
						break;
					}
				}
			}

			if (add) {
				hosts.add(hostInfo);
			}

		}
		return hosts;
	}

	/**
	 * 检查总体资源是否满足
	 * 
	 * @param weightMap
	 * @param nowWeightMap
	 * @return
	 */
	private boolean CheckCountWeight(HashMap<Integer, Integer> weightMap, HashMap<Integer, Integer> nowWeightMap) {
		for (Entry<Integer, Integer> en : weightMap.entrySet()) {
			Integer jobWeightValue = en.getValue();
			if (jobWeightValue == 0) {
				continue;
			}
			int type = en.getKey();
			ArrayList<UdsJobWeightLimit> weightSet = weightCountMap.get(type);
			if (null == weightSet) {
				continue;
			}
			for (UdsJobWeightLimit weightCount : weightSet) {
				if (DateUtils.isTimeWindowRang(weightCount.getTimeWinds())) {
					Integer nowWeightValue = nowWeightMap.get(type);
					if (null == nowWeightValue) {
						nowWeightValue = 0;
					}
					int nowValue = nowWeightValue + jobWeightValue;
					if (nowValue > weightCount.getLimitValue()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取
	 * 
	 * @param jobExecutionContext
	 * @return
	 */
	private HashMap<Integer, Integer> loadAndGetWeightMapOfJobContext(JobExecutionContext jobExecutionContext) {
		HashMap<Integer, Integer> weightMap = jobExecutionContext.getWeightMap();
		if (weightMap == null) {
			List<UdsJobWeight> list = jobWeightMapper.select(jobExecutionContext.getPlatform(),
					jobExecutionContext.getSystem(), jobExecutionContext.getJob());
			weightMap = new HashMap<Integer, Integer>();
			for (UdsJobWeight udsJobWeight : list) {
				weightMap.putIfAbsent(udsJobWeight.getLimitType(), udsJobWeight.getConfValue());
			}
			jobExecutionContext.setWeightMap(weightMap);
		}
		return weightMap;
	}

	@PostConstruct
	public void load() {
		HashMap<Integer, ArrayList<UdsJobWeightLimit>> weightCountMap = new HashMap<Integer, ArrayList<UdsJobWeightLimit>>();
		HashMap<Integer, ArrayList<UdsJobWeightLimit>> weightServerMap = new HashMap<Integer, ArrayList<UdsJobWeightLimit>>();
		List<UdsJobWeightLimit> list = jobWeightLimitMapper.select(c -> c);
		for (UdsJobWeightLimit weight : list) {
			String serverIds = weight.getServerIds();
			if (serverIds.matches("^[0-9]+[0-9,]*[0-9]*$")) {
				for (String id : serverIds.split(Symbol.DOU_HAO_REG)) {
					Integer serverId = Integer.valueOf(id.trim());
					ArrayList<UdsJobWeightLimit> weightList = weightServerMap.get(serverId);
					if (weightList == null) {
						weightServerMap.putIfAbsent(serverId, weightList = new ArrayList<UdsJobWeightLimit>());
					}
					weightList.add(weight);
				}
			} else if (Symbol.XING_HAO.equals(serverIds)) {
				ArrayList<UdsJobWeightLimit> weightList = weightCountMap.get(weight.getLimitType());
				if (weightList == null) {
					weightCountMap.putIfAbsent(weight.getLimitType(), weightList = new ArrayList<UdsJobWeightLimit>());
				}
				weightList.add(weight);
				continue;
			}
			logger.error("db loda uds job weight is error,weight{}", weight);
			// error
		}

		this.weightCountMap = weightCountMap;
		this.weightServerMap = weightServerMap;
	}

}
