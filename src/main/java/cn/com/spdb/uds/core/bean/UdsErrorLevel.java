package cn.com.spdb.uds.core.bean;


public enum UdsErrorLevel {
	L((byte) 1, "低级"), 
	M((byte) 2, "中级"), 
	H((byte) 3, "高级"),
	;

	private byte level;

	private String name;

	private UdsErrorLevel(byte level, String name) {
		this.level = level;
		this.name = name;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte level() {
		return getLevel();
	}

}
