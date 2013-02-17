package huard.iws.db;

import huard.iws.bean.PersonBean;
import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
		Timestamp lastAction = rs.getTimestamp("lastAction");
		personPrivilege.setLastAction(lastAction != null ? lastAction.getTime() : 0);
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
			String query = "select password from personPrivilege where personId = ? limit 1";
			password = getSimpleJdbcTemplate().queryForObject(query,String.class,personId);
		}
		//if already exists a privilege for this person, update lastLogin 
		String query = "select count(*) from personPrivilege where personId = ?";
		int count = getSimpleJdbcTemplate().queryForInt(query,personId);

		query = "insert ignore personPrivilege set personId = ?, privilege = ?,password=?";
		getSimpleJdbcTemplate().update(query, personId, privilege,password);

		query = "update personPrivilege set password = ?,enabled=? ";
		if (count>0)
			query += ", lastLogin = now()";
		query += " where personId=?";
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
			String query = "select distinct password from personPrivilege where personId = ? limit 1";
			return getSimpleJdbcTemplate().queryForObject(query,String.class,personId);
		}
		catch(Exception e){
			return "";
		}
	}
	public int getPrivilegeEnabled(int personId){
		try{	
			String query = "select distinct enabled from personPrivilege where personId = ?";
			return getSimpleJdbcTemplate().queryForInt(query,personId);
		}
		catch(Exception e){
			return 0;
		}
	}

	public void deletePersonPrivilege(int privilege){
		String query = "delete from personPrivilege where id=?";
		getSimpleJdbcTemplate().update(query, privilege);
	}
	
	public void updateLastAction(PersonBean person){
		String query = "update personPrivilege set lastAction=now()  where personId=?";
		getSimpleJdbcTemplate().update(query, person.getId());
	}	
	public List<PersonPrivilege> getActivePersons(){
		String query = "select * from personPrivilege where lastAction >= DATE_SUB(NOW(),INTERVAL 30 MINUTE)  group by personId";
		List<PersonPrivilege> persons = getSimpleJdbcTemplate().query(query, rowMapper);
		return persons;
	}
	public void clearLastActionTime(){
		String query = "update personPrivilege set lastAction='0000-00-00 00:00:00'";
		getSimpleJdbcTemplate().update(query);
	}	
	public void updateSubscriptionMd5(int personId,String subscriptionMd5){
		String query = "update personPrivilege set subscriptionMd5=?  where personId=?";
		getSimpleJdbcTemplate().update(query, subscriptionMd5,personId);
	}

}
