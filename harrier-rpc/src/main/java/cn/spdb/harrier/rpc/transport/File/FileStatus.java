package cn.spdb.harrier.rpc.transport.File;

public enum FileStatus {
	LOAD_FILE((byte) 2), UPLOAD_FILE((byte) 1), SUCC_FILE((byte) 0), FAIL_FILE((byte) -1);
	;

	private Byte type;

	private FileStatus(Byte type) {
		this.type = type;
	}

	public byte status() {
		return type;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public static FileStatus getFileStatus(Byte status) {
		for (FileStatus fileStatus : FileStatus.values()) {
			if (fileStatus.status() == status) {
				return fileStatus;
			}
		}
		return null;
	}

}
