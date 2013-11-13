package huard3.actions;
import java.util.*;
import huard3.utils.*;


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
	private boolean wraper;
	private String sourceToWrap;
	private boolean onSite;
	private boolean hebrewEdit;
	private String foundBySearchWords;


	public PubPage(){
		super();
		html = "";
		message=false;
		expDate = new Date().getTime()+604800000;
		toRollingMessages = false;
		stopRollingDate = new Date().getTime()+172800000;
		hasImage=false;
		imageFilename="";
		wraper=false;
		sourceToWrap="";
		onSite=false;

	}

	public String getFormatedPubDate(){
		return Utils.getFormatedDate(this.pubDate);
	}

	/**
	 * @return
	 */
	public String getHtml() {
		return html;
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
			List wordsToMarkList = wt.getSubstringsList(wordsToMark);
			// The double loop uniques the list to save computation resources
			for (int i=0; i<wordsToMarkList.size(); i++){
				for (int j=i+1; j<wordsToMarkList.size(); j++){
					if (wordsToMarkList.get(i).equals(wordsToMarkList.get(j))) wordsToMarkList.remove(j);
				}
			}
			for (int i=0; i<wordsToMarkList.size(); i++){
				String wordToMark = (String)wordsToMarkList.get(i);
				html = addHtmlTagsBeforeAndAfterWord(html, wordToMark, "<font class=hilight>", "</font>");
			}
		}


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
			sb.append(" "+tagBeforeWord + textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()) + tagAfterWord+" ");
			text = spaceAfterWordPos!=-1 ? " "+textFromWordAndOn.substring(spaceAfterWordPos) : "";
		}
		sb.append(text);
		return sb.toString();
	}


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
		return foundBySearchWords.replaceAll("&","%26");
	}

	/**
	 * @param string
	 */
	public void setFoundBySearchWords(String string) {
		foundBySearchWords = string;
	}

	public boolean isHebrewEdit() {
		return hebrewEdit;
	}

	public void setHebrewEdit(boolean hebrewEdit) {
		this.hebrewEdit = hebrewEdit;
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



}
