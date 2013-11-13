package huard3.actions;
import huard3.utils.FundsQuery;
import huard3.utils.InfoPagesQuery;
import huard3.utils.InfoPagesUpdate;
import huard3.utils.InternalUseInfoPagesQuery;
import huard3.utils.LogFileHandler;
import huard3.utils.PageHandler;
import huard3.utils.ProtectRecordsInUse;
import huard3.utils.ResearchField;
import huard3.utils.SearchUpdate;
import huard3.utils.Utils;
import huard3.utils.WordsTokenizer;
import huard3.utils.WorkersQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;


public class InfoPageValidator {

	private static final Logger logger = Logger.getLogger(InfoPageValidator.class);
	private InfoPagesUpdate infoPagesUpdate;
	private InfoPagesQuery infoPagesQuery;
	private InfoPage infoPage;
	private Fund fund;
	private String fundShortName;
	//private String pubDate;
	//private String subDate;
	private ResearchField[] allResearchFields;
	private int[] researchFieldsIntArray;
	private boolean allYear;
	private boolean newInfoPage;
	private long secondSubDate;
	private long thirdSubDate;
	private long fourthSubDate;
	//private String stopRollingDate;
	private boolean detailsAreAllright;
	private String blackListWords;

	public InfoPageValidator(){
		infoPagesUpdate = new InfoPagesUpdate();
		infoPagesQuery = new InfoPagesQuery();
		infoPage = new InfoPage();
		researchFieldsIntArray = new int[getAllResearchFieldsOrderdByNum().length];
		for(int i=0;i<=researchFieldsIntArray.length-1;i++) researchFieldsIntArray[i]=0;

	    detailsAreAllright=true;
	    newInfoPage=false;
	    allYear=false;




	}

	public void setArdNum(String ardNum){
		infoPage.setArdNum(Integer.parseInt(ardNum));
	}

	public String getArdNum(){
		return ""+infoPage.getArdNum();
	}


	public void setTitle(String title){
		infoPage.setTitle(title);

	}

	public String getTitle(){
		if (!(infoPage.getTitle().equals("")))return infoPage.getTitle();
		else {
			detailsAreAllright=false;
			return "No Title Was entered";
		}
	}


	public void setPubDate(String pubDate){
		try{
			infoPage.setPubDate(DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(pubDate).getTime());
		}
		catch(ParseException pe){
			infoPage.setPubDate(0);
		}
	}

	public String getPubDate(){
		if (infoPage.getPubDate()!=0 && infoPage.getPubDate()< new Date().getTime()+86400000L){
			return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(infoPage.getPubDate()));
		}
		else {
			detailsAreAllright=false;
			return "No Date/Invalid Date Was Entered";
		}
	}

	public void setSubDate(String subDate){
		try{
			infoPage.setSubDate(DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(subDate).getTime());
		}
		catch(ParseException pe){
			infoPage.setSubDate(0);
		}
	}

	public String getSubDate(){
		if (infoPage.getSubDate()!=0)return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(infoPage.getSubDate()));
		else if (isAllYear()) return "All Year";
		else {
			detailsAreAllright=false;
			return "No Date/Invalid Date Was Entered";
		}
	}


	public void setSecondSubDate(String secondSubDate){
		try{
			this.secondSubDate=DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(secondSubDate).getTime();
			setHasAdditionalSubDates(true);
		}
		catch(ParseException pe){
			this.secondSubDate=0;
		}
	}

	public String getSecondSubDate(){
		if (this.secondSubDate!=0)return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(this.secondSubDate));
		else return "";
	}

	public void setThirdSubDate(String thirdSubDate){
		try{
			this.thirdSubDate=DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(thirdSubDate).getTime();
			setHasAdditionalSubDates(true);
		}
		catch(ParseException pe){
			this.thirdSubDate=0;
		}
	}

	public String getThirdSubDate(){
		if (this.thirdSubDate!=0)return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(this.thirdSubDate));
		else return "";
	}

	public void setFourthSubDate(String fourthSubDate){
		try{
			this.fourthSubDate=DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(fourthSubDate).getTime();
			setHasAdditionalSubDates(true);
		}
		catch(ParseException pe){
			this.fourthSubDate=0;
		}
	}

	public String getFourthSubDate(){
		if (this.fourthSubDate!=0)return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(this.fourthSubDate));
		else return "";
	}

	public String getAdditionalSubDatesString(){
		StringBuffer subDatesSB = new StringBuffer();
		if (secondSubDate !=0) {							//1 year - 45 days a reminder on a repetitive will come at 1 year - 60 days
			if ((secondSubDate - infoPage.getSubDate()) > 27648000000L) {
					detailsAreAllright = false;
					return "You are missusing the Additional Submission Date function. Mark the document as repetitive instead.";
			}
			subDatesSB.append(getSecondSubDate()+", ");
			if (thirdSubDate!=0){
				if (thirdSubDate - secondSubDate > 27648000000L) {
					detailsAreAllright = false;
					return "You are missusing the Additional Submission Date function. Mark the document as repetitive instead.";
				}
				subDatesSB.append(getThirdSubDate()+", ");
				if (fourthSubDate!=0){
					if (fourthSubDate - thirdSubDate > 27648000000L) {
						detailsAreAllright = false;
						return "You are missusing the Additional Submission Date function. Mark the document as repetitive instead.";
					}
					subDatesSB.append(getFourthSubDate()+", ");
				}
			}
		}

		if (subDatesSB.length()>0) return (subDatesSB.delete(subDatesSB.length()-2,subDatesSB.length()).toString());
		else return "No Additional Submission Dates Were Entered, Old Submission dates will be deleted";
	}


	//The set method for fundShortName also initialise fund object and sets to parameters of
	//a infoPage object fundNum and FundShortName.

	public void setFundShortName (String fundShortName){
		this.fundShortName=fundShortName;
		if (this.fund==null) this.fund=new FundsQuery().getFundByShortName(this.fundShortName);
		infoPage.setFundNum(fund.getFundNum());
		infoPage.setFundShortName(fund.getShortName());
	}

	public String getFundShortName(){
		return this.fundShortName;
	}


	public String getFundFullName(){
	    	if (!(this.fund.getFullName().equals(""))){

	        	return this.fund.getFullName();
	        }
	        else{
	        	detailsAreAllright=false;
	        	return "No Fund Was Entered";
	        }
	}

	public int getFundNum(){
		return infoPage.getFundNum();
	}

	public void setFundNum(int fundNum){
		infoPage.setFundNum(fundNum);
	}












	public void setDocType(String docType){
		infoPage.setDocType(docType);
	}

	public String getDocType(){
		if(!(infoPage.getDocType().equals("")))return infoPage.getDocType();
		else{
			detailsAreAllright=false;
			return "No Document Type Was Entered";
		}

	}

	public void setDeskId(String deskId){
		infoPage.setDeskId(deskId);
	}

	public String getDeskId(){
		if(!(infoPage.getDeskId().equals("")))return infoPage.getDeskId();
		else{
			detailsAreAllright=false;
			return "No Desk Id Was Entered";
		}

	}

	public void setDocOwner(String docOwner){
		infoPage.setDocOwner(docOwner);
	}

	public String getDocOwner(){
		if(!(infoPage.getDocOwner().equals("")))return infoPage.getDocOwner();
		else{
			detailsAreAllright=false;
			return "No Document Owner Was Entered";
		}

	}

	public ResearchField[] getAllResearchFieldsOrderdByNum(){
		if (allResearchFields==null) allResearchFields=new InternalUseInfoPagesQuery().getAllResearchFieldsOrderdByNum();
		return allResearchFields;
	}




	public void setResearchFieldInResearchFieldsIntArray(int pos){
		researchFieldsIntArray[pos]=1;
	}

	public int fromResearchFieldsIntArrayToInt(){
		int k=0;
		int l=1;
		final int j=researchFieldsIntArray.length-1;
		for(int i=j;i>=0;i--){
			if (researchFieldsIntArray[i]==1) k=k+1*l;
			l=l*10;
		}
	    return k;
	}

	public void setResearchFields(){
		infoPage.setResearchFields(fromResearchFieldsIntArrayToInt());
	}

	public void setResearchFields(String researchFields){
		infoPage.setResearchFields(Integer.parseInt(researchFields));
	}

	public String getResearchFields(){
		return ""+infoPage.getResearchFields();
	}


	public void setHasTabledVersion(boolean hasTabledVersion){
		infoPage.setHasTabledVersion(true);
	}

	public boolean isHasTabledVersion(){
		return infoPage.isHasTabledVersion();
	}

	public void setHasLocalWebPage (boolean hasLocalWebPage){
		infoPage.setHasLocalWebPage(true);
	}

	public boolean isHasLocalWebPage(){
		return infoPage.isHasLocalWebPage();
	}

	public void setRepetitive(boolean repetitive){
		infoPage.setRepetitive(true);

	}

	public boolean isRepetitive(){
		if (infoPage.isRepetitive()) return true;
		else return false;
	}


	public void setDescriptionOnly(boolean descriptionOnly){
		infoPage.setDescriptionOnly(true);
	}

	public boolean isDescriptionOnly(){
		if (infoPage.isDescriptionOnly()) return true;
		else return false;
	}


	public void setRestricted(boolean restricted){
		infoPage.setRestricted(true);
	}

	public boolean isRestricted(){
		if (infoPage.isRestricted()) return true;
		else return false;
	}

	public void setHasAdditionalSubDates(boolean hasAddtionalSubDates){
		infoPage.setHasAdditionalSubDates(true);
	}
	public boolean isHasAdditionalSubDates(){
		if (infoPage.isHasAdditionalSubDates()) return true;
		else return false;
	}

	public void setPageWebAddress(String pageWebAddress){
		infoPage.setPageWebAddress (pageWebAddress.replaceFirst("http://",""));
	}

	public String getPageWebAddress(){
		if ( ! infoPage.getPageWebAddress().equals("")) return infoPage.getPageWebAddress();
		else {
			detailsAreAllright=false;
			return "No Web Page Address Was Entered";}
	}




	public void setKeywords(String keywords){
		infoPage.setKeywords(keywords);
	}

	public String getKeywords(){
		return Utils.moveHebrewCharsFromAsciiToHebrewCharset(infoPage.getKeywords());
	}


	public String[] getKeywordsArray(){
       if(!(infoPage.getKeywords().equals(""))){
       	WordsTokenizer wt= new WordsTokenizer(",");
		List list = wt.getSubstringsList(infoPage.getKeywords()) ;
		String[] keywords = new String[list.size()];
		for ( int i = 0 ; i < list.size() ; ++i ){
			keywords[i]=Utils.moveHebrewCharsFromAsciiToHebrewCharset(""+list.get( i ));

		}

		return keywords;
       }
       else {
       	detailsAreAllright=false;
       	String[] errStr = new String[1];
       	errStr[0]="No keywords where entered, please enter few";
       	return errStr;
       }
	}

	public void setNewInfoPage(boolean newInfoPage){
		this.newInfoPage=newInfoPage;

	}

	public boolean isNewInfoPage(){
		return this.newInfoPage;
	}

	public void setAllYear(boolean allYear){
		this.allYear=true;
	}

	public boolean isAllYear(){
		return this.allYear;
	}


	public void releaseInfoPage(){
		ProtectRecordsInUse.getProtector().releaseInfoPage(infoPage.getArdNum());
	}

	public InfoPage getInfoPage(){
		return infoPage;
	}

    public boolean isDetailsAreAllright(){
    	return detailsAreAllright;
    }

    public boolean isInfoPageStillHoldedByMe(String username){
    	return ProtectRecordsInUse.getProtector().isInfoPageStillHoldedByMe(infoPage.getArdNum(),username);
    }

    public boolean updateInfoPageDetailsInDatabase(String username){
    	if (newInfoPage) sendMailToAllPeopleWhoseMailAddressIsOnThisInfoPage();
    	long now = new Date().getTime();
    	updateLastUpdateOfResearchFieldsAndDocTypesPages(now);
    	if (! newInfoPage)
    			if ((infoPage.isHasTabledVersion() &&  ! infoPagesQuery.isInfoPageHasTabledVersion(infoPage.getArdNum())) || infoPage.getPubDate()!=infoPagesQuery.getInfoPageDetailsByArdNum(""+infoPage.getArdNum()).getPubDate())
    				sendMailToAllPeopleWhoseMailAddressIsOnThisInfoPage();
    	if (infoPagesUpdate.updateOrInsertInfoPagesDetailsIntoInfoPagesTable(infoPage,newInfoPage)){
    		String action = (newInfoPage ? "created" : "updated" );
    		LogFileHandler.writeToUserLog(action, username, infoPage,"");
    		return true;
    	}
    	else return false;
    }

	public void updateLastUpdateOfResearchFieldsAndDocTypesPages(long now){
		infoPagesUpdate.updateResearchFieldsLastUpdates(infoPage.getResearchFieldsIntArray(), now);
		infoPagesUpdate.updateDocTypesLastUpdates(infoPage.getDocType(), now);
	}





	public void sendMailToAllPeopleWhoseMailAddressIsOnThisInfoPage(){
		StringBuffer to = new StringBuffer();
		logger.info("Desk Id: "+infoPage.getDeskId() );
		Worker [] workers = new WorkersQuery().getAllWorkersByDeskId(this.infoPage.getDeskId());
		logger.info("Workers list size: " + workers.length);
		for (int i=0; i< workers.length; i++){
			String workerTitle = workers[i].getEnglishTitle();
			logger.info("Worker title: "+workerTitle);
			if (workerTitle.indexOf("Coordinator")!=-1 || workerTitle.indexOf("Assistant")!=-1){
				to.append(Utils.getMailAddress(workers[i].getEnglishName())+",");
			}
		}

		if (to.length()>0) to.deleteCharAt(to.length()-1);
		logger.info("To: "+to.toString());
		String budgetOfficer = new FundsQuery().getFundByFundNum(""+this.infoPage.getFundNum()).getBudgetOfficer();
		if (budgetOfficer.length()>0 && ! budgetOfficer.equals("null")) to.append(","+Utils.getMailAddress(budgetOfficer));
		logger.info("To: "+to.toString());
		String from = "webmaster@ard.huji.ac.il";
		String replyTo = "webmaster@ard.huji.ac.il";
		String subject = "A message from ARD webmaster";
		String body = "I would like to inform you your mail address is on the following Call Of Proposal:\n" +
			"http://"+Utils.getServerName()+"/"+Utils.getVersionName()+"/infoPageViewer.jsp?ardNum="+this.infoPage.getArdNum()+" \n" +
			"Contact webmaster@ard.huji.ac.il if any problem with that\n"+
			"******************************************************\n"+
			"The call of proposal will be online in about 1 hour\n" +
			"******************************************************\n";

		//MailCollector.getMailCollector().add(from,to.toString(),"yair@ard.huji.ac.il","",replyTo,subject,body,"");
		//logger.info("The mail was passed to the Mailer");

		to = new StringBuffer(Utils.getWorkersMailAddressesToInformOnNewPages());
		logger.info("To: "+to.toString());
		body = "I would like to inform you of a new Call Of Proposal:\n" +
					"http://"+Utils.getServerName()+"/"+Utils.getVersionName()+"/infoPageViewer.jsp?ardNum="+this.infoPage.getArdNum();
		//MailCollector.getMailCollector().add(from,to.toString(),"yair@ard.huji.ac.il","",replyTo,subject,body,"");
		//logger.info("The mail was passed to the Mailer");
	}


    public boolean insertInfoPageKeywordsIntoDatabase(){
    	return infoPagesUpdate.insertInfoPageKeywordsIntoDatabase(infoPage.getArdNum(),this.getKeywordsArray());
    }

    public boolean insertInfoPageAdditionalSubDatesIntoDatabase(){
    	return infoPagesUpdate.insertInfoPageAdditionalSubDatesIntoDatabase(infoPage.getArdNum(),secondSubDate,thirdSubDate,fourthSubDate);
    }

    public boolean sendHtmlToSite(String username){
        PageHandler pageHandler = new PageHandler();
    	String src = "/home/" + username + "/internet/send.html";
    	String dest = Utils.getPath()+"html/" + infoPage.getArdNum() + ".html";
    	return pageHandler.sendHtmlToSite(src,dest);
    }

	public void insertBlackListWords(){
		if (blackListWords!=null){
			new SearchUpdate().insertBlackListWords(blackListWords);
		}
	}



	/**
	 * @return
	 */
	public String getBlackListWords() {
		return blackListWords;
	}

	/**
	 * @param string
	 */
	public void setBlackListWords(String string) {
		blackListWords = string;
	}

	/**
	 * @return
	 */
	public boolean isKeepInRollingMessages() {
		return infoPage.isKeepInRollingMessages();
	}

	/**
	 * @return
	 */
	public String getStopRollingDate() {
		if (infoPage.isKeepInRollingMessages()){
			if (infoPage.getStopRollingDate()!=0)return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(infoPage.getStopRollingDate()));
			else {
				detailsAreAllright=false;
				return "No Date or Invalid Date Was Entered, a valid 'stop rolling' date must be entered";
			}
		}
		return "";

	}

	/**
	 * @param b
	 */
	public void setKeepInRollingMessages(boolean keepInRollingMessages) {
		infoPage.setKeepInRollingMessages(true);
	}

	/**
	 * @param string
	 */
	public void setStopRollingDate(String stopRollingDate) {
		try{
			infoPage.setStopRollingDate(DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(stopRollingDate).getTime());
		}
		catch(ParseException pe){
			System.out.println(pe);
		}
	}

}

