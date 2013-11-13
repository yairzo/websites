package huard.iws.service;

import huard.iws.db.PageBodyImageDao;
import huard.iws.model.PageBodyImage;
import huard.iws.bean.PersonBean;

import java.util.List;

public class PageBodyImageServiceImpl implements PageBodyImageService{

	public int insertPageBodyImage(PageBodyImage pageBodyImage){
		return pageBodyImageDao.insertPageBodyImage(pageBodyImage);
	}

	public synchronized PageBodyImage getPageBodyImage(int id){
		return pageBodyImageDao.getPageBodyImage(id);
	}
	
	public synchronized PageBodyImage getPageBodyImage(String urlTitle){
		return pageBodyImageDao.getPageBodyImage(urlTitle);
	}

	public List<PageBodyImage> getPageBodyImages( int page, PersonBean personBean){
		return pageBodyImageDao.getPageBodyImages( page,personBean);
	}

	public List<PageBodyImage> getApprovedPageBodyImages(){
		return pageBodyImageDao.getApprovedPageBodyImages();
	}

	public void deletePageBodyImage(int id){
		pageBodyImageDao.deletePageBodyImage(id);
	}

	public void approvePageBodyImage(int id){
		pageBodyImageDao.approvePageBodyImage(id);
	}

	public int countImages (){
		return pageBodyImageDao.countImages ();
	}
	
	public void updatePageBodyImage(PageBodyImage pageBodyImage){
		pageBodyImageDao.updatePageBodyImage(pageBodyImage);
	}

	PageBodyImageDao pageBodyImageDao;

	public void setPageBodyImageDao(PageBodyImageDao pageBodyImageDao) {
		this.pageBodyImageDao = pageBodyImageDao;
	}

}
