package cn.spdb.harrier.common.enmus;

import java.util.Locale;

import cn.spdb.harrier.common.Constants;

public enum Message {
	SUCCESS(0, "success", "成功"), 
	SYSTEM_INFORMATION(1000, "harrier system information", "海鹞系统信息"),
	SYSTEM_NODE_DISCONNECTED(1001, "the system node [%s] is disconnected", "系统节点[%s]失联"),

	NOT_TIME_JOB(2000, "[%s] not a time job", "[%s]不是定时作业"),
	NOT_STATUS_SUCCESS(2001, "external,the current job [%s] status is not success", "外部触发，当前作业[%s]状态不是SUCCESS"),
	JOB_HAS_BEEN_RUN(2002, "the current job configuration [%s] has been run", "当前作业配置[%s]已经运行过了"),
	JOB_NOT_NEXT_RUN(2003, "the current job configuration [%s] is not the next run configuration","当前作业配置[%s]不是下一次运行配置"),
	JOB_BATCH_ERROR(2004, "the current job[%s] batch is error", "作业[%s]批次错误"),
	JOB_FREQUENTNES_CONFIG_ERROR(2005, "the current job [%s] frequentnes configuration is error", "当前作业[%s]频度配置错误"),
	JOB_FREQUENTNES_ERROR(2005, "the current job [%s] frequentnes is error", "当前作业[%s]频度错误"),
	SIGNAL_JOB_NOT_EXIST(2006, "the signal [%s] mapping job does not exist", "信号[%s]映射作业不存在"), 
	JOB_CONFIG_NOT_EXIST(2007, "job [%s] information does not exist in the job configuration table", "作业[%s]信息不存在作业配置表中"),
	JOB_SYSTEM_NOT_EXIST(2008,"job [%s] platform information does not exist in the system table", "作业平台[%s]信息不存在系统表中" ),
	JOB_STATUS_ERROR(2009,"job [%s] status is error,please check the log","作业[%s]状态不正确,请查看日志"),
	JOB_NOT_EXIST(2010, "job [%s] information does not exist in the job table", "作业[%s]信息不存在作业表中"),
	
	FORGOT_PWD_EMAIL_TITLE(3000, "forgot password", "忘记密码"),

	FORGOT_PWD_EMAIL_CONTENT(3001, "[harrier system information] you are changing your password,the verification code is: %s", "【海鹞系统信息】您正在修改密码，验证码为：%s"),
	REGISTER_USER_EMAIL_TITLE(3002, "forgot password", "注册用户"),
	REGISTER_USER_EMAIL_CONTENT(3003, "[harrier system information] you are changing your registering,the verification code is: %s", "【海鹞系统信息】您正在注册，验证码为：%s"),

	;

	private final int code;
	private final String enMsg;
	private final String zhMsg;

	Message(int code, String enMsg, String zhMsg) {
		this.code = code;
		this.enMsg = enMsg;
		this.zhMsg = zhMsg;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return getMsg(Constants.LANGUAGE);
	}

	public String getMsg(String language) {
		if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(language)) {
			return this.zhMsg;
		} else {
			return this.enMsg;
		}
	}
}
