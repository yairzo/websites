package huard.iws.service;

import huard.iws.model.AListColumnInstruction;
import huard.iws.util.RequestWrapper;

public interface ListColumnInstructionService {

	public AListColumnInstruction getListColumnInstruction(String type, int id, String username);

	public AListColumnInstruction getListColumnInstruction(String type, int id, int listId, String username);

	//public AListColumnInstruction getListColumnInstructionByListId(int listId);

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction);

	public void updateListColumnInstruction(AListColumnInstruction aListColumnInstruction, int parentListId);

	public void insertListColumnInstruction(AListColumnInstruction aListColumnInstruction);

	public void insertListColumnDesign(AListColumnInstruction aListColumnInstruction);

	public void insertIfNotExistsListColumnInstruction(AListColumnInstruction aListColumnInstruction);

	public void deleteListColumnInstruction(int id);

	public void deleteListColumnDesign(int listId, int parentListId, RequestWrapper request);

}
