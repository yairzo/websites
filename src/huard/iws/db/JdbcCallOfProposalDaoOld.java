package huard.iws.db;

import huard.iws.model.CallOfProposalOld;
import huard.iws.util.SQLUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCallOfProposalDaoOld implements CallOfProposalDaoOld {

	public CallOfProposalOld getCallOfProposal(int id, String server){
		CallOfProposalOld callOfProposal = new CallOfProposalOld();
		try{
			String query = "select * from InfoPages inner join TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
				+ " where InfoPages.ardNum =" + id;
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()){
				callOfProposal.setId(rs.getInt("ardNum"));
				callOfProposal.setFundId(rs.getInt("fundNum"));
				callOfProposal.setPublicationTimeMillis(rs.getLong("pubDate"));
				callOfProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
				callOfProposal.setTitle(rs.getString("title"));
				callOfProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
				callOfProposal.setDeskId(rs.getString("deskId"));

			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callOfProposal;
	}

	public CallOfProposalOld getCallOfProposal(String title, String server){
		CallOfProposalOld callOfProposal = new CallOfProposalOld();
		try{
			String query = "select ardNum from InfoPages where title = '" + SQLUtils.toSQLString(title) + "'";
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			int id = 0;
			if (rs.next()){
				id = rs.getInt("ardNum");
			}
			callOfProposal = getCallOfProposal(id, server);
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callOfProposal;
	}


	public List<CallOfProposalOld> getCallsOfProposals(String server){
		return getCallsOfProposals(server, false);
	}

	public List<CallOfProposalOld> getCallsOfProposals(String server, boolean open){
		List<CallOfProposalOld> callOfProposals = new ArrayList<CallOfProposalOld>();
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
				CallOfProposalOld callOfProposal  = new CallOfProposalOld();
				callOfProposal.setId(rs.getInt("ardNum"));
				callOfProposal.setFundId(rs.getInt("fundNum"));
				callOfProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
				callOfProposal.setTitle(rs.getString("title"));
				callOfProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
				callOfProposal.setDeskId(rs.getString("deskId"));

				callOfProposals.add(callOfProposal);
			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return callOfProposals;
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





	/*public CallOfProposal getCallOfProposal(int id){
		String query = "select * from HUARD.InfoPages inner join HUARD.TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
			+ " where InfoPages.ardNum = ?";
		CallOfProposal callOfProposal = getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		return callOfProposal;
	}

	private ParameterizedRowMapper<CallOfProposal> rowMapper = new ParameterizedRowMapper<CallOfProposal>(){
		public CallOfProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            CallOfProposal callOfProposal = new CallOfProposal();
            callOfProposal.setId(rs.getInt("ardNum"));
            callOfProposal.setFundId(rs.getInt("fundNum"));
            callOfProposal.setSubmissionTimeMillis(rs.getLong("subDate"));
            callOfProposal.setTitle(rs.getString("title"));
            callOfProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
            return callOfProposal;
        }
	};

	public List<CallOfProposal> getCallsOfProposals(){
		String query  = "select * from HUARD.InfoPages inner join HUARD.TabledInfoPages on InfoPages.ardNum = TabledInfoPages.ardNum"
			+ " order by title";
		List<CallOfProposal> callOfProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callOfProposals;
	}*/
	
	public List<CallOfProposalOld> getAliveTabledInfoPages(Integer ardNum, String server){
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
	public List<CallOfProposalOld> moveResultSetToTabledInfoPages(ResultSet resultSet) throws SQLException{
		List<CallOfProposalOld> tabledInfoPages = new ArrayList<CallOfProposalOld>();
		while (resultSet.next()){
			CallOfProposalOld tabledInfoPage = new CallOfProposalOld();
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
