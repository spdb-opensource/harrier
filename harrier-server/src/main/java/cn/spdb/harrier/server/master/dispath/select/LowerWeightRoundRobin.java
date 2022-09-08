
package cn.spdb.harrier.server.master.dispath.select;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class LowerWeightRoundRobin extends AbstractSelector<HostWeight> {

	private ConcurrentMap<String, HostWeight> workGroupWeightMap = new ConcurrentHashMap<>();

	private static final int RECYCLE_PERIOD = 60 * 3;

	private AtomicBoolean updateLock = new AtomicBoolean();

	@Override
	public HostWeight doSelect(final Collection<HostWeight> sources) {
		List<HostWeight> hosts = new ArrayList<>(sources);
		int totalWeight = 0;
		HostWeight maxWeigth = null;
		HostWeight lowerNode = null;
		LocalDateTime now = LocalDateTime.now();

		for (HostWeight host : hosts) {
			String address = host.getHost().getAddress();
			HostWeight HostWeight = workGroupWeightMap.get(address);
			if (HostWeight == null) {
				workGroupWeightMap.putIfAbsent(address, new HostWeight());
				HostWeight = workGroupWeightMap.get(address);
			}

			if (!HostWeight.getUpdateTime().equals(host.getUpdateTime())) {
				HostWeight.setUpdateTime(host.getUpdateTime());
				HostWeight.setWeight(host.getWeight());
			}

			HostWeight.increaseCurrent();
			if (lowerNode == null || maxWeigth.getCurrentWeight() < HostWeight.getCurrentWeight()) {
				maxWeigth = HostWeight;
				lowerNode = host;
			}
			totalWeight += host.getWeight();
		}

		if (!updateLock.get() && updateLock.compareAndSet(false, true)) {
			try {
				workGroupWeightMap.entrySet().removeIf(
						item -> Duration.between(item.getValue().getUpdateTime(), now).getSeconds() > RECYCLE_PERIOD);
			} finally {
				updateLock.set(false);
			}
		}

		if (maxWeigth != null) {
			maxWeigth.sel(totalWeight);
			return lowerNode;
		}

		return hosts.get(ThreadLocalRandom.current().nextInt(hosts.size()));
	}
}
