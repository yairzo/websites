package huard.iws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import huard.iws.util.LanguageUtils;

import huard.iws.bean.PersonBean;
import huard.iws.model.Attachment;
import huard.iws.model.CallForProposal;
import huard.iws.model.CallForProposalOld;
import huard.iws.model.Post;


public class ImportCallForProposalsServiceImpl implements ImportCallForProposalsService{
	private  static Map<String, Integer> typeMap ;

	static{
		typeMap = new HashMap<String, Integer>();
		typeMap.put("Research Grant", 1);
		typeMap.put("Researchers Exchange", 2);
		typeMap.put("Conference", 3);
		typeMap.put("Scholarship", 4);
	}

public void importCallForProposals(){
	
	List<CallForProposalOld> callForProposalsOld = callForProposalServiceOld.getCallForProposalsOldWebsite();
	for(CallForProposalOld callForProposalOld: callForProposalsOld){
		CallForProposal callForProposal = new CallForProposal();
		callForProposal.setTitle(callForProposalOld.getTitle());
		String localeId=LanguageUtils.getLanguage(callForProposalOld.getTitle()).getLocaleId();
		callForProposal.setLocaleId(localeId);
		callForProposal.setDeskId(mopDeskService.getMopDesk(callForProposalOld.getDeskId()).getId());
		List<PersonBean> deskPersons;
		if(localeId.equals("iw_IL")){
			deskPersons=mopDeskService.getPersonsList(callForProposal.getDeskId(),0);
		}
		else
			deskPersons=mopDeskService.getPersonsListEnglish(callForProposal.getDeskId(),0);
		int creatorId = 0;
		for(PersonBean personBean:deskPersons){
			if(personBean.getTitle().indexOf("עוזר")>=0 || personBean.getTitle().indexOf("Assistant")>=0){
				creatorId=personBean.getId();
				break;
			}
		}
		callForProposal.setCreatorId(creatorId);
		int newid=callForProposalService.insertCallForProposal(callForProposal);
		if(newid==0)
			break;
		callForProposal.setId(newid);
		int ardNum=callForProposalOld.getId();
		callForProposalService.insertArdNum(ardNum,newid);
		callForProposal.setPublicationTime(callForProposalOld.getPublicationTimeMillis());
		callForProposal.setCreationTime(callForProposalOld.getPublicationTimeMillis());
		callForProposal.setFundId(callForProposalOld.getFundId());//currently ids are the same for HUARD Funds and huard fund
		callForProposal.setFinalSubmissionTime(callForProposalOld.getSubmissionTimeMillis());
		callForProposal.setAllYearSubmission(callForProposalOld.getSubmissionTimeMillis()==0?true:false);
		String docType = callForProposalOld.getDocType();
		callForProposal.setTypeId(typeMap.get(docType));
		callForProposal.setRequireLogin(callForProposalOld.getRestricted()==1?true:false);
		callForProposal.setOriginalCallWebAddress(callForProposalOld.getPageWebAddress());
		callForProposal.setKeepInRollingMessagesExpiryTime(callForProposalOld.getStopRollingTimeMillis());
		callForProposal.setShowDescriptionOnly(callForProposalOld.isDescriptionOnly());
		String submissionDetails = "";
		if(callForProposalOld.getSubSite().equals("ARD")){
			submissionDetails =  messageService.getMessage(localeId + ".callForProposal.submissionAtAuthority");
			if(callForProposalOld.getSubDateArd()!=null && !callForProposalOld.getSubDateArd().equals("null") )
				submissionDetails +=" " + callForProposalOld.getSubDateArd();
		}
		else if (callForProposalOld.getSubSite().equals("Fund")){
			submissionDetails =  messageService.getMessage(localeId + ".callForProposal.submissionAtFund");
			if(callForProposalOld.getSubDateFund()!=null && !callForProposalOld.getSubDateFund().equals("null") )
				submissionDetails +=" " + callForProposalOld.getSubDateFund();
		}
		if(callForProposalOld.getNumOfCopies()>0){
			submissionDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.submissionCopiesShort");
			submissionDetails += callForProposalOld.getNumOfCopies();
			if(callForProposalOld.getSendDigitalCopy())
				messageService.getMessage(localeId + ".callForProposal.copyMop");
		}
		if(callForProposalOld.getSendDigitalCopy()){
			for(PersonBean personBean:deskPersons){
				if(personBean.getTitle().indexOf("עוזר")>=0 || personBean.getTitle().indexOf("Assistant")>=0){
					submissionDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.submissionEmail") + 
					"<a href=\"mailto:" + personBean.getEmail()+ "\">"+ personBean.getPreferedLocaleDegreeFullName()+"</a>" + 
					" " + personBean.getTitle();
					break;
				}
			}
		}
		submissionDetails += "<br>" + cleanText(callForProposalOld.getSubDateDetails());
		callForProposal.setSubmissionDetails(submissionDetails);
		String budgetDetails = "";
		String budgetOfficerName = callForProposalServiceOld.getFundBudgetOfficer(callForProposalOld.getFundId());
		PersonBean budgetOfficer = new PersonBean(personService.getPersonByFullNameEnglish(budgetOfficerName));
		if(callForProposalOld.getDocType().equals("Research Grant") && callForProposalOld.getAppendBudgetOfficerLine()){
			budgetDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.budgetApprover");
			if(budgetOfficer!=null){
				budgetDetails += "<a href=\"mailto:" + budgetOfficer.getEmail()+ "\">" 
					+ budgetOfficer.getPreferedLocaleDegreeFullName() + "</a> " + budgetOfficer.getTitle();
			}
		}
		budgetDetails += "<br>" + cleanText(callForProposalOld.getBudgetDetails());
		callForProposal.setBudgetDetails(budgetDetails);
		for(PersonBean personBean:deskPersons){
			if ((personBean.getTitle().contains("Budget") || personBean.getTitle().contains("Financial")) && budgetOfficer.getId()!=personBean.getId())
				continue;
			if (! personBean.getFirstNameEnglish().equals("")) {
				submissionDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.submissionEmail") + 
				"<a href=\"mailto:" + personBean.getEmail()+ "\">"+ personBean.getPreferedLocaleDegreeFullName()+"</a>" + 
				" " + personBean.getTitle();
			}
		}
		String contactPersonDetails=cleanText(callForProposalOld.getDeskAndContact());
		for(PersonBean personBean:deskPersons){
			if(personBean.getTitle().indexOf("עוזר")>=0 || personBean.getTitle().indexOf("Assistant")>=0 ||
					personBean.getTitle().indexOf("ראש מדור")>=0 || personBean.getTitle().indexOf("Coordinator")>=0){
				contactPersonDetails += "<br>" + "<a href=\"mailto:" + personBean.getEmail()+ "\">";
				if(localeId.equals("iw_IL")) contactPersonDetails += personBean.getDegreeFullNameHebrew();
				else contactPersonDetails += personBean.getDegreeFullNameEnglish();
				contactPersonDetails += "</a><img src=\"image/bullet_orange_website.gif\" width=\"12\" height=\"8\">" + personBean.getTitle()
						+ "<img src=\"image/bullet_orange_website.gif\" width=\"12\" height=\"8\">" + personBean.getPhone();  
			}
		}
		callForProposal.setContactPersonDetails(contactPersonDetails);
		callForProposal.setDescription(cleanText(callForProposalOld.getDescription()));
		callForProposal.setFundingPeriod(cleanText(callForProposalOld.getFundingPeriod()));
		callForProposal.setAmountOfGrant(cleanText(callForProposalOld.getAmountOfGrant()));
		callForProposal.setEligibilityRequirements(cleanText(callForProposalOld.getEligibilityRequirements()));
		callForProposal.setActivityLocation(cleanText(callForProposalOld.getActivityLocation()));
		callForProposal.setPossibleCollaboration(cleanText(callForProposalOld.getPossibleCollaboration()));
		callForProposal.setAdditionalInformation(cleanText(callForProposalOld.getadditionalInformation()));
		callForProposal.setUpdateTime(callForProposalServiceOld.getUpdateTime(ardNum));
		callForProposal.setSubmissionDates(callForProposalServiceOld.getSubmissionDates(ardNum));
		//subjectIds
		Post post=postService.getPostByMessageSubject(callForProposalOld.getTitle());
		if(post.getId()>0)//found post by message class
			callForProposal.setSubjectsIds(post.getSubjectsIds());
		//attachments and formDetails
		String formDetails = callForProposalOld.getForms();
		Pattern p = Pattern.compile("\\*(.*?)\\*(.*?)\\*");
		Matcher m = p.matcher(formDetails);
		while(m.find()){
			String linkText = m.group(1);
			String linkAddress = m.group(2);
			if (linkAddress.indexOf("http://ard.huji.ac.il/docs/")>-1){
					Attachment attachment= new Attachment();
					int attachmentId =0;
					String contentType ="";
					try {
				    	URL toDownload = new URL(linkAddress);
				    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					    byte[] chunk = new byte[4096];
					    int bytesRead;
					    InputStream stream = toDownload.openStream();
					    while ((bytesRead = stream.read(chunk)) > 0) {
					        outputStream.write(chunk, 0, bytesRead);
					    }
						attachment.setFile(outputStream.toByteArray());
						attachment.setTitle(linkText);
						contentType = linkAddress.substring(linkAddress.lastIndexOf("."));
						attachment.setContentType(contentType);
						attachmentId = callForProposalService.insertAttachmentToCallForProposal(callForProposal.getId(),attachment);
						linkAddress = "fileViewer?callForProposalId=" + callForProposal.getId() +"&attachmentId=" + attachmentId + "&contentType="+contentType;
				    } catch (Exception e) {
					    e.printStackTrace();
				    }
			}
			formDetails = formDetails.replaceFirst("\\*(.*?)\\*(.*?)\\*","<a href=\""+linkAddress+"\">"+linkText+"</a>");
		}
		callForProposal.setFormDetails(cleanText(formDetails));
		callForProposalService.updateCallForProposal(callForProposal);
		if(callForProposalOld.getHasTabledVersion())
			callForProposalService.insertCallForProposalOnline(callForProposal);
	}
}
    
public String cleanText(String text){
	text=text.replace("xxxxx", "");
	text=text.replace("null", "");
	text = text.replaceAll("~", "<br>");	
	//links
	Pattern p = Pattern.compile("\\*(.*?)\\*(.*?)\\*");
	Matcher m = p.matcher(text);
	while(m.find())
		text = text.replaceFirst("\\*(.*?)\\*(.*?)\\*","<a href=\""+m.group(2)+"\">"+m.group(1)+"</a>");
	return text;
}

private CallForProposalService callForProposalService;
public void setCallForProposalService(CallForProposalService callForProposalService) {
	this.callForProposalService = callForProposalService;
}

private CallForProposalServiceOld callForProposalServiceOld;
public void setCallForProposalServiceOld(CallForProposalServiceOld callForProposalServiceOld) {
	this.callForProposalServiceOld = callForProposalServiceOld;
}

private MopDeskService mopDeskService;
public void setMopDeskService(MopDeskService mopDeskService) {
	this.mopDeskService = mopDeskService;
}

protected MessageService messageService;
public void setMessageService(MessageService messageService) {
	this.messageService = messageService;
}

private PostService postService;
public void setPostService(PostService postService) {
	this.postService = postService;
}

protected PersonService personService;
public void setPersonService(PersonService personService) {
	this.personService = personService;
}
}
