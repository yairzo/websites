package huard.iws.bean;

import huard.iws.model.Attachment;
import huard.iws.model.CallOfProposal;
import huard.iws.model.Committee;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.model.AdditionalSubmissionDate;
import huard.iws.service.ConfigurationService;
import huard.iws.service.FundService;
import huard.iws.service.MessageService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallOfProposalBean {
	private int id;
	private String title;
	private int creatorId;
	private long creationTime;
	private long publicationTime;
	private String publicationTimeString;
	private long finalSubmissionTime;
	private String finalSubmissionTimeString;
	private boolean allYearSubmission;
	private boolean allYearSubmissionYearPassedAlert;
	private boolean hasAdditionalSubmissionDates;
	private int fundId;
	private int typeId;
	private long keepInRollingMessagesExpiryTime;
	private String keepInRollingMessagesExpiryTimeString;
	private int deskId;
	private String originalCallWebAddress;
	private boolean requireLogin;
	private boolean showDescriptionOnly;
	private String submissionDetails;
	private String contactPersonDetails;
	private String formDetails;
	private String description;
	private String fundingPeriod;
	private String amountOfGrant;
	private String eligibilityRequirements;
	private String activityLocation;
	private String possibleCollaboration;
	private String budgetDetails;
	private String additionalInformation;
	private List<Integer> subjectsIds;
	private List<Long> submissionDates;
	private List<AdditionalSubmissionDate> submissionDatesList;
	private List<Attachment> attachments;
	private String localeId;
	private long updateTime;
	
	private MessageService messageService;
	private ConfigurationService configurationService;
	private FundService fundService;
	private MopDeskService mopDeskService;	

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
		this.formDetails="";
		this.description="";
		this.fundingPeriod="";
		this.amountOfGrant="";
		this.eligibilityRequirements="";
		this.activityLocation="";
		this.possibleCollaboration="";
		this.budgetDetails="";
		this.additionalInformation="";
		this.submissionDates=new ArrayList<Long>();
		this.attachments=new ArrayList<Attachment>();
		this.localeId="";
		this.updateTime=0;
		this.submissionDatesList=new ArrayList<AdditionalSubmissionDate>();
		for (int i=0; i< 3; i++){
			AdditionalSubmissionDate additionalSubmissionDate = new AdditionalSubmissionDate();
			submissionDatesList.add(additionalSubmissionDate);
		}
	}


	public CallOfProposalBean(CallOfProposal callOfProposal, boolean applyObjs){
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
		this.formDetails=callOfProposal.getFormDetails();
		this.description=callOfProposal.getDescription();
		this.fundingPeriod=callOfProposal.getFundingPeriod();
		this.amountOfGrant = callOfProposal.getAmountOfGrant();
		this.eligibilityRequirements=callOfProposal.getEligibilityRequirements();
		this.activityLocation=callOfProposal.getActivityLocation();
		this.possibleCollaboration=callOfProposal.getPossibleCollaboration();
		this.budgetDetails=callOfProposal.getBudgetDetails();
		this.additionalInformation=callOfProposal.getAdditionalInformation();
		this.subjectsIds =callOfProposal.getSubjectsIds(); 
		this.submissionDates =callOfProposal.getSubmissionDates(); 
		this.attachments = callOfProposal.getAttachments();
		this.localeId=callOfProposal.getLocaleId();
		this.updateTime= callOfProposal.getUpdateTime();
		this.submissionDatesList=callOfProposal.getSubmissionDatesList();
		init(applyObjs);
		
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
		callOfProposal.setFormDetails(formDetails);
		callOfProposal.setDescription(description);
		callOfProposal.setFundingPeriod(fundingPeriod);
		callOfProposal.setAmountOfGrant(amountOfGrant);
		callOfProposal.setEligibilityRequirements(eligibilityRequirements);
		callOfProposal.setActivityLocation(activityLocation);
		callOfProposal.setPossibleCollaboration(possibleCollaboration);
		callOfProposal.setBudgetDetails(budgetDetails);
		callOfProposal.setAdditionalInformation(additionalInformation);
		callOfProposal.setSubjectsIds(subjectsIds);
		callOfProposal.setSubmissionDates(submissionDates);
		callOfProposal.setAttachments(attachments);
		callOfProposal.setLocaleId(localeId);
		callOfProposal.setUpdateTime(updateTime);
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
		this.publicationTime=publicationTime;
	}
	
	public String getPublicationTimeString() {
		if(publicationTime==0)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(publicationTime);
	}
	
	public void setPublicationTimeString(String publicationTimeString) {
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(publicationTimeString); 
			this.publicationTime = formattedDate.getTime();
		}
		catch(Exception e){
			this.publicationTime=0;
		}
	}

	public long getFinalSubmissionTime() {
		return finalSubmissionTime;
	}
	public void setFinalSubmissionTime(long finalSubmissionTime) {
		this.finalSubmissionTime=finalSubmissionTime;
	}

	public String getFinalSubmissionTimeString() {
		if(finalSubmissionTime==0)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(finalSubmissionTime);
	}
	
	public void setFinalSubmissionTimeString(String finalSubmissionTimeString) {
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(finalSubmissionTimeString); 
			this.finalSubmissionTime = formattedDate.getTime();
		}
		catch(Exception e){
			this.finalSubmissionTime=0;
		}
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
		this.keepInRollingMessagesExpiryTime=keepInRollingMessagesExpiryTime;
	}
	public String getKeepInRollingMessagesExpiryTimeString() {
		if(keepInRollingMessagesExpiryTime==0)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(keepInRollingMessagesExpiryTime);
	}
	
	public void setKeepInRollingMessagesExpiryTimeString(String keepInRollingMessagesExpiryTimeString) {
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date formattedDate = (Date)formatter.parse(keepInRollingMessagesExpiryTimeString); 
			this.keepInRollingMessagesExpiryTime = formattedDate.getTime();
		}
		catch(Exception e){
			this.keepInRollingMessagesExpiryTime=0;
		}
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

	public String getFormDetails() {
		return formDetails;
	}

	public void setFormDetails(String formDetails) {
		this.formDetails = formDetails;
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
	
	public List<Long> getSubmissionDates() {
		return submissionDates;
	}

	public void setSubmissionDates(List<Long> submissionDates) {
		this.submissionDates = submissionDates;
	}

	
	public List<AdditionalSubmissionDate> getSubmissionDatesList() {
		for (int i=0; i< 3; i++){
			AdditionalSubmissionDate additionalSubmissionDate = new AdditionalSubmissionDate();
			if(submissionDates.size()>i){
				Long submissionDate = submissionDates.get(i);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				additionalSubmissionDate.setSubmissionDate(formatter.format(submissionDate));
				submissionDatesList.set(i, additionalSubmissionDate);
			}
			else{
				additionalSubmissionDate.setSubmissionDate("");
				submissionDatesList.add(additionalSubmissionDate);
			}
		}
		return submissionDatesList; 
	}

	public void setSubmissionDatesList(List<AdditionalSubmissionDate> submissionDatesList) {
		this.submissionDatesList = submissionDatesList;	
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	
	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public Map <Integer, Attachment> getAttachmentsMap(){
		Map<Integer, Attachment> attachmentsMap = new HashMap<Integer, Attachment>();
		for (Attachment attachment: this.attachments){
			attachmentsMap.put(attachment.getId(), attachment);
		}
		return attachmentsMap;
	}
	public PersonBean getCreator() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean creator = new PersonBean(
				personService.getPerson(this.creatorId));
		return creator;
	}
	
	public boolean getExpired() {
		if (finalSubmissionTime < new Date().getTime()+86400000L && finalSubmissionTime != 0)
			return true;
		else
			return false;
	}
	
	public void init(boolean applyObjs){
		if (applyObjs){
			fundService = (FundService) ApplicationContextProvider.getContext().getBean("fundService");
			messageService = (MessageService) ApplicationContextProvider.getContext().getBean("messageService");
			configurationService = (ConfigurationService) ApplicationContextProvider.getContext().getBean("configurationService");
			mopDeskService = (MopDeskService) ApplicationContextProvider.getContext().getBean("mopDeskService");
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" <a class=\"big\" href=\"http://" + configurationService.getConfigurationString("website", "webServer") +
				"/huard/infoPageViewer.jsp?ardNum=" + this.id + "\">" + title + "</a><br/> ");
		sb.append("<span class=\"medium\">");
		if (fundId != 0){
		    Fund fund= fundService.getArdFund(fundId);
			sb.append(" <a class=\"bold\" href=\"http://" + fund.getWebAddress() + "\">" + fund.getName() + ", " + fund.getShortName() + "</a>;;");
		}
		else
			sb.append("# Funding agency #");
		if(finalSubmissionTime==0)
			sb.append(messageService.getMessage("general.callOfProposal.submission", "iw_IL") + ": "
					+ messageService.getMessage("general.callOfProposal.submissionAllYear", "iw_IL") + " <br/> ");
		else	
			sb.append(messageService.getMessage("general.callOfProposal.submission", "iw_IL") + ": "
				+ DateUtils.getLocaleDependentShortDateFormat(finalSubmissionTime, "iw_IL") + " <br/> ");
		if (! amountOfGrant.isEmpty())
			sb.append(messageService.getMessage("general.callOfProposal.amountOfGrant", "iw_IL") + ": " + amountOfGrant.trim() + ";;");
		sb.append(messageService.getMessage("general.callOfProposal.successIndex", "iw_IL") + ": xxxxx;;");
		sb.append(" " +messageService.getMessage("general.callOfProposal.deskPrefix", "iw_IL"));
		sb.append("<a class=\"underline\" href=\"mailto:" + getCreator().getEmail() + "\">"+getCreator().getDegreeFullNameHebrew()+"</a>");
		//sb.append("#mu# #mp##mue#");
		if (deskId != 0){
			MopDesk mopDesk = mopDeskService.getMopDesk(deskId);
			sb.append(", <span class=\"bold\">" + mopDesk.getHebrewName() + "." + "</span>");
		}
		String str=sb.toString();
		str=str.replace("<p>","");
		str=str.replace("</p>","");
		str="<p dir=\"rtl\">" + str + "</p>";
		return str;
	}

}
