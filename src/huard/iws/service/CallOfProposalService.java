package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposal;
import huard.iws.util.SearchCreteria;

public interface CallOfProposalService {

	public CallOfProposal getCallOfProposal(int id);

	public boolean existsCallOfProposalOnline(int id);
	
	public CallOfProposal getCallOfProposal(String title);
	
	public int insertCallOfProposal(CallOfProposal callOfProposal);

	public void insertCallOfProposalOnline(CallOfProposal callOfProposal);

	public void updateCallOfProposal(CallOfProposal callOfProposal);
	
	public void updateCallOfProposalOnline(CallOfProposal callOfProposal);

	public void removeCallOfProposalOnline(int id);
	
	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund);

	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund,boolean open);

	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund,String localeId);

}
