package model;

public class Mail {
	private String subject;
	private String from;
	private String text;
	private long sentDate;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Mail [subject=" + subject + ", from=" + from + ", text=" + text + "]";
	}

	public long getSentDate() {
		return sentDate;
	}

	public void setSentDate(long sentDate) {
		this.sentDate = sentDate;
	}
}
