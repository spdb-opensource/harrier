package cn.spdb.harrier.rpc.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.common.RpcResponse;

public class RpcFuture implements Future<Object> {

	private CountDownLatch latch = new CountDownLatch(1);

	private RpcResponse rpcRespose;
	private RpcRequest rpcRequest;

	private long requestId;

	public RpcFuture(RpcRequest rpcRequest, long requestId) {
		this.requestId = requestId;
		this.rpcRequest = rpcRequest;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public RpcResponse get(){
		boolean success=false;
		try {
			success = latch.await(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!success) {
			throw new RuntimeException("Timeout exception request id : " + this.requestId + " Request class name :"
					+ this.rpcRequest.getClassName() + "request method :" + this.rpcRequest.getMethodName());
		}
		return rpcRespose;
	}

	@Override
	public RpcResponse get(long timeout, TimeUnit unit) {
		boolean success=false;
		try {
			success = latch.await(timeout, unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!success) {
			throw new RuntimeException("Timeout exception request id : " + this.requestId + " Request class name :"
					+ this.rpcRequest.getClassName() + "request method :" + this.rpcRequest.getMethodName());
		}
		return rpcRespose;
	}

	public void done(RpcResponse response) {
		this.rpcRespose = response;
		latch.countDown();
	}
}
