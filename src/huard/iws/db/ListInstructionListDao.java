package huard.iws.db;

import huard.iws.model.AListInstruction;

import java.util.List;

public interface ListInstructionListDao {

	public List<AListInstruction> getListInstructions(int listId);

}
