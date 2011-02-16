package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposal;

public interface CallOfProposalService {

	public CallOfProposal getCallOfProposal(int id);

	public CallOfProposal getCallOfProposal(String title);

	public List<CallOfProposal> getCallsOfProposals();

	public List<CallOfProposal> getCallsOfProposals(boolean open);

	public List<CallOfProposal> getCallsOfProposals(String localeId);

	public void insertAuthorizedMD5 (String md5);

}
