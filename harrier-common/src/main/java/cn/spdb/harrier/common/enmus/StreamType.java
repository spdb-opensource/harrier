package cn.spdb.harrier.common.enmus;

public enum StreamType {
	TIMING_STREAM(1, "定时任务"), SINGN_FILE(2, "信号文件"), HTTP(3, "HTTP请求"), JOB_STREAM(4, "作业触发"), INVOKE_STREAM(5, "置Pending"),
	FORCE_START(6, "补数"),SELF_JOB_STREAM(7,"自检测触发"),DEP_CHANGE_STREAM(8,"修改依赖触发");

	private int id;
	private String name;

	private StreamType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
