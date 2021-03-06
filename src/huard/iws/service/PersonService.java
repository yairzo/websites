package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.QueryElementsMap;
import huard.iws.model.Person;

import java.sql.Date;
import java.util.List;

public interface PersonService {

	public Person getPerson(String type, int id, String username);

	public Person getPerson(int id);

	public Person getPerson (String details, String [] fields);

	public Person getPersonByCivilId(String civilId);

	public int getPersonIdBySubscriptionMd5(String subscriptionMd5);
	
	/*public Person getPersonByMopDeskId(int mopDeskId);*/

	public void updatePerson(Person person);

	public int insertPerson(Person person);

	public void deletePerson(int id);

	public Date  getLastLogin(int personId);

	public boolean isYearFirstLogin (int personId);

	public void updateLastLogin(int personId);

	public void insertPersonPrivilege(Person person, String privilege, boolean updateLastLogin, String md5, String subscriptionInitPage);

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
	
	public List<PersonBean> getUsers (String role, boolean enabled, QueryElementsMap queryElements);

	public int getUsersCount (String role, boolean enabled);

	public int getUsersCount (String role, boolean enabled, QueryElementsMap queryElements);
	
	public int getOnBehalfOf(String module,int personId);

	public int getOfficialRepresentative(String module,int id);

	public Person getPersonByFullNameEnglish(String fullNameEnglish);
	
	public void changeCollectPublications(int id);

}
