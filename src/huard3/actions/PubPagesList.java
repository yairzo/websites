package huard3.actions;
import huard3.utils.*;
import java.text.*;
import java.util.*;


public class PubPagesList {
	private boolean messages;
	private InternalUsePubPagesQuery pubPagesQuery;
	private PubPage[] pubPagesSortedByArdNum;
	private PubPage[] pubPagesSortedByTitle;
	
	public PubPagesList(){
		pubPagesQuery = new InternalUsePubPagesQuery();
	}
	
	public PubPage[] getPubPagesSortedByArdNum(){
		if (pubPagesSortedByArdNum==null) {
			  pubPagesSortedByArdNum=pubPagesQuery.getPubPagesSortedByArdNum(messages); 
		}
		return pubPagesSortedByArdNum;	
	}
	
	public PubPage[] getPubPagesSortedByTitleByDesk(String deskId){
		if (pubPagesSortedByTitle==null) pubPagesSortedByTitle=pubPagesQuery.getPubPagesSortedByTitle(messages);
		return pubPagesSortedByTitle;		
	}
	
	public boolean isPubPageBusy(int ardNum){
		return ProtectRecordsInUse.getProtector().isPubPageBusy(ardNum);		
	}
	
	public void releasePubPagesHoldedToLong(){
		ProtectRecordsInUse.getProtector().releasePubPagesHoldedToLong();
	}
		
	public boolean isPubPageStillHoldedByMe(int ardNum,String username){
		return ProtectRecordsInUse.getProtector().isPubPageStillHoldedByMe(ardNum,username);
	}
    
	public String getFormatedSubDate(long subDate){
		return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(subDate));
	}
	
	public String getUserOccupyingPubPage(int ardNum){
		return ProtectRecordsInUse.getProtector().getUserOccupyingPubPage(ardNum);
	}
	
	

	

	/**
	 * @return
	 */
	public boolean isMessages() {
		return messages;
	}

	/**
	 * @return
	 */
	public InternalUsePubPagesQuery getPubPagesQuery() {
		return pubPagesQuery;
	}

	/**
	 * @param b
	 */
	public void setMessages(boolean b) {
		this.messages = b;
	}

	/**
	 * @param query
	 */
	public void setPubPagesQuery(InternalUsePubPagesQuery query) {
		pubPagesQuery = query;
	}

}
