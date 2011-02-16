package huard.iws.service;

import huard.iws.model.PersonListAttribution;

import java.util.List;

public interface PersonAttributionListService {

	public List<PersonListAttribution> getPersonAttributionsByPersonId(int personId);

	public List<PersonListAttribution> getPersonAttributions(int listId);

	public List<PersonListAttribution> getPersonAttributionsByListId(int listId);

	public List<PersonListAttribution> getPersonAttributionsByListId( int listId, int orderColumn);

	public List<PersonListAttribution> getPersonAttributionsByListId( int listId, String order);

	public void updateConnectedDetailsPersonAttributions(int personId, PersonListAttribution personAttribution);

}
