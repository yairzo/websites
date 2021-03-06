package huard.iws.bean;

import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.model.Language;
import huard.iws.service.CallForProposalService;
import huard.iws.service.ConfigurationService;
import huard.iws.service.FundService;
import huard.iws.service.MessageService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;
import huard.iws.util.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallForProposalBean {
	private int id;
	private String title;
	private String urlTitle;
	private int creatorId;
	private long creationTime;
	private long publicationTime;
	private String publicationTimeString;
	private long finalSubmissionTime;
	private String finalSubmissionTimeString;
	private String finalSubmissionHour;
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
	private String contactPersons;
	private String contactPersonDetails;
	private String fundContact;
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
	private List<String> submissionDatesList;
	private List<Attachment> attachments;
	private String localeId;
	private long updateTime;
	private int isDeleted;
	private int targetAudience;
	private List<Integer> countryIds;
	private int hourType;
	
	private CallForProposalService callForProposalService;
	private MessageService messageService;
	private ConfigurationService configurationService;
	private FundService fundService;
	private MopDeskService mopDeskService;	

	public CallForProposalBean(){
		this.id = 0;
		this.title = "";
		this.urlTitle = "";
		this.creatorId = 0;
		this.creationTime = 0;
		this.publicationTime = 0;
		this.finalSubmissionTime = 0;
		this.finalSubmissionHour = "";
		this.allYearSubmission = false;
		this.allYearSubmissionYearPassedAlert=true;
		this.hasAdditionalSubmissionDates=false;
		this.fundId=0;
		this.typeId=0;
		this.keepInRollingMessagesExpiryTime=0;
		this.deskId=0;
		this.originalCallWebAddress="";
		this.requireLogin=false;
		this.showDescriptionOnly=false;
		this.submissionDetails="";
		this.contactPersons="";
		this.contactPersonDetails="";
		this.fundContact="";
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
		this.submissionDatesList=new ArrayList<String>();
		this.isDeleted=0;
		this.targetAudience=0;
		for (int i=0; i< 3; i++){
			submissionDatesList.add("");
		}
		this.countryIds=new ArrayList<Integer>();
		this.hourType=0;

	}


	public CallForProposalBean(CallForProposal callForProposal, boolean applyObjs){
		this.id = callForProposal.getId();
		this.title = callForProposal.getTitle();
		this.urlTitle = callForProposal.getUrlTitle();
		this.creatorId = callForProposal.getCreatorId();
		this.creationTime = callForProposal.getCreationTime();
		this.publicationTime = callForProposal.getPublicationTime();
		this.finalSubmissionTime =callForProposal.getFinalSubmissionTime();
		this.finalSubmissionHour = callForProposal.getFinalSubmissionHour();
		this.allYearSubmission = callForProposal.getAllYearSubmission();
		this.allYearSubmissionYearPassedAlert=callForProposal.getAllYearSubmissionYearPassedAlert();
		this.hasAdditionalSubmissionDates=callForProposal.getHasAdditionalSubmissionDates();
		this.fundId = callForProposal.getFundId();
		this.typeId=callForProposal.getTypeId();
		this.keepInRollingMessagesExpiryTime=callForProposal.getKeepInRollingMessagesExpiryTime();
		this.deskId = callForProposal.getDeskId();
		this.originalCallWebAddress=callForProposal.getOriginalCallWebAddress();
		this.requireLogin=callForProposal.getRequireLogin();
		this.showDescriptionOnly=callForProposal.getShowDescriptionOnly();
		this.submissionDetails=callForProposal.getSubmissionDetails();
		this.contactPersons=callForProposal.getContactPersons();
		this.contactPersonDetails=callForProposal.getContactPersonDetails();
		this.fundContact=callForProposal.getFundContact();
		this.formDetails=callForProposal.getFormDetails();
		this.description=callForProposal.getDescription();
		this.fundingPeriod=callForProposal.getFundingPeriod();
		this.amountOfGrant = callForProposal.getAmountOfGrant();
		this.eligibilityRequirements=callForProposal.getEligibilityRequirements();
		this.activityLocation=callForProposal.getActivityLocation();
		this.possibleCollaboration=callForProposal.getPossibleCollaboration();
		this.budgetDetails=callForProposal.getBudgetDetails();
		this.additionalInformation=callForProposal.getAdditionalInformation();
		this.subjectsIds =callForProposal.getSubjectsIds(); 
		this.submissionDates =callForProposal.getSubmissionDates(); 
		this.attachments = callForProposal.getAttachments();
		this.localeId=callForProposal.getLocaleId();
		this.updateTime= callForProposal.getUpdateTime();
		this.submissionDatesList=callForProposal.getSubmissionDatesList();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (long submissionDate: callForProposal.getSubmissionDates()){
            if(submissionDate>1000)
             	this.submissionDatesList.add(formatter.format(submissionDate));
        }	
        while (submissionDatesList.size() < 3)
        	this.submissionDatesList.add("");
        this.isDeleted=callForProposal.getIsDeleted();
        this.targetAudience=callForProposal.getTargetAudience();
        this.countryIds=callForProposal.getCountryIds();
        this.hourType=callForProposal.getHourType();
        init(applyObjs);
	}

	public CallForProposal toCallForProposal(){
		CallForProposal callForProposal = new CallForProposal();
		callForProposal.setId(id);
		callForProposal.setTitle(title);
		callForProposal.setUrlTitle(urlTitle);
		callForProposal.setCreatorId(creatorId);
		callForProposal.setCreationTime(creationTime);
		callForProposal.setPublicationTime(publicationTime);
		callForProposal.setFinalSubmissionTime(finalSubmissionTime);
		callForProposal.setFinalSubmissionHour(finalSubmissionHour);
		callForProposal.setAllYearSubmission(allYearSubmission);
		callForProposal.setAllYearSubmissionYearPassedAlert(allYearSubmissionYearPassedAlert);
		callForProposal.setHasAdditionalSubmissionDates(hasAdditionalSubmissionDates);
		callForProposal.setFundId(fundId);
		callForProposal.setTypeId(typeId);
		callForProposal.setKeepInRollingMessagesExpiryTime(keepInRollingMessagesExpiryTime);
		callForProposal.setDeskId(deskId);
		callForProposal.setOriginalCallWebAddress(originalCallWebAddress);
		callForProposal.setRequireLogin(requireLogin);
		callForProposal.setShowDescriptionOnly(showDescriptionOnly);
		callForProposal.setSubmissionDetails(submissionDetails);
		callForProposal.setContactPersons(contactPersons);
		callForProposal.setContactPersonDetails(contactPersonDetails);
		callForProposal.setFundContact(fundContact);
		callForProposal.setFormDetails(formDetails);
		callForProposal.setDescription(description);
		callForProposal.setFundingPeriod(fundingPeriod);
		callForProposal.setAmountOfGrant(amountOfGrant);
		callForProposal.setEligibilityRequirements(eligibilityRequirements);
		callForProposal.setActivityLocation(activityLocation);
		callForProposal.setPossibleCollaboration(possibleCollaboration);
		callForProposal.setBudgetDetails(budgetDetails);
		callForProposal.setAdditionalInformation(additionalInformation);
		callForProposal.setSubjectsIds(subjectsIds);
		callForProposal.setSubmissionDates(submissionDates);
		callForProposal.setAttachments(attachments);
		callForProposal.setLocaleId(localeId);
		callForProposal.setUpdateTime(updateTime);
		List<Long> submissionDates= new ArrayList<Long>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (String additionalSubmissionDate: submissionDatesList){
			try{
				Date formattedDate = (Date)formatter.parse(additionalSubmissionDate); 
				submissionDates.add(formattedDate.getTime());
			}
			catch(Exception e){
				submissionDates.add(new Long(1000));
			}
	    }	
		callForProposal.setSubmissionDates(submissionDates);
		callForProposal.setIsDeleted(isDeleted);
		callForProposal.setTargetAudience(targetAudience);
		callForProposal.setCountryIds(countryIds);
		callForProposal.setHourType(hourType);
		return callForProposal;
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
	
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
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
	
	public String getFinalSubmissionHour() {
		return finalSubmissionHour;
	}
	public void setFinalSubmissionHour(String finalSubmissionHour) {
		this.finalSubmissionHour = finalSubmissionHour;
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

	public Fund getFund(){
		Fund fund=new Fund();
		try{
			fund=fundService.getFund(fundId);
		}
		catch(Exception e){
			return fund;
		}
		return fund;
	}

	public MopDesk getDesk(){
		MopDesk desk=new MopDesk();
		try{
			desk=mopDeskService.getMopDesk(deskId);
		}
		catch(Exception e){
			return desk;
		}
		return desk;
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
		return TextUtils.cleanBrFromEditor(submissionDetails);
	}

	public void setSubmissionDetails(String submissionDetails) {
		this.submissionDetails = submissionDetails;
	}

	public String getContactPersons() {
		return TextUtils.cleanBrFromEditor(contactPersons);
	}

	public void setContactPersons(String contactPersons) {
		this.contactPersons = contactPersons;
	}

	public String getContactPersonDetails() {
		return TextUtils.cleanBrFromEditor(contactPersonDetails);
	}

	public void setContactPersonDetails(String contactPersonDetails) {
		this.contactPersonDetails = contactPersonDetails;
	}

	public String getFundContact() {
		return TextUtils.cleanBrFromEditor(fundContact);
	}

	public void setFundContact(String fundContact) {
		this.fundContact = fundContact;
	}
	public String getFormDetails() {
		return TextUtils.cleanBrFromEditor(formDetails);
	}

	public void setFormDetails(String formDetails) {
		this.formDetails = formDetails;
	}


	public String getDescription() {
		return TextUtils.cleanBrFromEditor(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFundingPeriod() {
		return fundingPeriod;
	}

	public String getStrippedFundingPeriod() {
		return TextUtils.cleanHtmlFromEditor(fundingPeriod);
	}
	public boolean isFundingPeriodBullets() {
		return fundingPeriod.trim().endsWith("</ul>");
	}

	public void setFundingPeriod(String fundingPeriod) {
		this.fundingPeriod = fundingPeriod;
	}
	
	public String getAmountOfGrant() {
		return amountOfGrant;
	}

	public String getStrippedAmountOfGrant() {
		return TextUtils.cleanHtmlFromEditor(amountOfGrant);
	}
	public boolean isAmountOfGrantBullets() {
		return amountOfGrant.trim().endsWith("</ul>");
	}
	public void setAmountOfGrant(String amountOfGrant) {
		this.amountOfGrant = amountOfGrant;
	}

	public String getEligibilityRequirements() {
		return eligibilityRequirements;
	}

	public String getStrippedEligibilityRequirements() {
		return TextUtils.cleanHtmlFromEditor(eligibilityRequirements);
	}
	public boolean isEligibilityRequirementsBullets() {
		return eligibilityRequirements.trim().endsWith("</ul>");
	}

	public void setEligibilityRequirements(String eligibilityRequirements) {
		this.eligibilityRequirements = eligibilityRequirements;
	}

	public String getActivityLocation() {
		return activityLocation;
	}
	
	public String getStrippedActivityLocation() {
		return TextUtils.cleanHtmlFromEditor(activityLocation);
	}
	public boolean isActivityLocationBullets() {
		return activityLocation.trim().endsWith("</ul>");
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}

	public String getPossibleCollaboration() {
		return possibleCollaboration;
	}

	public String getStrippedPossibleCollaboration() {
		return TextUtils.cleanHtmlFromEditor(possibleCollaboration);
	}
	public boolean isPossibleCollaborationBullets() {
		return possibleCollaboration.trim().endsWith("</ul>");
	}

	public void setPossibleCollaboration(String possibleCollaboration) {
		this.possibleCollaboration = possibleCollaboration;
	}

	public String getBudgetDetails() {
		return TextUtils.cleanBrFromEditor(budgetDetails);
	}

	public void setBudgetDetails(String budgetDetails) {
		this.budgetDetails = budgetDetails;
	}
	
	public String getAdditionalInformation() {
		return TextUtils.cleanBrFromEditor(additionalInformation);
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
	
	public String getSubjectsNames() {
		String subjectsNames=callForProposalService.getSubjectsNames(this.id);
		return subjectsNames;
	}
	
	public List<Long> getSubmissionDates() {
		return submissionDates;
	}

	public void setSubmissionDates(List<Long> submissionDates) {
		this.submissionDates = submissionDates;
	}

	
	public List<String> getSubmissionDatesList() {
		return submissionDatesList; 
	}

	public void setSubmissionDatesList(List<String> submissionDatesList) {
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

	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getTargetAudience() {
		return targetAudience;
	}
	public void setTargetAudience(int targetAudience) {
		this.targetAudience = targetAudience;
	}

	public List<Integer> getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(List<Integer> countryIds) {
		this.countryIds = countryIds;
	}
	
	public int getHourType() {
		return hourType;
	}
	public void setHourType(int hourType) {
		this.hourType = hourType;
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
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		if (finalSubmissionTime < cal.getTimeInMillis() && finalSubmissionTime != 0)
			return true;
		else
			return false;
	}
	
	public void init(boolean applyObjs){
		if (applyObjs){
			callForProposalService = (CallForProposalService) ApplicationContextProvider.getContext().getBean("callForProposalService");
			fundService = (FundService) ApplicationContextProvider.getContext().getBean("fundService");
			messageService = (MessageService) ApplicationContextProvider.getContext().getBean("messageService");
			configurationService = (ConfigurationService) ApplicationContextProvider.getContext().getBean("configurationService");
			mopDeskService = (MopDeskService) ApplicationContextProvider.getContext().getBean("mopDeskService");
		}
	}
	
	public String getAlign(){
		Language lang= LanguageUtils.getLanguage(localeId);
		return lang.getAlign();
	}
	public String getTrimmedTitle(){
		String trimmedTitle =  title.substring(0, Math.min(title.length(), 60));
		if (title.length() > 60)
			trimmedTitle += "...";
		return trimmedTitle;
	}
	
	public String getDeskName(){
		MopDesk mopDesk = mopDeskService.getMopDesk(deskId);
		if(localeId.equals("iw_IL"))
			return mopDesk.getHebrewName();
		else
			return  mopDesk.getEnglishName();
	}
	
	public String toPostMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append(" <a class=\"big\" href=\"https://" + configurationService.getConfigurationString("website", "webServer") +
				"/call_for_proposal/" + this.urlTitle + "\">" + title + "</a><br/> ");
		sb.append("<span class=\"medium\">");
		if (fundId != 0){
		    Fund fund= fundService.getFund(fundId);
			sb.append(" <a class=\"bold\" href=\"" + fund.getWebAddress() + "\">" + fund.getName() + ", " + fund.getShortName() + "</a>;;");
		}
		else
			sb.append("# Funding agency #");
		if(finalSubmissionTime==0)
			sb.append(messageService.getMessage("general.callForProposal.submission", getLocaleId()) + ": "
					+ messageService.getMessage("general.callForProposal.submissionAllYear", getLocaleId()) + " <br/> ");
		else	
			sb.append(messageService.getMessage("general.callForProposal.submission", getLocaleId()) + ": "
				+ DateUtils.getLocaleDependentShortDateFormat(finalSubmissionTime, getLocaleId()) + " <br/> ");
		if (! amountOfGrant.isEmpty())
			sb.append(messageService.getMessage("general.callForProposal.amountOfGrant", getLocaleId()) + ": " + amountOfGrant.trim() + ";;");
		sb.append(messageService.getMessage("general.callForProposal.successIndex", getLocaleId()) + ": xxxxx;;");
		sb.append(" " +messageService.getMessage("general.callForProposal.deskPrefix", getLocaleId()));
		PersonBean creator=getCreator();
		sb.append("<a class=\"underline\" href=\"mailto:" + creator.getEmail() + "\">");
		if(localeId.equals("iw_IL"))
			sb.append(creator.getDegreeFullNameHebrew());
		else
			sb.append(creator.getDegreeFullNameEnglish());
		sb.append("</a>");
		//sb.append("#mu# #mp##mue#");
		if (deskId != 0){
			MopDesk mopDesk = mopDeskService.getMopDesk(deskId);
			if(localeId.equals("iw_IL"))
				sb.append(", <span class=\"bold\">" + mopDesk.getHebrewName() + "." + "</span>");
			else
				sb.append(", <span class=\"bold\">" + mopDesk.getEnglishName() + "." + "</span>");
		}
		String str=sb.toString();
		str=str.replace("<p>","");
		str=str.replace("</p>","");
		String dir=localeId.equals("en_US")?"ltr":"rtl";
		str="<p dir=\""+dir+"\">" + str + "</p>";
		return str;
	}
	
	public String toPostMessageNew(){
		StringBuilder sb = new StringBuilder();
		String dir=localeId.equals("en_US")?"ltr":"rtl";
		String align=localeId.equals("en_US")?"left":"right";
		
		sb.append("<p width=\"559\" align=\""+align+"\" valign=\"middle\" style=\"font-family:Arial;direction:"+dir+";text-align:"+align+";text-decoration:none;vertical-align:middle;\">");
		sb.append("<font style=\"font-weight:bold;font-size:16px;color:#333333;text-decoration:none;\">" + title + "</font>");
		sb.append("<br>");
		sb.append("<font style=\"font-weight:normal;font-size:14px;color:#333333;line-height:18px;\">");
		sb.append("<strong>"+messageService.getMessage("general.callForProposal.submission", getLocaleId())+":</strong>&nbsp;");
		if(finalSubmissionTime==0)
			sb.append(messageService.getMessage("general.callForProposal.submissionAllYear", getLocaleId()));
		else	
			sb.append(DateUtils.getLocaleDependentShortDateFormat(finalSubmissionTime, getLocaleId()));
		sb.append("&nbsp;<img src=\"image/post/dot.gif\" width=\"5\" height=\"5\" valign=\"middle\" style=\"vertical-align:middle;\" alt=\"\" />&nbsp; ");
		sb.append("<strong>"+ messageService.getMessage("general.callForProposal.fundPrefix", getLocaleId()) +"</strong>&nbsp;");
		if (fundId != 0){
		    Fund fund= fundService.getFund(fundId);
			//sb.append(" <a style=\"color:#04bde5;text-decoration:none;\" href=\"" + fund.getWebAddress() + "\">" + fund.getName() + ", " + fund.getShortName() + "</a>");
			sb.append("<font style=\"color:#333333;text-decoration:none;\">"+fund.getName()+"</font>");
		}
		sb.append("&nbsp;<img src=\"image/post/dot.gif\" width=\"5\" height=\"5\" valign=\"middle\" style=\"vertical-align:middle\" alt=\"\" />&nbsp; ");
		sb.append("<strong>"+messageService.getMessage("general.callForProposal.successRate", getLocaleId()) + ":</strong>&nbsp;xxxxx");
		sb.append("&nbsp;<img src=\"image/post/dot.gif\" width=\"5\" height=\"5\" valign=\"middle\" style=\"vertical-align:middle\" alt=\"\" />&nbsp; ");
		sb.append("<strong>"+messageService.getMessage("general.callForProposal.amountOfGrant", getLocaleId()) + ":</strong>&nbsp;");
		if (! amountOfGrant.isEmpty())
			sb.append(amountOfGrant.trim()); 
		sb.append("&nbsp;<img src=\"image/post/dot.gif\" width=\"5\" height=\"5\" valign=\"middle\" style=\"vertical-align:middle\" alt=\"\" />&nbsp;");
		sb.append("<strong><nobr>"+messageService.getMessage("general.callForProposal.deskPrefix", getLocaleId())+"</nobr></strong>&nbsp;");
		PersonBean creator=getCreator();
		if(localeId.equals("iw_IL"))
			sb.append(creator.getDegreeFullNameHebrew()+",&nbsp;");
		else
			sb.append(creator.getDegreeFullNameEnglish()+",&nbsp;");
		sb.append("<a href=\"mailto:" + creator.getEmail() + "\" style=\"color:#00a6ca;text-decoration:none;\">" + creator.getEmail() + "</a>");
		sb.append("&nbsp;<img src=\"image/post/dot.gif\" width=\"5\" height=\"5\" valign=\"middle\" style=\"vertical-align:middle\" alt=\"\" />&nbsp;");
		if (deskId != 0){
			MopDesk mopDesk = mopDeskService.getMopDesk(deskId);
			sb.append("<strong>"+messageService.getMessage("general.callForProposal.desk", getLocaleId())+"</strong>&nbsp;");
			if(localeId.equals("iw_IL"))
				sb.append(mopDesk.getHebrewName());
			else
				sb.append(mopDesk.getEnglishName());
			List<PersonBean> deskPersons=mopDeskService.getDeskCoordinators(deskId);
			if(deskPersons.size()>0 && !deskPersons.get(0).getPhone().isEmpty())
				sb.append(", "+deskPersons.get(0).getPhone());
		}
		sb.append("</p>");

		return sb.toString();
	}

}
