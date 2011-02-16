package huard.iws.model;

public class PartnerInstituteProposal {
	private int id;
	private int partnerInstituteId;
	private int proposalId;


	public PartnerInstituteProposal(){

	}

	public PartnerInstituteProposal(int partnerInstituteId, int proposalId){
		this.partnerInstituteId = partnerInstituteId;
		this.proposalId = proposalId;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPartnerInstituteId() {
		return partnerInstituteId;
	}
	public void setPartnerInstituteId(int partnerInstituteId) {
		this.partnerInstituteId = partnerInstituteId;
	}
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}

}
