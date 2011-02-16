package huard.iws.db;

import huard.iws.model.Proposal;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface ProposalListDao {

	public List<Proposal> getProposalList(ListView lv, SearchCreteria search);

	public List<Proposal> getProposalListByPersonId(int personId);

	public List<Proposal> getProposalList(ListView lv);

	public List<Proposal> getProposalList();

	public List<Proposal> getProposalListByPersonId(ListView lv, SearchCreteria search, int personId);

	public List<Proposal> getProposalListByPersonId(ListView lv, int personId);

	public int getMaxYearId();

}
