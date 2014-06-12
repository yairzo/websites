package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;
import huard.iws.model.PageBodyImage;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.ImageGalleryService;
import huard.iws.util.RequestWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

		if(action.equals("addPoolPicture")){
			if (request.getRequest().getContentType().indexOf("multipart")!=-1){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
				Iterator fileNames = multipartRequest.getFileNames();
				if (fileNames.hasNext()) {
					String filename = (String) fileNames.next();
					MultipartFile file = multipartRequest.getFile(filename);
					if (file.getSize()>0){
						PageBodyImageBean pageBodyImageBean = new PageBodyImageBean();
						try {
							pageBodyImageBean.setImage(file.getBytes());
							pageBodyImageBean.setName(request.getParameter("newPoolPictureName", "galleryItem"+new Date().getTime()));
							pageBodyImageBean.setTitle("galleryItem"+new Date().getTime());
							pageBodyImageService.insertPageBodyImage(pageBodyImageBean.toPageBodyImage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return null;
		}
		if(action.equals("save")){
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(request.getParameter("pictures", ""));
				JSONArray jsonList = (JSONArray) obj;
				List<ImageGalleryItem> imageGalleryItems= new ArrayList<ImageGalleryItem>();
				imageGalleryService.prepareDeleteOldCategoryItems(categoryId,userPersonBean);
				for (int i=0;i<jsonList.size();i++) {
					JSONObject pictureobj = (JSONObject)jsonList.get(i);
					ImageGalleryItem imageGalleryItem= new ImageGalleryItem();
					imageGalleryItem.setParentId(categoryId);
					imageGalleryItem.setTitle(pictureobj.get("url").toString());
					imageGalleryItem.setText(pictureobj.get("title").toString());
					imageGalleryItem.setPlace(new Integer(pictureobj.get("counter").toString()));
					imageGalleryItems.add(imageGalleryItem);
				}
				imageGalleryService.insertImageGalleryItems(imageGalleryItems, userPersonBean);
				imageGalleryService.deleteOldCategoryItems(categoryId,userPersonBean);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
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
