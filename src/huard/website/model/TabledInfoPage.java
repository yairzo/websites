package huard.website.model;
import huard.website.util.*;
import huard.website.viewer.page.PagesDbHandler;
import huard.website.baseDb.*;
import java.text.DateFormat;
import java.text.ParseException;


import java.util.*;

import org.apache.log4j.Logger;


public class TabledInfoPage extends InfoPage{

	private static final Logger logger = Logger.getLogger(TabledInfoPage.class);

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
	private boolean appendBudgetOfficeLine;

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
		 sendDigitalCopy=false;
		 appendBudgetOfficeLine = false;
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
		appendBudgetOfficeLine = false;
	}
	public String toString(){
		if (this.isDescriptionOnly()) {
			StringBuffer text = new StringBuffer();
			text.append(title+" ");
			text.append(description+" ");
			Fund fund = new PagesDbHandler().getFundByFundNum(""+this.getFundNum());
			text.append(fund.getFullName()+" "+fund.getShortName());
			List<String> keywords = new DbHandler().getInfoPageKeywords(this.ardNum);
			for (String keyword: keywords){
				text.append(" "+keyword);
			}
			return text.toString();
		}
		else {
			StringBuffer text = new StringBuffer();
			if (! this.isHasExpired()) text.append(subDateDetails+" * ");
			text.append(title+" * ");
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
			Fund fund = new PagesDbHandler().getFundByFundNum(""+this.getFundNum());
			text.append(fund.getFullName()+" "+fund.getShortName());
			List<String> keywords = new DbHandler().getInfoPageKeywords(this.ardNum);
			for (String keyword: keywords){
				text.append(" "+keyword);
			}
			return text.toString();
		}
	}

	public boolean isHasExpired() {
		long actualSubDate = subDate;
		if(subSite.equals("ARD") && subDateFund != null){
			try {
				actualSubDate = DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(subDateFund).getTime();
			}
			catch (ParseException pe){
				//logger.info(pe);
			}
		}
		//logger.info(subDate + " "+new Date().getTime()+ " "+(new Date().getTime()-86400000L)+" "+actualSubDate);
		if ((new Date().getTime()-86400000L) > actualSubDate && actualSubDate != 0){
			return true;
		}
		else
			return false;
	}

	public String toContinuousString(){
		if (this.isDescriptionOnly()) {
			StringBuffer text = new StringBuffer();
			text.append(title+" ");
			text.append(description+" ");
			return text.toString();
		}
		else {
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
	}

	public String getSubmissionExpression(boolean heb){
       	if (this.getSubDate()==0) return heb ? "כל השנה" : "All Year" ;
    	else if (this.isHasExpired()){
    		if (this.isRepetitive()){
    			Calendar calendar = new GregorianCalendar();
    			calendar.setTime(new Date(this.getSubDate()));
    			return Utils.getMonthFullName(calendar.get(Calendar.MONTH), heb) ;
    		}
    		else return "Expired";
    	}
    	else {
    		if (subSite.equals("ARD") && subDateFund !=null && !subDateFund.equals("xxxxx") && !subDateFund.equals("null")) return subDateFund;
    		return Utils.getFormatedDate(this.getSubDate());
    	}
    }



	public void markWords(String wordsToMark){
		WordsTokenizer wt = new WordsTokenizer(",");
		List<String> wordsToMarkList = wt.getSubstringsList(wordsToMark);
		// The double loop uniques the list to save computation resources

		for (int i=0; i<wordsToMarkList.size(); i++){
			for (int j=0; j<wordsToMarkList.size(); j++){
				if (i!=j){
					String word1 = (String)wordsToMarkList.get(i);
					String word2 = (String)wordsToMarkList.get(j);
					if (word1.indexOf(word2)!=-1 && word1.indexOf(word2)==0) {
						wordsToMarkList.remove(i);
						i=0;
						j=0;
					}
				}
			}
		}
		for (int i=0; i<wordsToMarkList.size(); i++){
			String word;
			if ((word=(String)wordsToMarkList.get(i)).indexOf("\"")!=-1){
				wordsToMarkList.add(word.replaceAll("\"","&quot;"));
			}
		}
		titleHighlighted = title;
		for (int i=0; i<wordsToMarkList.size(); i++){
			String wordToMark = (String)wordsToMarkList.get(i);
			titleHighlighted = addHtmlTagsBeforeAndAfterWord(titleHighlighted, wordToMark, "<font class=titleHilight>", "</font>");
			subDateDetails = addHtmlTagsBeforeAndAfterWord(subDateDetails, wordToMark, "<font class=hilight>", "</font>");
			deskAndContact = addHtmlTagsBeforeAndAfterWord(deskAndContact, wordToMark, "<font class=hilight>", "</font>");
			forms = addHtmlTagsBeforeAndAfterWord(forms, wordToMark, "<font class=hilight>", "</font>");
			description = addHtmlTagsBeforeAndAfterWord(description, wordToMark, "<font class=hilight>", "</font>");
			maxFundingPeriod = addHtmlTagsBeforeAndAfterWord(maxFundingPeriod, wordToMark, "<font class=hilight>", "</font>");
			amountOfGrant = addHtmlTagsBeforeAndAfterWord(amountOfGrant, wordToMark, "<font class=hilight>", "</font>");
			eligibilityRequirements = addHtmlTagsBeforeAndAfterWord(eligibilityRequirements, wordToMark, "<font class=hilight>", "</font>");
			activityLocation = addHtmlTagsBeforeAndAfterWord(activityLocation, wordToMark, "<font class=hilight>", "</font>");
			possibleCollaboration = addHtmlTagsBeforeAndAfterWord(possibleCollaboration, wordToMark, "<font class=hilight>", "</font>");
			budgetDetails = addHtmlTagsBeforeAndAfterWord(budgetDetails, wordToMark, "<font class=hilight>", "</font>");
			additionalInformation = addHtmlTagsBeforeAndAfterWord(additionalInformation, wordToMark, "<font class=hilight>", "</font>");

		}
	}

	public void markWords(){
		if (foundBySearchWords!=null) {
			WordsTokenizer wt = new WordsTokenizer(",");
			List<String> wordsToMarkList = wt.getSubstringsList(foundBySearchWords);
		// The double loop uniques the list to save computation resources
			for (int i=0; i<wordsToMarkList.size(); i++){
				for (int j=0; j<wordsToMarkList.size(); j++){
					if (i!=j){
						String word1 = (String)wordsToMarkList.get(i);
						String word2 = (String)wordsToMarkList.get(j);
						if (word1.indexOf(word2)!=-1 && word1.indexOf(word2)==0) {
							wordsToMarkList.remove(i);
							i=0;
							j=0;
						}
					}
				}
			}
			for (int i=0; i<wordsToMarkList.size(); i++){
				String word;
				if ((word=(String)wordsToMarkList.get(i)).indexOf("\"")!=-1){
					wordsToMarkList.add(word.replaceAll("\"","&quot;"));
				}
			}
			titleHighlighted = title;
			for (int i=0; i<wordsToMarkList.size(); i++){
				String wordToMark = (String)wordsToMarkList.get(i);
				titleHighlighted = addHtmlTagsBeforeAndAfterWord(titleHighlighted, wordToMark.trim(), "<font class=titleHilight>", "</font>");
				subDateDetails = addHtmlTagsBeforeAndAfterWord(subDateDetails, wordToMark, "<font class=hilight>", "</font>");
				deskAndContact = addHtmlTagsBeforeAndAfterWord(deskAndContact, wordToMark, "<font class=hilight>", "</font>");
				forms = addHtmlTagsBeforeAndAfterWord(forms, wordToMark, "<font class=hilight>", "</font>");
				description = addHtmlTagsBeforeAndAfterWord(description, wordToMark, "<font class=hilight>", "</font>");
				maxFundingPeriod = addHtmlTagsBeforeAndAfterWord(maxFundingPeriod, wordToMark, "<font class=hilight>", "</font>");
				amountOfGrant = addHtmlTagsBeforeAndAfterWord(amountOfGrant, wordToMark, "<font class=hilight>", "</font>");
				eligibilityRequirements = addHtmlTagsBeforeAndAfterWord(eligibilityRequirements, wordToMark, "<font class=hilight>", "</font>");
				activityLocation = addHtmlTagsBeforeAndAfterWord(activityLocation, wordToMark, "<font class=hilight>", "</font>");
				possibleCollaboration = addHtmlTagsBeforeAndAfterWord(possibleCollaboration, wordToMark, "<font class=hilight>", "</font>");
				budgetDetails = addHtmlTagsBeforeAndAfterWord(budgetDetails, wordToMark, "<font class=hilight>", "</font>");
				additionalInformation = addHtmlTagsBeforeAndAfterWord(additionalInformation, wordToMark, "<font class=hilight>", "</font>");
			}
		}
	}


	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){

	    text = wrapCharsWithSpaces(text);
		StringBuffer sb = new StringBuffer();
		int pos;
		wordToMark = wordToMark.trim();

		while (text.indexOf(" "+wordToMark)!=-1||
				text.indexOf("-"+wordToMark)!=-1 ||
				text.indexOf(" "+Utils.firstLetterToUppercase(wordToMark))!=-1 ||
				text.indexOf("*"+wordToMark)!=-1 ||
				text.indexOf("("+wordToMark)!=-1){

			text = text.replaceAll("\\*", " \\* ");
			char c=' ';
			if ((pos=text.indexOf(" "+wordToMark))!=-1) c=' ';
			else if ((pos=text.indexOf("-"+wordToMark))!=-1) c='-';
			else if ((pos=text.indexOf(" "+Utils.firstLetterToUppercase(wordToMark)))!=-1) c=' ';
			else if ((pos=text.indexOf("*"+wordToMark))!=-1) c='*';
			else if ((pos=text.indexOf("("+wordToMark))!=-1) c='(';
			String textBeforeWord = text.substring(0, pos);
			/*logger.info("************************************");
			logger.info("TextBeforeWord: "+textBeforeWord);
			logger.info("************************************");*/
			String textFromWordAndOn = text.substring(pos+1);
			/*logger.info("************************************");
			logger.info("TextFromWordAndOn: "+textFromWordAndOn);
			logger.info("************************************");*/
			String textAfterWord = textFromWordAndOn.substring(wordToMark.length());
			/*logger.info("************************************");
			logger.info("TextAfterWord: "+textAfterWord);
			logger.info("************************************");*/
			String textAfterWordBeforeFirstSpace = textAfterWord.substring(0, (pos = textAfterWord.indexOf(" "))!=-1 ? pos : textAfterWord.length());
			/*logger.info("************************************");
			logger.info("TextAfterWordBeforeFirstSpace: "+textAfterWordBeforeFirstSpace);
			logger.info("************************************");*/
			String textAfterWordAfterFirstSpace = textAfterWord.substring( pos !=-1 ? pos : textAfterWord.length());
			/*logger.info("************************************");
			logger.info("TextAfterWordAfterFirstSpace: "+textAfterWordAfterFirstSpace);
			logger.info("************************************");*/
			textBeforeWord = trimChars(textBeforeWord);
			sb.append(textBeforeWord.replaceAll(" \\* ", "\\*"));
			String stringToAppend = c + tagBeforeWord + wordToMark +
			textAfterWordBeforeFirstSpace + tagAfterWord;
			stringToAppend = stringToAppend.replaceAll(" \\* ", "\\*");
			/*logger.info("************************************");
			logger.info("StringToAppend: "+stringToAppend);
			logger.info("************************************");*/
			sb.append(stringToAppend);
			text = textAfterWordAfterFirstSpace;
			text = text.replaceAll(" \\* ", "\\*");
		}
		text = trimChars(text);
		/*logger.info("************************************");
		logger.info("RestOfTheTextFixed: "+text);
		logger.info("************************************");*/
		sb.append(text);
		return sb.toString();
	}


	public String wrapCharsWithSpaces(String text){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		return text;
	}
	public String trimChars(String text){
		text = text.replaceAll(" ~ ","~");
		text = text.replaceAll(" <","<");
		text = text.replaceAll("> ",">");
		//text = text.trim();
		return text;
	}

	public String minimizeSpaces(String text){
		char [] c = text.toCharArray();
		StringBuffer sb = new StringBuffer("");
		boolean lastWasSpace=false;
		for (int i=0; i<c.length; i++){
			if ((int)c[i]==32) {
				if (! lastWasSpace) sb.append(c[i]);
				lastWasSpace = true;
			}
			else {
				sb.append(c[i]);
				lastWasSpace=false;
			}
		}
		String newText = sb.toString().replaceAll("> ",">").replaceAll(" <","<");
		return newText;

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

	public String getDescriptionNo_13_10() {
		return description.replaceAll((char)13+"","").replaceAll((char)10+"","");
	}

	public String getFormatedDescription(){
		String descriptionText = getFormatedText(getDescriptionNo_13_10());
		descriptionText = Utils.addBackslashBeforeDoubleQuots(descriptionText);
		return descriptionText;
	}

	public String getFormatedText(String text){
		text = text.replaceAll("~","<br>");
		//text = text.replaceAll("<br>","");
		//text = Utils.moveHebrewCharsFromAsciiToHebrewCharset(text);
		/*char [] c= text.toCharArray();
		for (int i=0; i<c.length; i++){
			System.out.print((long)c[i]+" ");
		}*/
		WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
		List elementsList = elementsTokenizer.getSubstringsListNoTrim(text);
		int elementsListSize;
		if ((elementsListSize=elementsList.size())>=3){
			StringBuffer textBuffer = new StringBuffer();
			int i, k = (int)elementsListSize/3;
			for (i = 0; i<k; i++){
				textBuffer.append((String)elementsList.get(i*3));
				String linkText = (String)elementsList.get(i*3+1);
				linkText = linkText.replaceAll("<br>","~");
				String linkTarget = (String)elementsList.get(i*3+2);
				linkTarget = linkTarget.replaceAll("<br>","~");
				if (linkTarget.indexOf("http") != -1 || linkTarget.indexOf("mailto") != -1 || linkTarget.indexOf("ftp") != -1 ) textBuffer.append("<a href=\""+linkTarget+"\" target=\"view_window\">"+
						linkText + "</a>");
				else {
					linkTarget = linkTarget.trim();
					textBuffer.append("<a href=\"ftp://ard.huji.ac.il/pub/" + this.getFundNum()
							+ "/" + linkTarget + "\">" + linkText + "</a>");
				}


			}
			for (int j=i*3; j<elementsListSize; j++) textBuffer.append("&nbsp;"+(String)elementsList.get(j));
				text = textBuffer.toString();

			}
		text = takeOffAllBackslashs(text);
		return text;
	}

	public String takeOffAllBackslashs(String s){
		int pos;
		while ((pos=s.indexOf("\\"))!=-1){
			String s1 = s.substring(0,pos);
			s = s1.concat(s.substring(pos+1));
		}
		return s;
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

	public boolean isHasSubDateFund(){
		return Utils.isHasValue(subDateFund);
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
	public String getDeskAndContact() {
		return deskAndContact;
	}
	public boolean isHasDeskAndContact(){
		return Utils.isHasValue(deskAndContact);
	}
	public void setDeskAndContact(String string) {
		deskAndContact = string;
	}
	public String getForms() {
		return forms;
	}
	public boolean isHasForms(){
		return (Utils.isHasValue(forms) && ! isDescriptionOnly());
	}
	public void setForms(String string) {
		forms = string;
	}
	public String getSubSite() {
		return subSite;
	}
	public void setSubSite(String string) {
		subSite = string;
	}
	public String getSubDateDetails() {
		return subDateDetails;
	}

	public boolean isHasSubDateDetails(){
		return Utils.isHasValue(subDateDetails);
	}
	public void setSubDateDetails(String string) {
		subDateDetails = string;
	}


	public boolean isHasActivityLocation() {
		return Utils.isHasValue( activityLocation);
	}
	public boolean isHasAdditionalInformation() {
		return Utils.isHasValue( additionalInformation);
	}
	public boolean isHasAmountOfGrant() {
		return Utils.isHasValue( amountOfGrant);
	}
	public boolean isHasBudgetDetails() {
		return Utils.isHasValue( budgetDetails);
	}
	public boolean isHasEligibilityRequirements() {
		return Utils.isHasValue( eligibilityRequirements);
	}
	public boolean isHasMaxFundingPeriod() {
		return Utils.isHasValue( maxFundingPeriod);
	}
	public boolean isHasPossibleCollaboration() {
		return Utils.isHasValue( possibleCollaboration);
	}

	public boolean isSendDigitalCopy() {
		return sendDigitalCopy;
	}

	public void setSendDigitalCopy(boolean sendDigitalCopy) {
		this.sendDigitalCopy = sendDigitalCopy;
	}

	public boolean isAppendBudgetOfficeLine() {
		return appendBudgetOfficeLine;
	}

	public void setAppendBudgetOfficeLine(boolean appendBudgetOfficeLine) {
		this.appendBudgetOfficeLine = appendBudgetOfficeLine;
	}







}
