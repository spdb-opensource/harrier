package cn.spdb.harrier.common.cache;

import java.util.concurrent.Callable;

public interface HarrierCache {

	<T> T get(String key);

	void put(String key, Object object);

	void delete(String verifyKey);

	<T> T get(String key, Callable<?> call);
}
