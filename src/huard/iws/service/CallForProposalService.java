package huard.iws.service;

import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.DayInCalendar;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.ListView;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

public interface CallForProposalService {

	public CallForProposal getCallForProposal(int id);

	public CallForProposal getCallForProposalOnline(int id);

	public CallForProposal getCallForProposalOnline(String urlTitle);
	
	public String getCallForProposalUrlTitleByArdNum(int ardNum);
	
	public boolean existsCallForProposalOnline(int id);
	
	public CallForProposal getCallForProposal(String title);
	
	public int insertCallForProposal(CallForProposal callForProposal);

	public void insertCallForProposalOnline(CallForProposal callForProposal);

	public void updateCallForProposal(CallForProposal callForProposal);
	
	public void updateCallForProposalOnline(CallForProposal callForProposal);

	public void removeCallForProposalOnline(int id);
	
	public List<CallForProposal> getCallForProposals(ListView lv,CallForProposalSearchCreteria searchCriteria);

	public void prepareListView(ListView lv,CallForProposalSearchCreteria searchCreteria);

	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria);

	public List<CallForProposal> getCallForProposalsOnline(String ids);

	//public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria,String localeId);

	public void insertArdNum(int ardNum,int id);
	
	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment);

	public void deleteFile(int id);

	public int copyCallForProposal (CallForProposal callForProposal);

	public String getFirstDayOfCalendarMonth();

	public String getLastDayOfCalendarMonth();

	public String getFirstDayOfCalendarMonth(String date);

	public String getLastDayOfCalendarMonth(String date);

	public List<CallForProposal> getCalendarMonthCallForProposals(String firstDay, String lastDay);

	public LinkedHashMap<String, DayInCalendar> getMonthDaysMap(String date);
	
	public int countCallForProposalsByUrlTitle(int id,String urlTitle);

	public int countCallForProposalsByTitle(int id,String title);

	public List<Integer> getDaysWithFunds(String month,String year);

	public List<CallForProposal> getCallForProposalsPerDay(String date);
	
	public Timestamp getCallForProposalsLastUpdate();

}
