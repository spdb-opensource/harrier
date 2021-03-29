package cn.com.spdb.uds.core.master;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;

public class MasterDispatcherFactory {

	private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
	/** JOB处理池 */
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 10000L, TimeUnit.MILLISECONDS,
			linkedBlockingQueue, new NameThreadFactory(MasterPendingJobDisposeFactory.class.getSimpleName()));

	private HashSet<String> platformSet = new HashSet<String>();

	public void submit(List<UdsJobBean> dispatcherWaitList, String key) {
		if (!platformSet.contains(key)) {
			platformSet.add(key);
			executor.submit(new DispatcherJobConsumer(dispatcherWaitList, key));
		}
	}

	private class DispatcherJobConsumer implements Runnable {

		public DispatcherJobConsumer(List<UdsJobBean> dispatcherWaitList, String key) {
			this.dispatcherWaitList = dispatcherWaitList;
			this.key = key;
		}

		private List<UdsJobBean> dispatcherWaitList = new ArrayList<UdsJobBean>();

		private String key = "";

		@Override
		public void run() {
			long now = System.currentTimeMillis();
			try {
				for (UdsJobBean udsJobBean : dispatcherWaitList) {
					// 权重判断
					if (udsJobBean.getCheck_weight() == UdsConstant.TRUE_NUM) {
						if (!MasterManager.getInstance().checkLimitWeight(udsJobBean)) {
							break;
						}
					}
					if (MasterManager.getInstance().dispatcherJob(udsJobBean)) {
						MasterManager.getInstance().removeDispatcherJob(udsJobBean);
					} else {
						if (udsJobBean.getDispatcher_time() == null
								|| now - udsJobBean.getDispatcher_time().getTime() > DateUtils.TIME_MILLSECOND_OF_MINUTE
										* UdsConstant.DISPATCHER_OVER_MINUTE) {
							MasterManager.getInstance().removeDispatcherJob(udsJobBean);
						}
					}
				}
			} finally {
				platformSet.remove(key);
			}
		}

	}
}
