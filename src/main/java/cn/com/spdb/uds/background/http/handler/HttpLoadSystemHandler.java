package cn.com.spdb.uds.background.http.handler;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.LoadUdsSystem;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;

@HttpMapProtocol(value = "/load/udssystem")
public class HttpLoadSystemHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		String listStr = (String) objectMap.get("systemlist");
		List<UdsSystemBean> list=JSON.parseArray(listStr, UdsSystemBean.class);
		UdsJobControlDao controlDao= DBManager.getInstance().getDao(UdsJobControlDao.class);
		controlDao.updateUdsSystemListRunNum(list);
		UdsConstant.loadUdsSystemMap();
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(null, RpcCommand.SERVER_COMMAND);
		UdsRpcClientManager.getInstance().sendBroadcastMessage(udsRpcEvent, LoadUdsSystem.class.getSimpleName(),
				true);
		return HttpResultCode.SUCCESS;
	}

}
