package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposal;
import huard.iws.util.SearchCreteria;
import huard.iws.util.CallForProposalSearchCreteria;

public interface CallOfProposalService {

	public CallOfProposal getCallOfProposal(int id);

	public CallOfProposal getCallOfProposalOnline(int id);

	public boolean existsCallOfProposalOnline(int id);
	
	public CallOfProposal getCallOfProposal(String title);
	
	public int insertCallOfProposal(CallOfProposal callOfProposal);

	public void insertCallOfProposalOnline(CallOfProposal callOfProposal);

	public void updateCallOfProposal(CallOfProposal callOfProposal);
	
	public void updateCallOfProposalOnline(CallOfProposal callOfProposal);

	public void removeCallOfProposalOnline(int id);
	
	public List<CallOfProposal> getCallsOfProposals(CallForProposalSearchCreteria searchCriteria);

	public List<CallOfProposal> getCallsOfProposalsOnline(CallForProposalSearchCreteria searchCriteria);

	public List<CallOfProposal> getCallsOfProposals(CallForProposalSearchCreteria searchCriteria,String localeId);

}
