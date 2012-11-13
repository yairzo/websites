package huard.iws.db;

import java.util.List;

import huard.iws.model.TextualPage;

public interface TextualPageDao {

	public TextualPage getTextualPage(int id);

	public boolean existsTextualPageOnline(int id);
	
	public TextualPage getTextualPage(String title);
	
	public int insertTextualPage(TextualPage textualPage);
	
	public void insertTextualPageOnline(TextualPage textualPage);

	public void updateTextualPage(TextualPage textualPage);

	public void updateTextualPageOnline(TextualPage textualPage);

	public void removeTextualPageOnline(int id);
	
	public List<TextualPage> getTextualPages();


}
