package huard.iws.service;

import java.util.List;



public interface LocksService {
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller,String locksOn);
	
	public void updateLock (String id, String subject, String arguments, int expiryMinutes);
	
	public boolean acquireLockList (List<LockedObject> lockedObjects,String locksOn);

	public void updateLockList (List<LockedObject> lockedObjects);

	public void releaseLock (String id, String subject, String arguments);

	public class LockedObject{
		String id;
		String subject;
		String arguments;
		int lockedBy;
		int expiryMinutes;
		String controller;

		public LockedObject(String id,String subject,String arguments,int expiryMinutes,int lockedBy,String controller){
			this.id=id;
			this.subject=subject;
			this.arguments=arguments;
			this.expiryMinutes=expiryMinutes;
			this.lockedBy=lockedBy;
			this.controller=controller;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getArguments() {
			return arguments;
		}
		public void setArguments(String arguments) {
			this.arguments = arguments;
		}
		
		public int getLockedBy() {
			return lockedBy;
		}
		public void setLockedBy(int lockedBy) {
			this.lockedBy = lockedBy;
		}

		public int getExpiryMinutes() {
			return expiryMinutes;
		}
		public void setExpiryMinutes(int expiryMinutes) {
			this.expiryMinutes = expiryMinutes;
		}
		
		public String getController() {
			return controller;
		}
		public void setController(String controller) {
			this.controller = controller;
		}

	}
	
}
