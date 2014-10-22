package huard.iws.web;

import huard.iws.bean.PageBodyImageBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;
import huard.iws.model.PageBodyImage;
import huard.iws.model.TextualPage;
import huard.iws.service.PageBodyImageService;
import huard.iws.service.ImageGalleryService;
import huard.iws.service.TextualPageService;
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
		int level = request.getIntParameter("level", 0);
		if (action.equals("getCategoryPictures")){
			JSONArray jsonList = new JSONArray();
			List<ImageGalleryItem> imageGalleryItems = imageGalleryService.getImageGalleryItems(categoryId, userPersonBean);
			for(ImageGalleryItem imageGalleryItem : imageGalleryItems){
				JSONObject pictureobj = new JSONObject();
				pictureobj.put("text", imageGalleryItem.getText());
				pictureobj.put("title", imageGalleryItem.getTitle());
				pictureobj.put("url", imageGalleryItem.getUrl());
				pictureobj.put("id", ""+imageGalleryItem.getId());
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
		if(action.equals("getTextualPageNames")){
			List<TextualPage> textualPages =textualPageService.getTextualPagesByTitle(request.getParameter("term", ""));
			JSONArray jsonList = new JSONArray();
			for (TextualPage textualPage: textualPages){
				JSONObject pageobj = new JSONObject();
				pageobj.put("label", textualPage.getUrlTitle());
				jsonList.add(pageobj);
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
				for (int i=0;i<jsonList.size();i++) {
					JSONObject pictureobj = (JSONObject)jsonList.get(i);
					String pictureId= pictureobj.get("id").toString();
					if(!pictureId.equals("0")){//update
						ImageGalleryItem imageGalleryItem= imageGalleryService.getImageGalleryItem(new Integer(pictureId).intValue(), userPersonBean);
						imageGalleryItem.setParentId(categoryId);
						imageGalleryItem.setLevel(level);
						imageGalleryItem.setTitle(pictureobj.get("title").toString());
						imageGalleryItem.setText(pictureobj.get("text").toString());
						imageGalleryItem.setUrl(pictureobj.get("url").toString());
						imageGalleryItem.setPlace(i+1);
						imageGalleryService.updateImageGalleryItem(imageGalleryItem, userPersonBean);
					}
					else{//new
						ImageGalleryItem imageGalleryItem= new ImageGalleryItem();
						imageGalleryItem.setParentId(categoryId);
						imageGalleryItem.setLevel(level);
						imageGalleryItem.setTitle(pictureobj.get("title").toString());
						imageGalleryItem.setText(pictureobj.get("text").toString());
						imageGalleryItem.setUrl(pictureobj.get("url").toString());
						imageGalleryItem.setPlace(i+1);
						imageGalleryItems.add(imageGalleryItem);
						imageGalleryService.insertImageGalleryItem(imageGalleryItem, userPersonBean);
					}
				}
				//delete
				obj = parser.parse(request.getParameter("deletedPictures", ""));
				jsonList = (JSONArray) obj;
				for (int i=0;i<jsonList.size();i++) {
					int pictureId=new Integer(jsonList.get(i).toString()).intValue();
					imageGalleryService.deleteImageGalleryItem(pictureId, userPersonBean);			
				}

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
	private TextualPageService textualPageService;

	public void setTextualPageService(
			TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
}
