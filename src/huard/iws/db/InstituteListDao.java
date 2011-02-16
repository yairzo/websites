package huard.iws.db;

import huard.iws.model.Institute;

import java.util.List;

public interface InstituteListDao {

	public List<Institute> getInstitutes ( int countryId);

	public List<Institute> getInstitutes ();

}
