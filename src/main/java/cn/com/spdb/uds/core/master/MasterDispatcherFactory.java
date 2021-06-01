package cn.com.spdb.uds.core.master;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.spdb.uds.core.bean.SystemConterBean;
import cn.com.spdb.uds.utils.NameThreadFactory;

@Deprecated
public class MasterDispatcherFactory {

	private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
	/** JOB处理池 */
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 10000L, TimeUnit.MILLISECONDS,
			linkedBlockingQueue, new NameThreadFactory(MasterPendingJobDisposeFactory.class.getSimpleName()));

	private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	Semaphore semaphore = new Semaphore(1);

	private class plaformJobDeal implements Runnable {
		private String plaformKey = "";

		private SystemConterBean sysConBean=null;

		public plaformJobDeal(String plaformKey) {
			this.plaformKey = plaformKey;

		}

		@Override
		public void run() {

		}

	}

	private class SinghtJobDeal implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					String keyJobPlafrom = queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}

}
