package huard.iws.db;

import huard.iws.model.PartnerInstituteProposal;
import huard.iws.model.PartnerProposal;

import java.util.List;

public interface PartnerProposalDao {

	public void insertPartnerProposal (PartnerProposal partnerProposal);

	public void deletePartnerProposal (PartnerProposal partnerProposal);

	public List<PartnerProposal> getPartnerProposals(int proposalId);

	public void insertPartnerInstituteProposal (PartnerInstituteProposal PartnerInstituteProposal);

	public void deletePartnerInstituteProposal (PartnerInstituteProposal PartnerInstituteProposal);

	public List<PartnerInstituteProposal> getPartnerInstituteProposals(int proposalId);


}
