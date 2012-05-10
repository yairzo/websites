package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.ConferenceProposalDao;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.List;

public class ConferenceProposalListServiceImpl implements ConferenceProposalListService{

	public List<ConferenceProposal> getConferenceProposalsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean,boolean forGrading) {
		List<ConferenceProposal> l = getConferenceProposals(lv, search, userPersonBean, forGrading);
		List<ConferenceProposal> conferenceProposalsPage = new ArrayList<ConferenceProposal>();
		for (Object o : l){
			ConferenceProposal conferenceProposal = (ConferenceProposal) o;
			conferenceProposalsPage.add(conferenceProposal);
		}
		return conferenceProposalsPage;
	}

	public void prepareListView(ListView lv, SearchCreteria search,PersonBean userPersonBean,boolean forGrading){
		lv.setLastPage(lv.getNumOfPages(conferenceProposalDao.countConferenceProposals(lv,search,userPersonBean,forGrading)));
		lv.setNearPages(lv.getScroll());
	}


	public List<ConferenceProposal> getConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean,boolean forGrading) {
		return conferenceProposalDao.getConferenceProposals(lv, search,userPersonBean,forGrading);
	}
	public List<ConferenceProposal> getConferenceProposalsByDate(String fromDate) {
		return conferenceProposalDao.getConferenceProposalsByDate(fromDate);
	}

	public void gradeHigher(ConferenceProposal conferenceProposal, String deadline){
		conferenceProposalDao.gradeHigher(conferenceProposal, deadline);
	}

	public void gradeLower(ConferenceProposal conferenceProposal, String deadline){
		conferenceProposalDao.gradeLower(conferenceProposal, deadline);
	}
	
	public void insertGradingInfo(ConferenceProposalGrading conferenceProposalGrading){
		conferenceProposalDao.insertGradingInfo(conferenceProposalGrading);
	}
	
	public void updateLastGradingByApproverDeadline(int approverId,String deadline,String deadlineRemarks){
		conferenceProposalDao.updateLastGradingByApproverDeadline(approverId,deadline,deadlineRemarks);
	}

	public List<ConferenceProposalGrading> getAllGradingsByCurrentDeadline(String deadline){
		return conferenceProposalDao.getAllGradingsByCurrentDeadline(deadline);
	}

	private ConferenceProposalDao conferenceProposalDao;

	public void setConferenceProposalDao(ConferenceProposalDao conferenceProposalDao) {
		this.conferenceProposalDao = conferenceProposalDao;
	}


}
