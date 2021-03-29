package cn.com.spdb.uds.core.bean;

public enum JobTagsType {

	server(1);

	private int type;

	private JobTagsType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
