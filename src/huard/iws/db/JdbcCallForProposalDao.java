package huard.iws.db;

import huard.iws.model.CallForProposal;
import huard.iws.model.DayInCalendar;
import huard.iws.model.Attachment;
import huard.iws.model.FundInDay;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.ListView;
import huard.iws.util.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcCallForProposalDao extends SimpleJdbcDaoSupport implements CallForProposalDao {

	public CallForProposal getCallForProposal(int id){
		String query = "select * from callForProposalDraft where id =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}
	
	public CallForProposal getCallForProposalOnline(int id){
		String query = "select * from callForProposal where id =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}
	
	public boolean existsCallForProposalOnline(int id){
		String query = "select * from callForProposal where id =?";
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
		String query = "select * from callForProposalDraft where title =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, title);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		return 	callForProposal;	
	}

	private void applySubjectIds(CallForProposal callForProposal){
		String query = "select * from subjectToCallForProposal where callForProposalId = ?";
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
		String query = "select * from callForProposalDate where callForProposalId = ? order by submissionDate";
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
		String query = "select * from callForProposalFile where callForProposalId = ?";
		List<Attachment> files =  getSimpleJdbcTemplate().query(query, filesRowMapper, callForProposal.getId());
		callForProposal.setAttachments(files);
	}
	
	public void deleteFile(int id){
		String query = "delete from callForProposalFile where id = ?";
		getSimpleJdbcTemplate().update(query,id);
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
		
		final String query = "insert ignore callForProposalDraft set title = '" + callForProposal.getTitle().replace("'","") + "'"+
				", urlTitle = ?" +
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
		//logger.info(query);
		final String urlTitle= callForProposal.getUrlTitle();
		final int creatorId= callForProposal.getCreatorId();
		final String localeId= callForProposal.getLocaleId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
						ps.setString(1, urlTitle);
						ps.setInt(2, creatorId);
						ps.setString(3, localeId);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey()==null?0:keyHolder.getKey().intValue();
	}
	
	public void insertCallForProposalOnline(CallForProposal callForProposal){
		final String query = "insert callForProposal set id = ?"+
				", title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", publicationTime = ?" +
				", finalSubmissionTime = ?" +
				", finalSubmissionHour = ?" +
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
				", updateTime = now()"+ 
				", isDeleted = ?"+
				", targetAudience = ?";
		//logger.info(query);
		String keepInRollingMessagesExpiryTime="";
		if(callForProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getKeepInRollingMessagesExpiryTime());
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getFinalSubmissionTime());
		String publicationTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getPublicationTime());
		getSimpleJdbcTemplate().update(query,
				callForProposal.getId(),
				callForProposal.getTitle(),
				callForProposal.getUrlTitle(),
				callForProposal.getCreatorId(),
				publicationTime,
				finalSubmissionTime,
				callForProposal.getFinalSubmissionHour(),
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
	    		callForProposal.getTargetAudience());
	}
	

	public void updateCallForProposal(CallForProposal callForProposal){
		System.out.println("111111111111:"+callForProposal.getFormDetails());
		String query = "update callForProposalDraft set " +
				" title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", publicationTime = ?" +
				", finalSubmissionTime = ?" +
				", finalSubmissionHour = ?" +
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
				", targetAudience = ?" +
			" where id = ?;";
		logger.info(query);

		String keepInRollingMessagesExpiryTime="";
		if(callForProposal.getKeepInRollingMessagesExpiryTime()==0)//
			keepInRollingMessagesExpiryTime="0000-00-00 00:00:00";
		else
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getKeepInRollingMessagesExpiryTime());
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getFinalSubmissionTime());
		String publicationTime="";
		if(callForProposal.getPublicationTime()==0)
			publicationTime="0000-00-00 00:00:00";
		else
			publicationTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getPublicationTime());
		String updateTime="";
		if(callForProposal.getUpdateTime()==0)
			updateTime=DateUtils.formatTimestampWithoutMillis(new java.util.Date().getTime());//always
		else// when from import
			updateTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getUpdateTime());
		logger.info("updatetime:"+updateTime);

		
		getSimpleJdbcTemplate().update(query,
				callForProposal.getTitle(),
				callForProposal.getUrlTitle(),
				callForProposal.getCreatorId(),
				publicationTime,
				finalSubmissionTime,
				callForProposal.getFinalSubmissionHour(),
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
	    		callForProposal.getTargetAudience(),
				callForProposal.getId());
		
		query = "delete from subjectToCallForProposal where callForProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callForProposal.getSubjectsIds()){
				query  = "insert subjectToCallForProposal set callForProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callForProposalDate where callForProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		for (Long submissionDate: callForProposal.getSubmissionDates()){
			query  = "insert callForProposalDate set callForProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), DateUtils.formatTimestampWithoutMillis(submissionDate));
		}
		
		if(callForProposal.getAttachments()!=null){
			for (Attachment attachment: callForProposal.getAttachments()){
				query  = "insert callForProposalFile set callForProposalId = ?, fileId = ?, contentType= ?, title= ?";
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
			keepInRollingMessagesExpiryTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getKeepInRollingMessagesExpiryTime());
		String finalSubmissionTime="";
		if(callForProposal.getFinalSubmissionTime()==0)//
			finalSubmissionTime="0000-00-00 00:00:00";
		else
			finalSubmissionTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getFinalSubmissionTime());
		String publicationTime=DateUtils.formatTimestampWithoutMillis(callForProposal.getPublicationTime());
		String query = "update callForProposal set " +
				" title = ?" +
				", urlTitle = ?" +
				", creatorId = ?" +
				", publicationTime = ?" +
				", finalSubmissionTime = ?" +
				", finalSubmissionHour = ?" +
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
				", targetAudience = ?" +
			" where id = ?;";
		logger.info(query);
		getSimpleJdbcTemplate().update(query,
				callForProposal.getTitle(),
				callForProposal.getUrlTitle(),
				callForProposal.getCreatorId(),
				publicationTime,
				finalSubmissionTime,
				callForProposal.getFinalSubmissionHour(),
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
	    		callForProposal.getTargetAudience(),
				callForProposal.getId());
		
		/*query = "delete from subjectToCallForProposal where callForProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callForProposal.getSubjectsIds()){
				query  = "insert subjectToCallForProposal set callForProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), subjectId);
			}
		}
		
		query = "delete from callForProposalDate where callForProposalId = ?";
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		for (Long submissionDate: callForProposal.getSubmissionDates()){
			query  = "insert callForProposalDate set callForProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), DateUtils.formatTimestampWithoutMillis(submissionDate));
		}
		
		for (Attachment attachment: callForProposal.getAttachments()){
			query  = "insert callForProposalFile set callForProposalId = ?, fileId = ?, contentType= ?, title= ?";
			if (attachment != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), attachment.getFile(), attachment.getContentType(), attachment.getTitle());
		}
		 */
	}	
	
	public void removeCallForProposalOnline(int id){
		String query = "delete from callForProposal where id= ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	public List<CallForProposal> getCallForProposals(ListView lv,CallForProposalSearchCreteria searchCriteria){
		String query  = "select distinct callForProposalDraft.* from callForProposalDraft";
		query += getCallForProposalsWhereClause(searchCriteria,"callForProposalDraft");
		query += " limit "+ (lv.getPage()-1) * lv.getRowsInPage() + "," + lv.getRowsInPage();
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}
	
	public int countCallForProposals(CallForProposalSearchCreteria searchCreteria) {
		String query = "select count(*) from (";
		query += "select distinct callForProposalDraft.* from callForProposalDraft";
		query += getCallForProposalsWhereClause(searchCreteria,"callForProposalDraft");
		query += ") as t;";
		logger.info(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}

	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria){
		String query  = "select distinct callForProposal.* from callForProposal";
		query += getCallForProposalsWhereClause(searchCriteria,"callForProposal");
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	
	public String getCallForProposalsWhereClause(CallForProposalSearchCreteria searchCriteria, String mainTable){
		String whereClause="";
		if (searchCriteria.getSearchByTemporaryFund())
			whereClause += " inner join fund on fund.financialId=" + mainTable +".fundId";
		if(!searchCriteria.getSearchBySubjectIds().isEmpty() || searchCriteria.getSearchByAllSubjects())
			whereClause += " inner join subjectToCallForProposal on subjectToCallForProposal.callForProposalId=" + mainTable +".id";
		whereClause += " where true";
		if(searchCriteria.getSearchByTemporaryFund())
			whereClause +=" and fund.isTemporary=1";
		if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty() || !searchCriteria.getSearchBySubmissionDateTo().isEmpty())
			whereClause +=" and (true";
		if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty())
			whereClause +=" and " + mainTable +".finalSubmissionTime >='"+searchCriteria.getSearchBySubmissionDateFrom()+"'";
		if(!searchCriteria.getSearchBySubmissionDateTo().isEmpty())
			whereClause +=" and " + mainTable +".finalSubmissionTime <='"+searchCriteria.getSearchBySubmissionDateTo()+"'";
		if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty() || !searchCriteria.getSearchBySubmissionDateTo().isEmpty())
			whereClause +=" or " + mainTable +".finalSubmissionTime = 0)";
		if(searchCriteria.getSearchByFund()>0)
			whereClause +=" and " + mainTable +".fundId="+searchCriteria.getSearchByFund();
		if(searchCriteria.getSearchByDesk()>0)
			whereClause +=" and " + mainTable +".deskId="+searchCriteria.getSearchByDesk();
		if(searchCriteria.getSearchByTargetAudience()>0)
			whereClause +=" and " + mainTable +".targetAudience="+searchCriteria.getSearchByTargetAudience();
		if(searchCriteria.getSearchByType()>0)
			whereClause +=" and " + mainTable +".typeId="+searchCriteria.getSearchByType();
		if(searchCriteria.getSearchByCreator()>0)
			whereClause +=" and " + mainTable +".creatorId="+searchCriteria.getSearchByCreator();
		if(!searchCriteria.getSearchBySearchWords().isEmpty())
			whereClause +=" and " + mainTable +".id in ("+searchCriteria.getSearchBySearchWords() + ")";
		if(!searchCriteria.getSearchByAllSubjects() && !searchCriteria.getSearchBySubjectIds().isEmpty())
			whereClause +=" and subjectToCallForProposal.subjectId in ("+searchCriteria.getSearchBySubjectIds() + ")";
		if(searchCriteria.getSearchDeleted())//not include deleted
			whereClause +=" and " + mainTable +".isDeleted=1";
		else
			whereClause +=" and " + mainTable +".isDeleted=0";
		if(searchCriteria.getSearchExpired()) 
			whereClause +=" and " + mainTable +".finalSubmissionTime < now()";
		if(searchCriteria.getSearchByAllYear())
			whereClause +=" and " + mainTable +".finalSubmissionTime = 0";
		if(searchCriteria.getSearchOpen()) 
			whereClause +=" and (" + mainTable +".finalSubmissionTime >= now() or " + mainTable +".finalSubmissionTime = 0)";
			
		
		if(searchCriteria.getSearchByAllSubjects()){
			String query = "select count(*) from subject where parentId not in(-1,1)";
			int countSubjects = getSimpleJdbcTemplate().queryForInt(query);
			whereClause +=" group by subjectToCallForProposal.callForProposalId having count(*)="+countSubjects;
		}

		
		whereClause += "  order by " + mainTable +".localeId," +mainTable +".id desc";
		
		if(searchCriteria.getLimit()>0)
			whereClause += "  limit " + searchCriteria.getLimit();
		
		logger.info(whereClause);
		return whereClause;
	}
	
	public List<CallForProposal> getCallForProposalsOnline(String ids ){
		String query  = "select distinct callForProposal.* from callForProposal";
		if(!ids.isEmpty())
			query += " where id in ("+ids + ") and isDeleted=0";
		query+=" order by localeId, id desc";
		logger.info(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	private ParameterizedRowMapper<CallForProposal> rowMapper = new ParameterizedRowMapper<CallForProposal>(){
		public CallForProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            CallForProposal callForProposal = new CallForProposal();
            callForProposal.setId(rs.getInt("id"));
            callForProposal.setTitle(rs.getString("title"));
            callForProposal.setUrlTitle(rs.getString("urlTitle"));
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
    		callForProposal.setFinalSubmissionHour(rs.getString("finalSubmissionHour"));
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
    		callForProposal.setTargetAudience(rs.getInt("targetAudience"));
           return callForProposal;
        }
	};
	
	public void insertArdNum(int ardNum,int id){
		String query  = "insert callForProposalHistoryId set callForProposalId = ?, callForProposalHistoryId = ?";
		getSimpleJdbcTemplate().update(query,id, ardNum);
	}

	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment){
		final String query  = "insert callForProposalFile set callForProposalId = ?, fileId = ?, contentType= ?, title= ?";
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
	
	public String getFirstDayOfCalendarMonth(){
		String query = "select date_sub(date_format(date_add(curdate(), interval 0 month), '%Y-%m-01'), interval (dayofweek(date_format(date_add(curdate(), interval 0 month), '%Y-%m-01'))-1) day) AS a;";
		String firstDay = getSimpleJdbcTemplate().queryForObject(query, String.class);
		return 	firstDay;
	}
	
	public String getLastDayOfCalendarMonth(){
		String query = "select date_sub(date_format(date_add(curdate(), interval 1 month), '%Y-%m-01') + interval 7 day, interval (dayofweek(date_format(date_add(curdate(), interval 1 month), '%Y-%m-01'))-1) day) AS a;";
		String lastDay = getSimpleJdbcTemplate().queryForObject(query, String.class);
		return 	lastDay;	
	}
	
	public List<CallForProposal> getCalendarMonthCallForProposals(String firstDay, String lastDay){
		String query = "select * from callForProposal where isDeleted=0 and finalSubmissionTime>='" + firstDay + "' and finalSubmissionTime<'" + lastDay + "'  order by finalSubmissionTime;";
		System.out.println("Query:"+ query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	public String getFirstDayOfCalendarMonth(String date){
		String query = "select date_sub(?, interval (dayofweek(?)-1) day) AS a;";
		String firstDay = getSimpleJdbcTemplate().queryForObject(query, String.class,date,date);
		return 	firstDay;
	}
	
	public String getLastDayOfCalendarMonth(String date){
		String query = "select date_sub(?, interval (dayofweek(?)-1) day) + interval 7 day AS a;";
		String lastDay = getSimpleJdbcTemplate().queryForObject(query, String.class, date,date);
		return 	lastDay;	
	}
	
	public LinkedHashMap<String, DayInCalendar> getMonthDaysMap(String date){
		final LinkedHashMap<String, DayInCalendar> daysMap = new LinkedHashMap<String, DayInCalendar>();
		String query = "SELECT '"+date+"' + INTERVAL (numberId-1) DAY AS ascendingDays FROM naturalNumbers WHERE numberId BETWEEN 1 AND 42 ORDER BY numberId ASC;";
		System.out.println("Query:"+ query);
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				DayInCalendar dayInCalendar = new DayInCalendar();
				dayInCalendar.setDay(rs.getString(1));
				dayInCalendar.setFundsInDay(new ArrayList<FundInDay>());
				daysMap.put(rs.getString(1), dayInCalendar);
				return null;
			}
		});
		return 	daysMap;	
	}

	
	public int countCallForProposalsByUrlTitle(int id,String urlTitle){
		String query = "select count(*) from callForProposalDraft where urlTitle='" + urlTitle +"' and id<>"+ id;
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	public int countCallForProposalsByTitle(int id,String title){
		String query = "select count(*) from callForProposalDraft where title='" + title +"' and id<>"+ id;
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public List<Integer> getDaysWithFunds(String month,String year){
		String query = "SELECT distinct DAYOFMONTH(finalSubmissionTime) as day from callForProposal where isDeleted=0 and Month(finalSubmissionTime)="+month+" and YEAR(finalSubmissionTime)="+year;
		System.out.println("Query:"+ query);
		List<Integer> days = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				Integer day = new Integer(rs.getInt("day"));
				return day;
			}
		});
		return days;
	}
	
	public List<CallForProposal> getCallForProposalsPerDay(String date){
		String query = "select * from callForProposal where isDeleted=0 and DATE(finalSubmissionTime)='" + date + "'";
		System.out.println("Query:"+ query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

}
