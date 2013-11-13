package huard3.actions;
import huard3.utils.*;

public class PubPageReviver {
	private PubPagesUpdate pubPagesUpdate;
	private InternalUsePubPagesQuery pubPagesQuery;
	private int ardNum;
	private int newArdNum;


	public PubPageReviver(){
		pubPagesUpdate = new PubPagesUpdate();
		pubPagesQuery = new InternalUsePubPagesQuery();
		newArdNum = pubPagesQuery.getFirstFreeArdNum("PubPages");
	}




	public boolean revivePubPage(String username){
		PubPage pubPage = pubPagesQuery.getPubPageByArdNum(this.ardNum);
		pubPagesUpdate.revivePubPage(pubPage);
		LogFileHandler.writeToUserLog("revived",username,pubPage,"");
		return true;
	}

	/**
	 * @return
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * @return
	 */
	public int getNewArdNum() {
		return newArdNum;
	}

	/**
	 * @param i
	 */
	public void setArdNum(int i) {
		ardNum = i;
	}

	/**
	 * @param i
	 */
	public void setNewArdNum(int i) {
		newArdNum = i;
	}

}
