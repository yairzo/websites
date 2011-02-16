package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.model.ProposalState;

import java.util.List;

public interface ProposalStateHistoryService {

	public void insertProposalState(String stateName, String actionName, ProposalBean proposalBean,
			PersonBean userPersonBean, String personRole);
	public void insertProposalState(int stateId, String actionName, ProposalBean proposalBean,
			PersonBean userPersonBean, String personRole);

	public boolean isProposalStateInHistory(ProposalState proposalState);

	public List<ProposalState> getProposalStates(int proposalId);

}
