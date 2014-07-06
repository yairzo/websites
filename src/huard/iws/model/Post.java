package huard.iws.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post implements ISubjectRelated{
	private int id;
	private int creatorId;
	private int senderId;
	private List<Integer> subjectsIds;
	private String additionalAddresses;
	private String messageSubject;
	private String message;
	private String messageNew;
	private Timestamp creationTime;
	private boolean verified;
	private boolean sent;
	private boolean sendImmediately;
	private Timestamp sendTime;
	private boolean selfSend;
	private boolean selfSent;
	private String localeId;
	private int typeId;
	private int countNotSent;

	private List<Attachment> attachments;

	public Post (){
		this.id=0;
		this.creatorId = 0;
		this.senderId = 0;
		this.subjectsIds= new ArrayList<Integer>();
		this.additionalAddresses="";
		this.messageSubject="";
		this.message="";
		this.messageNew="";
		this.verified=false;
		this.sent=false;
		this.sendImmediately = false;
		this.sendTime = new Timestamp(System.currentTimeMillis());
		this.selfSend = false;
		this.selfSent = false;
		this.localeId = "iw_IL";
		this.typeId = 1;
		this.countNotSent=0;
	}

	public String getAdditionalAddresses() {
		return additionalAddresses;
	}
	public void setAdditionalAddresses(String additionalAddresses) {
		this.additionalAddresses = additionalAddresses;
	}
	public Timestamp getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessageNew() {
		return messageNew;
	}
	public void setMessageNew(String messageNew) {
		this.messageNew = messageNew;
	}

	public int getCreatorId() {
		return creatorId;
	}



	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}



	public int getSenderId() {
		return senderId;
	}



	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}



	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getMessageSubject() {
		return messageSubject;
	}
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}


	public List<Integer> getSubjectsIds() {
		return subjectsIds;
	}
	public void setSubjectsIds(List<Integer> subjectsIds) {
		this.subjectsIds = subjectsIds;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isSelfSent() {
		return selfSent;
	}

	public void setSelfSent(boolean selfSent) {
		this.selfSent = selfSent;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public boolean isSelfSend() {
		return selfSend;
	}
	public void setSelfSend(boolean selfSend) {
		this.selfSend = selfSend;
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public boolean isSendImmediately() {
		return sendImmediately;
	}

	public void setSendImmediately(boolean sendImmediately) {
		this.sendImmediately = sendImmediately;
	}

	public int getCountNotSent() {
		return countNotSent;
	}

	public void setCountNotSent(int countNotSent) {
		this.countNotSent = countNotSent;
	}
}
