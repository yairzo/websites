package huard3.actions;
import huard3.utils.*;
import java.util.*;



public class TabledInfoPage extends InfoPage{

	private String subSite;
	private String subDateArd;
	private String subDateFund;
	private String subDateDetails;
	private String deskAndContact;
	private String forms;
	private String description;
	private String maxFundingPeriod;
	private String amountOfGrant;
	private String eligibilityRequirements;
	private String activityLocation;
	private String possibleCollaboration;
	private String budgetDetails;
	private int numOfCopies;
	private String additionalInformation;
	private boolean sendDigitalCopy;
	private boolean appendBudgetOfficerLine;

	public TabledInfoPage(){
		 super();
		 subSite="";
		 subDateArd="";
		 subDateFund="";
		 subDateDetails="";
		 deskAndContact="";
		 forms="";
		 description="";
		 maxFundingPeriod="";
		 amountOfGrant="";
		 eligibilityRequirements="";
		 activityLocation="";
		 possibleCollaboration="";
		 budgetDetails="";
		 numOfCopies=0;
		 additionalInformation="";
		 sendDigitalCopy = false;
		 appendBudgetOfficerLine = true;
	}

	public TabledInfoPage(InfoPage infoPage){
		ardNum=infoPage.getArdNum();
		title=infoPage.getTitle();
		pubDate=infoPage.getPubDate();
		fundNum=infoPage.getFundNum();
		fundShortName=infoPage.getFundShortName();
		subDate=infoPage.getSubDate();
		docType=infoPage.getDocType();
		researchFields=infoPage.getResearchFields();
		hasTabledVersion=infoPage.isHasTabledVersion();
		docOwner=infoPage.getDocOwner();
		restricted=infoPage.isRestricted();
		hasAdditionalSubDates=infoPage.isHasAdditionalSubDates();
		deskId=infoPage.getDeskId();
		docOwner=infoPage.getDocOwner();
		keywords=infoPage.getKeywords();
		hasLocalWebPage = infoPage.isHasLocalWebPage();
		pageWebAddress = infoPage.getPageWebAddress();
		subSite="";
		subDateArd="";
		subDateFund="";
		subDateDetails="";
		deskAndContact="";
		forms="";
		description="";
		maxFundingPeriod="";
		amountOfGrant="";
		eligibilityRequirements="";
		activityLocation="";
		possibleCollaboration="";
		budgetDetails="";
		numOfCopies=0;
		additionalInformation="";
		sendDigitalCopy = false;
		appendBudgetOfficerLine = true;
	}
	public String toString(){
		StringBuffer text = new StringBuffer();
		text.append(subDateDetails+" * ");
		text.append(deskAndContact+" * ");
		text.append(forms+" * ");
		text.append(description+" * ");
		text.append(maxFundingPeriod+" * ");
		text.append(amountOfGrant+" * ");
		text.append(eligibilityRequirements+" * ");
		text.append(activityLocation+" * ");
		text.append(possibleCollaboration+" * ");
		text.append(budgetDetails+" * ");
		text.append(additionalInformation+" * ");
		return text.toString();
	}

	public String toContinuousString(){
		StringBuffer text = new StringBuffer();
		text.append(title+" ");
		text.append(subDateDetails+" ");
		text.append(deskAndContact+" ");
		text.append(forms+" ");
		text.append(description+" ");
		text.append(maxFundingPeriod+" ");
		text.append(amountOfGrant+" ");
		text.append(eligibilityRequirements+" ");
		text.append(activityLocation+" ");
		text.append(possibleCollaboration+" ");
		text.append(budgetDetails+" ");
		text.append(additionalInformation+" ");
		return text.toString();
	}

	public void markWords(String wordsToMark){
		WordsTokenizer wt = new WordsTokenizer(",");
		List wordsToMarkList = wt.getSubstringsList(wordsToMark);
		// The double loop uniques the list to save computation resources
		for (int i=0; i<wordsToMarkList.size(); i++){
			for (int j=i+1; j<wordsToMarkList.size(); j++){
				if (wordsToMarkList.get(i).equals(wordsToMarkList.get(j))) wordsToMarkList.remove(j);
			}
		}

		for (int i=0; i<wordsToMarkList.size(); i++){
			String wordToMark = (String)wordsToMarkList.get(i);
			title = addHtmlTagsBeforeAndAfterWord(title, wordToMark, "<font class=titleHilight>", "</font>");
			subDateDetails = addHtmlTagsBeforeAndAfterWord(subDateDetails, wordToMark, "<font class=hilight>", "</font>");
			deskAndContact = addHtmlTagsBeforeAndAfterWord(deskAndContact, wordToMark, "<font class=hilight>", "</font>");
			forms = addHtmlTagsBeforeAndAfterWord(forms, wordToMark, "<font class=hilight>", "</font>");
			description = addHtmlTagsBeforeAndAfterWord(description, wordToMark, "<font class=hilight>", "</font>");
			maxFundingPeriod = addHtmlTagsBeforeAndAfterWord(maxFundingPeriod, wordToMark, "<font class=hilight>", "</font>");
			amountOfGrant = addHtmlTagsBeforeAndAfterWord(amountOfGrant, wordToMark, "<font class=hilight>", "</font>");
			eligibilityRequirements = addHtmlTagsBeforeAndAfterWord(eligibilityRequirements, wordToMark, "<font class=hilight>", "</font>");
			addHtmlTagsBeforeAndAfterWord(activityLocation, wordToMark, "<font class=hilight>", "</font>");
			possibleCollaboration = addHtmlTagsBeforeAndAfterWord(possibleCollaboration, wordToMark, "<font class=hilight>", "</font>");
			budgetDetails = addHtmlTagsBeforeAndAfterWord(budgetDetails, wordToMark, "<font class=hilight>", "</font>");
			additionalInformation = addHtmlTagsBeforeAndAfterWord(additionalInformation, wordToMark, "<font class=hilight>", "</font>");

		}
	}
	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
			String textBeforeWord = text.substring(0,pos);
			String textFromWordAndOn = text.substring(pos+1);
			int spaceAfterWordPos = textFromWordAndOn.indexOf(" ");
			sb.append(textBeforeWord);
			sb.append(" "+tagBeforeWord + textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()) + tagAfterWord+" ");
			text = spaceAfterWordPos!=-1 ? " "+textFromWordAndOn.substring(spaceAfterWordPos) : "";
		}
		sb.append(text);
		return sb.toString();
	}


	public String cleanHtmlTags(String text){
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos=text.indexOf("<"))!=-1){
			sb.append(text.substring(0,pos));
			text = text.substring(text.indexOf(">")+1);
		}
		sb.append(text);
		return sb.toString();
	}
	public String getTitleWithoutHtmlTags(){
		return cleanHtmlTags(this.title);
	}
	/**
	 * @return
	 */
	public String getActivityLocation() {
		return activityLocation;
	}

	/**
	 * @return
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @return
	 */
	public String getAmountOfGrant() {
		return amountOfGrant;
	}

	/**
	 * @return
	 */
	public String getBudgetDetails() {
		return budgetDetails;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public String getEligibilityRequirements() {
		return eligibilityRequirements;
	}

	/**
	 * @return
	 */
	public String getMaxFundingPeriod() {
		return maxFundingPeriod;
	}

	/**
	 * @return
	 */
	public int getNumOfCopies() {
		return numOfCopies;
	}

	/**
	 * @return
	 */
	public String getPossibleCollaboration() {
		return possibleCollaboration;
	}

	/**
	 * @return
	 */
	public String getSubDateArd() {
		return subDateArd;
	}

	/**
	 * @return
	 */
	public String getSubDateFund() {
		return subDateFund;
	}



	/**
	 * @param string
	 */
	public void setActivityLocation(String string) {
		activityLocation = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalInformation(String string) {
		additionalInformation = string;
	}

	/**
	 * @param string
	 */
	public void setAmountOfGrant(String string) {
		amountOfGrant = string;
	}

	/**
	 * @param string
	 */
	public void setBudgetDetails(String string) {
		budgetDetails = string;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param string
	 */
	public void setEligibilityRequirements(String string) {
		eligibilityRequirements = string;
	}

	/**
	 * @param string
	 */
	public void setMaxFundingPeriod(String string) {
		maxFundingPeriod = string;
	}

	/**
	 * @param i
	 */
	public void setNumOfCopies(int i) {
		numOfCopies = i;
	}

	/**
	 * @param string
	 */
	public void setPossibleCollaboration(String string) {
		possibleCollaboration = string;
	}

	/**
	 * @param string
	 */
	public void setSubDateArd(String string) {
		subDateArd = string;
	}

	/**
	 * @param string
	 */
	public void setSubDateFund(String string) {
		subDateFund = string;
	}




	/**
	 * @return
	 */
	public String getDeskAndContact() {
		return deskAndContact;
	}

	/**
	 * @param string
	 */
	public void setDeskAndContact(String string) {
		deskAndContact = string;
	}

	/**
	 * @return
	 */
	public String getForms() {
		return forms;
	}

	/**
	 * @param string
	 */
	public void setForms(String string) {
		forms = string;
	}

	/**
	 * @return
	 */
	public String getSubSite() {
		return subSite;
	}



	/**
	 * @param string
	 */
	public void setSubSite(String string) {
		subSite = string;
	}

	/**
	 * @return
	 */
	public String getSubDateDetails() {
		return subDateDetails;
	}

	/**
	 * @param string
	 */
	public void setSubDateDetails(String string) {
		subDateDetails = string;
	}

	public boolean isSendDigitalCopy() {
		return sendDigitalCopy;
	}

	public void setSendDigitalCopy(boolean sendDigitalCopy) {
		this.sendDigitalCopy = sendDigitalCopy;
	}

	public boolean isAppendBudgetOfficerLine() {
		return appendBudgetOfficerLine;
	}

	public void setAppendBudgetOfficerLine(boolean appendBudgetOfficerLine) {
		this.appendBudgetOfficerLine = appendBudgetOfficerLine;
	}




}
