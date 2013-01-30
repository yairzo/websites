package huard.iws.web.validate;

import huard.iws.bean.CallForProposalBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class EditCallForProposalValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(CallForProposalBean.class);
	}

	public void validate ( Object command, Errors errors ){
		CallForProposalBean callForProposalBean = (CallForProposalBean) command;
		ValidationUtils.rejectIfEmpty(errors, "title", "iw_IL.required.titleCallForProposal");
		if ( callForProposalBean.getPublicationTime()==0 ){
			errors.rejectValue("publicationTime", "iw_IL.invalid.publicationTime");
		}
		if ( callForProposalBean.getFinalSubmissionTime()==0 ){
			errors.rejectValue("finalSubmissionTime", "iw_IL.invalid.finalSubmissionTime");
		}
		if ( callForProposalBean.getKeepInRollingMessagesExpiryTime()==0 ){
			errors.rejectValue("keepInRollingMessagesExpiryTime", "iw_IL.invalid.keepInRollingMessagesExpiryTime");
		}
		if ( callForProposalBean.getFundId()==0 ){
			errors.rejectValue("fundId", "iw_IL.invalid.fund");
		}
		if ( callForProposalBean.getTypeId()==0 ){
			errors.rejectValue("typeId", "iw_IL.invalid.type");
		}
		if ( callForProposalBean.getDeskId()==0 ){
			errors.rejectValue("deskId", "iw_IL.invalid.desk");
		}
	}
}
