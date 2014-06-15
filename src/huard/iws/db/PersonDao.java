package huard.iws.db;


import huard.iws.model.Person;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PersonDao {

	public Person getPerson(int id);

	public Person getPersonByCivilId(String civilId);

	public int getPersonIdBySubscriptionMd5(String subscriptionMd5);

	/*public Person getPersonByMopDeskId(int mopDeskId);*/

	public void updatePerson(Person person);

	public int insertPerson(Person person);

	public void deletePerson(int id);

	public Date  getLastLogin(int personId);

	public void updateLastLogin(int personId);

	public List<Person> getPersons(ListView lv, SearchCreteria search);

	public int countPersons(ListView lv, SearchCreteria search);
	
	public List<Person> getPersons(ListView lv);

	public List<Person> getPersons();

	public List<Person> getPersons(List<Integer> ids);

	public List<Person> getPersons(String role);

	public void insertPersonPrivilege(Person person, String priviledge, boolean updateLastLogin, String password, String subscriptionInitPage);

	public void updatePersonPrivilegePassowrd(Person person, String encodedPassword);
	
	public void updatePersonPrivilegeMD5(Person person, String privilege, boolean updateLastLogin, String md5);

	public boolean isSubscribed(int personId, boolean enabled);
	
	public String getSinglePrivilege(int personId, boolean enabled);
	
	public boolean isAutoSubscribed(int personId, boolean enabled);

	public boolean enablePersonPrivilege(String md5);

	public boolean authenticate(int personId, String pasword);

	public List<Person> getUsers (String role, boolean enabled);
	
	public List<Person> getUsers (String role, boolean enabled, QueryElementsMap queryElements);
	
	public int getUsersCount (String role, boolean enabled);
	
	public int getUsersCount (String role, boolean enabled, QueryElementsMap queryElements);

	public Map<Integer, Timestamp> getPersonsLastLogins();
	
	public int countPerson();
	
	public List<Person> getConferenceResearchers();
	
	public int getOnBehalfOf(String module,int personId);

	public int getOfficialRepresentative(String module,int id);

	public Person getPersonByFullNameEnglish(String fullNameEnglish);
	
}
