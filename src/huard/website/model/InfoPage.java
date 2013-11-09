package huard.website.model;

import huard.website.baseDb.DbHandler;
import huard.website.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class InfoPage extends ArdPage {
	//private static final Logger logger = Logger.getLogger(InfoPage.class);

	protected int fundNum;

	protected String fundShortName;

	protected long subDate;

	protected int researchFields;

	protected boolean hasTabledVersion;

	protected boolean repetitive;

	protected boolean hasAdditionalSubDates;

	protected boolean hasLocalWebPage;

	protected String pageWebAddress;

	protected String foundBySearchWords;

	protected boolean keepInRollingMessages;

	protected long stopRollingDate;

	// protected boolean hasForms;
	protected boolean descriptionOnly;

	protected long[] additionalSubDates;

	protected String hebrewDocType;

	protected boolean deleted;

	//protected boolean hasForms;

	protected DbHandler dbHandler;

	public InfoPage() {
		super();
		fundNum = 0;
		fundShortName = "";
		subDate = 0;
		researchFields = 0;
		hasTabledVersion = false;
		docOwner = "";
		restricted = false;
		hasAdditionalSubDates = false;
		hasLocalWebPage = false;
		pageWebAddress = "";
		foundBySearchWords = "";
		keepInRollingMessages = false;
		stopRollingDate = 0;
		// hasForms=false;
		descriptionOnly = false;
		//hasForms = false;
		deleted = false;
		dbHandler = new DbHandler();
	}

	public String [] getAllSubmissionDatesExceptCurrent(String categoryColor){
		List<String> subDatesList = new ArrayList<String>();
		long [] allSubDates = getAllSubDates();
		for (int i=0; i<allSubDates.length; i++){
			if (allSubDates[i]!=0 && allSubDates[i]<subDate) subDatesList.add("<font color="+categoryColor+">"+Utils.getFormatedDate(allSubDates[i])+"</font>");
			else if (allSubDates[i]>subDate) subDatesList.add(Utils.getFormatedDate(allSubDates[i]));
		}
		String [] allSubDatesExceptCurrent = new String[subDatesList.size()];
		for (int i=0; i<allSubDatesExceptCurrent.length; i++){
			allSubDatesExceptCurrent[i] = (String) subDatesList.get(i);
		}
		return allSubDatesExceptCurrent;

	}
	public long [] getAllSubDates(){
		long [] allSubDates = new long[4];
		allSubDates [0] = dbHandler.getTabledInfoPageDetailsByArdNum(""+ardNum).getSubDate();
		System.arraycopy(getAdditionalSubDates(),0,allSubDates,1,getAdditionalSubDates().length);
		return allSubDates;
	}


	public long[] getAdditionalSubDates() {
		if (hasAdditionalSubDates && this.additionalSubDates == null) {
			this.additionalSubDates = dbHandler.getAdditionalSubDates(""
					+ ardNum);
		}
		return this.additionalSubDates;
	}


	public void setAdditionalSubDates(long[] additionalSubDates) {
		this.additionalSubDates = additionalSubDates;
	}

	public String getSubDateExpression(boolean heb) {
		if (subDate == 0)
			return heb ? "כל השנה" : "All Year";
		else if (isHasExpired()) {
			if (repetitive) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date(subDate));
				return Utils.getMonthFullName(calendar.get(Calendar.MONTH),heb);
			} else
				return heb ? "פג תוקף" : "Expired";
		} else
			return getFormatedDate(subDate);
	}

	public boolean isHasExpired() {
		if (subDate < new Date().getTime()+86400000L && subDate != 0)
			return true;
		else
			return false;
	}

	public String getFormatedDate(long date) {
		return Utils.getFormatedDate(date);
	}

	public String getFormatedSubDate(boolean heb) {
		return Utils.getFormatedDate(this.subDate, heb);
	}

	public String getFormatedSubDate() {
		return Utils.getFormatedDate(this.subDate, this.isHebrew());
	}


	public String getFormatedPubDate() {
		return Utils.getFormatedDate(this.pubDate);
	}

	public int getFundNum() {
		return fundNum;
	}

	public void setFundNum(int fundNum) {
		this.fundNum = fundNum;
	}

	public long getSubDate() {
		return subDate;
	}

	public void setSubDate(long subDate) {
		this.subDate = subDate;
	}

	public int getResearchFields() {
		return researchFields;
	}

	public void setResearchFields(int researchFields) {
		this.researchFields = researchFields;
	}

	public boolean isHasTabledVersion() {
		return hasTabledVersion;
	}

	public void setHasTabledVersion(boolean hasTabledVersion) {
		this.hasTabledVersion = hasTabledVersion;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isRepetitive() {
		return repetitive;
	}

	public void setRepetitive(boolean repetitive) {
		this.repetitive = repetitive;
	}

	public boolean isHasAdditionalSubDates() {
		return hasAdditionalSubDates;
	}

	public void setHasAdditionalSubDates(boolean hasAdditionalSubDates) {
		this.hasAdditionalSubDates = hasAdditionalSubDates;
	}

	/**
	 * Returns the fundShortName.
	 *
	 * @return String
	 */
	public String getFundShortName() {
		return fundShortName;
	}

	/**
	 * Sets the fundShortName.
	 *
	 * @param fundShortName
	 *            The fundShortName to set
	 */
	public void setFundShortName(String fundShortName) {
		this.fundShortName = fundShortName;
	}

	public boolean isHasLocalWebPage() {
		return hasLocalWebPage;
	}

	public void setHasLocalWebPage(boolean b) {
		hasLocalWebPage = b;
	}

	public String getPageWebAddress() {
		return pageWebAddress;
	}

	public void setPageWebAddress(String string) {
		pageWebAddress = string;
	}

	public boolean isNew() {
		return (new Date().getTime() - (Utils.getDaysAsNew() * 86400000) > this.pubDate);
	}

	public int[] getResearchFieldsIntArray() {
		int numOfResearchFields = new DbHandler().getRowsCount("HUARD",
				"ResearchFields");
		int[] researchFieldsArray = new int[numOfResearchFields];
		int j = researchFields;
		for (int i = numOfResearchFields - 1; i >= 0; i--) {
			if (j % 2 == 1) {
				researchFieldsArray[i] = 1;
				j = j - 1;
			} else
				researchFieldsArray[i] = 0;
			j = j / 10;
		}
		return researchFieldsArray;
	}

	/**
	 * @return
	 */
	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	public String getFoundBySearchWordsHandled() {
		//System.out.println("huard1.contents.InfoPage.getFoundBySearchWordHandled(): foundBySearchWords: "+foundBySearchWords);
		String foundBySearchWordHandled = foundBySearchWords.replaceAll("&","%26");
		foundBySearchWordHandled = foundBySearchWords.replaceAll("\"","%22");
		return foundBySearchWordHandled;
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
		//if (string.indexOf("\"")!=-1) foundBySearchWords = foundBySearchWords.concat(","+string.replaceAll("\"","&quot;"));
	}

	/**
	 * @return
	 */
	public boolean isKeepInRollingMessages() {
		return keepInRollingMessages;
	}

	/**
	 * @return
	 */
	public long getStopRollingDate() {
		return stopRollingDate;
	}

	/**
	 * @param b
	 */
	public void setKeepInRollingMessages(boolean b) {
		keepInRollingMessages = b;
	}

	/**
	 * @param l
	 */
	public void setStopRollingDate(long l) {
		stopRollingDate = l;
	}

	/*
	 * public boolean isHasForms() { return hasForms; } public void
	 * setHasForms(boolean hasForms) { this.hasForms = hasForms; }
	 */
	public boolean isDescriptionOnly() {
		return descriptionOnly;
	}

	public void setDescriptionOnly(boolean descriptionOnly) {
		this.descriptionOnly = descriptionOnly;
	}

	public String getHebrewDocType() {
		if (hebrewDocType == null)
			hebrewDocType = dbHandler.getHebrewDocTypeByEnglishDocType(docType);
		return hebrewDocType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/*public boolean isHasForms() {
		return hasForms;
	}

	public void setHasForms(boolean hasForms) {
		this.hasForms = hasForms;
	}*/

}
