package huard.iws.service;

import huard.iws.db.PersonPrivilegeDao;

public class StartUpService{
	
	
	public void init(){
		personPrivilegeDao.clearLastActionTime();
	}

	PersonPrivilegeDao personPrivilegeDao;

	public void setPersonPrivilegeDao(PersonPrivilegeDao personPrivilegeDao) {
		this.personPrivilegeDao = personPrivilegeDao;
	}

}
