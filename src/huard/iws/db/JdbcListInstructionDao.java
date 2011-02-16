package huard.iws.db;

import huard.iws.model.AListInstruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcListInstructionDao extends SimpleJdbcDaoSupport implements ListInstructionDao{

	//private static final Logger logger = Logger.getLogger(JdbcListInstructionDao.class);


	public AListInstruction getListInstruction(int id){
		String aListInstrucitonSelect = "select * from listInstruction where id=?";
		AListInstruction aListInstruction =
			getSimpleJdbcTemplate().queryForObject(aListInstrucitonSelect,
					rowMapper,	id);
		return aListInstruction;
	}


	public ParameterizedRowMapper<AListInstruction> rowMapper  = new ParameterizedRowMapper<AListInstruction>(){
		public AListInstruction mapRow(ResultSet rs, int rowNum) throws SQLException{
            AListInstruction aListInstruction = new AListInstruction();
            aListInstruction.setId(rs.getInt("id"));
            aListInstruction.setListId(rs.getInt("listId"));
            aListInstruction.setSelectsFrom(rs.getString("selectsFrom"));
            aListInstruction.setSubTables(rs.getString("subTables"));
            aListInstruction.setColumnsSelection(rs.getString("columnsSelection"));
            aListInstruction.setDefaultOrderByColumn(rs.getString("defaultOrderByColumn"));
            aListInstruction.setDefaultOrderDirection(rs.getString("defaultOrderDirection"));
            aListInstruction.setPartialViewConditions(rs.getString("partialViewConditions"));
            aListInstruction.setMaster(rs.getBoolean("master"));
            return aListInstruction;
        }
	};

	public void updateListInstruction(AListInstruction aListInstruction){
		String aListInstructionUpdate = "update listInstruction set "+
			"selectsFrom = ?,"+
			"subTables = ?," +
			"columnsSelection = ?," +
			"defaultOrderByColumn = ?," +
			"defaultOrderDirection = ?," +
			"partialViewConditions = ?" +
			" where id = ?";
		getSimpleJdbcTemplate().update(aListInstructionUpdate,
				aListInstruction.getSelectsFrom(),
				aListInstruction.getSubTables(),
				aListInstruction.getColumnsSelection(),
				aListInstruction.getDefaultOrderByColumn(),
				aListInstruction.getDefaultOrderDirection(),
				aListInstruction.getPartialViewConditions(),
				aListInstruction.getId() );
	}

	public int insertListInstruction(AListInstruction listInstruction){
		final String query = "insert listInstruction set "+
		"listId = ?,"+
		"selectsFrom = ?," +
		"subTables = ?," +
		"columnsSelection = ?," +
		"defaultOrderByColumn = ?," +
		"defaultOrderDirection = ?," +
		"partialViewConditions = ?," +
		"master = ?";
		final int listId = listInstruction.getListId();
		final String selectsFrom = listInstruction.getSelectsFrom();
		final String subTables = listInstruction.getSubTables();
		final String columnsSelection = listInstruction.getColumnsSelection();
		final String defaultOrderByColumn = listInstruction.getDefaultOrderByColumn();
		final String defaultOrderDirection = listInstruction.getDefaultOrderDirection();
		final String partialViewConditions = listInstruction.getPartialViewConditions();
		final boolean master = listInstruction.isMaster();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1, listId);
		            ps.setString(2, selectsFrom);
		            ps.setString(3, subTables);
		            ps.setString(4, columnsSelection);
		            ps.setString(5, defaultOrderByColumn);
		            ps.setString(6, defaultOrderDirection);
		            ps.setString(7, partialViewConditions);
		            ps.setBoolean(8, master);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}


	public void deleteListInstruction( int id){
		String aListInstructionDelete = "delete from listInstruction where id = ?";
		getSimpleJdbcTemplate().update(aListInstructionDelete, id);
	}

	public ParameterizedRowMapper<AListInstruction> getRowMapper() {
		return rowMapper;
	}
}
