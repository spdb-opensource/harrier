package cn.com.spdb.uds.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class UdsJobWeightDao extends AbstractBaseDao {

//	public boolean ckeckUdsJobWeightLimit(String platform, String system, String job) {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("platform", platform);
//		map.put("system", system);
//		map.put("job", job);
//		int num = selectOne("uds_job_weight.ckeckUdsJobWeightLimit", map);
//		return num > 0 ? false : true;
//	}

//	public int addUdsJobWeightLimit(String platform, String system, String job) {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("platform", platform);
//		map.put("system", system);
//		map.put("job", job);
//		int num = update("uds_job_weight.addUdsJobWeightLimit", map);
//		return num;
//	}

//	public int subUdsJobWeightLimit(String platform, String system, String job) {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("platform", platform);
//		map.put("system", system);
//		map.put("job", job);
//		int num = update("uds_job_weight.subUdsJobWeightLimit", map);
//		return num;
//	}

//	public HashMap<Integer, Integer> getUdsJobWeightMap(String platform, String system, String job) {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("platform", platform);
//		map.put("system", system);
//		map.put("job", job);
//		HashMap<Integer, Integer> weightMap = new HashMap<Integer, Integer>();
//		List<HashMap<String, Integer>> list = selectList("uds_job_weight.getUdsJobWeightMap", map);
//		if (list != null) {
//			for (HashMap<String, Integer> tmpMap : list) {
//				try {
//					Integer limitType = tmpMap.get("limit_type");
////					Integer value = Integer.parseInt(String.valueOf(tmpMap.get("conf_value")));
//					Integer value = tmpMap.get("conf_value");
//					Integer confValue = weightMap.get(limitType);
//					if (confValue == null) {
//						weightMap.put(limitType, value);
//					} else {
//						weightMap.put(limitType, value + confValue);
//					}
//				} catch (NumberFormatException e) {
//					UdsLogger.error(LogEvent.ERROR_DB, job, e.getMessage());
//				}
//			}
//		}
//		return weightMap;
//	}
//

	public HashMap<Integer, Integer> getUdsJobWeightMap(String platform, String system, String job) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		HashMap<Integer, Integer> weightMap = new HashMap<Integer, Integer>();
		List<HashMap<String, Integer>> list = selectList("uds_job_weight.getUdsJobWeightMap", map);

		if (list != null) {
			for (HashMap<String, Integer> tmpMap : list) {
				try {
					Integer limitType = tmpMap.get("limit_type");
//					Integer value = Integer.parseInt(String.valueOf(tmpMap.get("conf_value")));
					Integer value = tmpMap.get("conf_value");
//					Integer confValue = weightMap.get(limitType);
					weightMap.put(limitType, value);
//					if (confValue == null) {
//						weightMap.put(limitType, value);
//					} else {
//						weightMap.put(limitType, value + confValue);
//					}
				} catch (NumberFormatException e) {
					UdsLogger.error(LogEvent.ERROR_DB, job, e.getMessage());
				}
			}
		}
		return weightMap;
	}

	public HashMap<Integer, Integer> getUdsJobWeightLimitMap() {
		List<HashMap<String, Integer>> list = selectList("uds_job_weight.getUdsJobWeightLimitMap");
		HashMap<Integer, Integer> weightLimitMap = new HashMap<Integer, Integer>();
		for (HashMap<String, Integer> tmpMap : list) {
			try {
				Integer limitType = tmpMap.get("limit_type");
				Integer value = tmpMap.get("limit_value");
				weightLimitMap.put(limitType, value);
			} catch (NumberFormatException e) {
				UdsLogger.error(LogEvent.ERROR_DB, e.getMessage());
			}
		}
		return weightLimitMap;
	}

	public Integer getUdsJobWeightLimit(int limitType) {
		return selectOne("uds_job_weight.getUdsJobWeightLimit", limitType);
	}

}
