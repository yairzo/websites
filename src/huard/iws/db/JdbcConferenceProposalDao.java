package huard.iws.db;

import huard.iws.bean.PersonBean;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import huard.iws.util.SQLUtils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcConferenceProposalDao extends SimpleJdbcDaoSupport implements ConferenceProposalDao {
	

	public ConferenceProposal getConferenceProposal(int id){
		String query = "select * from conferenceProposal where id=?";
		logger.info(query);
		ConferenceProposal conferenceProposal =
			getSimpleJdbcTemplate().queryForObject(query, rowMapper,	id);
		logger.info("conference proposal id: " + conferenceProposal.getId());
		conferenceProposal.setFromAssosiate(getSupportFromAssosiate(id));
		conferenceProposal.setFromExternal(getSupportFromExternal(id));
		conferenceProposal.setFromAdmitanceFee(getSupportFromAdmitanceFee(id));
		conferenceProposal.setScientificCommittees(getScientificCommittees(id));
		conferenceProposal.setOperationalCommittees(getOperationalCommittees(id));
		return conferenceProposal;
	}
	
	public List<FinancialSupport> getSupportFromAssosiate(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=1";
		List<FinancialSupport> supportFromAssosiate =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromAssosiate;
	}	

	public List<FinancialSupport> getSupportFromExternal(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=2";
		List<FinancialSupport> supportFromExternal =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromExternal;
	}
	
	public List<FinancialSupport> getSupportFromAdmitanceFee(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=3";
		List<FinancialSupport> supportFromAdmitanceFee =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromAdmitanceFee;
	}
	
	public List<Committee> getScientificCommittees(int conferenceProposalId){
		String query = "select * from committee where conferenceProposalId=? and type=1";
		List<Committee> scientificCommittees =
			getSimpleJdbcTemplate().query(query, committeeRowMapper ,	conferenceProposalId);
		return scientificCommittees;
	}	
	
	public List<Committee> getOperationalCommittees(int conferenceProposalId){
		String query = "select * from committee where conferenceProposalId=? and type=2";
		List<Committee> operationalCommittees =
			getSimpleJdbcTemplate().query(query, committeeRowMapper ,	conferenceProposalId);
		return operationalCommittees;
	}	
	
	ParameterizedRowMapper<Committee> committeeRowMapper	= new ParameterizedRowMapper<Committee>(){
		public Committee mapRow(ResultSet rs, int rowNum) throws SQLException{
			Committee committee = new Committee();
			committee.setId(rs.getInt("id"));
			committee.setConferenceProposalId(rs.getInt("conferenceProposalId"));
			committee.setName(rs.getString("name"));
			committee.setInstitute(rs.getString("institute"));
			committee.setInstituteRole(rs.getString("instituteRole"));
			committee.setCommitteeRole(rs.getString("committeeRole"));
			return committee;
		}
    };
	ParameterizedRowMapper<FinancialSupport> financialSupportRowMapper	= new ParameterizedRowMapper<FinancialSupport>(){
		public FinancialSupport mapRow(ResultSet rs, int rowNum) throws SQLException{
			FinancialSupport support = new FinancialSupport();
			support.setId(rs.getInt("id"));
			support.setConferenceProposalId(rs.getInt("conferenceProposalId"));
			support.setName(rs.getString("name"));
			support.setSum(rs.getString("sum"));
			support.setCurrency(rs.getString("currency"));
			return support;
		}
    };	
    
	public void insertFinancialSupport(FinancialSupport financialSupport){
		String query = "insert financialSupport set conferenceProposalId = ?, name = ?, sum = ?, type = ?, currency = ?";
		getSimpleJdbcTemplate().update(query,
				financialSupport.getConferenceProposalId(),
				financialSupport.getName(),
				financialSupport.getSum(),
				financialSupport.getType(),
				financialSupport.getCurrency()
		);
	}    
	public void insertCommittee(Committee committee){
		String query = "insert committee set conferenceProposalId = ?, name = ?, institute = ?, instituteRole = ?, committeeRole = ?, type=?";
		getSimpleJdbcTemplate().update(query,
				committee.getConferenceProposalId(),
				committee.getName(),
				committee.getInstitute(),
				committee.getInstituteRole(),
				committee.getCommitteeRole(),
				committee.getType()
		);
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
			long fromDate = 0;
			Timestamp aFromDate = rs.getTimestamp("fromDate");
			if (aFromDate != null)
				fromDate = aFromDate.getTime();
			conferenceProposal.setFromDate(fromDate);
			long toDate = 0;
			Timestamp aToDate = rs.getTimestamp("toDate");
			if (aToDate != null)
				toDate = aToDate.getTime();
			conferenceProposal.setToDate(toDate);
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
			conferenceProposal.setInitiatingBody(rs.getString("initiatingBody"));
			conferenceProposal.setInitiatingBodyRole(rs.getInt("initiatingBodyRole"));
			long openDate = 0;
			Timestamp aOpenDate = rs.getTimestamp("openDate");
			if (aOpenDate != null)
				openDate = aOpenDate.getTime();
			conferenceProposal.setOpenDate(openDate);
			long submissionDate = 0;
			Timestamp aSubmissionDate = rs.getTimestamp("submissionDate");
			if (aSubmissionDate != null)
				submissionDate = aSubmissionDate.getTime();
			conferenceProposal.setSubmissionDate(submissionDate);
			conferenceProposal.setTotalCost(rs.getDouble("totalCost"));
			conferenceProposal.setTotalCostCurrency(rs.getInt("totalCostCurrency"));
			conferenceProposal.setSupportSum(rs.getDouble("supportSum"));
			conferenceProposal.setSupportCurrency(rs.getInt("supportCurrency"));
			conferenceProposal.setAuditorium(rs.getBoolean("auditorium"));
			conferenceProposal.setSeminarRoom(rs.getBoolean("seminarRoom"));
			conferenceProposal.setParticipants(rs.getInt("participants"));
			conferenceProposal.setPrefferedCampus(rs.getInt("prefferedCampus"));
			conferenceProposal.setOrganizingCompany(rs.getBoolean("organizingCompany"));
			conferenceProposal.setOrganizingCompanyName(rs.getString("organizingCompanyName"));
			conferenceProposal.setOrganizingCompanyPhone(rs.getString("organizingCompanyPhone"));
			conferenceProposal.setOrganizingCompanyFax(rs.getString("organizingCompanyFax"));
			conferenceProposal.setOrganizingCompanyEmail(rs.getString("organizingCompanyEmail"));
			conferenceProposal.setSubmitted(rs.getBoolean("submitted"));
			conferenceProposal.setRemarks(rs.getString("remarks"));
			conferenceProposal.setContactPerson(rs.getString("contactPerson"));
			conferenceProposal.setContactPersonRole(rs.getString("contactPersonRole"));
			conferenceProposal.setContactPersonPhone(rs.getString("contactPersonPhone"));
			conferenceProposal.setContactPersonFax(rs.getString("contactPersonFax"));
			conferenceProposal.setContactPersonEmail(rs.getString("contactPersonEmail"));
			conferenceProposal.setAdminRemarks(rs.getString("adminRemarks"));
			long deadline = 0;
			Timestamp adeadline = rs.getTimestamp("deadline");
			if (adeadline != null)
				deadline = adeadline.getTime();
			conferenceProposal.setDeadline(deadline);
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
			long fromDate = 0;
			Timestamp aFromDate = rs.getTimestamp("fromDate");
			if (aFromDate != null)
				fromDate = aFromDate.getTime();
			conferenceProposal.setFromDate(fromDate);
			long toDate = 0;
			Timestamp aToDate = rs.getTimestamp("toDate");
			if (aToDate != null)
				toDate = aToDate.getTime();
			conferenceProposal.setToDate(toDate);
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
			conferenceProposal.setInitiatingBody(rs.getString("initiatingBody"));
			conferenceProposal.setInitiatingBodyRole(rs.getInt("initiatingBodyRole"));
			long openDate = 0;
			Timestamp aOpenDate = rs.getTimestamp("openDate");
			if (aOpenDate != null)
				openDate = aOpenDate.getTime();
			conferenceProposal.setOpenDate(openDate);
			long submissionDate = 0;
			Timestamp aSubmissionDate = rs.getTimestamp("submissionDate");
			if (aSubmissionDate != null)
				submissionDate = aSubmissionDate.getTime();
			conferenceProposal.setSubmissionDate(submissionDate);
			conferenceProposal.setTotalCost(rs.getDouble("totalCost"));
			conferenceProposal.setTotalCostCurrency(rs.getInt("totalCostCurrency"));
			conferenceProposal.setSupportSum(rs.getDouble("supportSum"));
			conferenceProposal.setSupportCurrency(rs.getInt("supportCurrency"));
			conferenceProposal.setAuditorium(rs.getBoolean("auditorium"));
			conferenceProposal.setSeminarRoom(rs.getBoolean("seminarRoom"));
			conferenceProposal.setParticipants(rs.getInt("participants"));
			conferenceProposal.setPrefferedCampus(rs.getInt("prefferedCampus"));
			conferenceProposal.setOrganizingCompany(rs.getBoolean("organizingCompany"));
			conferenceProposal.setOrganizingCompanyName(rs.getString("organizingCompanyName"));
			conferenceProposal.setOrganizingCompanyPhone(rs.getString("organizingCompanyPhone"));
			conferenceProposal.setOrganizingCompanyFax(rs.getString("organizingCompanyFax"));
			conferenceProposal.setOrganizingCompanyEmail(rs.getString("organizingCompanyEmail"));
			conferenceProposal.setSubmitted(rs.getBoolean("submitted"));
			conferenceProposal.setRemarks(rs.getString("remarks"));
			conferenceProposal.setContactPerson(rs.getString("contactPerson"));
			conferenceProposal.setContactPersonRole(rs.getString("contactPersonRole"));
			conferenceProposal.setContactPersonPhone(rs.getString("contactPersonPhone"));
			conferenceProposal.setContactPersonFax(rs.getString("contactPersonFax"));
			conferenceProposal.setContactPersonEmail(rs.getString("contactPersonEmail"));			
			conferenceProposal.setAdminRemarks(rs.getString("adminRemarks"));
			long deadline = 0;
			Timestamp adeadline = rs.getTimestamp("deadline");
			if (adeadline != null)
				deadline = adeadline.getTime();
			conferenceProposal.setDeadline(deadline);
            return conferenceProposal;
        }
	};

	public int insertConferenceProposal(ConferenceProposal conferenceProposal){
		final String proposalInsert = "insert conferenceProposal set personId = ?,approverId=0,openDate=now(),fromDate=now(),toDate=now(),submissionDate=now(),deadline=?;";
		final int personId = conferenceProposal.getPersonId();
		final long deadline = conferenceProposal.getDeadline();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setInt(1, personId);
		            ps.setTimestamp(2, new java.sql.Timestamp(deadline));
		            return ps;
		        }
		    },
		    keyHolder);
		final int key=keyHolder.getKey().intValue();
		final String proposalVersionInsert = "insert conferenceProposalVersion set conferenceProposalId = ?,personId = ?,approverId=0,openDate=now(),fromDate=now(),toDate=now(),submissionDate=now(),deadline=?;";
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalVersionInsert, new String[] {"id"});
		            ps.setInt(1, key);
		            ps.setInt(2, personId);
		            ps.setTimestamp(3, new java.sql.Timestamp(deadline));
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
				", initiatingBody =  ?" + 
				", initiatingBodyRole = ?" + 
				//", openDate=  ?" + 
				", submissionDate= now()" + 
				", totalCost= ?" + 
				", totalCostCurrency= ?" + 
				", supportSum= ?" + 
				", supportCurrency= ?" + 
				", auditorium= ?" + 
				", seminarRoom= ?" + 
				", participants= ?" + 
				", prefferedCampus= ?" + 
				", organizingCompany= ?" + 
				", organizingCompanyName= ?" + 
				", organizingCompanyPhone= ?" + 
				", organizingCompanyFax= ?" + 
				", organizingCompanyEmail= ?" + 
				", submitted= ?" + 
				", remarks= ?" + 
				", contactPerson= ?" + 
				", contactPersonRole= ?" + 
				", contactPersonPhone= ?" + 
				", contactPersonFax= ?" + 
				", contactPersonEmail= ?" + 
				", adminRemarks= ?" + 
				", deadline= ?" + 
				" where id = ?;";
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getApproverEvaluation(),
				conferenceProposal.getGrade(),
				conferenceProposal.getDescription(),
				conferenceProposal.getSubject(),
				new java.sql.Timestamp(conferenceProposal.getFromDate()),
				new java.sql.Timestamp(conferenceProposal.getToDate()),
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
				conferenceProposal.getInitiatingBody(),
				conferenceProposal.getInitiatingBodyRole(),
				//new java.sql.Timestamp(conferenceProposal.getOpenDate()),
				//new java.sql.Timestamp(conferenceProposal.getSubmissionDate()),
				conferenceProposal.getTotalCost(),
				conferenceProposal.getTotalCostCurrency(),
				conferenceProposal.getSupportSum(),
				conferenceProposal.getSupportCurrency(),
				conferenceProposal.getAuditorium(),
				conferenceProposal.getSeminarRoom(),
				conferenceProposal.getParticipants(),
				conferenceProposal.getPrefferedCampus(),
				conferenceProposal.getOrganizingCompany(),
				conferenceProposal.getOrganizingCompanyName(),
				conferenceProposal.getOrganizingCompanyPhone(),
				conferenceProposal.getOrganizingCompanyFax(),
				conferenceProposal.getOrganizingCompanyEmail(),
				conferenceProposal.getSubmitted(),
				conferenceProposal.getRemarks(),
				conferenceProposal.getContactPerson(),
				conferenceProposal.getContactPersonRole(),
				conferenceProposal.getContactPersonPhone(),
				conferenceProposal.getContactPersonFax(),
				conferenceProposal.getContactPersonEmail(),				
				conferenceProposal.getAdminRemarks(),	
				new java.sql.Timestamp(conferenceProposal.getDeadline()),
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
				", initiatingBody =  ?" + 
				", initiatingBodyRole = ?" + 
				", openDate=  now()" + 
				", submissionDate= now()" + 
				", totalCost= ?" + 
				", totalCostCurrency= ?" + 
				", supportSum= ?" + 
				", supportCurrency= ?" + 
				", auditorium= ?" + 
				", seminarRoom= ?" + 
				", participants= ?" + 
				", prefferedCampus= ?" + 
				", organizingCompany= ?" + 
				", organizingCompanyName= ?" + 
				", organizingCompanyPhone= ?" + 
				", organizingCompanyFax= ?" + 
				", organizingCompanyEmail= ?" + 
				", submitted= ?" + 
				", remarks= ?" + 
				", contactPerson= ?" + 
				", contactPersonRole= ?" + 
				", contactPersonPhone= ?" + 
				", contactPersonFax= ?" + 
				", contactPersonEmail= ?" + 
				", adminRemarks= ?" + 
				", deadline= ?" + 
				";";

		
		getSimpleJdbcTemplate().update(proposalVersionInsert,conferenceProposal.getId(),
				conferenceProposal.getPersonId(),
				conferenceProposal.getApproverId(),
				conferenceProposal.getApproverEvaluation(),
				conferenceProposal.getGrade(),
				conferenceProposal.getDescription(),
				conferenceProposal.getSubject(),
				new java.sql.Timestamp(conferenceProposal.getFromDate()),
				new java.sql.Timestamp(conferenceProposal.getToDate()),
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
				conferenceProposal.getInitiatingBody(),
				conferenceProposal.getInitiatingBodyRole(),
				//new java.sql.Timestamp(conferenceProposal.getOpenDate()),
				//new java.sql.Timestamp(conferenceProposal.getSubmissionDate()),
				conferenceProposal.getTotalCost(),
				conferenceProposal.getTotalCostCurrency(),
				conferenceProposal.getSupportSum(),
				conferenceProposal.getSupportCurrency(),
				conferenceProposal.getAuditorium(),
				conferenceProposal.getSeminarRoom(),
				conferenceProposal.getParticipants(),
				conferenceProposal.getPrefferedCampus(),
				conferenceProposal.getOrganizingCompany(),
				conferenceProposal.getOrganizingCompanyName(),
				conferenceProposal.getOrganizingCompanyPhone(),
				conferenceProposal.getOrganizingCompanyFax(),
				conferenceProposal.getOrganizingCompanyEmail(),
				conferenceProposal.getSubmitted(),
				conferenceProposal.getRemarks(),
				conferenceProposal.getContactPerson(),
				conferenceProposal.getContactPersonRole(),
				conferenceProposal.getContactPersonPhone(),
				conferenceProposal.getContactPersonFax(),
				conferenceProposal.getContactPersonEmail(),				
				conferenceProposal.getAdminRemarks(),
				new java.sql.Timestamp(conferenceProposal.getDeadline()));
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
	
	public List<ConferenceProposal> getConferenceProposalsByDate(String fromDate) {
		String query = "select * from conferenceProposal where deadline= ? order by id";
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper,fromDate);
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

		if ((search != null && (!search.getWhereClause().isEmpty() || !search.getSearchPhrase().isEmpty())) || userPersonBean.isAuthorized("CONFERENCE","APPROVER") || userPersonBean.isAuthorized("RESEARCHER") )
			whereClause += " where";

		if (userPersonBean.isAuthorized("RESEARCHER")){
			whereClause += " personId = " + userPersonBean.getId() ;
			if (search != null && (!search.getWhereClause().isEmpty() || !search.getSearchPhrase().isEmpty()))
				whereClause += " and";
		}
		else if (userPersonBean.isAuthorized("CONFERENCE","APPROVER")){
			whereClause += " approverId = " + userPersonBean.getId() ;
			if (search != null && (!search.getWhereClause().isEmpty() || !search.getSearchPhrase().isEmpty()))
				whereClause += " and";
		}
		
		if (search != null && (!search.getWhereClause().isEmpty() || !search.getSearchPhrase().isEmpty())){
			if (!search.getWhereClause().isEmpty()){// where clause
				whereClause+= search.getWhereClause();
				if(!search.getSearchPhrase().isEmpty())
					whereClause += " and";
			}
			if(!search.getSearchPhrase().isEmpty()){ //search phrase
				whereClause +=  " personId in (select id from person where (concat(lastNameHebrew, ' ', firstNameHebrew, ' ', email) = '" + SQLUtils.toSQLString(search.getSearchPhrase()) + "'"
				+ " or concat(lastNameHebrew, ' ', firstNameHebrew)='" + SQLUtils.toSQLString(search.getSearchPhrase()) + "' "
				+ " or concat(firstNameHebrew, ' ', lastNameHebrew)='" + SQLUtils.toSQLString(search.getSearchPhrase()) + "' "
				+ " or lastNameHebrew like '%" + SQLUtils.toSQLString(search.getSearchPhrase()) + "%' "
				+ " or firstNameHebrew like '%" + SQLUtils.toSQLString(search.getSearchPhrase()) + "%' "
				+ " or email = '" + SQLUtils.toSQLString(search.getSearchPhrase()) + "')) ";
			}
		}
		//order by
		if (userPersonBean.isAuthorized("CONFERENCE","APPROVER")){
			whereClause += " order by grade";
		}		
		return whereClause;
	}
	
	
	public void gradeHigher(ConferenceProposal conferenceProposal, String deadline){
		String query = "update conferenceProposal set grade=grade - 1 where approverId=? and grade=? and date(deadline)='"+deadline +"';";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()+1);
		System.out.println(query);
		query = "update conferenceProposal set grade= grade + 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		System.out.println(query);
	}
	public void gradeLower(ConferenceProposal conferenceProposal, String deadline){
		String query = "update conferenceProposal set grade=grade + 1 where approverId=? and grade=? and date(deadline)='"+deadline +"';";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()-1);
		System.out.println(query);
		query = "update conferenceProposal set grade= grade - 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		System.out.println(query);
	}
	public int getMaxGrade(int approverId, String deadline){
		String query = "select max(grade) from conferenceProposal where approverId=? and date(deadline)='"+deadline +"';";
		System.out.println(query);
		return getSimpleJdbcTemplate().queryForInt(query,approverId);
	}

}
