package huard.iws.service;

import huard.iws.model.Proposal;

public interface ProposalService {

	public Proposal getProposal (String type, int id, String username);

	public Proposal getProposal (int id);

	public void updateProposal (Proposal proposal);

	public int insertProposal ();

	public void deleteProposal (int id);

	public void deleteProposalAttachment(int attachmentId, int proposalId);

	public void moveProposalAttachmentUp (int attachmentId, int proposalId);

	public void moveProposalAttachmentDown (int proposalAttachmentId, int proposalId);

	public void moveProposalAttachmentToLast (int proposalAttachmentId, int proposalId);

}
