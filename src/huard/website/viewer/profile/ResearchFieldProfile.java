package huard.website.viewer.profile;
import huard.website.model.*;
import huard.website.util.*;

public class ResearchFieldProfile extends Profile {
	private int researchFieldNum;
	private String orderBy;
	private boolean fullList;
	private final String profileName="ResearchField";
	private MultipleOptionsPatternedPage page;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";


    public ResearchFieldProfile(){
    	super();
    	if (orderBy==null) orderBy="subDate";
    	page = dbHandler.getMultipleOptionsPatternedPageByProfileName(profileName);
    }

    public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("MultipleOptionsPatternedPages",101,
				getTitle(true),heb, ip);
	}

    public String getTitle(boolean heb){
    	if (heb) return page.getTitle().substring(0,page.getTitle().indexOf("_"))+" "+getResearchFieldFullName(heb);
    	else return page.getTitle().substring(page.getTitle().indexOf("_")+1)+" "+getResearchFieldFullName(heb);
    }

    public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

	/**
	 * Returns the num.
	 * @return int
	 */
	public int getResearchFieldNum() {
		return researchFieldNum;
	}

	/**
	 * Sets the researchFieldNum.
	 * @param researchFieldNum The researchFieldNum to set
	 */
	public void setResearchFieldNum(int researchFieldNum) {
		this.researchFieldNum = researchFieldNum;
	}


    public TabledInfoPage[] getInfoPagesByResearchField(){
		if (infoPages==null) infoPages = dbHandler.getInfoPagesByResearchField(this.researchFieldNum, fullList, orderBy);
		return infoPages;
	}


    public String getResearchFieldFullName(boolean heb){
    	ResearchField researchField =  dbHandler.getResearchFieldByNum(this.researchFieldNum);
    	return heb ? researchField.getHebrewName() : researchField.getEnglishName();
    }

	public String getLastUpdate(){
		return dbHandler.getResearchFieldLastUpdate(researchFieldNum);
	}


	/**
	 * @return
	 */
	public boolean isFullList() {
		return fullList;
	}

	/**
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param b
	 */
	public void setFullList(boolean b) {
		fullList = b;
	}

	/**
	 * @param string
	 */
	public void setOrderBy(String string) {
		orderBy = string;
	}

	public void setResearchField(String researchField){
		setResearchFieldNum(dbHandler.getResearchFieldNumByResearchFieldName(researchField));
	}

}
