package cn.spdb.harrier.server.master.rpc.transport;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.WorkingInfo;
import cn.spdb.harrier.server.master.MasterManagerService;

@RpcServiceHandler("MasterTransportServerInterfasce")
public class MasterTransportServer implements MasterTransportServerInterfasce {

	private MasterManagerService masterService = BeanContext.getBean(MasterManagerService.class);

	@Override
	public Boolean streamJobSignal(JobSignal jobSignal) {
		return masterService.streamJobSignal(jobSignal);
	}

	@Override
	public void insertUdsJobStepRecord(UdsJobStepRecord record) {
		masterService.insertUdsJobStepRecord(record);
	}

	@Override
	public void updateJobStepRecord(UdsJobStepRecord record) {
		masterService.updateJobStepRecord(record);
	}

	@Override
	public void jobContextDone(JobExecutionContext jobContext) {
		if (jobContext.getExecutionStatus().equals(ExecutionStatus.SUCCESS)) {
			masterService.conversionSuccess(jobContext);
		} else {
			masterService.conversionFailure(jobContext);
		}
		masterService.updateJobRecord(jobContext);
		masterService.decrementWeightAndJob(jobContext.getHost(), jobContext);
	}

	@Override
	public void updateWorkingInfo(WorkingInfo workingInfo) {
		masterService.updateWorkingInfo(workingInfo);
	}

}
