package cn.com.spdb.uds.core.bean;

import java.util.Comparator;
import java.util.PriorityQueue;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.db.bean.UdsJobBean;

public class SystemConterBean {

	private String platform;
	private String system;
	// 使用那个平台参数
	private String platformSystemId;

	private long updateTime = System.currentTimeMillis();

	private PriorityQueue<UdsJobBean> priQueue = new PriorityQueue<UdsJobBean>(150, new Comparator<UdsJobBean>() {
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

	public SystemConterBean(String platform, String system) {
		this.platform = platform;
		this.system = system;
		this.platformSystemId = UdsConstant.getUdsSystemBeanKey(platform, system);
	}

	public boolean isremove(long overtime) {
		return System.currentTimeMillis() - updateTime > overtime;
	}

	public void clearPriQueue() {
		priQueue.clear();
	}

	public synchronized UdsJobBean peek() {
		updateTime = System.currentTimeMillis();
		return priQueue.peek();
	}

	public synchronized UdsJobBean poll() {
		updateTime = System.currentTimeMillis();
		return priQueue.poll();
	}

	public synchronized boolean remove(UdsJobBean jobBean) {
		updateTime = System.currentTimeMillis();
		return priQueue.remove(jobBean);
	}

	public synchronized boolean offer(UdsJobBean jobBean) {
		if (priQueue.contains(jobBean)) {
			return true;
		}
		return priQueue.offer(jobBean);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((platformSystemId == null) ? 0 : platformSystemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemConterBean other = (SystemConterBean) obj;
		if (platformSystemId == null) {
			if (other.platformSystemId != null)
				return false;
		} else if (!platformSystemId.equals(other.platformSystemId))
			return false;
		return true;
	}

	public String getPlatformSystemId() {
		return platformSystemId;
	}

	public void setPlatformSystemId(String platfomSystemId) {
		this.platformSystemId = platfomSystemId;
	}

	public PriorityQueue<UdsJobBean> getPriQueue() {
		return priQueue;
	}

	public void setPriQueue(PriorityQueue<UdsJobBean> priQueue) {
		this.priQueue = priQueue;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

}