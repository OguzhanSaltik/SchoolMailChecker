package mail;

import java.io.IOException;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
@RequestScoped
public class MailSender {
	private String content;
	private String receiver;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void send() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mail@gmail.com", "password");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mail@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject("Testing Subject");
			message.setText(content);

			Transport.send(message);

			System.out.println("Done");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("http://localhost:8080/jboss-javaee-webapp/faces/check.xhtml");

		} catch (MessagingException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}