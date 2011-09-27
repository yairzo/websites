package huard.iws.service;

import huard.iws.db.PageBodyImageDao;
import huard.iws.model.PageBodyImage;

import java.util.List;

public class PageBodyImageServiceImpl implements PageBodyImageService{

	public int insertPageBodyImage(PageBodyImage pageBodyImage){
		return pageBodyImageDao.insertPageBodyImage(pageBodyImage);
	}

	public synchronized PageBodyImage getPageBodyImage(int id){
		return pageBodyImageDao.getPageBodyImage(id);
	}

	public List<PageBodyImage> getPageBodyImages( int page){
		return pageBodyImageDao.getPageBodyImages( page);
	}

	public void deletePageBodyImage(int id){
		pageBodyImageDao.deletePageBodyImage(id);
	}

	public int countImages (){
		return pageBodyImageDao.countImages ();
	}

	PageBodyImageDao pageBodyImageDao;

	public void setPageBodyImageDao(PageBodyImageDao pageBodyImageDao) {
		this.pageBodyImageDao = pageBodyImageDao;
	}

}
