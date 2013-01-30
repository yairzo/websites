package huard.iws.service;

import java.util.List;

import huard.iws.model.CallForProposal;
import huard.iws.util.SearchCreteria;
import huard.iws.util.CallForProposalSearchCreteria;

public interface CallForProposalService {

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

	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria);

	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria,String localeId);

}
