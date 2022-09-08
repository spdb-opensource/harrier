package cn.spdb.harrier.api.rpc.transport;

import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;

@RpcHandler("WorkTransportServerInterface")
public interface WorkTransportServerInterface {

	@RpcMethod
	Boolean killJob(Long taskInstanceId);
}