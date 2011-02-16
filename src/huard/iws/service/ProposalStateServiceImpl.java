package huard.iws.service;

import huard.iws.constant.ProposalActions;
import huard.iws.constant.ProposalStates;

import java.util.Map;



public class ProposalStateServiceImpl implements ProposalStateService{
	//private static final Logger logger = Logger.getLogger(ProposalStateServiceImpl.class);
	private String [] proposalStatesDisplay;

	public Map<String,Integer> getProposalStates(){
		return ProposalStates.getProposalStates();
	}

	public int getProposalStateId( String stateName){
		return ProposalStates.getProposalStates().get(stateName);
	}

	public int getProposalActionId( String actionName){
		return ProposalActions.getProposalActions().get(actionName);
	}

	public String getProposalStateDisplay( String stateName){
		int stateId = ProposalStates.getProposalStates().get(stateName);
		return getProposalStateDisplay(stateId);
	}

	public String getProposalStateDisplay( int stateId){
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalState."+stateId, new String [] {});
		return stateDisplay;
	}

	public String getProposalActionDisplay( String actionName){
		int key = ProposalActions.getProposalActions().get(actionName);
		if (key == -1) return "";
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalAction."+key, new String [] {});
		return stateDisplay;
	}

	public ProposalStateDetails  getProposalStateDetails( String stateName){
		ProposalStateDetails proposalStateDetails = new ProposalStateDetails();
		proposalStateDetails.setStateId(ProposalStates.getProposalStates().get(stateName));
		proposalStateDetails.setStateName(stateName);
		int key = ProposalStates.getProposalStates().get(stateName);
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalState."+key, new String [] {});
		proposalStateDetails.setStateDisplay(stateDisplay);
		return proposalStateDetails;
	}

	public ProposalActionDetails  getProposalActionDetails( String actionName){
		ProposalActionDetails proposalActionDetails = new ProposalActionDetails();
		proposalActionDetails.setActionId(ProposalActions.getProposalActions().get(actionName));
		proposalActionDetails.setActionName(actionName);
		int key = ProposalActions.getProposalActions().get(actionName);
		String stateDisplay = messageService.getMessage("iw_IL.eqfSystem.editProposal.proposalAction."+key, new String [] {});
		proposalActionDetails.setActionDisplay(stateDisplay);
		return proposalActionDetails;
	}


	public String [] getProposalStatesDisplay(){
		if ( proposalStatesDisplay == null ) {
			proposalStatesDisplay = new String [getProposalStates().size()];
			for (String stateName: getProposalStates().keySet()){
				ProposalStateDetails proposalStateDetails = getProposalStateDetails(stateName);
				proposalStatesDisplay [ proposalStateDetails.getStateId() ] =
					proposalStateDetails.getStateDisplay();
			}
		}
		return proposalStatesDisplay;
	}


	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public class ProposalStateDetails{
		private int stateId;
		private String stateName;
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
		public String getStateName() {
			return stateName;
		}
		public void setStateName(String stateName) {
			this.stateName = stateName;
		}
	}

	public class ProposalActionDetails{
		private int actionId;
		private String actionName;
		private String actionDisplay;

		public String getActionDisplay() {
			return actionDisplay;
		}
		public void setActionDisplay(String actionDisplay) {
			this.actionDisplay = actionDisplay;
		}
		public int getActionId() {
			return actionId;
		}
		public void setActionId(int actionId) {
			this.actionId = actionId;
		}
		public String getActionName() {
			return actionName;
		}
		public void setActionName(String actionName) {
			this.actionName = actionName;
		}


	}



}
