package cn.spdb.harrier.api.rpc.transport;

import java.util.List;

import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;

@RpcHandler("MasterTransportServerInterfasce")
public interface MasterTransportServerInterfasce {
	@RpcMethod
	Boolean streamJobSignal(JobSignal jobSignal);

	@RpcMethod
	Boolean complementJob(List<String[]> jobList, UdsComplement complement);

	@RpcMethod
	String command(String cmd, String... args);

}