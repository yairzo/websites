package huard.iws.service;

import huard.iws.model.PersonListAttribution;

import java.util.List;

public interface PersonAttributionListService {

	public List<PersonListAttribution> getPersonAttributionsByPersonId(int personId);

	public List<PersonListAttribution> getPersonAttributions(int listId);

	public List<PersonListAttribution> getPersonAttributionsByListId(int listId, String filter);

	public List<PersonListAttribution> getPersonAttributionsByListId( int listId, int orderColumn, String filter);

	public List<PersonListAttribution> getPersonAttributionsByListId( int listId, String order, String filter);

	public void updateConnectedDetailsPersonAttributions(int personId, PersonListAttribution personAttribution);

}
