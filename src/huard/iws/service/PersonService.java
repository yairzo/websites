package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.Person;

import java.sql.Date;
import java.util.List;

public interface PersonService {

	public Person getPerson(String type, int id, String username);

	public Person getPerson(int id);

	public Person getPerson (String details, String [] fields);

	public Person getPersonByCivilId(String civilId);

	/*public Person getPersonByMopDeskId(int mopDeskId);*/

	public void updatePerson(Person person);

	public int insertPerson(Person person);

	public void deletePerson(int id);

	public Date  getLastLogin(int personId);

	public boolean isYearFirstLogin (int personId);

	public void updateLastLogin(int personId);

	public void insertPersonPrivilege(Person person, String privilege, boolean updateLastLogin, String md5);

	public void updatePersonPrivilegePassword(Person person, String encodedPassword);
	
	public void updatePersonPrivilegeMD5(Person person, String privilege, boolean updateLastLogin,  String md5);

	public boolean enablePersonPrivilege (String md5);

	public boolean isSubscribed(int personId);
	
	public String getSinglePrivilege(int personId, boolean enabled);
	
	public boolean isAutoSubscribed(int personId);

	public boolean isDisabled(int personId);

	public boolean authenticate(int personId, String encodedPassword);

	public List<PersonBean> getUsers (String role);

	public List<PersonBean> getUsers (String role, boolean enabled);

	public List<PersonBean> getUsers (String role, boolean enabled, String additionalCondition);
	
	public int getOnBehalfOf(String module,int personId);

	public int getImpersonator(String module,int personId);

}
