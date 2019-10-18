package hjx.shop.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		//props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");// 鎸囧畾楠岃瘉涓簍rue

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("13076523257@163.com", "aqcxcx13435");
			}
		};

		Session session = Session.getInstance(props, auth);

		
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("13076523257@163.com")); 

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 璁剧疆鍙戦�佹柟寮忎笌鎺ユ敹鑰�

		message.setSubject("测试");
		// message.setText("杩欐槸涓�灏佹縺娲婚偖浠讹紝璇�<a href='#'>鐐瑰嚮</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.鍒涘缓 Transport鐢ㄤ簬灏嗛偖浠跺彂閫�

		Transport.send(message);
	}
	@Test
	public void test() throws AddressException, MessagingException {
		MailUtils.sendMail("13076523257@163.com", "ssssssssss");
	}
}
