package huard.iws.bean;

import huard.iws.model.Attachment;
import huard.iws.model.Language;
import huard.iws.model.Post;
import huard.iws.model.Subject;
import huard.iws.service.MessageService;
import huard.iws.service.PersonService;
import huard.iws.service.SubjectService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.MD5Encoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class PostBean {

	private static final Logger logger = Logger.getLogger(PostBean.class);

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
	private List<Attachment> attachments;

	private PersonBean creator;
	private PersonBean sender;
	private int recipientId;
	private PersonBean recipient;

	private String date;
	private String hour;

	private SubjectService subjectService;
	private PersonService personService;
	private MessageService messageService;




	private void  init(){
		personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
		creator = new PersonBean ( personService.getPerson(creatorId));
		creator.setLocaleId(this.localeId);
		if (senderId > 0){
			sender = new PersonBean ( personService.getPerson(senderId));
			sender.setLocaleId(this.localeId);
		}
		subjectService = (SubjectService) ApplicationContextProvider.getContext().getBean("subjectService");
		messageService = (MessageService) ApplicationContextProvider.getContext().getBean("messageService");
	}


	public PostBean(){
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
		this.attachments = new ArrayList<Attachment>();
	}

	public boolean isHasAttachment(){
		return this.message.contains("#@#");
	}

	public boolean isSelfSendPhase(){
		return this.selfSend && this.selfSent && !this.isVerified();
	}

	public List<Subject> getRecipientPostCommonSubjects(){
		return subjectService.getCommonSubjects(this.toPost(), getRecipient().toPerson(), this.getLocaleId());
	}

	public String getRecipientPostCommonSubjectsString(){
		StringBuilder sb = new StringBuilder();
		for (Subject subject: getRecipientPostCommonSubjects()){
			SubjectBean subjectBean = new SubjectBean(subject, this.localeId);
			sb.append(subjectBean.getName()+", ");
		}
		if (sb.length() > 120){
			String s = sb.substring(0, 120);
			s = s.substring(0, s.lastIndexOf(","));
			s += " " + messageService.getMessage("post.moreSubjects", this.localeId);
			sb = new StringBuilder(s);
		}
		else if (sb.length() > 2)
			sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}

	public String getFormattedSendTime(){
		return DateUtils.formatDateTime(sendTime);
	}
	public String getFormattedSendDate(){
		return DateUtils.formatDate(sendTime);
	}

	public Language getLanguage(){
		return LanguageUtils.getLanguage(localeId);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public PostBean(Post post){
		this.id = post.getId();
		this.creatorId = post.getCreatorId();
		this.senderId = post.getSenderId();
		this.subjectsIds = post.getSubjectsIds();
		this.additionalAddresses = post.getAdditionalAddresses();
		this.messageSubject = post.getMessageSubject();
		this.message = post.getMessage();
		this.messageNew = post.getMessageNew();
		this.creationTime = post.getCreationTime();
		this.verified = post.isVerified();
		this.sent = post.isSent();
		this.sendImmediately = post.isSendImmediately();
		this.sendTime = post.getSendTime();
		this.selfSend = post.isSelfSend();
		this.selfSent = post.isSelfSent();
		this.localeId = post.getLocaleId();
		this.typeId = post.getTypeId();
		this.attachments = post.getAttachments();
		init();
		}

	public Post toPost(){
		Post post = new Post();
		post.setId(id);
		post.setCreatorId(creatorId);
		post.setSenderId(senderId);
		post.setSubjectsIds(subjectsIds);
		post.setAdditionalAddresses(additionalAddresses);
		post.setMessageSubject(messageSubject);
		post.setMessage(message);
		post.setMessageNew(messageNew);
		post.setCreationTime(creationTime);
		post.setVerified(verified);
		post.setSent(sent);
		post.setSendImmediately(sendImmediately);
		post.setSendTime(sendTime);
		post.setSelfSend(selfSend);
		post.setSelfSent(selfSent);
		post.setLocaleId(localeId);
		post.setTypeId(typeId);
		post.setAttachments(attachments);
		return post;
	}


	public String getFormattedMessage(){
		String formattedMessage = this.message;
		formattedMessage = formattedMessage.replaceAll(";;", "<img src=\"/image/bullet.gif\">");
		formattedMessage = formattedMessage.replaceAll("#@#", "<img src=\"/image/attach.jpg\">");
		formattedMessage = formattedMessage.replaceAll("#mu#", "<a class=\"underline\" href=\"mailto:" + sender.getEmail() + "\">");
		formattedMessage = formattedMessage.replaceAll("#mp#",sender.getFullName());
		formattedMessage = formattedMessage.replaceAll("#mue#","</a>");
		return formattedMessage;
	}

	public String getMd5(){
		return MD5Encoder.digest(this.id + this.messageSubject + this.creationTime.toString());
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
	
	public String getMessageNewFormatted() {
		String newMessage=this.messageNew;
		newMessage=newMessage.replaceAll("<p(.*?)>", "");
		newMessage=newMessage.replaceAll("</p>", "");
		newMessage=newMessage.replaceAll("src=\"image/post/dot.gif\"", "src=\"cid:dot\"");
		return newMessage;
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

	public String getSubjectsIdsString(){
		String subjectsIds="";
		for (Integer subjectId :getSubjectsIds()){
			subjectsIds += subjectId +", ";
		}
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
	public boolean isSelfSend() {
		return selfSend;
	}
	public void setSelfSend(boolean selfSend) {
		this.selfSend = selfSend;
	}
	public boolean isSelfSent() {
		return selfSent;
	}
	public void setSelfSent(boolean selfSent) {
		this.selfSent = selfSent;
	}
	public PersonBean getCreator() {
		return creator;
	}
	public void setCreator(PersonBean creator) {
		this.creator = creator;
	}
	public PersonBean getSender() {
		return sender;
	}
	public void setSender(PersonBean sender) {
		this.sender = sender;
	}
	public PersonBean getRecipient() {
		if (recipient == null && recipientId > 0){
			recipient = new PersonBean ( personService.getPerson(recipientId));
		}
		return recipient;
	}
	public void setRecipient(PersonBean recipient) {
		this.recipient = recipient;
	}
	public int getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public Map <Integer, Attachment> getAttachmentsMap(){
		Map<Integer, Attachment> attachmentsMap = new HashMap<Integer, Attachment>();
		for (Attachment attachment: this.attachments){
			logger.info("Attachment id: " + attachment.getId());
			attachmentsMap.put(attachment.getId(), attachment);
		}
		return attachmentsMap;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
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

}
