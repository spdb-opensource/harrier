package cn.com.spdb.uds.background.http.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.LoadUdsSystem;
import cn.com.spdb.uds.core.master.plan.AbstractDispatcherPlan;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.utils.Symbol;
import io.netty.util.internal.StringUtil;

@HttpMapProtocol(value = "/system/concurrent")
public class HttpSystemConcurrentHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {

		String platform = (String) objectMap.get("platform");
		String system = (String) objectMap.get("system");
		int maxRunJob = (int) objectMap.get("max_run_job");
		int usePlatfrom = (int) objectMap.get("use_Platfrom");

		int strategy = (int) objectMap.get("strategy");
		String strategyPro = (String) objectMap.get("strategy_pro");

		String system_key = platform + "_" + system;
		UdsSystemBean bean = UdsConstant.MAP_SYSTEM_JOB.get(system_key);
		if (bean == null || maxRunJob < 0) {
			return "平台应用配置为空";
		}
		// 并发数目
		if (system.equals(Symbol.XING_HAO)) {
			bean.setUse_platform((byte) UdsConstant.FALSE_NUM);
			bean.setMax_run_job(maxRunJob);
		} else {
			if (usePlatfrom == UdsConstant.TRUE_NUM) {
				bean.setMax_run_job(0);
				bean.setUse_platform((byte) UdsConstant.TRUE_NUM);
			} else {
				bean.setMax_run_job(maxRunJob);
				bean.setUse_platform((byte) UdsConstant.FALSE_NUM);
			}
		}

		if (!AbstractDispatcherPlan.isExistDispatcherPlan(strategy)) {
			return "策略不存在";
		}
		if (StringUtil.isNullOrEmpty(strategyPro)) {
			bean.setStrategy_pro("");
		}
		/*
		Extract Class
		 */
		StrategyChecker strategyChecker = new StrategyChecker();
		Object object = strategyChecker.checker(strategy,bean,strategyPro);
		if(object instanceof String){
			String message = (String) object;
		}else if(object instanceof UdsSystemBean){
			bean = (UdsSystemBean) object;
		}

		UdsJobControlDao UdsJobControlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		List<UdsSystemBean> listUdsSystemBeans = new ArrayList<UdsSystemBean>();
		listUdsSystemBeans.add(bean);
		int tmp = UdsJobControlDao.updateUdsSystemList(listUdsSystemBeans);
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(null, RpcCommand.SERVER_COMMAND);
		UdsRpcClientManager.getInstance().sendBroadcastMessage(udsRpcEvent, LoadUdsSystem.class.getSimpleName(),
				true);
		if (tmp > 0) {
			return HttpResultCode.SUCCESS;
		}
		return HttpResultCode.ERROR;
	}

}
