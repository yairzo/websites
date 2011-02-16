package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.db.ProposalListDao;
import huard.iws.model.Proposal;
import huard.iws.util.BaseUtils;
import huard.iws.util.DateUtils;
import huard.iws.util.ListPaginator;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProposalListServiceImpl implements ProposalListService{
	private ProposalListDao proposalListDao;

	private final int PROPOSALS_IN_PAGE=3;

	//private static final Logger logger = Logger.getLogger(ProposalListServiceImpl.class);

	public List<ProposalBean> getProposalsPage(ListView lv, SearchCreteria search, PersonBean personBean) {
		ListPaginator lp = new ListPaginator(getProposals(lv, search, personBean), PROPOSALS_IN_PAGE);
		List l = lp.getPage(lv.getPage());

		List<ProposalBean> proposalsPage = new ArrayList<ProposalBean>();
		for (Object o : l){
			ProposalBean proposal = (ProposalBean) o;
			proposalsPage.add(proposal);
		}

		return proposalsPage;
	}



	public int prepareListView(ListView lv, SearchCreteria search, PersonBean personBean){
		ListPaginator lp = new ListPaginator(getProposals(lv, search, personBean), PROPOSALS_IN_PAGE);
		lv.setLastPage(lp.getNumOfPages());
		lv.setNearPages(lp.getNearPages(lv.getPage()));
		return lp.getNumOfPages();
	}

	public ProposalListDao getProposalListDao() {
		return proposalListDao;
	}

	public void setProposalListDao(ProposalListDao proposalListDao) {
		this.proposalListDao = proposalListDao;
	}



	public List<ProposalBean> getProposals(ListView lv, SearchCreteria search, PersonBean personBean) {
		List<Proposal> proposals = proposalListDao.getProposalList();
		List<ProposalBean> searchResults = new ArrayList<ProposalBean>();
		for (Proposal proposal: proposals){
			ProposalBean proposalBean = new ProposalBean(proposal);
			if ((personBean.isAuthorized("EQF", "ADMIN") || proposalBean.isOnProposal(personBean)) && proposalBean.isMatch(search)){
				searchResults.add(proposalBean);
			}
		}
		Comparator<ProposalBean> proposalBeanComparator = new ProposalBeanComparator(lv, personBean);
		SortedSet<ProposalBean> orderedSearchResults = new TreeSet<ProposalBean>(proposalBeanComparator);
		for (ProposalBean proposalBean: searchResults){
			orderedSearchResults.add(proposalBean);
		}
		return BaseUtils.toList(orderedSearchResults);
	}

	public List<ProposalBean> getProposals(PersonBean personBean) {
		return getProposals(new ListView(), null, personBean);
	}



	public int getNextYearId(){
		int maxYearId = proposalListDao.getMaxYearId();
		if (maxYearId == 0){ //it's the first proposal ever
			Calendar c = new GregorianCalendar();
			maxYearId = c.get(Calendar.YEAR) * 100000;
		}
		int maxYearIdYearPart = maxYearId / 100000;
		if (DateUtils.isCurrentYear(maxYearIdYearPart)){
			return maxYearId + 1;
		}
		else{
			return (maxYearIdYearPart+1)*100000+1;
		}
	}


	private class ProposalBeanComparator implements Comparator<ProposalBean>{


		private ListView listView;
		private PersonBean personBean;

		public ProposalBeanComparator(ListView listView, PersonBean personBean){
			this.listView = listView;
			this.personBean = personBean;
		}



		public int compare(ProposalBean pb1, ProposalBean pb2)
		{
			int i = 0;

			if (listView.getOrderBy().equals("") || listView.getOrderBy().equals("stateId")){
				int requiredAction2 = pb2.isRequireAction(personBean) ? 1 : 0 ;
				int requiredAction1 = pb1.isRequireAction(personBean) ? 1 : 0 ;
				i = requiredAction2 - requiredAction1;
				if (i == 0)
					i =  pb2.getStateId() - pb1.getStateId();
			}
			else if (listView.getOrderBy().equals("hebrewTitle")){
				return pb1.getHebrewTitle().compareTo(pb2.getHebrewTitle());
			}
			else if (listView.getOrderBy().equals("mainResearcherId")){
				i = pb1.getMainResearcher().getFullNameHebrew()
					.compareTo(pb2.getMainResearcher().getFullNameHebrew());
			}
			else if (listView.getOrderBy().equals("fundId")){
				i = pb2.getFund().getShortName().compareTo(pb1.getFund().getShortName());
			}
			if (i == 0)
				return pb1.getHebrewTitle().compareTo(pb2.getHebrewTitle());
			return i ;
		}


	}



}