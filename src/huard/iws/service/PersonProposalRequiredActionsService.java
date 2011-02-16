package huard.iws.service;

import huard.iws.service.PersonProposalRequiredActionsServiceImpl.PersonProposalRequiredActionDetails;

import java.util.Map;

public interface PersonProposalRequiredActionsService {

	public Map<String, Integer> getPersonProposalRequiredActions();

	public int getPersonProposalRequiredActionId (String requiredActionName);

	/*public String getPersonProposalRequiredActionDisplay (String requiredActionsName);*/

	public PersonProposalRequiredActionDetails  getPersonProposalStateDetails( String stateName);

}
