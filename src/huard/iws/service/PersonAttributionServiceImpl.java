package huard.iws.service;

import huard.iws.db.PersonAttributionDao;
import huard.iws.db.PersonDao;
import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;

public class PersonAttributionServiceImpl implements PersonAttributionService{

	public PersonListAttribution getPersonAttribution ( String type, int id, String username){
		return personAttributionDao.getPersonAttribution(id);
	}

	public PersonListAttribution getPersonAttributionFromPerson(String type, int personId, String username){
		Person person = personDao.getPerson(personId);
		PersonListAttribution personAttribution = person.toPersonAttribution();
		return personAttribution;
	}

	public PersonListAttribution getPersonAttributionByListIdPersonId(String type, int listId, int personId, String username){
		return personAttributionDao.getPersonAttributionByListIdPersonId(listId,personId);
	}

	public void updatePersonAttribution(PersonListAttribution personAttribution){
		personAttributionDao.updatePersonAttribution(personAttribution);
	}

	public void updatePersonAttributionPlaceInList(PersonListAttribution personAttribution){
		personAttributionDao.updatePersonAttributionPlaceInList(personAttribution);
	}

	public int insertPersonAttribution(PersonListAttribution personAttribution){
		return personAttributionDao.insertPersonAttribution(personAttribution);
	}

	public void deletePersonAttribution(int id){
		personAttributionDao.deletePersonAttribution(id);
	}

	PersonAttributionDao personAttributionDao;

	public void setPersonAttributionDao(PersonAttributionDao personAttributionDao) {
		this.personAttributionDao = personAttributionDao;
	}

	PersonDao personDao;

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}





}
