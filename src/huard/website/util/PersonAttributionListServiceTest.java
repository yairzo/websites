package huard.website.util;

import huard.iws.model.PersonListAttribution;
import huard.iws.service.PersonAttributionListService;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class PersonAttributionListServiceTest {


	public static void main (String [] args){
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(PersonAttributionListService.class);
		factory.setServiceUrl("rmi://localhost:1199/PersonAttributionListService");
		factory.afterPropertiesSet();
		PersonAttributionListService personAttributionListService = (PersonAttributionListService) factory.getObject();
		for (PersonListAttribution personListAttribution: personAttributionListService.getPersonAttributionsByListId(5,"")){
			System.out.println(personListAttribution.getEmail());
		}

	}

	/*private interface PersonAttributionListService {

		public List<PersonListAttribution> getPersonAttributionsByPersonId(int personId);

		public List<PersonListAttribution> getPersonAttributions(int listId);

		public List<PersonListAttribution> getPersonAttributionsByListId(int listId);

		public List<PersonListAttribution> getPersonAttributionsByListId( int listId, int orderColumn);

		public List<PersonListAttribution> getPersonAttributionsByListId( int listId, String order);

		public void updateConnectedDetailsPersonAttributions(int personId, PersonListAttribution personAttribution);

	}


	private class PersonListAttribution{


		private int id;
		private int personId;
		private int listId;
		private String title;
		private String email;
		private String phone;
		private String fax;
		private String department;
		private int facultyId;
		private String address;
		private int placeInList;
		private int titleId;
		private boolean connectDetails;
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public boolean isConnectDetails() {
			return connectDetails;
		}
		public void setConnectDetails(boolean connectDetails) {
			this.connectDetails = connectDetails;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public int getFacultyId() {
			return facultyId;
		}
		public void setFacultyId(int facultyId) {
			this.facultyId = facultyId;
		}
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			this.fax = fax;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getListId() {
			return listId;
		}
		public void setListId(int listId) {
			this.listId = listId;
		}
		public int getPersonId() {
			return personId;
		}
		public void setPersonId(int personId) {
			this.personId = personId;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public int getPlaceInList() {
			return placeInList;
		}
		public void setPlaceInList(int placeInList) {
			this.placeInList = placeInList;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getTitleId() {
			return titleId;
		}
		public void setTitleId(int titleId) {
			this.titleId = titleId;
		}


	}
*/
}
