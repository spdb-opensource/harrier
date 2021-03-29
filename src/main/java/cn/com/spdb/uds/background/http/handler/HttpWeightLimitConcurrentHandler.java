package cn.com.spdb.uds.background.http.handler;

import java.util.HashMap;
import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.LoadTagsInMaster;
import cn.com.spdb.uds.background.socket.command.LoadUdsConstantServer;
import cn.com.spdb.uds.background.socket.command.LoadWeightLimitInMaster;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;

@HttpMapProtocol(value = "/server/load/uds_job_weight_limit")
public class HttpWeightLimitConcurrentHandler extends AbstractHttpPostBodyWorkHandler{

	@Override
	public String handler(Map<String, Object> objectMap) {
		// TODO Auto-generated method stub
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(null, RpcCommand.SERVER_COMMAND);
		UdsRpcClientManager.getInstance().sendBroadcastMessage(udsRpcEvent, LoadWeightLimitInMaster.class.getSimpleName(),
				true);
		return HttpResultCode.SUCCESS;
	}

}
