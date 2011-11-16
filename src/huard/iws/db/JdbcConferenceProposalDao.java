package huard.iws.db;

import huard.iws.model.ConferenceProposal;
import huard.iws.bean.PersonBean;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

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
			conferenceProposal.setGrade(rs.getInt("grade"));
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
			conferenceProposal.setGrade(rs.getInt("grade"));
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
				", grade = ?" +				
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
				conferenceProposal.getGrade(),
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
				", grade = ?" +
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

		
		getSimpleJdbcTemplate().update(proposalVersionInsert,conferenceProposal.getId(),
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getApproverEvaluation(),
				conferenceProposal.getGrade(),
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
				conferenceProposal.getFinancialAttachContentType());
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

	public List<ConferenceProposal> getConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean) {

		String query = "select * from conferenceProposal";
		//get where clause by search critieria
		query += getConferenceProposalsWhereClause(search,userPersonBean);

		query += " limit "+ (lv.getPage()-1) * lv.getRowsInPage() + "," + lv.getRowsInPage();

		System.out.println(query);

		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper);
		//applyPersonSubjectIds(persons);
		return conferenceProposals;
    }
	
	public int countConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean) {

		String query = "select count(*) from conferenceProposal";
		//get where clause by search critieria
		query += getConferenceProposalsWhereClause(search,userPersonBean);


		System.out.println(query);

		return getSimpleJdbcTemplate().queryForInt(query);

    }

	public String getConferenceProposalsWhereClause(SearchCreteria search, PersonBean userPersonBean){
		String whereClause="";
		// seaching by a search field

		if ((search != null && ! search.getSearchPhrase().isEmpty()) || userPersonBean.isAuthorized("CONFERENCE","APPROVER") || userPersonBean.isAuthorized("RESEARCHER") )
			whereClause += " where";

		if (userPersonBean.isAuthorized("RESEARCHER")){
			whereClause += " personId = " + userPersonBean.getId() ;
			if (search != null && ! search.getSearchPhrase().isEmpty())
				whereClause += " and";
		}
		else if (userPersonBean.isAuthorized("CONFERENCE","APPROVER")){
			whereClause += " approverId = " + userPersonBean.getId() ;
			if (search != null && ! search.getSearchPhrase().isEmpty())
				whereClause += " and";
		}
		
		/*if (search != null && ! search.getSearchPhrase().isEmpty())
			whereClause +=  " (concat(lastNameHebrew, ' ', firstNameHebrew, ' ', email) = '" + SQLUtils.toSQLString(search.getSearchPhrase()) + "'"
				+ " or concat(lastNameHebrew, ' ', firstNameHebrew)='" + SQLUtils.toSQLString(search.getSearchPhrase()) + "' "
				+ " or concat(firstNameHebrew, ' ', lastNameHebrew)='" + SQLUtils.toSQLString(search.getSearchPhrase()) + "' "
				+ " or lastNameHebrew like '%" + SQLUtils.toSQLString(search.getSearchPhrase()) + "%' "
				+ " or firstNameHebrew like '%" + SQLUtils.toSQLString(search.getSearchPhrase()) + "%' "
				+ " or email = '" + SQLUtils.toSQLString(search.getSearchPhrase()) + "') ";
		*/
		if (userPersonBean.isAuthorized("CONFERENCE","APPROVER")){
			whereClause += " order by grade";
		}		
		return whereClause;
	}
	
	
	public void gradeHigher(ConferenceProposal conferenceProposal){
		String query = "update conferenceProposal set grade=grade - 1 where approverId=? and grade=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()+1);
		System.out.println(query);
		query = "update conferenceProposal set grade= grade + 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		System.out.println(query);
	}
	public void gradeLower(ConferenceProposal conferenceProposal){
		String query = "update conferenceProposal set grade=grade + 1 where approverId=? and grade=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()-1);
		System.out.println(query);
		query = "update conferenceProposal set grade= grade - 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		System.out.println(query);
	}
	public int getMaxGrade(int approverId){
		String query = "select max(grade) from conferenceProposal where approverId=? ;";
		System.out.println(query);
		return getSimpleJdbcTemplate().queryForInt(query,approverId);
	}

}
