package huard.iws.service;

import java.util.List;
import java.util.Map;

import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.model.Committee;

public interface ConferenceProposalService {

	public ConferenceProposal getConferenceProposal( int id);
	
	public ConferenceProposal getVersionConferenceProposal(int confId, int verId);
	
	public int getPreviousVersion(int confId, int verId);

	public int getNextVersion(int confId, int verId);

	public int getFirstVersion(int confId);

	public int getLastVersion(int confId);

	public int insertConferenceProposal (ConferenceProposal conferenceProposal);

	public void updateConferenceProposal (ConferenceProposal conferenceProposal);

	public void deleteConferenceProposal (int id);
	
	public List<ConferenceProposal> getConferenceProposals();

	public List<ConferenceProposal> getConferenceProposalsByPerson(int personId);

	public Map<Integer, ConferenceProposal> getConferenceProposalsMap();

	public Map<Integer, ConferenceProposal> getConferenceProposalsByPersonMap(int personId);
	
	public int getMaxGrade(int approverId, String deadline);

	public void rearangeGrades(int grade, int approverId, String deadline);
	
	public void insertFinancialSupport(FinancialSupport financialSupport);

	public void insertCommittee(Committee committee);

	public void deleteFinancialSupport(int financialSupportId);

	public void deleteCommittee(int committeeId);

}
