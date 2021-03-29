package cn.com.spdb.uds.core.master;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.InterfaceHttpWorkeHandler;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.filter.pending.AbstractPendingFilter;
import cn.com.spdb.uds.core.filter.pending.JobDependFilter;
import cn.com.spdb.uds.core.filter.pending.JobSystemMaxNumFilter;
import cn.com.spdb.uds.core.filter.pending.JobTimeWindowFilter;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.NameThreadFactory;
import cn.com.spdb.uds.utils.Symbol;

/**
 * Pending 作业检测处理工厂
 * 
 * @author T-luzl
 *
 */
public class MasterPendingJobDisposeFactory {
	/** 等待作业过滤 */
	private List<AbstractPendingFilter> jobPendingFilterList = new ArrayList<AbstractPendingFilter>();

	private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
	/** JOB处理池 */
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 10000L, TimeUnit.MILLISECONDS,
			linkedBlockingQueue, new NameThreadFactory(MasterPendingJobDisposeFactory.class.getSimpleName()));

	private void init() {
		jobPendingFilterList.add(new JobSystemMaxNumFilter());
		jobPendingFilterList.add(new JobTimeWindowFilter());
		jobPendingFilterList.add(new JobDependFilter());
	}

	public MasterPendingJobDisposeFactory() {
		init();
	}

	public void submit(UdsJobBean udsJobBean) {
		executor.submit(new PendingJobConsumer(udsJobBean));
	}

	/**
	 * 检测处理
	 * 
	 * @author T-luzl
	 *
	 */
	private class PendingJobConsumer implements Runnable {
		private UdsJobBean udsJobBean = null;

		public PendingJobConsumer(UdsJobBean udsJobBean) {
			this.udsJobBean = udsJobBean;
		}

		@Override
		public void run() {
			try {
				if (udsJobBean == null) {
					return;
				}
				UdsSystemBean udsplatform = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(), Symbol.XING_HAO);
				if (udsplatform == null) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob());
					return;
				}
				// 平台参数设置为0停止对该平台分发
				if (udsplatform.getMax_run_job() == 0) {
					UdsLogger.logEvent(LogEvent.MASTER_PENDIG, "MASTER PENDIG PLATFORM NUM IS 0",
							udsplatform.getSystem(), udsplatform.getPlatform());
					return;
				}
				UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(),
						udsJobBean.getSystem());
				if (udsSystemBean == null) {
					return;
				}
				boolean pass = true;
				for (AbstractPendingFilter filter : jobPendingFilterList) {
					if (!filter.checkDependency(udsJobBean, udsSystemBean)) {
						pass = false;
						break;
					}
				}
				if (pass) {
					MasterManager.getInstance().transfromDealToDispatcher(udsJobBean);
				} 
			} finally {
				MasterManager.getInstance().removeDeal(udsJobBean);
			}
		}
	}

	
	
	
	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

}
