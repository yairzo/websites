package huard.iws.db;

import huard.iws.model.ConferenceProposal;

import java.sql.Timestamp;
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
	
	public ConferenceProposal getVersionConferenceProposal(int confId, int verId){
		String conferenceSelect = "select  * from conferenceProposalVersion where conferenceProposalId = ? and id = ? ";
		ConferenceProposal conferenceProposal =
		getSimpleJdbcTemplate().queryForObject(conferenceSelect, versionRowMapper,	confId ,verId );
		return conferenceProposal;
	}

	public int getFirstVersion(int confId){
		String query = "select min(id) from conferenceProposalVersion where conferenceProposalId = ?";
		return getSimpleJdbcTemplate().queryForInt(query,confId);
	}
	
	public int getPreviousVersion(int confId, int verId){
		if (verId==0)
			verId = getLastVersion(confId);
		if( verId == getFirstVersion(confId)){ 
			return getFirstVersion(confId);//this is first version there is no previous
		}
		else{
			String query = "select id from conferenceProposalVersion where conferenceProposalId = ? and id<" + verId + " order by id desc limit 1";
			return getSimpleJdbcTemplate().queryForInt(query,confId);
		}
	}
	public int getNextVersion(int confId, int verId){
		if (verId==0 || verId==getLastVersion(confId)){
			return getLastVersion(confId);//this is latest version so there is no next
		}
		else{
			String query = "select id from conferenceProposalVersion where conferenceProposalId = ? and id> " + verId + " order by id limit 1";
			return getSimpleJdbcTemplate().queryForInt(query,confId);
		}
	}


	public int getLastVersion(int confId){
		String query = "select max(id) from conferenceProposalVersion where conferenceProposalId = ?";
		return getSimpleJdbcTemplate().queryForInt(query,confId);
	}

	private ParameterizedRowMapper<ConferenceProposal> rowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("id"));
			conferenceProposal.setPersonId(rs.getInt("personId"));
			conferenceProposal.setApproverId(rs.getInt("approverId"));
			conferenceProposal.setApproverEvaluation(rs.getString("approverEvaluation"));
			conferenceProposal.setDescription(rs.getString("description"));
			conferenceProposal.setSubject(rs.getString("subject"));
			conferenceProposal.setFromDate(rs.getTimestamp("fromDate"));
			conferenceProposal.setToDate(rs.getTimestamp("toDate"));
			conferenceProposal.setLocation(rs.getString("location"));
			conferenceProposal.setLocationDetail(rs.getString("locationDetails"));
			conferenceProposal.setAudienceGuests(rs.getInt("audienceGuests"));
			conferenceProposal.setAudienceLecturers(rs.getInt("audienceLecturers"));
			conferenceProposal.setForeignGuests(rs.getInt("foreignGuests"));
			conferenceProposal.setForeignLecturers(rs.getInt("foreignLecturers"));
			conferenceProposal.setLocalGuests(rs.getInt("localGuests"));
			conferenceProposal.setLocalLecturers(rs.getInt("localLecturers"));
			conferenceProposal.setGuestsAttach(rs.getBytes("guestsAttach"));
			conferenceProposal.setGuestsAttachContentType(rs.getString("guestsAttachContentType"));
			conferenceProposal.setProgramAttach(rs.getBytes("programAttach"));
			conferenceProposal.setProgramAttachContentType(rs.getString("programAttachContentType"));
			conferenceProposal.setFinancialAttach(rs.getBytes("financeAttach"));
			conferenceProposal.setFinancialAttachContentType(rs.getString("financeAttachContentType"));
			
            return conferenceProposal;
        }
	};
	private ParameterizedRowMapper<ConferenceProposal> versionRowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("conferenceProposalId"));
			conferenceProposal.setPersonId(rs.getInt("personId"));
			conferenceProposal.setApproverId(rs.getInt("approverId"));
			conferenceProposal.setApproverEvaluation(rs.getString("approverEvaluation"));
			conferenceProposal.setDescription(rs.getString("description"));
			conferenceProposal.setSubject(rs.getString("subject"));
			conferenceProposal.setFromDate(rs.getTimestamp("fromDate"));
			conferenceProposal.setToDate(rs.getTimestamp("toDate"));
			conferenceProposal.setLocation(rs.getString("location"));
			conferenceProposal.setVersionId(rs.getInt("id"));
			conferenceProposal.setLocationDetail(rs.getString("locationDetails"));
			conferenceProposal.setAudienceGuests(rs.getInt("audienceGuests"));
			conferenceProposal.setAudienceLecturers(rs.getInt("audienceLecturers"));
			conferenceProposal.setForeignGuests(rs.getInt("foreignGuests"));
			conferenceProposal.setForeignLecturers(rs.getInt("foreignLecturers"));
			conferenceProposal.setLocalGuests(rs.getInt("localGuests"));
			conferenceProposal.setLocalLecturers(rs.getInt("localLecturers"));
			conferenceProposal.setGuestsAttach(rs.getBytes("guestsAttach"));
			conferenceProposal.setGuestsAttachContentType(rs.getString("guestsAttachContentType"));
			conferenceProposal.setProgramAttach(rs.getBytes("programAttach"));
			conferenceProposal.setProgramAttachContentType(rs.getString("programAttachContentType"));
			conferenceProposal.setFinancialAttach(rs.getBytes("financeAttach"));
			conferenceProposal.setFinancialAttachContentType(rs.getString("financeAttachContentType"));
			
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
				", approverEvaluation = ?" +				
				", description = ?" + 
				", subject = ?" + 
				", fromDate = ?" + 
				", toDate = ?" + 
				", location = ?" + 
				", locationDetails = ?" + 
				", foreignLecturers = ?" + 
				", localLecturers = ?" + 
				", audienceLecturers = ?" + 
				", foreignGuests = ?" + 
				", localGuests = ?" + 
				", audienceGuests = ?" + 
				", guestsAttach = ?" + 
				", guestsAttachContentType = ?" + 
				", programAttach = ?" + 
				", programAttachContentType = ?" + 
				", financeAttach = ?" + 
				", financeAttachContentType = ?" + 
				" where id = ?;";
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getApproverEvaluation(),
				conferenceProposal.getDescription(),
				conferenceProposal.getSubject(),
				conferenceProposal.getFromDate(),
				conferenceProposal.getToDate(),
				conferenceProposal.getLocation(),
				conferenceProposal.getLocationDetail(),
				conferenceProposal.getForeignLecturers(),
				conferenceProposal.getLocalLecturers(),
				conferenceProposal.getAudienceLecturers(),
				conferenceProposal.getForeignGuests(),
				conferenceProposal.getLocalGuests(),
				conferenceProposal.getAudienceGuests(),
				conferenceProposal.getGuestsAttach(),
				conferenceProposal.getGuestsAttachContentType(),
				conferenceProposal.getProgramAttach(),
				conferenceProposal.getProgramAttachContentType(),
				conferenceProposal.getFinancialAttach(),
				conferenceProposal.getFinancialAttachContentType(),
				conferenceProposal.getId());
		
		//insert to version table
		 String proposalVersionInsert = "insert conferenceProposalVersion set "+
				" conferenceProposalId = ?" +
				", personId = ?" + 
				", approverId = ?" + 
				", approverEvaluation = ?" +
				", description = ?"+
				", subject = ?" + 
				", fromDate = ?" + 
				", toDate = ?" + 
				", location = ?" + 
				", locationDetails = ?" +
				", foreignLecturers = ?" + 
				", localLecturers = ?" + 
				", audienceLecturers = ?" + 
				", foreignGuests = ?" + 
				", localGuests = ?" + 
				", audienceGuests = ?" + 
				", guestsAttach = ?" + 
				", guestsAttachContentType = ?" + 
				", programAttach = ?" + 
				", programAttachContentType = ?" + 
				", financeAttach = ?" + 
				", financeAttachContentType = ?" + 
				";";
		/*final int proposalId = conferenceProposal.getId();
		final int personId = conferenceProposal.getPersonId();
		final int approverId = conferenceProposal.getApproverId();
		final String approverEvaluation = conferenceProposal.getApproverEvaluation();
		final String description = conferenceProposal.getDescription();
		final String subject = conferenceProposal.getSubject();
		final Timestamp fromDate = conferenceProposal.getFromDate();
		final Timestamp toDate = conferenceProposal.getToDate();
		final String location = conferenceProposal.getLocation();
		final String locationDetail = conferenceProposal.getLocationDetail();
		final int foreignLecturers = conferenceProposal.getForeignLecturers();
		final int localLecturers = conferenceProposal.getLocalLecturers();
		final int audienceLecturers = conferenceProposal.getAudienceLecturers();
		final int foreignGuests = conferenceProposal.getForeignGuests();
		final int localGuests = conferenceProposal.getLocalGuests();
		final int audienceGuests = conferenceProposal.getAudienceGuests();
		final byte [] guestsAttach = conferenceProposal.getGuestsAttach();
		final String guestsAttachContentType = conferenceProposal.getGuestsAttachContentType();
		final byte [] programAttach = conferenceProposal.getProgramAttach();
		final String programAttachContentType = conferenceProposal.getProgramAttachContentType();
		final byte [] financialAttach = conferenceProposal.getFinancialAttach();
		final String financialAttachContentType = conferenceProposal.getFinancialAttachContentType();
		*/
		
		getSimpleJdbcTemplate().update(proposalVersionInsert,conferenceProposal.getId(),
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getApproverEvaluation(),
				conferenceProposal.getDescription(),
				conferenceProposal.getSubject(),
				conferenceProposal.getFromDate(),
				conferenceProposal.getToDate(),
				conferenceProposal.getLocation(),
				conferenceProposal.getLocationDetail(),
				conferenceProposal.getForeignLecturers(),
				conferenceProposal.getLocalLecturers(),
				conferenceProposal.getAudienceLecturers(),
				conferenceProposal.getForeignGuests(),
				conferenceProposal.getLocalGuests(),
				conferenceProposal.getAudienceGuests(),
				conferenceProposal.getGuestsAttach(),
				conferenceProposal.getGuestsAttachContentType(),
				conferenceProposal.getProgramAttach(),
				conferenceProposal.getProgramAttachContentType(),
				conferenceProposal.getFinancialAttach(),
				conferenceProposal.getFinancialAttachContentType()
				
				/*new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalVersionInsert, new String[] {"id"});
		            ps.setInt(1, proposalId);
		            ps.setInt(2, personId);
		            ps.setInt(3, approverId);
		            ps.setString(4, approverEvaluation);
		            ps.setString(5, description);
		            ps.setString(6, subject);
		            ps.setTimestamp(7, fromDate);
		            ps.setTimestamp(8, toDate);
		            ps.setString(9, location);
		            ps.setString(10, locationDetail);
		            ps.setInt(11, foreignLecturers);
		            ps.setInt(12, localLecturers);
		            ps.setInt(13, audienceLecturers);
		            ps.setInt(14, foreignGuests);
		            ps.setInt(15, localGuests);
		            ps.setInt(16, audienceGuests);
		            ps.setBytes(17, guestsAttach);
		            ps.setString(18, guestsAttachContentType);
		            ps.setBytes(19, programAttach);
		            ps.setString(20, programAttachContentType);
		            ps.setBytes(21, financialAttach);
		            ps.setString(22, financialAttachContentType);

		            return ps;
		        }
		    }*/);
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
