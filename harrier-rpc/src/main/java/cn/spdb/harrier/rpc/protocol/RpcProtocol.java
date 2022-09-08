package cn.spdb.harrier.rpc.protocol;

public class RpcProtocol<T> {

	private ProtocolHeader protocolHeader;

	private T body;

	public ProtocolHeader getProtocolHeader() {
		return protocolHeader;
	}

	public void setProtocolHeader(ProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
