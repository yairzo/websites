package huard.iws.db;

import huard.iws.model.PageUrl;
import huard.iws.model.TextualPageOld;
import huard.iws.util.ListView;
import huard.iws.util.SQLUtils;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class JdbcUrlsCheckerDao extends SimpleJdbcDaoSupport implements UrlsCheckerDao {

	public void markExistingRowsInInfoPagesUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "UPDATE InfoPagesURLs SET thisBuild=0;";
			logger.debug(query);
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	/* moved to JdbcCallForProposal
	public List<CallForProposal> getAliveTabledInfoPages(Integer ardNum,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			java.util.Date now = new java.util.Date();
			String query = "SELECT * FROM InfoPages,TabledInfoPages WHERE"+
				" InfoPages.ardNum = TabledInfoPages.ardNum AND InfoPages.isDeleted=0 "+
				(ardNum!=null ? " AND InfoPages.ardNum="+ardNum+";"  : " AND InfoPages.subDate>"+now.getTime()+
						" ORDER BY RAND();");
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			return moveResultSetToTabledInfoPages(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public List<CallForProposal> moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
		List<CallForProposal> tabledInfoPages = new ArrayList<CallForProposal>();
		while (resultSet.next()){
			CallForProposal tabledInfoPage = new CallForProposal();
			tabledInfoPage.setId(resultSet.getInt("ardNum"));
			tabledInfoPage.setTitle(resultSet.getString("title"));
			tabledInfoPage.setPublicationTimeMillis(resultSet.getLong("pubDate"));
			tabledInfoPage.setFundId(resultSet.getInt("fundNum"));
			//tabledInfoPage.setFundShortName(resultSet.getString("fundShortName"));
			tabledInfoPage.setSubmissionTimeMillis(resultSet.getLong("subDate"));
			//tabledInfoPage.setDocType(resultSet.getString("docType"));
			tabledInfoPage.setDeskId(resultSet.getString("deskId"));
			//tabledInfoPage.setResearchFields(resultSet.getInt("researchFields"));
			//tabledInfoPage.setHasTabledVersion(resultSet.getBoolean("hasTabledVersion"));
			//tabledInfoPage.setRepetitive(resultSet.getBoolean("repetitive"));
			//tabledInfoPage.setDocOwner(resultSet.getString("docOwner"));
			//tabledInfoPage.setRestricted(resultSet.getBoolean("restricted"));
			//tabledInfoPage.setHasAdditionalSubDates(resultSet.getBoolean("hasAdditionalSubDates"));
			//tabledInfoPage.setHasLocalWebPage(resultSet.getBoolean("hasLocalWebPage"));
			tabledInfoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
			tabledInfoPage.setDescriptionOnly(resultSet.getBoolean("descriptionOnly"));
			//tabledInfoPage.setSubSite(resultSet.getString("subSite"));
			//tabledInfoPage.setSubDateArd(resultSet.getString("subDateArd"));
			//tabledInfoPage.setSubDateFund(resultSet.getString("subDateFund"));
			//tabledInfoPage.setSubDateDetails(resultSet.getString("subDateDetails"));
			tabledInfoPage.setDeskAndContact(resultSet.getString("deskAndContact"));
			tabledInfoPage.setForms(resultSet.getString("forms"));
			tabledInfoPage.setDescription(resultSet.getString("description"));
			//tabledInfoPage.setMaxFundingPeriod(resultSet.getString("maxFundingPeriod"));
			tabledInfoPage.setAmountOfGrant(resultSet.getString("amountOfGrant"));
			//tabledInfoPage.setEligibilityRequirements(resultSet.getString("eligibilityRequirements"));
			//tabledInfoPage.setActivityLocation(resultSet.getString("activityLocation"));
			//tabledInfoPage.setPossibleCollaboration(resultSet.getString("possibleCollaboration"));
			tabledInfoPage.setBudgetDetails(resultSet.getString("budgetDetails"));
			//tabledInfoPage.setNumOfCopies(resultSet.getInt("numOfCopies"));
			tabledInfoPage.setAdditionalInformation(resultSet.getString("additionalInformation"));
			tabledInfoPages.add(tabledInfoPage);
		}
		return tabledInfoPages;
	}*/

	public void insertTabledInfoPagesURLsTable(int ardNum, List<PageUrl> URLsList,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			for (PageUrl pageUrl: URLsList){
				if (!pageUrl.isValid())
					continue;
				String query = "UPDATE InfoPagesURLs SET ardNum="+ardNum+
				", urlText='" + SQLUtils.toSQLString(pageUrl.getUrlText()) + "', formerFileSize=fileSize, fileSize=0," +
				" thisBuild=1 WHERE ardNum=" + ardNum + " AND URL='" + pageUrl.getUrl() + "'";
				logger.debug(query);
				int r = statement.executeUpdate(query);

				if (r==0) {
					query = "INSERT InfoPagesURLs (ardNum, URL, urlText, thisBuild, checkedTime) VALUES ("
							+ ardNum + ", '" + pageUrl.getUrl() + "', '" + SQLUtils.toSQLString(pageUrl.getUrlText()) + "', 1, NOW());";
					logger.debug(query);
					statement.executeUpdate(query);
				}
			}
		}
		catch(SQLException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	public void deleteOldRowsFromInfoPagesUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "DELETE FROM InfoPagesURLs WHERE thisBuild=0";
			logger.debug(query);
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public void markExistingRowsInPubPagesUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE PubPagesURLs SET thisBuild=0 ;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public List<TextualPageOld> getAliveAndOnSitePubPages(Integer ardNum,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPages WHERE onSite=1 AND isDeleted=0 "
			+(ardNum!=null ? "AND PubPages.ardNum="+ardNum+";"  : "ORDER BY RAND();");
			logger.debug(query);
			ResultSet resultSet = statement.executeQuery(query);
			return moveResultSetToPubPage(resultSet);
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public List<TextualPageOld> moveResultSetToPubPage(ResultSet resultSet){
		try{
			List<TextualPageOld> pubPages = new ArrayList<TextualPageOld>();
			while (resultSet.next()){
				TextualPageOld pubPage = new TextualPageOld();
				pubPage.setId(resultSet.getInt("ardNum"));
				pubPage.setTitle(resultSet.getString("title"));
				pubPage.setHtml(resultSet.getString("html"));
				//pubPage.setDocType(resultSet.getString("docType"));
				//pubPage.setPubDate(resultSet.getLong("pubDate"));
				//pubPage.setDeskId(resultSet.getString("deskId"));
				//pubPage.setDocOwner(resultSet.getString("docOwner"));
				//pubPage.setRestricted(resultSet.getBoolean("restricted"));
				//pubPage.setMessage(resultSet.getInt("message")==1);
				//pubPage.setExpDate(resultSet.getLong("expDate"));
				//pubPage.setToRollingMessages(resultSet.getInt("toRollingMessages")==1);
				//pubPage.setStopRollingDate(resultSet.getLong("stopRollingDate"));
				//pubPage.setHasImage(resultSet.getBoolean("hasImage"));
				//pubPage.setImageFilename(resultSet.getString("imageFilename"));
				//pubPage.setFileRepresentation(resultSet.getBoolean("fileRepresentation"));
				//pubPage.setLink(resultSet.getString("link"));
				//pubPage.setReferenceFile(resultSet.getString("referenceFile"));
				//pubPage.setSource(resultSet.getString("source"));
				//pubPage.setInternalUseDescription(resultSet.getString("internalUseDescription"));
				//pubPage.setWraper(resultSet.getBoolean("wraper"));
				//pubPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
				//pubPage.setOnSite(resultSet.getBoolean("onSite"));
				pubPages.add(pubPage);
			}
			return pubPages;
		}
		catch (SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public void insertPubPagesURLsTable(int ardNum, List<PageUrl> URLsList, String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			for (PageUrl pageUrl: URLsList){
				if (!pageUrl.isValid())
					continue;
				int r = statement.executeUpdate("UPDATE PubPagesURLs SET ardNum=" + ardNum +
					", urlText='" + SQLUtils.toSQLString(pageUrl.getUrlText()) + "', formerFileSize=fileSize, fileSize=0,"+
					" thisBuild=1 WHERE ardNum=" + ardNum + " AND URL='"+pageUrl.getUrl() + "'");
				if (r==0) 
					statement.executeUpdate("INSERT PubPagesURLs SET ardNum=" + ardNum +
							", URL='"+pageUrl.getUrl()+"', urlText='"+SQLUtils.toSQLString(pageUrl.getUrlText()) + "', thisBuild=1");
			}
		}
		catch(SQLException e){
			System.out.println("Here: "+e);
		}
	}	
	
	public void deleteOldRowsFromPubPagesUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM PubPagesURLs WHERE thisBuild=0 ;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	/*public void deleteFromInfoPagesMailUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM InfoPagesMailURLs;");
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void insertTabledInfoPagesMailUrlsTable(int ardNum, List<PageMailUrl> pageMailUrls,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			for (PageMailUrl pageMailUrl: pageMailUrls){
				statement.executeUpdate("REPLACE InfoPagesMailURLs SET ardNum="+ardNum+
					", mailAddress=\""+pageMailUrl.getMailAddress()+"\""+
					", urlText=\""+pageMailUrl.getUrlText()+"\""+
					", status=\"unknown\";");
				statement.close();
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public void deleteFromPubPagesMailUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM PubPagesMailURLs;");
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void insertPubPagesMailUrlsTable(int ardNum, List<PageMailUrl> pageMailUrls,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			for (PageMailUrl pageMailUrl: pageMailUrls){
				statement.executeUpdate("REPLACE PubPagesMailURLs SET ardNum="+ardNum+
					", mailAddress=\""+pageMailUrl.getMailAddress()+"\""+
					", urlText=\""+pageMailUrl.getUrlText()+"\""+
					", status=\"unknown\";");
				statement.close();
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}*/

	
	public List<PageUrl> getInfoPagesUrls(Integer ardNum,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM InfoPagesURLs WHERE DATEDIFF(now(), checkedTime) > 1";
			if (ardNum != null)
				query += " AND ardNum = " + ardNum;
			query += " ORDER BY checkedTime LIMIT 30";
			logger.debug(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<PageUrl> pagesURLs = new ArrayList<PageUrl>();
			while (resultSet.next()){
				PageUrl pageUrl = new PageUrl();
				pageUrl.setArdNum(resultSet.getInt("ardNum"));
				pageUrl.setUrl(resultSet.getString("URL"));
				pageUrl.setUrlText(resultSet.getString("urlText"));
				pageUrl.setFileSize(resultSet.getLong("fileSize"));
				pageUrl.setFormerFileSize(resultSet.getLong("formerFileSize"));
				pageUrl.setStatus(resultSet.getString("status"));
				pagesURLs.add(pageUrl);
			}
			return pagesURLs;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}

	/* public List<CallForProposal> getInfoPageByArdNum(int ardNum,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM TabledInfoPages, InfoPages WHERE InfoPages.ardNum="+ardNum+";");
			List<CallForProposal> infoPages = moveResultSetToTabledInfoPages(resultSet);
			return infoPages;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}*/

	public List<PageUrl> getSearchInfoPagesUrls(ListView lv, SearchCreteria search, String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM InfoPagesURLs ";
			if (search != null)
				query += search.getFullWhereCluase();
			query += " order by "+lv.getOrderBy();
			logger.debug(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<PageUrl> pagesURLs = new ArrayList<PageUrl>();
			while (resultSet.next()){
				PageUrl pageUrl = new PageUrl();
				pageUrl.setArdNum(resultSet.getInt("ardNum"));
				pageUrl.setUrl(resultSet.getString("URL"));
				pageUrl.setUrlText(resultSet.getString("urlText"));
				pageUrl.setFileSize(resultSet.getLong("fileSize"));
				pageUrl.setFormerFileSize(resultSet.getLong("formerFileSize"));
				pageUrl.setStatus(resultSet.getString("status"));
				pagesURLs.add(pageUrl);
			}
			return pagesURLs;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public void updateTabledInfoPagesUrl(PageUrl pageUrl,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "UPDATE InfoPagesURLs SET fileSize="+pageUrl.getFileSize()
					+", status='"+pageUrl.getStatus()+"', checkedTime=now() WHERE ardNum="+pageUrl.getArdNum()+" AND URL='"+pageUrl.getUrl()+"'";
			logger.debug(query);
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public List<PageUrl> getPubPagesUrls(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPagesURLs"
				+" WHERE DATEDIFF(now(), checkedTime) > 90 ORDER BY RAND();";
			ResultSet resultSet = statement.executeQuery(query);
			logger.debug(query);
			List<PageUrl> pagesURLs = new ArrayList<PageUrl>();
			while (resultSet.next()){
				PageUrl pageUrl = new PageUrl();
				pageUrl.setArdNum(resultSet.getInt("ardNum"));
				pageUrl.setUrl(resultSet.getString("URL"));
				pageUrl.setUrlText(resultSet.getString("urlText"));
				pageUrl.setFileSize(resultSet.getLong("fileSize"));
				pageUrl.setFormerFileSize(resultSet.getLong("formerFileSize"));
				pageUrl.setStatus(resultSet.getString("status"));
				pagesURLs.add(pageUrl);
			}
			return pagesURLs;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	public List<PageUrl> getSearchPubPagesUrls(ListView lv, SearchCreteria search, String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM PubPagesURLs ";
			if (search != null)
				query += search.getFullWhereCluase();
			query += " order by "+lv.getOrderBy();
			logger.debug(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<PageUrl> pagesURLs = new ArrayList<PageUrl>();
			while (resultSet.next()){
				PageUrl pageUrl = new PageUrl();
				pageUrl.setArdNum(resultSet.getInt("ardNum"));
				pageUrl.setUrl(resultSet.getString("URL"));
				pageUrl.setUrlText(resultSet.getString("urlText"));
				pageUrl.setFileSize(resultSet.getLong("fileSize"));
				pageUrl.setFormerFileSize(resultSet.getLong("formerFileSize"));
				pageUrl.setStatus(resultSet.getString("status"));
				pagesURLs.add(pageUrl);
			}
			return pagesURLs;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public void updatePubPagesUrl(PageUrl pageUrl,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			String query = "UPDATE PubPagesURLs SET fileSize="+pageUrl.getFileSize()
			+", status=\""+pageUrl.getStatus()+"\", checkedTime=now() WHERE ardNum="+pageUrl.getArdNum()+" AND URL=\""+pageUrl.getUrl()+"\";";
			logger.debug(query);
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	/*public List<PageMailUrl> getInfoPagesMailUrlsFromTable(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InfoPagesMailURLs;");
			List<PageMailUrl> pageMailUrls = new ArrayList<PageMailUrl>();
			while(resultSet.next()){
				PageMailUrl pageMailUrl = new PageMailUrl();
				pageMailUrl.setArdNum(resultSet.getInt("ardNum"));
				pageMailUrl.setMailAddress(resultSet.getString("mailAddress"));
				pageMailUrl.setUrlText(resultSet.getString("urlText"));
				pageMailUrl.setStatus(resultSet.getString("status"));
				pageMailUrls.add(pageMailUrl);
			}
			statement.close();
			return pageMailUrls;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public List<PageMailUrl> getPubPagesMailUrlsFromTable(String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PubPagesMailURLs;");
			List<PageMailUrl> pageMailUrls = new ArrayList<PageMailUrl>();
			while(resultSet.next()){
				PageMailUrl pageMailUrl = new PageMailUrl();
				pageMailUrl.setArdNum(resultSet.getInt("ardNum"));
				pageMailUrl.setMailAddress(resultSet.getString("mailAddress"));
				pageMailUrl.setUrlText(resultSet.getString("urlText"));
				pageMailUrl.setStatus(resultSet.getString("status"));
				pageMailUrls.add(pageMailUrl);
			}
			statement.close();
			return pageMailUrls;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}

	public boolean isSuspectedMailAddress(String mailAddress,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM MAILER.SuspectedMailAddresses"+
					" WHERE mailAddress=\""+mailAddress+"\";");
			resultSet.next();
			int r = resultSet.getInt(1);
			statement.close();
			return r>0;
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}
	
	public void setInfoPagesMailUrlSuspected(String address,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE InfoPagesMailURLs set status=\"suspected\" WHERE mailAddress=\""+address+"\"");
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void setPubPagesMailUrlSuspected(String address,String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE PubPagesMailURLs set status=\"suspected\" WHERE mailAddress=\""+address+"\"");
			statement.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}*/

}
