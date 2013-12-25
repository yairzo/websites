package huard.iws.db;


import huard.iws.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcCountryDao extends SimpleJdbcDaoSupport implements CountryDao {

	public Country getCountry(int id){
		String query = "select * from country where id=?;";
		logger.debug(query);
		Country country =  getSimpleJdbcTemplate().queryForObject(query, countryRowMapper,id);
		return country;
	}

	public List<Country> getCountries(){
		String query = "select * from country;";
		logger.debug(query);
		List<Country> countries =  getSimpleJdbcTemplate().query(query, countryRowMapper);
		return countries;
	}
	
	
	private ParameterizedRowMapper<Country> countryRowMapper = new ParameterizedRowMapper<Country>(){
		public Country mapRow(ResultSet rs, int rowNum) throws SQLException{
			Country country = new Country();
			country.setNameHebrew(rs.getString("nameHebrew"));
			country.setName(rs.getString("name"));
			country.setId(rs.getInt("id"));
            return country;
 		}
	};



}
