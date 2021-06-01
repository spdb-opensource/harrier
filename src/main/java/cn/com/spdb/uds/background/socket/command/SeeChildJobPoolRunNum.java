package cn.com.spdb.uds.background.socket.command;

import java.util.concurrent.ThreadPoolExecutor;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;

public class SeeChildJobPoolRunNum extends AbsstactConsoleCommand {

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
		if (!UdsConstant.IS_PRIMARY_SERVER || UdsConstant.SEND_LOCATE == UdsConstant.TRUE_NUM) {
			ThreadPoolExecutor executor = ChildManager.getInstance().getExecutor();
			buffer.append("CorePoolSize:" + executor.getCorePoolSize()).append("\r\n");
			buffer.append("MaximumPoolSize:" + executor.getMaximumPoolSize()).append("\r\n");
			buffer.append("ActiveCount:" + executor.getActiveCount()).append("\r\n");
			buffer.append("QueueSize:" + executor.getQueue().size()).append("\r\n");
			buffer.append("PoolSize:" + executor.getPoolSize()).append("\r\n");
			buffer.append("TaskCount:" + executor.getTaskCount()).append("\r\n");
			buffer.append("CompletedTaskCount:" + executor.getCompletedTaskCount()).append("\r\n");
			buffer.append("JobRunPoolNum:" + ChildManager.getInstance().getRunJobsPool().size()).append("\r\n");
		} else {
			buffer.append("is master").append("\n");
		}
	}

}
