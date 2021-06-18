package cn.com.spdb.uds.core.bean;

public enum UdsErrorCode {
	// ---- 1-99----// 系统默认预留
	
	// -----100-199----// 系统
	SERVER_DEATH(100,"机器死亡"),
	SERVER_ACTIVE(101,"机器重新活跃"),

	// -----400-499----// 作业报错
	SIGNA_NORMALIZE_ERROR(400, "信号文件不符规范"),
	JOB_DB_NULL(402, "数据库配置错误"),
	JOB_RCV_STATUS_ERROR(403, "接受信号文件的运行状态不正确"),
	JOB_RCV_DATE_ERROR(404, "接受信号文件作业日期不正确"),
	JOB_RCV_FREQUENCY_ERROR(405, "接受信号文件作业日频率隔不符合"),
	JOB_DELAY(406,"作业执行超过时间窗口"),
	JOB_DISTRIBUT(407,"作业分发失败"),
	JOB_SCRIPT(408,"作业脚本执行失败"),
	JOB_FORCE_START(409,"作业强制执行"),
	JOB_STREAM_FREQUENCY_ERROR(410,"触发作业的日期不正确"),
	JOB_STREAM_STATUS_ERROR(411,"触发作业的运行状态不正确"),
	JOB_SCHEDULER_STATUS_ERROR(412,"定时作业的运行状态不正确"),
	JOB_RCV_BACTH_ERROR(413, "接受信号文件作业批次号不正确"),
	JOB_STREAM_BACTH_ERROR(414, "触发作业批次号不正确"),
	JOB_SCHEDULER_BATCH_ERROR(415,"定时作业的批次不正确"),
	JOB_TAG_IS_NOT_EIST(416,"作业标签分发没有对应的机器"),
	JOB_FINSH_NOTICE(450,"作业完成"),
	;

	private int code;

	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private UdsErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int code() {
		return getCode();
	}
}
