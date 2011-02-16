package huard.iws.service;

import huard.iws.db.PartnerProposalDao;
import huard.iws.model.PartnerInstituteProposal;
import huard.iws.model.PartnerProposal;

import java.util.List;

public class PartnerProposalServiceImpl implements PartnerProposalService{

	public void insertPartnerProposal (PartnerProposal partnerProposal){
		partnerProposalDao.insertPartnerProposal(partnerProposal);
	}

	public void deletePartnerProposal (PartnerProposal partnerProposal){
		partnerProposalDao.deletePartnerProposal(partnerProposal);
	}

	public List<PartnerProposal> getPartnerProposals(int proposalId){
		return partnerProposalDao.getPartnerProposals(proposalId);
	}



	public void insertPartnerInstituteProposal (PartnerInstituteProposal PartnerInstituteProposal){
		partnerProposalDao.insertPartnerInstituteProposal(PartnerInstituteProposal);
	}

	public void deletePartnerInstituteProposal (PartnerInstituteProposal PartnerInstituteProposal){
		partnerProposalDao.deletePartnerInstituteProposal(PartnerInstituteProposal);
	}

	public List<PartnerInstituteProposal> getPartnerInstituteProposals(int proposalId){
		return partnerProposalDao.getPartnerInstituteProposals(proposalId);
	}

	private PartnerProposalDao partnerProposalDao;

	public void setPartnerProposalDao(PartnerProposalDao partnerProposalDao) {
		this.partnerProposalDao = partnerProposalDao;
	}

}
