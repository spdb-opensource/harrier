package cn.spdb.harrier.rpc.common;

import cn.spdb.harrier.rpc.client.RpcRequestTable;
import cn.spdb.harrier.rpc.compress.CompressEnum;
import cn.spdb.harrier.rpc.protocol.ProtocolEventType;
import cn.spdb.harrier.rpc.protocol.ProtocolHeader;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.serializer.SerializeEnum;

public class RpcRequest {

	private String className;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;

//	private Byte eventType;
//
//	private Boolean ack;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String getHandlerMethName() {
		return className + "." + methodName;
	}

//	public Byte getEventType() {
//		return eventType;
//	}
//
//	public void setEventType(Byte eventType) {
//		this.eventType = eventType;
//	}
//
//	public Boolean getAck() {
//		return ack;
//	}
//
//	public void setAck(Boolean ack) {
//		this.ack = ack;
//	}

	public static RpcProtocol<RpcRequest> buildProtocol(RpcRequest request) {
		return buildProtocol(request, CompressEnum.NO.getType());
	}

	public static RpcProtocol<RpcRequest> buildProtocol(RpcRequest request, byte compressType) {
		return buildProtocol(request, SerializeEnum.PROTOSTUFF.getType(), compressType);
	}

	public static RpcProtocol<RpcRequest> buildProtocol(RpcRequest request, byte serialization, byte compressType) {
		RpcProtocol<RpcRequest> protocol = new RpcProtocol<RpcRequest>();
		ProtocolHeader header = new ProtocolHeader();
		header.setEventType(ProtocolEventType.REQUEST.getType());
		header.setCompressType(compressType);
		header.setSerialization(serialization);
		header.setRequestId(RpcRequestTable.getRequestId());
		protocol.setProtocolHeader(header);
		protocol.setBody(request);
		return protocol;
	}
}
