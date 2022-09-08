package cn.spdb.harrier.rpc.common;

public enum ResponseEventType {
	ACK((byte) 1, "ack"), BUSINESS_RSP((byte) 2, "business response");

	private Byte type;
	private String description;

	private ResponseEventType(Byte type, String description) {
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
