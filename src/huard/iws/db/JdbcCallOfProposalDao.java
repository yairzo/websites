package huard.iws.db;

import huard.iws.model.CallOfProposal;
import huard.iws.model.Attachment;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCallOfProposalDao extends SimpleJdbcDaoSupport implements CallOfProposalDao {

	public CallOfProposal getCallOfProposal(int id){
		String query = "select * from callOfProposalDraft where id =?";
		CallOfProposal callOfProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callOfProposal);
		applySubmissionDates(callOfProposal);
		applyFiles(callOfProposal);
		return 	callOfProposal;	
	}
	
	public boolean existsCallOfProposalOnline(int id){
		String query = "select * from callOfProposal where callOfProposalId =?";
		try{
			CallOfProposal callOfProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public CallOfProposal getCallOfProposal(String title){
		String query = "select * from callOfProposalDraft where title =?";
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
		String query = "select * from callOfProposalDate where callOfProposalId = ? order by submissionDate";
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
		String query = "select * from callOfProposalFile where callOfProposalId = ?";
		List<Attachment> files =  getSimpleJdbcTemplate().query(query, filesRowMapper, callOfProposal.getId());
		callOfProposal.setAttachments(files);
	}
	
	private ParameterizedRowMapper<Attachment> filesRowMapper = new ParameterizedRowMapper<Attachment>(){
		public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException{
			Attachment file = new Attachment();
			file.setFile(rs.getBytes("fileId"));
			file.setContentType(rs.getString("contentType"));
			file.setTitle(rs.getString("title"));
			file.setId(rs.getInt("id"));
            return file;
		}
	};

	public int insertCallOfProposal(CallOfProposal callOfProposal){
		final String query = "insert callOfProposalDraft set title='###" + new java.util.Date().getTime() + "###'" +
				", creatorId = ?" +
				", publicationTime = now()" +
				", fundId = 0" +
				", typeId = 0" +
				", deskId = 0" +
				", originalCallWebAddress = ''" +
				", submissionDetails = ''" +
				", contactPersonDetails = ''" +
				", formDetails = ''" +
				", description = ''" +
				", fundingPeriod = ''" +
				", amountOfGrant = ''" +
				", eligibilityRequirements = ''" +
				", activityLocation = ''" +
				", possibleCollaboration = ''" +
				", budgetDetails = ''" +
				", additionalInformation = ''"+
				", localeId=?;";
		logger.info(query);
		final int creatorId= callOfProposal.getCreatorId();
		final String localeId= callOfProposal.getLocaleId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, creatorId);
						ps.setString(2, localeId);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void insertCallOfProposalOnline(CallOfProposal callOfProposal){
		String keepInRollingMessagesExpiryTime="";
		if(callOfProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callOfProposal.getKeepInRollingMessagesExpiryTime()).toString();
		final String query = "insert callOfProposal set callOfProposalId = ?"+
				", title = ?" +
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
				", showDescriptionOnly = ?" +				
				", submissionDetails= ?" +
				", contactPersonDetails= ?" +
				", formDetails= ?" +
				", description = ?" +
				", fundingPeriod = ?" +
				", amountOfGrant = ?" +
				", eligibilityRequirements = ?" +
				", activityLocation = ?" +
				", possibleCollaboration = ?" +
				", budgetDetails = ?" +
				", additionalInformation = ?" +
				", localeId = ?";
		System.out.println("1111111:" + query);
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				callOfProposal.getId(),
				callOfProposal.getTitle(),
				callOfProposal.getCreatorId(),
				new java.sql.Timestamp(callOfProposal.getPublicationTime()),
				new java.sql.Timestamp(callOfProposal.getFinalSubmissionTime()),
	    		callOfProposal.getAllYearSubmission(),
	    		callOfProposal.getAllYearSubmissionYearPassedAlert(),
	    		callOfProposal.getHasAdditionalSubmissionDates(),
	    		callOfProposal.getFundId(),
	    		callOfProposal.getTypeId(),
	    		keepInRollingMessagesExpiryTime,
	    		callOfProposal.getDeskId(),
	    		callOfProposal.getOriginalCallWebAddress(),
	    		callOfProposal.getRequireLogin(),
	    		callOfProposal.getShowDescriptionOnly(),
	    		callOfProposal.getSubmissionDetails().trim(),
	    		callOfProposal.getContactPersonDetails().trim(),
	    		callOfProposal.getFormDetails().trim(),
	    		callOfProposal.getDescription().trim(),
	    		callOfProposal.getFundingPeriod().trim(),
	    		callOfProposal.getAmountOfGrant().trim(),
	    		callOfProposal.getEligibilityRequirements().trim(),
	    		callOfProposal.getActivityLocation().trim(),
	    		callOfProposal.getPossibleCollaboration().trim(),
	    		callOfProposal.getBudgetDetails().trim(),
	    		callOfProposal.getAdditionalInformation().trim(),
	    		callOfProposal.getLocaleId());
	}
	
	public void updateCallOfProposal(CallOfProposal callOfProposal){
		String query = "update callOfProposalDraft set " +
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
				", showDescriptionOnly = ?" +				
				", submissionDetails= ?" +
				", contactPersonDetails= ?" +
				", formDetails= ?" +
				", description = ?" +
				", fundingPeriod = ?" +
				", amountOfGrant = ?" +
				", eligibilityRequirements = ?" +
				", activityLocation = ?" +
				", possibleCollaboration = ?" +
				", budgetDetails = ?" +
				", additionalInformation = ?" +
				", localeId = ?" +
			" where id = ?;";
		System.out.println(query);
		logger.info(query);
		String publicationTime="";
		if(callOfProposal.getPublicationTime()==0)//
			publicationTime="0000-00-00 00:00:00";
		else
			publicationTime=new java.sql.Timestamp(callOfProposal.getPublicationTime()).toString();
		String finalSubmissionTime="";
		if(callOfProposal.getFinalSubmissionTime()==0)//
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=new java.sql.Timestamp(callOfProposal.getFinalSubmissionTime()).toString();
		String keepInRollingMessagesExpiryTime="";
		if(callOfProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callOfProposal.getKeepInRollingMessagesExpiryTime()).toString();
		getSimpleJdbcTemplate().update(query,
				callOfProposal.getTitle(),
				callOfProposal.getCreatorId(),
				publicationTime,
				finalSubmissionTime,
	    		callOfProposal.getAllYearSubmission(),
	    		callOfProposal.getAllYearSubmissionYearPassedAlert(),
	    		callOfProposal.getHasAdditionalSubmissionDates(),
	    		callOfProposal.getFundId(),
	    		callOfProposal.getTypeId(),
	    		keepInRollingMessagesExpiryTime,
	    		callOfProposal.getDeskId(),
	    		callOfProposal.getOriginalCallWebAddress(),
	    		callOfProposal.getRequireLogin(),
	    		callOfProposal.getShowDescriptionOnly(),
	    		callOfProposal.getSubmissionDetails().trim(),
	    		callOfProposal.getContactPersonDetails().trim(),
	    		callOfProposal.getFormDetails().trim(),
	    		callOfProposal.getDescription().trim(),
	    		callOfProposal.getFundingPeriod().trim(),
	    		callOfProposal.getAmountOfGrant().trim(),
	    		callOfProposal.getEligibilityRequirements().trim(),
	    		callOfProposal.getActivityLocation().trim(),
	    		callOfProposal.getPossibleCollaboration().trim(),
	    		callOfProposal.getBudgetDetails().trim(),
	    		callOfProposal.getAdditionalInformation().trim(),
	    		callOfProposal.getLocaleId(),
				callOfProposal.getId());
		
		query = "delete from subjectToCallOfProposal where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		if(callOfProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callOfProposal.getSubjectsIds()){
				query  = "insert subjectToCallOfProposal set callOfProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callOfProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callOfProposalDate where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (Long submissionDate: callOfProposal.getSubmissionDates()){
			query  = "insert callOfProposalDate set callOfProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), new java.sql.Timestamp(submissionDate));
		}
		
		//query = "delete from callOfProposalFile where callOfProposalId = ?";
		//getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (Attachment attachment: callOfProposal.getAttachments()){
			query  = "insert callOfProposalFile set callOfProposalId = ?, fileId = ?, contentType= ?, title= ?";
			if (attachment != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), attachment.getFile(), attachment.getContentType(), attachment.getTitle());
		}
	}
	public void updateCallOfProposalOnline(CallOfProposal callOfProposal){
		String keepInRollingMessagesExpiryTime="";
		if(callOfProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callOfProposal.getKeepInRollingMessagesExpiryTime()).toString();
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
				", showDescriptionOnly = ?" +
				", submissionDetails= ?" +
				", contactPersonDetails= ?" +
				", formDetails= ?" +
				", description = ?" +
				", fundingPeriod = ?" +
				", amountOfGrant = ?" +
				", eligibilityRequirements = ?" +
				", activityLocation = ?" +
				", possibleCollaboration = ?" +
				", budgetDetails = ?" +
				", additionalInformation = ?" +
				", localeId = ?" +
			" where callOfProposalId = ?;";
		System.out.println("111111111111111:"+query);
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
	    		keepInRollingMessagesExpiryTime,
	    		callOfProposal.getDeskId(),
	    		callOfProposal.getOriginalCallWebAddress(),
	    		callOfProposal.getRequireLogin(),
	    		callOfProposal.getShowDescriptionOnly(),
	    		callOfProposal.getSubmissionDetails(),
	    		callOfProposal.getContactPersonDetails(),
	    		callOfProposal.getFormDetails(),
	    		callOfProposal.getDescription(),
	    		callOfProposal.getFundingPeriod(),
	    		callOfProposal.getAmountOfGrant(),
	    		callOfProposal.getEligibilityRequirements(),
	    		callOfProposal.getActivityLocation(),
	    		callOfProposal.getPossibleCollaboration(),
	    		callOfProposal.getBudgetDetails(),
	    		callOfProposal.getAdditionalInformation(),
	    		callOfProposal.getLocaleId(),
				callOfProposal.getId());
		
		/*query = "delete from subjectToCallOfProposal where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		if(callOfProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callOfProposal.getSubjectsIds()){
				query  = "insert subjectToCallOfProposal set callOfProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callOfProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callOfProposalDate where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callOfProposal.getId());
		for (Long submissionDate: callOfProposal.getSubmissionDates()){
			query  = "insert callOfProposalDate set callOfProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), new java.sql.Timestamp(submissionDate));
		}
		
		for (Attachment attachment: callOfProposal.getAttachments()){
			query  = "insert callOfProposalFile set callOfProposalId = ?, fileId = ?, contentType= ?, title= ?";
			if (attachment != null)
				getSimpleJdbcTemplate().update(query,callOfProposal.getId(), attachment.getFile(), attachment.getContentType(), attachment.getTitle());
		}
		 */
	}	
	
	public void removeCallOfProposalOnline(int id){
		String query = "delete from callOfProposal where callOfProposalId= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<CallOfProposal> getCallsOfProposals( boolean temporaryFund, boolean open){
		String query = "select callOfProposalDraft.* from callOfProposalDraft  ";
		if (open)
				query += " where finalSubmissionTime > " + System.currentTimeMillis() + " or finalSubmissionTime = 0";
		query += getCallOfProposalsWhereClause(temporaryFund);
		System.out.println(query);
		List<CallOfProposal> callOfProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callOfProposals;
	}

	public List<CallOfProposal> getCallsOfProposals(boolean temporaryFund){
		String query  = "select callOfProposalDraft.* from callOfProposalDraft";
		query += getCallOfProposalsWhereClause(temporaryFund);
		List<CallOfProposal> callOfProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callOfProposals;
	}
	
	public String getCallOfProposalsWhereClause(boolean temporaryFund){
		String whereClause="";
		if (temporaryFund)
			whereClause += ",fund where fund.financialId=callOfProposalDraft.fundId and fund.isTemporary=1 ";
		whereClause += "  order by callOfProposalDraft.title";
		System.out.println("111111111111111111:"+whereClause);
		return whereClause;
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
    		callOfProposal.setShowDescriptionOnly(rs.getBoolean("showDescriptionOnly"));
    		callOfProposal.setSubmissionDetails(rs.getString("submissionDetails"));
    		callOfProposal.setContactPersonDetails(rs.getString("contactPersonDetails"));
    		callOfProposal.setFormDetails(rs.getString("formDetails"));
    		callOfProposal.setDescription(rs.getString("description"));
    		callOfProposal.setFundingPeriod(rs.getString("fundingPeriod"));
    		callOfProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
    		callOfProposal.setEligibilityRequirements(rs.getString("eligibilityRequirements"));
    		callOfProposal.setActivityLocation(rs.getString("activityLocation"));
    		callOfProposal.setPossibleCollaboration(rs.getString("possibleCollaboration"));
    		callOfProposal.setBudgetDetails(rs.getString("budgetDetails"));
    		callOfProposal.setAdditionalInformation(rs.getString("additionalInformation"));
    		callOfProposal.setLocaleId(rs.getString("localeId"));
           return callOfProposal;
        }
	};
	

}
