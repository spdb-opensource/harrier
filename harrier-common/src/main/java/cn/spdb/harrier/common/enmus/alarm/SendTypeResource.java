package cn.spdb.harrier.common.enmus.alarm;

import static cn.spdb.harrier.common.constants.alarm.EmailParamsConstants.*;
import static cn.spdb.harrier.common.constants.alarm.HttpAlertConstants.*;

public enum SendTypeResource {

	EMAIL("邮件", new String[][] { 
		{"收件人",NAME_EMAIL_RECEIVERS}, 
		{"抄送人",NAME_EMAIL_RECEIVERCCS},
		{"发送人",NAME_MAIL_SENDER},
		{"用户",NAME_MAIL_USER},
		{"密码",NAME_MAIL_PASSWD},
		{"地址",NAME_MAIL_SMTP_HOST},
		{"端口",NAME_MAIL_SMTP_PORT},
		}),
	HTTP("http", new String[][] { 
		{"URL",URL},
		{"请求头",HEADER_PARAMS},
		{"请求体",BODY_PARAMS},
		{"协议类型",REQUEST_TYPE},
		{"告警键",CONTENT_FIELD},
		}),
	WECHAT("微信", new String[][] { 
		{,}, 
		{,},
		}, false);

	private String name;
	private String[][] resource;
	private Boolean open = true;

	private SendTypeResource(String name, String[][] resource) {
		this.name = name;
		this.resource = resource;
	}

	private SendTypeResource(String name, String[][] resource, Boolean open) {
		this.name = name;
		this.resource = resource;
		this.open = open;
	}

	public String[][] getResource() {
		return resource;
	}

	public void setResource(String[][] resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

}
