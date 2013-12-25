package huard.iws.db;

import huard.iws.model.Institute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcInstituteDao extends SimpleJdbcDaoSupport implements InstituteDao {

	private static final Logger logger = Logger.getLogger(JdbcInstituteDao.class);


	public Institute getInstitute(int id){
		String instituteSelect = "select * from institute where id=?";
		logger.debug(instituteSelect);
		Institute institute =
			getSimpleJdbcTemplate().queryForObject(instituteSelect, rowMapper,	id);
		return institute;
	}

	ParameterizedRowMapper<Institute> rowMapper = new ParameterizedRowMapper<Institute>(){
		public Institute mapRow(ResultSet rs, int rowNum) throws SQLException{
            Institute institute = new Institute();
            institute.setId(rs.getInt("id"));
            institute.setName(rs.getString("name"));
            institute.setCity(rs.getString("city"));
            institute.setState(rs.getString("state"));
            institute.setCountryId(rs.getInt("countryId"));
            institute.setContinentId(rs.getInt("continentId"));
            return institute;
        }
	};



	public ParameterizedRowMapper<Institute> getRowMapper() {
		return rowMapper;
	}

	public void updateInstitute(Institute institute){
		String instituteUpdate = "update institute set name = ? , city = ?," +
				" state = ?, countryId = ?, continentId = ? where id = ?";
		logger.debug(instituteUpdate);
		getSimpleJdbcTemplate().update(instituteUpdate,
				institute.getName() ,
				institute.getCity(),
				institute.getState(),
				institute.getCountryId(),
				institute.getContinentId(),
				institute.getId());
	}

	public int insertInstitute(Institute institute){
		final String query = "insert institute set name = ? , city = ?," +
		" state = ?, countryId = ?, continentId = ? ";
		logger.debug(query);
		final String name = institute.getName();
		final String city = institute.getCity();
		final String state = institute.getState();
		final int countryId = institute.getCountryId();
		final int continentId = institute.getContinentId();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setString(1, name);
		            ps.setString(2, city);
		            ps.setString(3, state);
		            ps.setInt(4, countryId);
		            ps.setInt(5, continentId);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deleteInstitute(int id){
		String instituteDelete = "delete from institute where id =?";
		logger.debug(instituteDelete);
		getSimpleJdbcTemplate().update(instituteDelete, id);
	}

}
