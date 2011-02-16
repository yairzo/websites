package huard.iws.service;

import huard.iws.service.ProposalStateServiceImpl.ProposalActionDetails;
import huard.iws.service.ProposalStateServiceImpl.ProposalStateDetails;

import java.util.Map;

public interface ProposalStateService {

	public Map<String, Integer> getProposalStates();

	public int getProposalStateId (String stateName);

	public int getProposalActionId (String actionName);

	public String getProposalStateDisplay (String stateName);

	public String getProposalStateDisplay (int stateId);

	public String getProposalActionDisplay( String actionName);

	public ProposalStateDetails  getProposalStateDetails( String stateName);

	public ProposalActionDetails  getProposalActionDetails( String actionName);

	public String [] getProposalStatesDisplay();

}
