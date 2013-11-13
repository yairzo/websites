package huard3.actions;
import huard3.utils.PubPagesUpdate;
import huard3.utils.Utils;
import huard3.utils.WordsTokenizer;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class PubPageUpdater {
	private boolean message;
	private int ardNum;
	private String title;
	private long pubDate;
	private String html;
	private String docType;
	private String docOwner;
	private String deskId;
	private boolean restricted;
    private String keywords;
    private String expDate;
    private boolean toRollingMessages;
    private String stopRollingDate;
    private boolean hasImage;
    private String imageFilename;
    private boolean fileRepresentation;
    private String link;
    private String referenceFile;
    private String source;
    private String internalUseDescription;
    private boolean wraper;
    private String sourceToWrap;
    private boolean onSite;
	private boolean newPubPage;
	private PubPage pubPage;
	private PubPagesUpdate pubPagesUpdate;

	public PubPageUpdater (){
		pubPagesUpdate = new PubPagesUpdate();
	}

	public PubPage getPubPage(){
		if (pubPage==null){
			pubPage = new PubPage();
			pubPage.setArdNum(ardNum);
			pubPage.setPubDate(pubDate);
			pubPage.setTitle(editText(title));
			pubPage.setHtml(editText(html));
			pubPage.setDocType(docType);
			pubPage.setDeskId(deskId);
			pubPage.setDocOwner(docOwner);
			pubPage.setRestricted(restricted);
			pubPage.setKeywords(keywords);
			pubPage.setMessage(message);
			if (message){
				try{
					pubPage.setExpDate(DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(expDate).getTime());
					pubPage.setStopRollingDate(DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(stopRollingDate).getTime());
				}
				catch(ParseException pe){
					pubPage.setPubDate(0);
				}
			}
			pubPage.setToRollingMessages(toRollingMessages);
			pubPage.setHasImage(hasImage);
			pubPage.setImageFilename(imageFilename);
			pubPage.setFileRepresentation(fileRepresentation);
			pubPage.setLink(link);
			pubPage.setReferenceFile(referenceFile);
			pubPage.setSource(source);
			pubPage.setInternalUseDescription(internalUseDescription);
			pubPage.setWraper(wraper);
			pubPage.setSourceToWrap(sourceToWrap);
			pubPage.setOnSite(onSite);

		}
		return pubPage;
	}


	public boolean insertOrUpdatePubPageToDatabase (String username){
		if (newPubPage) {
			//LogFileHandler.writeToUserLog("create", username, getPubPage(),"");
			if (pubPagesUpdate.insertPubPage(getPubPage()))
				return insertPubPageKeywordsIntoDatabase();
			else return false;
		}
		else {
			//LogFileHandler.writeToUserLog("update", username, getPubPage(),"");
			if (pubPagesUpdate.updatePubPage(getPubPage()))
				return insertPubPageKeywordsIntoDatabase();
			else return false;
		}
	}

	public boolean insertPubPageKeywordsIntoDatabase(){
		return pubPagesUpdate.insertPubPageKeywordsIntoDatabase(getPubPage().getArdNum(),getKeywordsArray());
	}

	public String[] getKeywordsArray(){
		if (pubPage.getKeywords().equals("null")) pubPage.setKeywords(pubPage.getTitle());
		WordsTokenizer wt= new WordsTokenizer(",");
		List list = wt.getSubstringsList(getPubPage().getKeywords()) ;
		String[] keywords = new String[list.size()];
		for ( int i = 0 ; i < list.size() ; ++i ){
			keywords[i]=""+list.get( i );
		}
		return keywords;
	}


	public String editText(String text){
		if (text!=null){
			text = text.replaceAll("\"","&quot;");
			//text = text.replaceAll("#","");
			if (Utils.isHebrew(text)) text = handleHebrewTags(text);
		}
		return text;
	}

	public String handleHebrewTags(String text){
		char [] charArray = text.toCharArray();
		boolean insideTag = false;
		StringBuffer fixedTagsText = new StringBuffer();
		for (int i=0; i< charArray.length; i++){
			if (charArray[i]=='<') insideTag = true;
			if (charArray[i]=='>') insideTag = false;
			if (insideTag==true){
				if ((int)charArray[i]==1499) charArray[i]='h';
				else if ((int)charArray[i]==1508) charArray[i]='p';
				else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1513) {
					charArray[i]='b';
					charArray[i+1]='r';
				}
				else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1512) {
					charArray[i]='u';
					charArray[i+1]='l';

				}
				else if ((int)charArray[i]==1512 && (int)charArray[i+1]==1502) {
					charArray[i]='o';
					charArray[i+1]='l';

				}
				else if ((int)charArray[i]==1513 && (int)charArray[i+1]==1493) {
					charArray[i]='l';
					charArray[i+1]='i';

				}
				else if ((int)charArray[i]==1491) {
					charArray[i]='b';
				}
				else if ((int)charArray[i]==1511) {
					charArray[i]='u';
					charArray[i+1]=' ';
				}
			}
			//System.out.println(charArray[i]+" "+(int)charArray[i]);
			fixedTagsText.append(charArray[i]);
		}
		return fixedTagsText.toString();
	}


	/**
	 * @return
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * @return
	 */
	public String getDeskId() {
		return deskId;
	}

	/**
	 * @return
	 */
	public String getDocOwner() {
		return docOwner;
	}

	/**
	 * @return
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @return
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @return
	 */
	public long getPubDate() {
		return pubDate;
	}

	/**
	 * @return
	 */
	public boolean isRestricted() {
		return restricted;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param i
	 */
	public void setArdNum(int i) {
		ardNum = i;
	}

	/**
	 * @param string
	 */
	public void setDeskId(String string) {
		deskId = string;
	}

	/**
	 * @param string
	 */
	public void setDocOwner(String string) {
		docOwner = string;
	}

	/**
	 * @param string
	 */
	public void setHtml(String string) {
		html = string;
	}

	/**
	 * @param string
	 */
	public void setKeywords(String string) {
		keywords = string;
	}

	/**
	 * @param l
	 */
	public void setPubDate(long l) {
		pubDate = l;
	}

	/**
	 * @param b
	 */
	public void setRestricted(boolean b) {
		restricted = true;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @return
	 */
	public boolean isNewPubPage() {
		return newPubPage;
	}

	/**
	 * @param b
	 */
	public void setNewPubPage(boolean b) {
		newPubPage = true;
	}

	/**
	 * @return
	 */
	public boolean isMessage() {
		return message;
	}

	/**
	 * @param b
	 */
	public void setMessage(boolean b) {
		message = b;
	}

	/**
	 * @return
	 */
	public String getExpDate() {
		return expDate;
	}

	/**
	 * @return
	 */
	public String getStopRollingDate() {
		return stopRollingDate;
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
	public void setExpDate(String s) {
		expDate = s;
	}

	/**
	 * @param l
	 */
	public void setStopRollingDate(String s) {
		stopRollingDate = s;
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

	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param string
	 */
	public void setDocType(String string) {
		docType = string;
	}

	/**
	 * @return
	 */
	public String getReferenceFile() {
		return referenceFile;
	}

	/**
	 * @param string
	 */
	public void setReferenceFile(String string) {
		referenceFile = string;
	}

	/**
	 * @return
	 */
	public String getInternalUseDescription() {
		return internalUseDescription;
	}

	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param string
	 */
	public void setInternalUseDescription(String string) {
		internalUseDescription = string;
	}

	/**
	 * @param string
	 */
	public void setSource(String string) {
		source = string;
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
