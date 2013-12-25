package huard.iws.db;

import huard.iws.model.Continent;
import huard.iws.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcUniverseDao extends SimpleJdbcDaoSupport implements UniverseDao {
	//private static final Logger logger = Logger.getLogger(JdbcTableDescriptionDao.class);

	public Country getCountry(int countryId){
		String queryString = "select * from country where id = ?";
		logger.debug(queryString);
		Country country =
			getSimpleJdbcTemplate().queryForObject(queryString, rowMapper, countryId);
		return country;
	}

	public List<Country> getCoutries(int continentId){
		String queryString = "select * from country where continentId = ?";
		logger.debug(queryString);
		List<Country> countries =
			getSimpleJdbcTemplate().query(queryString, rowMapper, continentId);
		return countries;
	}

	ParameterizedRowMapper<Country> rowMapper = new ParameterizedRowMapper<Country>(){
		public Country mapRow(ResultSet rs, int rowNum) throws SQLException{
            Country country = new Country();
            country.setId(rs.getInt("id"));
            country.setName(rs.getString("name"));
            country.setContinentId(rs.getInt("continentId"));
            return country;
        }
	};

	public List<Continent> getContinents(){
		String queryString = "select * from continent;";
		logger.debug(queryString);
		List<Continent> continents =
			getSimpleJdbcTemplate().query(queryString, new ParameterizedRowMapper<Continent>(){
				public Continent mapRow(ResultSet rs, int rowNum) throws SQLException{
		            Continent continent = new Continent();
		            continent.setId(rs.getInt("id"));
		            continent.setName(rs.getString("name"));
		            return continent;
		        }
			});
		return continents;
	}

	private Country getCountry(String name){
		String query = "select * from country where name = ?;";
		logger.debug(query);
		List<Country> countries = getSimpleJdbcTemplate().query(query, rowMapper, name);
		return countries.size() == 1 ? countries.get(0) : null;

	}

	public int insertCountry(int continentId, String countryName){
		Country country;
		if ((country = getCountry(countryName))==null){
			final String query = "insert country set name = ?, continentId = ?;";
			logger.debug(query);
			final int aContinentId = continentId;
			final String aCountry = countryName;

			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(
					new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps =
								connection.prepareStatement(query, new String[] {"id"});
							ps.setString(1, aCountry);
							ps.setInt(2, aContinentId);
							return ps;
						}
					},
					keyHolder);
			country = new Country();
			country.setId(keyHolder.getKey().intValue());
		}
		return country.getId() ;
	}

}
