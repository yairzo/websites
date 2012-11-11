package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposal;

public interface CallOfProposalService {

	public CallOfProposal getCallOfProposal(int id);

	public boolean existsCallOfProposalOnline(int id);
	
	public CallOfProposal getCallOfProposal(String title);
	
	public int insertCallOfProposal(CallOfProposal callOfProposal);

	public void insertCallOfProposalOnline(CallOfProposal callOfProposal);

	public void updateCallOfProposal(CallOfProposal callOfProposal);
	
	public void updateCallOfProposalOnline(CallOfProposal callOfProposal);

	public void removeCallOfProposalOnline(int id);
	
	public List<CallOfProposal> getCallsOfProposals();

	public List<CallOfProposal> getCallsOfProposals(boolean open);

	public List<CallOfProposal> getCallsOfProposals(String localeId);

}
