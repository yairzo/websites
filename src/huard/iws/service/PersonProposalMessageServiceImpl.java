package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.constant.PersonProposalTypes;
import huard.iws.model.PersonProposal;

import java.util.List;

public class PersonProposalMessageServiceImpl implements PersonProposalMessageService {
	//private static final Logger logger = Logger.getLogger(PersonProposalMessageServiceImpl.class);

	//TODO: adjust to the new structure of createSimpleProposalMessage

	public void sendApprovalRequestToAllSecondaryResearchers(int proposalId){
		List<PersonProposal> personProposals = personProposalService.getResearchersPersonProposals(proposalId);

		PersonBean mainResearcher =null;
		ProposalBean proposal = new ProposalBean(proposalService.getProposal(proposalId));
		for (PersonProposal personProposal: personProposals){
			if (personProposal.getTypeId() == PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
				mainResearcher = new PersonBean(personService.getPerson(personProposal.getPersonId()));
			}
			else{
				PersonBean researcher = new PersonBean(personService.getPerson(personProposal.getPersonId()));
				mailMessageService.createSimpleProposalMail(researcher, mainResearcher, proposal, "secondaryResearchersApprovalRequest");
			}
		}
	}

	//TODO: complete the move to new mailMessage template


	public void informAllResearchersOnFundResponse(int proposalId, boolean approved){
		List<PersonProposal> personProposals = personProposalService.getResearchersPersonProposals(proposalId);
		ProposalBean proposal =  new ProposalBean (proposalService.getProposal(proposalId));
		for (PersonProposal personProposal: personProposals){
			PersonBean recipient = new PersonBean (personService.getPerson(personProposal.getPersonId()));
			String messageKey = "allResearchersFund" + (approved ? "Approval" : "Refusal");
			mailMessageService.createSimpleProposalMail(recipient, proposal, messageKey);
		}
	}

	public void informAllResearchersOnBudgetOpened(int proposalId){
		List<PersonProposal> personProposals = personProposalService.getResearchersPersonProposals(proposalId);
		ProposalBean proposal =  new ProposalBean (proposalService.getProposal(proposalId));
		for (PersonProposal personProposal: personProposals){
			PersonBean recipient = new PersonBean (personService.getPerson(personProposal.getPersonId()));
			mailMessageService.createSimpleProposalMail(recipient, proposal, "allResearchersBudgetOpened");
		}
	}


	public void informAllResearchersOnDeanResponse(PersonBean dean, int proposalId, boolean approved){
		List<PersonProposal> personProposals = personProposalService.getResearchersPersonProposals(proposalId);
		ProposalBean proposal =  new ProposalBean (proposalService.getProposal(proposalId));
		for (PersonProposal personProposal: personProposals){
			PersonBean recipient = new PersonBean (personService.getPerson(personProposal.getPersonId()));
			String messageKey = "allResearchersDean" + (approved ? "Approval" : "Refusal");
			mailMessageService.createSimpleProposalMail(recipient, proposal, messageKey);
		}
	}

	public void informMopDeskOnDeanResponse(PersonBean dean, int proposalId, boolean approved){
		PersonProposal personProposal = personProposalService.getPersonProposalByType(proposalId,
				PersonProposalTypes.getTypeId("MOP_DESK"));
		ProposalBean proposal = new ProposalBean (proposalService.getProposal(proposalId));
		PersonBean mopDeskPerson = new PersonBean (personService.getPerson(personProposal.getPersonId()));
		String messageKey = "informMopOnDean" + (approved ? "Approval" : "Refusal");
		mailMessageService.createSimpleProposalMail(mopDeskPerson, dean, proposal, messageKey);
	}




	private PersonProposalService personProposalService;

	public void setPersonProposalService(PersonProposalService personProposalService) {
		this.personProposalService = personProposalService;
	}

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private ProposalService proposalService;

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	private MailMessageService mailMessageService;

	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}




}
