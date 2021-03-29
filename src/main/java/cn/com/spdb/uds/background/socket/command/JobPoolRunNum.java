package cn.com.spdb.uds.background.socket.command;

import java.util.concurrent.ThreadPoolExecutor;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;

public class JobPoolRunNum extends AbsstactConsoleCommand {

//	@Override
//	public String hanlder(String param, PrintWriter pw) {
//		if (!UdsConstant.IS_PRIMARY_SERVER) {
//			ThreadPoolExecutor executor = ChildManager.getInstance().getExecutor();
//			pw.println("CorePoolSize:" + executor.getCorePoolSize());
//			pw.println("MaximumPoolSize:" + executor.getMaximumPoolSize());
//			pw.println("ActiveCount:" + executor.getActiveCount());
//			pw.println("QueueSize:" + executor.getQueue().size());
//			pw.println("PoolSize:" + executor.getPoolSize());
//			pw.println("TaskCount:" + executor.getTaskCount());
//			pw.println("CompletedTaskCount:" + executor.getCompletedTaskCount());
//			pw.println("JobRunPoolNum:" + ChildManager.getInstance().getRunJobsPool().size());
//		}
//		return HttpResultCode.SUCCESS;
//	}

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		if (!UdsConstant.IS_PRIMARY_SERVER) {
			ThreadPoolExecutor executor = ChildManager.getInstance().getExecutor();
			buffer.append("CorePoolSize:" + executor.getCorePoolSize()).append("\n");
			buffer.append("MaximumPoolSize:" + executor.getMaximumPoolSize()).append("\n");
			buffer.append("ActiveCount:" + executor.getActiveCount()).append("\n");
			buffer.append("QueueSize:" + executor.getQueue().size()).append("\n");
			buffer.append("PoolSize:" + executor.getPoolSize()).append("\n");
			buffer.append("TaskCount:" + executor.getTaskCount()).append("\n");
			buffer.append("CompletedTaskCount:" + executor.getCompletedTaskCount()).append("\n");
			buffer.append("JobRunPoolNum:" + ChildManager.getInstance().getRunJobsPool().size()).append("\n");
		} else {
			buffer.append("is master").append("\n");
		}
	}

}
