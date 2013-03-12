package huard.iws.web.validate;

import huard.iws.bean.TextualPageBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TextualPageValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(TextualPageBean.class);
	}

	public void validate ( Object command, Errors errors ){
		TextualPageBean textualPageBean = (TextualPageBean)command;
		ValidationUtils.rejectIfEmpty(errors, "title", "iw_IL.required.titleCallForProposal");
		if(textualPageBean.getCategoryId()==0)
			errors.rejectValue("categoryId", "iw_IL.required.categoryId");
	}
}
