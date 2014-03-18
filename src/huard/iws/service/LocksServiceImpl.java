package huard.iws.service;

import java.sql.Timestamp;
import java.util.List;

import huard.iws.db.LocksDao;
import huard.iws.service.LocksService.LockedObject;

public class LocksServiceImpl implements LocksService{
	
	final int MAX_ACQUIRE_LOCKS_TRIES = 4;
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller){
		String locksOn = configurationService.getConfigurationString("iws", "lock");
		if(!locksOn.equals("true"))
			return true;
		locksDao.deleteExpiredLock (id, subject, arguments);
		if(locksDao.isLockedBySelf(id, subject, arguments, lockedBy)){
			locksDao.updateExpiryTime(id, subject, arguments,expiryMinutes);
			return true;
		}
		return locksDao.insertLock(id, subject, arguments, expiryMinutes, lockedBy, controller);
	}
	
	public void updateLock (String id, String subject, String arguments, int expiryMinutes){
		locksDao.updateExpiryTime(id, subject, arguments,expiryMinutes);
	}
	
	public boolean acquireLockList (List<LockedObject> lockedObjects){
		String locksOn = configurationService.getConfigurationString("iws", "lock");
		if(!locksOn.equals("true"))
			return true;
		for (LockedObject lockedObject:lockedObjects)
			locksDao.deleteExpiredLock (lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments());
		Boolean allAcquired=true;
		for (LockedObject lockedObject:lockedObjects){
			boolean success=acquireLock(lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments(),lockedObject.getExpiryMinutes(),lockedObject.getLockedBy(),lockedObject.getController());	
			if (success==false){
				allAcquired=false;
				break;
			}
		}
		if(!allAcquired){
			for (LockedObject lockedObject:lockedObjects)
				locksDao.deleteLockBy(lockedObject.getId(),lockedObject.getSubject(),lockedObject.getArguments(),lockedObject.getLockedBy());
			return false;
		}
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
	
	public String lockedByName (String id, String subject, String arguments){
		return locksDao.lockedByName(id, subject, arguments);
	}
	
	public List<LockedObject> lockedObjectsByPerson(int personId){
		return locksDao.lockedObjectsByPerson(personId);
	}

	public LockedObject getLockedObject(String id, String subject, String arguments){
		return locksDao.getLockedObject(id, subject, arguments);
	}

	private LocksDao locksDao;

	public void setLocksDao(LocksDao locksDao) {
		this.locksDao = locksDao;
	}
	protected ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
