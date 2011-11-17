package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.ConferenceProposalDao;
import huard.iws.model.ConferenceProposal;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.List;

public class ConferenceProposalListServiceImpl implements ConferenceProposalListService{

	public List<ConferenceProposal> getConferenceProposalsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean) {
		List<ConferenceProposal> l = getConferenceProposals(lv, search, userPersonBean);
		List<ConferenceProposal> conferenceProposalsPage = new ArrayList<ConferenceProposal>();
		for (Object o : l){
			ConferenceProposal conferenceProposal = (ConferenceProposal) o;
			conferenceProposalsPage.add(conferenceProposal);
		}
		return conferenceProposalsPage;
	}

	public void prepareListView(ListView lv, SearchCreteria search,PersonBean userPersonBean){
		lv.setLastPage(lv.getNumOfPages(conferenceProposalDao.countConferenceProposals(lv,search,userPersonBean)));
		lv.setNearPages(lv.getScroll());
	}


	public List<ConferenceProposal> getConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean) {
		return conferenceProposalDao.getConferenceProposals(lv, search,userPersonBean);
	}
	public List<ConferenceProposal> getConferenceProposalsByDate(String fromDate) {
		return conferenceProposalDao.getConferenceProposalsByDate(fromDate);
	}

	public void gradeHigher(ConferenceProposal conferenceProposal){
		conferenceProposalDao.gradeHigher(conferenceProposal);
	}

	public void gradeLower(ConferenceProposal conferenceProposal){
		conferenceProposalDao.gradeLower(conferenceProposal);
	}
	


	private ConferenceProposalDao conferenceProposalDao;

	public void setConferenceProposalDao(ConferenceProposalDao conferenceProposalDao) {
		this.conferenceProposalDao = conferenceProposalDao;
	}


}
