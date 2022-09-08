package cn.spdb.harrier.common.enmus.alarm;

public enum SendStatus {
NEW("NEW"),SENDING("SENDING"),STOP("STOP"),SUCC("SUCC"),FAIL("FAIL");
	
	private String name;

	private SendStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
