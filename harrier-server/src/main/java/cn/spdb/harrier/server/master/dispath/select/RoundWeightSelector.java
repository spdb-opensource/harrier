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

public class RoundWeightSelector extends AbstractSelector<HostWeight> {

	private ConcurrentMap<String, HostWeight> workWeightMap = new ConcurrentHashMap<>();

	private static final int RECYCLE_PERIOD = 60 * 3;

	private AtomicBoolean updateLock = new AtomicBoolean();

	@Override
	public HostWeight doSelect(final Collection<HostWeight> sources) {

		List<HostWeight> hosts = new ArrayList<>(sources);

		int totalWeight = 0;
		long maxCurrent = Long.MIN_VALUE;
		LocalDateTime now = LocalDateTime.now();
		HostWeight selectedHost = null;
		HostWeight maxWeightHost = null;

		for (HostWeight host : hosts) {
			int weight = host.getWeight();
			if (weight < 0) {
				weight = 0;
			}
			String address = host.getHost().getAddress();
			HostWeight HostWeight = workWeightMap.get(address);
			if (HostWeight == null) {
				HostWeight = new HostWeight();
				HostWeight.setWeight(weight);
				workWeightMap.putIfAbsent(address, HostWeight);
				HostWeight = workWeightMap.get(address);
			}
			if (weight != HostWeight.getWeight()) {
				HostWeight.setWeight(weight);
			}

			long cur = HostWeight.increaseCurrent();
			HostWeight.setUpdateTime(now);
			if (cur > maxCurrent) {
				maxCurrent = cur;
				selectedHost = host;
				maxWeightHost = HostWeight;
			}
			totalWeight += weight;
		}

		if (!updateLock.get() && updateLock.compareAndSet(false, true)) {
			try {
				workWeightMap.entrySet().removeIf(
						item -> Duration.between(item.getValue().getUpdateTime(), now).getSeconds() > RECYCLE_PERIOD);
			} finally {
				updateLock.set(false);
			}
		}

		if (selectedHost != null) {
			maxWeightHost.sel(totalWeight);
			return selectedHost;
		}

		return hosts.get(ThreadLocalRandom.current().nextInt(hosts.size()));
	}
}
