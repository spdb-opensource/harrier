package cn.spdb.harrier.server.master.dispath.select;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

public abstract class AbstractSelector<T> implements Selector<T> {

	@Override
	public T select(Collection<T> sources) {

		if (CollectionUtils.isEmpty(sources)) {
			return null;
		}
		if (sources.size() == 1) {
			return (T) sources.toArray()[0];
		}
		try {

			return doSelect(sources);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected abstract T doSelect(Collection<T> sources);

}
