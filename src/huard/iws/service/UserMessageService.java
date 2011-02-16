package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSourceAware;



public interface UserMessageService extends MessageSourceAware{

	public String getAddedResearcherMessage(PersonBean addedResearcher);

	public String getRemovedResearcherMessage(PersonBean removedResearcher);

	public String getSecondaryResearchersApprovalRequestMessage();

	public String getCopyProposalMessage();

	public String getResearcherUnknownFundMessage();

	public String getMessageSentMessage();


	public void putUserMessageInSession(RequestWrapper request, String message);

	public String getSessionUserMessage(HttpServletRequest request);



}
