package cn.spdb.harrier.api.utils;

public enum JobDeployStatus {
	// 变更状态相关
	UPDATE_ADD("新增", 1),
	UPDATE_CHANGE("变更", 2),
	UPDATE_DEL("下线", 3),
	
	// 任务状态相关
	PROCESS_REDAY("待投产", 1),
	PROCESS_FAILED("投产失败", 2),
	PROCESS_SUCCESS("投产成功", 3),
	
	// 同步状态相关
	SYN_COMMON("正常线上开发无需同步", 0),
	SYN_SUCCESS("同步成功", 1),
	SYN_FAILED("同步失败", 2),
	
	// 作业触发类型
	STREAM_JOB("作业触发", 0),
	STREAM_CRON("定时触发", 1),
	STREAM_SIGNAL("信号文件触发", 2);
	
	
	
	private JobDeployStatus(String key, Integer value) {
		this.key = key;
		this.value = value;
	}
	// 标识
	private String key;
	// 值
	private Integer value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
		
}
