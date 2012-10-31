package huard.iws.db;

import huard.iws.model.CallOfProposal;
import huard.iws.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCallOfProposalDao extends SimpleJdbcDaoSupport implements CallOfProposalDao {

	public CallOfProposal getCallOfProposal(int id){
		String query = "select * from callOfProposal where id =?";
		CallOfProposal callOfProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callOfProposal);
		applySubmissionDates(callOfProposal);
		applyFiles(callOfProposal);
		return 	callOfProposal;	
	}

	public CallOfProposal getCallOfProposal(String title){
		String query = "select * from callOfProposal where title =?";
		CallOfProposal callOfProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, title);
		applySubjectIds(callOfProposal);
		applySubmissionDates(callOfProposal);
		applyFiles(callOfProposal);
		return 	callOfProposal;	
	}

	private void applySubjectIds(CallOfProposal callOfProposal){
		String query = "select * from subjectToCallOfProposal where callOfProposalId = ?";
		List<Integer> subjectsIds =  getSimpleJdbcTemplate().query(query, subjectToPostRowMapper, callOfProposal.getId());
		callOfProposal.setSubjectsIds(subjectsIds);
	}
	private ParameterizedRowMapper<Integer> subjectToPostRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer subjectId = rs.getInt("subjectId");
            return subjectId;
		}
	};
	private void applySubmissionDates(CallOfProposal callOfProposal){
		String query = "select * from callOfProposalDates where callOfProposalId = ? order by submissionDate";
		List<Long> submissionDates =  getSimpleJdbcTemplate().query(query, submissionDatesRowMapper, callOfProposal.getId());
		callOfProposal.setSubmissionDates(submissionDates);
	}
	private ParameterizedRowMapper<Long> submissionDatesRowMapper = new ParameterizedRowMapper<Long>(){
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException{
			long submissionDate = 0;
			Timestamp submissionDateTS = rs.getTimestamp("submissionDate");
			if (submissionDateTS != null)
				submissionDate = submissionDateTS.getTime();
            return submissionDate;
		}
	};
	
	private void applyFiles(CallOfProposal callOfProposal){
		String query = "select * from callOfProposalFiles where callOfProposalId = ?";
		List<byte[]> files =  getSimpleJdbcTemplate().query(query, filesRowMapper, callOfProposal.getId());
		callOfProposal.setFiles(files);
	}
	
	private ParameterizedRowMapper<byte[]> filesRowMapper = new ParameterizedRowMapper<byte[]>(){
		public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException{
			byte[] file = rs.getBytes("fileId");
            return file;
		}
	};

	public int insertCallOfProposal(CallOfProposal callOfProposal){
		final String query = "insert callOfProposal set title='###" + new java.util.Date().getTime() + "###'" +
				", creatorId = ?" +
				", fundId = 0" +
				", typeId = 0" +
				", deskId = 0" +
				", originalCallWebAddress = ''" +
				", description = ''" +
				", fundingPeriod = ''" +
				", amountOfGrant = ''" +
				", eligibilityRequirements = ''" +
				", activityLocation = ''" +
				", possibleCollaboration = ''" +
				", budgetDetails = ''" +
				", additionalInformation = '';";
		logger.info(query);
		final int creatorId= callOfProposal.getCreatorId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, creatorId);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void updateCallOfProposal(CallOfProposal callOfProposal){
		String query = "update callOfProposal set " +
				" title = ?" +
				", creatorId = ?" +
				", publicationTime = ?" +
				", finalSubmissionTime = ?" +
				", allYearSubmission = ?" +
				", allYearSubmissionYearPassedAlert = ?" +
				", hasAdditionalSubmissionDates = ?" +
				", fundId = ?" +
				", typeId = ?" +
				", keepInRollingMessagesExpiryTime = ?" +
				", deskId = ?" +
				", originalCallWebAddress = ?" +
				", requireLogin = ?" +
				", description = ?" +
				", fundingPeriod = ?" +
				", amountOfGrant = ?" +
				", eligibilityRequirements = ?" +
				", activityLocation = ?" +
				", possibleCollaboration = ?" +
				", budgetDetails = ?" +
				", additionalInformation = ?" +
			" where id = ?;";
		System.out.println(query);
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				callOfProposal.getTitle(),
				callOfProposal.getCreatorId(),
				new java.sql.Timestamp(callOfProposal.getPublicationTime()),
				new java.sql.Timestamp(callOfProposal.getFinalSubmissionTime()),
	    		callOfProposal.getAllYearSubmission(),
	    		callOfProposal.getAllYearSubmissionYearPassedAlert(),
	    		callOfProposal.getHasAdditionalSubmissionDates(),
	    		callOfProposal.getFundId(),
	    		callOfProposal.getTypeId(),
	    		new java.sql.Timestamp(callOfProposal.getKeepInRollingMessagesExpiryTime()),
	    		callOfProposal.getDeskId(),
	    		callOfProposal.getOriginalCallWebAddress(),
	    		callOfProposal.getRequireLogin(),
	    		callOfProposal.getDescription(),
	    		callOfProposal.getFundingPeriod(),
	    		callOfProposal.getAmountOfGrant(),
	    		callOfProposal.getEligibilityRequirements(),
	    		callOfProposal.getActivityLocation(),
	    		callOfProposal.getPossibleCollaboration(),
	    		callOfProposal.getBudgetDetails(),
	    		callOfProposal.getAdditionalInformation(),
				callOfProposal.getId());
		
		query = "delete from subjectToCallOfProposal where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (Integer subjectId: callOfProposal.getSubjectsIds()){
			query  = "insert subjectToCallOfProposal set callOfProposalId = ?, subjectId = ?";
			if (subjectId != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), subjectId);
		}
		
		query = "delete from callOfProposalDates where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (Long submissionDate: callOfProposal.getSubmissionDates()){
			query  = "insert callOfProposalDates set callOfProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), new java.sql.Timestamp(submissionDate));
		}
		
		query = "delete from callOfProposalFiles where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (byte[] fileId: callOfProposal.getFiles()){
			query  = "insert callOfProposalFiles set callOfProposalId = ?, fileId = ?";
			if (fileId != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), fileId);
		}
		

	}
	
	public List<CallOfProposal> getCallsOfProposals( boolean open){
		String query = "select * from callOfProposal  ";
		if (open)
				query += " where finalSubmissionTime > " + System.currentTimeMillis() + " or finalSubmissionTime = 0 order by title";
		System.out.println(query);
		List<CallOfProposal> callOfProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callOfProposals;
	}


	public List<CallOfProposal> getCallsOfProposals(){
		String query  = "select * from callOfProposal order by title";
		List<CallOfProposal> callOfProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callOfProposals;
	}
	

	private ParameterizedRowMapper<CallOfProposal> rowMapper = new ParameterizedRowMapper<CallOfProposal>(){
		public CallOfProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            CallOfProposal callOfProposal = new CallOfProposal();
            callOfProposal.setId(rs.getInt("id"));
            callOfProposal.setTitle(rs.getString("title"));
    		callOfProposal.setCreatorId(rs.getInt("creatorId"));
			long creationTime = 0;
			Timestamp creationTimeTS = rs.getTimestamp("creationTime");
			if (creationTimeTS != null)
				creationTime = creationTimeTS.getTime();
    		callOfProposal.setCreationTime(creationTime);
			long publicationTime = 0;
			Timestamp publicationTimeTS = rs.getTimestamp("publicationTime");
			if (publicationTimeTS != null)
				publicationTime = publicationTimeTS.getTime();
			callOfProposal.setPublicationTime(publicationTime);
			long finalSubmissionTime = 0;
			Timestamp finalSubmissionTimeTS = rs.getTimestamp("finalSubmissionTime");
			if (finalSubmissionTimeTS != null)
				finalSubmissionTime = finalSubmissionTimeTS.getTime();
    		callOfProposal.setFinalSubmissionTime(finalSubmissionTime);
    		callOfProposal.setAllYearSubmission(rs.getBoolean("allYearSubmission"));
    		callOfProposal.setAllYearSubmissionYearPassedAlert(rs.getBoolean("allYearSubmissionYearPassedAlert"));
    		callOfProposal.setHasAdditionalSubmissionDates(rs.getBoolean("hasAdditionalSubmissionDates"));
    		callOfProposal.setFundId(rs.getInt("fundId"));
    		callOfProposal.setTypeId(rs.getInt("typeId"));
			long keepInRollingMessagesExpiryTime = 0;
			Timestamp keepInRollingMessagesExpiryTimeTS = rs.getTimestamp("keepInRollingMessagesExpiryTime");
			if (keepInRollingMessagesExpiryTimeTS != null)
				keepInRollingMessagesExpiryTime = keepInRollingMessagesExpiryTimeTS.getTime();
    		callOfProposal.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
    		callOfProposal.setDeskId(rs.getInt("deskId"));
    		callOfProposal.setOriginalCallWebAddress(rs.getString("originalCallWebAddress"));
    		callOfProposal.setRequireLogin(rs.getBoolean("requireLogin"));
    		callOfProposal.setDescription(rs.getString("description"));
    		callOfProposal.setFundingPeriod(rs.getString("fundingPeriod"));
    		callOfProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
    		callOfProposal.setEligibilityRequirements(rs.getString("eligibilityRequirements"));
    		callOfProposal.setActivityLocation(rs.getString("activityLocation"));
    		callOfProposal.setPossibleCollaboration(rs.getString("possibleCollaboration"));
    		callOfProposal.setBudgetDetails(rs.getString("budgetDetails"));
    		callOfProposal.setAdditionalInformation(rs.getString("additionalInformation"));
           return callOfProposal;
        }
	};
	

}
