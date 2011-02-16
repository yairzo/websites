package huard.iws.db;

import huard.iws.model.Continent;
import huard.iws.model.Country;

import java.util.List;


public interface UniverseDao {

	public Country getCountry(int countryId);

	public List<Country> getCoutries(int continentId);

	public List<Continent> getContinents();

	public int insertCountry(int continentId, String countryName);

}
