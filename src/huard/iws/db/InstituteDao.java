package huard.iws.db;

import huard.iws.model.Institute;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface InstituteDao {

	public Institute getInstitute(int id);

	public ParameterizedRowMapper<Institute> getRowMapper();

	public int insertInstitute(Institute institute);

	public void updateInstitute(Institute institute);

	public void deleteInstitute (int id);




}
