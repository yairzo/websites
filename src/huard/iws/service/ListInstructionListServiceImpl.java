package huard.iws.service;

import huard.iws.db.ListInstructionListDao;
import huard.iws.model.AListInstruction;

import java.util.List;

public class ListInstructionListServiceImpl implements ListInstructionListService{
	private ListInstructionListDao listInstructionListDao;


	public List<AListInstruction> getListInstructions(int listId){
		return listInstructionListDao.getListInstructions(listId);
	}

	public void setListInstructionListDao(
			ListInstructionListDao listInstructionListDao) {
		this.listInstructionListDao = listInstructionListDao;
	}

}
