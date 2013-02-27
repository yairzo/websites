package huard.iws.service;

import java.util.List;

import huard.iws.model.TextualPage;
import huard.iws.model.Template;
import huard.iws.model.Category;
import huard.iws.model.TextualPageOld;

public interface TextualPageService {

	public TextualPage getTextualPage(int id);

	public boolean existsTextualPageOnline(int id);
	
	public TextualPage getTextualPage(String title);
	
	public int insertTextualPage(TextualPage textualPage);
	
	public void insertTextualPageOnline(TextualPage textualPage);

	public void updateTextualPage(TextualPage textualPage);

	public void updateTextualPageOnline(TextualPage textualPage);

	public void removeTextualPageOnline(int id);
	
	public List<TextualPage> getTextualPages();

	public List<TextualPage> getOnlineTextualPages();

	public void addTemplate(Template template);

	public Template getTemplate(int id);

	public void updateTemplate(Template template);

	public List<Template> getTemplates();

	public List<TextualPageOld> getTextualPagesOldWebsite(String server);
	
	public void insertArdNum(int ardNum,int id);


}
