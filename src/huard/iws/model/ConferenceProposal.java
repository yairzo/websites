package huard.iws.model;

import java.sql.Timestamp;

public class ConferenceProposal {

	private int id;
	private int personId;
	private int approverId;
	private String approverEvaluation;
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
	
	/*private int initiatingBody;
	private String initiatingBodyRole;*/
	private String subject;
	private Timestamp fromDate;
	private Timestamp toDate;
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


	public ConferenceProposal(){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		this.id = 0;
		this.personId = 0;
		this.approverId = 0;
		this.approverEvaluation="";
		this.description = "";
		this.versionId = 0;
		/*this.initiatingBody = 0;
		this.initiatingBodyRole = "";*/
		this.subject = "";
		this.fromDate = now;
		this.toDate = now;
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
	
	public Timestamp getFromDate() {
		return fromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
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


}
