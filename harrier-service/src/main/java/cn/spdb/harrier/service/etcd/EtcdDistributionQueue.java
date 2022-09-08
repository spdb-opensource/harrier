package cn.spdb.harrier.service.etcd;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSON;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.GetOption.SortOrder;
import io.etcd.jetcd.options.GetOption.SortTarget;


public class EtcdDistributionQueue<E> {

	private EtcdOperator etcdOperator;

	private final String queue_prefix;

	private final ByteSequence etcd_queue_prefix;

	
	public EtcdDistributionQueue(EtcdOperator etcdOperator, String queue_prefix) {
		this.etcdOperator = etcdOperator;
		this.queue_prefix = queue_prefix;
		this.etcd_queue_prefix = ByteSequence.from(queue_prefix, StandardCharsets.UTF_8);
	}

	public int size() {
		GetOption option = GetOption.newBuilder().withCountOnly(true).withRange(etcd_queue_prefix).build();
		int size = 0;
		try {
			size = (int) etcdOperator.getKv().get(etcd_queue_prefix, option).get().getCount();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return size;
	}

	public boolean isEmpty() {
		return size() <= 0;
	}

	public boolean contains(E e) {
		String json = JSON.toJSONString(e);
		ByteSequence etcdQueueObjectKey = ByteSequence.from(queue_prefix + "_" + json.hashCode(),
				StandardCharsets.UTF_8);
		CompletableFuture<GetResponse> getFuture = etcdOperator.getKv().get(etcdQueueObjectKey);
		try {
			return !getFuture.get().getKvs().isEmpty();
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public boolean remove(E e) {
		String json = JSON.toJSONString(e);
		ByteSequence etcdQueueObjectKey = ByteSequence.from(queue_prefix + "_" + json.hashCode(),
				StandardCharsets.UTF_8);
		DeleteOption option = DeleteOption.newBuilder().withPrevKV(true).build();
		CompletableFuture<DeleteResponse> deletFuture = etcdOperator.getKv().delete(etcdQueueObjectKey, option);
		return deletFuture.isDone();

	}

	public boolean clear() {
		DeleteOption option = DeleteOption.newBuilder().withPrevKV(true).withRange(etcd_queue_prefix).build();
		CompletableFuture<DeleteResponse> deletefuture = etcdOperator.getKv().delete(etcd_queue_prefix, option);
		return deletefuture.isDone();
	}

	public boolean add(E e) {
		String json = JSON.toJSONString(e);
		System.out.println(e + "----" + e.hashCode());
		ByteSequence etcdQueueObjectKey = ByteSequence.from(queue_prefix + "_" + json.hashCode(),
				StandardCharsets.UTF_8);
		ByteSequence value = ByteSequence.from(json, StandardCharsets.UTF_8);
		CompletableFuture<PutResponse> putfuture = etcdOperator.getKv().put(etcdQueueObjectKey, value);
		return putfuture.isDone();
	}

	public E peek() {
		GetOption option = GetOption.newBuilder().withSortOrder(SortOrder.ASCEND).withSortField(SortTarget.CREATE)
				.withLimit(1).withRange(etcd_queue_prefix).build();
		CompletableFuture<GetResponse> getfuture = etcdOperator.getKv().get(etcd_queue_prefix, option);
		try {
			List<KeyValue> kvList = getfuture.get().getKvs();
			if (!kvList.isEmpty()) {
				String jsonStr = kvList.get(0).getValue().toString(StandardCharsets.UTF_8);
				return (E) JSON.parseObject(jsonStr, Object.class);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public E poll() {
		GetOption option = GetOption.newBuilder().withSortOrder(SortOrder.ASCEND).withSortField(SortTarget.CREATE)
				.withLimit(1).withRange(etcd_queue_prefix).build();
		CompletableFuture<GetResponse> getfuture = etcdOperator.getKv().get(etcd_queue_prefix, option);
		try {
			List<KeyValue> kvList = getfuture.get().getKvs();
			if (!kvList.isEmpty()) {
				KeyValue kvKeyValue = kvList.get(0);
				String jsonStr = kvKeyValue.getValue().toString(StandardCharsets.UTF_8);
				etcdOperator.getKv().delete(kvKeyValue.getKey());
				return (E) JSON.parseObject(jsonStr, Object.class);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
