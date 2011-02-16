package huard.iws.service;

import huard.iws.model.Institute;

import java.util.LinkedHashMap;
import java.util.List;

public interface InstituteListService {

	public List<Institute> getInstitutes ();

	public List<Institute> getInstitutes ( int countryId);

	public LinkedHashMap<String, Institute> getInstitutesMap ();

	public Institute getInstituteByName(String instituteName);

}
