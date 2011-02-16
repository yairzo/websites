package huard.iws.model;

public class PartnerProposal {
	private int id;
	private int partnerId;
	private int proposalId;

	public PartnerProposal(){

	}

	public PartnerProposal( int partnerId, int proposalId){
		this.partnerId = partnerId;
		this.proposalId = proposalId;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getProposalId() {
		return proposalId;
	}
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}


}
