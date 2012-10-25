package huard.iws.service;

import huard.iws.db.CallOfProposalDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

public class CallOfProposalServiceImpl implements CallOfProposalService{

	public CallOfProposal getCallOfProposal(int id){
		return callOfProposalDao.getCallOfProposal(id);
	}

	public CallOfProposal getCallOfProposal(String title){
		return callOfProposalDao.getCallOfProposal(title);
	}
	
	public int insertCallOfProposal(CallOfProposal callOfProposal){
		return callOfProposalDao.insertCallOfProposal(callOfProposal);
	}

	public void updateCallOfProposal(CallOfProposal callOfProposal){
		callOfProposalDao.updateCallOfProposal(callOfProposal);
	}
	

	public List<CallOfProposal> getCallsOfProposals(){
		return getCallsOfProposals(false);
	}

	public List<CallOfProposal> getCallsOfProposals(boolean open){
		return callOfProposalDao.getCallsOfProposals(open);
	}

	public List<CallOfProposal> getCallsOfProposals(String localeId){
		List<CallOfProposal> localeCallsOfProposals = new ArrayList<CallOfProposal>();
		for (CallOfProposal callOfProposal: getCallsOfProposals(true)){

			if (LanguageUtils.getLanguage(callOfProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallsOfProposals.add(callOfProposal);
		}
		return localeCallsOfProposals;
	}

	private CallOfProposalDao callOfProposalDao;

	public void setCallOfProposalDao(CallOfProposalDao callOfProposalDao) {
		this.callOfProposalDao = callOfProposalDao;
	}

}
