package huard.iws.service;



public interface LocksService {
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes);
	
}
