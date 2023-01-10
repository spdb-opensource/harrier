package cn.spdb.harrier.service.registry;

import java.util.Collection;

import cn.spdb.harrier.dao.entity.UdsServer;

public interface HarrierRegistry {
	
	

	UdsServer registrydb(UdsServer udsServer, SubscribeListener listener);

	void unregistrydb(String ip, int port);

	void addListener(SubscribeListener listener);
	
	UdsServer getLeader(String nodeClusterType);
	
	Collection<UdsServer> getUdsServerList();
}
