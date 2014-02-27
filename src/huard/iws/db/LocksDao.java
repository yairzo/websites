package huard.iws.db;

public interface LocksDao {
	
	public boolean insertIgnoreLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller);

	public boolean isLockedBySelf (String id, String subject, String arguments, int lockedBy);
	
	public boolean isLockedByOthers (String id, String subject, String arguments, int lockedBy);

	public void deleteLock (String id, String subject, String arguments);
	
	public void deleteExpiredLock (String id, String subject, String arguments);		
	
	public void updateExpiryTime (String id, String subject, String arguments, int expiryMinutes);
}
