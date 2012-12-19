package huard.iws.service;

import huard.iws.db.CallOfProposalDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.LanguageUtils;
import huard.iws.util.SearchCreteria;
import java.util.ArrayList;
import java.util.List;

public class CallOfProposalServiceImpl implements CallOfProposalService{

	public CallOfProposal getCallOfProposal(int id){
		return callOfProposalDao.getCallOfProposal(id);
	}
	
	public boolean existsCallOfProposalOnline(int id){
		return callOfProposalDao.existsCallOfProposalOnline(id);
	}
	
	public CallOfProposal getCallOfProposal(String title){
		return callOfProposalDao.getCallOfProposal(title);
	}
	
	public int insertCallOfProposal(CallOfProposal callOfProposal){
		return callOfProposalDao.insertCallOfProposal(callOfProposal);
	}
	
	public void insertCallOfProposalOnline(CallOfProposal callOfProposal){
		callOfProposalDao.insertCallOfProposalOnline(callOfProposal);
	}

	public void updateCallOfProposal(CallOfProposal callOfProposal){
		callOfProposalDao.updateCallOfProposal(callOfProposal);
	}
	
	public void updateCallOfProposalOnline(CallOfProposal callOfProposal){
		callOfProposalDao.updateCallOfProposalOnline(callOfProposal);
	}

	public void removeCallOfProposalOnline(int id){
		callOfProposalDao.removeCallOfProposalOnline(id);
	}
	
	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund){
		return getCallsOfProposals(temporaryFund,false);
	}

	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund,boolean open){
		return callOfProposalDao.getCallsOfProposals(temporaryFund,open);
	}

	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund,String localeId){
		List<CallOfProposal> localeCallsOfProposals = new ArrayList<CallOfProposal>();
		for (CallOfProposal callOfProposal: getCallsOfProposals(temporaryFund,true)){

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
