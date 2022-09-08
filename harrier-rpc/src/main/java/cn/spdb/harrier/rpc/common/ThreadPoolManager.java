package cn.spdb.harrier.rpc.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

	private static class ThreadPoolManagerIner {
		private static ThreadPoolManager instance = new ThreadPoolManager();
	}

	private static final long KEEP_ALIVE_TIME = 200;
	private static final int WORK_QUEUE_SIZE = 60;

	public static ThreadPoolManager getInstance() {
		return ThreadPoolManager.ThreadPoolManagerIner.instance;
	}

	private ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
			Runtime.getRuntime().availableProcessors() * 4, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE));

	public void addExecute(Runnable task) {
		executorService.submit(task);
	}
}
