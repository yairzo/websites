package huard.iws.service;


import java.util.List;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;

public interface ImageGalleryService {

	public List<ImageGalleryItem> getImageGalleryItems(int parentId, PersonBean userBean);
	
	public List<ImageGalleryItem> getImageGalleryItems(int parentId, boolean reverseOrder, PersonBean userBean);

	public ImageGalleryItem getImageGalleryItem(int id, PersonBean userBean);

	public void moveImageGalleryItemUp (int id, PersonBean userBean);

	public void moveImageGalleryItemDown (int id, PersonBean userBean);

	public void moveImageGalleryItemToLast (int id, PersonBean userBean);

	public int insertImageGalleryItem (int parentId, PersonBean userBean);

	public void updateImageGalleryItem (ImageGalleryItem imageGalleryItem, PersonBean userBean);

	public void deleteImageGalleryItem (int id, PersonBean userBean);
	
	public ImageGalleryItem getDefaultChildItem(List<ImageGalleryItem> imageGalleryItems);

	public void insertImageGalleryItems (List<ImageGalleryItem> imageGalleryItems, PersonBean userBean);

	public void prepareDeleteOldCategoryItems (int categoryId, PersonBean userBean);

	public void deleteOldCategoryItems (int categoryId, PersonBean userBean);

}
