package huard.iws.db;

import huard.iws.model.PageBodyImage;

import java.util.List;

public interface PageBodyImageDao {

	public int insertPageBodyImage(PageBodyImage pageBodyImage);

	public PageBodyImage getPageBodyImage(int id);

	public List<PageBodyImage> getPageBodyImages( int page);

	public void deletePageBodyImage(int id);

	public int countImages( );

}
