package huard.iws.db;

import huard.iws.model.PageBodyImage;
import huard.iws.bean.PersonBean;
import java.util.List;

public interface PageBodyImageDao {

	public int insertPageBodyImage(PageBodyImage pageBodyImage);

	public PageBodyImage getPageBodyImage(int id);

	public List<PageBodyImage> getPageBodyImages( int page, PersonBean userPersonBean);
	
	public List<PageBodyImage> getApprovedPageBodyImages();

	public void deletePageBodyImage(int id);

	public void approvePageBodyImage(int id);

	public int countImages( );

}
