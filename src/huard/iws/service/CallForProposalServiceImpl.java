package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.CallForProposalDao;
import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.Post;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.LanguageUtils;
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
	
	public List<CallForProposal> getCallForProposalsOnline(String ids){
		return callForProposalDao.getCallForProposalsOnline(ids);
	}

	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria,String localeId){
		List<CallForProposal> localeCallForProposals = new ArrayList<CallForProposal>();
		for (CallForProposal callForProposal: callForProposalDao.getCallForProposals(searchCriteria)){
			if (LanguageUtils.getLanguage(callForProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallForProposals.add(callForProposal);
		}
		return localeCallForProposals;
	}

	public void insertArdNum(int ardNum,int id){
		callForProposalDao.insertArdNum(ardNum,id);
	}
	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment){
		return callForProposalDao.insertAttachmentToCallForProposal(callForProposalId,attachment);
	}

	
	public int copyCallForProposal (CallForProposal callForProposal){
		CallForProposal newCallForProposal = callForProposal;
		newCallForProposal.setTitle(newCallForProposal.getTitle() + " - copy");
		int newCallForProposalId = this.insertCallForProposal(newCallForProposal);
		newCallForProposal.setId(newCallForProposalId);
		callForProposalDao.updateCallForProposal(newCallForProposal);
		return newCallForProposalId;
	}
	
	public String getFirstDayOfCalendarMonth(){
		return callForProposalDao.getFirstDayOfCalendarMonth();
	}

	public String getLastDayOfCalendarMonth(){
		return callForProposalDao.getLastDayOfCalendarMonth();
	}

	public String getFirstDayOfCalendarMonth(String date){
		return callForProposalDao.getFirstDayOfCalendarMonth(date);
	}

	public String getLastDayOfCalendarMonth(String date){
		return callForProposalDao.getLastDayOfCalendarMonth(date);
	}

	public List<CallForProposal> getCalendarMonthCallForProposals(String firstDay, String lastDay){
		return callForProposalDao.getCalendarMonthCallForProposals(firstDay,lastDay);
	}
	
	
	
	private CallForProposalDao callForProposalDao;

	public void setCallForProposalDao(CallForProposalDao callForProposalDao) {
		this.callForProposalDao = callForProposalDao;
	}

}
