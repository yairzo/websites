package huard.iws.db;

import huard.iws.model.ConferenceProposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcConferenceProposalDao extends SimpleJdbcDaoSupport implements ConferenceProposalDao {

	public ConferenceProposal getConferenceProposal(int id){
		String conferenceSelect = "select * from conferenceProposal where id=?";
		ConferenceProposal conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(conferenceSelect, rowMapper,	id);
		return conferenceProposal;
	}
	
	public ConferenceProposal getPrevVersionConferenceProposal(int confId, int verId){
		String conferenceSelect ="";
		ConferenceProposal conferenceProposal;
		if (verId == 0){ // current working version
			conferenceSelect ="select  * from conferenceProposalVersion where conferenceProposalId = ? order by Id DESC limit 1";
			conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(conferenceSelect, versionRowMapper,	confId );
		}
		else{
			conferenceSelect = "select  * from conferenceProposalVersion where conferenceProposalId = ? and id < ? order by Id DESC limit 1 ";
			conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(conferenceSelect, versionRowMapper,	confId ,verId );
		}
		return conferenceProposal;
	}
	
	public int getFirstVersion(int confId){
		String query = "select min(id) from conferenceProposalVersion where conferenceProposalId = ?";
		return getSimpleJdbcTemplate().queryForInt(query,confId);
	}

	public int getLastVersion(int confId){
		String query = "select max(id) from conferenceProposalVersion where conferenceProposalId = ?";
		return getSimpleJdbcTemplate().queryForInt(query,confId);
	}

	public ConferenceProposal getNextVersionConferenceProposal(int confId, int verId){
		String conferenceSelect ="";
		ConferenceProposal conferenceProposal;
		if (verId == 0){ // current working version
			conferenceSelect ="select  * from conferenceProposalVersion where conferenceProposalId = ? order by Id ASC limit 1";
			conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(conferenceSelect, versionRowMapper,	confId );
		}
		else{
			conferenceSelect = "select  * from conferenceProposalVersion where conferenceProposalId = ? and id > ? order by Id ASC limit 1 ";
			conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(conferenceSelect, versionRowMapper,	confId ,verId );
		}
		return conferenceProposal;
	}

	private ParameterizedRowMapper<ConferenceProposal> rowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("id"));
			conferenceProposal.setPersonId(rs.getInt("personId"));
			conferenceProposal.setApproverId(rs.getInt("approverId"));
			conferenceProposal.setDescription(rs.getString("description"));
            return conferenceProposal;
        }
	};
	private ParameterizedRowMapper<ConferenceProposal> versionRowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("conferenceProposalId"));
			conferenceProposal.setPersonId(rs.getInt("personId"));
			conferenceProposal.setApproverId(rs.getInt("approverId"));
			conferenceProposal.setDescription(rs.getString("description"));
			conferenceProposal.setVersionId(rs.getInt("id"));
            return conferenceProposal;
        }
	};

	public int insertConferenceProposal(ConferenceProposal conferenceProposal){
		final String proposalInsert = "insert conferenceProposal set personId = ?,approverId=0;";
		final int personId = conferenceProposal.getPersonId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setInt(1, personId);
		            return ps;
		        }
		    },
		    keyHolder);
		final int key=keyHolder.getKey().intValue();
		final String proposalVersionInsert = "insert conferenceProposalVersion set conferenceProposalId = ?,personId = ?,approverId=0;";
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalVersionInsert, new String[] {"id"});
		            ps.setInt(1, key);
		            ps.setInt(2, personId);
		            return ps;
		        }
		    });
		
		return keyHolder.getKey().intValue();
	}

	public void updateConferenceProposal(ConferenceProposal conferenceProposal){
		String query = "update conferenceProposal set " +
				" personId = ?" +
				", approverId = ?" +
				", description = ? where id = ?;";
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getDescription(),
				conferenceProposal.getId());
		
		//insert to version table
		final String proposalVersionInsert = "insert conferenceProposalVersion set "+
				" conferenceProposalId = ?" +
				",personId = ?" + 
				",approverId = ?" + 
				",description = ?;" ;
		final int proposalId = conferenceProposal.getId();
		final int personId = conferenceProposal.getPersonId();
		final int approverId = conferenceProposal.getApproverId();
		final String description = conferenceProposal.getDescription();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalVersionInsert, new String[] {"id"});
		            ps.setInt(1, proposalId);
		            ps.setInt(2, personId);
		            ps.setInt(3, approverId);
		            ps.setString(4, description);
		            return ps;
		        }
		    });
	}


	public void deleteConferenceProposal(int id){
		String query = "delete from conferenceProposal where id = ?;";
		getSimpleJdbcTemplate().update(query, id);
	}
	
	public List<ConferenceProposal> getConferenceProposals() {
		String query = "select * from conferenceProposal order by id";
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return conferenceProposals;
    }

	public List<ConferenceProposal> getConferenceProposalsByPerson( int personId) {
		String query = "select * from conferenceProposal where personId = ?";
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper, personId);
		return conferenceProposals;
    }



}
