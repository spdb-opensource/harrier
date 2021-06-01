package cn.com.spdb.uds.core.bean;

public enum JobStatus {
	READY("Ready", "准备"), PENDING("Pending", "等待"), DISPATCHER("Dispatcher", "分发"), RUNING("Running", "运行"),
	DONE("Done", "完成"), FAILED("Failed", "失败"), UNKNOWN("Unknown", "未知");

	private String id;

	private String name;

	private JobStatus(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String status() {
		return id;
	}

	public JobStatus getJoyStatus(String status) {
		for (JobStatus s : JobStatus.values()) {
			if (s.name.equals(status)) {
				return s;
			}
		}
		return null;
	}
}
