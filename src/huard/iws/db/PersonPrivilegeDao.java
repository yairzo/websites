package huard.iws.db;

import java.util.List;

import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

public interface PersonPrivilegeDao {

	public List<PersonPrivilege> getPersonPrivileges (int personId);

	public List<Privilege> getAllPrivileges ();

	public void insertPersonPrivilege(int personId, String privilege, String password);
	
	public void deletePersonPrivilege(int privilege);


}
