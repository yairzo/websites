package huard.iws.service;

import huard.iws.db.InstituteListDao;
import huard.iws.model.Institute;

import java.util.LinkedHashMap;
import java.util.List;

public class InstituteListServiceImpl implements InstituteListService{

	public List<Institute> getInstitutes ( int countryId){
		return instituteListDao.getInstitutes(countryId);
	}

	public List<Institute> getInstitutes (){
		return instituteListDao.getInstitutes();
	}



	public LinkedHashMap<String, Institute> getInstitutesMap(){
		List<Institute> institutes = getInstitutes();
		LinkedHashMap<String, Institute> institutesMap = new LinkedHashMap<String, Institute>();
		for (Institute institute: institutes){
			institutesMap.put(institute.getName(), institute);
		}
		return institutesMap;
	}

	public Institute getInstituteByName(String instituteName){
		return getInstitutesMap().get(instituteName);
	}

	private InstituteListDao instituteListDao;

	public void setInstituteListDao(InstituteListDao instituteListDao) {
		this.instituteListDao = instituteListDao;
	}

}
