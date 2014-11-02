package huard.iws.service;


import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import huard.iws.bean.PersonBean;
import huard.iws.db.ImageGalleryDao;
import huard.iws.model.ImageGalleryItem;
import huard.iws.util.LanguageUtils;

public class ImageGalleryServiceImpl implements ImageGalleryService{
	
	private static final Logger logger = Logger.getLogger(ImageGalleryServiceImpl.class);

	public List<ImageGalleryItem> getImageGalleryItems(int parentId, PersonBean userBean){
		List<ImageGalleryItem> imageGalleryItems = imageGalleryDao.getImageGalleryItems(parentId, userBean);
		return imageGalleryItems;		
	}
	
	public List<ImageGalleryItem> getImageGalleryItems(int parentId,boolean reverseOrder, PersonBean userBean){
		List<ImageGalleryItem> imageGalleryItems = imageGalleryDao.getImageGalleryItems(parentId, reverseOrder, userBean);
		return imageGalleryItems;
	}

	public List<ImageGalleryItem> getGalleries(){
		return imageGalleryDao.getGalleries();
	}

	public ImageGalleryItem getImageGalleryItem(int id, PersonBean userBean){
		return imageGalleryDao.getImageGalleryItem(id, userBean);
	}

	public int getCategory(String urlTitle){
		return imageGalleryDao.getCategory(urlTitle);
	}

	public void updateImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean){
		imageGalleryDao.updateImageGalleryItem(imageGalleryItem, userBean);
	}


	public void moveImageGalleryItemUp (int id, PersonBean userBean){
		ImageGalleryItem imageGalleryItem = getImageGalleryItem(id, userBean);
		List<ImageGalleryItem> imageGalleryItems = getImageGalleryItems(imageGalleryItem.getParentId(), userBean);
		for (ImageGalleryItem aImageGalleryItem: imageGalleryItems){
			int aImageGalleryItemPlace;
			if (imageGalleryItem.getPlace() - (aImageGalleryItemPlace = aImageGalleryItem.getPlace()) == 1){
				aImageGalleryItem.setPlace(imageGalleryItem.getPlace());
				imageGalleryItem.setPlace(aImageGalleryItemPlace);
				updateImageGalleryItem(imageGalleryItem, userBean);
				updateImageGalleryItem(aImageGalleryItem, userBean);
				break;
			}
		}
	}
	public void moveImageGalleryItemDown (int id, PersonBean userBean){
		ImageGalleryItem imageGalleryItem = getImageGalleryItem(id, userBean);
		List<ImageGalleryItem> imageGalleryItems = getImageGalleryItems(imageGalleryItem.getParentId(), userBean);
		for (ImageGalleryItem aImageGalleryItem: imageGalleryItems){
			int aImageGalleryItemPlace;
			if ((aImageGalleryItemPlace = aImageGalleryItem.getPlace())  - imageGalleryItem.getPlace() == 1){
				aImageGalleryItem.setPlace(imageGalleryItem.getPlace());
				imageGalleryItem.setPlace(aImageGalleryItemPlace);
				updateImageGalleryItem(imageGalleryItem, userBean);
				updateImageGalleryItem(aImageGalleryItem, userBean);
				break;
			}
		}
	}

	public void moveImageGalleryItemToLast (int id, PersonBean userBean){
		ImageGalleryItem imageGalleryItem = getImageGalleryItem(id, userBean);
		int imageGalleryItemsNum = getImageGalleryItems(imageGalleryItem.getParentId(), userBean).size();
		for (int i=0; i<imageGalleryItemsNum; i++){
			moveImageGalleryItemDown(id, userBean);
		}
	}
	
	public ImageGalleryItem getDefaultChildItem(List<ImageGalleryItem> imageGalleryItems){
		ImageGalleryItem imageGalleryItem = imageGalleryItems.get(0); 
		logger.debug("Gallery item: " + imageGalleryItem.getId() + " " + imageGalleryItem.getParentId());
		if (imageGalleryItem.getSubItems() == null || imageGalleryItem.getSubItems().isEmpty())
			return imageGalleryItem;
		return getDefaultChildItem(imageGalleryItem.getSubItems());			
	}

	
	public int insertImageGalleryItem (int parentId, PersonBean userBean){
		ImageGalleryItem parentImageGalleryItem = getImageGalleryItem(parentId, userBean);
		Locale locale = LanguageUtils.getLanguage(parentImageGalleryItem.getTitle()).getLocale();
		return imageGalleryDao.insertImageGalleryItem(parentId, locale, userBean);
	}
	
	public void insertImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean){
		imageGalleryDao.insertImageGalleryItem(imageGalleryItem, userBean);
	}

	public void deleteImageGalleryItem (int id, PersonBean userBean){
		ImageGalleryItem imageGalleryItem = getImageGalleryItem(id, userBean);
		int parentId = imageGalleryItem.getParentId();
		imageGalleryDao.rearangeImageGallery(imageGalleryItem.getPlace(),parentId, userBean);
		imageGalleryDao.deleteImageGalleryItem(id, userBean);
	}
	
	public void insertImageGalleryItems (List<ImageGalleryItem> imageGalleryItems, PersonBean userBean){
		for(ImageGalleryItem imageGalleryItem:imageGalleryItems){
			imageGalleryDao.insertImageGalleryItem(imageGalleryItem, userBean);
		}
	}

	public void prepareDeleteOldCategoryItems (int categoryId, PersonBean userBean){
		imageGalleryDao.prepareDeleteOldCategoryItems(categoryId,userBean);
	}

	public void deleteOldCategoryItems (int categoryId, PersonBean userBean){
		imageGalleryDao.deleteOldCategoryItems(categoryId,userBean);
	}

	private ImageGalleryDao imageGalleryDao;

	public void setImageGalleryDao(ImageGalleryDao imageGalleryDao) {
		this.imageGalleryDao = imageGalleryDao;
	}

}
