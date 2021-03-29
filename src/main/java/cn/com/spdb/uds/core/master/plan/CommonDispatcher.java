package cn.com.spdb.uds.core.master.plan;

import java.util.Map.Entry;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;

public class CommonDispatcher extends AbstractDispatcherPlan {

	@Override
	public UdsRpcClient getUdsRpcClient(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean, boolean over) {
		String serverName = null;
		int max = over == true ? Integer.MIN_VALUE : 0;
		for (Entry<String, ChildServerInfo> entry : MasterManager.getInstance().getChildServerJobMap().entrySet()) {
			ChildServerInfo date = entry.getValue();
			if (date.getEnable() != UdsConstant.TRUE_NUM) {
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
		return 0;
	}

}
