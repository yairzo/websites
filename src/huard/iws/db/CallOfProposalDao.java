package huard.iws.db;

import java.util.List;

import huard.iws.model.CallOfProposal;

public interface CallOfProposalDao {

	public CallOfProposal getCallOfProposal(int id);

	public CallOfProposal getCallOfProposal(String title);
	
	public int insertCallOfProposal(CallOfProposal callOfProposal);
	
	public void updateCallOfProposal(CallOfProposal callOfProposal);

	public List<CallOfProposal> getCallsOfProposals();

	public List<CallOfProposal> getCallsOfProposals( boolean open);


}
