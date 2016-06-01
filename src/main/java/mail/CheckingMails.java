package mail;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import com.google.gson.Gson;

import model.Mail;

//import model.Mail;

@ManagedBean(name = "checking", eager = true)
@SessionScoped
public class CheckingMails {
	private String host = "pop.gmail.com";// change accordingly
	// private static String mailStoreType = "pop3";
	private String username = "bilmuh.syt@gmail.com";// change accordingly
	private String password = "1234ZXCV";// change accordingly

	public ArrayList<Mail> mails = new ArrayList<>();

	public ArrayList<Mail> getMails() {
		return mails;
	}

	public void setMails(ArrayList<Mail> mails) {
		this.mails = mails;
	}

	private String totalMessage;

	public String getMessagesAsJson() {
		String json = new Gson().toJson(mails);
		System.out.println(json);
		return json;
	}

	public void check() {
		try {
			checkMails();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("http://localhost:8080/jboss-javaee-webapp/faces/showMails.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkMails() {
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");

			Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, username, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			totalMessage = String.valueOf(messages.length);
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];

				Mail mail = new Mail();

				mail.setSubject(message.getSubject());
				mail.setFrom(message.getFrom()[0].toString());
				String mailContent = message.getContent().toString().substring(0, message.getContent().toString().length()-2);
			
				mail.setText(mailContent);

				long time = message.getSentDate().getTime();
				mail.setSentDate(time);

				mails.add(mail);

				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

			System.out.println("-------------------------------Done Checking MailBox---------------------------------");

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}