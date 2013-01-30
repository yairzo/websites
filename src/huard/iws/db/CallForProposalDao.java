package huard.iws.db;

import java.util.List;

import huard.iws.model.CallForProposal;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.SearchCreteria;

public interface CallForProposalDao {

	public CallForProposal getCallForProposal(int id);

	public CallForProposal getCallForProposalOnline(int id);

	public boolean existsCallForProposalOnline(int id);
	
	public CallForProposal getCallForProposal(String title);
	
	public int insertCallForProposal(CallForProposal callForProposal);
	
	public void insertCallForProposalOnline(CallForProposal callForProposal);

	public void updateCallForProposal(CallForProposal callForProposal);

	public void updateCallForProposalOnline(CallForProposal callForProposal);

	public void removeCallForProposalOnline(int id);
	
	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria);

	public List<CallForProposal> getCallForProposalsOnline( CallForProposalSearchCreteria searchCriteria);


}
