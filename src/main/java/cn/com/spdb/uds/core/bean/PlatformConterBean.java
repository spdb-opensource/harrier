package cn.com.spdb.uds.core.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.db.bean.UdsJobBean;

@Deprecated
public class PlatformConterBean {

	/** 等待处理作业 */
	private ArrayList<UdsJobBean> waitDealJobList = new ArrayList<UdsJobBean>();

	/** 处理中 */
	private ArrayList<UdsJobBean> dealJobList = new ArrayList<UdsJobBean>();
	/** 超出次数清空dealJobList */
	private int dealTimesOver = 0;

	/** 等待更新作业 */
	private ArrayList<UdsJobBean> updateDispatcherJobList = new ArrayList<UdsJobBean>();

	/** 等待分发作业 */
	private ArrayList<UdsJobBean> dispatcherJobList = new ArrayList<UdsJobBean>();


	/** 当前查询到多少 */
	private int initNum = 0;

	public int getInitNum() {
		return initNum;
	}

	public void setInitNum(int initNum) {
		this.initNum = initNum;
	}

	public void addWaitDealJobAll(Collection<UdsJobBean> collections) {
		waitDealJobList.addAll(collections);
	}

	/**
	 * 等待处理是否结束
	 * 
	 * @return
	 */
	public boolean isWaitDealJobinsh() {
		return waitDealJobList.size() > 0 ? false : true;
	}

	/**
	 * 等待处理切换到处理
	 * 
	 * @param transfromNum
	 */
	public void transfromWaitToDeal(int transfromNum) {
		int max = waitDealJobList.size() > transfromNum ? transfromNum : waitDealJobList.size();
		if (max > 0) {
			List<UdsJobBean> list = new ArrayList<UdsJobBean>(max);
			list.addAll(waitDealJobList.subList(0, max));
			waitDealJobList.removeAll(list);
			synchronized (dealJobList) {
				dealTimesOver = 0;
				dealJobList.addAll(list);
			}
		}
	}

	/**
	 * 处理中的
	 * 
	 * @return
	 */
	public boolean isDealJobfinsh() {
		dealTimesOver++;
		synchronized (dealJobList) {
			// 处理过次数释放
			if (dealTimesOver > 10) {
				dealJobList.clear();
			}
			return dealJobList.size() > 0 ? false : true;
		}
	}

	/**
	 * 处理中到等待更新
	 * 
	 * @param jobBean
	 */
	public void transfromDealToUpdateDispatcher(UdsJobBean bean) {
		synchronized (dealJobList) {
			dealJobList.remove(bean);
		}
		synchronized (updateDispatcherJobList) {
			if (!updateDispatcherJobList.contains(bean)) {
				updateDispatcherJobList.add(bean);
			}
		}
	}

	public boolean removeDealJob(UdsJobBean jobBean) {
		synchronized (dealJobList) {
			return dealJobList.remove(jobBean);
		}
	}

	public ArrayList<UdsJobBean> getNewDealJobList() {
		ArrayList<UdsJobBean> list = new ArrayList<UdsJobBean>();
		synchronized (list) {
			list.addAll(dealJobList);
		}
		return list;
	}

	public ArrayList<UdsJobBean> getNewDispatcherJobList(int toIndex) {
		ArrayList<UdsJobBean> list = new ArrayList<UdsJobBean>();
		int size = dispatcherJobList.size();
		if (size > 0) {
			synchronized (dispatcherJobList) {
				dispatcherJobList.sort(new Comparator<UdsJobBean>() {

					@Override
					public int compare(UdsJobBean o1, UdsJobBean o2) {
						if (o1.getPriority() == o2.getPriority()) {
							if (o1.getDispatcher_time() != null && o2.getDispatcher_time() != null) {
								return o2.getDispatcher_time().compareTo(o1.getDispatcher_time());
							}
						}
						return o2.getPriority() - o1.getPriority();
					}
				});

			}
			int index = size > toIndex ? size - toIndex : size;
			list.addAll(dispatcherJobList.subList(0, index));
		}
		return list;
	}

	public List<UdsJobBean> transfromUpdateDispatcherToDispatcher() {
		ArrayList<UdsJobBean> list = new ArrayList<UdsJobBean>();
		if (updateDispatcherJobList.size() > 0) {
			synchronized (updateDispatcherJobList) {
				list.addAll(updateDispatcherJobList);
				updateDispatcherJobList.removeAll(list);
			}
			for (UdsJobBean bean : list) {
				bean.setLast_status(JobStatus.DISPATCHER.status());
				bean.setDispatcher_time(new Timestamp(System.currentTimeMillis()));
				synchronized (dispatcherJobList) {
					if (!dispatcherJobList.contains(bean)) {
						dispatcherJobList.add(bean);
					}
				}
			}
		}
		return list;
	}

	public void removeDispatcherJob(UdsJobBean udsJobBean) {
		synchronized (dispatcherJobList) {
			dispatcherJobList.remove(udsJobBean);
		}
	}

	public void addDispatcherJobList(UdsJobBean udsJobBean) {
		synchronized (dispatcherJobList) {
			if (!dispatcherJobList.contains(udsJobBean)) {
//			udsJobBean.setPriority((short) (udsJobBean.getPriority() + 1));
				udsJobBean.setDispatcher_time(new Timestamp(System.currentTimeMillis()));
				dispatcherJobList.add(udsJobBean);
			}
		}
	}
}
