package huard.iws.bean;

import huard.iws.model.Attachment;
import huard.iws.model.MailMessage;
import huard.iws.model.Person;
import huard.iws.service.ListService;
import huard.iws.service.PersonListService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MailMessageBean {
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

	private boolean sendMeOnly;
	private boolean ccMe = true;
	private List<Person> recipientPersons;
	private Person senderPerson;
	private AListBean listBean;

	//private Attachment addedAttachment;

	//private static final Logger logger = Logger.getLogger(MailMessageBean.class);

	public AListBean getListBean(){
		return getListBean(null);
	}


	public AListBean getListBean(RequestWrapper request){
		if (listBean == null) {
			ListService listService = (ListService) ApplicationContextProvider.getContext().getBean("listService");
			listBean = new AListBean(listService.getList(this.getListId()), request);
		}
		return listBean;
	}

	public List<Person>getRecipientPersons(){
		if (recipientPersons == null) {
			PersonListService personListService = (PersonListService) ApplicationContextProvider.getContext().getBean("personListService");
			recipientPersons = personListService.getPersons(recipientsPersonsIds);
		}
		return recipientPersons;
	}

	public Person getSenderPerson(){
		if (senderPerson == null){
			PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
			senderPerson = personService.getPerson(senderPersonId);
		}
		return senderPerson;
	}


	public List<Integer> getRecipientsPersonsIds() {
		return recipientsPersonsIds;
	}

	public void setRecipientsPersonsIds(List<Integer> recipientsPersonsIds) {
		this.recipientsPersonsIds = recipientsPersonsIds;
	}

	public MailMessageBean(){
		this.listId = 0;
		this.senderPersonId = 0;
		this.messageSubject="";
		this.message="";
		this.recipientsPersonsIds = new ArrayList<Integer>();
		this.additionalAddresses = "";
		this.cc = "";
		this.bcc = "";
		this.sendTime = new Timestamp(System.currentTimeMillis());
		this.attachments = new ArrayList<Attachment>();
	}


	public MailMessageBean(MailMessage mailMessage){
		this.id = mailMessage.getId();
		this.listId = mailMessage.getListId();
		this.messageSubject = mailMessage.getMessageSubject();
		this.message = mailMessage.getMessage();
		this.recipientsPersonsIds = mailMessage.getRecipientsPersonsIds();
		this.senderPersonId = mailMessage.getSenderPersonId();
		this.additionalAddresses = mailMessage.getAdditionalAddresses();
		this.cc = mailMessage.getCc();
		this.bcc = mailMessage.getBcc();
		this.attachments = mailMessage.getAttachments();
	}

	public MailMessage toMailMessage(){
		MailMessage mailMessage = new MailMessage();
		mailMessage.setId(id);
		mailMessage.setListId(listId);
		mailMessage.setMessageSubject(messageSubject);
		mailMessage.setMessage(message);
		mailMessage.setRecipientsPersonsIds(recipientsPersonsIds);
		mailMessage.setSenderPersonId(senderPersonId);
		mailMessage.setAdditionalAddresses(additionalAddresses);
		mailMessage.setCc(cc);
		mailMessage.setBcc(bcc);
		mailMessage.setSendTime(sendTime);
		mailMessage.setAttachments(attachments);
		return mailMessage;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}



	public int getSenderPersonId() {
		return senderPersonId;
	}


	public void setSenderPersonId(int senderPersonId) {
		this.senderPersonId = senderPersonId;
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

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public boolean isCcMe() {
		return ccMe;
	}

	public void setCcMe(boolean ccMe) {
		this.ccMe = ccMe;
	}


	public boolean isSendMeOnly() {
		return sendMeOnly;
	}


	public void setSendMeOnly(boolean sendMeOnly) {
		this.sendMeOnly = sendMeOnly;
	}


	public List<Attachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	/*public Attachment getAddedAttachment() {
		return addedAttachment;
	}


	public void setAddedAttachment(Attachment addedAttachment) {
		this.addedAttachment = addedAttachment;
	}*/


}
