package huard.iws.service;

import huard.iws.model.PersonListAttribution;

public interface PersonAttributionService {

	public PersonListAttribution getPersonAttribution(String type, int id, String username);

	public PersonListAttribution getPersonAttributionFromPerson(String type, int personId, String username);

	public PersonListAttribution getPersonAttributionByListIdPersonId(String type, int listId, int personId, String username);

	public void updatePersonAttribution(PersonListAttribution personAttribution);

	public void updatePersonAttributionPlaceInList(PersonListAttribution personAttribution);

	public int insertPersonAttribution(PersonListAttribution personAttribution);

	public void deletePersonAttribution(int id);

}
