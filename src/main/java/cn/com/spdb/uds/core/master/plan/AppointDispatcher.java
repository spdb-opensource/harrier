package cn.com.spdb.uds.core.master.plan;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.utils.Symbol;

/**
 * 指定机器分发
 * 
 * @author T-luzl
 *
 */
public class AppointDispatcher extends AbstractDispatcherPlan {

	@Override
	public UdsRpcClient getUdsRpcClient(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean, boolean over) {
		String str = udsSystemBean.getStrategy_pro().trim();
		String serverName = null;
		int max = over == true ? Integer.MIN_VALUE : 0;
		if (StringUtils.isBlank(str)) {
			for (Entry<String, ChildServerInfo> entry : MasterManager.getInstance().getChildServerJobMap().entrySet()) {
				ChildServerInfo date = entry.getValue();
				if (date.getEnable() != UdsConstant.FALSE_NUM) {
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
		} else if (str.contains(Symbol.GAN_TAN_HAO)) {
			List<String> list = Arrays.asList(str.split(Symbol.GAN_TAN_HAO));
			for (Entry<String, ChildServerInfo> entry : MasterManager.getInstance().getChildServerJobMap().entrySet()) {
				ChildServerInfo date = entry.getValue();
				int tmpMax = date.getMaxJobNum() * date.getPerformanceRatio() / 1000;
				int jobNum = tmpMax - date.getJobNum();
				if (!over && jobNum <= 0) {
					continue;
				}
				if (list.contains(date.getName())) {
					continue;
				}
				if (jobNum > max) {
					serverName = date.getName();
					max = jobNum;
				}
			}
		} else if (str.contains(Symbol.DOU_HAO)) {
			List<String> list = Arrays.asList(str.split(Symbol.DOU_HAO));
			for (Entry<String, ChildServerInfo> entry : MasterManager.getInstance().getChildServerJobMap().entrySet()) {
				ChildServerInfo date = entry.getValue();
				int tmpMax = date.getMaxJobNum() * date.getPerformanceRatio() / 1000;
				int jobNum = tmpMax - date.getJobNum();
				if (!over && jobNum <= 0) {
					continue;
				}
				if (!list.contains(date.getName())) {
					continue;
				}
				if (jobNum > max) {
					serverName = date.getName();
					max = jobNum;
				}
			}
		} else {
			return null;
		}
		if (serverName != null) {
			return UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
		}
		return null;
	}

	@Override
	public Short getStrategyNum() {
		return 1;
	}

}
