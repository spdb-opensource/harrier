package cn.spdb.harrier.common.enmus;

public enum DownLoadResourceProtocol {
	HTTP("http"), HTTPS("https"), SCP("scp"), AWS("aws"), SPDB("spdb"),LOCAL("local");

	private String name;

	private DownLoadResourceProtocol(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static DownLoadResourceProtocol valueNaeOf(String name) {
		for (DownLoadResourceProtocol protocol : DownLoadResourceProtocol.values()) {
			if (protocol.getName().equals(name)) {
				return protocol;
			}
		}
		return null;
	}

}
