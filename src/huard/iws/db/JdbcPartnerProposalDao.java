package huard.iws.db;

import huard.iws.model.PartnerInstituteProposal;
import huard.iws.model.PartnerProposal;
import huard.iws.util.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPartnerProposalDao  extends SimpleJdbcDaoSupport implements PartnerProposalDao {
	//private static final Logger logger = Logger.getLogger(JdbcPartnerProposalDao.class);

	public void insertPartnerProposal(PartnerProposal partnerProposal){
		String query = "insert partnerToProposal set partnerId = ?, proposalId = ?, creationDate = ?";
		getSimpleJdbcTemplate().update(query,
				partnerProposal.getPartnerId() ,
				partnerProposal.getProposalId(),
				DateUtils.formatTimestampWithoutMillis(new java.util.Date().getTime())
		);
	}

	public void deletePartnerProposal(PartnerProposal partnerProposal){
		String query = "delete from partnerToProposal where partnerId = ? and proposalId = ? ;";
		getSimpleJdbcTemplate().update(query,
				partnerProposal.getPartnerId() ,
				partnerProposal.getProposalId()
		);
	}

	public List<PartnerProposal> getPartnerProposals(int proposalId){
		String query = "select * from partnerToProposal where proposalId = ? order by creationDate desc;";
		List<PartnerProposal> partnerProposals =
			getSimpleJdbcTemplate().query(query, partnerProposalRowMapper, proposalId);
		return partnerProposals;
	 }



	ParameterizedRowMapper<PartnerProposal> partnerProposalRowMapper = new ParameterizedRowMapper<PartnerProposal>(){
		public PartnerProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            PartnerProposal partnerProposal = new PartnerProposal();
            partnerProposal.setId(rs.getInt("id"));
            partnerProposal.setPartnerId(rs.getInt("partnerId"));
            partnerProposal.setProposalId(rs.getInt("proposalId"));
            return partnerProposal;
        }
	};

	public void insertPartnerInstituteProposal(PartnerInstituteProposal partnerInstituteProposal){
		String query = "insert partnerInstituteToProposal set partnerInstituteId = ?, proposalId = ?, creationDate = now()";
		getSimpleJdbcTemplate().update(query,
				partnerInstituteProposal.getPartnerInstituteId() ,
				partnerInstituteProposal.getProposalId()
		);
	}

	public void deletePartnerInstituteProposal(PartnerInstituteProposal partnerInstituteProposal){
		String query = "delete from partnerInstituteToProposal where partnerInstituteId = ? and proposalId = ? ;";
		getSimpleJdbcTemplate().update(query,
				partnerInstituteProposal.getPartnerInstituteId() ,
				partnerInstituteProposal.getProposalId()
		);
	}

	public List<PartnerInstituteProposal> getPartnerInstituteProposals(int proposalId){
		String query = "select * from partnerInstituteToProposal where proposalId = ? order by creationDate desc;";
		List<PartnerInstituteProposal> partnerInstituteProposals =
			getSimpleJdbcTemplate().query(query, partnerIntituteProposalRowMapper, proposalId);
		return partnerInstituteProposals;
	 }



	ParameterizedRowMapper<PartnerInstituteProposal> partnerIntituteProposalRowMapper = new ParameterizedRowMapper<PartnerInstituteProposal>(){
		public PartnerInstituteProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            PartnerInstituteProposal partnerInstituteProposal = new PartnerInstituteProposal();
            partnerInstituteProposal.setId(rs.getInt("id"));
            partnerInstituteProposal.setPartnerInstituteId(rs.getInt("partnerInstituteId"));
            partnerInstituteProposal.setProposalId(rs.getInt("proposalId"));
            return partnerInstituteProposal;
        }
	};
}
