package huard.website.viewer.page;

import huard.website.model.PubPage;
import huard.website.util.PageAccessLog;
import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.util.List;

public class PubPageViewer {
	private int ardNum;
	private String title;
	private long pubDate;
	private PubPage pubPage;
	private PagesDbHandler dbHandler;
	// private PubPage [] pubPages;
	private String foundBySearchWords;
	private String category;
	private String parentPageTitle;

	public PubPageViewer() {
		dbHandler = new PagesDbHandler();
	}

	public void logAccessToPage(boolean heb, String ip) {
		PageAccessLog.logAccesToPage("Free text pages"
				+ (getPubPage().isMessage() ? " - Message" : ""), getPubPage()
				.getArdNum(), getPubPage().getTitle(), getPubPage().isHebrew(),
				ip);
	}

	public PubPage getPubPage() {
		if (pubPage == null) {
			pubPage = dbHandler.getPubPageByArdNum(ardNum);
			if (foundBySearchWords != null)
				pubPage.markWords(foundBySearchWords);
		}
		return pubPage;
	}

	public boolean isAuthorized(String ipString) {
		if (getPubPage().isRestricted()) {
			WordsTokenizer wt = new WordsTokenizer(".");
			List<String> addressNumsList = wt.getSubstringsList(ipString);
			int[] addressNumsArray = new int[addressNumsList.size()];
			for (int i = 0; i < addressNumsArray.length; i++) {
				addressNumsArray[i] = Integer.parseInt((String) addressNumsList
						.get(i));
			}
			if (addressNumsArray[0] == 132) {
				if (addressNumsArray[1] >= 64 && addressNumsArray[1] <= 65)
					return true;
				else
					return false;
			} else if (addressNumsArray[0] == 128 && addressNumsArray[1] == 139) {
				if (addressNumsArray[2] <= 31)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return true;
	}

	public String getFormatedText(String text) {
		text = text.replaceAll("~", "<br>");
		text = text.replaceAll("&quot;", "\"");
		if (Utils.isHebrew(getPubPage().getTitle())) {
			text = text.replaceAll("<ul>", "<ul class=\"heb\">");
			text = text.replaceAll("<ol>", "<ol class=\"heb\">");
		}
		WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
		List<String> elementsList = elementsTokenizer.getSubstringsListNoTrim(text);
		int elementsListSize;
		if ((elementsListSize = elementsList.size()) >= 3) {
			StringBuffer textBuffer = new StringBuffer();
			int i, k = (int) elementsListSize / 3;
			for (i = 0; i < k; i++) {
				textBuffer.append((String) elementsList.get(i * 3));
				String linkText = (String) elementsList.get(i * 3 + 1);
				String linkAddress = (String) elementsList.get(i * 3 + 2);
				textBuffer.append("<a href=\""
						+ linkAddress
						+ (isTargetLocalPubPage(linkAddress) ? "&category="
								+ getPubPage().getCategory()
								+ "&parentPageTitle=" + getPubPage().getTitle()
								: "") + "\" target=\""
						+ (isTargetLocalPubPage(linkAddress) ? "" : "_blank")
						+ "\">" + linkText + "</a>");
			}
			for (int j = i * 3; j < elementsListSize; j++)
				textBuffer.append("&nbsp;" + (String) elementsList.get(j));
			text = textBuffer.toString();
		}
		// System.out.println(text);
		return text;
	}

	/*
	 * public boolean isInnerLink(String link){ return
	 * ((link.indexOf("ard.huji.ac.il") != -1 || link.indexOf('/')==0) &&
	 * link.indexOf("ftp://")==-1 ); }
	 */
	public boolean isTargetLocalPubPage(String link) {
		if (link.indexOf("pubPageViewer.jsp?ardNum=") != -1
				|| link.indexOf("pubPageWraper.jsp?ardNum=") != -1)
			return true;
		else
			return false;
	}

	public String getFormatedDate(long date) {
		return Utils.getFormatedDate(date);
	}

	public boolean isHebrew(String str) {
		return Utils.isHebrew(str);
	}

	public String getFormatedPubPageLastUpdateDate() {
		return Utils.getFormatedDate(dbHandler
				.getPubPageLastUpdate(getPubPage().getArdNum()));
	}

	/*
	 * This method doesn't belong here public PubPage [] getToRollingMessages(){
	 * if (pubPages==null) pubPages = dbHandler.getToRollingMessages(); return
	 * pubPages; }
	 */

	/**
	 * @return
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * @param string
	 */
	public void setArdNum(int ardNum) {
		this.ardNum = ardNum;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param string
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return
	 */
	public long getPubDate() {
		return pubDate;
	}

	/**
	 * @param string
	 */
	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
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

	public String getCategory() {
		if (category != null && !category.isEmpty()) {
			System.out.println("Category by param: " + category);
			return category;
		}
		category = getPubPage().getCategory();
		System.out.println("Category page: " + category);
		if (category == null || category.isEmpty()) {
			category = Utils.DEFAULT_PUB_PAGE_CATEGORY;
			System.out.println("Category website default: " + category);
		}
		return category;
	}

	public String getCategoryLang() {
		return Utils.isHebrew(getCategory()) ? "heb" : "eng";
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isHasParentPage() {
		if (!getParentPageTitle().equals(""))
			return true;
		else
			return false;
	}

	public String getParentPageTitle() {
		if (parentPageTitle != null && !parentPageTitle.equals(""))
			return parentPageTitle;
		else if (getPubPage().getParentPageTitle() != null
				&& !getPubPage().getParentPageTitle().equals(""))
			return getPubPage().getParentPageTitle();
		else
			return "";
	}

	public void setParentPageTitle(String parentPageTitle) {
		this.parentPageTitle = parentPageTitle;
	}

}
