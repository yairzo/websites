package huard.iws.service;

import java.util.List;

import huard.iws.db.PersonPrivilegeDao;
import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

public class PersonPrivilegeServiceImpl implements PersonPrivilegeService{

	public List<PersonPrivilege> getPersonPrivileges (int personId){
		return personPrivilegeDao.getPersonPrivileges(personId);
	}

	public List<Privilege> getAllPrivileges (){
		return personPrivilegeDao.getAllPrivileges();
	}
	public void insertPersonPrivilege(int personId, String privilege, String password){
		personPrivilegeDao.insertPersonPrivilege(personId,privilege,password);
	}
	public void deletePersonPrivilege(int privilege){
		personPrivilegeDao.deletePersonPrivilege(privilege);
	}


	PersonPrivilegeDao personPrivilegeDao;

	public void setPersonPrivilegeDao(PersonPrivilegeDao personPrivilegeDao) {
		this.personPrivilegeDao = personPrivilegeDao;
	}


}
