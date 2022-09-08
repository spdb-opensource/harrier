package cn.spdb.harrier.rpc.client;

public class RpcRequestCache {

	private RpcFuture future;

	private String handlerMethName;

	public RpcFuture getFuture() {
		return future;
	}

	public void setFuture(RpcFuture future) {
		this.future = future;
	}

	public String getHandlerMethName() {
		return handlerMethName;
	}

	public void setHandlerMethName(String serviceName) {
		this.handlerMethName = serviceName;
	}

}
