package huard.iws.service;

import huard.iws.db.CountryDao;
import huard.iws.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryServiceImpl implements CountryService{

	public Country getCountry(int id){
		return countryDao.getCountry(id);
	}

	
	public List<Country> getCountries(){
		return countryDao.getCountries();
	}

	public List<Country> getFilteredCountries ( String term){
		List<Country> countries = getCountries();
		List<Country> filteredCountries = new ArrayList<Country>();
		for (Country country: countries){
			if (country !=null && ((country.getName()!=null && country.getName().toLowerCase().contains(term.toLowerCase())) || (country.getNameHebrew()!=null && country.getNameHebrew().contains(term)))){
				filteredCountries.add(country);
			}
		}
		return filteredCountries;
	}

	
	private CountryDao countryDao;

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

}
