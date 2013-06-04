package huard.iws.service;

import huard.iws.model.Country;

import java.util.List;

public interface CountryService {
	
	public Country getCountry(int id);
	
	public List<Country> getCountries();

	public List<Country> getFilteredCountries(String term);

}
