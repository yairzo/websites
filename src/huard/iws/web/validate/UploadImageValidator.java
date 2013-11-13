package huard.iws.web.validate;

import java.util.Iterator;

import huard.iws.bean.PageBodyImageBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class UploadImageValidator implements Validator {
	public boolean supports (Class clazz){
		return clazz.equals(PageBodyImageBean.class);
	}

	public void validate ( Object command, Errors errors ){
			PageBodyImageBean pageBodyImageBean = (PageBodyImageBean) command;
			if(pageBodyImageBean.getId()>0){
				ValidationUtils.rejectIfEmpty(errors, "name", "iw_IL.required.name");
				ValidationUtils.rejectIfEmpty(errors, "captionHebrew", "iw_IL.required.captionHebrew");
				ValidationUtils.rejectIfEmpty(errors, "captionEnglish", "iw_IL.required.captionEnglish");
				//ValidationUtils.rejectIfEmpty(errors, "image", "iw_IL.required.image");
				ValidationUtils.rejectIfEmpty(errors, "title", "iw_IL.required.urlTitle");
				//check image format
				if(pageBodyImageBean.getImage().length>0){
					int imgwidth = pageBodyImageBean.getWidth();
					int imgheight = pageBodyImageBean.getHeight();
					int imgsize =pageBodyImageBean.getImage().length;
					String extention = "";
					byte [] img =  pageBodyImageBean.getImage();
					if(img[0]==-1 && img[1]==-40 )
						extention="jpg";
					//if(imgheight != 240 || imgwidth != 420 || imgsize > 300000 || !extention.equals("jpg"))
					if(imgsize > 300000 )
						errors.rejectValue( "image", "iw_IL.uploadImage.imageNotCorrect");
				}
			}
	
	}


}
