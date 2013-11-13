package huard3.utils;
import huard3.actions.*;
import java.util.*;

import java.sql.SQLException;
import java.sql.Statement;


public class InfoPagesUpdate extends UpdateDatabase {

	public InfoPagesUpdate(){
		super();
	}

	public void insertResearchFields(int ardNum,int researchFields){
   		try{
    		Statement statement = getCurrentConnection().createStatement();
    	    String updateResearchFields = "UPDATE InfoPages SET researchFields=\""+researchFields+"\" WHERE ardNum=\""+ardNum+"\"";
    	    statement.executeUpdate(updateResearchFields);
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);

    	}

    }

	public void copyInfoPage (int oldArdNum, int newArdNum){
   		try{
    		Statement s = getCurrentConnection().createStatement();
    		String tempTable ="Tbl_"+ (""+new Date().getTime()).substring(5)+oldArdNum+newArdNum;
    		s.executeUpdate("CREATE  TEMPORARY TABLE "+tempTable+" SELECT * FROM InfoPages WHERE ardNum='"+oldArdNum+"';");
    		System.out.println("UPDATE "+tempTable+" SET ardNum="+newArdNum+", title=concat(title, ' - copy'), hasTabledVersion=0, isDraft=1;");
    		s.executeUpdate("UPDATE "+tempTable+" SET ardNum="+newArdNum+", title=concat(title, ' - copy'), hasTabledVersion=0, isDraft=1;");
    		s.executeUpdate("INSERT InfoPages SELECT * FROM "+tempTable+";");

    		tempTable ="Tbl_"+ (""+new Date().getTime()).substring(5)+oldArdNum+newArdNum;
    		s.executeUpdate("CREATE  TEMPORARY TABLE "+tempTable+" SELECT * FROM InfoPagesKeywords WHERE ardNum='"+oldArdNum+"';");
    		System.out.println("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("INSERT InfoPagesKeywords SELECT * FROM "+tempTable+";");

    		tempTable ="Tbl_"+ (""+new Date().getTime()).substring(5)+oldArdNum+newArdNum;
    		s.executeUpdate("CREATE  TEMPORARY TABLE "+tempTable+" SELECT * FROM TabledInfoPages WHERE ardNum='"+oldArdNum+"';");
    		System.out.println("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("INSERT TabledInfoPages SELECT * FROM "+tempTable+";");

    		tempTable ="Tbl_"+ (""+new Date().getTime()).substring(5)+oldArdNum+newArdNum;
    		s.executeUpdate("CREATE  TEMPORARY TABLE "+tempTable+" SELECT * FROM AdditionalSubDates WHERE ardNum='"+oldArdNum+"';");
    		System.out.println("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("UPDATE "+tempTable+" SET ardNum="+newArdNum+";");
    		s.executeUpdate("INSERT AdditionalSubDates SELECT * FROM "+tempTable+";");
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);

    	}

    }


    public boolean updateOrInsertInfoPagesDetailsIntoInfoPagesTable(InfoPage infoPage, boolean aNewInfoPage){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();
    		System.out.println(getFullInfoPageUpdateString(infoPage,aNewInfoPage));
    		updateStatement.executeUpdate(getFullInfoPageUpdateString(infoPage,aNewInfoPage));
			updateStatement.executeUpdate("DELETE FROM InfoPagesLastUpdates WHERE ardNum="+infoPage.getArdNum()+";");
						updateStatement.executeUpdate("INSERT InfoPagesLastUpdates SET ardNum="+infoPage.getArdNum()+", date="+new Date().getTime()+";");
    		return true;
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return false;
    	}
    }

	public boolean updateOrInsertInfoPagesDetailsIntoInfoPagesNewTable(InfoPage infoPage){
			try{
				Statement updateStatement = getCurrentConnection().createStatement();
				updateStatement.executeUpdate(getFullInfoPageNewUpdateString(infoPage,true));
				return true;
			}
			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				return false;
			}
		}


    public String getFullInfoPageUpdateString(InfoPage infoPage,boolean aNewInfoPage){
    	if(!aNewInfoPage){
    		String updateString = "UPDATE InfoPages SET title=\""+infoPage.getTitle()+
        	"\",pubDate=\""+infoPage.getPubDate()+
        	"\",subDate=\""+infoPage.getSubDate()+
        	"\",fundNum=\""+infoPage.getFundNum()+
        	"\",fundShortName=\""+infoPage.getFundShortName()+
        	"\",docType=\""+infoPage.getDocType()+
   		 	"\",deskId=\""+infoPage.getDeskId()+
  		  	"\",researchFields=\""+infoPage.getResearchFields()+
  		  	"\",hasTabledVersion=\""+( infoPage.isHasTabledVersion() ? 1 : 0 ) +
   		 	"\",docOwner=\""+infoPage.getDocOwner()+
    		"\",repetitive=\""+( infoPage.isRepetitive() ? 1 : 0 ) +
    		"\",restricted=\""+( infoPage.isRestricted() ? 1 : 0 ) +
			"\",hasLocalWebPage=\""+( infoPage.isHasLocalWebPage() ? 1 : 0 ) +
			"\",pageWebAddress=\""+infoPage.getPageWebAddress()+
    		"\",hasAdditionalSubDates=\""+( infoPage.isHasAdditionalSubDates() ? 1 : 0 ) +
			"\",keepInRollingMessages=\""+( infoPage.isKeepInRollingMessages() ? 1 : 0 ) +
			"\",stopRollingDate=\""+infoPage.getStopRollingDate()+
			"\",descriptionOnly=\""+( infoPage.isDescriptionOnly() ? 1 : 0 ) +
			(infoPage.isHasTabledVersion() ? "\", isDraft=0" : "\"") +
			" WHERE ardNum=\""+infoPage.getArdNum()+"\";";
  			return updateString;
    	}
    	else{
            String updateString = "INSERT INTO InfoPages SET ardNum=("
            +infoPage.getArdNum()+") " +
            ",title=(\"" +Utils.moveHebrewCharsFromAsciiToHebrewCharset(infoPage.getTitle())+"\")" +
            ",pubDate=(" +infoPage.getPubDate()+")" +
            ",subDate=(" +infoPage.getSubDate()+")" +
            ",fundNum=(" +infoPage.getFundNum()+")" +
            ",fundShortName=(\"" +infoPage.getFundShortName()+"\")" +
            ",docType=(\"" +infoPage.getDocType()+"\")" +
            ",deskId=(\"" +infoPage.getDeskId()+"\")" +
            ",researchFields=(" +infoPage.getResearchFields()+")" +
            ",hasTabledVersion=(" +( infoPage.isHasTabledVersion() ? 1 : 0 ) +")" +
            ",docOwner=(\"" +infoPage.getDocOwner()+"\")" +
            ",repetitive=(" +( infoPage.isRepetitive() ? 1 : 0 )+")" +
            ",restricted=(" +( infoPage.isRestricted() ? 1 : 0 )+")" +
            ",hasAdditionalSubDates=(" +( infoPage.isHasAdditionalSubDates() ? 1 : 0 )+")" +
			",keepInRollingMessages=(" +( infoPage.isKeepInRollingMessages() ? 1 : 0 )+")" +
			",stopRollingDate=(" +infoPage.getStopRollingDate()+")" +
            ",hasLocalWebPage=(" +( infoPage.isHasLocalWebPage() ? 1 : 0 )+")" +
            ",descriptionOnly=("+( infoPage.isDescriptionOnly() ? 1 : 0 ) +")" +
            ",pageWebAddress=(\"" + infoPage.getPageWebAddress() + "\")" +
            ",isDraft=1;";
            return updateString;
    	}
    }



	public String getFullInfoPageNewUpdateString(InfoPage infoPage,boolean aNewInfoPage){
			if(!aNewInfoPage){
				String updateString = "UPDATE InfoPagesNew SET title=\""+infoPage.getTitle()+
				"\",pubDate=\""+infoPage.getPubDate()+
				"\",subDate=\""+infoPage.getSubDate()+
				"\",fundNum=\""+infoPage.getFundNum()+
				"\",fundShortName=\""+infoPage.getFundShortName()+
				"\",docType=\""+infoPage.getDocType()+
				"\",deskId=\""+infoPage.getDeskId()+
				"\",researchFields=\""+infoPage.getResearchFields()+
				"\",hasTabledVersion=\""+( infoPage.isHasTabledVersion() ? 1 : 0 ) +
				"\",docOwner=\""+infoPage.getDocOwner()+
				"\",repetitive=\""+( infoPage.isRepetitive() ? 1 : 0 ) +
				"\",restricted=\""+( infoPage.isRestricted() ? 1 : 0 ) +
				"\",hasLocalWebPage=\""+( infoPage.isHasLocalWebPage() ? 1 : 0 ) +
				"\",pageWebAddress=\""+infoPage.getPageWebAddress()+
				"\",hasAdditionalSubDates=\""+( infoPage.isHasAdditionalSubDates() ? 1 : 0 ) +
				"\" WHERE ardNum=\""+infoPage.getArdNum()+"\";";
				return updateString;
			}
			else{
				String updateString = "INSERT INTO InfoPagesNew SET ardNum=("
				+infoPage.getArdNum()+") " +
				",title=(\"" +infoPage.getTitle()+"\")" +
				",pubDate=(" +infoPage.getPubDate()+")" +
				",subDate=(" +infoPage.getSubDate()+")" +
				",fundNum=(" +infoPage.getFundNum()+")" +
				",fundShortName=(\"" +infoPage.getFundShortName()+"\")" +
				",docType=(\"" +infoPage.getDocType()+"\")" +
				",deskId=(\"" +infoPage.getDeskId()+"\")" +
				",researchFields=(" +infoPage.getResearchFields()+")" +
				",hasTabledVersion=(" +( infoPage.isHasTabledVersion() ? 1 : 0 ) +")" +
				",docOwner=(\"" +infoPage.getDocOwner()+"\")" +
				",repetitive=(" +( infoPage.isRepetitive() ? 1 : 0 )+")" +
				",restricted=(" +( infoPage.isRestricted() ? 1 : 0 )+")" +
				",hasAdditionalSubDates=(" +( infoPage.isHasAdditionalSubDates() ? 1 : 0 )+")" +
				",hasLocalWebPage=(" +( infoPage.isHasLocalWebPage() ? 1 : 0 )+")" +
				",pageWebAddress=(\"" + infoPage.getPageWebAddress() + "\");";

				return updateString;
			}
		}

    public boolean insertInfoPageKeywordsIntoDatabase(int ardNum,String[] keywords){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();

   	    	updateStatement.executeUpdate("DELETE FROM InfoPagesKeywords WHERE ardNum="+ardNum+";");
    	    for(int i=0;i<keywords.length;i++){

    	    	String updateString = "INSERT INTO InfoPagesKeywords (ardNum,keyword) VALUES ('"+ardNum+"','"+keywords[i]+"');" ;
    	    	updateStatement.executeUpdate( updateString );
    	    }
    	    return true;
    	}
    	catch(SQLException e){
    		System.out.println("Exception while updateing keywords: " + e);
    		return false;
    	}
    }

    public boolean insertInfoPageAdditionalSubDatesIntoDatabase(int ardNum, long secondSubDate, long thirdSubDate, long fourthSubDate){
    	try{
    		Statement updateStatement = getCurrentConnection().createStatement();
    		updateStatement.executeUpdate("DELETE FROM AdditionalSubDates WHERE ardNum="+ardNum+";");
        	if (secondSubDate!=0) updateStatement.executeUpdate("INSERT INTO AdditionalSubDates (ardNum,subDate) VALUES ('"+ardNum+"','"+secondSubDate+"');");
    	    if (thirdSubDate!=0)  updateStatement.executeUpdate("INSERT INTO AdditionalSubDates (ardNum,subDate) VALUES ('"+ardNum+"','"+thirdSubDate+"');");
        	if (fourthSubDate!=0) updateStatement.executeUpdate("INSERT INTO AdditionalSubDates (ardNum,subDate) VALUES ('"+ardNum+"','"+fourthSubDate+"');");


        	return true;
    	}
    	catch(SQLException e){
    		System.out.println("Exception while updating additional submission dates: " + e);
    		return false;
    	}
    }

     /*public void deleteInfoPageByArdNum(String ardNum){
    	try{
    		InternalUseInfoPagesQuery infoPageQuery = new InternalUseInfoPagesQuery();
    		Statement statement = getCurrentConnection().createStatement();
    		InfoPage infoPage = infoPageQuery.getInfoPageDetailsByArdNum(ardNum);
    		int oldInfoPageArdNum = infoPageQuery.getFirstOldsFreeArdNum("InfoPages");

    		statement.executeUpdate("UPDATE InfoPages SET ardNum="+ oldInfoPageArdNum+ " WHERE ardNum="+infoPage.getArdNum()+";");
    		statement.executeUpdate("UPDATE InfoPagesKeywords SET ardNum="+ oldInfoPageArdNum +" WHERE ardNum="+infoPage.getArdNum()+";");
    		statement.executeUpdate("UPDATE AdditionalSubDates SET ardNum="+ oldInfoPageArdNum +" WHERE ardNum="+infoPage.getArdNum()+";");
			statement.executeUpdate("UPDATE TabledInfoPages SET ardNum="+ oldInfoPageArdNum +" WHERE ardNum="+infoPage.getArdNum()+";");
			statement.executeUpdate("DELETE FROM InfoPagesLastUpdates WHERE ardNum="+ardNum+";");
			statement.executeUpdate("DELETE FROM InfoPagesIndex WHERE ardNum="+ardNum+";");
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);

    	}
    }*/

    public void deleteInfoPageByArdNum(String ardNum){
    	try{

    		Statement statement = getCurrentConnection().createStatement();
    		statement.executeUpdate("UPDATE InfoPages SET isDeleted=1, hasTabledVersion=0 WHERE ardNum='"+ardNum+"';");
     	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    	}
    }

    public boolean reviveInfoPage(InfoPage infoPage){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
            statement.executeUpdate("UPDATE InfoPages SET isDeleted=0 WHERE ardNum="+ infoPage.getArdNum() +";");
    		return true;
    	}
    	catch(SQLException e){
    		System.out.println("Exception while querying data: " + e);
    		return false;
    	}
    }


	public void updateResearchFieldsLastUpdates(int[] researchFieldsIntArray, long now){
		try{
			System.out.println("Connection is null ? " + (getCurrentConnection() == null));
			Statement statement = getCurrentConnection().createStatement();
			for (int i=0; i< researchFieldsIntArray.length; i++){
				if (researchFieldsIntArray[i]==1) statement.executeUpdate("UPDATE ResearchFields SET lastUpdate="+now+" WHERE num="+(i+1)+";");
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void updateDocTypesLastUpdates(String docType, long now){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("UPDATE DocTypes SET lastUpdate="+now+" WHERE docType=\""+docType+"\";");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}




    public void moveSubDatesOneStepForward(int ardNum){
   	try{
   		long [] additionalSubDates = new InternalUseInfoPagesQuery().getAdditionalSubDates(""+ardNum);
   		Statement statement = getCurrentConnection().createStatement();
   		statement.executeUpdate("UPDATE InfoPages SET subDate="+additionalSubDates[0]+" WHERE ardNum="+ardNum+";");
   		statement.executeUpdate("DELETE FROM AdditionalSubDates WHERE (ardNum="+ardNum+" AND subDate="+additionalSubDates[0]+");");
   		if (additionalSubDates.length==1) statement.executeUpdate("UPDATE InfoPages SET hasAdditionalSubDates=0 WHERE ardNum="+ardNum+";");
   		LogFileHandler.writeToUserLog("update", "huard", new InfoPagesQuery().getInfoPageDetailsByArdNumSkipKeywords(""+ardNum,"InfoPages"), "Additional submission dates switch");
   	}
   	catch(SQLException e){
    		System.out.println("Exception while updating subDates: " + e);
    }
   }

   public boolean updateOrInsertTabledInfoPagesDetailsIntoTabledInfoPagesTable(TabledInfoPage tabledInfoPage){
		   try{
			   Statement updateStatement = getCurrentConnection().createStatement();
			   String updateString = getFullTabledInfoPageUpdateString(tabledInfoPage,!(new QueryDatabase().isRecoredExistsInTable(""+tabledInfoPage.getArdNum(), "TabledInfoPages")));
			   System.out.println(updateString);
			   updateStatement.executeUpdate(updateString);
			   updateStatement.executeUpdate("DELETE FROM InfoPagesLastUpdates WHERE ardNum="+tabledInfoPage.getArdNum()+";");
			   updateStatement.executeUpdate("INSERT InfoPagesLastUpdates SET ardNum="+tabledInfoPage.getArdNum()+", date="+new Date().getTime()+";");

			   return true;
		   }
		   catch(SQLException e){
			   System.out.println("Exception while updating data: " + e);
			   return false;
		   }
	   }





	   public String getFullTabledInfoPageUpdateString(TabledInfoPage tabledInfoPage, boolean newRecored){
		   if (newRecored){
			   String updateString = "INSERT INTO TabledInfoPages SET ardNum=("+tabledInfoPage.getArdNum()+"),"+
			   "subSite=(\""+tabledInfoPage.getSubSite()+"\"),"+
			   "subDateArd=(\""+tabledInfoPage.getSubDateArd()+"\"),"+
			   "subDateFund=(\""+tabledInfoPage.getSubDateFund()+"\"),"+
			   "subDateDetails=(\""+tabledInfoPage.getSubDateDetails()+"\"),"+
			   "deskAndContact=(\""+tabledInfoPage.getDeskAndContact()+"\"),"+
			   "forms=(\""+tabledInfoPage.getForms()+"\"),"+
			   "description=(\""+tabledInfoPage.getDescription()+"\"),"+
			   "maxFundingPeriod=(\""+tabledInfoPage.getMaxFundingPeriod()+"\"),"+
			   "amountOfGrant=(\""+tabledInfoPage.getAmountOfGrant()+"\"),"+
			   "eligibilityRequirements=(\""+tabledInfoPage.getEligibilityRequirements()+"\"),"+
			   "activityLocation=(\""+tabledInfoPage.getActivityLocation()+"\"),"+
			   "possibleCollaboration=(\""+tabledInfoPage.getPossibleCollaboration()+"\"),"+
			   "budgetDetails=(\""+tabledInfoPage.getBudgetDetails()+"\"),"+
			   "numOfCopies=(\""+tabledInfoPage.getNumOfCopies()+"\"),"+
			   "additionalInformation=(\""+tabledInfoPage.getAdditionalInformation()+"\"),"+
			   "sendDigitalCopy=(\""+(tabledInfoPage.isSendDigitalCopy() ? 1 : 0)+"\"),"+
			   "appendBudgetOfficerLine=(\""+(tabledInfoPage.isAppendBudgetOfficerLine() ? 1 : 0)+"\")";

			   updateString = tripleAllBackslashes(updateString);
			   return markDoubleQuatsWithBackSlash(updateString);
		   }
		   else{
			   String updateString = "UPDATE TabledInfoPages SET " +
			   "subSite=\""+tabledInfoPage.getSubSite()+"\","+
			   "subDateArd=\""+tabledInfoPage.getSubDateArd()+"\","+
			   "subDateFund=\""+tabledInfoPage.getSubDateFund()+"\","+
			   "subDateDetails=\""+tabledInfoPage.getSubDateDetails()+"\","+
			   "deskAndContact=\""+tabledInfoPage.getDeskAndContact()+"\","+
			   "forms=\""+tabledInfoPage.getForms()+"\","+
			   "description=\""+tabledInfoPage.getDescription()+"\","+
			   "maxFundingPeriod=\""+tabledInfoPage.getMaxFundingPeriod()+"\","+
			   "amountOfGrant=\""+tabledInfoPage.getAmountOfGrant()+"\","+
			   "eligibilityRequirements=\""+tabledInfoPage.getEligibilityRequirements()+"\","+
			   "activityLocation=\""+tabledInfoPage.getActivityLocation()+"\","+
			   "possibleCollaboration=\""+tabledInfoPage.getPossibleCollaboration()+"\","+
			   "budgetDetails=\""+tabledInfoPage.getBudgetDetails()+"\","+
			   "numOfCopies=\""+tabledInfoPage.getNumOfCopies()+"\","+
			   "additionalInformation=\""+tabledInfoPage.getAdditionalInformation()+"\","+
			   "sendDigitalCopy=\""+(tabledInfoPage.isSendDigitalCopy() ? 1 : 0 )+"\","+
			   "appendBudgetOfficerLine=\""+(tabledInfoPage.isAppendBudgetOfficerLine() ? 1 : 0 )+"\""+
			   " WHERE ardNum="+tabledInfoPage.getArdNum()+";";
			   updateString = tripleAllBackslashes(updateString);
			   return markDoubleQuatsWithBackSlash(updateString);

		   }


	   }
	public String tripleAllBackslashes(String s){
			StringBuffer sb = new StringBuffer();
			int pos;
			while ((pos = s.indexOf("\\"))!=-1){
				sb.append(s.substring(0, pos)+"\\\\\\");
				s = s.substring(pos+1);
			}
			sb.append(s);
			return sb.toString();
		}

	public String markDoubleQuatsWithBackSlash(String str){
			return str.replaceAll("\"","\\\"");
	}


	public void deleteFromInfoPagesIndexTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM InfoPagesIndex;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void insertWordToInfoPagesIndexTable(String word,int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("INSERT IGNORE InfoPagesIndex SET word=\""+word+"\", ardNum="+ardNum);
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}

	public void createInfoPagesIndexCopyTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM InfoPagesIndex_copy;");
			statement.executeUpdate("INSERT InfoPagesIndex_copy SELECT * FROM InfoPagesIndex;");
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}
	public void stopInfoPageFromRolling(int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("UPDATE InfoPages SET keepInRollingMessages=0, stopRollingDate=0 WHERE ardNum="+ardNum+";");
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}






}
