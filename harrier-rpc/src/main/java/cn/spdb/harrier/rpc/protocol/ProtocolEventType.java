package cn.spdb.harrier.rpc.protocol;

public enum ProtocolEventType {

	HEARTBEAT(1, "heartbeat"), 
	REQUEST(2, "business request"),
	RESPONSE( 3, "business response"),
	COMPERESS_ERROR(4, "comperess error");

	private Byte type;
	private String description;

	private ProtocolEventType(int type, String description) {
		this.type = (byte) type;
		this.description = description;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
