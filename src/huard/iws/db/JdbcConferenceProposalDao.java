package huard.iws.db;

import huard.iws.bean.PersonBean;
import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.ConferenceProposalGrading;
import huard.iws.model.FinancialSupport;
import huard.iws.util.ListView;
import huard.iws.util.SQLUtils;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
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
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		conferenceProposal.setFromAssosiate(getSupportFromAssosiate(id));
		conferenceProposal.setFromExternal(getSupportFromExternal(id));
		conferenceProposal.setFromAdmitanceFee(getSupportFromAdmitanceFee(id));
		conferenceProposal.setScientificCommittees(getScientificCommittees(id));
		conferenceProposal.setOperationalCommittees(getOperationalCommittees(id));
		return conferenceProposal;
	}
	
	public List<FinancialSupport> getSupportFromAssosiate(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=1";
		logger.info(query);
		List<FinancialSupport> supportFromAssosiate =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromAssosiate;
	}	

	public List<FinancialSupport> getSupportFromExternal(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=2";
		logger.info(query);
		List<FinancialSupport> supportFromExternal =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromExternal;
	}
	
	public List<FinancialSupport> getSupportFromAdmitanceFee(int conferenceProposalId){
		String query = "select * from financialSupport where conferenceProposalId=? and type=3";
		logger.info(query);
		List<FinancialSupport> supportFromAdmitanceFee =
			getSimpleJdbcTemplate().query(query, financialSupportRowMapper ,	conferenceProposalId);
		return supportFromAdmitanceFee;
	}
	
	public List<Committee> getScientificCommittees(int conferenceProposalId){
		String query = "select * from committee where conferenceProposalId=? and type=1";
		logger.info(query);
		List<Committee> scientificCommittees =
			getSimpleJdbcTemplate().query(query, committeeRowMapper ,	conferenceProposalId);
		return scientificCommittees;
	}	
	
	public List<Committee> getOperationalCommittees(int conferenceProposalId){
		String query = "select * from committee where conferenceProposalId=? and type=2";
		logger.info(query);
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
			committee.setType(rs.getInt("type"));
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
			support.setType(rs.getInt("type"));
			support.setSumPerson(rs.getString("sumPerson"));
			return support;
		}
    };	
    
	public void insertFinancialSupport(FinancialSupport financialSupport){
		if (financialSupport.isEmpty())
			return;
		String query = "insert financialSupport set conferenceProposalId = ?, name = ?, sum = ?, type = ?, currency = ?, sumPerson=?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				financialSupport.getConferenceProposalId(),
				financialSupport.getName(),
				financialSupport.getSum(),
				financialSupport.getType(),
				financialSupport.getCurrency(),
				financialSupport.getSumPerson()
		);
	}   
	public void updateFinancialSupport(FinancialSupport financialSupport){
		String query = "update financialSupport set name = ?, sum = ?, currency = ?, type=?, sumPerson=?  where id =?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				financialSupport.getName(),
				financialSupport.getSum(),
				financialSupport.getCurrency(),
				financialSupport.getType(),
				financialSupport.getSumPerson(),
				financialSupport.getId()
		);
	}   
	public void insertCommittee(Committee committee){
		if (committee.isEmpty())
			return;
		String query = "insert committee set conferenceProposalId = ?, name = ?, institute = ?, instituteRole = ?, committeeRole = ?, type=?";
		logger.info(query + " " + committee.getConferenceProposalId()+" "+
				committee.getName()+" "+
				committee.getInstitute()+" "+
				committee.getInstituteRole()+" "+
				committee.getCommitteeRole()+" "+
				committee.getType());
		getSimpleJdbcTemplate().update(query,
				committee.getConferenceProposalId(),
				committee.getName(),
				committee.getInstitute(),
				committee.getInstituteRole(),
				committee.getCommitteeRole(),
				committee.getType()
		);
	}
	
	public void insertCommittees(ConferenceProposal conferenceProposal){
		String query = "delete from committee where conferenceProposalId = ?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getId());
		for (Committee committee: conferenceProposal.getScientificCommittees()){
			insertCommittee(committee);
		}
		for (Committee committee: conferenceProposal.getOperationalCommittees()){
			insertCommittee(committee);
		}
	}

	public void insertFinancialSupports(ConferenceProposal conferenceProposal){
		String query = "delete from financialSupport where conferenceProposalId = ?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getId());
		for (FinancialSupport financialSupport: conferenceProposal.getFromAssosiate()){
			insertFinancialSupport(financialSupport);
		}
		for (FinancialSupport financialSupport: conferenceProposal.getFromExternal()){
			insertFinancialSupport(financialSupport);
		}
		for (FinancialSupport financialSupport: conferenceProposal.getFromAdmitanceFee()){
			insertFinancialSupport(financialSupport);
		}
	}

	public void deleteFinancialSupport(int financialSupportId){
		String query = "delete from financialSupport where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query, financialSupportId);
	}

	public void updateCommittee(Committee committee){
		String query = "update committee set name = ?, institute = ?, instituteRole = ?, committeeRole = ? where id=?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				committee.getName(),
				committee.getInstitute(),
				committee.getInstituteRole(),
				committee.getCommitteeRole(),
				committee.getId()
		);
	} 	
	public void deleteCommittee(int committeeId){
		String query = "delete from committee where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query, committeeId);
	}
	
	public void insertGradingInfo(ConferenceProposalGrading conferenceProposalGrading){
		String query = "insert conferenceProposalGrading set approverId = ?, adminId = ?, deadline = ?, sentForGradingDate = now(), finishedGradingDate = ?, adminSendRemark=?";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				conferenceProposalGrading.getApproverId(),
				conferenceProposalGrading.getAdminId(),
				new java.sql.Timestamp(conferenceProposalGrading.getDeadline()),
				new java.sql.Timestamp(conferenceProposalGrading.getFinishedGradingDate()),
				conferenceProposalGrading.getAdminSendRemark()
		);
	} 
	public void updateLastGradingByApproverDeadline(int approverId,String deadline,String deadlineRemarks){
		String query = "select max(id) from conferenceProposalGrading where approverId = ? and date(deadline) =?;";
		logger.info(query);
		int id = getSimpleJdbcTemplate().queryForInt(query,approverId, deadline);
		query = "update conferenceProposalGrading set finishedGradingDate=now(), deadlineRemark=? where id=?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query, deadlineRemarks, id);
	}
	
	public List<ConferenceProposalGrading> getAllGradingsByCurrentDeadline(String deadline){
		String query = "select  * from  conferenceProposalGrading where date(deadline) =? order by deadline desc, sentForGradingDate";
		logger.info(query + " " + deadline);
		return getSimpleJdbcTemplate().query(query, gradingRowMapper,	deadline );
	}
	
	ParameterizedRowMapper<ConferenceProposalGrading> gradingRowMapper	= new ParameterizedRowMapper<ConferenceProposalGrading>(){
		public ConferenceProposalGrading mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposalGrading conferenceProposalGrading = new ConferenceProposalGrading();
			conferenceProposalGrading.setId(rs.getInt("id"));
			conferenceProposalGrading.setApproverId(rs.getInt("approverId"));
			conferenceProposalGrading.setAdminId(rs.getInt("adminId"));
			long deadline = 0;
			Timestamp adeadline = rs.getTimestamp("deadline");
			if (adeadline != null)
				deadline = adeadline.getTime();
			conferenceProposalGrading.setDeadline(deadline);
			long sentForGradingDate = 0;
			Timestamp aSentForGradingDate = rs.getTimestamp("sentForGradingDate");
			if (aSentForGradingDate != null)
				sentForGradingDate = aSentForGradingDate.getTime();
			conferenceProposalGrading.setSentForGradingDate(sentForGradingDate);
			long finishedGradingDate = 0;
			Timestamp aFinishedGradingDate = rs.getTimestamp("finishedGradingDate");
			if (aFinishedGradingDate != null)
				finishedGradingDate = aFinishedGradingDate.getTime();
			conferenceProposalGrading.setFinishedGradingDate(finishedGradingDate);
			conferenceProposalGrading.setAdminSendRemark(rs.getString("adminSendRemark"));
			conferenceProposalGrading.setDeadlineRemark(rs.getString("deadlineRemark"));

			return conferenceProposalGrading;
		}
    };	
    
	public ConferenceProposal getVersionConferenceProposal(int confId, int verId){
		String query = "select  * from conferenceProposalVersion where conferenceProposalId = ? and id = ? ";
		logger.info(query);
		ConferenceProposal conferenceProposal =
		getSimpleJdbcTemplate().queryForObject(query, versionRowMapper,	confId ,verId );
		logger.info("conference proposal id: " + conferenceProposal.getId());
		conferenceProposal.setFromAssosiate(getSupportFromAssosiate(confId));
		conferenceProposal.setFromExternal(getSupportFromExternal(confId));
		conferenceProposal.setFromAdmitanceFee(getSupportFromAdmitanceFee(confId));
		conferenceProposal.setScientificCommittees(getScientificCommittees(confId));
		conferenceProposal.setOperationalCommittees(getOperationalCommittees(confId));
		return conferenceProposal;
	}

	public int getFirstVersion(int confId){
		String query = "select min(id) from conferenceProposalVersion where conferenceProposalId = ?";
		logger.info(query);
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
			logger.info(query);
			return getSimpleJdbcTemplate().queryForInt(query,confId);
		}
	}
	public int getNextVersion(int confId, int verId){
		if (verId==0 || verId==getLastVersion(confId)){
			return getLastVersion(confId);//this is latest version so there is no next
		}
		else{
			String query = "select id from conferenceProposalVersion where conferenceProposalId = ? and id> " + verId + " order by id limit 1";
			logger.info(query);
			return getSimpleJdbcTemplate().queryForInt(query,confId);
		}
	}


	public int getLastVersion(int confId){
		String query = "select max(id) from conferenceProposalVersion where conferenceProposalId = ?";
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query,confId);
	}

	private ParameterizedRowMapper<ConferenceProposal> rowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("id"));
			conferenceProposal.setInternalId(rs.getInt("internalId"));
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
			conferenceProposal.setDeleted(rs.getBoolean("deleted"));
			conferenceProposal.setDeadlineRemarks(rs.getString("deadlineRemarks"));
			conferenceProposal.setIsInsideDeadline(rs.getBoolean("isInsideDeadline"));
			conferenceProposal.setCommitteeRemarks(rs.getString("committeeRemarks"));
            return conferenceProposal;
        }
	};
	private ParameterizedRowMapper<ConferenceProposal> versionRowMapper = new ParameterizedRowMapper<ConferenceProposal>(){
		public ConferenceProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			ConferenceProposal conferenceProposal = new ConferenceProposal();
			conferenceProposal.setId(rs.getInt("conferenceProposalId"));
			conferenceProposal.setInternalId(rs.getInt("internalId"));
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
			conferenceProposal.setDeadlineRemarks(rs.getString("deadlineRemarks"));
			conferenceProposal.setIsInsideDeadline(rs.getBoolean("isInsideDeadline"));
			conferenceProposal.setCommitteeRemarks(rs.getString("committeeRemarks"));
            return conferenceProposal;
        }
	};

	public int insertConferenceProposal(ConferenceProposal conferenceProposal){
		String query = "select max(internalId) from conferenceProposal;";
		logger.info(query);
		int maxInternal= getSimpleJdbcTemplate().queryForInt(query);
		//System.out.println(maxInternal);
		Calendar now = Calendar.getInstance();
		int internalYear=now.get(Calendar.YEAR) * 10000;
		//System.out.println(internalYear);
		final int internalId;
		if(maxInternal>internalYear)
			internalId=maxInternal +1;
		else
			internalId=internalYear+1;
		//System.out.println(internalId);
		
		final String queryS1 = "insert conferenceProposal set personId = ?,approverId=0,openDate=now(),fromDate=now(),toDate=now(),submissionDate='1970-01-01 02:00:01',deadline=?, internalId=?;";
		logger.info(queryS1);
		final int personId = conferenceProposal.getPersonId();
		final long deadline = conferenceProposal.getDeadline();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(queryS1, new String[] {"id"});
		            ps.setInt(1, personId);
		            ps.setTimestamp(2, new java.sql.Timestamp(deadline));
		            ps.setInt(3, internalId);
		            return ps;
		        }
		    },
		    keyHolder);
		final int key=keyHolder.getKey().intValue();
		final String queryS2 = "insert conferenceProposalVersion set conferenceProposalId = ?,personId = ?,approverId=0,openDate=now(),fromDate=now(),toDate=now(),submissionDate='1970-01-01 02:00:01',deadline=?, internalId=?;";
		logger.info(queryS2);
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(queryS2, new String[] {"id"});
		            ps.setInt(1, key);
		            ps.setInt(2, personId);
		            ps.setTimestamp(3, new java.sql.Timestamp(deadline));
		            ps.setInt(4, internalId);
		            return ps;
		        }
		    });
		
		return keyHolder.getKey().intValue();
	}

	public void updateConferenceProposal(ConferenceProposal conferenceProposal){
		String query = "update conferenceProposal set " +
				" personId = ?" +
				", internalId = ?" +
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
				", submissionDate= ?" + 
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
				", deleted= ?" + 
				", deadlineRemarks= ?" + 
				", isInsideDeadline= ?" + 
				", committeeRemarks= ?" +
				" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				conferenceProposal.getPersonId(),
				conferenceProposal.getInternalId(),
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
				new java.sql.Timestamp(conferenceProposal.getSubmissionDate()),
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
				conferenceProposal.getDeleted(),
				conferenceProposal.getDeadlineRemarks(),
				conferenceProposal.getIsInsideDeadline(),
				conferenceProposal.getCommitteeRemarks(),
				conferenceProposal.getId());
		
		insertCommittees(conferenceProposal);		
		insertFinancialSupports(conferenceProposal);	
		
		//insert to version table
		 query = "insert conferenceProposalVersion set "+
				" conferenceProposalId = ?" +
				", personId = ?" + 
				", internalId = ?" +
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
				", openDate=  ?" + 
				", submissionDate= ?" + 
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
				", deadlineRemarks= ?" + 
				", isInsideDeadline= ?" + 
				", committeeRemarks= ?" +
				";";

		logger.info(query);
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId(),
				conferenceProposal.getPersonId(),
				conferenceProposal.getInternalId(),
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
				new java.sql.Timestamp(conferenceProposal.getOpenDate()),
				new java.sql.Timestamp(conferenceProposal.getSubmissionDate()),
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
				conferenceProposal.getDeadlineRemarks(),
				conferenceProposal.getIsInsideDeadline(),
				conferenceProposal.getCommitteeRemarks());
	}


	public void deleteConferenceProposal(int id){
		String query = "delete from conferenceProposal where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query, id);
	}
	
	public List<ConferenceProposal> getConferenceProposals() {
		String query = "select * from conferenceProposal where deleted=0 order by id";
		logger.info(query);
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return conferenceProposals;
    }
	
	public List<ConferenceProposal> getConferenceProposalsByDate(String prevdeadline) {
		String query = "select * from conferenceProposal where deleted=0 and date(deadline)> '" + prevdeadline +"' order by id";
		logger.info(query);
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper);
		return conferenceProposals;
    }
	
	public List<ConferenceProposal> getConferenceProposalsByPerson( int personId) {
		String query = "select * from conferenceProposal where deleted=0 and personId = ?";
		logger.info(query);
		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper, personId);
		return conferenceProposals;
    }

	public List<ConferenceProposal> getConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean, boolean forGrading) {

		String query = "select * from conferenceProposal";
		//get where clause by search critieria
		query += getConferenceProposalsWhereClause(search,userPersonBean,forGrading);

		query += " limit "+ (lv.getPage()-1) * lv.getRowsInPage() + "," + lv.getRowsInPage();

		logger.info(query);

		List<ConferenceProposal> conferenceProposals =
			getSimpleJdbcTemplate().query(query, rowMapper);
		//applyPersonSubjectIds(persons);
		return conferenceProposals;
    }
	
	public int countConferenceProposals(ListView lv, SearchCreteria search, PersonBean userPersonBean, boolean forGrading) {

		String query = "select count(*) from conferenceProposal";
		//get where clause by search critieria
		query += getConferenceProposalsWhereClause(search,userPersonBean,forGrading);
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query);

    }

	public String getConferenceProposalsWhereClause(SearchCreteria search, PersonBean userPersonBean, boolean forGrading){
		
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
		if(!whereClause.equals(""))
			whereClause += " and deleted=0"; 
		else
			whereClause+=" where deleted=0";
		//order by
		if (forGrading){
			whereClause += " order by grade";
		}		
		return whereClause;
	}
	
	
	public void gradeHigher(ConferenceProposal conferenceProposal, String prevdeadline){
		String query = "update conferenceProposal set grade=grade - 1 where deleted=0 and approverId=? and grade=? and date(deadline)>'"+prevdeadline +"';";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()+1);
		logger.info(query);
		query = "update conferenceProposal set grade= grade + 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		logger.info(query);
	}
	public void gradeLower(ConferenceProposal conferenceProposal, String prevdeadline){
		String query = "update conferenceProposal set grade=grade + 1 where deleted=0 and approverId=? and grade=? and date(deadline)>'"+prevdeadline +"';";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getApproverId(),conferenceProposal.getGrade()-1);
		logger.info(query);
		query = "update conferenceProposal set grade= grade - 1 where id=?;";
		getSimpleJdbcTemplate().update(query,conferenceProposal.getId());
		logger.info(query);
	}
	public int getMaxGrade(int approverId, String prevdeadline){
		String query = "select max(grade) from conferenceProposal where deleted=0 and submitted = 1 and approverId=? and date(deadline)>'"+prevdeadline +"';";
		logger.info(query);
		logger.info("Approver id: " + approverId);
		return getSimpleJdbcTemplate().queryForInt(query,approverId);
	}
	public void rearangeGrades(int grade, int approverId, String prevdeadline){
		String query = "update conferenceProposal set grade=grade-1 where deleted=0 and submitted = 1 and grade>? and approverId=? and date(deadline)>'"+prevdeadline +"';";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,grade,approverId);
	}
	public void updateDeadlineRemarks(int approverId, String prevdeadline, String deadlineRemarks){
		String query = "update conferenceProposal set deadlineRemarks =? where deleted=0 and approverId=? and submitted=1 and date(deadline)>'"+prevdeadline +"' and isInsideDeadline=1;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,deadlineRemarks,approverId);
	}

}
