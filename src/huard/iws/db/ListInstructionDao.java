package huard.iws.db;

import huard.iws.model.AListInstruction;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface ListInstructionDao {

	public AListInstruction getListInstruction(int id);

	public void updateListInstruction(AListInstruction aListInstruction);

	public int insertListInstruction(AListInstruction aListInstruction);

	public ParameterizedRowMapper<AListInstruction> getRowMapper();

	public void deleteListInstruction(int id);
}
