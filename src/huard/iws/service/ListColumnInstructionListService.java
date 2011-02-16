package huard.iws.service;

import huard.iws.model.AListColumnInstruction;

import java.util.List;

public interface ListColumnInstructionListService {

	public List<AListColumnInstruction> getListColumnInstructions(int listId);

	public List<AListColumnInstruction> getListColumnInstructions(int listId, int parentListId);
}
