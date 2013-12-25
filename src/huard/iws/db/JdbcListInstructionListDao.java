package huard.iws.db;

import huard.iws.model.AListInstruction;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcListInstructionListDao extends SimpleJdbcDaoSupport implements ListInstructionListDao{


	public List<AListInstruction> getListInstructions(int listId){
		String listsSelect = "select * from listInstruction where listId = ?";
		logger.debug(listsSelect);
		List<AListInstruction> listInstructions =
			getSimpleJdbcTemplate().query(listsSelect, listInstructionDao.getRowMapper(), listId);
		return listInstructions;
	}

	private ListInstructionDao listInstructionDao;
	public void setListInstructionDao(ListInstructionDao listInstructionDao) {
		this.listInstructionDao = listInstructionDao;
	}


}
