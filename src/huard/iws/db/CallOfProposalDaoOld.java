package huard.iws.db;

import java.util.List;

import huard.iws.model.CallOfProposalOld;

public interface CallOfProposalDaoOld {

	public CallOfProposalOld getCallOfProposal(int id, String server);

	public CallOfProposalOld getCallOfProposal(String title, String server);

	public List<CallOfProposalOld> getCallsOfProposals(String server);

	public List<CallOfProposalOld> getCallsOfProposals(String server, boolean open);

	public void insertAuthorizedMD5(String md5, String server);
	
	public List<CallOfProposalOld> getAliveTabledInfoPages(Integer ardNum,String server);
	


}
