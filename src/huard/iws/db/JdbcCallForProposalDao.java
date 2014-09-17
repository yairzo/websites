package huard.iws.db;

import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.DayInCalendar;
import huard.iws.model.FundInDay;
import huard.iws.model.CallForProposalType;
import huard.iws.util.CallForProposalSearchCreteria;
import huard.iws.util.DateUtils;
import huard.iws.util.ListView;
import huard.iws.util.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;


public class JdbcCallForProposalDao extends SimpleJdbcDaoSupport implements CallForProposalDao {
	
	Logger logger = Logger.getLogger("JdbcCallForProposalDao");

	public CallForProposal getCallForProposal(int id){
		try{
			String query = "select * from callForProposalDraft where id =?";
			CallForProposal callForProposal =
				getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			logger.debug(query+ id);
			applySubjectIds(callForProposal);
			applySubmissionDates(callForProposal);
			applyFiles(callForProposal);
			applyCountries(callForProposal);
			return 	callForProposal;	
		}
		catch(Exception e){
			return new CallForProposal();
		}
	}
	
	public CallForProposal getCallForProposalOnline(int id){
		try{
			String query = "select * from callForProposal where id =?";
			CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			logger.debug(query+id);
			applySubjectIds(callForProposal);
			applySubmissionDates(callForProposal);
			applyFiles(callForProposal);
			applyCountries(callForProposal);
			return 	callForProposal;	
		}
		catch(Exception e){
			return new CallForProposal();
		}
	}
	
	public CallForProposal getCallForProposalOnline(String urlTitle){
	try{
		String query = "select * from callForProposal where urlTitle =?";
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, urlTitle);
		logger.debug(query + urlTitle);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		applyCountries(callForProposal);
		return 	callForProposal;	
	}
	catch(Exception e){
		return new CallForProposal();
	}
	}
	
	public String getCallForProposalUrlTitleByArdNum(int ardNum){
		String query = "select urlTitle from callForProposal inner join callForProposalHistoryId on" +
				" callForProposal.id=callForProposalHistoryId.callForProposalId where callForProposalHistoryId.callForProposalHistoryId =?";
		logger.debug(query);
		try{
			return getSimpleJdbcTemplate().queryForObject(query, String.class, ardNum);
		}
		catch(Exception e){
			return "";
		}
	}
	
	public boolean existsCallForProposalOnline(int id){
		String query = "select * from callForProposal where id =?";
		logger.debug(query);
		try{
			getSimpleJdbcTemplate().queryForObject(query, rowMapper, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public CallForProposal getCallForProposal(String title){
		String query = "select * from callForProposalDraft where title =?";
		logger.debug(query+title);
		CallForProposal callForProposal =
					getSimpleJdbcTemplate().queryForObject(query, rowMapper, title);
		applySubjectIds(callForProposal);
		applySubmissionDates(callForProposal);
		applyFiles(callForProposal);
		applyCountries(callForProposal);
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
	
	public String getSubjectsNames(int id){
		String query = "select group_concat(nameHebrew) from subjectToCallForProposal inner join subject on subject.id=subjectToCallForProposal.subjectId where callForProposalId = ? group by callForProposalId;";
		String subjectsNames =  getSimpleJdbcTemplate().queryForObject(query, String.class, id);
        return subjectsNames;
	}

	private void applySubmissionDates(CallForProposal callForProposal){
		String query = "select * from callForProposalDate where callForProposalId = ?"
				+ " and date(submissionDate) != date(?)" 
				+ " order by submissionDate;";
		logger.debug(query);
		List<Long> submissionDates =  getSimpleJdbcTemplate().query(query, submissionDatesRowMapper,
				callForProposal.getId(), SQLUtils.getTimestampString(callForProposal.getFinalSubmissionTime()));
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
		String query = "select * from callForProposalFile where pageId = ?";
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
			file.setId(rs.getInt("id"));
			file.setFile(rs.getBytes("attachment"));
			file.setContentType(rs.getString("attachmentContentType"));
			file.setTitle(rs.getString("title"));
			file.setFilename(rs.getString("filename"));			
            return file;
		}
	};
	
	private void applyCountries(CallForProposal callForProposal){
		String query = "select * from callForProposalCountry where callForProposalId = ?";
		List<Integer> countries =  getSimpleJdbcTemplate().query(query, countryRowMapper, callForProposal.getId());
		callForProposal.setCountryIds(countries);
	}
	
	public void deleteCountry(int id){
		String query = "delete from callForProposalCountry where id = ?";
		getSimpleJdbcTemplate().update(query,id);
	}
	
	private ParameterizedRowMapper<Integer> countryRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer countryId = rs.getInt("countryId");
            return countryId;
		}
	};
	
	public int insertCallForProposal(CallForProposal callForProposal){

		if(callForProposal.getTitle().isEmpty())
			callForProposal.setTitle("###" + new java.util.Date().getTime() + "###");
		if(callForProposal.getUrlTitle().isEmpty())
			callForProposal.setUrlTitle(callForProposal.getTitle());
		final String query = "insert ignore callForProposalDraft set title = '" + callForProposal.getTitle().replace("'","") + "'"+
				", urlTitle = ?" +
				", creatorId = ?" +
				", publicationTime = now()" +
				", fundId = 0" +
				", typeId = 0" +
				", deskId = 0" +
				", originalCallWebAddress = ''" +
				", submissionDetails = ''" +
				", contactPersons = ''" +
				", contactPersonDetails = ''" +
				", fundContact = ''" +
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
		logger.debug(query);
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
				", contactPersons=?" +
				", contactPersonDetails= ?" +
				", fundContact= ?" +
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
				", targetAudience = ?"+
				", hourType = ?";
		logger.debug(query);
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
	    		callForProposal.getContactPersons().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFundContact(),
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
	    		callForProposal.getHourType());
	}
	

	public void updateCallForProposal(CallForProposal callForProposal){
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
				", contactPersons= ?" +
				", contactPersonDetails= ?" +
				", fundContact= ?" +
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
				", hourType = ?" +
			" where id = ?;";
		logger.debug(query);

		
		String updateTime="";
		if(callForProposal.getUpdateTime() == 0)
			updateTime = SQLUtils.getTimestampString(System.currentTimeMillis());//always
		else// when from import
			updateTime = SQLUtils.getTimestampString(callForProposal.getUpdateTime());
		logger.debug("updatetime:"+updateTime);

		
		getSimpleJdbcTemplate().update(query,
				callForProposal.getTitle(),
				callForProposal.getUrlTitle(),
				callForProposal.getCreatorId(),
				SQLUtils.getTimestampString(callForProposal.getPublicationTime()),
				SQLUtils.getTimestampString(callForProposal.getFinalSubmissionTime()),
				callForProposal.getFinalSubmissionHour(),
	    		callForProposal.getAllYearSubmission(),
	    		callForProposal.getAllYearSubmissionYearPassedAlert(),
	    		callForProposal.getHasAdditionalSubmissionDates(),
	    		callForProposal.getFundId(),
	    		callForProposal.getTypeId(),
	    		SQLUtils.getTimestampString(callForProposal.getKeepInRollingMessagesExpiryTime()),
	    		callForProposal.getDeskId(),
	    		callForProposal.getOriginalCallWebAddress(),
	    		callForProposal.getRequireLogin(),
	    		callForProposal.getShowDescriptionOnly(),
	    		callForProposal.getSubmissionDetails().trim(),
	    		callForProposal.getContactPersons().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFundContact(),
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
	    		callForProposal.getHourType(),
				callForProposal.getId());
		
		query = "delete from subjectToCallForProposal where callForProposalId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getSubjectsIds()!=null){
			for (Integer subjectId: callForProposal.getSubjectsIds()){
				query  = "insert subjectToCallForProposal set callForProposalId = ?, subjectId = ?";
				if (subjectId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), subjectId);
			}
		}

		query = "delete from callForProposalCountry where callForProposalId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		if(callForProposal.getCountryIds()!=null){
			for (Integer countryId: callForProposal.getCountryIds()){
				query  = "insert callForProposalCountry set callForProposalId = ?, countryId = ?";
				if (countryId != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), countryId);
			}
		}

		query = "delete from callForProposalDate where callForProposalId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,callForProposal.getId());
		for (Long submissionDate: callForProposal.getSubmissionDates()){
			query  = "insert callForProposalDate set callForProposalId = ?, submissionDate = ?";
			if (submissionDate != null)
				getSimpleJdbcTemplate().update(query,callForProposal.getId(), DateUtils.formatTimestampWithoutMillis(submissionDate));
		}
		
		if(callForProposal.getAttachments()!=null){
			for (Attachment attachment: callForProposal.getAttachments()){
				query  = "insert callForProposalFile set pageId = ?, attachment = ?, attachmentContentType = ?, title = ?, filename = ?";
				if (attachment != null)
					getSimpleJdbcTemplate().update(query,callForProposal.getId(), 
							attachment.getFile(), attachment.getContentType(), 
							attachment.getTitle(), attachment.getFilename());
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
				", contactPersons= ?" +
				", contactPersonDetails= ?" +
				", fundContact= ?" +
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
				", hourType = ?" +
			" where id = ?;";
		logger.debug(query);
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
	    		callForProposal.getContactPersons().trim(),
	    		callForProposal.getContactPersonDetails().trim(),
	    		callForProposal.getFundContact(),
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
	    		callForProposal.getHourType(),
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
		logger.debug(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}
	
	public int countCallForProposals(CallForProposalSearchCreteria searchCreteria) {
		String query = "select count(*) from (";
		query += "select distinct callForProposalDraft.* from callForProposalDraft";
		query += getCallForProposalsWhereClause(searchCreteria,"callForProposalDraft");
		query += ") as t;";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}

	public List<CallForProposal> getCallForProposalsOnline(CallForProposalSearchCreteria searchCriteria){
		String query  = "select distinct callForProposal.*, "
				+ " if (callForProposal.finalSubmissionTime = 0,0,1)"
				+ " as allYearIndicator from callForProposal";
		query += getCallForProposalsWhereClause(searchCriteria,"callForProposal");
		logger.debug(query);
		
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	
	public String getCallForProposalsWhereClause(CallForProposalSearchCreteria searchCriteria, String mainTable){
		String whereClause="";
		if (searchCriteria.getSearchByTemporaryFund())
			whereClause += " inner join fund on fund.financialId=" + mainTable +".fundId";
		if(!searchCriteria.getSearchBySubjectIds().isEmpty() || searchCriteria.getSearchByAllSubjects())
			whereClause += " inner join subjectToCallForProposal on subjectToCallForProposal.callForProposalId=" + mainTable +".id";
		if(!searchCriteria.getSearchByCountryIds().isEmpty())
			whereClause += " inner join callForProposalCountry on callForProposalCountry.callForProposalId=" + mainTable +".id";
		whereClause += " where true";
		if(searchCriteria.getSearchByTemporaryFund())
			whereClause +=" and fund.isTemporary=1";
		if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty() || !searchCriteria.getSearchBySubmissionDateTo().isEmpty()){
			whereClause +=" and (( true ";
			if(!searchCriteria.getSearchBySubmissionDateFrom().isEmpty())
				whereClause += "and " + mainTable +".finalSubmissionTime >='"+searchCriteria.getSearchBySubmissionDateFrom()+"'";
			if(!searchCriteria.getSearchBySubmissionDateTo().isEmpty())
				whereClause += "and " + mainTable +".finalSubmissionTime <'"+DateUtils.addDay(searchCriteria.getSearchBySubmissionDateTo())+"'";
			whereClause += "))";
		}
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
		if(!searchCriteria.getSearchByCountryIds().isEmpty())
			whereClause +=" and callForProposalCountry.countryId in ("+searchCriteria.getSearchByCountryIds() + ")";
		if(searchCriteria.getSearchDeleted())//not include deleted
			whereClause +=" and " + mainTable +".isDeleted=1";
		else
			whereClause +=" and " + mainTable +".isDeleted=0";
		if(searchCriteria.getSearchExpired()) 
			whereClause +=" and " + mainTable +".finalSubmissionTime < curdate() and " + mainTable +".finalSubmissionTime <> 0";
		if(searchCriteria.getSearchByAllYear())
			whereClause +=" and " + mainTable +".finalSubmissionTime = 0";
		if(searchCriteria.getSearchOpen()) 
			whereClause +=" and (" + mainTable +".finalSubmissionTime >= curdate() or " + mainTable +".finalSubmissionTime = 0)";
			
		//limit to previous 18 months on site
		if(mainTable.equals("callForProposal"))
			whereClause += "  and (" + mainTable +".finalSubmissionTime >= DATE_SUB(now(),INTERVAL 18 MONTH) or " + mainTable +".finalSubmissionTime = 0)";
		
		
		if(searchCriteria.getSearchByAllSubjects()){
			String query = "select count(*) from subject where parentId not in(-1,1)";
			int countSubjects = getSimpleJdbcTemplate().queryForInt(query);
			whereClause +=" group by subjectToCallForProposal.callForProposalId having count(*)="+countSubjects;
		}
		
		/*if(!searchCriteria.getSearchByCountryIds().isEmpty() && searchCriteria.getSearchByAllCountries()){
			List <Integer> list =BaseUtils.getIntegerList(searchCriteria.getSearchByCountryIds(),",");
			whereClause +=" group by callForProposalCountry.callForProposalId having count(*)="+list.size();
		}*/

		if(mainTable.equals("callForProposalDraft"))
			whereClause += "  order by id desc";
		else{
			whereClause += "  order by " + mainTable +".localeId,";
			if (searchCriteria.isDefault())
				whereClause += mainTable +".publicationTime desc";
			else
				whereClause += "allYearIndicator desc, " + mainTable +".finalSubmissionTime desc";
		}
		
		logger.debug(whereClause);
		return whereClause;
	}


	
	public List<CallForProposal> getCallForProposalsOnlineSimple(String ids ,String viewType){
		String query  = "select distinct callForProposal.*, "
				+ " if (callForProposal.finalSubmissionTime = 0,0,1)"
				+ " as allYearIndicator from callForProposal where isDeleted=0";
		if(viewType.equals("new_cfps"))//when coming from 'latest calls..'
			query += "	and (publicationTime >= DATE_SUB(now(),INTERVAL 2 WEEK) ) and (finalSubmissionTime > curdate() or finalSubmissionTime = 0)";
		else if(!ids.isEmpty())//after search
			query += " and id in ("+ids + ") and (finalSubmissionTime >= DATE_SUB(now(),INTERVAL 18 MONTH) or finalSubmissionTime = 0)";

		if(viewType.equals("new_cfps"))
			query += " order by localeId, publicationTime desc";
		else 
			query += " order by localeId, allYearIndicator desc, finalSubmissionTime desc";
		
		logger.debug(query);
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
    		callForProposal.setContactPersons(rs.getString("contactPersons"));
    		callForProposal.setContactPersonDetails(rs.getString("contactPersonDetails"));
       		callForProposal.setFundContact(rs.getString("fundContact"));
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
    		callForProposal.setHourType(rs.getInt("hourType"));
           return callForProposal;
        }
	};
	
	public void insertArdNum(int ardNum,int id){
		String query  = "insert callForProposalHistoryId set callForProposalId = ?, callForProposalHistoryId = ?";
		getSimpleJdbcTemplate().update(query,id, ardNum);
	}

	public int insertAttachmentToCallForProposal(int callForProposalId, Attachment attachment){
		final String query  = "insert callForProposalFile set pageId = ?, attachment = ?, attachmentContentType = ?,"
				+ " title = ?, filename = ?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final int finalCallForProposalId= callForProposalId;
		final byte[] file= attachment.getFile();
		final String contentType = attachment.getContentType();
		final String title = attachment.getTitle();
		final String filename = attachment.getFilename();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
						ps.setInt(1, finalCallForProposalId);
						ps.setBytes(2, file);
						ps.setString(3, contentType);
						ps.setString(4, title);
						ps.setString(5, filename);
						return ps;
					}
				},
				keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public String getFirstDayOfCalendarMonth(){
		String query = "select date_sub(date_format(date_add(curdate(), interval 0 month), '%Y-%m-01'), interval (dayofweek(date_format(date_add(curdate(), interval 0 month), '%Y-%m-01'))-1) day) AS a;";
		logger.debug(query);
		String firstDay = getSimpleJdbcTemplate().queryForObject(query, String.class);
		return 	firstDay;
	}
	
	public String getLastDayOfCalendarMonth(){
		String query = "select date_sub(date_format(date_add(curdate(), interval 1 month), '%Y-%m-01') + interval 7 day, interval (dayofweek(date_format(date_add(curdate(), interval 1 month), '%Y-%m-01'))-1) day) AS a;";
		logger.debug(query);
		String lastDay = getSimpleJdbcTemplate().queryForObject(query, String.class);
		return 	lastDay;	
	}
	
	public List<CallForProposal> getCalendarMonthCallForProposals(String firstDay, String lastDay){
		String query = "select * from callForProposal where isDeleted=0 and finalSubmissionTime>='" + firstDay + "' and finalSubmissionTime<'" + lastDay + "'  order by finalSubmissionTime;";
		logger.debug(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}

	public String getFirstDayOfCalendarMonth(String date){
		String query = "select date_sub(?, interval (dayofweek(?)-1) day) AS a;";
		logger.debug(query);
		String firstDay = getSimpleJdbcTemplate().queryForObject(query, String.class,date,date);
		return 	firstDay;
	}
	
	public String getLastDayOfCalendarMonth(String date){
		String query = "select date_sub(?, interval (dayofweek(?)-1) day) + interval 7 day AS a;";
		logger.debug(query);
		String lastDay = getSimpleJdbcTemplate().queryForObject(query, String.class, date,date);
		return 	lastDay;	
	}
	
	public LinkedHashMap<String, DayInCalendar> getMonthDaysMap(String date){
		final LinkedHashMap<String, DayInCalendar> daysMap = new LinkedHashMap<String, DayInCalendar>();
		String query = "SELECT '"+date+"' + INTERVAL (numberId-1) DAY AS ascendingDays FROM naturalNumbers WHERE numberId BETWEEN 1 AND 42 ORDER BY numberId ASC;";
		logger.debug(query);
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
		try{
		String query = "select id from callForProposalDraft where urlTitle='" + urlTitle +"' and id<>"+ id + " limit 1";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public int countCallForProposalsByTitle(int id,String title){
		try{
		String query = "select id from callForProposalDraft where title='" + title +"' and id<>"+ id + " limit 1";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
		}
		catch(Exception e){
		return 0;
		}
	}
	
	public List<Integer> getDaysWithFunds(String month,String year){
		String query = "select distinct dayofmonth(finalSubmissionTime) as day from callForProposal where isDeleted=0 and Month(finalSubmissionTime)="+month+" and YEAR(finalSubmissionTime)="+year;
		logger.debug(query);
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
		logger.debug(query);
		List<CallForProposal> callForProposals = getSimpleJdbcTemplate().query(query, rowMapper);
		return callForProposals;
	}
	
	public Timestamp getCallForProposalsLastUpdate(){
	try{
		String query = "select updateTime from callForProposal order by updateTime desc limit 1";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForObject(query, new ParameterizedRowMapper<Timestamp>() {
			@Override
			public Timestamp mapRow(ResultSet r, int arg1)
					throws SQLException {
				Timestamp lastUpdate = r.getTimestamp("updateTime");
				return lastUpdate;
			}			
		});
	}
	catch(Exception e){
		return new Timestamp(0);
	}
		
	}
	
	
	public void updateFinalSubmissionTime(){
		String query = "select callForProposalId,MIN(submissionDate) as newDate from callForProposalDate inner join callForProposal"
				+ " on callForProposal.id=callForProposalDate.callForProposalId"
				+ " where submissiondate>DATE_SUB(now(),interval 1 day) and callForProposal.finalSubmissionTime<DATE(now())"
				+ " group by callForProposalId;";
		logger.debug(query);
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				int id= rs.getInt("callForProposalId");
				String date = rs.getString("newDate");
				//update
				String subQuery="update callForProposal set finalSubmissionTime=? where id=?";
				logger.debug(subQuery);
				getSimpleJdbcTemplate().update(subQuery,date,id);
				subQuery="update callForProposalDraft set finalSubmissionTime=? where id=?";
				logger.debug(subQuery);
				getSimpleJdbcTemplate().update(subQuery,date,id);
				return null;
			}
		});
	}
	public List<CallForProposalType> getCallForProposalTypes(){
		String query ="select * from callForProposalType order by id";
		logger.debug(query);
		List<CallForProposalType> callForProposalTypes = getSimpleJdbcTemplate().query(query,
				new ParameterizedRowMapper<CallForProposalType>(){
					public CallForProposalType mapRow(ResultSet rs, int rowNum) throws SQLException{
						CallForProposalType callForProposalType = new CallForProposalType();
						callForProposalType.setId(rs.getInt("id"));
						callForProposalType.setHebrewName(rs.getString("hebrewName"));
						callForProposalType.setEnglishName(rs.getString("englishName"));
						return callForProposalType;
					}
		});
		return callForProposalTypes;
	}	
	
	public static void main(String [] args){
		CallForProposalDao dao = null;
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(CallForProposalDao.class);
			factory.setServiceUrl("rmi://localhost:1199/CallForProposalDao");
			factory.afterPropertiesSet();
			dao = (CallForProposalDao)factory.getObject();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		dao.updateFinalSubmissionTime();
	}

}
