package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;
import huard.iws.model.PageBodyImage;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.ImageGalleryService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class GalleryHelperController extends GeneralWebsiteController{
	private static final Logger logger = Logger.getLogger(GalleryController.class);
	
	public ModelAndView handleRequestWebsite(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		String action = request.getParameter("action", "");
		String json="";
		if (action.equals("getCategoryPictures")){
			int category=1;
			List<ImageGalleryItem> imageGalleryItems = imageGalleryService.getImageGalleryItems(category, userPersonBean);
			json="[";
			for(ImageGalleryItem imageGalleryItem : imageGalleryItems){
				json+="{\"title\":\""+imageGalleryItem.getText()+"\", \"url\":\""+imageGalleryItem.getTitle()+"\"},";
			}
			json=json.substring(0, json.length()-1) +"]";
		}
		if(action.equals("getPoolPictures")){
			List<PageBodyImage> pageBodyImages =pageBodyImageService.getPageBodyImages(100,0,userPersonBean);
			json="[";
			for (PageBodyImage pageBodyImage: pageBodyImages){
				json+="{\"title\":\""+pageBodyImage.getName()+"\", \"url\":\""+pageBodyImage.getTitle()+"\"},";
			}
			json=json.substring(0, json.length()-1) +"]";
		}
		model.put("json", json);
		return new ModelAndView("galleryHelper",model);

	}
	private ImageGalleryService imageGalleryService;

	public void setImageGalleryService(
			ImageGalleryService imageGalleryService) {
		this.imageGalleryService = imageGalleryService;
	}
	private PageBodyImageService pageBodyImageService;

	public void setPageBodyImageService(
			PageBodyImageService pageBodyImageService) {
		this.pageBodyImageService = pageBodyImageService;
	}
}
