package cn.spdb.harrier.rpc.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RpcRequestTable {

	private static AtomicLong requestIdGen = new AtomicLong(0);

	private static ConcurrentHashMap<Long, RpcRequestCache> requestMap = new ConcurrentHashMap<Long, RpcRequestCache>();

	public static void put(Long key, RpcRequestCache requestCache) {
		requestMap.put(key, requestCache);
	}

	public static RpcRequestCache get(Long key) {
		return requestMap.get(key);
	}

	public static void remove(long key) {
		requestMap.remove(key);
	}

	public static long getRequestId() {
		return requestIdGen.incrementAndGet();
	}
}
