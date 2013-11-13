package huard3.utils;

import huard3.actions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;






public class InfoPagesQuery extends PageQuery{

	public InfoPagesQuery(){
		super();
	}

	public long[] getAdditionalSubDates(String ardNum){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
     		ResultSet resultSet = statement.executeQuery("SELECT * FROM AdditionalSubDates WHERE ardNum="+ardNum+" ORDER BY subDate;");
     		List additionalSubDatesList = new ArrayList();
     		while(resultSet.next()){
     			Long additionalSubDate = new Long(resultSet.getLong("subDate"));
     			additionalSubDatesList.add(additionalSubDate);
     		}
     		long [] additionalSubDates = new long[additionalSubDatesList.size()];
     		for (int i=0; i<additionalSubDatesList.size(); i++){
     			additionalSubDates[i] = ((Long)additionalSubDatesList.get(i)).longValue();
     		}
     		return additionalSubDates;

   		}

   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		long[] noAdditionalSubDates = new long[3];
    		return noAdditionalSubDates;
      	}
   	}

   	public ResearchField[] getAllResearchFieldsOrderdByNum(){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
  	        String researchFieldsQuery = "SELECT * FROM ResearchFields ORDER BY num;";
  	        ResultSet resultSet = statement.executeQuery(researchFieldsQuery);
  	        ResearchField[] researchFields = new ResearchField[getRowsCount("ResearchFields")];
  	        int i=0;
  	        while(resultSet.next()){
  	        	researchFields[i] = new ResearchField();
  	        	researchFields[i].setNum(resultSet.getInt("num"));
  	        	researchFields[i].setResearchFieldName(resultSet.getString("researchField"));
  	        	researchFields[i].setResearchFieldShort(resultSet.getString("researchFieldShort"));
  	        	researchFields[i].setExperimental(resultSet.getBoolean("experimental"));
  	        	i++;
  	        }
  	        return researchFields;
   		}
   		catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		ResearchField[] errRF = new ResearchField[0];
    		return errRF;
        }

   	}



   	public InfoPage[] moveResultSetAndKeywordsToInfoPage(ResultSet resultSet){
   		InfoPage [] infoPages = moveFromResultSetToInfoPage(resultSet);
   		for (int i=0; i<infoPages.length; i++) infoPages[i].setKeywords(getInfoPagesKeywordsByArdNum(""+infoPages[i].getArdNum()));
   		return infoPages;
   	}


   	public InfoPage[] moveFromResultSetToInfoPage(ResultSet resultSet){
   		try{
   			List<InfoPage> infoPagesList = new ArrayList<InfoPage>();
   	    	InfoPage infoPage;
     		while (resultSet.next()){
     			infoPage = new InfoPage();
     	    	infoPage.setArdNum(resultSet.getInt("ardNum"));
                infoPage.setTitle(resultSet.getString("title"));
                infoPage.setPubDate(resultSet.getLong("pubDate"));
                infoPage.setFundNum(resultSet.getInt("fundNum"));
                infoPage.setFundShortName(resultSet.getString("fundShortName"));
                infoPage.setSubDate(resultSet.getLong("subDate"));
                infoPage.setDocType(resultSet.getString("docType"));
                infoPage.setDeskId(resultSet.getString("deskId"));
                infoPage.setResearchFields(resultSet.getInt("researchFields"));
                infoPage.setHasTabledVersion(resultSet.getBoolean("hasTabledVersion"));
                infoPage.setRepetitive(resultSet.getBoolean("repetitive"));
                infoPage.setDocOwner(resultSet.getString("docOwner"));
                infoPage.setRestricted(resultSet.getBoolean("restricted"));
                infoPage.setHasAdditionalSubDates(resultSet.getBoolean("hasAdditionalSubDates"));
				infoPage.setHasLocalWebPage(resultSet.getBoolean("hasLocalWebPage"));
				infoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
				infoPage.setDescriptionOnly(resultSet.getBoolean("descriptionOnly"));
				infoPage.setKeepInRollingMessages(resultSet.getBoolean("keepInRollingMessages"));
				infoPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
				infoPage.setDeleted(resultSet.getBoolean("isDeleted"));
				infoPage.setDraft(resultSet.getBoolean("isDraft"));
      			infoPagesList.add(infoPage);
     		}

     		if (infoPagesList.size()>0){
     			InfoPage[] infoPagesArray = new InfoPage[infoPagesList.size()];
            	for (int i=0;i<infoPagesList.size();i++){
            		infoPagesArray[i]=(InfoPage)infoPagesList.get(i);
            	}
            	return infoPagesArray;
     		}
     		else {
     			InfoPage[] noInfoPages = new InfoPage[1];
     			noInfoPages[0] = new InfoPage();
     			noInfoPages[0].setTitle("No results");
     			return noInfoPages;
     		}



    	}
   	    catch (SQLException e){

   	    	System.out.println("InfoPagesQUery.moveFromResultSetToInfoPages(): "+e);
   	    	InfoPage[] noInfoPages = new InfoPage[0];
     		//noInfoPages[0] = new InfoPage();
     		//noInfoPages[0].setTitle("No results");
     		return noInfoPages;
   	    }

   	}


    public List moveBaseParmsAndFoundBySearchWordsFromResultSetToInfoPage(ResultSet resultSet){
   		try{
   			List infoPagesList = new ArrayList();
   	    	InfoPage infoPage;
     		while (resultSet.next()){
     			infoPage = new InfoPage();
     	    	infoPage.setArdNum(resultSet.getInt("ardNum"));
                infoPage.setTitle(resultSet.getString("title"));
                infoPage.setSubDate(resultSet.getLong("subDate"));
                infoPage.setDocType(resultSet.getString("docType"));
			    infoPage.setRepetitive(resultSet.getBoolean("repetitive"));
                infoPage.setHasAdditionalSubDates(resultSet.getBoolean("hasAdditionalSubDates"));
				infoPage.setHasLocalWebPage(resultSet.getBoolean("hasLocalWebPage"));
				infoPage.setHasTabledVersion(resultSet.getBoolean("hasTabledVersion"));
				infoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
				infoPage.setKeepInRollingMessages(resultSet.getBoolean("keepInRollingMessages"));
				infoPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
				//check the case where word1 equals word2
				infoPage.setFoundBySearchWords(resultSet.getString("word1")+","+resultSet.getString("word2"));
       			infoPagesList.add(infoPage);

     		}

     		return infoPagesList;



    	}
   	    catch (SQLException e){
   	    	System.out.println(e);
   	    	List infoPagesList = new ArrayList();
     		InfoPage infoPage = new InfoPage();
     		infoPagesList.add(infoPage);
     		return infoPagesList;
   	    }
    }



    public String getInfoPagesKeywordsByArdNum(String ardNum){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPagesKeywords WHERE ardNum=\""+ardNum+"\";");
        	StringBuffer keywordsSB = new StringBuffer();
         	while(resultSet.next()){
        		 keywordsSB.append(resultSet.getString("keyword")+",");
        	}
        	if (keywordsSB.length()>0) keywordsSB.delete(keywordsSB.length()-1,keywordsSB.length());
        	return keywordsSB.toString();
        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return "No Keywords";
    	}
    }





   	public InfoPage getInfoPageDetailsByArdNum(String ardNum){
   		 try{
        	Statement statement = getCurrentConnection().createStatement();
  	        String mainQuery = "SELECT * FROM InfoPages WHERE ardNum=\""+ardNum+"\"";
   	        ResultSet resultSet = statement.executeQuery(mainQuery);
   	        return moveResultSetAndKeywordsToInfoPage(resultSet)[0];

   	        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		InfoPage [] infoPages = new InfoPage [1];
    		infoPages[0] = new InfoPage();
    		return infoPages[0];
        }


   	}

   	public InfoPage getInfoPageDetailsByArdNumSkipKeywords(String ardNum, String table){
   		 try{
        	Statement statement = getCurrentConnection().createStatement();
  	        String mainQuery = "SELECT * FROM "+table+" WHERE ardNum=\""+ardNum+"\"";
   	        ResultSet resultSet = statement.executeQuery(mainQuery);
   	        return moveFromResultSetToInfoPage(resultSet)[0];

   	        }
        catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		InfoPage [] infoPages = new InfoPage [1];
    		infoPages[0] = new InfoPage();
    		return infoPages[0];
        }


   	}
   	public ResearchField getResearchFieldByNum(int num){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
            String researchFieldQuery="SELECT * FROM ResearchFields WHERE num=\""+num+"\"";
     		ResultSet resultSet = statement.executeQuery(researchFieldQuery);
     		resultSet.next();
     		ResearchField researchField = new ResearchField();
			researchField.setNum(num);
     		researchField.setResearchFieldName(resultSet.getString("researchField"));
     		researchField.setResearchFieldShort(resultSet.getString("researchFieldShort"));
     		researchField.setExperimental(resultSet.getBoolean("experimental"));
     		return researchField;

    	}
    	catch (SQLException e){
    		System.out.println("Exception while querying data: " + e);
    	    return new ResearchField();
    	}
    }

    public InfoPage [] getInfoPages() {
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    	    String query = "SELECT * FROM InfoPages;";
    	    ResultSet resultSet = statement.executeQuery(query);
    	    return moveFromResultSetToInfoPage(resultSet);
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		InfoPage [] infoPages = new InfoPage [0];
    		infoPages[0]=new InfoPage();
    		return infoPages;
    	}
    }
    public TabledInfoPage [] getTabledInfoPages(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String query = "SELECT * FROM TabledInfoPages;";
			ResultSet resultSet = statement.executeQuery(query);
			return moveTabledDataResultSetToTabledInfoPages(resultSet);
			}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			TabledInfoPage [] tabledInfoPages = new TabledInfoPage [1];
			return tabledInfoPages;
		}
	}

	public TabledInfoPage [] getAliveTabledInfoPages(){
			try{
				Statement statement = getCurrentConnection().createStatement();
				String query = "SELECT * FROM TabledInfoPages LEFT JOIN InfoPages ON TabledInfoPages.ardNum=InfoPages.ardNum WHERE TabledInfoPages.ardNum<10000;";
				ResultSet resultSet = statement.executeQuery(query);
				return moveTabledDataResultSetToTabledInfoPages(resultSet);
				}
			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				TabledInfoPage [] tabledInfoPages = new TabledInfoPage [1];
				return tabledInfoPages;
			}
		}

    public String [] getAllArdNums(){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
    	    String query = "SELECT ardNum FROM InfoPages";
    	    ResultSet resultSet = statement.executeQuery(query);
    	    List ardNumsList = new ArrayList();
    	    while (resultSet.next()){
    	    	ardNumsList.add(resultSet.getString("ardNum"));
    	    }
    	    String [] ardNums = new String [ardNumsList.size()];
    	    for (int i=0; i< ardNums.length; i++){
    	    	ardNums[i]= (String)ardNumsList.get(i);
    	    }
    	    return ardNums;
    	}
    	catch (SQLException e){
    		System.out.println(e);
    		return new String[1];
    	}
    }

	public TabledInfoPage getTabledInfoPageDetailsByArdNum(String ardNum){
			try{
				Statement statement = getCurrentConnection().createStatement();
				String mainQuery = "SELECT * FROM TabledInfoPages LEFT JOIN InfoPages ON TabledInfoPages.ardNum = InfoPages.ardNum WHERE TabledInfoPages.ardNum=\""+ardNum+"\"";
				ResultSet resultSet = statement.executeQuery(mainQuery);
				return moveResultSetToTabledInfoPages(resultSet)[0];
			}
			catch(SQLException e){
				System.out.println("InfoPagesQuery.getTabledInfoPageDetailsByArdNum(String ardNum): " + e);
				TabledInfoPage  tabledInfoPage = new TabledInfoPage();
				return tabledInfoPage;
			}
		}

		public TabledInfoPage [] moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
				List tabledInfoPagesList = new ArrayList();
				while (resultSet.next()){
					TabledInfoPage tabledInfoPage = new TabledInfoPage();
					tabledInfoPage.setArdNum(resultSet.getInt("ardNum"));
					tabledInfoPage.setTitle(resultSet.getString("title"));
					tabledInfoPage.setPubDate(resultSet.getLong("pubDate"));
					tabledInfoPage.setFundNum(resultSet.getInt("fundNum"));
					tabledInfoPage.setFundShortName(resultSet.getString("fundShortName"));
					tabledInfoPage.setSubDate(resultSet.getLong("subDate"));

					tabledInfoPage.setDocType(resultSet.getString("docType"));
					tabledInfoPage.setDeskId(resultSet.getString("deskId"));
					tabledInfoPage.setResearchFields(resultSet.getInt("researchFields"));
					tabledInfoPage.setHasTabledVersion(resultSet.getBoolean("hasTabledVersion"));
					tabledInfoPage.setRepetitive(resultSet.getBoolean("repetitive"));
					tabledInfoPage.setDocOwner(resultSet.getString("docOwner"));
					tabledInfoPage.setRestricted(resultSet.getBoolean("restricted"));
					tabledInfoPage.setHasAdditionalSubDates(resultSet.getBoolean("hasAdditionalSubDates"));
					tabledInfoPage.setHasLocalWebPage(resultSet.getBoolean("hasLocalWebPage"));
					tabledInfoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
					tabledInfoPage.setDescriptionOnly(resultSet.getBoolean("descriptionOnly"));
					tabledInfoPage.setKeepInRollingMessages(resultSet.getBoolean("keepInRollingMessages"));
					tabledInfoPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));

					tabledInfoPage.setKeywords(getInfoPagesKeywordsByArdNum(""+tabledInfoPage.getArdNum()));
					tabledInfoPage.setSubSite(resultSet.getString("subSite"));
					tabledInfoPage.setSubDateArd(resultSet.getString("subDateArd"));
					tabledInfoPage.setSubDateFund(resultSet.getString("subDateFund"));
					tabledInfoPage.setSubDateDetails(resultSet.getString("subDateDetails"));
					tabledInfoPage.setDeskAndContact(resultSet.getString("deskAndContact"));
					tabledInfoPage.setForms(resultSet.getString("forms"));
					tabledInfoPage.setDescription(resultSet.getString("description"));
					tabledInfoPage.setMaxFundingPeriod(resultSet.getString("maxFundingPeriod"));
					tabledInfoPage.setAmountOfGrant(resultSet.getString("amountOfGrant"));
					tabledInfoPage.setEligibilityRequirements(resultSet.getString("eligibilityRequirements"));
					tabledInfoPage.setActivityLocation(resultSet.getString("activityLocation"));
					tabledInfoPage.setPossibleCollaboration(resultSet.getString("possibleCollaboration"));
					tabledInfoPage.setBudgetDetails(resultSet.getString("budgetDetails"));
					tabledInfoPage.setNumOfCopies(resultSet.getInt("numOfCopies"));
					tabledInfoPage.setAdditionalInformation(resultSet.getString("additionalInformation"));
					tabledInfoPage.setSendDigitalCopy(resultSet.getBoolean("sendDigitalCopy"));
					tabledInfoPage.setAppendBudgetOfficerLine(resultSet.getBoolean("appendBudgetOfficerLine"));
					tabledInfoPagesList.add(tabledInfoPage);
				}

				TabledInfoPage [] tabledInfoPagesArray = new TabledInfoPage [tabledInfoPagesList.size()];
				for (int i=0; i<tabledInfoPagesArray.length; i++){
					tabledInfoPagesArray[i] = (TabledInfoPage)tabledInfoPagesList.get(i);
				}
				return tabledInfoPagesArray;

			/*}
			catch(SQLException e){
				System.out.println(e);
				TabledInfoPage [] tabledInfoPages = new TabledInfoPage [1];
				return tabledInfoPages;
			}*/

		}


	public TabledInfoPage [] moveTabledDataResultSetToTabledInfoPages(ResultSet resultSet){
		try{
			List tabledInfoPagesList = new ArrayList();
			while (resultSet.next()){
				TabledInfoPage tabledInfoPage = new TabledInfoPage();
				tabledInfoPage.setArdNum(resultSet.getInt("ardNum"));
				tabledInfoPage.setTitle(resultSet.getString("title"));
				tabledInfoPage.setDeskId(resultSet.getString("deskId"));
				tabledInfoPage.setFundNum(resultSet.getInt("fundNum"));
				tabledInfoPage.setSubSite(resultSet.getString("subSite"));
				tabledInfoPage.setSubDateArd(resultSet.getString("subDateArd"));
				tabledInfoPage.setSubDateFund(resultSet.getString("subDateFund"));
				tabledInfoPage.setSubDateDetails(resultSet.getString("subDateDetails"));
				tabledInfoPage.setDeskAndContact(resultSet.getString("deskAndContact"));
				tabledInfoPage.setForms(resultSet.getString("forms"));
				tabledInfoPage.setDescription(resultSet.getString("description"));
				tabledInfoPage.setMaxFundingPeriod(resultSet.getString("maxFundingPeriod"));
				tabledInfoPage.setAmountOfGrant(resultSet.getString("amountOfGrant"));
				tabledInfoPage.setEligibilityRequirements(resultSet.getString("eligibilityRequirements"));
				tabledInfoPage.setActivityLocation(resultSet.getString("activityLocation"));
				tabledInfoPage.setPossibleCollaboration(resultSet.getString("possibleCollaboration"));
				tabledInfoPage.setBudgetDetails(resultSet.getString("budgetDetails"));
				tabledInfoPage.setNumOfCopies(resultSet.getInt("numOfCopies"));
				tabledInfoPage.setAdditionalInformation(resultSet.getString("additionalInformation"));
				tabledInfoPage.setSendDigitalCopy(resultSet.getBoolean("sendDigitalCopy"));
				tabledInfoPagesList.add(tabledInfoPage);
			}
			TabledInfoPage [] tabledInfoPagesArray = new TabledInfoPage [tabledInfoPagesList.size()];
			for (int i=0; i<tabledInfoPagesArray.length; i++){
				tabledInfoPagesArray[i] = (TabledInfoPage)tabledInfoPagesList.get(i);
			}
			return tabledInfoPagesArray;
		}
		catch(SQLException e){
			System.out.println(e);
			TabledInfoPage [] tabledInfoPages = new TabledInfoPage [1];
			return tabledInfoPages;
		}

	}



    	public long getInfoPageLastUpdateDate(int ardNum){
    		try{
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT date FROM InfoPagesLastUpdates WHERE ardNum="+ardNum+";");
				resultSet.next();
				return resultSet.getLong("date");
			}
			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				return 0;

    		}
    	}

    	public InfoPage [] getNonRepetitiveWithNoAdditionalSubDatesExpiredInfoPages(Date now){
    		try {
    			Statement statement = getCurrentConnection().createStatement();
    			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE repetitive=0 AND subDate>0 AND subDate<"+
    				now.getTime()+" AND ardNum<10000 AND hasAdditionalSubDates=0;");
    			return moveFromResultSetToInfoPage(resultSet);
    		}
    		catch (SQLException e){
    			System.out.println(e);
    			return new InfoPage[0];
    		}
    	}

    	public InfoPage [] getRepetitiveInfoPages(){
			try {
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE repetitive=1 AND ardNum<10000;");
				return moveFromResultSetToInfoPage(resultSet);
			}
			catch (SQLException e){
				System.out.println(e);
				return new InfoPage[0];
			}
    	}

	public InfoPage [] getExpiredInfoPagesWithAdditionalSubDates(Date now){
		try {
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE hasAdditionalSubDates=1 AND subDate<"+now.getTime()+" AND ardNum<10000;");
			return moveFromResultSetToInfoPage(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			InfoPage [] infoPages = new InfoPage[1];
			infoPages[0] = new InfoPage();
			return infoPages;
		}
	}

	public InfoPage [] getAllYearInfoPages(){
		try {
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE subDate=0 AND ardNum<10000");
			return moveFromResultSetToInfoPage(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			return new InfoPage[0];
		}

	}

	public String getInfoPagesLastUpdate(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String queryString = "SELECT MAX(date) FROM InfoPagesLastUpdates;";
			ResultSet resultSet = statement.executeQuery(queryString);
			resultSet.next();
			String lastUpdateDate = Utils.getFormatedDate(resultSet.getLong(1));
			return lastUpdateDate;
		}
		catch(SQLException e){
			System.out.println(e);
			return "";
		}
	}

	public String getSiteLastUpdate(){
			try{
				Statement statement = getCurrentConnection().createStatement();
				String queryString = "SELECT MAX(date) FROM InfoPagesLastUpdates;";
				ResultSet resultSet = statement.executeQuery(queryString);
				resultSet.next();
				long x=resultSet.getLong(1);
				String queryString2 = "SELECT MAX(date) FROM PubPagesLastUpdates;";
				ResultSet resultSet2 = statement.executeQuery(queryString2);
				resultSet2.next();
				long y=resultSet2.getLong(1);
				if(x>y){
					String lastUpdateDate = Utils.getFormatedDate(x);
					return lastUpdateDate;
				}
				else{
					String lastUpdateDate = Utils.getFormatedDate(y);
					return lastUpdateDate;
				}

			}
			catch(SQLException e){
				System.out.println(e);
				return "";
			}
		}
	public boolean isInfoPageHasTabledVersion(int ardNum){
		try{
			Statement statement  = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT hasTabledVersion from InfoPages WHERE ardNum="+ardNum);
			resultSet.next();
			return resultSet.getBoolean("hasTabledVersion");
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}













}
