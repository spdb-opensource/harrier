package cn.com.spdb.uds.core.rpc.handler.protocol;

import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventCallBack;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;

/**
 * 心跳服务
 * 
 * @author T-luzl
 *
 */
@RpcEventProtocol(RpcCommand.SERVER_HEARTBEAT)
public class ServerHeartbeatHandler implements ServerRpcEventCallBack, ServerRpcEventHandler {

	private static AtomicInteger TIMES = new AtomicInteger(0);

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {

	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		UdsRpcEvent callbackEvent = event.callBackEvent();
		int times = TIMES.decrementAndGet();
		// 子节点定时发送数据
		if (times <= 0) {
			TIMES.set(5);
			if (!UdsConstant.IS_PRIMARY_SERVER || UdsConstant.SEND_LOCATE == UdsConstant.TRUE_NUM) {
				ChildServerInfo childServerInfo = ChildManager.getInstance().buildChildServerDate();
				String msg = JSON.toJSONString(childServerInfo);
				callbackEvent.addAttribute(RpcAttrKey.CHILD_SERVER_MSG, msg);
			}
		}
		return callbackEvent;
	}

	@Override
	public void callback(UdsRpcEvent callBackEvent) {
		UdsRpcClient rpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(callBackEvent.getSourceId());
		if (rpcClient != null) {
			rpcClient.updateMillisTime();
			// 主节点接受，子节点数据
			if ((UdsConstant.IS_PRIMARY_SERVER && !rpcClient.isMaster())
					|| UdsConstant.SEND_LOCATE == UdsConstant.TRUE_NUM) {
				String msg = callBackEvent.getAttribute(RpcAttrKey.CHILD_SERVER_MSG);
				ChildServerInfo childServerInfo = JSON.parseObject(msg, ChildServerInfo.class);
				MasterManager.getInstance().addChildServerJob(childServerInfo.getName(), childServerInfo);
			}
		}
	}
}
