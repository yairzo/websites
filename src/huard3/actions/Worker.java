package huard3.actions;
import huard3.utils.*;

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


	public Worker (){
		englishPre="";
		englishName="";
		hebrewPre="";
		hebrewName="";
		englishTitle="";
		hebrewTitle="";
		phone="";
		deskId="";
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

	/*public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		//System.out.println("Worker.addHtmlTagsBeforeAndAfterWord(): entered once !");
		StringBuffer sb = new StringBuffer();
		if (text.indexOf(wordToMark)!=-1){
			text = " "+text;
			text = text.replaceAll("~"," ~ ");
			text = text.replaceAll("<"," <");
			text = text.replaceAll(">","> ");
			int pos;
			while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
				String textBeforeWord = text.substring(0,pos);
				String textFromWordAndOn = text.substring(pos+1);
				int spaceAfterWordPos = textFromWordAndOn.substring(wordToMark.length()-1).indexOf(" ")+wordToMark.length();
				sb.append(textBeforeWord);
				sb.append(" "+tagBeforeWord + textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()) + tagAfterWord+" ");
				text = spaceAfterWordPos!=-1 ? " "+textFromWordAndOn.substring(spaceAfterWordPos) : "";
				//System.out.println("Worker.addHtmlTagsBeforeAndAfterWord(): sb: "+sb.toString());
				//System.out.println("Worker.addHtmlTagsBeforeAndAfterWord(): text: "+text);
			}
			sb.delete(0,1);
		}
		sb.append(text);
		return sb.toString();
	}*/

	/*
	 *
	 *
	 * old one
	 *
	 *
	 *public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		StringBuffer sb = new StringBuffer();
		int pos;
		wordToMark = wordToMark.trim();
		while ((pos=text.indexOf(" "+wordToMark))!=-1 || (pos=text.indexOf("-"+wordToMark))!=-1){
			String textBeforeWord = text.substring(0,pos);
			String textFromWordAndOn = text.substring(pos+1);
			int pos1;
			int spaceAfterWordPos = ((pos1 = textFromWordAndOn.substring(wordToMark.length()-1).indexOf(" ")) !=-1) ? pos1+wordToMark.length() : -1;
			sb.append(textBeforeWord);
			sb.append((textBeforeWord.length()>0 ? "&nbsp;" : "") +tagBeforeWord +
					textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()).trim() + tagAfterWord);
			text = spaceAfterWordPos!=-1 ? "&nbsp;"+textFromWordAndOn.substring(spaceAfterWordPos+1) : "";
		}
		sb.append(text);
		String newText = minimizeSpaces(sb.toString());
		//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): "+newText);
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
	public String addHtmlTagsBeforeAndAfterWord(String text, String wordToMark, String tagBeforeWord, String tagAfterWord){
		text = " "+text;
		text = text.replaceAll("~"," ~ ");
		text = text.replaceAll("<"," <");
		text = text.replaceAll(">","> ");
		StringBuffer sb = new StringBuffer();
		int pos;
		wordToMark = wordToMark.trim();
		while ((pos=text.indexOf(" "+wordToMark))!=-1
				|| (pos=text.indexOf("-"+wordToMark))!=-1
				|| (pos=text.indexOf(" "+Utils.firstLetterToUppercase(wordToMark)))!=-1){
			String textBeforeWord = text.substring(0,pos);
			String textFromWordAndOn = text.substring(pos+1);
			//int spaceAfterWordPos = textFromWordAndOn.substring(wordToMark.length()).indexOf(" ");
			int pos1;
			int spaceAfterWordPos = ((pos1 = textFromWordAndOn.substring(wordToMark.length()).indexOf(" ")) !=-1) ? pos1+wordToMark.length() : -1;
			sb.append(textBeforeWord);
			sb.append("&nbsp;" +tagBeforeWord +
					textFromWordAndOn.substring(0,spaceAfterWordPos !=-1 ? spaceAfterWordPos : textFromWordAndOn.length()).trim() + tagAfterWord);
			text = spaceAfterWordPos!=-1 ? "&nbsp;"+textFromWordAndOn.substring(spaceAfterWordPos+1) : "";
			//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): "+text);
		}
		sb.append(text);
		String newText = minimizeSpaces(sb.toString());
		//System.out.println("huard1.contents.TabledInfoPage.addHtmlTagsBeforeAndAfterWord(): "+newText);
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
		return Utils.getMailAddress(getEnglishNameWithoutHtmlTags());
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


}
