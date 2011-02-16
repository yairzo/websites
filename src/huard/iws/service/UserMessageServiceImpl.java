package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

public class UserMessageServiceImpl implements UserMessageService{
	final String eqfEditProposalBaseKey = "iw_IL.eqfSystem.editProposal.userMessage";

	public String getAddedResearcherMessage(PersonBean addedResearcher){
		String key1 = eqfEditProposalBaseKey+".addedResearcher";
		String part1 = messageSource.getMessage(key1, new String[]{addedResearcher.getDegreeFullNameHebrew()}, Locale.ENGLISH);
		String key2 =  key1 + "." + (addedResearcher.isValidEmail() ? "hasEmail" : "noEmail");
		String part2 = messageSource.getMessage(key2, null, Locale.ENGLISH);
		return part1 +" " + part2;
	}

	public String getRemovedResearcherMessage(PersonBean removedResearcher){
		String key1 = eqfEditProposalBaseKey+".removedResearcher";
		String part1 = messageSource.getMessage(key1, new String[]{removedResearcher.getDegreeFullNameHebrew()}, Locale.ENGLISH);
		String key2 =  key1 + "." + (removedResearcher.isValidEmail() ? "hasEmail" : "noEmail");
		String part2 = messageSource.getMessage(key2, null, Locale.ENGLISH);
		return part1 +" " + part2;
	}

	public String getSecondaryResearchersApprovalRequestMessage(){
		String key = eqfEditProposalBaseKey+".secondaryResearchersApprovalRequest";
		return getMessage(key);
	}

	public String getCopyProposalMessage(){
		String key = eqfEditProposalBaseKey+".copyProposal";
		return getMessage(key);
	}

	public String getResearcherUnknownFundMessage(){
		String key = eqfEditProposalBaseKey+".researcherAddedUnknownFund";
		return getMessage(key);
	}

	public String getMessageSentMessage(){
		String key = "iw_IL.iws.general.sendMessage.messageSent";
		return getMessage(key);
	}

	private String getMessage(String key){
		return messageSource.getMessage(key, null, Locale.ENGLISH);
	}


	public void putUserMessageInSession(RequestWrapper request, String message){
		request.getSession().setAttribute("userMessage", message);
	}

	private void cleanUserMessageInSession(HttpServletRequest request){
		request.getSession().setAttribute("userMessage", null);
	}

	public String getSessionUserMessage(HttpServletRequest request){
		String userMessage = (String)request.getSession().getAttribute("userMessage");
		cleanUserMessageInSession(request);
		return userMessage;
	}


	private MessageSource messageSource;

	 public void setMessageSource(MessageSource messageSourcePar) {

	        messageSource = messageSourcePar;
	 }

}
