package huard.iws.db;


import huard.iws.bean.PersonBean;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.model.FinancialSupport;
import huard.iws.util.ConferenceProposalSearchCreteria;
import huard.iws.util.ListView;

import java.util.List;
import java.util.Map;

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
	
	public List<ConferenceProposal> getConferenceProposals(ListView lv, ConferenceProposalSearchCreteria search, PersonBean userPersonBean,boolean forGrading);
	
	public int countConferenceProposals(ListView lv, ConferenceProposalSearchCreteria search, PersonBean userPersonBean, boolean forGrading);

	public void gradeHigher(ConferenceProposal conferenceProposal, String deadline);
	
	public void gradeLower(ConferenceProposal conferenceProposal, String deadline);
	
	public int getMaxGrade(int approverId, String deadline);

	public void rearangeGrades(int grade, int approverId, String deadline);

	public boolean checkGrades(int approverId, String deadline);

	public List<ConferenceProposal> getConferenceProposalsForCsv(String fromDate);
	
	public void insertFinancialSupport(FinancialSupport financialSupport);
	
	public void updateFinancialSupport(FinancialSupport financialSupport);
	
	public FinancialSupport getFinancialSupport(int financialSupportId);
	
	public void insertCommittee(Committee committee);
	
	public void insertCommittees(ConferenceProposal conferenceProposal);
	
	public void updateCommittee(Committee committee);
	
	public void deleteFinancialSupport(int financialSupportId);
	
	public void deleteCommittee(int committeeId);
	
	public void updateDeadlineRemarks(int approverId, String prevdeadline, String deadlineRemarks);
	
	public void insertGradingInfo(ConferenceProposalGrading conferenceProposalGrading);

	public void updateLastGradingByApproverDeadline(int approverId,String deadline,String deadlineRemarks);
	
	public void updateStatusPerGrading(String prevdeadline,int approverId, int statusId);

	public List<ConferenceProposalGrading> getAllGradingsByCurrentDeadline(String deadline);
	
	public ConferenceProposalGrading getApproverlastGrading(int approverId,String deadline);

	public Map<Integer, String> getStatusMap();

}
