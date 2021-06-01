package cn.com.spdb.uds.background.socket.command;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.master.MasterFactory;
import cn.com.spdb.uds.core.master.MasterManager;

public class SeeSignalJobQueue extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		MasterFactory masterFactory = MasterManager.getInstance().getMasterFactory();
		LinkedBlockingQueue<String> blockingQueue = masterFactory.getSignalQueue();
		ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = masterFactory.getSignalNum();
		buffer.append("signalQueue:").append(blockingQueue.toString()).append("\r\n");
		buffer.append("sinnalNum:").append(concurrentHashMap.toString()).append("\r\n");
		MasterManager.getInstance().getMasterFactory();

	}

}
