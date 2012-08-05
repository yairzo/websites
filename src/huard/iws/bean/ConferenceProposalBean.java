package huard.iws.bean;

import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConferenceProposalBean {
	
	private final int MAX_NUM_COMMITTEE = 10;
	private final int MAX_NUM_FINANCIAL_SUPPORT = 10;
	

	private int id;
	private int internalId;
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
	private String foreignLecturers;
	private String localLecturers;
	private String audienceLecturers;
	private String foreignGuests;
	private String localGuests;
	private String audienceGuests;
	private byte[] guestsAttach;
	private String guestsAttachContentType;
	private byte[] programAttach;
	private String programAttachContentType;
	private byte[] financialAttach;
	private String financialAttachContentType;

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


	public ConferenceProposalBean() {
		this.id = 0;
		this.internalId = 0;
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
		this.foreignLecturers = "";
		this.foreignGuests = "";
		this.localLecturers = "";
		this.localGuests = "";
		this.audienceLecturers = "";
		this.audienceGuests = "";
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
		this.adminRemarks ="";
		this.deadline=0;
		this.scientificCommittees = new ArrayList<Committee>();
		for (int i=0; i< MAX_NUM_COMMITTEE; i++){
			Committee committee = new Committee();
			committee.setType(Committee.TYPE_SCIENTIFIC);
			scientificCommittees.add(committee);
		}
		this.operationalCommittees = new ArrayList<Committee>();
		for (int i=0; i< MAX_NUM_COMMITTEE; i++){
			Committee committee = new Committee();
			committee.setType(Committee.TYPE_OPERATIONAL);
			operationalCommittees.add(committee);
		}
		this.fromAssosiate = new ArrayList<FinancialSupport>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setType(FinancialSupport.TYPE_ASSOSIATE);
			fromAssosiate.add(financialSupport);
		}
		this.fromExternal = new ArrayList<FinancialSupport>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setType(FinancialSupport.TYPE_EXTERNAL);
			fromExternal.add(financialSupport);
		}
		this.fromAdmitanceFee = new ArrayList<FinancialSupport>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			//financialSupport.setName("דמי הרשמה");
			financialSupport.setType(FinancialSupport.TYPE_ADMITANCEFEE);
			fromAdmitanceFee.add(financialSupport);
		}
		this.deleted =false;
		this.deadlineRemarks="";
		this.isInsideDeadline =true;
		this.committeeRemarks ="";
		this.companyAttach = new byte[0];
		this.companyAttachContentType = "";
		this.acceptTerms =false;
	}

	public ConferenceProposalBean(ConferenceProposal conferenceProposal) {
		this.id = conferenceProposal.getId();
		this.internalId = conferenceProposal.getInternalId();
		this.personId = conferenceProposal.getPersonId();
		this.approverId = conferenceProposal.getApproverId();
		this.approverEvaluation = conferenceProposal.getApproverEvaluation();
		this.grade = conferenceProposal.getGrade();
		this.description = conferenceProposal.getDescription();
		this.versionId = conferenceProposal.getVersionId();
		this.subject = conferenceProposal.getSubject();
		this.fromDate = conferenceProposal.getFromDate();
		this.toDate = conferenceProposal.getToDate();
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
		this.adminRemarks = conferenceProposal.getAdminRemarks();
		this.deadline = conferenceProposal.getDeadline();
		this.scientificCommittees = conferenceProposal
				.getScientificCommittees();
		for (int i = scientificCommittees.size(); i<MAX_NUM_COMMITTEE; i++){
			Committee committee = new Committee();
			committee.setConferenceProposalId(conferenceProposal.getId());
			committee.setType(Committee.TYPE_SCIENTIFIC);			
			scientificCommittees.add(committee);
		}
		this.operationalCommittees = conferenceProposal
				.getOperationalCommittees();
		for (int i = operationalCommittees.size(); i<MAX_NUM_COMMITTEE; i++){
			Committee committee = new Committee();
			committee.setConferenceProposalId(conferenceProposal.getId());
			committee.setType(Committee.TYPE_OPERATIONAL);			
			operationalCommittees.add(committee);
		}
		this.fromAssosiate = conferenceProposal.getFromAssosiate();
		for (int i=fromAssosiate.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setConferenceProposalId(conferenceProposal.getId());
			financialSupport.setType(FinancialSupport.TYPE_ASSOSIATE);
			fromAssosiate.add(financialSupport);
		}
		this.fromExternal = conferenceProposal.getFromExternal();
		for (int i=fromExternal.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setConferenceProposalId(conferenceProposal.getId());
			financialSupport.setType(FinancialSupport.TYPE_EXTERNAL);
			fromExternal.add(financialSupport);
		}
		this.fromAdmitanceFee = conferenceProposal.getFromAdmitanceFee();
		for (int i=fromAdmitanceFee.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupport financialSupport = new FinancialSupport();
			financialSupport.setConferenceProposalId(conferenceProposal.getId());
			//financialSupport.setName("דמי הרשמה");
			financialSupport.setType(FinancialSupport.TYPE_ADMITANCEFEE);
			fromAdmitanceFee.add(financialSupport);
		}
		
		
		this.deleted = conferenceProposal.getDeleted();
		this.deadlineRemarks = conferenceProposal.getDeadlineRemarks();
		this.isInsideDeadline =conferenceProposal.getIsInsideDeadline();
		this.committeeRemarks = conferenceProposal.getCommitteeRemarks();
		this.companyAttach = conferenceProposal.getCompanyAttach();
		this.companyAttachContentType = conferenceProposal.getCompanyAttachContentType();
		this.acceptTerms =conferenceProposal.getAcceptTerms();
		// System.out.println("beannnnnnnnnnnnnnnn:" + this.getSubject() +
		// this.getApproverEvaluation() + this.getApproverId() +
		// this.getDescription() + this.getLocation() + this.getLocationDetail()
		// + this.getFromDate());
	}

	public ConferenceProposal toConferenceProposal() {
		ConferenceProposal conferenceProposal = new ConferenceProposal();
		conferenceProposal.setId(id);
		conferenceProposal.setInternalId(internalId);
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
		conferenceProposal.setAdminRemarks(adminRemarks);
		conferenceProposal.setDeadline(deadline);
		conferenceProposal.setScientificCommittees(scientificCommittees);
		conferenceProposal.setOperationalCommittees(operationalCommittees);
		conferenceProposal.setFromAssosiate(fromAssosiate);
		conferenceProposal.setFromExternal(fromExternal);
		conferenceProposal.setFromAdmitanceFee(fromAdmitanceFee);
		conferenceProposal.setDeleted(deleted);
		conferenceProposal.setDeadlineRemarks(deadlineRemarks);
		conferenceProposal.setIsInsideDeadline(isInsideDeadline);
		conferenceProposal.setCommitteeRemarks(committeeRemarks);
		conferenceProposal.setCompanyAttach(companyAttach);
		conferenceProposal.setCompanyAttachContentType(companyAttachContentType);
		conferenceProposal.setAcceptTerms(acceptTerms);
		return conferenceProposal;
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
	
	public PersonBean getResearcher() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean researcher = new PersonBean(
				personService.getPerson(this.personId));
		return researcher;
	}
	public PersonBean getApprover() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean approver = new PersonBean(
				personService.getPerson(this.approverId));
		return approver;
	}

	public String getFormattedFromDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fromDate = new Date(this.fromDate);
		return formatter.format(fromDate);
	}
	
	public String getStatusDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if(this.submitted==true){
			Date submissionDate = new Date(this.submissionDate);
			return formatter.format(submissionDate);
		}
		else {
			Date openDate = new Date(this.openDate);
			return formatter.format(openDate);
		}
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

	public String getForeignLecturers() {
		return foreignLecturers;
	}

	public void setForeignLecturers(String foreignLecturers) {
		this.foreignLecturers = foreignLecturers;
	}

	public String getLocalLecturers() {
		return localLecturers;
	}

	public void setLocalLecturers(String localLecturers) {
		this.localLecturers = localLecturers;
	}

	public String getAudienceLecturers() {
		return audienceLecturers;
	}

	public void setAudienceLecturers(String audienceLecturers) {
		this.audienceLecturers = audienceLecturers;
	}

	public String getForeignGuests() {
		return foreignGuests;
	}

	public void setForeignGuests(String foreignGuests) {
		this.foreignGuests = foreignGuests;
	}

	public String getLocalGuests() {
		return localGuests;
	}

	public void setLocalGuests(String localGuests) {
		this.localGuests = localGuests;
	}

	public String getAudienceGuests() {
		return audienceGuests;
	}

	public void setAudienceGuests(String audienceGuests) {
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

	public int getSumFromAssociate(){
		int sum=0;
		try{
			for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++)
				if(!fromAssosiate.get(i).getSum().isEmpty())
					sum += new Integer(fromAssosiate.get(i).getSum()).intValue();
		}
		catch(Exception e){
			System.out.println("Exception trying to convert financial support sum to integer");
		}
		return sum;
	}
	public int getSumFromExternal(){
		int sum=0;
		try{
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++)
			if(!fromExternal.get(i).getSum().isEmpty())
				sum += new Integer(fromExternal.get(i).getSum()).intValue();
		}
		catch(Exception e){
			System.out.println("Exception trying to convert financial support sum to integer");
		}
		return sum;
	}
	public int getSumFromAdmitanceFee(){
		int sum=0;
		try{
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++)
			if(!fromAdmitanceFee.get(i).getSum().isEmpty())
				sum += new Integer(fromAdmitanceFee.get(i).getSum()).intValue();
		}
		catch(Exception e){
			System.out.println("Exception trying to convert financial support sum to integer");
		}
		return sum;
	}
	
	public boolean getAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}


}
