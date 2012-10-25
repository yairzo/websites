package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposal;

public interface CallOfProposalService {

	public CallOfProposal getCallOfProposal(int id);

	public CallOfProposal getCallOfProposal(String title);
	
	public int insertCallOfProposal(CallOfProposal callOfProposal);
	
	public void updateCallOfProposal(CallOfProposal callOfProposal);
	
	public List<CallOfProposal> getCallsOfProposals();

	public List<CallOfProposal> getCallsOfProposals(boolean open);

	public List<CallOfProposal> getCallsOfProposals(String localeId);

}
