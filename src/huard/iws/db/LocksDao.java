package huard.iws.db;

public interface LocksDao {
	
	public boolean insertLock (String id, String subject, String arguments, int expiryMinutes);
	
	public void deleteLock (String id, String subject, String arguments);
	
	public void deleteExpiredLock (String id, String subject, String arguments);		

}
