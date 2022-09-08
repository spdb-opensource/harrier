package cn.spdb.harrier.rpc.common;

public enum RequestEventType {
	HEARTBET((byte) 1, "heartbeat"), BUSINESS((byte) 2, "business request");

	private Byte type;
	private String description;

	private RequestEventType(Byte type, String description) {
		this.type = type;
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
