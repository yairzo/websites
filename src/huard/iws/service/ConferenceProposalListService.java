package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;

import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ConferenceProposalListService {

	public List<ConferenceProposal> getConferenceProposalsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean);

	public void prepareListView(ListView lv, SearchCreteria search, PersonBean userPersonBean);

	public void gradeHigher(ConferenceProposal conferenceProposal, String deadline);

	public void gradeLower(ConferenceProposal conferenceProposal, String deadline);
	
	public List<ConferenceProposal> getConferenceProposalsByDate(String fromDate);

	public void insertGradingInfo(ConferenceProposalGrading conferenceProposalGrading);
	
	public void updateLastGradingByApproverDeadline(int approverId,String deadline);

	public List<ConferenceProposalGrading> getAllGradingsByCurrentDeadline(String deadline);

}
