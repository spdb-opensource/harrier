package cn.com.spdb.uds.core.rpc.transfer;

import com.baidu.jprotobuf.pbrpc.ProtobufRPC;
import com.baidu.jprotobuf.pbrpc.ProtobufRPCService;

import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.UdsHandlerManger;

public class UdsTransferRpcImpl {

	/***
	 * 请求传输
	 */
	@ProtobufRPCService(serviceName = "udsTransferRpc", methodName = "transferEvent")
	public void transferEvent(UdsRpcEvent udsRpcEvent) {
		UdsHandlerManger.getInstance().addReceiveProducerRpcEvent(udsRpcEvent);
	}

	/***
	 * 有返回信息请求传输
	 */
	@ProtobufRPCService(serviceName = "udsTransferRpc", methodName = "transferEventConcurrent")
	public UdsRpcEvent transferEventConcurrent(UdsRpcEvent udsRpcEvent) {
		return UdsHandlerManger.getInstance().receiveServerConcurrentEvent(udsRpcEvent);
	}
}
