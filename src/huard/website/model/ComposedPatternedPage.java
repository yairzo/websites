package huard.website.model;
import huard.website.baseDb.*;
import huard.website.util.*;


public class ComposedPatternedPage extends ArdPage {
	private String columnsComposition;
	private String selectiveIndexingCondition;
	private String fileName;
	private String foundBySearchWords;
	private String keywords;

	private DbHandler dbHandler;

	public ComposedPatternedPage() {
		super();
		dbHandler = new DbHandler();
	}

	public String toString(){
		String content = dbHandler.getTablesContentByColumnsComposition(columnsComposition, selectiveIndexingCondition);
		content = content.concat(title+" "+keywords);
		return content;
	}

	public boolean isFoundBySearchWordsHebrew(){
		return Utils.isHebrew(foundBySearchWords);
	}

	public String getPureFileName(){
		return fileName.substring(0, fileName.indexOf("?")!=-1 ? fileName.indexOf("?") : fileName.length());
	}

	public String getFileNameParameters(){
		return fileName.substring(fileName.indexOf("?")!=-1 ? fileName.indexOf("?")+1 : fileName.length());
	}
	public boolean isFileNameHasParameters(){
		return fileName.indexOf("?")!=-1;
	}
	/**
	 * @return
	 */
	public String getColumnsComposition() {
		return columnsComposition;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return
	 */
	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	/**
	 * @param string
	 */
	public void setColumnsComposition(String string) {
		columnsComposition = string;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
	}

	/**
	 * @return
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param string
	 */
	public void setKeywords(String string) {
		keywords = string;
	}

	public String getEnglishTitle(){
		return title.substring(title.indexOf("_")+1);
	}

	public String getHebrewTitle(){
		return title.substring(0,title.indexOf("_"));
	}

	public String getSelectiveIndexingCondition() {
		return selectiveIndexingCondition;
	}

	public void setSelectiveIndexingCondition(String selectiveIndexingCondition) {
		this.selectiveIndexingCondition = selectiveIndexingCondition;
	}
}
