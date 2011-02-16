package huard.iws.service;

import huard.iws.model.Institute;


public interface InstituteService {

	public Institute getInstitute(int id);

	public void updateInstitute (Institute institute);

	public int insertInstitute (Institute institute);

	public void deleteInstitute(int id);

	public int insertCountry(int continentId, String country);

}
