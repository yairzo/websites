package huard.iws.db;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcLocksDao extends SimpleJdbcDaoSupport implements LocksDao{
	
	public boolean insertLock (String id, String subject, String arguments, int expiryMinutes){
		deleteExpiredLock (id, subject, arguments);
		String query = "insert ignore into moduleLock (id, subject, arguments, expiryTime) values (?, ?, ?, now() + interval ? minute)";
		int r = getSimpleJdbcTemplate().update(query, id, subject, arguments, expiryMinutes);
		return r > 0;
	}
	
	public void deleteLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ?";
		System.out.println("aaaaa:"+query);
		getSimpleJdbcTemplate().update(query, id, subject, arguments);
	}
	
	public void deleteExpiredLock (String id, String subject, String arguments){
		String query = "delete from moduleLock where id = ? and subject = ? and arguments = ? and expiryTime < now()";
		getSimpleJdbcTemplate().update(query, id, subject, arguments);
	}
	

}
