package cn.spdb.harrier.rpc.client;

import java.util.concurrent.ConcurrentHashMap;

public class ConsumerConfigCache {

	private static ConcurrentHashMap<String, ConsumerConfig> concurrentHashMap = new ConcurrentHashMap<String, ConsumerConfig>();

	public static ConsumerConfig getConfigByServersName(String handlerMethName) {
		return concurrentHashMap.get(handlerMethName);
	}

	public static void putConfig(String handlerMethName,ConsumerConfig consumerConfig) {
		concurrentHashMap.putIfAbsent(handlerMethName, consumerConfig);
	}

}
