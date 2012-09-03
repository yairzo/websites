package huard.iws.db;
import huard.iws.bean.PersonBean;

import java.util.List;

import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

public interface PersonPrivilegeDao {

	public List<PersonPrivilege> getPersonPrivileges (int personId);

	public List<Privilege> getAllPrivileges ();

	public void insertPersonPrivilege(int personId, String privilege, String password, String enabled);
	
	public void updatePersonPrivilege(int personId,String password, String enabled);
	
	public String getPrivilegePassword(int personId);

	public int getPrivilegeEnabled(int personId);

	public void deletePersonPrivilege(int privilege);

	public void updateLastAction(PersonBean personId);
	
	public List<Integer> getActivePersons ();

}
