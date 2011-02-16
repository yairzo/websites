package huard.iws.service;

import huard.iws.bean.AListBean;
import huard.iws.bean.AListColumnInstructionBean;
import huard.iws.db.ListColumnInstructionDao;
import huard.iws.db.ListColumnInstructionListDao;
import huard.iws.model.AList;
import huard.iws.model.AListColumnInstruction;
import huard.iws.util.RequestWrapper;

import java.util.List;


public class ListColumnInstructionServiceImpl implements ListColumnInstructionService{

	//private static final Logger logger = Logger.getLogger(ListColumnInstructionServiceImpl.class);

	public AListColumnInstruction getListColumnInstruction(String type, int id, String username){
		return listColumnInstructionDao.getListColumnInstruction(id);
	}

	public AListColumnInstruction getListColumnInstruction(String type, int id, int listId, String username){
		return listColumnInstructionDao.getListColumnInstruction(id, listId);
	}

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction, int parentListId){
		listColumnInstructionDao.updateListColumnInstruction(aListColumnInstruction, parentListId);
	}

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction){
		listColumnInstructionDao.updateListColumnInstruction(aListColumnInstruction);
	}

	public void insertListColumnInstruction(AListColumnInstruction aListColumnInstruction){
		listColumnInstructionDao.insertListColumnInstruction(aListColumnInstruction, false);
	}

	public void insertListColumnDesign (AListColumnInstruction aListColumnInstruction){
		listColumnInstructionDao.insertListColumnDesign(aListColumnInstruction);
	}

	public void insertIfNotExistsListColumnInstruction(AListColumnInstruction aListColumnInstruction){
		List<AListColumnInstruction> listColumnsInstructions = listColumnInstructionListDao.getListColumnInstructions(aListColumnInstruction.getListId());
		boolean inList = false;
		for (AListColumnInstruction columnInst : listColumnsInstructions){
			if (columnInst.getColumnName().equals(aListColumnInstruction.getColumnName())){
				inList = true;
					break;
			}
		}
		if (! inList) listColumnInstructionDao.insertListColumnInstruction(aListColumnInstruction, true);
	}

	public void deleteListColumnInstruction(int id){
		listColumnInstructionDao.deleteListColumnInstruction(id);
	}

	public void deleteListColumnDesign(int listId, int parentListId, RequestWrapper request){
		AList aList = listService.getList(listId);
		AListBean aListBean = new AListBean(aList, request);
		for (AListColumnInstructionBean columnInstruction: aListBean.getColumnBeans()){
			listColumnInstructionDao.deleteListColumnDesign(columnInstruction.getId(), parentListId);
		}
	}

	private ListColumnInstructionDao listColumnInstructionDao;

	public void setListColumnInstructionDao(ListColumnInstructionDao listColumnInstructionDao) {
		this.listColumnInstructionDao = listColumnInstructionDao;
	}

	private ListColumnInstructionListDao listColumnInstructionListDao;



	public void setListColumnInstructionListDao(
			ListColumnInstructionListDao listColumnInstructionListDao) {
		this.listColumnInstructionListDao = listColumnInstructionListDao;
	}

	public ListService listService;

	public void setListService(ListService listService) {
		this.listService = listService;
	}





}
