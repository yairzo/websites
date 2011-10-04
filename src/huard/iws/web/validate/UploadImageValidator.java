package huard.iws.web.validate;

import huard.iws.bean.PageBodyImageBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class UploadImageValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(PageBodyImageBean.class);
	}

	public void validate ( Object command, Errors errors ){
		PageBodyImageBean pageBodyImageBean = (PageBodyImageBean) command;
		if(pageBodyImageBean.getImage()!=null && pageBodyImageBean.getImage().length>0){
			ValidationUtils.rejectIfEmpty(errors, "name", "iw_IL.required.name");
			ValidationUtils.rejectIfEmpty(errors, "captionHebrew", "iw_IL.required.captionHebrew");
			ValidationUtils.rejectIfEmpty(errors, "captionEnglish", "iw_IL.required.captionEnglish");
			//ValidationUtils.rejectIfEmpty(errors, "image", "iw_IL.required.image");
		}
	}


}
