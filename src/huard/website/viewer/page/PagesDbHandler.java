package huard.website.viewer.page;

import huard.website.baseDb.DbHandler;
import huard.website.baseDb.ManagedConnection;
import huard.website.model.PubPage;
import huard.website.model.Worker;
import huard.website.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PagesDbHandler extends DbHandler{

	public PagesDbHandler(){
		super();
	}

	public PubPage getPubPageByArdNum(int ardNum){
		PubPage pubPage = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE ardNum="+ardNum+";";
			ResultSet resultSet = statement.executeQuery(query);
			pubPage = moveFromResultSetToPubPage(resultSet)[0];
			pubPage.setKeywords(getPubPagesKeywordsByArdNum(""+ardNum));
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huardSiteViewer.pages.DbHandler.getPubPageByArdNum(): "+e);
			pubPage = new PubPage();
		}
		archiveConection(connection);
		return pubPage;
 	}

	public long getPubPageLastUpdate(int ardNum){
		long lastUpdate = 0;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT date FROM PubPagesLastUpdates WHERE ardNum="+ardNum;
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			lastUpdate = resultSet.getLong("date");
			statement.close();
		}
		catch (SQLException e){
			System.out.println("huardSiteViewer.pages.DbHandler.getPubPageLastUpdate(): "+e);
		}
		archiveConection(connection);
		return lastUpdate;
	}

	public PubPage[] getToRollingMessages(){
		PubPage [] pubPages = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE toRollingMessages=1 AND ardNum<10000";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			pubPages = moveFromResultSetToPubPage(resultSet);
			statement.close();
		}
		catch(SQLException e){
			System.out.println("getToRollingMessages"+e);
			pubPages = new PubPage[1];
			pubPages[0] = new PubPage();
		}
		archiveConection(connection);
		return pubPages;
	}

	public Worker [] getWorkersByPhraseFromTitleByDesk(String phraseFromTitle, String deskId){
		Worker [] workers = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM Workers WHERE englishTitle LIKE '%"+ phraseFromTitle +"%' AND " +
					"deskId=\"" + deskId + "\" ORDER BY englishName";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			workers = moveFromResultSetToWorkers(resultSet);
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
			workers = new Worker[1];
			workers[0] = new Worker();
		}
		archiveConection(connection);
		return workers;
	}

	public String [] getPictureName(){
		String [] pictureNamesArray = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT pictureName FROM mainPagePictures";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<String> pictureNameList = new ArrayList<String>();
			while (resultSet.next())
			{
				pictureNameList.add(resultSet.getString("pictureName"));
			}

			if (pictureNameList.size()>0){
				pictureNamesArray = new String[pictureNameList.size()];
				for (int i=0; i<pictureNamesArray.length; i++){
					pictureNamesArray[i] = (String)(pictureNameList.get(i));
				}
			}
			else {
				pictureNamesArray = new String [1];
				pictureNamesArray[0] = new String();
			}
			statement.close();

		}
		catch(SQLException e){
			e.printStackTrace();
			pictureNamesArray = new String[]{""};
		}
		archiveConection(connection);
		return pictureNamesArray;
	}

	public String [] getCaption(){
		String [] captionsArray = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT caption FROM mainPagePictures";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<String> captionList = new ArrayList<String>();
			while (resultSet.next())
			{
				captionList.add(resultSet.getString("caption"));
			}
			if (captionList.size()>0){
				captionsArray = new String[captionList.size()];
				for (int i=0; i<captionsArray.length; i++){
					captionsArray[i] = (String)(captionList.get(i));
				}
			}
			else {
				captionsArray = new String [1];
				captionsArray[0] = new String();
			}
			statement.close();

		}
		catch(SQLException e){
			e.printStackTrace();
			captionsArray = new String[]{""};
		}
		archiveConection(connection);
		return captionsArray;
	}

	public String [] getEnglishCaption(){
		String [] englishCaptionsArray;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT englishCaption FROM mainPagePictures";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<String> englishCaptionList = new ArrayList<String>();
			while (resultSet.next())
			{
				englishCaptionList.add(resultSet.getString("englishCaption"));
			}

			if (englishCaptionList.size()>0){
				englishCaptionsArray = new String[englishCaptionList.size()];
				for (int i=0; i<englishCaptionsArray.length; i++){
					englishCaptionsArray[i] = (String)(englishCaptionList.get(i));
				}
			}
			else {
				englishCaptionsArray = new String [1];
				englishCaptionsArray[0] = new String();
			}
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
			englishCaptionsArray = new String[]{""};
		}
		archiveConection(connection);
		return englishCaptionsArray;
	}

	public Worker [] getWorkerByEnglishName(String englishName){
		Worker [] workers = null;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM Workers WHERE englishName=\"" + englishName;
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			workers = moveFromResultSetToWorkers(resultSet);
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huardSiteViewer.pages.PagesDbHandler.getWorkerByEnglishName(): "+e);
		}
		archiveConection(connection);
		return workers;
	}

	public long getInfoPageLastUpdateDate(int ardNum){
		long lastUpdate = 0;
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT date FROM InfoPagesLastUpdates WHERE ardNum="+ardNum;
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			lastUpdate = resultSet.getLong("date");
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huardSiteViewer.pages.PagesDbHandler.getInfoPageLastUpdate(): " + e);
		}
		archiveConection(connection);
		return lastUpdate;
	}

	public String getSiteLastUpdate(){
		String lastUpdateDate = "";
		ManagedConnection connection = getConnection();
		try{
			Statement statement = connection.createStatement();
			String query = "SELECT MAX(date) FROM InfoPagesLastUpdates;";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			long x=resultSet.getLong(1);
			query = "SELECT MAX(date) FROM PubPagesLastUpdates;";
			System.out.println(query);
			ResultSet resultSet2 = statement.executeQuery(query);
			resultSet2.next();
			long y=resultSet2.getLong(1);
			if(x>y)
				lastUpdateDate = Utils.getFormatedDate(x);
			else
				lastUpdateDate = Utils.getFormatedDate(y);
			statement.close();
		}
		catch(SQLException e){
			System.out.println("huardSiteViewer.pages.PagesDbHandler.getSiteLastUpdate(): "+e);
		}
		archiveConection(connection);
		return lastUpdateDate;
	}
}
