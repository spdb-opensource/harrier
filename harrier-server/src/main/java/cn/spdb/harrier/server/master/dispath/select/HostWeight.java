package cn.spdb.harrier.server.master.dispath.select;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import cn.spdb.harrier.common.utils.Host;

public class HostWeight {

	private Host host;
	private int weight;
	private AtomicLong current = new AtomicLong(0);
	private LocalDateTime updateTime;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
		current.set(0);
	}

	public long increaseCurrent() {
		return current.addAndGet(weight);
	}

	public void sel(int total) {
		current.addAndGet(-1L * total);
	}

	public long getCurrentWeight() {
		return current.get();
	}

	public AtomicLong getCurrent() {
		return current;
	}

	public void setCurrent(AtomicLong current) {
		this.current = current;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

}
