package huard3.actions;
import huard3.utils.*;
import java.util.*;

public class Fund {
	private int fundNum;
	private String shortName;
	private String fullName;
	private int ksfNum;
	private String parentFund;
//	private boolean hasForms;
	private String webAddress;
	private String phoneNum;
	private String fax;
	private String contact;
	private String mailAddress;
	private String keywords;
	private String docOwner;
	private String deskId;
	private String budgetOfficer;
	private String financialReporter;
	//private boolean hasWebsite;
	private String html;
	private long pubDate;
	private boolean putAnchor;
	private boolean restricted;
	private boolean hasLocalPage;
	private boolean hasAliveInfoPages;
	private InfoPage [] infoPages;
	
	public InfoPage[] getInfoPages() {
		return infoPages;
	}

	public void setInfoPages(InfoPage[] infoPages) {
		this.infoPages = infoPages;
	}

	public boolean isHasLocalPage() {
		return hasLocalPage;
	}

	public void setHasLocalPage(boolean hasLocalPage) {
		this.hasLocalPage = hasLocalPage;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public Fund(){
		fundNum=0;
		shortName="";
		fullName="";
		ksfNum=0;
		parentFund="";
//		hasForms=false;
//		webAddress="";
		phoneNum="";;
		fax="";;
		contact="";
		mailAddress="";
		docOwner="";;
		deskId="";
		keywords="";
		html="";
		pubDate=0;
		hasAliveInfoPages=false;
	}
	
	public void markText (String foundBySearchWords, boolean addAnchor){
		WordsTokenizer wt = new WordsTokenizer(",");
		List list = wt.getSubstringsList(foundBySearchWords);
		for (int i=0; i<list.size(); i++){
			String wordToMark = (String)list.get(i);
			String newShortName = addHtmlTagsBeforeAndAfterWord( shortName, wordToMark, "<font class=hilight>", "</font>");
			if ( ! newShortName.equals(shortName)) {
				shortName = newShortName;
				setPutAnchor(true);
			}
			String newFullName = addHtmlTagsBeforeAndAfterWord( fullName, wordToMark, "<font class=hilight>", "</font>");
			if ( ! newFullName.equals(fullName)) {
				fullName = newFullName;
				setPutAnchor(true);
			}				
		}
	}
	
	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		StringBuffer sb = new StringBuffer();
		if (text.indexOf(wordToMark)!=-1){
			text = " "+text;
			text = text.replaceAll("~"," ~ ");
			text = text.replaceAll("<"," <");
			text = text.replaceAll(">","> ");
			int pos;
			while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
				String textBeforeWord = text.substring(0,pos);
				String textFromWordAndOn = text.substring(pos+1);
				int spaceAfterWordPos = textFromWordAndOn.indexOf(" ");
				sb.append(textBeforeWord);
				sb.append(" "+tagBeforeWord + textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()) + tagAfterWord+" ");
				text = spaceAfterWordPos!=-1 ? " "+textFromWordAndOn.substring(spaceAfterWordPos) : "";
			}
			sb.delete(0,1);
		}
		sb.append(text);
		return sb.toString();
	}
	
	public int getFundNum(){
		return fundNum;
	}
	
	public void setFundNum(int fundNum){
		this.fundNum=fundNum;
	}
	
	public String getShortName(){
		return shortName;
	}
	
	public void setShortName(String shortName){
		this.shortName=shortName;
	}
	
	public String getFullName(){
		return fullName;
	}
	
	public void setFullName(String fullName){
		this.fullName=fullName;
	}
	
	public int getKsfNum(){
		return this.ksfNum;
	}
	
	public void setKsfNum(int ksfNum){
		this.ksfNum=ksfNum;
	}
	
	public String getParentFund(){
		return this.parentFund;
	}
	
	public void setParentFund(String parentFund){
		this.parentFund=parentFund;
	}
	
/*	public boolean isHasForms(){
		return hasForms;
	}
	
	public void setHasForms(boolean hasForms){
		this.hasForms=hasForms;
	}
*/
		
	public String getWebAddress(){
		if (webAddress !=null){
		if (webAddress.indexOf("www")!=-1) return webAddress.substring(webAddress.indexOf("www"),webAddress.length());
		}
		return webAddress;
	}
	
	public void setWebAddress(String webAddress){
		this.webAddress=webAddress;
	}
	
		
	public String getPhoneNum(){
		return phoneNum;
	}
	
	public void setPhoneNum(String phoneNum){
		this.phoneNum=phoneNum;
	}	
		
    public String getFax(){
		return fax;
	}
	
	public void setFax(String fax){
		this.fax=fax;
	}

    public String getContact(){
		return contact;
	}
	
	public void setContact(String contact){
		this.contact=contact;
	}

    public String getMailAddress(){
		return mailAddress;
	}
	
	public void setMailAddress(String mailAddress){
		this.mailAddress=mailAddress;
	}
	
	public String getDocOwner(){
    	return docOwner;
    }
    public void setDocOwner(String docOwner){
    	this.docOwner=docOwner;
    }
    
    public String getKeywords(){
    	return keywords;
    }
    
    public void setKeywords(String keywords){
    	this.keywords=keywords;
    }
    
	
	public String getDeskId(){
		return deskId;
	}
	public void setDeskId(String deskId){
		this.deskId=deskId;
	}
	
	/**
	 * @return
	 */
	public String getBudgetOfficer() {
		return budgetOfficer;
	}

	/**
	 * @param string
	 */
	public void setBudgetOfficer(String string) {
		budgetOfficer = string;
	}

	/**
	 * @return
	 */
	public String getFinancialReporter() {
		return financialReporter;
	}

	/**
	 * @param string
	 */
	public void setFinancialReporter(String string) {
		financialReporter = string;
	}

	/**
	 * @return
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param string
	 */
	public void setHtml(String string) {
		html = string;
	}

	
/*	public boolean isHasWebsite() {
		return hasWebsite;
	}

	
	public void setHasWebsite(boolean b) {
		hasWebsite = b;
	}
*/
	/**
	 * @return
	 */
	public boolean isPutAnchor() {
		return putAnchor;
	}

	/**
	 * @param b
	 */
	public void setPutAnchor(boolean b) {
		putAnchor = b;
	}

	public boolean isHasAliveInfoPages() {
		return hasAliveInfoPages;
	}

	public void setHasAliveInfoPages(boolean hasAliveInfoPages) {
		this.hasAliveInfoPages = hasAliveInfoPages;
	}

	public long getPubDate() {
		return pubDate;
	}

	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
	}

}

