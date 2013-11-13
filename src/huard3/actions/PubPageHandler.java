package huard3.actions;

import huard3.utils.*;


public class PubPageHandler {
	private boolean message;
	private int ardNum;
	//private String externalDocUrl;
	private PubPage pubPage;
	private boolean newPubPage;
	private boolean hebrew;
	private InternalUsePubPagesQuery pubPagesQuery;
	private String [] allDeskIds;
	private String [] allDocOwners;
	
	public PubPageHandler(){
		pubPagesQuery = new InternalUsePubPagesQuery();
		this.newPubPage = false;
	}
	
	

	/**
	 * @return
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * @param i
	 */
	public void setArdNum(int i) {
		ardNum = i;
	}
	
	public PubPage getPubPageByArdNum(){
		if (pubPage==null)
		{
			if ( ardNum == 0 )
			{
				pubPage = new PubPage() ;
				pubPage.setArdNum(pubPagesQuery.getFirstFreeArdNum("PubPages"));
				this.ardNum = pubPage.getArdNum();
				this.newPubPage = true;
			
			}
			else
			{
				pubPage = pubPagesQuery.getPubPageByArdNum(this.ardNum);
			}
		}
		return pubPage;
	}
	
	public String[] getAllDeskIds(){
		if (allDeskIds==null) allDeskIds= pubPagesQuery.getAllDeskIds();
		return allDeskIds;
	}
	
	public String[] getAllDocOwners(){
		if (allDocOwners==null) allDocOwners = pubPagesQuery.getAllUsersFirstLetterUpperCased();
		return allDocOwners;
	}
	
	public void setPubPageAsBusy(String username){
		ProtectRecordsInUse.getProtector().setPubPageAsBusy(this.ardNum,username);
	}
	
	public boolean isPubPageBusy(){
		if (ardNum!=0) return ProtectRecordsInUse.getProtector().isPubPageBusy(this.ardNum);
		else return false;
	}
	
	public boolean isPubPageStillHoldedByMe(String username){
		if (ardNum!=0) return ProtectRecordsInUse.getProtector().isPubPageStillHoldedByMe(this.ardNum,username);
		else return false;
	}

	
	public boolean isHebrew() {
		if (hebrew || Utils.isHebrew(getPubPageByArdNum().getTitle())) return true;
		return false;
	}

	
	public void setHebrew(boolean b) {
		hebrew = b;
	}

	
	public boolean isNewPubPage() {
		return newPubPage;
	}

	
	public InternalUsePubPagesQuery getPubPagesQuery() {
		return pubPagesQuery;
	}

	
	public void setPubPagesQuery(InternalUsePubPagesQuery query) {
		pubPagesQuery = query;
	}

	/**
	 * @return
	 */
	public boolean isMessage() {
		return message;
	}

	/**
	 * @param b
	 */
	public void setMessage(boolean b) {
		message = b;
	}

}
