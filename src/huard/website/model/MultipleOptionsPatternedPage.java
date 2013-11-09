package huard.website.model;
import huard.website.baseDb.*;
import huard.website.util.*;

import java.util.*;


public class MultipleOptionsPatternedPage extends ArdPage{
	private String contentOptionsTable;
	private String fileName;
	private String foundBySearchWords;
	private String keywords;
	private DbHandler dbHandler;

	public MultipleOptionsPatternedPage(){
		super();
		dbHandler = new DbHandler();
	}

	public String toString(){
		List contentOptionsList = dbHandler.getContentOptions( contentOptionsTable );
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<contentOptionsList.size(); i++){
			sb.append((String)contentOptionsList.get(i)+" ");
		}
		sb.append(keywords);
		return sb.toString();
	}

	public String getContentOptionsName(){
		//String contentOptionsName = "";
		//if (contentOptionsTable!=null){
			String contentOptionsName = contentOptionsTable.substring(0,1).toLowerCase()+contentOptionsTable.substring(1,contentOptionsTable.lastIndexOf("s"));
		//}
		return contentOptionsName;
	}

	public String getHebrewContentOptionByRessemblanceToFoundBySearchWords(boolean optionalPluralForm){
		WordsTokenizer wt  = new WordsTokenizer(",");
		List wordsList = wt.getSubstringsList(foundBySearchWords);
		String contentOption = dbHandler.getHebrewContentOptionByRessemblanceToWordsList(contentOptionsTable, wordsList, getContentOptionsName(), optionalPluralForm);
		return contentOption;
	}

	public String getEnglishContentOptionByRessemblanceToFoundBySearchWords(boolean optionalPluralForm){
		WordsTokenizer wt  = new WordsTokenizer(",");
		List wordsList = wt.getSubstringsList(foundBySearchWords);
		String contentOption = dbHandler.getEnglishContentOptionByRessemblanceToWordsList(contentOptionsTable, wordsList, getContentOptionsName(), optionalPluralForm);
		return contentOption;
	}


	public String getContentOptionByRessemblanceToFoundBySearchWords(boolean heb, boolean optionalPluralForm){
		WordsTokenizer wt  = new WordsTokenizer(",");
		List wordsList = wt.getSubstringsList(foundBySearchWords);
		String contentOption = "";
		if (heb) contentOption = dbHandler.getHebrewContentOptionByRessemblanceToWordsList(contentOptionsTable, wordsList, getContentOptionsName(), optionalPluralForm);
		else contentOption = dbHandler.getEnglishContentOptionByRessemblanceToWordsList(contentOptionsTable, wordsList, getContentOptionsName(), optionalPluralForm);
		return contentOption;
	}

	public String getEnglishTitle(){
		return title.substring(title.indexOf("_")+1);
	}

	public String getHebrewTitle(){
		return title.substring(0,title.indexOf("_"));
	}




	/**
	 * @return
	 */
	public String getContentOptionsTable() {
		return contentOptionsTable;
	}

	/**
	 * @param string
	 */
	public void setContentOptionsTable(String string) {
		contentOptionsTable = string;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
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

}
