package huard.iws.bean;

import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.MD5Encoder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.acegisecurity.GrantedAuthority;
import org.apache.log4j.Logger;

public class PersonBean implements Serializable {

	public static final long serialVersionUID = 1323243434;

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

	//private List<PersonListAttribution> personListAttributions;

	private List<String> privileges;

	private String sourcePage;

	private int placeInList;

	private String title;

	private int titleId;

	private String localeId;

	private boolean selfSubscriber;

	private boolean yearFirstVisit;

	private boolean busyRecord;

	private int personAttributionId;

	private Timestamp lastLogin;
	
	private static final Logger logger = Logger.getLogger(PostBean.class);

	private boolean passValidation;

	private boolean postNewDesign;
	
	private String imageUrl;
	
	private boolean collectPublications;
	
	private Timestamp lastSync;

	public PersonBean() {
		this.id = 0;
		this.degreeHebrew = "";
		this.civilId = "";
		this.firstNameHebrew = "";
		this.lastNameHebrew = "";
		this.firstNameEnglish = "";
		this.lastNameEnglish = "";
		this.degreeEnglish = "";
		this.phone = "";
		this.fax = "";
		this.email = "";
		this.department = "";
		this.facultyId = 0;
		this.address = "";
		this.homePhone = "";
		this.cellPhone = "";
		this.roomNumber = "";
		this.researchEnabled = false;
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
		
		this.localeId = "iw_IL";
		this.passValidation=false;
		this.postNewDesign=false;
		this.imageUrl="";
		
		this.collectPublications = false;
		this.lastSync = new Timestamp(System.currentTimeMillis());
	}

	public PersonBean(Person person) {
		this.id = person.getId();
		this.degreeHebrew = person.getDegreeHebrew();
		this.civilId = person.getCivilId();
		this.firstNameHebrew = person.getFirstNameHebrew();
		this.lastNameHebrew = person.getLastNameHebrew();
		this.firstNameEnglish = person.getFirstNameEnglish();
		this.lastNameEnglish = person.getLastNameEnglish();
		this.degreeEnglish = person.getDegreeEnglish();
		this.phone = person.getPhone();
		this.fax = person.getFax();
		this.email = person.getEmail();
		this.department = person.getDepartment();
		this.facultyId = person.getFacultyId();
		this.address = person.getAddress();
		this.homePhone = person.getHomePhone();
		this.cellPhone = person.getCellPhone();
		this.roomNumber = person.getRoomNumber();
		this.researchEnabled = person.isResearchEnabled();
		this.preferedLocaleId = person.getPreferedLocaleId();
		this.academicTitle = person.getAcademicTitle();
		this.websiteUrl = person.getWebsiteUrl();
		this.campusId = person.getCampusId();

		this.subjectsIds = person.getSubjectsIds();
		this.postReceiveDays = person.getPostReceiveDays();
		this.postReceiveHour = person.getPostReceiveHour();
		this.postReceiveImmediately = person.isPostReceiveImmediately();
		this.readsUTF8Mails = person.isReadsUTF8Mails();
		this.receivePosts = person.isReceivePosts();
		this.postNewDesign = person.isPostNewDesign();
		this.imageUrl = person.getImageUrl();
		this.localeId = "iw_IL";
		this.imageUrl = person.getImageUrl();
		this.collectPublications = person.isCollectPublications();
		this.lastSync = person.getLastSync();
	}

	public PersonBean(Person person, String localeId) {
		this(person);
		this.localeId = localeId;
	}

	public Person toPerson() {
		Person person = new Person();
		person.setId(id);
		person.setDegreeHebrew(degreeHebrew);
		person.setCivilId(civilId);
		person.setFirstNameHebrew(firstNameHebrew);
		person.setLastNameHebrew(lastNameHebrew);
		person.setFirstNameEnglish(firstNameEnglish);
		person.setLastNameEnglish(lastNameEnglish);
		person.setDegreeEnglish(degreeEnglish);
		person.setPhone(phone);
		person.setFax(fax);
		person.setEmail(email);
		person.setDepartment(department);
		person.setFacultyId(facultyId);
		person.setAddress(address);
		person.setHomePhone(homePhone);
		person.setCellPhone(cellPhone);
		person.setRoomNumber(roomNumber);
		person.setResearchEnabled(researchEnabled);
		person.setPreferedLocaleId(preferedLocaleId);
		person.setAcademicTitle(academicTitle);
		person.setWebsiteUrl(websiteUrl);
		person.setCampusId(campusId);

		person.setSubjectsIds(subjectsIds);
		person.setPostReceiveDays(postReceiveDays);
		person.setPostReceiveHour(postReceiveHour);
		person.setPostReceiveImmediately(postReceiveImmediately);
		person.setReadsUTF8Mails(readsUTF8Mails);
		person.setReceivePosts(receivePosts);
		// person.setPersonListAttributions(personListAttributions);
		person.setPostNewDesign(postNewDesign);
		person.setImageUrl(imageUrl);
		person.setCollectPublications(collectPublications);
		person.setLastSync(lastSync);
		return person;
	}

	public String toString(String[] fields) {
		StringBuilder details = new StringBuilder("");
		for (String field : fields) {
			if (field.equals("degreeHebrew"))
				details.append(this.degreeHebrew + " ");
			if (field.equals("civilId"))
				details.append(this.civilId + " ");
			if (field.equals("firstNameHebrew"))
				details.append(this.firstNameHebrew + " ");
			if (field.equals("lastNameHebrew"))
				details.append(this.lastNameHebrew + " ");
			if (field.equals("firstNameEnglish"))
				details.append(this.firstNameEnglish + " ");
			if (field.equals("degreeEnglish"))
				details.append(this.degreeEnglish + " ");
			if (field.equals("phone"))
				details.append(this.phone + " ");
			if (field.equals("fax"))
				details.append(this.fax + " ");
			if (field.equals("email"))
				details.append(this.email + " ");
			if (field.equals("department"))
				details.append(this.department + " ");
		}
		details.deleteCharAt(details.length() - 1);
		return details.toString();
	}

	public void buildFieldValueMap(Map<String, String> fieldValueMap) {
		fieldValueMap.put("person.degreeHebrew", this.degreeHebrew);
		fieldValueMap.put("person.firstNameHebrew", this.firstNameHebrew);
		fieldValueMap.put("person.lastNameHebrew", this.lastNameHebrew);
		fieldValueMap.put("person.degreeEnglish", this.degreeEnglish);
		fieldValueMap.put("person.firstNameEnglish", this.firstNameEnglish);
		fieldValueMap.put("person.lastNameEnglish", this.lastNameEnglish);
		fieldValueMap.put("person.civilId", this.civilId);
		fieldValueMap.put("person.email", this.email);
		fieldValueMap.put("person.phone", this.phone);
		fieldValueMap.put("person.fax", this.fax);
		fieldValueMap.put("person.department", this.department);
		fieldValueMap.put("person.facultyId", "" + this.facultyId);
		fieldValueMap.put("person.address", this.address);
		fieldValueMap.put("person.homePhone", this.homePhone);
		fieldValueMap.put("person.cellPhone", this.cellPhone);
		fieldValueMap.put("person.roomNumber", this.roomNumber);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void combinePersonAndPersonAttributionDetails(
			PersonListAttribution personListAttribution) {
		this.setPlaceInList(personListAttribution.getPlaceInList());
		this.setTitle(personListAttribution.getTitle());
		this.setTitleId(personListAttribution.getTitleId());
		if (personListAttribution.isConnectDetails())
			return;
		else {
			if (personListAttribution.getEmail() != null)
				this.setEmail(personListAttribution.getEmail());
			if (personListAttribution.getPhone() != null)
				this.setPhone(personListAttribution.getPhone());
			if (personListAttribution.getFax() != null)
				this.setFax(personListAttribution.getFax());
			if (personListAttribution.getDepartment() != null)
				this.setDepartment(personListAttribution.getDepartment());
		}
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getDegreeFullName() {
		String degreeFullName = "";
		if (localeId.equals("iw_IL"))
			degreeFullName = degreeHebrew + " " + firstNameHebrew + " "
					+ lastNameHebrew;
		else if (localeId.equals("en_US"))
			degreeFullName = degreeEnglish + " " + firstNameEnglish + " "
					+ lastNameEnglish;
		return degreeFullName;
	}
	public String getFullName() {
		String fullName = "";
		if (localeId.equals("iw_IL"))
			fullName = firstNameHebrew + " " + lastNameHebrew;
		else if (localeId.equals("en_US"))
			fullName = firstNameEnglish + " " + lastNameEnglish;
		return fullName;
	}
	public String getDegreeFullName(String localeId){
		String degreeFullName = "";
		if (localeId.equals("iw_IL"))
			degreeFullName = degreeHebrew + " " + firstNameHebrew + " "
					+ lastNameHebrew;
		else if (localeId.equals("en_US"))
			degreeFullName = degreeEnglish + " " + firstNameEnglish + " "
					+ lastNameEnglish;
		return degreeFullName;
	}

	public String getPreferedLocaleDegreeFullName(){
		return getDegreeFullName(this.preferedLocaleId);
	}

	public String getDegreeFullNameHebrew() {
		return (degreeHebrew == null ? "" : degreeHebrew) + " "
				+ firstNameHebrew + " " + lastNameHebrew;
	}
	public String getDegreeFullNameEnglish() {
		return (degreeEnglish == null ? "" : degreeEnglish) + " "
				+ firstNameEnglish + " " + lastNameEnglish;
	}

	public String getFullNameHebrew() {
		return firstNameHebrew + " " + lastNameHebrew;
	}

	public boolean isValidEmail() {
		return (email != null && !email.equals("") && email.indexOf("@") != -1);
	}

	public String getUsername() {
		return this.civilId;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public boolean isAuthorized(String moduleName, String authority) {
		if (privileges == null)
			return false;
		String privilege = "ROLE_" + moduleName + "_" + authority;
		return privileges.contains(privilege);
	}
	
	public boolean isOnlyAuthorized(String moduleName, String authority) {
		if (privileges == null)
			return false;
		String privilege = "ROLE_" + moduleName + "_" + authority;
		if (! privileges.contains(privilege))
			return false;
		for (String aPrivilege: privileges){
			if (!aPrivilege.equals(privilege) && aPrivilege.contains(moduleName))
				return false;
		}
		return true;
	}

	public boolean isAuthorized(String authority){
		if (privileges == null)
			return false;
		for (String privilege : privileges) {
			if (privilege.contains(authority))
				return true;
		}
		return false;
	}
	
	public boolean isAnyAuthorized(String ... authorities){
		if (privileges == null)
			return false;
		for (String privilege : privileges) {
			for (String authority: authorities){
				if (privilege.contains(authority))
					return true;
			}
		}
		return false;
	}
	
	public boolean isAnonymous(){
		return isOnlyAuthorized("LISTS", "ANONYMOUS");
	}
	public boolean isHujiIp(){
		return isOnlyAuthorized("WEBSITE", "HUJI");
	}


	public String getPersonMD5(){
		return MD5Encoder.digest(firstNameHebrew + " " + lastNameHebrew + " " +email);
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

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getDegreeHebrew() {
		return degreeHebrew;
	}

	public void setDegreeHebrew(String degreeHebrew) {
		this.degreeHebrew = degreeHebrew;
	}

	public int getPersonAttributionId() {
		return personAttributionId;
	}

	public void setPersonAttributionId(int personAttributionId) {
		this.personAttributionId = personAttributionId;
	}

	public boolean isBusyRecord() {
		return busyRecord;
	}

	public void setBusyRecord(boolean busyRecord) {
		this.busyRecord = busyRecord;
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

	public List<String> getPrivileges() {
		return privileges;
	}
	
	public String getSinglePrivilege(){
		if (privileges==null || privileges.size() == 0)
			return "";
		return privileges.get(0);
	}

	public void setPersonPriviliges(GrantedAuthority[] grantedAuthorities) {
		privileges = new ArrayList<String>();
		for (int i = 0; i < grantedAuthorities.length; i++) {
			privileges.add(grantedAuthorities[i].getAuthority());
		}
	}

	public int getPlaceInList() {
		return placeInList;
	}

	public void setPlaceInList(int placeInList) {
		this.placeInList = placeInList;
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

	public List<Integer> getSubjectsIds() {
		return subjectsIds;
	}

	public void setSubjectsIds(List<Integer> subjectsIds) {
		this.subjectsIds = subjectsIds;
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

	public boolean isSelfSubscriber() {
		return selfSubscriber;
	}

	public void setSelfSubscriber(boolean selfSubscriber) {
		this.selfSubscriber = selfSubscriber;
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
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

	public boolean isYearFirstVisit() {
		return yearFirstVisit;
	}

	public void setYearFirstVisit(boolean yearFirstVisit) {
		this.yearFirstVisit = yearFirstVisit;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isReadsUTF8Mails() {
		return readsUTF8Mails;
	}

	public void setReadsUTF8Mails(boolean readsUTF8Mails) {
		this.readsUTF8Mails = readsUTF8Mails;
	}

	public int getOnBehalfOf(String module) {
		PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
		return personService.getOnBehalfOf(module,this.id);
	}
	public int getOfficialRepresentative(String module) {
		PersonService personService = (PersonService) ApplicationContextProvider.getContext().getBean("personService");
		return personService.getOfficialRepresentative(module,this.id);
	}
	
	public boolean getPassValidation() {
		return passValidation;
	}
	public void setPassValidation(boolean passValidation) {
		this.passValidation= passValidation;
	}
	
	public String getDegreePartialNameHebrew() {
		return (degreeHebrew == null ? "" : degreeHebrew) + " "
				+ firstNameHebrew.substring(0,1) + ". " + lastNameHebrew;
	}
	public String getDegreePartialNameEnglish() {
		return (degreeEnglish == null ? "" : degreeEnglish) + " "
				+ firstNameEnglish.substring(0,1) + ". " + lastNameEnglish;
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
	
	public boolean isCollectPublications() {
		return collectPublications;
	}

	public void setCollectPublications(boolean collectPublications) {
		this.collectPublications = collectPublications;
	}

	public Timestamp getLastSync() {
		return lastSync;
	}

	public void setLastSync(Timestamp lastSync) {
		this.lastSync = lastSync;
	}
}
