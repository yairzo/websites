package huard.iws.db;

import huard.iws.model.Fund;
import huard.iws.util.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcFundDao extends SimpleJdbcDaoSupport implements FundDao {

	public Fund getFund(int id){
		String personSelect = "select * from fund where id=?";
		Fund fund =
			getSimpleJdbcTemplate().queryForObject(personSelect, rowMapper,	id);
		return fund;
	}
	
	public Fund getFundByFinancialId(int financialId){
		Fund fund =new Fund();
		String personSelect = "select * from fund where financialId=?";
		try{
			fund =	getSimpleJdbcTemplate().queryForObject(personSelect, rowMapper,	financialId);
		}
		catch(Exception e){
			return fund;
		}
		return fund;
	}

	private ParameterizedRowMapper<Fund> rowMapper = new ParameterizedRowMapper<Fund>(){
		public Fund mapRow(ResultSet rs, int rowNum) throws SQLException{
            Fund fund = new Fund();
            fund.setId(rs.getInt("id"));
            fund.setName(rs.getString("name"));
            fund.setShortName(rs.getString("shortName"));
            fund.setDeskId(rs.getInt("deskId"));
            fund.setTemporary(rs.getBoolean("isTemporary"));
            fund.setFinancialId(rs.getInt("financialId"));
            fund.setHujiId(rs.getInt("hujiId"));
            fund.setParentId(rs.getInt("parentId"));
            fund.setWebAddress(rs.getString("webAddress"));
            fund.setPhone(rs.getString("phone"));
            fund.setFax(rs.getString("fax"));
            fund.setContact(rs.getString("contact"));
            fund.setMailAddress(rs.getString("email"));
            fund.setKeywords(rs.getString("keywords"));
            fund.setCreatorId(rs.getInt("creatorId"));

            fund.setBudgetOfficerId(rs.getInt("budgetOfficerId"));
            fund.setFinancialReporterId(rs.getInt("financialReporterId"));

            fund.setHtml(rs.getString("html"));
            Timestamp creationTimeTS = rs.getTimestamp("creationTime");
			long creationTime = 0;
            if (creationTimeTS != null)
				creationTime = creationTimeTS.getTime();    		
            fund.setCreationTime(creationTime);
            Timestamp updateTimeTS = rs.getTimestamp("updateTime");
            long updateTime = 0;
            if (updateTimeTS != null)
            	updateTime = updateTimeTS.getTime(); 
            fund.setUpdateTime(updateTime);
            return fund;
        }
	};

	public void updateFund(Fund fund){
		String query = "update fund set " +
				" name = ?" +
				", shortName = ?" +
				", deskId = ?" +
				", isTemporary = ?" +
				", financialId = ?" +
				", hujiId = ?" +
				", parentId = ?" +
				", webAddress = ?" +
				", phone = ?" +
				", fax = ?" +
				", contact = ?" +
				", email = ?" +
				", keywords = ?" +
				", creatorId = ?" +
				", budgetOfficerId = ?" +
				", financialReporterId = ?" +
				", html = ?" +
				", creationTime = ?" +
				", updateTime = ?" +
				" where id = ?;";
		getSimpleJdbcTemplate().update(query,
				fund.getName(),
				fund.getShortName(),
				fund.getDeskId(),
				fund.isTemporary(),
				fund.getFinancialId(),
				fund.getHujiId(),
				fund.getParentId(),
				fund.getWebAddress(),
				fund.getPhone(),
				fund.getFax(),
				fund.getContact(),
				fund.getMailAddress(),
				fund.getKeywords(),
				fund.getCreatorId(),
				fund.getBudgetOfficerId(),
				fund.getFinancialReporterId(),
				fund.getHtml(),
				SQLUtils.getTimestampString(fund.getCreationTime()),
				SQLUtils.getTimestampString(fund.getUpdateTime()),
				fund.getId());
	}

	public int insertFund(Fund fund){
		final String proposalInsert = "insert fund set name = ?, shortName = ?, deskId = ?" +
				", isTemporary = ?, financialId=?, html='', keywords='';";
		final String name = fund.getName();
		final String shortName = fund.getShortName();
		final int deskId = fund.getDeskId();
		final boolean temporary = fund.isTemporary();
		final int financialId = fund.getFinancialId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setString(2, shortName);
		            ps.setInt(3, deskId);
		            ps.setBoolean(4, temporary);
		            ps.setInt(5, financialId);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deleteFund(int fundId){
		String query = "update fund set isDeleted = 1 where id = ?;";
		getSimpleJdbcTemplate().update(query, fundId);
	}

	public List<Fund> getFunds() {
		String query = "select * from fund order by shortName";
		List<Fund> funds =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return funds;
    }

	public List<Fund> getTemporaryFunds() {
		String query = "select * from fund where isTemporary=1 order by shortName";
		List<Fund> funds =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return funds;
    }

	public List<Fund> getFundsByDeskId( int mopDeskId) {
		String query = "select * from fund where deskId = ? order by shortName";
		List<Fund> funds =
			getSimpleJdbcTemplate().query(query, rowMapper, mopDeskId);
		return funds;
    }
	

	public Fund getArdFund(int id, String server){
		Fund fund = new Fund();
		try{
			String query = "select * from Funds where fundNum="+id;
			Connection connection = ArdConnectionSupplier.getConnectionSupplier().getConnection("HUARD", "SELECT", server);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()){
				fund.setId(rs.getInt("fundNum"));
				fund.setName(rs.getString("fullName"));
				fund.setShortName(rs.getString("shortName"));
				fund.setWebAddress(rs.getString("webAddress"));
			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		System.out.println("Fund: " + fund.getShortName());
		return fund;
	}

	public int getMaxFinancialIdForTemporary(){
		String query = "select max(financialId) from fund where financialId>9999;";
		logger.info(query);
		try{
			int id = getSimpleJdbcTemplate().queryForInt(query);
			if(id==0)
				return 10000;
			return id+1;
		}
		catch(Exception e){
			return 10000;
		}
	}



}
