package cn.com.spdb.uds.core.rpc.transfer;

import com.baidu.jprotobuf.pbrpc.CompressType;
import com.baidu.jprotobuf.pbrpc.ProtobufRPC;

import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;

public interface TransferRpc {

	/***
	 * 请求传输
	 */
	@ProtobufRPC(serviceName = "udsTransferRpc", methodName = "transferEvent", onceTalkTimeout = 1000 * 10, compressType = CompressType.GZIP)
	public void transferEvent(UdsRpcEvent udsRpcEvent);

	/***
	 * 有返回信息请求传输
	 */
	@ProtobufRPC(serviceName = "udsTransferRpc", methodName = "transferEventConcurrent", onceTalkTimeout = 1000 * 10, compressType = CompressType.GZIP)
	public UdsRpcEvent transferEventConcurrent(UdsRpcEvent udsRpcEvent);
}
