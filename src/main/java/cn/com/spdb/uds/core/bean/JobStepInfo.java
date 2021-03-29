package cn.com.spdb.uds.core.bean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class JobStepInfo implements Comparable<JobStepInfo> {

	// 脚本序列号
	private byte setpNum;
	// 环境变量
	private Map<String, String> envMap;
	// 命令参数 perl txt.pl 2019
	private List<String> cmdList;
	// 工作路径
	public String workDir;
	// 脚本类型
	private String stepType;
	// 保存日志路径
	private String logDir;
	// 日志
	private String logName;
	
	private String script_name;

	// 开始时间
	private Timestamp startTime;
	// 结束时间
	private Timestamp endtTime;
	// 返回码
	private int returnCode = 0;

	public byte getSetpNum() {
		return setpNum;
	}

	public void setSetpNum(byte setpNum) {
		this.setpNum = setpNum;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp timestamp) {
		this.startTime = timestamp;
	}

	public Timestamp getEndtTime() {
		return endtTime;
	}

	public void setEndtTime(Timestamp endtTime) {
		this.endtTime = endtTime;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	@Override
	public int compareTo(JobStepInfo o) {
		return getSetpNum() - o.getSetpNum();
	}

	public String getWorkDir() {
		return workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public Map<String, String> getEnvMap() {
		return envMap;
	}

	public void setEnvMap(Map<String, String> envMap) {
		this.envMap = envMap;
	}

	public List<String> getCmdList() {
		return cmdList;
	}

	public void setCmdList(List<String> cmdList) {
		this.cmdList = cmdList;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getScript_name() {
		return script_name;
	}

	public void setScript_name(String script_name) {
		this.script_name = script_name;
	}

	
}
