package huard.iws.service;

import huard.iws.db.ListInstructionDao;
import huard.iws.model.AListInstruction;

import java.util.List;


public class ListInstructionServiceImpl implements ListInstructionService{

	//private static final Logger logger = Logger.getLogger(ListInstructionServiceImpl.class);

	public AListInstruction getListInstruction(String type, int id, String username){
		return listInstructionDao.getListInstruction(id);
	}

	public AListInstruction getListInstruction(int id){
		return listInstructionDao.getListInstruction(id);
	}

	public AListInstruction getMasterListInstruction(String type, int listId, String username){
		return getMasterListInstruction(listId);
	}

	public AListInstruction getMasterListInstruction(int listId){
		List<AListInstruction> listInstructionList = listInstructionListService.getListInstructions(listId);
		for (AListInstruction listInstruction: listInstructionList){
			if (listInstruction.isMaster()) return listInstruction;
		}
		AListInstruction aListInstruction = new AListInstruction();
		aListInstruction.setMaster(true);
		return aListInstruction;
	}

	public List<AListInstruction> getListInstructionsByListId(int listId){
		return listInstructionListService.getListInstructions(listId);
	}

	public void updateListInstruction(AListInstruction aListInstruction){
		listInstructionDao.updateListInstruction(aListInstruction);
	}

	public int insertListInstruction(AListInstruction aListInstruction){
		return listInstructionDao.insertListInstruction(aListInstruction);
	}

	public void deleteListInstruction(int id){
		listInstructionDao.deleteListInstruction(id);
	}

	private ListInstructionDao listInstructionDao;

	public void setListInstructionDao(ListInstructionDao listInstructionDao) {
		this.listInstructionDao = listInstructionDao;
	}

	private ListInstructionListService listInstructionListService;



	public void setListInstructionListService(
			ListInstructionListService listInstructionListService) {
		this.listInstructionListService = listInstructionListService;
	}



}
