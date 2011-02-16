package huard.iws.service;

import huard.iws.service.PersonProposalStateServiceImpl.PersonProposalStateDetails;

import java.util.Map;

public interface PersonProposalStateService {

	public Map<String, Integer> getPersonProposalStates();

	public int getPersonProposalStateId (String stateName);

	/*public String getPersonProposalStateDisplay (String stateName);*/

	public PersonProposalStateDetails  getPersonProposalStateDetails( String stateName);

}
