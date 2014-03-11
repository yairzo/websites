package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.util.ConferenceProposalSearchCreteria;
import huard.iws.util.ListView;

import java.util.List;

public interface ConferenceProposalListService {

	public List<ConferenceProposal> getConferenceProposalsPage(ListView lv, ConferenceProposalSearchCreteria search, PersonBean userPersonBean,boolean forGrading);

	public void prepareListView(ListView lv, ConferenceProposalSearchCreteria search, PersonBean userPersonBean,boolean forGrading);

	public void gradeHigher(ConferenceProposal conferenceProposal, String deadline);

	public void gradeLower(ConferenceProposal conferenceProposal, String deadline);
	
	public List<ConferenceProposal> getConferenceProposalsForCsv(String fromDate);

	public void insertGradingInfo(ConferenceProposalGrading conferenceProposalGrading);
	
	public void updateLastGradingByApproverDeadline(int approverId,String deadline,String deadlineRemarks);

	public List<ConferenceProposalGrading> getAllGradingsByCurrentDeadline(String deadline);
	
	public ConferenceProposalGrading getApproverlastGrading(int approverId,String deadline);

	public void updateStatusPerGrading(String prevdeadline,int approverId, int statusId);
	

}
