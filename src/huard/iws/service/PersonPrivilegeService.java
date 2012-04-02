package huard.iws.service;

import java.util.List;

import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;

public interface PersonPrivilegeService {

	public List<PersonPrivilege> getPersonPrivileges (int personId);

	public List<Privilege> getAllPrivileges ();

	public void insertPersonPrivilege(int personId, String privilege, String encodedPassword);

	public void deletePersonPrivilege(int privilege);
}
