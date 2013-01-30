package huard.iws.db;

import java.util.List;

import huard.iws.model.CallForProposalOld;

public interface CallForProposalDaoOld {

	public CallForProposalOld getCallForProposal(int id, String server);

	public CallForProposalOld getCallForProposal(String title, String server);

	public List<CallForProposalOld> getCallForProposals(String server);

	public List<CallForProposalOld> getCallForProposals(String server, boolean open);

	public void insertAuthorizedMD5(String md5, String server);
	
	public List<CallForProposalOld> getAliveTabledInfoPages(Integer ardNum,String server);
	


}
