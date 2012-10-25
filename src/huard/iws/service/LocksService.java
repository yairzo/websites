package huard.iws.service;



public interface LocksService {
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes);
	
	public void releaseLock (String id, String subject, String arguments);
	
}
