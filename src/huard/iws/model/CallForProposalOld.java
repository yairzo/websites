package huard.iws.model;


public class CallForProposalOld {
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
	private boolean hasTabledVersion;
	private String docType;
	private String docOwner;
	private int restricted;
	private long stopRollingTimeMillis;
	private String subSite;
	private String subDateArd;
	private String subDateFund;
	private String subDateDetails;
	private int numOfCopies;
	private String fundingPeriod;
	private String eligibilityRequirements;
	private String activityLocation;
	private String possibleCollaboration;
	private boolean sendDigitalCopy;
	private boolean appendBudgetOfficerLine;
	
	public CallForProposalOld(){
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
		this.hasTabledVersion = false;
		this.docType="";
		this.docOwner="";
		this.restricted=0;
		this.stopRollingTimeMillis =0;
		this.subSite="";
		this.subDateArd="";
		this.subDateFund="";
		this.subDateDetails="";
		this.numOfCopies=0;
		this.fundingPeriod="";
		this.eligibilityRequirements="";
		this.activityLocation="";
		this.possibleCollaboration="";
		this.sendDigitalCopy=false;
		this.appendBudgetOfficerLine=false;
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
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocOwner() {
		return docOwner;
	}
	public void setDocOwner(String docOwner) {
		this.docOwner = docOwner;
	}
	public int getRestricted() {
		return restricted;
	}
	public void setRestricted(int restricted) {
		this.restricted = restricted;
	}
	public long getStopRollingTimeMillis() {
		return stopRollingTimeMillis;
	}

	public void setStopRollingTimeMillis(long stopRollingTimeMillis) {
		this.stopRollingTimeMillis = stopRollingTimeMillis;
	}

	public String getSubSite() {
		return subSite;
	}
	public void setSubSite(String subSite) {
		this.subSite = subSite;
	}

	public String getSubDateArd() {
		return subDateArd;
	}
	public void setSubDateArd(String subDateArd) {
		this.subDateArd = subDateArd;
	}

	public String getSubDateFund() {
		return subDateFund;
	}
	public void setSubDateFund(String subDateFund) {
		this.subDateFund = subDateFund;
	}

	public String getSubDateDetails() {
		return subDateDetails;
	}
	public void setSubDateDetails(String subDateDetails) {
		this.subDateDetails = subDateDetails;
	}


	public int getNumOfCopies() {
		return numOfCopies;
	}
	public void setNumOfCopies(int numOfCopies) {
		this.numOfCopies = numOfCopies;
	}

	public String getFundingPeriod() {
		return fundingPeriod;
	}
	public void setFundingPeriod(String fundingPeriod) {
		this.fundingPeriod = fundingPeriod;
	}

	public String getEligibilityRequirements() {
		return eligibilityRequirements;
	}
	public void setEligibilityRequirements(String eligibilityRequirements) {
		this.eligibilityRequirements = eligibilityRequirements;
	}

	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}

	public String getPossibleCollaboration() {
		return possibleCollaboration;
	}
	public void setPossibleCollaboration(String possibleCollaboration) {
		this.possibleCollaboration = possibleCollaboration;
	}

	public boolean getSendDigitalCopy() {
		return sendDigitalCopy;
	}
	public void setSendDigitalCopy(boolean sendDigitalCopy) {
		this.sendDigitalCopy = sendDigitalCopy;
	}

	public boolean getAppendBudgetOfficerLine() {
		return appendBudgetOfficerLine;
	}
	public void setAppendBudgetOfficerLine(boolean appendBudgetOfficerLine) {
		this.appendBudgetOfficerLine = appendBudgetOfficerLine;
	}

	public boolean getHasTabledVersion() {
		return hasTabledVersion;
	}
	public void setHasTabledVersion(boolean hasTabledVersion) {
		this.hasTabledVersion = hasTabledVersion;
	}


}
