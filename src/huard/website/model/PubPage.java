package huard.website.model;

import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.util.Date;
import java.util.List;




public class PubPage extends ArdPage{
	private String html;
	private boolean message;
	private long expDate;
	private boolean toRollingMessages;
	private long stopRollingDate;
	private boolean hasImage;
	private String imageFilename;
	private boolean fileRepresentation;
	private String link;
	private boolean onSite;
	private String foundBySearchWords;
	private boolean wraper;
	private String sourceToWrap;
	private int wrappedElementHeight;
	private String category;
	private String parentPageTitle;







	public PubPage(){
		super();
		html = "";
		message=false;
		expDate = new Date().getTime()+604800000;
		toRollingMessages = false;
		stopRollingDate = new Date().getTime()+172800000;
		hasImage=false;
		imageFilename="";
		onSite=false;
	}


	public boolean isHebrew(){
		return Utils.isHebrew(title);
	}

	public String getFormatedPubDate(){
		return Utils.getFormatedDate(this.pubDate);
	}

	/**
	 * @return
	 */
	public String getHtml() {
		return html.replaceAll("<end of preview>","");
	}

	public String getShortHtmlNo_13_10(){
		if (this.html.length()==0 || this.html.indexOf("<table")!=-1) return "No Preview";
		String html="";
		if (this.html.indexOf("<end of preview>")!=-1) html = this.html.substring(0, this.html.indexOf("<end of preview>"));
		else if (this.html.indexOf("</p>")!=-1) html = this.html.substring(0, this.html.indexOf("</p>")+4);
		else html = this.html.substring(0, this.html.length()>400 ? 400 : this.html.length()-1);
		html = html.replaceAll("\"","\\\\\"");
		html = html.replaceAll((char)13+"","").replaceAll((char)10+"","");
		return html;
		/*char [] c = html.toCharArray();
		boolean inTag=false;
		int lastStartOfTagIndex=0;
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<c.length; i++){
			if ( (int)c[i] != 13 && (int)c[i] !=10) sb.append(c[i]);
			if (c[i]=='<') {
				inTag=true;
				lastStartOfTagIndex = i;
			}
			else if (c[i]=='>') inTag=false;
		}
		if (inTag) return sb.toString().substring(0,lastStartOfTagIndex);
		else return sb.toString();
		*/
	}

	public String getHtmlWithHebrewTags(){
		char [] charArray = html.toCharArray();
		boolean insideTag = false;
		StringBuffer fixedTagsText = new StringBuffer();
		for (int i=0; i< charArray.length; i++){
			if (charArray[i]=='<') insideTag = true;
			if (charArray[i]=='>') insideTag = false;
			if (insideTag==true){
				if (charArray[i]=='h') charArray[i]=1499;
				else if (charArray[i]=='p') charArray[i]=1508;
				else if (charArray[i]=='b' && (int)charArray[i+1]=='r') {
					charArray[i] = 1512;
					charArray[i+1] = 1513;
				}
				else if (charArray[i]=='u' && charArray[i+1]=='l') {
					charArray[i] = 1512;
					charArray[i+1] = 1512;
				}
				else if (charArray[i]=='o' && charArray[i+1]=='l') {
					charArray[i] = 1512;
					charArray[i+1] = 1502;
				}
				else if (charArray[i]=='l' && charArray[i+1]=='i') {
					charArray[i] = 1513;
					charArray[i+1] = 1493;
				}
				else if (charArray[i]=='b')  {
					charArray[i] = 1491;
				}
				else if (charArray[i]=='u') {
					charArray[i] = 1511 ;
				}
			}
			fixedTagsText.append(charArray[i]);
		}
		return fixedTagsText.toString();
	}

	public void markWords(String wordsToMark){
			WordsTokenizer wt = new WordsTokenizer(",");
			List<String> wordsToMarkList = wt.getSubstringsList(wordsToMark);
			// The double loop uniques the list to save computation resources
			for (int i=0; i<wordsToMarkList.size(); i++){
				for (int j=i+1; j<wordsToMarkList.size(); j++){
					if (wordsToMarkList.get(i).equals(wordsToMarkList.get(j))) wordsToMarkList.remove(j);
				}
			}
			for (int i=0; i<wordsToMarkList.size(); i++){
				String word;
				if ((word=(String)wordsToMarkList.get(i)).indexOf("\"")!=-1){
					wordsToMarkList.add(word.replaceAll("\"","&quot;"));
				}
			}
			titleHighlighted = title;
			for (int i=0; i<wordsToMarkList.size(); i++){
				String wordToMark = (String)wordsToMarkList.get(i);
				titleHighlighted = addHtmlTagsBeforeAndAfterWord(titleHighlighted, wordToMark, "<font class=hilight>", "</font>");
				//System.out.println("huard1.contents.Pubpage.markWords(): title: "+title);
				html = addHtmlTagsBeforeAndAfterWord(html, wordToMark, "<font class=hilight>", "</font>");
			}
		}

	public void markWords(){
		if (foundBySearchWords!=null) {
			WordsTokenizer wt = new WordsTokenizer(",");
			List<String> wordsToMarkList = wt.getSubstringsList(foundBySearchWords);
			//System.out.println("huard1.contents.PuPage.markWords(): foundBySearchWords: "+foundBySearchWords);
			for (int i=0; i<wordsToMarkList.size(); i++){
				for (int j=i+1; j<wordsToMarkList.size(); j++){
					if (wordsToMarkList.get(i).equals(wordsToMarkList.get(j))) wordsToMarkList.remove(j);
				}
			}
			for (int i=0; i<wordsToMarkList.size(); i++){
				String word;
				if ((word=(String)wordsToMarkList.get(i)).indexOf("\"")!=-1){
					wordsToMarkList.add(word.replaceAll("\"","&quot;"));
				}
			}
			//System.out.println("huard1.contents.Pubpage.markWords(): wordsToMarkList.size(): "+wordsToMarkList.size());
			titleHighlighted = title;
			for (int i=0; i<wordsToMarkList.size(); i++){
				String wordToMark = (String)wordsToMarkList.get(i);
				titleHighlighted = addHtmlTagsBeforeAndAfterWord(titleHighlighted, wordToMark, "<font class=hilight>", "</font>");
				//System.out.println("huard1.contents.Pubpage.markWords(): title: "+title);
				//html = addHtmlTagsBeforeAndAfterWord(html, wordToMark, "<font class=hilight>", "</font>");
			}
		}
	}


	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = wrapCharsWithSpaces(text);
		StringBuffer sb = new StringBuffer();
		int pos;
		wordToMark = wordToMark.trim();
		while (text.indexOf(" "+wordToMark)!=-1||
				text.indexOf("-"+wordToMark)!=-1 ||
				text.indexOf(" "+Utils.firstLetterToUppercase(wordToMark))!=-1 ||
				text.indexOf("*"+wordToMark)!=-1 ||
				text.indexOf("("+wordToMark)!=-1){

			text = text.replaceAll("\\*", " \\* ");
			char c=' ';
			if ((pos=text.indexOf(" "+wordToMark))!=-1) c=' ';
			else if ((pos=text.indexOf("-"+wordToMark))!=-1) c='-';
			else if ((pos=text.indexOf(" "+Utils.firstLetterToUppercase(wordToMark)))!=-1) c=' ';
			else if ((pos=text.indexOf("*"+wordToMark))!=-1) c='*';
			else if ((pos=text.indexOf("("+wordToMark))!=-1) c='(';
			String textBeforeWord = text.substring(0, pos);
			/*System.out.println("************************************");
			System.out.println("TextBeforeWord: "+textBeforeWord);
			System.out.println("************************************");*/
			String textFromWordAndOn = text.substring(pos+1);
			/*System.out.println("************************************");
			System.out.println("TextFromWordAndOn: "+textFromWordAndOn);
			System.out.println("************************************");*/
			String textAfterWord = textFromWordAndOn.substring(wordToMark.length());
			/*System.out.println("************************************");
			System.out.println("TextAfterWord: "+textAfterWord);
			System.out.println("************************************");*/
			String textAfterWordBeforeFirstSpace = textAfterWord.substring(0, (pos = textAfterWord.indexOf(" "))!=-1 ? pos : textAfterWord.length());
			/*System.out.println("************************************");
			System.out.println("TextAfterWordBeforeFirstSpace: "+textAfterWordBeforeFirstSpace);
			System.out.println("************************************");*/
			String textAfterWordAfterFirstSpace = textAfterWord.substring( pos !=-1 ? pos : textAfterWord.length());
			/*System.out.println("************************************");
			System.out.println("TextAfterWordAfterFirstSpace: "+textAfterWordAfterFirstSpace);
			System.out.println("************************************");*/
			textBeforeWord = trimChars(textBeforeWord);
			sb.append(textBeforeWord.replaceAll(" \\* ", "\\*"));
			String stringToAppend = c + tagBeforeWord + wordToMark +
			textAfterWordBeforeFirstSpace + tagAfterWord;
			stringToAppend = stringToAppend.replaceAll(" \\* ", "\\*");
			/*System.out.println("************************************");
			System.out.println("StringToAppend: "+stringToAppend);
			System.out.println("************************************");*/
			sb.append(stringToAppend);
			text = textAfterWordAfterFirstSpace;
			text = text.replaceAll(" \\* ", "\\*");
		}
		text = trimChars(text);
		/*System.out.println("************************************");
		System.out.println("RestOfTheTextFixed: "+text);
		System.out.println("************************************");*/
		sb.append(text);
		return sb.toString();
	}

	public String wrapCharsWithSpaces(String text){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		return text;
	}
	public String trimChars(String text){
		text = text.replaceAll(" ~ ","~");
		text = text.replaceAll(" <","<");
		text = text.replaceAll("> ",">");
		//text = text.trim();
		return text;
	}


	public String minimizeSpaces(String text){
		char [] c = text.toCharArray();
		StringBuffer sb = new StringBuffer("");
		boolean lastWasSpace=false;
		for (int i=0; i<c.length; i++){
			if ((int)c[i]==32) {
				if (! lastWasSpace) sb.append(c[i]);
				lastWasSpace = true;
			}
			else {
				sb.append(c[i]);
				lastWasSpace=false;
			}
		}
		String newText = sb.toString().replaceAll("> ",">").replaceAll(" <","<");
		return newText;

	}
	/*
	 *
	 * old one
	 *
	 *public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		StringBuffer sb = new StringBuffer();
		int pos;
		wordToMark = wordToMark.trim();
		//int counter=0;
		while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
			String textBeforeWord = text.substring(0,pos);
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): textBeforeWord: "+textBeforeWord);
			String textFromWordAndOn = text.substring(pos+1);
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): textFromWord: "+textFromWordAndOn);
			//int spaceAfterWordPos = textFromWordAndOn.substring(wordToMark.length()-1).indexOf(" ")+wordToMark.length();
			int pos1;
			int spaceAfterWordPos = ((pos1 = textFromWordAndOn.substring(wordToMark.length()-1).indexOf(" ")) !=-1) ? pos1+wordToMark.length() : -1;
			sb.append(textBeforeWord);
			String tagedWord = (textBeforeWord.length()>0 ? "&nbsp;" : "") +tagBeforeWord +
			textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()).trim() + tagAfterWord;
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): tagedWord: "+tagedWord);
			sb.append(tagedWord);
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): "+text.substring(0,text.length()>100 ? 100 : text.length())+" "+counter);
			text = spaceAfterWordPos!=-1 ? "&nbsp;"+textFromWordAndOn.substring(spaceAfterWordPos+1) : "";
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): counter+text:"+counter+" "+text);
			//counter++;
		}
		sb.append(text);
		String newText = minimizeSpaces(sb.toString());
		System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): newText:"+newText);
		return newText;
	}

	public String minimizeSpaces(String text){
		char [] c = text.toCharArray();
		StringBuffer sb = new StringBuffer("");
		boolean lastWasSpace=false;
		for (int i=0; i<c.length; i++){
			if ((int)c[i]==32) {
				if (! lastWasSpace) sb.append(c[i]);
				lastWasSpace = true;
			}
			else {
				sb.append(c[i]);
				lastWasSpace=false;
			}
		}
		String newText = sb.toString().replaceAll("> ",">").replaceAll(" <","<");
		return newText;

	}
*/
/*
	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
			String textBeforeWord = text.substring(0,pos);
			String textFromWordAndOn = text.substring(pos+1);
			int spaceAfterWordPos = textFromWordAndOn.indexOf(" ");
			sb.append(textBeforeWord);
			sb.append(tagBeforeWord +
					textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()) + tagAfterWord);
			text = spaceAfterWordPos!=-1 ? " "+textFromWordAndOn.substring(spaceAfterWordPos) : "";
		}
		sb.append(text);
		return minimizeSpaces(sb.toString());
	}

	public String minimizeSpaces(String text){
		char [] c = text.toCharArray();
		StringBuffer sb = new StringBuffer("");
		boolean lastWasSpace=false;
		for (int i=0; i<c.length; i++){
			if ((int)c[i]==32) {
				if (! lastWasSpace) sb.append(c[i]);
				lastWasSpace = true;
			}
			else {
				sb.append(c[i]);
				lastWasSpace=false;
			}
		}
		//System.out.println("huard1.contents.PubPage.minimizeSpaces(): "+sb.toString());
		return sb.toString();
	}

*/
	/**
	 * @param string
	 */
	public void setHtml(String string) {
		html = string;
	}

	/**
	 * @return
	 */
	public long getExpDate() {
		return expDate;
	}

	public String getFormatedExpDate(){
		return Utils.getFormatedDate(expDate);
	}

	/**
	 * @return
	 */
	public boolean isMessage() {
		return message;
	}

	/**
	 * @return
	 */
	public long getStopRollingDate() {
		return stopRollingDate;
	}

	public String getFormatedStopRollingDate(){
		return Utils.getFormatedDate(stopRollingDate);
	}
	/**
	 * @return
	 */
	public boolean isToRollingMessages() {
		return toRollingMessages;
	}

	/**
	 * @param l
	 */
	public void setExpDate(long l) {
		expDate = l;
	}

	/**
	 * @param b
	 */
	public void setMessage(boolean b) {
		message = b;
	}

	/**
	 * @param l
	 */
	public void setStopRollingDate(long l) {
		stopRollingDate = l;
	}

	/**
	 * @param b
	 */
	public void setToRollingMessages(boolean b) {
		toRollingMessages = b;
	}


	/**
	 * @return
	 */
	public boolean isHasImage() {
		return hasImage;
	}

	/**
	 * @return
	 */
	public String getImageFilename() {
		return imageFilename;
	}

	/**
	 * @param b
	 */
	public void setHasImage(boolean b) {
		hasImage = b;
	}

	/**
	 * @param string
	 */
	public void setImageFilename(String string) {
		imageFilename = string;
	}

	/**
	 * @return
	 */
	public boolean isFileRepresentation() {
		return fileRepresentation;
	}

	/**
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param b
	 */
	public void setFileRepresentation(boolean b) {
		fileRepresentation = b;
	}

	/**
	 * @param string
	 */
	public void setLink(String string) {
		link = string;
	}

	/**
	 * @return
	 */
	public boolean isOnSite() {
		return onSite;
	}

	/**
	 * @param b
	 */
	public void setOnSite(boolean b) {
		onSite = b;
	}


	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}
	public String getFoundBySearchWordsHandled(){
		String foundBySearchWordHandled = foundBySearchWords.replaceAll("&","%26");
		foundBySearchWordHandled = foundBySearchWords.replaceAll("\"","%22");
		return foundBySearchWordHandled;
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
	}

	public String getCategory() {
		if (message) {
			if (docType.equals("Funding")) return this.isHebrew() ? "efsharuiot_mimun" : "financeSources";
			else return this.isHebrew() ? "harashut_lemop" : "RD_authority";
		}
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getFileFormat(){
		if (fileRepresentation) return link.substring(link.lastIndexOf(".")+1);
		else return "";
	}


	public String getSourceToWrap() {
		return sourceToWrap;
	}


	public void setSourceToWrap(String sourceToWrap) {
		this.sourceToWrap = sourceToWrap;
	}


	public boolean isWraper() {
		return wraper;
	}


	public void setWraper(boolean wraper) {
		this.wraper = wraper;
	}

	public String getParentPageTitle() {
		return parentPageTitle;
	}


	public void setParentPageTitle(String parentPageTitle) {
		this.parentPageTitle = parentPageTitle;
	}


	public int getWrappedElementHeight() {
		return wrappedElementHeight;
	}


	public void setWrappedElementHeight(int wrappedElementHeight) {
		this.wrappedElementHeight = wrappedElementHeight;
	}




}
