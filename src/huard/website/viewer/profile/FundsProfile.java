package huard.website.viewer.profile;
import huard.website.model.*;
import huard.website.util.*;


public class FundsProfile extends Profile{

	private final String profileName="Funds";
	private InfoPage [] infoPages;
	private Fund [] funds;
	private ProfilesDbHandler dbHandler;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";
//	private String categoryColor;
	private ComposedPatternedPage composedPatternedPage;



	public FundsProfile(){
		dbHandler = new ProfilesDbHandler();
		infoPages = dbHandler.getInfoPagesSortedByFundsFullNameThenByTitle();
		for (int i=0; i<infoPages.length; i++){
			if (infoPages[i].isHasAdditionalSubDates()) infoPages[i].setAdditionalSubDates(getAdditionalSubDates(infoPages[i].getArdNum()));
		}
		funds = dbHandler.getFundsSortedByFullName();
		int j=0;
		for (int i=0; i<funds.length; i++){
			int copyBeginIndex = j;
			while (j<infoPages.length && funds[i].getFundNum()==infoPages[j].getFundNum()){
				j++;
			}
			int copyEndIndex = j;
			int length = copyEndIndex - copyBeginIndex;
			TabledInfoPage [] fundsInfoPages = new TabledInfoPage [length];
			System.arraycopy(infoPages, copyBeginIndex, fundsInfoPages, 0, length );
			funds[i].setInfoPages(fundsInfoPages);
		}
		composedPatternedPage = dbHandler.getComposedPatternedPageByProfileName(profileName);
//	 	categoryColor = new MenusDbHandler().getCategoryByCategoryName( category.substring( 0,category.indexOf("_") ), "Eng" ).getBgcolor();
	}

	public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("ComposedPatternedPages",101,getTitle(true),heb,ip);
	}

	public String getTitle(boolean heb){
 		if (heb) return composedPatternedPage.getHebrewTitle();
 		else return composedPatternedPage.getEnglishTitle();
 	}

	public Fund [] getFunds(){
		return funds;
	}

	public String getVersionName(){
    	return Utils.getVersionName();
    }

	public String getLastUpdate(){
		return dbHandler.getInfoPagesLastUpdate();
	}

	public char getLetterByNum(int i){
		return (char)(i+65);
	}

	public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

	public boolean isFundFirstLetterChanged(int i){
		if (i>0) return ! funds[i-1].getFullName().substring(0,1).equals(funds[i].getFullName().substring(0,1));
		else return false;
	}
}
