package cn.com.spdb.uds.core.rpc.handler.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventCallBack;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;

@RpcEventProtocol(RpcCommand.SERVER_SHOUTDOWN)
public class ServerShoutDownHandler implements ServerRpcEventCallBack, ServerRpcEventHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(ServerShoutDownHandler.class);

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {

	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		UdsRpcClientManager.getInstance().removeUdsRpcClient(event.getSourceId());
		if (UdsConstant.IS_PRIMARY_SERVER) {
			MasterManager.getInstance().removeChildServerJobMap(event.getSourceId());
			MasterManager.getInstance().setCheckRecive(true);
		}
		LOGGER.warn("remove :" + event.getSourceId());
		return null;
	}

	@Override
	public void callback(UdsRpcEvent callBackEvent) {

	}

}
