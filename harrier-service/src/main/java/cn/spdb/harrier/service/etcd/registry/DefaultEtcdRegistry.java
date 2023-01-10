package cn.spdb.harrier.service.etcd.registry;

import java.util.Collection;

import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.service.etcd.AbstractEtcdRegistry;
import cn.spdb.harrier.service.registry.SubscribeListener;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.watch.WatchEvent;

public class DefaultEtcdRegistry extends AbstractEtcdRegistry {

	@Override
	public void discover(WatchEvent watchEvent) {

	}

	@Override
	public void unDiscover(WatchEvent watchEvent) {

	}

	@Override
	public void heartbeat(LeaseKeepAliveResponse leaseKeepAliveResponse) {

	}

	@Override
	public void unHeartbeat() {
		
	}

	@Override
	public UdsServer registrydb(UdsServer udsServer, SubscribeListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregistrydb(String ip, int port) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(SubscribeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UdsServer getLeader(String nodeClusterType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<UdsServer> getUdsServerList() {
		// TODO Auto-generated method stub
		return null;
	}
}
