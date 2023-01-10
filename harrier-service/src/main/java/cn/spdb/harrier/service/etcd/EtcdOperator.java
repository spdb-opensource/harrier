package cn.spdb.harrier.service.etcd;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.etcd.jetcd.Client;
import io.etcd.jetcd.Election;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.Txn;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.op.Cmp;

@Component
public class EtcdOperator {

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}

	@Value("${harrier.etcd.endpoints:}")

	private String endpoints;

	
	private Client client;

	private KV kv;

	private Lock lock;

	private Lease lease;

	private Watch watch;

	private Election election;

	@PostConstruct
	private void init() {
		if(StringUtils.isEmpty(endpoints)) {
			return;
		}
		client = Client.builder().endpoints(endpoints.split(",")).build();
		kv = client.getKVClient();
		lock = client.getLockClient();
		lease = client.getLeaseClient();
		watch = client.getWatchClient();
		election = client.getElectionClient();
	}

	@PreDestroy
	private void Destroy() {
		if(ObjectUtils.isEmpty(client)) {
			return;
		}
		kv.close();
		lock.close();
		lease.close();
		watch.close();
		election.close();
		client.close();
	}

	public KV getKv() {
		return kv;
	}

	public void setKv(KV kv) {
		this.kv = kv;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
	}

	public Watch getWatch() {
		return watch;
	}

	public void setWatch(Watch watch) {
		this.watch = watch;
	}

}
