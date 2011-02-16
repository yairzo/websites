package huard.iws.db;

import huard.iws.model.ProposalState;
import huard.iws.util.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcProposalStateHistoryDao extends SimpleJdbcDaoSupport implements ProposalStateHistoryDao {

	public void insertProposalState(ProposalState proposalState){
		String insert = "insert proposalStateHistory set stateId = ?, actionId = ?, proposalId = ?,  personId = ?"
			+", personRole = ?, stateDetails = ?, actionDetails = ?, timeLogged = ?;";

		getSimpleJdbcTemplate().update(insert,
				proposalState.getStateId(),
				proposalState.getActionId(),
				proposalState.getProposalId(),
				proposalState.getPersonId(),
				proposalState.getPersonRole(),
				proposalState.getStateDetails(),
				proposalState.getActionDetails(),
				DateUtils.getCurrentTime());
	}

	public boolean isProposalStateInHistory( ProposalState proposalState){
		String select = "select count(*) from proposalStateHistory where proposalId = ? and stateId = ?; ";
		int r = getSimpleJdbcTemplate().queryForInt(select,
				proposalState.getStateId(),
				proposalState.getProposalId());
		return r==1;
	}

	public List<ProposalState> getProposalStates(int proposalId){
		String query = "select * from proposalStateHistory where proposalId = ?; ";
		List<ProposalState> proposalStates = getSimpleJdbcTemplate().query(query, rowMapper, proposalId);
		return proposalStates;
	}

	private ParameterizedRowMapper<ProposalState> rowMapper = new ParameterizedRowMapper<ProposalState>(){
		public ProposalState mapRow(ResultSet rs, int rowNum) throws SQLException{
            ProposalState proposalState = new ProposalState();
            proposalState.setId(rs.getInt("id"));
            proposalState.setStateId(rs.getInt("stateId"));
            proposalState.setActionId(rs.getInt("actionId"));
            proposalState.setProposalId(rs.getInt("proposalId"));
            proposalState.setPersonId(rs.getInt("personId"));
            proposalState.setStateDetails(rs.getString("stateDetails"));
            proposalState.setActionDetails(rs.getString("actionDetails"));
            proposalState.setTimeLogged(rs.getTimestamp("timeLogged"));
            return proposalState;
        }
	};






}
