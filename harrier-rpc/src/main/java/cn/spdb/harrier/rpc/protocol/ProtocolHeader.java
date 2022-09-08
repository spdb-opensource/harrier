package cn.spdb.harrier.rpc.protocol;

public class ProtocolHeader {

	private int magic=RpcProtocolConstants.MAGIC;
	private byte version=RpcProtocolConstants.VERSION;
	private byte eventType;
	private byte compressType;
	private byte serialization;
	private long requestId;
	private int dataLength;

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(short magic) {
		this.magic = magic;
	}

	public byte getEventType() {
		return eventType;
	}

	public void setEventType(byte eventType) {
		this.eventType = eventType;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getSerialization() {
		return serialization;
	}

	public void setSerialization(byte serialization) {
		this.serialization = serialization;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public byte getCompressType() {
		return compressType;
	}

	public void setCompressType(byte compressType) {
		this.compressType = compressType;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

}
