package huard.iws.db;

import huard.iws.model.AListColumnInstruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcListColumnInstructionDao extends SimpleJdbcDaoSupport implements ListColumnInstructionDao{

	//private static final Logger logger = Logger.getLogger(JdbcListColumnInstructionDao.class);

	public AListColumnInstruction getListColumnInstruction(int id){
		return getListColumnInstruction(id, 0);
	}

	public AListColumnInstruction getListColumnInstruction(int id, int listId){
		String query = "select * from listColumnInstruction, listColumnDesign " +
				"where listColumnInstruction.id = listColumnDesign.listColumnInstructionId and listColumnInstruction.id = ?";
		logger.debug(query);
		AListColumnInstruction aListColumnInstruction;
		if (listId > 0){
				query += " and listId = ?";
		aListColumnInstruction =
			getSimpleJdbcTemplate().queryForObject(query,
					rowMapper,	id, listId);
		}
		else
			aListColumnInstruction = getSimpleJdbcTemplate().query(query, rowMapper, id).get(0);
		return aListColumnInstruction;
	}



	public ParameterizedRowMapper<AListColumnInstruction> rowMapper  = new ParameterizedRowMapper<AListColumnInstruction>(){
		public AListColumnInstruction mapRow(ResultSet rs, int rowNum) throws SQLException{
            AListColumnInstruction aListColumnInstruction = new AListColumnInstruction();
            aListColumnInstruction.setId(rs.getInt("listColumnInstruction.id"));
            aListColumnInstruction.setListId(rs.getInt("listId"));
            aListColumnInstruction.setColumnName(rs.getString("columnName"));
            aListColumnInstruction.setColumnDisplayName(rs.getString("columnDisplayName"));
            aListColumnInstruction.setOrderBy(rs.getString("orderBy"));
            aListColumnInstruction.setHidden(rs.getBoolean("hidden"));
            aListColumnInstruction.setWidth(rs.getInt("listColumnDesign.width"));
            aListColumnInstruction.setBold(rs.getBoolean("listColumnDesign.bold"));
            aListColumnInstruction.setAlign(rs.getString("listColumnDesign.align"));
            aListColumnInstruction.setNoBr(rs.getBoolean("listColumnDesign.nobr"));
            aListColumnInstruction.setMailAddress(rs.getBoolean("mailAddress"));
            aListColumnInstruction.setWebAddress(rs.getBoolean("webAddress"));
            aListColumnInstruction.setImage(rs.getBoolean("image"));
            aListColumnInstruction.setLinkTargetFromColumn(rs.getString("linkTargetFromColumn"));
            aListColumnInstruction.setUseHelperTable(rs.getBoolean("useHelperTable"));
            aListColumnInstruction.setHelperTableName(rs.getString("helperTableName"));
            aListColumnInstruction.setHelperTableDisplayColumnName(rs.getString("helperTableDisplayColumnName"));
            aListColumnInstruction.setSortable(rs.getBoolean("sortable"));
            aListColumnInstruction.setManuallyEdited(rs.getBoolean("manuallyEdited"));
            return aListColumnInstruction;
        }
	};

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction){
		updateListColumnInstruction(aListColumnInstruction, 0);
	}

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction, int parentListId){
		String query = "update listColumnInstruction set "+
			"listId = ?,"+
			"columnName = ?," +
			"columnDisplayName = ?," +
			"orderBy = ?, "+
			"hidden = ?," +
			"mailAddress = ?," +
			"webAddress = ?," +
			"image = ?," +
			"linkTargetFromColumn = ?," +
			"useHelperTable = ?," +
			"helperTableName = ?," +
			"helperTableDisplayColumnName = ?," +
			"sortable = ?," +
			"manuallyEdited = 1"+
			" where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				aListColumnInstruction.getListId(),
				aListColumnInstruction.getColumnName(),
				aListColumnInstruction.getColumnDisplayName(),
				aListColumnInstruction.getOrderBy(),
				aListColumnInstruction.isHidden(),
				aListColumnInstruction.isMailAddress(),
				aListColumnInstruction.isWebAddress(),
				aListColumnInstruction.isImage(),
				aListColumnInstruction.getLinkTargetFromColumn(),
				aListColumnInstruction.isUseHelperTable(),
				aListColumnInstruction.getHelperTableName(),
				aListColumnInstruction.getHelperTableDisplayColumnName(),
				aListColumnInstruction.isSortable(),
				aListColumnInstruction.getId() );
		updateListColumnDesign(aListColumnInstruction, parentListId);
	}

	private void updateListColumnDesign(AListColumnInstruction aListColumnInstruction, int parentListId){
		String query = "update listColumnDesign set width = ?, bold = ?, align = ?, nobr = ? where listColumnInstructionId = ? and listId = ?";
		logger.debug(query);
		int listId = parentListId > 0 ? parentListId : aListColumnInstruction.getListId();
		getSimpleJdbcTemplate().update(query,
				aListColumnInstruction.getWidth(),
				aListColumnInstruction.isBold(),
				aListColumnInstruction.getAlign(),
				aListColumnInstruction.isNoBr(),
				aListColumnInstruction.getId(),
				listId);
	}



	public void insertListColumnInstruction(AListColumnInstruction aListColumnInstruction, boolean autoGenerated){
		final String query = "insert listColumnInstruction set "+
		"listId = ?,"+
		"columnName = ?," +
		"columnDisplayName = ?," +
		"orderBy = ?,"+
		"hidden = ?," +
		"mailAddress = ?," +
		"webAddress = ?," +
		"image = ?," +
		"linkTargetFromColumn = ?," +
		"useHelperTable = ?," +
		"helperTableName = ?," +
		"helperTableDisplayColumnName = ?," +
		"sortable = ?,"+
		"manuallyEdited = "+(autoGenerated ? 0 : 1);


		final int listId = aListColumnInstruction.getListId();
		final String columnName =	aListColumnInstruction.getColumnName();
		final String columnDisplayName = aListColumnInstruction.getColumnDisplayName();
		final String orderBy = aListColumnInstruction.getOrderBy();
		final boolean hidden = 	aListColumnInstruction.isHidden();
		final boolean mailAddress = aListColumnInstruction.isMailAddress();
		final boolean webAddress = 	aListColumnInstruction.isWebAddress();
		final boolean image = aListColumnInstruction.isImage();
		final String linkTargetFromColumn = aListColumnInstruction.getLinkTargetFromColumn();
		final boolean useHelperTable = aListColumnInstruction.isUseHelperTable();
		final String helperTableName = aListColumnInstruction.getHelperTableName();
		final String helperTableDisplayColumn = aListColumnInstruction.getHelperTableDisplayColumnName();
		final boolean sortable =aListColumnInstruction.isSortable();
		logger.debug(query);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1, listId);
		            ps.setString(2, columnName);
		            ps.setString(3, columnDisplayName);
		            ps.setString(4, orderBy);
		            ps.setBoolean(5, hidden);
		            ps.setBoolean(6, mailAddress);
		            ps.setBoolean(7, webAddress);
		            ps.setBoolean(8, image);
		            ps.setString(9, linkTargetFromColumn);
		            ps.setBoolean(10, useHelperTable);
		            ps.setString(11, helperTableName);
		            ps.setString(12, helperTableDisplayColumn);
		            ps.setBoolean(13, sortable);
		            return ps;
		        }
		    },
		    keyHolder);

		aListColumnInstruction.setId(keyHolder.getKey().intValue());
		insertListColumnDesign(aListColumnInstruction);
	}

	public void insertListColumnDesign(AListColumnInstruction aListColumnInstruction){
		String query = "insert listColumnDesign set listColumnInstructionId = ?, listId = ?, width = ?" +
				", bold = ?, align = ?, nobr = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,
				aListColumnInstruction.getId(),
				aListColumnInstruction.getListId(),
				aListColumnInstruction.getWidth(),
				aListColumnInstruction.isBold(),
				aListColumnInstruction.getAlign(),
				aListColumnInstruction.isNoBr());
	}


	public void deleteListColumnInstruction( int id){
		String query = "delete from listColumnInstruction where id = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
	}

	public void deleteListColumnDesign (int columnInstructionId, int listId){
		String query = "delete from listColumnDesign where listColumnInstructionId = ?" +
				" and listId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, columnInstructionId, listId);
	}

	public ParameterizedRowMapper<AListColumnInstruction> getRowMapper() {
		return rowMapper;
	}

}
