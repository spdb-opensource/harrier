package cn.spdb.harrier.common.enmus.alarm;

public enum AlarmStatus {
	HIDE("HIDE"), INFO("INFO"), WARN("WARN"), DEAL("DEAL"), END("END");

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

	public static AlarmStatus indexOf(String name) {
		for (AlarmStatus state : values()) {
			if (state.getName().equals(name)) {
				return state;
			}
		}
		return null;
	}
}
