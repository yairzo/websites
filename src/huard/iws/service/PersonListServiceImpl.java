package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PersonDao;
import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class PersonListServiceImpl implements PersonListService{
	//private static final Logger logger = Logger.getLogger(PersonListService.class);
	//private final int PERSONS_IN_PAGE=7;

	public List<Person> getPersonsPage(ListView lv, SearchCreteria search) {
		//ListPaginator lp = new ListPaginator(getPersons(lv, search), PERSONS_IN_PAGE);
		//List l = lp.getPage(lv.getPage());
		List<Person> l = getPersons(lv, search);
		List<Person> personsPage = new ArrayList<Person>();
		for (Object o : l){
			Person person = (Person) o;
			personsPage.add(person);
		}
		return personsPage;
	}

	public void prepareListView(ListView lv, SearchCreteria search){
		//ListPaginator lp = new ListPaginator(getPersons(lv, search), PERSONS_IN_PAGE);
		//lv.setLastPage(lp.getNumOfPages());
		//lv.setNearPages(lp.getNearPages(lv.getPage()));
		System.out.println("personDao.countPersons(lv,search)"+personDao.countPersons(lv,search));
		lv.setLastPage(lv.getNumOfPages(personDao.countPersons(lv,search)));
		lv.setNearPages(lv.getScroll());
	}




	public List<Person> getPersons(ListView lv, SearchCreteria search) {
		return personDao.getPersons(lv, search);
	}

	public List<Person> getPersons() {
		return personDao.getPersons();
	}

	public List<Person> getPersons(String [] orderFields){
		List<Person> persons = getPersons();
		Collections.sort(persons, new PersonComparator(orderFields));
		return persons;
	}

	public List<Person> getPersons(List<Integer> ids) {
		return personDao.getPersons (ids);
	}

	public List<PersonBean> getPersonsList( int listId) {
		List<PersonBean> persons = new ArrayList<PersonBean>();
		try{
			List<PersonListAttribution> personListAttributions = personAttributionListService.getPersonAttributions(listId);
			for (PersonListAttribution personListAttribution : personListAttributions){
				Person person = personDao.getPerson(personListAttribution.getPersonId());
				PersonBean personBean = new PersonBean(person);
				personBean.combinePersonAndPersonAttributionDetails(personListAttribution);
				persons.add(personBean);
			}
			return persons;
		}
		catch (Exception e){
			return persons;
		}
	}

	public List<PersonBean> getPersonsList( int listId, String localeId) {
		List<PersonListAttribution> personListAttributions = personAttributionListService.getPersonAttributions(listId);
		List<PersonBean> persons = new ArrayList<PersonBean>();
		for (PersonListAttribution personListAttribution : personListAttributions){
			Person person = personDao.getPerson(personListAttribution.getPersonId());
			PersonBean personBean = new PersonBean(person, localeId);
			personBean.combinePersonAndPersonAttributionDetails(personListAttribution);
			persons.add(personBean);
		}
		return persons;
	}

	public Set<Integer> getPersonsListIds (int listId){
		Set<Integer> personListIds = new HashSet<Integer>();
		for (PersonBean personBean : getPersonsList(listId)){
			personListIds.add(personBean.getId());
		}
		return personListIds;
	}

	public PersonBean getPersonsListPerson (int listId, int personId){
		List<PersonBean> persons = getPersonsList(listId);
		for (PersonBean personBean: persons){
			if (personBean.getId() == personId) return personBean;
		}
		return null;
	}

	public PersonBean getPersonsListPerson (int listId){
		return getPersonsList(listId).get(0);
	}

	public LinkedHashMap<String,Person> getResearchersMap(){
		List<Person> persons = getPersons();
		LinkedHashMap<String,Person> researchersMap = new LinkedHashMap<String,Person>();
		for (Person person: persons){
			if (person.isResearchEnabled()){
				PersonBean personBean = new PersonBean(person);
				researchersMap.put(personBean.getFullNameHebrew(), person);
			}
		}
		return researchersMap;
	}

	public boolean isResearcher(String fullNameHebrew){
		return getResearchersMap().containsKey(fullNameHebrew);
	}

	public List<PersonBean> getPersons(String role){
		List<Person> persons = personDao.getPersons(role);
		List<PersonBean> personBeans = new ArrayList<PersonBean>();
		for (Person person: persons){
			PersonBean personBean = new PersonBean(person);
			personBeans.add(personBean);
		}
		return personBeans;
	}




	private PersonAttributionListService personAttributionListService;

	public void setPersonAttributionListService(
			PersonAttributionListService personAttributionListService) {
		this.personAttributionListService = personAttributionListService;
	}

	private PersonDao personDao;

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	private class PersonComparator implements Comparator<Person> {

		private String [] orderFields;

		public PersonComparator(String [] orderFields) {
			this.orderFields = orderFields;
		}

		public int compare(Person p1, Person p2) {

			for (String orderField : orderFields){
				int result;
				if (orderField.equals("lastNameHebrew")){
					result = p1.getLastNameHebrew().compareTo(p2.getLastNameHebrew());
					if (result !=0)
						return result;
				}
				if (orderField.equals("firstNameHebrew")){
					result = p1.getFirstNameHebrew().compareTo(p2.getFirstNameHebrew());
					if (result !=0)
						return result;
				}
				if (orderField.equals("email")){
					result = p1.getEmail().compareTo(p2.getEmail());
					if (result !=0)
						return result;
				}
			}
			return 0;
		}
	}








}
