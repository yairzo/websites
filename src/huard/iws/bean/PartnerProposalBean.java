package huard.iws.bean;

import huard.iws.model.Partner;
import huard.iws.model.PartnerProposal;
import huard.iws.service.PartnerService;
import huard.iws.util.ApplicationContextProvider;

public class PartnerProposalBean {
	private int id;
	private int partnerId;
	private int proposalId;
	private PartnerBean partnerBean;


	public PartnerProposalBean (PartnerProposal partnerProposal){
		this.id = partnerProposal.getId();
		this.partnerId = partnerProposal.getPartnerId();
		this.proposalId = partnerProposal.getProposalId();
	}

	public PartnerProposal toPartnerProposal(){
		PartnerProposal partnerProposal = new PartnerProposal();
		partnerProposal.setId(id);
		partnerProposal.setPartnerId(partnerId);
		partnerProposal.setProposalId(proposalId);
		return partnerProposal;
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

	public PartnerBean getPartnerBean() {
		if (partnerBean == null){
			Object obj = ApplicationContextProvider.getContext().getBean("partnerService");
			PartnerService partnerService = (PartnerService)obj;
			Partner partner = partnerService.getPartner(this.partnerId);
			this.partnerBean = new PartnerBean(partner);
		}
		return partnerBean;
	}

	public void setPartnerBean(PartnerBean partnerBean) {
		this.partnerBean = partnerBean;
	}

}
