package huard.iws.db;

import huard.iws.model.PersonProposal;
import huard.iws.util.DateUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPersonProposalDao extends SimpleJdbcDaoSupport implements PersonProposalDao {

	public void insertPersonProposal(PersonProposal personProposal){
		String insertString = "insert personToProposal set personId = ?, proposalId = ?, typeId = ?, stateId = ?, requiredActionsIds = ?, title = ?, lastUpdate = ?, creationDate = ?";
		getSimpleJdbcTemplate().update(insertString,
				personProposal.getPersonId() ,
				personProposal.getProposalId(),
				personProposal.getTypeId(),
				personProposal.getStateId(),
				personProposal.getRequiredActionsIdsString(),
				personProposal.getTitle(),
				new Date( new java.util.Date().getTime()),
				new Date( new java.util.Date().getTime())
		);
	}

	public void updatePersonProposal(PersonProposal personProposal){
		String insertString = "update personToProposal set typeId = ?, stateId = ?, requiredActionsIds = ?, approval = ?, title = ?, lastUpdate = ? where personId = ? and proposalId = ?;";
		getSimpleJdbcTemplate().update(insertString,
				personProposal.getTypeId(),
				personProposal.getStateId(),
				personProposal.getRequiredActionsIdsString(),
				personProposal.isApproval(),
				personProposal.getTitle(),
				DateUtils.formatTimestampWithoutMillis(System.currentTimeMillis()),
				personProposal.getPersonId() ,
				personProposal.getProposalId()
		);
	}

	public void deletePersonProposal(PersonProposal personProposal){
		String deleteString = "delete from personToProposal where personId = ? and proposalId = ? ;";
		getSimpleJdbcTemplate().update(deleteString,
				personProposal.getPersonId() ,
				personProposal.getProposalId()
		);
	}

	public List<PersonProposal> getPersonProposals(int proposalId){
		String personSelect = "select * from personToProposal where proposalId = ? order by typeId;";
		List<PersonProposal> personProposals =
			getSimpleJdbcTemplate().query(personSelect, rowMapper, proposalId);
		return personProposals;
	 }

	public PersonProposal getPersonProposal(int personId, int proposalId){
		String personSelect = "select * from personToProposal where proposalId = ? and personId= ?;";
		List<PersonProposal> personProposals = getSimpleJdbcTemplate().query(personSelect, rowMapper, proposalId, personId);
		if (personProposals.size() == 0) return null;
		return personProposals.get(0);
	}

	ParameterizedRowMapper<PersonProposal> rowMapper = new ParameterizedRowMapper<PersonProposal>(){
		public PersonProposal mapRow(ResultSet rs, int rowNum) throws SQLException{
            PersonProposal personProposal = new PersonProposal();
            personProposal.setId(rs.getInt("id"));
            personProposal.setPersonId(rs.getInt("personId"));
            personProposal.setProposalId(rs.getInt("proposalId"));
            personProposal.setTypeId(rs.getInt("typeId"));
            personProposal.setStateId(rs.getInt("stateId"));
            personProposal.setRequiredActionsIds(rs.getString("requiredActionsIds"));
            personProposal.setApproval(rs.getBoolean("approval"));
            personProposal.setTitle(rs.getString("title"));
            return personProposal;
        }
	};
	public boolean isExists(PersonProposal personProposal){
		String queryString = "select count(*) from personToProposal where personId = ? "+
			" and  proposalId = ? ;";
		int r = getSimpleJdbcTemplate().queryForInt(queryString, personProposal.getPersonId(), personProposal.getProposalId());
		return r==1;
	}
}
