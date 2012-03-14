package huard.iws.model;


public class CallOfProposal {
	private int id;
	private int fundId;
	private long publicationTimeMillis;
	private long submissionTimeMillis;
	private String title;
	private String amountOfGrant;
	private String deskId;
	private String deskAndContact;
	private String forms;
	private String description;
	private String budgetDetails;
	private String additionalInformation;
	private String pageWebAddress;
	private boolean descriptionOnly;




	public CallOfProposal(){
		this.id = 0;
		this.fundId = 0;
		this.publicationTimeMillis = 0;
		this.submissionTimeMillis = 0;
		this.title = "";
		this.amountOfGrant = "";
		this.deskId = "";
		this.deskAndContact="";
		this.forms="";
		this.description="";
		this.budgetDetails="";
		this.additionalInformation="";
		this.pageWebAddress="";
		this.descriptionOnly= false;
	}
	
	public String toString(){
			StringBuffer text = new StringBuffer();
			text.append(fundId+" * ");
			text.append(title+" * ");
			text.append(forms+" * ");
			text.append(description+" * ");
			text.append(amountOfGrant+" * ");
			text.append(budgetDetails+" * ");
			text.append(additionalInformation+" * ");
			return text.toString();
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
	
	public String getDeskAndContact() {
		return deskAndContact;
	}

	public void setDeskAndContact(String deskAndContact) {
		this.deskAndContact = deskAndContact;
	}
	
	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBudgetDetails() {
		return budgetDetails;
	}

	public void setBudgetDetails(String budgetDetails) {
		this.budgetDetails = budgetDetails;
	}

	
	public String getadditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getPageWebAddress() {
		return pageWebAddress;
	}

	public void setPageWebAddress(String string) {
		pageWebAddress = string;
	}
	
	public boolean isDescriptionOnly() {
		return descriptionOnly;
	}
	public void setDescriptionOnly(boolean descriptionOnly) {
		this.descriptionOnly = descriptionOnly;
	}
}
