package huard.iws.service;

import huard.iws.model.AListInstruction;

import java.util.List;

public interface ListInstructionService {

	public AListInstruction getListInstruction(String type, int id, String username);

	public AListInstruction getListInstruction(int id);

	public AListInstruction getMasterListInstruction(String type, int listId, String username);

	public AListInstruction getMasterListInstruction(int listId);

	public List<AListInstruction> getListInstructionsByListId(int listId);

	public void updateListInstruction(AListInstruction aListInstruction);

	public int insertListInstruction(AListInstruction aListInstruction);

	public void deleteListInstruction(int id);
}

