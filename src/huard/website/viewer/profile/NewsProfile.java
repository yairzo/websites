package huard.website.viewer.profile;

import huard.website.model.*;
import huard.website.util.*;




public class NewsProfile extends Profile {

	private final String profileName="News";
	private ComposedPatternedPage composedPatternedPage;
	private ProfilesDbHandler dbHandler;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";

	public NewsProfile(){
		super();
		dbHandler = new ProfilesDbHandler();
		composedPatternedPage = dbHandler.getComposedPatternedPageByProfileName(profileName);
	}

	public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("ComposedPatternedPages",103,getTitle(true),heb, ip);
	}

	public TabledInfoPage[] getNewInfoPages(){
		if (infoPages==null){
			infoPages=dbHandler.getNewInfoPages(Utils.getDaysAsNew());
		}
		return infoPages;
	}

	public TabledInfoPage[] getInfoPagesToRollingMessages(){
		if (infoPages==null){
			infoPages = dbHandler.getInfoPagesToRollingMessages();
		}
		return infoPages;
	}

	public String getTitle(boolean heb){
		if (heb) return composedPatternedPage.getHebrewTitle();
		else return composedPatternedPage.getEnglishTitle();
	}

	public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

	 public String getVersionName(){
	    	return Utils.getVersionName();
	    }

	 public String markApostrofWithBackSlash(String str){
			return str.replaceAll("'","\\\\'");
}





}
