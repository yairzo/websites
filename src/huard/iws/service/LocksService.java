package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.service.LocksService.LockedObject;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;

import java.util.List;
import java.sql.Timestamp;



public interface LocksService {
	
	public boolean acquireLock (String id, String subject, String arguments, int expiryMinutes, int lockedBy,String controller);
	
	public void updateLock (String id, String subject, String arguments, int expiryMinutes);
	
	public boolean acquireLockList (List<LockedObject> lockedObjects);

	public void updateLockList (List<LockedObject> lockedObjects);

	public void releaseLock (String id, String subject, String arguments);
	
	public String lockedByName (String id, String subject, String arguments);

	public List<LockedObject> lockedObjectsByPerson(int personId);

	public LockedObject getLockedObject(String id, String subject, String arguments);

	public class LockedObject{
		String id;
		String subject;
		String arguments;
		int lockedBy;
		int expiryMinutes;
		String controller;
		Timestamp expiryTime;
		
		public LockedObject(){
			this.id="";
			this.subject="";
			this.arguments="";
			this.expiryMinutes=0;
			this.lockedBy=0;
			this.controller="";
			//this.expiryTime=new Timestamp(new java.util.Date().getTime());
		}
		
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
		public Timestamp getExpiryTime() {
			return expiryTime;
		}
		public void setExpiryTime(Timestamp expiryTime) {
			this.expiryTime = expiryTime;
		}
		public String getExpiryTimeFormatted() {
			return DateUtils.formatDate(expiryTime.getTime(),"dd/MM/yyyy HH:mm:ss");
		}
		public String getLockedByName() {
			PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
			PersonBean lockedByPerson = new PersonBean(personService.getPerson(this.lockedBy));
			return lockedByPerson.getDegreeFullNameHebrew();
		}
	}
	
	
}
