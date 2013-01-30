package huard.iws.service;

import java.util.List;

import huard.iws.model.CallForProposalOld;

public interface CallForProposalServiceOld {

	public CallForProposalOld getCallForProposal(int id);

	public CallForProposalOld getCallForProposal(String title);

	public List<CallForProposalOld> getCallForProposals();

	public List<CallForProposalOld> getCallForProposals(boolean open);

	public List<CallForProposalOld> getCallForProposals(String localeId);

	public void insertAuthorizedMD5 (String md5);

}
