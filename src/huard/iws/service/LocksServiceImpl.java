package huard.iws.service;

import huard.iws.db.LocksDao;

public class LocksServiceImpl implements LocksService{
	
	final int MAX_ACQUIRE_LOCKS_TRIES = 4;
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes){
		for (int counter = 0; counter < MAX_ACQUIRE_LOCKS_TRIES; counter ++){
			if (locksDao.insertLock(id, subject, arguments, expiryMinutes))
				return true;
			try{
				Thread.sleep(1000);
			}
			catch (InterruptedException ie){
				continue;
			}	
		}
		return false;
	}
	
	public void releaseLock (String id, String subject, String arguments){
		locksDao.deleteLock(id, subject, arguments);
	}

	
	private LocksDao locksDao;


	public void setLocksDao(LocksDao locksDao) {
		this.locksDao = locksDao;
	}

}
