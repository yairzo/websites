package huard.iws.db;

import huard.iws.model.FinancialSupport;
import huard.iws.service.LocksService.LockedObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcLocksDao extends SimpleJdbcDaoSupport implements LocksDao{
	
	public boolean isLockedBySelf (String id, String subject, String arguments, int lockedBy){
		String query = "select count(*) from moduleLock where id = ? and subject = ? and arguments = ? and lockedBy=?  and expiryTime > now()";
		logger.debug(query);
		//System.out.println(query);
		int count= getSimpleJdbcTemplate().queryForInt(query, id,subject,arguments,lockedBy);
		//System.out.println("count:"+count);
		if (count>0)return true;
		else return false;
	}
	
	public boolean isLockedByOthers (String id, String subject, String arguments, int lockedBy){
		String query = "select count(*) from moduleLock where id = ? and subject = ? and arguments = ? and lockedBy<>?  and expiryTime >";
		logger.debug(query);
		//System.out.println(query);
		int count= getSimpleJdbcTemplate().queryForInt(query, id,subject,arguments,lockedBy);
		if (count>0)return true;
		else return false;
	}

	public boolean insertLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy, String controller){
		String query = "insert ignore into moduleLock (id, subject, arguments, expiryTime, lockedBy,controller) values (?, ?, ?, now() + interval ? minute,?,?)";
		logger.debug(query);
		//System.out.println(query);
		int r = getSimpleJdbcTemplate().update(query, id, subject, arguments, expiryMinutes,lockedBy,controller);
		//System.out.println(" r:"+ r);
		return r > 0;
	}

	public void deleteLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ?";
		logger.debug(query);
		//System.out.println(query);
		getSimpleJdbcTemplate().update(query, id, subject, arguments);
	}
	public void deleteLockBy (String id, String subject, String arguments, int lockedBy){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ? and lockedBy = ?";
		logger.debug(query);
		//System.out.println(query);
		getSimpleJdbcTemplate().update(query, id, subject, arguments,lockedBy);
	}
	
	public void deleteExpiredLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ? and expiryTime < now()";
		logger.debug(query);
		//System.out.println(query);
		getSimpleJdbcTemplate().update(query, id,subject, arguments);
	}
	public void updateExpiryTime (String id, String subject, String arguments,int expiryMinutes){
		String query = "update moduleLock set expiryTime=now() + interval ? minute where id = ? and subject = ? and arguments = ?";
		logger.debug(query);
		//System.out.println(query);
		getSimpleJdbcTemplate().update(query,expiryMinutes, id,subject, arguments);
	}
	public String lockedByName (String id, String subject, String arguments){
		String query = "select concat(firstNameHebrew,' ',lastNameHebrew) from moduleLock inner join person where moduleLock.lockedBy =person.id and moduleLock.id = ? and moduleLock.subject = ? and moduleLock.arguments = ?";
		logger.debug(query);
		//System.out.println(query);
		String name= getSimpleJdbcTemplate().queryForObject(query,String.class, id,subject,arguments);
		return name;
	}
	

	public LockedObject getLockedObject (String id, String subject, String arguments){
		String query = "select * from moduleLock where id= ? and subject = ? and arguments = ?";
		logger.debug(query);
		//System.out.println(query);
		LockedObject lockedObject =
				getSimpleJdbcTemplate().queryForObject(query, lockedObjectRowMapper ,id, subject,arguments );
		return lockedObject;
	}	
	public List<LockedObject> lockedObjectsByPerson (int lockedBy){
		String query = "select * from moduleLock where lockedBy=? and expiryTime > now()";
		logger.debug(query);
		//System.out.println(query);
		List<LockedObject> lockedObjectList =
				getSimpleJdbcTemplate().query(query, lockedObjectRowMapper ,lockedBy);
		return lockedObjectList;
	}
	
	ParameterizedRowMapper<LockedObject> lockedObjectRowMapper	= new ParameterizedRowMapper<LockedObject>(){
		public LockedObject mapRow(ResultSet rs, int rowNum) throws SQLException{
			LockedObject lockedObject = new LockedObject();
			lockedObject.setArguments(rs.getString("arguments"));
			lockedObject.setController(rs.getString("controller"));
			lockedObject.setId(rs.getString("id"));
			lockedObject.setLockedBy(rs.getInt("lockedBy"));
			lockedObject.setSubject(rs.getString("subject"));
			lockedObject.setExpiryTime(rs.getTimestamp("expiryTime"));
			return lockedObject;
		}
	};
}
