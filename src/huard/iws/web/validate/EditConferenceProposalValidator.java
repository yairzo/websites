package huard.iws.web.validate;


import huard.iws.bean.ConferenceProposalBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class EditConferenceProposalValidator implements Validator {
	//private static final Logger logger = Logger.getLogger(EditProposalValidator.class);

	public boolean supports (Class clazz){
		return clazz.equals(ConferenceProposalBean.class);
	}

	public void validate ( Object command, Errors errors ){
		ConferenceProposalBean conferenceProposalBean = (ConferenceProposalBean) command;
		if (conferenceProposalBean.getSubmitted())	{// after submit	
			ValidationUtils.rejectIfEmpty(errors, "subject", "iw_IL.conferenceProposal.required.subject");
			if ( conferenceProposalBean.getApproverId()==0 )
				errors.rejectValue("approverId", "iw_IL.conferenceProposal.required.approver");
			if(conferenceProposalBean.getSubject().isEmpty() || conferenceProposalBean.getApproverId()==0)
				conferenceProposalBean.setSubmitted(false);
		}
		
		//invalid formats
		if (! conferenceProposalBean.getOrganizingCompanyEmail().isEmpty() && ! conferenceProposalBean.getOrganizingCompanyEmail().matches("^[_a-zA-Z0-9-&\\+=]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)+$")){
			errors.rejectValue("organizingCompanyEmail", "iw_IL.invalid.email");
		}
		if (! conferenceProposalBean.getOrganizingCompanyPhone().isEmpty() && ! conferenceProposalBean.getOrganizingCompanyPhone().trim().matches("^[\\d]{2,3}-[\\d]{7}$")){
			errors.rejectValue("organizingCompanyPhone", "iw_IL.invalid.phone");
		}
		if (! conferenceProposalBean.getOrganizingCompanyFax().isEmpty() && ! conferenceProposalBean.getOrganizingCompanyFax().trim().matches("^[\\d]{2,3}-[\\d]{7}$")){
			errors.rejectValue("organizingCompanyFax", "iw_IL.invalid.phone");
		}	
		if (! conferenceProposalBean.getContactPersonEmail().isEmpty() && ! conferenceProposalBean.getContactPersonEmail().matches("^[_a-zA-Z0-9-&\\+=]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)+$")){
			errors.rejectValue("contactPersonEmail", "iw_IL.invalid.email");
		}
		if (! conferenceProposalBean.getContactPersonPhone().isEmpty() && ! conferenceProposalBean.getContactPersonPhone().trim().matches("^[\\d]{2,3}-[\\d]{7}$")){
			errors.rejectValue("contactPersonPhone", "iw_IL.invalid.phone");
		}
	}
}
