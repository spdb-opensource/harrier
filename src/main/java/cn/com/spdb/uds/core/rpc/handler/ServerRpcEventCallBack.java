package cn.com.spdb.uds.core.rpc.handler;

import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;

public interface ServerRpcEventCallBack extends UdsRpcHandler{

	/**
	 * 客服端，回调返回信息
	 * @param rpcEvent 回调事件
	 */
	void callback(UdsRpcEvent callBackEvent);
}
