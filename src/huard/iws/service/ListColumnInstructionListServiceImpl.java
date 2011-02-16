package huard.iws.service;

import huard.iws.db.ListColumnInstructionListDao;
import huard.iws.model.AListColumnInstruction;
import huard.iws.model.AListInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ListColumnInstructionListServiceImpl implements ListColumnInstructionListService{

	//private static final Logger logger = Logger.getLogger(ListColumnInstructionListServiceImpl.class);

	public List<AListColumnInstruction> getListColumnInstructions(int listId){
		return getListColumnInstructions(listId, 0);
	}

	public List<AListColumnInstruction> getListColumnInstructions(int listId, int parentListId){
		List<AListColumnInstruction> listColumnInstructions
			= listColumnInstructionListDao.getListColumnInstructions(listId, parentListId);

		List<AListColumnInstruction> orderdListColumnInstructions = new ArrayList<AListColumnInstruction>();
		AListInstruction listInstruction = listInstructionService.getMasterListInstruction(listId);
		StringTokenizer st = new StringTokenizer(listInstruction.getColumnsSelection(), ",");
		//We build an ordered list of column by the order in the master list instruction
		//We start with the visible columns
		while (st.hasMoreTokens()){
			String columnName = st.nextToken();
			for (AListColumnInstruction columnInstruction: listColumnInstructions){
				if (! columnInstruction.isHidden() && columnInstruction.getColumnName().equals(columnName)){
					orderdListColumnInstructions.add(columnInstruction);
				}
			}
		}
		//Now we add the unvisible columns, the order is not important anymore.
		for (AListColumnInstruction columnInstruction: listColumnInstructions){
			if (columnInstruction.isHidden()){
				orderdListColumnInstructions.add(columnInstruction);
			}
		}
		return orderdListColumnInstructions;
	}

	private ListColumnInstructionListDao listColumnInstructionListDao;

	public void setListColumnInstructionListDao(
			ListColumnInstructionListDao listColumnInstructionListDao) {
		this.listColumnInstructionListDao = listColumnInstructionListDao;
	}

	private ListInstructionService listInstructionService;

	public void setListInstructionService(
			ListInstructionService listInstructionService) {
		this.listInstructionService = listInstructionService;
	}



}
