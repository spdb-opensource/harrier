package cn.com.spdb.uds.db.dao;

import java.util.HashMap;
import java.util.List;

import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.db.bean.UdsServerBean;

public class UdsServerDao extends AbstractBaseDao {

	public List<UdsServerBean> getUdsServerList() {
		List<UdsServerBean> list = selectList("uds_server.getUdsServerS");
		return list;
	}

	public UdsServerBean getUdsServerByName(String server_name) {
		UdsServerBean udsServerBean = selectOne("uds_server.getUdsServerByServerName", server_name);
		return udsServerBean;
	}

	public UdsServerBean getUdsServerByIp(String ip) {
		UdsServerBean udsServerBean = selectOne("uds_server.getUdsServerByServerIp", ip);
		return udsServerBean;
	}
	
	public int insertUdsServer(UdsServerBean udsServerBean) {
		int num = insert("uds_server.insertUdsServer", udsServerBean);
		return num;
	}

	public int updateUdsServerStartTime(String server_name) {
		int num = insert("uds_server.updateUdsServerStartTime", server_name);
		return num;
	}
	
	public int updateUdsServerEndTime(String server_name) {
		int num = insert("uds_server.updateUdsServerEndTime", server_name);
		return num;
	}
	
	public int updateUdsServer(int ratio, int enable, int maxNum, String server_name, int location) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("max_job_num", maxNum);
		map.put("is_enable", enable);
		map.put("performance_ratio", ratio);
		map.put("server_name", server_name);
		map.put("location", location);
		return update("uds_server.updateUdsServer", map);
	}
	public int updateUdsServerTags(String server_name, String tags) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("server_name", server_name);
		map.put("tags",tags);
		return update("uds_server.updateUdsServerTags", map);
	}


	
	public HashMap<String, String> getUdsConstant() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<HashMap<Object, Object>> list = selectList("uds_server.getUdsConstant");
		if (list != null) {
			for (HashMap<Object, Object> map : list) {
				String key = String.valueOf(map.get("key"));
				String value = String.valueOf(map.get("value"));
				hashMap.put(key, value);
			}
		}
		return hashMap;
	}
}
