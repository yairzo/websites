package huard.iws.web;

import huard.iws.bean.PersonBean;
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
		
		if(userPersonBean.isAuthorized("ROLE_WEBSITE_ADMIN"))
			model.put("canEditGallery", true);
		String page =configurationService.getConfigurationString("iws", "websiteName").equals("websiteNano")?"galleryNano":"gallery";
		return new ModelAndView (page,model);


	}
}
