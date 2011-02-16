package huard.iws.service;


import huard.iws.db.UniverseDao;
import huard.iws.model.Continent;
import huard.iws.model.Country;

import java.util.List;

public class UniverseServiceImpl implements UniverseService{

	public Country getCountry(int countryId){
		return universeDao.getCountry(countryId);
	}

	public List<Country> getCountries(int continentId){
		return universeDao.getCoutries(continentId);
	}

	public List<Continent> getContinents(){
		return universeDao.getContinents();
	}

	public int insertCountry(int continentId, String countryName){
		return universeDao.insertCountry(continentId, countryName);
	}


	private UniverseDao universeDao;


	public void setUniverseDao(UniverseDao universeDao) {
		this.universeDao = universeDao;
	}

}
