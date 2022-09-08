package cn.spdb.harrier.service.etcd;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.lock.UnlockResponse;

public class EtcdLock {

	private EtcdOperator etcdOperator;

	public boolean lock(String lockId, long seconds) {
		ByteSequence lockKey = ByteSequence.from(lockId, StandardCharsets.UTF_8);
		long leaseId = 0;
		try {
			leaseId = etcdOperator.getLease().grant(seconds, seconds + 1, TimeUnit.SECONDS).get().getID();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		CompletableFuture<LockResponse> future = etcdOperator.getLock().lock(lockKey, leaseId);
		return future.isDone();
	}

	public void lock(String lockId) {
		lock(lockId, 5);
	}

	public boolean unlock(String lockId) {
		ByteSequence lockKey = ByteSequence.from(lockId, StandardCharsets.UTF_8);
		CompletableFuture<UnlockResponse> future = etcdOperator.getLock().unlock(lockKey);
		return future.isDone();
	}
}
