package cn.com.spdb.uds.log;

public enum LogEvent {
	CHILD_DISPATCHER(LogType.CHILD_LOG,"作业分发"),
	CHILD_JOB_STEP(LogType.CHILD_LOG,"执行脚本信息"),
	
	CHILD_JOB_STREAM(LogType.CHILD_LOG,"作业触发"),
	
	CHILD_JOB_STREAM_DATE_ERROR(LogType.CHILD_LOG,"作业触发时间不符合"),
	CHILD_JOB_STREAM_BATCH_ERROR(LogType.CHILD_LOG,"作业触发批次不符合"),
	CHILD_JOB_STREAM_FREQUENCY_ERROR(LogType.CHILD_LOG,"作业触发频率不符合要求"),
	CHILD_JOB_STREAM_READY(LogType.CHILD_LOG,"作业READY状态不检测时间频度"),
	CHILD_JOB_STREAM_JOB_SATUS_ERROR(LogType.CHILD_LOG,"触发作业状态不正确"),
	
	CHILD_JOB_STREAM_FAIL_JOB(LogType.CHILD_LOG,"作业触发失败的作业"),
	CHILD_FILE_STREAM_FAIL_JOB(LogType.CHILD_LOG,"触发文件夹触发作业更新"),
	
	
	MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL(LogType.MASTER_LOG,"作业触发创建信号文件失败"),
	MASTER_SIGNAL(LogType.MASTER_LOG,"信号文件"),
	MASTER_RCV(LogType.MASTER_LOG,"接受检测 "),
	MASTER_MOVE_FAIL(LogType.MASTER_LOG,"移动信号文件失败 "),
	MASTER_PENDIG(LogType.MASTER_LOG,"作业等待"),
	MASTER_DISPATCHER(LogType.MASTER_LOG,"作业分发"),
	MASTER_SCHEDULER(LogType.MASTER_LOG,"作业定时"),
	MASTER_JOB_CALL_AGAIN(LogType.MASTER_LOG,"主节点作业重调"),
	MASTER_JOB_RCV_STATUS_ERROR(LogType.MASTER_LOG,"接收信号文件作业状态不正确"),
	MASTER_JOB_SCHEDULER_STATUS_ERROR(LogType.MASTER_LOG,"定时作业状态不正确"),
	
	BACK_CONSOLE(LogType.BACK_GROUND_LOG,"控制平台"),
	HTTP_CONSOLE(LogType.BACK_GROUND_LOG,"监控平台"),
	HTTP_CONSOLE_COMMAND(LogType.BACK_GROUND_LOG,"监控平台发送的命令"),
	BACK_ERROR(LogType.BACK_GROUND_LOG,"控制平台错误"),
	HTTP_ERROR(LogType.BACK_GROUND_LOG,"监控平台错误"),
	
	ERROR(LogType.ERROR_LOG,"ERROR"),
	ERROR_DB(LogType.ERROR_LOG,"ERROR_DB"),
	ERROR_THROWABLE(LogType.ERROR_LOG,"THROWABLE"),
	;
	private String msg;
	private LogType logType;
	private LogEvent(LogType logType,String msg) {
		this.msg = msg;
		this.logType = logType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public LogType getLogType() {
		return logType;
	}
	public void setLogType(LogType logType) {
		this.logType = logType;
	}


}
