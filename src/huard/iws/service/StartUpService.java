package huard.iws.service;

import huard.iws.db.PersonPrivilegeDao;
import huard.iws.service.PersonDeskService;

public class StartUpService{
	
	
	public void init(){
		personPrivilegeDao.clearLastActionTime();
		personDeskService.init();
	}

	PersonPrivilegeDao personPrivilegeDao;

	public void setPersonPrivilegeDao(PersonPrivilegeDao personPrivilegeDao) {
		this.personPrivilegeDao = personPrivilegeDao;
	}
	
	private PersonDeskService personDeskService;

	public void setPersonDeskService(PersonDeskService personDeskService) {
		this.personDeskService = personDeskService;
	}	
}
