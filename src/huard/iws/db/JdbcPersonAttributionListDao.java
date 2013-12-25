package huard.iws.db;

import huard.iws.model.PersonListAttribution;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPersonAttributionListDao extends SimpleJdbcDaoSupport implements PersonAttributionListDao {

	//private static final Logger logger = Logger.getLogger(JdbcPersonAttributionListDao.class);

	public List<PersonListAttribution> getPersonAttributions(int personId) {
		String personSelect = "select * from personAttribution "+
		"where personId= ?";
		logger.debug(personSelect);
		List<PersonListAttribution> personAttributions =
			getSimpleJdbcTemplate().query(personSelect, personAttributionDao.getRowMapper(), personId);
		return personAttributions;
    }

	public List<PersonListAttribution> getPersonAttributionsByListId(int listId, String order) {
		String personSelect = "select * from personAttribution,person "+
		"where personAttribution.personId = person.id and listId= ? order by "+order+";";
		logger.debug(personSelect);
		List<PersonListAttribution> personAttributions =
			getSimpleJdbcTemplate().query(personSelect,personAttributionDao.getRowMapper(),listId);
		return personAttributions;
    }

	PersonAttributionDao personAttributionDao;

	public void setPersonAttributionDao(PersonAttributionDao personAttributionDao) {
		this.personAttributionDao = personAttributionDao;
	}
}
