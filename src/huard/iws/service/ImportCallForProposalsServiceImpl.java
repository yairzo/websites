package huard.iws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.*;
import java.io.*;

import huard.iws.util.LanguageUtils;
import huard.iws.util.WordsTokenizer;

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
		int newid=callForProposalService.insertCallForProposal(callForProposal);
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
		callForProposal.setDeskId(mopDeskService.getMopDesk(callForProposalOld.getDeskId()).getId());
		List<PersonBean> deskPersons =mopDeskService.getPersonsList(callForProposal.getDeskId(),0);
		for(PersonBean personBean:deskPersons){
			if(personBean.getTitle().indexOf("עוזר")>=0 || personBean.getTitle().indexOf("Assistant")>=0){
				callForProposal.setCreatorId(personBean.getId());
				break;
			}
		}
		callForProposal.setRequireLogin(callForProposalOld.getRestricted()==1?true:false);
		callForProposal.setOriginalCallWebAddress(callForProposalOld.getPageWebAddress());
		callForProposal.setKeepInRollingMessagesExpiryTime(callForProposalOld.getStopRollingTimeMillis());
		callForProposal.setShowDescriptionOnly(callForProposalOld.isDescriptionOnly());
		String localeId=LanguageUtils.getLanguage(callForProposalOld.getTitle()).getLocaleId();
		callForProposal.setLocaleId(localeId);
		String submissionDetails = "";
		if(callForProposalOld.getSubSite().equals("ARD")){
			submissionDetails =  messageService.getMessage(localeId + ".callForProposal.submissionAtAuthority");
			if(callForProposalOld.getSubDateArd()!=null)
				submissionDetails +=" " + callForProposalOld.getSubDateArd();
		}
		else if (callForProposalOld.getSubSite().equals("Fund")){
			submissionDetails =  messageService.getMessage(localeId + ".callForProposal.submissionAtFund");
			if(callForProposalOld.getSubDateFund()!=null)
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
					"<a href=\"mailto:" + personBean.getEmail()+ "\">"+ localeId=="iw_IL"?personBean.getDegreeFullNameHebrew():personBean.getDegreeFullNameEnglish()+"</a>" + 
					" " + personBean.getTitle();
					break;
				}
			}
		}
		submissionDetails += "<br>" + callForProposalOld.getSubDateDetails();
		callForProposal.setSubmissionDetails(submissionDetails);
		String budgetDetails = "";
		String budgetOfficerName = callForProposalServiceOld.getFundBudgetOfficer(callForProposalOld.getFundId());
		PersonBean budgetOfficer = new PersonBean(personService.getPersonByFullNameEnglish(budgetOfficerName));
		if(callForProposalOld.getDocType().equals("Research Grant") && callForProposalOld.getAppendBudgetOfficerLine()){
			budgetDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.budgetApprover") + 
			"<a href=\"mailto:" + budgetOfficer.getEmail()+ "\">"+ localeId=="iw_IL"?budgetOfficer.getDegreeFullNameHebrew():budgetOfficer.getDegreeFullNameEnglish()+"</a>" + 
			" " + budgetOfficer.getTitle();
		}
		budgetDetails += "<br>" + callForProposalOld.getBudgetDetails();
		callForProposal.setBudgetDetails(budgetDetails);
		for(PersonBean personBean:deskPersons){
			if ((personBean.getTitle().contains("Budget") || personBean.getTitle().contains("Financial")) && budgetOfficer.getId()!=personBean.getId())
				continue;
			if (! personBean.getFirstNameEnglish().equals("")) {
				submissionDetails += "<br>" + messageService.getMessage(localeId + ".callForProposal.submissionEmail") + 
				"<a href=\"mailto:" + personBean.getEmail()+ "\">"+ localeId=="iw_IL"?personBean.getDegreeFullNameHebrew():personBean.getDegreeFullNameEnglish()+"</a>" + 
				" " + personBean.getTitle();
			}
		}
		callForProposal.setContactPersonDetails(callForProposalOld.getDeskAndContact());
		callForProposal.setDescription(callForProposalOld.getDescription());
		callForProposal.setFundingPeriod(callForProposalOld.getFundingPeriod());
		callForProposal.setAmountOfGrant(callForProposalOld.getAmountOfGrant());
		callForProposal.setEligibilityRequirements(callForProposalOld.getEligibilityRequirements());
		callForProposal.setActivityLocation(callForProposalOld.getActivityLocation());
		callForProposal.setPossibleCollaboration(callForProposalOld.getPossibleCollaboration());
		callForProposal.setAdditionalInformation(callForProposalOld.getadditionalInformation());
		callForProposal.setUpdateTime(callForProposalServiceOld.getUpdateTime(ardNum));
		callForProposal.setSubmissionDates(callForProposalServiceOld.getSubmissionDates(ardNum));
		Post post=postService.getPostByMessageSubject(callForProposalOld.getTitle());
		if(post.getId()>0)//found post by message class
			callForProposal.setSubjectsIds(post.getSubjectsIds());
		//attachments and formDetails
		String formDetails = callForProposalOld.getForms();
		WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
		List<String> elementsList = elementsTokenizer.getSubstringsListNoTrim(formDetails);
		int elementsListSize;
		if ((elementsListSize = elementsList.size()) >= 3) {
			StringBuffer textBuffer = new StringBuffer();
			int i, k = (int) elementsListSize / 3;
			for (i = 0; i < k; i++) {
				textBuffer.append((String) elementsList.get(i * 3));
				String linkText = (String) elementsList.get(i * 3 + 1);
				String linkAddress = (String) elementsList.get(i * 3 + 2);
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
				    } catch (Exception e) {
					    e.printStackTrace();
				    }
					String newUrl = "fileViewer?callForProposalId=" + callForProposal.getId() +"&attachmentId=" + attachmentId + "&contentType="+contentType;
					textBuffer.append("<a href=\""	+ newUrl+ "\">" + linkText + "</a>");
				}
			}
			for (int j = i * 3; j < elementsListSize; j++)
				textBuffer.append("&nbsp;" + (String) elementsList.get(j));
			formDetails=textBuffer.toString();
		}
		callForProposal.setFormDetails(formDetails);
		try{
			callForProposalService.updateCallForProposal(callForProposal);
			if(callForProposalOld.getHasTabledVersion())
				callForProposalService.updateCallForProposalOnline(callForProposal);
		}
		catch(Exception e){
			System.out.println("Exception in Import call for proposals:" + e.getMessage());

		}
	}
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
