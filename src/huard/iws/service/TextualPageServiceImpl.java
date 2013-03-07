package huard.iws.service;

import huard.iws.db.TextualPageDao;
import huard.iws.model.Template;
import huard.iws.model.TextualPage;
import huard.iws.model.TextualPageOld;

import java.util.List;

public class TextualPageServiceImpl implements TextualPageService{

	public TextualPage getTextualPage(int id){
		return textualPageDao.getTextualPage(id);
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
	
	public List<TextualPage> getTextualPages(int creatorId){
		return textualPageDao.getTextualPages(creatorId);
	}

	public List<TextualPage> getOnlineTextualPagesSearch(String ids){
		return textualPageDao.getOnlineTextualPagesSearch(ids);
	}

	public List<TextualPage> getOnlineTextualPages(){
		return textualPageDao.getOnlineTextualPages();
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

}
