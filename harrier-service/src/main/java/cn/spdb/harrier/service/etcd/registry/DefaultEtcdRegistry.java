package cn.spdb.harrier.service.etcd.registry;

import cn.spdb.harrier.service.etcd.AbstractEtcdRegistry;
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
}
