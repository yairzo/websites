package huard3.utils;
import java.sql.*;
import huard3.actions.*;
import java.util.*;

public class PubPagesQuery extends PageQuery{

	public PubPagesQuery() {
		super();
	}

	public PubPage getPubPageByArdNum(int ardNum){
			 try{
				Statement statement = getCurrentConnection().createStatement();
				String mainQuery = "SELECT * FROM PubPages WHERE ardNum="+ardNum+";";
				ResultSet resultSet = statement.executeQuery(mainQuery);
				PubPage pubPage = moveFromResultSetToPubPage(resultSet)[0];
				pubPage.setKeywords(getPubPagesKeywordsByArdNum(""+ardNum));
				return pubPage;

				}
			catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				return new PubPage();
			}


		}

	public PubPage[] getPubPages(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE fileRepresentation=0");
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return new PubPage[0];
		}
	}


	public PubPage[] getNewMessagesByDocType(int daysAsNew, String docType){
		try{
			Statement statement = getCurrentConnection().createStatement();
			long timeAsNew = (long)(daysAsNew*86400000); // translate from days to millisecends
			long now = new java.util.Date().getTime();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE docType=\""+docType+"\" AND ardNum<10000 AND message=1 and onSite=1 AND " +"pubDate>"+(now-timeAsNew)+" ORDER BY pubDate DESC,title;");

			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
				System.out.println("Exception while querying data: " + e);
				PubPage [] pubPages = new PubPage[0];
				pubPages[0] = new PubPage();
				pubPages[0].setTitle("No results");

				return pubPages ;
		}
	   }


	public PubPage[] moveFromResultSetToPubPage(ResultSet resultSet){
		try{
			List<PubPage> pubPagesList = new ArrayList<PubPage>();
			while (resultSet.next()){
				PubPage pubPage = new PubPage();
				pubPage.setArdNum(resultSet.getInt("ardNum"));
				pubPage.setTitle(resultSet.getString("title"));
				pubPage.setHtml(resultSet.getString("html"));
				pubPage.setDocType(resultSet.getString("docType"));
				pubPage.setPubDate(resultSet.getLong("pubDate"));
				pubPage.setDeskId(resultSet.getString("deskId"));
				pubPage.setDocOwner(resultSet.getString("docOwner"));
				pubPage.setRestricted(resultSet.getBoolean("restricted"));
				pubPage.setMessage(resultSet.getInt("message")==1);
				pubPage.setExpDate(resultSet.getLong("expDate"));
				pubPage.setToRollingMessages(resultSet.getInt("toRollingMessages")==1);
				pubPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
				pubPage.setHasImage(resultSet.getBoolean("hasImage"));
				pubPage.setImageFilename(resultSet.getString("imageFilename"));
				pubPage.setFileRepresentation(resultSet.getBoolean("fileRepresentation"));
				pubPage.setLink(resultSet.getString("link"));
				pubPage.setReferenceFile(resultSet.getString("referenceFile"));
				pubPage.setSource(resultSet.getString("source"));
				pubPage.setInternalUseDescription(resultSet.getString("internalUseDescription"));
				pubPage.setWraper(resultSet.getBoolean("wraper"));
				pubPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
				pubPage.setOnSite(resultSet.getBoolean("onSite"));
				pubPage.setHebrewEdit(resultSet.getBoolean("hebrewEdit"));
				pubPage.setDeleted(resultSet.getBoolean("isDeleted"));
				pubPagesList.add(pubPage);
				}

			PubPage[] pubPagesArray = new PubPage[pubPagesList.size()>0 ? pubPagesList.size() : 1];
			pubPagesArray[0] = new PubPage();
			for (int i=0;i<pubPagesList.size();i++){
				pubPagesArray[i]=(PubPage)pubPagesList.get(i);
			}
			return pubPagesArray;
		}
		catch (SQLException e){
			System.out.println(e);
			PubPage[] noPubPages = new PubPage[1];
			noPubPages[0] = new PubPage();
			noPubPages[0].setTitle("No results");
			return noPubPages;
		}
	}

	public List<PubPage> moveBaseParamsAndFoundBySearchWordsFromResultSetToPubPagesArray(ResultSet resultSet){
		List<PubPage> pubPagesList = new ArrayList<PubPage>();
		try{
			while (resultSet.next()){
				PubPage pubPage = new PubPage();
				pubPage.setArdNum(resultSet.getInt("ardNum"));
				pubPage.setTitle(resultSet.getString("title"));
				pubPage.setFileRepresentation(resultSet.getBoolean("fileRepresentation"));
				pubPage.setLink(resultSet.getString("link"));
				pubPage.setFoundBySearchWords(resultSet.getString("word1")+","+resultSet.getString("word2"));
				pubPage.setDeleted(resultSet.getBoolean("deleted"));
				pubPagesList.add(pubPage);
			}

			}
		catch (SQLException e){
			System.out.println(e);
		}
		return pubPagesList;
	}

	public String getPubPagesKeywordsByArdNum(String ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPagesKeywords WHERE ardNum=\""+ardNum+"\";");
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

	public long getPubPageLastUpdateDate(int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT date FROM PubPagesLastUpdates WHERE ardNum="+ardNum+";");
			resultSet.next();
			return resultSet.getLong("date");
		}
		catch (SQLException e){
			System.out.println(e);
			return 0;
		}
	}

	public PubPage[] getMessagesByDocType(String docType){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE docType=\""+docType+"\" AND message=1 AND ardNum<10000 ORDER BY pubDate DESC");
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			PubPage [] pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
			return pubPages;
		}
	}

	public PubPage[] getMessages(){
			try{
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE message=1 AND ardNum<10000 ORDER BY pubDate DESC");
				return moveFromResultSetToPubPage(resultSet);
			}
			catch(SQLException e){
				System.out.println(e);
				PubPage [] pubPages = new PubPage[1];
				pubPages[0] = new PubPage();
				return pubPages;
			}
		}

	public PubPage[] getToRollingMessages(){
			try{
				Statement statement = getCurrentConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE toRollingMessages=1 AND ardNum<10000");
				return moveFromResultSetToPubPage(resultSet);
			}
			catch(SQLException e){
				System.out.println("getToRollingMessages"+e);
				PubPage [] pubPages = new PubPage[1];
				pubPages[0] = new PubPage();
				return pubPages;
			}
		}




	public String getMessagesLastUpdate(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			String queryString = "SELECT MAX(date) FROM PubPagesLastUpdates LEFT JOIN PubPages ON PubPagesLastUpdates.ardNum=PubPages.ardNum"+
				" WHERE PubPages.message=1;";
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

	public PubPage[] getPubPagesNotUpdatedForOneYear(long now){
		try{
			Statement statement = getCurrentConnection().createStatement(); 										//one year in ms
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages LEFT JOIN PubPagesLastUpdates ON PubPages.ardNum=PubPagesLastUpdates.ardNum WHERE PubPages.ardNum<10000 AND PubPages.message=0 AND PubPages.onSite=1 AND PubPagesLastUpdates.date <"+(now - 3153600000L)+";");
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return new PubPage [0];
		}
	}

	public PubPage[] getAliveAndOnSitePubPages(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPages WHERE ardNum<10000 AND onSite=1;");
			return moveFromResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return new PubPage [0];
		}
	}


}
