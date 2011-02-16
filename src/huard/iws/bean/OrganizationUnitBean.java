package huard.iws.bean;

import huard.iws.model.OrganizationUnit;
import huard.iws.service.ListColumnInstructionListService;
import huard.iws.util.ApplicationContextProvider;

import java.util.HashMap;
import java.util.Map;

public class OrganizationUnitBean extends ListViewableBean {

	private int typeId;
	private String nameHebrew;
	private String nameEnglish;
	private String email;
	private String websiteUrl;
	private String phone;
	private String fax;
	private String address;
	private String contact;
	private int placeInList;
	private int facultyId;

	private int listId;


	public OrganizationUnitBean(){
		super();
	}

	public OrganizationUnitBean(OrganizationUnit organizationUnit){
		this(organizationUnit, 0, 0);
	}


	public OrganizationUnitBean(OrganizationUnit organizationUnit, int listId){
		this(organizationUnit, listId, 0);
	}

	public OrganizationUnitBean(OrganizationUnit organizationUnit, int listId,  int parentListId){
		super();
		this.id = organizationUnit.getId();
		this.typeId = organizationUnit.getTypeId();
		this.nameHebrew = organizationUnit.getNameHebrew();
		this.nameEnglish = organizationUnit.getNameEnglish();
		this.email = organizationUnit.getEmail();
		this.websiteUrl = organizationUnit.getWebsiteUrl();
		this.phone = organizationUnit.getPhone();
		this.fax = organizationUnit.getFax();
		this.address = organizationUnit.getAddress();
		this.contact = organizationUnit.getContact();
		this.placeInList = organizationUnit.getPlaceInList();
		this.facultyId = organizationUnit.getFacultyId();

		this.listId = listId;

		init(parentListId);
	}



	public OrganizationUnit toOrganizationUnit(){
		OrganizationUnit organizationUnit = new OrganizationUnit();
		organizationUnit.setId(id);
		organizationUnit.setTypeId(typeId);
		organizationUnit.setNameHebrew(nameHebrew);
		organizationUnit.setNameEnglish(nameEnglish);
		organizationUnit.setEmail(email);
		organizationUnit.setWebsiteUrl(websiteUrl);
		organizationUnit.setPhone(phone);
		organizationUnit.setFax(fax);
		organizationUnit.setAddress(address);
		organizationUnit.setContact(contact);
		organizationUnit.setPlaceInList(placeInList);
		organizationUnit.setFacultyId(facultyId);
		return organizationUnit;
	}

	public void init(int parentListId){
		ListColumnInstructionListService columnInstructionsService = (ListColumnInstructionListService) ApplicationContextProvider.getContext().getBean("listColumnInstructionListService");
		columnInstructions = columnInstructionsService.getListColumnInstructions(listId, parentListId);

		fieldValueMap = new HashMap<String, String>();
		buildFieldValueMap( fieldValueMap);
	}



	public void buildFieldValueMap(Map<String, String> fieldValueMap){
		fieldValueMap.put("organizationUnit.nameHebrew", this.nameHebrew);
		fieldValueMap.put("organizationUnit.nameEnglish", this.nameEnglish);
		fieldValueMap.put("organizationUnit.email", this.email);
		fieldValueMap.put("organizationUnit.websiteUrl", this.websiteUrl);
		fieldValueMap.put("organizationUnit.phone", this.phone);
		fieldValueMap.put("organizationUnit.fax", this.fax);
		fieldValueMap.put("organizationUnit.facultyId", ""+this.facultyId);
		fieldValueMap.put("organizationUnit.address", this.address);
		fieldValueMap.put("organizationUnit.contact", this.contact);
	}



	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
	public String getNameHebrew() {
		return nameHebrew;
	}
	public void setNameHebrew(String nameHebrew) {
		this.nameHebrew = nameHebrew;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
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

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
