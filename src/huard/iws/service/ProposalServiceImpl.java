package huard.iws.service;

import huard.iws.db.ProposalDao;
import huard.iws.model.Proposal;
import huard.iws.model.ProposalAttachment;

import java.util.List;

public class ProposalServiceImpl implements ProposalService{

	public Proposal getProposal (String type, int id, String username){
		return proposalDao.getProposal(id);
	}

	public Proposal getProposal (int id){
		return proposalDao.getProposal(id);
	}

	public void updateProposal (Proposal proposal){
		proposalDao.updateProposal(proposal);
	}

	public int insertProposal (){
		int r = proposalDao.insertProposal();
		return r;
	}

	public void deleteProposal (int id){
		proposalDao.deleteProposal(id);
	}

	public void deleteProposalAttachment(int attachmentId, int proposalId){
		moveProposalAttachmentToLast (attachmentId, proposalId);
		proposalDao.deleteProposalAttachment(attachmentId);
	}

	private List<ProposalAttachment> getProposalAttachments(int proposalId){
		return proposalDao.getProposalAttachments(proposalId);
	}

	private ProposalAttachment getProposalAttachment(int attachmentId){
		return proposalDao.getProposalAttachment(attachmentId);
	}

	private void updateProposalAttachment(ProposalAttachment proposalAttachment){
		proposalDao.updateProposalAttachment(proposalAttachment);
	}

	public void moveProposalAttachmentUp (int attachmentId, int proposalId){
		ProposalAttachment proposalAttachment = getProposalAttachment(attachmentId);
		List<ProposalAttachment> proposalAttachments = getProposalAttachments(proposalId);
		for (ProposalAttachment aProposalAttachment: proposalAttachments){
			int aProposalAttachmentPlace;
			if (proposalAttachment.getPlace() - (aProposalAttachmentPlace = aProposalAttachment.getPlace()) == 1){
				aProposalAttachment.setPlace(proposalAttachment.getPlace());
				proposalAttachment.setPlace(aProposalAttachmentPlace);
				updateProposalAttachment(proposalAttachment);
				updateProposalAttachment(aProposalAttachment);
				break;
			}
		}
	}

	public void moveProposalAttachmentDown (int attachmentId, int proposalId){
		ProposalAttachment proposalAttachment = getProposalAttachment(attachmentId);
		List<ProposalAttachment> categories = getProposalAttachments(proposalId);
		for (ProposalAttachment aProposalAttachment : categories){
			int aProposalAttachmentPlace;
			if ((aProposalAttachmentPlace = aProposalAttachment.getPlace()) - proposalAttachment.getPlace() == 1){
				aProposalAttachment.setPlace(proposalAttachment.getPlace());
				proposalAttachment.setPlace(aProposalAttachmentPlace);
				updateProposalAttachment(proposalAttachment);
				updateProposalAttachment(aProposalAttachment);
				break;
			}
		}
	}

	public void moveProposalAttachmentToLast (int attachmentId, int proposalId){
		int proposalAttachmentsNum = getProposalAttachments(proposalId).size();
		for (int i=0; i<proposalAttachmentsNum; i++){
			moveProposalAttachmentDown(attachmentId, proposalId);
		}
	}





	private ProposalDao proposalDao;

	public void setProposalDao(ProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}
}
