package huard.iws.model;

import java.util.ArrayList;
import java.util.List;

public class Person implements ISubjectRelated, IMailable{
	private int id;
	private String degreeHebrew;
	private String civilId;
	private String firstNameHebrew;
	private String lastNameHebrew;
	private String firstNameEnglish;
	private String lastNameEnglish;
	private String degreeEnglish;
	private String phone;
	private String fax;
	private String email;
	private String department;
	private int facultyId;
	private String address;
	private String homePhone;
	private String cellPhone;
	private String roomNumber;
	private boolean researchEnabled;
	private String preferedLocaleId;
	private String academicTitle;
	private String websiteUrl;
	private int campusId;

	private List<Integer> subjectsIds;
	private List<Integer> postReceiveDays;
	private int postReceiveHour;
	private boolean postReceiveImmediately;
	private boolean readsUTF8Mails;
	private boolean receivePosts;
	private boolean postNewDesign;
	private String imageUrl;



	List<PersonListAttribution> personListAttributions;

	public Person(){
		this.id=0;
		this.degreeHebrew="";
		this.civilId="";
		this.firstNameHebrew="";
		this.lastNameHebrew="";
		this.firstNameEnglish="";
		this.lastNameEnglish="";
		this.degreeEnglish="";
		this.phone="";
		this.fax="";
		this.email="";
		this.department="";
		this.facultyId = 0;
		this.address="";
		this.homePhone="";
		this.cellPhone="";
		this.roomNumber="";
		this.researchEnabled=false;
		this.preferedLocaleId = "iw_IL";
		this.academicTitle = "";
		this.websiteUrl = "";
		this.campusId = 0;

		this.subjectsIds = new ArrayList<Integer>();
		this.postReceiveDays = new ArrayList<Integer>();
		this.postReceiveHour = 16;
		this.postReceiveImmediately = false;
		this.readsUTF8Mails = true;
		this.receivePosts = true;
		this.postNewDesign = false;
		this.imageUrl = "";
	}

	public PersonListAttribution toPersonAttribution(){
		PersonListAttribution personAttribution = new PersonListAttribution();
		personAttribution.setPhone(phone);
		personAttribution.setFax(fax);
		personAttribution.setEmail(email);
		personAttribution.setDepartment(department);
		personAttribution.setFacultyId(facultyId);
		personAttribution.setAddress(address);
		return personAttribution;
	}

	public List<PersonListAttribution> getPersonListAttributions() {
		return personListAttributions;
	}
	public void setPersonListAttributions(
			List<PersonListAttribution> personListAttributions) {
		this.personListAttributions = personListAttributions;
	}
	public String getFirstNameEnglish() {
		return firstNameEnglish;
	}
	public void setFirstNameEnglish(String firstNameEnglish) {
		this.firstNameEnglish = firstNameEnglish;
	}
	public String getFirstNameHebrew() {
		return firstNameHebrew;
	}
	public void setFirstNameHebrew(String firstNameHebrew) {
		this.firstNameHebrew = firstNameHebrew;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastNameEnglish() {
		return lastNameEnglish;
	}
	public void setLastNameEnglish(String lastNameEnglish) {
		this.lastNameEnglish = lastNameEnglish;
	}
	public String getLastNameHebrew() {
		return lastNameHebrew;
	}
	public void setLastNameHebrew(String lastNameHebrew) {
		this.lastNameHebrew = lastNameHebrew;
	}
	public String getDegreeHebrew() {
		return degreeHebrew;
	}
	public void setDegreeHebrew(String degreeHebrew) {
		this.degreeHebrew = degreeHebrew;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDegreeEnglish() {
		return degreeEnglish;
	}
	public void setDegreeEnglish(String degreeEnglish) {
		this.degreeEnglish = degreeEnglish;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isResearchEnabled() {
		return researchEnabled;
	}

	public void setResearchEnabled(boolean researchEnabled) {
		this.researchEnabled = researchEnabled;
	}



	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}


	public List<Integer> getSubjectsIds() {
		return subjectsIds;
	}

	public void setSubjectsIds(List<Integer> subjectsIds) {
		this.subjectsIds = subjectsIds;
	}



	public List<Integer> getPostReceiveDays() {
		return postReceiveDays;
	}

	public void setPostReceiveDays(List<Integer> postReceiveDays) {
		this.postReceiveDays = postReceiveDays;
	}

	public int getPostReceiveHour() {
		return postReceiveHour;
	}

	public void setPostReceiveHour(int postReceiveHour) {
		this.postReceiveHour = postReceiveHour;
	}

	public boolean isPostReceiveImmediately() {
		return postReceiveImmediately;
	}

	public void setPostReceiveImmediately(boolean postReceiveImmediately) {
		this.postReceiveImmediately = postReceiveImmediately;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getPreferedLocaleId() {
		return preferedLocaleId;
	}

	public void setPreferedLocaleId(String preferedLocaleId) {
		this.preferedLocaleId = preferedLocaleId;
	}

	public String getAcademicTitle() {
		return academicTitle;
	}

	public void setAcademicTitle(String academicTitle) {
		this.academicTitle = academicTitle;
	}

	public int getCampusId() {
		return campusId;
	}

	public void setCampusId(int campusId) {
		this.campusId = campusId;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public boolean isReadsUTF8Mails() {
		return readsUTF8Mails;
	}

	public void setReadsUTF8Mails(boolean readsUTF8Mails) {
		this.readsUTF8Mails = readsUTF8Mails;
	}

	public boolean isReceivePosts() {
		return receivePosts;
	}

	public void setReceivePosts(boolean receivePosts) {
		this.receivePosts = receivePosts;
	}
	
	public boolean isPostNewDesign() {
		return postNewDesign;
	}

	public void setPostNewDesign(boolean postNewDesign) {
		this.postNewDesign = postNewDesign;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
