package huard.iws.db;

import huard.iws.model.AListColumnInstruction;

import java.util.List;

public interface ListColumnInstructionListDao {

	public List<AListColumnInstruction> getListColumnInstructions(int listId);

	public List<AListColumnInstruction> getListColumnInstructions(int listId, int parentListId);
}
