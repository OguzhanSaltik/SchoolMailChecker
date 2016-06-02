package mail;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

@ManagedBean(name = "groups", eager = true)
@SessionScoped
public class CheckingGroups {
	private String host = "pop.gmail.com";// change accordingly
	// private static String mailStoreType = "pop3";
	private String username = "mail@gmail.com";// change accordingly
	private String password = "password";// change accordingly

	public ArrayList<Student> students = new ArrayList<>();

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void check() {
		try {
			checkMails();
			System.out.println("Students printing: " + students.toString());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("http://localhost:8080/jboss-javaee-webapp/faces/showGroups.xhtml");

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

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				String mailContent = message.getContent().toString().substring(0,
						message.getContent().toString().length() - 2).toString().replace("\\n", "\n");
				System.out.println("!!!!!!!!!!Mail Content: " + mailContent);
//				String s = "Merhaba Hocam,\n SYT dersinin 2.proje grup uyeleri asagidaki gibidir:\n  5110000000-Samet Ozguney\n 5110000001-Maulana Sapta\n 5110000002-Oguzhan Saltik\n";

				Pattern p = Pattern.compile("((\\d+)(-)(.*)((\r\n)|(\n)))", Pattern.UNICODE_CHARACTER_CLASS);

				Matcher m = p.matcher(mailContent.toString());

				//System.out.println("M.Find: " + m.find());
				while (m.find()) {
//					System.out.println("Found1: " + m.group(1));
//					System.out.println("Found2: " + m.group(2));
//					System.out.println("Found3: " + m.group(3));
//					System.out.println("Found4: " + m.group(4));
//					
//					System.out.println("Found1: " + m.group(1));
//					System.out.println("Found2: " + m.group(2));
//					System.out.println("Found3: " + m.group(3));
//					System.out.println("Found4: " + m.group(4));
//					
					 Student s = new Student(m.group(2),m.group(4), i);
					System.out.println("Found All: " + m.group());
					 students.add(s);
					System.out.println("-----------------------Student Added-------------------------");

				}

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