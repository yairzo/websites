package huard.iws.model;

public class Committee {
	private int id;
	private int conferenceProposalId;
	private String name;
	private String institute;
	private String instituteRole;
	private String committeeRole;
	private int type;
	
	public static int TYPE_SCIENTIFIC = 1;
	public static int TYPE_OPERATIONAL = 2;
	
	public Committee(){
		this.id = 0;
		this.conferenceProposalId = 0;
		this.name = "";
		this.institute = "";
		this.instituteRole = "";
		this.committeeRole = "";
		this.type = 0;
	}

	public boolean isEmpty(){
		return name.isEmpty() && institute.isEmpty()
				&& instituteRole.isEmpty() && committeeRole.isEmpty();
	}
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
