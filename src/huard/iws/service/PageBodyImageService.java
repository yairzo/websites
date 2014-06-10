package huard.iws.service;

import huard.iws.model.PageBodyImage;
import huard.iws.bean.PersonBean;

import java.util.List;

public interface PageBodyImageService {

	public int insertPageBodyImage(PageBodyImage pageBodyImage);

	public PageBodyImage getPageBodyImage(int id);
	
	public PageBodyImage getPageBodyImage(String urlTitle);

	public List<PageBodyImage> getPageBodyImages(int imgsPerPage, int page, PersonBean personBean);

	public List<PageBodyImage> getFilteredPageBodyImages(String term);

	public List<PageBodyImage> getApprovedPageBodyImages(String localeId);

	public void deletePageBodyImage(int id);

	public void approvePageBodyImage(int id);

	public void disapprovePageBodyImage(int id);

	public int countImages();

	public int countImagePages(int imgsPerPage);

	public void updatePageBodyImage(PageBodyImage pageBodyImage);

	public int getMaxImageId();

}
