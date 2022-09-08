package cn.spdb.harrier.server.master.rpc.transport;

import java.util.List;

import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.WorkingInfo;

@RpcHandler("MasterTransportServerInterfasce")
public interface MasterTransportServerInterfasce {

	@RpcMethod
	Boolean streamJobSignal(JobSignal jobSignal);

	@RpcMethod
	void jobContextDone(JobExecutionContext jobContext);

	@RpcMethod
	void updateWorkingInfo(WorkingInfo workingInfo);

	@RpcMethod
	void insertUdsJobStepRecord(UdsJobStepRecord record);

	@RpcMethod
	void updateJobStepRecord(UdsJobStepRecord record);

	@RpcMethod
	Boolean complementJob(List<String[]> jobList, UdsComplement complement);

	@RpcMethod
	void insertUdsJobRecord(JobExecutionContext jobContext);

	@RpcMethod
	String command(String cmd, String... args);
}