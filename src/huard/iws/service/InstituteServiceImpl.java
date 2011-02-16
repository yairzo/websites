package huard.iws.service;

import huard.iws.db.InstituteDao;
import huard.iws.model.Institute;



public class InstituteServiceImpl implements InstituteService{


	public Institute getInstitute(int id){
		return instituteDao.getInstitute(id);
	}

	public void updateInstitute (Institute institute){
		instituteDao.updateInstitute(institute);
	}

	public int insertInstitute (Institute institute){
		return instituteDao.insertInstitute(institute);
	}

	public void deleteInstitute(int id){
		instituteDao.deleteInstitute(id);
	}

	public int insertCountry(int continentId, String countryName){
		return universeService.insertCountry(continentId, countryName);
	}


	private InstituteDao instituteDao;


	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	private UniverseService universeService;


	public void setUniverseService(UniverseService universeService) {
		this.universeService = universeService;
	}

}
