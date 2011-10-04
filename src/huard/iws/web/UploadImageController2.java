package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;
import huard.iws.model.PageBodyImage;
import huard.iws.service.MessageService;
import huard.iws.service.PageBodyImageService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import java.io.*;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadImageController2 extends GeneralFormController {

	protected ModelAndView onSubmit(Object command, Map<String, Object> model,
			RequestWrapper request, PersonBean userPersonBean) throws Exception {
		PageBodyImageBean pageBodyImageBean = (PageBodyImageBean) command;
		String action = request.getParameter("action", "");

		if (action.equals("cancel")){
			return new ModelAndView( new RedirectView("welcome.html"));
		}
		else if (action != null && action.equals("delete")) {
			String id = request.getParameter("imageId", "");
			pageBodyImageService.deletePageBodyImage(Integer.parseInt(id));
		}
		else if (action != null && action.equals("approve")) {
			String id = request.getParameter("imageId", "");
			pageBodyImageService.approvePageBodyImage(Integer.parseInt(id));
		} else {
			//check size and format
			int imgwidth = pageBodyImageBean.getWidth();
			int imgheight = pageBodyImageBean.getHeight();
			int imgsize =pageBodyImageBean.getImage().length;
			String extention = "";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String filename = (String) fileNames.next();
				MultipartFile file = multipartRequest.getFile(filename);
				String originalFilename = file.getOriginalFilename();
				extention = originalFilename.substring(originalFilename.indexOf(".")+1);
			}
			if(imgheight==240 && imgwidth==420 && imgsize< 300000 && extention.equalsIgnoreCase("jpg")){
				int uploaderId = userPersonBean.getId();
				pageBodyImageBean.setUploaderPersonId(uploaderId);
				pageBodyImageService.insertPageBodyImage(pageBodyImageBean.toPageBodyImage());
			}
		    else{
				String userMessage = messageService.getMessage("iw_IL.uploadImage.imageNotCorrect");
				request.getSession().setAttribute("userMessage", userMessage);
		    }
		}
		
		return new ModelAndView(new RedirectView("uploadImage.html"));
	}

	protected ModelAndView onShowForm(RequestWrapper request,
			HttpServletResponse response, PersonBean userPersonBean,
			Map<String, Object> model) throws Exception {

		
		  int page = 0; 
		  String aPage; 
		  if ( request.getParameter("page","") != null && !request.getParameter("page","").equals("")){ 
			  aPage = request.getParameter("page","");
			  page = Integer.parseInt(aPage); 
		  } 
		  model.put("page", page);
		  model.put("previousPage", Math.max(0, page -1));
		  model.put("nextPage", Math.min(pageBodyImageService.countImages(),  page + 1));

		  List<PageBodyImage> pageBodyImages =	  pageBodyImageService.getPageBodyImages(page);
		  
		  List<PageBodyImageBean> pageBodyImagesBeans = new  ArrayList<PageBodyImageBean>(); 
		  for (PageBodyImage pageBodyImage: pageBodyImages){ 
			  pageBodyImagesBeans.add( new	PageBodyImageBean(pageBodyImage)); 
		  }
		  
		  model.put("images", pageBodyImagesBeans);

		return new ModelAndView(this.getFormView(), model);
	}

	protected Object getFormBackingObject(RequestWrapper requestWrapper,
			PersonBean userPersonBean) throws Exception {
		PageBodyImageBean pageBodyImageBean = new PageBodyImageBean();

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
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


}
