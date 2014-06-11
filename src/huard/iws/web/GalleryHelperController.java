package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;
import huard.iws.model.PageBodyImage;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.ImageGalleryService;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GalleryHelperController extends GeneralWebsiteController{
	private static final Logger logger = Logger.getLogger(GalleryController.class);
	
	public ModelAndView handleRequestWebsite(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		String action = request.getParameter("action", "");
		int categoryId = request.getIntParameter("category", 0);
		if (action.equals("getCategoryPictures")){
			JSONArray jsonList = new JSONArray();
			List<ImageGalleryItem> imageGalleryItems = imageGalleryService.getImageGalleryItems(categoryId, userPersonBean);
			for(ImageGalleryItem imageGalleryItem : imageGalleryItems){
				JSONObject pictureobj = new JSONObject();
				pictureobj.put("title", imageGalleryItem.getText());
				pictureobj.put("url", imageGalleryItem.getTitle());
				jsonList.add(pictureobj);
			}
			model.put("json", jsonList.toJSONString());
		}
		if(action.equals("getPoolPictureNames")){
			List<PageBodyImage> pageBodyImages =pageBodyImageService.getFilteredPageBodyImages(request.getParameter("term", ""));
			JSONArray jsonList = new JSONArray();
			for (PageBodyImage pageBodyImage: pageBodyImages){
				JSONObject pictureobj = new JSONObject();
			    pictureobj.put("label", pageBodyImage.getName());
				pictureobj.put("id", pageBodyImage.getTitle());
				jsonList.add(pictureobj);
			}
			model.put("json",  jsonList.toJSONString());
		}
		/*if(action.equals("updateGalleryPicture")){
			int galleryPictureId=request.getIntParameter("galleryPictureId", 0);
			if(galleryPictureId==0){// new
				galleryPictureId=imageGalleryService.insertImageGalleryItem(categoryId, userPersonBean);
			}	
			ImageGalleryItem imageGalleryItem= imageGalleryService.getImageGalleryItem(galleryPictureId, userPersonBean);
			imageGalleryItem.setTitle(request.getParameter("poolPictureUrl", ""));
			imageGalleryItem.setText(request.getParameter("poolPictureTitle", ""));
			imageGalleryService.updateImageGalleryItem(imageGalleryItem, userPersonBean);
			return null;
		}*/
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
