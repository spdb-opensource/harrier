package cn.spdb.harrier.server.worker.rpc.transport;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.worker.WorkerManagerThread;
import cn.spdb.harrier.server.worker.exec.TaskExecuteThread;

@RpcServiceHandler("WorkTransportServerInterface")
public class WorkTransportServer implements WorkTransportServerInterface {

	private WorkerManagerThread workerManagerThread = BeanContext.getBean(WorkerManagerThread.class);

	@Override
	public Boolean dispathcer(JobExecutionContext jobExecutionContext) {
		TaskExecuteThread taskExecuteThread = new TaskExecuteThread();
		jobExecutionContext.setExecutionStatus(ExecutionStatus.RUNING);
		taskExecuteThread.setJobExecutionContext(jobExecutionContext);
		if (workerManagerThread.offer(taskExecuteThread)) {
			workerManagerThread.incrementJob(taskExecuteThread.getJobExecutionContext());
			return true;
		}
		return false;
	}

//	@Override
//	public Boolean dispathcerAck(String platform,String systems,String job, ) {
//		TaskExecuteThread taskExecuteThread = new TaskExecuteThread();
//		jobExecutionContext.setExecutionStatus(ExecutionStatus.RUNING);
//		taskExecuteThread.setJobExecutionContext(jobExecutionContext);
//		if (workerManagerThread.offer(taskExecuteThread)) {
//			workerManagerThread.incrementJob(taskExecuteThread.getJobExecutionContext());
//		}
//		return false;
//	}
	
}
