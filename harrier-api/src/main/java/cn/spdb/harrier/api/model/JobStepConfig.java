package cn.spdb.harrier.api.model;

public class JobStepConfig {
	/**
	 * 脚本执行顺序
	 */
	private Byte stepNum; 
	
	/**
	 * 脚本执行命令
	 */
	private String operCmd;
	
	/**
	 * 脚本类型
	 */
	private String stepType;
	
	
	/**
	 * 脚本执行路径
	 */
	private String scriptPath;
	
	/**
	 * 脚本执行所需参数
	 */
	private String parameter;
	
	/**
	 * 环境参数
	 */
	private String environments;

	
	public String getEnvironments() {
		return environments;
	}

	public void setEnvironments(String environments) {
		this.environments = environments;
	}

	public Byte getStepNum() {
		return stepNum;
	}

	public void setStepNum(Byte stepNum) {
		this.stepNum = stepNum;
	}

	public String getOperCmd() {
		return operCmd;
	}

	public void setOperCmd(String operCmd) {
		this.operCmd = operCmd;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return stepNum + "@" + operCmd + "@" + stepType + "@" + scriptPath + "@" + parameter + "@" + environments;
	}
	
	
}
