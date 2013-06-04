package huard.iws.db;

import huard.iws.model.Country;
import huard.iws.model.Fund;

import java.util.List;

public interface CountryDao {

	public Country getCountry(int id);

	public List<Country> getCountries();

}
