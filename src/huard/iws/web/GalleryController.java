package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;
import huard.iws.service.ImageGalleryService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class GalleryController extends GeneralWebsiteController{
	private static final Logger logger = Logger.getLogger(GalleryController.class);
	
	public ModelAndView handleRequestWebsite(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		int category= request.getIntParameter("category", 0);
		model.put("pictureCategory", category);
		
		//get title
		ImageGalleryItem imageGalleryItem = imageGalleryService.getImageGalleryItem(category, userPersonBean);
		model.put("title", imageGalleryItem.getText());
		
		model.put("isLink", imageGalleryItem.isLink());
		
		if(userPersonBean.isAuthorized("ROLE_WEBSITE_ADMIN"))
			model.put("canEditGallery", true);
		String page =configurationService.getConfigurationString("iws", "websiteName").equals("websiteNano")?"galleryNano":"gallery";
		return new ModelAndView (page,model);


	}
	private ImageGalleryService imageGalleryService;

	public void setImageGalleryService(ImageGalleryService imageGalleryService) {
		this.imageGalleryService = imageGalleryService;
	}
}
