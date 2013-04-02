package huard.iws.db;

import java.util.List;

import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.util.CallForProposalSearchCreteria;

public interface CallForProposalDao {

	public CallForProposal getCallForProposal(int id);

	public CallForProposal getCallForProposalOnline(int id);

	public boolean existsCallForProposalOnline(int id);
	
	public CallForProposal getCallForProposal(String title);
	
	public int insertCallForProposal(CallForProposal callForProposal);
	
	public void insertCallForProposalOnline(CallForProposal callForProposal);

	public void updateCallForProposal(CallForProposal callForProposal);

	public void updateCallForProposalOnline(CallForProposal callForProposal);

	public void removeCallForProposalOnline(int id);
	
	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria);

	public List<CallForProposal> getCallForProposalsOnline( CallForProposalSearchCreteria searchCriteria);

	public List<CallForProposal> getCallForProposalsOnline( String ids);

	public void insertArdNum(int ardNum,int id);
	
	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment);

	public String getFirstDayOfCalendarMonth();

	public String getLastDayOfCalendarMonth();
	
	public String getFirstDayOfCalendarMonth(String date);

	public String getLastDayOfCalendarMonth(String date);
	
	public List<CallForProposal> getCalendarMonthCallForProposals(String firstDay, String lastDay);
	
}
