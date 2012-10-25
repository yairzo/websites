package huard.iws.service;

import java.util.List;

import huard.iws.model.CallOfProposalOld;

public interface CallOfProposalServiceOld {

	public CallOfProposalOld getCallOfProposal(int id);

	public CallOfProposalOld getCallOfProposal(String title);

	public List<CallOfProposalOld> getCallsOfProposals();

	public List<CallOfProposalOld> getCallsOfProposals(boolean open);

	public List<CallOfProposalOld> getCallsOfProposals(String localeId);

	public void insertAuthorizedMD5 (String md5);

}
