package huard.iws.web;

import huard.iws.bean.RegistrationFormBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Abstract;
import huard.iws.model.RegistrationForm;
import huard.iws.service.RegistrationFormService;
import huard.iws.util.RequestWrapper;
import huard.iws.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class RegistrationFormController extends GeneralWebsiteFormController{
	
	private static final Logger logger = Logger.getLogger(GeneralFormController.class);
	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		RegistrationFormBean registrationFormBean = (RegistrationFormBean) command;

		Map<String, Object> newModel = new HashMap<String, Object>();

		if(request.getIntParameter("attachmentId", 0) > 0){
			registrationFormService.deleteFile(request.getIntParameter("attachmentId", 0));
			newModel.put("id", registrationFormBean.getId())	;
			return new ModelAndView(new RedirectView("registrationForm.html"), newModel);
		}
		
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			if( fileNames.hasNext()){
				String filename = (String) fileNames.next();
				if(multipartRequest.getFile(filename).getSize()>0){
					MultipartFile file = multipartRequest.getFile(filename);
					String originalName = file.getOriginalFilename();
					String ext = originalName.substring(originalName.lastIndexOf(".")+1);
					Abstract attachment = new Abstract();
					attachment.setFile(file.getBytes());
					attachment.setContentType(TextUtils.getContentType(ext));
					attachment.setFilename("Abstract_"+System.currentTimeMillis()+"."+ext);
					attachment.setSubject(request.getParameter("subject",""));
					attachment.setMethodPresentation(request.getBooleanParameter("methodPresentation",false));
					registrationFormService.insertAttachmentToRegistrationForm(registrationFormBean.getId(), attachment);
				}
			}
		}	
		
		//if(request.getParameter("action", "").equals("delete"))
		//	registrationFormBean.setIsDeleted(1);

		registrationFormService.updateRegistrationForm(registrationFormBean.toRegistrationForm());
		if(request.getBooleanParameter("newForm", false))
			mailMessageService.createRegistrationFormMail( registrationFormBean,"registrationForm");
			
		//if(request.getParameter("action", "").equals("delete"))
		//	return new ModelAndView(new RedirectView("registrationForms.html"));

		
		return new ModelAndView(new RedirectView("homePage.html"), newModel);
	}

	
	protected ModelAndView onShowFormWebsite(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{
		
		RegistrationFormBean registrationFormBean = (RegistrationFormBean) model.get("command");
		
		if(registrationFormBean.getId()==0 && !request.getParameter("action", "").equals("new"))//someone entered non-existing id
			return new ModelAndView ( "pageNotFound", model);
		
		int id = request.getIntParameter("id", 0);
		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("new") || id == 0){
			RegistrationForm registrationForm= new RegistrationForm();
			int registrationFormId = registrationFormService.insertRegistrationForm(registrationForm);
			logger.info("registrationFormId " + registrationFormId);
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",registrationFormId);
			newModel.put("newForm",true);
			return new ModelAndView ( new RedirectView("registrationForm.html"), newModel);
		}
		else{//show edit
			model.put("newForm",request.getBooleanParameter("newForm", false));
			model.put("id",registrationFormBean.getId());
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		RegistrationFormBean registrationFormBean = new RegistrationFormBean();
		//logger.info("action : " + request.getParameter("action",""));
		
		int id = request.getIntParameter("id", 0);
		logger.info("id: " + id);
		
		
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return registrationFormBean;
		
		registrationFormBean = new RegistrationFormBean(registrationFormService.getRegistrationForm(id));
		
		return registrationFormBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	
	
	private RegistrationFormService registrationFormService;
	
	public void setRegistrationFormService(
			RegistrationFormService registrationFormService) {
		this.registrationFormService = registrationFormService;
	}

}
