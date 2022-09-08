package cn.spdb.harrier.service.email;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.mail.smtp.SMTPProvider;

import cn.spdb.harrier.common.constants.alarm.EmailParamsConstants;
import cn.spdb.harrier.service.email.config.EmailConfiguration;

@Component
public class EmailOperator {

	@Autowired
	private EmailConfiguration config;

	private Session getSession() {
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);

		Properties props = new Properties();
		props.setProperty(EmailParamsConstants.MAIL_SMTP_HOST, config.getMailSmtpHost());
		props.setProperty(EmailParamsConstants.MAIL_SMTP_PORT, config.getMailSmtpPort());
		props.setProperty(EmailParamsConstants.MAIL_SMTP_AUTH, config.getEnableSmtpAuth());
		props.setProperty(EmailParamsConstants.MAIL_PROTOCOL, config.getMailProtocol());
		props.setProperty(EmailParamsConstants.MAIL_SMTP_STARTTLS_ENABLE, config.getMailUseStartTLS());
		props.setProperty(EmailParamsConstants.MAIL_SMTP_SSL_ENABLE, config.getMailUseSSL());
		props.setProperty(EmailParamsConstants.MAIL_SMTP_SSL_TRUST, config.getSslTrust());

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(config.getMailUser(), config.getMailPasswd());
			}
		};

		Session session = Session.getInstance(props, auth);
		session.addProvider(new SMTPProvider());
		session.setDebug(false);
		return session;
	}

	public Boolean sendEmailMine(List<String> receivers, List<String> receiverCcs, String title, String content,
			List<String> attachment) {
		try {
			Session session = getSession();
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(config.getMailSender()));

			if (CollectionUtils.isNotEmpty(receivers)) {
				for (String receiver : receivers) {
					msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
				}
			}

			if (CollectionUtils.isNotEmpty(receiverCcs)) {
				for (String receiverCc : receiverCcs) {
					msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(receiverCc));
				}
			}
			// set subject
			msg.setSubject(title);

			MimeMultipart partList = new MimeMultipart();

			MimeBodyPart part1 = new MimeBodyPart();
			part1.setContent(content, "text/html;charset=utf-8");
			partList.addBodyPart(part1);

			if (CollectionUtils.isNotEmpty(attachment)) {
				for (String path : attachment) {
					File file = new File(path);
					if (file.exists() && file.isFile()) {
						continue;
					}
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.attachFile(file);
					attachmentPart.setFileName(MimeUtility.encodeText(file.getName()));
					partList.addBodyPart(attachmentPart);
				}
			}

			msg.setContent(partList);
			Transport.send(msg);
			return true;
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
