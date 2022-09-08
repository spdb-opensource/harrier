package cn.spdb.harrier.server.worker.rpc.transport;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;
import cn.spdb.harrier.server.entity.ComplementIns;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.worker.WorkerManagerThread;
import cn.spdb.harrier.server.worker.exec.ComplementRunable;
import cn.spdb.harrier.server.worker.exec.TaskExecuteRunnable;

@RpcServiceHandler("WorkTransportServerInterface")
public class WorkTransportServer implements WorkTransportServerInterface {

	private WorkerManagerThread workerManagerThread = BeanContext.getBean(WorkerManagerThread.class);

	@Override
	public Boolean dispathcer(JobExecutionContext jobExecutionContext) {
		TaskExecuteRunnable taskExecuteRunnable = new TaskExecuteRunnable();
		jobExecutionContext.setExecutionStatus(ExecutionStatus.RUNING);
		taskExecuteRunnable.setJobExecutionContext(jobExecutionContext);
		if (workerManagerThread.offer(taskExecuteRunnable)) {
			workerManagerThread.incrementJob(taskExecuteRunnable.getJobExecutionContext());
			return true;
		}
		return false;
	}

	@Override
	public Boolean killJob(Long taskInstanceId) {
		workerManagerThread.killTaskBeforeExecuteByInstanceId(taskInstanceId);
		return true;
	}

	@Override
	public Boolean dispathcerComplement(ComplementIns complementIns) {
		new Thread(new ComplementRunable(complementIns)).start();
		return true;
	}

}
