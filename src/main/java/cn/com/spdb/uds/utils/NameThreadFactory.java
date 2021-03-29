package cn.com.spdb.uds.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameThreadFactory implements ThreadFactory {

	private final ThreadGroup group;
	
	private final String namePrefix;
	
	private final AtomicInteger threadNumber=new AtomicInteger(1);
	
	
	
	public NameThreadFactory( String groupName) {
		this.namePrefix = groupName+"_";
		this.group = new ThreadGroup(groupName);
	}


	public Thread newThread(boolean deamon,Runnable runnable){
		Thread thread=new Thread(group, runnable, nextThreadNumber(),0);
		thread.setDaemon(deamon);
		return thread;
	}
	
	
	
	public Thread newDeamonThread(Runnable runnable){
		return newThread(true, runnable);
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(group,r,nextThreadNumber(),0);
	}
	
	private String nextThreadNumber(){
		return new StringBuffer(namePrefix).append(threadNumber.getAndIncrement()).toString();
	}
}
