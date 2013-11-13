package huard3.actions;
import huard3.utils.InfoPagesUpdate;
import huard3.utils.InternalUseInfoPagesQuery;
import huard3.utils.LogFileHandler;


public class InfoPageReviver {
	private InfoPagesUpdate infoPagesUpdate;
	private InternalUseInfoPagesQuery infoPagesQuery;

	private String ardNum;
	private String newArdNum;


	public InfoPageReviver(){
		infoPagesUpdate = new InfoPagesUpdate();
		infoPagesQuery = new InternalUseInfoPagesQuery();
		newArdNum = ""+infoPagesQuery.getFirstFreeArdNum("InfoPages");
	}

	public void setArdNum(String ardNum){
		this.ardNum=ardNum;
	}

	public String getArdNum(){
		return this.ardNum;
	}

	public String getNewArdNum(){
		return this.newArdNum;
	}

	public void reviveInfoPage(String username){
		InfoPage infoPage = infoPagesQuery.getInfoPageDetailsByArdNum(this.ardNum);
		infoPagesUpdate.reviveInfoPage(infoPage);
		LogFileHandler.writeToUserLog("revive",username,infoPage,"");
	}



}

