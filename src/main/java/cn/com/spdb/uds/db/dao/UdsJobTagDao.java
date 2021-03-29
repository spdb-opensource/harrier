package cn.com.spdb.uds.db.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.spdb.uds.core.bean.JobTagsType;
import cn.com.spdb.uds.db.AbstractBaseDao;
import cn.com.spdb.uds.db.bean.UdsJobTagBean;

public class UdsJobTagDao extends AbstractBaseDao {

	/**
	 * 获取按照 作业，应用，平台顺序获取标签
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param tagType
	 * @return
	 */
	public List<UdsJobTagBean> getUdsJobTags(String platform, String system, String job, int tagType) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("platform", platform);
		map.put("system", system);
		map.put("job", job);
		map.put("tag_type", tagType);
		return selectList("uds_job_tages.getUdsJobTags", map);
	}

	/**
	 * 获取按照 作业，应用，平台顺序获取其中靠前的一种
	 * 
	 * @param platform
	 * @param system
	 * @param job
	 * @param tagType
	 * @return
	 */
	public List<UdsJobTagBean> getUdsJobTagsOnlySort(String platform, String system, String job,
			JobTagsType jobTagsType) {
		int tagType = jobTagsType.getType();
		List<UdsJobTagBean> list = getUdsJobTags(platform, system, job, tagType);
		List<UdsJobTagBean> tmpList = new ArrayList<UdsJobTagBean>();
		if (list != null && list.size() > 0) {
			for (int i = 0, s = list.get(0).getSort(); i < list.size() && s == list.get(i).getSort(); i++) {
				tmpList.add(list.get(i));
			}
		}
		return tmpList;
	}

}
