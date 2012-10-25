package huard.iws.bean;

import java.util.List;

import huard.iws.model.CallOfProposal;

public class CallOfProposalBean {
	private int id;
	private String title;
	private int creatorId;
	private long creationTime;
	private long publicationTime;
	private long finalSubmissionTime;
	private boolean allYearSubmission;
	private boolean allYearSubmissionYearPassedAlert;
	private boolean hasAdditionalSubmissionDates;
	private int fundId;
	private int typeId;
	private long keepInRollingMessagesExpiryTime;
	private int deskId;
	private String originalCallWebAddress;
	private boolean requireLogin;
	private boolean showDescriptionOnly;
	private String submissionDetails;
	private String contactPersonDetails;
	private String description;
	private String fundingPeriod;
	private String amountOfGrant;
	private String eligibilityRequirements;
	private String activityLocation;
	private String possibleCollaboration;
	private String budgetDetails;
	private String additionalInformation;
	private List<Integer> subjectsIds;

	public CallOfProposalBean(){
		this.id = 0;
		this.title = "";
		this.creatorId = 0;
		this.creationTime = 0;
		this.publicationTime = 0;
		this.finalSubmissionTime = 0;
		this.allYearSubmission = false;
		this.allYearSubmissionYearPassedAlert=true;
		this.hasAdditionalSubmissionDates=false;
		this.fundId=0;
		this.typeId=0;
		this.keepInRollingMessagesExpiryTime=0;
		this.deskId=0;
		this.originalCallWebAddress="";
		this.requireLogin=false;
		this.showDescriptionOnly=true;
		this.submissionDetails="";
		this.contactPersonDetails="";
		this.description="";
		this.fundingPeriod="";
		this.amountOfGrant="";
		this.eligibilityRequirements="";
		this.activityLocation="";
		this.possibleCollaboration="";
		this.budgetDetails="";
		this.additionalInformation="";
	}


	public CallOfProposalBean(CallOfProposal callOfProposal){
		this.id = callOfProposal.getId();
		this.title = callOfProposal.getTitle();
		this.creatorId = callOfProposal.getCreatorId();
		this.creationTime = callOfProposal.getCreationTime();
		this.publicationTime = callOfProposal.getPublicationTime();
		this.finalSubmissionTime =callOfProposal.getFinalSubmissionTime();
		this.allYearSubmission = callOfProposal.getAllYearSubmission();
		this.allYearSubmissionYearPassedAlert=callOfProposal.getAllYearSubmissionYearPassedAlert();
		this.hasAdditionalSubmissionDates=callOfProposal.getHasAdditionalSubmissionDates();
		this.fundId = callOfProposal.getFundId();
		this.typeId=callOfProposal.getTypeId();
		this.keepInRollingMessagesExpiryTime=callOfProposal.getKeepInRollingMessagesExpiryTime();
		this.deskId = callOfProposal.getDeskId();
		this.originalCallWebAddress=callOfProposal.getOriginalCallWebAddress();
		this.requireLogin=callOfProposal.getRequireLogin();
		this.showDescriptionOnly=callOfProposal.getShowDescriptionOnly();
		this.submissionDetails=callOfProposal.getSubmissionDetails();
		this.contactPersonDetails=callOfProposal.getContactPersonDetails();
		this.description=callOfProposal.getDescription();
		this.fundingPeriod=callOfProposal.getFundingPeriod();
		this.amountOfGrant = callOfProposal.getAmountOfGrant();
		this.eligibilityRequirements=callOfProposal.getEligibilityRequirements();
		this.activityLocation=callOfProposal.getActivityLocation();
		this.possibleCollaboration=callOfProposal.getPossibleCollaboration();
		this.budgetDetails=callOfProposal.getBudgetDetails();
		this.additionalInformation=callOfProposal.getAdditionalInformation();
		this.subjectsIds =callOfProposal.getSubjectsIds(); 
	}

	public CallOfProposal toCallOfProposal(){
		CallOfProposal callOfProposal = new CallOfProposal();
		callOfProposal.setId(id);
		callOfProposal.setTitle(title);
		callOfProposal.setCreatorId(creatorId);
		callOfProposal.setCreationTime(creationTime);
		callOfProposal.setPublicationTime(publicationTime);
		callOfProposal.setFinalSubmissionTime(finalSubmissionTime);
		callOfProposal.setAllYearSubmission(allYearSubmission);
		callOfProposal.setAllYearSubmissionYearPassedAlert(allYearSubmissionYearPassedAlert);
		callOfProposal.setHasAdditionalSubmissionDates(hasAdditionalSubmissionDates);
		callOfProposal.setFundId(fundId);
		callOfProposal.setTypeId(typeId);
		callOfProposal.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
		callOfProposal.setDeskId(deskId);
		callOfProposal.setOriginalCallWebAddress(originalCallWebAddress);
		callOfProposal.setRequireLogin(requireLogin);
		callOfProposal.setShowDescriptionOnly(showDescriptionOnly);
		callOfProposal.setSubmissionDetails(submissionDetails);
		callOfProposal.setContactPersonDetails(contactPersonDetails);
		callOfProposal.setDescription(description);
		callOfProposal.setFundingPeriod(fundingPeriod);
		callOfProposal.setAmountOfGrant(amountOfGrant);
		callOfProposal.setEligibilityRequirements(eligibilityRequirements);
		callOfProposal.setActivityLocation(activityLocation);
		callOfProposal.setPossibleCollaboration(possibleCollaboration);
		callOfProposal.setBudgetDetails(budgetDetails);
		callOfProposal.setAdditionalInformation(additionalInformation);
		callOfProposal.setSubjectsIds(subjectsIds);
		return callOfProposal;
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

	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getPublicationTime() {
		return publicationTime;
	}
	public void setPublicationTime(long publicationTime) {
		this.publicationTime = publicationTime;
	}

	public long getFinalSubmissionTime() {
		return finalSubmissionTime;
	}
	public void setFinalSubmissionTime(long finalSubmissionTime) {
		this.finalSubmissionTime = finalSubmissionTime;
	}

	public boolean getAllYearSubmission() {
		return allYearSubmission;
	}
	public void setAllYearSubmission(boolean allYearSubmission) {
		this.allYearSubmission = allYearSubmission;
	}

	public boolean getAllYearSubmissionYearPassedAlert() {
		return allYearSubmissionYearPassedAlert;
	}
	public void setAllYearSubmissionYearPassedAlert(boolean allYearSubmissionYearPassedAlert) {
		this.allYearSubmissionYearPassedAlert = allYearSubmissionYearPassedAlert;
	}

	public boolean getHasAdditionalSubmissionDates() {
		return hasAdditionalSubmissionDates;
	}
	public void setHasAdditionalSubmissionDates(boolean hasAdditionalSubmissionDates) {
		this.hasAdditionalSubmissionDates = hasAdditionalSubmissionDates;
	}

	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public long getKeepInRollingMessagesExpiryTime() {
		return keepInRollingMessagesExpiryTime;
	}
	
	public void setKeepInRollingMessagesExpiryTime(long keepInRollingMessagesExpiryTime) {
		this.keepInRollingMessagesExpiryTime = keepInRollingMessagesExpiryTime;
	}

	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}

	public String getOriginalCallWebAddress() {
		return originalCallWebAddress;
	}
	public void setOriginalCallWebAddress(String originalCallWebAddress) {
		this.originalCallWebAddress = originalCallWebAddress;
	}

	public boolean getRequireLogin() {
		return requireLogin;
	}
	public void setRequireLogin(boolean requireLogin) {
		this.requireLogin = requireLogin;
	}

	public boolean getShowDescriptionOnly() {
		return showDescriptionOnly;
	}
	public void setShowDescriptionOnly(boolean showDescriptionOnly) {
		this.showDescriptionOnly = showDescriptionOnly;
	}

	public String getSubmissionDetails() {
		return submissionDetails;
	}

	public void setSubmissionDetails(String submissionDetails) {
		this.submissionDetails = submissionDetails;
	}
	
	public String getContactPersonDetails() {
		return contactPersonDetails;
	}

	public void setContactPersonDetails(String contactPersonDetails) {
		this.contactPersonDetails = contactPersonDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFundingPeriod() {
		return fundingPeriod;
	}

	public void setFundingPeriod(String fundingPeriod) {
		this.fundingPeriod = fundingPeriod;
	}
	
	public String getAmountOfGrant() {
		return amountOfGrant;
	}

	public void setAmountOfGrant(String amountOfGrant) {
		this.amountOfGrant = amountOfGrant;
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

	public String getBudgetDetails() {
		return budgetDetails;
	}

	public void setBudgetDetails(String budgetDetails) {
		this.budgetDetails = budgetDetails;
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public List<Integer> getSubjectsIds() {
		return subjectsIds;
	}

	public void setSubjectsIds(List<Integer> subjectsIds) {
		this.subjectsIds = subjectsIds;
	}


}
