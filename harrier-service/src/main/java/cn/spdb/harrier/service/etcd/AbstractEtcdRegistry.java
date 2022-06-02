package cn.spdb.harrier.service.etcd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cn.spdb.harrier.service.registry.HarrierRegistry;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Election;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.Watch.Watcher;
import io.etcd.jetcd.election.LeaderResponse;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;
import io.grpc.stub.StreamObserver;
import io.netty.util.internal.StringUtil;

public abstract class AbstractEtcdRegistry implements HarrierRegistry{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EtcdOperator etcdOperator;

	@Value("${etcdRegistryDir:root}")
	private String etcdRegistryDir;

	@Value("${namespace:namespace}")
	private String namespace;

	@Value("${group:group}")
	private String group;

	@Value("${serverName:serverName}")
	private String serverName;

	@Value("${heartSeccend:60}")
	private int heartSeconds;

	@Value("${maxTimeOut:180}")
	private int maxTimeOut;

	private long updateMillisTime;

	private String SERVER_REGISTRY_PATH;

	private String GROUP_REGISTRY_PATH;

	private final AtomicBoolean isStarted = new AtomicBoolean(false);

	public abstract void discover(WatchEvent watchEvent);

	public abstract void unDiscover(WatchEvent watchEvent);

	public abstract void heartbeat(LeaseKeepAliveResponse leaseKeepAliveResponse);

	public abstract void unHeartbeat();

	@PostConstruct
	private void init() {
		GROUP_REGISTRY_PATH = etcdRegistryDir + "/" + namespace + "/" + group + "/";
		SERVER_REGISTRY_PATH = GROUP_REGISTRY_PATH + serverName;
		Executors.newScheduledThreadPool(1, r -> {
			return new Thread(r, this.getClass().getSimpleName());
		}).scheduleAtFixedRate(() -> {
			try {
				if (System.currentTimeMillis() - updateMillisTime > maxTimeOut) {
					isStarted.getAndSet(false);
				}
				if (!isStarted.get()) {
					String ip = InetAddress.getLocalHost().getHostAddress();
					registerInstance(SERVER_REGISTRY_PATH, "test:" + ip);
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}, 5, heartSeconds, TimeUnit.SECONDS);

		subscribe(onNext -> {
			onNext.getEvents().forEach(action -> {
				KeyValue keyvalue = action.getKeyValue();
				String key = keyvalue.getKey().toString(StandardCharsets.UTF_8);
				String value = keyvalue.getValue().toString(StandardCharsets.UTF_8);
				switch (action.getEventType()) {
				case PUT: {
					LOGGER.debug("disocver server [{}] : [{}]", key, value);
					discover(action);
				}
					break;
				case DELETE: {
					LOGGER.debug("remove disocver server [{}] : [{}]", key, value);
					unDiscover(action);
				}
					break;
				default:
					break;
				}
			});
		});
	}

	@PreDestroy
	private void destroy() {
		deregisterInstance(SERVER_REGISTRY_PATH);
	}

	public void registerInstance(String serverPath, String metadata) {
		registerInstance(serverPath, metadata, "", false);
	}

	public void registerInstance(String serverNamePath, String metadata, String electionName, boolean isElection) {
		ByteSequence electionNameKey = ByteSequence.from(electionName, StandardCharsets.UTF_8);
		ByteSequence key = ByteSequence.from(serverNamePath, StandardCharsets.UTF_8);
		ByteSequence value = ByteSequence.from(metadata, StandardCharsets.UTF_8);
		KV kv = etcdOperator.getKv();
		Lease lease = etcdOperator.getLease();
		long leaseId = 0;
		try {
			leaseId = lease.grant(heartSeconds, heartSeconds, TimeUnit.SECONDS).get().getID();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		StreamObserver<LeaseKeepAliveResponse> observer = new StreamObserver<LeaseKeepAliveResponse>() {
			@Override
			public void onNext(LeaseKeepAliveResponse value) {
				LOGGER.debug("continue connect to ETCD ,lease id [{}]", value.getID());
				isStarted.getAndSet(true);
				heartbeat(value);
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

			}
		};
		lease.keepAlive(leaseId, observer);
		PutOption option = PutOption.newBuilder().withLeaseId(leaseId).build();
		kv.put(key, value, option);
		if (isElection) {
			Election election = etcdOperator.getElection();
			election.campaign(electionNameKey, leaseId, key);
		}
	}

	public void deregisterInstance(String serverPath) {
		ByteSequence key = ByteSequence.from(serverPath, StandardCharsets.UTF_8);
		KV kv = etcdOperator.getKv();
		Lease lease = etcdOperator.getLease();
		DeleteOption deleOption = DeleteOption.newBuilder().withPrevKV(true).build();
		CompletableFuture<DeleteResponse> deleteFuture = kv.delete(key, deleOption);
		try {
			long leaseId = deleteFuture.get().getPrevKvs().get(0).getLease();
			lease.revoke(leaseId);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public List<KeyValue> getAllInstance(String tmpKey) {
		KV kv = etcdOperator.getKv();
		ByteSequence key = ByteSequence.from(tmpKey, StandardCharsets.UTF_8);
		GetOption option = GetOption.newBuilder().withRange(key).build();
		CompletableFuture<GetResponse> getFuture = kv.get(key, option);
		List<KeyValue> kvs = new ArrayList<KeyValue>();
		try {
			kvs = getFuture.get().getKvs();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return kvs;
	}

	public Watcher subscribe(Consumer<WatchResponse> onNext) {
		return subscribe(null, null, onNext);
	}

	public Watcher subscribe(String group, Consumer<WatchResponse> onNext) {
		return subscribe(group, null, onNext);
	}

	public Watcher subscribe(String group, String serverName, Consumer<WatchResponse> onNext) {
		Watch watch = etcdOperator.getWatch();
		String tmpKey = etcdRegistryDir + "/" + namespace + "/";
		if (!StringUtil.isNullOrEmpty(group)) {
			tmpKey = tmpKey + group + "/";
			if (!StringUtil.isNullOrEmpty(serverName)) {
				tmpKey = tmpKey + serverName;
			}
		}
		ByteSequence key = ByteSequence.from(tmpKey, StandardCharsets.UTF_8);
		WatchOption option = WatchOption.newBuilder().withRange(key).build();
		return watch.watch(key, option, onNext);
	}

	public KeyValue getLeader(String electionName) {
		Election election = etcdOperator.getElection();
		ByteSequence electionNameKey = ByteSequence.from(electionName, StandardCharsets.UTF_8);
		CompletableFuture<LeaderResponse> leaderfuture = election.leader(electionNameKey);
		KeyValue keyValue = null;
		try {
			keyValue = leaderfuture.get().getKv();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return keyValue;
	}

	public String getEtcdRegistryDir() {
		return etcdRegistryDir;
	}

	public void setEtcdRegistryDir(String etcdRegistryDir) {
		this.etcdRegistryDir = etcdRegistryDir;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public AtomicBoolean getIsStarted() {
		return isStarted;
	}

	public String getSERVER_REGISTRY_PATH() {
		return SERVER_REGISTRY_PATH;
	}

	public void setSERVER_REGISTRY_PATH(String sERVER_REGISTRY_PATH) {
		SERVER_REGISTRY_PATH = sERVER_REGISTRY_PATH;
	}

	public String getGROUP_REGISTRY_PATH() {
		return GROUP_REGISTRY_PATH;
	}

	public void setGROUP_REGISTRY_PATH(String gROUP_REGISTRY_PATH) {
		GROUP_REGISTRY_PATH = gROUP_REGISTRY_PATH;
	}
}
