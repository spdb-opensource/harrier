package cn.spdb.harrier.rpc.serializer;

public enum SerializeEnum {
	FastJson((byte) 1, new FastJsonSerializer()), PROTOSTUFF((byte) 2, new ProtoStuffSerializer());

	private byte type;
	private Serializer serializer;

	private SerializeEnum(byte type, Serializer serializer) {
		this.type = type;
		this.serializer = serializer;
	}

	public static Serializer getSerializerByType(byte type) {
		for (SerializeEnum serializeEnum : SerializeEnum.values()) {
			if (serializeEnum.getType() == type) {
				return serializeEnum.getSerializer();
			}
		}
		return null;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

}
