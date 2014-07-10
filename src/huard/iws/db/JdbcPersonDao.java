package huard.iws.db;

import huard.iws.db.QueryElementsMap.QueryValidKey;
import huard.iws.model.Person;
import huard.iws.util.BaseUtils;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class JdbcPersonDao extends SimpleJdbcDaoSupport implements PersonDao {
	private static final Logger logger = Logger.getLogger(JdbcPersonDao.class);

	public Person getPerson(int id){
		try{
		String query = "select * from person where id=?";
		logger.debug(query);
		Person person =
			getSimpleJdbcTemplate().queryForObject(query, personRowMapper,	id);		
		applyPersonSubjectIds(person);
		return person;
		}
		catch(Exception e){
			return new Person();
		}
	}
	
	public int getOnBehalfOf(String module, int id){
		try{
			String query = "select personId from officialRepresentativePerson where module=? and officialRepresentativePersonId=?";
			logger.debug(query);
			return getSimpleJdbcTemplate().queryForInt(query, module,id);		
		}
		catch(Exception e){
			return id;
		}
	}
	
	public int getOfficialRepresentative(String module, int id){
		try{
			String query = "select officialRepresentativePersonId from officialRepresentativePerson where module=? and personId=?";
			logger.debug(query);
			return getSimpleJdbcTemplate().queryForInt(query, module,id);		
		}
		catch(Exception e){
			return id;
		}
	}

	private void applyPersonSubjectIds(Person person){
		String query = "select * from subjectToPerson where personId = ?";
		logger.debug(query);
		List<Integer> subjectsIds =  getSimpleJdbcTemplate().query(query, subjectToPersonRowMapper, person.getId());
		person.setSubjectsIds(subjectsIds);
	}

	private void applyPersonSubjectIds(List<Person> persons){
		for (Person person: persons){
			applyPersonSubjectIds(person);
		}
	}


	public Person getPersonByCivilId(String civilId){
		String query = "select * from person where civilId=?";
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, personRowMapper,	civilId);
		Person person = null;
		if (persons.size() > 0)
			person = persons.get(0);
		if (person !=null){
			applyPersonSubjectIds(person);
		}
		return person;
	}
	public int getPersonIdBySubscriptionMd5(String subscriptionMd5){
		String query = "select personId from personPrivilege where subscriptionMd5=? limit 1;";
		logger.debug(query);
		try{
			return getSimpleJdbcTemplate().queryForInt(query, subscriptionMd5);
		}
		catch(Exception e){
			return 0;
		}
	}

// The method was changed for demo please fix it
	/*public Person getPersonByMopDeskId(int mopDeskId){
		String query = "select * from person where id=?";
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, rowMapper,	2591);
		return persons.get(0);
	}*/

	private ParameterizedRowMapper<Person> personRowMapper = new PersonRowMapper();

	private ParameterizedRowMapper<Integer> subjectToPersonRowMapper = new ParameterizedRowMapper<Integer>(){
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
            Integer subjectId = rs.getInt("subjectId");
            return subjectId;
		}
	};



	private ParameterizedRowMapper<Person> getPersonRowMapper() {
		return personRowMapper;
	}

	public void updatePerson(Person person){
		String query = "update person set firstNameHebrew = ? , lastNameHebrew = ?," +
				" degreeHebrew = ?, civilId = ?, firstNameEnglish = ?, lastNameEnglish = ?,"+
				" degreeEnglish = ?, phone = ?, fax = ?, email = ?, department = ?, facultyId = ?, address = ?,"+
				" homePhone = ?, cellPhone = ?, roomNumber = ?, researchEnabled = ?, preferedLocaleId = ?,"+
				" academicTitle = ?, websiteUrl = ?, campusId = ?," +
				" postReceiveDays = ?, postReceiveHour = ?, postReceiveImmediately = ?, readsUTF8Mails = ?,receivePosts=?,"+
				" imageUrl=?, collectPublications=?, lastSync=? where id = ?";
		logger.debug (query);
		getSimpleJdbcTemplate().update(query,
				person.getFirstNameHebrew() ,
				person.getLastNameHebrew(),
				person.getDegreeHebrew(),
				person.getCivilId(),
				person.getFirstNameEnglish(),
				person.getLastNameEnglish(),
				person.getDegreeEnglish(),
				person.getPhone(),
				person.getFax(),
				person.getEmail(),
				person.getDepartment(),
				person.getFacultyId(),
				person.getAddress(),
				person.getHomePhone(),
				person.getCellPhone(),
				person.getRoomNumber(),
				person.isResearchEnabled(),
				person.getPreferedLocaleId(),
				person.getAcademicTitle(),
				person.getWebsiteUrl(),
				person.getCampusId(),

				BaseUtils.getString(person.getPostReceiveDays()),
				person.getPostReceiveHour(),
				person.isPostReceiveImmediately(),
				person.isReadsUTF8Mails(),
				person.isReceivePosts(),
				person.getImageUrl(),
				person.isCollectPublications(),
				person.getLastSync(),
				person.getId() );
		updatePersonSubjectIds(person);
	}

	public int insertPerson(Person person){
		final String query = "insert person set firstNameHebrew = ? , lastNameHebrew = ?," +
		" degreeHebrew = ?, civilId = ?, firstNameEnglish = ?, lastNameEnglish = ?,"+
		" degreeEnglish = ?, phone = ?, fax = ?, email = ?, department = ?, facultyId = ?, address = ?,"+
		" homePhone = ?, cellPhone = ?, roomNumber = ?, researchEnabled = ?, preferedLocaleId = ?,"+
		" academicTitle = ?, websiteUrl = ?, campusId = ?," +
		" postReceiveDays = ?, postReceiveHour = ?, postReceiveImmediately = ?, readsUTF8Mails = ?,receivePosts=?,"+
		" imageUrl=?, collectPublications=?, lastSync=? ;";
		logger.debug(query);
		final String firstNameHebrew = person.getFirstNameHebrew();
		final String lastNameHebrew = person.getLastNameHebrew();
		final String degreeHebrew = person.getDegreeHebrew();
		final String civilId = person.getCivilId();
		final String firstNameEnglish = person.getFirstNameEnglish();
		final String lastNameEnglish = person.getLastNameEnglish();
		final String degreeEnglish = person.getDegreeEnglish();
		final String phone = person.getPhone();
		final String fax = person.getFax();
		final String email = person.getEmail();
		final String department = person.getDepartment();
		final int facultyId = person.getFacultyId();
		final String address = person.getAddress();
		final String homePhone = person.getHomePhone();
		final String cellPhone = person.getCellPhone();
		final String roomNumber = person.getRoomNumber();
		final boolean researchEnabled = person.isResearchEnabled();
		final String preferedLocaleId = person.getPreferedLocaleId();
		final String academicTitle = person.getAcademicTitle();
		final String websiteUrl = person.getWebsiteUrl();
		final int campusId = person.getCampusId();
		final String postReceiveDays = BaseUtils.getString(person.getPostReceiveDays());
		final int postReceiveHour = person.getPostReceiveHour();
		final boolean postReceiveImmediately = person.isPostReceiveImmediately();
		final boolean readsUTF8Mails = person.isReadsUTF8Mails();
		final boolean receivePosts = person.isReceivePosts();
		final String imageUrl = person.getImageUrl();
		final boolean collectPublications = person.isCollectPublications();
		final Timestamp lastSync = person.getLastSync();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
				new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setString(1, firstNameHebrew);
					ps.setString(2, lastNameHebrew);
					ps.setString(3, degreeHebrew);
					ps.setString(4, civilId);
					ps.setString(5, firstNameEnglish);
					ps.setString(6, lastNameEnglish);
					ps.setString(7, degreeEnglish);
					ps.setString(8, phone);
					ps.setString(9, fax);
					ps.setString(10, email);
					ps.setString(11, department);
					ps.setInt(12, facultyId);
					ps.setString(13, address);
					ps.setString(14, homePhone);
					ps.setString(15, cellPhone);
					ps.setString(16, roomNumber);
					ps.setBoolean(17, researchEnabled);
					ps.setString(18, preferedLocaleId);
					ps.setString(19, academicTitle);
					ps.setString(20, websiteUrl);
					ps.setInt(21, campusId);

					ps.setString(22,postReceiveDays);
					ps.setInt(23, postReceiveHour);
					ps.setBoolean(24, postReceiveImmediately);
					ps.setBoolean(25, readsUTF8Mails);
					ps.setBoolean(26,receivePosts);
					ps.setString(27,imageUrl);
					ps.setBoolean(28,collectPublications);
					ps.setTimestamp(29,lastSync);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey().intValue();
	}

	private void updatePersonSubjectIds(Person person){
		String query = "delete from subjectToPerson where personId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query,person.getId());

		if (person.getSubjectsIds() != null){
			for (Integer subjectId: person.getSubjectsIds()){
				query  = "insert subjectToPerson set personId = ?, subjectId = ?";
				getSimpleJdbcTemplate().update(query,person.getId(), subjectId);
			}
		}
	}

	public void deletePerson(int id){
		String query = "delete from person where id =?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, id);
		logger.debug(query);
		query = "delete from subjectToPerson where personId = ?";
		getSimpleJdbcTemplate().update(query, id);
	}

	public java.sql.Date getLastLogin(int personId){
		String query = "select max(lastLogin) from personPrivilege where personId = ?;";
		logger.debug(query);
		Date lastLogin = getSimpleJdbcTemplate().queryForObject(query, Date.class, personId);
		return lastLogin;
	}

	public void updateLastLogin(int personId){
		String updateString = "update personPrivilege set lastLogin=now() where personId = ?;";
		logger.debug(updateString);
		getSimpleJdbcTemplate().update(updateString, personId);
	}

	public List<Person> getPersons(ListView lv, SearchCreteria search) {

		String query = "select * from person";
		//get where clause by search critieria
		query += getPersonsWhereClause(search);

		query += " group by civilId order by "+lv.getOrderBy();
		query += " limit "+ (lv.getPage()-1) * lv.getRowsInPage() + "," + lv.getRowsInPage();

		logger.debug(query);
		
		boolean [] searchPhraseValid = validateSearch(search);
		
		Object [] params = {search.getSearchPhrase(),
				search.getSearchPhrase(),
				search.getSearchPhrase(),
				"%"+search.getSearchPhrase()+"%",
				"%"+search.getSearchPhrase()+"%",
				search.getSearchPhrase()};
			
		if (!searchPhraseValid[0])
			params = new String [0];
		
		List<Person> persons = getSimpleJdbcTemplate().query(query, getPersonRowMapper(),
					params);
		applyPersonSubjectIds(persons);
		return persons;
    }
	
	public int countPersons(ListView lv, SearchCreteria search) {

		String query = "select count(*) from person";
		//get where clause by search criteria
		query += getPersonsWhereClause(search);
		
		
		boolean [] searchPhraseValid = validateSearch(search);
		Object [] params = {search.getSearchPhrase(),
					search.getSearchPhrase(),
					search.getSearchPhrase(),
					"%"+search.getSearchPhrase()+"%",
					"%"+search.getSearchPhrase()+"%",
					search.getSearchPhrase()};
		
		if (!searchPhraseValid[0])
			params = new String [0];			
		
		int r = getSimpleJdbcTemplate().queryForInt(query,
				params);

		logger.debug(query);
		return r;
    }

	public String getPersonsWhereClause(SearchCreteria search){
		boolean [] searchPhraseValid = validateSearch(search);
		String whereClause="";
		// seaching by a search field
		if (searchPhraseValid [1])
			whereClause += " inner join personPrivilege on person.id = personPrivilege.personId";

		if (searchPhraseValid [0] || searchPhraseValid [1])
			whereClause += " where";

		if (searchPhraseValid [1]){
			whereClause += " privilege = '" + search.getRoleFilter() + "'";
			if (searchPhraseValid [0])
				whereClause += " and";
		}
		if (searchPhraseValid [0])
			whereClause +=  " (concat(lastNameHebrew, ' ', firstNameHebrew, ' ', email) = ?"
				+ " or concat(lastNameHebrew, ' ', firstNameHebrew) = ? "
				+ " or concat(firstNameHebrew, ' ', lastNameHebrew) = ?"
				+ " or lastNameHebrew like ? "
				+ " or firstNameHebrew like ? "
				+ " or email = ?) ";
		
		if(searchPhraseValid [0] || searchPhraseValid [1])
			whereClause += " and";
		else
			whereClause +=" where";
		whereClause += " (concat(lastNameHebrew,firstNameHebrew)<>'')";
		
		return whereClause;
	}
	
	
	private boolean [] validateSearch (SearchCreteria search){
		boolean [] searchPhraseValid = new boolean [2];
		searchPhraseValid [0] = search != null && ! search.getSearchPhrase().isEmpty();
		searchPhraseValid [1] = search != null && ! search.getRoleFilter().isEmpty();
		return searchPhraseValid;
	}


	public List<Person> getPersons(ListView lv) {
		String query = "select * from person";
		query = query.concat(" order by "+lv.getOrderBy());
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, getPersonRowMapper());
		applyPersonSubjectIds(persons);
		return persons;
    }

	public List<Person> getPersons() {
		String query = "select * from person order by firstNameHebrew;";
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, getPersonRowMapper());
		applyPersonSubjectIds(persons);
		return persons;
    }

	public List<Person> getPersons(List<Integer> ids) {
		StringBuffer query = new StringBuffer("select * from person where id in (");
		for (int id: ids){
			query.append(" "+id+",");
		}
		query.deleteCharAt(query.length()-1);
		query.append(") order by lastNameHebrew;");
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query.toString(), getPersonRowMapper());
		applyPersonSubjectIds(persons);
		return persons;
    }
	public List<Person> getConferenceResearchers() {
		String query = "select distinct person.* from person inner join conferenceProposal on conferenceProposal.personId=person.id order by firstNameHebrew;";
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, getPersonRowMapper());
		return persons;
    }
	public List<Person> getPersons(String role) {
		String query = "select * from person inner join personPrivilege " +
				"on person.id = personPrivilege.personId where personPrivilege.privilege = ? order by firstNameHebrew;";
		logger.debug(query);
		List<Person> persons =
			getSimpleJdbcTemplate().query(query, getPersonRowMapper(), role);
		applyPersonSubjectIds(persons);
		return persons;
    }

	public void insertPersonPrivilege(Person person, String privilege, boolean updateLastLogin, 
			String password, String subscriptionInitPage){
		String query = "insert ignore personPrivilege set personId = ?, privilege = ?, password = ?, subscriptionInitPage = ?";
		if (updateLastLogin)
			query += ", lastLogin = now()";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, person.getId(), privilege, password, subscriptionInitPage);
	}
	
	public void updatePersonPrivilegePassowrd(Person person, String encodedPassword){
		String query = "update personPrivilege set password = ?"
			+ " where personId = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, encodedPassword, person.getId());
	}

	public void updatePersonPrivilegeMD5(Person person, String privilege, boolean updateLastLogin, String md5){
		String query = "update personPrivilege set  subscriptionMd5 = ?";
		if (updateLastLogin)
			query += ", lastLogin = now()";
		query += " where personId = ? and privilege = ?";
		logger.debug(query);
		getSimpleJdbcTemplate().update(query, md5, person.getId(), privilege);
	}

	public boolean enablePersonPrivilege(String md5){
		String query = "update personPrivilege set enabled = 1 where subscriptionMd5 = ?";
		logger.debug(query);
		int r = getSimpleJdbcTemplate().update(query, md5);
		return r>0;
	}

	public boolean isSubscribed(int personId, boolean enabled){
		String query = "select count(*) from personPrivilege where personId = ? and enabled = " + (enabled ? 1: 0);
		logger.debug(query);
		int  r = getSimpleJdbcTemplate().queryForInt(query, personId);
		return r > 0;
	}
	
	public String getSinglePrivilege(int personId, boolean enabled){
		String query = "select privilege from personPrivilege where personId = ? and enabled = " + (enabled ? 1: 0) + " limit 1";
		logger.debug(query);
		Class<String> string = null;
		String privilege = getSimpleJdbcTemplate().queryForObject(query, string, personId);
		return privilege;
	}
	
	public boolean isAutoSubscribed(int personId, boolean enabled){
		String query = "select count(*) from personPrivilege where personId = ? and subscriptionMd5!='' and enabled = " + (enabled ? 1: 0);
		logger.debug(query);
		int  r = getSimpleJdbcTemplate().queryForInt(query, personId);
		return r > 0;
	}

	public boolean authenticate(int personId, String encodedPassword){
		String query = "select count(*) from personPrivilege where personId = ? and password = ? and enabled = 1";
		logger.debug(query);
		int  r = getSimpleJdbcTemplate().queryForInt(query, personId, encodedPassword);
		return r > 0;
	}

	public List<Person> getUsers (String role, boolean enabled){
		return getUsers(role, enabled, null);
	}

	public List<Person> getUsers (String role, boolean enabled, QueryElementsMap queryElements){
		String additionalConditions = null;
		String joinPhrase = null;
		String orderPhrase = null;
		String limitPhrase = null;
		
		if (queryElements != null){
			additionalConditions = queryElements.get(QueryValidKey.additionalConditions);
			joinPhrase = queryElements.get(QueryValidKey.joinPhrase);
			orderPhrase = queryElements.get(QueryValidKey.orderPhrase);
			limitPhrase = queryElements.get(QueryValidKey.limitPhrase);
		}
				
		String query = "select * from person inner join personPrivilege on person.id = personPrivilege.personId";
		if (joinPhrase != null)
			query	+= joinPhrase;
		query += " where personPrivilege.privilege = ? and enabled = " + (enabled ? 1: 0);
		if (additionalConditions != null)
			query += " and " + additionalConditions;
		if (orderPhrase != null){
			query += " " + orderPhrase;
		}
		else{
			query += " order by lastLogin desc";
		}
		if (limitPhrase != null){
			query += " " + limitPhrase;
		}
		logger.info(query);
		List<Person> usersPersons = getSimpleJdbcTemplate().query(query, personRowMapper , role);
		applyPersonSubjectIds(usersPersons);
		return usersPersons;
	}
	
	public int getUsersCount (String role, boolean enabled){
		return getUsersCount(role, enabled, null);
	}

	public int getUsersCount (String role, boolean enabled, QueryElementsMap queryElements){
		String additionalConditions = null;
		String joinPhrase = null;
		String orderPhrase = null;
		String limitPhrase = null;
		
		if (queryElements != null){
			additionalConditions = queryElements.get(QueryValidKey.additionalConditions);
			joinPhrase = queryElements.get(QueryValidKey.joinPhrase);
			orderPhrase = queryElements.get(QueryValidKey.orderPhrase);
			limitPhrase = queryElements.get(QueryValidKey.limitPhrase);
		}
				
		String query = "select count(*) from person inner join personPrivilege on person.id = personPrivilege.personId";
		if (joinPhrase != null)
			query	+= joinPhrase;
		query += " where personPrivilege.privilege = ? and enabled = " + (enabled ? 1: 0);
		if (additionalConditions != null)
			query += " and " + additionalConditions;
		if (orderPhrase != null){
			query += " " + orderPhrase;
		}
		else{
			query += " order by lastLogin desc;";
		}
		if (limitPhrase != null){
			query += " " + limitPhrase;
		}
		logger.debug(query);
		int usersCount = getSimpleJdbcTemplate().queryForInt(query, role);
		return usersCount;
	}

	@SuppressWarnings("unused")
	public Map<Integer, Timestamp> getPersonsLastLogins(){
		final Map<Integer,Timestamp> usersLastLogins = new HashMap<Integer, Timestamp>();
		String query = "select personId, lastLogin from personPrivilege group by personId";
		List <Void> nothing = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Void>(){
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException{
				usersLastLogins.put(rs.getInt(1), rs.getTimestamp(2));
				return null;
			}
		});
		return usersLastLogins;
	}

	
	public int countPerson(){
		String query = "select count(*) from person";
		logger.debug(query);
		return getSimpleJdbcTemplate().queryForInt(query);
	}
	
	public Person getPersonByFullNameEnglish(String fullNameEnglish){
		Person person = new Person();
		try{
			String query = "select * from person where concat(firstNameEnglish,' ' ,lastNameEnglish) like '"+ fullNameEnglish +"'";
			logger.debug(query);
			person =  getSimpleJdbcTemplate().queryForObject(query, personRowMapper);	
			return person;
		}
		catch(Exception e){
			return person;
		}
	}

}
