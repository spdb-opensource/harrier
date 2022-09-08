
package cn.spdb.harrier.alarm.send.email;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.smtp.SMTPProvider;

import cn.spdb.harrier.alarm.send.email.exception.AlertEmailException;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.constants.alarm.EmailParamsConstants;
import cn.spdb.harrier.common.enmus.alarm.SendStatus;
import cn.spdb.harrier.common.enmus.alarm.ShowType;
import cn.spdb.harrier.common.utils.JSONUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.Symbol;

/**
 * mail utils
 */
public class EmailSender {

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

	private List<String> receivers;
	private List<String> receiverCcs;
	private String mailProtocol;
	private String mailSmtpHost;
	private String mailSmtpPort;
	private String mailSenderEmail;
	private String enableSmtpAuth;
	private String mailUser;
	private String mailPasswd;
	private String mailUseStartTLS;
	private String mailUseSSL;
	private String sslTrust;
	private List<String> attachmentPaths;
	private ShowType showType;
	private String mustNotNull = " must not be null";

	public EmailSender(Map<String, String> config) {

		String receiversConfig = config.get(EmailParamsConstants.NAME_EMAIL_RECEIVERS);
		if (ObjectUtils.isEmpty(receiversConfig)) {
			throw new AlertEmailException(EmailParamsConstants.NAME_EMAIL_RECEIVERS + mustNotNull);
		}
		receivers = Arrays.asList(receiversConfig.split(","));

		mailSenderEmail = config.get(EmailParamsConstants.NAME_MAIL_SENDER);
		requireNonNull(mailSenderEmail, EmailParamsConstants.MAIL_SENDER + mustNotNull);

		mailUser = config.get(EmailParamsConstants.NAME_MAIL_USER);
		requireNonNull(mailUser, EmailParamsConstants.MAIL_USER + mustNotNull);

		mailPasswd = config.get(EmailParamsConstants.NAME_MAIL_PASSWD);
		requireNonNull(mailPasswd, EmailParamsConstants.MAIL_PASSWD + mustNotNull);

		mailSmtpHost = config.get(EmailParamsConstants.NAME_MAIL_SMTP_HOST);
		requireNonNull(mailSmtpHost, EmailParamsConstants.MAIL_SMTP_HOST + mustNotNull);

		String type = config.get(EmailParamsConstants.SHOW_TYPE);
		if (ObjectUtils.isEmpty(type)) {
			showType = ShowType.TEXT;
		} else {
			type = type.toUpperCase();
			showType = ShowType.valueOf(type);
			if (ObjectUtils.isEmpty(showType)) {
				showType = ShowType.TEXT;
			}
		}

		String receiverCcsConfig = config.get(EmailParamsConstants.NAME_EMAIL_RECEIVERCCS);
		receiverCcs = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(receiverCcsConfig)) {
			receiverCcs = Arrays.asList(receiverCcsConfig.split(","));
		}

		mailSmtpPort = config.get(EmailParamsConstants.NAME_MAIL_SMTP_PORT);
		if (ObjectUtils.isEmpty(mailSmtpPort)) {
			mailSmtpPort = EmailConstants.DEFAULT_SMTP_PORT;
		}

		mailProtocol = config.get(EmailParamsConstants.NAME_MAIL_PROTOCOL);
		if (ObjectUtils.isEmpty(mailProtocol)) {
			mailProtocol = EmailConstants.DEFAULT_PROTOCOL;
		}

		enableSmtpAuth = config.get(EmailParamsConstants.NAME_MAIL_SMTP_AUTH);
		if (ObjectUtils.isEmpty(enableSmtpAuth)) {
			enableSmtpAuth = Constants.STRING_TRUE;
		}

		mailUseStartTLS = config.get(EmailParamsConstants.NAME_MAIL_SMTP_STARTTLS_ENABLE);
		if (ObjectUtils.isEmpty(mailUseStartTLS)) {
			mailUseStartTLS = Constants.STRING_TRUE;
		}

		mailUseSSL = config.get(EmailParamsConstants.NAME_MAIL_SMTP_SSL_ENABLE);
		if (ObjectUtils.isEmpty(mailUseSSL)) {
			mailUseSSL = Constants.STRING_FALSE;
		}

		sslTrust = config.get(EmailParamsConstants.NAME_MAIL_SMTP_SSL_TRUST);
		if (ObjectUtils.isEmpty(sslTrust)) {
			sslTrust = Symbol.XING_HAO;
		}

		String filesConfig = config.get(EmailConstants.ATTACHMENT_FILE_PATH);
		if (ObjectUtils.isNotEmpty(filesConfig)) {
			attachmentPaths = Arrays.asList(filesConfig.split(","));
		}
	}

	public AlarmResult sendMails(String title, String content) {
		return sendMails(this.receivers, this.receiverCcs, title, content, this.attachmentPaths);
	}

	public AlarmResult sendMailsToReceiverOnly(String title, String content) {
		return sendMails(this.receivers, new ArrayList<String>(), title, content, new ArrayList<String>());
	}

	public AlarmResult sendMailsNotAttachment(String title, String content) {
		return sendMails(this.receivers, this.receiverCcs, title, content, new ArrayList<String>());
	}

	public AlarmResult sendMailsToReceiverOnlyNotAttachment(String title, String content) {
		return sendMails(this.receivers, new ArrayList<String>(), title, content, new ArrayList<String>());
	}

	public AlarmResult sendMails(List<String> receivers, List<String> receiverCcs, String title, String content,
			List<String> attachment) {
		AlarmResult alermResult = new AlarmResult();
		alermResult.setStatus(SendStatus.FAIL.name());

		// if there is no receivers && no receiversCc, no need to process
		if (CollectionUtils.isEmpty(receivers) && CollectionUtils.isEmpty(receiverCcs)) {
			return alermResult;
		}
		receivers.removeIf(StringUtils::isEmpty);
		receiverCcs.removeIf(StringUtils::isEmpty);
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		if (ShowType.TABLE.equals(showType)) {
			List<LinkedHashMap> itemsList = JSONUtils.toList(content, LinkedHashMap.class);
			content = getTableTypeMessage(itemsList);
		}
		try {
			sendEmailMine(receivers, receiverCcs, title, content, attachment);
			alermResult.setStatus(SendStatus.SUCC.name());
		} catch (Exception e) {
			handleException(alermResult, e);
			e.printStackTrace();
		}
		return alermResult;
	}

	/**
	 * get session
	 *
	 * @return the new Session
	 */
	private Session getSession() {
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);

		Properties props = new Properties();
		props.setProperty(EmailParamsConstants.MAIL_SMTP_HOST, mailSmtpHost);
		props.setProperty(EmailParamsConstants.MAIL_SMTP_PORT, mailSmtpPort);
		props.setProperty(EmailParamsConstants.MAIL_SMTP_AUTH, enableSmtpAuth);
		props.setProperty(EmailParamsConstants.MAIL_PROTOCOL, mailProtocol);
		props.setProperty(EmailParamsConstants.MAIL_SMTP_STARTTLS_ENABLE, mailUseStartTLS);
		props.setProperty(EmailParamsConstants.MAIL_SMTP_SSL_ENABLE, mailUseSSL);
		props.setProperty(EmailParamsConstants.MAIL_SMTP_SSL_TRUST, sslTrust);

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, mailPasswd);
			}
		};

		Session session = Session.getInstance(props, auth);
		session.addProvider(new SMTPProvider());
		session.setDebug(false);
		return session;
	}

	/**
	 * attach content
	 */
	public Boolean sendEmailMine(List<String> receivers, List<String> receiverCcs, String title, String content,
			List<String> attachment) {
		try {
			Session session = getSession();
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(mailSenderEmail));

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
			part1.setContent(content, EmailConstants.TEXT_HTML_CHARSET_UTF_8);
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

	/**
	 * handle exception
	 */
	private void handleException(AlarmResult alermResult, Exception e) {
		logger.error("Send email to {} failed", receivers, e);
		alermResult.setMessage("Send email to {" + String.join(",", receivers) + "} failed，" + e.toString());
	}

	private String getTableTypeMessage(final List<LinkedHashMap> itemsList) {
		if (ObjectUtils.isEmpty(itemsList)) {
			return "";
		}
		List<LinkedHashMap> mapItemsList = new ArrayList<LinkedHashMap>();
		if (mapItemsList.size() > EmailConstants.NUMBER_1000) {
			mapItemsList = itemsList.subList(0, EmailConstants.NUMBER_1000);
		} else {
			mapItemsList = itemsList;
		}
		StringBuilder contents = new StringBuilder(200);
		boolean flag = true;
		String title = "";
		for (LinkedHashMap<String, Object> mapItems : mapItemsList) {
			Set<Map.Entry<String, Object>> entries = mapItems.entrySet();
			Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
			StringBuilder t = new StringBuilder(EmailConstants.TR);
			StringBuilder cs = new StringBuilder(EmailConstants.TR);
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				t.append(EmailConstants.TH).append(entry.getKey()).append(EmailConstants.TH_END);
				cs.append(EmailConstants.TD).append(String.valueOf(entry.getValue())).append(EmailConstants.TD_END);

			}
			t.append(EmailConstants.TR_END);
			cs.append(EmailConstants.TR_END);
			if (flag) {
				title = t.toString();
			}
			flag = false;
			contents.append(cs);
		}
		String htmlTableThead = StringUtils.isEmpty(title) ? "" : String.format("<thead>%s</thead>%n", title);

		return EmailConstants.HTML_HEADER_PREFIX + htmlTableThead + contents + EmailConstants.TABLE_BODY_HTML_TAIL;

	}

}
