package huard3.actions;
import huard3.utils.InfoPagesQuery;
import huard3.utils.InfoPagesUpdate;
import huard3.utils.LogFileHandler;


public class InfoPageDeleter {

	private String ardNum;
	private InfoPagesUpdate infoPagesUpdate;
    private String newArdNum;

	public InfoPageDeleter(){
		infoPagesUpdate = new InfoPagesUpdate();
	}

	public void setArdNum(String ardNum){
		this.ardNum=ardNum;
	}

	public String getArdNum(){
		return ardNum;
	}

	public InfoPage getInfoPage(){
		return new InfoPagesQuery().getInfoPageDetailsByArdNumSkipKeywords(""+ardNum,"InfoPages");
	}


	public void deleteInfoPage(String username){
		LogFileHandler.writeToUserLog("delete", username, getInfoPage(), "");
		infoPagesUpdate.deleteInfoPageByArdNum(this.ardNum);
	}

	public String getDeskId(){
		return getInfoPage().getDeskId();
	}

	public String getNewArdNum(){
		if (newArdNum==null) newArdNum="";
		return newArdNum;
	}



}


