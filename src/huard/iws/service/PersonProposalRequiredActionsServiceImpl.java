package huard.iws.service;

import huard.iws.constant.PersonProposalRequiredActions;

import java.util.Map;

public class PersonProposalRequiredActionsServiceImpl implements PersonProposalRequiredActionsService{

	public Map<String,Integer> getPersonProposalRequiredActions(){
		return PersonProposalRequiredActions.getPersonProposalRequiredActions();
	}

	public int getPersonProposalRequiredActionId( String requiredActionName){
		return PersonProposalRequiredActions.getRequiredActionId(requiredActionName);
	}

	/*public String getPersonProposalStateDisplay( String stateName){
		int key = PersonProposalStates.getPersonProposalStates().get(stateName);
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalState."+key, null);
		return stateDisplay;
	}*/

	public PersonProposalRequiredActionDetails  getPersonProposalStateDetails( String requiredActionName){
		PersonProposalRequiredActionDetails personProposalRequiredActionDetails = new PersonProposalRequiredActionDetails();
		personProposalRequiredActionDetails.setRequiredActionId(PersonProposalRequiredActions.getRequiredActionId(requiredActionName));
		int key = PersonProposalRequiredActions.getRequiredActionId(requiredActionName);
		String requiredActionDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.personProposalRequiredAction."+key, new String [] {});
		personProposalRequiredActionDetails.setRequiredActionDisplay(requiredActionDisplay);
		return personProposalRequiredActionDetails;
	}

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public class PersonProposalRequiredActionDetails{
		private int requiredActionId;
		private String requiredActionDisplay;
		public String getRequiredActionDisplay() {
			return requiredActionDisplay;
		}
		public void setRequiredActionDisplay(String requiredActionDisplay) {
			this.requiredActionDisplay = requiredActionDisplay;
		}
		public int getRequiredActionId() {
			return requiredActionId;
		}
		public void setRequiredActionId(int requiredActionId) {
			this.requiredActionId = requiredActionId;
		}


	}

}
