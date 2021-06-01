package cn.com.spdb.uds.background.http.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.core.master.plan.AbstractDispatcherPlan;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
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

//		String platform_key = platform + "_" + Symbol.XING_HAO;
//		UdsSystemBean platformBean = UdsConstant.MAP_SYSTEM_JOB.get(platform_key);

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
//				int num = platformBean.getMax_run_job();
//				if (bean.getUse_platform() == UdsConstant.FALSE_NUM) {
//					num += bean.getMax_run_job();
//				}
//				if (num < 0) {
//					return "平台并发数目不够";
//				}
//				platformBean.setMax_run_job(num);
				bean.setMax_run_job(0);
				bean.setUse_platform((byte) UdsConstant.TRUE_NUM);
			} else {
//				int num = platformBean.getMax_run_job() - (maxRunJob - bean.getMax_run_job());
//				if (num < 0) {
//					return "平台并发数目不够";
//				}
//				platformBean.setMax_run_job(num);
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
		switch (strategy) {
		case 0:
		case 3: {
			bean.setStrategy((short) strategy);
		}
			break;
		case 1: {
			if (strategyPro.contains(Symbol.DOU_HAO) || strategyPro.contains(Symbol.GAN_TAN_HAO)) {
				String[] names = strategyPro.split(Symbol.DOU_HAO + "|" + Symbol.GAN_TAN_HAO);
				for (String name : names) {
					name = name.trim();
					UdsRpcClient client = UdsRpcClientManager.getInstance().getUdsRpcClient(name);
					if (client == null) {
						UdsLogger.logEvent(LogEvent.HTTP_ERROR, "UdsRpcClient IS NULL", strategy, name);
					}
				}
				bean.setStrategy_pro(strategyPro);
			}
			bean.setStrategy((short) strategy);
		}
			break;
		case 2: {
			if (!strategyPro.matches("^[0-9,!]+$")) {
				UdsLogger.logEvent(LogEvent.HTTP_ERROR, "strategyPro error", strategy, strategyPro);
				return "存在配置序号不是数字";
			}
			if (strategyPro.contains(Symbol.DOU_HAO) || strategyPro.contains(Symbol.GAN_TAN_HAO)) {
				String[] orderStrs = strategyPro.split(Symbol.DOU_HAO + "|" + Symbol.GAN_TAN_HAO);
				for (String orderStr : orderStrs) {
					orderStr = orderStr.trim();
					short order;
					try {
						order = Short.parseShort(orderStr);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						UdsLogger.logEvent(LogEvent.HTTP_ERROR, "orderStr isnot short", strategy, orderStr);
						return "配置序号不是机器";
					}
					UdsRpcClient client = UdsRpcClientManager.getInstance().getUdsRpcClient(order);
					if (client == null) {
						UdsLogger.logEvent(LogEvent.HTTP_ERROR, "UdsRpcClient IS NULL", strategy, order);
					}
				}
				bean.setStrategy_pro(strategyPro);
			}
			bean.setStrategy((short) strategy);
		}
			break;
		default:
			return "策略没有配置";
		}

		UdsJobControlDao UdsJobControlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		List<UdsSystemBean> listUdsSystemBeans = new ArrayList<UdsSystemBean>();
		listUdsSystemBeans.add(bean);
		int tmp = UdsJobControlDao.updateUdsSystemList(listUdsSystemBeans);
		if (tmp > 0) {
			return HttpResultCode.SUCCESS;
		}
		return HttpResultCode.ERROR;
	}

}
