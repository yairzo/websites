package huard.iws.service;

import huard.iws.bean.FundBean;
import huard.iws.bean.InstituteBean;
import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PartnerBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.bean.ProposalBean;
import huard.iws.bean.ConferenceProposalBean;
import huard.iws.constant.Constants;
import huard.iws.db.MailMessageDao;
import huard.iws.model.Attachment;
import huard.iws.model.Fund;
import huard.iws.model.MailMessage;
import huard.iws.model.Person;
import huard.iws.util.BaseUtils;
import huard.iws.util.FileSystemResourceWrapper;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailMessageServiceImpl implements MailMessageService{
	private final String EQF_MAIL_ADDRESS = "mop@ard.huji.ac.il";
	private final String PROPOSAL_MAIL_MESSAGE_KEY = "iw_IL.eqfSystem.editProposal.mailMessage.";
	private final String PARTNER_MAIL_MESSAGE_KEY = "iw_IL.eqfSystem.editProposal.mailMessage.";
	private final String CONFERENCE_PROPOSAL_MAIL_ADDRESS = "conferences_committee@ard.huji.ac.il";

	private String server;


	private String getServer(){
		if (server == null){
			server = configurationService.getConfigurationString("server");
		}
		return server;
	}

	private void buildBasicModel(Map<String, Object> model, PersonBean recipient, ProposalBean proposal){
		model.put("privateMessageOpening", messageService.getMessage("iw_IL.eqf.message.privateMessageOpening"));
		model.put("recipient", recipient.getDegreeFullNameHebrew());
		model.put("proposal", proposal);
		model.put("server", getServer());
	}

	//TODO: Create a template for an error mail

	/*public void createSimpleErrorMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			PartnerBean partner, String messageKey){
		String subject = messageService.getMessage(ERROR_MAIL_MESSAGE_KEY+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		buildBasicModel(model, recipient, proposal);
		model.put("errorMessageOpening", messageService.getMessage(ERROR_MAIL_MESSAGE_KEY+"partnerMessageOpening"));
		model.put("errorProposalMessageOpening", messageService.getMessage(ERROR_MAIL_MESSAGE_KEY+"referedProposalMessageOpening"));

		String [] messageParams = new String []{sender.getDegreeFullNameHebrew(),
				partner.getInstituteBean().getName(), additionalnstituteName};
		model.put("message", messageService.getMessage(PARTNER_MAIL_MESSAGE_KEY+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, subject, body);
	}*/



	public void createSubscriptionMail (PersonBean recipient, String md5){
		String subject = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.subscription.subject");

		String message = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.subscription.message",
				new String[] {recipient.getPreferedLocaleDegreeFullName(),
					configurationService.getConfigurationString("server"),	""+recipient.getId(), md5});
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("language", LanguageUtils.getLanguagesMap().get(recipient.getPreferedLocaleId()));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, subject, body, getCommonResources());
	}


	public void createSimpleProposalMail(PersonBean recipient, ProposalBean proposal,
			String messageKey){
		createSimpleProposalMail(recipient, new PersonBean(), proposal, messageKey);
	}

	public void createSimpleProposalMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			String messageKey){
		String subject = messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		buildBasicModel(model, recipient, proposal);
		model.put("proposalMessageOpening", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+"proposalMessageOpening"));
		model.put("proposalMessage", true);
		String [] messageParams = new String []{sender.getDegreeFullNameHebrew()};
		model.put("message", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, subject, body, getCommonResources());
	}
	
	public void createSimpleConferenceMail(PersonBean recipient, ConferenceProposalBean conferenceProposal,
			String messageKey){
		createSimpleConferenceMail(recipient, new PersonBean(), conferenceProposal, messageKey);
	}

	public void createSimpleConferenceMail(PersonBean recipient, PersonBean sender, ConferenceProposalBean conferenceProposal,
			String messageKey){
		String subject = messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("align", "right");
		model.put("recipient", recipient.getDegreeFullNameHebrew());
		//to
		String [] to = new String[1];
		to[0] = recipient.getEmail();
		//cc
		List<Person> ccPersons = new ArrayList<Person>();
		for (PersonBean personBean : personListService.getPersonsList(configurationService.getConfigurationInt("conferenceProposalAdminListId"))){
			ccPersons.add(personBean.toPerson());
		}
		String [] cc = BaseUtils.toEmailsArray(ccPersons);
		
		model.put("conferenceProposal", conferenceProposal);
		model.put("server", getServer());
		model.put("proposalMessageOpening", messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage.proposalMessageOpening"));
		model.put("conferenceMessage", true);
		String [] messageParams = new String []{sender.getDegreeFullNameHebrew(),Integer.toString(conferenceProposal.getId()),conferenceProposal.getSubject(),getServer()};
		model.put("message", messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		System.out.println(body);
		messageService.sendMail(to, CONFERENCE_PROPOSAL_MAIL_ADDRESS, cc, null,subject, body, getCommonResources());
	}

	public void createSimpleConferenceGradeMail(PersonBean recipient, String messageKey){
		createSimpleConferenceGradeMail(recipient, new PersonBean(), messageKey);
	}

	public void createSimpleConferenceGradeMail(PersonBean recipient, PersonBean sender, String messageKey){
		String subject = messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("privateMessageOpening", messageService.getMessage("iw_IL.eqf.message.privateMessageOpening"));
		model.put("align", "right");
		model.put("recipient", recipient.getDegreeFullNameHebrew());
		model.put("server", getServer());
		String [] messageParams = new String []{sender.getDegreeFullNameHebrew(),getServer()};
		model.put("message", messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		System.out.println(body);
		messageService.sendMail(recipient.getEmail(), CONFERENCE_PROPOSAL_MAIL_ADDRESS, subject, body, getCommonResources());
	}

	
	public void createDeanGradeFinishedGradingMail(PersonBean dean, String messageKey){
		//subject
		String subject = messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".subject");
		//to
		List<Person> recipientPersons = new ArrayList<Person>();
		for (PersonBean personBean : personListService.getPersonsList(configurationService.getConfigurationInt("conferenceProposalAdminListId"))){
			recipientPersons.add(personBean.toPerson());
		}
		String [] to = BaseUtils.toEmailsArray(recipientPersons);
		//body
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("align", "right");
		model.put("deanName", dean.getDegreeFullName());
		model.put("privateMessageOpening", messageService.getMessage("iw_IL.eqf.message.privateMessageOpening"));
		model.put("server", getServer());
		String [] messageParams = new String []{dean.getDegreeFullNameHebrew(),getServer()};
		model.put("message", messageService.getMessage("iw_IL.eqfSystem.editConferenceProposal.mailMessage."+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		System.out.println(body);
		messageService.sendMail(to, CONFERENCE_PROPOSAL_MAIL_ADDRESS, null,null,subject, body, getCommonResources());
	}

	public void createSimplePartnerMail(PersonBean recipient, ProposalBean proposal,
			PartnerBean partner, String messageKey){
				createSimplePartnerMail(recipient, new PersonBean(), proposal, partner, messageKey);
	}


	public void createSimplePartnerMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			PartnerBean partner, String messageKey){
		String subject = messageService.getMessage(PARTNER_MAIL_MESSAGE_KEY+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		buildBasicModel(model, recipient, proposal);
		model.put("partnerMessageOpening", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+"partnerMessageOpening"));
		model.put("partnerProposalMessageOpening", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+"referedProposalMessageOpening"));
		model.put("partner", partner);
		model.put("partnerMessage", true);
		InstituteBean additionalInstitute = partner.getAdditionalIntituteBean();
		String additionalnstituteName = additionalInstitute != null ? additionalInstitute.getName() : "";
		String [] messageParams = new String []{sender.getDegreeFullNameHebrew(),
				partner.getInstituteBean().getName(), additionalnstituteName};
		model.put("message", messageService.getMessage(PARTNER_MAIL_MESSAGE_KEY+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, subject, body, getCommonResources());
	}


	public void createSimpleFundMail(PersonBean recipient, ProposalBean proposal,
			FundBean fund, String messageKey){
				createSimpleFundMail(recipient, new PersonBean(), proposal, fund, messageKey);
	}


	public void createSimpleFundMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			FundBean fund, String messageKey){
		String subject = messageService.getMessage(PARTNER_MAIL_MESSAGE_KEY+messageKey+".subject");
		Map<String, Object> model = new HashMap<String, Object>();
		buildBasicModel(model, recipient, proposal);
		model.put("fundMessageOpening", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+"fundMessageOpening"));
		model.put("fundProposalMessageOpening", messageService.getMessage(PROPOSAL_MAIL_MESSAGE_KEY+"referedProposalMessageOpening"));
		model.put("fund", fund);
		model.put("fundMessage", true);
		String [] messageParams = new String []{sender.getDegreeFullNameHebrew()};
		model.put("message", messageService.getMessage(PARTNER_MAIL_MESSAGE_KEY+messageKey+".body", messageParams));
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, subject, body, getCommonResources());
	}


	public void informMopOnNewProposal(ProposalBean proposal){
		Fund fund = fundService.getFund(proposal.getFundId());
		List<PersonBean> mopDeskPersons =
			mopDeskService.getPersonsList(fund.getDeskId(),
					Constants.getMopTitleId("COORDINATOR_HELPER"));

		for (PersonBean mopDeskPerson: mopDeskPersons){
			createSimpleProposalMail(mopDeskPerson, proposal, "mopNewProposal");
		}
	}


	public void informAdminOnPartnerDuality (ProposalBean proposalBean,
			PartnerBean partnerBean){
		List<PersonBean> mopPersons = mopDeskService.getPersonsList(proposalBean.getFund().getDeskId(),
				Constants.getMopTitleId("COORDINATOR_HELPER"));
		for (PersonBean mopPerson: mopPersons){
			this.createSimplePartnerMail(mopPerson, proposalBean, partnerBean, "partnerDuality");
		}
	}

	public void informAdminOnPartnerAdd (ProposalBean proposalBean,
			PartnerBean partnerBean){
		List<PersonBean> mopPersons = mopDeskService.getPersonsList(proposalBean.getFund().getDeskId(),
				Constants.getMopTitleId("COORDINATOR_HELPER"));
		for (PersonBean mopPerson: mopPersons){
			this.createSimplePartnerMail(mopPerson, proposalBean, partnerBean, "partnerAdd");
		}
	}

	public void informAdminsOnUnknownFund(ProposalBean proposal, Fund fund){
		List<PersonBean> admins = personListService.getPersonsList(configurationService.getConfigurationInt("adminsListId"));
		FundBean fundBean = new FundBean(fund);
		for (PersonBean admin: admins){
			createSimpleFundMail(admin, proposal, fundBean, "informAdminOnUnknownFund");
		}
	}

	public void createPostsMessage(PersonBean recipient, List<PostBean> posts){
		createPostsMessage(recipient, posts, null);
	}

	public void createPostsMessage(PersonBean recipient, List<PostBean> posts, String personMD5){
		if (posts == null || posts.size() == 0)
			return;
		Map<String, Object> model = new HashMap<String, Object>();

		boolean isSelfSend = true;
		boolean needsBulletImage = false;
		boolean needsAttachmentImage = false;

		Set<Integer> existingItemTypes = new HashSet<Integer>();

		// We have good reason to assume that all the personToPost comes after the self send personToPost
		// therefore isSelfSend is true unless we find a non selfSend
		for (PostBean post: posts){
			existingItemTypes.add(post.getTypeId());
			if (post.isHasAttachment())
				needsAttachmentImage = true;
			if (post.getMessage().contains(";;") || post.isSelfSendPhase())
				needsBulletImage = true;
			if (! post.isSelfSendPhase())
				isSelfSend = false;
		}

		String postsSubject;
		if (posts.size() ==1){
			postsSubject = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postSubject");
			if (isSelfSend){
				postsSubject = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postSubjectSelfSend");
			}
		}
		else{
			postsSubject = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postsSubject");
			if (isSelfSend){
				postsSubject = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postsSubjectSelfSend");
			}
		}

		String postsAnnouncement;

		if (posts.size() ==1){
			postsAnnouncement = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postAnnouncement");
			if (isSelfSend){
				postsAnnouncement = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postAnnouncementSelfSend");
			}
		}
		else{
			postsAnnouncement = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postsAnnouncement");
			if (isSelfSend)
				postsAnnouncement = messageService.getMessage(recipient.getPreferedLocaleId() + ".post.mailMessage.postsAnnouncementSelfSend");
		}
		model.put("postsAnnouncement", postsAnnouncement);
		model.put("allRightsReserved", messageService.getMessage("general.allRightsReserved", recipient.getPreferedLocaleId()));

		model.put("messageService", messageService);
		model.put("posts", posts);
		model.put("postTypes", postService.getPostTypesMap() );



		model.put("recipient", recipient);
		model.put("server", this.getServer());
		model.put("language", LanguageUtils.getLanguagesMap().get(recipient.getPreferedLocaleId()));

		model.put("postVerificationKey", "post.mailMessage.verification");
		model.put("postEditKey", "post.mailMessage.edit");

		model.put("editSubscriptionDetailsKey", "post.mailMessage.editSubscriptionDetails");
		model.put("postSuffixKey", "post.mailMessage.postSuffix");
		model.put("relevantSubjectsPrefixKey", "post.mailMessage.relevantSubjectsPrefix");
		if (personMD5 !=null )
			model.put("personMD5", personMD5);


		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "postsMailMessage.vm", model);
		List<FileSystemResourceWrapper> resources = getCommonResources();



		resources.add( new FileSystemResourceWrapper (
				configurationService.getConfigurationString("imagesPath") + "corner_left.jpg"));
		if (needsBulletImage)
			resources.add( new FileSystemResourceWrapper (
				configurationService.getConfigurationString("imagesPath") + "bullet.gif"));

		if (needsAttachmentImage)
			resources.add( new FileSystemResourceWrapper (
				configurationService.getConfigurationString("imagesPath") + "attach.jpg"));
		for (int itemTypeId: existingItemTypes){
			resources.add( new FileSystemResourceWrapper (
					configurationService.getConfigurationString("imagesPath") + "corner_"+itemTypeId+".jpg"));
		}

		List<Attachment> attachments = new ArrayList<Attachment>();
		for (PostBean postBean: posts){
			attachments.addAll(postBean.getAttachments());
		}
		messageService.sendMail(recipient.getEmail(), EQF_MAIL_ADDRESS, postsSubject, body, resources);
	}

	public MailMessage getMailMessage(int id){
		return mailMessageDao.getMailMessage(id);
	}
	public List<MailMessage> getMailMessages(){
		return mailMessageDao.getMailMessages();
	}

	public int insertMailMessage(MailMessage mailMessage){
		int mailMessageId = mailMessageDao.insertMailMessage(mailMessage);
		return mailMessageId;
	}

	public void updateMailMessage(MailMessage mailMessage){
		mailMessageDao.updateMailMessage(mailMessage);
	}

	public void sendMailMessage(MailMessageBean mailMessageBean){
		List<Person> recipientPersons = new ArrayList<Person>();
		if (mailMessageBean.getRecipientsPersonsIds().size() > 0)
			recipientPersons = personListService.getPersons( mailMessageBean.getRecipientsPersonsIds() );
		if (mailMessageBean.getListId() > 0 && ! mailMessageBean.isSendMeOnly())
			for (PersonBean personBean : personListService.getPersonsList(mailMessageBean.getListId())){
				recipientPersons.add(personBean.toPerson());
			}
		if (recipientPersons.size() == 0 && mailMessageBean.getAdditionalAddresses().length() == 0)
			return;
		String [] to = BaseUtils.toEmailsArray(recipientPersons);
		String []  additionalAddresses = BaseUtils.toArray(mailMessageBean.getAdditionalAddresses(), ",");
		if (to.length == 0)
			to = additionalAddresses;
		else
			System.arraycopy(additionalAddresses, 0, to, to.length, additionalAddresses.length);
		String [] cc = BaseUtils.toArray(mailMessageBean.getCc(), ",");
		String [] bcc = BaseUtils.toArray(mailMessageBean.getBcc(), ",");
		Person senderPerson = personService.getPerson(mailMessageBean.getSenderPersonId());
		Map<String ,Object> model = new HashMap<String, Object> ();
		model.put("message", mailMessageBean.getMessage());
		String body = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "simpleMailMessage.vm", model);
		messageService.sendMail(to, senderPerson.getEmail(), cc, bcc,
				mailMessageBean.getMessageSubject(), body, getCommonResources(), mailMessageBean.getAttachments());
	}

	public void sendMailMessage (String to, String from,  String messageSubject, String message){
		messageService.sendMail(to, from, messageSubject, message);
	}

	private List<FileSystemResourceWrapper> getCommonResources(){
		List<FileSystemResourceWrapper> resources = new ArrayList<FileSystemResourceWrapper>();
		resources.add( new FileSystemResourceWrapper (
				configurationService.getConfigurationString("imagesPath") + "header_post.jpg"));
		return resources;
	}

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	private MailMessageDao mailMessageDao;

	public void setMailMessageDao(MailMessageDao mailMessageDao) {
		this.mailMessageDao = mailMessageDao;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}

	private PersonListService personListService;

	public PersonListService getPersonListService() {
		return personListService;
	}

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}



	private VelocityEngine velocityEngine;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	private PersonService personService;


	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private PostService postService;


	public void setPostService(PostService postService) {
		this.postService = postService;
	}
}
