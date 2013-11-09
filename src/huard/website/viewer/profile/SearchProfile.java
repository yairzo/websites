package huard.website.viewer.profile;
import huard.website.model.ComposedPatternedPage;
import huard.website.model.MultipleOptionsPatternedPage;
import huard.website.model.PubPage;
import huard.website.model.TabledInfoPage;
import huard.website.util.PageAccessLog;
import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.util.ArrayList;
import java.util.List;

public class SearchProfile extends Profile {

    private String keywords;
    private boolean fullList;
    private String orderBy;
    private List<TabledInfoPage> infoPages;
	private List<PubPage> pubPages;
	private List<MultipleOptionsPatternedPage>  multipleOptionsPatternedPages;
	private List<ComposedPatternedPage> composedPatternedPages;
	private List<String>keywordsList;
	private static String title = "Search Results_תוצאות החיפוש";
	private static String categoryTableNameHeb = "harashut_lemop";
	private static String categoryTableNameEng = "RD_authority";
	char [] hebChars = new char [27];
	//private static final Logger logger = Logger.getLogger(SearchProfile.class);



    public SearchProfile(){
        super();
    	keywords="";
    	fullList = false;
    	orderBy="subDate";
		for (int j=0; j<hebChars.length; j++){
			hebChars[j] = (char)(1488+j);
		}
    }

    public void logAccessToPage(boolean heb, String ip){
		PageAccessLog.logAccesToPage("Search",0,"תוצאות חיפוש",heb, ip);
	}

    public void setKeywords(String keywords){
    	this.keywords=keywords.toLowerCase();
    }

    public String getKeywords(){
    	return this.keywords;
    }

    public String getKeywordsQuotsHex(){
    	return this.keywords.replaceAll("\"","%22");
    }

    public String getKeywordsHebrew(){
		return this.keywords;
    }

   public List<String> getKeywordsList(){
       if(!(this.keywords.equals(""))){
    	   if (keywordsList==null){
    		   WordsTokenizer wt= new WordsTokenizer(",");
    		   keywordsList = wt.getSubstringsList(this.keywords);
    		   for (String keyword : keywordsList){
    			   keyword = keyword.replaceAll("\"","\\\\\""); // added 041206
    			   keyword = keyword.toLowerCase();
    		   }
    	   }
    	   return keywordsList;
       }
       return null;
	}


    public List<TabledInfoPage> getInfoPages(){
    	if (infoPages==null){
    		List<String> searchWords = getKeywordsList();
    		String searchWordsAsString = "";
    		for (String searchWord: searchWords){
    			searchWordsAsString = searchWordsAsString.concat(searchWord+", ");
    		}
    		searchWordsAsString = searchWordsAsString.substring(0, searchWordsAsString.lastIndexOf(","));
    		//SearchLogger.addRow("Searcher", "searching InfoPages", "Search words entered are: "+searchWordsAsString, false);
    		infoPages = dbHandler.searchForInfoPages(getKeywordsList(),handleKeywords(getKeywordsList()),fullList, orderBy);
    	}
    	return infoPages;
    }

    public List<MultipleOptionsPatternedPage> getMultipleOptionsPatternedPages(){
    	if (multipleOptionsPatternedPages==null){
    		multipleOptionsPatternedPages = dbHandler.searchForMultipleOptionsPatternedPages(getKeywordsList(), handleKeywords(getKeywordsList()), "Funding");
    	}
    	return multipleOptionsPatternedPages;
    }

	public List<ComposedPatternedPage> getComposedPatternedPages(){
		if (composedPatternedPages==null){
			composedPatternedPages = dbHandler.searchForComposedPatternedPages(getKeywordsList(), handleKeywords(getKeywordsList()), "Ard");
		}
		return composedPatternedPages;
	}

	public List<PubPage> getPubPages(){
		if (pubPages==null)
			pubPages = dbHandler.searchForPubPages(getKeywordsList(),handleKeywords(getKeywordsList()));
		return pubPages;
	}

	public List<String> handleKeywords(List<String> keywordsList){
			List<String> keywordsVariationsList = new ArrayList<String>();
			for (String keyword : keywordsList){
				List<String> singleKeywordVariationsList = new ArrayList<String>();
				if (keyword!=null){
					keyword = keyword.trim();
					if( keyword.length()>1)
					if (Utils.isHebrew(keyword)){ //don't cut letters from the end if the word has less than 5 letters
															//The order is important, cutting a final letter
															//and the letter before may expose a yood or heh that should not be further cutted
						if (isSuitableForStriping(keyword)){
																				// for cases of a keyword with two words
							keyword = cutFromEnd(keyword,""+hebChars[9]);
							keyword = cutFromEnd(keyword,""+hebChars[4]);
							if (isSuitableForStriping(keyword)){
								keyword = cutFromEnd(keyword,""+hebChars[5]+hebChars[26]);
								keyword = cutFromEnd(keyword,""+hebChars[9]+hebChars[13]);
								keyword = cutFromEnd(keyword,""+hebChars[9]+hebChars[26]);
								keyword = cutFromEnd(keyword,""+hebChars[13]);
							}

						}

//						 add the basic form of the word
						singleKeywordVariationsList.add(keyword);

//			handle a case the basic word has ends with a letter that has a final form
						String changedKeyword;
						if (! (changedKeyword=replaceRegularLetterByFinalLetter(keyword)).equals(keyword))
							singleKeywordVariationsList.add(changedKeyword);

//			handle the case the basic word that was entered has a final letter, it should be replaced by a regular letter before adding chars to it.
						if (! (changedKeyword=replaceFinalLetterByRegularLetter(keyword)).equals(keyword))
							singleKeywordVariationsList.add(changedKeyword);

						addFinalsVariations(singleKeywordVariationsList);
						addInitialsVariations(singleKeywordVariationsList);
						//addModernVsStandardVariations(singleKeywordVariationsList);


					}
					else{
						keyword = cutFromEnd(keyword,"ies");
						keyword = cutFromEnd(keyword,"s");
						keyword = cutFromEnd(keyword,"ly");
						keyword = cutFromEnd(keyword,"y");
						handleCase(singleKeywordVariationsList,keyword);
						handleCase(singleKeywordVariationsList,keyword+"s");
						handleCase(singleKeywordVariationsList,keyword+"ies");
						handleCase(singleKeywordVariationsList,keyword+"ly");
						handleCase(singleKeywordVariationsList,keyword+"y");
						handleCase(singleKeywordVariationsList,keyword+"\\\'s");
					}
				}
			keywordsVariationsList.addAll(singleKeywordVariationsList);
			}
			return keywordsVariationsList;
	}


	public boolean isSuitableForStriping(String word){
		if (word.length()>3){
			int pos;
			if ((pos = word.lastIndexOf(" "))!=-1){
				word = word.substring(pos);
			}
			if (word.length()==4){
				if (isCharInMiddleOfWord(word, hebChars[5])) return false;
				else if (isCharInMiddleOfWord(word, hebChars[9])) return false;
				else return true;
			}
			else return true;
		}
		return false;
	}

	public boolean isCharInMiddleOfWord (String word, char c){
		return (word.indexOf(c)!=-1 && word.indexOf(c)!=0 && word.indexOf(c)!=word.length()-1);
	}


	public String replaceAll(String s, String regexp, String replacement){
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos = s.indexOf(regexp))!=-1){
			sb.append(s.substring(0, pos)+replacement);
			s = s.substring(pos+regexp.length());
		}
		sb.append(s);
		return sb.toString();
	}
	public String replaceAtRightEnd(String s, String regexp, String replacement){
		StringBuffer sb = new StringBuffer();
		int pos;
		if ((pos=s.lastIndexOf(regexp))==s.length()-1){
			sb.append(s.substring(0, pos)+replacement);
		}
		else return s;
		return sb.toString();
	}


	public void addFinalsVariations(List<String> keywordsVariationsList){
		//The first word in the list is the basic form, it remains untouched in the case of finals
		int keywordsVariationsListOriginalSize = keywordsVariationsList.size();
		for(int i=0; i<keywordsVariationsListOriginalSize; i++){
			String keyword = (String)keywordsVariationsList.get(i);
			//keywordsVariationsList.remove(i);
			keywordsVariationsList.add(keyword+hebChars[5]+hebChars[26]);
			keywordsVariationsList.add(keyword+hebChars[9]+hebChars[13]);
			keywordsVariationsList.add(keyword+hebChars[9]+hebChars[9]+hebChars[13]);
			keywordsVariationsList.add(keyword+hebChars[9]+hebChars[26]);
			keywordsVariationsList.add(keyword+hebChars[9]+hebChars[5]+hebChars[26]);
			keywordsVariationsList.add(keyword+hebChars[26]);
			keywordsVariationsList.add(keyword+hebChars[4]);
			keywordsVariationsList.add(keyword+hebChars[9]);
			keywordsVariationsList.add(keyword+hebChars[13]);
			keywordsVariationsList.add(keyword+hebChars[15]);
		}
	}

	public void addInitialsVariations(List<String> keywordsVariationsList){
		//The first word in the list is the basic form, it remains untouched
		int keywordsVariationsListOriginalSize = keywordsVariationsList.size();
		for(int i=0; i<keywordsVariationsListOriginalSize; i++){
			String keyword = (String)keywordsVariationsList.get(i);
			keywordsVariationsList.add(hebChars[4]+keyword);
			keywordsVariationsList.add(hebChars[1]+keyword);
			keywordsVariationsList.add(hebChars[12]+keyword);
			keywordsVariationsList.add(hebChars[14]+keyword);
			keywordsVariationsList.add(hebChars[5]+keyword);
			keywordsVariationsList.add(hebChars[5]+""+hebChars[4]+keyword);
			keywordsVariationsList.add(hebChars[5]+""+hebChars[1]+keyword);
			keywordsVariationsList.add(hebChars[5]+""+hebChars[12]+keyword);
			keywordsVariationsList.add(hebChars[5]+""+hebChars[14]+keyword);
			keywordsVariationsList.add(hebChars[5]+""+hebChars[14]+""+hebChars[4]+keyword);
		}

		String handledKeywords="";
		for (String keyword: keywordsVariationsList){
			handledKeywords = handledKeywords.concat(keyword+", ");
		}
		//SearchLogger.addRow("Searcher", "SearchingInfoPages", handledKeywords, false);
	}

	public void addModernVsStandardVariations(List<String> keywordsVariationsList){
			//The first word in the list is the basic form, it remains untouched
			int keywordsVariationsListOriginalSize = keywordsVariationsList.size();
			for(int i=0; i<keywordsVariationsListOriginalSize; i++){
				String keyword = (String)keywordsVariationsList.get(i);
				if (keyword.length()>3 && keyword.substring(1,keyword.length()-1).indexOf(hebChars[9])!=-1) keyword = keyword.substring(0,1)+keyword.substring(1,keyword.length()-1).replaceAll(""+hebChars[9],"")+keyword.substring(keyword.length()-1);
				keywordsVariationsList.add(keyword); //no yoods inside the word, there may be yoods in the edges
				//adds one yood between any two letters
				for (int j=1; j<keyword.length(); j++){
					keywordsVariationsList.add(keyword.substring(0,j)+hebChars[9]+keyword.substring(j));
				}
				//adds two following yoods
				if (keywordsVariationsList.size()<1000)
					for (int j=1; j<keyword.length()-1; j++){
						keywordsVariationsList.add(keyword.substring(0,j)+hebChars[9]+keyword.substring(j,j+1)+hebChars[9]+keyword.substring(j+1));
					}
				//adds two yoods with two letters between them
				if (keywordsVariationsList.size()<1000)
					for (int j=1; j<keyword.length()-2; j++){
						keywordsVariationsList.add(keyword.substring(0,j)+hebChars[9]+keyword.substring(j,j+2)+hebChars[9]+keyword.substring(j+2));
					}

				// add one yood after each letter, now without taking off the original yoods, to handle the case of one/two following yoods
				keyword = (String)keywordsVariationsList.get(i);
				//adds one yood between any two letters
				if (keyword.indexOf(hebChars[9])!=-1)
					for (int j=1; j<keyword.length(); j++){
						keywordsVariationsList.add(keyword.substring(0,j)+hebChars[9]+keyword.substring(j));
					}

				// vavs

				keyword = (String)keywordsVariationsList.get(i);
				keyword = keyword.replaceAll(""+hebChars[5],"");
				keywordsVariationsList.add(keyword); //no yoods
				//adds one vav between any two letters
				for (int j=1; j<keyword.length(); j++){
					keywordsVariationsList.add(keyword.substring(0,j)+hebChars[5]+keyword.substring(j));
				}
				//adds two following vavs
				for (int j=1; j<keyword.length()-1; j++){
					keywordsVariationsList.add(keyword.substring(0,j)+hebChars[5]+keyword.substring(j,j+1)+hebChars[5]+keyword.substring(j+1));
				}
				//adds two vavs with two letters between them
				for (int j=1; j<keyword.length()-2; j++){
					keywordsVariationsList.add(keyword.substring(0,j)+hebChars[5]+keyword.substring(j,j+2)+hebChars[5]+keyword.substring(j+2));
				}
			}
	}

	public String replaceRegularLetterByFinalLetter(String word){
		String newWord=word;
		if (newWord.endsWith(""+hebChars[11])) newWord = newWord.substring(0,newWord.length()-1).concat(""+hebChars[10]);
		else if (newWord.endsWith(""+hebChars[14])) newWord = newWord.substring(0,newWord.length()-1).concat(""+hebChars[13]);
		else if (newWord.endsWith(""+hebChars[16])) newWord = newWord.substring(0,newWord.length()-1).concat(""+hebChars[15]);
		else if (newWord.endsWith(""+hebChars[20])) newWord = newWord.substring(0,newWord.length()-1).concat(""+hebChars[19]);
		else if (newWord.endsWith(""+hebChars[22])) newWord = newWord.substring(0,newWord.length()-1).concat(""+hebChars[21]);
		return newWord;
	}

	public String replaceFinalLetterByRegularLetter(String word){
			String newWord=word;
			newWord = replaceAtRightEnd(newWord,""+hebChars[10],""+hebChars[11]);
			newWord = replaceAtRightEnd(newWord,""+hebChars[13],""+hebChars[14]);
			newWord = replaceAtRightEnd(newWord,""+hebChars[15],""+hebChars[16]);
			newWord = replaceAtRightEnd(newWord,""+hebChars[19],""+hebChars[20]);
			newWord = replaceAtRightEnd(newWord,""+hebChars[21],""+hebChars[22]);
			return newWord;
		}


    public void handleCase(List<String> keywordsVariationsList, String word){
		keywordsVariationsList.add(word);
		keywordsVariationsList.add(Character.toUpperCase(word.charAt(0))+word.substring(1));
	}

    public String cutFromEnd(String word, String str){
    	if (word.endsWith(str)) word = word.substring(0,word.lastIndexOf(str));
		return word;
    }


    public String getCategory(boolean heb){
		return heb ? categoryTableNameHeb : categoryTableNameEng;		
	}

    public String getTitle(boolean heb){
		if (heb) return title.substring(title.indexOf("_")+1);
		else return title.substring(0,title.indexOf("_"));
	}



	/**
	 * @return
	 */
	public boolean isFullList() {
		return fullList;
	}

	/**
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param b
	 */
	public void setFullList(boolean b) {
		fullList = b;
	}

	/**
	 * @param string
	 */
	public void setOrderBy(String string) {
		orderBy = string;
	}

}

