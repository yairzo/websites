package huard.iws.db;

import huard.iws.model.Proposal;
import huard.iws.model.ProposalAttachment;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface ProposalDao {

	public Proposal getProposal (int id);

	public void updateProposal (Proposal proposal);

	public int insertProposal ();

	public void deleteProposal (int id);

	public void deleteProposalAttachment (int attachmentId);

	public List<ProposalAttachment> getProposalAttachments(int proposalId);

	public ProposalAttachment getProposalAttachment(int attachmentId);

	public void updateProposalAttachment(ProposalAttachment proposalAttachment);

	public ParameterizedRowMapper<Proposal> getProposalRowMapper();

}
