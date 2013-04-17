package huard.iws.util;
import java.util.Set;

public class TextualPageSearchCreteria extends SearchCreteria{
	
	protected String searchBySearchWords;
	protected String searchWords;
	protected int searchByCreator;
	
	
	public TextualPageSearchCreteria(){
		super();
		this.searchBySearchWords= "";
		this.searchWords="";
		this.searchByCreator=0;
	}


	public String getSearchBySearchWords() {
		return searchBySearchWords;
	}

	public void setSearchBySearchWords(Set<Long> searchBySearchWords) {
		this.searchBySearchWords = BaseUtils.getStringFromLongSet(searchBySearchWords);
	}
	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}
	
	public int getSearchByCreator() {
		return searchByCreator;
	}

	public void setSearchByCreator(int searchByCreator) {
		this.searchByCreator = searchByCreator;
	}


}