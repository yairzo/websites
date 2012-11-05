package huard.iws.web.validate;

import huard.iws.bean.CallOfProposalBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class EditCallOfProposalValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(CallOfProposalBean.class);
	}

	public void validate ( Object command, Errors errors ){
		CallOfProposalBean callOfProposalBean = (CallOfProposalBean) command;
		ValidationUtils.rejectIfEmpty(errors, "title", "iw_IL.required.titleCallOfProposal");
		if ( callOfProposalBean.getPublicationTime().equals("01/01/1970") ){
			errors.rejectValue("publicationTime", "iw_IL.invalid.publicationTime");
		}
		if ( callOfProposalBean.getFinalSubmissionTime().equals("01/01/1970") ){
			errors.rejectValue("finalSubmissionTime", "iw_IL.invalid.finalSubmissionTime");
		}
		if ( callOfProposalBean.getKeepInRollingMessagesExpiryTime().equals("01/01/1970") ){
			errors.rejectValue("keepInRollingMessagesExpiryTime", "iw_IL.invalid.keepInRollingMessagesExpiryTime");
		}
		if ( callOfProposalBean.getFundId()==0 ){
			errors.rejectValue("fundId", "iw_IL.invalid.fund");
		}
		if ( callOfProposalBean.getTypeId()==0 ){
			errors.rejectValue("typeId", "iw_IL.invalid.type");
		}
		if ( callOfProposalBean.getDeskId()==0 ){
			errors.rejectValue("deskId", "iw_IL.invalid.desk");
		}
	}
}
