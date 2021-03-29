package cn.com.spdb.uds.db.bean;

import java.sql.Timestamp;

public class UdsJobStepRecord {

	//id
	private long id;
	// 平台
	private String platform;
	// 系统
	private String system;
	// 作业
	private String job;
	// 第几步
	private byte setp_num;
	private long num_times;
	private Timestamp start_time;
	private Timestamp end_time;
	private String job_date;
	private String server_name;
	// 执行文件名
	private String script_name;
	private byte virtual_enable;
	private String log_path;
	private String log_name;
	private int return_code;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public byte getSetp_num() {
		return setp_num;
	}
	public void setSetp_num(byte setp_num) {
		this.setp_num = setp_num;
	}
	public long getNum_times() {
		return num_times;
	}
	public void setNum_times(long num_times) {
		this.num_times = num_times;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getJob_date() {
		return job_date;
	}
	public void setJob_date(String job_date) {
		this.job_date = job_date;
	}
	public String getScript_name() {
		return script_name;
	}
	public void setScript_name(String script_name) {
		this.script_name = script_name;
	}
	public String getLog_path() {
		return log_path;
	}
	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public int getReturn_code() {
		return return_code;
	}
	public void setReturn_code(int return_code) {
		this.return_code = return_code;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public byte getVirtual_enable() {
		return virtual_enable;
	}
	public void setVirtual_enable(byte virtual_enable) {
		this.virtual_enable = virtual_enable;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
