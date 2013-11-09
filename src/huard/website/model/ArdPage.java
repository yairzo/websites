package huard.website.model;
import huard.website.util.*;

import java.util.*;


public class  ArdPage {
	protected int ardNum;
	protected String title;
	protected String titleHighlighted;
	protected String docType;
	protected long pubDate;
	protected boolean restricted;
	protected String docOwner;
	protected String deskId;
	protected String keywords;
	protected String referenceFile;
	protected String source;
	protected String internalUseDescription;
	protected String foundBySearchWords;

	public ArdPage (){
		ardNum=0;
		title="";
		titleHighlighted="";
		docType="";
		Date date=new Date();
		pubDate=date.getTime();
		restricted=false;
		docOwner="";
		deskId="";
		keywords="";
		referenceFile="";
		source="";
		internalUseDescription="";
	}

	public boolean isHebrew(){
		return Utils.isHebrew(title);
	}

	/*public void markText(){

	}*/




	/**
	 * Returns the ardNum.
	 * @return int
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * Returns the deskId.
	 * @return String
	 */
	public String getDeskId() {
		return deskId;
	}

	/**
	 * Returns the docOwner.
	 * @return String
	 */
	public String getDocOwner() {
		return docOwner;
	}

	/**
	 * Returns the restricted.
	 * @return boolean
	 */
	public boolean isRestricted() {
		return restricted;
	}

	/**
	 * Returns the title.
	 * @return String
	 */
	public String getTitle() {
		return title.replace('~','-');
	}



	public String getTitleQuotsBackslashed(){
		return title.replaceAll("\"","\\\\\"").replace('~','-');
	}

	/**
	 * Sets the ardNum.
	 * @param ardNum The ardNum to set
	 */
	public void setArdNum(int ardNum) {
		this.ardNum = ardNum;
	}

	/**
	 * Sets the deskId.
	 * @param deskId The deskId to set
	 */
	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	/**
	 * Sets the docOwner.
	 * @param docOwner The docOwner to set
	 */
	public void setDocOwner(String docOwner) {
		this.docOwner = docOwner;
	}

	/**
	 * Sets the restricted.
	 * @param restricted The restricted to set
	 */
	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	/**
	 * Sets the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the keywords.
	 * @return String
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords.
	 * @param keywords The keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return
	 */
	public long getPubDate() {
		return pubDate;
	}

	/**
	 * @param l
	 */
	public void setPubDate(long l) {
		pubDate = l;
	}

	/**
	 * @return
	 */
	public String getReferenceFile() {
		return referenceFile;
	}

	/**
	 * @param string
	 */
	public void setReferenceFile(String string) {
		referenceFile = string;
	}

	/**
	 * @return
	 */
	public String getInternalUseDescription() {
		return internalUseDescription;
	}

	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param string
	 */
	public void setInternalUseDescription(String string) {
		internalUseDescription = string;
	}

	/**
	 * @param string
	 */
	public void setSource(String string) {
		source = string;
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

	/**
	 * @return
	 */
	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
	}

	public String getTitleHighlighted() {
		return titleHighlighted;
	}

	public void setTitleHighlighted(String titleHighlighted) {
		this.titleHighlighted = titleHighlighted;
	}

	public String getTitleHighlightedQuotsBackslashed(){
		return titleHighlighted.replaceAll("\"","\\\\\"").replace('~','-');
	}

}
