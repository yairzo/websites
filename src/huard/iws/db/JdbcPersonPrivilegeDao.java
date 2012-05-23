package huard.iws.db;

import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPersonPrivilegeDao extends SimpleJdbcDaoSupport implements PersonPrivilegeDao {
	//private static final Logger logger = Logger.getLogger(JdbcPersonAttributionDao.class);


	public List<PersonPrivilege> getPersonPrivileges (int personId){
		String query = "select * from personPrivilege where personId=?";
		List<PersonPrivilege> personPrivileges =
			getSimpleJdbcTemplate().query(query, rowMapper,	personId);
		return personPrivileges;
	}
	
	
	public List<Privilege> getAllPrivileges (){
		String query = "select * from privilege;";
		List<Privilege> privileges =
			getSimpleJdbcTemplate().query(query,rowMapperPrivilege);
		return privileges;
	}
	


	ParameterizedRowMapper<PersonPrivilege> rowMapper = new ParameterizedRowMapper<PersonPrivilege>(){
	public PersonPrivilege mapRow(ResultSet rs, int rowNum) throws SQLException{
		PersonPrivilege personPrivilege = new PersonPrivilege();
		personPrivilege.setId(rs.getInt("id"));
		personPrivilege.setPersonId(rs.getInt("personId"));
		personPrivilege.setPassword(rs.getString("password"));
		personPrivilege.setPrivilege(rs.getString("privilege"));
		personPrivilege.setSubscriptionMd5(rs.getString("subscriptionMd5"));
        return personPrivilege;
    }
	};
	ParameterizedRowMapper<Privilege> rowMapperPrivilege = new ParameterizedRowMapper<Privilege>(){
	public Privilege mapRow(ResultSet rs, int rowNum) throws SQLException{
		Privilege privilege = new Privilege();
		privilege.setId(rs.getInt("id"));
		privilege.setPrivilege(rs.getString("privilege"));
        return privilege;
    }
	};

	public void insertPersonPrivilege(int personId, String privilege, String password, String enabled){
		if(password.isEmpty()){
			String query = "select distinct password from personPrivilege where personId = ?";
			password = getSimpleJdbcTemplate().queryForObject(query,String.class,personId);
		}
		String query = "insert ignore personPrivilege set personId = ?, privilege = ?,password=?";
		getSimpleJdbcTemplate().update(query, personId, privilege,password);

		query = "update personPrivilege set password = ?,enabled=?  where personId=?";
		getSimpleJdbcTemplate().update(query, password, enabled, personId);
	}
	
	public void updatePersonPrivilege(int personId,String password, String enabled){
		if(!password.isEmpty()){
			String query = "update personPrivilege set password = ?  where personId=?";
			getSimpleJdbcTemplate().update(query, password, personId);
		}

		String query = "update personPrivilege set enabled=?  where personId=?";
		getSimpleJdbcTemplate().update(query, enabled, personId);
	}
	
	
	public String getPrivilegePassword(int personId){
		try{	
			String query = "select distinct password from personPrivilege where personId = ?";
			return getSimpleJdbcTemplate().queryForObject(query,String.class,personId);
		}
		catch(Exception e){
			return "";
		}
	}
	public int getPrivilegeEnabled(int personId){
			String query = "select distinct enabled from personPrivilege where personId = ?";
			return getSimpleJdbcTemplate().queryForInt(query,personId);
	}

	public void deletePersonPrivilege(int privilege){
		String query = "delete from personPrivilege where id=?";
		getSimpleJdbcTemplate().update(query, privilege);
	}
	
}
