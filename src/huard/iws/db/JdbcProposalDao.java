package huard.iws.db;


import huard.iws.model.Proposal;
import huard.iws.model.ProposalAttachment;

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

public class JdbcProposalDao extends SimpleJdbcDaoSupport implements ProposalDao {
	//private static final Logger logger = Logger.getLogger(JdbcProposalDao.class);


	public Proposal getProposal(int id){
		String proposalSelect = "select * from proposal where id=?";
		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(proposalSelect, proposalRowMapper ,
					id);
		if (proposals.size()==0) return null;
		Proposal proposal = proposals.get(0);
		proposal.setResearchAttachments(getProposalAttachments(proposal.getId()));
		return proposal;
	}

	public List<ProposalAttachment> getProposalAttachments(int proposalId){
		String query = "select * from proposalAttachment where proposalId=? order by place";
		List<ProposalAttachment> proposalAttachments =
			getSimpleJdbcTemplate().query(query, proposalAttachmentRowMapper ,
					proposalId);

		return proposalAttachments;
	}


	ParameterizedRowMapper<Proposal> proposalRowMapper = new ParameterizedRowMapper<Proposal>(){
		public Proposal mapRow(ResultSet rs, int rowNum) throws SQLException{
			Proposal proposal = new Proposal();
			proposal.setId(rs.getInt("id"));
			proposal.setYearId(rs.getInt("yearId"));
			proposal.setHebrewTitle(rs.getString("hebrewTitle"));
			proposal.setEnglishTitle(rs.getString("englishTitle"));
			proposal.setFundId(rs.getInt("fundId"));

			proposal.setEthicsAttach(rs.getBytes("ethicsAttach"));
			proposal.setEthicsAttachContentType(rs.getString("ethicsAttachContentType"));
			proposal.setProposalUpdate(rs.getTimestamp("proposalUpdate"));
			proposal.setCreationDate(rs.getTimestamp("creationDate"));
			proposal.setStateId(rs.getInt("stateId"));

			proposal.setExperimental(rs.getBoolean("experimental"));
			proposal.setSafetyAttach(rs.getBytes("safetyAttach"));
			proposal.setSafetyAttachContentType(rs.getString("safetyAttachContentType"));
			proposal.setSafetySendDate(rs.getTimestamp("safetySendDate"));
			proposal.setSafetyApproved(rs.getBoolean("safetyApproved"));
			proposal.setSafetyUpdate(rs.getTimestamp("safetyUpdate"));
			proposal.setSafetyRefusalDetails(rs.getString("safetyRefusalDetails"));

			proposal.setHumanExperiment(rs.getBoolean("humanExperiment"));
			proposal.setHumanAttach(rs.getBytes("humanAttach"));
			proposal.setHumanAttachContentType(rs.getString("humanAttachContentType"));
			proposal.setHumanSendDate(rs.getTimestamp("humanSendDate"));
			proposal.setHumanApproved(rs.getBoolean("humanApproved"));
			proposal.setHumanUpdate(rs.getTimestamp("humanUpdate"));
			proposal.setHumanRefusalDetails(rs.getString("humanRefusalDetails"));

			proposal.setAnimalsExperiment(rs.getBoolean("animalsExperiment"));
			proposal.setAnimalsAttach(rs.getBytes("animalsAttach"));
			proposal.setAnimalsAttachContentType(rs.getString("animalsAttachContentType"));
			proposal.setAnimalsSendDate(rs.getTimestamp("animalsSendDate"));
			proposal.setAnimalsApproved(rs.getBoolean("animalsApproved"));
			proposal.setAnimalsUpdate(rs.getTimestamp("animalsUpdate"));
			proposal.setAnimalsRefusalDetails(rs.getString("animalsRefusalDetails"));

			proposal.setDeanApproved(rs.getBoolean("deanApproved"));
			proposal.setDeanRefusalDetails(rs.getString("deanRefusalDetails"));

			proposal.setFundingAgencyApproved(rs.getInt("fundingAgencyApproved"));
			proposal.setFundingAgencyDetails(rs.getString("fundingAgencyDetails"));

			proposal.setYissumResearcherHandled(rs.getBoolean("yissumResearcherHandled"));
			proposal.setNeedsYissumApproval(rs.getBoolean("needsYissumApproval"));
			proposal.setYissumSend(rs.getBoolean("yissumSend"));
			proposal.setYissumSendDate(rs.getTimestamp("yissumSendDate"));
			proposal.setYissumHandled(rs.getBoolean("yissumHandled"));
			proposal.setYissumApproved(rs.getBoolean("yissumApproved"));
			proposal.setYissumUpdate(rs.getTimestamp("yissumUpdate"));
			proposal.setYissumRefusalDetails(rs.getString("yissumRefusalDetails"));

			proposal.setBudgetHujiId(rs.getString("budgetHujiId"));
			proposal.setArchived(rs.getBoolean("archived"));
			return proposal;
		}
	};

	ParameterizedRowMapper<ProposalAttachment> proposalAttachmentRowMapper
		= new ParameterizedRowMapper<ProposalAttachment>(){
			public ProposalAttachment mapRow(ResultSet rs, int rowNum) throws SQLException{
				ProposalAttachment proposalAttachment = new ProposalAttachment();
				proposalAttachment.setId(rs.getInt("id"));
				proposalAttachment.setProposalId(rs.getInt("proposalId"));
				proposalAttachment.setTitle(rs.getString("title"));
				proposalAttachment.setFile(rs.getBytes("file"));
				proposalAttachment.setContentType(rs.getString("contentType"));
				proposalAttachment.setPlace(rs.getInt("place"));
				return proposalAttachment;
			}
	};

	public void updateProposal(Proposal proposal){
		String proposalUpdate = "update proposal set "+
		" yearId = ?,"+
		" hebrewTitle = ?,"+
		" englishTitle = ?,"+
		" fundId = ?,"+

		" ethicsAttach = ?,"+
		" ethicsAttachContentType = ?,"+
		" proposalUpdate = now(),"+
		" creationDate = ?,"+
		" stateId = ?,"+

		" experimental = ?,"+
		" safetyAttach = ?,"+
		" safetyAttachContentType = ?,"+
		" safetySendDate = ?,"+
		" safetyApproved = ?,"+
		" safetyUpdate = ?,"+
		" safetyRefusalDetails = ?,"+

		" humanExperiment = ?,"+
		" humanAttach = ?,"+
		" humanAttachContentType = ?,"+
		" humanSendDate = ?,"+
		" humanApproved = ?,"+
		" humanUpdate = ?,"+
		" humanRefusalDetails = ?,"+

		" animalsExperiment = ?,"+
		" animalsAttach = ?,"+
		" animalsAttachContentType = ?,"+
		" animalsSendDate = ?,"+
		" animalsApproved = ?,"+
		" animalsUpdate = ?,"+
		" animalsRefusalDetails = ?,"+

		" deanApproved = ?,"+
		" deanRefusalDetails = ?,"+

		" fundingAgencyApproved = ?,"+
		" fundingAgencyDetails = ?,"+

		" yissumResearcherHandled = ?,"+
		" needsYissumApproval = ?,"+
		" yissumSend = ?,"+
		" yissumSendDate = ?,"+
		" yissumHandled = ?,"+
		" yissumApproved = ?,"+
		" yissumUpdate = ?,"+
		" yissumRefusalDetails = ?,"+

		" budgetHujiId = ?,"+
		" archived = ?"+

		" where id = ?";
		getSimpleJdbcTemplate().update(proposalUpdate,
				proposal.getYearId(),
				proposal.getHebrewTitle(),
				proposal.getEnglishTitle(),
				proposal.getFundId(),

				proposal.getEthicsAttach(),
				proposal.getEthicsAttachContentType(),
				proposal.getCreationDate(),
				proposal.getStateId(),

				proposal.isExperimental(),
				proposal.getSafetyAttach(),
				proposal.getSafetyAttachContentType(),
				proposal.getSafetySendDate(),
				proposal.isSafetyApproved(),
				proposal.getSafetyUpdate(),
				proposal.getSafetyRefusalDetails(),

				proposal.isHumanExperiment(),
				proposal.getHumanAttach(),
				proposal.getHumanAttachContentType(),
				proposal.getHumanSendDate(),
				proposal.isHumanApproved(),
				proposal.getHumanUpdate(),
				proposal.getHumanRefusalDetails(),

				proposal.isAnimalsExperiment(),
				proposal.getAnimalsAttach(),
				proposal.getAnimalsAttachContentType(),
				proposal.getAnimalsSendDate(),
				proposal.isAnimalsApproved(),
				proposal.getAnimalsUpdate(),
				proposal.getAnimalsRefusalDetails(),

				proposal.isDeanApproved(),
				proposal.getDeanRefusalDetails(),

				proposal.getFundingAgencyApproved(),
				proposal.getFundingAgencyDetails(),

				proposal.isYissumResearcherHandled(),
				proposal.isNeedsYissumApproval(),
				proposal.isYissumSend(),
				proposal.getYissumSendDate(),
				proposal.isYissumHandled(),
				proposal.isYissumApproved(),
				proposal.getYissumUpdate(),
				proposal.getYissumRefusalDetails(),

				proposal.getBudgetHujiId(),
				proposal.isArchived(),

				proposal.getId()
		);
		updateProposalAttachments(proposal);
	}

	public void updateProposalAttachments(Proposal proposal){
		String query = "delete from proposalAttachment where proposalId = ?";
		getSimpleJdbcTemplate().update(query, proposal.getId());
		query = "insert proposalAttachment set proposalId = ?, title = ?, file = ?, contentType = ?, place = ?";
		for (ProposalAttachment proposalAttachment: proposal.getResearchAttachments()){
			getSimpleJdbcTemplate().update(query,
					proposal.getId(),
					proposalAttachment.getTitle(),
					proposalAttachment.getFile(),
					proposalAttachment.getContentType(),
					proposalAttachment.getPlace()
			);
		}
	}

	public int insertProposal(){
		final String proposalInsert = "insert proposal set creationDate = ?, stateId=0 ;";
		final Timestamp creationDate = new Timestamp(new java.util.Date().getTime());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(proposalInsert, new String[] {"id"});
		            ps.setTimestamp(1, creationDate);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}



	public void deleteProposal(int id){
		String personDelete = "delete from proposal where id =?";
		getSimpleJdbcTemplate().update(personDelete, id);
	}

	public void deleteProposalAttachment(int attachmentId){
		String personDelete = "delete from proposalAttachment where id =?";
		getSimpleJdbcTemplate().update(personDelete, attachmentId);
	}



	public ProposalAttachment getProposalAttachment(int attachmentId){
		String query = "select * from proposalAttachment where id = ?;";
		ProposalAttachment proposalAttachment = getSimpleJdbcTemplate().queryForObject(
				query, proposalAttachmentRowMapper, attachmentId);
		return proposalAttachment;
	}

	public void updateProposalAttachment(ProposalAttachment proposalAttachment){
		String query = "update proposalAttachment set proposalId = ?, title = ?, file = ?, contentType = ?, place = ? where id =?";
		getSimpleJdbcTemplate().update(query,
				proposalAttachment.getProposalId(),
				proposalAttachment.getTitle(),
				proposalAttachment.getFile(),
				proposalAttachment.getContentType(),
				proposalAttachment.getPlace(),
				proposalAttachment.getId()
		);
	}



	public ParameterizedRowMapper<Proposal> getProposalRowMapper() {
		return proposalRowMapper;
	}
}