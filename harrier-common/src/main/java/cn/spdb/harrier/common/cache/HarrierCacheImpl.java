package cn.spdb.harrier.common.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class HarrierCacheImpl implements HarrierCache {

	private final Cache<String, Object> cache;

	public HarrierCacheImpl() {
		this.cache = CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors())
				.initialCapacity(100).maximumSize(10000).expireAfterAccess(30, TimeUnit.MINUTES).build();
	}

	public <T> T get(String key) {
		return (T) cache.getIfPresent(key);
	}

	public void put(String key, Object object) {
		cache.put(key, object);
	}

	public void delete(String verifyKey) {
		cache.invalidate(verifyKey);
	}

	public <T> T get(String key, Callable<?> call) {
		try {
			return (T) cache.get(key, call);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
