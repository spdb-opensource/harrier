package cn.com.spdb.uds.db.bean;

public class UdsJobStepBean {
	// 平台
	private String platform;
	// 系统
	private String system;
	// 作业
	private String job;
	// 第几步
	private byte setp_num;

	// 命令 oper_cmd script_dir/script_name parameter
	// 执行的命令 如：perl , sh
	private String oper_cmd;
	// 执行文件目录
	private String script_dir;
	// 执行文件名
	private String script_name;
	// 附带参数 空格分割
	private String parameter;
	// 脚本类型
	private String step_type;
	private String environments;
	private String work_dir;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platfrom) {
		this.platform = platfrom;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getEnvironments() {
		return environments;
	}

	public void setEnvironments(String environments) {
		this.environments = environments;
	}

	public String getWork_dir() {
		return work_dir;
	}

	public void setWork_dir(String work_dir) {
		this.work_dir = work_dir;
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

	public String getOper_cmd() {
		return oper_cmd;
	}

	public void setOper_cmd(String oper_cmd) {
		this.oper_cmd = oper_cmd;
	}

	public String getScript_dir() {
		return script_dir;
	}

	public void setScript_dir(String script_dir) {
		this.script_dir = script_dir;
	}

	public String getScript_name() {
		return script_name;
	}

	public void setScript_name(String script_name) {
		this.script_name = script_name;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getStep_type() {
		return step_type;
	}

	public void setStep_type(String step_type) {
		this.step_type = step_type;
	}

}
