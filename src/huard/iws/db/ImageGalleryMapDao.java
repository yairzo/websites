package huard.iws.db;

import java.util.Map;

import huard.iws.bean.PersonBean;
import huard.iws.model.ImageGalleryItem;

public interface ImageGalleryMapDao {
	
	public Map<Integer, ImageGalleryItem> getImageGalleryMap (PersonBean userBean);
	
	public void invalidateImageGalleryMap(PersonBean userBean);
	
	public void printImageGalleryMap(PersonBean userBean);

}
