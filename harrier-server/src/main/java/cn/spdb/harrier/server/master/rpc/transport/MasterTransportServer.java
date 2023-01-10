package cn.spdb.harrier.server.master.rpc.transport;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import cn.spdb.harrier.common.CommandConstant;
import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.model.JobSignal;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;
import cn.spdb.harrier.server.entity.ComplementIns;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.WorkingInfo;
import cn.spdb.harrier.server.master.MasterManagerService;
import cn.spdb.harrier.server.master.cache.MasterMangerWorker;
import cn.spdb.harrier.server.master.complement.ComplementService;
import cn.spdb.harrier.server.script.command.Command;

@RpcServiceHandler("MasterTransportServerInterfasce")
public class MasterTransportServer implements MasterTransportServerInterfasce {

	private static MasterManagerService masterService = BeanContext.getBean(MasterManagerService.class);

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
		if (jobContext.getComplement()) {
			if (jobContext.getExecutionStatus().equals(ExecutionStatus.FAILURE)) {
				BeanContext.getBean(ComplementService.class).updateUdsComplement(jobContext.getComplementId(),
						ExecutionStatus.FAILURE);
			}
			BeanContext.getBean(ComplementService.class).updateUdsJobComplement(jobContext);
			BeanContext.getBean(MasterMangerWorker.class).getWork(jobContext.getHost()).getJobNum().decrementAndGet();
		} else {
			if (jobContext.getExecutionStatus().equals(ExecutionStatus.SUCCESS)) {
				masterService.conversionSuccess(jobContext);
				masterService.conversionSuccessAfter(jobContext);
			} else {
				masterService.conversionFailure(jobContext);
			}
			masterService.decrementWeightAndJob(jobContext.getHost(), jobContext);
		}
		masterService.updateJobRecord(jobContext);
	}

	@Override
	public void updateWorkingInfo(WorkingInfo workingInfo) {
		masterService.updateWorkingInfo(workingInfo);
	}

	@Override
	public Boolean complementJob(List<String[]> jobList, UdsComplement complement) {
		if (jobList.size() < 0 || ObjectUtils.isEmpty(complement.getBatchRange())
				|| ObjectUtils.isEmpty(complement.getComName()) || ObjectUtils.isEmpty(complement.getServerNameRange())
				|| complement.getStartTime().compareTo(complement.getEndTime()) > 0) {
			return false;
		}
		if (Arrays.stream(complement.getServerNameRange().split(Symbol.FEN_HAO_REG)).anyMatch(serverName -> ObjectUtils
				.isEmpty(BeanContext.getBean(MasterMangerWorker.class).getWorkerByServerName(serverName)))) {
			return false;
		}

		ComplementIns complementIns = new ComplementIns(jobList);
		complementIns.setUdsComplement(complement);
		BeanContext.getBean(ComplementService.class).addComplementIns(complementIns);
		return true;
	}

	@Override
	public void insertUdsJobRecord(JobExecutionContext jobContext) {
		masterService.insertJobRecord(jobContext);
		if (jobContext.getComplement()) {
			BeanContext.getBean(ComplementService.class).updateUdsJobComplement(jobContext);
			BeanContext.getBean(MasterMangerWorker.class).getWork(jobContext.getHost()).getJobNum().incrementAndGet();
		}
	}

	public String command(String cmd, String... args) {
		return Command.cmd(cmd, CommandConstant.MASTER, args);
	}

}
