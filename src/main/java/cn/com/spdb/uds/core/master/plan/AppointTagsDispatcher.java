package cn.com.spdb.uds.core.master.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.bean.JobTagsType;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobTagBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobTagDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class AppointTagsDispatcher extends AbstractDispatcherPlan {

	@Override
	public UdsRpcClient getUdsRpcClient(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean, boolean over) {

		UdsJobTagDao jobTagDao = DBManager.getInstance().getDao(UdsJobTagDao.class);
		List<UdsJobTagBean> tagBeanList = jobTagDao.getUdsJobTagsOnlySort(udsJobBean.getPlatform(),
				udsJobBean.getSystem(), udsJobBean.getSystem(), JobTagsType.server);
		String serverName = null;
		// DB获取作业标签
		int max = over == true ? Integer.MIN_VALUE : 0;
		for (Entry<String, ChildServerInfo> entry : MasterManager.getInstance().getChildServerJobMap().entrySet()) {
			ChildServerInfo date = entry.getValue();
			if (date.getEnable() != UdsConstant.TRUE_NUM) {
				continue;
			}
			ArrayList<String> tags = UdsConstant.SERVER_TAGS.get(date.getName());
			if (tags == null || tags.size() == 0) {
				continue;
			}
			boolean isNotPass = true;
			for (UdsJobTagBean tagBean : tagBeanList) {
				if (tags.contains(tagBean.getTag())) {
					isNotPass = false;
					break;
				}
			}
			if (isNotPass) {
				UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "udsjobtages is not exist", date.getName());
				continue;
			}
			
			int tmpMax = date.getMaxJobNum() * date.getPerformanceRatio() / 1000;
			int jobNum = tmpMax - date.getJobNum();
			if (!over && jobNum <= 0) {
				continue;
			}
			if (jobNum > max) {
				serverName = date.getName();
				max = jobNum;
			}
		}
		if (serverName != null) {
			return UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
		}
		return null;
	}

	@Override
	public Short getStrategyNum() {
		return 3;
	}

}
