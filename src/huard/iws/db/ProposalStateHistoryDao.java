package huard.iws.db;

import huard.iws.model.ProposalState;

import java.util.List;

public interface ProposalStateHistoryDao {

	public void insertProposalState(ProposalState proposalState);

	public boolean isProposalStateInHistory(ProposalState proposalState);

	public List<ProposalState> getProposalStates(int proposalId);


}
