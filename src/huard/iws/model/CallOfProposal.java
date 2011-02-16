package huard.iws.model;

public class CallOfProposal {
	private int id;
	private int fundId;
	private long publicationTimeMillis;
	private long submissionTimeMillis;
	private String title;
	private String amountOfGrant;
	private String deskId;



	public CallOfProposal(){
		this.id = 0;
		this.fundId = 0;
		this.publicationTimeMillis = 0;
		this.submissionTimeMillis = 0;
		this.title = "";
		this.amountOfGrant = "";
		this.deskId = "";
	}

	public String getAmountOfGrant() {
		return amountOfGrant;
	}
	public void setAmountOfGrant(String amountOfGrant) {
		this.amountOfGrant = amountOfGrant;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public long getSubmissionTimeMillis() {
		return submissionTimeMillis;
	}

	public void setSubmissionTimeMillis(long submissionTimeMillis) {
		this.submissionTimeMillis = submissionTimeMillis;
	}
	public String getDeskId() {
		return deskId;
	}

	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	public long getPublicationTimeMillis() {
		return publicationTimeMillis;
	}

	public void setPublicationTimeMillis(long publicationTimeMillis) {
		this.publicationTimeMillis = publicationTimeMillis;
	}
}
