package huard.website.viewer.profile;
import huard.website.model.*;
import huard.website.util.*;


public class DocTypeProfile extends Profile {

	private String docType;
	private String orderBy;
	private boolean fullList;
	private int from;
	private final String profileName="DocType";
	private MultipleOptionsPatternedPage page;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";

	public DocTypeProfile(){
		super();
		if (orderBy==null) orderBy = "subDate";
		page = dbHandler.getMultipleOptionsPatternedPageByProfileName(profileName);
	}

	public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("MultipleOptionsPatternedPages",100,getTitle(true),heb, ip);
	}

    public String getTitle(boolean heb){
    	if (heb) return page.getTitle().substring(0,page.getTitle().indexOf("_"))+getDocType(heb);
    	else return page.getTitle().substring(page.getTitle().indexOf("_")+1)+" "+getDocType(heb)+"s";
    }

	public String getDocType(boolean heb){
		if (heb) return dbHandler.getHebrewDocType(this.docType);
		else return docType;
	}	

	public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

    /**
	 * Returns the docTypeName.
	 * @return String
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * Sets the docTypeName.
	 * @param docTypeName The docTypeName to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public TabledInfoPage[] getInfoPagesByDocType(){
		if (infoPages==null) infoPages=dbHandler.getInfoPagesByDocType(this.docType, fullList, orderBy, from);
		return infoPages;
	}

	public String getLastUpdate(){
		return dbHandler.getDocTypeLastUpdate(docType);
	}


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

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

}
