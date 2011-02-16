package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.UploadImageBean;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class UploadImageController extends GeneralFormController{
		protected ModelAndView onSubmit(Object command,
				Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
				throws Exception{

			UploadImageBean uploadImageBean = (UploadImageBean) command;
			//if (request.getRequest().getContentType().indexOf("multipart")!=-1){
				//sivan
				//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
				//Iterator fileNames = multipartRequest.getFileNames();
				//while (fileNames.hasNext()) {
				//	String filename = (String) fileNames.next();
				//	MultipartFile file = multipartRequest.getFile(filename);
				//	if (file.getSize() == 0)
				//		continue;
				//	Attachment attachment = new Attachment();
				//	attachment.setTitle(file.getOriginalFilename());
				//	attachment.setFile(file.getBytes());
					//sivan
				//	attachment.setContentType(file.getContentType());
					//uploadImageBean.getAttachments().add(attachment);
				//}
			//}



			return new ModelAndView(new RedirectView("welcome.html"));
		}

		protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
				PersonBean userPersonBean, Map<String, Object> model) throws Exception{
			model.put("aNewMessage", request.getIntParameter("id", 0) == 0);

			return new ModelAndView(new RedirectView("welcome.html"));
		//	return new ModelAndView(getFormView(), model);
		}

		protected Object getFormBackingObject(
				RequestWrapper request, PersonBean userPersonBean) throws Exception{
			//create the same like mailMessageBean in /home/sivan/mop/workspace/iws/src/huard/iws/bean
			UploadImageBean uploadImageBean = new UploadImageBean();

			return uploadImageBean;
		}
}
