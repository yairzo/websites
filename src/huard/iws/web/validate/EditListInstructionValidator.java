package huard.iws.web.validate;

import huard.iws.model.AListInstruction;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EditListInstructionValidator implements Validator {

	public boolean supports (Class clazz){
		return clazz.equals(AListInstruction.class);
	}

	public void validate ( Object command, Errors errors ){
		//AListInstruction aListInstruction = (AListInstruction) command;
		ValidationUtils.rejectIfEmpty(errors, "selectsFrom", "required.name");
	}

}
