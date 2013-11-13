package huard3.actions;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import huard3.utils.*;



public class InfoPage extends ArdPage {
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
	protected boolean hasForms;
	protected boolean descriptionOnly;
	protected String [] additionalSubDates;








	public String[] getAdditionalSubDates() {
		return additionalSubDates;
	}

	public void setAdditionalSubDates(String[] additionalSubDates) {
		this.additionalSubDates = additionalSubDates;
	}

	public InfoPage(){
		super();
		fundNum=0;
		fundShortName="";
		subDate=0;
		researchFields= 0;
		hasTabledVersion=false;
		docOwner="";
		restricted=false;
	    hasAdditionalSubDates=false;
	    hasLocalWebPage=false;
	   	pageWebAddress="";
		foundBySearchWords="";
		keepInRollingMessages=false;
		stopRollingDate=0;
		hasForms=false;
		descriptionOnly=false;
	}

	public String getSubDateExpression(){
       	if (subDate==0) return "All Year";
    	else if (isHasExpired()){
    		if (repetitive){
    			Calendar calendar = new GregorianCalendar();
    			calendar.setTime(new Date(subDate));
    			return Utils.getMonthFullName(calendar.get(Calendar.MONTH));
    		}
    		else return "Expired";
    	}
    	else return getFormatedDate(subDate);
    }

	public boolean isHasExpired(){
    	if ((subDate+86400000) < new Date().getTime()) return true;
    	else return false;
    }

	public String getFormatedDate(long date){
    	return Utils.getFormatedDate(date);
    }


    public int getFundNum (){
		return fundNum;
	}
	public void setFundNum (int fundNum){
		    this.fundNum=fundNum;
	}
	public long getSubDate(){
		return subDate;
	}
	public void setSubDate(long subDate){
		this.subDate=subDate;
	}
	public int getResearchFields(){
    	return researchFields;
    }
    public void setResearchFields(int researchFields){
    	this.researchFields=researchFields;
    }
    public boolean isHasTabledVersion(){
    	return hasTabledVersion;
    }
    public void setHasTabledVersion(boolean hasTabledVersion){
    	this.hasTabledVersion=hasTabledVersion;
    }
    public String getKeywords(){
    	return keywords;
    }
    public void setKeywords(String keywords){
    	this.keywords=keywords;
    }
    public boolean isRepetitive(){
    	return repetitive;
    }
    public void setRepetitive(boolean repetitive){
     	this.repetitive=repetitive;
    }


    public boolean isHasAdditionalSubDates(){
    	return hasAdditionalSubDates;
    }
    public void setHasAdditionalSubDates(boolean hasAdditionalSubDates){
     	this.hasAdditionalSubDates=hasAdditionalSubDates;
    }






	/**
	 * Returns the fundShortName.
	 * @return String
	 */
	public String getFundShortName() {
		return fundShortName;
	}

	/**
	 * Sets the fundShortName.
	 * @param fundShortName The fundShortName to set
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

	public boolean isNew(){
		return (new Date().getTime()-(Utils.getDaysAsNew()*86400000) > this.pubDate);
	}

	public int[] getResearchFieldsIntArray(){
		int numOfResearchFields= new InfoPagesQuery().getRowsCount("ResearchFields");
		int[] researchFieldsArray = new int[numOfResearchFields];
		int j=researchFields;
		for (int i=numOfResearchFields-1;i>=0;i--){
			if (j%2==1){
				researchFieldsArray[i]=1;
				j=j-1;
			}
			else researchFieldsArray[i]=0;
			j=j/10;
		}
		return researchFieldsArray;
	}

	/**
	 * @return
	 */
	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	public String getFoundBySearchWordsHandled(){
		return foundBySearchWords.replaceAll("&","%26");
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
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
	public boolean isHasForms() {
		return hasForms;
	}
	public void setHasForms(boolean hasForms) {
		this.hasForms = hasForms;
	}

	public boolean isDescriptionOnly() {
		return descriptionOnly;
	}

	public void setDescriptionOnly(boolean descriptionOnly) {
		this.descriptionOnly = descriptionOnly;
	}

}

