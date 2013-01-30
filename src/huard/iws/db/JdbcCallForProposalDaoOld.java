package huard.iws.db;

import huard.iws.model.CallForProposalOld;
import huard.iws.util.SQLUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCallForProposalDaoOld implements CallForProposalDaoOld {

	public CallForProposalOld getCallForProposal(int id, String server){
		CallForProposalOld callForProposal = new CallForProposalOld();
		try{
			String query = "select * from InfoPages inner join TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
				+ " where InfoPages.ardNum =" + id;
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()){
				callForProposal.setId(rs.getInt("ardNum"));
				callForProposal.setFundId(rs.getInt("fundNum"));
				callForProposal.setPublicationTimeMillis(rs.getLong("pubDate"));
				callForProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
				callForProposal.setTitle(rs.getString("title"));
				callForProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
				callForProposal.setDeskId(rs.getString("deskId"));

			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callForProposal;
	}

	public CallForProposalOld getCallForProposal(String title, String server){
		CallForProposalOld callForProposal = new CallForProposalOld();
		try{
			String query = "select ardNum from InfoPages where title = '" + SQLUtils.toSQLString(title) + "'";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			int id = 0;
			if (rs.next()){
				id = rs.getInt("ardNum");
			}
			callForProposal = getCallForProposal(id, server);
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callForProposal;
	}


	public List<CallForProposalOld> getCallForProposals(String server){
		return getCallForProposals(server, false);
	}

	public List<CallForProposalOld> getCallForProposals(String server, boolean open){
		List<CallForProposalOld> callForProposals = new ArrayList<CallForProposalOld>();
		try{
			String query = "select * from InfoPages inner join TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum ";
			if (open)
				query += " where subDate > " + System.currentTimeMillis() + " or subDate = 0";
			query += " order by title";
			System.out.println(query);
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				CallForProposalOld callForProposal  = new CallForProposalOld();
				callForProposal.setId(rs.getInt("ardNum"));
				callForProposal.setFundId(rs.getInt("fundNum"));
				callForProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
				callForProposal.setTitle(rs.getString("title"));
				callForProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
				callForProposal.setDeskId(rs.getString("deskId"));

				callForProposals.add(callForProposal);
			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callForProposals;
	}

	public void insertAuthorizedMD5(String md5, String server){
		try{
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "INSERT", server);
			Statement statement = connection.createStatement();
			String query = "insert /*! ignore */ AuthorizedMD5 set md5 = '" + md5 + "'";
			statement.executeUpdate(query);
		}
		catch (SQLException e){
			System.out.println(e);
		}

	}





	/*public CallForProposal getCallForProposal(int id){
		String query = "select * from HUARD.InfoPages inner join HUARD.TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
			+ " where InfoPages.ardNum = ?";
		CallForProposal callForProposal = getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		return callForProposal;
	}

	private ParameterizedRowMapper<CallForProposal> rowMapper = new ParameterizedRowMapper<CallForProposal>(){
		public CallForProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            CallForProposal callForProposal = new CallForProposal();
            callForProposal.setId(rs.getInt("ardNum"));
            callForProposal.setFundId(rs.getInt("fundNum"));
            callForProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
            callForProposal.setTitle(rs.getString("title"));
            callForProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
            return callForProposal;
        }
	};

	public List<CallForProposal> getCallsForProposals(){
		String query  = "select * from HUARD.InfoPages inner join HUARD.TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
			+ " order by title";
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}*/
	
	public List<CallForProposalOld> getAliveTabledInfoPages(Integer ardNum, String server){
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
	public List<CallForProposalOld> moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
		List<CallForProposalOld> tabledInfoPages = new ArrayList<CallForProposalOld>();
		while (resultSet.next()){
			CallForProposalOld tabledInfoPage = new CallForProposalOld();
			tabledInfoPage.setId(resultSet.getInt("ardNum"));
			tabledInfoPage.setTitle(resultSet.getString("title"));
			tabledInfoPage.setPublicationTimeMillis(resultSet.getLong("pubDate"));
			tabledInfoPage.setFundId(resultSet.getInt("fundNum"));
			tabledInfoPage.setSubmissionTimeMillis(resultSet.getLong("subDate"));
			tabledInfoPage.setDeskId(resultSet.getString("deskId"));
			tabledInfoPage.setPageWebAddress(resultSet.getString("pageWebAddress"));
			tabledInfoPage.setDescriptionOnly(resultSet.getBoolean("descriptionOnly"));
			tabledInfoPage.setDeskAndContact(resultSet.getString("deskAndContact"));
			tabledInfoPage.setForms(resultSet.getString("forms"));
			tabledInfoPage.setDescription(resultSet.getString("description"));
			tabledInfoPage.setAmountOfGrant(resultSet.getString("amountOfGrant"));
			tabledInfoPage.setBudgetDetails(resultSet.getString("budgetDetails"));
			tabledInfoPage.setAdditionalInformation(resultSet.getString("additionalInformation"));
			tabledInfoPages.add(tabledInfoPage);
		}
		return tabledInfoPages;
	}

	

}
