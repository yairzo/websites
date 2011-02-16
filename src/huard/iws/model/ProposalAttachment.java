package huard.iws.model;

public class ProposalAttachment {
	private int id;
	private int proposalId;
	private String title;
	private byte [] file;
	private String contentType;
	private int place;

	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}


}
