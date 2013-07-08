package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PersonDao;
import huard.iws.model.Person;
import huard.iws.util.DateUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonServiceImpl implements PersonService {

	//private static final Logger logger = Logger.getLogger(PersonServiceImpl.class);

	public Person getPerson(String type, int id, String username){
		return personDao.getPerson(id);
	}

	public Person getPerson(int id){
		return personDao.getPerson(id);
	}
	public int getOnBehalfOf(String module,int personId){
		return personDao.getOnBehalfOf(module,personId);
	}
	public int getOfficialRepresentative(String module,int id){
		return personDao.getOfficialRepresentative(module,id);
	}
	public Person getPerson (String details, String [] fields){
		List<Person> persons = personListService.getPersons();
		Map <String, Person> personsMap = new HashMap<String, Person>();
		for (Person person : persons){
			PersonBean personBean = new PersonBean(person);
			String key = personBean.toString(fields);
			personsMap.put(key,person);
		}
		return personsMap.get(details);
	}

	public Person getPersonByCivilId(String username){
		return personDao.getPersonByCivilId( username );
	}

	public int getPersonIdBySubscriptionMd5(String subscriptionMd5){
		return personDao.getPersonIdBySubscriptionMd5( subscriptionMd5 );
	}

	/*public Person getPersonByMopDeskId(int mopDeskId){
		return personDao.getPersonByMopDeskId(mopDeskId);
	}*/

	public void updatePerson(Person person){
		personDao.updatePerson(person);
	}

	public int insertPerson(Person person){
		return personDao.insertPerson(person);
	}

	public void deletePerson(int id){
		personDao.deletePerson(id);
	}

	public Date getLastLogin(int personId){
		return personDao.getLastLogin(personId);
	}

	public void updateLastLogin(int personId){
		personDao.updateLastLogin(personId);

	}

	public boolean isYearFirstLogin(int personId){
		Date lastLogin = getLastLogin(personId);
		if (lastLogin == null) return true;
		return ! DateUtils.isSameYear(lastLogin);
	}

	public void insertPersonPrivilege(Person person, String privilege, boolean updateLastLogin, String password){
		personDao.insertPersonPrivilege(person, privilege, updateLastLogin, password);
	}
	
	public void updatePersonPrivilegePassword(Person person, String encodedPassword){
		personDao.updatePersonPrivilegePassowrd(person, encodedPassword);
	}

	public void updatePersonPrivilegeMD5(Person person, String privilege, boolean updateLastLogin, String md5){
		personDao.updatePersonPrivilegeMD5(person, privilege, updateLastLogin, md5);
	}

	public boolean enablePersonPrivilege (String md5){
		return personDao.enablePersonPrivilege(md5);
	}

	public boolean isSubscribed(int personId){
		return personDao.isSubscribed(personId, true);
	}
	
	public String getSinglePrivilege(int personId, boolean enabled){
		return personDao.getSinglePrivilege(personId, enabled);
	}
	
	public boolean isAutoSubscribed(int personId){
		return personDao.isAutoSubscribed(personId, true);
	}

	public boolean isDisabled(int personId){
		return personDao.isSubscribed(personId, false);
	}

	public boolean authenticate(int personId, String encodedPassword){
		return personDao.authenticate(personId, encodedPassword);
	}

	public List<PersonBean> getUsers (String role, boolean enabled, String additionaCondition, String joinTable){
		List<Person> usersPersons = personDao.getUsers(role, enabled, additionaCondition,joinTable);
		return applyLastLogins(usersPersons);
	}

	public List<PersonBean> getUsers (String role, boolean enabled){
		List<Person> usersPersons = personDao.getUsers(role, enabled);
		return applyLastLogins(usersPersons);
	}

	public List<PersonBean> getUsers (String role){
		List<Person> usersPersons = personDao.getUsers(role, true);
		return applyLastLogins(usersPersons);
	}

	private List<PersonBean> applyLastLogins(List<Person> usersPersons){
		Map<Integer, Timestamp> personsLastLoginsMap = personDao.getPersonsLastLogins();
		List<PersonBean> usersPersonBeans = new ArrayList<PersonBean>();
		for (Person person: usersPersons){
			PersonBean personBean = new PersonBean(person);
			personBean.setLastLogin(personsLastLoginsMap.get(person.getId()));
			usersPersonBeans.add(personBean);
		}
		return usersPersonBeans;
	}
	
	public Person getPersonByFullNameEnglish(String fullNameEnglish){
		return personDao.getPersonByFullNameEnglish(fullNameEnglish);
	}


	private PersonDao personDao;


	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	private PersonListService personListService;


	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

}
