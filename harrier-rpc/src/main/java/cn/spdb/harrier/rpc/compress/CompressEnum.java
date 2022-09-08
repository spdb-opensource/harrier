package cn.spdb.harrier.rpc.compress;

public enum CompressEnum {
	NO(0, null), SNAPPY(1, new SnappyCompress()), GIZP(2, new GZipCompress());

	private Compress compress;
	private byte type;

	private CompressEnum(int type, Compress compress) {
		this.compress = compress;
		this.type = (byte) type;
	}

	public Compress getCompress() {
		return compress;
	}

	public void setCompress(Compress compress) {
		this.compress = compress;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public static Compress getCompressByType(byte type) {
		for (CompressEnum compressEnum : CompressEnum.values()) {
			if (compressEnum.getType() == type) {
				return compressEnum.getCompress();
			}
		}
		return null;
	}
}
