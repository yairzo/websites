package huard.iws.db;

import huard.iws.model.PersonListAttribution;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface PersonAttributionDao {

	public PersonListAttribution getPersonAttribution(int personAttributionId);

	public PersonListAttribution getPersonAttributionByListIdPersonId(int listId, int personId);

	public void updatePersonAttribution(PersonListAttribution personAttribution);

	public void updatePersonAttributionPlaceInList(PersonListAttribution personAttribution);

	public int insertPersonAttribution(PersonListAttribution personAttribution);

	public void deletePersonAttribution(int id);

	public ParameterizedRowMapper<PersonListAttribution> getRowMapper();
}
