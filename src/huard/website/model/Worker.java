package huard.website.model;
import huard.website.util.*;

import java.util.*;

public class Worker {
	private String englishPre;
	private String englishName;
	private String hebrewName;
	private String hebrewPre;
	private String englishTitle;
	private String hebrewTitle;
	private String phone;
	private String deskId;
	private String email;


	public Worker (){
		englishPre="";
		englishName="";
		hebrewPre="";
		hebrewName="";
		englishTitle="";
		hebrewTitle="";
		phone="";
		deskId="";
		email = "";
	}
	public Worker (Worker worker){
		englishPre=worker.getEnglishPre();
		englishName=worker.getEnglishName();
		hebrewPre=worker.getHebrewPre();
		hebrewName=worker.getHebrewName();
		englishTitle=worker.getEnglishTitle();
		hebrewTitle=worker.getHebrewTitle();
		phone=worker.getPhone();
		deskId=worker.getDeskId();
		email = worker.getEmail();
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(englishName+" ");
		sb.append(hebrewName+" ");
		sb.append(englishTitle+" ");
		sb.append(hebrewTitle+" ");
		return sb.toString();
	}

	public boolean equals(Worker worker){
		if (! this.getEnglishPre().equals(worker.getEnglishPre())) return false;
		else if (! this.getEnglishName().equals(worker.getEnglishName())) return false;
		else if (! this.getHebrewPre().equals(worker.getHebrewPre())) return false;
		else if (! this.getHebrewName().equals(worker.getHebrewName())) return false;
		else if (! this.getEnglishTitle().equals(worker.getEnglishTitle())) return false;
		else if (! this.getHebrewTitle().equals(worker.getHebrewTitle())) return false;
		else if (! this.getPhone().equals(worker.getPhone())) return false;
		else if (! this.getDeskId().equals(worker.getDeskId())) return false;
		return true;
	}

	public boolean markText(String foundBySearchWords){
		if (foundBySearchWords!=null){
			WordsTokenizer wt = new WordsTokenizer(",");
			List list = wt.getSubstringsList(foundBySearchWords);
			for (int i=0; i<list.size(); i++){
				for (int j=i+1; j<list.size(); j++){
					if (list.get(i).equals(list.get(j))) list.remove(j);
				}
			}
			Worker workerCopy = new Worker(this);
			for (int i=0; i<list.size(); i++){
				String wordToMark = (String)list.get(i);
				englishPre = addHtmlTagsBeforeAndAfterWord( englishPre, wordToMark, "<font class=hilight>", "</font>");
				englishName = addHtmlTagsBeforeAndAfterWord( englishName, wordToMark, "<font class=hilight>", "</font>");
				hebrewPre = addHtmlTagsBeforeAndAfterWord( hebrewPre, wordToMark, "<font class=hilight>", "</font>");
				hebrewName = addHtmlTagsBeforeAndAfterWord( hebrewName, wordToMark, "<font class=hilight>", "</font>");
				englishTitle = addHtmlTagsBeforeAndAfterWord( englishTitle, wordToMark, "<font class=hilight>", "</font>");
				hebrewTitle = addHtmlTagsBeforeAndAfterWord( hebrewTitle, wordToMark, "<font class=hilight>", "</font>");
				phone = addHtmlTagsBeforeAndAfterWord( phone, wordToMark, "<font class=hilight>", "</font>");
			}
			boolean fieldWasMarked = ! workerCopy.equals(this);
			return fieldWasMarked;
		}
		return false;
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

	public String getName (boolean heb){
		return heb ? hebrewName : englishName;
	}

	public String getPre (boolean heb){
		return heb ? hebrewPre.replaceAll("\"","\\\\\"") : englishPre;
	}

	public String getTitle (boolean heb){
		return heb ? getHebrewTitleDoubleQuotsBackslashed() : englishTitle;
	}


	public String getMailAddress(){
		//return englishName.replace(' ','.').toLowerCase() +"@ard.huji.ac.il";
		//return email;
		return getEmail();
	}


	/**
	 * @return
	 */
	public String getEnglishName() {
		return englishName;
	}
	public String getEnglishNameWithoutHtmlTags(){
		String text=englishName;
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos=text.indexOf("<"))!=-1){
			sb.append(text.substring(0,pos));
			text = text.substring(text.indexOf(">")+1);
		}
		sb.append(text);
		return sb.toString();
	}



	/**
	 * @return
	 */
	public String getHebrewName() {
		return hebrewName;
	}


	public String getHebrewNameWithoutHtmlTags(){
		String text=hebrewName;
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos=text.indexOf("<"))!=-1){
			sb.append(text.substring(0,pos));
			text = text.substring(text.indexOf(">")+1);
		}
		sb.append(text);
		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getPhone() {
		return phone;
	}


	public void setEnglishName(String string) {
		englishName = string;
	}

	/**
	 * @param string
	 */
	public void setHebrewName(String string) {
		hebrewName = string;
	}

	/**
	 * @param string
	 */
	public void setPhone(String string) {
		phone = string;
	}



	/**
	 * @return
	 */
	public String getDeskId() {
		return deskId;
	}

	/**
	 * @param string
	 */
	public void setDeskId(String string) {
		deskId = string;
	}

	/**
	 * @return
	 */
	public String getEnglishTitle() {
		return englishTitle;
	}

	/**
	 * @return
	 */
	public String getHebrewTitle() {
		return hebrewTitle;
	}

	public String getHebrewTitleDoubleQuotsBackslashed(){
		return hebrewTitle.replaceAll("\"","\\\\\"");
	}

	/**
	 * @param string
	 */
	public void setEnglishTitle(String string) {
		englishTitle = string;
	}

	/**
	 * @param string
	 */
	public void setHebrewTitle(String string) {
		hebrewTitle = string;
	}




	/**
	 * @return
	 */
	public String getEnglishPre() {
		return englishPre;
	}

	/**
	 * @return
	 */
	public String getHebrewPre() {
		return hebrewPre;
	}

	/**
	 * @param string
	 */
	public void setEnglishPre(String string) {
		englishPre = string;
	}

	/**
	 * @param string
	 */
	public void setHebrewPre(String string) {
		hebrewPre = string;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
