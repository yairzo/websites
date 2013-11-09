package huard.website.viewer.profile;
import huard.website.model.*;
import huard.website.util.*;



public class MessagesProfile extends Profile {
	private final String profileName="Messages";
	private String docType;
	private PubPage [] pubPages;
	private ProfilesDbHandler dbHandler;
	private ComposedPatternedPage composedPatternedPage;
	private final String [] categoryTableNameEng = {"financeSources","RD_authority"};
	private final String [] categoryTableNameHeb = {"efsharuiot_mimun","harashut_lemop"};
	

	public MessagesProfile(){
		super();
		dbHandler = new ProfilesDbHandler();
	}

	public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("ComposedPatternedPages",0,getTitle(true),heb, ip);
	}

/*	public PubPage[] getMessages(){
		if (pubPages==null) pubPages = dbHandler.getMessagesByDocType(docType);
		return pubPages;
	}
*/

	public String getPlusImageName(){
		if (docType.equals("Funding")) return "plus.gif";
		else if (docType.equals("Ard")) return "b_plus.gif";
		return "";
	}

	public String getMinusImageName(){
		if (docType.equals("Funding")) return "minus.gif";
		else if (docType.equals("Ard")) return "b_minus.gif";
		return "";
	}


	public String getStyleClass(){
		if (docType.equals("Funding")) return "funding";
		else if (docType.equals("Ard")) return "authority";
		return "";
	}

	public String getTitle(boolean heb){
		if (composedPatternedPage==null) composedPatternedPage = dbHandler.getComposedPatternedPageByProfileNameAndDocType(profileName, docType);
		if (heb) return composedPatternedPage.getHebrewTitle();
		else return composedPatternedPage.getEnglishTitle();
	}

	public String getCategory(boolean heb){
		int i = docType.equals("Funding") ? 0 : 1;
		return heb ? categoryTableNameHeb[i] : categoryTableNameEng[i];
	}

	public PubPage[] getNewMessages(){
		if (pubPages==null) pubPages = dbHandler.getNewMessagesByDocType(Utils.getDaysAsNew(),docType);
		return pubPages;
	}

	public PubPage[] getMessages(){
		if (pubPages==null) pubPages = dbHandler.getMessagesByDocType(Utils.getDaysAsNew(),docType);
		return pubPages;
	}

	public PubPage[] getTopPriorityMessage(){
		if (pubPages == null)
			pubPages = dbHandler.getTopPriorityMessage();
		return pubPages;
	}

	public String getLastUpdate (){
		return dbHandler.getMessagesLastUpdate(docType);
	}




	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param string
	 */
	public void setDocType(String string) {
		docType = string;
	}

	public PubPage [] getToRollingMessages(){
		if (pubPages==null) pubPages = dbHandler.getToRollingMessages();
				return pubPages;
	}

	public boolean isHebrew(String str){
		return ProfilesDbHandler.isHebrew(str);
	}

	 public String getVersionName(){
	    	return Utils.getVersionName();
	    }
}
