package cn.com.spdb.uds.background.http.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.LoadNoticeFinshJob;
import cn.com.spdb.uds.background.socket.command.LoadTagsInMaster;
import cn.com.spdb.uds.background.socket.command.LoadUdsConstantServer;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.utils.Symbol;

@HttpMapProtocol(value = "/server/concurrent")
public class HttpServerConcurrentHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		String serverName = (String) objectMap.get("server_name");
		int ratio = (int) objectMap.get("performance_ratio");
		int enable = (int) objectMap.get("is_enable");
		int maxNum = (int) objectMap.get("max_job_num");
		int location = (int) objectMap.get("location");

		String tags = (String) objectMap.get("tags");
		if (UdsConstant.SERVER_NAME.equals(serverName) && UdsConstant.SEND_LOCATE != 1) {
			return "服务器为主节点不可修改";
		}
		UdsRpcClient udsRpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
		if (udsRpcClient == null || !udsRpcClient.isActive()) {
			return "服务器名字错误，或失活";
		}
		if (udsRpcClient.isMaster() && UdsConstant.SEND_LOCATE != 1) {
			return "服务器为主节点不可修改";
		}
		enable = enable == 1 ? 1 : 0;
		if (ratio < 0 || ratio > 10000) {
			return "ratio:参数错误";
		}
		if (location < 0) {
			return "location:参数错误";
		}
		UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);
		int updateUdsServer = udsServerDao.updateUdsServer(ratio, enable, maxNum, serverName, location);
		int updateUdsServerTags = udsServerDao.updateUdsServerTags(serverName, tags);

		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(serverName, RpcCommand.SERVER_COMMAND);
		int res = 0;
		if (updateUdsServer > 0) {
			boolean succ = UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent,
					LoadUdsConstantServer.class.getSimpleName());
			if (succ) {
				ChildServerInfo info = MasterManager.getInstance().getChildServerJobMap().get(serverName);
				if (info != null && info.getMaxJobNum() != maxNum) {
					info.setMaxJobNum((short) maxNum);
				}
				res = 0;
			} else {
				res = 1;
			}
		}
		if (updateUdsServerTags > 0) {
			UdsRpcClientManager.getInstance().sendBroadcastMessage(udsRpcEvent, LoadTagsInMaster.class.getSimpleName(),
					true);
		}

		return res > 0 ? HttpResultCode.ERROR : HttpResultCode.SUCCESS;

	}
}
