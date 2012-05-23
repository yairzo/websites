package huard.iws.service;

import java.util.List;

import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

public interface PersonPrivilegeService {

	public List<PersonPrivilege> getPersonPrivileges (int personId);

	public List<Privilege> getAllPrivileges ();

	public void insertPersonPrivilege(int personId, String privilege, String encodedPassword, String enabled);
	
	public void updatePersonPrivilege(int personId,String password, String enabled);

	public String getPrivilegePassword(int personId);
	
	public int getPrivilegeEnabled(int personId);
	
	public void deletePersonPrivilege(int privilege);
}
