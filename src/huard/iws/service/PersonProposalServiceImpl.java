package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonProposalBean;
import huard.iws.bean.ProposalBean;
import huard.iws.constant.PersonProposalRequiredActions;
import huard.iws.constant.PersonProposalStates;
import huard.iws.constant.PersonProposalTypes;
import huard.iws.db.PersonProposalDao;
import huard.iws.model.PersonProposal;

import java.util.ArrayList;
import java.util.List;

public class PersonProposalServiceImpl implements PersonProposalService{
	//private static final Logger logger = Logger.getLogger(PersonProposalServiceImpl.class);

	public PersonProposal insertPersonProposal(int personId, ProposalBean proposalBean, int typeId, boolean informedOfProposal){
		int stateId = 0;
		String title = "";
		List<Integer> requiredActionsIds = new ArrayList<Integer>();
		if (typeId == PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
			stateId = PersonProposalStates.getStateId("MAIN_YET_APPROVED");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("FILL_BASIC_DETAILS"));
		}
		else if (typeId == PersonProposalTypes.getTypeId("RESEARCHER")) {
			stateId = PersonProposalStates.getStateId(informedOfProposal ? "ADDED_ACKN" : "ADDED_NOT_ACKN") ;
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("FILL_BASIC_DETAILS"));
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("HANDLE_PARTNERS"));
		}
		else if (typeId == PersonProposalTypes.getTypeId("MOP_DESK")){
			stateId = PersonProposalStates.getStateId("ADDED");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("FILL_FUND_RESPONSE"));
			PersonBean personBean = personListService.getPersonsListPerson(
					mopDeskService.getMopDesk(proposalBean.getFund().getDeskId()).getPersonsListId(), personId);
			title = personBean.getTitle();
		}
		else if (typeId == PersonProposalTypes.getTypeId("DEAN")){
			stateId = PersonProposalStates.getStateId("WAIT_DEAN_APPROVAL");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("APPROVE"));
			PersonBean personBean = personListService.getPersonsListPerson(
					configurationService.getConfigurationInt("proposalApproversListId"), personId);
			title = personBean.getTitle();
		}
		else if (typeId == PersonProposalTypes.getTypeId("BUDGET_OFFICER")){
			stateId = PersonProposalStates.getStateId("ADDED");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("OPEN_BUDGET"));
			PersonBean personBean = personListService.getPersonsListPerson(
					mopDeskService.getMopDesk(proposalBean.getFund().getDeskId()).getPersonsListId(), personId);
			title = personBean.getTitle();
		}
		else if (typeId == PersonProposalTypes.getTypeId("ARCHIVER")){
			stateId = PersonProposalStates.getStateId("ADDED");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("ARCHIVE"));
//TODO: the archiver should come from another desk
			PersonBean personBean = personListService.getPersonsListPerson(
					configurationService.getConfigurationInt("archiversListId"), personId);
			title = personBean.getTitle();
		}
		else if (typeId == PersonProposalTypes.getTypeId("YISSUM")){
			stateId = PersonProposalStates.getStateId("ADDED");
			requiredActionsIds.add(PersonProposalRequiredActions.getRequiredActionId("APPROVE"));
		}

		PersonProposal personProposal = new PersonProposal(personId, proposalBean.getId(), typeId, stateId, requiredActionsIds, title);
		personProposalDao.insertPersonProposal(personProposal);
		return personProposal;
	}

	public void updatePersonProposal(PersonProposal personProposal){
		personProposalDao.updatePersonProposal(personProposal);
	}



	// A personProposal is anyone who is related somehow to the proposal
	// A researcher, a mop person, or a dean.
	// This method will update the personProposal status, depends on the proposal state and the person role


	public PersonProposal updatePersonProposal(int personId, int proposalId, int stateId){
		PersonProposal personProposal = personProposalDao.getPersonProposal(personId, proposalId);
		personProposal.setStateId(stateId);
		personProposalDao.updatePersonProposal(personProposal);
		return personProposal;
	}



	public void updateResearchersPersonProposals(int proposalId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getResearchersPersonProposals(proposalId)){
			personProposal.setRequiredActionsIds(requiredActions);
			updatePersonProposal(personProposal);
		}
	}

	public void updateResearchersPersonProposals(int proposalId, int stateId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getResearchersPersonProposals(proposalId)){
			personProposal.setStateId(stateId);
			personProposal.setRequiredActionsIds(requiredActions);
			updatePersonProposal(personProposal);
		}
	}

	public void updateMainResearcherPersonProposal(int proposalId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId()==PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
				personProposal.setRequiredActionsIds(requiredActions);
				updatePersonProposal(personProposal);
			}
		}
	}

	public void updateSecondaryResearchersPersonProposals(int proposalId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId()==PersonProposalTypes.getTypeId("RESEARCHER")){
				personProposal.setRequiredActionsIds(requiredActions);
				updatePersonProposal(personProposal);
			}
		}
	}

	public void updateSecondaryResearchersPersonProposals(int proposalId,
			int stateId, List<Integer> requiredActionsIds){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId()==PersonProposalTypes.getTypeId("RESEARCHER")){
				personProposal.setStateId(stateId);
				personProposal.setRequiredActionsIds(requiredActionsIds);
				updatePersonProposal(personProposal);
			}
		}
	}

	public void updateSecondaryResearchersPersonProposals(ProposalBean proposalBean,
			int stateId, List<Integer> requiredActionsIds){
		for (PersonProposalBean personProposalBean: proposalBean.getPersonProposalBeans()){
			if (personProposalBean.getTypeId()==PersonProposalTypes.getTypeId("RESEARCHER")){
				personProposalBean.setStateId(stateId);
				personProposalBean.setRequiredActionsIds(requiredActionsIds);
			}
		}
	}
	public void updatePersonProposals(int proposalId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			personProposal.setRequiredActionsIds(requiredActions);
			updatePersonProposal(personProposal);
		}
	}

	public void updatePersonProposal (int proposalId, int typeId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId()==typeId){
				personProposal.setRequiredActionsIds(requiredActions);
				updatePersonProposal(personProposal);
			}
		}
	}

	public void updatePersonProposal (int proposalId, int typeId, int stateId, List<Integer> requiredActions){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId()==typeId){
				personProposal.setStateId(stateId);
				personProposal.setRequiredActionsIds(requiredActions);
				updatePersonProposal(personProposal);
			}
		}
	}



	/*public void updatePersonProposalStateId(PersonProposal personProposal){
		if (! personProposal.isMainResearcher()){
			PersonProposal preUpdatePersonProposal = personProposalDao.getPersonProposal(
			personProposal.getPersonId(), personProposal.getProposalId());
			if (personProposal.getStateId() > preUpdatePersonProposal.getStateId()){
				preUpdatePersonProposal.setStateId(personProposal.getStateId());
				personProposalDao.updatePersonProposal(preUpdatePersonProposal);
			}
		}
	}*/

	public void deletePersonProposal(PersonProposal personProposal){
		personProposalDao.deletePersonProposal(personProposal);
	}

	public void deletePersonProposal(int personId, int proposalId){
		PersonProposal personProposal = new PersonProposal(personId, proposalId);
		personProposalDao.deletePersonProposal(personProposal);
	}

	public PersonProposal getPersonProposalByType(int proposalId, int typeId){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId() == typeId) return personProposal;
		}
		return null;
	}

	public List<PersonProposal> getPersonProposals(int proposalId){
		List<PersonProposal> personProposals = personProposalDao.getPersonProposals(proposalId);
		return personProposals;
	}

	public List<PersonProposal> getResearchersPersonProposals (int proposalId){
		List<PersonProposal> researchersPersonProposals = new ArrayList<PersonProposal>();
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (PersonProposalTypes.isResearcher(personProposal.getTypeId())){
				researchersPersonProposals.add(personProposal);
			}
		}
		return researchersPersonProposals;
	}



	public PersonProposal getPersonProposal(int personId, int proposalId){
		PersonProposal personProposal =  personProposalDao.getPersonProposal(personId, proposalId);
		return personProposal;
	}

	public boolean isExists(PersonProposal personProposal){
		return personProposalDao.isExists(personProposal);
	}

	public boolean isExists(int personId, int proposalId){
		PersonProposal personProposal = new PersonProposal(personId, proposalId);
		return isExists(personProposal);
	}

	public boolean isExistsByType (int proposalId, int typeId){
		for (PersonProposal personProposal: getPersonProposals(proposalId)){
			if (personProposal.getTypeId() == typeId) return true;
		}
		return false;
	}

	public boolean isLastToApprove(int proposalId, PersonProposal personProposal){
		List<PersonProposal> personProposals = getPersonProposals(proposalId);
		int countNotApproved=0;
		for (PersonProposal aPersonProposal: personProposals){
			if (! aPersonProposal.isApproval()) countNotApproved++;
		}
		return (countNotApproved==1 && personProposal.isApproval());
	}



	private PersonProposalDao personProposalDao;

	public void setPersonProposalDao(PersonProposalDao personProposalDao) {
		this.personProposalDao = personProposalDao;
	}

	private PersonListService personListService;

	public PersonListService getPersonListService() {
		return personListService;
	}

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
