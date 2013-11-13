
package huard3.utils;
import huard3.actions.*;
import java.util.*;

public class PubPagesIndexer {
	private PubPagesQuery pubPagesQuery;
	private PubPagesUpdate pubPagesUpdate;
	private PubPage[] pubPages;
	private char [] hebChars = new char [27];
	
	public PubPagesIndexer(){
		pubPagesQuery = new PubPagesQuery();
		pubPagesUpdate = new PubPagesUpdate();
		for (int j=0; j<hebChars.length; j++){
			hebChars[j] = (char)(224+j);
		}
	}
	
	
	public void indexPubPages(){
		if (pubPages==null) pubPages = pubPagesQuery.getAliveAndOnSitePubPages();
		for (int i=0; i<pubPages.length; i++){
			String text = pubPages[i].getTitle() + " "+ pubPages[i].getHtml();
			WordsTokenizer wt = new WordsTokenizer(" ");
			List wordsList = wt.getSubstringsList(text);
			for (int j=0; j<wordsList.size(); j++){
				pubPagesUpdate.insertWordToPubPagesIndexTable((String)wordsList.get(j), pubPages[i].getArdNum());
			
			}
		}
	}
	
	
	
		public String cutFromEnd(String word, String str){
			if (word.endsWith(str)) word = word.substring(0,word.indexOf(str));
			return word;
		}

}
