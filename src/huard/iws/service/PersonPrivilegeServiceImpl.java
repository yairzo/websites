package huard.iws.service;

import java.util.List;
import huard.iws.bean.PersonBean;
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
	public void insertPersonPrivilege(int personId, String privilege, String password, String enabled){
		personPrivilegeDao.insertPersonPrivilege(personId,privilege,password, enabled);
	}
	
	public void updatePersonPrivilege(int personId,String password, String enabled){
		personPrivilegeDao.updatePersonPrivilege(personId, password, enabled);
	}
	
	public String getPrivilegePassword(int personId){
		return personPrivilegeDao.getPrivilegePassword(personId);
	}
	
	public int getPrivilegeEnabled(int personId){
		return personPrivilegeDao.getPrivilegeEnabled(personId);
	}

	public void deletePersonPrivilege(int privilege){
		personPrivilegeDao.deletePersonPrivilege(privilege);
	}
	
	public void updateLastAction(PersonBean userPersonBean){
		personPrivilegeDao.updateLastAction(userPersonBean);
	}

	public List<PersonPrivilege> getActivePersons (){
		return personPrivilegeDao.getActivePersons();
	}

	PersonPrivilegeDao personPrivilegeDao;

	public void setPersonPrivilegeDao(PersonPrivilegeDao personPrivilegeDao) {
		this.personPrivilegeDao = personPrivilegeDao;
	}


}
