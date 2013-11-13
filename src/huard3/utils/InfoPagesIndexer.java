
package huard3.utils;
import huard3.actions.*;
import java.util.*;


public class InfoPagesIndexer {
	private InfoPagesQuery infoPagesQuery;
	private InfoPagesUpdate infoPagesUpdate;
	private TabledInfoPage[] infoPages;
	private char [] hebChars = new char [27];
	
	public InfoPagesIndexer(){
		infoPagesQuery = new InfoPagesQuery();
		infoPagesUpdate = new InfoPagesUpdate();
		for (int j=0; j<hebChars.length; j++){
			hebChars[j] = (char)(224+j);
		}
	}
	
	
	public void indexInfoPages(){
		if (infoPages==null) infoPages = infoPagesQuery.getAliveTabledInfoPages();
		for (int i=0; i<infoPages.length; i++){
			String text = infoPages[i].toContinuousString();
			WordsTokenizer wt = new WordsTokenizer(" ");
			List wordsList = wt.getSubstringsList(text);
			for (int j=0; j<wordsList.size(); j++){
				infoPagesUpdate.insertWordToInfoPagesIndexTable((String)wordsList.get(j), infoPages[i].getArdNum());
				
			}
		}
	}
	
	
	
	public String cutFromEnd(String word, String str){
		if (word.endsWith(str)) word = word.substring(0,word.indexOf(str));
		return word;
	}

}
