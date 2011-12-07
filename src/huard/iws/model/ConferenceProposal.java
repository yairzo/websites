package huard.iws.model;

import java.util.ArrayList;
import java.util.List;

public class ConferenceProposal {

	private int id;
	private int personId;
	private int approverId;
	private String approverEvaluation;
	private int grade;
	private String description;
	private int versionId;
	/*private String degreeHebrew;
	private String firstNameHebrew;
	private String lastNameHebrew;
	private String department;
	private int facultyId;
	private String phone;
	private String fax;
	private String email;*/
	private String subject;
	private long fromDate;
	private long toDate;
	private String location;
	private String locationDetail;
	private int foreignLecturers;
	private int localLecturers;
	private int audienceLecturers;
	private int foreignGuests;
	private int localGuests;
	private int audienceGuests;
	private byte [] guestsAttach;
	private String guestsAttachContentType;
	private byte [] programAttach;
	private String  programAttachContentType;
	private byte [] financialAttach;
	private String  financialAttachContentType;

	private String initiatingBody;
	private int initiatingBodyRole;
	private long openDate;
	private long submissionDate;
	private double totalCost;
	private int totalCostCurrency;
	private double supportSum;
	private int supportCurrency;
	private boolean auditorium;
	private boolean seminarRoom;
	private int participants;
	private int prefferedCampus;
	private boolean organizingCompany;
	private String organizingCompanyName;
	private String organizingCompanyPhone;
	private String organizingCompanyFax;
	private String organizingCompanyEmail;
	private boolean submitted;
	private String remarks;
	private String contactPerson;
	private String contactPersonRole;
	private String contactPersonPhone;
	private String contactPersonFax;
	private String contactPersonEmail;
	
	private List<Committee> scientificCommittees;
	private List<Committee> operationalCommittees;
	private List<FinancialSupport> fromAssosiate;
	private List<FinancialSupport> fromExternal;
	private List<FinancialSupport> fromAdmitanceFee;
	

	public ConferenceProposal(){
		this.id = 0;
		this.personId = 0;
		this.approverId = 0;
		this.approverEvaluation="";
		this.grade =0;
		this.description = "";
		this.versionId = 0;
		this.subject = "";
		this.fromDate = 0;
		this.toDate = 0;
		this.location = "";
		this.locationDetail = "";
		this.foreignLecturers = 0;
		this.foreignGuests = 0;
		this.localLecturers = 0;
		this.localGuests = 0;
		this.audienceLecturers = 0;
		this.audienceGuests = 0;
		this.guestsAttach = new byte[0];
		this.guestsAttachContentType = "";
		this.programAttach = new byte[0];
		this.programAttachContentType = "";
		this.financialAttach = new byte[0];
		this.financialAttachContentType = "";
		this.initiatingBody = "";
		this.initiatingBodyRole = 0;
		this.openDate = 0;
		this.submissionDate = 0;
		this.totalCost = 0;
		this.totalCostCurrency = 0;
		this.supportSum = 0;
		this.supportCurrency=0;
		this.auditorium=false;
		this.seminarRoom=false;
		this.participants=0;
		this.prefferedCampus=0;
		this.organizingCompany=false;
		this.organizingCompanyName="";
		this.organizingCompanyPhone="";
		this.organizingCompanyFax="";
		this.organizingCompanyEmail="";
		this.submitted=false;
		this.remarks="";
		this.contactPerson="";
		this.contactPersonRole="";
		this.contactPersonPhone="";
		this.contactPersonFax="";
		this.contactPersonEmail="";
		this.scientificCommittees= new ArrayList<Committee>();
		this.operationalCommittees= new ArrayList<Committee>();
		this.fromAssosiate = new ArrayList<FinancialSupport>();
		this.fromExternal = new ArrayList<FinancialSupport>();
		this.fromAdmitanceFee = new ArrayList<FinancialSupport>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getApproverId() {
		return approverId;
	}
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public String getApproverEvaluation() {
		return approverEvaluation;
	}
	public void setApproverEvaluation(String approverEvaluation) {
		this.approverEvaluation = approverEvaluation;
	}

	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public int getForeignLecturers() {
		return foreignLecturers;
	}
	public void setForeignLecturers(int foreignLecturers) {
		this.foreignLecturers = foreignLecturers;
	}
	
	public int getLocalLecturers() {
		return localLecturers;
	}
	public void setLocalLecturers(int localLecturers) {
		this.localLecturers = localLecturers;
	}
	
	public int getAudienceLecturers() {
		return audienceLecturers;
	}
	public void setAudienceLecturers(int audienceLecturers) {
		this.audienceLecturers = audienceLecturers;
	}
	
	public int getForeignGuests() {
		return foreignGuests;
	}
	public void setForeignGuests(int foreignGuests) {
		this.foreignGuests = foreignGuests;
	}
	
	public int getLocalGuests() {
		return localGuests;
	}
	public void setLocalGuests(int localGuests) {
		this.localGuests = localGuests;
	}
	
	public int getAudienceGuests() {
		return audienceGuests;
	}
	public void setAudienceGuests(int audienceGuests) {
		this.audienceGuests = audienceGuests;
	}
	
	public byte[] getGuestsAttach(){
		return guestsAttach;
	}
	public void setGuestsAttach(byte [] guestsAttach){
		this.guestsAttach = guestsAttach;
	}
	
	public String getGuestsAttachContentType() {
		return guestsAttachContentType;
	}
	public void setGuestsAttachContentType(String guestsAttachContentType) {
		this.guestsAttachContentType = guestsAttachContentType;
	}

	public byte[] getProgramAttach(){
		return programAttach;
	}
	public void setProgramAttach(byte [] programAttach){
		this.programAttach = programAttach;
	}
	
	public String getProgramAttachContentType() {
		return programAttachContentType;
	}
	public void setProgramAttachContentType(String programAttachContentType) {
		this.programAttachContentType = programAttachContentType;
	}	


	public byte[] getFinancialAttach(){
		return financialAttach;
	}
	public void setFinancialAttach(byte [] financialAttach){
		this.financialAttach = financialAttach;
	}
	
	public String getFinancialAttachContentType() {
		return financialAttachContentType;
	}
	public void setFinancialAttachContentType(String financialAttachContentType) {
		this.financialAttachContentType = financialAttachContentType;
	}

	public String getInitiatingBody() {
		return initiatingBody;
	}
	public void setInitiatingBody(String initiatingBody) {
		this.initiatingBody = initiatingBody;
	}

	public int getInitiatingBodyRole() {
		return initiatingBodyRole;
	}
	public void setInitiatingBodyRole(int initiatingBodyRole) {
		this.initiatingBodyRole = initiatingBodyRole;
	}

	public long getOpenDate() {
		return openDate;
	}
	public void setOpenDate(long openDate) {
		this.openDate = openDate;
	}

	public long getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(long submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	

	public int getTotalCostCurrency() {
		return totalCostCurrency;
	}
	public void setTotalCostCurrency(int totalCostCurrency) {
		this.totalCostCurrency = totalCostCurrency;
	}

	

	public int getSupportCurrency() {
		return supportCurrency;
	}
	public void setSupportCurrency(int supportCurrency) {
		this.supportCurrency = supportCurrency;
	}

	public boolean getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(boolean auditorium) {
		this.auditorium = auditorium;
	}

	public boolean getSeminarRoom() {
		return seminarRoom;
	}
	public void setSeminarRoom(boolean seminarRoom) {
		this.seminarRoom = seminarRoom;
	}

	public int getParticipants() {
		return participants;
	}
	public void setParticipants (int participants) {
		this.participants = participants;
	}
	
	public int getPrefferedCampus() {
		return prefferedCampus;
	}
	public void setPrefferedCampus (int prefferedCampus) {
		this.prefferedCampus = prefferedCampus;
	}
	
	public boolean getOrganizingCompany() {
		return organizingCompany;
	}
	public void setOrganizingCompany(boolean organizingCompany) {
		this.organizingCompany = organizingCompany;
	}

	public String getOrganizingCompanyName() {
		return organizingCompanyName;
	}
	public void setOrganizingCompanyName(String organizingCompanyName) {
		this.organizingCompanyName = organizingCompanyName;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getSupportSum() {
		return supportSum;
	}

	public void setSupportSum(double supportSum) {
		this.supportSum = supportSum;
	}

	public String getOrganizingCompanyPhone() {
		return organizingCompanyPhone;
	}
	public void setOrganizingCompanyPhone(String organizingCompanyPhone) {
		this.organizingCompanyPhone = organizingCompanyPhone;
	}

	public String getOrganizingCompanyFax() {
		return organizingCompanyFax;
	}
	public void setOrganizingCompanyFax(String organizingCompanyFax) {
		this.organizingCompanyFax = organizingCompanyFax;
	}

	public String getOrganizingCompanyEmail() {
		return organizingCompanyEmail;
	}
	public void setOrganizingCompanyEmail(String organizingCompanyEmail) {
		this.organizingCompanyEmail = organizingCompanyEmail;
	}

	public boolean getSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getContactPerson(){
		return contactPerson;
	}
	public void setContactPerson(String contactPerson){
		this.contactPerson = contactPerson;
	}

	public String getContactPersonRole(){
		return contactPersonRole;
	}
	public void setContactPersonRole(String contactPersonRole){
		this.contactPersonRole = contactPersonRole;
	}

	public String getContactPersonPhone(){
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone){
		this.contactPersonPhone = contactPersonPhone;
	}

	public String getContactPersonFax(){
		return contactPersonFax;
	}
	public void setContactPersonFax(String contactPersonFax){
		this.contactPersonFax = contactPersonFax;
	}
	
	public String getContactPersonEmail(){
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail){
		this.contactPersonEmail = contactPersonEmail;
	}
	
	public List<Committee> getScientificCommittees() {
		return scientificCommittees;
	}

	public void setScientificCommittees(List<Committee> scientificCommittees) {
		this.scientificCommittees = scientificCommittees;
	}

	public List<Committee> getOperationalCommittees() {
		return operationalCommittees;
	}

	public void setOperationalCommittees(List<Committee> operationalCommittees) {
		this.operationalCommittees = operationalCommittees;
	}

	public List<FinancialSupport> getFromAssosiate() {
		return fromAssosiate;
	}

	public void setFromAssosiate(List<FinancialSupport> fromAssosiate) {
		this.fromAssosiate = fromAssosiate;
	}

	public List<FinancialSupport> getFromExternal() {
		return fromExternal;
	}

	public void setFromExternal(List<FinancialSupport> fromExternal) {
		this.fromExternal = fromExternal;
	}

	public List<FinancialSupport> getFromAdmitanceFee() {
		return fromAdmitanceFee;
	}

	public void setFromAdmitanceFee(List<FinancialSupport> fromAdmitanceFee) {
		this.fromAdmitanceFee = fromAdmitanceFee;
	}

}
