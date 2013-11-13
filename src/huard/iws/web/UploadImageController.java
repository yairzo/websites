package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.TextualPageBean;
import huard.iws.model.PageBodyImage;
import huard.iws.model.TextualPage;
import huard.iws.service.MessageService;
import huard.iws.service.PageBodyImageService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class UploadImageController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command, Map<String, Object> model,
			RequestWrapper request, PersonBean userPersonBean) throws Exception {
		PageBodyImageBean pageBodyImageBean = (PageBodyImageBean) command;

		PageBodyImage pageBodyImageOld =pageBodyImageService.getPageBodyImage(pageBodyImageBean.getId());
		if(pageBodyImageBean.getImage().length==0)
			pageBodyImageBean.setImage(pageBodyImageOld.getImage());

		//update
		pageBodyImageService.updatePageBodyImage(pageBodyImageBean.toPageBodyImage());

		Map<String,Object> newModel = new HashMap<String, Object>();
		newModel.put("id", pageBodyImageBean.getId())	;

		return new ModelAndView(new RedirectView("uploadImage.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request,
			HttpServletResponse response, PersonBean userPersonBean,
			Map<String, Object> model) throws Exception {
		
		PageBodyImageBean pageBodyImageBean = (PageBodyImageBean) model.get("command");
		int id = request.getIntParameter("id", 0);
		if (request.getParameter("action", "").equals("new")){
			int uploaderId = userPersonBean.getId();
			pageBodyImageBean.setUploaderPersonId(uploaderId);			
			int imageId = pageBodyImageService.insertPageBodyImage(pageBodyImageBean.toPageBodyImage());
			Map<String, Object> newModel = new HashMap<String, Object>();
			newModel.put("id",imageId);
			return new ModelAndView ( new RedirectView("uploadImage.html"), newModel);
		}
		else{
			if(pageBodyImageBean.getTitle().startsWith("###"))
				pageBodyImageBean.setTitle("");
		}
		
		  int page = 0;
		  String aPage;
		  if ( request.getParameter("page","") != null && !request.getParameter("page","").equals("")){
			  aPage = request.getParameter("page","");
			  page = Integer.parseInt(aPage);
		  }
		  model.put("page", page);
		  model.put("previousPage", Math.max(0, page -1));
		  model.put("nextPage", Math.min(pageBodyImageService.countImages(),  page + 1));

		  List<PageBodyImage> pageBodyImages =	  pageBodyImageService.getPageBodyImages(page,userPersonBean);

		  List<PageBodyImageBean> pageBodyImagesBeans = new  ArrayList<PageBodyImageBean>();
		  for (PageBodyImage pageBodyImage: pageBodyImages){
			  pageBodyImagesBeans.add( new	PageBodyImageBean(pageBodyImage));
		  }

		  model.put("images", pageBodyImagesBeans);

		return new ModelAndView(this.getFormView(), model);
	}

	protected Object getFormBackingObject(RequestWrapper request,
			PersonBean userPersonBean) throws Exception {
		
		PageBodyImageBean pageBodyImageBean = new PageBodyImageBean();
		int id = request.getIntParameter("id", 0);
		if ( isFormSubmission(request.getRequest()) 
				|| request.getParameter("action","").equals("new")
				|| id == 0)
			return pageBodyImageBean;
		

		pageBodyImageBean = new PageBodyImageBean(pageBodyImageService.getPageBodyImage(id));
		//logger.info("image id: " + pageBodyImageBean.getId());		
		

		return pageBodyImageBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

	private PageBodyImageService pageBodyImageService;

	public void setPageBodyImageService(
			PageBodyImageService pageBodyImageService) {
		this.pageBodyImageService = pageBodyImageService;
	}

}
