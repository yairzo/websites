package huard.iws.web.validate;

import huard.iws.bean.PersonListAttributionBean;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EditPersonAttributionValidator implements Validator {
	//private static final Logger logger = Logger.getLogger(EditPersonAttributionValidator.class);

	public boolean supports (Class clazz){
		return clazz.equals(PersonListAttributionBean.class);
	}

	public void validate ( Object command, Errors errors ){
		//PersonListAttributionBean personAttribution = (PersonListAttributionBean) command;
		/*ValidationUtils.rejectIfEmpty(errors, "title", "required.title");
		ValidationUtils.rejectIfEmpty(errors, "email", "required.email");
		ValidationUtils.rejectIfEmpty(errors, "phone", "required.phone");
		ValidationUtils.rejectIfEmpty(errors, "fax", "required.fax");
		ValidationUtils.rejectIfEmpty(errors, "address", "required.address");
		ValidationUtils.rejectIfEmpty(errors, "department", "required.department");*/
	}

}
