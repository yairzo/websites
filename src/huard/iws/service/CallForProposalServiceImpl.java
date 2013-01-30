package huard.iws.service;

import huard.iws.db.CallForProposalDao;
import huard.iws.model.CallForProposal;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.LanguageUtils;
import huard.iws.util.SearchCreteria;
import java.util.ArrayList;
import java.util.List;

public class CallForProposalServiceImpl implements CallForProposalService{

	public CallForProposal getCallForProposal(int id){
		return callForProposalDao.getCallForProposal(id);
	}
	
	public CallForProposal getCallForProposalOnline(int id){
		return callForProposalDao.getCallForProposalOnline(id);
	}
	
	public boolean existsCallForProposalOnline(int id){
		return callForProposalDao.existsCallForProposalOnline(id);
	}
	
	public CallForProposal getCallForProposal(String title){
		return callForProposalDao.getCallForProposal(title);
	}
	
	public int insertCallForProposal(CallForProposal callForProposal){
		return callForProposalDao.insertCallForProposal(callForProposal);
	}
	
	public void insertCallForProposalOnline(CallForProposal callForProposal){
		callForProposalDao.insertCallForProposalOnline(callForProposal);
	}

	public void updateCallForProposal(CallForProposal callForProposal){
		callForProposalDao.updateCallForProposal(callForProposal);
	}
	
	public void updateCallForProposalOnline(CallForProposal callForProposal){
		callForProposalDao.updateCallForProposalOnline(callForProposal);
	}

	public void removeCallForProposalOnline(int id){
		callForProposalDao.removeCallForProposalOnline(id);
	}
	
	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria){
		return callForProposalDao.getCallForProposals(searchCriteria);
	}

	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria){
		return callForProposalDao.getCallForProposalsOnline(searchCriteria);
	}

	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria,String localeId){
		List<CallForProposal> localeCallForProposals = new ArrayList<CallForProposal>();
		for (CallForProposal callForProposal: callForProposalDao.getCallForProposals(searchCriteria)){
			if (LanguageUtils.getLanguage(callForProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallForProposals.add(callForProposal);
		}
		return localeCallForProposals;
	}

	private CallForProposalDao callForProposalDao;

	public void setCallForProposalDao(CallForProposalDao callForProposalDao) {
		this.callForProposalDao = callForProposalDao;
	}

}
