package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.db.ProposalStateHistoryDao;
import huard.iws.model.ProposalState;

import java.util.List;

public class ProposalStateHistoryServiceImpl implements ProposalStateHistoryService{

	public void insertProposalState(String stateName, String actionName, ProposalBean proposalBean,
			PersonBean userPersonBean, String personRole){
		ProposalState proposalState = new ProposalState(proposalStateService.getProposalStateId(stateName),
				proposalStateService.getProposalActionId(actionName),
				proposalBean.getId(), userPersonBean.getId(), personRole,
				proposalStateService.getProposalStateDisplay(stateName),
				proposalStateService.getProposalActionDisplay(actionName));
		proposalStateHistoryDao.insertProposalState(proposalState);
	}

	public void insertProposalState(int stateId, String actionName, ProposalBean proposalBean,
			PersonBean userPersonBean, String personRole){
		ProposalState proposalState = new ProposalState(stateId,
				proposalStateService.getProposalActionId(actionName),
				proposalBean.getId(), userPersonBean.getId(), personRole,
				proposalStateService.getProposalStateDisplay(stateId),
				proposalStateService.getProposalActionDisplay(actionName));
		proposalStateHistoryDao.insertProposalState(proposalState);
	}

	public boolean isProposalStateInHistory(ProposalState proposalState){
		return proposalStateHistoryDao.isProposalStateInHistory(proposalState);
	}

	public List<ProposalState> getProposalStates(int proposalId){
		return proposalStateHistoryDao.getProposalStates(proposalId);
	}


	private ProposalStateHistoryDao proposalStateHistoryDao;


	public void setProposalStateHistoryDao(
			ProposalStateHistoryDao proposalStateHistoryDao) {
		this.proposalStateHistoryDao = proposalStateHistoryDao;
	}

	private ProposalStateService proposalStateService;


	public void setProposalStateService(ProposalStateService proposalStateService) {
		this.proposalStateService = proposalStateService;
	}


}
