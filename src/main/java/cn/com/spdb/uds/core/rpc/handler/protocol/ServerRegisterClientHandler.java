package cn.com.spdb.uds.core.rpc.handler.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventCallBack;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;

@RpcEventProtocol(RpcCommand.SERVER_REGISTER)
public class ServerRegisterClientHandler implements ServerRpcEventCallBack, ServerRpcEventHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(ServerRegisterClientHandler.class);

	@Override
	public void callback(UdsRpcEvent callBackEvent) {
		int code = callBackEvent.getAttribute(RpcAttrKey.CODE);
		if (code == RpcResultCode.SUCCESS) {
			// 本地添加客户端
			UdsRpcClient rpcClient = UdsRpcClientManager.getInstance().addUdsRpcClient(callBackEvent.getSourceId());
			if (rpcClient != null) {
				rpcClient.updateMillisTime();
				LOGGER.info("register Success:", rpcClient);
				return;
			}
		}
		LOGGER.error("register error", callBackEvent.getSourceId());
	}

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {
		// 提供自己的ip和PORT 进行连接注册
		event.addAttribute(RpcAttrKey.IP, UdsConstant.LOCATE_IP);
		event.addAttribute(RpcAttrKey.PORT, UdsConstant.RPC_SERVER_PORT);
	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		String sourceId = event.getSourceId();
		// 注册到本地客服端
		UdsRpcClient client = UdsRpcClientManager.getInstance().addUdsRpcClient(sourceId);
		UdsRpcEvent callbackEvent = event.callBackEvent();
		if (client == null) {
			LOGGER.warn(" check databases conifg " + "event:" + event.toString());
		} else {
			client.updateMillisTime();
			callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.SUCCESS);
		}
		// 序列化
		callbackEvent.outputSerialize();
		// 直接返回
		client.write(callbackEvent);
		return null;
	}
}
