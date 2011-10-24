package huard.iws.db;

import huard.iws.model.ConferenceProposal;

import java.util.List;

public interface ConferenceProposalDao {

	public int insertConferenceProposal(ConferenceProposal conferenceProposal);

	public void updateConferenceProposal(ConferenceProposal conferenceProposal);

	public void deleteConferenceProposal(int id);

	public ConferenceProposal getConferenceProposal(int id);

	public ConferenceProposal getPrevVersionConferenceProposal(int confId, int verId);

	public ConferenceProposal getNextVersionConferenceProposal(int confId, int verId);
	
	public int getFirstVersion(int confId);

	public int getLastVersion(int confId);

	public List<ConferenceProposal> getConferenceProposals();
	
	public List<ConferenceProposal> getConferenceProposalsByPerson( int personId);

}
