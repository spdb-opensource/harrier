package cn.com.spdb.uds.core.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.db.bean.UdsJobBean;

public class PlatformSystemCenterBean {

	private String platform;
	/** 等待处理作业 */
	private ArrayList<UdsJobBean> waitDealJobList = new ArrayList<UdsJobBean>();
	/** 当前查询到多少 */
	private int waitInitNum = 0;
	/** 处理中 */
	private ConcurrentLinkedQueue<UdsJobBean> dealJobQueue = new ConcurrentLinkedQueue<UdsJobBean>();
	private AtomicInteger dealInitNum = new AtomicInteger(0);

	/** 等待更新作业 */
	private ArrayList<UdsJobBean> updateDispatcherJobList = new ArrayList<UdsJobBean>();

	/** 等待分发作业 */
	private ConcurrentHashMap<String, SystemConterBean> platformConterMap = new ConcurrentHashMap<String, SystemConterBean>();

	/** 移除长时间不用的应用 */
	public void checkRemovePlatformConterMapBytime(final long over) {
		platformConterMap.forEachValue(2, new Consumer<SystemConterBean>() {
			@Override
			public void accept(SystemConterBean t) {
				String key = UdsConstant.getUdsSystemBeanKey(t.getPlatform(), t.getSystem());
				if (!key.equals(t.getPlatformSystemId())) {
					if (t.isremove(over)) {
						platformConterMap.remove(key);
					}
				}
			}
		});
	}

	public PlatformSystemCenterBean(String platform) {
		super();
		this.platform = platform;
	}

	/** 添加等待处理 */
	public void addWaitDealJobAll(Collection<UdsJobBean> collections) {
		waitDealJobList.addAll(collections);
	}

	/** 判断等待处理 */
	public boolean isWaitDealJobinsh() {
		return waitDealJobList.size() > 0 ? false : true;
	}

	/** 获取并移除等待处理的作业 */
	private ArrayList<UdsJobBean> getNewAndRemove(int size) {
		ArrayList<UdsJobBean> list = new ArrayList<UdsJobBean>();
		int tmpSize = waitDealJobList.size();
		if (tmpSize > 0) {
			size = tmpSize > size ? size : tmpSize;
			list.addAll(waitDealJobList.subList(0, size));
			waitDealJobList.removeAll(list);
		}
		return list;
	}

	/** 添加处理中 */
	private ArrayList<UdsJobBean> addAllDealJobQueue(ArrayList<UdsJobBean> list) {
		list.iterator().forEachRemaining(new Consumer<UdsJobBean>() {
			@Override
			public void accept(UdsJobBean udsJobBean) {
				synchronized (dealJobQueue) {
					if (!dealJobQueue.contains(udsJobBean)) {
						dealJobQueue.offer(udsJobBean);
					} else {
						list.remove(udsJobBean);
					}
				}
			}
		});
		return list;
	}

	/** 等待处理到处理中的作业 */
	public ArrayList<UdsJobBean> transfromWaitToDeal(int transfromNum) {
		ArrayList<UdsJobBean> list = getNewAndRemove(transfromNum);
		addAllDealJobQueue(list);
		return list;
	}

	/** 移除处理中的 */
	public void removeDealJobQueueJob(UdsJobBean udsJobBean) {
		dealInitNum.getAndSet(0);
		dealJobQueue.remove(udsJobBean);
	}

	/** 处理中是否完成, */
	public boolean isDealJobfinsh() {
		int value = dealInitNum.incrementAndGet();
		if (value > 10) {
			dealJobQueue.clear();
			dealInitNum.getAndSet(0);
		}
		return dealJobQueue.size() > 0 ? false : true;
	}

	/** 添加在更新 */
	public void addUpdateDispatcherJobList(UdsJobBean jobBean) {
		synchronized (updateDispatcherJobList) {
			if (!updateDispatcherJobList.contains(jobBean)) {
				updateDispatcherJobList.add(jobBean);
			}
		}
	}

	/** 获取需要更新的作业 */
	public List<UdsJobBean> getNewOrRemoveUpdateDispatcherJobList() {
		List<UdsJobBean> list = new ArrayList<UdsJobBean>();
		synchronized (updateDispatcherJobList) {
			list.addAll(updateDispatcherJobList);
			updateDispatcherJobList.removeAll(list);
		}
		return list;
	}

	/** 添加到优先级分发对列 */
	public void addDispatcherQueue(UdsJobBean udsJobBean) {
		String key = udsJobBean.getPlatfromSytemKey();
		if (StringUtils.isBlank(key)) {
			return;
		}
		SystemConterBean systemConterBean = platformConterMap.get(key);
		if (systemConterBean == null) {
			platformConterMap.put(key,
					systemConterBean = new SystemConterBean(udsJobBean.getPlatform(), udsJobBean.getSystem()));
		}
		systemConterBean.offer(udsJobBean);
	}

	/** 添加到优先级分发对列 */
	public void addAllDispatcherQueue(List<UdsJobBean> list) {
		list.iterator().forEachRemaining(new Consumer<UdsJobBean>() {
			@Override
			public void accept(UdsJobBean udsJobBean) {
				addDispatcherQueue(udsJobBean);
			}
		});
	}

	/** 按照应用，优先级出列,不移除 */
	public UdsJobBean dispatcherQueuePeek(String key) {
		SystemConterBean systemConterBean = platformConterMap.get(key);
		if (systemConterBean == null) {
			return null;
		}
		return systemConterBean.peek();
	}

	/** 按照应用，优先级出列，移除 */
	public UdsJobBean dispatcherQueuePoll(String key) {
		SystemConterBean systemConterBean = platformConterMap.get(key);
		if (systemConterBean == null) {
			return null;
		}
		return systemConterBean.poll();
	}

	public boolean dispatcherQueueRemove(UdsJobBean udsJobBean) {
		String key = udsJobBean.getPlatfromSytemKey();
		if (StringUtils.isBlank(key)) {
			return false;
		}
		SystemConterBean systemConterBean = platformConterMap.get(key);
		if (systemConterBean == null) {
			return false;
		}
		return systemConterBean.remove(udsJobBean);
	}

	public int getWaitInitNum() {
		return waitInitNum;
	}

	public void setWaitInitNum(int initNum) {
		this.waitInitNum = initNum;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public ArrayList<UdsJobBean> getWaitDealJobList() {
		return waitDealJobList;
	}

	public void setWaitDealJobList(ArrayList<UdsJobBean> waitDealJobList) {
		this.waitDealJobList = waitDealJobList;
	}

	public ConcurrentLinkedQueue<UdsJobBean> getDealJobQueue() {
		return dealJobQueue;
	}

	public void setDealJobQueue(ConcurrentLinkedQueue<UdsJobBean> dealJobQueue) {
		this.dealJobQueue = dealJobQueue;
	}

	public ArrayList<UdsJobBean> getUpdateDispatcherJobList() {
		return updateDispatcherJobList;
	}

	public void setUpdateDispatcherJobList(ArrayList<UdsJobBean> updateDispatcherJobList) {
		this.updateDispatcherJobList = updateDispatcherJobList;
	}

	public ConcurrentHashMap<String, SystemConterBean> getPlatformConterMap() {
		return platformConterMap;
	}

	public void setPlatformConterMap(ConcurrentHashMap<String, SystemConterBean> platformConterMap) {
		this.platformConterMap = platformConterMap;
	}

	public AtomicInteger getDealInitNum() {
		return dealInitNum;
	}

	public void setDealInitNum(AtomicInteger dealInitNum) {
		this.dealInitNum = dealInitNum;
	}

}
