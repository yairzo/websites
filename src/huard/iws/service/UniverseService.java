package huard.iws.service;

import huard.iws.model.Continent;
import huard.iws.model.Country;

import java.util.List;

public interface UniverseService {

	public Country getCountry(int countryId);

	public List<Country> getCountries(int continentId);

	public List<Continent> getContinents();

	public int insertCountry(int continentId, String countryName);

}
