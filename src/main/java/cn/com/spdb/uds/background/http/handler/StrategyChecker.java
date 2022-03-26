package cn.com.spdb.uds.background.http.handler;

import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.Symbol;

public class StrategyChecker {

    public Object checker(int strategy, UdsSystemBean bean, String strategyPro) {
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
        return bean;
    }
}
