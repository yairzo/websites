package huard.iws.model;

import java.util.ArrayList;
import java.util.List;

public class ConferenceProposal {

	private int id;
	private int internalId;
	private int personId;
	private int approverId;
	private String approverEvaluation;
	private int grade;
	private String description;
	private int versionId;
	private String subject;
	private long fromDate;
	private long toDate;
	private String location;
	private String locationDetail;
	private int foreignLecturers;
	private int localLecturers;
	private int foreignGuests;
	private int localGuests;
	private int foreignAudience;
	private int localAudience;
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
	private int totalCost;
	private int totalCostCurrency;
	private int supportSum;
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
	private String adminRemarks;
	private long deadline;
	private List<Committee> scientificCommittees;
	private List<Committee> operationalCommittees;
	private List<FinancialSupport> fromAssosiate;
	private List<FinancialSupport> fromExternal;
	private List<FinancialSupport> fromAdmitanceFee;
	private boolean deleted;
	private String deadlineRemarks;
	private boolean isInsideDeadline;
	private String committeeRemarks;
	private byte[] companyAttach;
	private String companyAttachContentType;
	private boolean acceptTerms;
	private int creatorId;
	private int statusId;
	private long statusDate;

	public ConferenceProposal(){
		this.id = 0;
		this.internalId=0;
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
		this.foreignAudience = 0;
		this.localAudience = 0;
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
		this.adminRemarks="";
		this.deadline=0;
		this.deleted=false;
		this.deadlineRemarks="";
		this.isInsideDeadline = true;
		this.committeeRemarks = "";
		this.companyAttach = new byte[0];
		this.companyAttachContentType= "";
		this.acceptTerms=false;
		this.creatorId=0;
		this.statusId=0;
		this.statusDate=0;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getInternalId() {
		return internalId;
	}
	public void setInternalId(int internalId) {
		this.internalId = internalId;
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


	public int getForeignAudience() {
		return foreignAudience;
	}


	public void setForeignAudience(int foreignAudience) {
		this.foreignAudience = foreignAudience;
	}


	public int getLocalAudience() {
		return localAudience;
	}


	public void setLocalAudience(int localAudience) {
		this.localAudience = localAudience;
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

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public int getSupportSum() {
		return supportSum;
	}

	public void setSupportSum(int supportSum) {
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

	public String getAdminRemarks(){
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks){
		this.adminRemarks = adminRemarks;
	}	

	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
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

	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getDeadlineRemarks(){
		return deadlineRemarks;
	}
	public void setDeadlineRemarks(String deadlineRemarks){
		this.deadlineRemarks = deadlineRemarks;
	}	
	
	public boolean getIsInsideDeadline() {
		return isInsideDeadline;
	}
	public void setIsInsideDeadline(boolean isInsideDeadline) {
		this.isInsideDeadline = isInsideDeadline;
	}

	public String getCommitteeRemarks(){
		return committeeRemarks;
	}
	public void setCommitteeRemarks(String committeeRemarks){
		this.committeeRemarks = committeeRemarks;
	}	

	public byte[] getCompanyAttach() {
		return companyAttach;
	}

	public void setCompanyAttach(byte[] companyAttach) {
		this.companyAttach = companyAttach;
	}

	public String getCompanyAttachContentType() {
		return companyAttachContentType;
	}

	public void setCompanyAttachContentType(String companyAttachContentType) {
		this.companyAttachContentType = companyAttachContentType;
	}

	public boolean getAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public long getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(long statusDate) {
		this.statusDate = statusDate;
	}


}
