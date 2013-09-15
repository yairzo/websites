package huard.iws.service;

import huard.iws.db.TextualPageDao;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.model.TextualPageOld;
import huard.iws.util.TextualPageSearchCreteria;
import huard.iws.util.ListView;

import java.util.List;

public class TextualPageServiceImpl implements TextualPageService{

	public TextualPage getTextualPage(int id){
		return textualPageDao.getTextualPage(id);
	}

	public TextualPage getTextualPageOnline(int id){
		return textualPageDao.getTextualPageOnline(id);
	}
	
	public TextualPage getTextualPageOnline(String urlTitle){
		return textualPageDao.getTextualPageOnline(urlTitle);
	}
	
	public String getTextualPageUrlTitleByArdNum(int ardNum){
		return textualPageDao.getTextualPageUrlTitleByArdNum(ardNum);
	}

	public boolean existsTextualPageOnline(int id){
		return textualPageDao.existsTextualPageOnline(id);
	}
	
	public TextualPage getTextualPage(String title){
		return textualPageDao.getTextualPage(title);
	}
	
	public int insertTextualPage(TextualPage textualPage){
		return textualPageDao.insertTextualPage(textualPage);
	}
	
	public void insertTextualPageOnline(TextualPage textualPage){
		textualPageDao.insertTextualPageOnline(textualPage);
	}

	public void updateTextualPage(TextualPage textualPage){
		textualPageDao.updateTextualPage(textualPage);
	}
	
	public void updateTextualPageOnline(TextualPage textualPage){
		textualPageDao.updateTextualPageOnline(textualPage);
	}

	public void removeTextualPageOnline(int id){
		textualPageDao.removeTextualPageOnline(id);
	}
	

	public List<TextualPage> getTextualPages(ListView lv,TextualPageSearchCreteria searchCreteria){
		return textualPageDao.getTextualPages(lv,searchCreteria);
	}
	
	public void prepareListView(ListView lv, TextualPageSearchCreteria searchCreteria){
		lv.setCountRows(textualPageDao.countTextualPages(lv,searchCreteria));
		lv.setLastPage(lv.getNumOfPages());
		lv.setNearPages(lv.getScroll());
	}

	public List<TextualPage> getOnlineTextualPagesSearch(String ids){
		return textualPageDao.getOnlineTextualPagesSearch(ids);
	}
	
	public List<TextualPage> getOnlineMessagesSearch(String ids){
		return textualPageDao.getOnlineMessagesSearch(ids);
	}

	public List<TextualPage> getOnlineTextualPages(){
		return textualPageDao.getOnlineTextualPages();
	}
	
	public List<TextualPage> getOnlineMessages(){
		return textualPageDao.getOnlineMessages();
	}


	private TextualPageDao textualPageDao;

	public void setTextualPageDao(TextualPageDao textualPageDao) {
		this.textualPageDao = textualPageDao;
	}

	public void addTemplate(Template template){
		textualPageDao.addTemplate(template);
	}

	public Template getTemplate(int id){
		return textualPageDao.getTemplate(id);
	}

	public void updateTemplate(Template template){
		textualPageDao.updateTemplate(template);
	}

	public List<Template> getTemplates(){
		return textualPageDao.getTemplates();
	}

	public List<TextualPageOld> getTextualPagesOldWebsite(String server){
		return textualPageDao.getTextualPagesOldWebsite(server);
	}

	public void insertArdNum(int ardNum,int id){
		textualPageDao.insertArdNum(ardNum,id);
	}

	public int countTextualPagesByUrlTitle(int id,String urlTitle){
		return textualPageDao.countTextualPagesByUrlTitle(id,urlTitle);
	}

	public int countTextualPagesByTitle(int id,String title){
		return textualPageDao.countTextualPagesByTitle(id,title);
	}

}
