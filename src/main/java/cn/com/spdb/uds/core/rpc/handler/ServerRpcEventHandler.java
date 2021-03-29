package cn.com.spdb.uds.core.rpc.handler;

import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;

public interface ServerRpcEventHandler extends UdsRpcHandler{

	/**
	 * 客服端，发送接口
	 * @param event 发送事件
	 * @param paramters 参数
	 */
	void sendHandle(UdsRpcEvent event,Object paramters);
	/**
	 * 服务端，接受处理接口
	 * @param 接受事件 
	 * @return 返回，回调事件 null 不回调
	 */
	UdsRpcEvent receiveHandle(UdsRpcEvent event);
	
}
