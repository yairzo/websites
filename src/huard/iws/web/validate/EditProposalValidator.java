package huard.iws.web.validate;


import huard.iws.bean.PersonProposalBean;
import huard.iws.bean.ProposalBean;
import huard.iws.constant.PersonProposalTypes;
import huard.iws.model.Institute;
import huard.iws.model.Partner;
import huard.iws.service.PersonListService;
import huard.iws.service.ProposalService;
import huard.iws.web.EditProposalController.EditProposalControllerCommand;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class EditProposalValidator implements Validator {
	//private static final Logger logger = Logger.getLogger(EditProposalValidator.class);

	public boolean supports (Class clazz){
		return clazz.equals(EditProposalControllerCommand.class);
	}

	public void validate ( Object command, Errors errors ){

		EditProposalControllerCommand aCommand = (EditProposalControllerCommand) command;





		if (aCommand.getAction().equals("addPartner")){
			Partner partner = aCommand.getPartner();
			if (! validateText(partner.getName())){
				errors.rejectValue("partner.name", "iw_IL.eqfSystem.editProposal.partnerName.required");
			}
			if (! validateText(partner.getDegree())){
				errors.rejectValue("partner.degree", "iw_IL.eqfSystem.editProposal.partnerDegree.required");
			}
			Institute institute = aCommand.getInstitute();
			if (! validateText(institute.getName())){
				errors.rejectValue("institute.name", "iw_IL.eqfSystem.editProposal.instituteName.required");
			}

		}



		if (aCommand.getAction()!=null  && (aCommand.getAction().equals("autosave")
				|| aCommand.getAction().equals("cancel"))) return ;

		ProposalBean proposalBean = aCommand.getProposalBean();


		if (! validateText(proposalBean.getHebrewTitle())){
			errors.rejectValue("proposalBean.hebrewTitle", "iw_IL.eqfSystem.editProposal.hebrewTitle.required");
		}
		if (! validateText(proposalBean.getEnglishTitle())){
			errors.rejectValue("proposalBean.englishTitle", "iw_IL.eqfSystem.editProposal.englishTitle.required");
		}



		List<PersonProposalBean> personProposalBeans = proposalBean.getPersonProposalBeans();
		boolean mainResearcherApproval = false;
		for (PersonProposalBean personProposalBean: personProposalBeans){
			if (personProposalBean.getTypeId() == PersonProposalTypes.getTypeId("MAIN_RESEARCHER") && proposalBean.getStateId() == 0 && personProposalBean.isApproval()){
				mainResearcherApproval = true;
			}
		}
		// if the main researcher is trying to approve
		if (mainResearcherApproval){
			boolean validApproval = true;

			if (proposalBean.getFundId() == 0 ) {
				errors.rejectValue("proposalBean.fundId", 	"iw_IL.eqfSystem.editProposal.fundId.required");
				validApproval = false;
			}
			if ( ! hasProposalAttach(proposalBean)){
				errors.rejectValue("proposalBean.proposalAttach", 	"iw_IL.eqfSystem.editProposal.proposalAttach.required");
				validApproval = false;
			}
			if ( ! hasEthicsAttach(proposalBean)){
				errors.rejectValue("proposalBean.ethicsAttach", 	"iw_IL.eqfSystem.editProposal.ethicsAttach.required");
				validApproval = false;
			}

			if (!validApproval){
				for (PersonProposalBean personProposalBean: personProposalBeans){
					if (personProposalBean.getTypeId()==PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
						personProposalBean.setApproval(false);
					}
				}
			}
		}




		else if (proposalBean.getStateId()==4 &&
				proposalBean.isExperimentApproved()){

			if ( proposalBean.isExperimental() && ! hasSafetyAttach(proposalBean)){
				errors.rejectValue("proposalBean.safetyAttach", 	"iw_IL.eqfSystem.editProposal.safetyAttach.required");
			}
			if ( proposalBean.isHumanExperiment() && ! hasHumanAttach(proposalBean)){
				errors.rejectValue("proposalBean.humanAttach", 	"iw_IL.eqfSystem.editProposal.humanAttach.required");
			}
			if ( proposalBean.isAnimalsExperiment() && ! hasAnimalsAttach(proposalBean)){
				errors.rejectValue("proposalBean.animalsAttach", 	"iw_IL.eqfSystem.editProposal.animalsAttach.required");
			}
		}


		if (aCommand.getAction()!=null && aCommand.getAction().equals("addResearcher")){
			if (! personListService.isResearcher(aCommand.getAddedResearcher())){
				 errors.rejectValue("addedResearcher", 	"iw_IL.eqfSystem.editProposal.addedResearcher.notAResearcher");
			 }
		}
	}

	private boolean hasProposalAttach(ProposalBean proposalBean){
		return (proposalBean.getResearchAttachments().size() > 0)
		|| (proposalService.getProposal(proposalBean.getId()).getResearchAttachments().size() > 0);
	}

	private boolean hasEthicsAttach(ProposalBean proposalBean){
		return (proposalBean.getEthicsAttach() != null && proposalBean.getEthicsAttach().length > 0)
		|| (proposalService.getProposal(proposalBean.getId()).getEthicsAttach()!=null
				&& proposalService.getProposal(proposalBean.getId()).getEthicsAttach().length > 0);
	}

	private boolean hasSafetyAttach(ProposalBean proposalBean){
		return (proposalBean.getSafetyAttach() != null && proposalBean.getSafetyAttach().length !=0)
		|| (proposalService.getProposal(proposalBean.getId()).getSafetyAttach()!=null
				&& proposalService.getProposal(proposalBean.getId()).getSafetyAttach().length!=0);
	}

	private boolean hasHumanAttach(ProposalBean proposalBean){
		return (proposalBean.getHumanAttach() != null && proposalBean.getHumanAttach().length !=0)
		|| (proposalService.getProposal(proposalBean.getId()).getHumanAttach()!=null
				&& proposalService.getProposal(proposalBean.getId()).getHumanAttach().length!=0);
	}

	private boolean hasAnimalsAttach(ProposalBean proposalBean){
		return (proposalBean.getAnimalsAttach() != null && proposalBean.getAnimalsAttach().length !=0)
		|| (proposalService.getProposal(proposalBean.getId()).getAnimalsAttach()!=null
				&& proposalService.getProposal(proposalBean.getId()).getAnimalsAttach().length!=0);
	}


	private boolean validateText(String text){
		if (text == null || text.trim().length() == 0) return false;
		return true;
	}

	private ProposalService proposalService;

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
}
