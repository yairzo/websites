package huard.iws.model;

import huard.iws.bean.ConferenceProposalBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConferenceProposal {

	private int id;
	private int personId;
	private int approverId;
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
	private String initiatingBodyRole;
	private String subject;
	private Timestamp fromDate;
	private Timestamp toDate;
	private String location;
	private String locationDetail;
	private int foreignLecturers;
	private int localLecturers;
	private int audienceLecturers;
	private int foreigGuests;
	private int localGuests;
	private int audienceGuests;
	private byte [] guestsAttach;
	private String guestsAttachContentType;
	private byte [] programAttach;
	private String  programAttachContentType;
	private byte [] financialAttach;
	private String  financialAttachContentType;
	private String convensionDetails;
	private String approverOpinion;
	private Timestamp updateDate;*/


	public ConferenceProposal(){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		this.id = 0;
		this.personId = 0;
		this.approverId = 0;
		this.description = "";
		this.versionId = 0;
		/*this.initiatingBody = 0;
		this.initiatingBodyRole = "";
		this.subject = "";
		this.fromDate = now;
		this.toDate = now;
		this.location = "";
		this.locationDetail = "";
		this.foreignLecturers = 0;
		this.foreigGuests = 0;
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
		this.convensionDetails="";
		this.approverOpinion="";
		this.updateDate=now;*/
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


}
