package huard.iws.util;
import java.util.Set;

public class TextualPageSearchCreteria extends SearchCreteria{
	
	protected String searchBySearchWords;
	protected String searchWords;
	protected int searchByCreator;
	protected boolean searchDeleted;
	protected boolean searchList;
	
	
	public TextualPageSearchCreteria(){
		super();
		this.searchBySearchWords= "";
		this.searchWords="";
		this.searchByCreator=0;
		this.searchDeleted=false;
		this.searchList=false;
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

	
	public boolean getSearchDeleted() {
		return searchDeleted;
	}

	public void setSearchDeleted(boolean searchDeleted) {
		this.searchDeleted = searchDeleted;
	}
	public boolean getSearchList() {
		return searchList;
	}

	public void setSearchList(boolean searchList) {
		this.searchList = searchList;
	}

}