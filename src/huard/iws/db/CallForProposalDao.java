package huard.iws.db;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.CallForProposalType;
import huard.iws.model.DayInCalendar;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.ListView;

public interface CallForProposalDao {

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
	
	public List<CallForProposal> getCallForProposals(ListView lv,CallForProposalSearchCreteria searchCreteria);

	public int countCallForProposals(CallForProposalSearchCreteria searchCreteria);

	public List<CallForProposal> getCallForProposalsOnline( CallForProposalSearchCreteria searchCreteria);

	public List<CallForProposal> getCallForProposalsOnlineSimple( String ids,String viewType);

	public void insertArdNum(int ardNum,int id);
	
	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment);

	public void deleteFile(int id);

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
	
	public void updateFinalSubmissionTime();

	public List<CallForProposalType> getCallForProposalTypes();

	public String getSubjectsNames(int id);

}
