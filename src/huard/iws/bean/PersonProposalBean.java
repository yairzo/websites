package huard.iws.bean;

import huard.iws.constant.PersonProposalTypes;
import huard.iws.constant.ProposalStates;
import huard.iws.model.PersonProposal;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonService;
import huard.iws.service.ProposalService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.BaseUtils;

import java.util.List;

public class PersonProposalBean {

	//private static final Logger logger = Logger.getLogger(PersonProposalBean.class);

	private int id;
	private int personId;
	private int proposalId;
	private int typeId;
	private Integer stateId;
	private List<Integer> requiredActionsIds;
	private boolean approval;
	private String title;

	private PersonBean personBean;
	private ProposalBean proposalBean;

	private PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
	private ProposalService proposalService = (ProposalService) ApplicationContextProvider.getContext().getBean("proposalService");
	private PersonProposalService personProposalService = (PersonProposalService) ApplicationContextProvider.getContext().getBean("personProposalService");

	private final int [] mainResearcherNeedActionProposalStates = { ProposalStates.getProposalStateId("DRAFT"), ProposalStates.getProposalStateId("FUND_APPROVED")};
	private final int [] secondaryResearcherNeedActionProposalStates = { ProposalStates.getProposalStateId("DRAFT"), ProposalStates.getProposalStateId("APPROVED_BY_MAIN"), ProposalStates.getProposalStateId("FUND_APPROVED")};
	private final int [] mopPersonNeedActionProposalStates = { ProposalStates.getProposalStateId("APPROVED_BY_ALL"), ProposalStates.getProposalStateId("DEAN_APPROVED")};
	private final int [] budgetOfficerNeedActionProposalStates = { ProposalStates.getProposalStateId("PASSED_MOP_BUDGET_OFFICER")};
	private final int [] deanNeedActionProposalStates = { ProposalStates.getProposalStateId("DEAN_WAIT")};



	public boolean isActionRequired(){
		if (personProposalService.isExists(this.toPersonProposal())){
			if (typeId == PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
				if (BaseUtils.contains(mainResearcherNeedActionProposalStates, proposalBean.getStateId())){
					return true;
				}
			}
			// a secondary researcher
			else if (typeId == PersonProposalTypes.getTypeId("RESEARCHER")){
				if (BaseUtils.contains(secondaryResearcherNeedActionProposalStates, proposalBean.getStateId())){
					return true;
				}
			}
			else if (typeId == PersonProposalTypes.getTypeId("MOP_DESK")){
				if (BaseUtils.contains(mopPersonNeedActionProposalStates, proposalBean.getStateId())){
					return true;
				}
			}
			else if (typeId == PersonProposalTypes.getTypeId("DEAN")){
				if (BaseUtils.contains(deanNeedActionProposalStates, proposalBean.getStateId())){
					return true;
				}
			}
			else if (typeId == PersonProposalTypes.getTypeId("BUDGET_OFFICER")){
				if (BaseUtils.contains(budgetOfficerNeedActionProposalStates, proposalBean.getStateId())){
					return true;
				}
			}
		}
		return false;

	}




	public PersonProposalBean(){

	}

	private void init(){
		this.personBean = new PersonBean(personService.getPerson(personId));
		personBean.setTitle(this.getTitle());
		this.proposalBean = new ProposalBean(proposalService.getProposal(proposalId));
	}


	public PersonProposalBean(int personId, int proposalId){
		this.personId = personId;
		this.proposalId = proposalId;
		init();
	}

	public PersonProposalBean(PersonProposal personProposal){
		this.id = personProposal.getId();
		this.personId = personProposal.getPersonId();
		this.proposalId = personProposal.getProposalId();
		this.typeId = personProposal.getTypeId();
		this.stateId = personProposal.getStateId();
		this.requiredActionsIds = personProposal.getRequiredActionsIds();
		this.approval = personProposal.isApproval();
		this.title = personProposal.getTitle();
		init();
	}

	public PersonProposal toPersonProposal(){
		PersonProposal personProposal = new PersonProposal();
		personProposal.setId(id);
		personProposal.setPersonId(personId);
		personProposal.setProposalId(proposalId);
		personProposal.setTypeId(typeId);
		personProposal.setStateId(stateId);
		personProposal.setRequiredActionsIds(requiredActionsIds);
		personProposal.setApproval(approval);
		personProposal.setTitle(title);
		return personProposal;
	}





	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getTypeId() {
		return typeId;
	}




	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}




	public PersonBean getPersonBean() {
		return personBean;
	}



	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getProposalId() {
		return proposalId;
	}

	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public List<Integer> getRequiredActionsIds() {
		return requiredActionsIds;
	}




	public void setRequiredActionsIds(List<Integer> requiredActionsIds) {
		this.requiredActionsIds = requiredActionsIds;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}



}
