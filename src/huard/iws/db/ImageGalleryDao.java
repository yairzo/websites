package huard.iws.db;


import java.util.List;
import java.util.Locale;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;

public interface ImageGalleryDao {

	public List<ImageGalleryItem> getGalleries();

	public List<ImageGalleryItem> getImageGalleryItems(int parentId, PersonBean userBean);
	
	public List<ImageGalleryItem> getImageGalleryItems(int parentId, boolean reverseOrder, PersonBean userBean);

	public ImageGalleryItem getImageGalleryItem(int id, PersonBean userBean);

	public int getCategory(String urlTitle);

	public void updateImageGalleryItem(ImageGalleryItem imageGalleryItem, PersonBean userBean);

	public int insertImageGalleryItem(int parentId, Locale locale, PersonBean userBean);

	public void deleteImageGalleryItem(int id, PersonBean userBean);

	public void rearangeImageGallery(int place, int parentId, PersonBean userBean);
	
	public void insertImageGalleryItem(ImageGalleryItem imageGalleryItem, PersonBean userBean);

	public void prepareDeleteOldCategoryItems (int categoryId, PersonBean userBean);

	public void deleteOldCategoryItems (int categoryId, PersonBean userBean);


}
