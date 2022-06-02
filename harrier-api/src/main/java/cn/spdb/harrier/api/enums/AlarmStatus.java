package cn.spdb.harrier.api.enums;

public enum AlarmStatus {
	HIDE("HIDE"), INFO("INFO"), WARN("WARN"), DEAL("DEAL"),END("END");

	private AlarmStatus(String msg) {
		this.name = msg;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String msg) {
		this.name = msg;
	}

	public static AlarmStatus indexOf(int index) {
		if(values().length>index) {
			return values()[index];
		}
		return null;
	}
}
