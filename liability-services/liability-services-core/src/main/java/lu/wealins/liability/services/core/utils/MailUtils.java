package lu.wealins.liability.services.core.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;

public class MailUtils {
	private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	@SuppressWarnings("unused")
	public static void SendEmail(String text, String email) {

		try {

			MimeMessage message = new MimeMessage(MailUtils.session());
			message.setFrom("info@wealins.com");
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			message.setText("");

			// Now set the actual message
			message.setText(text);

			// Send message

			Transport.send(message);
		} catch (MessagingException me) {
			logger.info(me.getMessage());
		}
	}

	public static Session session() {
		JndiTemplate template = new JndiTemplate();
		Session session = null;
		try {
			session = (Session) template.lookup("java:jboss/mail/Default");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return session;
	}
}
