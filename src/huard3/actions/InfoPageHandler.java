package huard3.actions;
import huard3.utils.*;

import java.text.DateFormat;
import java.util.*;

public class InfoPageHandler{

	protected InternalUseInfoPagesQuery infoPagesQuery;
	protected InfoPage infoPage;
	protected String ardNum;
	protected String [] fundsShortNames;
	protected String [] allDocTypes;
	protected String [] allDeskIds;
	protected String [] allDocOwners;
	protected ResearchField [] allResearchFields;
	protected boolean newInfoPage;
	protected long [] additionalSubDates;
	protected double suggestedKeywordsThreshold;
	protected final int MAX_ADDITIONAL_SUB_DATES=3;




	public InfoPageHandler(){
		infoPagesQuery = new InternalUseInfoPagesQuery();
		ardNum = null ;
		newInfoPage=false;
		if (suggestedKeywordsThreshold==0) this.suggestedKeywordsThreshold=0.001;

	}

	public void setArdNum(String ardNum){
		this.ardNum=ardNum;
	}

	public String getArdNum(){
		return ardNum;
	}




	public InfoPage getInfoPageByArdNum(){
		if (infoPage==null)
		{
			if ( ardNum == null)
			{
				infoPage = new InfoPage() ;
				infoPage.setArdNum(infoPagesQuery.getFirstFreeArdNum("InfoPages"));
				this.ardNum=""+infoPage.getArdNum();
				this.newInfoPage=true;
				infoPage.setDraft(true);

			}
			else
			{
				infoPage = infoPagesQuery.getInfoPageDetailsByArdNum(this.ardNum);
			}
		}
		return infoPage;
	}

	public int copyInfoPage (String username){
		int newArdNum = infoPagesQuery.getFirstFreeArdNum("InfoPages");
		new InfoPagesUpdate().copyInfoPage(Integer.parseInt(this.ardNum), newArdNum);
		getInfoPageByArdNum();
		infoPage.setArdNum(newArdNum);
		infoPage.setTitle(infoPage.getTitle().concat(" - copy"));
		LogFileHandler.writeToUserLog("created", username, infoPage,"");
		return newArdNum;
	}

	public InfoPage getInfoPageByArdNum(String table){
		if (infoPage==null) infoPage = infoPagesQuery.getInfoPageDetailsByArdNum(this.ardNum);
		return infoPage;
	}

	public String getFormatedPubDate(){
		return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(getInfoPageByArdNum().getPubDate()));
	}

	public String getFormatedSubDate(){
		if (getInfoPageByArdNum().getSubDate()!=0) return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(getInfoPageByArdNum().getSubDate()));
		else return "";
	}

	public String getFormatedStopRollingDate(){
		if (getInfoPageByArdNum().getStopRollingDate()!=0) return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(getInfoPageByArdNum().getStopRollingDate()));
		else return "";
	}

	public String[] getAllFundsShortNames(){
		if (fundsShortNames==null) fundsShortNames=new FundsQuery().getFundsShortNamesSortedByShortName() ;
		return fundsShortNames;
	}

	public String getFundShortName(){
		try
		{
			return infoPage.getFundShortName();
		}
		catch( Exception e )
		{
			return "" ;
		}
	}






	public String[] getAllDocTypes(){
		if (allDocTypes==null) allDocTypes= infoPagesQuery.getAllDocTypes();
		return allDocTypes;
	}

	public String[] getAllDeskIds(){
		if (allDeskIds==null) allDeskIds= infoPagesQuery.getAllDeskIds();
		return allDeskIds;
	}

	public String[] getAllDocOwners(){
		if (allDocOwners==null) allDocOwners = infoPagesQuery.getAllUsersFirstLetterUpperCased();
		return allDocOwners;
	}

	public ResearchField[] getAllResearchFieldsOrderdByNum(){
		if (allResearchFields==null) allResearchFields=infoPagesQuery.getAllResearchFieldsOrderdByNum();
		return allResearchFields;
	}


	public int[] getResearchFieldsIntArray(){
		int numOfResearchFields=infoPagesQuery.getRowsCount("ResearchFields");
		int[] researchFields = new int[numOfResearchFields];
		int j=infoPage.getResearchFields();
		for (int i=numOfResearchFields-1;i>=0;i--){

			if (j%2==1){
				researchFields[i]=1;
				j=j-1;
			}
			else researchFields[i]=0;
			j=j/10;
		}
		return researchFields;
	}



	public String[] getFormatedAdditionalSubDates(){
		if (additionalSubDates==null){
			additionalSubDates=infoPagesQuery.getAdditionalSubDates(ardNum);
		}
		String [] formatedAdditionalSubDates = new String[MAX_ADDITIONAL_SUB_DATES];
		for (int i=0; i<formatedAdditionalSubDates.length; i++){
			if (i<additionalSubDates.length) formatedAdditionalSubDates[i] = DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(additionalSubDates[i]));
			else formatedAdditionalSubDates[i]="";

		}
		return formatedAdditionalSubDates;
	}


	public String getSuggestedKeywords(){
		StringBuffer suggestedWords = new StringBuffer();
		if (infoPage.isHasTabledVersion()){
			TabledInfoPage tabledInfoPage = infoPagesQuery.getTabledInfoPageDetailsByArdNum(""+infoPage.getArdNum());
			String text = tabledInfoPage.getTitle() +" "+ tabledInfoPage.getDescription();
			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList = stripAndLowerCaseWordsList(wordsList);
			List uniqueWordsList = uniqueWordsList(wordsList);

			for (int i=0; i<uniqueWordsList.size(); i++){
				String word = (String)uniqueWordsList.get(i);
				if (word.length()>=3 && isTextWord(word)){
					double occuranceFrequency = new SearchQuery().getOccuranceFrequency(handleHebrew(word),"ArdPagesWordsOccurances");
					if (occuranceFrequency>0 && occuranceFrequency < this.suggestedKeywordsThreshold) suggestedWords.append(word+" ");
				}
			}
		}
		return suggestedWords.toString();
	}

	public List uniqueWordsList(List wordsList){
		for (int i=0; i<wordsList.size()-1; i++){
			for(int j=i+1; j<wordsList.size(); j++){
				if ( ((String)wordsList.get(i)).equals((String)wordsList.get(j))) wordsList.remove(j);
			}
		}
		return wordsList;
	}

	public List<String> stripAndLowerCaseWordsList(List<String> wordsList){
		for (int i=0; i<wordsList.size(); i++){
			wordsList.set(i,stripAndLowerCase(wordsList.get(i)));
		}
		return wordsList;
	}

	public String stripAndLowerCase(String word){
		word = word.toLowerCase();
		word = word.replace(':',' ');
		word = word.replace(';',' ');
		word = word.replace('~',' ');
		word = word.replace(',',' ');
		word = word.replace('.',' ');
		word = word.replace('(',' ');
		word = word.replace(')',' ');
		return word.trim();
	}

	public String handleHebrew(String word){
		return Utils.checkForHebrewCharsAndChangeThemToAscii(word);
	}

	public boolean isTextWord(String word){
		if (word.indexOf('<')!=-1 || word.indexOf('>')!=-1) return false;
		else return true;
	}

	public boolean isHasRecordInTabledInfoPagesTable(){
		return infoPagesQuery.isRecoredExistsInTable(ardNum, "TabledInfoPages");
	}




	public boolean isNewInfoPage(){
		return newInfoPage;
	}

	public void setInfoPageAsBusy(String username){
		ProtectRecordsInUse.getProtector().setInfoPageAsBusy(Integer.parseInt(this.ardNum),username);
	}

	public boolean isInfoPageBusy(){
		if (ardNum!=null) return ProtectRecordsInUse.getProtector().isInfoPageBusy(Integer.parseInt(this.ardNum));
		else return false;
	}

	public boolean isInfoPageStillHoldedByMe(String username){
    	if (ardNum!=null) return ProtectRecordsInUse.getProtector().isInfoPageStillHoldedByMe(Integer.parseInt(this.ardNum),username);
    	else return false;
    }

    public String getDeskId(){
    	return infoPage.getDeskId();
    }
    public String getVersionName(){
    	return Utils.getVersionName();
    }

	/**
	 * @return
	 */
	public double getSuggestedKeywordsThreshold() {
		return suggestedKeywordsThreshold;
	}

	/**
	 * @param d
	 */
	public void setSuggestedKeywordsThreshold(double d) {
		suggestedKeywordsThreshold = d;
	}

	public void setNewInfoPage(boolean newInfoPage) {
		this.newInfoPage = newInfoPage;
	}

}

