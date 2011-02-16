package huard.iws.db;

import huard.iws.model.Proposal;
import huard.iws.util.BaseUtils;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcProposalListDao extends SimpleJdbcDaoSupport implements ProposalListDao{

	public List<Proposal> getProposalList(ListView lv, SearchCreteria search) {
		String query = "select p.* from proposal p, personToProposal ptp";
		query = query.concat(" where p.id = ptp.proposalId and");
		query = query.concat(" "+search.getSearchField()+" =  ?");
		query = query.concat(" order by "+lv.getOrderBy());

		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper(),
			search.getSearchPhrase());
		proposals = BaseUtils.removeDups(proposals);
		return proposals;
    }

	public List<Proposal> getProposalListByPersonId(int personId){
		String query = "select p.* from proposal p, personToProposal ptp";
		query = query.concat(" where p.id = ptp.proposalId and");
		query = query.concat(" ptp.personId = ? ;");
		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper(),
			personId);
		return proposals;
	}



	public List<Proposal> getProposalList(){
		String query = "select p.* from proposal p ;";
		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper());
		return proposals;
	}



	public List<Proposal> getProposalList(ListView lv) {
		String query = "select * from proposal";
		query = query.concat(" order by "+lv.getOrderBy());

		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper());
		return proposals;
    }

	public List<Proposal> getProposalListByPersonId(ListView lv, SearchCreteria search, int personId) {
		String query = "select p.* from proposal p, personToProposal ptp, personToProposal ptp1";
		query = query.concat(" where "+search.getSearchField()+" =  ?");
		query = query.concat(" and p.id = ptp.proposalId ");
		query = query.concat(" and ptp1.personId = ? ");
		query = query.concat(" order by "+lv.getOrderBy());

		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper(),
			search.getSearchPhrase(), personId);
		proposals = BaseUtils.removeDups(proposals);
		return proposals;
    }


	public List<Proposal> getProposalListByPersonId(ListView lv, int personId) {
		String query = "select p.* from proposal p, personToProposal ptp ";
		query = query.concat(" where p.id = ptp.proposalId ");
		query = query.concat(" and ptp.personId = ? ");
		query = query.concat(" order by "+lv.getOrderBy());

		List<Proposal> proposals =
			getSimpleJdbcTemplate().query(query, proposalDao.getProposalRowMapper(), personId);
		return proposals;
    }

	public int getMaxYearId(){
		String query = "select max(yearId) from proposal;";
		Integer maxYearId = getSimpleJdbcTemplate().queryForObject(query, new ParameterizedRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
				int maxYearId = rs.getInt("max(yearId)");
				return maxYearId;
			}
		});
		return maxYearId ;
	}

	private ProposalDao proposalDao;

	public void setProposalDao(ProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}


}

