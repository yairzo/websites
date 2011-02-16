package huard.iws.db;

import huard.iws.model.AListColumnInstruction;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcListColumnInstructionListDao extends SimpleJdbcDaoSupport implements ListColumnInstructionListDao {

	public List<AListColumnInstruction> getListColumnInstructions(int listId){
		return getListColumnInstructions(listId, 0);
	}

	public List<AListColumnInstruction> getListColumnInstructions(int listId, int parentListId){
		if (parentListId == 0)
			parentListId = listId;
		String query = "select * from listColumnInstruction, listColumnDesign " +
		"where listColumnInstruction.id = listColumnDesign.listColumnInstructionId " +
		"and listColumnInstruction.listId=? and listColumnDesign.listId = ? order by hidden";

		List<AListColumnInstruction> listColumnInstructions =
			getSimpleJdbcTemplate().query(query, listColumnInstructionDao.getRowMapper(), listId, parentListId);
		return listColumnInstructions;
	}

	private ListColumnInstructionDao listColumnInstructionDao;
	public void setListColumnInstructionDao(ListColumnInstructionDao listColumnInstructionDao) {
		this.listColumnInstructionDao = listColumnInstructionDao;
	}

}
