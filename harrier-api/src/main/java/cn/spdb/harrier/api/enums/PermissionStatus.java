package cn.spdb.harrier.api.enums;

public enum PermissionStatus {
	R("R"), W("W"), RW("RW");

	private String status;

	private PermissionStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
