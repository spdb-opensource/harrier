package cn.com.spdb.uds.core.bean;

import java.util.Comparator;
import java.util.PriorityQueue;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.utils.Symbol;

public class SystemConterBean {

	private String platform;
	private String system;

	private long updateTime = System.currentTimeMillis();

	private PriorityQueue<UdsJobBean> priQueue = new PriorityQueue<UdsJobBean>(150, new Comparator<UdsJobBean>() {
		@Override
		public int compare(UdsJobBean o1, UdsJobBean o2) {
			if (o1.getPriority() == o2.getPriority()) {
				if (o1.getDispatcher_time() != null && o2.getDispatcher_time() != null) {
					if (o1.getDispatcher_time().equals(o2.getDispatcher_time())) {
						return o1.getJob().compareTo(o2.getJob());
					}
					return o1.getDispatcher_time().compareTo(o2.getDispatcher_time());
				}
			}
			return o2.getPriority() - o1.getPriority();
		}
	});

	public SystemConterBean(String platform, String system) {
		UdsSystemBean bean=UdsConstant.getUdsSystemBean(platform, system);
		this.platform = platform;
		this.system = bean.getUse_platform()==UdsConstant.TRUE_NUM? Symbol.XING_HAO:system;

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
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
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
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		return true;
	}

	public String getPlatformSystemId() {
		return platform+Symbol.XIA_HUA_XIAN+system;
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