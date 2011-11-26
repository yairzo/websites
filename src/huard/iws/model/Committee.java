package huard.iws.model;

public class Committee {
	private int id;
	private int conferenceProposalId;
	private String name;
	private String institute;
	private String instituteRole;
	private String committeeRole;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConferenceProposalId() {
		return conferenceProposalId;
	}
	public void setConferenceProposalId(int conferenceProposalId) {
		this.conferenceProposalId = conferenceProposalId;
	}
	public String getInstituteRole() {
		return instituteRole;
	}
	public void setInstituteRole(String instituteRole) {
		this.instituteRole = instituteRole;
	}
	public String getCommitteeRole() {
		return committeeRole;
	}
	public void setCommitteeRole(String committeeRole) {
		this.committeeRole = committeeRole;
	}


}
