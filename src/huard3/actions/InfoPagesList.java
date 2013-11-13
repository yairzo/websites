package huard3.actions;
import huard3.utils.InternalUseInfoPagesQuery;
import huard3.utils.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class InfoPagesList {

	private InternalUseInfoPagesQuery infoPagesQuery;
	private InfoPage[] infoPages;


	public InfoPagesList(){
		infoPagesQuery = new InternalUseInfoPagesQuery();
	}

	public InfoPage[] getInfoPagesByDesk(String deskId, String orderBy){
		if (infoPages==null) {
			 infoPages=infoPagesQuery.getInfoPagesByDesk(deskId, orderBy);
		}
		return infoPages;
	}



	public boolean isInfoPageBusy(int ardNum){
		return ProtectRecordsInUse.getProtector().isInfoPageBusy(ardNum);
	}

	public void releaseInfoPagesHoldedToLong(){
		ProtectRecordsInUse.getProtector().releaseInfoPagesHoldedToLong();
	}

	 public boolean isInfoPageStillHoldedByMe(int ardNum,String username){
    	return ProtectRecordsInUse.getProtector().isInfoPageStillHoldedByMe(ardNum,username);
    }

    public String getFormatedSubDate(long subDate){
    	return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(subDate));
	}

	public String getUserOccupyingInfoPage(int ardNum){
		return ProtectRecordsInUse.getProtector().getUserOccupyingInfoPage(ardNum);
	}

	public String getDatabaseName(){
		return Utils.getDatabaseName();
	}

}

