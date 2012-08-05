package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.model.Person;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public interface PersonListService {

	public List<Person> getPersonsPage(ListView lv, SearchCreteria search);

	public List<Person> getPersons();

	public List<Person> getPersons(String [] orderingFields);

	public List<Person> getPersons(List<Integer> ids);

	public List<PersonBean> getPersonsList (int listId);

	public List<PersonBean> getPersonsList( int listId, String localeId);

	public Set<Integer> getPersonsListIds (int listId);

	public PersonBean getPersonsListPerson (int listId, int personId);

	public PersonBean getPersonsListPerson (int listId);

	public LinkedHashMap<String,Person> getResearchersMap();

	public boolean isResearcher(String hebrewFullName);

	public List<PersonBean> getPersons(String role);

	public void prepareListView(ListView lv, SearchCreteria search);
	
	public List<Person> getConferenceResearchers(String [] orderingFields);

}
