package cn.spdb.harrier.api.rpc.transport;

import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;

@RpcHandler("MasterTransportServerInterfasce")
public interface MasterTransportServerInterfasce {
	@RpcMethod
	Boolean streamJobSignal(JobSignal jobSignal);

}