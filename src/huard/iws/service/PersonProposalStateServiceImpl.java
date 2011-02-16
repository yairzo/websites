package huard.iws.service;

import huard.iws.constant.PersonProposalStates;

import java.util.Map;

public class PersonProposalStateServiceImpl implements PersonProposalStateService{

	public Map<String,Integer> getPersonProposalStates(){
		return PersonProposalStates.getPersonProposalStates();
	}

	public int getPersonProposalStateId( String stateName){
		return PersonProposalStates.getPersonProposalStates().get(stateName);
	}

	/*public String getPersonProposalStateDisplay( String stateName){
		int key = PersonProposalStates.getPersonProposalStates().get(stateName);
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalState."+key, null);
		return stateDisplay;
	}*/

	public PersonProposalStateDetails  getPersonProposalStateDetails( String stateName){
		PersonProposalStateDetails personProposalStateDetails = new PersonProposalStateDetails();
		personProposalStateDetails.setStateId(PersonProposalStates.getPersonProposalStates().get(stateName));
		int key = PersonProposalStates.getPersonProposalStates().get(stateName);
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.personProposalState."+key, new String [] {});
		personProposalStateDetails.setStateDisplay(stateDisplay);
		return personProposalStateDetails;
	}

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public class PersonProposalStateDetails{
		private int stateId;
		private String stateDisplay;

		public String getStateDisplay() {
			return stateDisplay;
		}
		public void setStateDisplay(String stateDisplay) {
			this.stateDisplay = stateDisplay;
		}
		public int getStateId() {
			return stateId;
		}
		public void setStateId(int stateId) {
			this.stateId = stateId;
		}
	}

}
