package cn.com.spdb.uds.background.socket.command;

import java.util.concurrent.ThreadPoolExecutor;

import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.handler.UdsHandlerManger;

public class SeeUdsHandlerManger extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		ThreadPoolExecutor executor = UdsHandlerManger.getInstance().getExecutor();
		buffer.append("CorePoolSize:" + executor.getCorePoolSize()).append("\r\n");
		buffer.append("MaximumPoolSize:" + executor.getMaximumPoolSize()).append("\r\n");
		buffer.append("PoolSize:" + executor.getPoolSize()).append("\r\n");
		buffer.append("ActiveCount:" + executor.getActiveCount()).append("\r\n");
		buffer.append("QueueSize:" + executor.getQueue().size()).append("\r\n");
		buffer.append("CompletedTaskCount:" + executor.getCompletedTaskCount()).append("\r\n");
		buffer.append("TaskCount:" + executor.getTaskCount()).append("\r\n");

	}

}
