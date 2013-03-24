package huard.iws.db;

import huard.iws.model.CallForProposal;
import huard.iws.model.Attachment;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCallForProposalDao extends SimpleJdbcDaoSupport implements CallForProposalDao {

	public CallForProposal getCallForProposal(int id){
		String query = "select * from callOfProposalDraft where id =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}
	
	public CallForProposal getCallForProposalOnline(int id){
		String query = "select * from callOfProposal where id =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}
	
	public boolean existsCallForProposalOnline(int id){
		String query = "select * from callOfProposal where id =?";
		try{
			CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public CallForProposal getCallForProposal(String title){
		String query = "select * from callOfProposalDraft where title =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, title);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}

	private void applySubjectIds(CallForProposal callForProposal){
		String query = "select * from subjectToCallOfProposal where callOfProposalId = ?";
		List<Integer> subjectsIds =  getSimpleJdbcTemplate().query(query, subjectToPostRowMapper, callForProposal.getId());
		callForProposal.setSubjectsIds(subjectsIds);
	}
	private ParameterizedRowMapper<Integer> subjectToPostRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer subjectId = rs.getInt("subjectId");
            return subjectId;
		}
	};
	private void applySubmissionDates(CallForProposal callForProposal){
		String query = "select * from callOfProposalDate where callOfProposalId = ? order by submissionDate";
		List<Long> submissionDates =  getSimpleJdbcTemplate().query(query, submissionDatesRowMapper, callForProposal.getId());
		callForProposal.setSubmissionDates(submissionDates);
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
	
	private void applyFiles(CallForProposal callForProposal){
		String query = "select * from callOfProposalFile where callOfProposalId = ?";
		List<Attachment> files =  getSimpleJdbcTemplate().query(query, filesRowMapper, callForProposal.getId());
		callForProposal.setAttachments(files);
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

	public int insertCallForProposal(CallForProposal callForProposal){

		if(callForProposal.getTitle().isEmpty())
			callForProposal.setTitle("###" + new java.util.Date().getTime() + "###");
		
		final String query = "insert ignore callOfProposalDraft set title = '" + callForProposal.getTitle().replace("'","") + "'"+
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
				", localeId=?"+
				", updateTime=now();";
		logger.info(query);
		final int creatorId= callForProposal.getCreatorId();
		final String localeId= callForProposal.getLocaleId();
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
		return keyHolder.getKey()==null?0:keyHolder.getKey().intValue();
	}
	
	public void insertCallForProposalOnline(CallForProposal callForProposal){
		String keepInRollingMessagesExpiryTime="";
		if(callForProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callForProposal.getKeepInRollingMessagesExpiryTime()).toString();
		final String query = "insert callOfProposal set id = ?"+
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
				", localeId = ?" + 
				", updateTime = ?"+ 
				", isDeleted = ?";
		logger.info(query);
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=new java.sql.Timestamp(callForProposal.getFinalSubmissionTime()).toString();
		String updateTime="";
		if(callForProposal.getUpdateTime()==0)
			updateTime=new java.sql.Timestamp(new java.util.Date().getTime()).toString();
		else
			updateTime=new java.sql.Timestamp(callForProposal.getUpdateTime()).toString();
		getSimpleJdbcTemplate().update(query,
				callForProposal.getId(),
				callForProposal.getTitle(),
				callForProposal.getCreatorId(),
				new java.sql.Timestamp(callForProposal.getPublicationTime()),
				finalSubmissionTime,
	    		callForProposal.getAllYearSubmission(),
	    		callForProposal.getAllYearSubmissionYearPassedAlert(),
	    		callForProposal.getHasAdditionalSubmissionDates(),
	    		callForProposal.getFundId(),
	    		callForProposal.getTypeId(),
	    		keepInRollingMessagesExpiryTime,
	    		callForProposal.getDeskId(),
	    		callForProposal.getOriginalCallWebAddress(),
	    		callForProposal.getRequireLogin(),
	    		callForProposal.getShowDescriptionOnly(),
	    		callForProposal.getSubmissionDetails().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFormDetails().trim(),
	    		callForProposal.getDescription().trim(),
	    		callForProposal.getFundingPeriod().trim(),
	    		callForProposal.getAmountOfGrant().trim(),
	    		callForProposal.getEligibilityRequirements().trim(),
	    		callForProposal.getActivityLocation().trim(),
	    		callForProposal.getPossibleCollaboration().trim(),
	    		callForProposal.getBudgetDetails().trim(),
	    		callForProposal.getAdditionalInformation().trim(),
	    		callForProposal.getLocaleId(),
	    		updateTime,
	    		callForProposal.getIsDeleted());
	}
	

	public void updateCallForProposal(CallForProposal callForProposal){
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
				", updateTime = ?" +
				", isDeleted = ?" +
			" where id = ?;";
		System.out.println(query);
		logger.info(query);
		String publicationTime="";
		if(callForProposal.getPublicationTime()==0)//
			publicationTime="0000-00-00 00:00:00";
		else
			publicationTime=new java.sql.Timestamp(callForProposal.getPublicationTime()).toString();
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)//
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=new java.sql.Timestamp(callForProposal.getFinalSubmissionTime()).toString();
		String keepInRollingMessagesExpiryTime="";
		if(callForProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callForProposal.getKeepInRollingMessagesExpiryTime()).toString();
		String updateTime="";
		if(callForProposal.getUpdateTime()==0)
			updateTime=new java.sql.Timestamp(new java.util.Date().getTime()).toString();
		else
			updateTime=new java.sql.Timestamp(callForProposal.getUpdateTime()).toString();
			getSimpleJdbcTemplate().update(query,
				callForProposal.getTitle(),
				callForProposal.getCreatorId(),
				publicationTime,
				finalSubmissionTime,
	    		callForProposal.getAllYearSubmission(),
	    		callForProposal.getAllYearSubmissionYearPassedAlert(),
	    		callForProposal.getHasAdditionalSubmissionDates(),
	    		callForProposal.getFundId(),
	    		callForProposal.getTypeId(),
	    		keepInRollingMessagesExpiryTime,
	    		callForProposal.getDeskId(),
	    		callForProposal.getOriginalCallWebAddress(),
	    		callForProposal.getRequireLogin(),
	    		callForProposal.getShowDescriptionOnly(),
	    		callForProposal.getSubmissionDetails().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFormDetails().trim(),
	    		callForProposal.getDescription().trim(),
	    		callForProposal.getFundingPeriod().trim(),
	    		callForProposal.getAmountOfGrant().trim(),
	    		callForProposal.getEligibilityRequirements().trim(),
	    		callForProposal.getActivityLocation().trim(),
	    		callForProposal.getPossibleCollaboration().trim(),
	    		callForProposal.getBudgetDetails().trim(),
	    		callForProposal.getAdditionalInformation().trim(),
	    		callForProposal.getLocaleId(),
	    		updateTime,
	    		callForProposal.getIsDeleted(),
				callForProposal.getId());
		
		query = "delete from subjectToCallOfProposal where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callForProposal.getSubjectsIds()){
				query  = "insert subjectToCallOfProposal set callOfProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callOfProposalDate where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		for (Long submissionDate: callForProposal.getSubmissionDates()){
			query  = "insert callOfProposalDate set callOfProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), new java.sql.Timestamp(submissionDate));
		}
		
		if(callForProposal.getAttachments()!=null){
			for (Attachment attachment: callForProposal.getAttachments()){
				query  = "insert callOfProposalFile set callOfProposalId = ?, fileId = ?, contentType= ?, title= ?";
				if (attachment != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), attachment.getFile(), attachment.getContentType(), attachment.getTitle());
			}
		}
	}
	public void updateCallForProposalOnline(CallForProposal callForProposal){
		String keepInRollingMessagesExpiryTime="";
		if(callForProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=new java.sql.Timestamp(callForProposal.getKeepInRollingMessagesExpiryTime()).toString();
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)//
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=new java.sql.Timestamp(callForProposal.getFinalSubmissionTime()).toString();
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
				", updateTime = now()" +
				", isDeleted = ?" +
			" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				callForProposal.getTitle(),
				callForProposal.getCreatorId(),
				new java.sql.Timestamp(callForProposal.getPublicationTime()),
				finalSubmissionTime,
	    		callForProposal.getAllYearSubmission(),
	    		callForProposal.getAllYearSubmissionYearPassedAlert(),
	    		callForProposal.getHasAdditionalSubmissionDates(),
	    		callForProposal.getFundId(),
	    		callForProposal.getTypeId(),
	    		keepInRollingMessagesExpiryTime,
	    		callForProposal.getDeskId(),
	    		callForProposal.getOriginalCallWebAddress(),
	    		callForProposal.getRequireLogin(),
	    		callForProposal.getShowDescriptionOnly(),
	    		callForProposal.getSubmissionDetails().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFormDetails().trim(),
	    		callForProposal.getDescription().trim(),
	    		callForProposal.getFundingPeriod().trim(),
	    		callForProposal.getAmountOfGrant().trim(),
	    		callForProposal.getEligibilityRequirements().trim(),
	    		callForProposal.getActivityLocation().trim(),
	    		callForProposal.getPossibleCollaboration().trim(),
	    		callForProposal.getBudgetDetails().trim(),
	    		callForProposal.getAdditionalInformation().trim(),
	    		callForProposal.getLocaleId(),
	    		callForProposal.getIsDeleted(),
				callForProposal.getId());
		
		/*query = "delete from subjectToCallOfProposal where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callForProposal.getSubjectsIds()){
				query  = "insert subjectToCallOfProposal set callOfProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callOfProposalDate where callOfProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		for (Long submissionDate: callForProposal.getSubmissionDates()){
			query  = "insert callOfProposalDate set callOfProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), new java.sql.Timestamp(submissionDate));
		}
		
		for (Attachment attachment: callForProposal.getAttachments()){
			query  = "insert callOfProposalFile set callOfProposalId = ?, fileId = ?, contentType= ?, title= ?";
			if (attachment != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), attachment.getFile(), attachment.getContentType(), attachment.getTitle());
		}
		 */
	}	
	
	public void removeCallForProposalOnline(int id){
		String query = "delete from callOfProposal where id= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<CallForProposal> getCallForProposals(CallForProposalSearchCreteria searchCriteria){
		String query  = "select distinct callOfProposalDraft.* from callOfProposalDraft";
		query += getCallForProposalsWhereClause(searchCriteria,"callOfProposalDraft");
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}
	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria){
		String query  = "select distinct callOfProposal.* from callOfProposal";
		query += getCallForProposalsWhereClause(searchCriteria,"callOfProposal");
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	
	public String getCallForProposalsWhereClause(CallForProposalSearchCreteria searchCriteria, String mainTable){
		String whereClause="";
		if (searchCriteria.getSearchByTemporaryFund())
			whereClause += " inner join fund on fund.financialId=" + mainTable +".fundId";
		if(!searchCriteria.getSearchBySubjectIds().isEmpty())
			whereClause += " inner join subjectToCallOfProposal on subjectToCallOfProposal.callOfProposalId=" + mainTable +".id";
		whereClause += " where true";
		if(searchCriteria.getSearchByTemporaryFund())
			whereClause +=" and fund.isTemporary=1";
		if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty())
			whereClause +=" and " + mainTable +".finalSubmissionTime >='"+searchCriteria.getSearchBySubmissionDateFrom()+"'";
		if(!searchCriteria.getSearchBySubmissionDateTo().isEmpty())
			whereClause +=" and " + mainTable +".finalSubmissionTime <='"+searchCriteria.getSearchBySubmissionDateTo()+"'";
		if(searchCriteria.getSearchByFund()>0)
			whereClause +=" and " + mainTable +".fundId="+searchCriteria.getSearchByFund();
		if(searchCriteria.getSearchByDesk()>0)
			whereClause +=" and " + mainTable +".deskId="+searchCriteria.getSearchByDesk();
		if(searchCriteria.getSearchByType()>0)
			whereClause +=" and " + mainTable +".typeId="+searchCriteria.getSearchByType();
		if(searchCriteria.getSearchByCreator()>0)
			whereClause +=" and " + mainTable +".creatorId="+searchCriteria.getSearchByCreator();
		if(!searchCriteria.getSearchBySearchWords().isEmpty())
			whereClause +=" and " + mainTable +".id in ("+searchCriteria.getSearchBySearchWords() + ")";
		if(!searchCriteria.getSearchBySubjectIds().isEmpty())
			whereClause +=" and subjectToCallOfProposal.subjectId in ("+searchCriteria.getSearchBySubjectIds() + ")";
		if(!searchCriteria.getSearchDeleted())//not include deleted
			whereClause +=" and " + mainTable +".isDeleted=0";
		if(!searchCriteria.getSearchExpired())//not include expired
			whereClause +=" and (" + mainTable +".finalSubmissionTime < now() or " + mainTable +".finalSubmissionTime = 0)";
			
		whereClause += "  order by " + mainTable +".id";
		
		logger.info(whereClause);
		return whereClause;
	}
	
	public List<CallForProposal> getCallForProposalsOnline(String ids ){
		String query  = "select distinct callOfProposal.* from callOfProposal";
		if(!ids.isEmpty())
			query += " where id in ("+ids + ") and isDeleted=0 order by id";
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	private ParameterizedRowMapper<CallForProposal> rowMapper = new ParameterizedRowMapper<CallForProposal>(){
		public CallForProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            CallForProposal callForProposal = new CallForProposal();
            callForProposal.setId(rs.getInt("id"));
            callForProposal.setTitle(rs.getString("title"));
    		callForProposal.setCreatorId(rs.getInt("creatorId"));
			long creationTime = 0;
			Timestamp creationTimeTS = rs.getTimestamp("creationTime");
			if (creationTimeTS != null)
				creationTime = creationTimeTS.getTime();
    		callForProposal.setCreationTime(creationTime);
			long publicationTime = 0;
			Timestamp publicationTimeTS = rs.getTimestamp("publicationTime");
			if (publicationTimeTS != null)
				publicationTime = publicationTimeTS.getTime();
			callForProposal.setPublicationTime(publicationTime);
			long finalSubmissionTime = 0;
			Timestamp finalSubmissionTimeTS = rs.getTimestamp("finalSubmissionTime");
			if (finalSubmissionTimeTS != null)
				finalSubmissionTime = finalSubmissionTimeTS.getTime();
    		callForProposal.setFinalSubmissionTime(finalSubmissionTime);
    		callForProposal.setAllYearSubmission(rs.getBoolean("allYearSubmission"));
    		callForProposal.setAllYearSubmissionYearPassedAlert(rs.getBoolean("allYearSubmissionYearPassedAlert"));
    		callForProposal.setHasAdditionalSubmissionDates(rs.getBoolean("hasAdditionalSubmissionDates"));
    		callForProposal.setFundId(rs.getInt("fundId"));
    		callForProposal.setTypeId(rs.getInt("typeId"));
			long keepInRollingMessagesExpiryTime = 0;
			Timestamp keepInRollingMessagesExpiryTimeTS = rs.getTimestamp("keepInRollingMessagesExpiryTime");
			if (keepInRollingMessagesExpiryTimeTS != null)
				keepInRollingMessagesExpiryTime = keepInRollingMessagesExpiryTimeTS.getTime();
    		callForProposal.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
    		callForProposal.setDeskId(rs.getInt("deskId"));
    		callForProposal.setOriginalCallWebAddress(rs.getString("originalCallWebAddress"));
    		callForProposal.setRequireLogin(rs.getBoolean("requireLogin"));
    		callForProposal.setShowDescriptionOnly(rs.getBoolean("showDescriptionOnly"));
    		callForProposal.setSubmissionDetails(rs.getString("submissionDetails"));
    		callForProposal.setContactPersonDetails(rs.getString("contactPersonDetails"));
    		callForProposal.setFormDetails(rs.getString("formDetails"));
    		callForProposal.setDescription(rs.getString("description"));
    		callForProposal.setFundingPeriod(rs.getString("fundingPeriod"));
    		callForProposal.setAmountOfGrant(rs.getString("amountOfGrant"));
    		callForProposal.setEligibilityRequirements(rs.getString("eligibilityRequirements"));
    		callForProposal.setActivityLocation(rs.getString("activityLocation"));
    		callForProposal.setPossibleCollaboration(rs.getString("possibleCollaboration"));
    		callForProposal.setBudgetDetails(rs.getString("budgetDetails"));
    		callForProposal.setAdditionalInformation(rs.getString("additionalInformation"));
    		callForProposal.setLocaleId(rs.getString("localeId"));
			long updateTime = 0;
			Timestamp updateTimeTS = rs.getTimestamp("updateTime");
			if (updateTimeTS != null)
				updateTime = updateTimeTS.getTime();
    		callForProposal.setUpdateTime(updateTime);
           return callForProposal;
        }
	};
	
	public void insertArdNum(int ardNum,int id){
		String query  = "insert callForProposalHistoryId set callForProposalId = ?, callForProposalHistoryId = ?";
		getSimpleJdbcTemplate().update(query,id, ardNum);
	}

	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment){
		final String query  = "insert callOfProposalFile set callOfProposalId = ?, fileId = ?, contentType= ?, title= ?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final int finalCallForProposalId= callForProposalId;
		final byte[] file= attachment.getFile();
		final String contentType = attachment.getContentType();
		final String title = attachment.getTitle();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, finalCallForProposalId);
						ps.setBytes(2, file);
						ps.setString(3, contentType);
						ps.setString(4, title);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}

}
