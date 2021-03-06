package huard.iws.bean;

import huard.iws.model.Committee;
import huard.iws.model.ConferenceProposal;
import huard.iws.model.FinancialSupport;
import huard.iws.model.Language;
import huard.iws.service.PersonService;
import huard.iws.service.ConferenceProposalService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.RequestWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ConferenceProposalBean {
	
	private final int MAX_NUM_COMMITTEE = 10;
	private final int MAX_NUM_FINANCIAL_SUPPORT = 10;
	//private static final Logger logger = Logger.getLogger(ConferenceProposalBean.class);
	private  static Map<String, Integer> statusMap ;

	static{
		statusMap = new HashMap<String, Integer>();
		statusMap.put("STATUS_DRAFT", 0);
		statusMap.put("STATUS_SUBMITTED", 1);
		statusMap.put("STATUS_SENT_TO_APPROVER", 2);
		statusMap.put("STATUS_GRADED", 3);
		statusMap.put("STATUS_READY_FOR_CONFERENCE", 4);
		statusMap.put("STATUS_DISCUSSED_IN_CONFERENCE", 5);
		statusMap.put("STATUS_FINISHED_TREATMENT", 6);
		statusMap.put("STATUS_DELETED", 7);
	}

	

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
	private List<FinancialSupportBean> fromAssosiate;
	private List<FinancialSupportBean> fromExternal;
	private List<FinancialSupportBean> fromAdmitanceFee;
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
	private boolean approverVerified;


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
		this.fromAssosiate = new ArrayList<FinancialSupportBean>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupport = new FinancialSupportBean();
			financialSupport.setType("" + FinancialSupport.TYPE_ASSOSIATE);
			fromAssosiate.add(financialSupport);
		}
		this.fromExternal = new ArrayList<FinancialSupportBean>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupport = new FinancialSupportBean();
			financialSupport.setType("" + FinancialSupport.TYPE_EXTERNAL);
			fromExternal.add(financialSupport);
		}
		this.fromAdmitanceFee = new ArrayList<FinancialSupportBean>();
		for (int i=0; i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupport = new FinancialSupportBean();
			financialSupport.setType("" + FinancialSupport.TYPE_ADMITANCEFEE);
			fromAdmitanceFee.add(financialSupport);
		}
		this.deleted =false;
		this.deadlineRemarks="";
		this.isInsideDeadline =true;
		this.committeeRemarks ="";
		this.companyAttach = new byte[0];
		this.companyAttachContentType = "";
		this.acceptTerms =false;
		this.creatorId=0;
		this.statusId=0;
		this.statusDate=0;
		this.approverVerified=false;
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
		this.foreignGuests = conferenceProposal.getForeignGuests();
		this.localGuests = conferenceProposal.getLocalGuests();
		this.foreignAudience = conferenceProposal.getForeignAudience();
		this.localAudience = conferenceProposal.getLocalAudience();
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
		fromAssosiate = new ArrayList<FinancialSupportBean>();
		for (FinancialSupport financialSupport: conferenceProposal.getFromAssosiate()){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean(financialSupport);
			fromAssosiate.add(financialSupportBean);
		}
		for (int i=fromAssosiate.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean();
			financialSupportBean.setConferenceProposalId("" + conferenceProposal.getId());
			financialSupportBean.setType("" + FinancialSupport.TYPE_ASSOSIATE);
			fromAssosiate.add(financialSupportBean);
		}
		fromExternal = new ArrayList<FinancialSupportBean>();
		for (FinancialSupport financialSupport: conferenceProposal.getFromExternal()){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean(financialSupport);
			fromExternal.add(financialSupportBean);
		}
		for (int i=fromExternal.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean();
			financialSupportBean.setConferenceProposalId("" + conferenceProposal.getId());
			financialSupportBean.setType("" + FinancialSupport.TYPE_EXTERNAL);
			fromExternal.add(financialSupportBean);
		}
		fromAdmitanceFee = new ArrayList<FinancialSupportBean>();
		for (FinancialSupport financialSupport: conferenceProposal.getFromAdmitanceFee()){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean(financialSupport);
			fromAdmitanceFee.add(financialSupportBean);
		}
		for (int i=fromAdmitanceFee.size(); i< MAX_NUM_FINANCIAL_SUPPORT; i++){
			FinancialSupportBean financialSupportBean = new FinancialSupportBean();
			financialSupportBean.setConferenceProposalId("" + conferenceProposal.getId());
			financialSupportBean.setType("" + FinancialSupport.TYPE_ADMITANCEFEE);
			fromAdmitanceFee.add(financialSupportBean);
		}
		this.deleted = conferenceProposal.getDeleted();
		this.deadlineRemarks = conferenceProposal.getDeadlineRemarks();
		this.isInsideDeadline =conferenceProposal.getIsInsideDeadline();
		this.committeeRemarks = conferenceProposal.getCommitteeRemarks();
		this.companyAttach = conferenceProposal.getCompanyAttach();
		this.companyAttachContentType = conferenceProposal.getCompanyAttachContentType();
		this.acceptTerms =conferenceProposal.getAcceptTerms();
		this.creatorId = conferenceProposal.getCreatorId();
		this.statusId=conferenceProposal.getStatusId();
		this.statusDate=conferenceProposal.getStatusDate();
		this.approverVerified=conferenceProposal.getApproverVerified();
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
		conferenceProposal.setForeignGuests(foreignGuests);
		conferenceProposal.setLocalGuests(localGuests);
		conferenceProposal.setForeignAudience(foreignAudience);
		conferenceProposal.setLocalAudience(localAudience);		
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
		for (FinancialSupportBean financialSupportBean: fromAssosiate){
			FinancialSupport financialSupport = financialSupportBean.toFinancialSupport();
			
			conferenceProposal.getFromAssosiate().add(financialSupport);
		}
		for (FinancialSupportBean financialSupportBean: fromExternal){
			FinancialSupport financialSupport = financialSupportBean.toFinancialSupport();
			conferenceProposal.getFromExternal().add(financialSupport);
		}
		for (FinancialSupportBean financialSupportBean: fromAdmitanceFee){
			FinancialSupport financialSupport = financialSupportBean.toFinancialSupport();
			conferenceProposal.getFromAdmitanceFee().add(financialSupport);
		}
		conferenceProposal.setDeleted(deleted);
		conferenceProposal.setDeadlineRemarks(deadlineRemarks);
		conferenceProposal.setIsInsideDeadline(isInsideDeadline);
		conferenceProposal.setCommitteeRemarks(committeeRemarks);
		conferenceProposal.setCompanyAttach(companyAttach);
		conferenceProposal.setCompanyAttachContentType(companyAttachContentType);
		conferenceProposal.setAcceptTerms(acceptTerms);
		conferenceProposal.setCreatorId(creatorId);
		conferenceProposal.setStatusId(statusId);
		conferenceProposal.setStatusDate(statusDate);
		conferenceProposal.setApproverVerified(approverVerified);
		return conferenceProposal;
	}

	public static Integer getStatusMapId(String statusDesc){
		if (statusMap.containsKey(statusDesc))
			return statusMap.get(statusDesc);
		else
			return -1;
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
	public PersonBean getCreator() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean creator = new PersonBean(
				personService.getPerson(this.creatorId));
		return creator;
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

	public List<FinancialSupportBean> getFromAssosiate() {
		return fromAssosiate;
	}

	public void setFromAssosiate(List<FinancialSupportBean> fromAssosiate) {
		this.fromAssosiate = fromAssosiate;
	}

	public List<FinancialSupportBean> getFromExternal() {
		return fromExternal;
	}

	public void setFromExternal(List<FinancialSupportBean> fromExternal) {
		this.fromExternal = fromExternal;
	}

	public List<FinancialSupportBean> getFromAdmitanceFee() {
		return fromAdmitanceFee;
	}

	public void setFromAdmitanceFee(List<FinancialSupportBean> fromAdmitanceFee) {
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

	public String getStatus() {
		ConferenceProposalService conferenceProposalService = (ConferenceProposalService) ApplicationContextProvider
				.getContext().getBean("conferenceProposalService");
		return conferenceProposalService.getStatusMap().get(statusId);
	}
	
	public long getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(long statusDate) {
		this.statusDate = statusDate;
	}
	public String getFormattedStatusDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date statusDate = new Date(this.statusDate);
		return formatter.format(statusDate);
	}
	public boolean getApproverVerified() {
		return approverVerified;
	}
	public void setApproverVerified(boolean approverVerified) {
		this.approverVerified = approverVerified;
	}


}
