package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ProposalListService {
	public List<ProposalBean> getProposalsPage(ListView lv, SearchCreteria search, PersonBean personBean);

	public List<ProposalBean> getProposals(PersonBean personBean);

	public int prepareListView(ListView lv, SearchCreteria search, PersonBean personBean);

	public int getNextYearId();

}
