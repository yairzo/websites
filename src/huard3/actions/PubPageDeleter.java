package huard3.actions;
import huard3.utils.*;

public class PubPageDeleter {

	private int ardNum;
	private PubPage pubPage;
	private PubPagesUpdate pubPagesUpdate;
	private int newArdNum;

	public PubPageDeleter(){
		pubPagesUpdate = new PubPagesUpdate();
	}

	public PubPageDeleter(int ardNum){
		this.ardNum = ardNum;
		pubPagesUpdate = new PubPagesUpdate();
	}

	public PubPage getPubPage(){
		if (pubPage==null)
		pubPage = new PubPagesQuery().getPubPageByArdNum(ardNum);
		return pubPage;
	}

	public void deletePubPage(String username){
		newArdNum = new InternalUsePubPagesQuery().getFirstOldsFreeArdNum("PubPages");
		LogFileHandler.writeToUserLog("delete", username, getPubPage(), "");
		pubPagesUpdate.deletePubPageByArdNum(this.ardNum);
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

	/**
	 * @return
	 */
	public int getNewArdNum() {
		return newArdNum;
	}

	/**
	 * @param i
	 */
	public void setNewArdNum(int i) {
		newArdNum = i;
	}

}
