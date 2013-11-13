package huard3.utils;
import huard3.actions.InfoPage;
import huard3.actions.PubPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class ProfileExecuterInfoPagesQuery extends InfoPagesQuery {

	private long now;
	public ProfileExecuterInfoPagesQuery(){
		super();
		now = new Date().getTime();
	}

   /* public InfoPage[] integratedProfileExecuter (long fromSubDate, long untilSubDate, String[] keywordsArray){
     	try{
     		String ardNumsFromKeywordsQueryString="";
     		StringBuffer SBFromKeywordsQuery = new StringBuffer();
     		if (keywordsArray!=null){
        		Statement statement = getCurrentConnection().createStatement();
        		String keywordsQueryString="SELECT * FROM InfoPagesKeywords WHERE ardNum<10000 AND (LOCATE('"+keywordsArray[0]+"',keyword)>0) OR (INSTR('"+keywordsArray[0]+"',keyword)>0) OR (LOCATE('"+keywordsArray[1]+"',keyword)>0) OR (INSTR('"+keywordsArray[1]+"',keyword)>0) OR (LOCATE('"+keywordsArray[2]+"',keyword)>0) OR (INSTR('"+keywordsArray[2]+"',keyword)>0);";
                ResultSet keywordsResultSet =  statement.executeQuery(keywordsQueryString);
             	SBFromKeywordsQuery.append(" AND (");
             	while(keywordsResultSet.next()){
          	    	SBFromKeywordsQuery.append("ardNum=\""+keywordsResultSet.getInt("ardNum")+"\" OR ");
       	        }

        	   	if (SBFromKeywordsQuery.length()>6){//the length of the stringBuffer if no keywords where entered
              		SBFromKeywordsQuery.delete(SBFromKeywordsQuery.length()-4,SBFromKeywordsQuery.length());
                    SBFromKeywordsQuery.append(")");
     	            ardNumsFromKeywordsQueryString = SBFromKeywordsQuery.toString();

             	}
             	else {
          	    	InfoPage[] noInfoPages = new InfoPage[1];
             		noInfoPages[0] = new InfoPage();
             		noInfoPages[0].setTitle("No results");
             		return noInfoPages;
            	}
     		}
           	else{
        		ardNumsFromKeywordsQueryString ="";
        	}

        	Statement additionalSubDatesStatement = getCurrentConnection().createStatement();
        	String additionalSubDatesQueryString="SELECT * FROM AdditionalSubDates WHERE (subDate>="+fromSubDate+" AND subDate<="+untilSubDate+");";
        	System.out.print(additionalSubDatesQueryString);
        	StringBuffer ardNumsFromAdditionalSubDatesQuery = new StringBuffer();
        	ResultSet additionalSubDatesResultSet = additionalSubDatesStatement.executeQuery(additionalSubDatesQueryString);
        	while (additionalSubDatesResultSet.next()){
        		ardNumsFromAdditionalSubDatesQuery.append(" OR ardNum=\""+additionalSubDatesResultSet.getInt("ardNum")+"\" ");
        	}


        	Statement statement = getCurrentConnection().createStatement();
            String mainQueryString = "SELECT * FROM InfoPages WHERE ((subDate>="+
     	       fromSubDate+" AND subDate<="+
     	       untilSubDate+") OR subDate=0"+ardNumsFromAdditionalSubDatesQuery.toString()+")"+ardNumsFromKeywordsQueryString+" ORDER BY subDate, title;";

     	    ResultSet mainResultSet = statement.executeQuery(mainQueryString);
       	    return moveFromResultSetToInfoPage(mainResultSet);

     	}

     	catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
        }
   }*/


   /*public InfoPage[] getInfoPagesByRangeOfSubDatesAndKeywords(long fromSubDate, long untilSubDate, String[] keywordsArray){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
   		String queryString = "SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive, hasAdditionalSubDates, hasLocalWebPage, pageWebAddress"+
   		   " FROM InfoPages LEFT JOIN InfoPagesKeywords"+
   		   " ON InfoPages.ardNum=InfoPagesKeywords.ardNum"+
   		   " LEFT JOIN AdditionalSubDates ON InfoPages.ardNum=AdditionalSubDates.ardNum"+
   		   " WHERE InfoPages.ardNum<10000 AND (((keyword LIKE '%"+keywordsArray[0]+"%' OR INSTR('"+keywordsArray[0]+"',keyword )>0)"+
   		   " OR (keyword LIKE '%"+keywordsArray[1]+"%' OR INSTR('"+keywordsArray[1]+"',keyword )>0)"+
   		   " OR (keyword LIKE '%"+keywordsArray[2]+"%' OR INSTR('"+keywordsArray[2]+"',keyword )>0 ))"+
   		   " AND ((("+fromSubDate+"<=InfoPages.subDate) AND (InfoPages.subDate<="+untilSubDate+"))"+
   		   " OR InfoPages.subDate=0 OR (("+fromSubDate+"<=AdditionalSubDates.subDate)"+
   		   " AND (AdditionalSubDates.subDate<="+untilSubDate+")))) ORDER BY InfoPages.subDate DESC, title ;";

   		ResultSet resultSet = statement.executeQuery(queryString);
   		return moveBaseParmsFromResultSetToInfoPage(resultSet);
   	}
   	catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
   	}
   }*/

   /*public InfoPage [] getInfoPagesByTextualSearch(String[] keywordsArray){
		try{
		    Statement statement = getCurrentConnection().createStatement();
			StringBuffer querySB = new StringBuffer("SELECT * FROM InfoPages LEFT JOIN TabledInfoPages On InfoPages.ardNum=TabledInfoPages.ardNum WHERE ");
			for (int i=0; i<keywordsArray.length; i++){
				querySB.append("TabledInfoPages.description LIKE '% "+keywordsArray[i]+" %' OR TabledInfoPages.description LIKE '% "+keywordsArray[i]+".%' OR TabledInfoPages.description LIKE '% "+keywordsArray[i]+",%' OR ");
			}
			querySB.delete(querySB.length()-4,querySB.length()-1);
			querySB.append(";");
			System.out.println(querySB);
			ResultSet resultSet = statement.executeQuery(querySB.toString());
			return moveBaseParmsFromResultSetToInfoPage(resultSet);
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			InfoPage [] infoPages = new InfoPage[0];
			infoPages[0] = new InfoPage();
			infoPages[0].setTitle("No results");
       		return infoPages ;
		}
   }*/

   /*public InfoPage[] getInfoPagesByKeywords(String[] keywordsArray, boolean fullList, String orderBy){
	   try{
		   Statement statement = getCurrentConnection().createStatement();
		   String queryString = "SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive, hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress"+
			  " FROM InfoPages LEFT JOIN InfoPagesKeywords"+
			  " ON InfoPages.ardNum=InfoPagesKeywords.ardNum"+
			  " LEFT JOIN AdditionalSubDates ON InfoPages.ardNum=AdditionalSubDates.ardNum"+
			  " WHERE InfoPages.ardNum<10000 AND (((keyword LIKE '%"+keywordsArray[0]+"%' OR INSTR('"+keywordsArray[0]+"',keyword )>0)"+
			  " OR (keyword LIKE '%"+keywordsArray[1]+"%' OR INSTR('"+keywordsArray[1]+"',keyword )>0)"+
			  " OR (keyword LIKE '%"+keywordsArray[2]+"%' OR INSTR('"+keywordsArray[2]+"',keyword )>0 )))"+
			  (fullList ? "" : " AND (InfoPages.subDate>"+now+" OR InfoPages.subDate=0)" ) +" ORDER BY BINARY InfoPages."+orderBy+", BINARY title;";

   		   ResultSet resultSet = statement.executeQuery(queryString);
		   return moveBaseParmsFromResultSetToInfoPage(resultSet);
	   }
	   catch(SQLException e){
			   System.out.println("Exception while querying data: " + e);
			   InfoPage [] infoPages = new InfoPage[0];
			   infoPages[0] = new InfoPage();
			   infoPages[0].setTitle("No results");

			   return infoPages ;
	   }
	  }*/

	/*public InfoPage[] getInfoPagesByKeywordsAndTextualSearch(String[] originalKeywordsArray, String [] handledKeywordsArray, boolean fullList, String orderBy){
		   try{
			   Statement statement = getCurrentConnection().createStatement();
			   StringBuffer querySB = new StringBuffer();
			   querySB.append("SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive, hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress"+
				  " FROM InfoPages LEFT JOIN InfoPagesKeywords"+
				  " ON InfoPages.ardNum=InfoPagesKeywords.ardNum"+
				  " LEFT JOIN AdditionalSubDates ON InfoPages.ardNum=AdditionalSubDates.ardNum"+
				  " LEFT JOIN TabledInfoPages ON InfoPages.ardNum=TabledInfoPages.ardNum"+
				  " WHERE InfoPages.ardNum<10000"+
					(fullList ? "" : " AND (InfoPages.subDate>"+now+" OR InfoPages.subDate=0)")+
				  " AND ((keyword LIKE '"+originalKeywordsArray[0]+"'"+
				  " OR keyword LIKE '"+originalKeywordsArray[1]+"'"+
				  " OR keyword LIKE '"+originalKeywordsArray[2]+"')");

				for (int i=0; i<handledKeywordsArray.length; i++){
					querySB.append(" OR (InfoPages.title LIKE '% "+handledKeywordsArray[i]+" %' OR InfoPages.title LIKE '% "+handledKeywordsArray[i]+".%' OR InfoPages.title LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.subDateDetails LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.subDateDetails LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.subDateDetails LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.deskAndContact LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.deskAndContact LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.deskAndContact LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.description LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.description LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.description LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.maxFundingPeriod LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.maxFundingPeriod LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.maxFundingPeriod LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.amountOfGrant LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.amountOfGrant LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.amountOfGrant LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.eligibilityRequirements LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.eligibilityRequirements LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.eligibilityRequirements LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.activityLocation LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.activityLocation LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.activityLocation LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.possibleCollaboration LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.possibleCollaboration LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.possibleCollaboration LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.budgetDetails LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.budgetDetails LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.budgetDetails LIKE '% "+handledKeywordsArray[i]+",%')");
					querySB.append(" OR (TabledInfoPages.additionalInformation LIKE '% "+handledKeywordsArray[i]+" %' OR TabledInfoPages.additionalInformation LIKE '% "+handledKeywordsArray[i]+".%' OR TabledInfoPages.additionalInformation LIKE '% "+handledKeywordsArray[i]+",%')");

				}
				querySB.append(") ORDER BY BINARY InfoPages."+orderBy+", BINARY title;");


			   ResultSet resultSet = statement.executeQuery(querySB.toString());
			   return moveBaseParmsFromResultSetToInfoPage(resultSet);
		   }
		   catch(SQLException e){
				   System.out.println("Exception while querying data: " + e);
				   InfoPage [] infoPages = new InfoPage[0];
				   //infoPages[0] = new InfoPage();
				   //infoPages[0].setTitle("No results");

				   return infoPages ;
		   }
	}*/


	/*public List getIndexedWords(String indexTable, String word){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT description, ardNum FROM "+indexTable+" WHERE description LIKE '% "+word+" %'");
			List list = new ArrayList();
			while (resultSet.next()){
				list.add(new Integer(resultSet.getInt(2)));
			}
			return list;
		}
		catch(SQLException e){
			System.out.println(e);
			return new ArrayList();
		}
	}*/



	/*public InfoPage[] getInfoPagesByKeywordsAndIndexedTextualSearch1(String[] originalKeywordsArray, String [] handledKeywordsArray, boolean fullList, String orderBy){
		try{
			Statement statement = getCurrentConnection().createStatement();
	        ResultSet resultSet;
			StringBuffer querySB;
		    List ardNumsList = new ArrayList();
		    for (int j=0; j<handledKeywordsArray.length/10; j++){
				 querySB = new StringBuffer();
				for (int i=j*10; i<(j+1)*10 && i<handledKeywordsArray.length; i++){
					querySB.append("SELECT DISTINCT ardNum from InfoPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\" UNION ");
					if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
						while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
							handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
						}
						querySB.append("SELECT DISTINCT InfoPagesIndex.ardNum FROM InfoPagesIndex"+
							" LEFT JOIN InfoPagesIndex_copy ON InfoPagesIndex.ardNum=InfoPagesIndex_copy.ardNum"+
							" WHERE InfoPagesIndex.word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\""+
							" AND InfoPagesIndex_copy.word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\" UNION ");

					}



					/*if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
						while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
							handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
						}
						querySB.append("SELECT DISTINCT * from InfoPagesIndex WHERE word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\" UNION ");
						querySB.append("SELECT DISTINCT * from InfoPagesIndex WHERE word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\" UNION ");
					}
		    	}
				if (querySB.length()>0){
					querySB.delete(querySB.length()-6,querySB.length());
		    		querySB.append(";");

		    		resultSet = statement.executeQuery(querySB.toString());
		    		while (resultSet.next()){
				    	ardNumsList.add(new Integer(resultSet.getInt("ardNum")));
		    		}
				}
		    }
			querySB = new StringBuffer();
			querySB.append("SELECT ardNum from InfoPagesKeywords WHERE ");
			for (int i=0; i<originalKeywordsArray.length; i++){
				querySB.append("word=\""+originalKeywordsArray[i]+"\" OR ");
			}
			querySB.delete(querySB.length()-3,querySB.length());
			//System.out.println(querySB.toString());
			resultSet = statement.executeQuery(querySB.toString());
			while (resultSet.next()){
				ardNumsList.add(new Integer(resultSet.getInt("ardNum")));
			}


			if (ardNumsList.size()>0){
				ardNumsList = uniqueArdNumsList(ardNumsList);
				querySB = new StringBuffer();
			    querySB.append("SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive, hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress"+
			  " FROM InfoPages LEFT JOIN AdditionalSubDates ON InfoPages.ardNum=AdditionalSubDates.ardNum"+
			  " WHERE InfoPages.ardNum<10000"+
				(fullList ? "" : " AND (InfoPages.subDate>"+now+" OR InfoPages.subDate=0)"));
				querySB.append(" AND (");

				for (int i=0; i<ardNumsList.size();i++){
					querySB.append(" InfoPages.ardNum="+((Integer)ardNumsList.get(i)).intValue()+" OR");
				}
				querySB.delete(querySB.length()-2,querySB.length());

				querySB.append(") ORDER BY InfoPages."+orderBy+",title;");
				resultSet = statement.executeQuery(querySB.toString());
				//System.out.println(querySB.toString());
				return moveBaseParmsFromResultSetToInfoPage(resultSet);
			}
			return new InfoPage[0];
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			InfoPage [] infoPages = new InfoPage[0];
			return infoPages ;
		}
	}*/


	public InfoPage[] getInfoPagesByKeywordsAndIndexedTextualSearch(String[] originalKeywordsArray, String [] handledKeywordsArray, boolean fullList, String orderBy){
			try{
				Date date = new Date();
				Statement statement = getCurrentConnection().createStatement();
				statement.executeUpdate("CREATE TEMPORARY TABLE SearchResultInfoPages_"+date.getTime()+" (ardNum smallint(6), word1 varchar(255), word2 varchar(255));");
				for (int i=0; i<handledKeywordsArray.length; i++){
					statement.executeUpdate("INSERT SearchResultInfoPages_"+date.getTime()+" SELECT ardNum, word, word FROM InfoPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\";");
					if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
						while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
							handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
						}
					String updateString = "INSERT SearchResultInfoPages_"+date.getTime()+" SELECT InfoPagesIndex.ardNum, InfoPagesIndex.word, InfoPagesIndex_copy.word"+
					" FROM InfoPagesIndex, InfoPagesIndex_copy WHERE InfoPagesIndex.ardNum=InfoPagesIndex_copy.ardNum"+
					" AND InfoPagesIndex.word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\""+
					" AND InfoPagesIndex_copy.word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\";";
					statement.executeUpdate(updateString);

					}
				}
				for (int i=0; i<originalKeywordsArray.length; i++){
					statement.executeUpdate("INSERT SearchResultInfoPages_"+date.getTime()+" SELECT ardNum, keyword, keyword from InfoPagesKeywords WHERE keyword=\""+originalKeywordsArray[i]+"\" AND ardNum<10000;");
				}
				/*ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(InfoPages.ardNum), title, InfoPages.subDate, docType, repetitive,"+
					" hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress, word1, word2"+
					" FROM SearchResultInfoPages_"+date.getTime()+" LEFT JOIN InfoPages ON SearchResultInfoPages_"+date.getTime()+".ardNum=InfoPages.ardNum"+
					" LEFT JOIN AdditionalSubDates ON InfoPages.ardNum=AdditionalSubDates.ardNum  "+ (fullList ? "" : "WHERE InfoPages.subDate>"+now+" OR InfoPages.subDate=0")+
					" ORDER BY "+orderBy+" ;");*/

				String queryString = "SELECT DISTINCT InfoPages.ardNum, title, InfoPages.subDate, docType, repetitive,"+
				" hasAdditionalSubDates, hasLocalWebPage, hasTabledVersion, pageWebAddress, keepInRollingMessages, stopRollingDate, word1, word2"+
				" FROM InfoPages, SearchResultInfoPages_"+date.getTime()+" WHERE (SearchResultInfoPages_"+date.getTime()+
				".ardNum=InfoPages.ardNum) "+
				 (fullList ? "" : "AND ((InfoPages.subDate+86400000)>"+now+" OR InfoPages.subDate=0)")+
				" ORDER BY "+orderBy+";";
				//System.out.println("xxx: "+queryString);
				ResultSet resultSet = statement.executeQuery(queryString);
				List infoPagesList = moveBaseParmsAndFoundBySearchWordsFromResultSetToInfoPage(resultSet);
				//System.out.println("Search Results list size: "+infoPagesList.size());
				for (int i=0; i<infoPagesList.size();i++){
					InfoPage infoPage1 = (InfoPage)infoPagesList.get(i);
					for (int j=i+1; j<infoPagesList.size(); j++){
						InfoPage infoPage2 = (InfoPage)infoPagesList.get(j);
						if (infoPage1.getArdNum()==infoPage2.getArdNum()) {
							infoPage1.setFoundBySearchWords(infoPage1.getFoundBySearchWords()+","+infoPage2.getFoundBySearchWords());
							infoPagesList.set(i,infoPage1);
							infoPagesList.remove(j);
							j--;
						}
					}
				}
				InfoPage [] infoPages = new InfoPage[infoPagesList.size()];
				for (int i=0; i<infoPages.length; i++){
					infoPages[i] = (InfoPage)infoPagesList.get(i);
				}


				resultSet = statement.executeQuery("SELECT DISTINCT * FROM SearchResultInfoPages_"+date.getTime()+
				",InfoPages WHERE (SearchResultInfoPages_"+date.getTime()+".ardNum=InfoPages.ardNum) "+
				 (fullList ? "" : "AND (InfoPages.subDate>"+now+" OR InfoPages.subDate=0)")+
				" ORDER BY "+orderBy+" ;");


				try{
					BufferedWriter bw = new BufferedWriter( new FileWriter("/home/"+Utils.getVersionName()+"/wordsIndexer/searchLog.txt", true));
					bw.write("Search Words for Info Pages: ");
					for (int i=0; i<originalKeywordsArray.length; i++){
						bw.write(originalKeywordsArray[i]+",");
					}
					bw.newLine();
					bw.write("These Words were Found: ");

					while (resultSet.next()){
						String word1 = resultSet.getString("word1");
						String word2 = resultSet.getString("word2");
						//InfoPage infoPage = new InfoPage();
						if (word1.equals(word2)) {
							int ardNum = resultSet.getInt("ardNum");
							bw.write(ardNum+" -");
							bw.write(" "+word1+", ");
						}
						else {
							int ardNum = resultSet.getInt("ardNum");
							bw.write(ardNum+"-"+word1+"-"+word2+", ");
						}
					}
					bw.newLine();
					bw.close();
				}
				catch (IOException e){
					System.out.println("No Search Log Writing: "+e);
				}


				return infoPages;
			}

			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				InfoPage [] infoPages = new InfoPage[0];
				return infoPages ;
			}
		}

	public List uniqueArdNumsList(List list){
		for (int i=0; i<list.size();i++){
			for (int j=i+1; j<list.size();j++){
				if (((Integer)list.get(i)).intValue()==((Integer)list.get(j)).intValue()){
					list.remove(j);
					j--;
				}
			}
		}
		return list;
	}

/*	public MultipleOptionsPatternedPage [] getMultipleOptionsPatternedPagesByKeywordsAndIndexedTextualSearch(String [] originalKeywordsArray, String[] handledKeywordsArray, String docType){
		try{
			Date date1 = new Date();
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("CREATE TEMPORARY TABLE SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+" (ardNum smallint(6), word1 varchar(255), word2 varchar(255));");

			Date date2 = new Date();
			statement.executeUpdate("CREATE TEMPORARY TABLE MultipleOptionsPatternedPagesIndex_copy"+date2.getTime()+" LIKE MultipleOptionsPatternedPagesIndex");
			statement.executeUpdate("INSERT MultipleOptionsPatternedPagesIndex_copy"+date2.getTime()+" SELECT * FROM MultipleOptionsPatternedPagesIndex;");

			for (int i=0; i<handledKeywordsArray.length; i++){
				statement.executeUpdate("INSERT SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+" SELECT ardNum, word, word FROM MultipleOptionsPatternedPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\";");
				if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
					while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
						handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
					}
					String updateString = "INSERT SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+" SELECT MultipleOptionsPatternedPagesIndex.ardNum, " +						"MultipleOptionsPatternedPagesIndex.word, MultipleOptionsPatternedPagesIndex_copy"+date2.getTime()+".word"+
						" FROM MultipleOptionsPatternedPagesIndex, MultipleOptionsPatternedPagesIndex_copy"+date2.getTime()+" WHERE MultipleOptionsPatternedPagesIndex.ardNum=MultipleOptionsPatternedPagesIndex_copy"
						+date2.getTime()+".ardNum AND MultipleOptionsPatternedPagesIndex.word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\""+
						" AND MultipleOptionsPatternedPagesIndex_copy"+date2.getTime()+".word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\";";
					statement.executeUpdate(updateString);

				}
			}
			ResultSet resultSet = statement.executeQuery("SELECT DISTINCT MultipleOptionsPatternedPages.ardNum, contentOptionsTable, fileName, docType, word1, word2"+
				" FROM SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+",MultipleOptionsPatternedPages WHERE (SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+".ardNum=MultipleOptionsPatternedPages.ardNum)"+
				" AND docType=\""+docType+"\" GROUP BY word1, word2;");
			List patternedPagesList = new ArrayList();
			while (resultSet.next()){
				MultipleOptionsPatternedPage patternedPage = new MultipleOptionsPatternedPage();
				patternedPage.setArdNum(resultSet.getInt(1));
				patternedPage.setContentOptionsTable(resultSet.getString(2));
				patternedPage.setFileName(resultSet.getString(3));
				patternedPage.setFoundBySearchWords(resultSet.getString(5)+","+resultSet.getString(6));
				patternedPagesList.add(patternedPage);
			}

			for (int i=0; i<patternedPagesList.size();i++){
				MultipleOptionsPatternedPage patternedPage1 = (MultipleOptionsPatternedPage)patternedPagesList.get(i);
				for (int j=i+1; j<patternedPagesList.size(); j++){
					MultipleOptionsPatternedPage patternedPage2 = (MultipleOptionsPatternedPage)patternedPagesList.get(j);
					if (patternedPage1.getArdNum()==patternedPage2.getArdNum()) {
						patternedPage1.setFoundBySearchWords(patternedPage1.getFoundBySearchWords()+","+patternedPage2.getFoundBySearchWords());
						patternedPagesList.set(i,patternedPage1);
						patternedPagesList.remove(j);
						j--;
					}
				}
			}
			MultipleOptionsPatternedPage [] patternedPages = new MultipleOptionsPatternedPage[patternedPagesList.size()];
			for (int i=0; i<patternedPages.length; i++){
				patternedPages[i] = (MultipleOptionsPatternedPage)patternedPagesList.get(i);
			}

			resultSet = statement.executeQuery("SELECT DISTINCT * FROM SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+
				",MultipleOptionsPatternedPages WHERE (SearchResultMultipleOptionsPatternedPages_"+date1.getTime()+".ardNum=MultipleOptionsPatternedPages.ardNum)"+
				" AND docType=\""+docType+"\";");
			try{
				BufferedWriter bw = new BufferedWriter( new FileWriter("/home/"+Utils.getVersionName()+"/wordsIndexer/searchLog.txt", true));
				bw.write("Search Words for Multiple Options Patterned Pages: ");
				for (int i=0; i<originalKeywordsArray.length; i++){
					bw.write(originalKeywordsArray[i]+",");
				}
				bw.newLine();
				bw.write("These Words were Found: ");
				while (resultSet.next()){
					String word1 = resultSet.getString("word1");
					String word2 = resultSet.getString("word2");
					if (word1.equals(word2)) {
						int ardNum = resultSet.getInt("ardNum");
						bw.write(ardNum+" -");
						bw.write(" "+word1+", ");
					}
					else {
						int ardNum = resultSet.getInt("ardNum");
						bw.write(ardNum+"-"+word1+"-"+word2+", ");
					}
				}
				bw.newLine();
				bw.close();
			}
			catch (IOException e){
				System.out.println("No Search Log Writing: "+e);
			}
			return patternedPages;
		}

		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			MultipleOptionsPatternedPage [] pages = new MultipleOptionsPatternedPage[1];
			pages[0] = new MultipleOptionsPatternedPage();
			return pages;
		}
	}

	public ComposedPatternedPage [] getComposedPatternedPagesByKeywordsAndIndexedTextualSearch(String [] originalKeywordsArray, String[] handledKeywordsArray, String docType){
		try{
			Date date1 = new Date();
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("CREATE TEMPORARY TABLE SearchResultComposedPatternedPages_"+date1.getTime()+" (ardNum smallint(6), word1 varchar(255), word2 varchar(255));");

			Date date2 = new Date();
			statement.executeUpdate("CREATE TEMPORARY TABLE ComposedPatternedPagesIndex_copy"+date2.getTime()+" LIKE ComposedPatternedPagesIndex");
			statement.executeUpdate("INSERT ComposedPatternedPagesIndex_copy"+date2.getTime()+" SELECT * FROM ComposedPatternedPagesIndex;");

			for (int i=0; i<handledKeywordsArray.length; i++){
				statement.executeUpdate("INSERT SearchResultComposedPatternedPages_"+date1.getTime()+" SELECT ardNum, word, word FROM ComposedPatternedPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\";");
				//ResultSet resultSet2 = statement.executeQuery("SELECT * FROM SearchResultComposedPatternedPages_"+date1.getTime()+";");
				if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
					while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
						handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
					}
					String updateString = "INSERT SearchResultComposedPatternedPages_"+date1.getTime()+" SELECT ComposedPatternedPagesIndex.ardNum, " +
						"ComposedPatternedPagesIndex.word, ComposedPatternedPagesIndex_copy"+date2.getTime()+".word"+
						" FROM ComposedPatternedPagesIndex, ComposedPatternedPagesIndex_copy"+date2.getTime()+" WHERE ComposedPatternedPagesIndex.ardNum=ComposedPatternedPagesIndex_copy"
						+date2.getTime()+".ardNum AND ComposedPatternedPagesIndex.word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\""+
						" AND ComposedPatternedPagesIndex_copy"+date2.getTime()+".word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\";";
					statement.executeUpdate(updateString);

				}
			}
			ResultSet resultSet = statement.executeQuery("SELECT DISTINCT ComposedPatternedPages.ardNum, title, fileName, word1, word2"+
				" FROM SearchResultComposedPatternedPages_"+date1.getTime()+",ComposedPatternedPages WHERE (SearchResultComposedPatternedPages_"+date1.getTime()+".ardNum=ComposedPatternedPages.ardNum)"+
				" AND docType=\""+docType+"\" GROUP BY word1, word2;");
			List patternedPagesList = new ArrayList();
			while (resultSet.next()){
				ComposedPatternedPage patternedPage = new ComposedPatternedPage();
				patternedPage.setArdNum(resultSet.getInt(1));
				patternedPage.setTitle(resultSet.getString(2));
				patternedPage.setFileName(resultSet.getString(3));
				patternedPage.setFoundBySearchWords(resultSet.getString(4)+","+resultSet.getString(5));
				patternedPagesList.add(patternedPage);
			}

			for (int i=0; i<patternedPagesList.size();i++){
				ComposedPatternedPage patternedPage1 = (ComposedPatternedPage)patternedPagesList.get(i);
				for (int j=i+1; j<patternedPagesList.size(); j++){
					ComposedPatternedPage patternedPage2 = (ComposedPatternedPage)patternedPagesList.get(j);
					if (patternedPage1.getArdNum()==patternedPage2.getArdNum()) {
						patternedPage1.setFoundBySearchWords(patternedPage1.getFoundBySearchWords()+","+patternedPage2.getFoundBySearchWords());
						patternedPagesList.set(i,patternedPage1);
						patternedPagesList.remove(j);
						j--;
					}
				}
			}
			ComposedPatternedPage [] patternedPages = new ComposedPatternedPage[patternedPagesList.size()];
			for (int i=0; i<patternedPages.length; i++){
				patternedPages[i] = (ComposedPatternedPage)patternedPagesList.get(i);
			}

			resultSet = statement.executeQuery("SELECT DISTINCT * FROM SearchResultComposedPatternedPages_"+date1.getTime()+
				",ComposedPatternedPages WHERE (SearchResultComposedPatternedPages_"+date1.getTime()+".ardNum=ComposedPatternedPages.ardNum)"+
				" AND docType=\""+docType+"\";");
			try{
				BufferedWriter bw = new BufferedWriter( new FileWriter("/home/"+Utils.getVersionName()+"/wordsIndexer/searchLog.txt", true));
				bw.write("Search Words for Composed Patterned Pages: ");
				for (int i=0; i<originalKeywordsArray.length; i++){
					bw.write(originalKeywordsArray[i]+",");
				}
				bw.newLine();
				bw.write("These Words were Found: ");
				while (resultSet.next()){
					String word1 = resultSet.getString("word1");
					String word2 = resultSet.getString("word2");
					if (word1.equals(word2)) {
						int ardNum = resultSet.getInt("ardNum");
						bw.write(ardNum+" -");
						bw.write(" "+word1+", ");
					}
					else {
						int ardNum = resultSet.getInt("ardNum");
						bw.write(ardNum+"-"+word1+"-"+word2+", ");
					}
				}
				bw.newLine();
				bw.close();
			}
			catch (IOException e){
				System.out.println("No Search Log Writing: "+e);
			}
			return patternedPages;
		}

		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			ComposedPatternedPage [] pages = new ComposedPatternedPage[1];
			pages[0] = new ComposedPatternedPage();
			return pages;
		}
	}*/


	/*public PubPage[] getPubPagesByKeywordsAndTextualSearch(String[] originalKeywordsArray, String [] handledKeywordsArray){
	   try{
		   Statement statement = getCurrentConnection().createStatement();
		   StringBuffer querySB = new StringBuffer();
		   querySB.append("SELECT DISTINCT PubPages.ardNum,title,fileRepresentation,link FROM PubPages LEFT JOIN PubPagesKeywords"+
			  " ON PubPages.ardNum=PubPagesKeywords.ardNum"+
			  " WHERE PubPages.ardNum<10000"+
			  " AND ((word=\""+originalKeywordsArray[0]+"\""+
			  " OR word=\""+originalKeywordsArray[1]+"\""+
			  " OR word=\""+originalKeywordsArray[2]+"\")");

			for (int i=0; i<handledKeywordsArray.length; i++){
				querySB.append(" OR (PubPages.title LIKE '% "+handledKeywordsArray[i]+" %' ");
				querySB.append(" OR (PubPages.html LIKE '% "+handledKeywordsArray[i]+" %' ");
			}
			querySB.append(") ORDER BY BINARY PubPages.title;");
	   	    System.out.println(querySB.toString());
		    ResultSet resultSet = statement.executeQuery(querySB.toString());
		    return new PubPagesQuery().moveBaseParamsFromResultSetToPubPagesArray(resultSet);
	   }
	  catch(SQLException e){
		   System.out.println("Exception while querying data: " + e);
		   PubPage [] pubPages = new PubPage[0];
		   return pubPages ;
	   }
	}*/

	public PubPage[] getPubPagesByKeywordsAndIndexedTextualSearch(String[] originalKeywordsArray, String [] handledKeywordsArray){
		try{
			Date date = new Date();
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("CREATE TEMPORARY TABLE SearchResultPubPages_"+date.getTime()+" (ardNum smallint(6), word1 varchar(255), word2 varchar(255));");
			for (int i=0; i<handledKeywordsArray.length; i++){
				statement.executeUpdate("INSERT SearchResultPubPages_"+date.getTime()+" SELECT ardNum, word, word FROM PubPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\";");
				if (handledKeywordsArray[i].trim().indexOf(" ")!=-1){
					while (handledKeywordsArray[i].indexOf(" ")!=handledKeywordsArray[i].lastIndexOf(" ")){ //for cases of more than two words separated with spaces
						handledKeywordsArray[i] = handledKeywordsArray[i].substring(0,handledKeywordsArray[i].lastIndexOf(" "));
					}
					statement.executeUpdate("INSERT SearchResultPubPages_"+date.getTime()+" SELECT PubPagesIndex.ardNum, PubPagesIndex.word,"+
						" PubPagesIndex_copy.word FROM PubPagesIndex, PubPagesIndex_copy WHERE PubPagesIndex.ardNum=PubPagesIndex_copy.ardNum"+
						" AND PubPagesIndex.word=\""+handledKeywordsArray[i].substring(0,handledKeywordsArray[i].indexOf(" "))+"\""+
						" AND PubPagesIndex_copy.word=\""+handledKeywordsArray[i].substring(handledKeywordsArray[i].indexOf(" ")+1)+"\";");
				}
			}
			for (int i=0; i<originalKeywordsArray.length; i++){
				statement.executeUpdate("INSERT SearchResultPubPages_"+date.getTime()+" SELECT PubPagesKeywords.ardNum, keyword, keyword FROM PubPagesKeywords, PubPages WHERE PubPagesKeywords.ardNum=PubPages.ardNum AND keyword=\""+originalKeywordsArray[i]+"\" AND PubPagesKeywords.ardNum<10000 AND onSite=1;");
			}

			ResultSet resultSet = statement.executeQuery("SELECT DISTINCT PubPages.ardNum, title, fileRepresentation, link, onSite, word1, word2"+
				" FROM PubPages, SearchResultPubPages_"+date.getTime()+" WHERE (SearchResultPubPages_"+date.getTime()+".ardNum=PubPages.ardNum) " +				"ORDER By title;");
			List pubPagesList = new PubPagesQuery().moveBaseParamsAndFoundBySearchWordsFromResultSetToPubPagesArray(resultSet);
			for (int i=0; i<pubPagesList.size();i++){
				PubPage pubPage1 = (PubPage)pubPagesList.get(i);
				for (int j=i+1; j<pubPagesList.size(); j++){
					PubPage pubPage2 = (PubPage)pubPagesList.get(j);
					if (pubPage1.getArdNum()==pubPage2.getArdNum()) {
						pubPage1.setFoundBySearchWords(pubPage1.getFoundBySearchWords()+","+pubPage2.getFoundBySearchWords());
						pubPagesList.set(i,pubPage1);
						pubPagesList.remove(j);
						j--;
					}
				}
			}
			PubPage [] pubPages = new PubPage[pubPagesList.size()];
			for (int i=0; i<pubPages.length; i++){
				pubPages[i] = (PubPage)pubPagesList.get(i);
			}

			resultSet = statement.executeQuery("SELECT DISTINCT * FROM SearchResultPubPages_"+date.getTime()+";");
			try{
				BufferedWriter bw = new BufferedWriter( new FileWriter("/home/"+Utils.getVersionName()+"/wordsIndexer/searchLog.txt", true));
				bw.write("Search Words for Inforamation Pages: ");
				for (int i=0; i<originalKeywordsArray.length; i++){
					bw.write(originalKeywordsArray[i]+",");
				}
				bw.newLine();
				bw.write("These Words were Found: ");
				while (resultSet.next()){
					String word1 = resultSet.getString("word1");
					String word2 = resultSet.getString("word2");
					if (word1.equals(word2)) {
						bw.write(resultSet.getInt("ardNum")+" -");
						bw.write(" "+word1+", ");
					}
					else bw.write(resultSet.getInt("ardNum")+"-"+word1+"-"+word2+", ");
				}
				bw.newLine();
				bw.close();
			}
			catch (IOException e){
				System.out.println("No Search Log Writing: "+e);
			}
		return pubPages;
		}
		catch(SQLException e){
			System.out.println(e);
			PubPage [] pubPages = new PubPage[0];
			return pubPages ;
		}
	}

	/*public PubPage[] getPubPagesByKeywordsAndIndexedTextualSearch(String[] originalKeywordsArray, String [] handledKeywordsArray){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet;
			StringBuffer querySB;
			List ardNumsList = new ArrayList();
			for (int j=0; j<handledKeywordsArray.hashCode()%100; j++){
				 querySB = new StringBuffer();
				for (int i=j*100; i<(j+1)*100 && i<handledKeywordsArray.length; i++){
					//querySB.append("SELECT DISTINCT * from PubPagesIndex WHERE CONVERT(word USING utf8)=\""+handledKeywordsArray[i]+"\" OR CONVERT(word USING utf8)=\""+handledKeywordsArray[i]+".\" OR CONVERT(word USING utf8)=\""+handledKeywordsArray[i]+",\" UNION ");
					querySB.append("SELECT DISTINCT * from PubPagesIndex WHERE word=\""+handledKeywordsArray[i]+"\" OR word=\""+handledKeywordsArray[i]+".\" OR word=\""+handledKeywordsArray[i]+",\" UNION ");
				}
				if (querySB.length()>0){
					querySB.delete(querySB.length()-6,querySB.length());
					querySB.append(";");
					resultSet = statement.executeQuery(querySB.toString());
					while (resultSet.next()){
						ardNumsList.add(new Integer(resultSet.getInt("ardNum")));
					}
				}
			}

			/*querySB = new StringBuffer();
			querySB.append("SELECT ardNum from PubPagesKeywords WHERE ");
			querySB.append("(PubPagesKeywords.keyword=\""+originalKeywordsArray[0]+"\" OR PubPagesKeywords.keyword=\""+originalKeywordsArray[1]+".\" OR PubPagesKeywords.keyword=\""+originalKeywordsArray[2]+",\")");
			System.out.println(querySB.toString());
			resultSet = statement.executeQuery(querySB.toString());
			while (resultSet.next()){
				ardNumsList.add(new Integer(resultSet.getInt("ardNum")));
			}
			*/
			/*ardNumsList = uniqueArdNumsList(ardNumsList);
			if (ardNumsList.size()>0){
				querySB = new StringBuffer();
				querySB.append("SELECT PubPages.ardNum, title, fileRepresentation, link"+
				  	" FROM PubPages WHERE PubPages.ardNum<10000 AND");
				for (int i=0; i<ardNumsList.size();i++){
					querySB.append(" PubPages.ardNum="+((Integer)ardNumsList.get(i)).intValue()+" OR");
				}
				querySB.delete(querySB.length()-2,querySB.length());
				querySB.append(" ORDER BY BINARY title;");
			//System.out.println(querySB.toString());
				resultSet = statement.executeQuery(querySB.toString());
				return new PubPagesQuery().moveBaseParamsFromResultSetToPubPagesArray(resultSet);
			}
			return new PubPage[0];
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
			PubPage [] pubPages = new PubPage[0];
			return pubPages ;
		}
	}*/

   public InfoPage[] getInfoPagesByRangeOfSubDates(long fromSubDate, long untilSubDate, boolean fullList){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM AdditionalSubDates WHERE (subDate>="+fromSubDate+" AND subDate<="+untilSubDate+") AND ardNum<10000;");
        List infoPagesList = new ArrayList();
        while (resultSet.next()){
        	InfoPage infoPage = getInfoPageDetailsByArdNumSkipKeywords(""+resultSet.getInt("ardNum"),"InfoPages");
        	infoPage.setSubDate(resultSet.getLong("subDate"));
        	infoPagesList.add(infoPage);
        }


        String mainQueryString = "SELECT * FROM InfoPages WHERE ardNum<10000 AND (subDate>="+
     	       fromSubDate+" AND subDate<="+
     	       untilSubDate+") AND (subDate+86400000)>="+(fullList ? 0 : now)+" ORDER BY subDate, title;";
     	System.out.println(mainQueryString);


        ResultSet mainResultSet = statement.executeQuery(mainQueryString);
        InfoPage[] infoPages = moveFromResultSetToInfoPage(mainResultSet);
        for (int i=0; i<infoPages.length; i++){
        	infoPagesList.add(infoPages[i]);
        }
        return Utils.getInfoPagesArrayFromInfoPagesList(infoPagesList);
   	}
   	catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
        }
   }

/*  public InfoPage[] getInfoPagesByFundNumSortedByTitle(int fundNum){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
   		ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE "+
   				"ardNum<10000 AND fundNum="+fundNum+" ORDER BY Title;");
   		InfoPage [] infoPages = moveFromResultSetToInfoPage(resultSet);
   		/*resultSet.beforeFirst();
   		int i=0;
   		while (resultSet.next()){
   			if (! resultSet.getString("forms").equals("xxxxx")) infoPages[i].setHasForms(true);
   			i++;
   		}
   		return infoPages;

   	}
   	catch(SQLException e){
   		System.out.println("Exception while querying data: " + e);
  		InfoPage [] infoPages = new InfoPage[0];
   		infoPages[0] = new InfoPage();
   		infoPages[0].setTitle("No results");
   		return infoPages ;
    }
   }
*/

   	public InfoPage [] getInfoPagesByFundNumSortedByTitle(int fundNum){
   		try{
   	   		Statement statement = getCurrentConnection().createStatement();
   	   		//ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE "+
   			//	"ardNum<10000 AND fundNum="+fundNum+" ORDER BY Title;");

   	   		ResultSet resultSet = statement.executeQuery("SELECT InfoPages.ardNum,title,subDate,forms FROM InfoPages,TabledInfoPages WHERE "+
   				"InfoPages.ardNum=TabledInfoPages.ardNum AND InfoPages.ardNum<10000 AND fundNum="+fundNum+" ORDER BY Title;");
   	   		InfoPage [] infoPages = moveFromResultSetToInfoPage(resultSet);
/*   	   		resultSet.beforeFirst();
   	   		int i=0;
   	   		while (resultSet.next()){
   	   			if (! resultSet.getString("forms").equals("xxxxx")) infoPages[i].setHasForms(true);
   	   			i++;
   	   		}
*/
   	   		return infoPages;


   	   	}
   		catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");
       		return infoPages ;
        }
   }


   public InfoPage [] getInfoPagesSortedByFundsFullNameThenByTitle(){
		try{
			Statement statement  = getCurrentConnection().createStatement();
			String queryString = "SELECT * FROM InfoPages,TabledInfoPages,Funds WHERE "+
			"InfoPages.ardNum=TabledInfoPages.ardNum AND InfoPages.fundNum=Funds.fundNum AND InfoPages.ardNum<10000 ORDER BY fullName,subDate DESC;";
			System.out.println(queryString);
			ResultSet resultSet = statement.executeQuery(queryString);
			InfoPage [] infoPages = moveFromResultSetToInfoPage(resultSet);
			resultSet.beforeFirst();
			int i=0;
			while (resultSet.next()){
				if (! resultSet.getString("forms").trim().equals("xxxxx")) infoPages[i].setHasForms(true);
				i++;
			}
			return infoPages;
		}
		catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
        }
	}


 /*  This method is used to pick the documents that belongs to a research field
     it calculates if a document belongs to a research field by a simple algorithm
     follow it and you will understand it. remember that the presentation of the
     research fields is 1s and 0s, for example 10110 if there are 5 research fields
     where the left one is the first research field. IMPORTANT: the research fields
     serial numbers must be 'ratzif'. if you change the research fields you must change the data
     in database for example: to add a research field you have to multiply the researchFields
     column by 10 */

   public InfoPage[] getInfoPagesByResearchField(int researchFieldNum, boolean fullList, String orderBy ){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
   		ResultSet resultSet;
   		if ( (researchFieldNum>0) && (researchFieldNum<=getRowsCount("ResearchFields"))){


	     	/* An example can explain this method easily: if we are looking for documents that are medicine related (research field number 1)
    	 	 * so we are looking for documents with researchField value of 1****.... (number of digits depands on number of research fields)
	      	 * if we have 5 research fields : 5-1+1=5, 10pow5=100000 if (the value in researchField column for this document)%100000>=100000/10 then this document is
	      	 * medicine related.
    	  	 */


	     	int i=(getRowsCount("ResearchFields")-researchFieldNum)+1;
    		int j = (int)Math.pow(10,i);
    		int k = 1*j/10;
	    	resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE ardNum<10000 AND researchFields%"+j+">="+k
			+ (fullList ? "" : " AND ((subDate+86400000)>"+now+" OR subDate=0)" ) +" ORDER BY BINARY "+orderBy+",BINARY title");
        	return moveFromResultSetToInfoPage(resultSet);
   		}
   		else {
   			InfoPage [] infoPages = new InfoPage[1];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");
       		return infoPages ;
   		}
    }
    catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
    }
   }

   public InfoPage[] getInfoPagesByDocTypeAndDeskId(String docTypeName, boolean fullList, String orderBy){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
        ResultSet resultSet;

   	    if (docTypeName!=null){
   	    	String queryString = "SELECT * FROM InfoPages WHERE ardNum<10000 AND docType=\""+docTypeName+"\""
			+ (fullList ? "" : " AND ((subDate+86400000)>"+now+" OR subDate=0)" ) +" ORDER BY BINARY "+orderBy+",BINARY title;";
			resultSet = statement.executeQuery(queryString);

   	    }
   	    else resultSet = statement.executeQuery("SELECT * FROM InfoPages ORDER BY docType;");
   		return moveFromResultSetToInfoPage(resultSet);
   	}
   	catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No Results");

       		return infoPages ;
    }
   }

   public InfoPage[] getNewInfoPages(int daysAsNew){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
   		long timeAsNew = (long)daysAsNew*86400000; // translate from days to millisecends
   		long now = new java.util.Date().getTime();
   		ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE ardNum<10000 AND pubDate>"+(now-timeAsNew)+" ORDER BY pubDate DESC,title;");
   		return moveFromResultSetToInfoPage(resultSet);
   	}
   	catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
    }
   }

   public InfoPage[] getInfoPagesSortedByPubDate(){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
    	ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE ardNum<10000 ORDER BY pubDate DESC,title");
    	return moveFromResultSetToInfoPage(resultSet);
   	}
    catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[1];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
    }
   }

   public InfoPage[] getInfoPagesSortedBySubDate(){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
    	ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE ardNum<10000 ORDER BY subDate DESC,title");
    	return moveFromResultSetToInfoPage(resultSet);
   	}
    catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
    }
   }

   public String[] getKeywordsByResearchField(int researchFieldNum){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
     	int i=(getRowsCount("ResearchFields")-researchFieldNum)+1;
    	int j = (int)Math.pow(10,i);
    	int k = 1*j/10;
        String keywordsQueryString = "SELECT DISTINCT InfoPagesKeywords.keyword FROM InfoPagesKeywords"+
         " LEFT JOIN InfoPages ON InfoPagesKeywords.ardNum=InfoPages.ardNum WHERE"+
         " InfoPages.ardNum<10000 AND researchFields%"+j+">="+k+" ORDER BY keyword;";

    	ResultSet resultSet = statement.executeQuery(keywordsQueryString);
    	List keywordsList = new ArrayList();
    	while (resultSet.next()){
    		keywordsList.add(resultSet.getString("keyword"));
    	}
    	String[] keywordsString = new String[keywordsList.size()];
    	for (int l=0; l<keywordsString.length; l++){
    		keywordsString[l] = ((String)keywordsList.get(l)).toLowerCase();

    	}

   	    return keywordsString;
   	}
   	catch(SQLException e){
   		System.out.println("Exception while querying data: " + e);
   		String [] errorStrings = new String[1];
   		errorStrings[0] = "Exception Occured";
   		return errorStrings;
   	}

   }


   public InfoPage[] getInfoPagesByDocOwner(String docOwner){
   	try{
   		Statement statement = getCurrentConnection().createStatement();
    	ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPages WHERE ardNum<10000 AND docOwner=\""+docOwner+"\" ORDER BY ardNum, title;");
    	return moveFromResultSetToInfoPage(resultSet);
   	}
    catch(SQLException e){
       		System.out.println("Exception while querying data: " + e);
      		InfoPage [] infoPages = new InfoPage[0];
       		infoPages[0] = new InfoPage();
       		infoPages[0].setTitle("No results");

       		return infoPages ;
    }
   }

   public String getDocTypeLastUpdate(String docType){
   	try{
		Statement statement = getCurrentConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT lastUpdate FROM DocTypes WHERE docType=\""+docType+"\";");
		resultSet.next();
		String docTypeLastUpdateDate = Utils.getFormatedDate(resultSet.getLong("lastUpdate"));
		return docTypeLastUpdateDate;
   	}
   	catch(SQLException e){
   		System.out.println(e);
   		return "";
   	}
   }

   public String getResearchFieldLastUpdate(int researchFieldNum){
	   try{
		   Statement statement = getCurrentConnection().createStatement();
		   ResultSet resultSet = statement.executeQuery("SELECT lastUpdate FROM ResearchFields WHERE num="+researchFieldNum+";");
		   resultSet.next();
		   String researchFieldLastUpdateDate = Utils.getFormatedDate(resultSet.getLong("lastUpdate"));
		   return researchFieldLastUpdateDate;
	   }
	   catch(SQLException e){
		   System.out.println(e);
		   return "";
	   }
	  }

	public List getAssociatedWords(String word){
		List associatedWords = new ArrayList();
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM WordsAssociations WHERE word1=\""+word+"\" OR word2=\""+word+"\";");
			while (resultSet.next()){
				String word1 = resultSet.getString("word1");
				String word2 = resultSet.getString("word2");
				if (! word1.equals(word2))
					if (word1.equals(word)) associatedWords.add(word2);
					else associatedWords.add(word1);
			}

		}
		catch(SQLException e){
			System.out.println(e);
		}
		return associatedWords;
	}


   public int getResearchFieldNumByResearchFieldName(String researchField){
   		try{
   			Statement statement = getCurrentConnection().createStatement();
   			ResultSet resultSet = statement.executeQuery("SELECT num FROM ResearchFields WHERE researchField=\""+researchField+"\";");
   			resultSet.next();
   			return resultSet.getInt(1);
   		}
   		catch (SQLException e){
	   		System.out.println(e);
   			return 0;
   		}
   }


}
