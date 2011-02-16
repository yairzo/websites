package huard.iws.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MailMessage {
	private int id;
	private int listId;
	private String messageSubject;
	private String message;
	private List<Integer> recipientsPersonsIds;
	private int senderPersonId;
	private String additionalAddresses;
	private String cc;
	private String bcc;
	private Timestamp sendTime;

	private List<Attachment> attachments;

	public MailMessage(){
		this.listId = 0;
		this.senderPersonId = 0;
		this.messageSubject="";
		this.message="לחץ כאן להוספת תוכן להודעה";
		this.recipientsPersonsIds = new ArrayList<Integer>();
		this.additionalAddresses = "";
		this.cc = "";
		this.bcc = "";
		this.sendTime = new Timestamp(System.currentTimeMillis());
	}


	public int getSenderPersonId() {
		return senderPersonId;
	}
	public void setSenderPersonId(int senderPersonId) {
		this.senderPersonId = senderPersonId;
	}

	public List<Integer> getRecipientsPersonsIds() {
		return recipientsPersonsIds;
	}
	public void setRecipientsPersonsIds(List<Integer> recipientsPersonsIds) {
		this.recipientsPersonsIds = recipientsPersonsIds;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getMessageSubject() {
		return messageSubject;
	}
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getAdditionalAddresses() {
		return additionalAddresses;
	}
	public void setAdditionalAddresses(String additionalAddresses) {
		this.additionalAddresses = additionalAddresses;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public List<Attachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


}
