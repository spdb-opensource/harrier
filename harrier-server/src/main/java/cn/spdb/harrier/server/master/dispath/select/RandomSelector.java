
package cn.spdb.harrier.server.master.dispath.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSelector extends AbstractSelector<HostWeight> {

	@Override
	public HostWeight doSelect(final Collection<HostWeight> source) {

		List<HostWeight> hosts = new ArrayList<>(source);
		int size = hosts.size();
		int[] weights = new int[size];
		int totalWeight = 0;
		
		int index = 0;
		for (HostWeight host : hosts) {
			totalWeight += host.getWeight();
			weights[index] = host.getWeight();
			index++;
		}

		if (totalWeight > 0) {
			int offset = ThreadLocalRandom.current().nextInt(totalWeight);
			for (int i = 0; i < size; i++) {
				offset -= weights[i];
				if (offset < 0) {
					return hosts.get(i);
				}
			}
		}
		return hosts.get(ThreadLocalRandom.current().nextInt(size));
	}

}
