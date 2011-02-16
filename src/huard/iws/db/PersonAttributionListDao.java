package huard.iws.db;

import huard.iws.model.PersonListAttribution;

import java.util.List;

public interface PersonAttributionListDao {

	public List<PersonListAttribution> getPersonAttributions(int personId);

	public List<PersonListAttribution> getPersonAttributionsByListId(int listId, String order);

}
