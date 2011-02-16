package huard.iws.service;

import huard.iws.model.PartnerInstituteProposal;
import huard.iws.model.PartnerProposal;

import java.util.List;

public interface PartnerProposalService {

	public void insertPartnerProposal (PartnerProposal partnerProposal);

	public void deletePartnerProposal (PartnerProposal partnerProposal);

	public List<PartnerProposal> getPartnerProposals(int proposalId);

	public void insertPartnerInstituteProposal (PartnerInstituteProposal partnerInstituteProposal);

	public void deletePartnerInstituteProposal (PartnerInstituteProposal partnerInstituteProposal);

	public List<PartnerInstituteProposal> getPartnerInstituteProposals(int proposalId);

}
