package huard.iws.db;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcLocksDao extends SimpleJdbcDaoSupport implements LocksDao{
	
	public boolean isLockedBySelf (String id, String subject, String arguments, int lockedBy){
		String query = "select count(*) from moduleLock where id = ? and subject = ? and arguments = ? and lockedBy=?";
		logger.debug(query);
		System.out.println(query);
		int count= getSimpleJdbcTemplate().queryForInt(query, id,subject,arguments,lockedBy);
		if (count>0)return true;
		else return false;
	}
	
	public boolean isLockedByOthers (String id, String subject, String arguments, int lockedBy){
		String query = "select count(*) from moduleLock where id = ? and subject = ? and arguments = ? and lockedBy<>?";
		logger.debug(query);
		System.out.println(query);
		int count= getSimpleJdbcTemplate().queryForInt(query, id,subject,arguments,lockedBy);
		if (count>0)return true;
		else return false;
	}

	public boolean insertIgnoreLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy, String controller){
		String query = "insert ignore into moduleLock (id, subject, arguments, expiryTime, lockedBy,controller) values (?, ?, ?, now() + interval ? minute,?,?)";
		logger.debug(query);
		System.out.println(query);
		int r = getSimpleJdbcTemplate().update(query, id, subject, arguments, expiryMinutes,lockedBy,controller);
		return r > 0;
	}

	public void deleteLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ?";
		logger.debug(query);
		System.out.println(query);
		getSimpleJdbcTemplate().update(query, id, subject, arguments);
	}
	
	public void deleteExpiredLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ? and expiryTime < now()";
		logger.debug(query);
		System.out.println(query);
		getSimpleJdbcTemplate().update(query, id,subject, arguments);
	}
	public void updateExpiryTime (String id, String subject, String arguments,int expiryMinutes){
		String query = "update moduleLock set expiryTime=now() + interval ? minute where id = ? and subject = ? and arguments = ?";
		logger.debug(query);
		System.out.println(query);
		getSimpleJdbcTemplate().update(query,expiryMinutes, id,subject, arguments);
	}
}
