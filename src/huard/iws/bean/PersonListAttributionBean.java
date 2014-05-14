package huard.iws.bean;

import huard.iws.model.AList;
import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListColumnInstructionListService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

import java.util.HashMap;
import java.util.Map;

public class PersonListAttributionBean extends ListViewableBean{
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
	private String imageUrl;

	private AList list  = new AList();

	private Person person;

	private boolean newPersonAttribution;

	//private static final Logger logger = Logger.getLogger(PersonListAttributionBean.class);

	public PersonListAttributionBean(){
		super();
		this.id = 0;
		this.personId = 0;
		this.listId = 0;
		this.title = "";
		this.email = "";
		this.phone = "";
		this.fax = "";
		this.department = "";
		this.facultyId = 0;
		this.address = "";
		this.placeInList = -10;
		this.titleId = 0;
		this.connectDetails = false;
		this.imageUrl="";
	}

	public PersonListAttributionBean(PersonListAttribution personListAttribution){
		this(personListAttribution, 0);
	}


	public PersonListAttributionBean(PersonListAttribution personListAttribution, int parentListId){
		super();
		this.id = personListAttribution.getId();
		this.personId = personListAttribution.getPersonId();
		this.listId = personListAttribution.getListId();
		this.title = personListAttribution.getTitle();
		this.email = personListAttribution.getEmail();
		this.phone = personListAttribution.getPhone();
		this.fax = personListAttribution.getFax();
		this.department = personListAttribution.getDepartment();
		this.facultyId = personListAttribution.getFacultyId();
		this.address = personListAttribution.getAddress();
		this.placeInList = personListAttribution.getPlaceInList();
		this.titleId = personListAttribution.getTitleId();
		this.connectDetails = personListAttribution.isConnectDetails();
		this.imageUrl = personListAttribution.getImageUrl();
		if (personId > 0 && listId > 0)
			init(parentListId);
	}

	public PersonListAttribution toPersonListAttribution(){
		PersonListAttribution personListAttribution = new PersonListAttribution();
		personListAttribution.setId(id);
		personListAttribution.setPersonId(personId);
		personListAttribution.setListId(listId);
		personListAttribution.setTitle(title);
		personListAttribution.setEmail(email);
		personListAttribution.setPhone(phone);
		personListAttribution.setFax(fax);
		personListAttribution.setDepartment(department);
		personListAttribution.setFacultyId(facultyId);
		personListAttribution.setAddress(address);
		personListAttribution.setPlaceInList(placeInList);
		personListAttribution.setTitleId(titleId);
		personListAttribution.setConnectDetails(connectDetails);
		personListAttribution.setImageUrl(imageUrl);
		return personListAttribution;
	}

	public void init(int parentListId){
		PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
		person = personService.getPerson(personId);
		PersonBean personBean = new PersonBean(person);

		ListColumnInstructionListService columnInstructionsService = (ListColumnInstructionListService) ApplicationContextProvider.getContext().getBean("listColumnInstructionListService");
		columnInstructions = columnInstructionsService.getListColumnInstructions(listId, parentListId);

		fieldValueMap = new HashMap<String, String>();
		buildFieldValueMap( fieldValueMap);
		personBean.buildFieldValueMap(fieldValueMap);
	}



	public void buildFieldValueMap(Map<String, String> fieldValueMap){
		fieldValueMap.put("personAttribution.title", this.title);
		fieldValueMap.put("personAttribution.email", this.email);
		fieldValueMap.put("personAttribution.phone", this.phone);
		fieldValueMap.put("personAttribution.fax", this.fax);
		fieldValueMap.put("personAttribution.department", this.department);
		fieldValueMap.put("personAttribution.facultyId", ""+this.facultyId);
		fieldValueMap.put("personAttribution.address", this.address);
		fieldValueMap.put("personAttribution.titleId", ""+this.titleId);
		fieldValueMap.put("personAttribution.imageUrl", ""+this.imageUrl);
	}









	public boolean isNewPersonAttribution() {
		return newPersonAttribution;
	}

	public void setNewPersonAttribution(boolean newPersonAttribution) {
		this.newPersonAttribution = newPersonAttribution;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}



	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public AList getList() {
		return list;
	}

	public void setList(AList list) {
		this.list = list;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public int getPlaceInList() {
		return placeInList;
	}

	public void setPlaceInList(int placeInList) {
		this.placeInList = placeInList;
	}

	public boolean isConnectDetails() {
		return connectDetails;
	}

	public void setConnectDetails(boolean connectDetails) {
		this.connectDetails = connectDetails;
	}

	public PersonBean getPersonBean(){
		return new PersonBean(person);
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
