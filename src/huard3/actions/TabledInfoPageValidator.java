package huard3.actions;
import huard3.utils.*;
import java.util.*;


public class TabledInfoPageValidator {
	private TabledInfoPage tabledInfoPage;
	private InfoPagesUpdate infoPagesUpdate;
	private String ardNum;
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
	private String numOfCopies;
	private String additionalInformation;
	private boolean sendDigitalCopy;
	private boolean appendBudgetOfficerLine;


	public boolean isAppendBudgetOfficerLine() {
		return appendBudgetOfficerLine;
	}


	public void setAppendBudgetOfficerLine(boolean appendBudgetOfficerLine) {
		this.appendBudgetOfficerLine = appendBudgetOfficerLine;
	}


	public boolean isSendDigitalCopy() {
		return sendDigitalCopy;
	}


	public void setSendDigitalCopy(boolean sendDigitalCopy) {
		this.sendDigitalCopy = sendDigitalCopy;
	}


	public TabledInfoPageValidator(){
		infoPagesUpdate = new InfoPagesUpdate();
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
	public String getArdNum() {
		return ardNum;
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
	public String getDeskAndContact() {
		return deskAndContact;
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
	public String getForms() {
		return forms;
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
	public String getNumOfCopies() {
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
	public void setArdNum(String string) {
		ardNum = string;
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
	public void setDeskAndContact(String string) {
		deskAndContact = string;
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
	public void setForms(String string) {
		forms = string;
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
	public void setNumOfCopies(String string) {
		numOfCopies = string;
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

    public TabledInfoPage getTabledInfoPage(){
    	if (tabledInfoPage==null){
    		InfoPage infoPage = new InfoPagesQuery().getInfoPageDetailsByArdNum(ardNum);
    		tabledInfoPage = new TabledInfoPage(infoPage);
    		tabledInfoPage.setSubSite(subSite);
    		tabledInfoPage.setSubDateArd(subDateArd);
			tabledInfoPage.setSubDateFund(subDateFund);
			tabledInfoPage.setSubDateDetails(editText(subDateDetails));
    		tabledInfoPage.setDeskAndContact(editText(deskAndContact));
			tabledInfoPage.setForms(editText(forms));

			tabledInfoPage.setDescription(editText(description));

			tabledInfoPage.setMaxFundingPeriod(editText(maxFundingPeriod));
			tabledInfoPage.setAmountOfGrant(editText(amountOfGrant));
			tabledInfoPage.setEligibilityRequirements(editText(eligibilityRequirements));
			tabledInfoPage.setActivityLocation(editText(activityLocation));
			tabledInfoPage.setPossibleCollaboration(editText(possibleCollaboration));
			tabledInfoPage.setBudgetDetails(editText(budgetDetails));
			tabledInfoPage.setNumOfCopies(Integer.parseInt(numOfCopies));
			tabledInfoPage.setAdditionalInformation(editText(additionalInformation));
			tabledInfoPage.setSendDigitalCopy(sendDigitalCopy);
			tabledInfoPage.setAppendBudgetOfficerLine(appendBudgetOfficerLine);
    	}
    	return tabledInfoPage;
    }

    public boolean updateTabledInfoPageDetailsInTabledInfoPagesTable(TabledInfoPage tabledInfoPage){
		long now = new Date().getTime();
		updateLastUpdateOfResearchFieldsAndDocTypesPages(now);
    	return infoPagesUpdate.updateOrInsertTabledInfoPagesDetailsIntoTabledInfoPagesTable(tabledInfoPage);
    }

	public void updateLastUpdateOfResearchFieldsAndDocTypesPages(long now){
			infoPagesUpdate.updateResearchFieldsLastUpdates(getTabledInfoPage().getResearchFieldsIntArray(), now);
			infoPagesUpdate.updateDocTypesLastUpdates(getTabledInfoPage().getDocType(), now);
		}

	public boolean isInfoPageStillHoldedByMe(String username){
			return ProtectRecordsInUse.getProtector().isInfoPageStillHoldedByMe(getTabledInfoPage().getArdNum(),username);
	}








    	/* Replaces " with &quot;
    	 * replaces \n with <br>
    	 */


	public String editText(String text){


		if (text!=null){
			text = text.replaceAll("\"","&quot;");
			text = text.replaceAll("#","");
			if (Utils.isHebrew(text)) text = handleHebrewTags(text);
			text = Utils.moveHebrewCharsFromAsciiToHebrewCharset(text);
		}
		return text;
	}

	public String handleHebrewTags(String text){
			char [] charArray = text.toCharArray();
			boolean insideTag = false;
			StringBuffer fixedTagsText = new StringBuffer();
			for (int i=0; i< charArray.length; i++){
				if (charArray[i]=='<') insideTag = true;
				if (charArray[i]=='>') insideTag = false;
				if (insideTag==true){
					if ((int)charArray[i]==1499) charArray[i]='h';
					else if ((int)charArray[i]==1508) charArray[i]='p';
					else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1513) {
						charArray[i]='b';
						charArray[i+1]='r';
					}
					else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1512) {
						charArray[i]='u';
						charArray[i+1]='l';

					}
					else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1502) {
						charArray[i]='o';
						charArray[i+1]='l';

					}
					else if ((int)charArray[i]==1513 && (int)charArray[i+1]==1493) {
						charArray[i]='l';
						charArray[i+1]='i';

					}
					else if ((int)charArray[i]==1491) {
						charArray[i]='b';
					}
					else if ((int)charArray[i]==1511) {
						charArray[i]='u';
					}
				}
				fixedTagsText.append(charArray[i]);
			}
			return fixedTagsText.toString();
		}

	public String getFormatedText(String text){
		text = text.replaceAll("~","<br>");
		WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
		List elementsList = elementsTokenizer.getSubstringsListNoTrim(text);
		int elementsListSize;
		if ((elementsListSize=elementsList.size())>=3){
			StringBuffer textBuffer = new StringBuffer();
			int i;
			for (i = 0; i<(int)elementsListSize/3 ; i++){
				textBuffer.append((String)elementsList.get(i*3));
				String linkText = (String)elementsList.get(i*3+1);
				linkText = linkText.replaceAll("<br>","~");
				String linkTarget = (String)elementsList.get(i*3+2);
				linkTarget = linkTarget.replaceAll("<br>","~");
				if (linkTarget.indexOf("http") != -1) textBuffer.append("<a href=\""+linkTarget+"\" target=\"view_window\">"+
						linkText + "</a>");
				else if (linkTarget.indexOf("/") == -1) {
					 linkTarget = linkTarget.trim();
					 textBuffer.append("<a href=\"ftp://ard.huji.ac.il/pub/" + tabledInfoPage.getFundNum()
							+ "/" + linkTarget + "\">" + linkText + "</a>");
				}
				else textBuffer.append("<a href=\""+linkTarget+"\" >"+ linkText + "</a>");
			}

			for (int j=i*3; j<elementsListSize; j++) textBuffer.append("&nbsp;"+(String)elementsList.get(j));
			text = textBuffer.toString();
		}

		return Utils.moveHebrewCharsFromAsciiToHebrewCharset(text);
	}


    public String getFormatedDate(long date){
    	return Utils.getFormatedDate(date);
    }



	public String getSubSite() {
		return subSite;
	}


	public void setSubSite(String string) {
		subSite = string;
	}

	/*public String getFormatedForms(){
			String forms;
			StringBuffer formatedForms = new StringBuffer("");
			if ((forms = getTabledInfoPage().getForms())!= null && forms.indexOf('*') != -1){

				WordsTokenizer linesTokenizer = new WordsTokenizer("~");
				List linesList = linesTokenizer.getSubstringsListNoTrim(forms);
				for (int i=0; i<linesList.size(); i++){
					String line = (String)linesList.get(i);
					WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
					List elementsList = elementsTokenizer.getSubstringsListNoTrim(line);
					if (elementsList.size()==4){
						formatedForms.append((String)elementsList.get(0)+" ");
						String linkTarget = (String)elementsList.get(2);
						linkTarget = linkTarget.trim();
						if (linkTarget.indexOf("://") != -1) formatedForms.append("<a href=\""+linkTarget+"\">"+elementsList.get(1)+"</a>");
						else formatedForms.append("<a href=\"ftp://"+Utils.getServerName()+"/pub/"+tabledInfoPage.getFundNum()+"/"+linkTarget+"\" target=\"view_window\" >"+elementsList.get(1)+"</a>");
						formatedForms.append(" "+(String)elementsList.get(3)+"<br>");
					}
					else formatedForms.append(line);
				}
			}
			else formatedForms.append(getFormatedText(forms));
			return formatedForms.toString();
		}*/

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

	public boolean isHebrew(){
		return Utils.isHebrew(getTabledInfoPage().getTitle());
	}

}
