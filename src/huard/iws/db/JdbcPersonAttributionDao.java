package huard.iws.db;

import huard.iws.model.PersonListAttribution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcPersonAttributionDao extends SimpleJdbcDaoSupport implements PersonAttributionDao {
	//private static final Logger logger = Logger.getLogger(JdbcPersonAttributionDao.class);


	public PersonListAttribution getPersonAttribution (int personAttributionId){
		String personSelect = "select * from personAttribution where id=?";
		logger.debug(personSelect);
		PersonListAttribution personAttribution =
			getSimpleJdbcTemplate().queryForObject(personSelect, rowMapper,	personAttributionId);
		return personAttribution;
	}

	public PersonListAttribution getPersonAttributionByListIdPersonId (int listId, int personId){
		String personSelect = "select * from personAttribution where listId=? and personId = ?";
		logger.debug(personSelect);
		PersonListAttribution personAttribution =
			getSimpleJdbcTemplate().queryForObject(personSelect, rowMapper,	listId, personId);
		return personAttribution;
	}

	ParameterizedRowMapper<PersonListAttribution> rowMapper = new ParameterizedRowMapper<PersonListAttribution>(){
	public PersonListAttribution mapRow(ResultSet rs, int rowNum) throws SQLException{
        PersonListAttribution personAttribution = new PersonListAttribution();
        personAttribution.setId(rs.getInt("id"));
        personAttribution.setPersonId(rs.getInt("personId"));
        personAttribution.setListId(rs.getInt("listId"));
        personAttribution.setTitle(rs.getString("title"));
        personAttribution.setEmail(rs.getString("email"));
        personAttribution.setPhone(rs.getString("phone"));
        personAttribution.setFax(rs.getString("fax"));
        personAttribution.setAddress(rs.getString("address"));
        personAttribution.setDepartment(rs.getString("department"));
        personAttribution.setFacultyId(rs.getInt("facultyId"));
        personAttribution.setPlaceInList(rs.getInt("placeInList"));
        personAttribution.setTitleId(rs.getInt("titleId"));
        personAttribution.setConnectDetails(rs.getBoolean("connectDetails"));
        personAttribution.setImageUrl(rs.getString("imageUrl"));
        personAttribution.prepareForView();
        return personAttribution;
    }
	};


	public void updatePersonAttribution(PersonListAttribution personAttribution){
		String attributionUpdate = "update personAttribution set personId = ?," +
				"listId = ?," +
				"title = ?,"+
				"email = ?,"+
				"phone = ?,"+
				"fax = ?,"+
				"address = ?,"+
				"department = ?,"+
				"facultyId = ?,"+
				"placeInList = ?,"+
				"titleId = ?,"+
				"connectDetails = ?,"+
				"imageUrl = ?"+
				" where id = ?";
		logger.debug(attributionUpdate);

		getSimpleJdbcTemplate().update(attributionUpdate,
					personAttribution.getPersonId(),
					personAttribution.getListId(),
					personAttribution.getTitle(),
					personAttribution.getEmail(),
					personAttribution.getPhone(),
					personAttribution.getFax(),
					personAttribution.getAddress(),
					personAttribution.getDepartment(),
					personAttribution.getFacultyId(),
					personAttribution.getPlaceInList(),
					personAttribution.getTitleId(),
					personAttribution.isConnectDetails(),
					personAttribution.getImageUrl(),
					personAttribution.getId() );
	}

	public void updatePersonAttributionPlaceInList(PersonListAttribution personAttribution){
		String attributionUpdate = "update personAttribution set placeInList = ?"+
				" where id = ?";
		logger.debug(attributionUpdate);
		getSimpleJdbcTemplate().update(attributionUpdate,
					personAttribution.getPlaceInList(),
					personAttribution.getId() );
	}

	public int insertPersonAttribution(PersonListAttribution personListAttribution){
		final String query = "insert personAttribution set personId = ?,"+
		"listId = ?," +
		"title = ?,"+
		"email = ?,"+
		"phone = ?,"+
		"fax = ?,"+
		"address = ?,"+
		"department = ?,"+
		"facultyId = ?,"+
		"placeInList = ?,"+
		"titleId = ?,"+
		"connectDetails = ?,"+
		"imageUrl = ?";
		logger.debug(query);
		final int personId = personListAttribution.getPersonId();
		final int listId = personListAttribution.getListId();
		final String title = personListAttribution.getTitle();
		final String email = personListAttribution.getEmail();
		final String phone = personListAttribution.getPhone();
		final String fax = personListAttribution.getFax();
		final String address = personListAttribution.getAddress();
		final String department = personListAttribution.getDepartment();
		final int facultyId = personListAttribution.getFacultyId();
		final int placeInList = personListAttribution.getPlaceInList();
		final int titleId = personListAttribution.getTitleId();
		final boolean connectDetails = personListAttribution.isConnectDetails();
		final String imageUrl = personListAttribution.getImageUrl();
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt (1, personId);
					ps.setInt (2, listId);
					ps.setString (3, title);
					ps.setString (4, email);
					ps.setString (5, phone);
					ps.setString (6, fax);
					ps.setString (7, address);
					ps.setString (8, department);
					ps.setInt (9, facultyId);
					ps.setInt (10, placeInList);
					ps.setInt (11, titleId);
					ps.setBoolean(12,connectDetails);
					ps.setString (13, imageUrl);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void deletePersonAttribution(int id ){
		String attributionDelete = "delete from personAttribution where id = ?";
		logger.debug(attributionDelete);
		getSimpleJdbcTemplate().update(attributionDelete, id);
	}
	
	public Map<Integer, Integer> getPersonDeskMap(){
		final Map<Integer,Integer> personDeskMap = new HashMap<Integer,Integer>();
		String query = "select personId,desk.id from desk inner join personAttribution on desk.personsListId=personAttribution.listId order by personId;";
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				if(!personDeskMap.containsKey(rs.getInt(1)))
					personDeskMap.put(rs.getInt(1), rs.getInt(2));
				return null;
			}
		});
		query = "select personId,desk.id from desk inner join personAttribution on desk.personsListIdEnglish=personAttribution.listId order by personId;";
		getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				if(!personDeskMap.containsKey(rs.getInt(1)))
					personDeskMap.put(rs.getInt(1), rs.getInt(2));
				return null;
			}
		});
		return personDeskMap;
	}
	
	public ParameterizedRowMapper<PersonListAttribution> getRowMapper(){
		return rowMapper;
	}
	
}
