package huard3.actions;
import huard3.utils.InternalUseInfoPagesQuery;

import huard3.utils.Utils;

public class OldInfoPagesList {
	private InternalUseInfoPagesQuery infoPagesQuery;
	InfoPage[] infoPagesSortedByArdNum;
	InfoPage[] infoPagesSortedByTitle;
	
	public OldInfoPagesList(){
		infoPagesQuery = new InternalUseInfoPagesQuery();
	}
	
	public InfoPage[] getInfoPagesSortedByArdNumByDesk(String deskId){
		if (infoPagesSortedByArdNum==null) infoPagesSortedByArdNum=infoPagesQuery.getOldInfoPagesSortedByArdNum(deskId); 
		return infoPagesSortedByArdNum;	
	}
	
	public InfoPage[] getInfoPagesSortedByTitleByDesk(String deskId){
		if (infoPagesSortedByTitle==null) infoPagesSortedByTitle=infoPagesQuery.getOldInfoPagesSortedByTitle(deskId);
		
		return infoPagesSortedByTitle;		
	}
	
	public String getFormatedSubDate(long subDate){
    	return Utils.getFormatedDate(subDate);
	}
	
	
	
	

}

