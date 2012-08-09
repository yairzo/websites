package huard.iws.web.validate;

import huard.iws.bean.PersonBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class EditPersonValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(PersonBean.class);
	}

	public void validate ( Object command, Errors errors ){
		PersonBean personBean = (PersonBean) command;
		ValidationUtils.rejectIfEmpty(errors, "firstNameHebrew", "iw_IL.required.firstNameHebrew");
		ValidationUtils.rejectIfEmpty(errors, "lastNameHebrew", "iw_IL.required.lastNameHebrew");
		ValidationUtils.rejectIfEmpty(errors, "degreeHebrew", "iw_IL.required.degreeHebrew");
		if (!personBean.isOnlyAuthorized("CONFERENCE","RESEARCHER")){
			ValidationUtils.rejectIfEmpty(errors, "firstNameEnglish", "iw_IL.required.firstNameEnglish");
			ValidationUtils.rejectIfEmpty(errors, "lastNameEnglish", "iw_IL.required.lastNameEnglish");
			ValidationUtils.rejectIfEmpty(errors, "degreeEnglish", "iw_IL.required.degreeEnglish");
		}
		if (! personBean.getEmail().isEmpty() && ! personBean.getEmail().matches("^[_a-zA-Z0-9-&\\+=]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)+$")){
			errors.rejectValue("email", "iw_IL.invalid.email");
		}
		else
			ValidationUtils.rejectIfEmpty(errors, "email", "iw_IL.required.email");
		ValidationUtils.rejectIfEmpty(errors, "phone", "iw_IL.required.phone");
		if (! personBean.getPhone().trim().matches("^[\\d]{2,3}-[\\d]{7}$")){
			errors.rejectValue("phone", "iw_IL.invalid.phone");
		}
		if( personBean.isAuthorized("ROLE_CONFERENCE_RESEARCHER")){
			ValidationUtils.rejectIfEmpty(errors, "cellPhone", "iw_IL.required.cellPhone");
			if (! personBean.getPhone().trim().matches("^[\\d]{2,3}-[\\d]{7}$")){
				errors.rejectValue("cellPhone", "iw_IL.invalid.cellPhone");
			}
		}
		if (personBean.getFacultyId() == 0){
			errors.rejectValue("facultyId", "iw_IL.required.facultyId");
		}
		if (!personBean.isOnlyAuthorized("CONFERENCE","RESEARCHER")){
			if (personBean.getCampusId() == 0){
				errors.rejectValue("campusId", "iw_IL.required.campusId");
			}
		}

		ValidationUtils.rejectIfEmpty(errors, "department", "iw_IL.required.department");
		if (!personBean.isOnlyAuthorized("CONFERENCE","RESEARCHER")){
			ValidationUtils.rejectIfEmpty(errors, "academicTitle", "iw_IL.required.academicTitle");
		}

		if (!personBean.getCivilId().isEmpty() && ! personBean.getCivilId().trim().matches("\\d{8}")){
			errors.rejectValue("civilId", "iw_IL.invalid.civilId");
		}
	}
}
