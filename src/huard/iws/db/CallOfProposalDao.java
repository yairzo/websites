package huard.iws.db;

import java.util.List;

import huard.iws.model.CallOfProposal;

public interface CallOfProposalDao {

	public CallOfProposal getCallOfProposal(int id, String server);

	public CallOfProposal getCallOfProposal(String title, String server);

	public List<CallOfProposal> getCallsOfProposals(String server);

	public List<CallOfProposal> getCallsOfProposals(String server, boolean open);

	public void insertAuthorizedMD5(String md5, String server);

}
