package huard.iws.service;

import huard.iws.db.CallOfProposalDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.CallForProposalSearchCreteria;
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
	
	public List<CallOfProposal> getCallsOfProposals(CallForProposalSearchCreteria searchCriteria){
		return callOfProposalDao.getCallsOfProposals(searchCriteria);
	}

	//public List<CallOfProposal> getCallsOfProposals(CallForProposalSearchCreteria searchCriteria,boolean open){
	//	return callOfProposalDao.getCallsOfProposals(searchCriteria,open);
	//}

	public List<CallOfProposal> getCallsOfProposals(CallForProposalSearchCreteria searchCriteria,String localeId){
		List<CallOfProposal> localeCallsOfProposals = new ArrayList<CallOfProposal>();
		for (CallOfProposal callOfProposal: callOfProposalDao.getCallsOfProposals(searchCriteria)){
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
