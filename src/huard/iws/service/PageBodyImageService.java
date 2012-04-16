package huard.iws.service;

import huard.iws.model.PageBodyImage;
import huard.iws.bean.PersonBean;

import java.util.List;

public interface PageBodyImageService {

	public int insertPageBodyImage(PageBodyImage pageBodyImage);

	public PageBodyImage getPageBodyImage(int id);

	public List<PageBodyImage> getPageBodyImages( int page, PersonBean personBean);

	public List<PageBodyImage> getApprovedPageBodyImages();

	public void deletePageBodyImage(int id);

	public void approvePageBodyImage(int id);

	public int countImages();

}
