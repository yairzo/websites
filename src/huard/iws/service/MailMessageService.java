package huard.iws.service;

import huard.iws.bean.FundBean;
import huard.iws.bean.MailMessageBean;
import huard.iws.bean.PartnerBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PostBean;
import huard.iws.bean.ProposalBean;
import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.RegistrationFormBean;
import huard.iws.model.Fund;
import huard.iws.model.MailMessage;
import huard.iws.model.ConferenceProposalGrading;

import java.util.List;



public interface MailMessageService {

	public void createRegistrationFormMail(RegistrationFormBean registrationFormBean,
			String messageKey);

	public void createPostSubscriptionMail (PersonBean recipient, String md5);
	
	public void createConferenceSubscriptionMail (PersonBean recipient, String md5);

	public void createPasswordMail (PersonBean recipient, String md5);

	public void createSimpleProposalMail(PersonBean recipient, ProposalBean proposal,
			String messageKey);

	public void createSimpleProposalMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			String messageKey);

	public void createSimpleConferenceMail(PersonBean recipient, ConferenceProposalBean conferenceProposalBean,
			String messageKey);

	public void createSimpleConferenceMail(PersonBean recipient, PersonBean sender, ConferenceProposalBean conferenceProposalBean,
			String messageKey);

	public void createSimpleConferenceGradeMail(PersonBean recipient, String messageKey);

	public void createSimpleConferenceGradeMail(ConferenceProposalGrading conferenceProposalGrading, PersonBean recipient, PersonBean sender, String messageKey);

	public void createDeanGradeFinishedGradingMail(PersonBean dean,  String messageKey);

	public void createGradingErrorMail(int approverId,  String messageKey);

	public void createSimplePartnerMail(PersonBean recipient, ProposalBean proposal,
			PartnerBean partner, String messageKey);

	public void createSimplePartnerMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			PartnerBean partner, String messageKey);

	public void createSimpleFundMail(PersonBean recipient, ProposalBean proposal,
			FundBean fund, String messageKey);

	public void createSimpleFundMail(PersonBean recipient, PersonBean sender, ProposalBean proposal,
			FundBean fund, String messageKey);

	/*public void informAddedResearcher(PersonBean addedResearcher,
			PersonBean sender, ProposalBean proposal);

	public void informRemovedResearcher(PersonBean removedResearcher,
			PersonBean sender, ProposalBean proposal);
	*/
	public void informMopOnNewProposal(ProposalBean proposal);
	/*
	public void sendDeanApprovalRequest (PersonBean dean,
			PersonBean sender, ProposalBean proposal);


	public void sendBudgetOfficerOpenBudgetRequest (PersonBean budgetOffice,
			PersonBean sender, ProposalBean proposal);

	public void sendYissumApprovalRequest (PersonBean yissumPerson, PersonBean sender, ProposalBean proposal);

	public void sendResearcherYissumResponse(PersonBean personBean, ProposalBean proposal);

	*/
	public void informAdminOnPartnerDuality (ProposalBean proposal,
			PartnerBean partner);

	public void informAdminOnPartnerAdd (ProposalBean proposal,
			PartnerBean partnerBean);
/*
	public void informAdminOnPartnerInstituteAdd (ProposalBean proposal,
			PartnerInstitute partnerInstitute);

	public void informArchiverOnProposal (ProposalBean proposal,
			PersonBean archiver);
*/
	public void informAdminsOnUnknownFund(ProposalBean proposalBean, Fund fund);
/*
	public void sendPosts(Person person, Set<Post> posts);

	public void sendUserMessage(MailMessage mailMessage);

	*/

	public MailMessage getMailMessage(int id);

	public List<MailMessage> getMailMessages();



	public int insertMailMessage(MailMessage mailMessage);

	public void updateMailMessage(MailMessage mailMessage);

	public void createPostsMessage(PersonBean recipient, List<PostBean> posts);

	public void createPostsMessage(PersonBean recipient, List<PostBean> posts, String personMD5);

	public void sendMailMessage(MailMessageBean mailMessageBean);



}
