package cn.com.spdb.uds.core.master.plan;

import java.util.HashMap;

import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;

public abstract class AbstractDispatcherPlan {

	private static HashMap<Short, AbstractDispatcherPlan> DISPATCHER_PLAN_MAP = new HashMap<Short, AbstractDispatcherPlan>();

	public abstract UdsRpcClient getUdsRpcClient(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean, boolean over);

	public abstract Short getStrategyNum();

	public static boolean isExistDispatcherPlan(int num) {
		Short s = (short) num;
		return DISPATCHER_PLAN_MAP.containsKey(s);
	}

	public AbstractDispatcherPlan() {
		DISPATCHER_PLAN_MAP.put(getStrategyNum(), this);
	}

	public synchronized static UdsRpcClient getUdsRpcClient(UdsSystemBean udsSystemBean, UdsJobBean udsJobBean) {
		AbstractDispatcherPlan plan = DISPATCHER_PLAN_MAP.get(udsSystemBean.getStrategy());
		if (plan != null) {
			return plan.getUdsRpcClient(udsJobBean, udsSystemBean, false);
		}
		return null;
	}

	public static UdsRpcClient getUdsRpcClientOver(UdsSystemBean udsSystemBean, UdsJobBean udsJobBean) {
		AbstractDispatcherPlan plan = DISPATCHER_PLAN_MAP.get(udsSystemBean.getStrategy());
		if (plan != null) {
			return plan.getUdsRpcClient(udsJobBean, udsSystemBean, true);
		}
		return null;
	}
}
