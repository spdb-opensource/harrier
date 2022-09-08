package cn.spdb.harrier.common.enmus;

public enum StreamType {
	TIMING_STREAM((byte) 1, "定时任务"), SINGN_FILE((byte) 2, "信号文件"), HTTP((byte) 3, "HTTP请求"), JOB_STREAM((byte) 4, "作业触发"), INVOKE_STREAM((byte) 5, "置Pending"),
	FORCE_START((byte) 6, "补数"),SELF_JOB_STREAM((byte) 7,"自检测触发"),DEP_CHANGE_STREAM((byte) 8,"修改依赖触发");

	private Byte id;
	private String name;

	private StreamType(Byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
