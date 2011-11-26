package huard.iws.db;


import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.bean.PersonBean;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ConferenceProposalDao {

	public int insertConferenceProposal(ConferenceProposal conferenceProposal);

	public void updateConferenceProposal(ConferenceProposal conferenceProposal);

	public void deleteConferenceProposal(int id);

	public ConferenceProposal getConferenceProposal(int id);

	public ConferenceProposal getVersionConferenceProposal(int confId, int verId);

	public int getPreviousVersion(int confId, int verId);

	public int getNextVersion(int confId, int verId);

	public int getFirstVersion(int confId);

	public int getLastVersion(int confId);

	public List<ConferenceProposal> getConferenceProposals();
	
	public List<ConferenceProposal> getConferenceProposalsByPerson( int personId);
	
	public List<ConferenceProposal> getConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean);
	
	public int countConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean);

	public void gradeHigher(ConferenceProposal conferenceProposal);
	
	public void gradeLower(ConferenceProposal conferenceProposal);
	
	public int getMaxGrade(int approverId);

	public List<ConferenceProposal> getConferenceProposalsByDate(String fromDate);
	
	public void updateFromAdmitanceFee(FinancialSupport financialSupport);
}
