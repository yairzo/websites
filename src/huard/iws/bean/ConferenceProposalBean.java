package huard.iws.bean;

import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConferenceProposalBean {

	private int id;
	private int personId;
	private int approverId;
	private String approverEvaluation;
	private int grade;
	private String description;
	private int versionId;
	/*
	 * private String degreeHebrew; private String firstNameHebrew; private
	 * String lastNameHebrew; private String department; private int facultyId;
	 * private String phone; private String fax; private String email;
	 */
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
	private byte[] guestsAttach;
	private String guestsAttachContentType;
	private byte[] programAttach;
	private String programAttachContentType;
	private byte[] financialAttach;
	private String financialAttachContentType;

	private int initiatingBody;
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

	public ConferenceProposalBean() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		this.id = 0;
		this.personId = 0;
		this.approverId = 0;
		this.approverEvaluation = "";
		this.grade = 0;
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
		this.initiatingBody = 0;
		this.initiatingBodyRole = 0;
		this.openDate = now.getTime();
		this.submissionDate = now.getTime();
		this.totalCost = 0;
		this.totalCostCurrency = 0;
		this.supportSum = 0;
		this.supportCurrency = 0;
		this.auditorium = false;
		this.seminarRoom = false;
		this.participants = 0;
		this.prefferedCampus = 0;
		this.organizingCompany = false;
		this.organizingCompanyName = "";
		this.organizingCompanyPhone = "";
		this.organizingCompanyFax = "";
		this.organizingCompanyEmail = "";
		this.submitted = false;
		this.remarks = "";
		this.contactPerson = "";
		this.contactPersonRole = "";
		this.contactPersonPhone = "";
		this.contactPersonFax = "";
		this.contactPersonEmail = "";
		this.scientificCommittees = new ArrayList<Committee>();
		this.operationalCommittees = new ArrayList<Committee>();
		this.fromAssosiate = new ArrayList<FinancialSupport>();
		this.fromExternal = new ArrayList<FinancialSupport>();
		this.fromAdmitanceFee = new ArrayList<FinancialSupport>();

	}

	public ConferenceProposalBean(ConferenceProposal conferenceProposal) {
		this.id = conferenceProposal.getId();
		this.personId = conferenceProposal.getPersonId();
		this.approverId = conferenceProposal.getApproverId();
		this.approverEvaluation = conferenceProposal.getApproverEvaluation();
		this.grade = conferenceProposal.getGrade();
		this.description = conferenceProposal.getDescription();
		this.versionId = conferenceProposal.getVersionId();
		this.subject = conferenceProposal.getSubject();
		this.fromDate = conferenceProposal.getFromDate();
		this.toDate = conferenceProposal.getFromDate();
		this.location = conferenceProposal.getLocation();
		this.locationDetail = conferenceProposal.getLocationDetail();
		this.foreignLecturers = conferenceProposal.getForeignLecturers();
		this.localLecturers = conferenceProposal.getLocalLecturers();
		this.audienceLecturers = conferenceProposal.getAudienceLecturers();
		this.foreignGuests = conferenceProposal.getForeignGuests();
		this.localGuests = conferenceProposal.getLocalGuests();
		this.audienceGuests = conferenceProposal.getAudienceGuests();
		this.guestsAttach = conferenceProposal.getGuestsAttach();
		this.guestsAttachContentType = conferenceProposal
				.getGuestsAttachContentType();
		this.programAttach = conferenceProposal.getProgramAttach();
		this.programAttachContentType = conferenceProposal
				.getProgramAttachContentType();
		this.financialAttach = conferenceProposal.getFinancialAttach();
		this.financialAttachContentType = conferenceProposal
				.getFinancialAttachContentType();
		this.initiatingBody = conferenceProposal.getInitiatingBody();
		this.initiatingBodyRole = conferenceProposal.getInitiatingBodyRole();
		this.openDate = conferenceProposal.getOpenDate();
		this.submissionDate = conferenceProposal.getSubmissionDate();
		this.totalCost = conferenceProposal.getTotalCost();
		this.totalCostCurrency = conferenceProposal.getTotalCostCurrency();
		this.supportSum = conferenceProposal.getSupportSum();
		this.supportCurrency = conferenceProposal.getSupportCurrency();
		this.auditorium = conferenceProposal.getAuditorium();
		this.seminarRoom = conferenceProposal.getSeminarRoom();
		this.participants = conferenceProposal.getParticipants();
		this.prefferedCampus = conferenceProposal.getPrefferedCampus();
		this.organizingCompany = conferenceProposal.getOrganizingCompany();
		this.organizingCompanyName = conferenceProposal
				.getOrganizingCompanyName();
		this.organizingCompanyPhone = conferenceProposal
				.getOrganizingCompanyPhone();
		this.organizingCompanyFax = conferenceProposal
				.getOrganizingCompanyFax();
		this.organizingCompanyEmail = conferenceProposal
				.getOrganizingCompanyEmail();
		this.submitted = conferenceProposal.getSubmitted();
		this.remarks = conferenceProposal.getRemarks();
		this.contactPerson = conferenceProposal.getContactPerson();
		this.contactPersonRole = conferenceProposal.getContactPersonRole();
		this.contactPersonPhone = conferenceProposal.getContactPersonPhone();
		this.contactPersonFax = conferenceProposal.getContactPersonFax();
		this.contactPersonEmail = conferenceProposal.getContactPersonEmail();
		this.scientificCommittees = conferenceProposal
				.getScientificCommittees();
		this.operationalCommittees = conferenceProposal
				.getOperationalCommittees();
		this.fromAssosiate = conferenceProposal.getFromAssosiate();
		this.fromExternal = conferenceProposal.getFromExternal();
		this.fromAdmitanceFee = conferenceProposal.getFromAdmitanceFee();

		// System.out.println("beannnnnnnnnnnnnnnn:" + this.getSubject() +
		// this.getApproverEvaluation() + this.getApproverId() +
		// this.getDescription() + this.getLocation() + this.getLocationDetail()
		// + this.getFromDate());
	}

	public ConferenceProposal toConferenceProposal() {
		ConferenceProposal conferenceProposal = new ConferenceProposal();
		conferenceProposal.setId(id);
		conferenceProposal.setPersonId(personId);
		conferenceProposal.setApproverId(approverId);
		conferenceProposal.setApproverEvaluation(approverEvaluation);
		conferenceProposal.setGrade(grade);
		conferenceProposal.setDescription(description);
		conferenceProposal.setVersionId(versionId);
		conferenceProposal.setSubject(subject);
		conferenceProposal.setFromDate(fromDate);
		conferenceProposal.setToDate(toDate);
		conferenceProposal.setLocation(location);
		conferenceProposal.setLocationDetail(locationDetail);
		conferenceProposal.setForeignLecturers(foreignLecturers);
		conferenceProposal.setLocalLecturers(localLecturers);
		conferenceProposal.setAudienceLecturers(audienceLecturers);
		conferenceProposal.setForeignGuests(foreignGuests);
		conferenceProposal.setLocalGuests(localGuests);
		conferenceProposal.setAudienceGuests(audienceGuests);
		conferenceProposal.setGuestsAttach(guestsAttach);
		conferenceProposal.setGuestsAttachContentType(guestsAttachContentType);
		conferenceProposal.setProgramAttach(programAttach);
		conferenceProposal
				.setProgramAttachContentType(programAttachContentType);
		conferenceProposal.setFinancialAttach(financialAttach);
		conferenceProposal
				.setFinancialAttachContentType(financialAttachContentType);
		conferenceProposal.setInitiatingBody(initiatingBody);
		conferenceProposal.setInitiatingBodyRole(initiatingBodyRole);
		conferenceProposal.setOpenDate(openDate);
		conferenceProposal.setSubmissionDate(submissionDate);
		conferenceProposal.setTotalCost(totalCost);
		conferenceProposal.setTotalCostCurrency(totalCostCurrency);
		conferenceProposal.setSupportSum(supportSum);
		conferenceProposal.setSupportCurrency(supportCurrency);
		conferenceProposal.setAuditorium(auditorium);
		conferenceProposal.setSeminarRoom(seminarRoom);
		conferenceProposal.setParticipants(participants);
		conferenceProposal.setPrefferedCampus(prefferedCampus);
		conferenceProposal.setOrganizingCompany(organizingCompany);
		conferenceProposal.setOrganizingCompanyName(organizingCompanyName);
		conferenceProposal.setOrganizingCompanyPhone(organizingCompanyPhone);
		conferenceProposal.setOrganizingCompanyFax(organizingCompanyFax);
		conferenceProposal.setOrganizingCompanyEmail(organizingCompanyEmail);
		conferenceProposal.setSubmitted(submitted);
		conferenceProposal.setRemarks(remarks);
		conferenceProposal.setContactPerson(contactPerson);
		conferenceProposal.setContactPersonRole(contactPersonRole);
		conferenceProposal.setContactPersonPhone(contactPersonPhone);
		conferenceProposal.setContactPersonFax(contactPersonFax);
		conferenceProposal.setContactPersonEmail(contactPersonEmail);
		conferenceProposal.setScientificCommittees(scientificCommittees);
		conferenceProposal.setOperationalCommittees(operationalCommittees);
		conferenceProposal.setFromAssosiate(fromAssosiate);
		conferenceProposal.setFromExternal(fromExternal);
		conferenceProposal.setFromAdmitanceFee(fromAdmitanceFee);
		return conferenceProposal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonBean getResearcher() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean researcher = new PersonBean(
				personService.getPerson(this.personId));
		return researcher;
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

	public byte[] getGuestsAttach() {
		return guestsAttach;
	}

	public void setGuestsAttach(byte[] guestsAttach) {
		this.guestsAttach = guestsAttach;
	}

	public String getGuestsAttachContentType() {
		return guestsAttachContentType;
	}

	public void setGuestsAttachContentType(String guestsAttachContentType) {
		this.guestsAttachContentType = guestsAttachContentType;
	}

	public byte[] getProgramAttach() {
		return programAttach;
	}

	public void setProgramAttach(byte[] programAttach) {
		this.programAttach = programAttach;
	}

	public String getProgramAttachContentType() {
		return programAttachContentType;
	}

	public void setProgramAttachContentType(String programAttachContentType) {
		this.programAttachContentType = programAttachContentType;
	}

	public byte[] getFinancialAttach() {
		return financialAttach;
	}

	public void setFinancialAttach(byte[] financialAttach) {
		this.financialAttach = financialAttach;
	}

	public String getFinancialAttachContentType() {
		return financialAttachContentType;
	}

	public void setFinancialAttachContentType(String financialAttachContentType) {
		this.financialAttachContentType = financialAttachContentType;
	}

	public int getInitiatingBody() {
		return initiatingBody;
	}

	public void setInitiatingBody(int initiatingBody) {
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

	public void setParticipants(int participants) {
		this.participants = participants;
	}

	public int getPrefferedCampus() {
		return prefferedCampus;
	}

	public void setPrefferedCampus(int prefferedCampus) {
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPersonRole() {
		return contactPersonRole;
	}

	public void setContactPersonRole(String contactPersonRole) {
		this.contactPersonRole = contactPersonRole;
	}

	public String getContactPersonPhone() {
		return contactPersonPhone;
	}

	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}

	public String getContactPersonFax() {
		return contactPersonFax;
	}

	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
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
