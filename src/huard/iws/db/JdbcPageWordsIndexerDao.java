package huard.iws.db;

import huard.iws.model.CallOfProposal;
import huard.iws.model.TextualPage;
import huard.iws.model.Desk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class JdbcPageWordsIndexerDao extends SimpleJdbcDaoSupport implements PageWordsIndexerDao {

	private java.util.Date now;
	

	public List<CallOfProposal> getLatelyUpdatedInfoPages(long runsInterval, String server){
		try{
			now = new java.util.Date();
			long lastRunTime = now.getTime() - runsInterval;
			//System.out.println("lastRunTime:"+lastRunTime);
			//lastRunTime=1300000000000L;// just for testing
			String queryString = "SELECT * FROM InfoPages,TabledInfoPages,InfoPagesLastUpdates"+
			" WHERE InfoPages.isDeleted=0 AND date>="+lastRunTime+" AND InfoPages.ardNum=TabledInfoPages.ardNum AND "+
			" InfoPages.ardNum = InfoPagesLastUpdates.ardNum";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			return moveResultSetToTabledInfoPages(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			return null;
		}

	}	
	public List<TextualPage> getLatelyUpdatedPubPages(long runsInterval, String server){
		try{
			now = new java.util.Date();
			long lastRunTime = now.getTime() - runsInterval;
			//lastRunTime=1300000000000L;// just for testing
			String queryString = "SELECT * FROM PubPages,PubPagesLastUpdates"+
			" WHERE PubPages.isDeleted=0 AND PubPages.onSite=1 AND date>="+lastRunTime+" AND PubPages.ardNum = PubPagesLastUpdates.ardNum";
			System.out.println(queryString);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			return moveResultSetToPubPages(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			return null;
		}

	}	
	
	public void deleteLatelyUpdatedInfoPagesFromIndexTable(List<CallOfProposal> indexedInfoPages,boolean fullIndex, String server){
		try{
			String queryInClause = buildInfoPagesQueryInClause(indexedInfoPages);			
			String query = "DELETE FROM InfoPagesIndex";
			if (!fullIndex)
				query += " WHERE ardNum IN ("+queryInClause+")";
			System.out.println(query);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "DELETE", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	private String buildInfoPagesQueryInClause(List<CallOfProposal> infoPages){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (CallOfProposal infoPage: infoPages){
			if (!first)
				sb.append(",");
			sb.append(infoPage.getId());
			first = false;
		}
		return sb.toString();
	}

	public void deleteLatelyUpdatedPubPagesFromIndexTable(List<TextualPage> indexedTextualPages,boolean fullIndex,String server){
		try{
			String queryInClause = buildPubPagesQueryInClause(indexedTextualPages);
			String query = "DELETE FROM PubPagesIndex";
			if (!fullIndex)
				query += " WHERE ardNum IN ("+queryInClause+")";
			System.out.println(query);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "DELETE", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	private String buildPubPagesQueryInClause(List<TextualPage> textualPages){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (TextualPage textualPage: textualPages){
			if (!first)
				sb.append(",");
			sb.append(textualPage.getId());
			first = false;
		}
		return sb.toString();
	}
	

	public int getEnglishDesk (String deskId,String server){
		try{
			String query = "SELECT listIdEnglish FROM Desks WHERE deskId='"+deskId + "';";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getInt("listIdEnglish");
		}
		catch (SQLException e){
			System.out.println(e);
			return 0;
		}
	}
	
	public int getHebrewDesk (String deskId,String server){
		try{
			String query = "SELECT listIdHebrew FROM Desks WHERE deskId='"+deskId + "';";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getInt("listIdHebrew");
		}
		catch (SQLException e){
			System.out.println(e);
			return 0;
		}
	}
	
	public void insertWordToInfoPagesIndexTable(String columnsvalues,String server){
		try{
			columnsvalues = columnsvalues.replaceAll("\"", "\\\\\"");
			String insertString ="INSERT IGNORE INTO InfoPagesIndex VALUES " + columnsvalues + ";";
			//System.out.println(insertString);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "INSERT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(insertString);
		}
		catch (SQLException e){
			System.out.println("Insert Word: "+e);
		}
	}

	public void insertWordToTextualPagesIndexTable(String columnsvalues,String server){
		try{
			columnsvalues = columnsvalues.replaceAll("\"", "\\\\\"");
			String insertString ="INSERT IGNORE INTO PubPagesIndex VALUES " + columnsvalues + ";";
			//System.out.println(insertString);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "INSERT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(insertString);
		}
		catch (SQLException e){
			System.out.println("Insert Word: "+e);
		}
	}

	public void purgeInfoPagesIndexTable(String server){
		try{
			String updateString ="DELETE FROM InfoPagesIndex WHERE word LIKE '%<%' OR word LIKE '%>%';";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "DELETE", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(updateString);
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}
	public void purgePubPagesIndexTable(String server){
		try{
			String updateString ="DELETE FROM PubPagesIndex WHERE word LIKE '%<%' OR word LIKE '%>%';";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "DELETE", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(updateString);
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}	
	public List<CallOfProposal> moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
		List<CallOfProposal> callOfProposals = new ArrayList<CallOfProposal>();
		while (resultSet.next()){
			CallOfProposal callOfProposal = new CallOfProposal();
			callOfProposal.setId(resultSet.getInt("ardNum"));
			callOfProposal.setTitle(resultSet.getString("title"));
			callOfProposal.setPublicationTimeMillis(resultSet.getLong("pubDate"));
			callOfProposal.setSubmissionTimeMillis(resultSet.getLong("subDate"));
			callOfProposal.setDeskId(resultSet.getString("deskId"));
			callOfProposal.setFundId(resultSet.getInt("fundNum"));
			callOfProposal.setDeskAndContact(resultSet.getString("deskAndContact"));
			callOfProposal.setForms(resultSet.getString("forms"));
			callOfProposal.setDescription(resultSet.getString("description"));
			callOfProposal.setAmountOfGrant(resultSet.getString("amountOfGrant"));
			callOfProposal.setBudgetDetails(resultSet.getString("budgetDetails"));
			callOfProposal.setAdditionalInformation(resultSet.getString("additionalInformation"));
			callOfProposals.add(callOfProposal);
		}
		return callOfProposals;
	}
	public List<TextualPage> moveResultSetToPubPages(ResultSet resultSet) throws SQLException{
   		List<TextualPage> textualPages = new ArrayList<TextualPage>();
   	   	while (resultSet.next()){
   	   		TextualPage textPage = new TextualPage();
   	   		textPage.setId(resultSet.getInt("ardNum"));
   	   		textPage.setTitle(resultSet.getString("title"));
   	   		textPage.setHtml(resultSet.getString("html"));
   	   		textPage.setWraper(resultSet.getBoolean("wraper"));
   	   		textPage.setSourceToWrap(resultSet.getString("sourceToWrap"));
   	   		textualPages.add(textPage);
     	}
     	return textualPages;
	}	

	public List<Desk> getDesks(String server){
		try{
			String queryString = "SELECT * FROM Desks;";
			System.out.println(queryString);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			return moveResultSetToDesksList(resultSet);
		}
		catch (SQLException e){
			System.out.println(e);
			return null;
		}
	}	
	
	public List<Desk> moveResultSetToDesksList(ResultSet resultSet) throws SQLException{
		List<Desk> desks = new ArrayList<Desk>();
   	   	while (resultSet.next()){
   	   		Desk desk = new Desk();
   	   		desk.setId(resultSet.getString("deskId"));
   	   		desk.setEnglishListId(resultSet.getInt("listIdEnglish"));
   	   		desk.setHebrewListId(resultSet.getInt("listIdHebrew"));
   	   		desks.add(desk);
     	}
     	return desks;
	}	

}
