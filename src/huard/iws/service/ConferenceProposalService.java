package huard.iws.service;

import java.util.List;
import java.util.Map;

import huard.iws.model.ConferenceProposal;

public interface ConferenceProposalService {

	public ConferenceProposal getConferenceProposal( int id);
	
	public ConferenceProposal getPrevVersionConferenceProposal(int confId, int verId);
	
	public int getFirstVersion(int confId);

	public int getLastVersion(int confId);

	public ConferenceProposal getNextVersionConferenceProposal(int confId, int verId);

	public int insertConferenceProposal (ConferenceProposal conferenceProposal);

	public void updateConferenceProposal (ConferenceProposal conferenceProposal);

	public void deleteConferenceProposal (int id);
	
	public List<ConferenceProposal> getConferenceProposals();

	public List<ConferenceProposal> getConferenceProposalsByPerson(int personId);

	public Map<Integer, ConferenceProposal> getConferenceProposalsMap();

	public Map<Integer, ConferenceProposal> getConferenceProposalsByPersonMap(int personId);

}
