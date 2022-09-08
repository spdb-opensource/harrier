package cn.spdb.harrier.service.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

	@Value("${mail.smtp.host:}")
	private String mailSmtpHost;
	
	@Value("${mail.smtp.port:25}")
	private String mailSmtpPort;
	
	@Value("${mail.smtp.auth:true}")
	private String enableSmtpAuth;
	
	@Value("${mail.transport.protocol:smtp}")
	private String mailProtocol;
	
	@Value("${mail.smtp.starttls.enable:true}")
	private String mailUseStartTLS;
	
	@Value("${mail.smtp.ssl.enable:false}")
	private String mailUseSSL;
	
	@Value("${mail.smtp.ssl.trust:*}")
	private String sslTrust;
	
	@Value("${mail.user:}")
	private String mailUser;
	
	@Value("${mail.passwd:}")
	private String mailPasswd;
	
	@Value("${mail.sender:}")
	private String mailSender;

	@Override
	public String toString() {
		return "EmailConfiguration [mailSmtpHost=" + mailSmtpHost + ", mailSmtpPort=" + mailSmtpPort
				+ ", enableSmtpAuth=" + enableSmtpAuth + ", mailProtocol=" + mailProtocol + ", mailUseStartTLS="
				+ mailUseStartTLS + ", mailUseSSL=" + mailUseSSL + ", sslTrust=" + sslTrust + ", mailUser=" + mailUser
				+ ", mailPasswd=" + mailPasswd + "]";
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getEnableSmtpAuth() {
		return enableSmtpAuth;
	}

	public void setEnableSmtpAuth(String enableSmtpAuth) {
		this.enableSmtpAuth = enableSmtpAuth;
	}

	public String getMailProtocol() {
		return mailProtocol;
	}

	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}

	public String getMailUseStartTLS() {
		return mailUseStartTLS;
	}

	public void setMailUseStartTLS(String mailUseStartTLS) {
		this.mailUseStartTLS = mailUseStartTLS;
	}

	public String getMailUseSSL() {
		return mailUseSSL;
	}

	public void setMailUseSSL(String mailUseSSL) {
		this.mailUseSSL = mailUseSSL;
	}

	public String getSslTrust() {
		return sslTrust;
	}

	public void setSslTrust(String sslTrust) {
		this.sslTrust = sslTrust;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPasswd() {
		return mailPasswd;
	}

	public void setMailPasswd(String mailPasswd) {
		this.mailPasswd = mailPasswd;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

}
