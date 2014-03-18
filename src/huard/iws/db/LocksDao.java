package huard.iws.db;

import huard.iws.service.LocksService.LockedObject;

import java.sql.Timestamp;
import java.util.List;

public interface LocksDao {
	
	public boolean insertLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller);

	public boolean isLockedBySelf (String id, String subject, String arguments, int lockedBy);
	
	public boolean isLockedByOthers (String id, String subject, String arguments, int lockedBy);

	public void deleteLock (String id, String subject, String arguments);

	public void deleteLockBy (String id, String subject, String arguments, int lockedBy);

	public void deleteExpiredLock (String id, String subject, String arguments);		
	
	public void updateExpiryTime (String id, String subject, String arguments, int expiryMinutes);
	
	public String lockedByName (String id, String subject, String arguments);

	public List<LockedObject> lockedObjectsByPerson(int personId);
	
	public LockedObject getLockedObject(String id, String subject, String arguments);

}
