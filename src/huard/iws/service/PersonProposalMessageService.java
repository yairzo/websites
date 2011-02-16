package huard.iws.service;

import huard.iws.bean.PersonBean;

public interface PersonProposalMessageService {

	public void sendApprovalRequestToAllSecondaryResearchers(int proposaId);

	public void informAllResearchersOnDeanResponse(PersonBean dean, int proposalId, boolean approved);

	public void informMopDeskOnDeanResponse(PersonBean dean, int proposalId, boolean approved);

	public void informAllResearchersOnFundResponse(int proposalId, boolean approved);

	public void informAllResearchersOnBudgetOpened(int proposalId);

}
