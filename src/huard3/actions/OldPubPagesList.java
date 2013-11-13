package huard3.actions;
import huard3.utils.*;

public class OldPubPagesList {
	private boolean isMessages;
	private InternalUsePubPagesQuery pubPagesQuery;
	PubPage[] pubPagesSortedByArdNum;
	PubPage[] pubPagesSortedByTitle;
	
	public OldPubPagesList(){
		pubPagesQuery = new InternalUsePubPagesQuery();
	}
	
	public PubPage[] getPubPagesSortedByArdNum(){
		if (pubPagesSortedByArdNum==null) pubPagesSortedByArdNum=pubPagesQuery.getOldPubPagesSortedByArdNum(isMessages); 
		return pubPagesSortedByArdNum;	
	}
	
	public PubPage[] getPubPagesSortedByTitle(){
		if (pubPagesSortedByTitle==null) pubPagesSortedByTitle=pubPagesQuery.getOldPubPagesSortedByTitle(isMessages);
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
    
		public String getUserOccupyingPubPage(int ardNum){
			return ProtectRecordsInUse.getProtector().getUserOccupyingPubPage(ardNum);
		}

	/**
	 * @return
	 */
	public boolean isMessages() {
		return isMessages;
	}

	/**
	 * @param b
	 */
	public void setMessages(boolean b) {
		isMessages = b;
	}

}
