package huard.iws.model;

import java.io.Serializable;

public class PersonListAttribution implements Serializable{
	private static final long serialVersionUID = 121212121;

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

	public PersonListAttribution(){
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
	}

	public boolean isConnectDetails() {
		return connectDetails;
	}

	public void setConnectDetails(boolean connectDetails) {
		this.connectDetails = connectDetails;
	}

	public void prepareForView(){
		if (title!=null) title = title.replaceAll("\"", "&quot;");
		if (department!=null) department = department.replaceAll("\"", "&quot;");
		if (address!=null) address = address.replaceAll("\"", "&quot;");
	}

	public int getPlaceInList() {
		return placeInList;
	}
	public void setPlaceInList(int placeInList) {
		this.placeInList = placeInList;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

}
