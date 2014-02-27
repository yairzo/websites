package huard.iws.service;

import java.util.List;

import huard.iws.db.LocksDao;

public class LocksServiceImpl implements LocksService{
	
	final int MAX_ACQUIRE_LOCKS_TRIES = 4;
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller,String locksOn){
		if(!locksOn.equals("true"))
			return true;
		locksDao.deleteExpiredLock (id, subject, arguments);
		if(locksDao.isLockedBySelf(id, subject, arguments, lockedBy)){
			locksDao.updateExpiryTime(id, subject, arguments,expiryMinutes);
			return true;
		}
		return locksDao.insertIgnoreLock(id, subject, arguments, expiryMinutes, lockedBy, controller);
	}
	
	public void updateLock (String id, String subject, String arguments, int expiryMinutes){
		locksDao.updateExpiryTime(id, subject, arguments,expiryMinutes);
	}
	
	public boolean acquireLockList (List<LockedObject> lockedObjects,String locksOn){
		if(!locksOn.equals("true"))
			return true;
		for (LockedObject lockedObject:lockedObjects)
			locksDao.deleteExpiredLock (lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments());
		for (LockedObject lockedObject:lockedObjects){
			if(locksDao.isLockedByOthers(lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments(),lockedObject.getLockedBy()))
				return false;
		}
		//not locked by others, lock now
		for (LockedObject lockedObject:lockedObjects)
			locksDao.insertIgnoreLock(lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments(),lockedObject.getExpiryMinutes(),lockedObject.getLockedBy(),lockedObject.getController());	
		return true;
	}
	
	public void updateLockList (List<LockedObject> lockedObjects){
		for (LockedObject lockedObject:lockedObjects){
			locksDao.updateExpiryTime(lockedObject.getId(), lockedObject.getSubject(), lockedObject.getArguments(),lockedObject.getExpiryMinutes());
		}
	}
	
	public void releaseLock (String id, String subject, String arguments){
		locksDao.deleteLock(id, subject, arguments);
	}
	
	private LocksDao locksDao;


	public void setLocksDao(LocksDao locksDao) {
		this.locksDao = locksDao;
	}

}
