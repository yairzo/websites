package huard.iws.db;

import huard.iws.model.CallOfProposal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPageWordsIndexerDao extends SimpleJdbcDaoSupport implements PageWordsIndexerDao {

	private java.util.Date now;
	
	public List<CallOfProposal> getLatelyUpdatedInfoPages(long runsInterval, String server){
		try{
			now = new java.util.Date();
			long lastRunTime = now.getTime() - runsInterval;
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
	public void deleteLatelyUpdatedInfoPagesFromIndexTable(long runsInterval, String server){
		try{
			now = new java.util.Date();
			long lastRunTime = now.getTime() - runsInterval;
			String updateString = "DELETE FROM InfoPagesIndex WHERE ardNum IN (SELECT ardNum FROM InfoPagesLastUpdates"+
			" WHERE date>"+lastRunTime+");";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "DELETE", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(updateString);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}	
	
	public int getEnglishDesk (String deskId){
		String query = "SELECT listIdEnglish FROM Desks WHERE deskId=?";
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query,deskId);
	}
	public int getHebrewDesk (String deskId){
		String query = "SELECT listIdHebrew FROM Desks WHERE deskId=?";
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query,deskId);
	}
	
	public void insertWordToInfoPagesIndexTable(String word,int ardNum,String server){
		try{
			word = word.replaceAll("\"", "\\\\\"");
			String updateString ="INSERT IGNORE InfoPagesIndex SET word=\""+word+"\", ardNum="+ardNum;
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "INSERT", server);
			Statement statement = connection.createStatement();
			statement.executeUpdate(updateString);

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
	

}
