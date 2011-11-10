package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.ConferenceProposal;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ConferenceProposalListService {

	public List<ConferenceProposal> getConferenceProposalsPage(ListView lv, SearchCreteria search, PersonBean userPersonBean);

	public void prepareListView(ListView lv, SearchCreteria search, PersonBean userPersonBean);

}
