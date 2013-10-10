package huard.iws.service;

import java.sql.Timestamp;
import java.util.List;

import huard.iws.model.TextualPage;
import huard.iws.model.Template;
import huard.iws.model.TextualPageOld;
import huard.iws.util.ListView;
import huard.iws.util.TextualPageSearchCreteria;

public interface TextualPageService {

	public TextualPage getTextualPage(int id);

	public TextualPage getTextualPageOnline(int id);

	public TextualPage getTextualPageOnline(String urlTitle);

	public String getTextualPageUrlTitleByArdNum(int ardNum);

	public boolean existsTextualPageOnline(int id);
	
	public TextualPage getTextualPage(String title);
	
	public int insertTextualPage(TextualPage textualPage);
	
	public void insertTextualPageOnline(TextualPage textualPage);

	public void updateTextualPage(TextualPage textualPage);

	public void updateTextualPageOnline(TextualPage textualPage);

	public void removeTextualPageOnline(int id);
	
	public List<TextualPage> getTextualPages(ListView lv,TextualPageSearchCreteria searchCreteria);

	public void prepareListView(ListView lv,TextualPageSearchCreteria searchCreteria);
	
	public List<TextualPage> getOnlineTextualPages();
	
	public List<TextualPage> getOnlineMessages();

	public List<TextualPage> getOnlineTextualPagesSearch(String ids);
 
	public List<TextualPage> getOnlineMessagesSearch(String ids);
	
	public void addTemplate(Template template);

	public Template getTemplate(int id);

	public void updateTemplate(Template template);

	public List<Template> getTemplates();

	public List<TextualPageOld> getTextualPagesOldWebsite(String server);
	
	public void insertArdNum(int ardNum,int id);

	public int countTextualPagesByUrlTitle(int id,String urlTitle);

	public int countTextualPagesByTitle(int id,String title);
	
	public Timestamp getTextualPagesLastUpdate();

}
